package main.game.maze;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector2DNormalizeMagnitudeTest {

    @Test
    void normalizedVectorHasExpectedMagnitudeAndDirection() {
        Vector2D v = new Vector2D(new Point2D(10, 10), new Point2D(13, 14)); // dx=3, dy=4 -> mag=5
        Vector2D n = v.normalize(20); // scale unit vector by 20

        double dx = n.getEnd().getX() - n.getStart().getX();
        double dy = n.getEnd().getY() - n.getStart().getY();

        assertEquals(20.0, Math.hypot(dx, dy), 1e-6);
        assertEquals(3.0/5.0, dx/20.0, 1e-6);
        assertEquals(4.0/5.0, dy/20.0, 1e-6);
    }
}
