package main.game.maze.game.hazard;

/**
 * A cooldown for the player's teleports, so a second ghost or hole cannot send them straight back. One per
 * player; time is whatever steady clock the frontend has, in seconds.
 */
public final class TeleportGate {

    private final double cooldownSeconds;
    private double lastUse = -Double.MAX_VALUE;

    public TeleportGate(double cooldownSeconds) {
        this.cooldownSeconds = Math.max(0, cooldownSeconds);
    }

    /** Takes the teleport if the cooldown is over (and starts the next cooldown); false while it is not. */
    public boolean tryUse(double nowSeconds) {
        if (nowSeconds - lastUse < cooldownSeconds) {
            return false;
        }
        lastUse = nowSeconds;
        return true;
    }

    /** New level: ready at once. */
    public void reset() {
        lastUse = -Double.MAX_VALUE;
    }
}
