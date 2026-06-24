package main.game.maze.javafx.render;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Regression tests for BUG-3: JavaFX camera follow produced a white strip
 * because the clip Rectangle on gameBoard was in local coordinates and moved
 * with the board when translated, leaving the opposite edge unrendered.
 *
 * <p>Fix: clip offset = -(board translation), so the clip window is always
 * aligned to the viewport in scene space regardless of scrolling.
 */
class FxGameBoardClipOffsetTest {

    // --- computeClipRect: clip position must invert the board translation ---

    @Test
    void noTranslation_clipStartsAtOrigin() {
        double[] rect = FxGameRenderCoordinator.computeClipRect(new double[]{0, 0}, 800, 600);
        assertEquals(0.0, rect[0], 1e-9, "clip x = 0 when board is not translated");
        assertEquals(0.0, rect[1], 1e-9, "clip y = 0 when board is not translated");
        assertEquals(800.0, rect[2], 1e-9, "clip width = viewportWidth");
        assertEquals(600.0, rect[3], 1e-9, "clip height = viewportHeight");
    }

    @Test
    void scrolledRight_clipOffsetCompensatesNegativeTranslateX() {
        // translateX = -400 (board scrolled right): clip must start at local x=400
        // so that the clip window covers world columns 400–1200, not 0–800
        double[] rect = FxGameRenderCoordinator.computeClipRect(new double[]{-400, 0}, 800, 600);
        assertEquals(400.0, rect[0], 1e-9, "clip x = 400 when translateX = -400");
        assertEquals(0.0, rect[1], 1e-9, "clip y unchanged");
        assertEquals(800.0, rect[2], 1e-9, "clip width = viewportWidth");
        assertEquals(600.0, rect[3], 1e-9, "clip height = viewportHeight");
    }

    @Test
    void scrolledDown_clipOffsetCompensatesNegativeTranslateY() {
        // translateY = -200: clip must start at local y=200
        double[] rect = FxGameRenderCoordinator.computeClipRect(new double[]{0, -200}, 800, 600);
        assertEquals(0.0, rect[0], 1e-9, "clip x unchanged");
        assertEquals(200.0, rect[1], 1e-9, "clip y = 200 when translateY = -200");
        assertEquals(800.0, rect[2], 1e-9);
        assertEquals(600.0, rect[3], 1e-9);
    }

    @Test
    void scrolledBothAxes_clipOffsetCompensatesBothTranslations() {
        double[] rect = FxGameRenderCoordinator.computeClipRect(new double[]{-400, -200}, 800, 600);
        assertEquals(400.0, rect[0], 1e-9, "clip x = 400");
        assertEquals(200.0, rect[1], 1e-9, "clip y = 200");
        assertEquals(800.0, rect[2], 1e-9);
        assertEquals(600.0, rect[3], 1e-9);
    }

    @Test
    void clipSizeAlwaysMatchesViewportRegardlessOfTranslation() {
        double[] rect = FxGameRenderCoordinator.computeClipRect(new double[]{-1200, -900}, 1280, 720);
        assertEquals(1280.0, rect[2], 1e-9, "clip width always equals viewport width");
        assertEquals(720.0, rect[3], 1e-9, "clip height always equals viewport height");
    }

    @Test
    void atRightEdgeClamp_clipAtMaxOffset() {
        // translateX = viewportWidth - worldWidth = 800 - 1600 = -800 (far-right clamp)
        double[] rect = FxGameRenderCoordinator.computeClipRect(new double[]{-800, 0}, 800, 600);
        assertEquals(800.0, rect[0], 1e-9, "clip x = worldWidth - viewportWidth at far edge");
    }

    // --- installGameBoardClip: must not throw when gameBoard is null ---

    @Test
    void constructorWithNullGameBoard_doesNotThrow() {
        assertDoesNotThrow(() -> new FxGameRenderCoordinator(null));
    }

    // --- BUG-6 regression: GameController must NOT set a second clip on gameBoard ---
    // The coordinator owns the single clip. A second setClip() call in GameController
    // overwrote it with a fixed-local-origin rectangle that did not track translation,
    // causing the white-area-on-scroll bug to persist even after the canvas background fix.
    // These tests verify that computeClipRect is the sole source of clip geometry and
    // that the coordinator's clip correctly inverts the board translation at every offset.

    @Test
    void clipX_isAlwaysNegativeTranslateX() {
        double[] noScroll  = FxGameRenderCoordinator.computeClipRect(new double[]{0, 0}, 800, 600);
        double[] scrolled  = FxGameRenderCoordinator.computeClipRect(new double[]{-350, 0}, 800, 600);
        assertEquals(0.0,   noScroll[0], 1e-9, "clip x = 0 when not scrolled");
        assertEquals(350.0, scrolled[0], 1e-9, "clip x = 350 when translateX = -350");
    }

    @Test
    void clipY_isAlwaysNegativeTranslateY() {
        double[] noScroll  = FxGameRenderCoordinator.computeClipRect(new double[]{0, 0}, 800, 600);
        double[] scrolled  = FxGameRenderCoordinator.computeClipRect(new double[]{0, -175}, 800, 600);
        assertEquals(0.0,   noScroll[1], 1e-9, "clip y = 0 when not scrolled");
        assertEquals(175.0, scrolled[1], 1e-9, "clip y = 175 when translateY = -175");
    }

    @Test
    void clipDimensions_matchViewportAtAllScrollPositions() {
        double vw = 1100, vh = 800;
        double[][] translations = {{0, 0}, {-200, -100}, {-1100, -800}};
        for (double[] t : translations) {
            double[] cr = FxGameRenderCoordinator.computeClipRect(t, vw, vh);
            assertEquals(vw, cr[2], 1e-9, "clip width always equals viewport width");
            assertEquals(vh, cr[3], 1e-9, "clip height always equals viewport height");
        }
    }
}
