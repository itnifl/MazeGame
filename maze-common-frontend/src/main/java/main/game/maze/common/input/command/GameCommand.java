package main.game.maze.common.input.command;

import main.game.maze.common.input.InputFrame;

/**
 * Command executed by input routing, parameterized on the frontend key type {@code K}.
 *
 * @param <K> the physical key type
 */
public interface GameCommand<K> {
    void execute(GameCommandContext context, InputFrame<K> frame);
}
