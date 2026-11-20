package main.game.maze.game.items;

import java.util.function.LongSupplier;

/**
 * The power shield from a shield cube: while active the player takes no damage. A new cube
 * restarts the full duration. Clock-based (nanoseconds), so it needs no ticking; thread-safe.
 */
public final class PowerShield {

    private static final double NANOS = 1_000_000_000.0;

    private final LongSupplier clockNanos;
    private volatile long activeUntilNanos = Long.MIN_VALUE;

    public PowerShield() {
        this(System::nanoTime);
    }

    public PowerShield(LongSupplier clockNanos) {
        this.clockNanos = clockNanos;
    }

    public void activate(double seconds) {
        activeUntilNanos = clockNanos.getAsLong() + (long) (Math.max(0, seconds) * NANOS);
    }

    public boolean isActive() {
        return remainingSeconds() > 0;
    }

    public double remainingSeconds() {
        if (activeUntilNanos == Long.MIN_VALUE) {
            return 0;
        }
        return Math.max(0, (activeUntilNanos - clockNanos.getAsLong()) / NANOS);
    }

    /** New level / death: no shield. */
    public void reset() {
        activeUntilNanos = Long.MIN_VALUE;
    }
}
