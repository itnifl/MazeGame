package main.game.maze.libgdx.helper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
import main.game.maze.mazeworld.BreakableWall;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.WallCollisionUtil;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.generators.WallSegment;

/**
 * Coordinates enemy movement, combat frame updates, and win transition checks.
 */
public final class GdxGameCombatAndEnemyFlowSupport {

    private static final float EXPLOSION_SHAKE_DURATION_SECONDS = 0.20f;
    private static final float FLAME_SCAN_STEP = 6f;
    private static final float FLAME_CORRIDOR_HALF_WIDTH = 18f;

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

    public static int applyDirectionalFlameExplosion(List<GdxEnemyRuntime> animatedEnemies,
                                                     MazeArena maze,
                                                     float originX,
                                                     float originY,
                                                     int damagePerDirection,
                                                     float maxRange) {
        if (animatedEnemies == null || animatedEnemies.isEmpty() || damagePerDirection <= 0 || maxRange <= 0f) {
            return 0;
        }

        GameMazeWorld world = GdxWallDamageSupport.worldFrom(maze);
        List<WallSegment> wallSegments = maze == null ? List.of() : maze.walls();

        int totalApplied = 0;
        totalApplied += applyDirectionalFlame(world, animatedEnemies, wallSegments, originX, originY, 1, 0, damagePerDirection, maxRange);
        totalApplied += applyDirectionalFlame(world, animatedEnemies, wallSegments, originX, originY, -1, 0, damagePerDirection, maxRange);
        totalApplied += applyDirectionalFlame(world, animatedEnemies, wallSegments, originX, originY, 0, 1, damagePerDirection, maxRange);
        totalApplied += applyDirectionalFlame(world, animatedEnemies, wallSegments, originX, originY, 0, -1, damagePerDirection, maxRange);
        return totalApplied;
    }

    private static int applyDirectionalFlame(GameMazeWorld world,
                                             List<GdxEnemyRuntime> animatedEnemies,
                                             List<WallSegment> wallSegments,
                                             float originX,
                                             float originY,
                                             int dirX,
                                             int dirY,
                                             int damageBudget,
                                             float maxRange) {
        List<DirectionalEnemyTarget> targets = new ArrayList<>();
        for (GdxEnemyRuntime enemy : animatedEnemies) {
            if (enemy == null || !enemy.isAlive() || enemy.isInvulnerable()) {
                continue;
            }
            Float distance = projectedDistanceOnRay(originX, originY, enemy.x(), enemy.y(), dirX, dirY, maxRange);
            if (distance == null) {
                continue;
            }
            float centerX = enemy.x() + enemy.size() * 0.5f;
            float centerY = enemy.y() + enemy.size() * 0.5f;
            targets.add(new DirectionalEnemyTarget(enemy, distance, centerX, centerY));
        }

        targets.sort(Comparator.comparingDouble(DirectionalEnemyTarget::distance));

        int remaining = damageBudget;
        int applied = 0;
        int targetIndex = 0;
        float searchStart = 0f;

        while (remaining > 0 && searchStart <= maxRange) {
            WallHit nextWall = findNextWallHit(originX, originY, dirX, dirY, wallSegments, searchStart, maxRange);
            float wallDistance = nextWall == null ? Float.POSITIVE_INFINITY : nextWall.distance();

            while (targetIndex < targets.size()) {
                DirectionalEnemyTarget currentTarget = targets.get(targetIndex);
                if (wallSegments != null && !wallSegments.isEmpty()
                        && WallCollisionUtil.wallBetween(originX, originY, currentTarget.centerX(), currentTarget.centerY(), wallSegments)) {
                    if (nextWall != null && world != null) {
                        BreakableWall breakableWall = world.findBreakableWall(nextWall.wall());
                        if (breakableWall != null) {
                            int toApply = Math.min(remaining, Math.max(0, breakableWall.getRemainingHp()));
                            if (toApply > 0) {
                                boolean destroyed = world.applyWallDamage(breakableWall, toApply);
                                remaining -= toApply;
                                applied += toApply;
                                if (!destroyed) {
                                    return applied;
                                }
                            }
                        }
                    }
                    return applied;
                }
                if (currentTarget.distance() >= wallDistance) {
                    break;
                }
                int hp = Math.max(0, currentTarget.enemy().currentHitPoints());
                if (hp > 0) {
                    int toApply = Math.min(hp, remaining);
                    currentTarget.enemy().takeDamage(toApply);
                    remaining -= toApply;
                    applied += toApply;
                }
                targetIndex++;
                if (remaining <= 0) {
                    return applied;
                }
            }

            if (nextWall == null || world == null) {
                break;
            }

            BreakableWall breakableWall = world.findBreakableWall(nextWall.wall());
            if (breakableWall == null) {
                break;
            }

            int toApply = Math.min(remaining, Math.max(0, breakableWall.getRemainingHp()));
            if (toApply <= 0) {
                break;
            }
            boolean destroyed = world.applyWallDamage(breakableWall, toApply);
            remaining -= toApply;
            applied += toApply;
            if (!destroyed) {
                break;
            }
            searchStart = nextWall.distance();
        }

        while (remaining > 0 && targetIndex < targets.size()) {
            int hp = Math.max(0, targets.get(targetIndex).enemy().currentHitPoints());
            if (hp > 0) {
                int toApply = Math.min(hp, remaining);
                targets.get(targetIndex).enemy().takeDamage(toApply);
                remaining -= toApply;
                applied += toApply;
            }
            targetIndex++;
        }
        return applied;
    }

    private static Float projectedDistanceOnRay(float originX,
                                                float originY,
                                                float targetX,
                                                float targetY,
                                                int dirX,
                                                int dirY,
                                                float maxRange) {
        float corridorHalfWidth = FLAME_CORRIDOR_HALF_WIDTH;
        if (dirX != 0) {
            if (Math.abs(targetY - originY) > corridorHalfWidth) {
                return null;
            }
            float delta = targetX - originX;
            if (Math.signum(delta) != dirX) {
                return null;
            }
            float distance = Math.abs(delta);
            return distance <= maxRange ? distance : null;
        }
        if (Math.abs(targetX - originX) > corridorHalfWidth) {
            return null;
        }
        float delta = targetY - originY;
        if (Math.signum(delta) != dirY) {
            return null;
        }
        float distance = Math.abs(delta);
        return distance <= maxRange ? distance : null;
    }

    private static WallHit findNextWallHit(float originX,
                                           float originY,
                                           int dirX,
                                           int dirY,
                                           List<WallSegment> wallSegments,
                                           float searchStart,
                                           float maxRange) {
        if (wallSegments == null || wallSegments.isEmpty()) {
            return null;
        }

        WallHit nearest = null;
        for (WallSegment wall : wallSegments) {
            if (dirX != 0 && Math.abs(wall.x1 - wall.x2) < 0.001f) {
                float minY = Math.min(wall.y1, wall.y2);
                float maxY = Math.max(wall.y1, wall.y2);
                if (originY < minY || originY > maxY) {
                    continue;
                }
                float distance = wall.x1 - originX;
                if (Math.signum(distance) != dirX) {
                    continue;
                }
                float absDistance = Math.abs(distance);
                if (absDistance <= searchStart || absDistance > maxRange) {
                    continue;
                }
                if (nearest == null || absDistance < nearest.distance()) {
                    nearest = new WallHit(new Vector2D(wall.x1, wall.y1, wall.x2, wall.y2), absDistance);
                }
                continue;
            }

            if (dirY != 0 && Math.abs(wall.y1 - wall.y2) < 0.001f) {
                float minX = Math.min(wall.x1, wall.x2);
                float maxX = Math.max(wall.x1, wall.x2);
                if (originX < minX || originX > maxX) {
                    continue;
                }
                float distance = wall.y1 - originY;
                if (Math.signum(distance) != dirY) {
                    continue;
                }
                float absDistance = Math.abs(distance);
                if (absDistance <= searchStart || absDistance > maxRange) {
                    continue;
                }
                if (nearest == null || absDistance < nearest.distance()) {
                    nearest = new WallHit(new Vector2D(wall.x1, wall.y1, wall.x2, wall.y2), absDistance);
                }
            }
        }
        return nearest;
    }

    private record DirectionalEnemyTarget(GdxEnemyRuntime enemy, float distance, float centerX, float centerY) {
    }

    private record WallHit(Vector2D wall, float distance) {
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
