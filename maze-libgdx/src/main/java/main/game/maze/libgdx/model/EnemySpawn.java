package main.game.maze.libgdx.model;

import main.game.maze.opponents.BehaviorType;

/**
 * Immutable enemy sprite placement derived from the shared Ecore/XMI model.
 *
 * <p>{@code speed} is the per-enemy movement rate in the same units the
 * opponent model uses, already scaled by the active difficulty's
 * {@code monstersMovementSpeedMultiplier} (see
 * {@link main.game.maze.opponents.util.EnemySpawnPlanner#applySpeedMultiplier}).
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
        double nonTangibilityEnergy) {

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
                touchSoundPath, BehaviorType.WANDER, speed, 0.0);
    }

    /** Convenience constructor matching the 11-arg canonical form minus nonTangibilityEnergy. */
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
                touchSoundPath, behavior, speed, 0.0);
    }
}


