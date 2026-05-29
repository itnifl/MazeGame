package main.game.maze.libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Launches the MazeGame libGDX backend.
 *
 * <p><strong>Status: WIP placeholder.</strong> This currently boots a libGDX
 * window, installs the libGDX implementations of the common-graphics
 * abstractions via {@link GdxBackend#install()} and renders a status message.
 * The actual MazeGame game loop, characters, controllers and maze world still
 * live in the {@code maze} module and are bound to JavaFX; porting them
 * behind the common-graphics abstractions is the next phase of the
 * pluggable-backend migration.
 *
 * <p>To run: {@code mvn -pl maze-libgdx -am package} then
 * {@code java -cp "maze-libgdx/target/maze-libgdx-1.0.0-SNAPSHOT.jar;maze-libgdx/target/libs/*" main.game.maze.libgdx.GdxAppLauncher}
 * (use {@code :} instead of {@code ;} on Linux/macOS).
 */
public final class GdxAppLauncher {

    private GdxAppLauncher() {}

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("MazeGame (libGDX backend, WIP)");
        config.setWindowedMode(1024, 768);
        config.setForegroundFPS(60);
        new Lwjgl3Application(new MazeGameGdxListener(), config);
    }

    private static final class MazeGameGdxListener extends ApplicationAdapter {
        private SpriteBatch batch;
        private BitmapFont font;

        @Override
        public void create() {
            GdxBackend.install();
            batch = new SpriteBatch();
            font = new BitmapFont();
            font.setColor(Color.WHITE);
            Gdx.app.log("MazeGame", "libGDX backend installed.");
        }

        @Override
        public void render() {
            ScreenUtils.clear(0.05f, 0.05f, 0.10f, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            batch.begin();
            font.draw(batch, "MazeGame: libGDX backend is installed (WIP).", 32, 720);
            font.draw(batch, "Game loop has not been ported off JavaFX yet.", 32, 690);
            font.draw(batch, "Run the JavaFX backend launch config to play.", 32, 660);
            batch.end();
        }

        @Override
        public void dispose() {
            if (batch != null) batch.dispose();
            if (font != null) font.dispose();
        }
    }
}
