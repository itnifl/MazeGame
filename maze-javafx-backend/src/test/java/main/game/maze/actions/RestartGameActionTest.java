package main.game.maze.actions;

import main.game.maze.App;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.game.maze.actions.RestartGameAction;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.IAudioEngine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/** Tests RestartGameAction behavior on the FX thread. */
public class RestartGameActionTest {

    @AfterEach
    void restoreAudioEngine() {
        AudioEngine.reset();
        App.gameController = null;
    }

    @BeforeAll
    static void initFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        latch.await(2, TimeUnit.SECONDS);
    }

    /** Run a task on the FX thread and surface any thrown errors to the test thread. */
    private static void runOnFx(Runnable r) throws Exception {
        CountDownLatch done = new CountDownLatch(1);
        final Throwable[] err = new Throwable[1];
        Platform.runLater(() -> {
            try { r.run(); } catch (Throwable t) { err[0] = t; } finally { done.countDown(); }
        });
        assertTrue(done.await(5, TimeUnit.SECONDS), "FX task timed out");
        if (err[0] != null) {
            if (err[0] instanceof RuntimeException re) throw re;
            if (err[0] instanceof Error e) throw e;
            throw new RuntimeException(err[0]);
        }
    }

    private static void ensureAudioReady() {
        // Background music now routes via AudioEngine channels in App/RestartGameAction.
    }

    @Test
    @DisplayName("Load leaves audio untouched when no scene is attached")
    void loadWithoutSceneDoesNotRestartMusic() {
        RecordingAudioEngine audio = new RecordingAudioEngine();
        AudioEngine.set(audio);

        RestartGameAction action = new RestartGameAction(new AnchorPane());
        action.Load();

        assertEquals(0, audio.stopCalls);
        assertEquals(0, audio.loopCalls);
    }

    @Test
    @DisplayName("Load replaces the content root once")
    @org.junit.jupiter.api.Disabled("Not ready yet: requires refactor to allow injecting a mock GameController")
    void loadReplacesRoot() throws Exception {
        runOnFx(() -> {
            try {
                ensureAudioReady();

                AnchorPane oldRoot = new AnchorPane();
                Stage stage = new Stage();
                stage.setScene(new Scene(oldRoot, 800, 600));

                RestartGameAction action = new RestartGameAction(oldRoot);
                action.Load();

                assertEquals(1, oldRoot.getChildren().size(),
                        "RestartGameAction should replace content with a single new child/root");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    @DisplayName("Repeated Load calls do not accumulate children")
    @org.junit.jupiter.api.Disabled("Not ready yet: requires refactor to allow injecting a mock GameController")
    void repeatedLoadDoesNotAccumulateChildren() throws Exception {
        runOnFx(() -> {
            try {
                ensureAudioReady();

                AnchorPane oldRoot = new AnchorPane();
                Stage stage = new Stage();
                stage.setScene(new Scene(oldRoot, 800, 600));

                RestartGameAction action = new RestartGameAction(oldRoot);
                action.Load();
                action.Load(); // intentionally twice

                assertEquals(1, oldRoot.getChildren().size(),
                        "Content must remain a single child after repeated Load()");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static final class RecordingAudioEngine implements IAudioEngine {
        private int loopCalls;
        private int stopCalls;

        @Override
        public void play(String resourcePath) {}

        @Override
        public void playRateLimited(String resourcePath, String soundId, long cooldownMs) {}

        @Override
        public void playLoop(String resourcePath, String channelId) {
            loopCalls++;
        }

        @Override
        public void stopChannel(String channelId) {
            stopCalls++;
        }

        @Override
        public void dispose() {}
    }
}


