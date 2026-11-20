package main.game.maze;

import javafx.scene.input.KeyCode;
import main.game.maze.common.input.GameAction;
import main.game.maze.common.input.KeyBindingRegistry;
import main.game.maze.common.input.KeyBindingRegistry.BindingKind;

/**
 * Central input binding and command registration for JavaFX gameplay.
 * Mirrors {@link main.game.maze.libgdx.helper.GdxGameInputBindingsSupport}
 * but for JavaFX {@link KeyCode} instead of libGDX {@code Input.Keys}.
 */
public final class JavaFxInputBindingsSupport {

    private JavaFxInputBindingsSupport() {
    }

    /**
     * Configures the default key-to-action bindings for JavaFX gameplay.
     * Registers all gameplay commands with their corresponding {@link GameAction} triggers.
     *
     * @param keyBindingRegistry the registry to configure
     */
    public static void configureDefaultBindings(KeyBindingRegistry<KeyCode> keyBindingRegistry) {
        keyBindingRegistry
                // Menu/navigation
                .bind(GameAction.RETURN_TO_MENU, KeyCode.ESCAPE, BindingKind.EDGE)
                .command(GameAction.RETURN_TO_MENU, new ReturnToMenuCommand())
                // High scores
                .bind(GameAction.OPEN_HIGH_SCORES, KeyCode.H, BindingKind.EDGE)
                .command(GameAction.OPEN_HIGH_SCORES, new OpenHighScoresCommand())
                // Path hint (press = show, release = clear)
                .bind(GameAction.APPLY_PATH_HINT, KeyCode.P, BindingKind.HELD)
                .command(GameAction.APPLY_PATH_HINT, new ApplyPathHintCommand())
                // Flame attack (space bar)
                .bind(GameAction.FLAME_ATTACK, KeyCode.SPACE, BindingKind.EDGE)
                .command(GameAction.FLAME_ATTACK, new TriggerFlameAttackCommand())
                .bind(GameAction.SWITCH_WEAPON, KeyCode.TAB, BindingKind.EDGE)
                .command(GameAction.SWITCH_WEAPON, new main.game.maze.common.input.command.SwitchWeaponCommand<>())
                // Spanning tree: each press toggles visibility on/off
                .bind(GameAction.TOGGLE_SPANNING_TREE, KeyCode.O, BindingKind.EDGE)
                .command(GameAction.TOGGLE_SPANNING_TREE, new ToggleSpanningTreeCommand())
                // right click uses the weapon in hand, like Space
                .bindMouse(GameAction.FLAME_ATTACK, main.game.maze.common.input.MouseButton.SECONDARY)
                // Player movement (HELD so it's continuously checked): see JavaFxMovementKeys
                .command(GameAction.MOVE_PLAYER, new MovePlayerCommand())
                // Terminal (currently integrated via ESC dialogue in GameController)
                .bind(GameAction.TOGGLE_TERMINAL, KeyCode.T, BindingKind.EDGE)
                .command(GameAction.TOGGLE_TERMINAL, new ToggleTerminalCommand());
        for (KeyCode key : JavaFxMovementKeys.KEYS.all()) {
            keyBindingRegistry.bind(GameAction.MOVE_PLAYER, key, BindingKind.HELD);
        }
    }
}
