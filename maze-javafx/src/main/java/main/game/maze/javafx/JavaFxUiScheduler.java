package main.game.maze.javafx;

import javafx.application.Platform;
import main.game.maze.common.graphics.IUiScheduler;

/**
 * Production scheduler backed by {@link javafx.application.Platform}.
 * Silently degrades to direct execution if the FX toolkit is not initialised
 * so unit tests that touch this code path do not blow up.
 */
public final class JavaFxUiScheduler implements IUiScheduler {

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
    public void runOnUiThread(Runnable action) {
        if (action == null) return;
        if (isUiThread()) {
            action.run();
            return;
        }
        runLater(action);
    }

    @Override
    public boolean isUiThread() {
        try {
            return Platform.isFxApplicationThread();
        } catch (IllegalStateException toolkitNotReady) {
            return false;
        }
    }
}


