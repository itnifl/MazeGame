package main.game.maze.common.graphics.fire;

import java.util.List;

/**
 * The nuclear blast of the small nuclear grenade, drawn as a mushroom cloud: a blinding white flash, a shockwave ring
 * racing out over the floor, a skirt of dust rolling out from the ground zero, a burning stem rising and the rolling cap
 * of fire on top that cools into dark smoke, with embers drifting up. Frontend-neutral and deterministic (a function of
 * the progress and the clock); each frontend says which way is up on its screen.
 *
 * @param x      ground zero
 * @param y      ground zero
 * @param radius the blast radius; the cloud is sized from it
 * @param up     which way is up on the screen in this frontend's y: -1 for y-down (JavaFX), +1 for y-up (libGDX)
 */
public record MushroomCloudEffect(double x, double y, double radius, int up) implements PuffEffect {

    /** How long the cloud plays. */
    public static final double LIFETIME_SECONDS = 4.0;
    /** The deep roar of the blast. */
    public static final String SOUND = "/main/game/maze/sounds/nuke-explosion.wav";
    /** Screen shake: stronger and longer than a bomb's. */
    public static final double SHAKE_STRENGTH = 26.0;
    public static final double SHAKE_SECONDS = 1.8;

    private static final int RING_PUFFS = 40;
    private static final int SKIRT_PUFFS = 18;
    private static final int STEM_PUFFS = 9;
    private static final int CAP_PUFFS = 16;
    private static final int EMBERS = 14;
    /** Height of the cap above ground zero, as a share of the radius. */
    private static final double CAP_HEIGHT = 0.8;

    public MushroomCloudEffect {
        up = up >= 0 ? 1 : -1;
        radius = Math.max(1.0, radius);
    }

    @Override
    public double lifetimeSeconds() {
        return LIFETIME_SECONDS;
    }

    @Override
    public List<FirePuff> puffs(double progress, double clock) {
        if (Double.isNaN(progress) || progress >= 1) {
            return List.of();
        }
        double p = PuffFrame.clamp(progress, 0, 1);
        PuffFrame frame = new PuffFrame();
        flash(frame, p);
        shockwave(frame, p);
        skirt(frame, p, clock);
        double capRise = PuffFrame.easeOutCubic(PuffFrame.clamp(p / 0.45, 0, 1));
        double capY = y + up * radius * CAP_HEIGHT * capRise;
        stem(frame, p, clock, capY);
        cap(frame, p, clock, capY);
        embers(frame, p, clock, capY);
        return frame.ordered();
    }

    /** The first instant: a white-hot disc that swells and is gone within half a second. */
    private void flash(PuffFrame frame, double p) {
        double t = p / 0.12;
        if (t >= 1) {
            return;
        }
        double r = radius * (0.15 + 0.45 * PuffFrame.easeOutCubic(t));
        frame.add(FirePuff.Layer.CORE, x, y, r, 1.0f, 1.0f, 0.95f, 1.0 - t);
        frame.add(FirePuff.Layer.INNER, x, y, r * 1.6, 1.0f, 0.85f, 0.5f, 0.6 * (1.0 - t));
    }

    /** A ring of light racing out over the floor to the edge of the blast. */
    private void shockwave(PuffFrame frame, double p) {
        double t = p / 0.35;
        if (t >= 1) {
            return;
        }
        double r = radius * PuffFrame.easeOutCubic(t);
        double fade = 1.0 - t;
        for (int i = 0; i < RING_PUFFS; i++) {
            double a = Math.PI * 2 * i / RING_PUFFS;
            frame.add(FirePuff.Layer.SHOCKWAVE, x + Math.cos(a) * r, y + Math.sin(a) * r * 0.55, 5.0 + 6.0 * fade,
                    1.0f, 0.9f, 0.7f, 0.55 * fade);
        }
    }

    /** Dust and fire rolling out along the ground from ground zero, a flat ellipse. */
    private void skirt(PuffFrame frame, double p, double clock) {
        double spread = PuffFrame.easeOutCubic(PuffFrame.clamp(p / 0.5, 0, 1));
        double fire = 1.0 - PuffFrame.smoothstep(0.15, 0.55, p);
        double dust = PuffFrame.smoothstep(0.05, 0.3, p) * (1.0 - PuffFrame.smoothstep(0.75, 1.0, p));
        for (int i = 0; i < SKIRT_PUFFS; i++) {
            double a = Math.PI * 2 * i / SKIRT_PUFFS + 0.3 * Math.sin(i * 1.7);
            double reach = radius * (0.25 + 0.4 * spread) * (0.8 + 0.2 * Math.sin(i * 2.3));
            double px = x + Math.cos(a) * reach;
            double py = y + Math.sin(a) * reach * 0.35;
            double r = radius * 0.09 * (1.0 + 0.6 * spread);
            frame.add(FirePuff.Layer.SMOKE, px, py, r * 1.3, 0.35f, 0.29f, 0.24f, 0.55 * dust);
            if (fire > 0.01) {
                frame.flame(px, py, r, PuffFrame.clamp(p * 1.6, 0, 1), clock, i);
            }
        }
    }

    /** The burning column from ground zero up to the cap, narrower than the cap, darkening as it cools. */
    private void stem(PuffFrame frame, double p, double clock, double capY) {
        if (p < 0.04) {
            return;
        }
        double width = radius * 0.09;
        double cool = PuffFrame.smoothstep(0.35, 0.95, p);
        for (int i = 0; i < STEM_PUFFS; i++) {
            double along = (i + 0.5) / STEM_PUFFS;
            double sway = Math.sin(clock * 2.0 + i * 0.9) * width * 0.25;
            double py = y + (capY - y) * along;
            double r = width * (1.3 - 0.4 * along);
            frame.add(FirePuff.Layer.SMOKE, x + sway, py, r * 1.35, 0.22f, 0.18f, 0.16f, 0.6 * cool);
            frame.flame(x + sway, py, r, PuffFrame.clamp(0.15 + cool * 0.8, 0, 1), clock, i * 1.3);
        }
    }

    /** The rolling cap: a wide ring of fire turning over itself, a glowing underside, cooling into dark smoke. */
    private void cap(PuffFrame frame, double p, double clock, double capY) {
        if (p < 0.03) {
            return;
        }
        double grow = PuffFrame.easeOutCubic(PuffFrame.clamp(p / 0.6, 0, 1));
        double width = radius * (0.18 + 0.30 * grow);
        double height = width * 0.42;
        double cool = PuffFrame.smoothstep(0.4, 1.0, p);
        double roll = clock * 1.6;
        for (int i = 0; i < CAP_PUFFS; i++) {
            double a = Math.PI * 2 * i / CAP_PUFFS + roll;
            double px = x + Math.cos(a) * width;
            double py = capY + Math.sin(a) * height;
            double r = width * 0.42 * (0.85 + 0.15 * Math.sin(i * 2.1));
            frame.add(FirePuff.Layer.SMOKE, px, py, r * 1.25, 0.18f, 0.15f, 0.14f, 0.75 * (0.3 + 0.7 * cool));
            frame.flame(px, py, r, PuffFrame.clamp(0.1 + cool * 0.85, 0, 1), clock, i * 0.7);
        }
        // the top of the cap and its glowing heart
        frame.add(FirePuff.Layer.SMOKE, x, capY + up * height * 0.6, width * 0.75, 0.2f, 0.17f, 0.15f, 0.7 * (0.3 + 0.7 * cool));
        frame.add(FirePuff.Layer.BODY, x, capY, width * 0.6, 1.0f, 0.45f, 0.08f, 0.55 * (1.0 - cool));
        frame.add(FirePuff.Layer.INNER, x, capY - up * height * 0.3, width * 0.35, 1.0f, 0.8f, 0.3f, 0.6 * (1.0 - cool));
    }

    /** Embers drifting up out of the cloud. */
    private void embers(PuffFrame frame, double p, double clock, double capY) {
        if (p < 0.1) {
            return;
        }
        double fade = 1.0 - PuffFrame.smoothstep(0.6, 1.0, p);
        for (int i = 0; i < EMBERS; i++) {
            double cycle = (clock * 0.35 + i * 0.137) % 1.0;
            double px = x + Math.sin(i * 3.1 + clock) * radius * 0.35;
            double py = y + (capY - y) * 0.3 + up * radius * 0.9 * cycle;
            frame.add(FirePuff.Layer.EMBER, px, py, 2.2, 1.0f, 0.65f, 0.2f, 0.9 * fade * (1.0 - cycle));
        }
    }

    @Override
    public double[] bounds() {
        double pad = radius * 1.15 + 20;
        double top = up * radius * (CAP_HEIGHT + 1.0);
        return new double[] {x - pad, Math.min(y - pad, y + top - pad), x + pad, Math.max(y + pad, y + top + pad)};
    }
}
