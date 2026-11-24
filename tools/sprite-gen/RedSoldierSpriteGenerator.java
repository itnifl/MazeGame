import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;

/**
 * Generates the red soldiers dropped by the UFOs (level 7) into {@code main/game/maze/soldier-red/}: every soldier sprite
 * of {@link SoldierSpriteGenerator} with the green camouflage (uniform, helmet, net) turned into crimson, so the red
 * soldiers move and fire like the player's own but are plainly the enemy. Run {@code SoldierSpriteGenerator} first.
 * Deterministic.
 *
 * <pre>java tools/sprite-gen/RedSoldierSpriteGenerator.java [spriteDir]</pre>
 */
public final class RedSoldierSpriteGenerator {

    private RedSoldierSpriteGenerator() {
    }

    public static void main(String[] args) throws IOException {
        Path base = Path.of(args.length > 0 ? args[0] : "maze-common-frontend/src/main/resources/main/game/maze");
        Path from = base.resolve("soldier");
        Path to = base.resolve("soldier-red");
        Files.createDirectories(to);
        try (var files = Files.list(from)) {
            for (Path file : (Iterable<Path>) files.filter(p -> p.toString().endsWith(".png")).sorted()::iterator) {
                BufferedImage source = ImageIO.read(file.toFile());
                ImageIO.write(redden(source), "png", to.resolve(file.getFileName().toString().replace("soldier-", "soldier-")).toFile());
                System.out.println("wrote soldier-red/" + file.getFileName());
            }
        }
    }

    static BufferedImage redden(BufferedImage source) {
        BufferedImage out = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < source.getHeight(); y++) {
            for (int x = 0; x < source.getWidth(); x++) {
                int argb = source.getRGB(x, y);
                int a = argb >>> 24;
                int r = (argb >> 16) & 0xff;
                int g = (argb >> 8) & 0xff;
                int b = argb & 0xff;
                if (a > 0 && g > r + 6 && g >= b) {     // the greens (the uniform, the helmet, the net)
                    int nr = Math.min(255, (int) (g * 1.45) + 14);
                    int ng = (int) (r * 0.42);
                    int nb = (int) (b * 0.5);
                    argb = (a << 24) | (nr << 16) | (ng << 8) | nb;
                }
                out.setRGB(x, y, argb);
            }
        }
        return out;
    }
}
