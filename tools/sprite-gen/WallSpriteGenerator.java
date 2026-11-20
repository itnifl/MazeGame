import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;

/**
 * Generates {@code glassWall.png}, the wall look of the weakest material (the other materials have
 * their sprites already): a pale blue pane with a bright edge, a diagonal shine and a few cracks. Same
 * proportions as the other wall sprites (about 1:3.4). Deterministic.
 *
 * <pre>java tools/sprite-gen/WallSpriteGenerator.java [spriteDir]</pre>
 */
public final class WallSpriteGenerator {

    private static final int WIDTH = 220;
    private static final int HEIGHT = 740;

    private WallSpriteGenerator() {
    }

    public static void main(String[] args) throws IOException {
        Path dir = Path.of(args.length > 0 ? args[0] : "maze-common-frontend/src/main/resources/main/game/maze");
        Files.createDirectories(dir);
        ImageIO.write(glass(), "png", dir.resolve("glassWall.png").toFile());
        System.out.println("wrote glassWall.png");
    }

    static BufferedImage glass() {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // the pane
            g.setPaint(new GradientPaint(0, 0, new Color(0xcf, 0xf1, 0xff, 0xe6), WIDTH, 0, new Color(0x7f, 0xc4, 0xe8, 0xe6)));
            g.fillRoundRect(0, 0, WIDTH, HEIGHT, 28, 28);
            // frame
            g.setColor(new Color(0xe8, 0xfb, 0xff));
            g.setStroke(new java.awt.BasicStroke(10f));
            g.drawRoundRect(5, 5, WIDTH - 10, HEIGHT - 10, 24, 24);
            // diagonal shine
            g.setColor(new Color(255, 255, 255, 120));
            for (int offset = 0; offset < HEIGHT + WIDTH; offset += 170) {
                g.setStroke(new java.awt.BasicStroke(26f));
                g.drawLine(0, offset, WIDTH, offset - WIDTH);
            }
            // cracks
            g.setColor(new Color(0x3d, 0x7f, 0xa3, 190));
            g.setStroke(new java.awt.BasicStroke(4f));
            int[][] cracks = {{40, 120, 110, 190, 90, 270}, {150, 400, 100, 470, 130, 560}, {60, 600, 120, 660, 80, 720}};
            for (int[] c : cracks) {
                g.drawLine(c[0], c[1], c[2], c[3]);
                g.drawLine(c[2], c[3], c[4], c[5]);
            }
        } finally {
            g.dispose();
        }
        return image;
    }
}
