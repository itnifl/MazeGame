package main.game.maze.libgdx.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import main.game.maze.game.score.PathHintBudget;
import main.game.maze.game.session.GameMode;
import org.junit.jupiter.api.Test;

class GdxGameRuntimeSupportTest {

    @Test
    void applyPathPenaltyNoopOutsidePlayingMode() {
        PathHintBudget budget = new PathHintBudget(5f);

        var result = GdxGameRuntimeSupport.applyPathPenalty(
                GameMode.WON,
                true,
                budget,
                0f,
                1f,
                50f);

        assertTrue(result.showHintInfo());
        assertEquals(0f, result.pathPenaltyPoints(), 0.001f);
        assertFalse(result.exhaustedNotified());
    }

    @Test
    void applyPathPenaltyConsumesBudgetAndAddsPenalty() {
        PathHintBudget budget = new PathHintBudget(5f);

        var result = GdxGameRuntimeSupport.applyPathPenalty(
                GameMode.PLAYING,
                true,
                budget,
                10f,
                1f,
                20f);

        assertTrue(result.showHintInfo());
        assertEquals(30f, result.pathPenaltyPoints(), 0.001f);
        assertFalse(result.exhaustedNotified());
    }

    @Test
    void applyPathPenaltyTurnsHintOffWhenAlreadyExhausted() {
        PathHintBudget budget = new PathHintBudget(0f);

        var result = GdxGameRuntimeSupport.applyPathPenalty(
                GameMode.PLAYING,
                true,
                budget,
                42f,
                1f,
                20f);

        assertFalse(result.showHintInfo());
        assertEquals(42f, result.pathPenaltyPoints(), 0.001f);
        assertTrue(result.exhaustedNotified());
    }

    @Test
    void currentEnemyContactsReturnsEmptyWhenNoEnemies() {
        assertEquals(List.of(), GdxGameRuntimeSupport.currentEnemyContacts(List.of()));
    }
}
