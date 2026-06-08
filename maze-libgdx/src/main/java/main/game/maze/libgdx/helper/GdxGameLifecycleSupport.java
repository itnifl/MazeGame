package main.game.maze.libgdx.helper;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.function.Function;
import main.game.maze.constants.ImageResourceConstants;

/**
 * Lifecycle support for initializing and disposing libGDX graphics resources.
 */
public final class GdxGameLifecycleSupport {

    private GdxGameLifecycleSupport() {
    }

    public static GraphicsResources createGraphicsResources() {
        BitmapFont font = new BitmapFont();
        font.setColor(Color.WHITE);
        return new GraphicsResources(
                new SpriteBatch(),
                new ShapeRenderer(),
                font,
                new GlyphLayout());
    }

    public static OverlayResources createOverlayResources(Function<String, Texture> textureLoader) {
        return new OverlayResources(
                new OrthographicCamera(),
                new OrthographicCamera(),
                textureLoader.apply(ImageResourceConstants.WinOverlayBackdropImage),
                textureLoader.apply(ImageResourceConstants.GameOverOverlayBackdropImage));
    }

    public static void disposeGraphicsResources(
            SpriteBatch batch,
            ShapeRenderer shapes,
            BitmapFont font) {
        if (batch != null) {
            batch.dispose();
        }
        if (shapes != null) {
            shapes.dispose();
        }
        if (font != null) {
            font.dispose();
        }
    }

    public record GraphicsResources(
            SpriteBatch batch,
            ShapeRenderer shapes,
            BitmapFont font,
            GlyphLayout glyphLayout) {
    }

    public record OverlayResources(
            OrthographicCamera camera,
            OrthographicCamera hudCamera,
            Texture winBackgroundTexture,
            Texture gameOverBackgroundTexture) {
    }
}