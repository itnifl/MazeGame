package main.game.maze;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.game.maze.common.scoring.GameScoringConstants;
import main.game.maze.difficulties.DifficultiesFactory;
import main.game.maze.difficulties.Difficulty;

/**
 * Parity guard: the JavaFX GameController must derive its base score
 * exclusively from GameScoringConstants. Enforces GR-17 / GR-22.
 */
class GameControllerScoringParityTest {

    @Test
    void controllerBaseScoreMatchesSharedConstantsForEveryDifficulty() {
        for (Difficulty d : new Difficulty[] {
                DifficultiesFactory.eINSTANCE.createEasyDifficulty(),
                DifficultiesFactory.eINSTANCE.createNormalDifficulty(),
                DifficultiesFactory.eINSTANCE.createHardDifficulty() }) {
            GameController controller = new GameController();
            controller.setStartDifficulty(d);
            assertEquals(GameScoringConstants.baseScoreFor(d), controller.getBaseScoreForCurrentDifficulty(),
                    "JavaFX base score must equal shared constant for difficulty " + d.eClass().getName());
        }
    }

    @Test
    void controllerBaseScoreOnNullDifficultyDefaultsToEasy() {
        GameController controller = new GameController();
        controller.setStartDifficulty(null);
        assertEquals(GameScoringConstants.EASY_BASE_SCORE, controller.getBaseScoreForCurrentDifficulty());
    }
}
