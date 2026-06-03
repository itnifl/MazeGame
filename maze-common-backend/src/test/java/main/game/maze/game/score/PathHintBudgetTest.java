package main.game.maze.game.score;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PathHintBudgetTest {

    @Test
    void consumeStopsAtBudgetAndMarksExhausted() {
        PathHintBudget budget = new PathHintBudget(2.5f);

        float first = budget.consume(1f);
        float second = budget.consume(5f);

        assertEquals(1f, first, 0.0001f);
        assertEquals(1.5f, second, 0.0001f);
        assertEquals(0f, budget.remainingSeconds(), 0.0001f);
        assertEquals(2.5f, budget.usedSeconds(), 0.0001f);
        assertTrue(budget.exhausted());
    }

    @Test
    void exhaustedBudgetConsumesNothing() {
        PathHintBudget budget = new PathHintBudget(1f);
        budget.consume(1f);

        float consumed = budget.consume(0.5f);

        assertEquals(0f, consumed, 0.0001f);
        assertEquals(1f, budget.usedSeconds(), 0.0001f);
    }
}
