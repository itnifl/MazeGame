package main.game.maze.common.graphics.fire;

import static main.game.maze.common.graphics.fire.PuffFrame.easeOutCubic;

import java.util.List;
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
        PuffFrame frame = new PuffFrame();

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
                        * PuffFrame.clamp(staticJitter + 0.5 * Math.sin(clockSeconds * 13.0 + phase), -1, 1);
                double x = blast.centerX() + dx * along - dy * lateral;
                double y = blast.centerY() + dy * along + dx * lateral;
                // the travelling front billows larger; puffs taper slightly towards the far end
                boolean front = i == count && reach < 1.0;
                double taper = 1.0 - 0.25 * Math.pow(along / Math.max(arms[a], 1e-6), 2);
                double radius = width * 0.5 * (0.85 + 0.3 * rnd.nextDouble()) * taper * (front ? 1.25 : 1.0);
                frame.flame(x, y, radius, p, clockSeconds, phase);
            }
        }

        // central fireball, bigger and longer-lived than the arm puffs
        double coreRadius = width * (0.85 + 0.45 * easeOutCubic(Math.min(1.0, p / 0.3)));
        frame.flame(blast.centerX(), blast.centerY(), coreRadius, p * 0.85, clockSeconds, 0.0);

        addShockwave(frame, blast, p);
        addEmbers(frame, blast, p, clockSeconds);
        return frame.ordered();
    }

    private static void addShockwave(PuffFrame frame, FireBlast blast, double p) {
        double life = p / 0.4;
        if (life >= 1.0) {
            return;
        }
        double width = blast.corridorWidth();
        double ringRadius = width * 0.6 + easeOutCubic(life) * width * 1.8;
        double alpha = 0.5 * (1.0 - life);
        for (int i = 0; i < SHOCKWAVE_POINTS; i++) {
            double angle = Math.PI * 2 * i / SHOCKWAVE_POINTS;
            frame.add(FirePuff.Layer.SHOCKWAVE,
                    blast.centerX() + Math.cos(angle) * ringRadius,
                    blast.centerY() + Math.sin(angle) * ringRadius,
                    width * 0.07, 1.0f, 0.90f, 0.62f, alpha);
        }
    }

    private static void addEmbers(PuffFrame frame, FireBlast blast, double p, double clock) {
        SplittableRandom rnd = new SplittableRandom(blast.seed() ^ 0x5DEECE66DL);
        double width = blast.corridorWidth();
        double maxDistance = Math.min(blast.maxArm() + width * 0.5, width * 2.6);
        double travel = easeOutCubic(Math.min(1.0, p * 1.4));
        for (int i = 0; i < EMBERS; i++) {
            double angle = rnd.nextDouble() * Math.PI * 2;
            double distance = (0.3 + 0.7 * rnd.nextDouble()) * maxDistance * travel;
            double twinkle = 0.75 + 0.25 * Math.sin(clock * 23.0 + i);
            frame.add(FirePuff.Layer.EMBER,
                    blast.centerX() + Math.cos(angle) * distance,
                    blast.centerY() + Math.sin(angle) * distance,
                    width * 0.08 * (1.0 - 0.5 * p), 1.0f, 0.72f, 0.25f, (1.0 - p) * twinkle);
        }
    }
}
