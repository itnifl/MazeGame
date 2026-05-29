package main.game.maze.mazeworld.generators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.service.MazeNavigationGraph;

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
        float[] points = computeStartAndGoal(world.getNavigationGraph(), heightPx, widthPx);
        this.startX = points[0];
        this.startY = points[1];
        this.goalX = points[2];
        this.goalY = points[3];
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

    private static float[] computeStartAndGoal(MazeNavigationGraph graph, int heightPx, int widthPx) {
        if (graph == null) {
            return new float[] {24f, heightPx - 24f, widthPx - 24f, 24f};
        }

        MazeNavigationGraph.Node[][] grid = graph.getGrid();
        MazeNavigationGraph.Node startNode = null;
        MazeNavigationGraph.Node goalNode = null;
        double bestStart = Double.POSITIVE_INFINITY;
        double bestGoal = Double.POSITIVE_INFINITY;

        double targetStartX = 60.0;
        double targetStartY = 60.0;
        double targetGoalX = widthPx - 60.0;
        double targetGoalY = heightPx - 60.0;

        for (int c = 0; c < graph.getCols(); c++) {
            for (int r = 0; r < graph.getRows(); r++) {
                MazeNavigationGraph.Node node = grid[c][r];
                if (node == null) {
                    continue;
                }
                double x = node.getX();
                double y = heightPx - node.getY();
                double ds = sqr(x - targetStartX) + sqr(y - targetStartY);
                if (ds < bestStart) {
                    bestStart = ds;
                    startNode = node;
                }
                double dg = sqr(x - targetGoalX) + sqr(y - targetGoalY);
                if (dg < bestGoal) {
                    bestGoal = dg;
                    goalNode = node;
                }
            }
        }

        if (startNode == null || goalNode == null) {
            return new float[] {24f, heightPx - 24f, widthPx - 24f, 24f};
        }

        return new float[] {
            (float) startNode.getX(),
            (float) (heightPx - startNode.getY()),
            (float) goalNode.getX(),
            (float) (heightPx - goalNode.getY())
        };
    }

    private static double sqr(double v) {
        return v * v;
    }
}
