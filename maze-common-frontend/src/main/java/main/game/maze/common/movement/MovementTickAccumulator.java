package main.game.maze.common.movement;

/**
 * Fixed-timestep accumulator that converts a variable amount of elapsed real
 * time into a whole number of enemy-movement ticks.
 *
 * <p>The gameplay model advances an enemy by exactly its {@code speed} units per
 * movement tick, so the effective enemy velocity is {@code speed × ticksPerSecond}
 * (see {@link GameplayTickRate}). To keep that velocity independent of how long a
 * frame or a background-loop iteration actually takes, callers must accumulate the
 * real elapsed time and run exactly as many ticks as that time represents — never
 * a single tick per iteration. Otherwise the effective rate collapses to
 * {@code 1 / iterationDuration}, which on the JavaFX background loop drops well
 * below the target once the per-iteration AI work (heavier on hard difficulty)
 * dominates the sleep interval, making enemies visibly slower than in libGDX.</p>
 *
 * <p>This is the same catch-up logic libGDX has always run per enemy
 * ({@code moveAccumulator += dt * tickRate}); extracting it here lets both
 * frontends share one implementation so a difficulty-scaled enemy travels the
 * same distance per real-time second everywhere.</p>
 *
 * <p>Instances are not thread-safe; confine each accumulator to a single thread.</p>
 */
public final class MovementTickAccumulator {

    private final double ticksPerSecond;
    private final int maxTicksPerFrame;
    private double accumulatedTicks;

    /**
     * @param ticksPerSecond   target movement ticks per real-time second (&gt; 0),
     *                         typically {@link GameplayTickRate#ENEMY_MOVEMENT_TICKS_PER_SECOND}
     * @param maxTicksPerFrame upper bound on ticks returned from a single
     *                         {@link #accumulate(double)} call (&ge; 1). Caps the
     *                         catch-up burst after a long pause and prevents a
     *                         "spiral of death".
     */
    public MovementTickAccumulator(double ticksPerSecond, int maxTicksPerFrame) {
        if (ticksPerSecond <= 0d) {
            throw new IllegalArgumentException("ticksPerSecond must be > 0");
        }
        if (maxTicksPerFrame < 1) {
            throw new IllegalArgumentException("maxTicksPerFrame must be >= 1");
        }
        this.ticksPerSecond = ticksPerSecond;
        this.maxTicksPerFrame = maxTicksPerFrame;
    }

    /**
     * Accumulates {@code elapsedSeconds} of real time and returns how many
     * movement ticks should run now.
     *
     * <p>Fractional remainder is carried to the next call so the long-run average
     * equals {@code ticksPerSecond}. Any whole ticks beyond {@code maxTicksPerFrame}
     * are dropped (not carried) — matching libGDX — so a single long pause cannot
     * trigger an unbounded burst of movement.</p>
     *
     * @param elapsedSeconds real seconds elapsed since the previous call; values
     *                       &le; 0 are ignored and return {@code 0}
     * @return the number of ticks to run, in {@code [0, maxTicksPerFrame]}
     */
    public int accumulate(double elapsedSeconds) {
        if (elapsedSeconds <= 0d) {
            return 0;
        }
        accumulatedTicks += elapsedSeconds * ticksPerSecond;
        int ticks = (int) accumulatedTicks;
        if (ticks <= 0) {
            return 0;
        }
        accumulatedTicks -= ticks;
        return Math.min(ticks, maxTicksPerFrame);
    }

    /** Discards any carried fractional remainder (e.g. after a pause/restart). */
    public void reset() {
        accumulatedTicks = 0d;
    }
}
