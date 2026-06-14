package main.game.maze.javafx.audio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.IAudioEngine;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import main.game.maze.constants.AudioChannelConstants;
import main.game.maze.common.constants.AudioResourceConstants;

class FxGameAudioCoordinatorTest {

    private RecordingAudioEngine mockEngine;

    @BeforeEach
    void setUp() {
        mockEngine = new RecordingAudioEngine();
        AudioEngine.set(mockEngine);
    }

    @AfterEach
    void tearDown() {
        AudioEngine.reset();
    }

    @Test
    void playInGameMusicRoutesCorrectly() {
        MazeVisualStyleConfig config = new MazeVisualStyleConfig(
                "easy", "norm", "hard",
                "icon", "goal", "easyWall", "normWall", "hardWall", 
                "menu.mp3", "select.wav", "custom-in-game.mp3", "win.mp3");
                
        FxGameAudioCoordinator coordinator = new FxGameAudioCoordinator(config);
        coordinator.playInGameMusic();
        
        assertTrue(mockEngine.stoppedChannels.contains(AudioChannelConstants.MENU_MUSIC));
        assertEquals("custom-in-game.mp3", mockEngine.lastPlayedLoop);
        assertEquals(AudioChannelConstants.IN_GAME_MUSIC, mockEngine.lastPlayedLoopChannel);
    }

    @Test
    void playGameOverMusicRoutesCorrectly() {
        MazeVisualStyleConfig config = MazeVisualStyleConfig.DEFAULT;
        FxGameAudioCoordinator coordinator = new FxGameAudioCoordinator(config);
        coordinator.playGameOverMusic();
        
        assertTrue(mockEngine.stoppedChannels.contains(AudioChannelConstants.IN_GAME_MUSIC));
        assertEquals(AudioResourceConstants.GameOverSound, mockEngine.lastPlayedLoop);
        assertEquals(AudioChannelConstants.GAME_OVER_MUSIC, mockEngine.lastPlayedLoopChannel);
    }

    private static class RecordingAudioEngine implements IAudioEngine {
        final java.util.List<String> stoppedChannels = new java.util.ArrayList<>();
        String lastPlayedLoop;
        String lastPlayedLoopChannel;

        public void init() {}
        @Override public void dispose() {}
        @Override public void play(String soundName) {}
        @Override public void playRateLimited(String soundName, String soundId, long cooldown) {}
        
        @Override 
        public void playLoop(String soundName, String channel) {
            lastPlayedLoop = soundName;
            lastPlayedLoopChannel = channel;
        }
        
        @Override 
        public void stopChannel(String channel) {
            stoppedChannels.add(channel);
        }
    }
}
