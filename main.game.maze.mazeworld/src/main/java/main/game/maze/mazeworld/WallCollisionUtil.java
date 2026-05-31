package main.game.maze.mazeworld;

import java.util.List;

import main.game.maze.mazeworld.generators.WallSegment;

/**
 * Single canonical AABB-vs-axis-aligned-wall collision test shared by every
 * frontend. Neither the JavaFX controller nor the libGDX screen should
 * implement this logic independently.
 *
 * <p>Two methods are provided so callers can work with whichever wall
 * representation they already hold — {@link WallSegment} ({@link #wouldCollide})
 * or {@link Vector2D} ({@link #wouldCollideVectors}). Java type erasure prevents
 * both from sharing the name {@code wouldCollide}.
 *
 * <p>All collision maths are in the private {@link #wallHitsAABB} helper so
 * the algorithm lives in exactly one place.
 */
public final class WallCollisionUtil {

    private WallCollisionUtil() {
        // utility class
    }

    /**
     * Tests whether a square of side {@code size} centred at {@code (cx, cy)}
     * overlaps any wall in {@code walls} (WallSegment list) or lies outside the
     * maze bounds.
     */
    public static boolean wouldCollide(double cx, double cy, double size,
                                       double maxX, double maxY,
                                       List<WallSegment> walls) {
        double half = size * 0.5d;
        if (cx - half < 0d || cy - half < 0d) return true;
        if (cx + half > maxX || cy + half > maxY) return true;
        double left   = cx - half;
        double right  = cx + half;
        double bottom = cy - half;
        double top    = cy + half;
        for (WallSegment w : walls) {
            if (wallHitsAABB(w.x1, w.y1, w.x2, w.y2, left, right, bottom, top)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tests whether a square of side {@code size} centred at {@code (cx, cy)}
     * overlaps any wall in {@code walls} ({@link Vector2D} list, unflipped
     * JavaFX coordinate space) or lies outside the maze bounds.
     */
    public static boolean wouldCollideVectors(double cx, double cy, double size,
                                              double maxX, double maxY,
                                              List<Vector2D> walls) {
        double half = size * 0.5d;
        if (cx - half < 0d || cy - half < 0d) return true;
        if (cx + half > maxX || cy + half > maxY) return true;
        double left   = cx - half;
        double right  = cx + half;
        double bottom = cy - half;
        double top    = cy + half;
        for (Vector2D w : walls) {
            double wx1 = w.getStart().getX();
            double wy1 = w.getStart().getY();
            double wx2 = w.getEnd().getX();
            double wy2 = w.getEnd().getY();
            if (wallHitsAABB(wx1, wy1, wx2, wy2, left, right, bottom, top)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Core geometry: does an axis-aligned wall segment from {@code (wx1,wy1)} to
     * {@code (wx2,wy2)} intersect the AABB defined by the four edges?
     */
    private static boolean wallHitsAABB(double wx1, double wy1, double wx2, double wy2,
                                         double left, double right, double bottom, double top) {
        if (Math.abs(wy1 - wy2) < 0.001d) {
            // horizontal wall
            double minX = Math.min(wx1, wx2);
            double maxX = Math.max(wx1, wx2);
            if (right < minX || left > maxX) return false;
            return bottom <= wy1 && top >= wy1;
        } else {
            // vertical wall
            double minY = Math.min(wy1, wy2);
            double maxY = Math.max(wy1, wy2);
            if (top < minY || bottom > maxY) return false;
            return left <= wx1 && right >= wx1;
        }
    }
}
