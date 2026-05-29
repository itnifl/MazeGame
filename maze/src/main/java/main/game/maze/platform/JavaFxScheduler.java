package main.game.maze.platform;

import javafx.application.Platform;

/**
 * Production scheduler backed by {@link javafx.application.Platform}.
 * Silently degrades to direct execution if the FX toolkit is not initialised
 * so unit tests that touch this code path do not blow up.
 */
public final class JavaFxScheduler implements IFxScheduler {

    @Override
    public void runLater(Runnable action) {
        if (action == null) return;
        try {
            Platform.runLater(action);
        } catch (IllegalStateException toolkitNotReady) {
            action.run();
        }
    }

    @Override
    public void runOnFxThread(Runnable action) {
        if (action == null) return;
        if (isFxApplicationThread()) {
            action.run();
            return;
        }
        runLater(action);
    }

    @Override
    public boolean isFxApplicationThread() {
        try {
            return Platform.isFxApplicationThread();
        } catch (IllegalStateException toolkitNotReady) {
            return false;
        }
    }
}
