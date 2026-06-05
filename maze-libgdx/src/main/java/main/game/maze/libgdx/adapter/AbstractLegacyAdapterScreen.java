package main.game.maze.libgdx.adapter;

import com.badlogic.gdx.ApplicationAdapter;
import main.game.maze.libgdx.GdxGameContext;
import main.game.maze.libgdx.controller.AbstractGdxScreenController;

/**
 * Shared screen wrapper for legacy ApplicationAdapter-based gameplay.
 */
public abstract class AbstractLegacyAdapterScreen extends AbstractGdxScreenController {

    private final ApplicationAdapterScreen delegate;

    protected AbstractLegacyAdapterScreen(GdxGameContext context, ApplicationAdapter adapter) {
        super(context);
        this.delegate = new ApplicationAdapterScreen(adapter);
    }

    @Override
    public void show() {
        delegate.show();
    }

    @Override
    public void render(float delta) {
        delegate.render(delta);
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
        delegate.hide();
    }

    @Override
    public void dispose() {
        delegate.dispose();
    }
}


