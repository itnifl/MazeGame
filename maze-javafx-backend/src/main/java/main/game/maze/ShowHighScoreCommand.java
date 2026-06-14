package main.game.maze;

final class ShowHighScoreCommand implements JavaFxGameCommand {
    @Override
    public void execute(JavaFxInputCommandContext context) {
        context.openHighScores();
    }
}
