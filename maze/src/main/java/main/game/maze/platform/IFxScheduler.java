package main.game.maze.platform;

/**
 * Abstraction over JavaFX UI thread scheduling so business code does not
 * depend on {@code javafx.application.Platform} directly. Tests can install
 * a synchronous implementation via {@link FxScheduler#set(IFxScheduler)}.
 */
public interface IFxScheduler {
    /** Runs the action on the UI thread, asynchronously if needed. */
    void runLater(Runnable action);

    /** Runs immediately if already on the UI thread, otherwise via {@link #runLater}. */
    void runOnFxThread(Runnable action);

    /** True when called from the UI thread. */
    boolean isFxApplicationThread();
}
