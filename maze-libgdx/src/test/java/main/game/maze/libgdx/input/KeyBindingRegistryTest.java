package main.game.maze.libgdx.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;

class KeyBindingRegistryTest {

    @Test
    void tracksBoundKeysAndTriggerKinds() {
        KeyBindingRegistry registry = new KeyBindingRegistry()
                .bind(GameAction.TOGGLE_TERMINAL, 1, KeyBindingRegistry.BindingKind.EDGE)
                .bind(GameAction.MOVE_PLAYER, 2, KeyBindingRegistry.BindingKind.HELD);

        assertEquals(Set.of(1, 2), registry.trackedKeyCodes());

        InputFrame edgeFrame = new InputFrame(Set.of(), Set.of(1), 0, 0, false);
        InputFrame heldFrame = new InputFrame(Set.of(2), Set.of(), 0, 0, false);

        assertTrue(registry.isTriggered(GameAction.TOGGLE_TERMINAL, edgeFrame));
        assertTrue(registry.isTriggered(GameAction.MOVE_PLAYER, heldFrame));
    }
}
