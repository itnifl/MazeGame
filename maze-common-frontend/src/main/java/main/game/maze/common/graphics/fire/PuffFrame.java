package main.game.maze.common.graphics.fire;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * One frame of {@link FirePuff}s being built, grouped by layer. Shared by the explosion
 * models so they draw flames the same way and return puffs ordered back to front.
 */
final class PuffFrame {

    private final Map<FirePuff.Layer, List<FirePuff>> layers = new EnumMap<>(FirePuff.Layer.class);

    PuffFrame() {
        for (FirePuff.Layer layer : FirePuff.Layer.values()) {
            layers.put(layer, new ArrayList<>());
        }
    }

    /** Adds one circle; nearly invisible or zero-size circles are skipped. */
    void add(FirePuff.Layer layer, double x, double y, double radius,
             float red, float green, float blue, double alpha) {
        float a = (float) clamp(alpha, 0, 1);
        if (a <= 0.004f || radius <= 0) {
            return;
        }
        layers.get(layer).add(new FirePuff(layer, x, y, radius, red, green, blue, a));
    }

    /**
     * Adds one billowing fire puff as concentric smoke, rim, body, inner and core circles.
     * {@code p} is the puff's life progress: it swells, cools from white-hot to red and
     * turns to smoke as {@code p} goes from 0 to 1.
     */
    void flame(double x, double y, double baseRadius, double p, double clock, double phase) {
        double swell = 0.8 + 0.4 * Math.sin(Math.PI * Math.min(1.0, p * 1.3));
        double flicker = 1.0 + 0.12 * Math.sin(clock * 17.0 + phase) + 0.06 * Math.sin(clock * 31.0 + phase * 2);
        double r = baseRadius * swell * flicker;

        double fire = 1.0 - smoothstep(0.55, 1.0, p);
        double heat = 1.0 - smoothstep(0.0, 0.75, p);
        double whiteHot = 1.0 - smoothstep(0.0, 0.4, p);
        double smoke = smoothstep(0.15, 0.6, p) * (1.0 - smoothstep(0.7, 1.0, p));

        // additive layers overlap ~3x, so per-puff alphas stay low to avoid flat saturation
        add(FirePuff.Layer.SMOKE, x, y, r * (1.25 + 0.5 * p), 0.12f, 0.10f, 0.10f, 0.65 * smoke);
        add(FirePuff.Layer.RIM, x, y, r * 1.12, 0.78f, 0.10f, 0.02f, 0.45 * fire);
        add(FirePuff.Layer.BODY, x, y, r * (0.75 + 0.15 * heat), 1.0f, 0.42f, 0.04f, 0.42 * fire);
        add(FirePuff.Layer.INNER, x, y, r * 0.55 * (0.6 + 0.4 * heat), 1.0f, 0.80f, 0.22f, 0.45 * fire * heat);
        add(FirePuff.Layer.CORE, x, y, r * 0.28, 1.0f, 0.97f, 0.85f, 0.85 * whiteHot);
    }

    /** The puffs ordered by {@link FirePuff.Layer}, back to front. */
    List<FirePuff> ordered() {
        List<FirePuff> ordered = new ArrayList<>();
        for (FirePuff.Layer layer : FirePuff.Layer.values()) {
            ordered.addAll(layers.get(layer));
        }
        return ordered;
    }

    static double easeOutCubic(double t) {
        double inv = 1.0 - clamp(t, 0, 1);
        return 1.0 - inv * inv * inv;
    }

    static double smoothstep(double edge0, double edge1, double x) {
        double t = clamp((x - edge0) / (edge1 - edge0), 0, 1);
        return t * t * (3 - 2 * t);
    }

    static double clamp(double v, double lo, double hi) {
        return Math.max(lo, Math.min(hi, v));
    }
}
