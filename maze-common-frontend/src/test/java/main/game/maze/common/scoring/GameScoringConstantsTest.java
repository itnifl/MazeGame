package main.game.maze.common.scoring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.game.maze.difficulties.DifficultiesFactory;
import main.game.maze.difficulties.Difficulty;

class GameScoringConstantsTest {

    @Test
    void baseScoreIsFiveThousandTenThousandThirtyThousand() {
        assertEquals(5000, GameScoringConstants.EASY_BASE_SCORE);
        assertEquals(10000, GameScoringConstants.NORMAL_BASE_SCORE);
        assertEquals(30000, GameScoringConstants.HARD_BASE_SCORE);
    }

    @Test
    void baseScoreForKnownDifficultiesMatchesConstants() {
        Difficulty easy = DifficultiesFactory.eINSTANCE.createEasyDifficulty();
        Difficulty normal = DifficultiesFactory.eINSTANCE.createNormalDifficulty();
        Difficulty hard = DifficultiesFactory.eINSTANCE.createHardDifficulty();

        assertEquals(GameScoringConstants.EASY_BASE_SCORE, GameScoringConstants.baseScoreFor(easy));
        assertEquals(GameScoringConstants.NORMAL_BASE_SCORE, GameScoringConstants.baseScoreFor(normal));
        assertEquals(GameScoringConstants.HARD_BASE_SCORE, GameScoringConstants.baseScoreFor(hard));
    }

    @Test
    void nullOrUnknownDifficultyFallsBackToEasy() {
        assertEquals(GameScoringConstants.EASY_BASE_SCORE, GameScoringConstants.baseScoreFor(null));
    }
}
