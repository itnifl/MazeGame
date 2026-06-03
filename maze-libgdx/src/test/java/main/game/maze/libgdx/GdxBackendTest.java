package main.game.maze.libgdx;

import main.game.maze.common.graphics.IAudioEngine;
import main.game.maze.common.graphics.IUiScheduler;
import main.game.maze.libgdx.backend.GdxAudioEngine;
import main.game.maze.libgdx.backend.GdxUiScheduler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Sanity tests for the libGDX backend implementations. These exercise the
 * graceful-degradation paths when libGDX is not initialised (no
 * {@code Gdx.app}, {@code Gdx.audio} or {@code Gdx.files}). Full integration
 * against a running {@code Lwjgl3Application} requires a display and is out
 * of scope for unit tests.
 */
class GdxBackendTest {

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
    @DisplayName("GdxAudioEngine silently no-ops when Gdx.audio is null")
    void audioEngineNoopsWithoutGdx() {
        IAudioEngine engine = new GdxAudioEngine();
        assertDoesNotThrow(() -> engine.play("/sound/missing.wav"));
        assertDoesNotThrow(() -> engine.playRateLimited("/sound/missing.wav", "id", 100L));
        assertDoesNotThrow(engine::dispose);
    }
}
