package main.game.maze.libgdx.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.SpriteAnimationUtil;
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
import main.game.maze.libgdx.model.EnemyAnimationSpec;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.mazeworld.WallCollisionUtil;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.ProjectileType;

public final class GdxEnemyRuntime implements EnemyRuntime {
    private static final String FALLBACK_THROW_SOUND = "/main/game/maze/menuselect.wav";
    private static final String FALLBACK_EXPLOSION_SOUND = "/main/game/maze/error.wav";
    private static final long RANGED_SOUND_COOLDOWN_MS = 120L;
    private static final float IMPACT_DURATION_SECONDS = 0.24f;

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
    private float shotCooldownRemaining;
    private int currentHitPoints;
    private float invulnerableSecondsRemaining;
    private static final float RESPAWN_INVULNERABILITY_SECONDS = 2f;
    private static final java.util.concurrent.atomic.AtomicInteger RUNTIME_SEQUENCE =
            new java.util.concurrent.atomic.AtomicInteger(0);
    private final List<ActiveProjectile> activeProjectiles = new ArrayList<>();
    private final List<BeamEffect> activeBeams = new ArrayList<>();
    private final List<ImpactEffect> activeImpacts = new ArrayList<>();

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
        this.shotCooldownRemaining = 0f;
        this.currentHitPoints = Math.max(1, spawn.maxHitPoints());
        this.invulnerableSecondsRemaining = 0f;
    }

    public static GdxEnemyRuntime fromSpawn(EnemySpawn spawn,
                                     int index,
                                     WorldView world,
                                     float javaFxTickRate,
                                     int maxEnemyTicksPerFrame) {
        // spawn.speed() already incorporates difficulty multiplier.
        float speed = Math.max(1f, spawn.speed());
        float phase = index * 0.8f;
        String runtimeId = (spawn.id() == null ? "enemy" : spawn.id()) + "#" + RUNTIME_SEQUENCE.getAndIncrement();
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

    /** The original immutable spawn data this enemy was created from (spawn coords, resurrection time, etc.). */
    public EnemySpawn originalSpawn() {
        return spawn;
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
                spawn.visibilityLevel(),
                spawn.projectileType(),
                spawn.splashRadius(),
                spawn.arcHeight(),
                spawn.attackRange(),
                spawn.attackCooldownMs(),
                spawn.projectileSpeed(),
                spawn.resurrectionTimeMs(),
                spawn.maxHitPoints());
    }

    /** Apply {@code amount} points of damage. Returns {@code true} if the enemy just died. */
    public boolean takeDamage(int amount) {
        if (currentHitPoints <= 0 || invulnerableSecondsRemaining > 0f) {
            return false;
        }
        currentHitPoints = Math.max(0, currentHitPoints - Math.max(0, amount));
        return currentHitPoints <= 0;
    }

    /** Kill the enemy instantly regardless of current HP. Returns {@code true} always. */
    public boolean kill() {
        currentHitPoints = 0;
        return true;
    }

    public boolean isAlive() {
        return currentHitPoints > 0;
    }

    public int currentHitPoints() {
        return currentHitPoints;
    }

    /** Tick the post-respawn invulnerability window down by {@code dt} seconds. */
    public void tickInvulnerability(float dt) {
        invulnerableSecondsRemaining = Math.max(0f, invulnerableSecondsRemaining - dt);
    }

    /** Grant a brief invulnerability window (used on respawn to prevent instant re-kill). */
    public void grantRespawnInvulnerability() {
        invulnerableSecondsRemaining = RESPAWN_INVULNERABILITY_SECONDS;
    }

    /** {@code true} while the post-respawn invulnerability window is active. */
    public boolean isInvulnerable() {
        return invulnerableSecondsRemaining > 0f;
    }

    public int updateRangedAttacks(float dt, MazeArena maze, float playerX, float playerY, float playerRadius) {
        return updateRangedAttacks(dt, maze, playerX, playerY, playerRadius, null);
    }

    public int updateRangedAttacks(float dt, MazeArena maze, float playerX, float playerY, float playerRadius,
                                   Consumer<float[]> onProjectileHitWall) {
        if (dt <= 0f) {
            return 0;
        }
        int dealtDamage = 0;
        shotCooldownRemaining = Math.max(0f, shotCooldownRemaining - dt);
        for (BeamEffect beam : activeBeams) {
            beam.remaining = Math.max(0f, beam.remaining - dt);
        }
        activeBeams.removeIf(beam -> beam.remaining <= 0f);
        for (ImpactEffect impact : activeImpacts) {
            impact.remaining = Math.max(0f, impact.remaining - dt);
        }
        activeImpacts.removeIf(impact -> impact.remaining <= 0f);

        for (int i = activeProjectiles.size() - 1; i >= 0; i--) {
            ActiveProjectile projectile = activeProjectiles.get(i);
            float previousX = projectile.x;
            float previousY = projectile.y;
            projectile.tick(dt);

            if (projectile.type == ProjectileType.STRAIGHT
                    && maze != null
                    && WallCollisionUtil.wallBetween(previousX, previousY, projectile.x, projectile.y, maze.walls())) {
                if (onProjectileHitWall != null) {
                    onProjectileHitWall.accept(new float[]{projectile.x, projectile.y});
                }
                emitImpact(projectile.x, projectile.y, 16f, 4f);
                playExplosionSound();
                activeProjectiles.remove(i);
                continue;
            }

            boolean hitPlayer = distanceSquared(projectile.x, projectile.y, playerX, playerY)
                    <= squared(playerRadius + projectile.radius());
            if (projectile.type == ProjectileType.STRAIGHT && hitPlayer) {
                dealtDamage += Math.max(0, spawn.attackDamage());
                emitImpact(projectile.x, projectile.y, 18f, 5f);
                playExplosionSound();
                activeProjectiles.remove(i);
                continue;
            }

            boolean outOfBounds = maze != null
                    && (projectile.boundsX() < 0f || projectile.boundsY() < 0f
                    || projectile.boundsX() > maze.widthPx() || projectile.boundsY() > maze.heightPx());

            if ((projectile.type == ProjectileType.LOB && projectile.arrived()) || outOfBounds || projectile.lifeSeconds > 5f) {
                if (projectile.type == ProjectileType.LOB
                        && distanceSquared(playerX, playerY, projectile.targetX, projectile.targetY)
                        <= squared(Math.max(0f, spawn.splashRadius()))) {
                    dealtDamage += Math.max(0, spawn.attackDamage());
                }
                if (projectile.type == ProjectileType.LOB || projectile.type == ProjectileType.STRAIGHT) {
                    float impactRadius = projectile.type == ProjectileType.LOB
                            ? Math.max(24f, Math.max(0f, spawn.splashRadius()))
                            : 18f;
                    float shakeMagnitude = projectile.type == ProjectileType.LOB ? 8f : 5f;
                    emitImpact(projectile.boundsX(), projectile.boundsY(), impactRadius, shakeMagnitude);
                    playExplosionSound();
                }
                activeProjectiles.remove(i);
                continue;
            }
        }

        if (spawn.attackRange() <= 0f || spawn.projectileSpeed() <= 0f || spawn.attackCooldownMs() <= 0) {
            return dealtDamage;
        }

        if (shotCooldownRemaining > 0f) {
            return dealtDamage;
        }

        float dx = playerX - x;
        float dy = playerY - y;
        float range = Math.max(0f, spawn.attackRange());
        if (distanceSquared(x, y, playerX, playerY) > squared(range)) {
            return dealtDamage;
        }

        ProjectileType projectileType = spawn.projectileType() == null
                ? ProjectileType.STRAIGHT
                : spawn.projectileType();

        if (projectileType == ProjectileType.BEAM) {
            boolean blocked = maze != null && WallCollisionUtil.wallBetween(x, y, playerX, playerY, maze.walls());
            if (!blocked) {
                dealtDamage += Math.max(0, spawn.attackDamage());
            }
            playThrowSound();
            activeBeams.add(new BeamEffect(x, y, playerX, playerY, blocked, 0.14f));
            shotCooldownRemaining = spawn.attackCooldownMs() / 1000f;
            return dealtDamage;
        }

        float speedValue = Math.max(1f, spawn.projectileSpeed());
        float distance = (float) Math.sqrt(distanceSquared(x, y, playerX, playerY));
        if (distance < 0.001f) {
            return dealtDamage;
        }
        if (activeProjectiles.size() >= 8) {
            return dealtDamage;
        }
        float duration = Math.max(0.15f, distance / speedValue);
        activeProjectiles.add(new ActiveProjectile(
                projectileType,
                x,
                y,
                playerX,
                playerY,
                duration,
                Math.max(0f, spawn.arcHeight())));
        playThrowSound();
        shotCooldownRemaining = spawn.attackCooldownMs() / 1000f;
        return dealtDamage;
    }

    private void playThrowSound() {
        String path = spawn.touchSoundPath();
        if (path == null || path.isBlank()) {
            path = FALLBACK_THROW_SOUND;
        }
        AudioEngine.get().playRateLimited(path, runtimeEnemyId + ":throw", RANGED_SOUND_COOLDOWN_MS);
    }

    private void playExplosionSound() {
        AudioEngine.get().playRateLimited(
                FALLBACK_EXPLOSION_SOUND,
                runtimeEnemyId + ":explode",
                RANGED_SOUND_COOLDOWN_MS);
    }

    private void emitImpact(float impactX, float impactY, float maxRadius, float shakeMagnitude) {
        activeImpacts.add(new ImpactEffect(
                impactX,
                impactY,
                Math.max(8f, maxRadius),
                Math.max(0f, shakeMagnitude),
                IMPACT_DURATION_SECONDS));
    }

    public List<ProjectileVisual> projectileVisuals() {
        if (activeProjectiles.isEmpty()) {
            return List.of();
        }
        List<ProjectileVisual> visuals = new ArrayList<>(activeProjectiles.size());
        for (ActiveProjectile projectile : activeProjectiles) {
            float shadow = projectile.type == ProjectileType.LOB
                    ? Math.max(2f, 6f * (float) Math.sin(Math.PI * projectile.progress))
                    : 0f;
            visuals.add(new ProjectileVisual(
                    projectile.x,
                    projectile.y,
                    projectile.radius(),
                    shadow,
                    projectile.type == ProjectileType.LOB));
        }
        return Collections.unmodifiableList(visuals);
    }

    public List<BeamVisual> beamVisuals() {
        if (activeBeams.isEmpty()) {
            return List.of();
        }
        List<BeamVisual> visuals = new ArrayList<>(activeBeams.size());
        for (BeamEffect beam : activeBeams) {
            float alpha = beam.maxDuration <= 0f ? 0f : Math.max(0f, beam.remaining / beam.maxDuration);
            visuals.add(new BeamVisual(beam.x1, beam.y1, beam.x2, beam.y2, alpha, beam.blocked));
        }
        return Collections.unmodifiableList(visuals);
    }

    public List<ImpactVisual> impactVisuals() {
        if (activeImpacts.isEmpty()) {
            return List.of();
        }
        List<ImpactVisual> visuals = new ArrayList<>(activeImpacts.size());
        for (ImpactEffect impact : activeImpacts) {
            float progress = impact.maxDuration <= 0f ? 1f : 1f - Math.max(0f, impact.remaining / impact.maxDuration);
            float alpha = Math.max(0f, 1f - progress);
            float radius = Math.max(2f, impact.maxRadius * (0.35f + 0.65f * progress));
            visuals.add(new ImpactVisual(impact.x, impact.y, radius, alpha, impact.shakeMagnitude));
        }
        return Collections.unmodifiableList(visuals);
    }

    public String imagePath() {
        return imagePath;
    }

    /**
     * Returns the image path to render for this frame of the animation clock.
     * Picks the directional frame-1 image based on the current movement direction,
     * then replaces the first digit in the filename with the 1-indexed frame number
     * derived from {@code animationClock} at {@value #ANIMATION_FPS} fps.
     * Falls back to {@link #imagePath()} when no animation spec is set.
     */
    public String currentFramePath(float animationClock) {
        EnemyAnimationSpec spec = spawn != null ? spawn.animationSpec() : null;
        int frameCount = spec != null ? spec.animationFrameCount() : 1;
        String dirPath = directionalImagePath(spec);
        if (frameCount <= 1) {
            return dirPath;
        }
        int frameIndex = ((int) (animationClock * ANIMATION_FPS)) % frameCount;
        return deriveAnimationFramePath(dirPath, frameIndex);
    }

    /** Returns the sprite-scale multiplier from the animation spec (1.0 if none). */
    public float spriteScale() {
        EnemyAnimationSpec spec = spawn != null ? spawn.animationSpec() : null;
        return spec != null ? spec.spriteScale() : 1.0f;
    }

    /**
     * Derives the animation frame path for {@code frameIndex} from the frame-1 path.
     * Delegates to {@link SpriteAnimationUtil#deriveAnimationFramePath}.
     */
    static String deriveAnimationFramePath(String frame1Path, int frameIndex) {
        return SpriteAnimationUtil.deriveAnimationFramePath(frame1Path, frameIndex);
    }

    private static final float ANIMATION_FPS = 4.0f;

    private String directionalImagePath(EnemyAnimationSpec spec) {
        if (spec == null) {
            return imagePath;
        }
        if (directionX > 0) {
            return spec.imageTurnRight() != null ? spec.imageTurnRight() : imagePath;
        }
        if (directionX < 0) {
            return spec.imageTurnLeft() != null ? spec.imageTurnLeft() : imagePath;
        }
        if (directionY > 0) {
            return spec.imageTurnUp() != null ? spec.imageTurnUp() : imagePath;
        }
        if (directionY < 0) {
            return spec.imageTurnDown() != null ? spec.imageTurnDown() : imagePath;
        }
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

    /** Attack damage this enemy deals per hit; used by wall-damage callbacks. */
    public int attackDamage() {
        return spawn != null ? spawn.attackDamage() : 0;
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

    private static float distanceSquared(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        return dx * dx + dy * dy;
    }

    private static float squared(float value) {
        return value * value;
    }

    public record ProjectileVisual(float x, float y, float radius, float shadowRadius, boolean lob) {
    }

    public record BeamVisual(float x1, float y1, float x2, float y2, float alpha, boolean blocked) {
    }

    public record ImpactVisual(float x, float y, float radius, float alpha, float shakeMagnitude) {
    }

    private static final class ActiveProjectile {
        private final ProjectileType type;
        private final float sx;
        private final float sy;
        private final float targetX;
        private final float targetY;
        private final float duration;
        private final float arcHeight;
        private float progress;
        private float lifeSeconds;
        private float x;
        private float y;
        private float boundsX;
        private float boundsY;

        private ActiveProjectile(
                ProjectileType type,
                float sx,
                float sy,
                float targetX,
                float targetY,
                float duration,
                float arcHeight) {
            this.type = type;
            this.sx = sx;
            this.sy = sy;
            this.targetX = targetX;
            this.targetY = targetY;
            this.duration = duration;
            this.arcHeight = arcHeight;
            this.progress = 0f;
            this.lifeSeconds = 0f;
            this.x = sx;
            this.y = sy;
            this.boundsX = sx;
            this.boundsY = sy;
        }

        private void tick(float dt) {
            lifeSeconds += dt;
            progress = Math.min(1f, progress + dt / duration);
            float lerpX = sx + (targetX - sx) * progress;
            float lerpY = sy + (targetY - sy) * progress;
            boundsX = lerpX;
            boundsY = lerpY;
            if (type == ProjectileType.LOB) {
                float arcOffset = (float) (arcHeight * Math.sin(Math.PI * progress));
                x = lerpX;
                y = lerpY + arcOffset;
            } else {
                x = lerpX;
                y = lerpY;
            }
        }

        private float boundsX() {
            return boundsX;
        }

        private float boundsY() {
            return boundsY;
        }

        private boolean arrived() {
            return progress >= 1f;
        }

        private float radius() {
            return type == ProjectileType.LOB ? 6f : 5f;
        }
    }

    private static final class BeamEffect {
        private final float x1;
        private final float y1;
        private final float x2;
        private final float y2;
        private final boolean blocked;
        private final float maxDuration;
        private float remaining;

        private BeamEffect(float x1, float y1, float x2, float y2, boolean blocked, float duration) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.blocked = blocked;
            this.maxDuration = duration;
            this.remaining = duration;
        }
    }

    private static final class ImpactEffect {
        private final float x;
        private final float y;
        private final float maxRadius;
        private final float shakeMagnitude;
        private final float maxDuration;
        private float remaining;

        private ImpactEffect(float x, float y, float maxRadius, float shakeMagnitude, float duration) {
            this.x = x;
            this.y = y;
            this.maxRadius = maxRadius;
            this.shakeMagnitude = shakeMagnitude;
            this.maxDuration = duration;
            this.remaining = duration;
        }
    }
}
