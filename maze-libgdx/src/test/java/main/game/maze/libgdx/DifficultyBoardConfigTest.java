package main.game.maze.libgdx;

import main.game.maze.libgdx.adapter.*;
import main.game.maze.libgdx.controller.*;
import main.game.maze.libgdx.helper.*;
import main.game.maze.libgdx.service.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import main.game.maze.difficulties.DifficultiesFactory;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.mazeworld.constants.StageConstants;
import org.junit.jupiter.api.Test;

class DifficultyBoardConfigTest {

    @Test
    void boardSizeUsesEasyDefaultsWhenDifficultyIsNull() {
        assertEquals(StageConstants.BoardMaxX, DifficultyBoardConfig.boardWidth(null));
        assertEquals(StageConstants.BoardMaxY, DifficultyBoardConfig.boardHeight(null));
        assertEquals(StageConstants.BoardMaxX + "x" + StageConstants.BoardMaxY,
                DifficultyBoardConfig.boardSizeLabel(null));
    }

    @Test
    void boardSizeMatchesNormalDifficultyConstants() {
        Difficulty normal = DifficultiesFactory.eINSTANCE.createNormalDifficulty();

        assertEquals(StageConstants.BoardMaxXMedium, DifficultyBoardConfig.boardWidth(normal));
        assertEquals(StageConstants.BoardMaxYMedium, DifficultyBoardConfig.boardHeight(normal));
        assertEquals(StageConstants.BoardMaxXMedium + "x" + StageConstants.BoardMaxYMedium,
                DifficultyBoardConfig.boardSizeLabel(normal));
    }

    @Test
    void boardSizeMatchesHardDifficultyConstants() {
        Difficulty hard = DifficultiesFactory.eINSTANCE.createHardDifficulty();

        assertEquals(StageConstants.BoardMaxXLarge, DifficultyBoardConfig.boardWidth(hard));
        assertEquals(StageConstants.BoardMaxYLarge, DifficultyBoardConfig.boardHeight(hard));
        assertEquals(StageConstants.BoardMaxXLarge + "x" + StageConstants.BoardMaxYLarge,
                DifficultyBoardConfig.boardSizeLabel(hard));
    }
}


