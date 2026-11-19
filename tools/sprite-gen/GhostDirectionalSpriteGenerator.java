import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * Generates directional sprites ({@code -left/-right/-up/-down}) for ghost variants that
 * only have a single front-facing image, following the style of the hand-drawn
 * {@code ghost1-left/right.png}: the face slides towards the travel direction and the
 * lower body trails behind.
 *
 * <p>Run from the repository root with the Java 21 source launcher:
 * <pre>java tools/sprite-gen/GhostDirectionalSpriteGenerator.java [spriteDir]</pre>
 *
 * <ul>
 *   <li><b>left</b>: face shifted left, body below the face sheared to the right (trailing tail)</li>
 *   <li><b>right</b>: mirror of left</li>
 *   <li><b>up</b>: face erased (back view)</li>
 *   <li><b>down</b>: face shifted down (looking towards the viewer / downwards)</li>
 * </ul>
 * Face boxes are measured once per sprite and listed in {@link #GHOSTS}.
 */
public final class GhostDirectionalSpriteGenerator {

    /**
     * Ghost sprite, the pixel box containing its face features (eyes, brows, mouth), and
     * whether its sheet has a texture (grid lines / shading) that erased areas must continue.
     */
    record GhostSpec(String name, Rectangle face, boolean texturedSheet) {
    }

    static final List<GhostSpec> GHOSTS = List.of(
            new GhostSpec("ghost2", new Rectangle(170, 212, 166, 54), true),
            new GhostSpec("ghost3", new Rectangle(228, 90, 141, 116), false));

    private static final double FACE_SHIFT_X = 0.08;
    private static final double FACE_SHIFT_DOWN = 0.05;
    private static final double TAIL_SHEAR = 0.10;
    private static final int PIXEL_STEP = 8;
    private static final int OPAQUE = 200;

    private GhostDirectionalSpriteGenerator() {
    }

    public static void main(String[] args) throws IOException {
        Path dir = Path.of(args.length > 0 ? args[0]
                : "maze-common-frontend/src/main/resources/main/game/maze");
        for (GhostSpec ghost : GHOSTS) {
            File in = dir.resolve(ghost.name() + ".png").toFile();
            BufferedImage base = argb(ImageIO.read(in));
            BufferedImage left = left(base, ghost);
            write(dir, ghost.name() + "-left.png", left);
            write(dir, ghost.name() + "-right.png", mirror(left));
            write(dir, ghost.name() + "-up.png", eraseFace(base, ghost));
            write(dir, ghost.name() + "-down.png", down(base, ghost));
        }
    }

    static BufferedImage left(BufferedImage base, GhostSpec ghost) {
        Rectangle body = opaqueBounds(base);
        int dx = -(int) Math.round(body.width * FACE_SHIFT_X);
        BufferedImage moved = moveFace(base, ghost, dx, 0);
        Rectangle face = ghost.face();
        return shearBelow(moved, face.y + face.height, body, (int) Math.round(body.width * TAIL_SHEAR));
    }

    static BufferedImage down(BufferedImage base, GhostSpec ghost) {
        int dy = (int) Math.round(opaqueBounds(base).height * FACE_SHIFT_DOWN);
        return moveFace(base, ghost, 0, dy);
    }

    /** Erases the face and pastes it back offset by (dx, dy), only onto light body pixels. */
    static BufferedImage moveFace(BufferedImage base, GhostSpec ghost, int dx, int dy) {
        Rectangle face = ghost.face();
        BufferedImage out = eraseFace(base, ghost);
        for (int y = face.y; y < face.y + face.height; y++) {
            for (int x = face.x; x < face.x + face.width; x++) {
                int argb = base.getRGB(x, y);
                int tx = x + dx;
                int ty = y + dy;
                if (isFeature(argb) && inside(out, tx, ty) && isLightBody(out.getRGB(tx, ty))) {
                    out.setRGB(tx, ty, argb);
                }
            }
        }
        return out;
    }

    /**
     * Removes the face features (plus their anti-aliased rim) and fills the hole with the
     * sheet texture copied from the same column one texture period further down, so grid
     * lines or shading continue seamlessly. Flat sheets, or pixels without a usable donor,
     * get the sheet's dominant body colour.
     */
    static BufferedImage eraseFace(BufferedImage base, GhostSpec ghost) {
        Rectangle face = ghost.face();
        int bodyArgb = bodyColor(base, face);
        boolean[][] mask = featureMask(base, face, ERASE_RIM);
        int period = texturePeriod(base, face);
        BufferedImage out = copy(base);
        for (int y = face.y - ERASE_RIM; y < face.y + face.height + ERASE_RIM; y++) {
            for (int x = face.x - ERASE_RIM; x < face.x + face.width + ERASE_RIM; x++) {
                if (!masked(mask, face, x, y)) {
                    continue;
                }
                int donorY = y + period;
                boolean donorOk = ghost.texturedSheet() && inside(base, x, donorY) && isLightBody(base.getRGB(x, donorY))
                        && !masked(mask, face, x, donorY);
                out.setRGB(x, y, donorOk ? base.getRGB(x, donorY) : bodyArgb);
            }
        }
        return out;
    }

    private static final int ERASE_RIM = 2;

    /** Feature pixels in the face box, dilated by {@code rim} pixels; indexed relative to the box minus rim. */
    private static boolean[][] featureMask(BufferedImage img, Rectangle face, int rim) {
        int w = face.width + 2 * rim;
        int h = face.height + 2 * rim;
        boolean[][] mask = new boolean[h][w];
        for (int y = face.y; y < face.y + face.height; y++) {
            for (int x = face.x; x < face.x + face.width; x++) {
                if (!inside(img, x, y) || !isFeature(img.getRGB(x, y))) {
                    continue;
                }
                for (int dy = -rim; dy <= rim; dy++) {
                    for (int dx = -rim; dx <= rim; dx++) {
                        mask[y - face.y + rim + dy][x - face.x + rim + dx] = true;
                    }
                }
            }
        }
        return mask;
    }

    private static boolean masked(boolean[][] mask, Rectangle face, int x, int y) {
        int my = y - face.y + ERASE_RIM;
        int mx = x - face.x + ERASE_RIM;
        return my >= 0 && mx >= 0 && my < mask.length && mx < mask[0].length && mask[my][mx];
    }

    /**
     * Vertical offset (at least the face height) at which the sheet texture below the face
     * repeats best, found by comparing luminance of a clean strip with the strip shifted down.
     */
    static int texturePeriod(BufferedImage img, Rectangle face) {
        int minD = face.height + 2 * ERASE_RIM;
        int stripTop = face.y + face.height + ERASE_RIM;
        int best = minD;
        double bestScore = Double.MAX_VALUE;
        for (int d = minD; d <= minD + 40; d++) {
            double sum = 0;
            int n = 0;
            for (int y = stripTop; y < stripTop + 16; y++) {
                for (int x = face.x; x < face.x + face.width; x++) {
                    if (inside(img, x, y + d) && isLightBody(img.getRGB(x, y)) && isLightBody(img.getRGB(x, y + d))) {
                        sum += Math.abs(lum(img.getRGB(x, y)) - lum(img.getRGB(x, y + d)));
                        n++;
                    }
                }
            }
            if (n > 0 && sum / n < bestScore) {
                bestScore = sum / n;
                best = d;
            }
        }
        return best;
    }

    private static double lum(int argb) {
        return 0.299 * ((argb >> 16) & 0xFF) + 0.587 * ((argb >> 8) & 0xFF) + 0.114 * (argb & 0xFF);
    }

    /** Shifts rows below {@code startY} to the right, growing linearly to {@code maxShift} at the bottom. */
    static BufferedImage shearBelow(BufferedImage src, int startY, Rectangle body, int maxShift) {
        BufferedImage out = new BufferedImage(src.getWidth() + maxShift, src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int bottom = body.y + body.height - 1;
        for (int y = 0; y < src.getHeight(); y++) {
            int shift = 0;
            if (y > startY && bottom > startY) {
                double t = Math.min(1.0, (y - startY) / (double) (bottom - startY));
                // snap to the pixel-art step without exceeding maxShift (canvas was widened by maxShift)
                shift = (int) Math.floor(t * maxShift / PIXEL_STEP) * PIXEL_STEP;
            }
            for (int x = 0; x < src.getWidth(); x++) {
                out.setRGB(x + shift, y, src.getRGB(x, y));
            }
        }
        return out;
    }

    static BufferedImage mirror(BufferedImage src) {
        BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < src.getHeight(); y++) {
            for (int x = 0; x < src.getWidth(); x++) {
                out.setRGB(src.getWidth() - 1 - x, y, src.getRGB(x, y));
            }
        }
        return out;
    }

    /** Most common light opaque colour in the face box (the ghost's sheet colour). */
    static int bodyColor(BufferedImage img, Rectangle face) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int y = face.y; y < face.y + face.height; y++) {
            for (int x = face.x; x < face.x + face.width; x++) {
                if (inside(img, x, y) && isLightBody(img.getRGB(x, y))) {
                    counts.merge(img.getRGB(x, y), 1, Integer::sum);
                }
            }
        }
        return counts.entrySet().stream().max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse(0xFFF4F4F4);
    }

    static boolean isFeature(int argb) {
        if ((argb >>> 24) < OPAQUE) {
            return false;
        }
        int r = (argb >> 16) & 0xFF;
        int g = (argb >> 8) & 0xFF;
        int b = argb & 0xFF;
        // near-black only, so grey sheet shading counts as body, not as a feature
        boolean dark = 0.299 * r + 0.587 * g + 0.114 * b < 90;
        boolean red = r > 140 && g < 100 && b < 100;
        return dark || red;
    }

    static boolean isLightBody(int argb) {
        return (argb >>> 24) >= OPAQUE && !isFeature(argb);
    }

    static Rectangle opaqueBounds(BufferedImage img) {
        int minX = img.getWidth();
        int minY = img.getHeight();
        int maxX = -1;
        int maxY = -1;
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                if ((img.getRGB(x, y) >>> 24) >= OPAQUE) {
                    minX = Math.min(minX, x);
                    minY = Math.min(minY, y);
                    maxX = Math.max(maxX, x);
                    maxY = Math.max(maxY, y);
                }
            }
        }
        return maxX < 0 ? new Rectangle() : new Rectangle(minX, minY, maxX - minX + 1, maxY - minY + 1);
    }

    private static boolean inside(BufferedImage img, int x, int y) {
        return x >= 0 && y >= 0 && x < img.getWidth() && y < img.getHeight();
    }

    private static BufferedImage copy(BufferedImage src) {
        BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        out.setRGB(0, 0, src.getWidth(), src.getHeight(),
                src.getRGB(0, 0, src.getWidth(), src.getHeight(), null, 0, src.getWidth()), 0, src.getWidth());
        return out;
    }

    private static BufferedImage argb(BufferedImage src) {
        return src.getType() == BufferedImage.TYPE_INT_ARGB ? src : copy(src);
    }

    private static void write(Path dir, String name, BufferedImage img) throws IOException {
        ImageIO.write(img, "png", dir.resolve(name).toFile());
        System.out.println("wrote " + name);
    }
}
