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
                // Spanning tree: each press toggles visibility on/off
                .bind(GameAction.TOGGLE_SPANNING_TREE, KeyCode.O, BindingKind.EDGE)
                .command(GameAction.TOGGLE_SPANNING_TREE, new ToggleSpanningTreeCommand())
                // Player movement (HELD so it's continuously checked)
                .bind(GameAction.MOVE_PLAYER, KeyCode.UP, BindingKind.HELD)
                .bind(GameAction.MOVE_PLAYER, KeyCode.DOWN, BindingKind.HELD)
                .bind(GameAction.MOVE_PLAYER, KeyCode.LEFT, BindingKind.HELD)
                .bind(GameAction.MOVE_PLAYER, KeyCode.RIGHT, BindingKind.HELD)
                .bind(GameAction.MOVE_PLAYER, KeyCode.W, BindingKind.HELD)
                .bind(GameAction.MOVE_PLAYER, KeyCode.A, BindingKind.HELD)
                .bind(GameAction.MOVE_PLAYER, KeyCode.S, BindingKind.HELD)
                .bind(GameAction.MOVE_PLAYER, KeyCode.D, BindingKind.HELD)
                .bind(GameAction.MOVE_PLAYER, KeyCode.NUMPAD8, BindingKind.HELD)
                .bind(GameAction.MOVE_PLAYER, KeyCode.NUMPAD5, BindingKind.HELD)
                .bind(GameAction.MOVE_PLAYER, KeyCode.NUMPAD4, BindingKind.HELD)
                .bind(GameAction.MOVE_PLAYER, KeyCode.NUMPAD6, BindingKind.HELD)
                .command(GameAction.MOVE_PLAYER, new MovePlayerCommand())
                // Terminal (currently integrated via ESC dialogue in GameController)
                .bind(GameAction.TOGGLE_TERMINAL, KeyCode.T, BindingKind.EDGE)
                .command(GameAction.TOGGLE_TERMINAL, new ToggleTerminalCommand());
    }
}
