package main.game.maze.libgdx.input.command;

import main.game.maze.common.input.InputFrame;
import main.game.maze.common.input.command.GameCommand;
import main.game.maze.common.input.command.GameCommandContext;

public final class TriggerFlameAttackCommand implements GameCommand<Integer> {

    @Override
    public void execute(GameCommandContext context, InputFrame<Integer> frame) {
        if (context.terminalActive()) {
            return;
        }
        context.triggerPlayerFlameAttack();
    }
}