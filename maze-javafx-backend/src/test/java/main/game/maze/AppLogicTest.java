package main.game.maze;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for pure-logic static helpers in {@link App} that need no FX toolkit.
 */
class AppLogicTest {

    @Test
    void needsFullscreen_boardSmallerThanScreen_returnsFalse() {
        assertFalse(App.needsFullscreenForBoard(800, 600, 1920.0, 1080.0));
    }

    @Test
    void needsFullscreen_boardEqualToScreen_returnsFalse() {
        assertFalse(App.needsFullscreenForBoard(1920, 1080, 1920.0, 1080.0));
    }

    @Test
    void needsFullscreen_boardWiderThanScreen_returnsTrue() {
        assertTrue(App.needsFullscreenForBoard(2000, 600, 1920.0, 1080.0));
    }

    @Test
    void needsFullscreen_boardTallerThanScreen_returnsTrue() {
        assertTrue(App.needsFullscreenForBoard(800, 1200, 1920.0, 1080.0));
    }

    @Test
    void needsFullscreen_bothDimensionsExceed_returnsTrue() {
        assertTrue(App.needsFullscreenForBoard(2000, 1200, 1920.0, 1080.0));
    }

    // --- Static delegation helpers ---

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
