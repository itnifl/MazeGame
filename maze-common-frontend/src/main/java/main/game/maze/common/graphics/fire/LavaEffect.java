package main.game.maze.common.graphics.fire;

import java.util.ArrayList;
import java.util.List;

/**
 * The lava of a level, drawn to look like the real thing: a river of molten rock with an uneven, living edge (the width
 * breathes along the stream), a dark cooled bank, a red-orange body with a hot yellow middle whose bright veins flow
 * downstream, plates of dark crust that drift with the flow and leave glowing cracks between them, bubbles that swell and
 * pop, sparks and embers that rise and fade, wisps of smoke, and a warm glow thrown on the floor beside it. The wooden
 * bridges lie on top. It is a chain of circles along the middle line, so any frontend that can play a {@link PuffEffect}
 * can draw it. It lasts as long as the level and flows with the running clock; every detail is a function of the clock
 * and of the place, so the picture is the same in both frontends. Drawn <em>under</em> the walls.
 *
 * <p>The field is in world space (y-down); {@code flipHeight} &gt; 0 turns it over for a y-up screen
 * ({@code y' = flipHeight - y}), as the other level effects do.
 */
public final class LavaEffect implements PuffEffect {

    /** Practically endless: the level removes the effect, the clock never does. */
    static final double LIFETIME = 1.0e9;
    private static final double CHAIN_STEP = 6.0;
    private static final double HALO_STEP = 14.0;
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

    /**
     * How the lava looks, from the level model.
     *
     * @param flowSpeed pixels per second the lava flows
     * @param glow      0 to 1: how strongly it lights the floor beside it
     * @param crust     0 to 1: how much of the surface has cooled to dark crust
     */
    public record Look(double flowSpeed, double glow, double crust) {
        public static final Look DEFAULT = new Look(34.0, 0.65, 0.45);
    }

    private final List<Stream> streams;
    private final double width;
    private final double bridgeOverhang;
    private final Look look;
    private final double flipHeight;
    private final double boardWidth;
    private final double boardHeight;

    /**
     * @param width          width of every stream
     * @param bridgeOverhang how far a bridge reaches past the edge of the stream on each side
     */
    public LavaEffect(List<Stream> streams, double width, double bridgeOverhang, Look look, double boardWidth,
                      double boardHeight, double flipHeight) {
        this.streams = List.copyOf(streams);
        this.width = width;
        this.bridgeOverhang = bridgeOverhang;
        this.look = look == null ? Look.DEFAULT : look;
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

    /** Up on the screen, as a sign for the y it is drawn at. */
    private double up() {
        return flipHeight > 0 ? 1.0 : -1.0;
    }

    @Override
    public List<FirePuff> puffs(double progress, double clock) {
        if (progress >= 1.0 || streams.isEmpty()) {
            return List.of();
        }
        PuffFrame frame = new PuffFrame();
        int streamIndex = 0;
        for (Stream stream : streams) {
            drawStream(frame, stream, clock, streamIndex++);
            for (Bridge bridge : stream.bridges()) {
                bridge(frame, bridge);
            }
        }
        return frame.ordered();
    }

    // ---------------------------------------------------------------- one stream

    private void drawStream(PuffFrame frame, Stream stream, double clock, int index) {
        List<double[]> samples = samples(stream.path());   // {x, y, distance along}
        if (samples.isEmpty()) {
            return;
        }
        double total = samples.get(samples.size() - 1)[2];
        double half = width / 2.0;
        double seed = index * 37.0;
        double flow = look.flowSpeed();

        // the warm glow on the floor beside the stream (additive, soft, flickering a little)
        if (look.glow() > 0) {
            for (double s = 0; s <= total; s += HALO_STEP) {
                double[] p = at(samples, s);
                double flicker = 0.75 + 0.25 * noise(s * 0.09 - clock * 1.7 + seed);
                frame.add(FirePuff.Layer.BODY, p[0], y(p[1]), half * 2.3, 1.0f, 0.32f, 0.04f, 0.045 * look.glow() * flicker);
                frame.add(FirePuff.Layer.BODY, p[0], y(p[1]), half * 1.55, 1.0f, 0.42f, 0.06f, 0.06 * look.glow() * flicker);
            }
        }
        // three passes along the stream so a later circle never paints over an earlier pass: bank, body, hot middle
        for (int pass = 0; pass < 3; pass++) {
            for (double[] p : samples) {
                double s = p[2];
                // the width breathes along the stream and over time: an uneven, living edge
                double breathe = 0.88 + 0.13 * noise(s * 0.045 + seed) + 0.04 * Math.sin(clock * 0.9 + s * 0.02);
                double r = half * breathe;
                if (pass == 0) {
                    frame.add(FirePuff.Layer.SMOKE, p[0], y(p[1]), r + 7.0, 0.08f, 0.06f, 0.055f, 1.0);   // cooled basalt bank
                    frame.add(FirePuff.Layer.SMOKE, p[0], y(p[1]), r + 3.2, 0.30f, 0.07f, 0.03f, 1.0);    // the bank glows red at the edge
                } else if (pass == 1) {
                    frame.add(FirePuff.Layer.SMOKE, p[0], y(p[1]), r, 0.78f, 0.16f, 0.03f, 1.0);           // the molten body
                    frame.add(FirePuff.Layer.SMOKE, p[0], y(p[1]), r * 0.74, 0.95f, 0.36f, 0.05f, 1.0);
                } else {
                    // hot veins that flow downstream: the bright middle swells and shrinks as the pattern travels
                    double vein = 0.5 + 0.5 * noise(s * 0.06 - clock * flow * 0.05 + seed);
                    frame.add(FirePuff.Layer.SMOKE, p[0], y(p[1]), r * (0.24 + 0.22 * vein), 1.0f, 0.52f + 0.16f * (float) vein, 0.10f, 1.0);
                }
            }
        }
        // plates of cooled crust drift with the flow; the molten body shows as glowing cracks between them
        int plates = (int) Math.round(look.crust() * total / 16.0);
        for (int k = 0; k < plates; k++) {
            double s = (k * (total / Math.max(1, plates)) + clock * flow * 0.55 + hash(k + index * 101) * 40.0) % total;
            double[] p = at(samples, s);
            double side = (hash(k * 7 + 3) - 0.5) * half * 1.15;
            double radius = 5.0 + 6.5 * hash(k * 13 + 5);
            double nx = -p[4];
            double ny = p[3];
            double px = p[0] + nx * side;
            double py = p[1] + ny * side;
            float shade = 0.07f + 0.08f * (float) hash(k * 5 + 1);
            frame.add(FirePuff.Layer.SMOKE, px, y(py), radius, shade + 0.03f, shade * 0.55f, shade * 0.4f, 0.96);
            frame.add(FirePuff.Layer.SMOKE, px + radius * 0.55, y(py + radius * 0.2), radius * 0.62, shade, shade * 0.5f, shade * 0.35f, 0.96);
            frame.add(FirePuff.Layer.SMOKE, px - radius * 0.5, y(py - radius * 0.3), radius * 0.55, shade, shade * 0.5f, shade * 0.35f, 0.96);
        }
        // bubbles swell and pop; sparks rise and fade; smoke drifts up
        int bubbles = (int) (total / 55.0);
        for (int k = 0; k < bubbles; k++) {
            double period = 1.8 + 1.6 * hash(k + 17);
            double life = ((clock / period) + hash(k * 3 + 11)) % 1.0;
            double[] p = at(samples, hash(k * 19 + 2) * total);
            double side = (hash(k * 23 + 9) - 0.5) * half * 0.9;
            double bx = p[0] - p[4] * side;
            double by = p[1] + p[3] * side;
            if (life < 0.8) {
                double swell = 1.5 + 5.5 * (life / 0.8);
                frame.add(FirePuff.Layer.RIM, bx, y(by), swell + 1.2, 1.0f, 0.55f, 0.1f, 0.25);
                frame.add(FirePuff.Layer.INNER, bx - swell * 0.25, y(by) + up() * swell * 0.25, swell * 0.35, 1.0f, 0.9f, 0.55f, 0.55);
            } else {
                double burst = (life - 0.8) / 0.2;
                frame.add(FirePuff.Layer.SHOCKWAVE, bx, y(by), 7.0 + 6.0 * burst, 1.0f, 0.7f, 0.25f, 0.35 * (1 - burst));
                frame.add(FirePuff.Layer.EMBER, bx, y(by) + up() * 6 * burst, 1.8, 1.0f, 0.85f, 0.4f, 0.9 * (1 - burst));
            }
        }
        int sparks = (int) (total / 40.0);
        for (int k = 0; k < sparks; k++) {
            double period = 1.4 + 1.2 * hash(k + 41);
            double life = ((clock / period) + hash(k * 5 + 29)) % 1.0;
            double[] p = at(samples, hash(k * 29 + 4) * total);
            double drift = Math.sin(clock * 2.1 + k) * 5.0 * life;
            frame.add(FirePuff.Layer.EMBER, p[0] + drift, y(p[1]) + up() * (6 + 34 * life), 1.3 + 0.9 * (1 - life),
                    1.0f, 0.55f + 0.35f * (float) (1 - life), 0.15f, 0.95 * (1 - life));
        }
        int wisps = (int) (total / 90.0);
        for (int k = 0; k < wisps; k++) {
            double period = 3.4 + 2.4 * hash(k + 61);
            double life = ((clock / period) + hash(k * 7 + 43)) % 1.0;
            double[] p = at(samples, hash(k * 31 + 8) * total);
            double sway = Math.sin(clock * 0.8 + k * 1.9) * 9.0 * life;
            frame.add(FirePuff.Layer.SMOKE, p[0] + sway, y(p[1]) + up() * (10 + 46 * life), 6.0 + 11.0 * life, 0.16f, 0.14f, 0.13f, 0.20 * (1 - life) * Math.min(1, life * 6));
        }
    }

    /** The points of the middle line every {@link #CHAIN_STEP} pixels: x, y, distance along, and the unit direction. */
    private static List<double[]> samples(List<Point> path) {
        List<double[]> samples = new ArrayList<>();
        double walked = 0;
        for (int i = 0; i + 1 < path.size(); i++) {
            Point a = path.get(i);
            Point b = path.get(i + 1);
            double length = Math.hypot(b.x() - a.x(), b.y() - a.y());
            if (length == 0) {
                continue;
            }
            double dx = (b.x() - a.x()) / length;
            double dy = (b.y() - a.y()) / length;
            for (double s = 0; s < length; s += CHAIN_STEP) {
                samples.add(new double[] {a.x() + dx * s, a.y() + dy * s, walked + s, dx, dy});
            }
            walked += length;
        }
        return samples;
    }

    /** The sample at this distance along the stream (the nearest one). */
    private static double[] at(List<double[]> samples, double distance) {
        // the samples restart their spacing on every segment, so look the distance up in what was recorded (binary search)
        int low = 0;
        int high = samples.size() - 1;
        while (low < high) {
            int mid = (low + high) >>> 1;
            if (samples.get(mid)[2] < distance) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        if (low > 0 && Math.abs(samples.get(low - 1)[2] - distance) <= Math.abs(samples.get(low)[2] - distance)) {
            low--;
        }
        return samples.get(low);
    }

    /** Smooth pseudo-random wobble between about -1 and 1, the same everywhere for the same input. */
    static double noise(double x) {
        return (Math.sin(x) + 0.6 * Math.sin(2.31 * x + 1.7) + 0.35 * Math.sin(4.77 * x + 4.1)) / 1.95;
    }

    /** A fixed pseudo-random number from 0 to 1 for this whole number. */
    static double hash(int k) {
        double v = Math.sin(k * 12.9898 + 78.233) * 43758.5453;
        return v - Math.floor(v);
    }

    // ---------------------------------------------------------------- bridges

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

    @Override
    public double[] bounds() {
        return new double[] {0, 0, boardWidth, boardHeight};
    }
}
