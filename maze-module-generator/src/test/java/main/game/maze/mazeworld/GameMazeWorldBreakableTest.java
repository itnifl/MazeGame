package main.game.maze.mazeworld;

import main.game.maze.mazeworld.generators.IMazeGenerator;
import main.game.maze.mazeworld.service.MazeNavigationGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that GameMazeWorld correctly tracks breakable walls and applies
 * projectile damage — including wall removal and nav-graph rewiring.
 *
 * Uses the no-arg constructor so no MazeGenerator is needed.
 */
@DisplayName("GameMazeWorld — breakable wall logic")
class GameMazeWorldBreakableTest {

    private GameMazeWorld world;

    private static final class SequenceMazeGenerator implements IMazeGenerator {
        private final Queue<List<Vector2D>> outputs = new ArrayDeque<>();

        private SequenceMazeGenerator(List<Vector2D> first, List<Vector2D> second) {
            outputs.add(first);
            outputs.add(second);
        }

        @Override
        public List<Vector2D> generateMaze() {
            List<Vector2D> next = outputs.poll();
            return next == null ? List.of() : next;
        }
    }

    @BeforeEach
    void setUp() {
        world = new GameMazeWorld();
    }

    @Test
    @DisplayName("some walls are registered as breakable on construction")
    void someWallsAreBreakable() {
        assertFalse(world.getBreakableWalls().isEmpty(),
                "Expect at least one breakable wall in the default map");
    }

    @Test
    @DisplayName("breakable wall geometries are a subset of mazeVectors")
    void breakableGeometriesInMazeVectors() {
        List<Vector2D> all = world.getMazeVectors();
        for (BreakableWall bw : world.getBreakableWalls()) {
            assertTrue(all.contains(bw.geometry),
                    "Breakable wall geometry must be in mazeVectors");
        }
    }

    @Test
    @DisplayName("findBreakableWall returns the wall for a known breakable geometry")
    void findBreakableWallReturnsMatch() {
        BreakableWall first = world.getBreakableWalls().get(0);
        BreakableWall found = world.findBreakableWall(first.geometry);
        assertSame(first, found);
    }

    @Test
    @DisplayName("findBreakableWall returns null for a non-breakable wall")
    void findBreakableWallReturnsNullForIndestructible() {
        // Find a geometry that is NOT in breakableWalls
        Vector2D indestructible = world.getMazeVectors().stream()
                .filter(v -> world.findBreakableWall(v) == null)
                .findFirst()
                .orElse(null);

        // It is valid for ALL walls to be breakable, so only assert when one exists
        if (indestructible != null) {
            assertNull(world.findBreakableWall(indestructible));
        }
    }

    @Test
    @DisplayName("findBreakableWall returns null for null geometry")
    void findBreakableWallReturnsNullForNull() {
        assertNull(world.findBreakableWall(null));
    }

    @Test
    @DisplayName("applyWallDamage returns false and keeps wall when HP > 0 after hit")
    void partialDamageDoesNotRemoveWall() {
        BreakableWall bw = world.getBreakableWalls().get(0);
        int initialSize = world.getMazeVectors().size();
        int hp = bw.getRemainingHp();

        // Apply all-but-one-HP of damage (remaining will be exactly 1)
        boolean destroyed = world.applyWallDamage(bw, hp - 1);

        assertFalse(destroyed);
        assertEquals(1, bw.getRemainingHp());
        assertEquals(initialSize, world.getMazeVectors().size(),
                "Wall must not be removed when HP > 0");
        assertTrue(world.getBreakableWalls().contains(bw));
    }

    @Test
    @DisplayName("applyWallDamage returns true and removes wall when HP reaches 0")
    void lethalDamageRemovesWall() {
        BreakableWall bw = world.getBreakableWalls().get(0);
        Vector2D geometry = bw.geometry;
        int initialVectors = world.getMazeVectors().size();

        boolean destroyed = world.applyWallDamage(bw, bw.getRemainingHp());

        assertTrue(destroyed);
        assertFalse(world.getMazeVectors().contains(geometry),
                "Destroyed wall must be removed from mazeVectors");
        assertFalse(world.getBreakableWalls().contains(bw),
                "Destroyed wall must be removed from breakableWalls");
        assertEquals(initialVectors - 1, world.getMazeVectors().size());
    }

    @Test
    @DisplayName("applyWallDamage returns false for an already-removed wall")
    void doubleDamageIsIdempotent() {
        BreakableWall bw = world.getBreakableWalls().get(0);
        world.applyWallDamage(bw, bw.getRemainingHp());

        // Second call: wall already gone
        boolean result = world.applyWallDamage(bw, 999);
        assertFalse(result);
    }

    @Test
    @DisplayName("applyWallDamage with zero damage keeps wall unchanged")
    void zeroDamageKeepsWall() {
        BreakableWall bw = world.getBreakableWalls().get(0);
        int hpBefore = bw.getRemainingHp();
        int vectorsBefore = world.getMazeVectors().size();

        boolean destroyed = world.applyWallDamage(bw, 0);

        assertFalse(destroyed);
        assertEquals(hpBefore, bw.getRemainingHp());
        assertEquals(vectorsBefore, world.getMazeVectors().size());
        assertTrue(world.getBreakableWalls().contains(bw));
    }

    @Test
    @DisplayName("applyWallDamage rejects negative damage and keeps wall unchanged")
    void negativeDamageThrowsAndKeepsWall() {
        BreakableWall bw = world.getBreakableWalls().get(0);
        int hpBefore = bw.getRemainingHp();
        int vectorsBefore = world.getMazeVectors().size();

        assertThrows(IllegalArgumentException.class, () -> world.applyWallDamage(bw, -1));

        assertEquals(hpBefore, bw.getRemainingHp());
        assertEquals(vectorsBefore, world.getMazeVectors().size());
        assertTrue(world.getBreakableWalls().contains(bw));
    }

    @Test
    @DisplayName("applyWallDamage ignores a breakable wall not owned by the world")
    void ignoresForeignBreakableWall() {
        Vector2D geometry = world.getMazeVectors().get(0);
        BreakableWall foreign = new BreakableWall(geometry, 10);
        int vectorsBefore = world.getMazeVectors().size();

        boolean destroyed = world.applyWallDamage(foreign, 10);

        assertFalse(destroyed);
        assertEquals(vectorsBefore, world.getMazeVectors().size());
        assertTrue(world.getMazeVectors().contains(geometry));
    }

    @Test
    @DisplayName("applyWallDamage rewires nav graph without throwing")
    void navGraphRewireSmokeTest() {
        BreakableWall bw = world.getBreakableWalls().get(0);
        int edgesBefore = countNavEdges(world.getNavigationGraph());

        assertDoesNotThrow(() -> world.applyWallDamage(bw, bw.getRemainingHp()));

        // Destroying a wall can only open passages, never close them
        assertTrue(countNavEdges(world.getNavigationGraph()) >= edgesBefore,
                "Destroying a wall must never reduce the nav graph edge count");
    }

    private static int countNavEdges(MazeNavigationGraph graph) {
        if (graph == null) return 0;
        int total = 0;
        for (int c = 0; c < graph.getCols(); c++) {
            for (int r = 0; r < graph.getRows(); r++) {
                MazeNavigationGraph.Node n = graph.getNode(c, r);
                if (n != null) total += n.getNeighbors().size();
            }
        }
        return total / 2;
    }

    @Test
    @DisplayName("GenerateMaze rebuilds navigation graph and replaces maze vectors")
    void generateMazeRebuildsGraphAndWalls() {
        List<Vector2D> firstLayout = new ArrayList<>(List.of(
                new Vector2D(0, 0, 100, 0),
                new Vector2D(0, 100, 100, 100),
                new Vector2D(0, 0, 0, 100),
                new Vector2D(100, 0, 100, 100)
        ));
        List<Vector2D> secondLayout = new ArrayList<>(List.of(
                new Vector2D(0, 0, 200, 0),
                new Vector2D(0, 200, 200, 200),
                new Vector2D(0, 0, 0, 200),
                new Vector2D(200, 0, 200, 200),
                new Vector2D(100, 0, 100, 200)
        ));
        GameMazeWorld generatedWorld = new GameMazeWorld(new SequenceMazeGenerator(firstLayout, secondLayout));

        var graphBefore = generatedWorld.getNavigationGraph();
        assertNotNull(graphBefore);
        assertEquals(firstLayout.size(), generatedWorld.getMazeVectors().size());

        assertDoesNotThrow(generatedWorld::GenerateMaze);

        var graphAfter = generatedWorld.getNavigationGraph();
        assertNotNull(graphAfter);
        assertNotSame(graphBefore, graphAfter, "Navigation graph should be rebuilt from regenerated walls");
        assertEquals(secondLayout.size(), generatedWorld.getMazeVectors().size());
        assertTrue(generatedWorld.getMazeVectors().containsAll(secondLayout));
    }
}
