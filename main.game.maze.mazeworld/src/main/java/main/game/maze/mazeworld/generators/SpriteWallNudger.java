package main.game.maze.mazeworld.generators;

import java.util.List;

/**
 * Backend-neutral helper that nudges an axis-aligned rectangle off any
 * maze {@link WallSegment} it intersects. Used by:
 * <ul>
 *   <li>JavaFX {@code CharacterIntersectionFixerService} to keep initial
 *       sprite placements out of walls.</li>
 *   <li>libGDX {@code GdxGameScreen} to keep the goal/heart sprite from
 *       being bisected by a generated wall.</li>
 * </ul>
 * The geometry is shared (segment vs AABB hit-test + 1px directional nudge)
 * so both frontends apply the same rule (see GR-14 / MGR-14 in the rule docs).
 */
public final class SpriteWallNudger {

    /** Movable AABB the nudger can inspect and translate. */
    public interface MovableAabb {
        float minX();
        float minY();
        float maxX();
        float maxY();
        /** Translate the rectangle by (dx, dy) in world units. */
        void offset(float dx, float dy);
    }

    private SpriteWallNudger() {
    }

    /**
     * Repeatedly nudges the rect by 1 unit perpendicular to any wall it
     * intersects, up to {@code maxIterations} times. A horizontal wall nudges
     * the rect vertically; a vertical wall nudges it horizontally; diagonal
     * walls (none exist in the current generator but handled for safety)
     * nudge along the wall-center-to-rect-center vector.
     */
    public static void nudgeOffWalls(MovableAabb rect, List<WallSegment> walls, int maxIterations) {
        if (rect == null || walls == null || walls.isEmpty()) {
            return;
        }
        for (int i = 0; i < maxIterations; i++) {
            WallSegment hit = firstIntersecting(rect, walls);
            if (hit == null) {
                return;
            }
            nudge(rect, hit);
        }
    }

    public static WallSegment firstIntersecting(MovableAabb rect, List<WallSegment> walls) {
        for (WallSegment w : walls) {
            if (intersects(rect, w)) {
                return w;
            }
        }
        return null;
    }

    public static boolean intersects(MovableAabb rect, WallSegment wall) {
        float rx1 = rect.minX();
        float ry1 = rect.minY();
        float rx2 = rect.maxX();
        float ry2 = rect.maxY();
        float x1 = wall.x1, y1 = wall.y1, x2 = wall.x2, y2 = wall.y2;

        float wx1 = Math.min(x1, x2), wy1 = Math.min(y1, y2);
        float wx2 = Math.max(x1, x2), wy2 = Math.max(y1, y2);
        if (rx2 < wx1 || rx1 > wx2 || ry2 < wy1 || ry1 > wy2) {
            return false;
        }
        if (pointInRect(x1, y1, rx1, ry1, rx2, ry2) || pointInRect(x2, y2, rx1, ry1, rx2, ry2)) {
            return true;
        }
        if (segmentsIntersect(x1, y1, x2, y2, rx1, ry1, rx2, ry1)) return true;
        if (segmentsIntersect(x1, y1, x2, y2, rx2, ry1, rx2, ry2)) return true;
        if (segmentsIntersect(x1, y1, x2, y2, rx2, ry2, rx1, ry2)) return true;
        if (segmentsIntersect(x1, y1, x2, y2, rx1, ry2, rx1, ry1)) return true;
        return false;
    }

    private static void nudge(MovableAabb rect, WallSegment wall) {
        float cx = (rect.minX() + rect.maxX()) * 0.5f;
        float cy = (rect.minY() + rect.maxY()) * 0.5f;
        if (wall.isHorizontal()) {
            rect.offset(0f, cy >= wall.y1 ? 1f : -1f);
        } else if (Math.abs(wall.x1 - wall.x2) < 1e-4f) {
            rect.offset(cx >= wall.x1 ? 1f : -1f, 0f);
        } else {
            float mx = (wall.x1 + wall.x2) * 0.5f;
            float my = (wall.y1 + wall.y2) * 0.5f;
            float dx = cx - mx;
            float dy = cy - my;
            float len = (float) Math.hypot(dx, dy);
            if (len == 0f) len = 1f;
            rect.offset(dx / len, dy / len);
        }
    }

    private static boolean pointInRect(float x, float y, float rx1, float ry1, float rx2, float ry2) {
        return x >= rx1 && x <= rx2 && y >= ry1 && y <= ry2;
    }

    private static boolean segmentsIntersect(float x1, float y1, float x2, float y2,
                                             float x3, float y3, float x4, float y4) {
        float d1 = direction(x3, y3, x4, y4, x1, y1);
        float d2 = direction(x3, y3, x4, y4, x2, y2);
        float d3 = direction(x1, y1, x2, y2, x3, y3);
        float d4 = direction(x1, y1, x2, y2, x4, y4);
        if (((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) &&
            ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0))) {
            return true;
        }
        if (d1 == 0 && onSegment(x3, y3, x4, y4, x1, y1)) return true;
        if (d2 == 0 && onSegment(x3, y3, x4, y4, x2, y2)) return true;
        if (d3 == 0 && onSegment(x1, y1, x2, y2, x3, y3)) return true;
        if (d4 == 0 && onSegment(x1, y1, x2, y2, x4, y4)) return true;
        return false;
    }

    private static float direction(float xi, float yi, float xj, float yj, float xk, float yk) {
        return (xk - xi) * (yj - yi) - (xj - xi) * (yk - yi);
    }

    private static boolean onSegment(float xi, float yi, float xj, float yj, float xk, float yk) {
        return Math.min(xi, xj) <= xk && xk <= Math.max(xi, xj) &&
               Math.min(yi, yj) <= yk && yk <= Math.max(yi, yj);
    }
}
