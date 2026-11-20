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

    /** Attack key: use the weapon in hand (plant a bomb, fire the laser, swing the sledgehammer). */
    void triggerPlayerFlameAttack();

    /** Tab: switch to the next weapon in stock. */
    default void switchWeapon() {
    }

    void applyMovementFromFrame();

    void requestStop();

    boolean stopRequested();
}
