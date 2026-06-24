package main.game.maze.libgdx.helper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies GdxGameLayoutSupport.computeCameraPosition (F27): the libGDX camera
 * centres on the player when the maze is larger than the viewport, clamped to
 * maze edges. Tests use the pure-math helper directly — no GL context needed.
 */
class GdxCameraFollowTest {

    private static final float D = 0.5f;

    // --- Maze fits or equals the viewport — fixed at maze centre ---

    @Test
    void mazeSmaller_xCentresOnMaze_yUsesViewportHalf() {
        // maze 400×300 inside view 800×600
        // camX: maze fits → mazeW/2 = 200
        // camY: maze fits → halfH = viewH/2 = 300 (bottom-anchor: camera shows full viewport height)
        float[] pos = GdxGameLayoutSupport.computeCameraPosition(800, 600, 400, 300, 200, 150);
        assertEquals(200f, pos[0], D);
        assertEquals(300f, pos[1], D);
    }

    @Test
    void mazeEqualToViewport_cameraAtMazeCentre() {
        float[] pos = GdxGameLayoutSupport.computeCameraPosition(800, 600, 800, 600, 100, 100);
        assertEquals(400f, pos[0], D);
        assertEquals(300f, pos[1], D);
    }

    // --- Maze wider than viewport — player-follow on X, fixed on Y ---

    @Test
    void mazeWider_playerAtLeft_clampedToLeftEdge() {
        // view 800×600, maze 1600×600 → halfW = 400
        // camX = clamp(100, 400, 1200) → 400
        // camY: maze height = view height → camY = halfH = 300
        float[] pos = GdxGameLayoutSupport.computeCameraPosition(800, 600, 1600, 600, 100, 300);
        assertEquals(400f, pos[0], D, "clamped at left edge");
        assertEquals(300f, pos[1], D, "y: maze height fits; fixed at half");
    }

    @Test
    void mazeWider_playerAtCentre_cameraFollowsPlayer() {
        // camX = clamp(800, 400, 1200) → 800
        float[] pos = GdxGameLayoutSupport.computeCameraPosition(800, 600, 1600, 600, 800, 300);
        assertEquals(800f, pos[0], D);
    }

    @Test
    void mazeWider_playerAtRightEdge_clampedToRightEdge() {
        // camX = clamp(1550, 400, 1200) → 1200
        float[] pos = GdxGameLayoutSupport.computeCameraPosition(800, 600, 1600, 600, 1550, 300);
        assertEquals(1200f, pos[0], D, "clamped at right edge");
    }

    // --- Maze taller than viewport — player-follow on Y, fixed on X ---

    @Test
    void mazeTaller_playerAtTop_clampedToTopEdge() {
        // view 800×600, maze 800×1200 → halfH = 300
        // camY = clamp(50, 300, 900) → 300
        float[] pos = GdxGameLayoutSupport.computeCameraPosition(800, 600, 800, 1200, 400, 50);
        assertEquals(400f, pos[0], D, "x: maze width fits; fixed at half");
        assertEquals(300f, pos[1], D, "clamped at top");
    }

    @Test
    void mazeTaller_playerAtCentre_cameraFollowsPlayer() {
        // camY = clamp(600, 300, 900) → 600
        float[] pos = GdxGameLayoutSupport.computeCameraPosition(800, 600, 800, 1200, 400, 600);
        assertEquals(600f, pos[1], D);
    }

    @Test
    void mazeTaller_playerAtBottomEdge_clampedToBottomEdge() {
        // camY = clamp(1150, 300, 900) → 900
        float[] pos = GdxGameLayoutSupport.computeCameraPosition(800, 600, 800, 1200, 400, 1150);
        assertEquals(900f, pos[1], D, "clamped at bottom edge");
    }

    // --- Both axes larger than viewport ---

    @Test
    void mazeLarger_playerAtCentre_cameraFollowsOnBothAxes() {
        // view 800×600, maze 1600×1200, player at (1000, 800)
        // camX = clamp(1000, 400, 1200) → 1000
        // camY = clamp(800, 300, 900)  → 800
        float[] pos = GdxGameLayoutSupport.computeCameraPosition(800, 600, 1600, 1200, 1000, 800);
        assertEquals(1000f, pos[0], D);
        assertEquals(800f,  pos[1], D);
    }

    @Test
    void mazeLarger_playerNearFarCorner_clampsBothEdges() {
        // camX = clamp(1550, 400, 1200) → 1200
        // camY = clamp(1150, 300, 900)  → 900
        float[] pos = GdxGameLayoutSupport.computeCameraPosition(800, 600, 1600, 1200, 1550, 1150);
        assertEquals(1200f, pos[0], D);
        assertEquals(900f,  pos[1], D);
    }

    // --- Null-guard on the public update entry point ---

    @Test
    void updateCameraFollow_nullArguments_doesNotThrow() {
        assertDoesNotThrow(() ->
                GdxGameLayoutSupport.updateCameraFollow(null, null, null, null));
    }
}
