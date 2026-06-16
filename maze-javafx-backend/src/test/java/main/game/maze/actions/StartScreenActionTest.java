package main.game.maze.actions;

import main.game.maze.App;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.IAudioEngine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.AnchorPane;

import static org.junit.jupiter.api.Assertions.*;

class StartScreenActionTest {

    @AfterEach
    void cleanup() {
        AudioEngine.reset();
        App.gameController = null;
    }

    // When root has no scene, Load() must return early without any audio calls.
    @Test
    void load_withoutScene_doesNotCallAudio() {
        RecordingAudio audio = new RecordingAudio();
        AudioEngine.set(audio);

        StartScreenAction action = new StartScreenAction(new AnchorPane());
        action.Load();

        assertEquals(0, audio.stopCalls.size(),
                "Load() must not call stopChannel when root has no attached scene");
    }

    // When root is null, Load() must return early without throwing.
    @Test
    void load_withNullRoot_doesNotThrow() {
        assertDoesNotThrow(() -> new StartScreenAction(null).Load());
    }

    // Constructor with a valid AnchorPane does not throw.
    @Test
    void constructor_withValidRoot_doesNotThrow() {
        assertDoesNotThrow(() -> new StartScreenAction(new AnchorPane()));
    }

    // -----------------------------------------------------------------------
    private static final class RecordingAudio implements IAudioEngine {
        final List<String> stopCalls = new ArrayList<>();
        final List<String> loopCalls = new ArrayList<>();

        @Override public void play(String p) {}
        @Override public void playRateLimited(String p, String id, long ms) {}
        @Override public void playLoop(String r, String ch) { loopCalls.add(ch); }

        @Override
        public void stopChannel(String channelId) {
            stopCalls.add(channelId);
        }

        @Override public void dispose() {}
    }
}
