package main.game.maze.common.input.command;

/**
 * Minimal command-facing facade over game state and side effects.
 * Frontend-neutral; both libGDX and JavaFX command contexts implement this interface.
 */
public interface GameCommandContext {

    boolean terminalActive();

    void requestReturnToMenu();

    void openTerminalPrompt();

    void openHighScores();

    void toggleSpanningTree();

    void applyPathHintHeld(boolean held);

    void applyMovementFromFrame();

    void requestStop();

    boolean stopRequested();
}
