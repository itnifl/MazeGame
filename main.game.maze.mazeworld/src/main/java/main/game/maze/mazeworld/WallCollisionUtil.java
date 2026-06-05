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

    /**
     * Returns {@code true} if any {@link WallSegment} in {@code walls} separates the
     * line segment from {@code (ex, ey)} to {@code (px, py)}; i.e. the straight line
     * between the two positions crosses a wall.  Used to prevent enemies from
     * damaging a player who is on the other side of a wall.
     */
    public static boolean wallBetween(double ex, double ey, double px, double py,
                                      List<WallSegment> walls) {
        for (WallSegment w : walls) {
            if (segmentCrossesWall(ex, ey, px, py, w.x1, w.y1, w.x2, w.y2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Same as {@link #wallBetween} but accepts walls in the unflipped JavaFX
     * {@link Vector2D} representation.
     */
    public static boolean wallBetweenVectors(double ex, double ey, double px, double py,
                                             List<Vector2D> walls) {
        for (Vector2D w : walls) {
            double wx1 = w.getStart().getX();
            double wy1 = w.getStart().getY();
            double wx2 = w.getEnd().getX();
            double wy2 = w.getEnd().getY();
            if (segmentCrossesWall(ex, ey, px, py, wx1, wy1, wx2, wy2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Core geometry: does the line from {@code (ex,ey)} to {@code (px,py)} strictly
     * cross the axis-aligned wall segment from {@code (wx1,wy1)} to {@code (wx2,wy2)}?
     * "Strictly" means the two points must be on opposite sides; touching the wall
     * from the same side does not count.
     */
    private static boolean segmentCrossesWall(double ex, double ey, double px, double py,
                                               double wx1, double wy1, double wx2, double wy2) {
        if (Math.abs(wy1 - wy2) < 0.001d) {
            // Horizontal wall at y = wy1
            double sign1 = ey - wy1;
            double sign2 = py - wy1;
            if (sign1 * sign2 >= 0) return false; // same side or touching — not crossing
            // t is the parameter along (ex→px) where the line crosses y = wy1
            double t = (wy1 - ey) / (py - ey);
            double xCross = ex + t * (px - ex);
            double minX = Math.min(wx1, wx2);
            double maxX = Math.max(wx1, wx2);
            return xCross >= minX && xCross <= maxX;
        } else {
            // Vertical wall at x = wx1
            double sign1 = ex - wx1;
            double sign2 = px - wx1;
            if (sign1 * sign2 >= 0) return false; // same side or touching — not crossing
            double t = (wx1 - ex) / (px - ex);
            double yCross = ey + t * (py - ey);
            double minY = Math.min(wy1, wy2);
            double maxY = Math.max(wy1, wy2);
            return yCross >= minY && yCross <= maxY;
        }
    }
}


