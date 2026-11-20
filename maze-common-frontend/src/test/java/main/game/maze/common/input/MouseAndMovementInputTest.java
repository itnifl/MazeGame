package main.game.maze.common.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Mouse-button bindings and movement keys (shared by both frontends)")
class MouseAndMovementInputTest {

    @Test
    @DisplayName("a right click triggers the action bound to the secondary button, a left click does not")
    void secondaryClickTriggersBoundAction() {
        KeyBindingRegistry<String> registry = new KeyBindingRegistry<String>()
                .bind(GameAction.FLAME_ATTACK, "SPACE", KeyBindingRegistry.BindingKind.EDGE)
                .bindMouse(GameAction.FLAME_ATTACK, MouseButton.SECONDARY);

        InputFrame<String> rightClick = new InputFrame<>(Set.of(), Set.of(), 0, 0, false, true);
        InputFrame<String> leftClick = new InputFrame<>(Set.of(), Set.of(), 0, 0, true, false);
        InputFrame<String> nothing = new InputFrame<>(Set.of(), Set.of(), 0, 0, false);

        assertTrue(registry.isTriggered(GameAction.FLAME_ATTACK, rightClick));
        assertFalse(registry.isTriggered(GameAction.FLAME_ATTACK, leftClick));
        assertFalse(registry.isTriggered(GameAction.FLAME_ATTACK, nothing));
        assertTrue(rightClick.isMouseClicked(MouseButton.SECONDARY));
        assertTrue(leftClick.isMouseClicked(MouseButton.PRIMARY));
        assertFalse(nothing.secondaryMouseClicked(), "the 5-argument frame has no right click");
        assertEquals(Set.of("SPACE"), registry.trackedKeys(), "mouse bindings are not keys");
    }

    @Test
    @DisplayName("movement keys: Q/A left, W up, E right, S/D down, besides the arrows")
    void movementKeys() {
        MovementKeys<String> keys = new MovementKeys<>(
                Set.of("LEFT", "A", "Q"), Set.of("RIGHT", "E"), Set.of("UP", "W"), Set.of("DOWN", "S", "D"));

        assertEquals(-1, keys.dx(held("Q")));
        assertEquals(-1, keys.dx(held("A")));
        assertEquals(1, keys.dx(held("E")));
        assertEquals(-1, keys.dy(held("W")), "up is negative y (screen y-down)");
        assertEquals(1, keys.dy(held("D")));
        assertEquals(1, keys.dy(held("S")));
        assertEquals(0, keys.dx(held("Q", "E")), "opposite keys cancel");
        assertTrue(keys.isLeft(held("Q")) && keys.isDown(held("D")) && keys.isRight(held("E")) && keys.isUp(held("W")));
        assertEquals(Set.of("LEFT", "A", "Q", "RIGHT", "E", "UP", "W", "DOWN", "S", "D"), keys.all());
    }

    @Test
    @DisplayName("a key cannot move in two directions")
    void keysAreExclusive() {
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> new MovementKeys<>(Set.of("D"), Set.of("D"), Set.of(), Set.of()));
    }

    private static InputFrame<String> held(String... keys) {
        return new InputFrame<>(Set.of(keys), Set.of(), 0, 0, false);
    }
}
