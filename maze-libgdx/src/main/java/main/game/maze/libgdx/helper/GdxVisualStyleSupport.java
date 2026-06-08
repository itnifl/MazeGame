package main.game.maze.libgdx.helper;

import java.util.function.Supplier;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import main.game.maze.common.graphics.config.PropertiesMazeVisualStyleLoader;
import main.game.maze.common.graphics.config.XmiMazeVisualStyleLoader;

/**
 * Shared visual style loading policy for libGDX entry points and screens.
 */
public final class GdxVisualStyleSupport {

    private GdxVisualStyleSupport() {
    }

    public static MazeVisualStyleConfig loadOrDefault() {
        return loadOrDefault(
                () -> new XmiMazeVisualStyleLoader().load(),
                () -> new PropertiesMazeVisualStyleLoader().load(),
                () -> {
                });
    }

    public static MazeVisualStyleConfig loadOrDefault(
            Supplier<MazeVisualStyleConfig> xmiLoader,
            Supplier<MazeVisualStyleConfig> propertiesLoader,
            Runnable onFallback) {
        try {
            return xmiLoader.get();
        } catch (RuntimeException ex) {
            try {
                return propertiesLoader.get();
            } catch (RuntimeException fallbackEx) {
                onFallback.run();
                return MazeVisualStyleConfig.DEFAULT;
            }
        }
    }
}
