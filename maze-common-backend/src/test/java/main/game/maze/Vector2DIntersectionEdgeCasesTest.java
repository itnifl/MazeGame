package main.game.maze;

import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector2DIntersectionEdgeCasesTest {

    @Test
    void intersectsWhenCollinearOverlapping() {
        Vector2D a = new Vector2D(new Point2D(0, 0), new Point2D(10, 0));
        Vector2D b = new Vector2D(new Point2D(5, 0), new Point2D(15, 0));
        assertTrue(a.doIntersect(b, 0));
    }

    @Test
    void intersectsWhenTouchingAtEndpoint() {
        Vector2D a = new Vector2D(new Point2D(0, 0), new Point2D(10, 0));
        Vector2D b = new Vector2D(new Point2D(10, 0), new Point2D(10, 10));
        assertTrue(a.doIntersect(b, 0));
    }

    @Test
    void noIntersectionWithGapEvenWithOffset() {
        Vector2D a = new Vector2D(new Point2D(0, 0), new Point2D(0, 10));    // vertical
        Vector2D b = new Vector2D(new Point2D(5, 0), new Point2D(5, 10));    // vertical parallel
        assertFalse(a.doIntersect(b, 1));
    }
}


