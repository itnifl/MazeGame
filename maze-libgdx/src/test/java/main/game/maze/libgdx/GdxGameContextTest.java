package main.game.maze.libgdx;

import main.game.maze.libgdx.adapter.*;
import main.game.maze.libgdx.controller.*;
import main.game.maze.libgdx.helper.*;
import main.game.maze.libgdx.service.*;
import static org.junit.jupiter.api.Assertions.assertSame;

import main.game.maze.common.graphics.config.MazeRuntimeConfig;
import org.junit.jupiter.api.Test;

class GdxGameContextTest {

    @Test
    void recordExposesRuntimeConfigAndAssets() {
        GdxAssetService assets = new GdxAssetService();
        GdxGameContext context = new GdxGameContext(MazeRuntimeConfig.DEFAULT, assets);

        assertSame(MazeRuntimeConfig.DEFAULT, context.runtimeConfig());
        assertSame(assets, context.assets());
    }
}
