package main.game.maze.common.graphics.fire;

import java.util.List;

/**
 * The player flashing in after a teleport: a blinding white flash that shrinks, a pale-blue column of light
 * dropping onto the spot, a ring of light spreading across the ground and sparkles rising. Over in well under a
 * second.
 */
public record TeleportFlashEffect(double x, double y, boolean yUp) implements PuffEffect {

    /** A flash on a screen whose y axis points down (JavaFX): the column of light rises towards smaller y. */
    public TeleportFlashEffect(double x, double y) {
        this(x, y, false);
    }


    static final double LIFETIME = 0.75;
    private static final double RING_REACH = 52.0;
    private static final double COLUMN_HEIGHT = 70.0;
    private static final int RING_DOTS = 22;
    private static final int COLUMN_PUFFS = 10;
    private static final int SPARKLES = 12;

    /** +1 when the screen's y axis points up (libGDX), -1 when it points down (JavaFX): which way is "up". */
    private double rise() {
        return yUp ? 1 : -1;
    }

    @Override
    public double lifetimeSeconds() {
        return LIFETIME;
    }

    @Override
    public List<FirePuff> puffs(double progress, double clock) {
        if (Double.isNaN(progress) || progress >= 1) {
            return List.of();
        }
        double p = Math.max(0, progress);
        double fade = 1 - p;
        PuffFrame frame = new PuffFrame();
        // the column of light, brightest at the start and narrowing
        for (int i = 0; i < COLUMN_PUFFS; i++) {
            double t = i / (double) (COLUMN_PUFFS - 1);
            frame.add(FirePuff.Layer.BODY, x, y + rise() * t * COLUMN_HEIGHT, 13 * fade + 4, 0.6f, 0.85f, 1.0f,
                    0.4 * fade * (1 - 0.6 * t));
        }
        // a glow spreading over the ground and the white flash on the player
        frame.add(FirePuff.Layer.BODY, x, y, 20 + 30 * p, 0.7f, 0.9f, 1.0f, 0.5 * fade);
        frame.add(FirePuff.Layer.INNER, x, y, 12 + 6 * fade, 0.9f, 0.97f, 1.0f, 0.9 * fade);
        frame.add(FirePuff.Layer.CORE, x, y, 5 + 14 * fade, 1.0f, 1.0f, 1.0f, Math.min(1.0, 1.6 * fade));
        // the ring of light running out over the ground
        double reach = 8 + RING_REACH * (1 - fade * fade);
        for (int i = 0; i < RING_DOTS; i++) {
            double a = 2 * Math.PI * i / RING_DOTS;
            frame.add(FirePuff.Layer.SHOCKWAVE, x + Math.cos(a) * reach, y + Math.sin(a) * reach * 0.8, 2.2, 0.75f, 0.92f, 1.0f, 0.85 * fade);
        }
        // sparkles rising from the spot
        for (int i = 0; i < SPARKLES; i++) {
            double angle = i * 2.399;
            double spread = 6 + 16 * ((i * 37) % 10) / 10.0;
            double lift = (14 + (i % 5) * 9) * p + 2;
            frame.add(FirePuff.Layer.EMBER, x + Math.cos(angle) * spread, y + rise() * lift + Math.sin(angle) * spread * 0.4, 1.6,
                    0.85f, 0.95f, 1.0f, 0.95 * fade);
        }
        return frame.ordered();
    }

    @Override
    public double[] bounds() {
        double reachX = RING_REACH + 12;
        double up = y + rise() * (COLUMN_HEIGHT + 20);
        double down = y - rise() * reachX;
        return new double[] {x - reachX, Math.min(up, down), x + reachX, Math.max(up, down)};
    }
}
