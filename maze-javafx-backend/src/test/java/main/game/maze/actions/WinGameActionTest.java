package main.game.maze.actions;

import main.game.maze.App;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.IAudioEngine;
import main.game.maze.constants.AudioChannelConstants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;

class WinGameActionTest {

    @BeforeAll
    static void initFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        assertTrue(latch.await(2, TimeUnit.SECONDS), "JavaFX startup timed out");
    }

    @AfterEach
    void cleanup() {
        AudioEngine.reset();
        App.gameController = null;
    }

    // WinGameAction can be constructed without throwing.
    @Test
    void constructor_doesNotThrow() {
        assertDoesNotThrow(() ->
                new WinGameAction(null, new AtomicInteger(), new AnchorPane(), () -> {}));
    }

    // WinGame() when root has no scene attached must not crash, and music must be stopped.
    @Test
    void winGame_withoutScene_doesNotTriggerAudio() throws Exception {
        RecordingAudio audio = new RecordingAudio();
        AudioEngine.set(audio);

        // Provide a real PlayerCharacter so updateScore() does not NPE.
        PlayerCharacter player = new PlayerCharacter(new javafx.scene.image.ImageView(), 0, 0, null);
        WinGameAction action = new WinGameAction(player, new AtomicInteger(), new AnchorPane(), () -> {});

        // Call on FX thread; with null root it should return early after stopping music.
        CountDownLatch done = new CountDownLatch(1);
        final Throwable[] error = {null};
        Platform.runLater(() -> {
            try {
                action.WinGame();
            } catch (Throwable t) {
                error[0] = t;
            } finally {
                done.countDown();
            }
        });
        assertTrue(done.await(5, TimeUnit.SECONDS), "FX task timed out");
        if (error[0] != null) throw new RuntimeException(error[0]);

        // stopChannel(IN_GAME_MUSIC) is called before the scene null-check, so we can assert it.
        assertTrue(audio.stopCalls.contains(AudioChannelConstants.IN_GAME_MUSIC),
                "WinGame must stop in-game music even when there is no scene");
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
