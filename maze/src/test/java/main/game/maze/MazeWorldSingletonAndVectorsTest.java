package main.game.maze;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MazeWorldSingletonAndVectorsTest {

    @Test
    void getWorldReturnsSingletonAndHasOrthogonalSegments() {
        MazeWorld w1 = MazeWorld.GetWorld();
        MazeWorld w2 = MazeWorld.GetWorld();
        assertSame(w1, w2, "GetWorld should return the same singleton instance");

        assertFalse(w1.getMazeVectors().isEmpty(), "Maze should have segments");

        for (Vector2D v : w1.getMazeVectors()) {
            double dx = v.getEnd().getX() - v.getStart().getX();
            double dy = v.getEnd().getY() - v.getStart().getY();
            assertTrue(dx == 0 || dy == 0, "Segment must be horizontal or vertical: " + v);
        }
    }
}
