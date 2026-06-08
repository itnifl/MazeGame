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
        float highScoresButtonH) {

    public static MenuLayout zero() {
        return new MenuLayout(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);
    }
}
