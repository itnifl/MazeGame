import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

/**
 * Generates the wall builder sprite {@code zombie-builder.png} from the regular zombie sprite
 * ({@code zombie1-down.png}): a construction worker zombie with a yellow hard hat, an orange hi-vis vest with
 * reflective stripes and a hammer in its hand. Deterministic.
 *
 * <pre>java tools/sprite-gen/WallBuilderSpriteGenerator.java [spriteDir]</pre>
 */
public final class WallBuilderSpriteGenerator {

    private WallBuilderSpriteGenerator() {
    }

    public static void main(String[] args) throws IOException {
        Path dir = Path.of(args.length > 0 ? args[0] : "maze-common-frontend/src/main/resources/main/game/maze");
        BufferedImage zombie = ImageIO.read(dir.resolve("zombie1-down.png").toFile());
        BufferedImage out = new BufferedImage(zombie.getWidth(), zombie.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < out.getHeight(); y++) {
            for (int x = 0; x < out.getWidth(); x++) {
                out.setRGB(x, y, zombie.getRGB(x, y));
            }
        }
        Graphics2D g = out.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = out.getWidth();
            int h = out.getHeight();
            // hi-vis vest over the body, only where the zombie is
            g.setClip(new java.awt.geom.Area(opaque(zombie)));
            g.setColor(new Color(0xff, 0x7a, 0x12, 235));
            g.fillRect(w * 22 / 100, h * 38 / 100, w * 56 / 100, h * 34 / 100);
            g.setColor(new Color(0xe8, 0xf0, 0xc0, 245));   // reflective stripes
            g.fillRect(w * 22 / 100, h * 47 / 100, w * 56 / 100, h * 3 / 100);
            g.fillRect(w * 22 / 100, h * 58 / 100, w * 56 / 100, h * 3 / 100);
            g.setColor(new Color(0xb8, 0x50, 0x08, 255));   // the open front of the vest
            g.fillRect(w * 49 / 100, h * 38 / 100, w * 2 / 100, h * 34 / 100);
            g.setClip(null);
            // yellow hard hat with a brim and a ridge
            g.setColor(new Color(0xf5, 0xc4, 0x0a));
            g.fillArc(w * 27 / 100, h * 1 / 100, w * 46 / 100, h * 24 / 100, 0, 180);
            g.setColor(new Color(0xc9, 0x9a, 0x00));
            g.fillRoundRect(w * 22 / 100, h * 12 / 100, w * 56 / 100, h * 4 / 100, 14, 14);
            g.setColor(new Color(0xff, 0xe0, 0x60));
            g.fillRect(w * 47 / 100, h * 2 / 100, w * 6 / 100, h * 11 / 100);
            // a hammer in the hand
            g.setColor(new Color(0x7a, 0x4a, 0x22));
            g.setStroke(new BasicStroke(Math.max(8, w / 30f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawLine(w * 80 / 100, h * 52 / 100, w * 92 / 100, h * 30 / 100);
            g.setColor(new Color(0x55, 0x5b, 0x66));
            g.fillRoundRect(w * 84 / 100, h * 24 / 100, w * 14 / 100, h * 8 / 100, 10, 10);
        } finally {
            g.dispose();
        }
        ImageIO.write(out, "png", dir.resolve("zombie-builder.png").toFile());
        System.out.println("wrote zombie-builder.png");
    }

    /** The part of the image that is not transparent, as a path (so the vest stays on the body). */
    private static java.awt.geom.Path2D opaque(BufferedImage image) {
        java.awt.geom.Path2D.Double path = new java.awt.geom.Path2D.Double(java.awt.geom.Path2D.WIND_NON_ZERO);
        for (int y = 0; y < image.getHeight(); y++) {
            int start = -1;
            for (int x = 0; x <= image.getWidth(); x++) {
                boolean solid = x < image.getWidth() && (image.getRGB(x, y) >>> 24) > 100;
                if (solid && start < 0) {
                    start = x;
                } else if (!solid && start >= 0) {
                    path.append(new java.awt.Rectangle(start, y, x - start, 1), false);
                    start = -1;
                }
            }
        }
        return path;
    }
}
