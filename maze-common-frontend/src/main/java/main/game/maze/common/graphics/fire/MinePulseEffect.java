package main.game.maze.common.graphics.fire;

import java.util.List;

/**
 * A mine lying in the maze: a dark spiked ball whose red glow pulses. It lives as long as the mine is in the
 * level (the frontend stops drawing it when the mine goes off), so its lifetime is practically endless and the
 * pulse follows the running clock, not the life progress.
 *
 * @param phase        0..1, so neighbouring mines do not beat in step
 * @param pulseSeconds one beat of the glow
 */
public record MinePulseEffect(double x, double y, double phase, double pulseSeconds) implements PuffEffect {

    /** Practically endless: the level removes the effect, the clock never does. */
    static final double LIFETIME = 1.0e9;
    private static final double BODY_RADIUS = 7.5;
    private static final double MAX_GLOW_RADIUS = 18.0;

    @Override
    public double lifetimeSeconds() {
        return LIFETIME;
    }

    @Override
    public List<FirePuff> puffs(double progress, double clock) {
        double glow = 0.5 + 0.5 * Math.sin(2 * Math.PI * (clock / pulseSeconds + phase));
        PuffFrame frame = new PuffFrame();
        // the body is drawn first (back layer), the glow over it
        frame.add(FirePuff.Layer.SMOKE, x + 1, y + 2, BODY_RADIUS + 1.5, 0.0f, 0.0f, 0.0f, 0.3);
        for (int k = 0; k < 6; k++) {
            double a = Math.toRadians(k * 60 + 15);
            frame.add(FirePuff.Layer.SMOKE, x + Math.cos(a) * 9.5, y + Math.sin(a) * 9.5, 2.0, 0.2f, 0.2f, 0.24f, 1.0);
        }
        frame.add(FirePuff.Layer.SMOKE, x, y, BODY_RADIUS, 0.17f, 0.17f, 0.2f, 1.0);
        frame.add(FirePuff.Layer.SMOKE, x - 2.5, y - 2.5, 2.2, 0.42f, 0.42f, 0.47f, 1.0);
        frame.add(FirePuff.Layer.BODY, x, y, 10 + 7 * glow, 1.0f, 0.15f, 0.1f, 0.2 + 0.45 * glow);
        frame.add(FirePuff.Layer.CORE, x, y, 2.5 + 2 * glow, 1.0f, (float) (0.35 + 0.4 * glow), 0.25f, 0.85 + 0.15 * glow);
        return frame.ordered();
    }

    @Override
    public double[] bounds() {
        double pad = MAX_GLOW_RADIUS + 2;
        return new double[] {x - pad, y - pad, x + pad, y + pad};
    }
}
