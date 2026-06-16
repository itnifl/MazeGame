package main.game.maze.libgdx.asset;

import main.game.maze.libgdx.service.GdxAssetService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Extended tests for {@link GdxAssetService} covering public API edge cases
 * not addressed in the existing {@code GdxAssetServiceTest}.
 */
class GdxAssetServiceExtendedTest {

    // getTexture with null path must not throw.
    @Test
    void getTexture_nullPath_doesNotThrow() {
        GdxAssetService service = new GdxAssetService();
        assertDoesNotThrow(() -> service.getTexture(null));
    }

    // queueTexture called multiple times with the same path must not throw.
    @Test
    void queueTexture_duplicatePath_doesNotThrow() {
        GdxAssetService service = new GdxAssetService();
        assertDoesNotThrow(() -> {
            service.queueTexture("/main/game/maze/img.png");
            service.queueTexture("/main/game/maze/img.png");
        });
    }

    // queueTexture with null path must not throw.
    @Test
    void queueTexture_nullPath_doesNotThrow() {
        GdxAssetService service = new GdxAssetService();
        assertDoesNotThrow(() -> service.queueTexture(null));
    }

    // dispose can be safely called multiple times (idempotent).
    @Test
    void dispose_calledMultipleTimes_doesNotThrow() {
        GdxAssetService service = new GdxAssetService();
        assertDoesNotThrow(() -> {
            service.dispose();
            service.dispose();
        });
    }

    // loadingProgress returns a value in [0, 1] after queuing.
    @Test
    void loadingProgress_afterQueuing_remainsInValidRange() {
        GdxAssetService service = new GdxAssetService();
        service.queueTexture("/main/game/maze/test.png");
        float progress = service.loadingProgress();
        assertTrue(progress >= 0f && progress <= 1f,
                "loadingProgress must be in [0, 1] but was " + progress);
    }

    // getTexture returns null when Gdx files are not available (headless).
    @Test
    void getTexture_headless_returnsNull() {
        GdxAssetService service = new GdxAssetService();
        assertNull(service.getTexture("/main/game/maze/player.png"),
                "getTexture must return null in headless test context");
    }

    // updateLoading returns true and does not throw when no assets are queued.
    @Test
    void updateLoading_noQueuedAssets_returnsTrue() {
        GdxAssetService service = new GdxAssetService();
        assertTrue(service.updateLoading());
    }
}
