package main.game.maze.libgdx.input.command;

import com.badlogic.gdx.Input;
import main.game.maze.libgdx.input.InputFrame;

public final class ApplyPathHintCommand implements GameCommand {

    @Override
    public void execute(GameCommandContext context, InputFrame frame) {
        if (context.terminalActive()) {
            return;
        }
        context.applyPathHintHeld(frame.isHeld(Input.Keys.P));
    }
}
