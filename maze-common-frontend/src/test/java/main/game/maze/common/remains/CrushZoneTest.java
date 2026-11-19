package main.game.maze.common.remains;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CrushZone")
class CrushZoneTest {

    @Test
    @DisplayName("an enemy touching the collapsing wall (plus margin) is crushed; one further away is not")
    void crushesAlongsideTheWall() {
        // horizontal wall from (0,100) to (200,100); enemy radius 15
        double reach = 15 + CrushZone.MARGIN;
        assertTrue(CrushZone.crushes(0, 100, 200, 100, 50, 100 + reach - 0.5, 15));
        assertTrue(CrushZone.crushes(0, 100, 200, 100, 120, 100 - 10, 15));
        assertFalse(CrushZone.crushes(0, 100, 200, 100, 50, 100 + reach + 0.5, 15));
    }

    @Test
    @DisplayName("past the ends of the wall only the end cap counts")
    void endCaps() {
        assertTrue(CrushZone.crushes(0, 0, 100, 0, 110, 0, 5));
        assertFalse(CrushZone.crushes(0, 0, 100, 0, 140, 0, 5));
        // degenerate segment behaves like a point
        assertTrue(CrushZone.crushes(10, 10, 10, 10, 15, 10, 5));
    }
}
