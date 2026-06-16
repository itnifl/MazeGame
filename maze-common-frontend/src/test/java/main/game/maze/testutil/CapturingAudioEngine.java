package main.game.maze.testutil;

import main.game.maze.common.graphics.IAudioEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Test double for {@link IAudioEngine} that records every call so tests can
 * assert on which audio operations were performed and in what order.
 */
public final class CapturingAudioEngine implements IAudioEngine {

    public final List<String> playCalls = new ArrayList<>();
    public final List<String> playLoopCalls = new ArrayList<>();
    public final List<String> stopChannelCalls = new ArrayList<>();
    public final List<String> playRateLimitedCalls = new ArrayList<>();
    public int disposeCalls = 0;

    @Override
    public void play(String resourcePath) {
        playCalls.add(resourcePath);
    }

    @Override
    public void playRateLimited(String resourcePath, String soundId, long cooldownMs) {
        playRateLimitedCalls.add(soundId + ":" + resourcePath);
    }

    @Override
    public void playLoop(String resourcePath, String channelId) {
        playLoopCalls.add(channelId + ":" + resourcePath);
    }

    @Override
    public void stopChannel(String channelId) {
        stopChannelCalls.add(channelId);
    }

    @Override
    public void dispose() {
        disposeCalls++;
    }

    public int totalCalls() {
        return playCalls.size() + playLoopCalls.size() + stopChannelCalls.size() + playRateLimitedCalls.size();
    }
}
