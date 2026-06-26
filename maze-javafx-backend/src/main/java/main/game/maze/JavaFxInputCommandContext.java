package main.game.maze;

import main.game.maze.common.input.command.GameCommandContext;

/**
 * JavaFX implementation of the shared {@link GameCommandContext} interface.
 * Adapts between the shared command contract and JavaFX-specific action sinks.
 *
 * <p>The sink interface is split into three narrower interfaces (ISP) so that
 * components which only need one concern can declare a narrower dependency:</p>
 * <ul>
 *   <li>{@link NavigationSink} – menu / score navigation</li>
 *   <li>{@link OverlaySink} – path-hint and spanning-tree overlays</li>
 *   <li>{@link HudSink} – HUD / debug updates</li>
 * </ul>
 * {@link ActionSink} composes all three for full-controller wiring.
 */
public final class JavaFxInputCommandContext implements GameCommandContext {

    /** Menu and high-score navigation actions. */
    interface NavigationSink {
        void showHighScore();
        void openDifficultyPickerAndMaybeRestart();
        void openTerminalPrompt();
    }

    /** Path-hint and spanning-tree overlay actions. */
    interface OverlaySink {
        void showNavigationPath();
        void clearNavigationPath();
        void showSpanningTree();
        void clearSpanningTree();
    }

    /** HUD update actions. */
    interface HudSink {
        void updateDebugLabels();
        void updateScoreHud();
    }

    /** Full sink interface used by {@link main.game.maze.GameController}. Composes all three. */
    interface ActionSink extends NavigationSink, OverlaySink, HudSink {
        void triggerPlayerFlameAttack();
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
    public void triggerPlayerFlameAttack() {
        sink.triggerPlayerFlameAttack();
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
