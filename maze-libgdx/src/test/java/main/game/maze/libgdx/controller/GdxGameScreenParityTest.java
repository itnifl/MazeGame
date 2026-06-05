package main.game.maze.libgdx.controller;

import main.game.maze.libgdx.adapter.*;
import main.game.maze.libgdx.controller.*;
import main.game.maze.libgdx.helper.*;
import main.game.maze.libgdx.service.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;

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
    void boardSizesMatchJavaFxConstants() throws Exception {
        GdxGameScreenController screen = new GdxGameScreenController(null, 48f, 16, 12, 160f, true);

        Method widthMethod = GdxGameScreenController.class.getDeclaredMethod("boardWidth", main.game.maze.difficulties.Difficulty.class);
        Method heightMethod = GdxGameScreenController.class.getDeclaredMethod("boardHeight", main.game.maze.difficulties.Difficulty.class);
        widthMethod.setAccessible(true);
        heightMethod.setAccessible(true);

        int easyW = (int) widthMethod.invoke(screen, DifficultiesFactory.eINSTANCE.createEasyDifficulty());
        int easyH = (int) heightMethod.invoke(screen, DifficultiesFactory.eINSTANCE.createEasyDifficulty());
        int normalW = (int) widthMethod.invoke(screen, DifficultiesFactory.eINSTANCE.createNormalDifficulty());
        int normalH = (int) heightMethod.invoke(screen, DifficultiesFactory.eINSTANCE.createNormalDifficulty());
        int hardW = (int) widthMethod.invoke(screen, DifficultiesFactory.eINSTANCE.createHardDifficulty());
        int hardH = (int) heightMethod.invoke(screen, DifficultiesFactory.eINSTANCE.createHardDifficulty());

        assertEquals(StageConstants.BoardMaxX, easyW);
        assertEquals(StageConstants.BoardMaxY, easyH);
        assertEquals(StageConstants.BoardMaxXMedium, normalW);
        assertEquals(StageConstants.BoardMaxYMedium, normalH);
        assertEquals(StageConstants.BoardMaxXLarge, hardW);
        assertEquals(StageConstants.BoardMaxYLarge, hardH);
    }

    @Test
    void mouseHitTestingUsesSameRectLogic() throws Exception {
        Method contains = GdxGameScreenController.class.getDeclaredMethod(
                "contains", float.class, float.class, float.class, float.class, float.class, float.class);
        contains.setAccessible(true);

        boolean inside = (boolean) contains.invoke(null, 10f, 10f, 5f, 5f, 20f, 20f);
        boolean outside = (boolean) contains.invoke(null, 30.1f, 30.1f, 5f, 5f, 20f, 20f);

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



