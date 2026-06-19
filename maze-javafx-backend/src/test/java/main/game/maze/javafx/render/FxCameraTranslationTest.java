package main.game.maze.javafx.render;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link FxGameRenderCoordinator#computeCameraTranslation} and its null-guard methods.
 * {@code computeCameraTranslation} is a pure-math static method that needs no JavaFX toolkit.
 */
class FxCameraTranslationTest {

    // ---- computeCameraTranslation cases ----

    @Test
    void worldFitsInViewport_returnsZeroTranslation() {
        // World 200x200, viewport 800x600 — world is smaller on both axes
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(800, 600, 200, 200, 100, 100, false);
        assertArrayEquals(new double[]{0, 0}, t, "Small world — no translation");
    }

    @Test
    void worldFitsInViewportFullscreen_returnsZeroTranslation() {
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(800, 600, 200, 200, 100, 100, true);
        assertArrayEquals(new double[]{0, 0}, t);
    }

    @Test
    void bothAxesOverflow_windowed_returnsZeroTranslation() {
        // World larger than viewport on both axes, windowed — stay at origin
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(400, 300, 1200, 900, 600, 450, false);
        assertArrayEquals(new double[]{0, 0}, t, "Both-overflow windowed must anchor at origin");
    }

    @Test
    void onlyWidthOverflows_windowed_anchorsToRightEdge() {
        // World wider but shorter than viewport — anchor width to right, no Y translation
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(400, 600, 1200, 300, 600, 150, false);
        // worldWider=true, worldTaller=false → translateX = viewportW - worldW = 400-1200 = -800
        assertArrayEquals(new double[]{-800, 0}, t, 1e-9);
    }

    @Test
    void onlyHeightOverflows_windowed_anchorsToBottomEdge() {
        // World taller but narrower than viewport — anchor height to bottom, no X translation
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(800, 300, 400, 900, 200, 450, false);
        // worldWider=false, worldTaller=true → translateY = viewportH - worldH = 300-900 = -600
        assertArrayEquals(new double[]{0, -600}, t, 1e-9);
    }

    @Test
    void widthOverflows_fullscreen_centreFollowsClamped() {
        // viewport 800x400, world 1600x300, player at (100, 150)
        // worldWider=true, worldTaller=false
        // translateX = 800/2 - 100 = 300 → clamped to 0 (min(300,0)=0)
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(800, 400, 1600, 300, 100, 150, true);
        assertEquals(0.0, t[0], 1e-9, "Centre follow clamped at left edge");
        assertEquals(0.0, t[1], 1e-9);
    }

    @Test
    void widthOverflows_fullscreen_clampedToRightEdge() {
        // viewport 800x400, world 1600x300, player at (1500, 150) — close to right edge
        // translateX = 800/2 - 1500 = -1100 → max(-1100, 800-1600) = max(-1100,-800) = -800
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(800, 400, 1600, 300, 1500, 150, true);
        assertEquals(-800.0, t[0], 1e-9, "Clamped at right edge");
    }

    @Test
    void heightOverflows_fullscreen_centreFollowsClamped() {
        // viewport 400x600, world 300x1200, player at (100, 100)
        // worldWider=false, worldTaller=true
        // translateY = 600/2 - 100 = 200 → clamped to 0
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(400, 600, 300, 1200, 100, 100, true);
        assertEquals(0.0, t[0], 1e-9);
        assertEquals(0.0, t[1], 1e-9, "Clamped at top");
    }

    @Test
    void heightOverflows_fullscreen_clampedToBottomEdge() {
        // viewport 400x600, world 300x1200, player at (100, 1100) — near bottom
        // translateY = 600/2 - 1100 = -800 → max(-800, 600-1200) = max(-800,-600) = -600
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(400, 600, 300, 1200, 100, 1100, true);
        assertEquals(-600.0, t[1], 1e-9, "Clamped at bottom edge");
    }

    // ---- Null-guard paths (no toolkit needed since guards short-circuit) ----

    @Test
    void constructorWithNullPane_doesNotThrow() {
        // FxGameRenderCoordinator.installGameBoardClip() guards against null gameBoard
        assertDoesNotThrow(() -> new FxGameRenderCoordinator(null));
    }

    @Test
    void clearSpanningTree_withNullCanvas_doesNotThrow() {
        FxGameRenderCoordinator coord = new FxGameRenderCoordinator(null);
        assertDoesNotThrow(() -> coord.clearSpanningTree(null));
    }

    @Test
    void showSpanningTree_withNullArguments_doesNotThrow() {
        FxGameRenderCoordinator coord = new FxGameRenderCoordinator(null);
        assertDoesNotThrow(() -> coord.showSpanningTree(null, null, null));
    }

    @Test
    void updateCameraFollow_withNullPlayer_doesNotThrow() {
        FxGameRenderCoordinator coord = new FxGameRenderCoordinator(null);
        assertDoesNotThrow(() -> coord.updateCameraFollow(null));
    }
}
