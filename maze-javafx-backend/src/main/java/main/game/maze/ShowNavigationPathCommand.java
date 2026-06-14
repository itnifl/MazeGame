package main.game.maze;

final class ShowNavigationPathCommand implements JavaFxGameCommand {
    @Override
    public void execute(JavaFxInputCommandContext context) {
        context.applyPathHintHeld(true);
    }
}
