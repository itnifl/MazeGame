import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * Generates {@code gameBackGround2.png}, the background of the normal difficulty: a lush, seamless
 * meadow (layered noise, thousands of grass blades, shaded clumps, clover and tiny flowers) with patches
 * of burning grass: scorched earth, a charred rim, glowing embers, flame tongues and drifting smoke.
 * Everything wraps round the edges so the tile repeats without a seam. Deterministic.
 *
 * <pre>java tools/sprite-gen/GrassBackgroundGenerator.java [spriteDir]</pre>
 */
public final class GrassBackgroundGenerator {

    static final int SIZE = 1024;
    private static final long SEED = 2024L;

    private GrassBackgroundGenerator() {
    }

    public static void main(String[] args) throws IOException {
        Path dir = Path.of(args.length > 0 ? args[0] : "maze-common-frontend/src/main/resources/main/game/maze");
        Files.createDirectories(dir);
        ImageIO.write(meadow(), "png", dir.resolve("gameBackGround2.png").toFile());
        System.out.println("wrote gameBackGround2.png");
    }

    static BufferedImage meadow() {
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB);
        Random random = new Random(SEED);
        paintGround(image, random);
        Graphics2D g = image.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            shadedClumps(g, random);
            blades(g, random, 9000, 0.0);
            // burning patches sit between the layers of grass so the blades overlap their edges
            double[][] patches = {
                {170, 210, 70}, {610, 120, 55}, {880, 330, 80}, {330, 560, 60},
                {760, 690, 70}, {120, 830, 55}, {520, 900, 75}};
            for (double[] patch : patches) {
                burningPatch(g, random, patch[0], patch[1], patch[2]);
            }
            blades(g, random, 5000, 1.0);
            flowers(g, random);
            for (double[] patch : patches) {
                flames(g, random, patch[0], patch[1], patch[2]);
            }
            for (double[] patch : patches) {
                smoke(g, random, patch[0], patch[1], patch[2]);
            }
        } finally {
            g.dispose();
        }
        return image;
    }

    // --- ground: tileable value noise in a few octaves -----------------------------------------

    private static void paintGround(BufferedImage image, Random random) {
        int[] cells = {4, 8, 16, 32, 64};
        double[] weight = {0.35, 0.28, 0.2, 0.1, 0.07};
        double[][][] lattice = new double[cells.length][][];
        for (int o = 0; o < cells.length; o++) {
            lattice[o] = new double[cells[o]][cells[o]];
            for (double[] row : lattice[o]) {
                for (int i = 0; i < row.length; i++) {
                    row[i] = random.nextDouble();
                }
            }
        }
        Color dark = new Color(0x1f, 0x52, 0x1a);
        Color light = new Color(0x58, 0xa0, 0x2e);
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                double v = 0;
                for (int o = 0; o < cells.length; o++) {
                    v += weight[o] * sample(lattice[o], x / (double) SIZE, y / (double) SIZE);
                }
                v = Math.max(0, Math.min(1, (v - 0.25) / 0.5));
                image.setRGB(x, y, mix(dark, light, v).getRGB());
            }
        }
    }

    /** Smoothly interpolated lattice value at (u, v) in [0, 1); wraps, so the result tiles. */
    private static double sample(double[][] lattice, double u, double v) {
        int n = lattice.length;
        double fx = u * n;
        double fy = v * n;
        int x0 = (int) Math.floor(fx) % n;
        int y0 = (int) Math.floor(fy) % n;
        int x1 = (x0 + 1) % n;
        int y1 = (y0 + 1) % n;
        double tx = smooth(fx - Math.floor(fx));
        double ty = smooth(fy - Math.floor(fy));
        double top = lattice[y0][x0] * (1 - tx) + lattice[y0][x1] * tx;
        double bottom = lattice[y1][x0] * (1 - tx) + lattice[y1][x1] * tx;
        return top * (1 - ty) + bottom * ty;
    }

    private static double smooth(double t) {
        return t * t * (3 - 2 * t);
    }

    // --- grass ------------------------------------------------------------------------------------

    private static void shadedClumps(Graphics2D g, Random random) {
        for (int i = 0; i < 90; i++) {
            double x = random.nextDouble() * SIZE;
            double y = random.nextDouble() * SIZE;
            double r = 14 + random.nextDouble() * 34;
            g.setColor(new Color(8, 40, 10, 34));
            wrapped(x, y, r, (px, py) -> g.fill(new Ellipse2D.Double(px - r, py - r * 0.6, r * 2, r * 1.2)));
        }
    }

    /** {@code tint} 0 = deep greens, 1 = sunlit greens. */
    private static void blades(Graphics2D g, Random random, int count, double tint) {
        Color[] deep = {new Color(0x1d, 0x5a, 0x1c), new Color(0x27, 0x6b, 0x20), new Color(0x33, 0x7a, 0x25)};
        Color[] sun = {new Color(0x4f, 0x9a, 0x2c), new Color(0x69, 0xb1, 0x34), new Color(0x8c, 0xc8, 0x45), new Color(0x7d, 0xbd, 0x3c)};
        Color[] palette = tint < 0.5 ? deep : sun;
        for (int i = 0; i < count; i++) {
            double x = random.nextDouble() * SIZE;
            double y = random.nextDouble() * SIZE;
            double angle = Math.toRadians(-90 + (random.nextDouble() - 0.5) * 70);
            double length = 8 + random.nextDouble() * 12;
            Color base = palette[random.nextInt(palette.length)];
            double bend = (random.nextDouble() - 0.5) * 6;
            g.setColor(new Color(base.getRed(), base.getGreen(), base.getBlue(), 200));
            g.setStroke(new BasicStroke((float) (1.1 + random.nextDouble() * 1.1), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            double ex = Math.cos(angle) * length;
            double ey = Math.sin(angle) * length;
            wrapped(x, y, length, (px, py) -> {
                Path2D blade = new Path2D.Double();
                blade.moveTo(px, py);
                blade.quadTo(px + ex * 0.5 + bend, py + ey * 0.5, px + ex, py + ey);
                g.draw(blade);
            });
        }
    }

    private static void flowers(Graphics2D g, Random random) {
        Color[] petals = {new Color(0xff, 0xf2, 0x9a), new Color(0xff, 0xff, 0xff), new Color(0xf6, 0xd2, 0x4b), new Color(0xe9, 0x9a, 0xd2)};
        for (int i = 0; i < 260; i++) {
            double x = random.nextDouble() * SIZE;
            double y = random.nextDouble() * SIZE;
            Color c = petals[random.nextInt(petals.length)];
            g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 215));
            double r = 1.2 + random.nextDouble() * 1.1;
            wrapped(x, y, 4, (px, py) -> g.fill(new Ellipse2D.Double(px - r, py - r, r * 2, r * 2)));
        }
    }

    // --- burning patches -------------------------------------------------------------------------

    /** Scorched earth with a charred, glowing rim and embers. */
    private static void burningPatch(Graphics2D g, Random random, double cx, double cy, double radius) {
        // a wide dim glow on the grass round the fire
        glow(g, cx, cy, radius * 2.0, new Color(0xff, 0x8a, 0x1e, 70));
        // charred rim: brown-orange blobs, then the black scorch inside
        for (int i = 0; i < 9; i++) {
            double a = random.nextDouble() * Math.PI * 2;
            double d = radius * 0.45 * random.nextDouble();
            double r = radius * (0.55 + random.nextDouble() * 0.3);
            blob(g, cx + Math.cos(a) * d, cy + Math.sin(a) * d * 0.8, r, new Color(0x6a, 0x3a, 0x14, 120));
        }
        for (int i = 0; i < 9; i++) {
            double a = random.nextDouble() * Math.PI * 2;
            double d = radius * 0.35 * random.nextDouble();
            double r = radius * (0.4 + random.nextDouble() * 0.35);
            blob(g, cx + Math.cos(a) * d, cy + Math.sin(a) * d * 0.8, r, new Color(0x14, 0x0d, 0x09, 200));
        }
        // burnt blades round the edge of the patch
        g.setStroke(new BasicStroke(1.4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (int i = 0; i < 160; i++) {
            double a = random.nextDouble() * Math.PI * 2;
            double d = radius * (0.5 + random.nextDouble() * 0.75);
            double x = cx + Math.cos(a) * d;
            double y = cy + Math.sin(a) * d * 0.85;
            double angle = Math.toRadians(-90 + (random.nextDouble() - 0.5) * 80);
            double length = 6 + random.nextDouble() * 9;
            g.setColor(random.nextInt(3) == 0 ? new Color(0x8a, 0x4a, 0x14, 210) : new Color(0x1c, 0x14, 0x0e, 220));
            double ex = Math.cos(angle) * length;
            double ey = Math.sin(angle) * length;
            wrapped(x, y, length, (px, py) -> g.draw(new java.awt.geom.Line2D.Double(px, py, px + ex, py + ey)));
        }
        // glowing embers in the char
        for (int i = 0; i < 30; i++) {
            double a = random.nextDouble() * Math.PI * 2;
            double d = radius * 0.85 * Math.sqrt(random.nextDouble());
            double r = 1.2 + random.nextDouble() * 2.4;
            glow(g, cx + Math.cos(a) * d, cy + Math.sin(a) * d * 0.8, r * 2.2,
                    random.nextBoolean() ? new Color(0xff, 0x6a, 0x10, 210) : new Color(0xff, 0xb0, 0x30, 210));
        }
    }

    /** Flame tongues rising from the patch: red outside, orange, yellow core; tallest in the middle. */
    private static void flames(Graphics2D g, Random random, double cx, double cy, double radius) {
        int tongues = 9 + (int) (radius / 8);
        for (int i = 0; i < tongues; i++) {
            double a = random.nextDouble() * Math.PI * 2;
            double d = radius * 0.55 * Math.pow(random.nextDouble(), 1.4);
            double x = cx + Math.cos(a) * d;
            double y = cy + Math.sin(a) * d * 0.8;
            double middle = 1.25 - 0.8 * d / radius;
            double height = (18 + random.nextDouble() * 26) * middle;
            double width = (9 + random.nextDouble() * 9) * (0.8 + 0.3 * middle);
            double lean = (random.nextDouble() - 0.5) * 16;
            double sway = (random.nextDouble() - 0.5) * 10;
            glow(g, x, y - height * 0.4, height * 0.95, new Color(0xff, 0x7a, 0x14, 50));
            flame(g, x, y, width, height, lean, sway, new Color(0xcf, 0x24, 0x12, 215));
            flame(g, x, y, width * 0.7, height * 0.78, lean * 0.8, sway * 0.7, new Color(0xff, 0x86, 0x1a, 235));
            flame(g, x, y, width * 0.4, height * 0.5, lean * 0.5, sway * 0.4, new Color(0xff, 0xe0, 0x78, 245));
        }
    }

    private static void flame(Graphics2D g, double x, double y, double width, double height, double lean,
                              double sway, Color color) {
        g.setColor(color);
        wrapped(x, y, height, (px, py) -> {
            Path2D tongue = new Path2D.Double();
            tongue.moveTo(px - width / 2, py);
            // left side: bulges out, then sweeps in to a tip that curls with the sway
            tongue.curveTo(px - width * 0.85 + sway * 0.3, py - height * 0.3, px - width * 0.2 + sway, py - height * 0.62,
                    px + lean, py - height);
            // right side: back down, with its own bulge so the tongue is not symmetric
            tongue.curveTo(px + width * 0.1 + sway * 0.6, py - height * 0.66, px + width * 0.95 - sway * 0.2, py - height * 0.34,
                    px + width / 2, py);
            tongue.closePath();
            g.fill(tongue);
        });
    }

    private static void smoke(Graphics2D g, Random random, double cx, double cy, double radius) {
        for (int i = 0; i < 5; i++) {
            double drift = (random.nextDouble() - 0.3) * 30;
            double rise = 40 + i * 26 + random.nextDouble() * 14;
            double r = 12 + i * 5 + random.nextDouble() * 6;
            blob(g, cx + drift + i * 3, cy - rise - radius * 0.2, r, new Color(0x33, 0x33, 0x33, 74 - i * 10));
        }
    }

    // --- drawing helpers -------------------------------------------------------------------------

    private static void blob(Graphics2D g, double x, double y, double r, Color color) {
        Color clear = new Color(color.getRed(), color.getGreen(), color.getBlue(), 0);
        RadialGradientPaint paint = new RadialGradientPaint((float) x, (float) y, (float) r,
                new float[] {0f, 0.55f, 1f}, new Color[] {color, color, clear});
        glowPaint(g, x, y, r, color, paint);
    }

    private static void glow(Graphics2D g, double x, double y, double r, Color color) {
        Color clear = new Color(color.getRed(), color.getGreen(), color.getBlue(), 0);
        RadialGradientPaint paint = new RadialGradientPaint((float) x, (float) y, (float) Math.max(1, r),
                new float[] {0f, 1f}, new Color[] {color, clear});
        glowPaint(g, x, y, r, color, paint);
    }

    /** Fills a gradient circle at every wrapped position that touches the tile. */
    private static void glowPaint(Graphics2D g, double x, double y, double r, Color ignored, RadialGradientPaint paint) {
        wrapped(x, y, r, (px, py) -> {
            RadialGradientPaint moved = new RadialGradientPaint((float) px, (float) py, paint.getRadius(),
                    paint.getFractions(), paint.getColors());
            g.setPaint(moved);
            g.fill(new Ellipse2D.Double(px - r, py - r, r * 2, r * 2));
        });
    }

    /** Calls {@code draw} for (x, y) and for its copies shifted by a tile wherever {@code reach} crosses an edge. */
    private static void wrapped(double x, double y, double reach, Painter draw) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                double px = x + dx * SIZE;
                double py = y + dy * SIZE;
                if (px + reach >= 0 && px - reach <= SIZE && py + reach >= 0 && py - reach <= SIZE) {
                    draw.paint(px, py);
                }
            }
        }
    }

    private interface Painter {
        void paint(double x, double y);
    }

    private static Color mix(Color a, Color b, double t) {
        return new Color(
                (int) Math.round(a.getRed() + (b.getRed() - a.getRed()) * t),
                (int) Math.round(a.getGreen() + (b.getGreen() - a.getGreen()) * t),
                (int) Math.round(a.getBlue() + (b.getBlue() - a.getBlue()) * t));
    }
}
