package main.game.maze.common.movement;

/**
 * Single source of truth for the enemy movement tick rate shared by every
 * frontend.
 *
 * <p>The gameplay model integrates enemy movement as a fixed number of discrete
 * ticks per second where each tick advances an enemy by exactly its {@code speed}
 * units (see {@link PatrolMovementService} — the {@code deltaSeconds} argument is
 * used only for time accounting, never for the per-tick distance). The effective
 * enemy speed is therefore {@code speed × ticksPerSecond}.</p>
 *
 * <p>Both frontends MUST drive their enemy-movement cadence from this constant so
 * a difficulty-scaled enemy travels the same distance per real-time second in
 * libGDX and JavaFX. Historically libGDX scaled its velocities by {@code 30} while
 * the JavaFX background loop slept {@code 100 ms} (10&nbsp;Hz), making JavaFX
 * enemies move at one third of the intended speed — most visible on hard
 * difficulty where the scaled speed is highest.</p>
 */
public final class GameplayTickRate {

    /**
     * Canonical enemy movement ticks per second. libGDX scales its per-tick
     * speeds by this factor to obtain a per-second velocity; JavaFX runs its
     * background movement loop at this frequency.
     */
    public static final double ENEMY_MOVEMENT_TICKS_PER_SECOND = 30d;

    /**
     * Real seconds represented by a single movement tick. Pass this as the
     * {@code deltaSeconds} of the movement services and as the {@code dt} of any
     * per-tick time integration (projectiles, ghost energy drain) so their
     * real-time rates stay independent of the tick frequency.
     */
    public static final double SECONDS_PER_TICK = 1d / ENEMY_MOVEMENT_TICKS_PER_SECOND;

    /**
     * Maximum movement ticks a single frame / loop iteration may run when catching
     * up after a slow iteration. Bounds the {@link MovementTickAccumulator}
     * catch-up burst so a long pause cannot trigger a spiral of movement. Shared by
     * both frontends so their catch-up behaviour is identical.
     */
    public static final int MAX_TICKS_PER_FRAME = 4;

    private GameplayTickRate() {
        // Constants holder — not instantiable.
    }

    /**
     * Sleep/scheduling interval, in whole milliseconds, that realises
     * {@link #ENEMY_MOVEMENT_TICKS_PER_SECOND} (≈ 33 ms for 30 Hz).
     *
     * @return the per-tick interval in milliseconds (always ≥ 1)
     */
    public static long intervalMillis() {
        return Math.max(1L, Math.round(1000d / ENEMY_MOVEMENT_TICKS_PER_SECOND));
    }
}
