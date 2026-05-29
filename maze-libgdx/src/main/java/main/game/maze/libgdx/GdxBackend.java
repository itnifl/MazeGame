package main.game.maze.libgdx;

import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.AnimationEngine;
import main.game.maze.common.graphics.UiScheduler;

/**
 * Entry point for activating the libGDX rendering/audio backend. Call
 * {@link #install()} once the libGDX render thread is up (typically from
 * inside {@code ApplicationListener.create()}) so {@link GdxUiScheduler}
 * captures the correct render thread reference.
 *
 * <p>This is the only place in the codebase that knows the binding between
 * the common-graphics abstractions and the libGDX implementations.
 */
public final class GdxBackend {

    private GdxBackend() {}

    /** Installs libGDX implementations as the active singletons. */
    public static void install() {
        UiScheduler.set(new GdxUiScheduler());
        AudioEngine.set(new GdxAudioEngine());
        AnimationEngine.reset();
    }
}
