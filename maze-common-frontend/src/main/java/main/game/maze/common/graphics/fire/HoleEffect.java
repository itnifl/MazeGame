package main.game.maze.common.graphics.fire;

import java.util.List;

/**
 * The hole a mine leaves in the ground: a black pit with a ragged earthen rim, and violet and cyan sparks
 * spiralling down into it, as if it had no bottom. It lives as long as the level, so its lifetime is
 * practically endless and the swirl follows the running clock.
 */
public record HoleEffect(double x, double y, double radius) implements PuffEffect {

    /** Practically endless: the level removes the effect, the clock never does. */
    static final double LIFETIME = 1.0e9;
    private static final int SPARKS = 12;

    @Override
    public double lifetimeSeconds() {
        return LIFETIME;
    }

    @Override
    public List<FirePuff> puffs(double progress, double clock) {
        PuffFrame frame = new PuffFrame();
        // the ground, back to front: churned earth, a rim, then the pit that gets darker towards the middle
        frame.add(FirePuff.Layer.SMOKE, x, y, radius * 1.3, 0.16f, 0.1f, 0.06f, 0.55);
        frame.add(FirePuff.Layer.SMOKE, x, y, radius * 1.12, 0.29f, 0.2f, 0.13f, 1.0);
        frame.add(FirePuff.Layer.SMOKE, x, y, radius * 0.96, 0.08f, 0.05f, 0.03f, 1.0);
        frame.add(FirePuff.Layer.SMOKE, x, y, radius * 0.78, 0.0f, 0.0f, 0.0f, 1.0);
        // a faint violet depth, and sparks spiralling in
        frame.add(FirePuff.Layer.INNER, x, y, radius * 0.55, 0.55f, 0.3f, 1.0f, 0.14 + 0.08 * Math.sin(clock * 2.0));
        for (int i = 0; i < SPARKS; i++) {
            double life = (i / (double) SPARKS + clock * 0.35) % 1.0;
            double angle = i * 2.4 + clock * 3.0 + life * 4.0;
            double distance = radius * 0.92 * (1 - life);
            boolean violet = i % 2 == 0;
            frame.add(FirePuff.Layer.EMBER, x + Math.cos(angle) * distance, y + Math.sin(angle) * distance * 0.9, 1.6,
                    violet ? 0.72f : 0.4f, violet ? 0.42f : 0.92f, 1.0f, 0.9 * (1 - life));
        }
        return frame.ordered();
    }

    @Override
    public double[] bounds() {
        double pad = radius * 1.35 + 2;
        return new double[] {x - pad, y - pad, x + pad, y + pad};
    }
}
