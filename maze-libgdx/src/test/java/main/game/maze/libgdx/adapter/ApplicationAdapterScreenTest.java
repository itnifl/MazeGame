package main.game.maze.libgdx.adapter;

import main.game.maze.libgdx.adapter.*;
import main.game.maze.libgdx.controller.*;
import main.game.maze.libgdx.helper.*;
import main.game.maze.libgdx.service.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.badlogic.gdx.ApplicationAdapter;
import org.junit.jupiter.api.Test;

class ApplicationAdapterScreenTest {

    @Test
    void showInitializesDelegateOnlyOnce() {
        RecordingAdapter delegate = new RecordingAdapter();
        ApplicationAdapterScreen screen = new ApplicationAdapterScreen(delegate);

        screen.show();
        screen.show();

        assertEquals(1, delegate.createCalls);
    }

    @Test
    void renderAndResizeDelegateToApplicationAdapter() {
        RecordingAdapter delegate = new RecordingAdapter();
        ApplicationAdapterScreen screen = new ApplicationAdapterScreen(delegate);

        screen.show();
        screen.render(1f / 60f);
        screen.resize(800, 600);

        assertEquals(1, delegate.renderCalls);
        assertEquals(1, delegate.resizeCalls);
    }

    @Test
    void disposeDelegatesToApplicationAdapter() {
        RecordingAdapter delegate = new RecordingAdapter();
        ApplicationAdapterScreen screen = new ApplicationAdapterScreen(delegate);

        screen.dispose();

        assertEquals(1, delegate.disposeCalls);
    }

    private static final class RecordingAdapter extends ApplicationAdapter {

        private int createCalls;
        private int renderCalls;
        private int resizeCalls;
        private int disposeCalls;

        @Override
        public void create() {
            createCalls++;
        }

        @Override
        public void render() {
            renderCalls++;
        }

        @Override
        public void resize(int width, int height) {
            resizeCalls++;
        }

        @Override
        public void dispose() {
            disposeCalls++;
        }
    }
}
