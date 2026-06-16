package main.game.maze.common.movement;

import main.game.maze.testutil.FakeWorldView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChasePlayerMovementServiceTest {

    private final ChasePlayerMovementService service = new ChasePlayerMovementService();

    // Enemy starts at (100, 100), player at (200, 100): enemy should move right.
    @Test
    void tick_approachesPlayerOnOpenBoard_horizontally() {
        FakeWorldView world = new FakeWorldView().playerAt(200, 100);
        EnemyState enemy = new EnemyState("e1", 100, 100, 0, 0, 16, 4);

        MovementResult result = service.tick(enemy, world);

        assertTrue(result.moved(), "enemy must advance toward player");
        assertTrue(result.x() > 100, "enemy x must increase when player is to the right");
        assertEquals(100.0, result.y(), 1e-9, "enemy y must not change on a horizontal chase");
        assertEquals(1, result.directionX(), "direction must be +x");
        assertEquals(0, result.directionY());
    }

    // Enemy at (100, 100), player at (100, 200): enemy should move down.
    @Test
    void tick_approachesPlayerOnOpenBoard_vertically() {
        FakeWorldView world = new FakeWorldView().playerAt(100, 200);
        EnemyState enemy = new EnemyState("e1", 100, 100, 0, 0, 16, 4);

        MovementResult result = service.tick(enemy, world);

        assertTrue(result.moved());
        assertEquals(100.0, result.x(), 1e-9);
        assertTrue(result.y() > 100);
        assertEquals(0, result.directionX());
        assertEquals(1, result.directionY());
    }

    // Enemy at player's position: desired direction is (0,0), fallback must still pick a step.
    @Test
    void tick_atPlayerPosition_stillMovesViaFallback() {
        FakeWorldView world = new FakeWorldView().playerAt(100, 100);
        EnemyState enemy = new EnemyState("e1", 100, 100, 0, 0, 16, 4);

        MovementResult result = assertDoesNotThrow(() -> service.tick(enemy, world));
        assertTrue(result.moved() || result.directionX() != 0 || result.directionY() != 0,
                "fallback should move or provide a non-zero heading");
    }

    // When the direct path is walled, the enemy tries the perpendicular axis.
    @Test
    void tick_usesAltAxisWhenDirectPathBlocked() {
        // Player is to the right (dx > 0), but right wall is blocked.
        // Enemy at (100, 100), player at (200, 100).
        // Block the step to (104, 100).
        FakeWorldView world = new FakeWorldView()
                .playerAt(200, 100)
                .blockAt(104, 100);
        EnemyState enemy = new EnemyState("e1", 100, 100, 0, 0, 8, 4);

        MovementResult result = service.tick(enemy, world);

        // Should still move (via alt axis or fallback), not stand still.
        assertTrue(result.moved(), "enemy must find an alternative direction when direct path is blocked");
        // The blocked direct-right step must NOT have been taken.
        assertFalse(result.x() > 100 && result.y() == 100.0,
                "enemy should not take the blocked direct-right step");
    }

    // When all cardinal directions are blocked, enemy stands still but reports heading.
    @Test
    void tick_allDirectionsBlocked_doesNotMove() {
        FakeWorldView world = new FakeWorldView().playerAt(200, 100).allBlocked();
        EnemyState enemy = new EnemyState("e1", 100, 100, 0, 0, 16, 4);

        MovementResult result = service.tick(enemy, world);

        assertFalse(result.moved(), "enemy must not move when every direction is blocked");
        assertEquals(100.0, result.x(), 1e-9, "x must not change when blocked");
        assertEquals(100.0, result.y(), 1e-9, "y must not change when blocked");
        // Direction must still reflect the desired heading toward the player.
        assertEquals(1, result.directionX(), "desired direction must still be toward player");
    }

    // Two consecutive ticks on a clear board: direction is stable (no oscillation).
    @Test
    void tick_directionIsStableOnSymmetricBoard() {
        FakeWorldView world = new FakeWorldView().playerAt(200, 100);
        EnemyState enemy = new EnemyState("e1", 100, 100, 0, 0, 16, 4);

        MovementResult r1 = service.tick(enemy, world);
        EnemyState next = new EnemyState("e1", r1.x(), r1.y(), r1.directionX(), r1.directionY(), 16, 4);
        MovementResult r2 = service.tick(next, world);

        assertEquals(r1.directionX(), r2.directionX(), "x-direction must be stable");
        assertEquals(r1.directionY(), r2.directionY(), "y-direction must be stable");
    }
}
