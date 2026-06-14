package main.game.maze;

/**
 * Handles TOGGLE_SPANNING_TREE action (O key, EDGE binding).
 * Each press alternates the spanning-tree overlay between visible and hidden.
 * The toggle state lives in {@link JavaFxInputCommandContext}.
 */
final class ToggleSpanningTreeCommand implements JavaFxGameCommand {
    @Override
    public void execute(JavaFxInputCommandContext context) {
        context.toggleSpanningTree();
    }
}
