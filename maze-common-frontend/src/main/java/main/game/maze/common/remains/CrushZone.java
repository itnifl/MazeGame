package main.game.maze.common.remains;

/**
 * Decides which enemies a collapsing wall segment crushes: those whose body touches the
 * segment, plus a small margin for falling rubble. Frame-agnostic (pure geometry).
 */
public final class CrushZone {

    /** Extra reach of the falling rubble beyond the wall line, in pixels. */
    public static final double MARGIN = 6.0;
    /** Share of an enemy's drawn sprite size that counts as its body radius. */
    public static final double BODY_RADIUS_FACTOR = 0.4;

    private CrushZone() {
    }

    /**
     * Whether the wall segment from (ax, ay) to (bx, by) crushes an enemy centred at (cx, cy).
     *
     * @param radius enemy body radius
     */
    public static boolean crushes(double ax, double ay, double bx, double by,
                                  double cx, double cy, double radius) {
        double dx = bx - ax;
        double dy = by - ay;
        double lengthSq = dx * dx + dy * dy;
        double t = lengthSq == 0 ? 0 : Math.max(0, Math.min(1, ((cx - ax) * dx + (cy - ay) * dy) / lengthSq));
        double nearestX = ax + t * dx;
        double nearestY = ay + t * dy;
        return Math.hypot(cx - nearestX, cy - nearestY) <= radius + MARGIN;
    }
}
