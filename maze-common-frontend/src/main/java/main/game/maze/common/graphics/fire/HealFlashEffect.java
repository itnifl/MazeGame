package main.game.maze.common.graphics.fire;

import java.util.List;

/**
 * The player flashing after picking up health: three bright white-gold pulses of light over the
 * player (additive glow, so the sprite looks lit up) that fade out, with a soft green halo. Drawn
 * by both frontends' puff renderers; {@code size} is the player size.
 */
public record HealFlashEffect(double x, double y, double size) implements PuffEffect {

    private static final int PULSES = 3;
    private static final double CORE_FACTOR = 0.7;
    private static final double HALO_FACTOR = 1.4;

    /** Flash strength 0..1 at this point of the effect: three pulses, each weaker than the last. */
    public static double strength(double progress) {
        if (Double.isNaN(progress) || progress < 0 || progress >= 1) {
            return 0;
        }
        return Math.abs(Math.sin(Math.PI * PULSES * progress)) * (1 - 0.7 * progress);
    }

    @Override
    public double lifetimeSeconds() {
        return HealFeedback.LIFETIME_SECONDS;
    }

    @Override
    public List<FirePuff> puffs(double progress, double clock) {
        double strength = strength(progress);
        if (strength <= 0) {
            return List.of();
        }
        double core = size * CORE_FACTOR * (0.8 + 0.4 * strength);
        PuffFrame frame = new PuffFrame();
        frame.add(FirePuff.Layer.INNER, x, y, core * HALO_FACTOR, 0.45f, 1.0f, 0.5f, 0.45 * strength);
        frame.add(FirePuff.Layer.CORE, x, y, core, 1.0f, 0.97f, 0.8f, 0.85 * strength);
        return frame.ordered();
    }

    @Override
    public double[] bounds() {
        double extent = size * CORE_FACTOR * 1.2 * HALO_FACTOR;
        return new double[] {x - extent, y - extent, x + extent, y + extent};
    }
}
