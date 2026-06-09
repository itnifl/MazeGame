package main.game.maze.libgdx;

import main.game.maze.libgdx.adapter.*;
import main.game.maze.libgdx.controller.*;
import main.game.maze.libgdx.helper.*;
import main.game.maze.libgdx.service.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.game.maze.libgdx.controller.GdxPlayerInputController;

class GdxPlayerInputControllerTest {

    @Test
    void oppositeHorizontalKeysCancelOut() {
        GdxPlayerInputController controller = new GdxPlayerInputController();

        GdxPlayerInputController.MovementIntent intent =
                controller.resolveMovement(true, true, false, false);

        assertFalse(intent.hasMovement());
        assertEquals(0f, intent.dx(), 0.0001f);
        assertEquals(0f, intent.dy(), 0.0001f);
    }

    @Test
    void diagonalMovementIsNormalized() {
        GdxPlayerInputController controller = new GdxPlayerInputController();

        GdxPlayerInputController.MovementIntent intent =
                controller.resolveMovement(true, false, false, true);

        assertTrue(intent.hasMovement());
        assertEquals(-0.7071f, intent.dx(), 0.001f);
        assertEquals(0.7071f, intent.dy(), 0.001f);
    }
}
