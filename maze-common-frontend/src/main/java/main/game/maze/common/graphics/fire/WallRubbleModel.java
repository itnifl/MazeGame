package main.game.maze.common.graphics.fire;

import static main.game.maze.common.graphics.fire.PuffFrame.easeOutCubic;
import static main.game.maze.common.graphics.fire.PuffFrame.smoothstep;

import java.util.List;
import java.util.SplittableRandom;

/**
 * Turns a collapsing {@link WallRubble} into the layered puffs to draw for one frame.
 *
 * <p>Frontend-neutral and side-effect free. Over the collapse ({@code progress} 0 &rarr; 1):
 * <ul>
 *   <li>chunks in the wall material break off along the wall line, tumble out to both sides,
 *       swell slightly while "falling", settle and fade,</li>
 *   <li>a dust cloud in the material dust colour billows up along the wall and settles,</li>
 *   <li>steel walls throw a few sparks.</li>
 * </ul>
 * Puffs are returned ordered by {@link FirePuff.Layer} (back to front).
 */
public final class WallRubbleModel {

    /** Fraction of the life the chunks spend tumbling before they settle. */
    private static final double FALL_PHASE = 0.45;
    private static final double BLOCK_SPACING = 8.0;
    private static final double GRAVEL_SPACING = 4.0;
    private static final double DUST_SPACING = 11.0;
    private static final int MIN_CHUNKS = 6;
    private static final int MAX_BLOCKS = 36;
    private static final int MAX_GRAVEL = 70;
    private static final int SPARKS = 8;

    private WallRubbleModel() {
    }

    /**
     * @param rubble       the collapsing wall
     * @param progress     life progress, 0 at collapse and 1 when the animation is over
     * @param clockSeconds a running clock used only for tumbling and spark twinkle
     * @return puffs ordered back to front; empty when {@code progress >= 1}
     */
    public static List<FirePuff> puffs(WallRubble rubble, double progress, double clockSeconds) {
        if (rubble == null || Double.isNaN(progress) || progress >= 1.0) {
            return List.of();
        }
        double p = Math.max(0.0, progress);
        PuffFrame frame = new PuffFrame();
        double length = rubble.length();
        double ux = length == 0 ? 1 : (rubble.bx() - rubble.ax()) / length;
        double uy = length == 0 ? 0 : (rubble.by() - rubble.ay()) / length;
        double nx = -uy;
        double ny = ux;
        addDust(frame, rubble, p, length, ux, uy);
        addChunks(frame, rubble, p, clockSeconds, length, ux, uy, nx, ny);
        if (rubble.material() == RubbleMaterial.STEEL) {
            addSparks(frame, rubble, p, clockSeconds, length, ux, uy, nx, ny);
        }
        return frame.ordered();
    }

    private static void addDust(PuffFrame frame, WallRubble rubble, double p, double length, double ux, double uy) {
        double alpha = smoothstep(0.0, 0.12, p) * (1.0 - smoothstep(0.4, 1.0, p)) * 0.42;
        double grow = easeOutCubic(Math.min(1.0, p / 0.6));
        float[] dust = rubble.material().dust();
        int puffs = Math.max(2, (int) Math.ceil(length / DUST_SPACING));
        SplittableRandom rnd = new SplittableRandom(rubble.seed() ^ 0x2545F4914F6CDD1DL);
        for (int i = 0; i < puffs; i++) {
            double along = length * (i + 0.5) / puffs + (rnd.nextDouble() - 0.5) * DUST_SPACING * 0.5;
            along = Math.max(0, Math.min(length, along));
            double radius = rubble.thickness() * (0.7 + 1.5 * grow) * (0.6 + 0.7 * rnd.nextDouble());
            double side = (rnd.nextDouble() * 2 - 1) * rubble.thickness() * 0.8 * grow;
            frame.add(FirePuff.Layer.SMOKE, rubble.ax() + ux * along - uy * side, rubble.ay() + uy * along + ux * side,
                    radius, dust[0], dust[1], dust[2], alpha * (0.7 + 0.3 * rnd.nextDouble()));
        }
    }

    /** Big blocks that start as the wall itself, plus gravel thrown further out. */
    private static void addChunks(PuffFrame frame, WallRubble rubble, double p, double clock, double length,
                                  double ux, double uy, double nx, double ny) {
        double fall = Math.min(1.0, p / FALL_PHASE);
        double travel = easeOutCubic(fall);
        double airborne = Math.sin(Math.PI * fall);
        double alpha = 1.0 - smoothstep(0.8, 1.0, p);
        double t = rubble.thickness();
        float[] base = rubble.material().chunk();
        float[] light = rubble.material().highlight();
        SplittableRandom rnd = new SplittableRandom(rubble.seed());

        int blocks = (int) Math.max(MIN_CHUNKS, Math.min(MAX_BLOCKS, Math.round(length / BLOCK_SPACING)));
        for (int i = 0; i < blocks; i++) {
            double along = length * (i + rnd.nextDouble()) / blocks;
            double side = (i % 2 == 0 ? 1 : -1) * (0.3 * t + rnd.nextDouble() * 1.6 * t);
            double drift = (rnd.nextDouble() - 0.5) * t * 0.8;
            double tumble = 0.88 + 0.12 * Math.sin(clock * 18.0 + i * 2.3);
            double size = t * (0.4 + 0.35 * rnd.nextDouble()) * (1.0 + 0.35 * airborne) * tumble;
            chunk(frame, rubble, along + drift * travel, side * travel, size, ux, uy, nx, ny, base, light, alpha);
        }

        int gravel = (int) Math.max(MIN_CHUNKS, Math.min(MAX_GRAVEL, Math.round(length / GRAVEL_SPACING)));
        double gravelTravel = easeOutCubic(Math.min(1.0, p / (FALL_PHASE * 0.8)));
        for (int i = 0; i < gravel; i++) {
            double along = length * rnd.nextDouble();
            double side = (rnd.nextBoolean() ? 1 : -1) * t * (0.4 + 2.6 * rnd.nextDouble());
            double size = t * (0.1 + 0.12 * rnd.nextDouble());
            frame.add(FirePuff.Layer.DEBRIS,
                    rubble.ax() + ux * along + nx * side * gravelTravel,
                    rubble.ay() + uy * along + ny * side * gravelTravel,
                    size, base[0], base[1], base[2], alpha);
        }
    }

    private static void chunk(PuffFrame frame, WallRubble rubble, double along, double side, double size,
                              double ux, double uy, double nx, double ny, float[] base, float[] light, double alpha) {
        double x = rubble.ax() + ux * along + nx * side;
        double y = rubble.ay() + uy * along + ny * side;
        frame.add(FirePuff.Layer.DEBRIS, x, y, size, base[0], base[1], base[2], alpha);
        frame.add(FirePuff.Layer.DEBRIS, x - size * 0.2, y - size * 0.2, size * 0.55,
                light[0], light[1], light[2], alpha);
    }

    private static void addSparks(PuffFrame frame, WallRubble rubble, double p, double clock, double length,
                                  double ux, double uy, double nx, double ny) {
        if (p >= 0.35) {
            return;
        }
        double life = p / 0.35;
        SplittableRandom rnd = new SplittableRandom(rubble.seed() * 13 + 5);
        for (int i = 0; i < SPARKS; i++) {
            double along = length * rnd.nextDouble();
            double side = (rnd.nextBoolean() ? 1 : -1) * rubble.thickness() * 2.5 * rnd.nextDouble() * easeOutCubic(life);
            double twinkle = 0.7 + 0.3 * Math.sin(clock * 30.0 + i);
            frame.add(FirePuff.Layer.EMBER,
                    rubble.ax() + ux * along + nx * side, rubble.ay() + uy * along + ny * side,
                    rubble.thickness() * 0.12, 1.0f, 0.8f, 0.35f, (1.0 - life) * twinkle);
        }
    }
}
