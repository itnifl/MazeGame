package main.game.maze.common.graphics;

/**
 * Scheduler that runs every action immediately on the caller thread.
 * Used as the safe default in {@link UiScheduler} and as an explicit test
 * double in unit tests that want fully synchronous behaviour.
 */
public final class SynchronousUiScheduler implements IUiScheduler {

    @Override
    public void runLater(Runnable action) {
        if (action != null) action.run();
    }

    @Override
    public void runOnUiThread(Runnable action) {
        if (action != null) action.run();
    }

    @Override
    public boolean isUiThread() {
        return true;
    }
}
