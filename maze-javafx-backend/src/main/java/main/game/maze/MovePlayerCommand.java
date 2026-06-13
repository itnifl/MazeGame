package main.game.maze;

/**
 * Handles MOVE_PLAYER action (movement keys: W/A/S/D, arrow keys, numpad).
 * In the current JavaFX implementation, movement is applied by the movement timer
 * based on the current InputFrame's held keys. This command exists for structural
 * parity with the shared input system and future integration with the InputRouter.
 */
final class MovePlayerCommand implements JavaFxGameCommand {
    @Override
    public void execute(JavaFxInputCommandContext context) {
        // Movement is applied by the movement timer in GameController,
        // which checks the currentInputFrame and applies movement accordingly.
        // This command is called when a movement key is held, but the actual
        // movement logic is in the timer loop, not here. This design allows
        // for future refactoring to move movement application into the command.
        context.applyMovementFromFrame();
    }
}
