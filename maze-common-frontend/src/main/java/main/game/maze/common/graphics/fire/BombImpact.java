package main.game.maze.common.graphics.fire;

/**
 * What the player's bomb does to the senses when it goes off, shared by both frontends: a deep explosion
 * sound and a heavy screen shake that dies away.
 */
public final class BombImpact {

    /** Classpath path of the explosion sound (made by {@code SoundEffectGenerator}). */
    public static final String SOUND = "/main/game/maze/sounds/bomb-explosion.wav";
    /** Biggest sideways shake of the screen, in pixels. */
    public static final double SHAKE_STRENGTH = 14.0;
    /** How long the screen shakes. */
    public static final double SHAKE_SECONDS = 0.7;

    private static final double FREQUENCY_X = 58.0;
    private static final double FREQUENCY_Y = 47.0;

    private BombImpact() {
    }

    /**
     * Where the screen is pushed {@code elapsedSeconds} after the explosion: {x, y} in pixels, strongest at the
     * start and fading to nothing; {0, 0} before and after the shake.
     */
    public static double[] shakeOffset(double elapsedSeconds) {
        if (elapsedSeconds < 0 || elapsedSeconds >= SHAKE_SECONDS) {
            return new double[] {0, 0};
        }
        double remaining = 1 - elapsedSeconds / SHAKE_SECONDS;
        double envelope = remaining * remaining;
        return new double[] {
            Math.sin(elapsedSeconds * FREQUENCY_X) * SHAKE_STRENGTH * envelope,
            Math.cos(elapsedSeconds * FREQUENCY_Y) * SHAKE_STRENGTH * 0.7 * envelope};
    }
}
