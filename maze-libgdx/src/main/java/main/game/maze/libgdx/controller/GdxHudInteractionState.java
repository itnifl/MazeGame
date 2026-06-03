package main.game.maze.libgdx.controller;

/**
 * Tracks transient HUD interaction state, so the screen class can focus on
 * gameplay flow instead of button timing details.
 */
public final class GdxHudInteractionState {
    private float commandButtonPressedSeconds;
    private float terminalButtonPressedSeconds;
    private boolean commandsOverlayVisible;

    public void tick(float dt) {
        if (commandButtonPressedSeconds > 0f) {
            commandButtonPressedSeconds = Math.max(0f, commandButtonPressedSeconds - dt);
        }
        if (terminalButtonPressedSeconds > 0f) {
            terminalButtonPressedSeconds = Math.max(0f, terminalButtonPressedSeconds - dt);
        }
    }

    public void pressCommandsButton(float pressDurationSeconds) {
        commandButtonPressedSeconds = pressDurationSeconds;
        commandsOverlayVisible = !commandsOverlayVisible;
    }

    public void pressTerminalButton(float pressDurationSeconds) {
        terminalButtonPressedSeconds = pressDurationSeconds;
    }

    public boolean commandsOverlayVisible() {
        return commandsOverlayVisible;
    }

    public void hideCommandsOverlay() {
        commandsOverlayVisible = false;
    }

    public float commandButtonPressedSeconds() {
        return commandButtonPressedSeconds;
    }

    public float terminalButtonPressedSeconds() {
        return terminalButtonPressedSeconds;
    }

    public float commandPressOffsetY() {
        return commandButtonPressedSeconds > 0f ? -2f : 0f;
    }

    public float terminalPressOffsetY() {
        return terminalButtonPressedSeconds > 0f ? -2f : 0f;
    }

    public void reset() {
        commandButtonPressedSeconds = 0f;
        terminalButtonPressedSeconds = 0f;
        commandsOverlayVisible = false;
    }
}