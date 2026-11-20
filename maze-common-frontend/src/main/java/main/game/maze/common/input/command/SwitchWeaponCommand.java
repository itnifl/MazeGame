package main.game.maze.common.input.command;

import main.game.maze.common.input.InputFrame;

/** Tab: switch to the next weapon in stock (shared by both frontends). */
public final class SwitchWeaponCommand<K> implements GameCommand<K> {

    @Override
    public void execute(GameCommandContext context, InputFrame<K> frame) {
        if (context.terminalActive()) {
            return;
        }
        context.switchWeapon();
    }
}
