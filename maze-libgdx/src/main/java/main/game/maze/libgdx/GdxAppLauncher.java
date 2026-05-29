package main.game.maze.libgdx;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import main.game.maze.common.graphics.config.MazeConfigLoader;
import main.game.maze.common.graphics.config.MazeRuntimeConfig;
import main.game.maze.common.graphics.config.PropertiesMazeConfigLoader;
import main.game.maze.libgdx.game.MazeArena;
import main.game.maze.libgdx.game.RealMaze;
import main.game.maze.libgdx.game.SampleMaze;

/**
 * Launches the MazeGame libGDX backend.
 *
 * <p>Phase 3 (current): renders either the shared {@code GameMazeWorld}
 * (Phase 3 RealMaze adapter) or the standalone {@code SampleMaze} placeholder
 * depending on the runtime config.
 *
 * <p>Phase 4 (F16) entrypoint: runtime parameters (window size, maze grid,
 * which arena backs the game) come from a {@link MazeConfigLoader} so the
 * game can be reconfigured without recompiling. Defaults to a properties
 * file lookup; falls back to {@link MazeRuntimeConfig#DEFAULT}.
 *
 * <p>To run: {@code pwsh ./make-libgdx.ps1 run}.
 */
public final class GdxAppLauncher {

    private GdxAppLauncher() {}

    public static void main(String[] args) {
        MazeRuntimeConfig cfg = new PropertiesMazeConfigLoader().load();

        Lwjgl3ApplicationConfiguration appConfig = new Lwjgl3ApplicationConfiguration();
        appConfig.setTitle("MazeGame (libGDX backend)");
        appConfig.setWindowedMode(cfg.windowWidth(), cfg.windowHeight());
        appConfig.setForegroundFPS(60);

        MazeArena arena = cfg.useRealMaze()
            ? RealMaze.fresh(cfg.windowWidth(), cfg.windowHeight())
            : new SampleMaze(cfg.mazeCols(), cfg.mazeRows(), cfg.cellSize(), 1L);

        new Lwjgl3Application(new GdxGameScreen(arena), appConfig);
    }
}
