package main.game.maze;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for pure-logic static helpers in {@link App} that need no FX toolkit.
 */
class AppLogicTest {

    @Test
    void displayName_withNullDifficulty_returnsEmptyString() {
        String name = App.displayName(null);
        assertNotNull(name, "displayName(null) must not return null");
        assertEquals("", name, "displayName(null) must return empty string");
    }

    @Test
    void getBoardMaxX_withNullDifficulty_returnsPositiveDefault() {
        App.lastChosenDifficulty = null;
        int w = App.getBoardMaxX();
        assertTrue(w > 0, "getBoardMaxX with null difficulty must return a positive default");
    }

    @Test
    void getBoardMaxY_withNullDifficulty_returnsPositiveDefault() {
        App.lastChosenDifficulty = null;
        int h = App.getBoardMaxY();
        assertTrue(h > 0, "getBoardMaxY with null difficulty must return a positive default");
    }
}
