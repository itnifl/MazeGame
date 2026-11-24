package main.game.maze.game.strike;

import main.game.maze.game.items.PickupCatalog;
import main.game.maze.game.items.PickupKind;

/**
 * How the strike weapons look and sound, the same in both frontends: the animated soldier sprites, the grenade lying on
 * the floor with its fuse, the soldiers' gunfire.
 */
public final class StrikeVisuals {

    /** Folder of the soldier sprites (made by {@code tools/sprite-gen/SoldierSpriteGenerator.java}). */
    public static final String SOLDIER_DIR = "/main/game/maze/soldier/";
    /** Folder of the red soldiers of the UFO raid (made by {@code tools/sprite-gen/RedSoldierSpriteGenerator.java}). */
    public static final String RED_SOLDIER_DIR = "/main/game/maze/soldier-red/";
    /** The sprite the soldier sprites are named after (a standing soldier seen from the side, facing right). */
    public static final String SOLDIER_SPRITE = SOLDIER_DIR + "soldier-side-stand.png";
    /** A soldier is drawn this many times the player's size. */
    public static final double SOLDIER_SIZE_FACTOR = 1.0;
    /** A soldier changes leg every this many pixels walked. */
    public static final double STRIDE_PX = 9.0;
    /** Radius of the glowing marker of a grenade or bomb lying on the floor, in pixels; the fat bomb's is larger. */
    public static final double FUSE_MARKER_RADIUS = 7.0;
    /** The faintest the flickering fuse marker gets (it flickers between this and fully opaque). */
    public static final double MIN_FUSE_ALPHA = 0.35;
    /** A grenade lying on the floor is drawn this many times the item size; the nuclear one larger, so it is seen. */
    public static final double LYING_SIZE_FACTOR = 0.7;
    public static final double LYING_NUCLEAR_SIZE_FACTOR = 1.1;
    /** The bomb an airstrike drops, falling nose down, and the shadow it casts. */
    public static final String AERIAL_BOMB_SPRITE = "/main/game/maze/strike/aerial-bomb.png";
    public static final String BOMB_SHADOW_SPRITE = "/main/game/maze/strike/bomb-shadow.png";
    /** An airstrike bomb is seen for this long before it hits, falling from {@link #FALL_HEIGHT_PX} above its target. */
    public static final double FALL_SECONDS = 0.7;
    public static final double FALL_HEIGHT_PX = 260.0;

    /**
     * An airstrike bomb on its way down: {@code height} pixels above the point it will hit (drawn that far "up" the
     * screen), its size, and how dark and big its shadow on the floor is (0 to 1, growing as it comes down).
     */
    public record Falling(double x, double y, double height, double size, double shadow) {
    }
    /** The soldiers' gunfire, rate limited so ten soldiers do not play ten sounds at once. */
    public static final String SOLDIER_FIRE_SOUND = "/main/game/maze/sounds/gatling-burst.wav";
    public static final String SOLDIER_FIRE_SOUND_KEY = "infantry.fire";
    public static final long SOLDIER_FIRE_COOLDOWN_MILLIS = 180L;
    /** A soldier falls. */
    public static final String SOLDIER_DOWN_SOUND = "/main/game/maze/sounds/soldier-down.wav";

    private StrikeVisuals() {
    }

    /**
     * The sprite of a soldier now: seen from the side (mirrored when facing left, {@link #soldierFlipX}), from the front
     * (facing down) or from the back (facing up); standing, in one of two strides while walking, firing, or fallen.
     */
    public static String soldierSprite(InfantrySquad.SoldierView soldier) {
        return soldierSprite(soldier, SOLDIER_DIR);
    }

    /** As above, for the soldiers of another folder (the red ones, {@link #RED_SOLDIER_DIR}). */
    public static String soldierSprite(InfantrySquad.SoldierView soldier, String dir) {
        if (soldier.fallen()) {
            return dir + "soldier-fallen.png";
        }
        String facing = soldier.facingY() < 0 ? "up" : soldier.facingY() > 0 ? "down" : "side";
        String pose;
        if (soldier.firing()) {
            pose = "fire";
        } else if (soldier.walking()) {
            pose = "walk" + (1 + (int) (soldier.walked() / STRIDE_PX) % 2);
        } else {
            pose = "stand";
        }
        return dir + "soldier-" + facing + "-" + pose + ".png";
    }

    /** Whether the soldier's sprite is mirrored: seen from the side, facing left. */
    public static boolean soldierFlipX(InfantrySquad.SoldierView soldier) {
        return !soldier.fallen() && soldier.facingY() == 0 && soldier.facingX() < 0;
    }

    /** The sprite of the weapon a blast lies on the floor as (the grenade itself), or null to draw only the fuse glow. */
    public static String lyingSprite(ScheduledBlast blast, PickupCatalog items) {
        return lyingSprite(blast, items, 0.0);
    }

    /** The weapons drawn armed where they lie (a burning fuse, a pulled pin, a blinking light): two frames each. */
    public static final java.util.Set<PickupKind> ARMED = java.util.EnumSet.of(PickupKind.BOMB, PickupKind.FAT_BOMB,
            PickupKind.GRENADE, PickupKind.PLASMA_GRENADE, PickupKind.NUKE_GRENADE);
    /** Where the armed frames are: {@code armed-<kind>-1.png} and {@code -2.png} (from {@code PickupSpriteGenerator}). */
    public static final String ARMED_DIR = "/main/game/maze/strike/";

    /**
     * What a dropped weapon looks like where it lies at {@code clockSeconds}: the weapon itself, armed (its two frames
     * flip faster as the fuse burns down); a weapon without armed frames is drawn as its item sprite; null for what
     * does not lie on the floor.
     */
    public static String lyingSprite(ScheduledBlast blast, PickupCatalog items, double clockSeconds) {
        if (!blast.showsFuse() || blast.source() == null) {
            return null;
        }
        if (!ARMED.contains(blast.source())) {
            return items.spec(blast.source()).sprite();
        }
        double framesPerSecond = 4 + (1 - blast.fuseLeft()) * 10;
        int frame = 1 + (int) Math.floor(Math.max(0, clockSeconds) * framesPerSecond) % 2;
        return ARMED_DIR + "armed-" + blast.source().name().toLowerCase(java.util.Locale.ROOT).replace('_', '-')
                + "-" + frame + ".png";
    }

    /**
     * The airstrike bomb as it is seen falling, or null while it is still too high (or for anything that is not dropped
     * from the air).
     */
    public static Falling fallingBomb(ScheduledBlast blast, PickupCatalog items) {
        if (blast.source() != PickupKind.AIRSTRIKE || blast.showsFuse() || blast.secondsLeft() > FALL_SECONDS) {
            return null;
        }
        double left = blast.secondsLeft() / FALL_SECONDS;     // 1 when it comes into view, 0 when it hits
        double height = FALL_HEIGHT_PX * left * left;            // it speeds up as it falls
        return new Falling(blast.x(), blast.y(), height, items.itemSize() * LYING_SIZE_FACTOR, 1 - left);
    }

    /** Size of the lying weapon's sprite. */
    public static double lyingSize(ScheduledBlast blast, PickupCatalog items) {
        return items.itemSize() * (blast.nuclear() ? LYING_NUCLEAR_SIZE_FACTOR : LYING_SIZE_FACTOR);
    }

    /** Opacity of the fuse marker: it flickers faster as the fuse burns down. */
    public static double fuseAlpha(ScheduledBlast blast, double clockSeconds) {
        double left = blast.fuseLeft();
        double speed = 6 + (1 - left) * 24;
        double flicker = 0.5 + 0.5 * Math.sin(clockSeconds * speed);
        return MIN_FUSE_ALPHA + (1 - MIN_FUSE_ALPHA) * flicker;
    }

    /** Colour {red, green, blue} of the fuse marker: radioactive green round a nuclear grenade, fuse amber otherwise. */
    public static float[] fuseColour(ScheduledBlast blast) {
        return blast.nuclear() ? new float[] {0.55f, 1.0f, 0.25f} : new float[] {1.0f, 0.75f, 0.28f};
    }

    /** Radius of the fuse marker: bigger for bigger blasts, so the fat bomb and the nuke are easy to see and run from. */
    public static double fuseRadius(ScheduledBlast blast) {
        return FUSE_MARKER_RADIUS * Math.max(1.0, Math.min(2.5, blast.radius() / 200.0));
    }
}
