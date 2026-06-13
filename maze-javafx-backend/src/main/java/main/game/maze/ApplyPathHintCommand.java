package main.game.maze;

/**
 * Handles APPLY_PATH_HINT action (P key).
 * Shows the navigation path when the key is pressed.
 * Clearing the path is handled separately on key release
 * (through the edge-key tracking in GameController).
 */
final class ApplyPathHintCommand implements JavaFxGameCommand {
    @Override
    public void execute(JavaFxInputCommandContext context) {
        context.showNavigationPath();
    }
}
