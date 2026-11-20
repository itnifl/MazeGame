package main.game.maze.common.graphics;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Easy difficulty background: friendly light woods, bright and tiling without a seam")
class EasyBackgroundTest {

    private static BufferedImage background() throws IOException {
        String path = MazeVisualStyleConfig.DEFAULT.backgroundImageForDifficultyName("EasyDifficulty");
        var stream = EasyBackgroundTest.class.getResourceAsStream(path);
        assertNotNull(stream, "background missing: " + path);
        try (stream) {
            return ImageIO.read(stream);
        }
    }

    @Test
    @DisplayName("it is a large tile")
    void largeTile() throws IOException {
        BufferedImage image = background();
        assertTrue(image.getWidth() >= 1024 && image.getHeight() >= 1024, image.getWidth() + "x" + image.getHeight());
    }

    @Test
    @DisplayName("it is light and green, with a few warm leaves and red mushroom caps, nothing burning")
    void lightAndFriendly() throws IOException {
        BufferedImage image = background();
        long total = (long) image.getWidth() * image.getHeight();
        double brightness = 0;
        long green = 0;
        long warm = 0;
        long dark = 0;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;
                brightness += (r + g + b) / 3.0;
                if (g > r + 10 && g > b + 30) {
                    green++;
                }
                if (r > 200 && r > g + 40 && b < 140) {
                    warm++;
                }
                if (r + g + b < 150) {
                    dark++;
                }
            }
        }
        assertTrue(brightness / total > 120, "light, not gloomy: mean brightness " + brightness / total);
        assertTrue(green > total * 0.8, "mostly green: " + green * 100 / total + " %");
        assertTrue(warm > total * 0.0005 && warm < total * 0.05, "a few warm leaves and caps: " + warm * 10000 / total + " per 10 000 px");
        assertTrue(dark < total * 0.01, "no dark scorch or shadow pits: " + dark * 100 / total + " %");
    }

    @Test
    @DisplayName("the tile wraps: its edges meet like neighbouring pixels")
    void seamless() throws IOException {
        BufferedImage image = background();
        int w = image.getWidth();
        int h = image.getHeight();
        double seamX = 0;
        double inner = 0;
        for (int y = 0; y < h; y++) {
            seamX += difference(image.getRGB(w - 1, y), image.getRGB(0, y));
            inner += difference(image.getRGB(w / 2, y), image.getRGB(w / 2 + 1, y));
        }
        double seamY = 0;
        double innerY = 0;
        for (int x = 0; x < w; x++) {
            seamY += difference(image.getRGB(x, h - 1), image.getRGB(x, 0));
            innerY += difference(image.getRGB(x, h / 2), image.getRGB(x, h / 2 + 1));
        }
        assertTrue(seamX <= inner * 1.6 + h, "no vertical seam: " + seamX / h + " vs " + inner / h);
        assertTrue(seamY <= innerY * 1.6 + w, "no horizontal seam: " + seamY / w + " vs " + innerY / w);
    }

    private static int difference(int a, int b) {
        return Math.abs(((a >> 16) & 0xff) - ((b >> 16) & 0xff))
                + Math.abs(((a >> 8) & 0xff) - ((b >> 8) & 0xff))
                + Math.abs((a & 0xff) - (b & 0xff));
    }
}
