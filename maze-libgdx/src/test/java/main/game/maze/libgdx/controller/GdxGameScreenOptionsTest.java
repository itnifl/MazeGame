package main.game.maze.libgdx.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import main.game.maze.common.graphics.config.MazeRuntimeConfig;
import main.game.maze.difficulties.DifficultiesFactory;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.libgdx.service.GdxAssetService;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link GdxGameScreenOptions}, the SR-41 parameter object that
 * replaced the telescoping-constructor chain of {@link GdxGameScreenController}.
 */
class GdxGameScreenOptionsTest {

    @Test
    void builderDefaultsMirrorPreviousNoArgConstructor() {
        GdxGameScreenOptions options = GdxGameScreenOptions.builder().build();

        assertNull(options.arena(), "arena defaults to null (standalone maze)");
        assertTrue(options.useRealMaze(), "useRealMaze defaults to true");
        assertTrue(options.ownsAssetService(), "ownsAssetService defaults to true");
        assertTrue(options.autoStartOnCreate(), "autoStartOnCreate defaults to true");
        assertFalse(options.immediateStartOnCreate(), "immediateStartOnCreate defaults to false");
        assertFalse(options.showHighScoresOnCreate(), "showHighScoresOnCreate defaults to false");
        assertNull(options.returnToMenuAction(), "returnToMenuAction defaults to null");
        assertNull(options.forcedDifficulty(), "forcedDifficulty defaults to null");
    }

    @Test
    void builderSuppliesAssetServiceWhenNoneProvided() {
        GdxGameScreenOptions options = GdxGameScreenOptions.builder().build();

        assertNotNull(options.assetService(), "a default asset service must be created when none is supplied");
    }

    @Test
    void builderKeepsProvidedAssetService() {
        GdxAssetService assets = new GdxAssetService();

        GdxGameScreenOptions options = GdxGameScreenOptions.builder()
                .assetService(assets)
                .build();

        assertSame(assets, options.assetService(), "the supplied asset service instance must be retained");
    }

    @Test
    void runtimeConfigCopiesCellSizeAndUseRealMaze() {
        MazeRuntimeConfig cfg = new MazeRuntimeConfig(800, 600, 10, 8, 32f, 120f, false);

        GdxGameScreenOptions options = GdxGameScreenOptions.builder()
                .runtimeConfig(cfg)
                .build();

        assertEquals(32f, options.cellSize(), 0.0001f, "cellSize must be copied from the runtime config");
        assertFalse(options.useRealMaze(), "useRealMaze must be copied from the runtime config");
    }

    @Test
    void builderRetainsExplicitFlagsAndCollaborators() {
        Difficulty hard = DifficultiesFactory.eINSTANCE.createHardDifficulty();
        Runnable returnToMenu = () -> { };
        GdxAssetService assets = new GdxAssetService();

        GdxGameScreenOptions options = GdxGameScreenOptions.builder()
                .runtimeConfig(MazeRuntimeConfig.DEFAULT)
                .assetService(assets)
                .ownsAssetService(false)
                .autoStartOnCreate(false)
                .immediateStartOnCreate(true)
                .showHighScoresOnCreate(true)
                .returnToMenuAction(returnToMenu)
                .forcedDifficulty(hard)
                .build();

        assertFalse(options.ownsAssetService());
        assertFalse(options.autoStartOnCreate());
        assertTrue(options.immediateStartOnCreate());
        assertTrue(options.showHighScoresOnCreate());
        assertSame(returnToMenu, options.returnToMenuAction());
        assertSame(hard, options.forcedDifficulty());
        assertSame(assets, options.assetService());
    }

    @Test
    void controllerStoresForcedDifficultyFromOptions() throws Exception {
        Difficulty easy = DifficultiesFactory.eINSTANCE.createEasyDifficulty();

        GdxGameScreenController controller = new GdxGameScreenController(GdxGameScreenOptions.builder()
                .runtimeConfig(MazeRuntimeConfig.DEFAULT)
                .assetService(new GdxAssetService())
                .ownsAssetService(false)
                .forcedDifficulty(easy)
                .build());

        var field = GdxGameScreenController.class.getDeclaredField("forcedDifficulty");
        field.setAccessible(true);
        assertSame(easy, field.get(controller), "the controller must store the forced difficulty from the options");
    }
}
