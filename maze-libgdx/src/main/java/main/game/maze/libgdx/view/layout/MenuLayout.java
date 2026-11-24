package main.game.maze.libgdx.view.layout;

/**
 * Immutable start menu layout snapshot for one frame.
 */
public record MenuLayout(
        float comboX,
        float comboY,
        float comboW,
        float comboH,
        float buttonX,
        float buttonY,
        float buttonW,
        float buttonH,
        float highScoresButtonX,
        float highScoresButtonY,
        float highScoresButtonW,
        float highScoresButtonH,
        float loadButtonX,
        float loadButtonY,
        float loadButtonW,
        float loadButtonH,
        float helpButtonX,
        float helpButtonY,
        float helpButtonW,
        float helpButtonH) {

    public static MenuLayout zero() {
        return new MenuLayout(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);
    }

    /** Whether (x, y) is on the Help & Controls button. */
    public boolean onHelp(float x, float y) {
        return x >= helpButtonX && x <= helpButtonX + helpButtonW && y >= helpButtonY && y <= helpButtonY + helpButtonH;
    }
}
