package main.game.maze.game.hazard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import main.game.maze.mazeworld.Point2D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Edge teleport: the opposite edge, or a random outer edge, never in a wall")
class EdgeTeleportTest {

    /** Free points on a 30 px grid over 800 x 600, as the corridor points of a maze would be. */
    private static List<Point2D> grid() {
        List<Point2D> points = new ArrayList<>();
        for (int x = 15; x < 800; x += 30) {
            for (int y = 15; y < 600; y += 30) {
                points.add(new Point2D(x, y));
            }
        }
        return points;
    }

    private static boolean onOuterBand(Point2D point) {
        return point.getX() < 100 || point.getX() > 700 || point.getY() < 100 || point.getY() > 500;
    }

    @Test
    @DisplayName("falling in a hole near the left edge comes out at the right edge, at about the same height")
    void leftToRight() {
        Point2D out = EdgeTeleport.oppositeEdge(40, 300, grid());
        assertNotNull(out);
        assertTrue(out.getX() > 700, "the right edge: " + out.getX());
        assertEquals(300, out.getY(), 40);
    }

    @Test
    @DisplayName("near the right edge to the left, near the top to the bottom, near the bottom to the top")
    void otherEdges() {
        assertTrue(EdgeTeleport.oppositeEdge(760, 250, grid()).getX() < 100);
        assertTrue(EdgeTeleport.oppositeEdge(400, 30, grid()).getY() > 500);
        assertTrue(EdgeTeleport.oppositeEdge(300, 570, grid()).getY() < 100);
    }

    @Test
    @DisplayName("the exit is always one of the free points, so never inside a wall")
    void exitIsFree() {
        List<Point2D> free = grid();
        Set<Point2D> allowed = new HashSet<>(free);
        for (double x = 20; x < 800; x += 70) {
            for (double y = 20; y < 600; y += 70) {
                assertTrue(allowed.contains(EdgeTeleport.oppositeEdge(x, y, free)));
            }
        }
    }

    @Test
    @DisplayName("with few free points the exit is the nearest one to where the opposite edge would put the player")
    void sparsePoints() {
        List<Point2D> free = List.of(new Point2D(780, 100), new Point2D(780, 500), new Point2D(20, 300), new Point2D(400, 300));
        Point2D out = EdgeTeleport.oppositeEdge(30, 480, free);
        assertEquals(780, out.getX(), 1e-9);
        assertEquals(500, out.getY(), 1e-9);
    }

    @Test
    @DisplayName("no free points, no exit")
    void nothingFree() {
        assertNull(EdgeTeleport.oppositeEdge(10, 10, List.of()));
        assertNull(EdgeTeleport.randomOuterEdge(List.of(), new Random(1)));
    }

    @Test
    @DisplayName("a random outer edge spot is a free point at the edge of the map, never in the middle")
    void randomOuterEdge() {
        List<Point2D> free = grid();
        Random random = new Random(7);
        Set<Point2D> seen = new HashSet<>();
        for (int i = 0; i < 300; i++) {
            Point2D spot = EdgeTeleport.randomOuterEdge(free, random);
            assertTrue(free.contains(spot));
            assertTrue(onOuterBand(spot), "at the edge: " + spot);
            seen.add(spot);
        }
        assertTrue(seen.size() > 15, "it really is random: " + seen.size() + " different spots");
    }

    @Test
    @DisplayName("all four edges get used")
    void allEdges() {
        Random random = new Random(3);
        Set<String> edges = new HashSet<>();
        for (int i = 0; i < 400; i++) {
            Point2D spot = EdgeTeleport.randomOuterEdge(grid(), random);
            if (spot.getX() < 100) {
                edges.add("left");
            }
            if (spot.getX() > 700) {
                edges.add("right");
            }
            if (spot.getY() < 100) {
                edges.add("top");
            }
            if (spot.getY() > 500) {
                edges.add("bottom");
            }
        }
        assertEquals(4, edges.size(), edges.toString());
    }

    @Test
    @DisplayName("the cooldown stops a second teleport right after the first")
    void gate() {
        TeleportGate gate = new TeleportGate(2.5);
        assertTrue(gate.tryUse(10.0));
        assertFalse(gate.tryUse(11.0), "still cooling down");
        assertFalse(gate.tryUse(12.4));
        assertTrue(gate.tryUse(12.6), "ready again");
        gate.reset();
        assertTrue(gate.tryUse(12.7), "a new level starts ready");
    }
}
