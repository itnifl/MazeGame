package main.game.maze;

import main.game.maze.difficulties.DifficultiesFactory;
import main.game.maze.actions.base.CharacterActionScreens;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameControllerDifficultyBaseScoreTest {

    @Test
    void easyDifficultyUses10000BaseScore() {
        GameController gc = new GameController();
        assertEquals(10000, gc.getBaseScoreForCurrentDifficulty());
    }

    @Test
    void normalDifficultyUses20000BaseScore() {
        GameController gc = new GameController();
        gc.setStartDifficulty(DifficultiesFactory.eINSTANCE.createNormalDifficulty());
        assertEquals(20000, gc.getBaseScoreForCurrentDifficulty());
    }

    @Test
    void hardDifficultyUses30000BaseScore() {
        GameController gc = new GameController();
        gc.setStartDifficulty(DifficultiesFactory.eINSTANCE.createHardDifficulty());
        assertEquals(30000, gc.getBaseScoreForCurrentDifficulty());
    }

    @Test
    void resetScoreUsesConfiguredBaseScore() {
        CharacterActionScreens scoring = new CharacterActionScreens();
        scoring.setBaseScore(20000);

        assertEquals(20000, scoring.resetScore());
    }
}
