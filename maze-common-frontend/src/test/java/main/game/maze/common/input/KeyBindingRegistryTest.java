package main.game.maze.common.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;

class KeyBindingRegistryTest {

    @Test
    void tracksBoundKeysAndTriggerKinds() {
        KeyBindingRegistry<Integer> registry = new KeyBindingRegistry<Integer>()
                .bind(GameAction.TOGGLE_TERMINAL, 1, KeyBindingRegistry.BindingKind.EDGE)
                .bind(GameAction.MOVE_PLAYER, 2, KeyBindingRegistry.BindingKind.HELD);

        assertEquals(Set.of(1, 2), registry.trackedKeys());

        InputFrame<Integer> edgeFrame = new InputFrame<>(Set.of(), Set.of(1), 0, 0, false);
        InputFrame<Integer> heldFrame = new InputFrame<>(Set.of(2), Set.of(), 0, 0, false);

        assertTrue(registry.isTriggered(GameAction.TOGGLE_TERMINAL, edgeFrame));
        assertTrue(registry.isTriggered(GameAction.MOVE_PLAYER, heldFrame));
    }

    @Test
    void supportsNonIntegerKeyTypes() {
        KeyBindingRegistry<String> registry = new KeyBindingRegistry<String>()
                .bind(GameAction.OPEN_HIGH_SCORES, "H", KeyBindingRegistry.BindingKind.EDGE);

        assertEquals(Set.of("H"), registry.trackedKeys());

        InputFrame<String> edgeFrame = new InputFrame<>(Set.of(), Set.of("H"), 0, 0, false);
        assertTrue(registry.isTriggered(GameAction.OPEN_HIGH_SCORES, edgeFrame));
    }
}
