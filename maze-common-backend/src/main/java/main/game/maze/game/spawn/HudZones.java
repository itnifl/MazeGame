package main.game.maze.game.spawn;

import java.util.List;
import main.game.maze.game.items.Weapon;
import main.game.maze.game.items.WeaponHud;

/**
 * The parts of the game window the HUD covers when a level starts and the view is at the top left of the board,
 * as rectangles in board coordinates (y-down). Both frontends draw the same HUD, so a player who starts under it
 * would not be seen. Shared so that both place the player outside it.
 */
public final class HudZones {

    /** A rectangle: left, top, width, height. */
    public record Rect(double x, double y, double width, double height) {

        /** Whether this rectangle, grown by {@code margin} on every side, contains the point. */
        public boolean containsWithMargin(double px, double py, double margin) {
            return px >= x - margin && px <= x + width + margin && py >= y - margin && py <= y + height + margin;
        }
    }

    /** The bar with the health at the top of the window. */
    public static final double TOP_BAR_HEIGHT = 22.0;
    /** The panels start under the top bar. */
    public static final double PANEL_TOP = 22.0;
    public static final double PANEL_LEFT_MARGIN = 6.0;
    public static final double SCORE_PANEL_WIDTH = 170.0;
    public static final double SCORE_PANEL_HEIGHT = 54.0;
    /** The command row at the bottom of the window, with the labels above it. */
    public static final double BOTTOM_ROW_HEIGHT = 50.0;

    private HudZones() {
    }

    /** What the HUD covers on a board of this size. */
    public static List<Rect> covered(double boardWidth, double boardHeight) {
        return List.of(
                new Rect(0, 0, boardWidth, TOP_BAR_HEIGHT),
                new Rect(PANEL_LEFT_MARGIN, PANEL_TOP, WeaponHud.panelWidth(Weapon.values().length), WeaponHud.panelHeight()),
                new Rect(boardWidth - 6.0 - SCORE_PANEL_WIDTH, PANEL_TOP, SCORE_PANEL_WIDTH, SCORE_PANEL_HEIGHT),
                new Rect(0, boardHeight - BOTTOM_ROW_HEIGHT, boardWidth, BOTTOM_ROW_HEIGHT));
    }
}
