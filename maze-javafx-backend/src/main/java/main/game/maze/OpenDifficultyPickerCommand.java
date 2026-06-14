package main.game.maze;

final class OpenDifficultyPickerCommand implements JavaFxGameCommand {
    @Override
    public void execute(JavaFxInputCommandContext context) {
        context.requestReturnToMenu();
    }
}
