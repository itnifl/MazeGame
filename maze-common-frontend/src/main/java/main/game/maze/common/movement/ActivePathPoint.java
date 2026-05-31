package main.game.maze.common.movement;

/**
 * Backend-neutral immutable point used for exposing the currently active path
 * an enemy movement service is following at runtime.
 */
public record ActivePathPoint(double x, double y) {
}