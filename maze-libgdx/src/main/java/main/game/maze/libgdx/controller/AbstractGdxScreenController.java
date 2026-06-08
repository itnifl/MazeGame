package main.game.maze.libgdx.controller;

import com.badlogic.gdx.Screen;
import main.game.maze.libgdx.GdxGameContext;

/**
 * Common base for libGDX screens in this module.
 */
public abstract class AbstractGdxScreenController implements Screen {

    private final GdxGameContext context;

    protected AbstractGdxScreenController(GdxGameContext context) {
        this.context = context;
    }

    protected final GdxGameContext context() {
        return context;
    }

    @Override
    public void show() {
        // optional
    }

    @Override
    public void resize(int width, int height) {
        // optional
    }

    @Override
    public void pause() {
        // optional
    }

    @Override
    public void resume() {
        // optional
    }

    @Override
    public void hide() {
        // optional
    }

    @Override
    public void dispose() {
        // optional
    }
}
