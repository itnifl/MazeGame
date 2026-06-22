package main.game.maze.javafx.render;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link FxGameRenderCoordinator#computeCameraTranslation} (F27).
 * The camera always player-follows when the world is larger than the viewport;
 * fullscreen is no longer a separate mode.
 */
class FxCameraTranslationTest {

    // ---- World fits inside viewport — no translation needed ----

    @Test
    void worldFitsInViewport_returnsZeroTranslation() {
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(800, 600, 200, 200, 100, 100);
        assertArrayEquals(new double[]{0, 0}, t, "Small world — no translation");
    }

    @Test
    void worldExactlyFitsViewport_returnsZeroTranslation() {
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(800, 600, 800, 600, 400, 300);
        assertArrayEquals(new double[]{0, 0}, t);
    }

    // ---- Both axes overflow — player-follow with clamp ----

    @Test
    void bothAxesOverflow_playerAtCentre_followsCentred() {
        // viewport 400×300, world 1200×900, player at centre (600, 450)
        // tx = 200 - 600 = -400, clamped to [400-1200, 0] = [-800, 0] → -400
        // ty = 150 - 450 = -300, clamped to [300-900, 0] = [-600, 0] → -300
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(400, 300, 1200, 900, 600, 450);
        assertEquals(-400.0, t[0], 1e-9, "x: centre-follow player");
        assertEquals(-300.0, t[1], 1e-9, "y: centre-follow player");
    }

    @Test
    void bothAxesOverflow_playerNearOrigin_clampsToOrigin() {
        // viewport 400×300, world 1200×900, player at (10, 10)
        // tx = 200 - 10 = 190 → clamped to 0
        // ty = 150 - 10 = 140 → clamped to 0
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(400, 300, 1200, 900, 10, 10);
        assertEquals(0.0, t[0], 1e-9, "x: clamped at left edge");
        assertEquals(0.0, t[1], 1e-9, "y: clamped at top edge");
    }

    @Test
    void bothAxesOverflow_playerNearFarCorner_clampsToFarEdge() {
        // viewport 400×300, world 1200×900, player at (1190, 890)
        // tx = 200 - 1190 = -990 → max(-990, -800) = -800
        // ty = 150 - 890 = -740 → max(-740, -600) = -600
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(400, 300, 1200, 900, 1190, 890);
        assertEquals(400 - 1200, t[0], 1e-9, "x: clamped at right edge");
        assertEquals(300 - 900, t[1], 1e-9, "y: clamped at bottom edge");
    }

    // ---- Only width overflows — player-follow on X ----

    @Test
    void onlyWidthOverflows_playerAtLeft_clampsToOrigin() {
        // viewport 400×600, world 1200×300, player at (100, 150)
        // tx = 200 - 100 = 100 → clamped to 0
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(400, 600, 1200, 300, 100, 150);
        assertEquals(0.0, t[0], 1e-9, "Clamped at left edge");
        assertEquals(0.0, t[1], 1e-9, "Y unaffected");
    }

    @Test
    void onlyWidthOverflows_playerAtRight_clampsToRightEdge() {
        // viewport 400×600, world 1200×300, player at (1100, 150)
        // tx = 200 - 1100 = -900 → max(-900, -800) = -800
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(400, 600, 1200, 300, 1100, 150);
        assertEquals(400 - 1200, t[0], 1e-9, "Clamped at right edge");
        assertEquals(0.0, t[1], 1e-9);
    }

    @Test
    void onlyWidthOverflows_playerAtCentre_followsCentred() {
        // viewport 800×400, world 1600×300, player at (100, 150)
        // tx = 400 - 100 = 300 → clamped to 0
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(800, 400, 1600, 300, 100, 150);
        assertEquals(0.0, t[0], 1e-9, "Centre follow clamped at left edge");
        assertEquals(0.0, t[1], 1e-9);
    }

    @Test
    void onlyWidthOverflows_playerNearRightEdge_clampsAtRightEdge() {
        // viewport 800×400, world 1600×300, player at (1500, 150)
        // tx = 400 - 1500 = -1100 → max(-1100, -800) = -800
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(800, 400, 1600, 300, 1500, 150);
        assertEquals(-800.0, t[0], 1e-9, "Clamped at right edge");
    }

    // ---- Only height overflows — player-follow on Y ----

    @Test
    void onlyHeightOverflows_playerAtTop_clampsToOrigin() {
        // viewport 800×300, world 400×900, player at (200, 100)
        // ty = 150 - 100 = 50 → clamped to 0
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(800, 300, 400, 900, 200, 100);
        assertEquals(0.0, t[0], 1e-9);
        assertEquals(0.0, t[1], 1e-9, "Clamped at top");
    }

    @Test
    void onlyHeightOverflows_playerAtBottom_clampsToBottomEdge() {
        // viewport 400×600, world 300×1200, player at (100, 1100)
        // ty = 300 - 1100 = -800 → max(-800, -600) = -600
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(400, 600, 300, 1200, 100, 1100);
        assertEquals(-600.0, t[1], 1e-9, "Clamped at bottom edge");
    }

    // ---- Null-guard paths (no toolkit needed since guards short-circuit) ----

    @Test
    void constructorWithNullPane_doesNotThrow() {
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
