package main.game.maze.libgdx.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.DamageState;
import main.game.maze.common.graphics.DamagePathResolver;
import main.game.maze.common.graphics.SpriteAnimationUtil;
import main.game.maze.common.graphics.fire.FireBlast;
import main.game.maze.common.graphics.fire.HitReactionEffect;
import main.game.maze.common.graphics.fire.PumpkinBurst;
import main.game.maze.common.movement.ActivePathPoint;
import main.game.maze.common.movement.AdaptiveAggressiveMovementService;
import main.game.maze.common.movement.AntiLoopWanderMovementService;
import main.game.maze.common.movement.EnemySpawnUnstuckService;
import main.game.maze.common.movement.EnemyState;
import main.game.maze.common.movement.GhostNonTangibilityService;
import main.game.maze.common.movement.GhostPhasingMovementService;
import main.game.maze.common.movement.MovementResult;
import main.game.maze.common.movement.MovementTickAccumulator;
import main.game.maze.common.movement.PatrolMovementService;
import main.game.maze.common.movement.WorldView;
import main.game.maze.common.remains.DeathCause;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.interfaces.IDeathSubscriber;
import main.game.maze.service.EnemyVitals;
import main.game.maze.service.MortalityRules;
import main.game.maze.game.runtime.EnemyRuntime;
import main.game.maze.libgdx.model.EnemyAnimationSpec;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.libgdx.model.PumpkinBurstVisual;
import main.game.maze.mazeworld.WallCollisionUtil;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.ProjectileType;

public final class GdxEnemyRuntime implements EnemyRuntime, ICanDie {
    private static final Logger LOG = Logger.getLogger(GdxEnemyRuntime.class.getName());
    private static final String FALLBACK_THROW_SOUND = "/main/game/maze/menuselect.wav";
    private static final String FALLBACK_EXPLOSION_SOUND = "/main/game/maze/error.wav";
    private static final long RANGED_SOUND_COOLDOWN_MS = 120L;
    /** Impacts shake the screen only right after they land, however long the burst stays visible. */
    private static final float IMPACT_SHAKE_SECONDS = 0.24f;

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
    private final MovementTickAccumulator moveAccumulator;
    private String behaviorTypeLabel;
    private String movementTypeLabel;
    // Mirrors JavaFX ghost non-tangibility energy from the opponent model.
    double nonTangibilityEnergy;
    private final int visibilityLevel;
    private final float javaFxTickRate;
    private float shotCooldownRemaining;
    private int lifetimeShots = 0;
    private int lifetimeHits  = 0;
    /** HP, death and respawn protection: the same implementation the JavaFX enemies use. */
    private final EnemyVitals vitals;
    private HitReactionEffect reaction;
    private float reactionElapsed;
    private static final java.util.concurrent.atomic.AtomicInteger RUNTIME_SEQUENCE =
            new java.util.concurrent.atomic.AtomicInteger(0);
    private final List<ActiveProjectile> activeProjectiles = new ArrayList<>();
    private final List<BeamEffect> activeBeams = new ArrayList<>();
    private final List<ImpactEffect> activeImpacts = new ArrayList<>();
    private long impactCount;

    /** Shared by all enemies so each damage-sprite lookup happens once per game. */
    private static final DamagePathResolver CLASSPATH_DAMAGE_PATHS =
            DamagePathResolver.classpath(GdxEnemyRuntime.class);
    private DamagePathResolver damagePathResolver = CLASSPATH_DAMAGE_PATHS;

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
        this.moveAccumulator = new MovementTickAccumulator(javaFxTickRate, maxEnemyTicksPerFrame);
        BehaviorType behavior = spawn.behavior() == null ? BehaviorType.WANDER : spawn.behavior();
        this.behaviorTypeLabel = behavior.name();
        this.movementTypeLabel = "WANDER";
        this.nonTangibilityEnergy = spawn.nonTangibilityEnergy();
        this.visibilityLevel = spawn.visibilityLevel();
        this.shotCooldownRemaining = 0f;
        this.vitals = new EnemyVitals(this, spawn.maxHitPoints(), null);
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
                spawn.maxHitPoints(),
                spawn.animationSpec());
    }

    // --- ICanDie: delegated to the shared EnemyVitals (same rules as the JavaFX enemies) ------

    @Override
    public int getHitPoints() {
        return vitals.getHitPoints();
    }

    @Override
    public void setHitPoints(int hp) {
        vitals.setHitPoints(hp);
    }

    @Override
    public void subtractHitPoints(int hp) {
        vitals.subtractHitPoints(hp, DeathCause.OTHER);
    }

    @Override
    public void subtractHitPoints(int hp, DeathCause cause) {
        vitals.subtractHitPoints(hp, cause);
    }

    @Override
    public void subtractHitPoints(int hp, DeathCause cause, String attackerId) {
        vitals.subtractHitPoints(hp, cause, attackerId);
    }

    @Override
    public String killerId() {
        return vitals.killerId();
    }

    public int getMaxHitPoints() {
        return vitals.getMaxHitPoints();
    }

    /** Starts a hit reaction (shot jolt / sledgehammer stagger) shown on the sprite. */
    public void react(HitReactionEffect reaction) {
        this.reaction = reaction;
        this.reactionElapsed = 0f;
    }

    public void tickReaction(float dt) {
        if (reaction != null) {
            reactionElapsed += Math.max(0f, dt);
            if (reactionElapsed >= reaction.lifetimeSeconds()) {
                reaction = null;
            }
        }
    }

    /** Sprite offset {dx, dy} of the current hit reaction ({0, 0} when none). */
    public float[] reactionOffset() {
        if (reaction == null) {
            return new float[] {0f, 0f};
        }
        double[] offset = reaction.spriteOffset(reactionElapsed / reaction.lifetimeSeconds());
        return new float[] {(float) offset[0], (float) offset[1]};
    }

    @Override
    public void addHitPoints(int hp) {
        vitals.addHitPoints(hp);
    }

    @Override
    public void addDeathNotificationSubscriber(IDeathSubscriber subscriber) {
        vitals.addDeathSubscriber(subscriber);
    }

    @Override
    public DeathCause deathCause() {
        return vitals.deathCause();
    }

    /** Test seam: replaces the damage-sprite lookup; {@code null} restores the classpath lookup. */
    void setDamagePathResolver(DamagePathResolver resolver) {
        this.damagePathResolver = resolver != null ? resolver : CLASSPATH_DAMAGE_PATHS;
    }

    /** Tick the post-respawn invulnerability window down by {@code dt} seconds. */
    public void tickInvulnerability(float dt) {
        vitals.tick(dt);
    }

    /** Grant a brief invulnerability window (used on respawn to prevent instant re-kill). */
    public void grantRespawnInvulnerability() {
        vitals.respawn(MortalityRules.RESPAWN_INVULNERABILITY_SECONDS);
    }

    /** Whether projectiles, beams or impact explosions of this enemy are still playing out. */
    public boolean hasShotsInFlight() {
        return !activeProjectiles.isEmpty() || !activeBeams.isEmpty() || !activeImpacts.isEmpty();
    }

    /** {@code true} while the post-respawn invulnerability window is active. */
    @Override
    public boolean isInvulnerable() {
        return vitals.isInvulnerable();
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
                emitImpact(projectile.x, projectile.y, (float) PumpkinBurst.STRAIGHT_SHOT_RADIUS, 4f);
                playExplosionSound();
                activeProjectiles.remove(i);
                continue;
            }

            boolean hitPlayer = distanceSquared(projectile.x, projectile.y, playerX, playerY)
                    <= squared(playerRadius + projectile.radius());
            if (projectile.type == ProjectileType.STRAIGHT && hitPlayer) {
                dealtDamage += Math.max(0, spawn.attackDamage());
                lifetimeHits++;
                LOG.fine(() -> String.format("[%s] STRAIGHT hit player at (%.0f,%.0f)", runtimeEnemyId, projectile.x, projectile.y));
                emitImpact(projectile.x, projectile.y, (float) PumpkinBurst.STRAIGHT_SHOT_RADIUS, 5f);
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
                    lifetimeHits++;
                    LOG.fine(() -> String.format("[%s] LOB splash hit player at target (%.0f,%.0f)", runtimeEnemyId, projectile.targetX, projectile.targetY));
                }
                if (projectile.type == ProjectileType.LOB || projectile.type == ProjectileType.STRAIGHT) {
                    float impactRadius = (float) (projectile.type == ProjectileType.LOB
                            ? PumpkinBurst.lobRadius(spawn.splashRadius())
                            : PumpkinBurst.STRAIGHT_SHOT_RADIUS);
                    float shakeMagnitude = projectile.type == ProjectileType.LOB ? 8f : 5f;
                    emitImpact(projectile.boundsX(), projectile.boundsY(), impactRadius, shakeMagnitude);
                    playExplosionSound();
                }
                activeProjectiles.remove(i);
                continue;
            }
        }

        // A dead enemy (e.g. crushed earlier this frame) starts no new attack; in-flight shots still land.
        if (!isAlive()) {
            return dealtDamage;
        }

        if (spawn.attackRange() <= 0f || spawn.projectileSpeed() <= 0f || spawn.attackCooldownMs() <= 0) {
            return dealtDamage;
        }

        if (shotCooldownRemaining > 0f) {
            return dealtDamage;
        }

        float range = Math.max(0f, spawn.attackRange());
        if (distanceSquared(x, y, playerX, playerY) > squared(range)) {
            return dealtDamage;
        }

        ProjectileType projectileType = spawn.projectileType() == null
                ? ProjectileType.STRAIGHT
                : spawn.projectileType();

        if (projectileType == ProjectileType.BEAM) {
            boolean blocked = maze != null && WallCollisionUtil.wallBetween(x, y, playerX, playerY, maze.walls());
            lifetimeShots++;
            if (!blocked) {
                dealtDamage += Math.max(0, spawn.attackDamage());
                lifetimeHits++;
                LOG.fine(() -> String.format("[%s] BEAM fired — target=(%.0f,%.0f)", runtimeEnemyId, playerX, playerY));
            } else {
                LOG.fine(() -> String.format("[%s] BEAM blocked by wall — target=(%.0f,%.0f)", runtimeEnemyId, playerX, playerY));
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
        lifetimeShots++;
        LOG.fine(() -> String.format("[%s] %s fired — target=(%.0f,%.0f)", runtimeEnemyId, projectileType, playerX, playerY));
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
        float radius = Math.max(8f, maxRadius);
        // vary the debris scatter between shots that land on the same spot
        long seed = FireBlast.seedFor(impactX, impactY) + 31L * ++impactCount;
        activeImpacts.add(new ImpactEffect(
                new PumpkinBurst(impactX, impactY, radius, seed),
                Math.max(0f, shakeMagnitude),
                (float) PumpkinBurst.lifetimeFor(radius)));
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
            visuals.add(new BeamVisual(beam.x1, beam.y1, beam.x2, beam.y2, alpha, beam.blocked, 4f));
        }
        return Collections.unmodifiableList(visuals);
    }

    /** Projectile explosions to draw this frame. */
    public List<PumpkinBurstVisual> explosionVisuals() {
        if (activeImpacts.isEmpty()) {
            return List.of();
        }
        List<PumpkinBurstVisual> visuals = new ArrayList<>(activeImpacts.size());
        for (ImpactEffect impact : activeImpacts) {
            float progress = impact.maxDuration <= 0f ? 1f : 1f - Math.max(0f, impact.remaining / impact.maxDuration);
            visuals.add(new PumpkinBurstVisual(impact.burst, progress));
        }
        return Collections.unmodifiableList(visuals);
    }

    /** Strongest screen shake requested by impacts that landed within the last {@value #IMPACT_SHAKE_SECONDS} s. */
    public float impactShake() {
        float shake = 0f;
        for (ImpactEffect impact : activeImpacts) {
            if (impact.maxDuration - impact.remaining < IMPACT_SHAKE_SECONDS) {
                shake = Math.max(shake, impact.shakeMagnitude);
            }
        }
        return shake;
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
        String framePath = dirPath;
        if (frameCount > 1) {
            int frameIndex = ((int) (animationClock * ANIMATION_FPS)) % frameCount;
            framePath = deriveAnimationFramePath(dirPath, frameIndex);
        }
        if (spec != null && spec.damageStatesEnabled()) {
            // spec comes from spawn, so spawn is non-null here
            DamageState state = DamageState.of(vitals.getHitPoints(), vitals.getMaxHitPoints());
            if (state != DamageState.HEALTHY) {
                return damagePathResolver.resolve(framePath, state);
            }
        }
        return framePath;
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

    /** The ghost that teleports the player instead of hurting them. */
    public boolean isPortalGhost() {
        return main.game.maze.game.level.TeleportGhostSpec.isPortalGhostId(spawn != null ? spawn.id() : null);
    }

    /** A zombie sergeant: stands where it is put and fires its gatling gun. */
    public boolean isSergeant() {
        return main.game.maze.game.level.SergeantSpec.isSergeantId(spawn != null ? spawn.id() : null);
    }

    /** A guard tower: stands where it is put, very tough, shoots rpgs and a gatling gun. */
    public boolean isGuardTower() {
        return main.game.maze.game.level.GuardTowerSpec.isTowerId(spawn != null ? spawn.id() : null);
    }

    /** A wall builder: a harmless construction worker zombie the shared builder session walks to its building sites. */
    public boolean isWallBuilder() {
        return main.game.maze.game.level.WallBuilderSpec.isBuilderId(spawn != null ? spawn.id() : null);
    }

    /** Whether touching the player does no harm (portal ghosts teleport, wall builders only build). */
    public boolean isHarmless() {
        return isPortalGhost() || isWallBuilder();
    }

    /** Sergeants, guard towers and wall builders do not walk by the enemy AI: they stand, or the builder session moves them. */
    public boolean isStationary() {
        return isSergeant() || isGuardTower() || isWallBuilder();
    }

    /** Puts the enemy at (x, y), centre, screen space: where the builder session says it is. */
    public void placeAt(float newX, float newY) {
        this.x = newX;
        this.y = newY;
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
        if (isStationary()) {
            return;
        }
        if (nonTangibilityEnergy > 0) {
            nonTangibilityEnergy = GhostNonTangibilityService.drainEnergy(nonTangibilityEnergy, dt);
        }

        int budget = moveAccumulator.accumulate(dt);
        if (budget <= 0) {
            return;
        }
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
            return behaviorTypeLabel + " | " + movementTypeLabel + " " + projectileStats();
        }
        if (showBehaviorType) {
            return behaviorTypeLabel + " " + projectileStats();
        }
        if (showMovementType) {
            return movementTypeLabel;
        }
        return null;
    }

    public String projectileStats() {
        return "H:" + lifetimeHits + " S:" + lifetimeShots;
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

    public record BeamVisual(float x1, float y1, float x2, float y2, float alpha, boolean blocked, float width) {
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
        private final PumpkinBurst burst;
        private final float shakeMagnitude;
        private final float maxDuration;
        private float remaining;

        private ImpactEffect(PumpkinBurst burst, float shakeMagnitude, float duration) {
            this.burst = burst;
            this.shakeMagnitude = shakeMagnitude;
            this.maxDuration = duration;
            this.remaining = duration;
        }
    }
}
