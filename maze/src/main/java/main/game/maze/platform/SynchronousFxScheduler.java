package main.game.maze.platform;

/**
 * Test scheduler that runs every action immediately on the caller thread.
 * Intended to be installed via {@code FxScheduler.set(new SynchronousFxScheduler())}
 * in unit tests that exercise code paths previously wrapped in
 * {@code Platform.runLater}.
 */
public final class SynchronousFxScheduler implements IFxScheduler {

    @Override
    public void runLater(Runnable action) {
        if (action != null) action.run();
    }

    @Override
    public void runOnFxThread(Runnable action) {
        if (action != null) action.run();
    }

    @Override
    public boolean isFxApplicationThread() {
        return true;
    }
}
