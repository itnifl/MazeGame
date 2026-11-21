package main.game.maze.game.hazard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import main.game.maze.mazeworld.Point2D;

/**
 * Where a teleported player comes out. The candidates are always the free points of the level (corridor
 * points that keep clear of every wall), so nobody lands inside a wall. "The edge" is the outer band of the
 * area those points span.
 */
public final class EdgeTeleport {

    /** The outer band is this share of the shorter side of the area. */
    private static final double BAND_SHARE = 0.12;

    private EdgeTeleport() {
    }

    /**
     * The exit of a hole: the opposite edge of the map. The edge nearest to {@code (fromX, fromY)} decides which
     * edge is opposite; the exit is the free point in that edge's band nearest the same position along it.
     *
     * @return a free point, or null when there are none
     */
    public static Point2D oppositeEdge(double fromX, double fromY, List<Point2D> free) {
        if (free == null || free.isEmpty()) {
            return null;
        }
        Bounds bounds = Bounds.of(free);
        double toLeft = fromX - bounds.minX;
        double toRight = bounds.maxX - fromX;
        double toTop = fromY - bounds.minY;
        double toBottom = bounds.maxY - fromY;
        double nearest = Math.min(Math.min(toLeft, toRight), Math.min(toTop, toBottom));
        double idealX = fromX;
        double idealY = fromY;
        Edge target;
        if (nearest == toLeft) {
            idealX = bounds.maxX;
            target = Edge.RIGHT;
        } else if (nearest == toRight) {
            idealX = bounds.minX;
            target = Edge.LEFT;
        } else if (nearest == toTop) {
            idealY = bounds.maxY;
            target = Edge.BOTTOM;
        } else {
            idealY = bounds.minY;
            target = Edge.TOP;
        }
        List<Point2D> candidates = new ArrayList<>();
        for (Point2D point : free) {
            if (bounds.inBand(point, target)) {
                candidates.add(point);
            }
        }
        return nearestTo(candidates.isEmpty() ? free : candidates, idealX, idealY);
    }

    /**
     * A random free point in the outer band of the map, on any of the four edges.
     *
     * @return a free point, or null when there are none
     */
    public static Point2D randomOuterEdge(List<Point2D> free, Random random) {
        if (free == null || free.isEmpty()) {
            return null;
        }
        Bounds bounds = Bounds.of(free);
        List<Point2D> outer = new ArrayList<>();
        for (Point2D point : free) {
            for (Edge edge : Edge.values()) {
                if (bounds.inBand(point, edge)) {
                    outer.add(point);
                    break;
                }
            }
        }
        List<Point2D> pool = outer.isEmpty() ? free : outer;
        return pool.get(random.nextInt(pool.size()));
    }

    private static Point2D nearestTo(List<Point2D> points, double x, double y) {
        Point2D best = null;
        double bestDistance = Double.MAX_VALUE;
        for (Point2D point : points) {
            double distance = Math.hypot(point.getX() - x, point.getY() - y);
            if (distance < bestDistance) {
                bestDistance = distance;
                best = point;
            }
        }
        return best;
    }

    private enum Edge { LEFT, RIGHT, TOP, BOTTOM }

    /** The area the free points span, and its outer band. */
    private record Bounds(double minX, double maxX, double minY, double maxY, double band) {

        static Bounds of(List<Point2D> points) {
            double minX = Double.MAX_VALUE;
            double minY = Double.MAX_VALUE;
            double maxX = -Double.MAX_VALUE;
            double maxY = -Double.MAX_VALUE;
            for (Point2D point : points) {
                minX = Math.min(minX, point.getX());
                maxX = Math.max(maxX, point.getX());
                minY = Math.min(minY, point.getY());
                maxY = Math.max(maxY, point.getY());
            }
            double band = Math.max(1.0, BAND_SHARE * Math.min(maxX - minX, maxY - minY));
            return new Bounds(minX, maxX, minY, maxY, band);
        }

        boolean inBand(Point2D point, Edge edge) {
            return switch (edge) {
                case LEFT -> point.getX() <= minX + band;
                case RIGHT -> point.getX() >= maxX - band;
                case TOP -> point.getY() <= minY + band;
                case BOTTOM -> point.getY() >= maxY - band;
            };
        }
    }
}
