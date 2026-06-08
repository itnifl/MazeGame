package main.game.maze.libgdx.controller;

import main.game.maze.libgdx.adapter.*;
import main.game.maze.libgdx.controller.*;
import main.game.maze.libgdx.helper.*;
import main.game.maze.libgdx.service.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import main.game.maze.difficulties.DifficultiesFactory;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.mazeworld.constants.StageConstants;

class GdxGameScreenParityTest {

    @Test
    void playerSpeedMatchesJavaFxTickModel() {
        assertEquals(300f, GdxGameScreenController.toJavaFxLikeSpeed(10f), 0.001f);
    }

    @Test
    void boardSizesMatchJavaFxConstants() {
        int easyW = DifficultyBoardConfig.boardWidth(DifficultiesFactory.eINSTANCE.createEasyDifficulty());
        int easyH = DifficultyBoardConfig.boardHeight(DifficultiesFactory.eINSTANCE.createEasyDifficulty());
        int normalW = DifficultyBoardConfig.boardWidth(DifficultiesFactory.eINSTANCE.createNormalDifficulty());
        int normalH = DifficultyBoardConfig.boardHeight(DifficultiesFactory.eINSTANCE.createNormalDifficulty());
        int hardW = DifficultyBoardConfig.boardWidth(DifficultiesFactory.eINSTANCE.createHardDifficulty());
        int hardH = DifficultyBoardConfig.boardHeight(DifficultiesFactory.eINSTANCE.createHardDifficulty());

        assertEquals(StageConstants.BoardMaxX, easyW);
        assertEquals(StageConstants.BoardMaxY, easyH);
        assertEquals(StageConstants.BoardMaxXMedium, normalW);
        assertEquals(StageConstants.BoardMaxYMedium, normalH);
        assertEquals(StageConstants.BoardMaxXLarge, hardW);
        assertEquals(StageConstants.BoardMaxYLarge, hardH);
    }

    @Test
    void mouseHitTestingUsesSameRectLogic() {
        boolean inside = GdxGameInteractionSupport.contains(10f, 10f, 5f, 5f, 20f, 20f);
        boolean outside = GdxGameInteractionSupport.contains(30.1f, 30.1f, 5f, 5f, 20f, 20f);

        assertTrue(inside);
        assertFalse(outside);
    }

    @Test
    void infectionLevelControlsMistRenderingFlag() {
        EnemySpawn nonInfectious = new EnemySpawn("a", "/a.png", 10f, 10f, 20f, 1f, 1, 0, "", 1f);
        EnemySpawn infectious = new EnemySpawn("b", "/b.png", 10f, 10f, 20f, 1f, 1, 25, "", 1f);

        assertFalse(GdxGameScreenController.isInfectious(nonInfectious));
        assertTrue(GdxGameScreenController.isInfectious(infectious));
    }

    @Test
    void deathDisplayDelayMatchesJavaFxRule() {
        assertEquals(3f, GdxGameScreenController.deathDisplayDelaySeconds(), 0.001f);
    }
}



