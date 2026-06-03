package main.game.maze.libgdx;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.game.maze.libgdx.controller.GdxModeInputController;

class GdxModeInputControllerTest {

    @Test
    void consumeEscFiresOnceWhileHeld() {
        GdxModeInputController controller = new GdxModeInputController();

        assertTrue(controller.consumeEsc(true));
        assertFalse(controller.consumeEsc(true));
        assertFalse(controller.consumeEsc(false));
        assertTrue(controller.consumeEsc(true));
    }

    @Test
    void resetClearsLatches() {
        GdxModeInputController controller = new GdxModeInputController();
        controller.consumeT(true);
        controller.consumeH(true);

        controller.reset();

        assertTrue(controller.consumeT(true));
        assertTrue(controller.consumeH(true));
    }
}
