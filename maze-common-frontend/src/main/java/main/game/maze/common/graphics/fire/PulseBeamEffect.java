package main.game.maze.common.graphics.fire;

import java.util.List;

/**
 * The beam of the pulse beam cannon: a huge, blinding bolt, far thicker than the laser, with a white-hot core, a cyan
 * glow and a wide blue halo along the whole line, a flash at the muzzle, a shockwave and a burst of sparks where it
 * lands, and sparks that fly off the beam. It swells in an instant, holds, and fades. Frontend-neutral, like the other
 * effects.
 */
public record PulseBeamEffect(double x1, double y1, double x2, double y2) implements PuffEffect {

    static final double LIFETIME = 0.7;
    private static final double STEP = 5.0;

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
        // the beam swells in the first tenth of its life, then thins away
        double swell = p < 0.1 ? p / 0.1 : 1.0 - (p - 0.1) / 0.9;
        double strength = Math.max(0, swell);
        double length = Math.hypot(x2 - x1, y2 - y1);
        PuffFrame frame = new PuffFrame();
        double dx = length == 0 ? 1 : (x2 - x1) / length;
        double dy = length == 0 ? 0 : (y2 - y1) / length;
        for (double s = 0; s <= length; s += STEP) {
            double x = x1 + dx * s;
            double y = y1 + dy * s;
            double ripple = 1.0 + 0.12 * Math.sin(s * 0.18 - clock * 40);
            frame.add(FirePuff.Layer.BODY, x, y, 24 * strength * ripple, 0.2f, 0.45f, 1.0f, 0.22 * strength);
            frame.add(FirePuff.Layer.INNER, x, y, 13 * strength * ripple, 0.45f, 0.9f, 1.0f, 0.55 * strength);
            frame.add(FirePuff.Layer.CORE, x, y, 6.5 * strength, 1.0f, 1.0f, 1.0f, 0.95 * strength);
        }
        // the flash at the muzzle and the blast where the beam lands
        double flash = Math.max(0, 1 - p / 0.35);
        frame.add(FirePuff.Layer.BODY, x1, y1, 46 * flash, 0.5f, 0.8f, 1.0f, 0.5 * flash);
        frame.add(FirePuff.Layer.CORE, x1, y1, 22 * flash, 1.0f, 1.0f, 1.0f, 0.9 * flash);
        frame.add(FirePuff.Layer.BODY, x2, y2, 58 * flash, 0.4f, 0.75f, 1.0f, 0.5 * flash);
        frame.add(FirePuff.Layer.CORE, x2, y2, 26 * flash, 1.0f, 1.0f, 1.0f, 0.95 * flash);
        double ring = 10 + 90 * p;
        frame.add(FirePuff.Layer.SHOCKWAVE, x2, y2, ring, 0.6f, 0.9f, 1.0f, 0.4 * (1 - p));
        frame.add(FirePuff.Layer.SHOCKWAVE, x1, y1, 8 + 50 * p, 0.6f, 0.9f, 1.0f, 0.3 * (1 - p));
        // sparks flying off the beam and out of the impact
        for (int i = 0; i < 26; i++) {
            double along = length * (((i * 0.6180339) % 1.0));
            double side = ((i * 7) % 11 - 5) * (4 + 26 * p);
            double lift = (1 + (i % 4)) * 14 * p;
            frame.add(FirePuff.Layer.EMBER, x1 + dx * along - dy * (side + lift), y1 + dy * along + dx * (side + lift),
                    1.4 + (i % 3) * 0.5, 0.8f, 0.95f, 1.0f, 0.9 * (1 - p));
        }
        for (int i = 0; i < 14; i++) {
            double a = i * 0.9 + 0.3;
            double r = (10 + (i % 5) * 9) * (0.4 + 1.6 * p);
            frame.add(FirePuff.Layer.EMBER, x2 + Math.cos(a) * r, y2 + Math.sin(a) * r, 2.0, 1.0f, 1.0f, 1.0f, 0.9 * (1 - p));
        }
        return frame.ordered();
    }

    @Override
    public double[] bounds() {
        double pad = 110;
        return new double[] {Math.min(x1, x2) - pad, Math.min(y1, y2) - pad, Math.max(x1, x2) + pad, Math.max(y1, y2) + pad};
    }
}
