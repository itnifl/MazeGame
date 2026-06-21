package main.game.maze.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import main.game.maze.difficulties.DifficultiesFactory;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.mazeworld.constants.StageConstants;
import org.junit.jupiter.api.Test;

class DifficultyPresentationSupportTest {

    @Test
    void boardSizeUsesEasyDefaultsWhenDifficultyIsNull() {
        assertEquals(StageConstants.BoardMaxX, DifficultyPresentationSupport.boardWidth(null));
        assertEquals(StageConstants.BoardMaxY, DifficultyPresentationSupport.boardHeight(null));
        assertEquals(StageConstants.BoardMaxX + "x" + StageConstants.BoardMaxY,
                DifficultyPresentationSupport.boardSizeLabel(null));
    }

    @Test
    void boardSizeMatchesNormalAndHardDifficultyConstants() {
        Difficulty normal = DifficultiesFactory.eINSTANCE.createNormalDifficulty();
        Difficulty hard = DifficultiesFactory.eINSTANCE.createHardDifficulty();

        assertEquals(StageConstants.BoardMaxXMedium, DifficultyPresentationSupport.boardWidth(normal));
        assertEquals(StageConstants.BoardMaxYMedium, DifficultyPresentationSupport.boardHeight(normal));
        assertEquals(StageConstants.BoardMaxXLarge, DifficultyPresentationSupport.boardWidth(hard));
        assertEquals(StageConstants.BoardMaxYLarge, DifficultyPresentationSupport.boardHeight(hard));
    }

    @Test
    void easyBoardHeightIs665() {
        assertEquals(665, StageConstants.BoardMaxY,
                "Easy difficulty board height must be 665 px (600 original + 65 px extended bottom)");
    }

    @Test
    void displayNameUsesDifficultyModelNameAndFallback() {
        Difficulty easy = DifficultiesFactory.eINSTANCE.createEasyDifficulty();
        Difficulty normal = DifficultiesFactory.eINSTANCE.createNormalDifficulty();

        assertEquals("", DifficultyPresentationSupport.displayName(null));
        assertEquals("Easy", DifficultyPresentationSupport.displayName(easy));
        assertEquals("Normal", DifficultyPresentationSupport.displayName(normal));
        assertEquals("Easy", DifficultyPresentationSupport.displayNameOr(null, "Easy"));
    }
}
