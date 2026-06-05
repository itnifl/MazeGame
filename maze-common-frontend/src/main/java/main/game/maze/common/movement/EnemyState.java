package main.game.maze.common.movement;

/**
 * Per-tick input to {@link EnemyMovementService}: an enemy's current
 * position, last 4-way direction, bounding-box edge size, and per-tick
 * speed in world units. Immutable.
 */
public record EnemyState(
        String id,
        double x,
        double y,
        int directionX,
        int directionY,
        double size,
        double speed) {
}


