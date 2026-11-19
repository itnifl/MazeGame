import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * Generates the pumpkin bomber's turning sprites as asset files, using the same transforms
 * the JavaFX {@code PumpkinBomberCharacter} used to apply at runtime:
 * <ul>
 *   <li><b>left</b>: horizontal mirror of {@code pumpkinbomber.png}</li>
 *   <li><b>up</b>: brightened and more saturated tint (JavaFX {@code Color.brighter().saturate()})</li>
 * </ul>
 * Right and down keep using {@code pumpkinbomber.png}.
 *
 * <p>The same transforms are applied to every damage-state sprite
 * ({@code pumpkinbomber-damaged.png} etc.) so the damage marks match between directions.
 * Run {@code DamageSpriteGenerator} first.
 *
 * <p>Run from the repository root with the Java 21 source launcher:
 * <pre>java tools/sprite-gen/PumpkinBomberDirectionalSpriteGenerator.java [spriteDir]</pre>
 */
public final class PumpkinBomberDirectionalSpriteGenerator {

    /** Healthy base plus its damage-state variants; "" is the healthy sprite. */
    static final List<String> STATES = List.of("", "-damaged", "-bleeding", "-tired");

    /** JavaFX Color.brighter()/saturate() use a factor of 0.7. */
    private static final float FX_FACTOR = 0.7f;

    private PumpkinBomberDirectionalSpriteGenerator() {
    }

    public static void main(String[] args) throws IOException {
        Path dir = Path.of(args.length > 0 ? args[0]
                : "maze-common-frontend/src/main/resources/main/game/maze");
        int written = 0;
        for (String state : STATES) {
            File in = dir.resolve("pumpkinbomber" + state + ".png").toFile();
            if (!in.isFile()) {
                System.err.println("skip (missing): " + in);
                continue;
            }
            BufferedImage source = argb(ImageIO.read(in));
            write(dir, "pumpkinbomber" + state + "-left.png", mirror(source));
            write(dir, "pumpkinbomber" + state + "-up.png", tint(source));
            written += 2;
        }
        System.out.println(written + " sprites written to " + dir.toAbsolutePath());
    }

    static BufferedImage mirror(BufferedImage src) {
        int w = src.getWidth();
        BufferedImage out = new BufferedImage(w, src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < src.getHeight(); y++) {
            for (int x = 0; x < w; x++) {
                out.setRGB(w - 1 - x, y, src.getRGB(x, y));
            }
        }
        return out;
    }

    static BufferedImage tint(BufferedImage src) {
        BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        float[] hsb = new float[3];
        for (int y = 0; y < src.getHeight(); y++) {
            for (int x = 0; x < src.getWidth(); x++) {
                int argb = src.getRGB(x, y);
                int alpha = argb >>> 24;
                Color.RGBtoHSB((argb >> 16) & 0xFF, (argb >> 8) & 0xFF, argb & 0xFF, hsb);
                float saturation = Math.min(1f, hsb[1] / FX_FACTOR);
                float brightness = Math.min(1f, hsb[2] / FX_FACTOR);
                int rgb = Color.HSBtoRGB(hsb[0], saturation, brightness) & 0xFFFFFF;
                out.setRGB(x, y, (alpha << 24) | rgb);
            }
        }
        return out;
    }

    private static BufferedImage argb(BufferedImage src) {
        BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        var g = out.createGraphics();
        try {
            g.drawImage(src, 0, 0, null);
        } finally {
            g.dispose();
        }
        return out;
    }

    private static void write(Path dir, String name, BufferedImage image) throws IOException {
        ImageIO.write(image, "png", dir.resolve(name).toFile());
        System.out.println("wrote " + name);
    }
}
