package main.game.maze.common.graphics.fire;

import java.util.List;

/**
 * The lava of a level: dark crusted banks, a red-orange river that glows, bright blobs that flow along the stream,
 * a few sparks, and the wooden bridges on top. The streams are drawn as a chain of circles along their middle line,
 * so any frontend that can play a {@link PuffEffect} can draw it. It lasts as long as the level and flows with the
 * running clock. Drawn <em>under</em> the walls: the frontends draw it before the maze.
 *
 * <p>The field is in world space (y-down); {@code flipHeight} &gt; 0 turns it over for a y-up screen
 * ({@code y' = flipHeight - y}), as the other level effects do.
 */
public final class LavaEffect implements PuffEffect {

    /** Practically endless: the level removes the effect, the clock never does. */
    static final double LIFETIME = 1.0e9;
    private static final double CHAIN_STEP = 6.0;
    private static final double FLOW_SPEED = 38.0;
    private static final double BLOB_SPACING = 70.0;
    private static final double PLANK = 7.0;

    /** A point of a stream, world space. */
    public record Point(double x, double y) {
    }

    /** A bridge: centre, direction of the flow at the bridge and length along the flow. */
    public record Bridge(double x, double y, double flowAngleRadians, double length) {
    }

    /** A stream: its middle line (in the direction of the flow) and its bridges. */
    public record Stream(List<Point> path, List<Bridge> bridges) {
    }

    private final List<Stream> streams;
    private final double width;
    private final double bridgeOverhang;
    private final double flipHeight;
    private final double boardWidth;
    private final double boardHeight;

    /**
     * @param width          width of every stream
     * @param bridgeOverhang how far a bridge reaches past the edge of the stream on each side
     */
    public LavaEffect(List<Stream> streams, double width, double bridgeOverhang, double boardWidth, double boardHeight,
                      double flipHeight) {
        this.streams = List.copyOf(streams);
        this.width = width;
        this.bridgeOverhang = bridgeOverhang;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.flipHeight = flipHeight;
    }

    @Override
    public double lifetimeSeconds() {
        return LIFETIME;
    }

    private double y(double worldY) {
        return flipHeight > 0 ? flipHeight - worldY : worldY;
    }

    @Override
    public List<FirePuff> puffs(double progress, double clock) {
        if (progress >= 1.0 || streams.isEmpty()) {
            return List.of();
        }
        PuffFrame frame = new PuffFrame();
        double half = width / 2.0;
        for (Stream stream : streams) {
            List<Point> path = stream.path();
            double total = 0;
            for (int i = 0; i + 1 < path.size(); i++) {
                total += segmentLength(path, i);
            }
            // the banks, the river and its hot middle: three passes, each a chain of circles along the middle line,
            // so a later circle of one pass never paints over an earlier circle of the next
            for (int pass = 0; pass < 3; pass++) {
                double walked = 0;
                for (int i = 0; i + 1 < path.size(); i++) {
                    Point a = path.get(i);
                    Point b = path.get(i + 1);
                    double length = segmentLength(path, i);
                    for (double s = 0; s < length; s += CHAIN_STEP) {
                        double t = s / length;
                        double x = a.x() + (b.x() - a.x()) * t;
                        double yy = a.y() + (b.y() - a.y()) * t;
                        double flicker = 0.5 + 0.5 * Math.sin(clock * 1.6 + (walked + s) * 0.045);
                        if (pass == 0) {
                            frame.add(FirePuff.Layer.SMOKE, x, y(yy), half + 5.0, 0.20f, 0.05f, 0.03f, 1.0);
                        } else if (pass == 1) {
                            frame.add(FirePuff.Layer.SMOKE, x, y(yy), half - 1.0, 0.70f, 0.12f, 0.03f, 1.0);
                        } else {
                            frame.add(FirePuff.Layer.SMOKE, x, y(yy), half * 0.66, 0.95f, 0.40f + 0.10f * (float) flicker, 0.06f, 1.0);
                        }
                    }
                    walked += length;
                }
            }
            // bright blobs flowing along the stream, and sparks over it
            for (double base = 0; base < total; base += BLOB_SPACING) {
                double at = (base + clock * FLOW_SPEED) % total;
                double[] p = pointAt(path, at);
                double pulse = 0.75 + 0.25 * Math.sin(clock * 3 + base);
                frame.add(FirePuff.Layer.BODY, p[0], y(p[1]), half * 0.7 * pulse, 1.0f, 0.55f, 0.08f, 0.32);
                frame.add(FirePuff.Layer.INNER, p[0], y(p[1]), half * 0.34 * pulse, 1.0f, 0.86f, 0.28f, 0.55);
                if (((int) (base / BLOB_SPACING)) % 3 == 0) {
                    double[] q = pointAt(path, (at + 11) % total);
                    frame.add(FirePuff.Layer.EMBER, q[0] + Math.sin(clock * 7 + base) * 5, y(q[1]) + Math.cos(clock * 5 + base) * 5,
                            1.7, 1.0f, 0.9f, 0.5f, 0.85);
                }
            }
            for (Bridge bridge : stream.bridges()) {
                bridge(frame, bridge);
            }
        }
        return frame.ordered();
    }

    /** A wooden bridge: planks across the stream between two rails, drawn as rows of circles. */
    private void bridge(PuffFrame frame, Bridge bridge) {
        double reach = width / 2.0 + bridgeOverhang;
        // flipping the world turns the flow angle too
        double angle = flipHeight > 0 ? -bridge.flowAngleRadians() : bridge.flowAngleRadians();
        double ax = Math.cos(angle);
        double ay = Math.sin(angle);
        double cx = bridge.x();
        double cy = y(bridge.y());
        int planks = (int) Math.round(bridge.length() / PLANK);
        for (int p = 0; p < planks; p++) {
            double along = -bridge.length() / 2.0 + (p + 0.5) * (bridge.length() / planks);
            float shade = (p % 2 == 0) ? 0.47f : 0.40f;
            int rows = Math.max(1, (int) Math.round(2 * reach / 4.0));
            for (int row = 0; row <= rows; row++) {
                double across = -reach + row * (2 * reach / rows);   // evenly from rail to rail
                double x = cx + ax * along - ay * across;
                double yy = cy + ay * along + ax * across;
                frame.add(FirePuff.Layer.DEBRIS, x, yy, 4.2, shade, shade * 0.62f, 0.2f, 1.0);
            }
        }
        for (int side = -1; side <= 1; side += 2) {   // the rails on the two long edges
            for (double along = -bridge.length() / 2.0; along <= bridge.length() / 2.0; along += 4.0) {
                double x = cx + ax * along - ay * side * reach;
                double yy = cy + ay * along + ax * side * reach;
                frame.add(FirePuff.Layer.DEBRIS, x, yy, 2.6, 0.24f, 0.14f, 0.07f, 1.0);
            }
        }
    }

    private static double segmentLength(List<Point> path, int i) {
        return Math.hypot(path.get(i + 1).x() - path.get(i).x(), path.get(i + 1).y() - path.get(i).y());
    }

    /** The point {@code distance} along the stream, in world space. */
    private static double[] pointAt(List<Point> path, double distance) {
        double left = distance;
        for (int i = 0; i + 1 < path.size(); i++) {
            double length = segmentLength(path, i);
            if (left <= length || i + 2 == path.size()) {
                double t = length == 0 ? 0 : Math.min(1, left / length);
                return new double[] {path.get(i).x() + (path.get(i + 1).x() - path.get(i).x()) * t,
                        path.get(i).y() + (path.get(i + 1).y() - path.get(i).y()) * t};
            }
            left -= length;
        }
        return new double[] {path.get(0).x(), path.get(0).y()};
    }

    @Override
    public double[] bounds() {
        return new double[] {0, 0, boardWidth, boardHeight};
    }
}
