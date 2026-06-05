package main.game.maze.libgdx;

import main.game.maze.libgdx.adapter.*;
import main.game.maze.libgdx.controller.*;
import main.game.maze.libgdx.helper.*;
import main.game.maze.libgdx.service.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.game.maze.libgdx.controller.GdxTerminalController;
import org.junit.jupiter.api.Test;

class GdxTerminalControllerTest {

    @Test
    void submitQueuesCommandAndClosesTerminal() {
        GdxTerminalController controller = new GdxTerminalController(8);
        controller.open();

        assertTrue(controller.onKeyTyped('h'));
        assertTrue(controller.onKeyTyped('i'));
        controller.submit();

        assertFalse(controller.isActive());
        assertEquals("hi", controller.consumePendingCommand());
        assertNull(controller.consumePendingCommand());
    }

    @Test
    void backspaceRemovesLastCharacterOnly() {
        GdxTerminalController controller = new GdxTerminalController(8);
        controller.open();

        controller.onKeyTyped('a');
        controller.onKeyTyped('b');
        controller.backspace();

        assertEquals("a", controller.bufferText());
    }

    @Test
    void maxLengthStopsFurtherCharacterInput() {
        GdxTerminalController controller = new GdxTerminalController(2);
        controller.open();

        controller.onKeyTyped('a');
        controller.onKeyTyped('b');
        controller.onKeyTyped('c');

        assertEquals("ab", controller.bufferText());
    }
}


