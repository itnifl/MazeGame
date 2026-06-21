package main.game.maze.actions;

import main.game.maze.App;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.IAudioEngine;
import main.game.maze.common.constants.AudioResourceConstants;
import main.game.maze.constants.AudioChannelConstants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameOverActionTest {

    @AfterEach
    void cleanup() {
        AudioEngine.reset();
        App.gameController = null;
    }

    // startGameOverMusic() is a public static method — test it in isolation.
    // Note: stopChannel(IN_GAME_MUSIC) is called by showGameOverScreen(), not here;
    // this method's sole responsibility is to start the game-over loop.
    @Test
    void startGameOverMusic_loopsGameOverTrackOnGameOverChannel() {
        RecordingAudio audio = new RecordingAudio();
        AudioEngine.set(audio);

        GameOverAction.startGameOverMusic();

        assertTrue(audio.loopCalls.contains(AudioChannelConstants.GAME_OVER_MUSIC + ":" + AudioResourceConstants.GameOverSound),
                "startGameOverMusic must start the game-over music loop on GAME_OVER_MUSIC channel");
        assertTrue(audio.stopCalls.isEmpty(),
                "startGameOverMusic must NOT stop any channel — stopping IN_GAME_MUSIC is showGameOverScreen's responsibility");
    }

    // Calling startGameOverMusic() twice produces exactly two loop invocations (no dedup guard in this method).
    @Test
    void startGameOverMusic_calledTwice_producesExactlyTwoLoopCalls() {
        RecordingAudio audio = new RecordingAudio();
        AudioEngine.set(audio);

        GameOverAction.startGameOverMusic();
        GameOverAction.startGameOverMusic();

        long count = audio.loopCalls.stream()
                .filter(s -> s.startsWith(AudioChannelConstants.GAME_OVER_MUSIC))
                .count();
        assertEquals(2, count, "Each call to startGameOverMusic must issue one playLoop call");
    }

    // AddDeathNotification with no scene attached must not crash.
    @Test
    void addDeathNotification_withoutScene_doesNotThrow() {
        RecordingAudio audio = new RecordingAudio();
        AudioEngine.set(audio);

        // PlayerCharacter requires JavaFX scene; skip with null player to test guard.
        // We test that the action can be constructed without crashing.
        assertDoesNotThrow(() -> {
            new GameOverAction(null, new java.util.concurrent.atomic.AtomicInteger(),
                    new javafx.scene.layout.AnchorPane(), () -> {});
        });
    }

    @Test
    void hasDamagePenaltyAmount_requiresPositivePenalty() {
        assertFalse(GameOverAction.hasDamagePenaltyAmount(0));
        assertTrue(GameOverAction.hasDamagePenaltyAmount(10));
    }

    // -----------------------------------------------------------------------
    private static final class RecordingAudio implements IAudioEngine {
        final List<String> loopCalls = new ArrayList<>();
        final List<String> stopCalls = new ArrayList<>();

        @Override public void play(String p) {}
        @Override public void playRateLimited(String p, String id, long ms) {}

        @Override
        public void playLoop(String resourcePath, String channelId) {
            loopCalls.add(channelId + ":" + resourcePath);
        }

        @Override
        public void stopChannel(String channelId) {
            stopCalls.add(channelId);
        }

        @Override public void dispose() {}
    }
}
