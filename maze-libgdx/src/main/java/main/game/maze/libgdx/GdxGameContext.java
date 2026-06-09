package main.game.maze.libgdx;

import main.game.maze.common.graphics.config.MazeRuntimeConfig;
import main.game.maze.libgdx.service.GdxAssetService;

/**
 * Shared context object passed to libGDX screens.
 */
public record GdxGameContext(MazeRuntimeConfig runtimeConfig, GdxAssetService assets) {
}
