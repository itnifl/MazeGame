package main.game.maze.libgdx.input.command;

import main.game.maze.libgdx.input.InputFrame;

public final class OpenHighScoresCommand implements GameCommand {

    @Override
    public void execute(GameCommandContext context, InputFrame frame) {
        if (context.terminalActive()) {
            return;
        }
        context.openHighScores();
    }
}
