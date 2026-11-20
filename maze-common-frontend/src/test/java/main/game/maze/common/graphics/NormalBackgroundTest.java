package main.game.maze.common.graphics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Normal difficulty background: a lush meadow with patches of burning grass, tiling without a seam")
class NormalBackgroundTest {

    private static BufferedImage background() throws IOException {
        String path = MazeVisualStyleConfig.DEFAULT.backgroundImageForDifficultyName("NormalDifficulty");
        var stream = NormalBackgroundTest.class.getResourceAsStream(path);
        assertNotNull(stream, "background missing: " + path);
        try (stream) {
            return ImageIO.read(stream);
        }
    }

    @Test
    @DisplayName("it is a large tile, so the burning patches do not repeat every few steps")
    void largeTile() throws IOException {
        BufferedImage image = background();
        assertTrue(image.getWidth() >= 1024 && image.getHeight() >= 1024, image.getWidth() + "x" + image.getHeight());
    }

    @Test
    @DisplayName("it is mostly green grass, with a small share of fire colours")
    void lushWithFire() throws IOException {
        BufferedImage image = background();
        long green = 0;
        long fire = 0;
        long total = (long) image.getWidth() * image.getHeight();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;
                if (g > r + 20 && g > b + 20) {
                    green++;
                }
                if (r > 200 && r > g + 60 && g > 40 && b < 120) {
                    fire++;   // orange, red and yellow flame and ember pixels
                }
            }
        }
        assertTrue(green > total * 0.8, "mostly grass: " + green * 100 / total + " %");
        assertTrue(fire > total * 0.003, "there is burning: " + fire * 10000 / total + " per 10 000 px");
        assertTrue(fire < total * 0.08, "but not a field on fire: " + fire * 100 / total + " %");
    }

    @Test
    @DisplayName("the tile wraps: its left and right edges (and top and bottom) meet like neighbouring pixels")
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

    @Test
    @DisplayName("the other difficulties keep their own backgrounds")
    void othersUnchanged() {
        MazeVisualStyleConfig style = MazeVisualStyleConfig.DEFAULT;
        assertEquals("/main/game/maze/gameBackGround1.png", style.backgroundImageForDifficultyName("EasyDifficulty"));
        assertEquals("/main/game/maze/gameBackGround3.png", style.backgroundImageForDifficultyName("HardDifficulty"));
    }

    private static int difference(int a, int b) {
        return Math.abs(((a >> 16) & 0xff) - ((b >> 16) & 0xff))
                + Math.abs(((a >> 8) & 0xff) - ((b >> 8) & 0xff))
                + Math.abs((a & 0xff) - (b & 0xff));
    }
}
