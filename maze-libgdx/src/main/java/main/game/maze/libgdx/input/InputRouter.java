package main.game.maze.libgdx.input;

import main.game.maze.libgdx.input.command.GameCommand;
import main.game.maze.libgdx.input.command.GameCommandContext;

/**
 * Resolves active key bindings for a frame and runs their mapped commands.
 */
public final class InputRouter {

    private final KeyBindingRegistry registry;

    public InputRouter(KeyBindingRegistry registry) {
        this.registry = registry;
    }

    public void route(InputFrame frame, GameCommandContext context) {
        for (GameAction action : registry.executionOrder()) {
            if (!registry.isTriggered(action, frame)) {
                continue;
            }
            GameCommand command = registry.commandFor(action);
            if (command == null) {
                continue;
            }
            command.execute(context, frame);
            if (context.stopRequested()) {
                return;
            }
        }
    }
}
