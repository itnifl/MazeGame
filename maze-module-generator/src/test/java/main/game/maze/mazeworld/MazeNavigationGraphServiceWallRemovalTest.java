package main.game.maze.mazeworld;

import main.game.maze.mazeworld.service.MazeNavigationGraph;
import main.game.maze.mazeworld.service.MazeNavigationGraph.Node;
import main.game.maze.mazeworld.service.MazeNavigationGraphService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MazeNavigationGraphService — rewireAfterWallRemoval")
class MazeNavigationGraphServiceWallRemovalTest {

    /*
     * Test fixture design
     * -------------------
     * Four outer walls bound the arena to [0..200]×[0..200]. With stepSize=100 this
     * produces a node grid where the padded bounds are [−100..300]×[−100..300], giving
     * cols/rows = 5. Nodes sit at x ∈ {−100, 0, 100, 200, 300} and y ∈ the same set.
     *
     * The inner vertical wall at x=50 spans y=[−200..400], which is well outside the
     * node range. Because x=50 is strictly between adjacent nodes at x=0 (col=1) and
     * x=100 (col=2), the segment from (0,y) to (100,y) always crosses x=50 cleanly
     * without touching either endpoint of the wall — avoiding the collinear corner case
     * in Vector2D.doIntersect.
     *
     * Blocked pair: col=1,row=2 → (0,100) and col=2,row=2 → (100,100).
     */

    private static final double STEP = 100.0;

    private static List<Vector2D> outerWalls() {
        return new ArrayList<>(List.of(
                new Vector2D(0, 0, 200, 0),
                new Vector2D(0, 200, 200, 200),
                new Vector2D(0, 0, 0, 200),
                new Vector2D(200, 0, 200, 200)
        ));
    }

    /** Vertical wall at x=50, spanning well outside the node range. */
    private static Vector2D innerWall() {
        return new Vector2D(50, -200, 50, 400);
    }

    /** Returns the node pair whose edge is blocked by the inner wall. */
    private static Node left(MazeNavigationGraph g)  { return g.getNode(1, 2); }
    private static Node right(MazeNavigationGraph g) { return g.getNode(2, 2); }

    // -----------------------------------------------------------------------

    @Test
    @DisplayName("restores an edge that was blocked by the removed wall")
    void restoresEdgeAfterWallRemoval() {
        List<Vector2D> walls = outerWalls();
        Vector2D blocking = innerWall();
        walls.add(blocking);

        MazeNavigationGraph graph = MazeNavigationGraphService.buildFrom(walls, STEP);
        Node l = left(graph), r = right(graph);

        assertNotNull(l, "Left node must exist");
        assertNotNull(r, "Right node must exist");
        assertFalse(l.getNeighbors().contains(r),
                "Edge (0,100)→(100,100) should be blocked by inner wall before removal");

        walls.remove(blocking);
        MazeNavigationGraphService.rewireAfterWallRemoval(graph, walls);

        assertTrue(l.getNeighbors().contains(r),
                "Edge should be restored after the blocking wall is removed");
        assertTrue(r.getNeighbors().contains(l),
                "Restored edge must be bidirectional");
    }

    @Test
    @DisplayName("does not add duplicate edges when called repeatedly")
    void noDuplicateEdgesOnRepeatCall() {
        List<Vector2D> walls = outerWalls();
        Vector2D blocking = innerWall();
        walls.add(blocking);
        MazeNavigationGraph graph = MazeNavigationGraphService.buildFrom(walls, STEP);

        walls.remove(blocking);
        MazeNavigationGraphService.rewireAfterWallRemoval(graph, walls);
        MazeNavigationGraphService.rewireAfterWallRemoval(graph, walls); // second call

        Node l = left(graph), r = right(graph);
        long count = l.getNeighbors().stream().filter(n -> n == r).count();
        assertEquals(1, count, "Edge must not be duplicated by repeated rewire calls");
    }

    @Test
    @DisplayName("does not add an edge that is still blocked by the remaining wall")
    void doesNotAddEdgeWhenWallStillPresent() {
        List<Vector2D> walls = outerWalls();
        walls.add(innerWall());
        MazeNavigationGraph graph = MazeNavigationGraphService.buildFrom(walls, STEP);

        Node l = left(graph), r = right(graph);
        assertFalse(l.getNeighbors().contains(r), "Edge should be blocked initially");

        // Rewire WITHOUT removing the blocking wall → edge must stay absent
        MazeNavigationGraphService.rewireAfterWallRemoval(graph, walls);

        assertFalse(l.getNeighbors().contains(r),
                "Edge should remain blocked when the wall is still in the list");
    }

    @Test
    @DisplayName("total edge count increases after a blocking wall is removed")
    void edgeCountIncreasesAfterRemoval() {
        List<Vector2D> walls = outerWalls();
        Vector2D blocking = innerWall();
        walls.add(blocking);
        MazeNavigationGraph graph = MazeNavigationGraphService.buildFrom(walls, STEP);

        int before = countEdges(graph);
        walls.remove(blocking);
        MazeNavigationGraphService.rewireAfterWallRemoval(graph, walls);
        int after = countEdges(graph);

        assertTrue(after > before,
                "Removing the inner wall must open at least one new passage");
    }

    @Test
    @DisplayName("rewire tolerates null graph gracefully")
    void toleratesNullGraph() {
        assertDoesNotThrow(() ->
                MazeNavigationGraphService.rewireAfterWallRemoval(null, List.of()));
    }

    @Test
    @DisplayName("rewire tolerates null walls list gracefully")
    void toleratesNullWalls() {
        List<Vector2D> walls = outerWalls();
        MazeNavigationGraph graph = MazeNavigationGraphService.buildFrom(walls, STEP);
        assertDoesNotThrow(() ->
                MazeNavigationGraphService.rewireAfterWallRemoval(graph, null));
    }

    private static int countEdges(MazeNavigationGraph graph) {
        int total = 0;
        for (int c = 0; c < graph.getCols(); c++) {
            for (int r = 0; r < graph.getRows(); r++) {
                Node n = graph.getNode(c, r);
                if (n != null) total += n.getNeighbors().size();
            }
        }
        return total / 2;
    }
}
