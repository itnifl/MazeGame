package main.game.maze.libgdx.parity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.game.maze.common.scoring.GameScoringConstants;
import main.game.maze.difficulties.DifficultiesFactory;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.EasyDifficulty;
import main.game.maze.difficulties.HardDifficulty;
import main.game.maze.difficulties.NormalDifficulty;
import main.game.maze.game.score.ScoringEngine;
import main.game.maze.game.session.GameSession;
import main.game.maze.libgdx.helper.GdxScoreSupport;
import main.game.maze.mazeworld.constants.StageConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Cross-frontend parity contract for end-screen score calculations.
 *
 * <p>Guards GR-36 (death penalty = 5 000) and GR-37 (damage × 10 penalty
 * on GAME_OVER and WON screens). Both JavaFX and libGDX must delegate to the
 * same {@link ScoringEngine#characterScreenScore} method so that identical
 * inputs always produce identical scores.
 *
 * <p>The "JavaFX path" is represented by a direct call to
 * {@link ScoringEngine#characterScreenScore} (which is exactly what
 * {@code CharacterActionScreens.updateScore()} invokes). The libGDX path
 * is represented by {@link GdxScoreSupport#endScreenScore}.
 */
class EndScreenScoringParityTest {

    private final ScoringEngine engine = new ScoringEngine();

    // -----------------------------------------------------------------------
    // GR-36: death penalty constant is 5 000 in the shared module
    // -----------------------------------------------------------------------

    @Test
    void sharedDeathPenaltyConstantIs5000() {
        assertEquals(5000, StageConstants.ScoreDeathPenalty,
                "GR-36: StageConstants.ScoreDeathPenalty must be 5 000 — "
                + "update both frontends and this test if the rule changes");
    }

    // -----------------------------------------------------------------------
    // GR-36 + GR-37: both frontends produce the same end-screen score
    // for every combination of (dead, won, damage received)
    // -----------------------------------------------------------------------

    @ParameterizedTest(name = "base={0} moves={1} currentHp={2} hints={3} won={4}")
    @CsvSource({
        // base, moves, currentHp, hints, won
        "10000, 20,   0, 0,     false",   // dead, no hints
        "10000, 20,   0, 100,   false",   // dead with hint penalty
        "10000, 50,  50, 0,     false",   // took damage, survived
        "10000, 10, 100, 0,     false",   // survived full HP
        "10000, 10, 100, 0,     true",    // won, full HP
        "10000, 10,  80, 0,     true",    // won, took 20 damage
        "5000,   5,  60, 200,   false",   // survived with hint penalty
        "30000, 30,   0, 500,   false",   // dead, hints, hard-like base
        "30000,  0, 100, 0,     true",    // won, full HP, hard-like base
    })
    void libgdxAndJavafxProduceIdenticalEndScreenScore(
            int baseScore, int moveCount, int currentHp, float hints, boolean won) {
        int maxHp = 100;

        // JavaFX path: direct ScoringEngine call
        int dynamicPenalty = (int) Math.floor(Math.max(0f, hints));
        int javafxScore = engine.characterScreenScore(
                baseScore, moveCount, maxHp, currentHp, dynamicPenalty, won);

        // libGDX path: GdxScoreSupport wrapper
        GameSession session = new GameSession();
        session.setBaseScore(baseScore);
        for (int i = 0; i < moveCount; i++) session.incrementMoveCount();
        int libgdxScore = GdxScoreSupport.endScreenScore(engine, session, hints, maxHp, currentHp, won);

        assertEquals(javafxScore, libgdxScore,
                "GR-36/GR-37: libGDX and JavaFX must produce identical end-screen score "
                + "(base=" + baseScore + " moves=" + moveCount + " currentHp=" + currentHp
                + " hints=" + hints + " won=" + won + ")");
    }

    // -----------------------------------------------------------------------
    // GR-37: damage * 10 penalty is applied on end-screen (not on gameplay)
    // -----------------------------------------------------------------------

    @Test
    void damagePenaltyOnEndScreenIs10PerHpPoint() {
        int maxHp = 100;
        int damageReceived = 40;
        int currentHp = maxHp - damageReceived;

        int gameplayScore = engine.gameplayScore(10000, 0, 0f);
        int endScreenScore = engine.characterScreenScore(10000, 0, maxHp, currentHp, 0, false);

        int penalty = gameplayScore - endScreenScore;
        assertEquals(damageReceived * StageConstants.ScoreSubtractFactor, penalty,
                "GR-37: damage penalty must be damage * ScoreSubtractFactor (" + StageConstants.ScoreSubtractFactor + ")");
    }

    @Test
    void damagePenaltyAbsentWhenNoHpLost() {
        int gameplayScore = engine.gameplayScore(5000, 5, 0f);
        int endScreenScore = engine.characterScreenScore(5000, 5, 100, 100, 0, false);

        assertEquals(gameplayScore, endScreenScore,
                "GR-37: end-screen score must equal gameplay score when player took no damage and is alive");
    }

    // -----------------------------------------------------------------------
    // GR-36: death penalty shifts end-screen score relative to survived score
    // -----------------------------------------------------------------------

    @Test
    void deathPenaltySeparatesDeadFromSurvivedScore() {
        // Same moves and base score; one died (hp=0), one survived (hp=1).
        // died:     damagePenalty = 100*10 = 1000, deathPenalty = 5000 → total loss from base: 6000
        // survived: damagePenalty =  99*10 =  990, deathPenalty =    0 → total loss from base:  990
        // gap = (5000 + 1000) - (990) = 5010 = deathPenalty + 1*ScoreSubtractFactor
        int died     = engine.characterScreenScore(5000, 0, 100,  0, 0, false);
        int survived = engine.characterScreenScore(5000, 0, 100,  1, 0, false);

        int diff = survived - died;
        // Going from 1 HP (survived) to 0 HP (dead) adds: death penalty + 1 extra HP damage point * 10
        assertEquals(5000 + StageConstants.ScoreSubtractFactor, diff,
                "GR-36: gap between survived (1 HP) and dead (0 HP) must equal deathPenalty + ScoreSubtractFactor");
    }

    // -----------------------------------------------------------------------
    // Per-difficulty end-screen base scores agree between frontends (GR-17)
    // -----------------------------------------------------------------------

    @Test
    void endScreenScoreUsesSharedBaseScorePerDifficulty() {
        for (Difficulty d : new Difficulty[]{
                DifficultiesFactory.eINSTANCE.createEasyDifficulty(),
                DifficultiesFactory.eINSTANCE.createNormalDifficulty(),
                DifficultiesFactory.eINSTANCE.createHardDifficulty()}) {
            int sharedBase = GameScoringConstants.baseScoreFor(d);

            GameSession session = new GameSession();
            session.setBaseScore(sharedBase);

            // Full-HP survival, no moves, no hints: endScreenScore should equal sharedBase
            int libgdxEnd = GdxScoreSupport.endScreenScore(engine, session, 0f, 100, 100, false);
            int javafxEnd = engine.characterScreenScore(sharedBase, 0, 100, 100, 0, false);

            assertEquals(javafxEnd, libgdxEnd,
                    "GR-17/GR-36/GR-37: end-screen score must agree for difficulty " + d.eClass().getName());
            assertTrue(libgdxEnd > 0,
                    "End-screen score for " + d.eClass().getName() + " with no damage must be positive");
        }
    }

    // -----------------------------------------------------------------------
    // Hard difficulty end-screen with death penalty must exceed easy end-screen
    // (validates that higher base score compensates the same penalty)
    // -----------------------------------------------------------------------

    @Test
    void hardDeadScoreExceedsEasyDeadScoreDueToHigherBase() {
        Difficulty easy = DifficultiesFactory.eINSTANCE.createEasyDifficulty();
        Difficulty hard = DifficultiesFactory.eINSTANCE.createHardDifficulty();

        int easyDead = engine.characterScreenScore(GameScoringConstants.baseScoreFor(easy), 0, 100, 0, 0, false);
        int hardDead = engine.characterScreenScore(GameScoringConstants.baseScoreFor(hard), 0, 100, 0, 0, false);

        assertTrue(hardDead > easyDead,
                "Hard dead score (" + hardDead + ") must exceed Easy dead score (" + easyDead
                + ") because the hard base score is much higher");
    }
}
