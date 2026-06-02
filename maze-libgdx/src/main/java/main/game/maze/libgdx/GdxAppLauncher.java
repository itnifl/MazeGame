package main.game.maze.libgdx;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import main.game.maze.common.graphics.config.MazeConfigLoader;
import main.game.maze.common.graphics.config.MazeRuntimeConfig;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import main.game.maze.common.graphics.config.PropertiesMazeConfigLoader;
import main.game.maze.common.graphics.config.PropertiesMazeVisualStyleLoader;
import main.game.maze.common.graphics.config.XmiMazeVisualStyleLoader;
import main.game.maze.runtime.OclBootstrap;

/**
 * Launches the MazeGame libGDX backend.
 *
 * <p>Renders either the shared {@code GameMazeWorld}
 * or the standalone {@code SampleMaze} placeholder
 * depending on the runtime config.
 *
 * <p>runtime parameters (window size, maze grid,
 * which arena backs the game) come from a {@link MazeConfigLoader} so the
 * game can be reconfigured without recompiling. Defaults to a properties
 * file lookup; falls back to {@link MazeRuntimeConfig#DEFAULT}.
 *
 * <p>To run: {@code pwsh ./make-libgdx.ps1 run}.
 */
public final class GdxAppLauncher {

    private static final Logger LOGGER = Logger.getLogger(GdxAppLauncher.class.getName());

    private GdxAppLauncher() {}

    public static void main(String[] args) {
        OclBootstrap.init();
        MazeRuntimeConfig cfg = loadOrDefault();

        Lwjgl3ApplicationConfiguration appConfig = new Lwjgl3ApplicationConfiguration();
        appConfig.setTitle("MazeGame (libGDX backend)");
        appConfig.setWindowedMode(cfg.windowWidth(), cfg.windowHeight());
        appConfig.setForegroundFPS(60);
        MazeVisualStyleConfig style = loadStyleOrDefault();
        String iconPath = style.menuIconImagePath().startsWith("/")
            ? style.menuIconImagePath().substring(1)
            : style.menuIconImagePath();
        appConfig.setWindowIcon(iconPath);

        new Lwjgl3Application(new GdxGameScreen(null, cfg), appConfig);
    }

    static MazeRuntimeConfig loadOrDefault() {
        try {
            return new PropertiesMazeConfigLoader().load();
        } catch (RuntimeException ex) {
            LOGGER.log(Level.WARNING,
                "Failed to load runtime config; falling back to MazeRuntimeConfig.DEFAULT", ex);
            return MazeRuntimeConfig.DEFAULT;
        }
    }

    private static MazeVisualStyleConfig loadStyleOrDefault() {
        try {
            return new XmiMazeVisualStyleLoader().load();
        } catch (RuntimeException ex) {
            try {
                return new PropertiesMazeVisualStyleLoader().load();
            } catch (RuntimeException fallbackEx) {
                LOGGER.log(Level.WARNING,
                    "Failed to load visual style config; falling back to MazeVisualStyleConfig.DEFAULT", fallbackEx);
                return MazeVisualStyleConfig.DEFAULT;
            }
        }
    }
}
