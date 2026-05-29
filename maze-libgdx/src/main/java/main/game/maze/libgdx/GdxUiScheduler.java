package main.game.maze.libgdx;

import com.badlogic.gdx.Gdx;
import main.game.maze.common.graphics.IUiScheduler;

/**
 * libGDX scheduler. Dispatches work onto the GL render thread via
 * {@link com.badlogic.gdx.Application#postRunnable}. Falls back to inline
 * execution if libGDX has not been initialised (e.g. during unit tests that
 * exercise this class without a running {@code Lwjgl3Application}).
 */
public final class GdxUiScheduler implements IUiScheduler {

    private final Thread renderThread;

    /**
     * Creates a scheduler bound to the calling thread as the render thread.
     * Intended to be constructed from inside an {@code ApplicationListener.create()}
     * callback so the recorded thread is the actual GL render thread.
     */
    public GdxUiScheduler() {
        this.renderThread = Thread.currentThread();
    }

    @Override
    public void runLater(Runnable action) {
        if (action == null) return;
        if (Gdx.app != null) {
            Gdx.app.postRunnable(action);
        } else {
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
        return Thread.currentThread() == renderThread;
    }
}
