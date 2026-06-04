package main.game.maze.libgdx;

import com.badlogic.gdx.Game;
import main.game.maze.common.graphics.config.MazeRuntimeConfig;

/**
 * libGDX Game root. Owns shared resources and routes to screens.
 */
public final class GdxGame extends Game {

    private final MazeRuntimeConfig cfg;
    private GdxAssetService assetService;

    public GdxGame(MazeRuntimeConfig cfg) {
        this.cfg = cfg;
    }

    @Override
    public void create() {
        assetService = new GdxAssetService();
        setScreen(new ApplicationAdapterScreen(new GdxGameScreen(null, cfg, assetService, false)));
    }

    @Override
    public void dispose() {
        super.dispose();
        if (assetService != null) {
            assetService.dispose();
            assetService = null;
        }
    }
}
