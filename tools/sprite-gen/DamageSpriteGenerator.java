import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * Generates enemy damage-state sprites (issue #80) from the existing healthy sprites.
 *
 * <p>Run with the Java 21 source launcher from the repository root:
 * <pre>java tools/sprite-gen/DamageSpriteGenerator.java [spriteDir]</pre>
 * The default sprite directory is
 * {@code maze-common-frontend/src/main/resources/main/game/maze} relative to the
 * current working directory.
 *
 * <p>States are progressive: DAMAGED adds scratches and bruises, BLEEDING adds
 * blood splatter and drips on top of that, TIRED adds desaturation, darkening
 * and a slight slump on top of that. All marks are drawn on a pixel grid sized
 * from the sprite's silhouette so they match the pixel-art style, are clipped to
 * the original silhouette (alpha is never changed), and are seeded from the
 * base file name so output is reproducible and marks persist across states.
 *
 * <p>Output names follow {@code DamageStateResolver.deriveStatePath}:
 * {@code zombie1-right.png -> zombie1-damaged-right.png},
 * {@code ghost2.png -> ghost2-damaged.png}.
 */
public final class DamageSpriteGenerator {

    enum State { DAMAGED, BLEEDING, TIRED }

    private static final List<String> DIRECTIONS = List.of("left", "right", "up", "down");
    private static final int SOLID_ALPHA = 200;
    private static final int GRID_ROWS = 60;

    private static final int DARK_SCAR = 0x2A1A1A;
    private static final int SCAR_EDGE = 0x5A3A30;
    private static final int NICK = 0x8B1010;
    private static final int BLOOD = 0xA01010;
    private static final int BLOOD_DARK = 0x6E0808;
    private static final int BLOOD_HIGHLIGHT = 0xD02828;

    private DamageSpriteGenerator() {
    }

    public static void main(String[] args) throws IOException {
        Path dir = Path.of(args.length > 0 ? args[0]
                : "maze-common-frontend/src/main/resources/main/game/maze");
        int written = 0;
        for (String base : baseSprites()) {
            File in = dir.resolve(base).toFile();
            if (!in.isFile()) {
                System.err.println("skip (missing base): " + in);
                continue;
            }
            BufferedImage healthy = ImageIO.read(in);
            for (State state : State.values()) {
                String outName = deriveName(base, state);
                // seed per base sprite so marks stay in place as the state progresses
                BufferedImage out = render(healthy, state, base.hashCode());
                ImageIO.write(out, "png", dir.resolve(outName).toFile());
                System.out.println("wrote " + outName);
                written++;
            }
        }
        System.out.println(written + " sprites written to " + dir.toAbsolutePath());
    }

    /** Healthy sprites that enemy CharacterTypes in opponentModel.xmi resolve to. */
    static List<String> baseSprites() {
        List<String> bases = new ArrayList<>();
        for (int variant = 1; variant <= 3; variant++) {
            for (String dir : DIRECTIONS) {
                bases.add("zombie" + variant + "-" + dir + ".png");
            }
        }
        bases.addAll(List.of("ghost1.png", "ghost1-left.png", "ghost1-right.png",
                "ghost2.png", "ghost3.png", "pumpkinbomber.png"));
        // ghost2/3 directional sprites come from GhostDirectionalSpriteGenerator
        for (String ghost : List.of("ghost2", "ghost3")) {
            for (String dir : DIRECTIONS) {
                bases.add(ghost + "-" + dir + ".png");
            }
        }
        return bases;
    }

    /** Mirrors DamageStateResolver.deriveStatePath (verified by DamageStateSpriteAssetsTest). */
    static String deriveName(String base, State state) {
        String label = state.name().toLowerCase(Locale.ROOT);
        String stem = base.substring(0, base.lastIndexOf('.'));
        String ext = base.substring(base.lastIndexOf('.'));
        for (String dir : DIRECTIONS) {
            String suffix = "-" + dir;
            if (stem.endsWith(suffix)) {
                return stem.substring(0, stem.length() - suffix.length()) + "-" + label + suffix + ext;
            }
        }
        return stem + "-" + label + ext;
    }

    static BufferedImage render(BufferedImage src, State state, long seed) {
        BufferedImage img = copyArgb(src);
        Grid grid = Grid.of(img);
        if (grid == null) {
            return img;
        }
        Random rnd = new Random(seed);
        drawDamage(img, grid, rnd);
        adjust(img, 0.92, 0.0, 0.0);
        if (state == State.DAMAGED) {
            return img;
        }
        drawBlood(img, grid, rnd);
        adjust(img, 1.0, 0.0, 0.10);
        if (state == State.BLEEDING) {
            return img;
        }
        adjust(img, 0.80, 0.50, 0.0);
        return slump(img, grid, 0.94);
    }

    private static void drawDamage(BufferedImage img, Grid grid, Random rnd) {
        int scratches = 5 + rnd.nextInt(3);
        for (int i = 0; i < scratches; i++) {
            int[] cell = grid.randomSolidCell(rnd);
            if (cell == null) {
                return;
            }
            int len = 3 + rnd.nextInt(4);
            int dx = rnd.nextBoolean() ? 1 : -1;
            for (int s = 0; s < len; s++) {
                int cx = cell[0] + s * dx;
                int cy = cell[1] + s;
                grid.paint(img, cx, cy, DARK_SCAR);
                grid.paint(img, cx, cy - 1, SCAR_EDGE);
            }
        }
        int nicks = 4 + rnd.nextInt(3);
        for (int i = 0; i < nicks; i++) {
            int[] cell = grid.randomSolidCell(rnd);
            if (cell != null) {
                grid.paint(img, cell[0], cell[1], NICK);
            }
        }
    }

    private static void drawBlood(BufferedImage img, Grid grid, Random rnd) {
        int blobs = 4 + rnd.nextInt(3);
        for (int i = 0; i < blobs; i++) {
            int[] c = grid.randomSolidCell(rnd);
            if (c == null) {
                return;
            }
            int r = 1 + rnd.nextInt(2);
            for (int y = -r; y <= r; y++) {
                for (int x = -r; x <= r; x++) {
                    if (x * x + y * y <= r * r + rnd.nextInt(2)) {
                        grid.paint(img, c[0] + x, c[1] + y, (x + y) % 2 == 0 ? BLOOD : BLOOD_DARK);
                    }
                }
            }
            grid.paint(img, c[0] - 1, c[1] - 1, BLOOD_HIGHLIGHT);
            int drip = 2 + rnd.nextInt(5);
            int dripX = c[0] + rnd.nextInt(2 * r + 1) - r;
            for (int d = 1; d <= drip; d++) {
                if (!grid.isSolid(dripX, c[1] + r + d)) {
                    break;
                }
                grid.paint(img, dripX, c[1] + r + d, d == drip ? BLOOD_DARK : BLOOD);
            }
        }
    }

    /** Scales brightness, desaturates by {@code desat} (0..1) and adds a red tint (0..1). */
    private static void adjust(BufferedImage img, double brightness, double desat, double redTint) {
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int argb = img.getRGB(x, y);
                int a = argb >>> 24;
                if (a == 0) {
                    continue;
                }
                double r = (argb >> 16) & 0xFF;
                double g = (argb >> 8) & 0xFF;
                double b = argb & 0xFF;
                double lum = 0.299 * r + 0.587 * g + 0.114 * b;
                r = (r + (lum - r) * desat) * brightness;
                g = (g + (lum - g) * desat) * brightness;
                b = (b + (lum - b) * desat) * brightness * (desat > 0 ? 1.06 : 1.0);
                r = r + (200 - r) * redTint;
                g = g * (1 - redTint);
                b = b * (1 - redTint);
                img.setRGB(x, y, (a << 24) | (clamp(r) << 16) | (clamp(g) << 8) | clamp(b));
            }
        }
    }

    /** Squashes the sprite vertically towards its feet (nearest neighbour, keeps canvas size). */
    private static BufferedImage slump(BufferedImage img, Grid grid, double factor) {
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int bottom = grid.maxY;
        for (int y = 0; y <= bottom; y++) {
            int srcY = (int) Math.round(bottom - (bottom - y) / factor);
            if (srcY < 0) {
                continue;
            }
            for (int x = 0; x < img.getWidth(); x++) {
                out.setRGB(x, y, img.getRGB(x, srcY));
            }
        }
        for (int y = bottom + 1; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                out.setRGB(x, y, img.getRGB(x, y));
            }
        }
        return out;
    }

    private static BufferedImage copyArgb(BufferedImage src) {
        BufferedImage img = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g = img.createGraphics();
        try {
            g.drawImage(src, 0, 0, null);
        } finally {
            g.dispose();
        }
        return img;
    }

    private static int clamp(double v) {
        return (int) Math.max(0, Math.min(255, Math.round(v)));
    }

    /** Pixel-art grid aligned to the sprite's opaque bounding box. */
    private record Grid(int minX, int minY, int maxX, int maxY, int cell, BufferedImage mask) {

        static Grid of(BufferedImage img) {
            int minX = Integer.MAX_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxX = -1;
            int maxY = -1;
            for (int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    if ((img.getRGB(x, y) >>> 24) >= SOLID_ALPHA) {
                        minX = Math.min(minX, x);
                        minY = Math.min(minY, y);
                        maxX = Math.max(maxX, x);
                        maxY = Math.max(maxY, y);
                    }
                }
            }
            if (maxX < 0) {
                return null;
            }
            int cell = Math.max(3, Math.round((maxY - minY + 1) / (float) GRID_ROWS));
            return new Grid(minX, minY, maxX, maxY, cell, copyArgb(img));
        }

        boolean isSolid(int cx, int cy) {
            int px = minX + cx * cell + cell / 2;
            int py = minY + cy * cell + cell / 2;
            return px >= 0 && py >= 0 && px < mask.getWidth() && py < mask.getHeight()
                    && (mask.getRGB(px, py) >>> 24) >= SOLID_ALPHA;
        }

        int[] randomSolidCell(Random rnd) {
            int cols = (maxX - minX) / cell + 1;
            int rows = (maxY - minY) / cell + 1;
            for (int tries = 0; tries < 500; tries++) {
                // keep marks off the outermost rows (feet / top of head)
                int cx = rnd.nextInt(cols);
                int cy = rows / 8 + rnd.nextInt(Math.max(1, rows * 3 / 4));
                if (isSolid(cx, cy) && isSolid(cx - 1, cy) && isSolid(cx + 1, cy)) {
                    return new int[] {cx, cy};
                }
            }
            return null;
        }

        /** Paints one grid cell, only on pixels that are opaque in the original sprite. */
        void paint(BufferedImage img, int cx, int cy, int rgb) {
            if (!isSolid(cx, cy)) {
                return;
            }
            int x0 = minX + cx * cell;
            int y0 = minY + cy * cell;
            for (int y = y0; y < Math.min(y0 + cell, img.getHeight()); y++) {
                for (int x = x0; x < Math.min(x0 + cell, img.getWidth()); x++) {
                    int a = mask.getRGB(x, y) >>> 24;
                    if (a >= SOLID_ALPHA) {
                        img.setRGB(x, y, (a << 24) | rgb);
                    }
                }
            }
        }
    }
}
