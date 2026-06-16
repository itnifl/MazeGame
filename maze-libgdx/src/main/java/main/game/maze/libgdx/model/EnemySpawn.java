package main.game.maze.libgdx.model;

import main.game.maze.opponents.BehaviorType;

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
        int visibilityLevel) {

    /** Fully-visible value used for non-ghost enemies and as the EMF model default. */
    public static final int DEFAULT_VISIBILITY_LEVEL = 100;

    /** 10-arg convenience constructor — no behavior, nonTangibilityEnergy, or visibilityLevel. */
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
                touchSoundPath, BehaviorType.WANDER, speed, 0.0, DEFAULT_VISIBILITY_LEVEL);
    }

    /** 11-arg convenience constructor — has behavior, no nonTangibilityEnergy or visibilityLevel. */
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
                touchSoundPath, behavior, speed, 0.0, DEFAULT_VISIBILITY_LEVEL);
    }

    /** 12-arg convenience constructor — has nonTangibilityEnergy, no visibilityLevel. */
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
                touchSoundPath, behavior, speed, nonTangibilityEnergy, DEFAULT_VISIBILITY_LEVEL);
    }
}
