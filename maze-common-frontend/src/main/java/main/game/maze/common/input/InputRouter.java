package main.game.maze.common.input;

import main.game.maze.common.input.command.GameCommand;
import main.game.maze.common.input.command.GameCommandContext;

/**
 * Resolves active key bindings for a frame and runs their mapped commands,
 * parameterized on the physical key type {@code K}.
 *
 * @param <K> the physical key type
 */
public final class InputRouter<K> {

    private final KeyBindingRegistry<K> registry;

    public InputRouter(KeyBindingRegistry<K> registry) {
        this.registry = registry;
    }

    public void route(InputFrame<K> frame, GameCommandContext context) {
        for (GameAction action : registry.executionOrder()) {
            if (!registry.isTriggered(action, frame)) {
                continue;
            }
            GameCommand<K> command = registry.commandFor(action);
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
