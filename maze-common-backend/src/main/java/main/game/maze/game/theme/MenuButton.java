package main.game.maze.game.theme;

/**
 * The buttons of the main menu and the screens between levels, the same in both frontends: their label and the metal plate they are drawn
 * on (green to go on, amber to spend, red to leave, steel for the rest).
 */
public enum MenuButton {
    SAVE_SCORE("Save Score", MenuTheme.Art.BUTTON_STEEL),
    SHOP("Shop", MenuTheme.Art.BUTTON_BUY),
    NEXT_LEVEL("Next Level", MenuTheme.Art.BUTTON_GO),
    RESTART_LEVEL("Restart Level", MenuTheme.Art.BUTTON_STEEL),
    BACK_TO_MENU("Back to Menu", MenuTheme.Art.BUTTON_DANGER),
    BUY("Buy", MenuTheme.Art.BUTTON_BUY),
    DONE("Done", MenuTheme.Art.BUTTON_STEEL),
    START_MISSION("Start Mission", MenuTheme.Art.BUTTON_GO),
    RESTART_MISSION("Restart Mission", MenuTheme.Art.BUTTON_GO),
    HIGH_SCORES("High Scores", MenuTheme.Art.BUTTON_STEEL),
    LOAD("Load", MenuTheme.Art.BUTTON_STEEL);

    private final String label;
    private final MenuTheme.Art art;

    MenuButton(String label, MenuTheme.Art art) {
        this.label = label;
        this.art = art;
    }

    public String label() {
        return label;
    }

    public MenuTheme.Art art() {
        return art;
    }
}
