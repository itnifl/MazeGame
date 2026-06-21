package main.game.maze.libgdx.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GdxGameOverOverlayControllerTest {

    @Test
    void containsPointReturnsTrueInsideAndOnEdges() {
        assertTrue(GdxGameOverOverlayController.containsPoint(10f, 10f, 10f, 10f, 100f, 40f));
        assertTrue(GdxGameOverOverlayController.containsPoint(110f, 50f, 10f, 10f, 100f, 40f));
        assertTrue(GdxGameOverOverlayController.containsPoint(60f, 30f, 10f, 10f, 100f, 40f));
    }

    @Test
    void containsPointReturnsFalseOutside() {
        assertFalse(GdxGameOverOverlayController.containsPoint(9f, 10f, 10f, 10f, 100f, 40f));
        assertFalse(GdxGameOverOverlayController.containsPoint(111f, 50f, 10f, 10f, 100f, 40f));
        assertFalse(GdxGameOverOverlayController.containsPoint(10f, 51f, 10f, 10f, 100f, 40f));
    }

    @Test
    void toHudYConvertsFromScreenCoordinates() {
        assertEquals(500f, GdxGameOverOverlayController.toHudY(600f, 100));
        assertEquals(0f, GdxGameOverOverlayController.toHudY(600f, 600));
    }
}
