package main.game.maze.common.graphics.fire;

import static main.game.maze.common.graphics.fire.PuffFrame.easeOutCubic;

import java.util.List;
import java.util.SplittableRandom;

/**
 * How an enemy reacts to being hurt, scaled by the damage taken ({@link #severity}).
 * <ul>
 *   <li>{@link Kind#SHOT} (laser): blood spurts out along the shot and the enemy jolts back a
 *       little.</li>
 *   <li>{@link Kind#BLOW} (sledgehammer): a white impact flash and shock ring, dizzy stars and
 *       dust, and the enemy staggers heavily.</li>
 * </ul>
 * {@link #spriteOffset} is how far to draw the enemy sprite from its position during the reaction.
 *
 * @param dirX,dirY direction the hit came from towards the enemy (caller's coordinates)
 * @param severity  0..1, from {@link #severity(int, int)}
 */
public record HitReactionEffect(Kind kind, double x, double y, double dirX, double dirY,
                                double severity, long seed) implements PuffEffect {

    public enum Kind { SHOT, BLOW }

    static final double SHOT_LIFETIME = 0.4;
    static final double BLOW_LIFETIME = 0.55;
    private static final double SHOT_REACH = 26;
    private static final double BLOW_REACH = 34;

    /** Damage as a share of the enemy's maximum health, 0..1. */
    public static double severity(int damage, int maxHitPoints) {
        return Math.max(0, Math.min(1, damage / (double) Math.max(1, maxHitPoints)));
    }

    @Override
    public double lifetimeSeconds() {
        return kind == Kind.SHOT ? SHOT_LIFETIME : BLOW_LIFETIME;
    }

    @Override
    public List<FirePuff> puffs(double progress, double clock) {
        if (Double.isNaN(progress) || progress >= 1) {
            return List.of();
        }
        double p = Math.max(0, progress);
        PuffFrame frame = new PuffFrame();
        double s = Math.max(0.15, Math.min(1, severity));
        double len = Math.max(1e-9, Math.hypot(dirX, dirY));
        double ux = dirX / len;
        double uy = dirY / len;
        SplittableRandom rnd = new SplittableRandom(seed);
        if (kind == Kind.SHOT) {
            int drops = 4 + (int) Math.round(10 * s);
            for (int i = 0; i < drops; i++) {
                double spread = (rnd.nextDouble() - 0.5) * 1.2;
                double angle = Math.atan2(uy, ux) + spread;
                double d = SHOT_REACH * (0.4 + 0.6 * rnd.nextDouble()) * easeOutCubic(Math.min(1, p * 2));
                frame.add(FirePuff.Layer.DEBRIS, x + Math.cos(angle) * d, y + Math.sin(angle) * d,
                        1.2 + 1.6 * s * rnd.nextDouble(), 0.62f, 0.04f, 0.05f, 1 - p);
            }
            frame.add(FirePuff.Layer.RIM, x, y, 8 + 6 * s, 1.0f, 0.15f, 0.1f, 0.5 * (1 - p) * s);
        } else {
            double flash = Math.max(0, 1 - p / 0.25);
            frame.add(FirePuff.Layer.CORE, x, y, (10 + 10 * s) * (0.6 + 0.4 * flash), 1.0f, 1.0f, 1.0f, 0.95 * flash);
            if (p < 0.35) {
                double life = p / 0.35;
                double ring = 6 + BLOW_REACH * 0.7 * easeOutCubic(life);
                for (int i = 0; i < 16; i++) {
                    double angle = Math.PI * 2 * i / 16;
                    frame.add(FirePuff.Layer.SHOCKWAVE, x + Math.cos(angle) * ring, y + Math.sin(angle) * ring,
                            2.2, 1.0f, 0.95f, 0.85f, 0.7 * (1 - life));
                }
            }
            int stars = 3 + (int) Math.round(2 * s);
            for (int i = 0; i < stars; i++) {
                double angle = clock * 7 + Math.PI * 2 * i / stars;
                frame.add(FirePuff.Layer.EMBER, x + Math.cos(angle) * 12, y + Math.sin(angle) * 6 - 10,
                        2.4, 1.0f, 0.92f, 0.3f, 1 - p);
            }
            for (int i = 0; i < 6; i++) {
                double angle = rnd.nextDouble() * Math.PI * 2;
                double d = BLOW_REACH * (0.4 + 0.5 * rnd.nextDouble()) * easeOutCubic(p);
                frame.add(FirePuff.Layer.SMOKE, x + Math.cos(angle) * d, y + Math.sin(angle) * d,
                        4 + 3 * s, 0.55f, 0.5f, 0.45f, 0.45 * (1 - p));
            }
        }
        return frame.ordered();
    }

    /**
     * Where to draw the enemy sprite relative to its position at this point of the reaction:
     * a short jolt along the shot, or a heavy decaying stagger for a blow.
     *
     * @return {dx, dy}
     */
    public double[] spriteOffset(double progress) {
        if (progress >= 1 || progress < 0 || Double.isNaN(progress)) {
            return new double[] {0, 0};
        }
        double len = Math.max(1e-9, Math.hypot(dirX, dirY));
        double ux = dirX / len;
        double uy = dirY / len;
        double s = Math.max(0.15, Math.min(1, severity));
        if (kind == Kind.SHOT) {
            double jolt = 3 * s * Math.exp(-progress * 9);
            return new double[] {ux * jolt, uy * jolt};
        }
        double decay = Math.exp(-progress * 4);
        double knock = 9 * s * decay * Math.min(1, progress * 12);
        double wobble = 6 * s * decay * Math.sin(progress * 38);
        return new double[] {ux * knock - uy * wobble, uy * knock + ux * wobble};
    }

    @Override
    public double[] bounds() {
        double extent = (kind == Kind.SHOT ? SHOT_REACH + 3 : BLOW_REACH + 10) + 22;
        return new double[] {x - extent, y - extent, x + extent, y + extent};
    }
}
