package main.game.maze.libgdx.helper;

import com.badlogic.gdx.Input;
import main.game.maze.common.input.MouseButton;
import main.game.maze.libgdx.input.GdxMovementKeys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import java.util.function.BooleanSupplier;
import java.util.function.IntPredicate;
import main.game.maze.common.input.GameAction;
import main.game.maze.common.input.KeyBindingRegistry;
import main.game.maze.common.input.KeyBindingRegistry.BindingKind;
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

    public static void configureDefaultBindings(KeyBindingRegistry<Integer> keyBindingRegistry) {
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
                .bind(GameAction.FLAME_ATTACK, Input.Keys.SPACE, BindingKind.EDGE)
                .command(GameAction.FLAME_ATTACK, new main.game.maze.libgdx.input.command.TriggerFlameAttackCommand())
                .bind(GameAction.SWITCH_WEAPON, Input.Keys.TAB, BindingKind.EDGE)
                .command(GameAction.SWITCH_WEAPON, new main.game.maze.common.input.command.SwitchWeaponCommand<>())
                // right click uses the weapon in hand, like Space
                .bindMouse(GameAction.FLAME_ATTACK, MouseButton.SECONDARY)
                .command(GameAction.MOVE_PLAYER, new MovePlayerCommand());
        for (Integer key : GdxMovementKeys.KEYS.all()) {
            keyBindingRegistry.bind(GameAction.MOVE_PLAYER, key, BindingKind.HELD);
        }
    }

    /**
     * Builds the platform keyboard {@link InputProcessor} that feeds typed
     * characters to the right consumer. The OS-localized keyboard layout drives
     * {@code keyTyped}, so this is how æ/ø/å and accented letters reach the
     * terminal and win-name fields (libGDX {@code Input.Keys.*} constants map
     * only US-layout ASCII).
     *
     * @param captureWinName  true when typed chars should go to the win-name field
     * @param onWinNameChar   consumes a typed char as the win name; returns whether it was handled
     * @param onTerminalChar  consumes a typed char as terminal input; returns whether it was handled
     */
    public static InputProcessor createTerminalKeyboardProcessor(
            BooleanSupplier captureWinName,
            IntPredicate onWinNameChar,
            IntPredicate onTerminalChar) {
        return new InputAdapter() {
            @Override
            public boolean keyTyped(char character) {
                if (captureWinName.getAsBoolean()) {
                    return onWinNameChar.test(character);
                }
                return onTerminalChar.test(character);
            }
        };
    }
}
