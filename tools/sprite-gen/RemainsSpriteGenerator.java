import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * Generates the enemy death remains sprites used by {@code RemainsSpriteResolver}:
 * <ul>
 *   <li>{@code remains/burned-{1,2,3}.png}: pile of coal lumps with glowing cracks on an ash ring
 *       (fire / bomb deaths, any enemy)</li>
 *   <li>{@code remains/<variant>-shot.png}: the enemy's bleeding sprite lying on its side in a pool
 *       of blood, partly splattered (chunks torn out, splashes around)</li>
 *   <li>{@code remains/<variant>-crushed.png}: the enemy flattened onto a large blood splat</li>
 *   <li>{@code remains/generic-shot.png} / {@code generic-crushed.png}: blood only, the fallback for
 *       enemies without their own remains</li>
 * </ul>
 * All sprites are 256x256 with transparent background. Ground marks (ash, blood, coal) are drawn on
 * a 64x64 grid and scaled up 4x so they match the chunky pixel-art look of the enemy sprites.
 * Output is deterministic (seeded from the file name).
 *
 * <p>Needs the damage-state sprites ({@code *-bleeding*.png}); run {@code DamageSpriteGenerator} first.
 * Run from the repository root with the Java 21 source launcher:
 * <pre>java tools/sprite-gen/RemainsSpriteGenerator.java [spriteDir]</pre>
 */
public final class RemainsSpriteGenerator {

    static final int SIZE = 256;
    static final int GRID = 4;
    static final int LOGICAL = SIZE / GRID;
    static final int BURNED_VARIANTS = 3;
    static final List<String> VARIANTS =
            List.of("zombie1", "zombie2", "zombie3", "ghost1", "ghost2", "ghost3", "pumpkinbomber");

    private static final Color BLOOD_DARK = new Color(0x5a, 0x06, 0x08);
    private static final Color BLOOD = new Color(0x8e, 0x0e, 0x10);
    private static final Color BLOOD_WET = new Color(0xc4, 0x22, 0x22);
    private static final Color FLESH = new Color(0xd8, 0x6a, 0x6a);

    private RemainsSpriteGenerator() {
    }

    public static void main(String[] args) throws IOException {
        Path dir = Path.of(args.length > 0 ? args[0]
                : "maze-common-frontend/src/main/resources/main/game/maze");
        Path out = dir.resolve("remains");
        Files.createDirectories(out);
        int written = 0;
        for (int i = 1; i <= BURNED_VARIANTS; i++) {
            write(out, "burned-" + i + ".png", burned(new Random(("burned-" + i).hashCode())));
            written++;
        }
        write(out, "generic-shot.png", shot(null, new Random("generic-shot".hashCode())));
        write(out, "generic-crushed.png", crushed(null, new Random("generic-crushed".hashCode())));
        written += 2;
        for (String variant : VARIANTS) {
            BufferedImage body = bleedingBody(dir, variant);
            if (body == null) {
                System.err.println("skip (no bleeding sprite): " + variant);
                continue;
            }
            write(out, variant + "-shot.png", shot(body, new Random((variant + "-shot").hashCode())));
            write(out, variant + "-crushed.png", crushed(body, new Random((variant + "-crushed").hashCode())));
            written += 2;
        }
        System.out.println(written + " sprites written to " + out.toAbsolutePath());
    }

    // ------------------------------------------------------------------ burned

    static BufferedImage burned(Random rnd) {
        BufferedImage grid = new BufferedImage(LOGICAL, LOGICAL, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = grid.createGraphics();
        try {
            // ash ring and soot
            g.setColor(new Color(0x55, 0x52, 0x50, 70));
            g.fill(new Ellipse2D.Double(10, 30, 44, 22));
            g.setColor(new Color(0x2a, 0x28, 0x27, 120));
            g.fill(new Ellipse2D.Double(15, 33, 34, 16));
            // coal lumps: back row first, piled higher in the middle
            int lumps = 11 + rnd.nextInt(4);
            for (int i = 0; i < lumps; i++) {
                double t = rnd.nextDouble();
                double cx = 16 + 32 * t;
                double mound = 1.0 - Math.abs(t - 0.5) * 2; // 1 in the middle
                double cy = 44 - mound * 14 - rnd.nextDouble() * 6 + i * 0.4;
                double r = 4 + rnd.nextDouble() * 4 + mound * 2;
                coalLump(g, rnd, cx, cy, r);
            }
            // glowing cracks and embers
            for (int i = 0; i < 9; i++) {
                int x = 18 + rnd.nextInt(28);
                int y = 26 + rnd.nextInt(18);
                g.setColor(rnd.nextInt(3) == 0 ? new Color(0xff, 0xd0, 0x60) : new Color(0xff, 0x6a, 0x10));
                g.fillRect(x, y, 1, 1);
                if (rnd.nextBoolean()) {
                    g.setColor(new Color(0xd0, 0x40, 0x08));
                    g.fillRect(x + 1, y, 1, 1);
                }
            }
            // a few pale ash flecks
            g.setColor(new Color(0xb8, 0xb2, 0xaa, 200));
            for (int i = 0; i < 6; i++) {
                g.fillRect(14 + rnd.nextInt(36), 30 + rnd.nextInt(20), 1, 1);
            }
        } finally {
            g.dispose();
        }
        return upscale(grid);
    }

    private static void coalLump(Graphics2D g, Random rnd, double cx, double cy, double r) {
        int points = 5 + rnd.nextInt(3);
        Polygon lump = new Polygon();
        for (int i = 0; i < points; i++) {
            double a = Math.PI * 2 * i / points + rnd.nextDouble() * 0.4;
            double d = r * (0.7 + 0.3 * rnd.nextDouble());
            lump.addPoint((int) Math.round(cx + Math.cos(a) * d), (int) Math.round(cy + Math.sin(a) * d * 0.8));
        }
        g.setColor(new Color(0x16, 0x15, 0x15));
        g.fillPolygon(lump);
        // top-left facet highlight
        g.setColor(new Color(0x3c, 0x38, 0x36));
        g.fillRect((int) Math.round(cx - r * 0.4), (int) Math.round(cy - r * 0.5), Math.max(1, (int) (r * 0.5)), 1);
        g.setColor(new Color(0x26, 0x24, 0x23));
        g.fillRect((int) Math.round(cx - r * 0.2), (int) Math.round(cy - r * 0.2), Math.max(1, (int) (r * 0.4)), 1);
    }

    // ------------------------------------------------------------------ shot

    static BufferedImage shot(BufferedImage body, Random rnd) {
        BufferedImage grid = new BufferedImage(LOGICAL, LOGICAL, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = grid.createGraphics();
        try {
            blob(g, rnd, 32, 38, 18, 9, 9, BLOOD_DARK);
            blob(g, rnd, 31, 37, 14, 7, 7, BLOOD);
            blob(g, rnd, 28, 35, 5, 3, 3, BLOOD_WET);
            splashes(g, rnd, 32, 36, 26, 10);
        } finally {
            g.dispose();
        }
        BufferedImage out = upscale(grid);
        if (body != null) {
            BufferedImage lying = rotate90(fit(body, 170, 120));
            lying = darken(lying, 0.82f);
            Graphics2D bg = out.createGraphics();
            try {
                int x = (SIZE - lying.getWidth()) / 2;
                int y = (SIZE - lying.getHeight()) / 2 + 6;
                bg.drawImage(lying, x, y, null);
            } finally {
                bg.dispose();
            }
            // partly splattered: chunks torn out of the body, wounds and drops on top
            tearChunks(out, rnd, 2);
            Graphics2D top = out.createGraphics();
            try {
                for (int i = 0; i < 5; i++) {
                    int dx = 70 + rnd.nextInt(116);
                    int dy = 90 + rnd.nextInt(70);
                    top.setColor(rnd.nextBoolean() ? BLOOD : BLOOD_WET);
                    top.fillRect(dx - dx % GRID, dy - dy % GRID, GRID * (1 + rnd.nextInt(2)), GRID);
                }
            } finally {
                top.dispose();
            }
        }
        return out;
    }

    /**
     * Tears ragged chunks out of the body: clusters of small bites filled with dark blood,
     * with a few flesh flecks along the torn edge.
     */
    private static void tearChunks(BufferedImage img, Random rnd, int count) {
        for (int c = 0; c < count; c++) {
            int cx = 90 + rnd.nextInt(76);
            int cy = 108 + rnd.nextInt(40);
            int bites = 3 + rnd.nextInt(3);
            for (int b = 0; b < bites; b++) {
                int bx = cx + rnd.nextInt(17) - 8;
                int by = cy + rnd.nextInt(13) - 6;
                int r = 4 + rnd.nextInt(5);
                for (int gy = by - r; gy <= by + r; gy += GRID) {
                    for (int gx = bx - r; gx <= bx + r; gx += GRID) {
                        int x0 = gx - Math.floorMod(gx, GRID);
                        int y0 = gy - Math.floorMod(gy, GRID);
                        if (x0 < 0 || y0 < 0 || x0 + GRID > SIZE || y0 + GRID > SIZE
                                || (img.getRGB(x0, y0) >>> 24) < 128 || Math.hypot(gx - bx, gy - by) > r) {
                            continue;
                        }
                        boolean edge = Math.hypot(gx - bx, gy - by) > r - GRID;
                        Color color = edge && rnd.nextInt(4) == 0 ? FLESH : (rnd.nextInt(3) == 0 ? BLOOD : BLOOD_DARK);
                        for (int y = y0; y < y0 + GRID; y++) {
                            for (int x = x0; x < x0 + GRID; x++) {
                                img.setRGB(x, y, color.getRGB());
                            }
                        }
                    }
                }
            }
        }
    }

    // ------------------------------------------------------------------ crushed

    static BufferedImage crushed(BufferedImage body, Random rnd) {
        BufferedImage grid = new BufferedImage(LOGICAL, LOGICAL, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = grid.createGraphics();
        try {
            // radial spikes of a big splat
            int spikes = 11 + rnd.nextInt(4);
            for (int i = 0; i < spikes; i++) {
                double a = Math.PI * 2 * i / spikes + rnd.nextDouble() * 0.3;
                double len = 16 + rnd.nextDouble() * 12;
                spike(g, 32, 34, a, len, 2.5 + rnd.nextDouble() * 1.5, BLOOD_DARK);
            }
            blob(g, rnd, 32, 34, 15, 11, 10, BLOOD_DARK);
            blob(g, rnd, 32, 34, 12, 8, 8, BLOOD);
            blob(g, rnd, 29, 32, 5, 3, 4, BLOOD_WET);
            splashes(g, rnd, 32, 34, 30, 16);
        } finally {
            g.dispose();
        }
        BufferedImage out = upscale(grid);
        if (body != null) {
            BufferedImage flat = fit(body, 150, 200);
            flat = scale(flat, (int) (flat.getWidth() * 1.3), Math.max(GRID, (int) (flat.getHeight() * 0.42)));
            flat = tintRed(darken(flat, 0.85f), 0.18f);
            Graphics2D bg = out.createGraphics();
            try {
                bg.drawImage(flat, (SIZE - flat.getWidth()) / 2, 136 - flat.getHeight() / 2, null);
            } finally {
                bg.dispose();
            }
        }
        // gore chunks scattered over everything
        Graphics2D top = out.createGraphics();
        try {
            for (int i = 0; i < 7; i++) {
                double a = rnd.nextDouble() * Math.PI * 2;
                double d = 30 + rnd.nextDouble() * 70;
                int x = (int) (128 + Math.cos(a) * d);
                int y = (int) (136 + Math.sin(a) * d * 0.7);
                x -= x % GRID;
                y -= y % GRID;
                top.setColor(BLOOD_DARK);
                top.fillRect(x, y, GRID * 2, GRID * 2);
                top.setColor(FLESH);
                top.fillRect(x, y, GRID, GRID);
            }
        } finally {
            top.dispose();
        }
        return out;
    }

    // ------------------------------------------------------------------ shapes

    /** Irregular puddle: an ellipse with lumpy edge circles, on the logical grid. */
    private static void blob(Graphics2D g, Random rnd, double cx, double cy, double rx, double ry,
                             int lumps, Color color) {
        g.setColor(color);
        g.fill(new Ellipse2D.Double(cx - rx, cy - ry, rx * 2, ry * 2));
        for (int i = 0; i < lumps; i++) {
            double a = rnd.nextDouble() * Math.PI * 2;
            double r = Math.min(rx, ry) * (0.35 + rnd.nextDouble() * 0.4);
            double x = cx + Math.cos(a) * rx * 0.85;
            double y = cy + Math.sin(a) * ry * 0.85;
            g.fill(new Ellipse2D.Double(x - r, y - r, r * 2, r * 2));
        }
    }

    private static void splashes(Graphics2D g, Random rnd, double cx, double cy, double reach, int count) {
        for (int i = 0; i < count; i++) {
            double a = rnd.nextDouble() * Math.PI * 2;
            double d = reach * (0.6 + rnd.nextDouble() * 0.4);
            int x = (int) Math.round(cx + Math.cos(a) * d);
            int y = (int) Math.round(cy + Math.sin(a) * d * 0.6);
            g.setColor(rnd.nextInt(3) == 0 ? BLOOD_WET : BLOOD);
            int s = 1 + rnd.nextInt(2);
            g.fillRect(x, y, s, s);
        }
    }

    private static void spike(Graphics2D g, double cx, double cy, double angle, double len, double width,
                              Color color) {
        double ex = cx + Math.cos(angle) * len;
        double ey = cy + Math.sin(angle) * len * 0.7;
        double px = -Math.sin(angle) * width;
        double py = Math.cos(angle) * width;
        Polygon p = new Polygon();
        p.addPoint((int) Math.round(cx + px), (int) Math.round(cy + py));
        p.addPoint((int) Math.round(ex), (int) Math.round(ey));
        p.addPoint((int) Math.round(cx - px), (int) Math.round(cy - py));
        g.setColor(color);
        g.fillPolygon(p);
        g.fill(new Ellipse2D.Double(ex - width * 0.6, ey - width * 0.6, width * 1.2, width * 1.2));
    }

    // ------------------------------------------------------------------ image helpers

    private static BufferedImage bleedingBody(Path dir, String variant) throws IOException {
        for (String name : List.of(variant + "-bleeding-down.png", variant + "-bleeding.png")) {
            File f = dir.resolve(name).toFile();
            if (f.isFile()) {
                return cropToContent(argb(ImageIO.read(f)));
            }
        }
        return null;
    }

    static BufferedImage cropToContent(BufferedImage src) {
        int minX = src.getWidth();
        int minY = src.getHeight();
        int maxX = -1;
        int maxY = -1;
        for (int y = 0; y < src.getHeight(); y++) {
            for (int x = 0; x < src.getWidth(); x++) {
                if ((src.getRGB(x, y) >>> 24) > 16) {
                    minX = Math.min(minX, x);
                    minY = Math.min(minY, y);
                    maxX = Math.max(maxX, x);
                    maxY = Math.max(maxY, y);
                }
            }
        }
        if (maxX < 0) {
            return src;
        }
        return src.getSubimage(minX, minY, maxX - minX + 1, maxY - minY + 1);
    }

    /** Scales to fit inside maxW x maxH, keeping the aspect ratio (nearest neighbour, pixel art). */
    static BufferedImage fit(BufferedImage src, int maxW, int maxH) {
        double s = Math.min(maxW / (double) src.getWidth(), maxH / (double) src.getHeight());
        return scale(src, Math.max(1, (int) (src.getWidth() * s)), Math.max(1, (int) (src.getHeight() * s)));
    }

    static BufferedImage scale(BufferedImage src, int w, int h) {
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g.drawImage(src, 0, 0, w, h, null);
        } finally {
            g.dispose();
        }
        return out;
    }

    /** Rotates 90 degrees clockwise: the body lies on its side. */
    static BufferedImage rotate90(BufferedImage src) {
        BufferedImage out = new BufferedImage(src.getHeight(), src.getWidth(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();
        try {
            AffineTransform t = new AffineTransform();
            t.translate(src.getHeight(), 0);
            t.rotate(Math.PI / 2);
            g.drawImage(src, t, null);
        } finally {
            g.dispose();
        }
        return out;
    }

    static BufferedImage darken(BufferedImage src, float factor) {
        BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < src.getHeight(); y++) {
            for (int x = 0; x < src.getWidth(); x++) {
                int argb = src.getRGB(x, y);
                int r = (int) (((argb >> 16) & 0xFF) * factor);
                int gr = (int) (((argb >> 8) & 0xFF) * factor);
                int b = (int) ((argb & 0xFF) * factor);
                out.setRGB(x, y, (argb & 0xFF000000) | (r << 16) | (gr << 8) | b);
            }
        }
        return out;
    }

    static BufferedImage tintRed(BufferedImage src, float amount) {
        BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < src.getHeight(); y++) {
            for (int x = 0; x < src.getWidth(); x++) {
                int argb = src.getRGB(x, y);
                int r = (int) (((argb >> 16) & 0xFF) * (1 - amount) + 0x8e * amount);
                int gr = (int) (((argb >> 8) & 0xFF) * (1 - amount) + 0x0e * amount);
                int b = (int) ((argb & 0xFF) * (1 - amount) + 0x10 * amount);
                out.setRGB(x, y, (argb & 0xFF000000) | (r << 16) | (gr << 8) | b);
            }
        }
        return out;
    }

    private static BufferedImage upscale(BufferedImage grid) {
        return scale(grid, SIZE, SIZE);
    }

    private static BufferedImage argb(BufferedImage src) {
        BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();
        try {
            g.setComposite(AlphaComposite.Src);
            g.drawImage(src, 0, 0, null);
        } finally {
            g.dispose();
        }
        return out;
    }

    private static void write(Path dir, String name, BufferedImage image) throws IOException {
        ImageIO.write(image, "png", dir.resolve(name).toFile());
        System.out.println("wrote remains/" + name);
    }
}
