import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;

/**
 * Generates the animated soldier of the infantry strike (#164) into {@code main/game/maze/soldier/}: a soldier in a
 * camouflage uniform, webbing, boots, a helmet with a net and a rifle, seen from the side (facing right; the game mirrors
 * it for left), from the front (facing down) and from the back (facing up, with the backpack), each standing, in two
 * walking strides and firing (muzzle flash), plus a fallen soldier. Drawn on a 32x32 pixel grid with a soft shadow and
 * scaled up 2x (64x64, transparent), in the chunky pixel-art style of the other characters. Deterministic.
 *
 * <pre>java tools/sprite-gen/SoldierSpriteGenerator.java [spriteDir]</pre>
 */
public final class SoldierSpriteGenerator {

    static final int GRID = 32;
    static final int SCALE = 2;

    private static final Color OUTLINE = new Color(0x16, 0x14, 0x10);
    private static final Color SHADOW = new Color(0, 0, 0, 70);
    private static final Color HELMET = new Color(0x4a, 0x5a, 0x2a);
    private static final Color HELMET_LIGHT = new Color(0x70, 0x84, 0x42);
    private static final Color HELMET_DARK = new Color(0x2f, 0x3a, 0x1a);
    private static final Color NET = new Color(0x26, 0x2e, 0x14);
    private static final Color SKIN = new Color(0xd9, 0xa3, 0x7a);
    private static final Color SKIN_DARK = new Color(0xa8, 0x74, 0x50);
    private static final Color UNIFORM = new Color(0x58, 0x6b, 0x33);
    private static final Color CAMO_DARK = new Color(0x3a, 0x48, 0x22);
    private static final Color CAMO_LIGHT = new Color(0x84, 0x93, 0x52);
    private static final Color CAMO_BROWN = new Color(0x6b, 0x55, 0x32);
    private static final Color WEBBING = new Color(0x3d, 0x32, 0x20);
    private static final Color POUCH = new Color(0x52, 0x45, 0x2b);
    private static final Color BOOT = new Color(0x26, 0x20, 0x1b);
    private static final Color BOOT_LIGHT = new Color(0x45, 0x3a, 0x30);
    private static final Color METAL = new Color(0x2b, 0x2e, 0x34);
    private static final Color METAL_LIGHT = new Color(0x62, 0x68, 0x72);
    private static final Color WOOD = new Color(0x6a, 0x48, 0x28);
    private static final Color PACK = new Color(0x5c, 0x4a, 0x2c);
    private static final Color PACK_DARK = new Color(0x40, 0x33, 0x1e);
    private static final Color FLASH_OUT = new Color(0xff, 0x8a, 0x1e);
    private static final Color FLASH_MID = new Color(0xff, 0xd8, 0x4a);
    private static final Color FLASH_CORE = new Color(0xff, 0xff, 0xe8);

    /** Leg offsets of the poses: standing, stride one, stride two. */
    private static final int[][] STRIDES = {{0, 0}, {2, -2}, {-2, 2}};
    private static final String[] POSES = {"stand", "walk1", "walk2"};

    private SoldierSpriteGenerator() {
    }

    public static void main(String[] args) throws IOException {
        Path dir = Path.of(args.length > 0 ? args[0]
                : "maze-common-frontend/src/main/resources/main/game/maze").resolve("soldier");
        Files.createDirectories(dir);
        for (int i = 0; i < POSES.length; i++) {
            int[] s = STRIDES[i];
            write(dir, "soldier-side-" + POSES[i] + ".png", draw(g -> side(g, s[0], s[1], false)));
            write(dir, "soldier-down-" + POSES[i] + ".png", draw(g -> front(g, s[0], false)));
            write(dir, "soldier-up-" + POSES[i] + ".png", draw(g -> back(g, s[0], false)));
        }
        write(dir, "soldier-side-fire.png", draw(g -> side(g, 0, 0, true)));
        write(dir, "soldier-down-fire.png", draw(g -> front(g, 0, true)));
        write(dir, "soldier-up-fire.png", draw(g -> back(g, 0, true)));
        write(dir, "soldier-fallen.png", draw(SoldierSpriteGenerator::fallen));
    }

    /** Seen from the side, facing right; {@code near}/{@code far} move the legs for the stride. */
    static void side(Graphics2D g, int near, int far, boolean firing) {
        shadow(g, 9, 28, 14);
        // far leg (darker), then near leg
        leg(g, 13 + far, 21, CAMO_DARK);
        leg(g, 16 + near, 21, UNIFORM);
        // backpack on the back (left)
        box(g, OUTLINE, 6, 12, 6, 10);
        box(g, PACK, 7, 13, 4, 8);
        box(g, PACK_DARK, 7, 17, 4, 1);
        // torso
        box(g, OUTLINE, 10, 12, 11, 11);
        box(g, UNIFORM, 11, 13, 9, 9);
        camo(g, 11, 13, 9, 9, 3);
        box(g, WEBBING, 11, 19, 9, 2);
        box(g, POUCH, 17, 17, 3, 3);
        box(g, WEBBING, 14, 13, 1, 6);
        // head and helmet
        box(g, OUTLINE, 13, 5, 8, 8);
        box(g, SKIN, 14, 6, 6, 6);
        box(g, SKIN_DARK, 14, 10, 6, 1);
        px(g, OUTLINE, 18, 8);
        px(g, SKIN_DARK, 20, 9);
        helmetSide(g);
        // rifle along the facing, held at the shoulder
        rifle(g, 12, 15, firing);
        // arm and hand under the rifle
        box(g, OUTLINE, 15, 15, 5, 4);
        box(g, UNIFORM, 16, 16, 3, 2);
        box(g, SKIN, 19, 16, 2, 2);
    }

    private static void helmetSide(Graphics2D g) {
        box(g, OUTLINE, 11, 2, 11, 6);
        box(g, HELMET, 12, 3, 9, 4);
        box(g, HELMET_LIGHT, 14, 3, 4, 1);
        box(g, HELMET_DARK, 12, 6, 10, 1);   // brim
        px(g, NET, 13, 4);
        px(g, NET, 16, 5);
        px(g, NET, 19, 4);
        px(g, WEBBING, 14, 11);              // chin strap
    }

    private static void rifle(Graphics2D g, int x, int y, boolean firing) {
        box(g, OUTLINE, x - 1, y - 1, 19, 4);
        box(g, WOOD, x, y, 4, 2);            // stock
        box(g, METAL, x + 4, y, 13, 2);      // body and barrel
        box(g, METAL_LIGHT, x + 5, y, 9, 1);
        box(g, METAL, x + 9, y + 2, 2, 3);   // magazine
        if (firing) {
            muzzleFlash(g, x + 19, y + 1, 1, 0);
        }
    }

    /** Seen from the front (facing down); {@code stride} lifts one leg or the other. */
    static void front(Graphics2D g, int stride, boolean firing) {
        shadow(g, 9, 28, 14);
        legFront(g, 12, 21 - Math.max(0, stride / 2), UNIFORM);
        legFront(g, 17, 21 - Math.max(0, -stride / 2), UNIFORM);
        // torso with webbing straps and pouches
        box(g, OUTLINE, 9, 12, 14, 11);
        box(g, UNIFORM, 10, 13, 12, 9);
        camo(g, 10, 13, 12, 9, 5);
        box(g, WEBBING, 12, 13, 1, 7);
        box(g, WEBBING, 19, 13, 1, 7);
        box(g, WEBBING, 10, 19, 12, 2);
        box(g, POUCH, 11, 17, 3, 2);
        box(g, POUCH, 18, 17, 3, 2);
        // arms
        box(g, OUTLINE, 7, 13, 3, 8);
        box(g, UNIFORM, 8, 14, 1, 6);
        box(g, OUTLINE, 22, 13, 3, 8);
        box(g, UNIFORM, 23, 14, 1, 6);
        // head: face towards the viewer
        box(g, OUTLINE, 11, 5, 10, 8);
        box(g, SKIN, 12, 6, 8, 6);
        box(g, SKIN_DARK, 12, 11, 8, 1);
        px(g, OUTLINE, 14, 8);
        px(g, OUTLINE, 17, 8);
        box(g, SKIN_DARK, 15, 9, 2, 1);
        // helmet
        box(g, OUTLINE, 9, 2, 14, 6);
        box(g, HELMET, 10, 3, 12, 3);
        box(g, HELMET_LIGHT, 13, 3, 5, 1);
        box(g, HELMET_DARK, 9, 6, 14, 1);
        px(g, NET, 11, 4);
        px(g, NET, 15, 5);
        px(g, NET, 20, 4);
        // rifle held pointing down the way the soldier faces
        box(g, OUTLINE, 18, 15, 4, 14);
        box(g, METAL, 19, 16, 2, 12);
        box(g, METAL_LIGHT, 19, 16, 1, 10);
        box(g, WOOD, 19, 15, 2, 3);
        box(g, SKIN, 18, 18, 4, 2);          // hands
        if (firing) {
            muzzleFlash(g, 20, 29, 0, 1);
        }
    }

    /** Seen from the back (facing up), with the backpack; the rifle points up over the shoulder. */
    static void back(Graphics2D g, int stride, boolean firing) {
        shadow(g, 9, 28, 14);
        legFront(g, 12, 21 - Math.max(0, stride / 2), CAMO_DARK);
        legFront(g, 17, 21 - Math.max(0, -stride / 2), CAMO_DARK);
        box(g, OUTLINE, 9, 12, 14, 11);
        box(g, UNIFORM, 10, 13, 12, 9);
        camo(g, 10, 13, 12, 9, 7);
        box(g, WEBBING, 10, 19, 12, 2);
        // backpack
        box(g, OUTLINE, 11, 12, 10, 9);
        box(g, PACK, 12, 13, 8, 7);
        box(g, PACK_DARK, 12, 16, 8, 1);
        box(g, PACK_DARK, 15, 13, 2, 3);
        // arms
        box(g, OUTLINE, 7, 13, 3, 8);
        box(g, UNIFORM, 8, 14, 1, 6);
        box(g, OUTLINE, 22, 13, 3, 8);
        box(g, UNIFORM, 23, 14, 1, 6);
        // neck and the back of the helmet
        box(g, SKIN_DARK, 13, 10, 6, 2);
        box(g, OUTLINE, 9, 2, 14, 9);
        box(g, HELMET, 10, 3, 12, 7);
        box(g, HELMET_LIGHT, 13, 3, 5, 1);
        box(g, HELMET_DARK, 10, 9, 12, 1);
        px(g, NET, 12, 5);
        px(g, NET, 16, 7);
        px(g, NET, 19, 4);
        px(g, NET, 14, 8);
        // rifle pointing up over the right shoulder
        box(g, OUTLINE, 21, 1, 4, 15);
        box(g, METAL, 22, 2, 2, 11);
        box(g, METAL_LIGHT, 22, 2, 1, 9);
        box(g, WOOD, 22, 12, 2, 3);
        if (firing) {
            muzzleFlash(g, 23, 0, 0, -1);
        }
    }

    /** A fallen soldier lying on its side, the helmet beside it and the rifle dropped. */
    static void fallen(Graphics2D g) {
        shadow(g, 3, 25, 26);
        // body lying across, head to the left
        box(g, OUTLINE, 6, 16, 21, 8);
        box(g, UNIFORM, 7, 17, 19, 6);
        camo(g, 7, 17, 19, 6, 11);
        box(g, WEBBING, 15, 17, 2, 6);
        box(g, BOOT, 25, 17, 3, 6);
        box(g, BOOT_LIGHT, 26, 18, 1, 4);
        box(g, OUTLINE, 1, 16, 7, 7);
        box(g, SKIN, 2, 17, 5, 5);
        px(g, OUTLINE, 3, 19);
        // the helmet rolled off, the rifle on the floor
        box(g, OUTLINE, 4, 8, 8, 5);
        box(g, HELMET, 5, 9, 6, 3);
        box(g, HELMET_DARK, 4, 12, 8, 1);
        box(g, OUTLINE, 10, 26, 18, 3);
        box(g, METAL, 11, 27, 12, 1);
        box(g, WOOD, 23, 27, 4, 1);
    }

    private static void leg(Graphics2D g, int x, int top, Color cloth) {
        box(g, OUTLINE, x - 1, top, 5, 9);
        box(g, cloth, x, top, 3, 6);
        box(g, BOOT, x - 1, top + 6, 6, 3);
        box(g, BOOT_LIGHT, x, top + 6, 3, 1);
    }

    private static void legFront(Graphics2D g, int x, int top, Color cloth) {
        box(g, OUTLINE, x - 1, top, 5, 9);
        box(g, cloth, x, top, 3, 6);
        box(g, BOOT, x - 1, top + 6, 5, 3);
        box(g, BOOT_LIGHT, x, top + 6, 3, 1);
    }

    /** Spots of camouflage in the rectangle, laid out by {@code seed}. */
    private static void camo(Graphics2D g, int x, int y, int w, int h, int seed) {
        Color[] colours = {CAMO_DARK, CAMO_LIGHT, CAMO_BROWN};
        for (int i = 0; i < 7; i++) {
            int sx = x + Math.floorMod(seed * 7 + i * 5, Math.max(1, w - 1));
            int sy = y + Math.floorMod(seed * 3 + i * 4, Math.max(1, h - 1));
            box(g, colours[i % colours.length], sx, sy, 2, 1);
        }
    }

    /** A star-shaped muzzle flash at (x, y), pointing along (dx, dy). */
    private static void muzzleFlash(Graphics2D g, int x, int y, int dx, int dy) {
        g.setColor(new Color(0xff, 0xb0, 0x30, 120));
        g.fillOval(x - 4 + 2 * dx, y - 4 + 2 * dy, 9, 9);   // glow
        box(g, FLASH_OUT, x - 2 + dx, y - 2 + dy, 5, 5);
        box(g, FLASH_MID, x - 1 + dx, y - 1 + dy, 3, 3);
        px(g, FLASH_CORE, x + dx, y + dy);
        px(g, FLASH_MID, x + 3 * dx, y + 3 * dy);
        px(g, FLASH_OUT, x - 3 * dy + dx, y - 3 * dx + dy);
        px(g, FLASH_OUT, x + 3 * dy + dx, y + 3 * dx + dy);
    }

    private static void shadow(Graphics2D g, int x, int y, int w) {
        g.setColor(SHADOW);
        g.fillOval(x, y, w, 4);
    }

    private static void box(Graphics2D g, Color colour, int x, int y, int w, int h) {
        g.setColor(colour);
        g.fillRect(x, y, w, h);
    }

    private static void px(Graphics2D g, Color colour, int x, int y) {
        box(g, colour, x, y, 1, 1);
    }

    private interface Painter {
        void paint(Graphics2D g);
    }

    private static BufferedImage draw(Painter painter) {
        BufferedImage grid = new BufferedImage(GRID, GRID, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = grid.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            painter.paint(g);
        } finally {
            g.dispose();
        }
        BufferedImage out = new BufferedImage(GRID * SCALE, GRID * SCALE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D up = out.createGraphics();
        try {
            up.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            up.drawImage(grid, 0, 0, GRID * SCALE, GRID * SCALE, null);
        } finally {
            up.dispose();
        }
        return out;
    }

    private static void write(Path dir, String name, BufferedImage image) throws IOException {
        ImageIO.write(image, "png", dir.resolve(name).toFile());
        System.out.println("wrote soldier/" + name);
    }
}
