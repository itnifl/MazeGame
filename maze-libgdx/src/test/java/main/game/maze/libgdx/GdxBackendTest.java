package main.game.maze.libgdx;

import main.game.maze.libgdx.adapter.*;
import main.game.maze.libgdx.controller.*;
import main.game.maze.libgdx.helper.*;
import main.game.maze.libgdx.service.*;
import main.game.maze.common.graphics.IAudioEngine;
import main.game.maze.common.graphics.IUiScheduler;
import main.game.maze.libgdx.backend.GdxAudioEngine;
import main.game.maze.libgdx.backend.GdxUiScheduler;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Sanity tests for the libGDX backend implementations. These exercise the
 * graceful-degradation paths when libGDX is not initialised (no
 * {@code Gdx.app}, {@code Gdx.audio} or {@code Gdx.files}). Full integration
 * against a running {@code Lwjgl3Application} requires a display and is out
 * of scope for unit tests.
 */
class GdxBackendTest {

    @AfterEach
    void resetGdxApp() {
        Gdx.app = null;
    }

    @Test
    @DisplayName("GdxUiScheduler falls back to inline execution when Gdx.app is null")
    void schedulerFallsBackInline() {
        IUiScheduler scheduler = new GdxUiScheduler();
        int[] counter = new int[1];

        assertDoesNotThrow(() -> scheduler.runLater(() -> counter[0]++));
        assertDoesNotThrow(() -> scheduler.runOnUiThread(() -> counter[0]++));
        assertDoesNotThrow(() -> scheduler.isUiThread());

        assertEquals(2, counter[0], "Actions should run inline when Gdx.app is unavailable");
    }

    @Test
    @DisplayName("GdxUiScheduler tolerates null actions")
    void schedulerToleratesNull() {
        IUiScheduler scheduler = new GdxUiScheduler();
        assertDoesNotThrow(() -> scheduler.runLater(null));
        assertDoesNotThrow(() -> scheduler.runOnUiThread(null));
    }

    @Test
    @DisplayName("GdxUiScheduler.runOnUiThread runs inline on the captured render thread without posting")
    void schedulerRunsInlineOnRenderThread() {
        Application application = mock(Application.class);
        Gdx.app = application;
        IUiScheduler scheduler = new GdxUiScheduler();
        AtomicBoolean ran = new AtomicBoolean();

        scheduler.runOnUiThread(() -> ran.set(true));

        assertTrue(scheduler.isUiThread());
        assertTrue(ran.get(), "Render-thread callers must run the action immediately");
        verify(application, never()).postRunnable(any());
    }

    @Test
    @DisplayName("GdxUiScheduler.runOnUiThread posts from a worker thread instead of running inline")
    void schedulerPostsFromWorkerThread() throws InterruptedException {
        Application application = mock(Application.class);
        Gdx.app = application;
        IUiScheduler scheduler = new GdxUiScheduler();
        AtomicBoolean ran = new AtomicBoolean();
        Runnable action = () -> ran.set(true);
        AtomicReference<Boolean> workerIsUiThread = new AtomicReference<>();

        Thread worker = new Thread(() -> {
            workerIsUiThread.set(scheduler.isUiThread());
            scheduler.runOnUiThread(action);
        }, "scheduler-worker");
        worker.start();
        worker.join();

        assertEquals(Boolean.FALSE, workerIsUiThread.get());
        assertFalse(ran.get(), "Worker-thread callers must not run the action inline");
        verify(application).postRunnable(action);
    }

    @Test
    @DisplayName("GdxUiScheduler.runLater always posts, even from the render thread")
    void schedulerRunLaterAlwaysPosts() {
        Application application = mock(Application.class);
        Gdx.app = application;
        IUiScheduler scheduler = new GdxUiScheduler();
        AtomicBoolean ran = new AtomicBoolean();
        Runnable action = () -> ran.set(true);

        scheduler.runLater(action);

        assertFalse(ran.get());
        verify(application).postRunnable(action);
    }

    @Test
    @DisplayName("GdxAudioEngine silently no-ops when Gdx.audio is null")
    void audioEngineNoopsWithoutGdx() {
        IAudioEngine engine = new GdxAudioEngine();
        assertDoesNotThrow(() -> engine.play("/sound/missing.wav"));
        assertDoesNotThrow(() -> engine.playRateLimited("/sound/missing.wav", "id", 100L));
        assertDoesNotThrow(engine::dispose);
    }
}
