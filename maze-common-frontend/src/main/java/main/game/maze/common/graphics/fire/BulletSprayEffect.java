package main.game.maze.common.graphics.fire;

import java.util.List;

/**
 * A burst from a gatling gun: a spray of small bullets that leave the muzzle one after the other, fan out
 * in a narrow cone round the line of the shot and fly with short glowing tracer trails; where they land
 * they throw sparks. Unlike the laser beam there is no continuous line, only separate bullets.
 *
 * @param bullets how many bullets the spray has (a whole burst: 9; one enemy round: 3)
 */
public record BulletSprayEffect(double x1, double y1, double x2, double y2, int bullets) implements PuffEffect {

    static final double LIFETIME = 0.4;
    /** Half the opening angle of the cone, in radians (about 5 degrees). */
    private static final double SPREAD = Math.toRadians(5.0);
    /** Share of the life a bullet takes to fly to its target. */
    private static final double FLIGHT = 0.45;
    /** The last bullet leaves this far into the life. */
    private static final double STAGGER = 0.5;
    private static final double TRAIL_STEP = 0.05;
    private static final double SPARK_REACH = 9.0;

    public BulletSprayEffect {
        bullets = Math.max(1, bullets);
    }

    /** A whole burst of nine bullets. */
    public BulletSprayEffect(double x1, double y1, double x2, double y2) {
        this(x1, y1, x2, y2, 9);
    }

    @Override
    public double lifetimeSeconds() {
        return LIFETIME;
    }

    @Override
    public List<FirePuff> puffs(double progress, double clock) {
        if (Double.isNaN(progress) || progress >= 1) {
            return List.of();
        }
        double p = Math.max(0, progress);
        PuffFrame frame = new PuffFrame();
        double length = Math.hypot(x2 - x1, y2 - y1);
        double base = Math.atan2(y2 - y1, x2 - x1);
        // muzzle flash at the start of the burst
        double flash = 1 - Math.min(1, p / 0.25);
        frame.add(FirePuff.Layer.INNER, x1, y1, 9 * (0.5 + 0.5 * flash), 1.0f, 0.75f, 0.25f, 0.8 * flash);

        for (int i = 0; i < bullets; i++) {
            double angle = base + bulletAngle(i);
            double reach = length * (0.94 + 0.12 * unit(i * 2 + 1));
            double start = bullets == 1 ? 0 : STAGGER * i / (bullets - 1);
            double u = (p - start) / FLIGHT;
            double dirX = Math.cos(angle);
            double dirY = Math.sin(angle);
            if (u > 0 && u < 1) {
                frame.add(FirePuff.Layer.BODY, x1 + dirX * reach * u, y1 + dirY * reach * u, 4.5, 1.0f, 0.6f, 0.15f, 0.35);
                frame.add(FirePuff.Layer.CORE, x1 + dirX * reach * u, y1 + dirY * reach * u, 1.9, 1.0f, 0.95f, 0.7f, 1.0);
                for (int t = 1; t <= 3; t++) {
                    double back = Math.max(0, u - t * TRAIL_STEP);
                    frame.add(FirePuff.Layer.BODY, x1 + dirX * reach * back, y1 + dirY * reach * back,
                            3.2 - t * 0.6, 1.0f, 0.55f, 0.12f, 0.45 - t * 0.12);
                }
            } else if (u >= 1) {
                // landed: a few sparks that die away over the rest of the life
                double sparkAge = Math.min(1, (u - 1) * FLIGHT / Math.max(0.01, 1 - start - FLIGHT));
                double ex = x1 + dirX * reach;
                double ey = y1 + dirY * reach;
                for (int s = 0; s < 3; s++) {
                    double sparkAngle = Math.PI * 2 * (s + unit(i * 3 + s)) / 3;
                    double out = SPARK_REACH * sparkAge + 1;
                    frame.add(FirePuff.Layer.EMBER, ex + Math.cos(sparkAngle) * out, ey + Math.sin(sparkAngle) * out,
                            1.4, 1.0f, 0.7f, 0.3f, 1 - sparkAge);
                }
            }
        }
        return frame.ordered();
    }

    @Override
    public double[] bounds() {
        double length = Math.hypot(x2 - x1, y2 - y1);
        double pad = length * Math.sin(SPREAD) + length * 0.06 + SPARK_REACH + 12;
        return new double[] {Math.min(x1, x2) - pad, Math.min(y1, y2) - pad,
                Math.max(x1, x2) + pad, Math.max(y1, y2) + pad};
    }

    /** Where bullet {@code i} flies, relative to the line of the shot: spread over the cone, a little scattered. */
    private double bulletAngle(int i) {
        double lane = bullets == 1 ? 0 : (2.0 * i / (bullets - 1) - 1);
        // the middle bullet follows the line, the others fan out; the scatter keeps lanes from looking like a comb
        return SPREAD * (lane * 0.85 + (unit(i * 2) - 0.5) * 0.3 * Math.min(1, Math.abs(lane) * 2));
    }

    /** A fixed pseudo-random number in [0, 1) for {@code n}: same every time. */
    private static double unit(int n) {
        double x = Math.sin(n * 12.9898 + 4.1414) * 43758.5453;
        return x - Math.floor(x);
    }
}
