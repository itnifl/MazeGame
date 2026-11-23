import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;

/**
 * Generates the pickup sprites listed in {@code xmi/items/items.xmi} into
 * {@code main/game/maze/pickups/}: bomb, laser rifle, sledgehammer, power shield cube and a small
 * heart, plus the green arrow shown over the player after a heal. Each is drawn on a 32x32 pixel grid and scaled up 2x (64x64, transparent) to match the
 * chunky pixel-art enemies. The rifle and sledgehammer sprites are also drawn in the player's
 * hands. Deterministic.
 *
 * <pre>java tools/sprite-gen/PickupSpriteGenerator.java [spriteDir]</pre>
 */
public final class PickupSpriteGenerator {

    static final int GRID = 32;
    static final int SCALE = 2;

    private static final Color OUTLINE = new Color(0x1a, 0x14, 0x18);

    private PickupSpriteGenerator() {
    }

    public static void main(String[] args) throws IOException {
        Path dir = Path.of(args.length > 0 ? args[0]
                : "maze-common-frontend/src/main/resources/main/game/maze").resolve("pickups");
        Files.createDirectories(dir);
        write(dir, "bomb.png", bomb());
        write(dir, "laser-rifle.png", laserRifle());
        write(dir, "sledgehammer.png", sledgehammer());
        write(dir, "shield.png", shield());
        write(dir, "heart.png", heart());
        write(dir, "heal-arrow.png", healArrow());
        write(dir, "extra-life.png", extraLife());
        write(dir, "gatling-gun.png", gatlingGun());
        write(dir, "pulse-cannon.png", pulseCannon());
        // the shop weapons (#164); the soldiers of the infantry strike are drawn by SoldierSpriteGenerator
        write(dir, "grenade.png", grenade(new Color(0x4a, 0x5e, 0x2c), new Color(0x7a, 0x96, 0x48), null));
        write(dir, "plasma-grenade.png", grenade(new Color(0x4a, 0x20, 0x78), new Color(0x9a, 0x5a, 0xe8),
                new Color(0xd8, 0xa8, 0xff)));
        write(dir, "nuke-grenade.png", nukeGrenade());
        write(dir, "bunker-buster.png", bunkerBuster());
        write(dir, "fat-bomb.png", fatBomb());
        write(dir, "airstrike.png", airstrike());
        write(dir, "infantry-strike.png", infantryStrike());
        write(dir, "life-refill.png", lifeRefill());
        // what an airstrike drops: the bomb falling nose down, and the shadow it casts on the floor
        Path strike = dir.resolveSibling("strike");
        Files.createDirectories(strike);
        write(strike, "aerial-bomb.png", aerialBomb());
        write(strike, "bomb-shadow.png", bombShadow());
    }

    /** A pineapple hand grenade: a ridged egg body, the lever and the ring; an optional glow (plasma). */
    static BufferedImage grenade(Color body, Color light, Color glow) {
        return draw(g -> {
            if (glow != null) {
                g.setColor(new Color(glow.getRed(), glow.getGreen(), glow.getBlue(), 70));
                g.fill(new Ellipse2D.Double(4, 6, 24, 25));
            }
            g.setColor(OUTLINE);
            g.fill(new Ellipse2D.Double(8, 10, 16, 20));
            g.setColor(body);
            g.fill(new Ellipse2D.Double(9, 11, 14, 18));
            // the ridges
            g.setColor(OUTLINE);
            for (int y = 15; y <= 25; y += 4) {
                g.fillRect(10, y, 12, 1);
            }
            g.fillRect(15, 12, 1, 16);
            g.setColor(light);
            g.fillRect(11, 13, 3, 2);
            g.fillRect(11, 17, 2, 2);
            if (glow != null) {
                g.setColor(glow);
                g.fillRect(17, 17, 4, 2);
                g.fillRect(17, 21, 4, 2);
            }
            // the top, the lever and the ring
            g.setColor(new Color(0x8a, 0x8e, 0x98));
            g.fillRect(13, 7, 6, 4);
            g.fillRect(19, 8, 3, 9);
            g.setColor(new Color(0xc8, 0xcc, 0xd4));
            g.setStroke(new BasicStroke(1.5f));
            g.draw(new Ellipse2D.Double(8, 3, 6, 6));
        });
    }

    /** The small nuclear grenade: a yellow and black canister with the radiation sign and a green glow. */
    static BufferedImage nukeGrenade() {
        return draw(g -> {
            g.setColor(new Color(0x9a, 0xff, 0x40, 70));
            g.fill(new Ellipse2D.Double(2, 4, 28, 28));
            g.setColor(OUTLINE);
            g.fillRoundRect(8, 9, 16, 21, 6, 6);
            g.setColor(new Color(0xf0, 0xc8, 0x10));
            g.fillRoundRect(9, 10, 14, 19, 5, 5);
            // the radiation sign: three black wedges round a dot
            g.setColor(OUTLINE);
            g.fillArc(10, 13, 12, 12, 60, 60);
            g.fillArc(10, 13, 12, 12, 180, 60);
            g.fillArc(10, 13, 12, 12, 300, 60);
            g.setColor(new Color(0xf0, 0xc8, 0x10));
            g.fill(new Ellipse2D.Double(14, 17, 4, 4));
            g.setColor(OUTLINE);
            g.fill(new Ellipse2D.Double(15, 18, 2, 2));
            // the cap and the lever
            g.setColor(new Color(0x6b, 0x6e, 0x78));
            g.fillRect(12, 5, 8, 5);
            g.setColor(new Color(0xb0, 0xff, 0x60));
            g.fillRect(14, 6, 4, 2);
        });
    }

    /** The bunker buster rocket: a long olive rocket with a hardened steel nose and fins, pointing right. */
    static BufferedImage bunkerBuster() {
        return draw(g -> {
            g.setColor(OUTLINE);
            g.fillRect(3, 12, 22, 9);
            g.fillPolygon(new Polygon(new int[] {25, 31, 25}, new int[] {11, 16, 22}, 3));
            g.setColor(new Color(0x5a, 0x66, 0x34));
            g.fillRect(4, 13, 20, 7);
            g.setColor(new Color(0x80, 0x8e, 0x50));
            g.fillRect(4, 13, 20, 2);
            // the hardened nose
            g.setColor(new Color(0x9a, 0x9e, 0xae));
            g.fillPolygon(new Polygon(new int[] {24, 30, 24}, new int[] {13, 16, 20}, 3));
            // warning band and fins
            g.setColor(new Color(0xf0, 0xc8, 0x10));
            g.fillRect(18, 13, 2, 7);
            g.setColor(OUTLINE);
            g.fillPolygon(new Polygon(new int[] {2, 8, 2}, new int[] {7, 12, 12}, 3));
            g.fillPolygon(new Polygon(new int[] {2, 8, 2}, new int[] {26, 21, 21}, 3));
            // exhaust
            g.setColor(new Color(0xff, 0x9a, 0x1e));
            g.fillRect(0, 15, 3, 3);
        });
    }

    /** The fat bomb: a big round bomb with a red band and a long lit fuse. */
    static BufferedImage fatBomb() {
        return draw(g -> {
            g.setColor(OUTLINE);
            g.fill(new Ellipse2D.Double(1, 6, 28, 26));
            g.setColor(new Color(0x2c, 0x2c, 0x34));
            g.fill(new Ellipse2D.Double(2, 7, 26, 24));
            g.setColor(new Color(0xb0, 0x20, 0x20));
            g.fillRect(3, 17, 24, 4);
            g.setColor(new Color(0x55, 0x58, 0x66));
            g.fill(new Ellipse2D.Double(6, 10, 7, 5));
            g.setColor(new Color(0x6b, 0x6e, 0x78));
            g.fillRect(12, 3, 7, 4);
            g.setColor(new Color(0x8a, 0x6a, 0x3a));
            g.fillRect(18, 1, 2, 3);
            g.fillRect(20, 0, 3, 2);
            g.setColor(new Color(0xff, 0xec, 0x7a));
            g.fillRect(24, 0, 3, 3);
        });
    }

    /** The airstrike: a bomber seen from above, dropping bombs. */
    static BufferedImage airstrike() {
        return draw(g -> {
            Color hull = new Color(0x70, 0x7a, 0x86);
            g.setColor(OUTLINE);
            g.fillRect(14, 2, 5, 22);
            g.fillRect(3, 9, 27, 5);
            g.fillRect(9, 20, 15, 3);
            g.setColor(hull);
            g.fillRect(15, 3, 3, 20);
            g.fillRect(4, 10, 25, 3);
            g.fillRect(10, 21, 13, 1);
            g.setColor(new Color(0x9a, 0xd0, 0xff));
            g.fillRect(15, 4, 3, 3);
            // bombs falling
            g.setColor(OUTLINE);
            g.fillOval(6, 24, 4, 6);
            g.fillOval(22, 25, 4, 6);
            g.fillOval(14, 27, 4, 5);
        });
    }

    /** The infantry strike: a soldier's helmet in front of crossed rifles. */
    static BufferedImage infantryStrike() {
        return draw(g -> {
            g.setColor(OUTLINE);
            g.setStroke(new BasicStroke(3f));
            g.drawLine(3, 28, 28, 6);
            g.drawLine(3, 6, 28, 28);
            g.setColor(new Color(0x6a, 0x4a, 0x2a));
            g.setStroke(new BasicStroke(1.5f));
            g.drawLine(3, 28, 28, 6);
            g.drawLine(3, 6, 28, 28);
            g.setColor(OUTLINE);
            g.fillArc(6, 8, 20, 20, 0, 180);
            g.fillRect(4, 17, 24, 3);
            g.setColor(new Color(0x4a, 0x5e, 0x2c));
            g.fillArc(7, 9, 18, 18, 0, 180);
            g.fillRect(5, 17, 22, 2);
            g.setColor(new Color(0x7a, 0x96, 0x48));
            g.fillRect(11, 11, 4, 2);
        });
    }

    /** The extra life of the shop: a white medkit with a red cross. */
    static BufferedImage lifeRefill() {
        return draw(g -> {
            g.setColor(OUTLINE);
            g.fillRoundRect(3, 8, 26, 21, 4, 4);
            g.fillRect(11, 4, 10, 5);
            g.setColor(new Color(0xee, 0xee, 0xf2));
            g.fillRoundRect(4, 9, 24, 19, 3, 3);
            g.setColor(new Color(0xb8, 0xb8, 0xc4));
            g.fillRect(4, 24, 24, 4);
            g.setColor(new Color(0xd8, 0x20, 0x30));
            g.fillRect(14, 11, 4, 12);
            g.fillRect(10, 15, 12, 4);
        });
    }

    /** An aircraft bomb falling nose down: olive drab body with a yellow band, tail fins at the top. */
    static BufferedImage aerialBomb() {
        return draw(g -> {
            // tail fins
            g.setColor(OUTLINE);
            g.fillRect(9, 1, 14, 7);
            g.setColor(new Color(0x5a, 0x63, 0x4a));
            g.fillRect(10, 2, 12, 5);
            g.setColor(OUTLINE);
            g.fillRect(15, 1, 2, 7);
            // body
            g.setColor(OUTLINE);
            g.fill(new Ellipse2D.Double(9, 5, 14, 26));
            g.setColor(new Color(0x4b, 0x55, 0x2e));
            g.fill(new Ellipse2D.Double(10, 6, 12, 24));
            g.setColor(new Color(0x6e, 0x7c, 0x45));
            g.fillRect(12, 9, 3, 15);
            // yellow band and nose fuze
            g.setColor(new Color(0xe8, 0xb9, 0x23));
            g.fillRect(10, 11, 12, 3);
            g.setColor(new Color(0x9a, 0x9e, 0xae));
            g.fillRect(15, 29, 2, 2);
        });
    }

    /** The soft dark ellipse a falling bomb casts on the floor. */
    static BufferedImage bombShadow() {
        return draw(g -> {
            g.setColor(new Color(0, 0, 0, 60));
            g.fill(new Ellipse2D.Double(4, 10, 24, 12));
            g.setColor(new Color(0, 0, 0, 90));
            g.fill(new Ellipse2D.Double(8, 12, 16, 8));
        });
    }

    static BufferedImage bomb() {
        return draw(g -> {
            g.setColor(OUTLINE);
            g.fill(new Ellipse2D.Double(5, 9, 21, 21));
            g.setColor(new Color(0x2c, 0x2c, 0x34));
            g.fill(new Ellipse2D.Double(6, 10, 19, 19));
            g.setColor(new Color(0x55, 0x58, 0x66));
            g.fill(new Ellipse2D.Double(9, 13, 6, 5));
            g.setColor(new Color(0x9a, 0x9e, 0xae));
            g.fillRect(10, 14, 2, 2);
            // cap and fuse
            g.setColor(new Color(0x6b, 0x6e, 0x78));
            g.fillRect(13, 7, 5, 3);
            g.setColor(new Color(0x8a, 0x6a, 0x3a));
            g.fillRect(17, 5, 2, 3);
            g.fillRect(19, 4, 2, 2);
            g.fillRect(21, 3, 1, 2);
            // spark
            g.setColor(new Color(0xff, 0x9a, 0x1e));
            g.fillRect(21, 0, 3, 3);
            g.setColor(new Color(0xff, 0xec, 0x7a));
            g.fillRect(22, 1, 1, 1);
            g.setColor(new Color(0xff, 0x5a, 0x10));
            g.fillRect(24, 2, 1, 1);
            g.fillRect(20, 1, 1, 1);
        });
    }

    static BufferedImage laserRifle() {
        return draw(g -> {
            // stock
            g.setColor(OUTLINE);
            g.fillRect(1, 13, 9, 8);
            g.setColor(new Color(0x5a, 0x3c, 0x24));
            g.fillRect(2, 14, 7, 6);
            g.setColor(new Color(0x7a, 0x55, 0x34));
            g.fillRect(2, 14, 7, 2);
            // body
            g.setColor(OUTLINE);
            g.fillRect(8, 11, 15, 8);
            g.setColor(new Color(0x4a, 0x55, 0x66));
            g.fillRect(9, 12, 13, 6);
            g.setColor(new Color(0x7e, 0x8c, 0xa0));
            g.fillRect(9, 12, 13, 2);
            // energy cell
            g.setColor(new Color(0xff, 0x3a, 0x3a));
            g.fillRect(12, 15, 6, 2);
            g.setColor(new Color(0xff, 0xa0, 0xa0));
            g.fillRect(12, 15, 2, 1);
            // grip and trigger
            g.setColor(OUTLINE);
            g.fillRect(12, 18, 4, 6);
            g.setColor(new Color(0x33, 0x3a, 0x45));
            g.fillRect(13, 18, 2, 5);
            // barrel and emitter
            g.setColor(OUTLINE);
            g.fillRect(22, 13, 8, 4);
            g.setColor(new Color(0x9c, 0xa8, 0xb8));
            g.fillRect(22, 14, 7, 2);
            g.setColor(new Color(0xff, 0x40, 0x40));
            g.fillRect(29, 13, 3, 4);
            g.setColor(new Color(0xff, 0xd0, 0xd0));
            g.fillRect(30, 14, 1, 2);
        });
    }

    /** Rotary barrels, a drum magazine and a grip: the gun a zombie sergeant drops. */
    static BufferedImage gatlingGun() {
        return draw(g -> {
            // grip and stock
            g.setColor(OUTLINE);
            g.fillRect(1, 15, 7, 8);
            g.setColor(new Color(0x5a, 0x3c, 0x24));
            g.fillRect(2, 16, 5, 6);
            // body
            g.setColor(OUTLINE);
            g.fillRect(7, 11, 10, 10);
            g.setColor(new Color(0x55, 0x5c, 0x48));
            g.fillRect(8, 12, 8, 8);
            g.setColor(new Color(0x80, 0x88, 0x6a));
            g.fillRect(8, 12, 8, 2);
            // drum magazine
            g.setColor(OUTLINE);
            g.fill(new Ellipse2D.Double(8, 4, 9, 9));
            g.setColor(new Color(0xc8, 0x9a, 0x2e));
            g.fill(new Ellipse2D.Double(9, 5, 7, 7));
            g.setColor(new Color(0xf0, 0xd0, 0x70));
            g.fillRect(10, 6, 2, 2);
            // rotary barrels
            g.setColor(OUTLINE);
            g.fillRect(16, 11, 15, 10);
            g.setColor(new Color(0x7a, 0x82, 0x90));
            g.fillRect(17, 12, 13, 2);
            g.fillRect(17, 15, 13, 2);
            g.fillRect(17, 18, 13, 2);
            g.setColor(new Color(0x3a, 0x3e, 0x48));
            g.fillRect(17, 14, 13, 1);
            g.fillRect(17, 17, 13, 1);
            // muzzle ring
            g.setColor(new Color(0xb0, 0xb6, 0xc2));
            g.fillRect(28, 11, 3, 10);
        });
    }

    static BufferedImage sledgehammer() {
        return draw(g -> {
            // handle, diagonal from bottom-left
            g.setStroke(new BasicStroke(4f));
            g.setColor(OUTLINE);
            g.drawLine(5, 28, 19, 12);
            g.setStroke(new BasicStroke(2f));
            g.setColor(new Color(0x9a, 0x6a, 0x38));
            g.drawLine(5, 28, 19, 12);
            g.setColor(new Color(0x5a, 0x3a, 0x1e));
            g.fillRect(4, 26, 3, 3);
            // block head across the end of the handle (a mallet, not a shovel)
            double ux = 0.68, uy = -0.73;          // along the handle, towards the head
            double px = 0.73, py = 0.68;           // across the handle
            double cx = 21, cy = 10;               // head centre
            Polygon head = rect(cx, cy, px, py, ux, uy, 8.5, 4.5);
            Polygon face = rect(cx, cy, px, py, ux, uy, 7.0, 3.0);
            g.setColor(OUTLINE);
            g.fillPolygon(head);
            g.setColor(new Color(0x6a, 0x70, 0x7a));
            g.fillPolygon(face);
            g.setColor(new Color(0xb8, 0xbe, 0xc8));
            g.fillPolygon(rect(cx + ux * 1.5, cy + uy * 1.5, px, py, ux, uy, 6.0, 1.0));
            g.setColor(new Color(0x45, 0x4a, 0x52));
            g.fillPolygon(rect(cx - ux * 2, cy - uy * 2, px, py, ux, uy, 6.0, 0.8));
        });
    }

    /** The pulse beam cannon: a fat dark-steel barrel with glowing blue coils and a charged blue-white muzzle. */
    static BufferedImage pulseCannon() {
        return draw(g -> {
            // blue glow behind
            g.setColor(new Color(0x40, 0xa8, 0xff, 60));
            g.fill(new Ellipse2D.Double(1, 6, 30, 22));
            // the body and the barrel
            g.setColor(OUTLINE);
            g.fillRect(2, 11, 24, 11);
            g.setColor(new Color(0x3a, 0x40, 0x50));
            g.fillRect(3, 12, 22, 9);
            g.setColor(new Color(0x5c, 0x64, 0x78));
            g.fillRect(3, 12, 22, 2);
            // grip and stock
            g.setColor(OUTLINE);
            g.fillRect(6, 21, 5, 7);
            g.setColor(new Color(0x2a, 0x2e, 0x3a));
            g.fillRect(7, 21, 3, 6);
            // glowing coils round the barrel
            for (int x = 8; x <= 20; x += 4) {
                g.setColor(new Color(0x10, 0x30, 0x60));
                g.fillRect(x, 10, 2, 13);
                g.setColor(new Color(0x60, 0xd8, 0xff));
                g.fillRect(x, 11, 2, 11);
                g.setColor(new Color(0xd8, 0xfa, 0xff));
                g.fillRect(x, 13, 1, 7);
            }
            // the muzzle, charged
            g.setColor(OUTLINE);
            g.fillRect(25, 9, 5, 15);
            g.setColor(new Color(0x2a, 0x60, 0xc8));
            g.fillRect(26, 10, 3, 13);
            g.setColor(new Color(0xff, 0xff, 0xff));
            g.fillRect(27, 14, 3, 5);
            g.setColor(new Color(0xb8, 0xf0, 0xff, 180));
            g.fill(new Ellipse2D.Double(26, 12, 6, 9));
        });
    }

    static BufferedImage shield() {
        return draw(g -> {
            // glow
            g.setColor(new Color(0x40, 0xd8, 0xff, 70));
            g.fill(new Ellipse2D.Double(0, 0, 32, 32));
            // isometric cube
            Polygon top = new Polygon(new int[] {16, 27, 16, 5}, new int[] {4, 10, 16, 10}, 4);
            Polygon left = new Polygon(new int[] {5, 16, 16, 5}, new int[] {10, 16, 28, 22}, 4);
            Polygon right = new Polygon(new int[] {16, 27, 27, 16}, new int[] {16, 10, 22, 28}, 4);
            g.setColor(OUTLINE);
            g.setStroke(new BasicStroke(2f));
            g.drawPolygon(top);
            g.drawPolygon(left);
            g.drawPolygon(right);
            g.setColor(new Color(0x9c, 0xf2, 0xff));
            g.fillPolygon(top);
            g.setColor(new Color(0x28, 0x9c, 0xd8));
            g.fillPolygon(left);
            g.setColor(new Color(0x1a, 0x6a, 0xb0));
            g.fillPolygon(right);
            // energy core
            g.setColor(new Color(0xff, 0xff, 0xff));
            g.fillRect(15, 9, 3, 2);
            g.setColor(new Color(0xe0, 0xff, 0xff));
            g.fillRect(9, 16, 2, 4);
            g.fillRect(21, 16, 2, 4);
        });
    }

    static BufferedImage heart() {
        return draw(g -> {
            // fills the frame like the other items, so it reads at the same size in the maze
            Polygon heart = new Polygon();
            for (int i = 0; i < 64; i++) {
                double t = Math.PI * 2 * i / 64;
                double x = 16 * Math.pow(Math.sin(t), 3);
                double y = 13 * Math.cos(t) - 5 * Math.cos(2 * t) - 2 * Math.cos(3 * t) - Math.cos(4 * t);
                heart.addPoint((int) Math.round(16 + x * 0.88), (int) Math.round(15 - y * 0.88));
            }
            g.setColor(OUTLINE);
            g.setStroke(new BasicStroke(2f));
            g.drawPolygon(heart);
            g.setColor(new Color(0xe0, 0x22, 0x3a));
            g.fillPolygon(heart);
            g.setColor(new Color(0xff, 0x8a, 0x9a));
            g.fillRect(7, 6, 4, 4);
            g.setColor(new Color(0xff, 0xff, 0xff));
            g.fillRect(7, 6, 2, 2);
            g.setColor(new Color(0x9a, 0x10, 0x22));
            g.fillRect(18, 20, 4, 2);
            g.fillRect(22, 16, 2, 3);
        });
    }

    /** Extra life: a gold medallion with a white "+1", also the lives icon in the weapons HUD. */
    static BufferedImage extraLife() {
        return draw(g -> {
            g.setColor(OUTLINE);
            g.fill(new Ellipse2D.Double(1, 1, 30, 30));
            g.setColor(new Color(0xb8, 0x7a, 0x10));
            g.fill(new Ellipse2D.Double(2, 2, 28, 28));
            g.setColor(new Color(0xff, 0xc8, 0x2e));
            g.fill(new Ellipse2D.Double(4, 4, 24, 24));
            g.setColor(new Color(0xff, 0xec, 0x8a));
            g.fillRect(8, 6, 4, 2);
            g.fillRect(6, 8, 2, 4);
            // "+1" in white with a dark outline
            g.setColor(OUTLINE);
            g.fillRect(5, 14, 11, 4);
            g.fillRect(9, 10, 3, 12);
            g.fillRect(17, 8, 7, 16);
            g.setColor(Color.WHITE);
            g.fillRect(6, 15, 9, 2);
            g.fillRect(10, 11, 1, 10);
            g.fillRect(19, 9, 3, 14);
            g.fillRect(18, 10, 1, 2);
        });
    }

    /** Green arrow shown over the player's head after a heart is picked up (HealFeedback.ARROW_SPRITE). */
    static BufferedImage healArrow() {
        return draw(g -> {
            Polygon arrow = new Polygon(new int[] {16, 29, 21, 21, 11, 11, 3}, new int[] {1, 15, 15, 30, 30, 15, 15}, 7);
            g.setColor(OUTLINE);
            g.setStroke(new BasicStroke(2f));
            g.drawPolygon(arrow);
            g.setColor(new Color(0x2e, 0xc8, 0x4a));
            g.fillPolygon(arrow);
            g.setColor(new Color(0xa8, 0xff, 0x9a));
            g.fillRect(13, 8, 3, 3);
            g.fillRect(13, 15, 2, 12);
            g.setColor(new Color(0x16, 0x7a, 0x2c));
            g.fillRect(19, 16, 2, 13);
        });
    }

    /** Rectangle centred at (cx, cy): {@code halfLength} along (px, py), {@code halfWidth} along (ux, uy). */
    private static Polygon rect(double cx, double cy, double px, double py, double ux, double uy,
                                double halfLength, double halfWidth) {
        Polygon p = new Polygon();
        double[][] corners = {{1, 1}, {1, -1}, {-1, -1}, {-1, 1}};
        for (double[] c : corners) {
            p.addPoint((int) Math.round(cx + px * halfLength * c[0] + ux * halfWidth * c[1]),
                    (int) Math.round(cy + py * halfLength * c[0] + uy * halfWidth * c[1]));
        }
        return p;
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
        System.out.println("wrote " + dir.getFileName() + "/" + name);
    }
}
