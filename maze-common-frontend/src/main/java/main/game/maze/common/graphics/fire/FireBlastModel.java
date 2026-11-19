package main.game.maze.common.graphics.fire;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.SplittableRandom;

/**
 * Turns a {@link FireBlast} into the layered fire puffs to draw for one frame.
 *
 * <p>Frontend-neutral and side-effect free: frontends only draw the returned circles.
 * Over the blast's life ({@code progress} 0 &rarr; 1):
 * <ul>
 *   <li>the flame fronts shoot out along each arm during the first {@value #FRONT_PHASE} of the life,</li>
 *   <li>puffs billow (swell), flicker with {@code clockSeconds}, and wobble sideways inside the corridor,</li>
 *   <li>the colour cools from a white-hot core through yellow and orange to a red rim,</li>
 *   <li>smoke builds up as the fire dies, and a shockwave ring and embers burst from the centre.</li>
 * </ul>
 * Static per-puff jitter comes from {@link FireBlast#seed()}, so one blast looks stable from
 * frame to frame and only the clock-driven flicker moves.
 *
 * <p>Puffs are returned ordered by {@link FirePuff.Layer} (back to front).
 */
public final class FireBlastModel {

    /** Fraction of the life during which flame fronts travel out to full reach. */
    static final double FRONT_PHASE = 0.22;
    /** Spacing between puffs along an arm, as a fraction of the corridor width. */
    static final double PUFF_SPACING = 0.32;
    /** Maximum sideways wobble of a puff centre, as a fraction of the corridor width. */
    static final double MAX_LATERAL = 0.16;
    private static final int SHOCKWAVE_POINTS = 28;
    private static final int EMBERS = 18;

    private static final double[][] ARM_DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    private FireBlastModel() {
    }

    /**
     * @param blast        the detonation
     * @param progress     life progress, 0 at detonation and 1 when the blast is gone
     * @param clockSeconds a running clock used only for flicker
     * @return puffs ordered back to front; empty when {@code progress >= 1}
     */
    public static List<FirePuff> puffs(FireBlast blast, double progress, double clockSeconds) {
        if (blast == null || progress >= 1.0 || Double.isNaN(progress)) {
            return List.of();
        }
        double p = Math.max(0.0, progress);
        Map<FirePuff.Layer, List<FirePuff>> layers = new EnumMap<>(FirePuff.Layer.class);
        for (FirePuff.Layer layer : FirePuff.Layer.values()) {
            layers.put(layer, new ArrayList<>());
        }

        double width = blast.corridorWidth();
        double reach = easeOutCubic(Math.min(1.0, p / FRONT_PHASE));
        double[] arms = {blast.armPlusX(), blast.armMinusX(), blast.armPlusY(), blast.armMinusY()};

        for (int a = 0; a < ARM_DIRS.length; a++) {
            SplittableRandom rnd = new SplittableRandom(blast.seed() * 4 + a);
            double length = arms[a] * reach;
            if (length <= 0) {
                continue;
            }
            double dx = ARM_DIRS[a][0];
            double dy = ARM_DIRS[a][1];
            int count = (int) Math.ceil(length / (width * PUFF_SPACING));
            for (int i = 1; i <= count; i++) {
                double along = Math.min(length, i * width * PUFF_SPACING);
                double phase = rnd.nextDouble() * Math.PI * 2;
                double staticJitter = (rnd.nextDouble() * 2 - 1) * 0.5;
                double lateral = width * MAX_LATERAL
                        * clamp(staticJitter + 0.5 * Math.sin(clockSeconds * 13.0 + phase), -1, 1);
                double x = blast.centerX() + dx * along - dy * lateral;
                double y = blast.centerY() + dy * along + dx * lateral;
                // the travelling front billows larger; puffs taper slightly towards the far end
                boolean front = i == count && reach < 1.0;
                double taper = 1.0 - 0.25 * Math.pow(along / Math.max(arms[a], 1e-6), 2);
                double radius = width * 0.5 * (0.85 + 0.3 * rnd.nextDouble()) * taper * (front ? 1.25 : 1.0);
                addFlame(layers, x, y, radius, p, clockSeconds, phase);
            }
        }

        // central fireball, bigger and longer-lived than the arm puffs
        double coreRadius = width * (0.85 + 0.45 * easeOutCubic(Math.min(1.0, p / 0.3)));
        addFlame(layers, blast.centerX(), blast.centerY(), coreRadius, p * 0.85, clockSeconds, 0.0);

        addShockwave(layers, blast, p);
        addEmbers(layers, blast, p, clockSeconds);

        List<FirePuff> ordered = new ArrayList<>();
        for (FirePuff.Layer layer : FirePuff.Layer.values()) {
            ordered.addAll(layers.get(layer));
        }
        return ordered;
    }

    /** Adds one billowing fire puff as concentric smoke, rim, body, inner and core circles. */
    private static void addFlame(Map<FirePuff.Layer, List<FirePuff>> layers,
                                 double x, double y, double baseRadius,
                                 double p, double clock, double phase) {
        double swell = 0.8 + 0.4 * Math.sin(Math.PI * Math.min(1.0, p * 1.3));
        double flicker = 1.0 + 0.12 * Math.sin(clock * 17.0 + phase) + 0.06 * Math.sin(clock * 31.0 + phase * 2);
        double r = baseRadius * swell * flicker;

        double fire = 1.0 - smoothstep(0.55, 1.0, p);
        double heat = 1.0 - smoothstep(0.0, 0.75, p);
        double whiteHot = 1.0 - smoothstep(0.0, 0.4, p);
        double smoke = smoothstep(0.15, 0.6, p) * (1.0 - smoothstep(0.7, 1.0, p));

        // additive layers overlap ~3x along an arm, so per-puff alphas stay low to avoid flat saturation
        add(layers, FirePuff.Layer.SMOKE, x, y, r * (1.25 + 0.5 * p), 0.12f, 0.10f, 0.10f, 0.65 * smoke);
        add(layers, FirePuff.Layer.RIM, x, y, r * 1.12, 0.78f, 0.10f, 0.02f, 0.45 * fire);
        add(layers, FirePuff.Layer.BODY, x, y, r * (0.75 + 0.15 * heat), 1.0f, 0.42f, 0.04f, 0.42 * fire);
        add(layers, FirePuff.Layer.INNER, x, y, r * 0.55 * (0.6 + 0.4 * heat), 1.0f, 0.80f, 0.22f, 0.45 * fire * heat);
        add(layers, FirePuff.Layer.CORE, x, y, r * 0.28, 1.0f, 0.97f, 0.85f, 0.85 * whiteHot);
    }

    private static void addShockwave(Map<FirePuff.Layer, List<FirePuff>> layers, FireBlast blast, double p) {
        double life = p / 0.4;
        if (life >= 1.0) {
            return;
        }
        double width = blast.corridorWidth();
        double ringRadius = width * 0.6 + easeOutCubic(life) * width * 1.8;
        double alpha = 0.5 * (1.0 - life);
        for (int i = 0; i < SHOCKWAVE_POINTS; i++) {
            double angle = Math.PI * 2 * i / SHOCKWAVE_POINTS;
            add(layers, FirePuff.Layer.SHOCKWAVE,
                    blast.centerX() + Math.cos(angle) * ringRadius,
                    blast.centerY() + Math.sin(angle) * ringRadius,
                    width * 0.07, 1.0f, 0.90f, 0.62f, alpha);
        }
    }

    private static void addEmbers(Map<FirePuff.Layer, List<FirePuff>> layers, FireBlast blast,
                                  double p, double clock) {
        SplittableRandom rnd = new SplittableRandom(blast.seed() ^ 0x5DEECE66DL);
        double width = blast.corridorWidth();
        double maxDistance = Math.min(blast.maxArm() + width * 0.5, width * 2.6);
        double travel = easeOutCubic(Math.min(1.0, p * 1.4));
        for (int i = 0; i < EMBERS; i++) {
            double angle = rnd.nextDouble() * Math.PI * 2;
            double distance = (0.3 + 0.7 * rnd.nextDouble()) * maxDistance * travel;
            double twinkle = 0.75 + 0.25 * Math.sin(clock * 23.0 + i);
            add(layers, FirePuff.Layer.EMBER,
                    blast.centerX() + Math.cos(angle) * distance,
                    blast.centerY() + Math.sin(angle) * distance,
                    width * 0.08 * (1.0 - 0.5 * p), 1.0f, 0.72f, 0.25f, (1.0 - p) * twinkle);
        }
    }

    private static void add(Map<FirePuff.Layer, List<FirePuff>> layers, FirePuff.Layer layer,
                            double x, double y, double radius,
                            float red, float green, float blue, double alpha) {
        float a = (float) clamp(alpha, 0, 1);
        if (a <= 0.004f || radius <= 0) {
            return;
        }
        layers.get(layer).add(new FirePuff(layer, x, y, radius, red, green, blue, a));
    }

    static double easeOutCubic(double t) {
        double inv = 1.0 - clamp(t, 0, 1);
        return 1.0 - inv * inv * inv;
    }

    static double smoothstep(double edge0, double edge1, double x) {
        double t = clamp((x - edge0) / (edge1 - edge0), 0, 1);
        return t * t * (3 - 2 * t);
    }

    private static double clamp(double v, double lo, double hi) {
        return Math.max(lo, Math.min(hi, v));
    }
}
