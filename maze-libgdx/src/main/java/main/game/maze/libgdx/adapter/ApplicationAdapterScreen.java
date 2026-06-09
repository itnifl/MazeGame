package main.game.maze.libgdx.adapter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Screen;

/**
 * Compatibility adapter used while migrating from ApplicationAdapter to Screen.
 */
final class ApplicationAdapterScreen implements Screen {

    private final ApplicationAdapter delegate;
    private boolean initialized;

    ApplicationAdapterScreen(ApplicationAdapter delegate) {
        this.delegate = delegate;
    }

    @Override
    public void show() {
        if (!initialized) {
            delegate.create();
            initialized = true;
        }
    }

    @Override
    public void render(float delta) {
        delegate.render();
    }

    @Override
    public void resize(int width, int height) {
        delegate.resize(width, height);
    }

    @Override
    public void pause() {
        delegate.pause();
    }

    @Override
    public void resume() {
        delegate.resume();
    }

    @Override
    public void hide() {
        // Lifecycle remains owned by the Game that created this adapter.
    }

    @Override
    public void dispose() {
        delegate.dispose();
    }
}
