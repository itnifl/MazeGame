package main.game.maze.common.graphics;

/**
 * Abstraction over the host UI thread (JavaFX Application thread, libGDX
 * render thread, Swing EDT, etc.). Game code that mutates view state schedules
 * the work through this interface instead of calling the backend directly.
 *
 * <p>Tests install a {@link SynchronousUiScheduler}; production backends
 * install their own implementation at startup
 * (e.g. {@code JavaFxBackend.install()} or {@code GdxBackend.install()}).
 */
public interface IUiScheduler {
    /** Runs the action on the UI thread, asynchronously if needed. */
    void runLater(Runnable action);

    /** Runs immediately if already on the UI thread, otherwise via {@link #runLater}. */
    void runOnUiThread(Runnable action);

    /** True when called from the UI thread. */
    boolean isUiThread();
}


