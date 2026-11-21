import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * Generates {@code ghost-portal.png}, the ghost that teleports the player, from the ordinary ghost sprite
 * ({@code ghost1.png}): the white body turns shimmering cyan and violet, the outline deep violet, the eyes glow
 * magenta, and a portal swirl with sparkles spins on its belly. Deterministic.
 *
 * <pre>java tools/sprite-gen/PortalGhostSpriteGenerator.java [spriteDir]</pre>
 */
public final class PortalGhostSpriteGenerator {

    private PortalGhostSpriteGenerator() {
    }

    public static void main(String[] args) throws IOException {
        Path dir = Path.of(args.length > 0 ? args[0] : "maze-common-frontend/src/main/resources/main/game/maze");
        BufferedImage ghost = ImageIO.read(dir.resolve("ghost1.png").toFile());
        BufferedImage out = new BufferedImage(ghost.getWidth(), ghost.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int w = out.getWidth();
        int h = out.getHeight();
        Color deep = new Color(0x2a, 0x12, 0x5e);
        Color light = new Color(0xb9, 0xa4, 0xff);
        Color glow = new Color(0x6f, 0xe8, 0xff);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int argb = ghost.getRGB(x, y);
                int alpha = argb >>> 24;
                if (alpha == 0) {
                    continue;
                }
                double luminance = (((argb >> 16) & 0xff) + ((argb >> 8) & 0xff) + (argb & 0xff)) / (3.0 * 255);
                // dark outline -> deep violet; pale body -> violet fading to cyan towards the bottom
                double t = Math.max(0, Math.min(1, (luminance - 0.25) / 0.6));
                Color body = mix(light, glow, y / (double) h * 0.8);
                Color c = mix(deep, body, t);
                out.setRGB(x, y, alpha << 24 | c.getRed() << 16 | c.getGreen() << 8 | c.getBlue());
            }
        }
        Graphics2D g = out.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // glowing magenta eyes
            for (double[] eye : new double[][] {{0.31, 0.32}, {0.60, 0.32}}) {
                double ex = w * eye[0];
                double ey = h * eye[1];
                g.setPaint(new RadialGradientPaint((float) ex, (float) ey, w * 0.07f, new float[] {0f, 1f},
                        new Color[] {new Color(0xff, 0x6a, 0xf0, 255), new Color(0xff, 0x6a, 0xf0, 0)}));
                g.fillOval((int) (ex - w * 0.07), (int) (ey - w * 0.07), (int) (w * 0.14), (int) (w * 0.14));
            }
            // portal swirl on the belly: a spiral of cyan and white
            double cx = w * 0.46;
            double cy = h * 0.66;
            Path2D spiral = new Path2D.Double();
            for (int i = 0; i <= 90; i++) {
                double a = i * 0.22;
                double r = w * 0.005 + i * w * 0.0022;
                double px = cx + Math.cos(a) * r;
                double py = cy + Math.sin(a) * r * 0.9;
                if (i == 0) {
                    spiral.moveTo(px, py);
                } else {
                    spiral.lineTo(px, py);
                }
            }
            g.setStroke(new BasicStroke(w * 0.014f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.setColor(new Color(0x24, 0xc6, 0xf0, 230));
            g.draw(spiral);
            g.setStroke(new BasicStroke(w * 0.005f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.setColor(new Color(0xff, 0xff, 0xff, 240));
            g.draw(spiral);
            // sparkles
            Random random = new Random(9);
            g.setColor(new Color(0xff, 0xff, 0xff, 230));
            for (int i = 0; i < 14; i++) {
                int sx = (int) (w * (0.25 + random.nextDouble() * 0.5));
                int sy = (int) (h * (0.2 + random.nextDouble() * 0.6));
                if ((out.getRGB(sx, sy) >>> 24) > 0) {
                    int s = 4 + random.nextInt(6);
                    g.fillRect(sx - s / 2, sy - 1, s, 2);
                    g.fillRect(sx - 1, sy - s / 2, 2, s);
                }
            }
        } finally {
            g.dispose();
        }
        ImageIO.write(out, "png", dir.resolve("ghost-portal.png").toFile());
        System.out.println("wrote ghost-portal.png");
    }

    private static Color mix(Color a, Color b, double t) {
        t = Math.max(0, Math.min(1, t));
        return new Color(
                (int) Math.round(a.getRed() + (b.getRed() - a.getRed()) * t),
                (int) Math.round(a.getGreen() + (b.getGreen() - a.getGreen()) * t),
                (int) Math.round(a.getBlue() + (b.getBlue() - a.getBlue()) * t));
    }
}
