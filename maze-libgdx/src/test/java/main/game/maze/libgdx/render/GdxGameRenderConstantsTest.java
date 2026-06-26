package main.game.maze.libgdx.render;

import static org.junit.jupiter.api.Assertions.assertEquals;

import main.game.maze.libgdx.controller.GdxGameScreenMetrics;
import main.game.maze.mazeworld.constants.StageConstants;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link GdxGameRenderConstants#defaults()} produces the render
 * tuning block the gameplay screen previously inlined, so the SRP extraction
 * preserves the exact rendering parameters (no visual change).
 */
class GdxGameRenderConstantsTest {

    private static final float DELTA = 0.0001f;

    @Test
    void defaultsMatchTheExpectedRenderTuning() {
        GdxGameRenderCoordinator.RenderConstants c = GdxGameRenderConstants.defaults();

        assertEquals((float) StageConstants.WallThicknessPx, c.wallThickness(), DELTA);
        assertEquals(1f, c.playerAliveScale(), DELTA);
        assertEquals(1.8f, c.playerDeadScale(), DELTA);
        assertEquals(0.5f, c.halfRatio(), DELTA);
        assertEquals(4, c.infectionEdgeLayers());
        assertEquals(3.2f, c.infectionPulseSpeed(), DELTA);
        assertEquals(120f, c.infectionTriangleWidth(), DELTA);
        assertEquals(106f, c.infectionTriangleHeight(), DELTA);
        assertEquals(6, c.infectionGlowLayers());
        assertEquals("Infected!", c.infectionWarningText());
        assertEquals(22f, c.topMargin(), DELTA);
        assertEquals(170f, c.scorePanelWidth(), DELTA);
        assertEquals(54f, c.scorePanelHeight(), DELTA);
    }

    @Test
    void barHeightsAreSourcedFromTheSharedMetrics() {
        GdxGameRenderCoordinator.RenderConstants c = GdxGameRenderConstants.defaults();

        assertEquals(GdxGameScreenMetrics.BOTTOM_BAR_HEIGHT, c.bottomBarHeight(), DELTA);
        assertEquals(GdxGameScreenMetrics.HP_BAR_HEIGHT, c.hpBarHeight(), DELTA);
    }
}
