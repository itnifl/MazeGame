package main.game.maze.platform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Audio engine that records calls but plays nothing. Designed for unit tests
 * that want to assert sound triggers without initialising the JavaFX media
 * stack.
 */
public final class NoopAudioEngine implements IAudioEngine {
    private final List<String> playedResources = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void play(String resourcePath) {
        playedResources.add(resourcePath);
    }

    @Override
    public void playRateLimited(String resourcePath, String soundId, long cooldownMs) {
        playedResources.add(resourcePath);
    }

    @Override
    public void dispose() {
        playedResources.clear();
    }

    /** Snapshot of resource paths passed to play/playRateLimited so far. */
    public List<String> playedResources() {
        synchronized (playedResources) {
            return new ArrayList<>(playedResources);
        }
    }
}
