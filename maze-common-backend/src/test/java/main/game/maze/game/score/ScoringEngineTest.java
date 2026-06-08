package main.game.maze.game.score;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoringEngineTest {

    private final ScoringEngine engine = new ScoringEngine();

    @Test
    void gameplayScoreUsesMoveAndHintPenalties() {
        int score = engine.gameplayScore(5000, 12, 35.8f);

        assertEquals(4845, score);
    }

    @Test
    void characterScreenScoreAppliesDeathAndWinRules() {
        int wonScore = engine.characterScreenScore(5000, 10, 100, 90, 20, true);
        int deadScore = engine.characterScreenScore(5000, 10, 100, 0, 20, false);

        assertEquals(8780, wonScore);
        assertEquals(-120, deadScore);
    }
}
