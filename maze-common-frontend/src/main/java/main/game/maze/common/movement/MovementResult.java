package main.game.maze.common.movement;

/**
 * Per-tick output of {@link EnemyMovementService}: the enemy's new
 * position, the cardinal direction chosen this tick, and whether the
 * enemy actually advanced (vs being blocked by a wall on every axis).
 */
public record MovementResult(
        double x,
        double y,
        int directionX,
        int directionY,
        boolean moved) {
}
