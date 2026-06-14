package main.game.maze;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.scene.input.KeyCode;
import java.util.Set;
import org.junit.jupiter.api.Test;
import main.game.maze.common.input.GameAction;
import main.game.maze.common.input.InputFrame;
import main.game.maze.common.input.KeyBindingRegistry;
import main.game.maze.common.input.KeyBindingRegistry.BindingKind;

/**
 * Verifies that JavaFxInputBindingsSupport correctly configures the key binding registry
 * for JavaFX gameplay, ensuring all expected keys are bound and command registrations are correct.
 * Mirrors the structure of libGDX's GdxGameInputBindingsSupportTest but for KeyCode instead of Input.Keys.
 */
class JavaFxInputBindingsSupportTest {

    @Test
    void configuresAllExpectedKeyBindings() {
        KeyBindingRegistry<KeyCode> registry = new KeyBindingRegistry<>();
        JavaFxInputBindingsSupport.configureDefaultBindings(registry);

        // Verify all tracked keys include the expected bindings
        Set<KeyCode> trackedKeys = registry.trackedKeys();
        assertTrue(trackedKeys.contains(KeyCode.ESCAPE), "ESC should be tracked for RETURN_TO_MENU");
        assertTrue(trackedKeys.contains(KeyCode.H), "H should be tracked for OPEN_HIGH_SCORES");
        assertTrue(trackedKeys.contains(KeyCode.P), "P should be tracked for APPLY_PATH_HINT");
        assertTrue(trackedKeys.contains(KeyCode.O), "O should be tracked for TOGGLE_SPANNING_TREE");
        assertTrue(trackedKeys.contains(KeyCode.T), "T should be tracked for TOGGLE_TERMINAL");
        assertTrue(trackedKeys.contains(KeyCode.UP), "UP should be tracked for MOVE_PLAYER");
        assertTrue(trackedKeys.contains(KeyCode.DOWN), "DOWN should be tracked for MOVE_PLAYER");
        assertTrue(trackedKeys.contains(KeyCode.LEFT), "LEFT should be tracked for MOVE_PLAYER");
        assertTrue(trackedKeys.contains(KeyCode.RIGHT), "RIGHT should be tracked for MOVE_PLAYER");
        assertTrue(trackedKeys.contains(KeyCode.W), "W should be tracked for MOVE_PLAYER");
        assertTrue(trackedKeys.contains(KeyCode.A), "A should be tracked for MOVE_PLAYER");
        assertTrue(trackedKeys.contains(KeyCode.S), "S should be tracked for MOVE_PLAYER");
        assertTrue(trackedKeys.contains(KeyCode.D), "D should be tracked for MOVE_PLAYER");
    }

    @Test
    void bindingKindsCorrectForEdgeVsHeldActions() {
        KeyBindingRegistry<KeyCode> registry = new KeyBindingRegistry<>();
        JavaFxInputBindingsSupport.configureDefaultBindings(registry);

        // Edge actions should trigger on edge frame (key in edge set, not held)
        InputFrame<KeyCode> escapeEdge = new InputFrame<>(Set.of(), Set.of(KeyCode.ESCAPE), 0, 0, false);
        assertTrue(registry.isTriggered(GameAction.RETURN_TO_MENU, escapeEdge),
                "ESC key edge should trigger RETURN_TO_MENU");

        InputFrame<KeyCode> hEdge = new InputFrame<>(Set.of(), Set.of(KeyCode.H), 0, 0, false);
        assertTrue(registry.isTriggered(GameAction.OPEN_HIGH_SCORES, hEdge),
                "H key edge should trigger OPEN_HIGH_SCORES");

        // Held actions should trigger on held frame (key in held set)
        InputFrame<KeyCode> pHeld = new InputFrame<>(Set.of(KeyCode.P), Set.of(), 0, 0, false);
        assertTrue(registry.isTriggered(GameAction.APPLY_PATH_HINT, pHeld),
                "P key held should trigger APPLY_PATH_HINT");

        InputFrame<KeyCode> upHeld = new InputFrame<>(Set.of(KeyCode.UP), Set.of(), 0, 0, false);
        assertTrue(registry.isTriggered(GameAction.MOVE_PLAYER, upHeld),
                "UP key held should trigger MOVE_PLAYER");
    }

    @Test
    void multipleKeysMapToSingleAction() {
        KeyBindingRegistry<KeyCode> registry = new KeyBindingRegistry<>();
        JavaFxInputBindingsSupport.configureDefaultBindings(registry);

        // MOVE_PLAYER action should be triggered by multiple keys
        InputFrame<KeyCode> wHeld = new InputFrame<>(Set.of(KeyCode.W), Set.of(), 0, 0, false);
        assertTrue(registry.isTriggered(GameAction.MOVE_PLAYER, wHeld), "W key held should trigger MOVE_PLAYER");

        InputFrame<KeyCode> upHeld = new InputFrame<>(Set.of(KeyCode.UP), Set.of(), 0, 0, false);
        assertTrue(registry.isTriggered(GameAction.MOVE_PLAYER, upHeld), "UP key held should trigger MOVE_PLAYER");

        InputFrame<KeyCode> leftHeld = new InputFrame<>(Set.of(KeyCode.LEFT), Set.of(), 0, 0, false);
        assertTrue(registry.isTriggered(GameAction.MOVE_PLAYER, leftHeld), "LEFT key held should trigger MOVE_PLAYER");
    }

    @Test
    void numpadKeysIncludedForMovement() {
        KeyBindingRegistry<KeyCode> registry = new KeyBindingRegistry<>();
        JavaFxInputBindingsSupport.configureDefaultBindings(registry);

        Set<KeyCode> trackedKeys = registry.trackedKeys();
        assertTrue(trackedKeys.contains(KeyCode.NUMPAD8), "NUMPAD8 should be tracked for MOVE_PLAYER");
        assertTrue(trackedKeys.contains(KeyCode.NUMPAD5), "NUMPAD5 should be tracked for MOVE_PLAYER");
        assertTrue(trackedKeys.contains(KeyCode.NUMPAD4), "NUMPAD4 should be tracked for MOVE_PLAYER");
        assertTrue(trackedKeys.contains(KeyCode.NUMPAD6), "NUMPAD6 should be tracked for MOVE_PLAYER");

        // Verify they trigger the action
        InputFrame<KeyCode> numpad8Held = new InputFrame<>(Set.of(KeyCode.NUMPAD8), Set.of(), 0, 0, false);
        assertTrue(registry.isTriggered(GameAction.MOVE_PLAYER, numpad8Held),
                "NUMPAD8 held should trigger MOVE_PLAYER");
    }
}
