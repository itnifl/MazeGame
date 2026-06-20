package main.game.maze.libgdx.helper;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.common.movement.WorldView;
import main.game.maze.game.runtime.EnemyDirectorService;
import main.game.maze.game.session.GameMode;
import main.game.maze.game.session.GameSession;
import main.game.maze.libgdx.controller.GdxWinOverlayController;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.game.PlayerCombatStateService;
import main.game.maze.libgdx.model.DeadEnemy;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.libgdx.movement.GdxWorldView;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;

/**
 * Coordinates enemy movement, combat frame updates, and win transition checks.
 */
public final class GdxGameCombatAndEnemyFlowSupport {

    private static final float EXPLOSION_SHAKE_DURATION_SECONDS = 0.20f;

    private GdxGameCombatAndEnemyFlowSupport() {
    }

    public static void advanceEnemies(
            List<GdxEnemyRuntime> animatedEnemies,
            MazeArena maze,
            PlayerState player,
            EnemyDirectorService enemyDirectorService,
            float dt) {
        if (animatedEnemies.isEmpty() || maze == null || player == null) {
            return;
        }
        WorldView world = new GdxWorldView(maze, player);
        enemyDirectorService.advanceAll(animatedEnemies, world, dt);
        for (GdxEnemyRuntime enemy : animatedEnemies) {
            enemy.tickInvulnerability(dt);
        }
    }

    public static boolean updateCombat(
            float dt,
            PlayerState player,
            PlayerCombatStateService combatState,
            List<GdxEnemyRuntime> animatedEnemies,
            GameWorldModel worldModel) {
        int projectileDamage = updateRangedAttacks(dt, player, animatedEnemies, worldModel);
        if (projectileDamage > 0) {
            combatState.applyDirectDamage(projectileDamage);
        }
        var combatFrame = combatState.update(
                dt,
                player.x(),
                player.y(),
                player.halfSize(),
                GdxGameRuntimeSupport.currentEnemyContacts(animatedEnemies));
        worldModel.setCurrentHpRatio(combatFrame.hpRatio());
        worldModel.setPlayerTintRed(combatFrame.tintRed());
        worldModel.setPlayerTintGreen(combatFrame.tintGreen());
        worldModel.setPlayerTintBlue(combatFrame.tintBlue());
        worldModel.setInfectionWarningVisible(combatFrame.infected());
        return combatFrame.dead();
    }

    private static int updateRangedAttacks(
            float dt,
            PlayerState player,
            List<GdxEnemyRuntime> animatedEnemies,
            GameWorldModel worldModel) {
        if (player == null || worldModel == null) {
            return 0;
        }
        worldModel.setExplosionShakeRemainingSeconds(
                Math.max(0f, worldModel.explosionShakeRemainingSeconds() - Math.max(0f, dt)));
        if (worldModel.explosionShakeRemainingSeconds() <= 0f) {
            worldModel.setExplosionShakeIntensity(0f);
        }
        worldModel.enemyProjectiles().clear();
        worldModel.enemyBeams().clear();
        worldModel.enemyImpacts().clear();

        int totalDamage = 0;
        float maxShake = 0f;
        for (GdxEnemyRuntime enemy : animatedEnemies) {
            totalDamage += enemy.updateRangedAttacks(
                    dt,
                    worldModel.maze(),
                    player.x(),
                    player.y(),
                    player.halfSize(),
                    GdxWallDamageSupport.wallHitCallback(worldModel.maze(), enemy.attackDamage()));
            worldModel.enemyProjectiles().addAll(enemy.projectileVisuals());
            worldModel.enemyBeams().addAll(enemy.beamVisuals());
            var impacts = enemy.impactVisuals();
            worldModel.enemyImpacts().addAll(impacts);
            for (GdxEnemyRuntime.ImpactVisual impact : impacts) {
                maxShake = Math.max(maxShake, impact.shakeMagnitude());
            }
        }
        if (maxShake > 0f) {
            worldModel.setExplosionShakeRemainingSeconds(EXPLOSION_SHAKE_DURATION_SECONDS);
            worldModel.setExplosionShakeIntensity(Math.max(worldModel.explosionShakeIntensity(), maxShake));
        }
        return totalDamage;
    }

    /**
     * Kill every living enemy in {@code animatedEnemies}.
     * Enemies with {@code resurrectionTimeMs > 0} are moved to {@code deadEnemies}
     * so they reappear after the configured delay; the rest are simply removed.
     *
     * @return the number of enemies killed
     */
    public static int killEnemies(
            List<GdxEnemyRuntime> animatedEnemies,
            List<DeadEnemy> deadEnemies) {
        int killed = 0;
        List<GdxEnemyRuntime> toRemove = new ArrayList<>();
        for (GdxEnemyRuntime enemy : animatedEnemies) {
            if (!enemy.isAlive()) {
                continue;
            }
            enemy.kill();
            killed++;
            EnemySpawn spawn = enemy.originalSpawn();
            if (spawn.resurrectionTimeMs() > 0) {
                float seconds = spawn.resurrectionTimeMs() / 1000f;
                deadEnemies.add(new DeadEnemy(spawn, seconds));
            }
            toRemove.add(enemy);
        }
        animatedEnemies.removeAll(toRemove);
        return killed;
    }

    /**
     * Advance resurrection timers and re-add any enemy whose countdown has expired.
     * Respawned enemies receive a brief invulnerability window so the player is not
     * instantly damaged on their reappearance.
     */
    public static void tickResurrections(
            List<DeadEnemy> deadEnemies,
            List<GdxEnemyRuntime> animatedEnemies,
            WorldView world,
            float javaFxTickRate,
            int maxEnemyTicksPerFrame,
            float dt) {
        if (deadEnemies.isEmpty()) {
            return;
        }
        List<DeadEnemy> readyToRise = new ArrayList<>();
        for (DeadEnemy dead : deadEnemies) {
            dead.tick(dt);
            if (dead.ready()) {
                readyToRise.add(dead);
            }
        }
        deadEnemies.removeAll(readyToRise);
        int phaseOffset = animatedEnemies.size();
        for (DeadEnemy dead : readyToRise) {
            GdxEnemyRuntime risen = GdxEnemyRuntime.fromSpawn(
                    dead.spawn(), phaseOffset++, world, javaFxTickRate, maxEnemyTicksPerFrame);
            risen.grantRespawnInvulnerability();
            animatedEnemies.add(risen);
        }
    }

    public static boolean shouldTriggerWin(GameSession session, boolean combatFrameDead, PlayerState player, GameWorldModel worldModel) {
        return session.mode() == GameMode.PLAYING
                && !combatFrameDead
                && player != null
                && player.reached(worldModel.activeGoalX(), worldModel.activeGoalY(), worldModel.activeGoalSize() * 0.5f);
    }

    public static void triggerWin(
            GameSession session,
            GdxWinOverlayController winOverlayController,
            GameWorldModel worldModel,
            Runnable switchToWinMusic) {
        session.setMode(GameMode.WON);
        winOverlayController.reset(session);
        if (!worldModel.playedWinSound()) {
            worldModel.setPlayedWinSound(true);
            switchToWinMusic.run();
        }
    }
}
