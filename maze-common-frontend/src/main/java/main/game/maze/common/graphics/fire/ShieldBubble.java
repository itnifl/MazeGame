package main.game.maze.common.graphics.fire;

import java.util.List;

/**
 * The power shield around the player while it is active: a pulsing cyan ring that flickers
 * during its last second and a half, so the player sees it is about to run out.
 */
public final class ShieldBubble {

    /** Furthest any puff reaches, as a multiple of the bubble radius. */
    public static final double EXTENT_FACTOR = 1.25;
    private static final int POINTS = 28;

    private ShieldBubble() {
    }

    /**
     * @param radius           bubble radius (around the player sprite)
     * @param remainingSeconds shield time left; nothing is drawn at 0
     */
    public static List<FirePuff> puffs(double cx, double cy, double radius, double remainingSeconds, double clock) {
        if (!(remainingSeconds > 0)) {
            return List.of();
        }
        double flicker = remainingSeconds < 1.5 ? (Math.sin(clock * 30) > 0 ? 1 : 0.25) : 1;
        double pulse = 1 + 0.06 * Math.sin(clock * 6);
        PuffFrame frame = new PuffFrame();
        frame.add(FirePuff.Layer.RIM, cx, cy, radius * pulse, 0.25f, 0.75f, 1.0f, 0.12 * flicker);
        for (int i = 0; i < POINTS; i++) {
            double angle = Math.PI * 2 * i / POINTS + clock * 1.5;
            frame.add(FirePuff.Layer.SHOCKWAVE, cx + Math.cos(angle) * radius * pulse,
                    cy + Math.sin(angle) * radius * pulse, radius * 0.11, 0.55f, 0.9f, 1.0f, 0.55 * flicker);
        }
        return frame.ordered();
    }
}
