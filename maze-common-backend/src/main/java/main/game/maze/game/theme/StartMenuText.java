package main.game.maze.game.theme;

/** What the main menu says, the same in both frontends (its look is the {@link MenuTheme}). */
public final class StartMenuText {

    public static final String TITLE = "MAZE GAME";
    public static final String SUBTITLE = "Retro Challenge Mode";
    public static final String SELECT_DIFFICULTY = "SELECT DIFFICULTY";
    public static final String TAGLINE = "Collect the heart, avoid enemies, and maximize your score";

    private StartMenuText() {
    }

    /** The keys of the game; Esc goes back to the game when the menu was opened from it. */
    public static String controls(boolean fromGame) {
        return "Arrow keys to move, P path hint, O spanning tree, H high score, ESC "
                + (fromGame ? "return to game" : "restart menu");
    }
}
