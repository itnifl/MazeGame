package main.game.maze;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link FxGameWorldModel}, the gameplay scoring / path-hint
 * state container extracted from {@code GameController} (Phase 1 of the MVC
 * decomposition). Verifies default values, accessor round-trips, the shared
 * move-count instance, and the per-game reset semantics.
 */
class FxGameWorldModelTest {

    @Test
    void defaultsAreZeroAndOverlaysHidden() {
        FxGameWorldModel model = new FxGameWorldModel();

        assertEquals(0, model.playerMoveCount().get());
        assertEquals(0L, model.pathHintTotalUsedNanos());
        assertEquals(0L, model.pathHintPressStartNanos());
        assertFalse(model.pathHintKeyDown());
        assertFalse(model.isRouteHintVisible());
        assertEquals(0L, model.lastRouteHintPenaltyNanos());
        assertEquals(0.0, model.routeHintPenaltyAccumulator());
        assertEquals(0, model.routeHintPenaltyPoints());
        assertFalse(model.enemyPathOverlayVisible());
        assertEquals(0L, model.enemyPathOverlayHideAtNanos());
    }

    @Test
    void accessorsRoundTripValues() {
        FxGameWorldModel model = new FxGameWorldModel();

        model.setPathHintTotalUsedNanos(123L);
        model.setPathHintPressStartNanos(456L);
        model.setPathHintKeyDown(true);
        model.setRouteHintVisible(true);
        model.setLastRouteHintPenaltyNanos(789L);
        model.setRouteHintPenaltyAccumulator(2.5);
        model.setRouteHintPenaltyPoints(7);
        model.setEnemyPathOverlayVisible(true);
        model.setEnemyPathOverlayHideAtNanos(1_000L);

        assertEquals(123L, model.pathHintTotalUsedNanos());
        assertEquals(456L, model.pathHintPressStartNanos());
        assertTrue(model.pathHintKeyDown());
        assertTrue(model.isRouteHintVisible());
        assertEquals(789L, model.lastRouteHintPenaltyNanos());
        assertEquals(2.5, model.routeHintPenaltyAccumulator());
        assertEquals(7, model.routeHintPenaltyPoints());
        assertTrue(model.enemyPathOverlayVisible());
        assertEquals(1_000L, model.enemyPathOverlayHideAtNanos());
    }

    @Test
    void playerMoveCountReturnsTheSameSharedInstance() {
        FxGameWorldModel model = new FxGameWorldModel();

        assertSame(model.playerMoveCount(), model.playerMoveCount(),
                "playerMoveCount must expose the same AtomicInteger so score actions share it");

        model.playerMoveCount().getAndIncrement();
        assertEquals(1, model.playerMoveCount().get());
    }

    @Test
    void resetScoringStateClearsPenaltyAndPathHintButKeepsMoveCount() {
        FxGameWorldModel model = new FxGameWorldModel();

        model.setRouteHintPenaltyPoints(9);
        model.setRouteHintPenaltyAccumulator(3.3);
        model.setRouteHintVisible(true);
        model.setLastRouteHintPenaltyNanos(42L);
        model.setPathHintTotalUsedNanos(99L);
        model.setPathHintKeyDown(true);
        model.playerMoveCount().getAndIncrement();

        model.resetScoringState();

        assertEquals(0, model.routeHintPenaltyPoints());
        assertEquals(0.0, model.routeHintPenaltyAccumulator());
        assertFalse(model.isRouteHintVisible());
        assertEquals(0L, model.lastRouteHintPenaltyNanos());
        assertEquals(0L, model.pathHintTotalUsedNanos());
        assertFalse(model.pathHintKeyDown());
        assertEquals(1, model.playerMoveCount().get(),
                "resetScoringState must not reset the player move count");
    }
}
