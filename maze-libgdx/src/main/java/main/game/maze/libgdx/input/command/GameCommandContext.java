package main.game.maze.libgdx.input.command;

/**
 * Minimal command-facing facade over game state and side effects.
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
