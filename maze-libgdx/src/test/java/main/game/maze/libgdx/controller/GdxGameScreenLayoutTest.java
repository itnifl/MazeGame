package main.game.maze.libgdx.controller;

import main.game.maze.libgdx.adapter.*;
import main.game.maze.libgdx.controller.*;
import main.game.maze.libgdx.helper.*;
import main.game.maze.libgdx.service.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Locks in the libgdx HUD/viewport layout so:
 *  - HP bar is pinned to the top of the window and overlays the maze.
 *  - Bottom command bar is pinned to the bottom of the window and overlays
 *    the maze.
 *  - The gameplay viewport fills the entire window so there is no visible
 *    blank strip between the maze and either bar.
 */
class GdxGameScreenLayoutTest {

    @Test
        void gameStripFillsEntireWindow() {
        int w = 1024;
        int h = 768;

        GdxGameScreenController.GameStripBounds strip = GdxGameScreenController.computeGameStripBounds(w, h);

        assertEquals(0, strip.x(), "strip starts at left edge");
        assertEquals(0, strip.y(), "strip starts at bottom edge");
        assertEquals(w, strip.width(), "strip spans full window width");
        assertEquals(h, strip.height(), "strip spans full window height");
    }

    @Test
    void hpBarRendersAtWindowTop() {
        float h = 600f;

        float hpBarBottom = GdxGameScreenController.hpBarBottomY(h);
        float hpBarTop = hpBarBottom + GdxGameScreenController.hpBarHeight();

        assertTrue(hpBarTop <= h, "HP bar top edge must not extend past window top");
        assertTrue(h - hpBarTop <= 0.001f,
                "HP bar top edge must sit exactly on the window top");
    }

    @Test
    void bottomBarRendersAtWindowBottom() {
        float rowY = GdxGameScreenController.bottomRowY();
        float rowH = GdxGameScreenController.bottomRowHeight();

        assertTrue(rowY >= 0f, "bottom row must not render below the window");
        assertTrue(rowY + rowH <= GdxGameScreenController.bottomBarHeight(),
                "bottom row must fit inside the reserved bottom strip -> bar sticks to bottom");
        assertTrue(rowY <= 0.001f,
                "bottom row anchors at y=0 of the HUD -> bar sticks to bottom");
    }

    @Test
    void smallWindowKeepsBothBarsClampedAndStripNonNegative() {
        // Even with a degenerate window, computeGameStripBounds must remain valid.
        GdxGameScreenController.GameStripBounds strip = GdxGameScreenController.computeGameStripBounds(10, 10);
        assertTrue(strip.height() >= 1, "strip height never collapses below 1");
        assertTrue(strip.width() >= 1, "strip width never collapses below 1");
    }
}
