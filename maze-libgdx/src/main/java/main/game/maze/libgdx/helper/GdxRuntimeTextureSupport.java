package main.game.maze.libgdx.helper;

import com.badlogic.gdx.graphics.Texture;
import java.util.function.Function;
import main.game.maze.libgdx.model.RuntimeVisualModel;

/**
 * Runtime texture path materialization for libGDX game sessions.
 */
public final class GdxRuntimeTextureSupport {

    private GdxRuntimeTextureSupport() {
    }

    public record RuntimeTextures(
            Texture playerTexture,
            Texture playerDeathTexture,
            Texture goalTexture,
            Texture wallTexture,
            Texture backgroundTexture) {
    }

    public static RuntimeTextures load(RuntimeVisualModel runtimeModel, Function<String, Texture> loader) {
        return new RuntimeTextures(
                loader.apply(runtimeModel.playerImagePath()),
                loader.apply(runtimeModel.playerDeathImagePath()),
                loader.apply(runtimeModel.goalImagePath()),
                loader.apply(runtimeModel.wallImagePath()),
                loader.apply(runtimeModel.backgroundImagePath()));
    }
}
