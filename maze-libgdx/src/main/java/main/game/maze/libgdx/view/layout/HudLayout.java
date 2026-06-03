package main.game.maze.libgdx.view.layout;

/**
 * Immutable HUD layout snapshot for one frame.
 */
public record HudLayout(
        float commandButtonX,
        float commandButtonY,
        float commandButtonW,
        float commandButtonH,
        float terminalButtonX,
        float terminalButtonY,
        float terminalButtonW,
        float terminalButtonH) {

    public static HudLayout zero() {
        return new HudLayout(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);
    }
}
