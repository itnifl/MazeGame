package main.game.maze;

/**
 * Handles OPEN_HIGH_SCORES action (H key).
 * Displays the high scores screen.
 */
final class OpenHighScoresCommand implements JavaFxGameCommand {
    @Override
    public void execute(JavaFxInputCommandContext context) {
        context.showHighScore();
    }
}
