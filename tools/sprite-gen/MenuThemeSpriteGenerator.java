import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * Generates the textures of the screens between levels (the win screen and the shop) into {@code main/game/maze/ui/}:
 * the look of a military supply depot in the zombie maze, shared by the JavaFX and the libGDX screens (which ones are
 * used where, and their 9-slice insets, is the menu theme model {@code xmi/theme/menutheme.xmi}).
 *
 * <ul>
 *   <li>{@code panel.png}: a riveted gunmetal plate with a groove and worn corners (9-slice, 30 px)</li>
 *   <li>{@code banner.png}: the olive-drab title plate (9-slice, 14 px)</li>
 *   <li>{@code hazard-strip.png}: yellow and black warning stripes, tiling horizontally</li>
 *   <li>{@code card.png}, {@code card-selected.png}, {@code card-locked.png}: an ammo-crate item card with metal corner
 *       brackets, glowing amber when selected, grey when not sold yet (9-slice, 12 px)</li>
 *   <li>{@code slot.png}: the recessed well an item icon sits in (9-slice, 10 px)</li>
 *   <li>{@code button-go.png}, {@code button-buy.png}, {@code button-steel.png}, {@code button-danger.png}: bevelled
 *       metal plates with rivets (9-slice, 10 px)</li>
 *   <li>{@code tier-cheap.png}, {@code tier-medium.png}, {@code tier-expensive.png}: rank chevrons, one bronze, two
 *       silver, three gold</li>
 *   <li>{@code token.png}: the skull coin prices and the score are counted in; {@code lock.png}: a padlock</li>
 *   <li>{@code vignette.png}: darkens the edges of the backdrop</li>
 *   <li>{@code key.png}: a worn keyboard key for the controls guide (9-slice, 10 px), and {@code key-up.png},
 *       {@code key-down.png}, {@code key-left.png}, {@code key-right.png}: the arrow keys</li>
 * </ul>
 *
 * Deterministic (fixed seeds), drawn with antialiasing; icons are drawn at twice the size they are shown at.
 *
 * <pre>java tools/sprite-gen/MenuThemeSpriteGenerator.java [spriteDir]</pre>
 */
public final class MenuThemeSpriteGenerator {

    private static final Color OUTLINE = new Color(0x0b, 0x0d, 0x0b);
    private static final Color METAL_TOP = new Color(0x40, 0x46, 0x3e);
    private static final Color METAL_BOTTOM = new Color(0x24, 0x28, 0x23);
    private static final Color SCREEN = new Color(0x12, 0x15, 0x12, 236);
    private static final Color GROOVE = new Color(0x0f, 0x11, 0x0f);
    private static final Color GROOVE_LIGHT = new Color(0x5d, 0x64, 0x58);
    private static final Color RUST = new Color(0x8a, 0x4b, 0x1f);
    private static final Color OLIVE_TOP = new Color(0x55, 0x5e, 0x26);
    private static final Color OLIVE_BOTTOM = new Color(0x36, 0x3c, 0x16);
    private static final Color CRATE_TOP = new Color(0x34, 0x3f, 0x25);
    private static final Color CRATE_BOTTOM = new Color(0x23, 0x2b, 0x18);
    private static final Color BRACKET = new Color(0x7b, 0x80, 0x76);
    private static final Color BRACKET_DARK = new Color(0x3c, 0x40, 0x3a);
    private static final Color AMBER = new Color(0xff, 0xb0, 0x20);
    private static final Color HAZARD_YELLOW = new Color(0xe8, 0xb9, 0x23);
    private static final Color HAZARD_BLACK = new Color(0x16, 0x16, 0x14);

    private MenuThemeSpriteGenerator() {
    }

    public static void main(String[] args) throws IOException {
        Path dir = Path.of(args.length > 0 ? args[0]
                : "maze-common-frontend/src/main/resources/main/game/maze").resolve("ui");
        Files.createDirectories(dir);
        write(dir, "panel.png", panel());
        write(dir, "banner.png", banner());
        write(dir, "hazard-strip.png", hazardStrip());
        write(dir, "card.png", card(CRATE_TOP, CRATE_BOTTOM, BRACKET, null, 11));
        write(dir, "card-selected.png", card(CRATE_TOP.brighter(), CRATE_BOTTOM, BRACKET.brighter(), AMBER, 11));
        write(dir, "card-locked.png", card(new Color(0x2b, 0x2d, 0x2b), new Color(0x1d, 0x1e, 0x1d), BRACKET_DARK, null, 13));
        write(dir, "slot.png", slot());
        write(dir, "button-go.png", button(new Color(0x5c, 0x8f, 0x2e), new Color(0x2c, 0x4f, 0x14)));
        write(dir, "button-buy.png", button(new Color(0xe0, 0x96, 0x1e), new Color(0x8a, 0x52, 0x08)));
        write(dir, "button-steel.png", button(new Color(0x76, 0x7d, 0x86), new Color(0x38, 0x3d, 0x44)));
        write(dir, "button-danger.png", button(new Color(0x9c, 0x30, 0x26), new Color(0x52, 0x14, 0x0f)));
        write(dir, "tier-cheap.png", chevrons(1, new Color(0xcd, 0x8c, 0x4e)));
        write(dir, "tier-medium.png", chevrons(2, new Color(0xcf, 0xd6, 0xdd)));
        write(dir, "tier-expensive.png", chevrons(3, new Color(0xf4, 0xc8, 0x45)));
        write(dir, "token.png", token());
        write(dir, "lock.png", lock());
        write(dir, "vignette.png", vignette());
        write(dir, "key.png", key(null));
        write(dir, "key-up.png", key(0));
        write(dir, "key-right.png", key(90));
        write(dir, "key-down.png", key(180));
        write(dir, "key-left.png", key(270));
    }

    private static void write(Path dir, String name, BufferedImage image) throws IOException {
        ImageIO.write(image, "png", dir.resolve(name).toFile());
        System.out.println("wrote " + dir.resolve(name));
    }

    private static Graphics2D smooth(BufferedImage image) {
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        return g;
    }

    /** The main plate: gunmetal frame, a darker screen inside, a groove, rivets and rust in the corners. */
    static BufferedImage panel() {
        int size = 120;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = smooth(image);
        g.setPaint(new GradientPaint(0, 0, METAL_TOP, 0, size, METAL_BOTTOM));
        g.fill(new RoundRectangle2D.Double(0, 0, size, size, 18, 18));
        // brushed specks, only in the frame band (the stretched centre stays clean)
        Random random = new Random(11);
        for (int i = 0; i < 900; i++) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            if (x > 26 && x < size - 26 && y > 26 && y < size - 26) {
                continue;
            }
            g.setColor(random.nextBoolean() ? new Color(255, 255, 255, 14) : new Color(0, 0, 0, 30));
            g.fillRect(x, y, 1, 1);
        }
        // rust in the corners
        for (int[] corner : new int[][] {{6, 6}, {size - 22, 8}, {8, size - 20}, {size - 20, size - 22}}) {
            for (int i = 0; i < 26; i++) {
                int r = 1 + random.nextInt(3);
                g.setColor(new Color(RUST.getRed(), RUST.getGreen(), RUST.getBlue(), 40 + random.nextInt(50)));
                g.fill(new Ellipse2D.Double(corner[0] + random.nextInt(16), corner[1] + random.nextInt(14), r, r));
            }
        }
        // the screen and the groove round it
        g.setColor(SCREEN);
        g.fill(new RoundRectangle2D.Double(13, 13, size - 26, size - 26, 10, 10));
        g.setStroke(new BasicStroke(2f));
        g.setColor(GROOVE);
        g.draw(new RoundRectangle2D.Double(10, 10, size - 20, size - 20, 12, 12));
        g.setStroke(new BasicStroke(1f));
        g.setColor(GROOVE_LIGHT);
        g.draw(new RoundRectangle2D.Double(11.5, 11.5, size - 21, size - 21, 12, 12));
        // bevel and outline
        g.setColor(new Color(255, 255, 255, 40));
        g.drawLine(6, 1, size - 7, 1);
        g.setColor(new Color(0, 0, 0, 90));
        g.drawLine(6, size - 2, size - 7, size - 2);
        g.setColor(OUTLINE);
        g.setStroke(new BasicStroke(1.5f));
        g.draw(new RoundRectangle2D.Double(0.75, 0.75, size - 1.5, size - 1.5, 18, 18));
        for (int[] at : new int[][] {{5, 5}, {size - 6, 5}, {5, size - 6}, {size - 6, size - 6}}) {
            rivet(g, at[0], at[1], 3.2);
        }
        g.dispose();
        return image;
    }

    /** The title plate: olive drab with a bevel, an outline and a rivet at each end. */
    static BufferedImage banner() {
        int w = 96;
        int h = 40;
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = smooth(image);
        g.setPaint(new GradientPaint(0, 0, OLIVE_TOP, 0, h, OLIVE_BOTTOM));
        g.fill(new RoundRectangle2D.Double(0, 0, w, h, 8, 8));
        g.setColor(new Color(255, 255, 220, 50));
        g.drawLine(4, 2, w - 5, 2);
        g.setColor(new Color(0, 0, 0, 80));
        g.drawLine(4, h - 3, w - 5, h - 3);
        g.setColor(new Color(0, 0, 0, 70));
        g.setStroke(new BasicStroke(1f));
        g.draw(new RoundRectangle2D.Double(4.5, 4.5, w - 9, h - 9, 4, 4));
        g.setColor(OUTLINE);
        g.setStroke(new BasicStroke(1.5f));
        g.draw(new RoundRectangle2D.Double(0.75, 0.75, w - 1.5, h - 1.5, 8, 8));
        rivet(g, 8, h / 2.0, 2.6);
        rivet(g, w - 8, h / 2.0, 2.6);
        g.dispose();
        return image;
    }

    /** Diagonal warning stripes; 32 px wide so they tile without a seam (the stripes repeat every 16 px). */
    static BufferedImage hazardStrip() {
        int w = 32;
        int h = 12;
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Random random = new Random(5);
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                boolean yellow = Math.floorMod(x + y, 16) < 8;
                Color c = yellow ? HAZARD_YELLOW : HAZARD_BLACK;
                if (y == 0 || y == h - 1) {
                    c = OUTLINE;
                } else if (yellow && random.nextInt(9) == 0) {
                    c = c.darker();   // worn paint
                }
                image.setRGB(x, y, c.getRGB());
            }
        }
        return image;
    }

    /** An item card: a crate plank with seams, metal corner brackets with rivets, and an optional glowing border. */
    static BufferedImage card(Color top, Color bottom, Color bracket, Color glow, long seed) {
        int w = 80;
        int h = 44;
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = smooth(image);
        g.setPaint(new GradientPaint(0, 0, top, 0, h, bottom));
        g.fill(new RoundRectangle2D.Double(1, 1, w - 2, h - 2, 6, 6));
        // wood grain: faint horizontal streaks
        Random random = new Random(seed);
        for (int i = 0; i < 40; i++) {
            int y = 4 + random.nextInt(h - 8);
            int x = 4 + random.nextInt(w - 30);
            g.setColor(new Color(0, 0, 0, 20 + random.nextInt(25)));
            g.drawLine(x, y, x + 10 + random.nextInt(20), y);
        }
        g.setColor(new Color(255, 255, 255, 22));
        g.drawLine(4, 2, w - 5, 2);
        // corner brackets
        g.setColor(bracket);
        // an L along the outer edges of each corner
        for (boolean left : new boolean[] {true, false}) {
            for (boolean upper : new boolean[] {true, false}) {
                int barX = left ? 1 : w - 11;
                int postX = left ? 1 : w - 4;
                int barY = upper ? 1 : h - 4;
                int postY = upper ? 1 : h - 11;
                g.fillRect(barX, barY, 10, 3);
                g.fillRect(postX, postY, 3, 10);
            }
        }
        g.setColor(BRACKET_DARK);
        for (int[] at : new int[][] {{5, 5}, {w - 6, 5}, {5, h - 6}, {w - 6, h - 6}}) {
            g.fill(new Ellipse2D.Double(at[0] - 1.2, at[1] - 1.2, 2.4, 2.4));
        }
        g.setColor(OUTLINE);
        g.setStroke(new BasicStroke(1.2f));
        g.draw(new RoundRectangle2D.Double(1, 1, w - 2, h - 2, 6, 6));
        if (glow != null) {
            g.setColor(new Color(glow.getRed(), glow.getGreen(), glow.getBlue(), 90));
            g.setStroke(new BasicStroke(3f));
            g.draw(new RoundRectangle2D.Double(1.5, 1.5, w - 3, h - 3, 6, 6));
            g.setColor(glow);
            g.setStroke(new BasicStroke(1.5f));
            g.draw(new RoundRectangle2D.Double(2, 2, w - 4, h - 4, 5, 5));
        }
        g.dispose();
        return image;
    }

    /** The well an icon sits in: a dark square with an inner shadow and a lit lower edge. */
    static BufferedImage slot() {
        int size = 40;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = smooth(image);
        g.setPaint(new GradientPaint(0, 0, new Color(0x06, 0x07, 0x06), 0, size, new Color(0x1a, 0x1e, 0x19)));
        g.fill(new RoundRectangle2D.Double(1, 1, size - 2, size - 2, 7, 7));
        g.setColor(new Color(0, 0, 0, 150));
        g.setStroke(new BasicStroke(2f));
        g.drawLine(4, 3, size - 5, 3);
        g.setColor(new Color(255, 255, 255, 40));
        g.setStroke(new BasicStroke(1f));
        g.drawLine(4, size - 2, size - 5, size - 2);
        g.setColor(OUTLINE);
        g.draw(new RoundRectangle2D.Double(0.5, 0.5, size - 1, size - 1, 7, 7));
        g.dispose();
        return image;
    }

    /** A button: a bevelled metal plate in the given colours, with a rivet at each end. */
    static BufferedImage button(Color top, Color bottom) {
        int w = 64;
        int h = 32;
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = smooth(image);
        g.setPaint(new GradientPaint(0, 0, top, 0, h, bottom));
        g.fill(new RoundRectangle2D.Double(0, 0, w, h, 8, 8));
        g.setPaint(new GradientPaint(0, 0, new Color(255, 255, 255, 70), 0, h / 2f, new Color(255, 255, 255, 0)));
        g.fill(new RoundRectangle2D.Double(2, 2, w - 4, h / 2.0, 6, 6));
        g.setColor(new Color(0, 0, 0, 90));
        g.drawLine(4, h - 3, w - 5, h - 3);
        g.setColor(OUTLINE);
        g.setStroke(new BasicStroke(1.5f));
        g.draw(new RoundRectangle2D.Double(0.75, 0.75, w - 1.5, h - 1.5, 8, 8));
        rivet(g, 5.5, h / 2.0, 2.0);
        rivet(g, w - 5.5, h / 2.0, 2.0);
        g.dispose();
        return image;
    }

    /** Rank chevrons, stacked upwards, in the tier's metal. */
    static BufferedImage chevrons(int count, Color metal) {
        int size = 56;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = smooth(image);
        double step = 12;
        double first = size / 2.0 + (count - 1) * step / 2.0 + 6;
        for (int i = 0; i < count; i++) {
            double y = first - i * step;
            Path2D chevron = new Path2D.Double();
            chevron.moveTo(8, y);
            chevron.lineTo(size / 2.0, y - 14);
            chevron.lineTo(size - 8, y);
            chevron.lineTo(size - 8, y + 7);
            chevron.lineTo(size / 2.0, y - 7);
            chevron.lineTo(8, y + 7);
            chevron.closePath();
            g.setPaint(new GradientPaint(0, (float) (y - 14), metal.brighter(), 0, (float) (y + 7), metal.darker()));
            g.fill(chevron);
            g.setColor(OUTLINE);
            g.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.draw(chevron);
        }
        g.dispose();
        return image;
    }

    /** The skull coin: a dark red disc with a gold rim and a bone-white skull. */
    static BufferedImage token() {
        int size = 40;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = smooth(image);
        g.setColor(OUTLINE);
        g.fill(new Ellipse2D.Double(1, 1, size - 2, size - 2));
        g.setPaint(new GradientPaint(0, 0, new Color(0xf0, 0xc0, 0x55), 0, size, new Color(0x9a, 0x6a, 0x1c)));
        g.fill(new Ellipse2D.Double(2.5, 2.5, size - 5, size - 5));
        g.setPaint(new RadialGradientPaint(size / 2f - 4, size / 2f - 4, size / 2f,
                new float[] {0f, 1f}, new Color[] {new Color(0xa3, 0x2a, 0x22), new Color(0x52, 0x10, 0x0c)}));
        g.fill(new Ellipse2D.Double(6, 6, size - 12, size - 12));
        Color bone = new Color(0xf2, 0xea, 0xd3);
        g.setColor(bone);
        g.fill(new Ellipse2D.Double(12, 10, 16, 14));       // cranium
        g.fill(new RoundRectangle2D.Double(15, 21, 10, 8, 3, 3));   // jaw
        g.setColor(new Color(0x30, 0x08, 0x06));
        g.fill(new Ellipse2D.Double(14.5, 15, 4.5, 5));      // eyes
        g.fill(new Ellipse2D.Double(21, 15, 4.5, 5));
        g.fill(new Ellipse2D.Double(19, 20.5, 2, 2.5));      // nose
        g.setStroke(new BasicStroke(1f));
        g.drawLine(18, 25, 18, 28);                          // teeth
        g.drawLine(20, 25, 20, 28);
        g.drawLine(22, 25, 22, 28);
        g.dispose();
        return image;
    }

    /** A steel padlock. */
    static BufferedImage lock() {
        int size = 40;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = smooth(image);
        g.setStroke(new BasicStroke(5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
        g.setColor(OUTLINE);
        g.draw(new Arc2D.Double(11, 4, 18, 22, 0, 180, Arc2D.OPEN));
        g.drawLine(11, 15, 11, 19);
        g.drawLine(29, 15, 29, 19);
        g.setStroke(new BasicStroke(3f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
        g.setColor(new Color(0xb8, 0xbe, 0xc4));
        g.draw(new Arc2D.Double(11, 4, 18, 22, 0, 180, Arc2D.OPEN));
        g.drawLine(11, 15, 11, 19);
        g.drawLine(29, 15, 29, 19);
        g.setColor(OUTLINE);
        g.fill(new RoundRectangle2D.Double(6, 17, 28, 20, 5, 5));
        g.setPaint(new GradientPaint(0, 18, new Color(0x9a, 0xa1, 0xa8), 0, 36, new Color(0x4b, 0x50, 0x56)));
        g.fill(new RoundRectangle2D.Double(7.5, 18.5, 25, 17, 4, 4));
        g.setColor(OUTLINE);
        g.fill(new Ellipse2D.Double(17.5, 22, 5, 5));
        g.fillRect(19, 25, 2, 6);
        g.dispose();
        return image;
    }

    /**
     * A keyboard key seen from above: a light keycap with a dished top and a dark side; with an arrow pointing
     * {@code degrees} clockwise from up, or blank (null) for a key that gets its label as text.
     */
    static BufferedImage key(Integer degrees) {
        int size = 48;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = smooth(image);
        g.setColor(OUTLINE);
        g.fill(new RoundRectangle2D.Double(1, 1, size - 2, size - 2, 10, 10));
        g.setColor(new Color(0x55, 0x58, 0x52));
        g.fill(new RoundRectangle2D.Double(2, 2, size - 4, size - 4, 9, 9));
        g.setPaint(new GradientPaint(0, 4, new Color(0xe6, 0xe2, 0xd2), 0, size - 10, new Color(0xb4, 0xb0, 0x9e)));
        g.fill(new RoundRectangle2D.Double(5, 3, size - 10, size - 12, 7, 7));
        g.setColor(new Color(255, 255, 255, 90));
        g.drawLine(9, 5, size - 10, 5);
        if (degrees != null) {
            java.awt.geom.AffineTransform saved = g.getTransform();
            g.rotate(Math.toRadians(degrees), size / 2.0, (size - 9) / 2.0 + 1.5);
            Path2D arrow = new Path2D.Double();
            double cx = size / 2.0;
            double cy = (size - 9) / 2.0 + 1.5;
            arrow.moveTo(cx, cy - 11);
            arrow.lineTo(cx + 10, cy + 1);
            arrow.lineTo(cx + 4, cy + 1);
            arrow.lineTo(cx + 4, cy + 10);
            arrow.lineTo(cx - 4, cy + 10);
            arrow.lineTo(cx - 4, cy + 1);
            arrow.lineTo(cx - 10, cy + 1);
            arrow.closePath();
            g.setColor(new Color(0x26, 0x28, 0x24));
            g.fill(arrow);
            g.setTransform(saved);
        }
        g.dispose();
        return image;
    }

    /** Transparent in the middle, darkening to the edges. */
    static BufferedImage vignette() {
        int size = 256;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = smooth(image);
        g.setPaint(new RadialGradientPaint(size / 2f, size / 2f, size * 0.72f, new float[] {0f, 0.55f, 1f},
                new Color[] {new Color(0, 0, 0, 0), new Color(0, 0, 0, 70), new Color(0, 0, 0, 225)}));
        g.fillRect(0, 0, size, size);
        g.dispose();
        return image;
    }

    /** A domed rivet: a dark rim, a lit top-left and a highlight. */
    static void rivet(Graphics2D g, double cx, double cy, double r) {
        g.setColor(OUTLINE);
        g.fill(new Ellipse2D.Double(cx - r - 0.8, cy - r - 0.8, 2 * r + 1.6, 2 * r + 1.6));
        g.setPaint(new RadialGradientPaint((float) (cx - r / 3), (float) (cy - r / 3), (float) (r * 1.4),
                new float[] {0f, 1f}, new Color[] {new Color(0xc4, 0xc9, 0xbf), new Color(0x4a, 0x4f, 0x47)}));
        g.fill(new Ellipse2D.Double(cx - r, cy - r, 2 * r, 2 * r));
    }
}
