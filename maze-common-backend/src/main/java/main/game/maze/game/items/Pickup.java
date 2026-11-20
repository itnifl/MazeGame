package main.game.maze.game.items;

/**
 * An item lying in the maze, waiting to be picked up.
 *
 * @param x centre x in world space (y-down)
 * @param y centre y in world space (y-down)
 */
public record Pickup(long id, PickupKind kind, double x, double y) {
}
