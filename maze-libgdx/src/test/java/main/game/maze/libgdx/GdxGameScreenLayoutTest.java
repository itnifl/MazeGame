package main.game.maze.libgdx;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Locks in the libgdx HUD/viewport layout so:
 *  - HP bar is pinned to the top of the window and the top wall of the maze
 *    (the top edge of the gameplay viewport strip) is flush with the HP bar.
 *  - Bottom command bar is pinned to the bottom of the window and the bottom
 *    wall of the maze (the bottom edge of the gameplay viewport strip) is
 *    flush with the bottom bar.
 */
class GdxGameScreenLayoutTest {

    @Test
    void gameStripSitsBetweenBottomBarAndHpBar() {
        int w = 1024;
        int h = 768;

        GdxGameScreen.GameStripBounds strip = GdxGameScreen.computeGameStripBounds(w, h);

        assertEquals(0, strip.x(), "strip starts at left edge");
        assertEquals((int) GdxGameScreen.bottomBarHeight(), strip.y(),
                "strip bottom edge is exactly the bottom-bar top edge -> bottom wall flush with bottom bar");
        assertEquals(w, strip.width(), "strip spans full window width");
        assertEquals(h - (int) GdxGameScreen.bottomBarHeight() - (int) GdxGameScreen.hpBarHeight(), strip.height(),
                "strip height leaves room for both bars");

        int stripTop = strip.y() + strip.height();
        assertEquals(h - (int) GdxGameScreen.hpBarHeight(), stripTop,
                "strip top edge is exactly the HP-bar bottom edge -> top wall flush with HP bar");
    }

    @Test
    void hpBarRendersAtWindowTop() {
        float h = 600f;

        float hpBarBottom = GdxGameScreen.hpBarBottomY(h);
        float hpBarTop = hpBarBottom + GdxGameScreen.hpBarHeight();

        assertTrue(hpBarTop <= h, "HP bar top edge must not extend past window top");
        assertTrue(h - hpBarTop <= 1f + 0.001f,
                "HP bar top edge must sit within 1px of the window top -> bar sticks to top");
    }

    @Test
    void bottomBarRendersAtWindowBottom() {
        float rowY = GdxGameScreen.bottomRowY();
        float rowH = GdxGameScreen.bottomRowHeight();

        assertTrue(rowY >= 0f, "bottom row must not render below the window");
        assertTrue(rowY + rowH <= GdxGameScreen.bottomBarHeight(),
                "bottom row must fit inside the reserved bottom strip -> bar sticks to bottom");
        assertTrue(rowY <= 3f + 0.001f,
                "bottom row anchors near y=0 of the HUD -> bar sticks to bottom");
    }

    @Test
    void smallWindowKeepsBothBarsClampedAndStripNonNegative() {
        // Even with a degenerate window, computeGameStripBounds must remain valid.
        GdxGameScreen.GameStripBounds strip = GdxGameScreen.computeGameStripBounds(10, 10);
        assertTrue(strip.height() >= 1, "strip height never collapses below 1");
        assertTrue(strip.width() >= 1, "strip width never collapses below 1");
    }
}
