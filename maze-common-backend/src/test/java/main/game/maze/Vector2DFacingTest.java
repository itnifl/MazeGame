package main.game.maze;

import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector2DFacingTest {

    @Test
    void facingRightLeftUpDownIdle() {
        assertEquals(Vector2D.VectorFacing.RIGHT,
            new Vector2D(new Point2D(0,0), new Point2D(5,0)).getFacingFromVector());

        assertEquals(Vector2D.VectorFacing.LEFT,
            new Vector2D(new Point2D(5,0), new Point2D(0,0)).getFacingFromVector());

        assertEquals(Vector2D.VectorFacing.DOWN,
            new Vector2D(new Point2D(0,0), new Point2D(0,7)).getFacingFromVector());

        assertEquals(Vector2D.VectorFacing.UP,
            new Vector2D(new Point2D(0,7), new Point2D(0,0)).getFacingFromVector());

        assertEquals(Vector2D.VectorFacing.IDLE,
            new Vector2D(new Point2D(2,2), new Point2D(2,2)).getFacingFromVector());
    }
}


