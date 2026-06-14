package main.game.maze;

final class ShowSpanningTreeCommand implements JavaFxGameCommand {
    @Override
    public void execute(JavaFxInputCommandContext context) {
        context.toggleSpanningTree();
    }
}
