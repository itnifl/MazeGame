package main.game.maze.platform;

/**
 * Global accessor for the active {@link IFxScheduler}. Defaults to a JavaFX
 * implementation; tests may swap in a synchronous scheduler via {@link #set}.
 */
public final class FxScheduler {
    private static volatile IFxScheduler instance = new JavaFxScheduler();

    private FxScheduler() {}

    public static IFxScheduler get() {
        return instance;
    }

    /** Replace the active scheduler. Intended for tests and bootstrap. */
    public static void set(IFxScheduler scheduler) {
        if (scheduler == null) {
            throw new IllegalArgumentException("scheduler must not be null");
        }
        instance = scheduler;
    }

    /** Restore the default JavaFX-backed scheduler. */
    public static void reset() {
        instance = new JavaFxScheduler();
    }
}
