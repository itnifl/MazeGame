package main.game.maze.runtime.generators;

import java.util.List;

import main.game.maze.Vector2D;

/**
 * Strategy interface for maze generation.
 */
public interface IMazeGenerator {

    /**
     * Generates a list of wall segments that make up the maze.
     *
     * The coordinates should be in screen space, consistent with how
     * Vector2D is used elsewhere (e.g. in GameController).
     */
    List<Vector2D> generateMaze();
}