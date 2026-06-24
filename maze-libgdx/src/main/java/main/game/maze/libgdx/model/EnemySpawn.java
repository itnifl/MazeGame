package main.game.maze.libgdx.model;

import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.ProjectileType;

/**
 * Immutable enemy sprite placement derived from the shared Ecore/XMI model.
 *
 * <p>{@code speed} is the per-enemy movement rate in the same units the
 * opponent model uses, already scaled by the active difficulty's
 * {@code monstersMovementSpeedMultiplier} (see
 * {@link main.game.maze.opponents.util.EnemySpawnPlanner#applySpeedMultiplier}).
 *
 * <p>{@code visibilityLevel} is read from {@code Ghost.visibilityLevel} (0–100,
 * default {@value #DEFAULT_VISIBILITY_LEVEL}). Non-ghost enemies use the default.
 *
 * <p>The projectile-tuning fields ({@code projectileType}, {@code splashRadius},
 * {@code arcHeight}, {@code attackRange}, {@code attackCooldownMs},
 * {@code projectileSpeed}) are populated for ranged enemies and default to
 * {@link #DEFAULT_PROJECTILE_TYPE}/{@code 0} for melee enemies.
 *
 * <p>{@code resurrectionTimeMs} is read from {@code Zombie.resurrectionTime} (seconds
 * converted to milliseconds). A value of 0 means no resurrection.
 *
 * <p>{@code maxHitPoints} is the enemy's hit-point pool used by the libGDX runtime to
 * track damage. Defaults to {@value #DEFAULT_MAX_HIT_POINTS}.
 */
public record EnemySpawn(
        String id,
        String imagePath,
        float x,
        float y,
        float size,
        float effectiveThreat,
        int attackDamage,
        int infectionLevel,
        String touchSoundPath,
        BehaviorType behavior,
        float speed,
        double nonTangibilityEnergy,
        int visibilityLevel,
        ProjectileType projectileType,
        float splashRadius,
        float arcHeight,
        float attackRange,
        int attackCooldownMs,
        float projectileSpeed,
        int resurrectionTimeMs,
        int maxHitPoints) {

    /** Fully-visible value used for non-ghost enemies and as the EMF model default. */
    public static final int DEFAULT_VISIBILITY_LEVEL = 100;
    public static final ProjectileType DEFAULT_PROJECTILE_TYPE = ProjectileType.STRAIGHT;
    public static final float DEFAULT_SPLASH_RADIUS = 0f;
    public static final float DEFAULT_ARC_HEIGHT = 0f;
    public static final float DEFAULT_ATTACK_RANGE = 0f;
    public static final int DEFAULT_ATTACK_COOLDOWN_MS = 0;
    public static final float DEFAULT_PROJECTILE_SPEED = 0f;
    /** No resurrection by default. */
    public static final int DEFAULT_RESURRECTION_TIME_MS = 0;
    /** Default enemy hit-point pool (matches CharacterType health default). */
    public static final int DEFAULT_MAX_HIT_POINTS = 100;

    /** 10-arg convenience constructor — no behavior, nonTangibilityEnergy, visibilityLevel, or projectile tuning. */
    public EnemySpawn(
            String id,
            String imagePath,
            float x,
            float y,
            float size,
            float effectiveThreat,
            int attackDamage,
            int infectionLevel,
            String touchSoundPath,
            float speed) {
        this(id, imagePath, x, y, size, effectiveThreat, attackDamage, infectionLevel,
                touchSoundPath, BehaviorType.WANDER, speed, 0.0, DEFAULT_VISIBILITY_LEVEL,
                DEFAULT_PROJECTILE_TYPE, DEFAULT_SPLASH_RADIUS, DEFAULT_ARC_HEIGHT,
                DEFAULT_ATTACK_RANGE, DEFAULT_ATTACK_COOLDOWN_MS, DEFAULT_PROJECTILE_SPEED,
                DEFAULT_RESURRECTION_TIME_MS, DEFAULT_MAX_HIT_POINTS);
    }

    /** 11-arg convenience constructor — has behavior, no nonTangibilityEnergy, visibilityLevel, or projectile tuning. */
    public EnemySpawn(
            String id,
            String imagePath,
            float x,
            float y,
            float size,
            float effectiveThreat,
            int attackDamage,
            int infectionLevel,
            String touchSoundPath,
            BehaviorType behavior,
            float speed) {
        this(id, imagePath, x, y, size, effectiveThreat, attackDamage, infectionLevel,
                touchSoundPath, behavior, speed, 0.0, DEFAULT_VISIBILITY_LEVEL,
                DEFAULT_PROJECTILE_TYPE, DEFAULT_SPLASH_RADIUS, DEFAULT_ARC_HEIGHT,
                DEFAULT_ATTACK_RANGE, DEFAULT_ATTACK_COOLDOWN_MS, DEFAULT_PROJECTILE_SPEED,
                DEFAULT_RESURRECTION_TIME_MS, DEFAULT_MAX_HIT_POINTS);
    }

    /** 12-arg convenience constructor — has nonTangibilityEnergy, no visibilityLevel or projectile tuning. */
    public EnemySpawn(
            String id,
            String imagePath,
            float x,
            float y,
            float size,
            float effectiveThreat,
            int attackDamage,
            int infectionLevel,
            String touchSoundPath,
            BehaviorType behavior,
            float speed,
            double nonTangibilityEnergy) {
        this(id, imagePath, x, y, size, effectiveThreat, attackDamage, infectionLevel,
                touchSoundPath, behavior, speed, nonTangibilityEnergy, DEFAULT_VISIBILITY_LEVEL,
                DEFAULT_PROJECTILE_TYPE, DEFAULT_SPLASH_RADIUS, DEFAULT_ARC_HEIGHT,
                DEFAULT_ATTACK_RANGE, DEFAULT_ATTACK_COOLDOWN_MS, DEFAULT_PROJECTILE_SPEED,
                DEFAULT_RESURRECTION_TIME_MS, DEFAULT_MAX_HIT_POINTS);
    }

    /**
     * 19-arg convenience constructor — all projectile fields but default resurrection and max HP.
     * Kept for backwards-compatibility with call sites that pre-date resurrection support.
     */
    public EnemySpawn(
            String id,
            String imagePath,
            float x,
            float y,
            float size,
            float effectiveThreat,
            int attackDamage,
            int infectionLevel,
            String touchSoundPath,
            BehaviorType behavior,
            float speed,
            double nonTangibilityEnergy,
            int visibilityLevel,
            ProjectileType projectileType,
            float splashRadius,
            float arcHeight,
            float attackRange,
            int attackCooldownMs,
            float projectileSpeed) {
        this(id, imagePath, x, y, size, effectiveThreat, attackDamage, infectionLevel,
                touchSoundPath, behavior, speed, nonTangibilityEnergy, visibilityLevel,
                projectileType, splashRadius, arcHeight, attackRange, attackCooldownMs,
                projectileSpeed, DEFAULT_RESURRECTION_TIME_MS, DEFAULT_MAX_HIT_POINTS);
    }

    /** 13-arg convenience constructor — has visibilityLevel, no projectile tuning fields. */
    public EnemySpawn(
            String id,
            String imagePath,
            float x,
            float y,
            float size,
            float effectiveThreat,
            int attackDamage,
            int infectionLevel,
            String touchSoundPath,
            BehaviorType behavior,
            float speed,
            double nonTangibilityEnergy,
            int visibilityLevel) {
        this(id, imagePath, x, y, size, effectiveThreat, attackDamage, infectionLevel,
                touchSoundPath, behavior, speed, nonTangibilityEnergy, visibilityLevel,
                DEFAULT_PROJECTILE_TYPE, DEFAULT_SPLASH_RADIUS, DEFAULT_ARC_HEIGHT,
                DEFAULT_ATTACK_RANGE, DEFAULT_ATTACK_COOLDOWN_MS, DEFAULT_PROJECTILE_SPEED,
                DEFAULT_RESURRECTION_TIME_MS, DEFAULT_MAX_HIT_POINTS);
    }
}
