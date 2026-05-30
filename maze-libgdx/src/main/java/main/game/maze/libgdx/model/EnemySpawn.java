package main.game.maze.libgdx.model;

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
        float speed) {
}
