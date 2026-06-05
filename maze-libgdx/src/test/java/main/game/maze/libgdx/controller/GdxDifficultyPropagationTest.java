package main.game.maze.libgdx.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.Field;

import main.game.maze.common.graphics.config.MazeRuntimeConfig;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.DifficultiesFactory;
import main.game.maze.libgdx.service.GdxAssetService;
import org.junit.jupiter.api.Test;

/**
 * Regression tests for the bug where the difficulty selected in the start menu
 * was ignored and the game always started on Normal difficulty.
 *
 * <p>Root cause: GdxGame used {@code ignored -> routeToPlayScreen()} so the
 * Difficulty passed from MenuScreenController was discarded.
 * GdxGameScreenController#startGameFromSelection() then always called
 * difficultyService.getCurrent() on a fresh instance, returning Normal.
 *
 * <p>This class tests GdxGameScreenController directly. Routing-level tests
 * live in GdxGameRoutingTest in the main.game.maze.libgdx package.
 */
class GdxDifficultyPropagationTest {

    @Test
    void gdxGameScreenControllerStoresForcedDifficultyWhenProvided() throws Exception {
        GdxAssetService assets = new GdxAssetService();
        Difficulty hard = DifficultiesFactory.eINSTANCE.createHardDifficulty();

        GdxGameScreenController controller = new GdxGameScreenController(
                null, MazeRuntimeConfig.DEFAULT, assets, false, false, false, null, hard);

        assertSame(hard, readForcedDifficulty(controller),
                "forcedDifficulty must be the HardDifficulty instance passed to the constructor");
    }

    @Test
    void gdxGameScreenControllerForcedDifficultyIsNullWhenNotProvided() throws Exception {
        GdxAssetService assets = new GdxAssetService();

        GdxGameScreenController controller = new GdxGameScreenController(
                null, MazeRuntimeConfig.DEFAULT, assets, false, false, false, null, null);

        assertNull(readForcedDifficulty(controller),
                "forcedDifficulty must be null when constructed without one");
    }

    @Test
    void gdxGameScreenControllerStoresEasyDifficulty() throws Exception {
        GdxAssetService assets = new GdxAssetService();
        Difficulty easy = DifficultiesFactory.eINSTANCE.createEasyDifficulty();

        GdxGameScreenController controller = new GdxGameScreenController(
                null, MazeRuntimeConfig.DEFAULT, assets, false, false, false, null, easy);

        assertSame(easy, readForcedDifficulty(controller),
                "forcedDifficulty must be the EasyDifficulty instance passed to the constructor");
    }

    @Test
    void gdxGameScreenControllerStoresNormalDifficulty() throws Exception {
        GdxAssetService assets = new GdxAssetService();
        Difficulty normal = DifficultiesFactory.eINSTANCE.createNormalDifficulty();

        GdxGameScreenController controller = new GdxGameScreenController(
                null, MazeRuntimeConfig.DEFAULT, assets, false, false, false, null, normal);

        assertNotNull(readForcedDifficulty(controller));
        assertSame(normal, readForcedDifficulty(controller),
                "forcedDifficulty must be the NormalDifficulty instance passed to the constructor");
    }

    private static Difficulty readForcedDifficulty(GdxGameScreenController controller) throws Exception {
        Field f = GdxGameScreenController.class.getDeclaredField("forcedDifficulty");
        f.setAccessible(true);
        return (Difficulty) f.get(controller);
    }
}
