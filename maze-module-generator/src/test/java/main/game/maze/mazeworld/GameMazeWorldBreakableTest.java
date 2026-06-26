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
        BreakableWall foreign = new BreakableWall(geometry, new WallMaterialSpec("DIRT_BASIC", "Dirt", 10));
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

    // -----------------------------------------------------------------------
    // Cascade wall collapse
    // -----------------------------------------------------------------------

    /** Registers specific walls as breakable, bypassing the RNG in assignBreakableWalls. */
    private static void makeBreakable(GameMazeWorld world, WallMaterialSpec spec, Vector2D... geoms) {
        world.getBreakableWalls().clear();
        for (Vector2D g : geoms) {
            world.getBreakableWalls().add(new BreakableWall(g,
                    new WallMaterialSpec(spec.id(), spec.displayName(), spec.hitPoints())));
        }
    }

    @Test
    @DisplayName("applyWallDamage cascades and collapses adjacent breakable wall")
    void applyWallDamage_cascade_destroysAdjacentBreakableWall() {
        // A (vertical) and B (horizontal) share endpoint (100, 200).
        Vector2D geomA = new Vector2D(100, 100, 100, 200);
        Vector2D geomB = new Vector2D(100, 200, 200, 200);
        GameMazeWorld cascadeWorld = new GameMazeWorld(() -> List.of(geomA, geomB));
        WallMaterialSpec glass = new WallMaterialSpec("GLASS_BASIC", "Glass", 5);
        makeBreakable(cascadeWorld, glass, geomA, geomB);

        assertEquals(2, cascadeWorld.getMazeVectors().size());
        BreakableWall bwA = cascadeWorld.findBreakableWall(geomA);
        assertNotNull(bwA);

        boolean destroyed = cascadeWorld.applyWallDamage(bwA, 5);

        assertTrue(destroyed, "Primary wall must be reported as destroyed");
        assertEquals(0, cascadeWorld.getMazeVectors().size(),
                "Adjacent wall B must cascade-collapse when A is destroyed");
        assertNull(cascadeWorld.findBreakableWall(geomB),
                "Wall B must no longer be registered as breakable");
    }

    @Test
    @DisplayName("applyWallDamage cascade does not collapse indestructible (non-breakable) adjacent wall")
    void applyWallDamage_cascade_doesNotCollapseIndestructibleAdjacentWall() {
        // A is breakable; B shares an endpoint but is a plain (indestructible) wall.
        Vector2D geomA = new Vector2D(100, 100, 100, 200);
        Vector2D geomB = new Vector2D(100, 200, 200, 200);
        GameMazeWorld cascadeWorld = new GameMazeWorld(() -> List.of(geomA, geomB));
        // Only mark geomA as breakable; geomB stays indestructible.
        WallMaterialSpec glass = new WallMaterialSpec("GLASS_BASIC", "Glass", 5);
        makeBreakable(cascadeWorld, glass, geomA);

        BreakableWall bwA = cascadeWorld.findBreakableWall(geomA);
        assertNotNull(bwA);

        boolean destroyed = cascadeWorld.applyWallDamage(bwA, 5);

        assertTrue(destroyed);
        // geomB should still be in the world (it is indestructible)
        assertTrue(cascadeWorld.getMazeVectors().contains(geomB),
                "Indestructible adjacent wall must NOT collapse in the cascade");
        assertEquals(1, cascadeWorld.getMazeVectors().size());
    }

    @Test
    @DisplayName("applyWallDamage cascade propagates through a chain A-B-C")
    void applyWallDamage_cascade_propagatesThroughChain() {
        // Chain: A=(0,0,0,100), B=(0,100,100,100), C=(100,100,100,200)
        // A shares endpoint (0,100) with B; B shares endpoint (100,100) with C.
        Vector2D geomA = new Vector2D(0,   0,   0, 100);
        Vector2D geomB = new Vector2D(0, 100, 100, 100);
        Vector2D geomC = new Vector2D(100, 100, 100, 200);
        GameMazeWorld cascadeWorld = new GameMazeWorld(() -> List.of(geomA, geomB, geomC));
        WallMaterialSpec glass = new WallMaterialSpec("GLASS_BASIC", "Glass", 5);
        makeBreakable(cascadeWorld, glass, geomA, geomB, geomC);

        assertEquals(3, cascadeWorld.getMazeVectors().size());
        BreakableWall bwA = cascadeWorld.findBreakableWall(geomA);
        assertNotNull(bwA);

        boolean destroyed = cascadeWorld.applyWallDamage(bwA, 5);

        assertTrue(destroyed);
        assertEquals(0, cascadeWorld.getMazeVectors().size(),
                "Cascade must propagate through the full chain A-B-C");
    }

    @Test
    @DisplayName("applyWallDamage on an isolated wall causes no extra cascade")
    void applyWallDamage_noAdjacentBreakables_noExtraCascade() {
        // Only one breakable wall, far from any other; destroying it must leave others intact.
        Vector2D isolated   = new Vector2D(0,   0,   0, 100);
        Vector2D unrelated1 = new Vector2D(500, 500, 500, 600);
        Vector2D unrelated2 = new Vector2D(500, 600, 600, 600);
        GameMazeWorld cascadeWorld = new GameMazeWorld(
                () -> List.of(isolated, unrelated1, unrelated2));
        WallMaterialSpec glass = new WallMaterialSpec("GLASS_BASIC", "Glass", 5);
        makeBreakable(cascadeWorld, glass, isolated, unrelated1, unrelated2);

        BreakableWall bwIsolated = cascadeWorld.findBreakableWall(isolated);
        assertNotNull(bwIsolated);

        boolean destroyed = cascadeWorld.applyWallDamage(bwIsolated, 5);

        assertTrue(destroyed);
        assertEquals(2, cascadeWorld.getMazeVectors().size(),
                "Only the isolated wall must be removed; unrelated walls must survive");
        assertTrue(cascadeWorld.getMazeVectors().contains(unrelated1));
        assertTrue(cascadeWorld.getMazeVectors().contains(unrelated2));
    }
}

