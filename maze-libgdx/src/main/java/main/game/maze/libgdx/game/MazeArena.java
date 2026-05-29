package main.game.maze.libgdx.game;

import java.util.List;

/**
 * Backend-neutral view of a playable maze: a rectangular playfield, a list
 * of wall segments, plus start and goal positions in world (pixel) units.
 * Both {@link SampleMaze} (procedural grid) and {@link RealMaze} (wraps the
 * shared {@code main.game.maze.mazeworld.GameMazeWorld}) implement it so the
 * rendering / collision code can stay agnostic to the wall source.
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
