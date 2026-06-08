package main.game.maze.libgdx.input.command;

import main.game.maze.libgdx.input.InputFrame;

/**
 * Command executed by input routing.
 */
public interface GameCommand {
    void execute(GameCommandContext context, InputFrame frame);
}
