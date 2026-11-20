import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;

/**
 * Generates the zombie sergeant sprite {@code zombie-sergeant.png} from the regular zombie sprite
 * ({@code zombie1-down.png}): a dark green cast, a drill-sergeant cap, chevron stripes and a gatling
 * gun held across the body. Deterministic.
 *
 * <pre>java tools/sprite-gen/SergeantSpriteGenerator.java [spriteDir]</pre>
 */
public final class SergeantSpriteGenerator {

    private SergeantSpriteGenerator() {
    }

    public static void main(String[] args) throws IOException {
        Path dir = Path.of(args.length > 0 ? args[0] : "maze-common-frontend/src/main/resources/main/game/maze");
        BufferedImage zombie = ImageIO.read(dir.resolve("zombie1-down.png").toFile());
        BufferedImage out = new BufferedImage(zombie.getWidth(), zombie.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < out.getHeight(); y++) {
            for (int x = 0; x < out.getWidth(); x++) {
                int argb = zombie.getRGB(x, y);
                int alpha = argb >>> 24;
                int red = (int) (((argb >> 16) & 0xff) * 0.75);
                int green = (int) (((argb >> 8) & 0xff) * 0.9);
                int blue = (int) ((argb & 0xff) * 0.7);
                out.setRGB(x, y, alpha << 24 | red << 16 | green << 8 | blue);
            }
        }
        Graphics2D g = out.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = out.getWidth();
            int h = out.getHeight();
            // cap
            g.setColor(new Color(0x3b, 0x4a, 0x2a));
            g.fillRoundRect(w * 28 / 100, h * 4 / 100, w * 44 / 100, h * 10 / 100, 30, 30);
            g.setColor(new Color(0xc8, 0x9a, 0x2e));
            g.fillRect(w * 28 / 100, h * 12 / 100, w * 44 / 100, h * 2 / 100);
            // chevrons
            g.setColor(new Color(0xe0, 0xc0, 0x40));
            g.setStroke(new BasicStroke(Math.max(6, w / 40f)));
            for (int i = 0; i < 3; i++) {
                int y = h * (46 + i * 5) / 100;
                g.drawLine(w * 38 / 100, y, w / 2, y + h * 3 / 100);
                g.drawLine(w / 2, y + h * 3 / 100, w * 62 / 100, y);
            }
            // gatling gun across the body
            g.setColor(new Color(0x1a, 0x14, 0x18));
            g.fillRoundRect(w * 12 / 100, h * 62 / 100, w * 76 / 100, h * 9 / 100, 16, 16);
            g.setColor(new Color(0x7a, 0x82, 0x90));
            for (int i = 0; i < 3; i++) {
                g.fillRect(w * 52 / 100, h * (63 + i * 3) / 100, w * 36 / 100, h * 2 / 100);
            }
            g.setColor(new Color(0xc8, 0x9a, 0x2e));
            g.fillOval(w * 30 / 100, h * 57 / 100, w * 14 / 100, w * 14 / 100);
        } finally {
            g.dispose();
        }
        ImageIO.write(out, "png", dir.resolve("zombie-sergeant.png").toFile());
        System.out.println("wrote zombie-sergeant.png");
    }
}
