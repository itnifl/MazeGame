package main.game.maze;

/**
 * Handles TOGGLE_SPANNING_TREE action (O key).
 * Shows the spanning tree overlay when the key is pressed.
 * Clearing the tree is handled separately on key release
 * (through the edge-key tracking in GameController).
 */
final class ToggleSpanningTreeCommand implements JavaFxGameCommand {
    @Override
    public void execute(JavaFxInputCommandContext context) {
        context.showSpanningTree();
    }
}
