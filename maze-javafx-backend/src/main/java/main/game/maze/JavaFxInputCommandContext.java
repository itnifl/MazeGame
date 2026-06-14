package main.game.maze;

import main.game.maze.common.input.command.GameCommandContext;

/**
 * JavaFX implementation of the shared {@link GameCommandContext} interface.
 * Adapts between the shared command contract and JavaFX-specific action sinks.
 */
public final class JavaFxInputCommandContext implements GameCommandContext {

    interface ActionSink {
        void showHighScore();
        void openDifficultyPickerAndMaybeRestart();
        void showNavigationPath();
        void showSpanningTree();
        void clearNavigationPath();
        void clearSpanningTree();
        void updateDebugLabels();
        void updateScoreHud();
        void openTerminalPrompt();
    }

    private final ActionSink sink;
    private boolean spanningTreeVisible = false;

    JavaFxInputCommandContext(ActionSink sink) {
        this.sink = sink;
    }

    // Shared interface methods
    @Override
    public boolean terminalActive() {
        return false;
    }

    @Override
    public void requestReturnToMenu() {
        sink.openDifficultyPickerAndMaybeRestart();
    }

    @Override
    public void openTerminalPrompt() {
        sink.openTerminalPrompt();
    }

    @Override
    public void openHighScores() {
        sink.showHighScore();
    }

    @Override
    public void toggleSpanningTree() {
        spanningTreeVisible = !spanningTreeVisible;
        if (spanningTreeVisible) {
            sink.showSpanningTree();
        } else {
            sink.clearSpanningTree();
        }
    }

    @Override
    public void applyPathHintHeld(boolean held) {
        if (held) {
            sink.showNavigationPath();
        } else {
            sink.clearNavigationPath();
        }
    }

    @Override
    public void applyMovementFromFrame() {
        // Movement is handled by FxPlayingModeController directly
    }

    @Override
    public void requestStop() {
    }

    @Override
    public boolean stopRequested() {
        return false;
    }
    
    // JavaFX-specific methods
    public void updateDebugLabels() {
        sink.updateDebugLabels();
    }

    public void updateScoreHud() {
        sink.updateScoreHud();
    }
}
