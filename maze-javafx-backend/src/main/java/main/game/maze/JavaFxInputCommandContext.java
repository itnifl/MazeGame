package main.game.maze;

import main.game.maze.common.input.command.GameCommandContext;

/**
 * JavaFX implementation of the shared {@link GameCommandContext} interface.
 * Adapts between the shared command contract and JavaFX-specific action sinks.
 */
final class JavaFxInputCommandContext implements GameCommandContext {

    private final GameControllerInputSupport.GameKeyActionSink keyActionSink;

    JavaFxInputCommandContext(GameControllerInputSupport.GameKeyActionSink keyActionSink) {
        this.keyActionSink = keyActionSink;
    }

    // Shared interface methods
    @Override
    public boolean terminalActive() {
        // Terminal is not yet active in the current JavaFX implementation;
        // commands execute directly. When full terminal input handling is integrated,
        // this would check if a terminal prompt is open.
        return false;
    }

    @Override
    public void requestReturnToMenu() {
        keyActionSink.openDifficultyPickerAndMaybeRestart();
    }

    @Override
    public void openTerminalPrompt() {
        // Terminal is invoked via ESC in the current design.
        // This method is here for interface compliance with future refactoring.
    }

    @Override
    public void openHighScores() {
        keyActionSink.showHighScore();
    }

    @Override
    public void toggleSpanningTree() {
        keyActionSink.showSpanningTree();
    }

    @Override
    public void applyPathHintHeld(boolean held) {
        if (held) {
            keyActionSink.showNavigationPath();
        } else {
            keyActionSink.clearNavigationPath();
        }
    }

    @Override
    public void applyMovementFromFrame() {
        // Movement is applied by the movement timer in GameController,
        // which checks the currentInputFrame. This is called by MovePlayerCommand
        // but movement is actually applied elsewhere. This is a placeholder
        // for parity with the shared interface.
    }

    @Override
    public void requestStop() {
        // Stop is not yet integrated into commands; it's invoked via explicit stop() calls.
        // Placeholder for future integration.
    }

    @Override
    public boolean stopRequested() {
        return false;
    }

    // Legacy JavaFX-specific methods (kept for existing command compatibility)
    void showHighScore() {
        keyActionSink.showHighScore();
    }

    void openDifficultyPickerAndMaybeRestart() {
        keyActionSink.openDifficultyPickerAndMaybeRestart();
    }

    void showNavigationPath() {
        keyActionSink.showNavigationPath();
    }

    void showSpanningTree() {
        keyActionSink.showSpanningTree();
    }
}
