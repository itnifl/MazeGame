package main.game.maze.game.items;

/**
 * The charging of a weapon that does not fire at once (the pulse beam cannon): the attack key starts it, the weapon takes
 * {@code seconds} to energy up, and then the shot leaves. Changing weapon (or dying) cancels the charge and nothing is spent.
 * Shared by both frontends, which only draw the charge and fire the shot when {@link #tick} says so.
 */
public final class PulseCharge {

    private boolean charging;
    private double seconds;
    private double elapsed;

    /** Starts charging for {@code chargeSeconds}; ignored while a charge is already going on. */
    public void start(double chargeSeconds) {
        if (charging) {
            return;
        }
        charging = true;
        seconds = Math.max(0.0, chargeSeconds);
        elapsed = 0.0;
    }

    /** Stops the charge: the shot does not leave. */
    public void cancel() {
        charging = false;
        elapsed = 0.0;
    }

    public boolean charging() {
        return charging;
    }

    /** How far the charge has come: 0 at the start, 1 when the shot leaves. */
    public double progress() {
        return !charging ? 0.0 : seconds <= 0 ? 1.0 : Math.min(1.0, elapsed / seconds);
    }

    /**
     * Advances the charge.
     *
     * @return true in the one tick the charge is complete: fire now
     */
    public boolean tick(double dt) {
        if (!charging) {
            return false;
        }
        elapsed += Math.max(0.0, dt);
        if (elapsed >= seconds) {
            charging = false;
            elapsed = 0.0;
            return true;
        }
        return false;
    }
}
