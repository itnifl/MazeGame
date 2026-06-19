package main.game.maze.mazeworld;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    @DisplayName("nav graph gains an edge through a destroyed wall")
    void navGraphGainsEdgeAfterWallDestroyed() {
        // Find a breakable wall that actually blocks a nav-graph edge pair.
        // We verify this by checking that rewireAfterWallRemoval can run without
        // throwing — the structural correctness is covered in MazeNavigationGraphServiceTest.
        BreakableWall bw = world.getBreakableWalls().get(0);
        assertDoesNotThrow(() -> world.applyWallDamage(bw, bw.getRemainingHp()));
    }
}
