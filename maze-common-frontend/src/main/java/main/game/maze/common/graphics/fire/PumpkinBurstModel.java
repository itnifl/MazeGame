package main.game.maze.common.graphics.fire;

import static main.game.maze.common.graphics.fire.PuffFrame.easeOutCubic;
import static main.game.maze.common.graphics.fire.PuffFrame.smoothstep;

import java.util.List;
import java.util.SplittableRandom;

/**
 * Turns a {@link PumpkinBurst} into the layered puffs to draw for one frame.
 *
 * <p>Frontend-neutral and side-effect free. Over the burst's life ({@code progress} 0 &rarr; 1):
 * <ul>
 *   <li>a white-hot flash fills the centre for the first few frames,</li>
 *   <li>a lumpy fireball (a core plus lobes) swells out, cools from yellow to red and turns to smoke,</li>
 *   <li>a shockwave ring races out to the splash radius,</li>
 *   <li>chunks of orange pumpkin rind, green stem bits and pale seeds fly out, slow down,
 *       swell as if arcing up and back down, then fade,</li>
 *   <li>glowing embers scatter and twinkle out.</li>
 * </ul>
 * Static scatter comes from {@link PumpkinBurst#seed()}; only flicker and tumble follow the clock.
 * Puffs are returned ordered by {@link FirePuff.Layer} (back to front).
 */
public final class PumpkinBurstModel {

    private static final double FLASH_PHASE = 0.12;
    private static final double SHOCKWAVE_PHASE = 0.3;
    /** Fraction of the life the debris spends in flight before it lands and fades. */
    private static final double DEBRIS_FLIGHT = 0.5;
    private static final int LOBES = 7;
    private static final int SHOCKWAVE_POINTS = 64;
    private static final int RIND_CHUNKS = 9;
    private static final int STEM_BITS = 3;
    private static final int SEEDS = 10;
    private static final int EMBERS = 16;

    private PumpkinBurstModel() {
    }

    /**
     * @param burst        the explosion
     * @param progress     life progress, 0 at impact and 1 when the burst is gone
     * @param clockSeconds a running clock used only for flicker and tumbling
     * @return puffs ordered back to front; empty when {@code progress >= 1}
     */
    public static List<FirePuff> puffs(PumpkinBurst burst, double progress, double clockSeconds) {
        if (burst == null || Double.isNaN(progress) || progress >= 1.0) {
            return List.of();
        }
        double p = Math.max(0.0, progress);
        PuffFrame frame = new PuffFrame();
        addFlash(frame, burst, p);
        addFireball(frame, burst, p, clockSeconds);
        addShockwave(frame, burst, p);
        addDebris(frame, burst, p, clockSeconds);
        addSeeds(frame, burst, p);
        addEmbers(frame, burst, p, clockSeconds);
        return frame.ordered();
    }

    private static void addFlash(PuffFrame frame, PumpkinBurst burst, double p) {
        double t = p / FLASH_PHASE;
        if (t >= 1.0) {
            return;
        }
        frame.add(FirePuff.Layer.CORE, burst.centerX(), burst.centerY(), burst.radius() * 0.6 * (1.0 - t),
                1.0f, 0.98f, 0.88f, 0.95 * (1.0 - t));
    }

    private static void addFireball(PuffFrame frame, PumpkinBurst burst, double p, double clock) {
        double r = burst.radius();
        double grow = easeOutCubic(Math.min(1.0, p / 0.25));
        frame.flame(burst.centerX(), burst.centerY(), r * (0.32 + 0.2 * grow), p * 0.9, clock, 0.0);

        SplittableRandom rnd = new SplittableRandom(burst.seed());
        for (int i = 0; i < LOBES; i++) {
            double angle = Math.PI * 2 * (i + 0.35 * rnd.nextDouble()) / LOBES;
            double distance = r * 0.45 * grow * (0.8 + 0.4 * rnd.nextDouble());
            double size = r * 0.26 * (0.8 + 0.4 * rnd.nextDouble());
            frame.flame(burst.centerX() + Math.cos(angle) * distance,
                    burst.centerY() + Math.sin(angle) * distance,
                    size, p, clock, angle * 3);
        }
    }

    private static void addShockwave(PuffFrame frame, PumpkinBurst burst, double p) {
        double life = p / SHOCKWAVE_PHASE;
        if (life >= 1.0) {
            return;
        }
        double ring = burst.radius() * (0.3 + easeOutCubic(life));
        for (int i = 0; i < SHOCKWAVE_POINTS; i++) {
            double angle = Math.PI * 2 * i / SHOCKWAVE_POINTS;
            frame.add(FirePuff.Layer.SHOCKWAVE,
                    burst.centerX() + Math.cos(angle) * ring,
                    burst.centerY() + Math.sin(angle) * ring,
                    burst.radius() * 0.05, 1.0f, 0.85f, 0.55f, 0.5 * (1.0 - life));
        }
    }

    /** Rind chunks (dark edge + bright highlight) and green stem bits, flying out and tumbling. */
    private static void addDebris(PuffFrame frame, PumpkinBurst burst, double p, double clock) {
        SplittableRandom rnd = new SplittableRandom(burst.seed() ^ 0x9E3779B97F4A7C15L);
        double flight = Math.min(1.0, p / DEBRIS_FLIGHT);
        double height = Math.sin(Math.PI * flight); // fake arc: chunks swell while "airborne"
        double alpha = emerge(p) * (1.0 - smoothstep(0.55, 0.95, p));
        for (int i = 0; i < RIND_CHUNKS + STEM_BITS; i++) {
            boolean stem = i >= RIND_CHUNKS;
            double angle = rnd.nextDouble() * Math.PI * 2;
            double distance = burst.radius() * (0.9 + 0.55 * rnd.nextDouble()) * easeOutCubic(flight);
            double tumble = 0.85 + 0.15 * Math.sin(clock * 20.0 + i * 1.7);
            double size = burst.radius() * (stem ? 0.06 : 0.07 + 0.05 * rnd.nextDouble())
                    * (1.0 + 0.6 * height) * tumble;
            double x = burst.centerX() + Math.cos(angle) * distance;
            double y = burst.centerY() + Math.sin(angle) * distance;
            if (stem) {
                frame.add(FirePuff.Layer.DEBRIS, x, y, size, 0.22f, 0.38f, 0.08f, alpha);
                frame.add(FirePuff.Layer.DEBRIS, x, y, size * 0.55, 0.36f, 0.55f, 0.14f, alpha);
            } else {
                frame.add(FirePuff.Layer.DEBRIS, x, y, size, 0.70f, 0.30f, 0.04f, alpha);
                frame.add(FirePuff.Layer.DEBRIS, x - size * 0.2, y - size * 0.2, size * 0.6,
                        0.98f, 0.55f, 0.10f, alpha);
            }
        }
    }

    private static void addSeeds(PuffFrame frame, PumpkinBurst burst, double p) {
        SplittableRandom rnd = new SplittableRandom(burst.seed() ^ 0x5DEECE66DL);
        double travel = easeOutCubic(Math.min(1.0, p / 0.45));
        double alpha = emerge(p) * (1.0 - smoothstep(0.5, 0.9, p));
        for (int i = 0; i < SEEDS; i++) {
            double angle = rnd.nextDouble() * Math.PI * 2;
            double distance = burst.radius() * (0.4 + 0.75 * rnd.nextDouble()) * travel;
            frame.add(FirePuff.Layer.DEBRIS,
                    burst.centerX() + Math.cos(angle) * distance,
                    burst.centerY() + Math.sin(angle) * distance,
                    burst.radius() * 0.035, 0.96f, 0.90f, 0.66f, alpha);
        }
    }

    /** Fragments appear out of the flash rather than sitting on top of it. */
    private static double emerge(double p) {
        return smoothstep(FLASH_PHASE * 0.3, FLASH_PHASE, p);
    }

    private static void addEmbers(PuffFrame frame, PumpkinBurst burst, double p, double clock) {
        SplittableRandom rnd = new SplittableRandom(burst.seed() * 31 + 7);
        double travel = easeOutCubic(Math.min(1.0, p * 1.6));
        for (int i = 0; i < EMBERS; i++) {
            double angle = rnd.nextDouble() * Math.PI * 2;
            double distance = burst.radius() * (0.4 + rnd.nextDouble()) * travel;
            double twinkle = 0.75 + 0.25 * Math.sin(clock * 23.0 + i);
            frame.add(FirePuff.Layer.EMBER,
                    burst.centerX() + Math.cos(angle) * distance,
                    burst.centerY() + Math.sin(angle) * distance,
                    burst.radius() * 0.05 * (1.0 - 0.5 * p), 1.0f, 0.65f, 0.2f, (1.0 - p) * twinkle);
        }
    }
}
