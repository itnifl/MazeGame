package main.game.maze.libgdx;

import main.game.maze.libgdx.adapter.*;
import main.game.maze.libgdx.controller.*;
import main.game.maze.libgdx.helper.*;
import main.game.maze.libgdx.service.*;
import static org.junit.jupiter.api.Assertions.assertSame;

import main.game.maze.common.graphics.config.MazeRuntimeConfig;
import org.junit.jupiter.api.Test;

class AbstractGdxScreenTest {

    @Test
    void contextIsAvailableToSubclasses() {
        GdxGameContext context = new GdxGameContext(MazeRuntimeConfig.DEFAULT, new GdxAssetService());
        RecordingScreen screen = new RecordingScreen(context);

        assertSame(context, screen.exposedContext());
    }

    @Test
    void defaultLifecycleMethodsAreNoOp() {
        GdxGameContext context = new GdxGameContext(MazeRuntimeConfig.DEFAULT, new GdxAssetService());
        RecordingScreen screen = new RecordingScreen(context);

        screen.show();
        screen.resize(1, 1);
        screen.pause();
        screen.resume();
        screen.hide();
        screen.dispose();
    }

    private static final class RecordingScreen extends AbstractGdxScreenController {

        RecordingScreen(GdxGameContext context) {
            super(context);
        }

        @Override
        public void render(float delta) {
            // no-op for test
        }

        GdxGameContext exposedContext() {
            return context();
        }
    }
}
