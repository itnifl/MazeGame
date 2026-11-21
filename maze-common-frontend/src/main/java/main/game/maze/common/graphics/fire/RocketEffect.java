package main.game.maze.common.graphics.fire;

import java.util.List;

/**
 * An rpg rocket in flight: a slim olive body with a red warhead and fins, a roaring flame and a trail of smoke
 * behind it. A frontend draws it every frame at the rocket's current place by playing a fresh one that lives only
 * a moment ({@link #FRAME_SECONDS}), so it follows the rocket without any layer of its own.
 *
 * @param angleRadians the direction of flight (world space of the frontend that draws it)
 */
public record RocketEffect(double x, double y, double angleRadians) implements PuffEffect {

    /** How long one frame of the rocket lives: a little longer than a frame, so there is no flicker between frames. */
    public static final double FRAME_SECONDS = 0.07;
    private static final double LENGTH = 15.0;
    private static final double BODY_RADIUS = 2.6;
    private static final double TRAIL_REACH = 46.0;
    private static final int BODY_PUFFS = 6;
    private static final int TRAIL_PUFFS = 9;
    private static final double SMOKE_WOBBLE = 1.4;

    @Override
    public double lifetimeSeconds() {
        return FRAME_SECONDS;
    }

    @Override
    public List<FirePuff> puffs(double progress, double clock) {
        if (Double.isNaN(progress) || progress >= 1) {
            return List.of();
        }
        double dx = Math.cos(angleRadians);
        double dy = Math.sin(angleRadians);
        PuffFrame frame = new PuffFrame();
        // smoke trail, thinning out behind the rocket
        for (int i = 0; i < TRAIL_PUFFS; i++) {
            double back = LENGTH * 0.5 + TRAIL_REACH * (i + 1) / TRAIL_PUFFS;
            double fade = 1.0 - (i + 1.0) / (TRAIL_PUFFS + 1);
            double wobble = Math.sin(clock * 9 + i * 1.7) * SMOKE_WOBBLE;
            frame.add(FirePuff.Layer.SMOKE, x - dx * back - dy * wobble, y - dy * back + dx * wobble,
                    3.0 + (i + 1) * 0.55, 0.55f, 0.55f, 0.55f, 0.32 * fade);
        }
        // the flame at the tail
        double flicker = 1.0 + 0.25 * Math.sin(clock * 47) + 0.15 * Math.sin(clock * 71 + 1);
        double tailX = x - dx * LENGTH * 0.5;
        double tailY = y - dy * LENGTH * 0.5;
        frame.add(FirePuff.Layer.BODY, tailX - dx * 5, tailY - dy * 5, 6.0 * flicker, 1.0f, 0.42f, 0.08f, 0.55);
        frame.add(FirePuff.Layer.INNER, tailX - dx * 3, tailY - dy * 3, 3.8 * flicker, 1.0f, 0.75f, 0.2f, 0.9);
        frame.add(FirePuff.Layer.CORE, tailX - dx * 1.5, tailY - dy * 1.5, 2.0 * flicker, 1.0f, 0.98f, 0.8f, 1.0);
        // the body, nose to tail
        for (int i = 0; i < BODY_PUFFS; i++) {
            double along = LENGTH * (0.5 - (double) i / (BODY_PUFFS - 1));
            frame.add(FirePuff.Layer.EMBER, x + dx * along, y + dy * along, BODY_RADIUS, 0.3f, 0.34f, 0.2f, 1.0);
        }
        frame.add(FirePuff.Layer.EMBER, x + dx * LENGTH * 0.5, y + dy * LENGTH * 0.5, BODY_RADIUS * 0.95, 0.72f, 0.14f, 0.1f, 1.0);
        // fins
        for (int side = -1; side <= 1; side += 2) {
            frame.add(FirePuff.Layer.EMBER, tailX - dy * side * 3.4, tailY + dx * side * 3.4, 1.5, 0.2f, 0.22f, 0.15f, 1.0);
        }
        return frame.ordered();
    }

    @Override
    public double[] bounds() {
        // the farthest smoke puff: half the body and the trail behind it, its wobble and its own radius
        double smokeRadius = 3.0 + TRAIL_PUFFS * 0.55;
        double pad = LENGTH * 0.5 + TRAIL_REACH + SMOKE_WOBBLE + smokeRadius + 2;
        return new double[] {x - pad, y - pad, x + pad, y + pad};
    }
}
