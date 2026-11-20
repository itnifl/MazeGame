package main.game.maze.common.graphics.fire;

import java.util.List;

/** A ring of sparkles in the item's colour when the player picks something up. */
public record PickupSparkleEffect(double x, double y, float red, float green, float blue) implements PuffEffect {

    static final double LIFETIME = 0.4;
    private static final double REACH = 22;
    private static final int SPARKS = 10;

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
        PuffFrame frame = new PuffFrame();
        frame.add(FirePuff.Layer.CORE, x, y, 8 * (1 - p), 1.0f, 1.0f, 1.0f, 0.8 * (1 - p));
        for (int i = 0; i < SPARKS; i++) {
            double angle = Math.PI * 2 * i / SPARKS + p * 2;
            double d = 4 + REACH * PuffFrame.easeOutCubic(p);
            frame.add(FirePuff.Layer.EMBER, x + Math.cos(angle) * d, y + Math.sin(angle) * d, 2.2,
                    red, green, blue, 1 - p);
        }
        return frame.ordered();
    }

    @Override
    public double[] bounds() {
        double extent = 4 + REACH + 2.2 + 8;
        return new double[] {x - extent, y - extent, x + extent, y + extent};
    }
}
