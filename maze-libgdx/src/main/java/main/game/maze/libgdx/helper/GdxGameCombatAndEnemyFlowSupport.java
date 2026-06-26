package main.game.maze.libgdx.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;
import main.game.maze.common.movement.WorldView;
import main.game.maze.game.runtime.EnemyDirectorService;
import main.game.maze.game.session.GameMode;
import main.game.maze.game.session.GameSession;
import main.game.maze.libgdx.controller.GdxWinOverlayController;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.game.PlayerCombatStateService;
import main.game.maze.libgdx.model.DeadEnemy;
import main.game.maze.libgdx.model.EnemyDeathAnimation;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.libgdx.movement.GdxWorldView;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.flame.DirectionalFlameEngine;
import main.game.maze.mazeworld.flame.FlameTarget;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.constants.StageConstants;

/**
 * Coordinates enemy movement, combat frame updates, and win transition checks.
 */
public final class GdxGameCombatAndEnemyFlowSupport {

    private static final float EXPLOSION_SHAKE_DURATION_SECONDS = 0.20f;
    /** Half-width of the flame corridor in pixels. Four cells wide so the blast fills the corridor visibly. */
    private static final float FLAME_CORRIDOR_HALF_WIDTH = 120f;
    /** Full corridor width exposed for rendering — 85 %³ of one maze hallway for a snug fit. */
    public static final float FLAME_CORRIDOR_WIDTH = StageConstants.HallwayWidthPx * 0.85f * 0.85f * 0.85f;

    /**
     * Returns the pixel distance the flame visually reaches in direction {@code (dirX, dirY)}
     * before hitting a surviving wall, capped at {@code maxRange}.
     * Call AFTER applying damage so that destroyed walls are already absent from {@code walls}.
     */
    public static float flameVisualRange(float originX, float originY,
                                         int dirX, int dirY,
                                         List<Vector2D> walls, float maxRange) {
        return (float) DirectionalFlameEngine.flameVisualRange(
                originX, originY, dirX, dirY, walls, maxRange, FLAME_CORRIDOR_HALF_WIDTH);
    }

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

    public static int applyPlayerFlameAttack(List<GdxEnemyRuntime> animatedEnemies, int damage) {
        if (animatedEnemies == null || animatedEnemies.isEmpty() || damage <= 0) {
            return 0;
        }
        int remainingDamage = damage;
        int appliedDamage = 0;
        for (GdxEnemyRuntime enemy : animatedEnemies) {
            if (remainingDamage <= 0) {
                break;
            }
            if (enemy == null || !enemy.isAlive() || enemy.isInvulnerable()) {
                continue;
            }
            int hp = Math.max(0, enemy.currentHitPoints());
            if (hp <= 0) {
                continue;
            }
            int damageToApply = Math.min(hp, remainingDamage);
            if (damageToApply <= 0) {
                continue;
            }
            enemy.takeDamage(damageToApply);
            remainingDamage -= damageToApply;
            appliedDamage += damageToApply;
        }
        return appliedDamage;
    }

    /**
     * Backward-compatible overload without player damage (used by tests and legacy callers).
     */
    public static int applyDirectionalFlameExplosion(List<GdxEnemyRuntime> animatedEnemies,
                                                     MazeArena maze,
                                                     float originX,
                                                     float originY,
                                                     int damagePerDirection,
                                                     float maxRange) {
        return applyDirectionalFlameExplosion(animatedEnemies, maze, originX, originY,
                damagePerDirection, maxRange, Float.NaN, Float.NaN, null);
    }

    /**
     * Fires four cardinal flame corridors from {@code (originX, originY)}.
     *
     * <p>Each direction gets an independent {@code damagePerDirection} budget.  Targets
     * (enemies, breakable walls, and optionally the player) are processed in order of
     * distance along the ray.  The flame continues past a destroyed target and stops at
     * a surviving one.  The player is treated as a pass-through target: they receive the
     * remaining budget as damage at their position but do not block the flame.</p>
     *
     * <p>When a wall is destroyed the maze navigation graph is rewired automatically via
     * {@link GameMazeWorld#applyWallDamage}.</p>
     *
     * @param playerCenterX   player centre X in world pixels, or {@code Float.NaN} to skip
     * @param playerCenterY   player centre Y in world pixels, or {@code Float.NaN} to skip
     * @param playerCallback  receives the damage dealt to the player; may be {@code null}
     */
    public static int applyDirectionalFlameExplosion(List<GdxEnemyRuntime> animatedEnemies,
                                                     MazeArena maze,
                                                     float originX,
                                                     float originY,
                                                     int damagePerDirection,
                                                     float maxRange,
                                                     float playerCenterX,
                                                     float playerCenterY,
                                                     IntConsumer playerCallback) {
        if (damagePerDirection <= 0 || maxRange <= 0f) {
            return 0;
        }

        // GameMazeWorld wall vectors use Y-down (world/JavaFX) coordinates, but libGDX
        // player/enemy positions use Y-up (screen) coordinates.  Convert origin and player
        // centre to world space so DirectionalFlameEngine's wall-span checks are correct.
        float heightPx = maze.heightPx();
        float worldOriginY = heightPx - originY;
        float worldPlayerCY = Float.isNaN(playerCenterY) ? Float.NaN : heightPx - playerCenterY;

        GameMazeWorld world = GdxWallDamageSupport.worldFrom(maze);
        // Use the world's actual Vector2D references so findBreakableWall(ref) succeeds.
        List<Vector2D> wallVectors = world != null ? world.getMazeVectors() : List.of();

        int totalApplied = 0;
        // Horizontal rays: dirY=0, no Y-axis flip needed.
        totalApplied += applyDirectionalFlame(world, animatedEnemies, wallVectors, originX, worldOriginY,
                1, 0, damagePerDirection, maxRange, playerCenterX, worldPlayerCY, playerCallback, heightPx);
        totalApplied += applyDirectionalFlame(world, animatedEnemies, wallVectors, originX, worldOriginY,
                -1, 0, damagePerDirection, maxRange, playerCenterX, worldPlayerCY, playerCallback, heightPx);
        // Vertical rays: screen dirY and world dirY are opposite (Y-axis is flipped).
        // Screen (0,+1)="up in libGDX"  → world (0,-1)="decreasing world Y"
        // Screen (0,-1)="down in libGDX" → world (0,+1)="increasing world Y"
        totalApplied += applyDirectionalFlame(world, animatedEnemies, wallVectors, originX, worldOriginY,
                0, -1, damagePerDirection, maxRange, playerCenterX, worldPlayerCY, playerCallback, heightPx);
        totalApplied += applyDirectionalFlame(world, animatedEnemies, wallVectors, originX, worldOriginY,
                0, 1, damagePerDirection, maxRange, playerCenterX, worldPlayerCY, playerCallback, heightPx);
        return totalApplied;
    }

    /**
     * Applies one directional flame corridor.  Adapts {@link GdxEnemyRuntime} and
     * the optional player callback to {@link FlameTarget} and delegates to
     * {@link DirectionalFlameEngine} so both frontends share a single algorithm.
     */
    private static int applyDirectionalFlame(GameMazeWorld world,
                                             List<GdxEnemyRuntime> animatedEnemies,
                                             List<Vector2D> wallVectors,
                                             float originX,
                                             float worldOriginY,
                                             int dirX,
                                             int dirY,
                                             int damageBudget,
                                             float maxRange,
                                             float playerCenterX,
                                             float worldPlayerCY,
                                             IntConsumer playerCallback,
                                             float heightPx) {
        List<FlameTarget> candidates = new ArrayList<>();

        if (animatedEnemies != null) {
            for (GdxEnemyRuntime enemy : animatedEnemies) {
                if (enemy == null || !enemy.isAlive() || enemy.isInvulnerable()) {
                    continue;
                }
                final float cx = enemy.x() + enemy.size() * 0.5f;
                // Enemy positions are in libGDX screen space (Y-up); convert to world space (Y-down).
                final float cy = heightPx - (enemy.y() + enemy.size() * 0.5f);
                candidates.add(new FlameTarget() {
                    @Override public double centerX()        { return cx; }
                    @Override public double centerY()        { return cy; }
                    @Override public int    hitPoints()      { return Math.max(0, enemy.currentHitPoints()); }
                    @Override public void   applyDamage(int amount) { enemy.takeDamage(amount); }
                    @Override public boolean isPassThrough() { return false; }
                });
            }
        }

        if (playerCallback != null && !Float.isNaN(playerCenterX) && !Float.isNaN(worldPlayerCY)) {
            final float pcx = playerCenterX;
            final float pcy = worldPlayerCY;
            candidates.add(new FlameTarget() {
                @Override public double centerX()        { return pcx; }
                @Override public double centerY()        { return pcy; }
                @Override public int    hitPoints()      { return Integer.MAX_VALUE; }
                @Override public void   applyDamage(int amount) { playerCallback.accept(amount); }
                @Override public boolean isPassThrough() { return true; }
            });
        }

        return DirectionalFlameEngine.applyDirectionalFlame(
                candidates, world, wallVectors,
                originX, worldOriginY, dirX, dirY,
                damageBudget, maxRange, FLAME_CORRIDOR_HALF_WIDTH);
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
     * Removes enemies that were killed by flame damage ({@code takeDamage} reduced their
     * HP to 0) and creates death animations for each.  Unlike {@link #killEnemies}, this
     * processes enemies that are already dead — it skips living ones rather than killing
     * them.
     *
     * <p>Must be called after {@link #applyDirectionalFlameExplosion} so that the game
     * loop renders the death animations on the next frame.</p>
     *
     * @return the number of enemies removed
     */
    public static int processKilledByFlame(
            List<GdxEnemyRuntime> animatedEnemies,
            List<DeadEnemy> deadEnemies,
            List<EnemyDeathAnimation> dyingEnemies) {
        int killed = 0;
        List<GdxEnemyRuntime> toRemove = new ArrayList<>();
        for (GdxEnemyRuntime enemy : animatedEnemies) {
            if (enemy.isAlive()) {
                continue; // still alive — leave it in the list
            }
            dyingEnemies.add(new EnemyDeathAnimation(enemy.x(), enemy.y(), enemy.size()));
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
     * Kill every living enemy in {@code animatedEnemies}.
     * Enemies with {@code resurrectionTimeMs > 0} are moved to {@code deadEnemies}
     * so they reappear after the configured delay; the rest are simply removed.
     * A death animation entry is added to {@code dyingEnemies} for every killed enemy.
     *
     * @return the number of enemies killed
     */
    public static int killEnemies(
            List<GdxEnemyRuntime> animatedEnemies,
            List<DeadEnemy> deadEnemies,
            List<EnemyDeathAnimation> dyingEnemies) {
        int killed = 0;
        List<GdxEnemyRuntime> toRemove = new ArrayList<>();
        for (GdxEnemyRuntime enemy : animatedEnemies) {
            if (!enemy.isAlive()) {
                continue;
            }
            dyingEnemies.add(new EnemyDeathAnimation(enemy.x(), enemy.y(), enemy.size()));
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
     * Advance all active death animations and remove any that have finished.
     */
    public static void tickDeathAnimations(List<EnemyDeathAnimation> dyingEnemies, float dt) {
        dyingEnemies.removeIf(anim -> {
            anim.tick(dt);
            return anim.isDone();
        });
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
