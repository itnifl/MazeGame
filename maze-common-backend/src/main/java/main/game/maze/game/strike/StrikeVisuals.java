package main.game.maze.game.strike;

/** How the strike weapons look, the same in both frontends: the soldier sprite and the fuse glow of a lying grenade. */
public final class StrikeVisuals {

    /** The soldier of the infantry strike, drawn facing right (mirrored when it faces left). */
    public static final String SOLDIER_SPRITE = "/main/game/maze/pickups/soldier.png";
    /** A soldier is drawn this many times the player's size. */
    public static final double SOLDIER_SIZE_FACTOR = 0.9;
    /** Radius of the glowing marker of a grenade or bomb lying on the floor, in pixels; the fat bomb's is larger. */
    public static final double FUSE_MARKER_RADIUS = 7.0;
    /** The faintest the flickering fuse marker gets (it flickers between this and fully opaque). */
    public static final double MIN_FUSE_ALPHA = 0.35;
    /** The soldiers' gunfire, rate limited so ten soldiers do not play ten sounds at once. */
    public static final String SOLDIER_FIRE_SOUND = "/main/game/maze/sounds/gatling-burst.wav";
    public static final String SOLDIER_FIRE_SOUND_KEY = "infantry.fire";
    public static final long SOLDIER_FIRE_COOLDOWN_MILLIS = 180L;

    private StrikeVisuals() {
    }

    /** Opacity of the fuse marker: it flickers faster as the fuse burns down. */
    public static double fuseAlpha(ScheduledBlast blast, double clockSeconds) {
        double left = blast.fuseLeft();
        double speed = 6 + (1 - left) * 24;
        double flicker = 0.5 + 0.5 * Math.sin(clockSeconds * speed);
        return MIN_FUSE_ALPHA + (1 - MIN_FUSE_ALPHA) * flicker;
    }

    /** Radius of the fuse marker: bigger for bigger blasts, so the fat bomb is easy to see and run from. */
    public static double fuseRadius(ScheduledBlast blast) {
        return FUSE_MARKER_RADIUS * Math.max(1.0, Math.min(2.0, blast.radius() / 200.0));
    }
}
