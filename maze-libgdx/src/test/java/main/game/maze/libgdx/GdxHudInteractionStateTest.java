package main.game.maze.libgdx;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GdxHudInteractionStateTest {

    @Test
    void commandsButtonPressTogglesOverlayAndStartsPressTimer() {
        GdxHudInteractionState state = new GdxHudInteractionState();

        state.pressCommandsButton(0.14f);

        assertTrue(state.commandsOverlayVisible());
        assertTrue(state.commandButtonPressedSeconds() > 0f);
    }

    @Test
    void tickDrainsBothPressTimersToZero() {
        GdxHudInteractionState state = new GdxHudInteractionState();
        state.pressCommandsButton(0.14f);
        state.pressTerminalButton(0.14f);

        state.tick(1.0f);

        assertEquals(0f, state.commandButtonPressedSeconds());
        assertEquals(0f, state.terminalButtonPressedSeconds());
    }

    @Test
    void hideCommandsOverlayTurnsOverlayOff() {
        GdxHudInteractionState state = new GdxHudInteractionState();
        state.pressCommandsButton(0.14f);

        state.hideCommandsOverlay();

        assertFalse(state.commandsOverlayVisible());
    }
}