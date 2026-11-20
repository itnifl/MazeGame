package main.game.maze.common.graphics.fire;

import java.util.List;

/**
 * One laser rifle shot: a bright red beam from the muzzle to whatever stopped it, with a glow
 * and a spray of sparks at the hit point. Over in a fraction of a second.
 */
public record LaserBeamEffect(double x1, double y1, double x2, double y2) implements PuffEffect {

    static final double LIFETIME = 0.18;
    private static final double SPACING = 5.0;
    private static final double GLOW = 6.0;
    private static final double SPARK_REACH = 10.0;

    @Override
    public double lifetimeSeconds() {
        return LIFETIME;
    }

    @Override
    public List<FirePuff> puffs(double progress, double clock) {
        if (Double.isNaN(progress) || progress >= 1) {
            return List.of();
        }
        double fade = 1 - Math.max(0, progress);
        PuffFrame frame = new PuffFrame();
        double length = Math.hypot(x2 - x1, y2 - y1);
        int steps = Math.max(1, (int) Math.ceil(length / SPACING));
        for (int i = 0; i <= steps; i++) {
            double t = i / (double) steps;
            double x = x1 + (x2 - x1) * t;
            double y = y1 + (y2 - y1) * t;
            double flicker = 0.85 + 0.15 * Math.sin(clock * 60 + i);
            frame.add(FirePuff.Layer.BODY, x, y, GLOW * (0.6 + 0.4 * fade), 1.0f, 0.12f, 0.12f, 0.35 * fade * flicker);
            frame.add(FirePuff.Layer.CORE, x, y, 2.2, 1.0f, 0.85f, 0.85f, 0.95 * fade);
        }
        for (int i = 0; i < 6; i++) {
            double angle = Math.PI * 2 * i / 6 + clock * 9;
            double reach = SPARK_REACH * Math.max(0, progress) + 2;
            frame.add(FirePuff.Layer.EMBER, x2 + Math.cos(angle) * reach, y2 + Math.sin(angle) * reach,
                    1.6, 1.0f, 0.6f, 0.3f, fade);
        }
        return frame.ordered();
    }

    @Override
    public double[] bounds() {
        double pad = Math.max(GLOW, SPARK_REACH + 2 + 1.6);
        return new double[] {Math.min(x1, x2) - pad, Math.min(y1, y2) - pad,
                Math.max(x1, x2) + pad, Math.max(y1, y2) + pad};
    }
}
