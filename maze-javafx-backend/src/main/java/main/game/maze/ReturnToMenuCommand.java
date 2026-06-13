package main.game.maze;

/**
 * Handles RETURN_TO_MENU action (ESC key).
 * Opens the difficulty picker dialog and allows restart.
 */
final class ReturnToMenuCommand implements JavaFxGameCommand {
    @Override
    public void execute(JavaFxInputCommandContext context) {
        context.openDifficultyPickerAndMaybeRestart();
    }
}
