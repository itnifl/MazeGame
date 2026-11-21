package main.game.maze.common.graphics.fire;

import java.util.List;

/**
 * An anti-personnel mine lying in the maze: a low, round, olive-drab body with ridged rings, a dark pressure
 * plate in the middle and three small trip studs on the rim. A faint light-blue circle pulses round it, so a
 * careful player can see it coming. It lives as long as the mine is in the level (the frontend stops drawing it
 * when the mine goes off), so its lifetime is practically endless and the pulse follows the running clock, not
 * the life progress.
 *
 * @param phase        0..1, so neighbouring mines do not beat in step
 * @param pulseSeconds one beat of the pulse
 */
public record MinePulseEffect(double x, double y, double phase, double pulseSeconds) implements PuffEffect {

    /** Practically endless: the level removes the effect, the clock never does. */
    static final double LIFETIME = 1.0e9;
    private static final double BODY_RADIUS = 8.0;
    private static final int RING_DOTS = 20;
    /** Light blue, the colour of the pulse. */
    private static final float PULSE_RED = 0.55f;
    private static final float PULSE_GREEN = 0.82f;
    private static final float PULSE_BLUE = 1.0f;
    private static final double MAX_PULSE_RADIUS = 22.0;

    @Override
    public double lifetimeSeconds() {
        return LIFETIME;
    }

    @Override
    public List<FirePuff> puffs(double progress, double clock) {
        if (progress >= 1.0) {
            return List.of();
        }
        double glow = 0.5 + 0.5 * Math.sin(2 * Math.PI * (clock / pulseSeconds + phase));
        PuffFrame frame = new PuffFrame();
        // the body, on the back layer: shadow, dark rim, olive disc, ridged rings, pressure plate and button
        frame.add(FirePuff.Layer.SMOKE, x + 1, y + 2, BODY_RADIUS + 1.5, 0.0f, 0.0f, 0.0f, 0.3);
        frame.add(FirePuff.Layer.SMOKE, x, y, BODY_RADIUS + 0.6, 0.2f, 0.23f, 0.12f, 1.0);
        frame.add(FirePuff.Layer.SMOKE, x, y, BODY_RADIUS, 0.36f, 0.42f, 0.22f, 1.0);
        frame.add(FirePuff.Layer.SMOKE, x, y, BODY_RADIUS * 0.76, 0.27f, 0.32f, 0.16f, 1.0);
        frame.add(FirePuff.Layer.SMOKE, x, y, BODY_RADIUS * 0.68, 0.38f, 0.44f, 0.24f, 1.0);
        frame.add(FirePuff.Layer.SMOKE, x, y, BODY_RADIUS * 0.42, 0.16f, 0.18f, 0.1f, 1.0);
        frame.add(FirePuff.Layer.SMOKE, x - 0.7, y - 0.7, BODY_RADIUS * 0.2, 0.55f, 0.6f, 0.38f, 1.0);
        for (int k = 0; k < 3; k++) {
            double a = Math.toRadians(k * 120 - 90);
            frame.add(FirePuff.Layer.SMOKE, x + Math.cos(a) * (BODY_RADIUS - 0.8), y + Math.sin(a) * (BODY_RADIUS - 0.8),
                    1.5, 0.13f, 0.14f, 0.09f, 1.0);
        }
        frame.add(FirePuff.Layer.SMOKE, x - 2.8, y - 3.0, 1.6, 0.58f, 0.64f, 0.4f, 0.55);

        // the faint light-blue pulse: a soft disc and a dotted circle that swell and fade with the beat
        double radius = 14 + 8 * glow;
        frame.add(FirePuff.Layer.BODY, x, y, radius, PULSE_RED, PULSE_GREEN, PULSE_BLUE, 0.05 + 0.08 * glow);
        for (int i = 0; i < RING_DOTS; i++) {
            double a = 2 * Math.PI * i / RING_DOTS;
            frame.add(FirePuff.Layer.BODY, x + Math.cos(a) * radius, y + Math.sin(a) * radius, 1.3,
                    PULSE_RED, PULSE_GREEN, PULSE_BLUE, 0.14 + 0.2 * glow);
        }
        return frame.ordered();
    }

    @Override
    public double[] bounds() {
        double pad = MAX_PULSE_RADIUS + 2;
        return new double[] {x - pad, y - pad, x + pad, y + pad};
    }
}
