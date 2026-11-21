import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * Generates the guard tower sprites: {@code guard-tower.png} (a squat round stone tower seen from above, with a
 * crenellated top, a gatling gun and an rpg launcher on it) and its three damaged looks, the way the game names
 * them for every enemy: {@code guard-tower-damaged.png} (cracks and soot), {@code guard-tower-bleeding.png}
 * (chunks blown out of the wall, fires) and {@code guard-tower-tired.png} (half collapsed, charred, smoking).
 * Deterministic.
 *
 * <pre>java tools/sprite-gen/GuardTowerSpriteGenerator.java [spriteDir]</pre>
 */
public final class GuardTowerSpriteGenerator {

    static final int SIZE = 512;
    private static final Color STONE = new Color(0x8d8f96);
    private static final Color STONE_LIGHT = new Color(0xb4b6bd);
    private static final Color STONE_DARK = new Color(0x4d4f57);
    private static final Color OUTLINE = new Color(0x20222a);

    private GuardTowerSpriteGenerator() {
    }

    public static void main(String[] args) throws IOException {
        Path dir = Path.of(args.length > 0 ? args[0] : "maze-common-frontend/src/main/resources/main/game/maze");
        String[] names = {"guard-tower.png", "guard-tower-damaged.png", "guard-tower-bleeding.png", "guard-tower-tired.png"};
        for (int state = 0; state < names.length; state++) {
            ImageIO.write(render(state), "png", dir.resolve(names[state]).toFile());
            System.out.println("wrote " + names[state]);
        }
    }

    /** @param state 0 healthy, 1 damaged, 2 heavily damaged, 3 about to fall */
    static BufferedImage render(int state) {
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Random random = new Random(1234);
            double cx = SIZE / 2.0;
            double cy = SIZE / 2.0;
            double radius = SIZE * 0.44;
            // soft ground shadow
            g.setColor(new Color(0, 0, 0, 70));
            g.fill(new Ellipse2D.Double(cx - radius + 10, cy - radius + 16, radius * 2, radius * 2));
            // the wall of the tower: a stone ring
            Color wall = state >= 3 ? mix(STONE, new Color(0x2a2724), 0.55) : state == 2 ? mix(STONE, new Color(0x2a2724), 0.35)
                    : state == 1 ? mix(STONE, new Color(0x2a2724), 0.15) : STONE;
            g.setPaint(new RadialGradientPaint((float) (cx - radius * 0.3), (float) (cy - radius * 0.3), (float) (radius * 1.5),
                    new float[] {0f, 1f}, new Color[] {mix(wall, STONE_LIGHT, 0.5), mix(wall, STONE_DARK, 0.7)}));
            g.fill(new Ellipse2D.Double(cx - radius, cy - radius, radius * 2, radius * 2));
            g.setColor(OUTLINE);
            g.setStroke(new BasicStroke(7f));
            g.draw(new Ellipse2D.Double(cx - radius, cy - radius, radius * 2, radius * 2));
            // stone courses
            g.setStroke(new BasicStroke(2.5f));
            g.setColor(new Color(0x30323a, false));
            for (double r = radius * 0.62; r < radius; r += radius * 0.12) {
                g.draw(new Ellipse2D.Double(cx - r, cy - r, r * 2, r * 2));
            }
            // battlements: blocks round the rim, some gone when damaged
            int blocks = 16;
            for (int i = 0; i < blocks; i++) {
                double a = 2 * Math.PI * i / blocks;
                boolean gone = (state >= 2 && random.nextDouble() < (state == 2 ? 0.3 : 0.6))
                        || (state == 1 && i == 3);
                double bx = cx + Math.cos(a) * radius * 0.86;
                double by = cy + Math.sin(a) * radius * 0.86;
                if (gone) {
                    rubble(g, bx, by, radius * 0.07, random);
                    continue;
                }
                g.setColor(STONE_LIGHT);
                g.fill(new Ellipse2D.Double(bx - radius * 0.09, by - radius * 0.09, radius * 0.18, radius * 0.18));
                g.setColor(OUTLINE);
                g.setStroke(new BasicStroke(3f));
                g.draw(new Ellipse2D.Double(bx - radius * 0.09, by - radius * 0.09, radius * 0.18, radius * 0.18));
            }
            // the platform inside the battlements
            double platform = radius * 0.7;
            g.setColor(mix(new Color(0x5b5d66), new Color(0x1a1816), state * 0.18));
            g.fill(new Ellipse2D.Double(cx - platform, cy - platform, platform * 2, platform * 2));
            g.setColor(OUTLINE);
            g.setStroke(new BasicStroke(4f));
            g.draw(new Ellipse2D.Double(cx - platform, cy - platform, platform * 2, platform * 2));
            sandbags(g, cx, cy, platform);
            weapons(g, cx, cy, radius, state);
            if (state >= 1) {
                cracks(g, cx, cy, radius, state, random);
                soot(g, cx, cy, radius, state, random);
            }
            if (state >= 2) {
                blastHoles(g, cx, cy, radius, state, random);
                fires(g, cx, cy, radius, state, random);
            }
            if (state >= 3) {
                smoke(g, cx, cy, radius, random);
            }
        } finally {
            g.dispose();
        }
        return image;
    }

    private static void sandbags(Graphics2D g, double cx, double cy, double platform) {
        g.setStroke(new BasicStroke(3f));
        for (int i = 0; i < 9; i++) {
            double a = Math.PI * 0.15 + i * Math.PI * 0.12;
            double bx = cx + Math.cos(a) * platform * 0.9;
            double by = cy + Math.sin(a) * platform * 0.9;
            g.setColor(new Color(0xb3a173));
            g.fill(new Ellipse2D.Double(bx - 17, by - 12, 34, 24));
            g.setColor(new Color(0x4a3f26));
            g.draw(new Ellipse2D.Double(bx - 17, by - 12, 34, 24));
        }
    }

    /** The gatling gun (a cluster of barrels) pointing right and down, and the rpg launcher tube on the left. */
    private static void weapons(Graphics2D g, double cx, double cy, double radius, int state) {
        // rpg launcher: a fat tube over the shoulder of a mount
        g.setColor(new Color(0x2f3a2c));
        Path2D tube = new Path2D.Double();
        tube.moveTo(cx - radius * 0.55, cy - radius * 0.42);
        tube.lineTo(cx - radius * 0.05, cy - radius * 0.1);
        tube.lineTo(cx + radius * 0.05, cy - radius * 0.26);
        tube.lineTo(cx - radius * 0.45, cy - radius * 0.58);
        tube.closePath();
        g.fill(tube);
        g.setColor(OUTLINE);
        g.setStroke(new BasicStroke(4f));
        g.draw(tube);
        g.setColor(new Color(0x8a2b1b));   // the rocket's nose in the muzzle
        g.fill(new Ellipse2D.Double(cx - radius * 0.62, cy - radius * 0.58, radius * 0.16, radius * 0.16));
        // gatling gun: mount, barrel bundle
        g.setColor(new Color(0x3b3d44));
        g.fill(new Ellipse2D.Double(cx - radius * 0.14, cy - radius * 0.04, radius * 0.3, radius * 0.3));
        g.setColor(OUTLINE);
        g.draw(new Ellipse2D.Double(cx - radius * 0.14, cy - radius * 0.04, radius * 0.3, radius * 0.3));
        double length = state >= 3 ? radius * 0.32 : radius * 0.6;   // the barrels are bent short when ruined
        for (int i = -1; i <= 1; i++) {
            double oy = i * radius * 0.05;
            g.setColor(new Color(0x15161b));
            g.setStroke(new BasicStroke(9f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawLine((int) (cx + radius * 0.05), (int) (cy + radius * 0.14 + oy), (int) (cx + length), (int) (cy + radius * 0.2 + oy));
            g.setColor(new Color(0x6c6f78));
            g.setStroke(new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawLine((int) (cx + radius * 0.05), (int) (cy + radius * 0.14 + oy - 2), (int) (cx + length), (int) (cy + radius * 0.2 + oy - 2));
        }
    }

    private static void cracks(Graphics2D g, double cx, double cy, double radius, int state, Random random) {
        g.setColor(new Color(0x15120f));
        g.setStroke(new BasicStroke(state >= 2 ? 5f : 3.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int count = 3 + state * 3;
        for (int i = 0; i < count; i++) {
            double a = random.nextDouble() * 2 * Math.PI;
            double r = radius * (0.55 + random.nextDouble() * 0.4);
            double x = cx + Math.cos(a) * r;
            double y = cy + Math.sin(a) * r;
            Path2D crack = new Path2D.Double();
            crack.moveTo(x, y);
            for (int s = 0; s < 5; s++) {
                x += Math.cos(a + (random.nextDouble() - 0.5) * 1.4) * radius * 0.07;
                y += Math.sin(a + (random.nextDouble() - 0.5) * 1.4) * radius * 0.07;
                crack.lineTo(x, y);
            }
            g.draw(crack);
        }
    }

    private static void soot(Graphics2D g, double cx, double cy, double radius, int state, Random random) {
        int count = 4 + state * 4;
        for (int i = 0; i < count; i++) {
            double a = random.nextDouble() * 2 * Math.PI;
            double r = radius * random.nextDouble() * 0.9;
            double size = radius * (0.08 + random.nextDouble() * 0.12);
            double x = cx + Math.cos(a) * r;
            double y = cy + Math.sin(a) * r;
            g.setPaint(new RadialGradientPaint((float) x, (float) y, (float) size, new float[] {0f, 1f},
                    new Color[] {new Color(0, 0, 0, 120 + state * 20), new Color(0, 0, 0, 0)}));
            g.fill(new Ellipse2D.Double(x - size, y - size, size * 2, size * 2));
        }
    }

    private static void blastHoles(Graphics2D g, double cx, double cy, double radius, int state, Random random) {
        int count = state == 2 ? 2 : 4;
        for (int i = 0; i < count; i++) {
            double a = random.nextDouble() * 2 * Math.PI;
            double r = radius * (0.5 + random.nextDouble() * 0.4);
            double x = cx + Math.cos(a) * r;
            double y = cy + Math.sin(a) * r;
            double size = radius * (0.1 + random.nextDouble() * 0.08);
            g.setColor(new Color(0x0d0b0a));
            g.fill(new Ellipse2D.Double(x - size, y - size * 0.8, size * 2, size * 1.6));
            g.setColor(new Color(0x5a2b12));
            g.setStroke(new BasicStroke(3f));
            g.draw(new Ellipse2D.Double(x - size, y - size * 0.8, size * 2, size * 1.6));
            rubble(g, x + size, y + size * 0.4, size * 0.5, random);
        }
    }

    private static void rubble(Graphics2D g, double x, double y, double size, Random random) {
        for (int i = 0; i < 5; i++) {
            double px = x + (random.nextDouble() - 0.5) * size * 3;
            double py = y + (random.nextDouble() - 0.5) * size * 3;
            double s = size * (0.35 + random.nextDouble() * 0.5);
            g.setColor(random.nextBoolean() ? STONE_DARK : new Color(0x6a6c74));
            g.fill(new Ellipse2D.Double(px - s, py - s, s * 2, s * 1.6));
        }
    }

    private static void fires(Graphics2D g, double cx, double cy, double radius, int state, Random random) {
        int count = state == 2 ? 3 : 6;
        for (int i = 0; i < count; i++) {
            double a = random.nextDouble() * 2 * Math.PI;
            double r = radius * (0.3 + random.nextDouble() * 0.55);
            double x = cx + Math.cos(a) * r;
            double y = cy + Math.sin(a) * r;
            double size = radius * (0.1 + random.nextDouble() * 0.1);
            g.setPaint(new RadialGradientPaint((float) x, (float) y, (float) (size * 1.8), new float[] {0f, 0.5f, 1f},
                    new Color[] {new Color(255, 236, 140, 240), new Color(255, 120, 20, 200), new Color(200, 30, 0, 0)}));
            g.fill(new Ellipse2D.Double(x - size * 1.8, y - size * 1.8, size * 3.6, size * 3.6));
        }
    }

    private static void smoke(Graphics2D g, double cx, double cy, double radius, Random random) {
        for (int i = 0; i < 6; i++) {
            double x = cx + (random.nextDouble() - 0.5) * radius * 1.4;
            double y = cy + (random.nextDouble() - 0.5) * radius * 1.4;
            double size = radius * (0.18 + random.nextDouble() * 0.16);
            g.setPaint(new RadialGradientPaint((float) x, (float) y, (float) size, new float[] {0f, 1f},
                    new Color[] {new Color(40, 38, 38, 150), new Color(40, 38, 38, 0)}));
            g.fill(new Ellipse2D.Double(x - size, y - size, size * 2, size * 2));
        }
    }

    private static Color mix(Color a, Color b, double t) {
        t = Math.max(0, Math.min(1, t));
        return new Color((int) Math.round(a.getRed() + (b.getRed() - a.getRed()) * t),
                (int) Math.round(a.getGreen() + (b.getGreen() - a.getGreen()) * t),
                (int) Math.round(a.getBlue() + (b.getBlue() - a.getBlue()) * t));
    }
}
