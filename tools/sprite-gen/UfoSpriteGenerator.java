import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;

/**
 * Generates the sprites of the UFO retaliation (level 7) into {@code main/game/maze/ufo/}:
 * <ul>
 *   <li>{@code ufo.png}: a flying saucer seen from a slight angle, a glass dome, a rim of lights and a glowing hatch,
 *       on a 96x96 transparent canvas;</li>
 *   <li>{@code ufo-beam.png}: the glowing disc a beam makes on the ground: a green-white core, a bright ring and a soft
 *       halo (the game stacks it along the beam to draw the column of light).</li>
 * </ul>
 * Deterministic.
 *
 * <pre>java tools/sprite-gen/UfoSpriteGenerator.java [spriteDir]</pre>
 */
public final class UfoSpriteGenerator {

    static final int SIZE = 96;

    private UfoSpriteGenerator() {
    }

    public static void main(String[] args) throws IOException {
        Path dir = Path.of(args.length > 0 ? args[0] : "maze-common-frontend/src/main/resources/main/game/maze").resolve("ufo");
        Files.createDirectories(dir);
        ImageIO.write(ufo(), "png", dir.resolve("ufo.png").toFile());
        System.out.println("wrote ufo.png");
        ImageIO.write(beam(), "png", dir.resolve("ufo-beam.png").toFile());
        System.out.println("wrote ufo-beam.png");
    }

    static BufferedImage ufo() {
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // soft shadow-glow under the saucer
            g.setPaint(new RadialGradientPaint(48f, 62f, 40f, new float[] {0f, 1f},
                    new Color[] {new Color(0x7a, 0xff, 0xb0, 90), new Color(0x7a, 0xff, 0xb0, 0)}));
            g.fill(new Ellipse2D.Double(8, 40, 80, 46));
            // dome
            g.setPaint(new RadialGradientPaint(42f, 24f, 22f, new float[] {0f, 0.6f, 1f},
                    new Color[] {new Color(0xe8, 0xff, 0xf6, 235), new Color(0x7c, 0xd8, 0xc0, 220), new Color(0x2a, 0x7a, 0x74, 235)}));
            g.fill(new Ellipse2D.Double(30, 12, 36, 34));
            // hull: a wide lens, dark steel with a light top edge
            g.setPaint(new java.awt.GradientPaint(0, 36, new Color(0xb8, 0xc2, 0xcc), 0, 62, new Color(0x3a, 0x42, 0x50)));
            g.fill(new Ellipse2D.Double(6, 34, 84, 30));
            g.setColor(new Color(0x16, 0x1c, 0x26));
            g.setStroke(new BasicStroke(2f));
            g.draw(new Ellipse2D.Double(6, 34, 84, 30));
            g.setColor(new Color(0xe8, 0xee, 0xf4, 200));
            g.setStroke(new BasicStroke(1.4f));
            g.drawArc(12, 37, 72, 22, 20, 140);
            // rim lights, alternating green and amber
            for (int i = 0; i < 7; i++) {
                double t = i / 6.0;
                double x = 18 + t * 60;
                double y = 50 + Math.sin(t * Math.PI) * 6;
                g.setColor(i % 2 == 0 ? new Color(0x7a, 0xff, 0x9a) : new Color(0xff, 0xc8, 0x50));
                g.fill(new Ellipse2D.Double(x - 3, y - 3, 6, 6));
                g.setColor(new Color(255, 255, 255, 160));
                g.fill(new Ellipse2D.Double(x - 1.2, y - 1.8, 2.4, 2.4));
            }
            // the hatch the beam comes out of
            g.setPaint(new RadialGradientPaint(48f, 64f, 11f, new float[] {0f, 1f},
                    new Color[] {new Color(0xd8, 0xff, 0xe0, 255), new Color(0x4a, 0xe0, 0x80, 0)}));
            g.fill(new Ellipse2D.Double(36, 56, 24, 16));
        } finally {
            g.dispose();
        }
        return image;
    }

    static BufferedImage beam() {
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            float c = SIZE / 2f;
            g.setPaint(new RadialGradientPaint(c, c, c, new float[] {0f, 0.55f, 0.78f, 0.9f, 1f},
                    new Color[] {new Color(0xf2, 0xff, 0xf2, 235), new Color(0x6a, 0xff, 0x9a, 170),
                        new Color(0x4a, 0xf0, 0x8a, 120), new Color(0xb8, 0xff, 0xd0, 220), new Color(0x4a, 0xf0, 0x8a, 0)}));
            g.fill(new Ellipse2D.Double(0, 0, SIZE, SIZE));
            g.setColor(new Color(0xd8, 0xff, 0xe4, 230));
            g.setStroke(new BasicStroke(2.2f));
            g.draw(new Ellipse2D.Double(SIZE * 0.14, SIZE * 0.14, SIZE * 0.72, SIZE * 0.72));
        } finally {
            g.dispose();
        }
        return image;
    }
}
