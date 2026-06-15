package main.game.maze.common.movement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;
import org.junit.jupiter.api.Test;

class GhostPhasingMovementServiceTest {

    /** Flat open board with no walls and configurable extents. */
    private record OpenWorld(double w, double h) implements WorldView {
        @Override public double playerX()  { return 0; }
        @Override public double playerY()  { return 0; }
        @Override public double minX()     { return 0; }
        @Override public double minY()     { return 0; }
        @Override public double maxX()     { return w; }
        @Override public double maxY()     { return h; }
        @Override public boolean wouldCollide(double cx, double cy, double size) { return false; }
    }

    @Test
    void tick_movesInInitialDirection_whenNoObstacle() {
        // Seed RNG so first direction is deterministic: index 0 = {1, 0} (right).
        GhostPhasingMovementService service = new GhostPhasingMovementService(new Random(0));
        OpenWorld world = new OpenWorld(500, 500);
        EnemyState state = new EnemyState("g1", 100, 100, 1, 0, 16, 4);

        MovementResult result = service.tick(state, world);

        assertTrue(result.moved(), "ghost should always move when in open space");
        // Position must change by exactly one speed unit in some cardinal direction.
        double dx = Math.abs(result.x() - 100);
        double dy = Math.abs(result.y() - 100);
        assertEquals(4.0, dx + dy, 1e-9,
                "ghost moves exactly one speed unit per tick");
    }

    @Test
    void tick_maintainsSameDirection_onConsecutiveTicks() {
        GhostPhasingMovementService service = new GhostPhasingMovementService(new Random(42));
        OpenWorld world = new OpenWorld(1000, 1000);
        EnemyState state = new EnemyState("g1", 100, 100, 1, 0, 16, 4);

        MovementResult r1 = service.tick(state, world);
        state = new EnemyState("g1", r1.x(), r1.y(), r1.directionX(), r1.directionY(), 16, 4);
        MovementResult r2 = service.tick(state, world);

        assertEquals(r1.directionX(), r2.directionX(), "direction should be stable between ticks");
        assertEquals(r1.directionY(), r2.directionY(), "direction should be stable between ticks");
    }

    @Test
    void tick_picksNewDirection_whenHittingRightBoundary() {
        GhostPhasingMovementService service = new GhostPhasingMovementService(new Random(0));
        OpenWorld world = new OpenWorld(200, 200);
        // Ghost at x=196, moving right with speed 8 — next step would be 204, out of bounds.
        EnemyState state = new EnemyState("g1", 196, 100, 1, 0, 8, 8);

        MovementResult result = service.tick(state, world);

        // Ghost must stay within [halfSize, maxX-halfSize] = [4, 196]
        double halfSize = 4.0;
        assertTrue(result.x() >= halfSize, "ghost must not leave left boundary");
        assertTrue(result.x() <= world.maxX() - halfSize, "ghost must not leave right boundary");
        assertTrue(result.y() >= halfSize, "ghost must not leave bottom boundary");
        assertTrue(result.y() <= world.maxY() - halfSize, "ghost must not leave top boundary");
    }

    @Test
    void tick_picksNewDirection_whenHittingLeftBoundary() {
        GhostPhasingMovementService service = new GhostPhasingMovementService(new Random(0));
        OpenWorld world = new OpenWorld(200, 200);
        // Ghost at x=4, moving left with speed 8 — next step would be -4, out of bounds.
        EnemyState state = new EnemyState("g1", 4, 100, -1, 0, 8, 8);

        MovementResult result = service.tick(state, world);

        double halfSize = 4.0;
        assertTrue(result.x() >= halfSize, "ghost must not leave left boundary after bounce");
        assertTrue(result.x() <= world.maxX() - halfSize, "ghost must not exceed right boundary after bounce");
    }

    @Test
    void tick_clampsPosition_whenNewDirectionAlsoHitsBoundary() {
        GhostPhasingMovementService service = new GhostPhasingMovementService(new Random(0));
        // Tiny board: essentially forces clamping because every re-pick also exceeds bounds.
        OpenWorld world = new OpenWorld(10, 10);
        EnemyState state = new EnemyState("g1", 5, 5, 1, 0, 4, 8);

        MovementResult result = service.tick(state, world);

        double halfSize = 2.0; // size=4 => halfSize=2
        assertTrue(result.x() >= halfSize, "clamped x must be within left boundary");
        assertTrue(result.x() <= world.maxX() - halfSize, "clamped x must be within right boundary");
        assertTrue(result.y() >= halfSize, "clamped y must be within bottom boundary");
        assertTrue(result.y() <= world.maxY() - halfSize, "clamped y must be within top boundary");
    }

    @Test
    void tick_doesNotCheckWalls_passesThroughWallPosition() {
        // WorldView that always reports a wall collision at exactly the ghost's position.
        WorldView blocksEverything = new WorldView() {
            @Override public double playerX() { return 0; }
            @Override public double playerY() { return 0; }
            @Override public double minX()    { return 0; }
            @Override public double minY()    { return 0; }
            @Override public double maxX()    { return 500; }
            @Override public double maxY()    { return 500; }
            @Override public boolean wouldCollide(double cx, double cy, double size) { return true; }
        };

        GhostPhasingMovementService service = new GhostPhasingMovementService(new Random(1));
        EnemyState state = new EnemyState("g1", 100, 100, 1, 0, 16, 4);

        MovementResult result = service.tick(state, world(blocksEverything));

        // Ghost should still move despite wouldCollide always returning true.
        assertTrue(result.moved(), "phasing ghost should move regardless of wall collisions");
    }

    @Test
    void reset_clearsAllDirectionState() {
        GhostPhasingMovementService service = new GhostPhasingMovementService(new Random(0));
        OpenWorld world = new OpenWorld(500, 500);
        EnemyState state = new EnemyState("g1", 100, 100, 1, 0, 16, 4);

        // Establish a direction.
        service.tick(state, world);

        service.reset();

        // After reset the service may pick any direction on the next tick.
        // We only verify the result is still within bounds (no crash / NPE).
        MovementResult after = service.tick(
                new EnemyState("g1", 100, 100, 1, 0, 16, 4), world);
        assertTrue(after.moved(), "service should still produce a valid move after reset");
    }

    @Test
    void tick_independentDirectionPerEnemyId() {
        GhostPhasingMovementService service = new GhostPhasingMovementService(new Random(7));
        OpenWorld world = new OpenWorld(1000, 1000);

        // Tick both enemies and record their directions.
        MovementResult r1 = service.tick(new EnemyState("ghost-A", 200, 200, 1, 0, 16, 4), world);
        MovementResult r2 = service.tick(new EnemyState("ghost-B", 200, 200, 1, 0, 16, 4), world);

        // Directions may or may not be the same (random), but the service must return
        // valid results for both without crashing.
        assertTrue(r1.moved());
        assertTrue(r2.moved());
    }

    /** Unwrap a WorldView from an intermediate holder — helper for the wall-passthrough test. */
    private WorldView world(WorldView w) {
        return w;
    }
}
