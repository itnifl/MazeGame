package main.game.maze.libgdx;

import main.game.maze.libgdx.adapter.*;
import main.game.maze.libgdx.controller.*;
import main.game.maze.libgdx.helper.*;
import main.game.maze.libgdx.service.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.game.maze.libgdx.controller.GdxHudInteractionStateController;
import org.junit.jupiter.api.Test;

class GdxHudInteractionStateTest {

    @Test
    void commandsButtonPressTogglesOverlayAndStartsPressTimer() {
        GdxHudInteractionStateController state = new GdxHudInteractionStateController();

        state.pressCommandsButton(0.14f);

        assertTrue(state.commandsOverlayVisible());
        assertTrue(state.commandButtonPressedSeconds() > 0f);
    }

    @Test
    void tickDrainsBothPressTimersToZero() {
        GdxHudInteractionStateController state = new GdxHudInteractionStateController();
        state.pressCommandsButton(0.14f);
        state.pressTerminalButton(0.14f);

        state.tick(1.0f);

        assertEquals(0f, state.commandButtonPressedSeconds());
        assertEquals(0f, state.terminalButtonPressedSeconds());
    }

    @Test
    void hideCommandsOverlayTurnsOverlayOff() {
        GdxHudInteractionStateController state = new GdxHudInteractionStateController();
        state.pressCommandsButton(0.14f);

        state.hideCommandsOverlay();

        assertFalse(state.commandsOverlayVisible());
    }
}
