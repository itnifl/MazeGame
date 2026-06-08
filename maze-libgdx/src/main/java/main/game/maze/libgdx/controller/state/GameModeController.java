package main.game.maze.libgdx.controller.state;

/**
 * Common mode controller abstraction for per frame updates.
 */
@FunctionalInterface
public interface GameModeController {
    boolean update();
}
