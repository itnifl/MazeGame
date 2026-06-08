package main.game.maze.libgdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import java.util.function.Function;
import main.game.maze.common.graphics.config.MazeRuntimeConfig;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.libgdx.controller.LegacyPlayScreenController;
import main.game.maze.libgdx.controller.MenuScreenController;
import main.game.maze.libgdx.controller.PlayScreenController;
import main.game.maze.libgdx.service.GdxAssetService;

/**
 * libGDX Game root. Owns shared resources and routes to screens.
 */
public class GdxGame extends Game {

    private final MazeRuntimeConfig cfg;
    private final GdxAssetService providedAssetService;
    private final Function<GdxAssetService, Screen> initialScreenFactory;
    private GdxAssetService assetService;
    private GdxGameContext context;

    public GdxGame(MazeRuntimeConfig cfg) {
        this(cfg, null, null);
    }

    GdxGame(
            MazeRuntimeConfig cfg,
            GdxAssetService providedAssetService,
            Function<GdxAssetService, Screen> initialScreenFactory) {
        this.cfg = cfg;
        this.providedAssetService = providedAssetService;
        this.initialScreenFactory = initialScreenFactory;
    }

    @Override
    public void create() {
        assetService = providedAssetService != null ? providedAssetService : new GdxAssetService();
        context = new GdxGameContext(cfg, assetService);
        Screen initialScreen = initialScreenFactory != null
                ? initialScreenFactory.apply(assetService)
                : new MenuScreenController(context, d -> routeToPlayScreen(d), () -> routeToHighScores());
        routeToScreen(initialScreen);
    }

    void routeToMenuScreen() {
        routeToScreen(new MenuScreenController(context, d -> routeToPlayScreen(d), () -> routeToHighScores()));
    }

    void routeToPlayScreen() {
        routeToPlayScreen(null);
    }

    void routeToPlayScreen(Difficulty selected) {
        routeToScreen(new PlayScreenController(context, selected, this::routeToMenuScreen));
    }

    void routeToLegacyPlay(boolean autoStartOnCreate) {
        routeToScreen(new LegacyPlayScreenController(context, autoStartOnCreate, this::routeToMenuScreen));
    }

    void routeToHighScores() {
        routeToScreen(LegacyPlayScreenController.forHighScores(context, this::routeToMenuScreen));
    }

    GdxGameContext context() {
        return context;
    }

    void routeToScreen(Screen screen) {
        setScreen(screen);
    }

    @Override
    public void dispose() {
        super.dispose();
        if (assetService != null) {
            assetService.dispose();
            assetService = null;
        }
        context = null;
    }
}


