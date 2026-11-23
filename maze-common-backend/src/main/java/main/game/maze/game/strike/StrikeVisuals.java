package main.game.maze.game.strike;

import main.game.maze.game.items.PickupCatalog;

/**
 * How the strike weapons look and sound, the same in both frontends: the animated soldier sprites, the grenade lying on
 * the floor with its fuse, the soldiers' gunfire.
 */
public final class StrikeVisuals {

    /** Folder of the soldier sprites (made by {@code tools/sprite-gen/SoldierSpriteGenerator.java}). */
    public static final String SOLDIER_DIR = "/main/game/maze/soldier/";
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
        if (soldier.fallen()) {
            return SOLDIER_DIR + "soldier-fallen.png";
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
        return SOLDIER_DIR + "soldier-" + facing + "-" + pose + ".png";
    }

    /** Whether the soldier's sprite is mirrored: seen from the side, facing left. */
    public static boolean soldierFlipX(InfantrySquad.SoldierView soldier) {
        return !soldier.fallen() && soldier.facingY() == 0 && soldier.facingX() < 0;
    }

    /** The sprite of the weapon a blast lies on the floor as (the grenade itself), or null to draw only the fuse glow. */
    public static String lyingSprite(ScheduledBlast blast, PickupCatalog items) {
        return blast.showsFuse() && blast.source() != null ? items.spec(blast.source()).sprite() : null;
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
