package main.game.maze.mazeworld.generators;

import java.util.List;

/**
 * Backend-neutral view of a playable maze: a rectangular playfield, a list
 * of wall segments, plus start and goal positions in world units.
 */
public interface MazeArena {
    List<WallSegment> walls();

    float widthPx();
    float heightPx();

    float startX();
    float startY();
    float goalX();
    float goalY();
}
