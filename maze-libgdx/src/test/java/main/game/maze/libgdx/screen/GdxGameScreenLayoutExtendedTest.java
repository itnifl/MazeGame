package main.game.maze.libgdx.screen;

import main.game.maze.libgdx.controller.GdxGameScreenMetrics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Extended tests for {@link GdxGameScreenMetrics} covering additional screen
 * sizes and non-overlap invariants not in the existing GdxGameScreenLayoutTest.
 */
class GdxGameScreenLayoutExtendedTest {

    // Non-negative coordinates for any positive screen dimension.
    @ParameterizedTest
    @CsvSource({"800,600", "1920,1080", "320,240", "2560,1440"})
    void gameStripBounds_nonNegativeCoords_forVariousResolutions(int w, int h) {
        var strip = GdxGameScreenMetrics.computeGameStripBounds(w, h);
        assertTrue(strip.x() >= 0, "x must be non-negative");
        assertTrue(strip.y() >= 0, "y must be non-negative");
        assertTrue(strip.width() > 0, "width must be positive");
        assertTrue(strip.height() > 0, "height must be positive");
    }

    // HP bar height is a positive constant.
    @Test
    void hpBarHeight_isPositive() {
        assertTrue(GdxGameScreenMetrics.hpBarHeight() > 0f, "HP bar height must be positive");
    }

    // Bottom row height is a positive constant.
    @Test
    void bottomRowHeight_isPositive() {
        assertTrue(GdxGameScreenMetrics.bottomRowHeight() > 0f, "Bottom row height must be positive");
    }

    // Bottom bar height is at least as large as the bottom row.
    @Test
    void bottomBarHeight_atLeastAsLargeAsBottomRowHeight() {
        assertTrue(GdxGameScreenMetrics.bottomBarHeight() >= GdxGameScreenMetrics.bottomRowHeight(),
                "Bottom bar must accommodate at least one row");
    }

    // HP bar bottom for various heights is within the window.
    @ParameterizedTest
    @CsvSource({"400", "600", "768", "1080"})
    void hpBarBottomY_isWithinWindowHeight(float h) {
        float hpBarBottom = GdxGameScreenMetrics.hpBarBottomY(h);
        assertTrue(hpBarBottom >= 0f, "hpBarBottom must be non-negative");
        assertTrue(hpBarBottom <= h, "hpBarBottom must not exceed window height");
    }

    // Game strip width equals window width (no horizontal margin).
    @ParameterizedTest
    @CsvSource({"800,600", "1024,768", "1280,720"})
    void gameStrip_widthEqualsWindowWidth(int w, int h) {
        var strip = GdxGameScreenMetrics.computeGameStripBounds(w, h);
        assertEquals(w, strip.width(), "Game strip must fill the full window width");
    }
}
