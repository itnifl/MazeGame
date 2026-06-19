package main.game.maze.game.score;

import main.game.maze.mazeworld.constants.StageConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoringEngineTest {

    private final ScoringEngine engine = new ScoringEngine();

    // -------------------------------------------------------------------------
    // gameplayScore
    // -------------------------------------------------------------------------

    @Test
    void gameplayScoreUsesMoveAndHintPenalties() {
        int score = engine.gameplayScore(5000, 12, 35.8f);

        assertEquals(4845, score);
    }

    @Test
    void gameplayScoreIsNeverNegative() {
        // Move count so large that penalties exceed the base score.
        int score = engine.gameplayScore(100, 9999, 0f);

        assertEquals(0, score);
    }

    // -------------------------------------------------------------------------
    // characterScreenScore – won path
    // -------------------------------------------------------------------------

    @Test
    void characterScreenScoreAppliesWinBonusWhenWon() {
        // No damage, no death: only move penalty and win bonus.
        // baseScore=5000, moveCount=10 → movePenalty=100, winBonus=4000
        int score = engine.characterScreenScore(5000, 10, 100, 100, 0, true);

        assertEquals(8900, score);
    }

    @Test
    void characterScreenScoreAppliesDamageTimesTenOnWin() {
        // Took 10 HP of damage (currentHP=90), won the game.
        // damagePenalty = (100-90)*10 = 100
        int won = engine.characterScreenScore(5000, 10, 100, 90, 20, true);

        assertEquals(8780, won);
    }

    // -------------------------------------------------------------------------
    // characterScreenScore – death path  (GR-36: death penalty = 5 000)
    // -------------------------------------------------------------------------

    @Test
    void deathPenaltyConstantIs5000() {
        assertEquals(5000, StageConstants.ScoreDeathPenalty,
                "GR-36: ScoreDeathPenalty must be 5 000 — update this test if the rule changes");
    }

    @Test
    void characterScreenScoreAppliesDeathPenaltyOf5000WhenDead() {
        // currentHp=0 triggers death penalty of 5000.
        // baseScore=5000, moveCount=10 → movePenalty=100
        // hpReduction=100 → damagePenalty=1000, deathPenalty=5000, dynamic=20
        // total = 5000 - 100 - 1000 - 5000 - 20 = -1120
        int deadScore = engine.characterScreenScore(5000, 10, 100, 0, 20, false);

        assertEquals(-1120, deadScore,
                "GR-36: death penalty must be 5 000");
    }

    @Test
    void characterScreenScoreDeathPenaltyAbsentWhenAlive() {
        // currentHp=50 (took 50 damage), did not die, did not win.
        // damagePenalty = 50 * 10 = 500
        int score = engine.characterScreenScore(5000, 5, 100, 50, 0, false);
        int withoutDeathPenalty = 5000 - 50 - 500;

        assertEquals(withoutDeathPenalty, score,
                "GR-37: damage penalty applies but death penalty must be absent when HP > 0");
    }

    // -------------------------------------------------------------------------
    // characterScreenScore – damage * 10 rule (GR-37)
    // -------------------------------------------------------------------------

    @Test
    void damagePenaltyIsExactlyDamageTimes10() {
        int maxHp = 100;
        int damageReceived = 30;
        int currentHp = maxHp - damageReceived;

        // gameplayScore has no HP penalty; characterScreenScore adds damage * 10
        int gameplay = engine.gameplayScore(10000, 0, 0f);
        int endScreen = engine.characterScreenScore(10000, 0, maxHp, currentHp, 0, false);

        int damagePenalty = gameplay - endScreen;
        assertEquals(damageReceived * StageConstants.ScoreSubtractFactor, damagePenalty,
                "GR-37: damage penalty on end screen must equal damage * ScoreSubtractFactor (damage * 10)");
    }

    @Test
    void noDamageNoPenaltyDifference() {
        // Player took 0 damage and is alive → characterScreenScore equals gameplayScore modulo win bonus
        int gameplay = engine.gameplayScore(5000, 5, 0f);
        int endScreen = engine.characterScreenScore(5000, 5, 100, 100, 0, false);

        assertEquals(gameplay, endScreen,
                "GR-37: with 0 damage and no death, characterScreenScore must equal gameplayScore");
    }
}
