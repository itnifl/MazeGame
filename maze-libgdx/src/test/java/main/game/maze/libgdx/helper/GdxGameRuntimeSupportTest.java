package main.game.maze.libgdx.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import main.game.maze.game.score.PathHintBudget;
import main.game.maze.game.score.ScoringEngine;
import main.game.maze.game.session.GameMode;
import main.game.maze.game.session.GameSession;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.mazeworld.constants.StageConstants;
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

    // -------------------------------------------------------------------------
    // endScreenScore (GR-36: death penalty 5 000, GR-37: damage * 10)
    // -------------------------------------------------------------------------

    @Test
    void endScreenScoreAppliesDeathPenaltyOf5000WhenDead() {
        ScoringEngine engine = new ScoringEngine();
        GameSession session = new GameSession();
        session.setBaseScore(5000);
        GameWorldModel worldModel = new GameWorldModel();

        // currentHp=0 triggers the death penalty; no move penalty, no hint penalty
        int score = GdxGameRuntimeSupport.endScreenScore(engine, session, worldModel, 100, 0, false);

        // 5000 - 0 (moves) - 1000 (100 HP damage * 10) - 5000 (death) - 0 (hints) = -1000
        assertEquals(5000 - 1000 - StageConstants.ScoreDeathPenalty, score,
                "GR-36: endScreenScore must subtract 5 000 death penalty when currentHp=0");
    }

    @Test
    void endScreenScoreAppliesDamageTimesTenWhenDamaged() {
        ScoringEngine engine = new ScoringEngine();
        GameSession session = new GameSession();
        session.setBaseScore(10000);
        GameWorldModel worldModel = new GameWorldModel();

        // Took 30 damage (currentHp=70); player survived (not dead, not won)
        int score = GdxGameRuntimeSupport.endScreenScore(engine, session, worldModel, 100, 70, false);

        int expectedDamagePenalty = 30 * StageConstants.ScoreSubtractFactor;
        int expected = 10000 - expectedDamagePenalty;
        assertEquals(expected, score,
                "GR-37: endScreenScore must subtract damage * 10 when player took damage");
    }

    @Test
    void endScreenScoreAddsWinBonusWhenWon() {
        ScoringEngine engine = new ScoringEngine();
        GameSession session = new GameSession();
        session.setBaseScore(5000);
        GameWorldModel worldModel = new GameWorldModel();

        // Full HP win: no damage, no death, win bonus applies
        int score = GdxGameRuntimeSupport.endScreenScore(engine, session, worldModel, 100, 100, true);

        int expected = 5000 + StageConstants.ScoreWinBonus;
        assertEquals(expected, score,
                "Win bonus must be added to end-screen score when won=true");
    }

    @Test
    void endScreenScoreDiffersFromGameplayScoreByDeathPenaltyWhenDead() {
        ScoringEngine engine = new ScoringEngine();
        GameSession session = new GameSession();
        session.setBaseScore(5000);
        GameWorldModel worldModel = new GameWorldModel();

        int gameplayScore = GdxGameRuntimeSupport.currentScore(engine, session, worldModel);
        int endScore = GdxGameRuntimeSupport.endScreenScore(engine, session, worldModel, 100, 0, false);

        // endScore is lower by at least the death penalty plus the damage penalty
        assertTrue(endScore < gameplayScore,
                "GR-36: end-screen score for a dead player must be lower than the gameplay score");
        assertNotEquals(gameplayScore, endScore,
                "GR-36 / GR-37: end-screen score must differ from live gameplay score after death");
    }
}
