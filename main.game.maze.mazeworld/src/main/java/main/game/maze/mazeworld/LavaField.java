package main.game.maze.mazeworld;

import java.util.ArrayList;
import java.util.List;

/**
 * Streams of lava that snake across the maze, under the walls, with bridges over them. Lava kills whoever stands in it;
 * a bridge is the only place a stream can be crossed. World space (y-down), shared by both frontends.
 */
public final class LavaField {

    /** A bridge over a stream: centred on the stream, flat across it, {@code length} along the flow. */
    public record Bridge(double x, double y, double flowAngleRadians, double length) {
    }

    /** One stream: the middle line from where it starts to where it ends, and its bridges. */
    public record Stream(List<Point2D> path, List<Bridge> bridges) {

        public Stream {
            path = List.copyOf(path);
            bridges = List.copyOf(bridges);
        }
    }

    /** How far a bridge reaches past the edge of the stream on each side. */
    public static final double BRIDGE_OVERHANG = 10.0;
    private static final double SAMPLE_STEP = 5.0;

    private final List<Stream> streams;
    private final double width;

    public LavaField(List<Stream> streams, double width) {
        if (width <= 0) {
            throw new IllegalArgumentException("a stream needs a width");
        }
        this.streams = List.copyOf(streams);
        this.width = width;
    }

    public List<Stream> streams() {
        return streams;
    }

    /** The width of every stream, edge to edge. */
    public double width() {
        return width;
    }

    /** Whether the point is in a stream (a bridge does not matter here). */
    public boolean isLava(double x, double y) {
        double half = width / 2.0;
        for (Stream stream : streams) {
            if (distanceToPath(stream.path(), x, y) <= half) {
                return true;
            }
        }
        return false;
    }

    /** Whether the point is on a bridge. */
    public boolean onBridge(double x, double y) {
        for (Stream stream : streams) {
            for (Bridge bridge : stream.bridges()) {
                if (bridgeCovers(bridge, width, x, y)) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Whether the point is on this bridge, over a stream of this width. */
    public static boolean bridgeCovers(Bridge bridge, double streamWidth, double x, double y) {
        double dx = x - bridge.x();
        double dy = y - bridge.y();
        double along = dx * Math.cos(bridge.flowAngleRadians()) + dy * Math.sin(bridge.flowAngleRadians());
        double across = -dx * Math.sin(bridge.flowAngleRadians()) + dy * Math.cos(bridge.flowAngleRadians());
        return Math.abs(along) <= bridge.length() / 2.0 && Math.abs(across) <= streamWidth / 2.0 + BRIDGE_OVERHANG;
    }

    /** Whether standing here is deadly: in a stream and not on a bridge. */
    public boolean isDeadly(double x, double y) {
        return isLava(x, y) && !onBridge(x, y);
    }

    /** Whether the straight line between two points runs through deadly lava (a bridge is safe). */
    public boolean crossesDeadly(double x1, double y1, double x2, double y2) {
        double length = Math.hypot(x2 - x1, y2 - y1);
        // never step further than half a stream width, so a thin stream cannot be jumped over between two samples
        double step = Math.max(0.25, Math.min(SAMPLE_STEP, width / 2.0));
        int steps = Math.max(1, (int) Math.ceil(length / step));
        for (int i = 0; i <= steps; i++) {
            double t = (double) i / steps;
            if (isDeadly(x1 + (x2 - x1) * t, y1 + (y2 - y1) * t)) {
                return true;
            }
        }
        return false;
    }

    /** Shortest distance from the point to the middle line of the stream. */
    public static double distanceToPath(List<Point2D> path, double x, double y) {
        double best = Double.MAX_VALUE;
        for (int i = 0; i + 1 < path.size(); i++) {
            Point2D a = path.get(i);
            Point2D b = path.get(i + 1);
            double dx = b.getX() - a.getX();
            double dy = b.getY() - a.getY();
            double lengthSquared = dx * dx + dy * dy;
            double t = lengthSquared == 0 ? 0 : Math.max(0, Math.min(1, ((x - a.getX()) * dx + (y - a.getY()) * dy) / lengthSquared));
            best = Math.min(best, Math.hypot(x - (a.getX() + t * dx), y - (a.getY() + t * dy)));
        }
        return best;
    }

    /** All the points of all the streams, for tests and drawing. */
    public List<Point2D> allPathPoints() {
        List<Point2D> all = new ArrayList<>();
        for (Stream stream : streams) {
            all.addAll(stream.path());
        }
        return all;
    }
}
