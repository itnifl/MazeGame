package main.game.maze.mazeworld.generators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;

/**
 * Adapter that exposes shared GameMazeWorld as a MazeArena.
 */
public final class RealMaze implements MazeArena {

    private final float widthPx;
    private final float heightPx;
    private final float startX;
    private final float startY;
    private final float goalX;
    private final float goalY;
    private final List<WallSegment> walls;

    public RealMaze(GameMazeWorld world, int widthPx, int heightPx) {
        if (world == null) throw new IllegalArgumentException("world must not be null");
        if (widthPx <= 0 || heightPx <= 0) {
            throw new IllegalArgumentException("widthPx and heightPx must be positive");
        }
        this.widthPx = widthPx;
        this.heightPx = heightPx;
        this.walls = Collections.unmodifiableList(flipWalls(world.getMazeVectors(), heightPx));
        this.startX = 24f;
        this.startY = heightPx - 24f;
        this.goalX = widthPx - 24f;
        this.goalY = 24f;
    }

    public static RealMaze fresh(int widthPx, int heightPx) {
        return new RealMaze(GameMazeWorld.RegenerateWorld(widthPx, heightPx), widthPx, heightPx);
    }

    @Override public List<WallSegment> walls()    { return walls; }
    @Override public float widthPx()              { return widthPx; }
    @Override public float heightPx()             { return heightPx; }
    @Override public float startX()               { return startX; }
    @Override public float startY()               { return startY; }
    @Override public float goalX()                { return goalX; }
    @Override public float goalY()                { return goalY; }

    private static List<WallSegment> flipWalls(List<Vector2D> vectors, int heightPx) {
        List<WallSegment> out = new ArrayList<>(vectors.size());
        for (Vector2D v : vectors) {
            Point2D s = v.getStart();
            Point2D e = v.getEnd();
            float x1 = (float) s.getX();
            float y1 = heightPx - (float) s.getY();
            float x2 = (float) e.getX();
            float y2 = heightPx - (float) e.getY();
            out.add(new WallSegment(x1, y1, x2, y2));
        }
        return out;
    }
}
