package main.game.maze.common.graphics.fire;

/**
 * What the player sees after picking up health, besides the flash ({@link HealFlashEffect}): a green
 * arrow that pops up over the player's head and rises for {@link #LIFETIME_SECONDS}. One per player;
 * both frontends tick it every frame and draw {@link #ARROW_SPRITE} {@link #arrowLift} above the
 * player's centre (towards the top of the screen) while {@link #active()}.
 */
public final class HealFeedback {

    public static final double LIFETIME_SECONDS = 1.0;
    public static final String ARROW_SPRITE = "/main/game/maze/pickups/heal-arrow.png";
    /** Arrow size as a share of the player size. */
    public static final double ARROW_SIZE_FACTOR = 0.6;

    private double remaining;

    /** A heart was picked up: (re)start the arrow. */
    public synchronized void trigger() {
        remaining = LIFETIME_SECONDS;
    }

    public synchronized void tick(double dt) {
        remaining = Math.max(0, remaining - Math.max(0, dt));
    }

    public synchronized boolean active() {
        return remaining > 0;
    }

    /** 0 just after the heal, 1 when over. */
    public synchronized double progress() {
        return 1 - remaining / LIFETIME_SECONDS;
    }

    /** Distance from the player's centre up to the arrow's centre: just over the head, rising by half a player. */
    public double arrowLift(double playerSize) {
        double head = playerSize * 0.5 + playerSize * ARROW_SIZE_FACTOR * 0.5;
        return head + playerSize * 0.5 * PuffFrame.easeOutCubic(progress());
    }

    public synchronized void reset() {
        remaining = 0;
    }
}
