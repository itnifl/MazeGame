package main.game.maze.common.graphics;

/**
 * Global accessor for the active {@link IUiScheduler}. Defaults to a
 * {@link SynchronousUiScheduler} so the holder remains usable in unit tests
 * and headless tools that never install a real backend. Production code is
 * expected to call {@code install()} on whichever backend module it uses
 * (e.g. {@code JavaFxBackend} or {@code GdxBackend}) during startup.
 */
public final class UiScheduler {
    private static volatile IUiScheduler instance = new SynchronousUiScheduler();

    private UiScheduler() {}

    public static IUiScheduler get() {
        return instance;
    }

    /** Replace the active scheduler. Intended for backend bootstrap and tests. */
    public static void set(IUiScheduler scheduler) {
        if (scheduler == null) {
            throw new IllegalArgumentException("scheduler must not be null");
        }
        instance = scheduler;
    }

    /** Restore the safe synchronous default. */
    public static void reset() {
        instance = new SynchronousUiScheduler();
    }
}


