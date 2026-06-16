package main.game.maze.libgdx.game;

import java.util.List;
import main.game.maze.common.movement.ActivePathPoint;
import main.game.maze.common.movement.AdaptiveAggressiveMovementService;
import main.game.maze.common.movement.AntiLoopWanderMovementService;
import main.game.maze.common.movement.EnemySpawnUnstuckService;
import main.game.maze.common.movement.EnemyState;
import main.game.maze.common.movement.GhostNonTangibilityService;
import main.game.maze.common.movement.GhostPhasingMovementService;
import main.game.maze.common.movement.MovementResult;
import main.game.maze.common.movement.PatrolMovementService;
import main.game.maze.common.movement.WorldView;
import main.game.maze.game.runtime.EnemyRuntime;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.opponents.BehaviorType;

public final class GdxEnemyRuntime implements EnemyRuntime {
    private final EnemySpawn spawn;
    private final String runtimeEnemyId;
    private final String imagePath;
    private final float size;
    private final float speed;
    private final boolean infectious;
    private final float infectionStrength;
    // Cosmetic-only seed for the infectious mist pulse so neighbouring
    // enemies do not shimmer in perfect lockstep. Not used by movement.
    private final float phase;
    private float x;
    private float y;
    private int directionX;
    private int directionY;
    private float moveAccumulator;
    private String behaviorTypeLabel;
    private String movementTypeLabel;
    // Mirrors JavaFX ghost non-tangibility energy from the opponent model.
    double nonTangibilityEnergy;
    private final int visibilityLevel;
    private final float javaFxTickRate;
    private final int maxEnemyTicksPerFrame;

    private GdxEnemyRuntime(EnemySpawn spawn,
                            String runtimeEnemyId,
                            float javaFxTickRate,
                            int maxEnemyTicksPerFrame,
                            String imagePath,
                            float size,
                            float baseX,
                            float baseY,
                            float speed,
                            float phase) {
        this.spawn = spawn;
        this.runtimeEnemyId = runtimeEnemyId;
        this.javaFxTickRate = javaFxTickRate;
        this.maxEnemyTicksPerFrame = maxEnemyTicksPerFrame;
        this.imagePath = imagePath;
        this.size = size;
        this.speed = speed;
        this.infectious = spawn != null && spawn.infectionLevel() > 0;
        this.infectionStrength = infectious ? Math.min(1f, Math.max(0.35f, spawn.infectionLevel() / 100f)) : 0f;
        this.phase = phase;
        this.x = baseX;
        this.y = baseY;
        int[] initialDirection = seededCardinal(spawn.id(), 0L);
        this.directionX = initialDirection[0];
        this.directionY = initialDirection[1];
        this.moveAccumulator = 0f;
        BehaviorType behavior = spawn.behavior() == null ? BehaviorType.WANDER : spawn.behavior();
        this.behaviorTypeLabel = behavior.name();
        this.movementTypeLabel = "WANDER";
        this.nonTangibilityEnergy = spawn.nonTangibilityEnergy();
        this.visibilityLevel = spawn.visibilityLevel();
    }

    public static GdxEnemyRuntime fromSpawn(EnemySpawn spawn,
                                     int index,
                                     WorldView world,
                                     float javaFxTickRate,
                                     int maxEnemyTicksPerFrame) {
        // spawn.speed() already incorporates difficulty multiplier.
        float speed = Math.max(1f, spawn.speed());
        float phase = index * 0.8f;
        String runtimeId = (spawn.id() == null ? "enemy" : spawn.id()) + "#" + index;
        var resolution = EnemySpawnUnstuckService.nudgeIfColliding(world, spawn.x(), spawn.y(), spawn.size());
        return new GdxEnemyRuntime(
                spawn,
                runtimeId,
                javaFxTickRate,
                maxEnemyTicksPerFrame,
                spawn.imagePath(),
                spawn.size(),
                (float) resolution.x(),
                (float) resolution.y(),
                speed,
                phase);
    }

    public EnemySpawn contactSnapshot() {
        return new EnemySpawn(
                spawn.id(),
                spawn.imagePath(),
                x,
                y,
                spawn.size(),
                spawn.effectiveThreat(),
                spawn.attackDamage(),
                spawn.infectionLevel(),
                spawn.touchSoundPath(),
                spawn.behavior(),
                spawn.speed(),
                nonTangibilityEnergy,
                spawn.visibilityLevel());
    }

    public String imagePath() {
        return imagePath;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public float size() {
        return size;
    }

    public boolean infectious() {
        return infectious;
    }

    public float infectionStrength() {
        return infectionStrength;
    }

    public float phase() {
        return phase;
    }

    /**
     * Advance this enemy one frame through the shared movement service.
     */
    public void advance(WorldView world,
                 AntiLoopWanderMovementService wanderService,
                 PatrolMovementService patrolService,
                 AdaptiveAggressiveMovementService adaptiveService,
                 GhostPhasingMovementService phasingService,
                 float dt) {
        if (nonTangibilityEnergy > 0) {
            nonTangibilityEnergy = GhostNonTangibilityService.drainEnergy(nonTangibilityEnergy, dt);
        }

        moveAccumulator += dt * javaFxTickRate;
        int ticks = (int) moveAccumulator;
        if (ticks <= 0) {
            return;
        }
        moveAccumulator -= ticks;
        int budget = Math.min(ticks, maxEnemyTicksPerFrame);
        for (int i = 0; i < budget; i++) {
            if (nonTangibilityEnergy > 0) {
                EnemyState state = new EnemyState(runtimeEnemyId, x, y, directionX, directionY, size, speed);
                MovementResult next = phasingService.tick(state, world);
                x = (float) next.x();
                y = (float) next.y();
                directionX = next.directionX();
                directionY = next.directionY();
            } else {
                MovementResult next = nextMove(world, wanderService, patrolService, adaptiveService);
                x = (float) next.x();
                y = (float) next.y();
                directionX = next.directionX();
                directionY = next.directionY();
            }
        }
    }

    public float renderOpacity() {
        return (float) GhostNonTangibilityService.calculateOpacity(nonTangibilityEnergy, visibilityLevel);
    }

    private MovementResult nextMove(WorldView world,
                                    AntiLoopWanderMovementService wanderService,
                                    PatrolMovementService patrolService,
                                    AdaptiveAggressiveMovementService adaptiveService) {
        BehaviorType behavior = spawn.behavior() == null ? BehaviorType.WANDER : spawn.behavior();
        if (behavior == BehaviorType.PASSIVE) {
            behavior = BehaviorType.WANDER;
        }
        behaviorTypeLabel = behavior.name();
        EnemyState state = new EnemyState(runtimeEnemyId, x, y, directionX, directionY, size, speed);
        if (behavior == BehaviorType.AGGRESSIVE) {
            MovementResult result = adaptiveService.tick(state, world, 1.0d / javaFxTickRate);
            var mode = adaptiveService.modeForEnemy(runtimeEnemyId);
            if (mode == AdaptiveAggressiveMovementService.AggressiveMovementMode.PATH_FOLLOW) {
                movementTypeLabel = "AGGRESSIVE_PATH";
            } else if (mode == AdaptiveAggressiveMovementService.AggressiveMovementMode.WANDER_RECOVERY) {
                movementTypeLabel = "AGGRESSIVE_WANDER";
            } else {
                movementTypeLabel = "AGGRESSIVE_CHASE";
            }
            return result;
        }
        if (behavior == BehaviorType.PATROL) {
            MovementResult result = patrolService.tick(state, world, 1.0d / javaFxTickRate);
            movementTypeLabel = patrolService.modeForEnemy(runtimeEnemyId)
                    == PatrolMovementService.PatrolMovementMode.WANDER_RECOVERY
                    ? "PATROL_WANDER"
                    : "PATROL_PATH";
            return result;
        }
        movementTypeLabel = "WANDER";
        return wanderService.tick(state, world);
    }

    public String debugLabel(boolean showBehaviorType, boolean showMovementType) {
        if (showBehaviorType && showMovementType) {
            return behaviorTypeLabel + " | " + movementTypeLabel;
        }
        if (showBehaviorType) {
            return behaviorTypeLabel;
        }
        if (showMovementType) {
            return movementTypeLabel;
        }
        return null;
    }

    public List<ActivePathPoint> activePathPoints(PatrolMovementService patrolService,
                                           AdaptiveAggressiveMovementService adaptiveService) {
        BehaviorType behavior = spawn.behavior() == null ? BehaviorType.WANDER : spawn.behavior();
        if (behavior == BehaviorType.AGGRESSIVE) {
            return adaptiveService.currentPathForEnemy(runtimeEnemyId, x, y);
        }
        if (behavior == BehaviorType.PATROL) {
            return patrolService.currentPathForEnemy(runtimeEnemyId, x, y);
        }
        return List.of();
    }

    private static int[] seededCardinal(String id, long tick) {
        int seed = (id == null ? 0 : id.hashCode()) ^ (int) (tick * 31L + 17L);
        int idx = Math.floorMod(seed, 4);
        return switch (idx) {
            case 0 -> new int[] {1, 0};
            case 1 -> new int[] {0, 1};
            case 2 -> new int[] {0, -1};
            default -> new int[] {-1, 0};
        };
    }
}
