package main.game.maze.libgdx.helper;

import com.badlogic.gdx.Input;
import main.game.maze.libgdx.input.GameAction;
import main.game.maze.libgdx.input.KeyBindingRegistry;
import main.game.maze.libgdx.input.KeyBindingRegistry.BindingKind;
import main.game.maze.libgdx.input.command.MovePlayerCommand;
import main.game.maze.libgdx.input.command.OpenHighScoresCommand;
import main.game.maze.libgdx.input.command.ReturnToMenuCommand;
import main.game.maze.libgdx.input.command.ToggleSpanningTreeCommand;
import main.game.maze.libgdx.input.command.ToggleTerminalCommand;

/**
 * Central input binding and command registration for libGDX gameplay.
 */
public final class GdxGameInputBindingsSupport {

    private GdxGameInputBindingsSupport() {
    }

    public static void configureDefaultBindings(KeyBindingRegistry keyBindingRegistry) {
        keyBindingRegistry
                .bind(GameAction.RETURN_TO_MENU, Input.Keys.ESCAPE, BindingKind.EDGE)
                .command(GameAction.RETURN_TO_MENU, new ReturnToMenuCommand())
                .bind(GameAction.TOGGLE_TERMINAL, Input.Keys.T, BindingKind.EDGE)
                .command(GameAction.TOGGLE_TERMINAL, new ToggleTerminalCommand())
                .bind(GameAction.OPEN_HIGH_SCORES, Input.Keys.H, BindingKind.EDGE)
                .command(GameAction.OPEN_HIGH_SCORES, new OpenHighScoresCommand())
                .bind(GameAction.TOGGLE_SPANNING_TREE, Input.Keys.O, BindingKind.EDGE)
                .command(GameAction.TOGGLE_SPANNING_TREE, new ToggleSpanningTreeCommand())
                .bind(GameAction.APPLY_PATH_HINT, Input.Keys.P, BindingKind.HELD)
                .bind(GameAction.MOVE_PLAYER, Input.Keys.LEFT, BindingKind.HELD)
                .bind(GameAction.MOVE_PLAYER, Input.Keys.A, BindingKind.HELD)
                .bind(GameAction.MOVE_PLAYER, Input.Keys.RIGHT, BindingKind.HELD)
                .bind(GameAction.MOVE_PLAYER, Input.Keys.D, BindingKind.HELD)
                .bind(GameAction.MOVE_PLAYER, Input.Keys.DOWN, BindingKind.HELD)
                .bind(GameAction.MOVE_PLAYER, Input.Keys.S, BindingKind.HELD)
                .bind(GameAction.MOVE_PLAYER, Input.Keys.UP, BindingKind.HELD)
                .bind(GameAction.MOVE_PLAYER, Input.Keys.W, BindingKind.HELD)
                .command(GameAction.MOVE_PLAYER, new MovePlayerCommand());
    }
}