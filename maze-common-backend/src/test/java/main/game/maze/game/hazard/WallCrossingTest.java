package main.game.maze.game.hazard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.generators.WallSegment;
import org.junit.jupiter.api.Test;

class WallCrossingTest {

    @Test
    void countsEveryWallTheLineCrosses() {
        List<WallSegment> walls = List.of(new WallSegment(20, 0, 20, 20), new WallSegment(40, 0, 40, 20),
                new WallSegment(60, 0, 60, 20), new WallSegment(80, 0, 80, 20), new WallSegment(20, 50, 80, 50));
        assertEquals(4, WallCrossing.countSegments(10, 10, 90, 10, walls));
        assertEquals(1, WallCrossing.countSegments(10, 10, 30, 10, walls));
        assertEquals(0, WallCrossing.countSegments(10, 10, 15, 10, walls));
    }

    @Test
    void countsVectorWallsToo() {
        List<Vector2D> walls = List.of(new Vector2D(new Point2D(20, 0), new Point2D(20, 20)),
                new Vector2D(new Point2D(40, 0), new Point2D(40, 20)));
        assertEquals(2, WallCrossing.countVectors(10, 10, 50, 10, walls));
        assertEquals(1, WallCrossing.countVectors(10, 10, 30, 10, walls));
    }
}
