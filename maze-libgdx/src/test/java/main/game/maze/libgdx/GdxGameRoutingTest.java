package main.game.maze.libgdx;

import main.game.maze.libgdx.adapter.*;
import main.game.maze.libgdx.controller.*;
import main.game.maze.libgdx.helper.*;
import main.game.maze.libgdx.service.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.lang.reflect.Field;
import com.badlogic.gdx.Screen;
import main.game.maze.common.graphics.config.MazeRuntimeConfig;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.DifficultiesFactory;
import org.junit.jupiter.api.Test;

class GdxGameRoutingTest {

    @Test
    void createRoutesToInitialScreenFromFactory() {
        RecordingAssetService assets = new RecordingAssetService();
        RecordingScreen initial = new RecordingScreen();
        TestableGdxGame game = new TestableGdxGame(
                MazeRuntimeConfig.DEFAULT,
                assets,
                ignored -> initial);

        game.create();

        assertSame(initial, game.routedScreen);
    }

    @Test
    void createProvidesAssetServiceToInitialScreenFactory() {
        RecordingAssetService assets = new RecordingAssetService();
        Holder<RecordingAssetService> received = new Holder<>();
        TestableGdxGame game = new TestableGdxGame(
                MazeRuntimeConfig.DEFAULT,
                assets,
                service -> {
                    received.value = (RecordingAssetService) service;
                    return new RecordingScreen();
                });

        game.create();

        assertNotNull(received.value);
        assertSame(assets, received.value);
    }

    @Test
    void disposeDisposesAssetService() {
        RecordingAssetService assets = new RecordingAssetService();
        TestableGdxGame game = new TestableGdxGame(
                MazeRuntimeConfig.DEFAULT,
                assets,
                ignored -> new RecordingScreen());

        game.create();
        game.dispose();

        assertEquals(1, assets.disposeCalls);
    }

    @Test
    void defaultCreateRoutesToLegacyPlayScreenAndBuildsContext() {
        RecordingAssetService assets = new RecordingAssetService();
        TestableGdxGame game = new TestableGdxGame(
                MazeRuntimeConfig.DEFAULT,
                assets,
                null);

        game.create();

        assertInstanceOf(MenuScreenController.class, game.routedScreen);
        assertNotNull(game.context());
        assertSame(assets, game.context().assets());
        assertSame(MazeRuntimeConfig.DEFAULT, game.context().runtimeConfig());
    }

    @Test
    void routeToLegacyPlayUsesLegacyPlayScreen() {
        RecordingAssetService assets = new RecordingAssetService();
        TestableGdxGame game = new TestableGdxGame(
                MazeRuntimeConfig.DEFAULT,
                assets,
                null);
        game.create();

        game.routeToLegacyPlay(true);

        assertInstanceOf(LegacyPlayScreenController.class, game.routedScreen);
    }

    @Test
    void routeToPlayScreenUsesPlayScreen() {
        RecordingAssetService assets = new RecordingAssetService();
        TestableGdxGame game = new TestableGdxGame(
                MazeRuntimeConfig.DEFAULT,
                assets,
                null);
        game.create();

        game.routeToPlayScreen();

        assertInstanceOf(PlayScreenController.class, game.routedScreen);
    }

    @Test
    void routeToPlayScreenWithDifficultyCreatesPlayScreenController() {
        RecordingAssetService assets = new RecordingAssetService();
        TestableGdxGame game = new TestableGdxGame(MazeRuntimeConfig.DEFAULT, assets, null);
        game.create();
        Difficulty easy = DifficultiesFactory.eINSTANCE.createEasyDifficulty();

        game.routeToPlayScreen(easy);

        assertInstanceOf(PlayScreenController.class, game.routedScreen,
                "routeToPlayScreen(Difficulty) must route to a PlayScreenController");
    }

    @Test
    void routeToPlayScreenPassesDifficultyToInnerController() throws Exception {
        RecordingAssetService assets = new RecordingAssetService();
        TestableGdxGame game = new TestableGdxGame(MazeRuntimeConfig.DEFAULT, assets, null);
        game.create();
        Difficulty hard = DifficultiesFactory.eINSTANCE.createHardDifficulty();

        game.routeToPlayScreen(hard);

        GdxGameScreenController inner = extractInnerController(game.routedScreen);
        assertNotNull(inner, "PlayScreenController must wrap a GdxGameScreenController");
        Difficulty stored = readForcedDifficulty(inner);
        assertSame(hard, stored,
                "The difficulty selected in the menu must reach GdxGameScreenController.forcedDifficulty");
    }

    @Test
    void routeToPlayScreenWithoutDifficultyHasNullForcedDifficulty() throws Exception {
        RecordingAssetService assets = new RecordingAssetService();
        TestableGdxGame game = new TestableGdxGame(MazeRuntimeConfig.DEFAULT, assets, null);
        game.create();

        game.routeToPlayScreen();

        GdxGameScreenController inner = extractInnerController(game.routedScreen);
        assertNotNull(inner);
        assertNull(readForcedDifficulty(inner),
                "routeToPlayScreen() with no argument must leave forcedDifficulty null");
    }

    @Test
    void menuDifficultySelectionIsNotIgnoredByGdxGame() throws Exception {
        RecordingAssetService assets = new RecordingAssetService();
        TestableGdxGame game = new TestableGdxGame(MazeRuntimeConfig.DEFAULT, assets, null);
        game.create();
        Difficulty normal = DifficultiesFactory.eINSTANCE.createNormalDifficulty();
        Difficulty hard   = DifficultiesFactory.eINSTANCE.createHardDifficulty();

        game.routeToPlayScreen(hard);
        GdxGameScreenController hardCtrl = extractInnerController(game.routedScreen);

        game.routeToPlayScreen(normal);
        GdxGameScreenController normalCtrl = extractInnerController(game.routedScreen);

        assertSame(hard,   readForcedDifficulty(hardCtrl),   "Hard difficulty must be stored");
        assertSame(normal, readForcedDifficulty(normalCtrl), "Normal difficulty must be stored");
    }

    private static Difficulty readForcedDifficulty(GdxGameScreenController ctrl) throws Exception {
        Field f = GdxGameScreenController.class.getDeclaredField("forcedDifficulty");
        f.setAccessible(true);
        return (Difficulty) f.get(ctrl);
    }

    private static GdxGameScreenController extractInnerController(Screen screen) throws Exception {
        if (!(screen instanceof PlayScreenController)) {
            return null;
        }
        Class<?> adapterScreenBase = screen.getClass().getSuperclass();
        Field delegateField = adapterScreenBase.getDeclaredField("delegate");
        delegateField.setAccessible(true);
        Object appAdapterScreen = delegateField.get(screen);
        Field adapterField = appAdapterScreen.getClass().getDeclaredField("delegate");
        adapterField.setAccessible(true);
        Object adapter = adapterField.get(appAdapterScreen);
        return adapter instanceof GdxGameScreenController ctrl ? ctrl : null;
    }

    private static final class TestableGdxGame extends GdxGame {

        private Screen routedScreen;

        TestableGdxGame(
                MazeRuntimeConfig cfg,
                GdxAssetService providedAssetService,
                java.util.function.Function<GdxAssetService, Screen> initialScreenFactory) {
            super(cfg, providedAssetService, initialScreenFactory);
        }

        @Override
        void routeToScreen(Screen screen) {
            this.routedScreen = screen;
        }
    }

    private static final class RecordingAssetService extends GdxAssetService {

        private int disposeCalls;

        @Override
        public void dispose() {
            disposeCalls++;
        }
    }

    private static final class RecordingScreen implements Screen {

        @Override
        public void show() {}

        @Override
        public void render(float delta) {}

        @Override
        public void resize(int width, int height) {}

        @Override
        public void pause() {}

        @Override
        public void resume() {}

        @Override
        public void hide() {}

        @Override
        public void dispose() {}
    }

    private static final class Holder<T> {
        private T value;
    }
}



