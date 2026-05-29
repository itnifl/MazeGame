package main.game.maze.libgdx;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/**
 * Launches the MazeGame libGDX backend.
 *
 * <p>Phase 2 (current): boots a libGDX window running a self-contained
 * gameplay subset (procedural maze, arrow-key movement, goal cell). The
 * shared character / opponent / DSL pipeline lives in Tycho-packaged
 * Eclipse plugins and will be wired in once those bundles are repackaged
 * as plain Maven jars consumable by this module.
 *
 * <p>To run: {@code pwsh ./make-libgdx.ps1 run}.
 */
public final class GdxAppLauncher {

    private GdxAppLauncher() {}

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("MazeGame (libGDX backend)");
        config.setWindowedMode(1024, 768);
        config.setForegroundFPS(60);
        new Lwjgl3Application(new GdxGameScreen(), config);
    }
}
