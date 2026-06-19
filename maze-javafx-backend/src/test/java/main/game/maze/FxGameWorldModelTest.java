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
    void beginPathHint_setsRouteHintVisibleAndKeyDownAndTimestamp() {
        FxGameWorldModel model = new FxGameWorldModel();

        model.beginPathHint();

        assertTrue(model.isRouteHintVisible(), "beginPathHint must set isRouteHintVisible = true");
        assertTrue(model.pathHintKeyDown(), "beginPathHint must set pathHintKeyDown = true");
        assertTrue(model.pathHintPressStartNanos() > 0L,
                "beginPathHint must record a positive press-start timestamp");
        assertTrue(model.lastRouteHintPenaltyNanos() > 0L,
                "beginPathHint must record a positive penalty reference timestamp");
    }

    @Test
    void beginPathHint_idempotentWhenKeyAlreadyDown() {
        FxGameWorldModel model = new FxGameWorldModel();
        model.beginPathHint();
        long firstStart = model.pathHintPressStartNanos();

        // Second call while key is already down must not reset the press-start timestamp.
        model.beginPathHint();

        assertEquals(firstStart, model.pathHintPressStartNanos(),
                "second beginPathHint call must not reset pathHintPressStartNanos");
    }

    @Test
    void endPathHint_clearsKeyDownAndHidesOverlay() {
        FxGameWorldModel model = new FxGameWorldModel();
        model.beginPathHint();

        model.endPathHint(10_000_000_000L); // 10-second budget

        assertFalse(model.pathHintKeyDown(), "endPathHint must clear pathHintKeyDown");
        assertFalse(model.isRouteHintVisible(), "endPathHint must hide the route hint overlay");
        assertTrue(model.pathHintTotalUsedNanos() > 0L,
                "endPathHint must accumulate held nanoseconds into pathHintTotalUsedNanos");
    }

    @Test
    void endPathHint_cappedByBudget() {
        FxGameWorldModel model = new FxGameWorldModel();
        // Simulate a press that started 1000 seconds ago so the held duration exceeds the budget.
        model.setPathHintKeyDown(true);
        model.setPathHintPressStartNanos(System.nanoTime() - 1_000_000_000_000L);
        long budgetNanos = 5_000_000_000L; // 5-second cap

        model.endPathHint(budgetNanos);

        assertEquals(budgetNanos, model.pathHintTotalUsedNanos(),
                "endPathHint must cap pathHintTotalUsedNanos at budgetNanos");
    }

    @Test
    void endPathHint_whenNotDown_onlyHidesOverlay() {
        FxGameWorldModel model = new FxGameWorldModel();
        model.setRouteHintVisible(true); // overlay was visible for some other reason

        model.endPathHint(Long.MAX_VALUE); // key was never pressed

        assertFalse(model.isRouteHintVisible(),
                "endPathHint must hide the overlay even when pathHintKeyDown was false");
        assertEquals(0L, model.pathHintTotalUsedNanos(),
                "endPathHint must not accumulate nanos when key was not down");
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
