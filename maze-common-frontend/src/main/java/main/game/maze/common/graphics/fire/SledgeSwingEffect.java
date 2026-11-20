package main.game.maze.common.graphics.fire;

import static main.game.maze.common.graphics.fire.PuffFrame.easeOutCubic;
import static main.game.maze.common.graphics.fire.PuffFrame.smoothstep;

import java.util.List;

/**
 * A giant sledgehammer swing around the player: the steel head sweeps a wide arc (about 200
 * degrees, centred on the facing direction) leaving a motion-blur trail, then slams down with
 * a flash and a ring of dust. Frame-agnostic: the facing comes in the caller's coordinates.
 *
 * @param cx,cy     the player's centre
 * @param dirX,dirY the facing direction (unit-ish)
 * @param reach     how far the head travels from the centre
 */
public record SledgeSwingEffect(double cx, double cy, double dirX, double dirY, double reach) implements PuffEffect {

    static final double LIFETIME = 0.45;
    /** Fraction of the life spent swinging; the rest is the impact. */
    private static final double SWING = 0.55;
    private static final double ARC = Math.toRadians(200);
    private static final int TRAIL = 12;

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
        double facing = Math.atan2(dirY, dirX);
        double swing = easeOutCubic(Math.min(1, p / SWING));
        double headAngle = facing - ARC / 2 + ARC * swing;
        double headRadius = reach * 0.22;

        // motion-blur trail behind the head
        for (int i = 1; i <= TRAIL; i++) {
            double back = swing - i * 0.05;
            if (back < 0) {
                break;
            }
            double angle = facing - ARC / 2 + ARC * back;
            double alpha = (1 - i / (double) TRAIL) * 0.45 * (1 - smoothstep(SWING, 1, p));
            frame.add(FirePuff.Layer.SHOCKWAVE, cx + Math.cos(angle) * reach * 0.85, cy + Math.sin(angle) * reach * 0.85,
                    headRadius * (1 - i / (double) TRAIL * 0.5), 0.95f, 0.95f, 1.0f, alpha);
        }

        double headAlpha = 1 - smoothstep(0.8, 1, p);
        double hx = cx + Math.cos(headAngle) * reach * 0.85;
        double hy = cy + Math.sin(headAngle) * reach * 0.85;
        // handle from the player's hands to the head
        for (int i = 1; i <= 4; i++) {
            double t = i / 5.0;
            frame.add(FirePuff.Layer.DEBRIS, cx + (hx - cx) * t, cy + (hy - cy) * t, 1.6,
                    0.55f, 0.36f, 0.18f, headAlpha);
        }
        frame.add(FirePuff.Layer.DEBRIS, hx, hy, headRadius, 0.30f, 0.32f, 0.36f, headAlpha);
        frame.add(FirePuff.Layer.DEBRIS, hx - headRadius * 0.25, hy - headRadius * 0.25, headRadius * 0.5,
                0.75f, 0.78f, 0.84f, headAlpha);

        // impact: flash and dust where the head lands
        if (p > SWING) {
            double impact = (p - SWING) / (1 - SWING);
            frame.add(FirePuff.Layer.CORE, hx, hy, headRadius * (1.4 - impact), 1.0f, 1.0f, 0.95f, 0.9 * (1 - impact));
            for (int i = 0; i < 8; i++) {
                double angle = Math.PI * 2 * i / 8;
                double d = headRadius * (0.6 + 1.2 * impact);
                frame.add(FirePuff.Layer.SMOKE, hx + Math.cos(angle) * d, hy + Math.sin(angle) * d,
                        headRadius * 0.55, 0.55f, 0.52f, 0.48f, 0.5 * (1 - impact));
            }
        }
        return frame.ordered();
    }

    @Override
    public double[] bounds() {
        double extent = reach * 0.85 + reach * 0.22 * 2.4;   // head + impact dust ring (1.8 + 0.55 head radii)
        return new double[] {cx - extent, cy - extent, cx + extent, cy + extent};
    }
}
