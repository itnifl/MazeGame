package main.game.maze.javafx;

import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.UiScheduler;

/**
 * Entry point for activating the JavaFX rendering/audio backend. Call
 * {@link #install()} once during application startup (typically before the
 * first scene is built) to swap the safe-default scheduler/audio engine in
 * {@link UiScheduler} and {@link AudioEngine} for the JavaFX-backed ones.
 *
 * <p>This is the only place in the codebase that knows the binding between
 * the common-graphics abstractions and the JavaFX implementations. Everything
 * else should resolve them via {@code UiScheduler.get()} /
 * {@code AudioEngine.get()}.
 */
public final class JavaFxBackend {

    private JavaFxBackend() {}

    /** Installs JavaFX implementations as the active singletons. */
    public static void install() {
        UiScheduler.set(new JavaFxUiScheduler());
        AudioEngine.set(new JavaFxAudioEngine());
    }
}
