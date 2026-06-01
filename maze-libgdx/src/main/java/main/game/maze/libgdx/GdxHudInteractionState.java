package main.game.maze.libgdx;

/**
 * Tracks transient HUD interaction state, so the screen class can focus on
 * gameplay flow instead of button timing details.
 */
final class GdxHudInteractionState {
    private float commandButtonPressedSeconds;
    private float terminalButtonPressedSeconds;
    private boolean commandsOverlayVisible;

    void tick(float dt) {
        if (commandButtonPressedSeconds > 0f) {
            commandButtonPressedSeconds = Math.max(0f, commandButtonPressedSeconds - dt);
        }
        if (terminalButtonPressedSeconds > 0f) {
            terminalButtonPressedSeconds = Math.max(0f, terminalButtonPressedSeconds - dt);
        }
    }

    void pressCommandsButton(float pressDurationSeconds) {
        commandButtonPressedSeconds = pressDurationSeconds;
        commandsOverlayVisible = !commandsOverlayVisible;
    }

    void pressTerminalButton(float pressDurationSeconds) {
        terminalButtonPressedSeconds = pressDurationSeconds;
    }

    boolean commandsOverlayVisible() {
        return commandsOverlayVisible;
    }

    void hideCommandsOverlay() {
        commandsOverlayVisible = false;
    }

    float commandButtonPressedSeconds() {
        return commandButtonPressedSeconds;
    }

    float terminalButtonPressedSeconds() {
        return terminalButtonPressedSeconds;
    }

    float commandPressOffsetY() {
        return commandButtonPressedSeconds > 0f ? -2f : 0f;
    }

    float terminalPressOffsetY() {
        return terminalButtonPressedSeconds > 0f ? -2f : 0f;
    }

    void reset() {
        commandButtonPressedSeconds = 0f;
        terminalButtonPressedSeconds = 0f;
        commandsOverlayVisible = false;
    }
}