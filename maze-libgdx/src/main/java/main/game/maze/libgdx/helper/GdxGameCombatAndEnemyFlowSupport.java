package main.game.maze.libgdx.helper;

import java.util.ArrayList;
import java.util.Comparator;
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
import main.game.maze.mazeworld.BreakableWall;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.WallCollisionUtil;
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
    /** Full corridor width exposed for rendering — matches one maze hallway. */
    public static final float FLAME_CORRIDOR_WIDTH = StageConstants.HallwayWidthPx;

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

        GameMazeWorld world = GdxWallDamageSupport.worldFrom(maze);
        // Use the world's actual Vector2D references so findBreakableWall(ref) succeeds.
        List<Vector2D> wallVectors = world != null ? world.getMazeVectors() : List.of();

        int totalApplied = 0;
        totalApplied += applyDirectionalFlame(world, animatedEnemies, wallVectors, originX, originY,
                1, 0, damagePerDirection, maxRange, playerCenterX, playerCenterY, playerCallback);
        totalApplied += applyDirectionalFlame(world, animatedEnemies, wallVectors, originX, originY,
                -1, 0, damagePerDirection, maxRange, playerCenterX, playerCenterY, playerCallback);
        totalApplied += applyDirectionalFlame(world, animatedEnemies, wallVectors, originX, originY,
                0, 1, damagePerDirection, maxRange, playerCenterX, playerCenterY, playerCallback);
        totalApplied += applyDirectionalFlame(world, animatedEnemies, wallVectors, originX, originY,
                0, -1, damagePerDirection, maxRange, playerCenterX, playerCenterY, playerCallback);
        return totalApplied;
    }

    /**
     * Applies one directional flame corridor.  Processes all targets (enemies + optional
     * player + walls) in ascending distance order from the origin.
     *
     * <p>Rules:</p>
     * <ul>
     *   <li>Enemies and walls consume the budget and stop the flame when they survive.</li>
     *   <li>The player is a pass-through target: they receive the remaining budget at their
     *       position but neither consume it nor stop the flame.</li>
     *   <li>An indestructible wall always stops the flame.</li>
     *   <li>A breakable wall that is destroyed allows the flame to continue.</li>
     * </ul>
     */
    private static int applyDirectionalFlame(GameMazeWorld world,
                                             List<GdxEnemyRuntime> animatedEnemies,
                                             List<Vector2D> wallVectors,
                                             float originX,
                                             float originY,
                                             int dirX,
                                             int dirY,
                                             int damageBudget,
                                             float maxRange,
                                             float playerCenterX,
                                             float playerCenterY,
                                             IntConsumer playerCallback) {
        // --- Build sorted target list (enemies, plus optional player as pass-through) ---
        List<DirectionalTarget> targets = new ArrayList<>();

        if (animatedEnemies != null) {
            for (GdxEnemyRuntime enemy : animatedEnemies) {
                if (enemy == null || !enemy.isAlive() || enemy.isInvulnerable()) {
                    continue;
                }
                float cx = enemy.x() + enemy.size() * 0.5f;
                float cy = enemy.y() + enemy.size() * 0.5f;
                Float dist = projectedDistanceOnRay(originX, originY, cx, cy, dirX, dirY, maxRange);
                if (dist == null) {
                    continue;
                }
                targets.add(new DirectionalTarget(enemy, null, dist, cx, cy, false));
            }
        }

        if (playerCallback != null && !Float.isNaN(playerCenterX) && !Float.isNaN(playerCenterY)) {
            Float pd = projectedDistanceOnRay(originX, originY, playerCenterX, playerCenterY, dirX, dirY, maxRange);
            if (pd != null) {
                targets.add(new DirectionalTarget(null, playerCallback, pd, playerCenterX, playerCenterY, true));
            }
        }

        targets.sort(Comparator.comparingDouble(DirectionalTarget::distance));

        // --- Walk targets and walls in ascending distance, spending budget ---
        int remaining = damageBudget;
        int applied = 0;
        int targetIndex = 0;
        float searchStart = 0f;

        while (remaining > 0 && searchStart <= maxRange) {
            WallHit nextWall = findNextWallHit(originX, originY, dirX, dirY, wallVectors, searchStart, maxRange);
            float wallDistance = nextWall == null ? Float.POSITIVE_INFINITY : nextWall.distance();

            // Process all targets that sit before the next wall
            while (targetIndex < targets.size()) {
                DirectionalTarget current = targets.get(targetIndex);

                // If a wall is between origin and this target, handle the wall first
                if (!wallVectors.isEmpty()
                        && WallCollisionUtil.wallBetweenVectors(
                                originX, originY, current.centerX(), current.centerY(), wallVectors)) {
                    if (nextWall != null && world != null) {
                        BreakableWall bw = world.findBreakableWall(nextWall.wall());
                        if (bw != null) {
                            int toApply = Math.min(remaining, Math.max(0, bw.getRemainingHp()));
                            if (toApply > 0) {
                                boolean destroyed = world.applyWallDamage(bw, toApply);
                                remaining -= toApply;
                                applied += toApply;
                                if (!destroyed) {
                                    return applied; // wall survived → flame stops
                                }
                                // wall destroyed → continue outer loop with updated searchStart
                                searchStart = nextWall.distance();
                                break; // re-evaluate wall list from new searchStart
                            }
                        }
                    }
                    return applied; // indestructible wall stops the flame
                }

                if (current.distance() >= wallDistance) {
                    break; // next wall is closer; handle it in the outer-loop block below
                }

                if (current.isPlayer()) {
                    // Pass-through: player takes damage from remaining budget but doesn't block
                    if (current.playerCallback() != null && remaining > 0) {
                        current.playerCallback().accept(remaining);
                    }
                    targetIndex++;
                    continue;
                }

                // Enemy target
                GdxEnemyRuntime enemy = current.enemy();
                int hp = Math.max(0, enemy.currentHitPoints());
                if (hp > 0) {
                    int toApply = Math.min(hp, remaining);
                    enemy.takeDamage(toApply);
                    remaining -= toApply;
                    applied += toApply;
                    if (hp > toApply) {
                        // Enemy survived — flame stops here
                        return applied;
                    }
                }
                targetIndex++;
                if (remaining <= 0) {
                    return applied;
                }
            }

            // Handle the next wall if all pre-wall targets are processed
            if (nextWall == null || world == null) {
                break;
            }

            BreakableWall bw = world.findBreakableWall(nextWall.wall());
            if (bw == null) {
                break; // indestructible — stops flame
            }

            int toApply = Math.min(remaining, Math.max(0, bw.getRemainingHp()));
            if (toApply <= 0) {
                break;
            }
            boolean destroyed = world.applyWallDamage(bw, toApply);
            remaining -= toApply;
            applied += toApply;
            if (!destroyed) {
                break; // wall survived — stops flame
            }
            searchStart = nextWall.distance(); // wall destroyed — advance past it
        }

        // Budget remains after all walls — apply to any leftover targets beyond the last wall
        while (remaining > 0 && targetIndex < targets.size()) {
            DirectionalTarget current = targets.get(targetIndex);
            if (current.isPlayer()) {
                if (current.playerCallback() != null) {
                    current.playerCallback().accept(remaining);
                }
                targetIndex++;
                continue;
            }
            GdxEnemyRuntime enemy = current.enemy();
            int hp = Math.max(0, enemy.currentHitPoints());
            if (hp > 0) {
                int toApply = Math.min(hp, remaining);
                enemy.takeDamage(toApply);
                remaining -= toApply;
                applied += toApply;
                if (hp > toApply) {
                    return applied;
                }
            }
            targetIndex++;
        }
        return applied;
    }

    /** Returns the signed ray-axis distance if {@code (targetX, targetY)} lies within the
     *  flame corridor in direction {@code (dirX, dirY)}, or {@code null} otherwise. */
    private static Float projectedDistanceOnRay(float originX,
                                                float originY,
                                                float targetX,
                                                float targetY,
                                                int dirX,
                                                int dirY,
                                                float maxRange) {
        float hw = FLAME_CORRIDOR_HALF_WIDTH;
        if (dirX != 0) {
            if (Math.abs(targetY - originY) > hw) {
                return null;
            }
            float delta = targetX - originX;
            if (Math.signum(delta) != dirX) {
                return null;
            }
            float dist = Math.abs(delta);
            return dist <= maxRange ? dist : null;
        }
        if (Math.abs(targetX - originX) > hw) {
            return null;
        }
        float delta = targetY - originY;
        if (Math.signum(delta) != dirY) {
            return null;
        }
        float dist = Math.abs(delta);
        return dist <= maxRange ? dist : null;
    }

    /**
     * Finds the nearest wall segment that the flame ray will cross in direction
     * {@code (dirX, dirY)}, beyond {@code searchStart} and within {@code maxRange}.
     * Uses the actual {@link Vector2D} references from the world so that
     * {@link GameMazeWorld#findBreakableWall} can match by identity.
     */
    private static WallHit findNextWallHit(float originX,
                                           float originY,
                                           int dirX,
                                           int dirY,
                                           List<Vector2D> wallVectors,
                                           float searchStart,
                                           float maxRange) {
        if (wallVectors == null || wallVectors.isEmpty()) {
            return null;
        }
        WallHit nearest = null;
        for (Vector2D wall : wallVectors) {
            double x1 = wall.getStart().getX();
            double y1 = wall.getStart().getY();
            double x2 = wall.getEnd().getX();
            double y2 = wall.getEnd().getY();

            if (dirX != 0 && Math.abs(x1 - x2) < 0.001) {
                double minY = Math.min(y1, y2);
                double maxY = Math.max(y1, y2);
                if (originY < minY || originY > maxY) {
                    continue;
                }
                float dist = (float) (x1 - originX);
                if (Math.signum(dist) != dirX) {
                    continue;
                }
                float abs = Math.abs(dist);
                if (abs <= searchStart || abs > maxRange) {
                    continue;
                }
                if (nearest == null || abs < nearest.distance()) {
                    nearest = new WallHit(wall, abs);
                }
                continue;
            }

            if (dirY != 0 && Math.abs(y1 - y2) < 0.001) {
                double minX = Math.min(x1, x2);
                double maxX = Math.max(x1, x2);
                if (originX < minX || originX > maxX) {
                    continue;
                }
                float dist = (float) (y1 - originY);
                if (Math.signum(dist) != dirY) {
                    continue;
                }
                float abs = Math.abs(dist);
                if (abs <= searchStart || abs > maxRange) {
                    continue;
                }
                if (nearest == null || abs < nearest.distance()) {
                    nearest = new WallHit(wall, abs);
                }
            }
        }
        return nearest;
    }

    private record DirectionalTarget(GdxEnemyRuntime enemy,
                                     IntConsumer playerCallback,
                                     float distance,
                                     float centerX,
                                     float centerY,
                                     boolean isPlayer) {
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
