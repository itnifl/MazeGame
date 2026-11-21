package main.game.maze.common.graphics.fire;

import java.util.List;

/**
 * The pulse beam cannon gathering energy: a ball of blue-white light on the player that swells and brightens as the charge
 * comes up, with rings and sparks pulled in towards it. One frame lives only a moment ({@link #FRAME_SECONDS}); a frontend
 * plays a fresh one as the charge goes on, each at the progress the charge has reached.
 *
 * @param progress how far the charge has come, 0 to 1
 */
public record PulseChargeEffect(double x, double y, double progress) implements PuffEffect {

    public static final double FRAME_SECONDS = 0.16;
    private static final double REACH = 70.0;

    @Override
    public double lifetimeSeconds() {
        return FRAME_SECONDS;
    }

    @Override
    public List<FirePuff> puffs(double lifeProgress, double clock) {
        if (Double.isNaN(lifeProgress) || lifeProgress >= 1) {
            return List.of();
        }
        double c = Math.max(0, Math.min(1, progress));
        PuffFrame frame = new PuffFrame();
        double throb = 1.0 + 0.15 * Math.sin(clock * (14 + 26 * c));
        frame.add(FirePuff.Layer.BODY, x, y, (14 + 34 * c) * throb, 0.3f, 0.55f, 1.0f, 0.22 + 0.25 * c);
        frame.add(FirePuff.Layer.INNER, x, y, (8 + 18 * c) * throb, 0.55f, 0.9f, 1.0f, 0.4 + 0.4 * c);
        frame.add(FirePuff.Layer.CORE, x, y, (3.5 + 9 * c) * throb, 1.0f, 1.0f, 1.0f, 0.6 + 0.4 * c);
        // rings pulled in towards the ball: each shrinks from far away to nothing, one after another
        for (int k = 0; k < 3; k++) {
            double phase = ((clock * (1.2 + 1.5 * c)) + k / 3.0) % 1.0;
            frame.add(FirePuff.Layer.SHOCKWAVE, x, y, REACH * (1 - phase) + 6, 0.55f, 0.85f, 1.0f, 0.35 * phase * (0.4 + c));
        }
        // sparks streaming in from all round
        int sparks = 6 + (int) (12 * c);
        for (int i = 0; i < sparks; i++) {
            double a = i * 2 * Math.PI / sparks + clock * 2.4;
            double phase = ((clock * 1.6) + i * 0.173) % 1.0;
            double r = REACH * (1 - phase);
            frame.add(FirePuff.Layer.EMBER, x + Math.cos(a) * r, y + Math.sin(a) * r, 1.4 + c, 0.75f, 0.92f, 1.0f, 0.9 * phase);
        }
        return frame.ordered();
    }

    @Override
    public double[] bounds() {
        double pad = REACH + 20;
        return new double[] {x - pad, y - pad, x + pad, y + pad};
    }
}
