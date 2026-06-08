package main.game.maze.game.audio;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.common.graphics.IAudioEngine;
import main.game.maze.constants.AudioChannelConstants;
import main.game.maze.common.constants.AudioResourceConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GameAudioDirectorTest {

    @Test
    void switchToInGameStopsOtherChannelsThenLoopsInGameMusic() {
        RecordingAudioEngine engine = new RecordingAudioEngine();
        GameAudioDirector director = new GameAudioDirector(() -> engine);

        director.switchToInGameMusic("/music/ingame.wav");

        assertTrue(engine.events.contains("stop:" + AudioChannelConstants.MENU_MUSIC));
        assertTrue(engine.events.contains("stop:" + AudioChannelConstants.WIN_MUSIC));
        assertTrue(engine.events.contains("stop:" + AudioChannelConstants.GAME_OVER_MUSIC));
        assertTrue(engine.events.contains("loop:/music/ingame.wav:" + AudioChannelConstants.IN_GAME_MUSIC));
    }

    @Test
    void switchToWinMusicPlaysWinComment() {
        RecordingAudioEngine engine = new RecordingAudioEngine();
        GameAudioDirector director = new GameAudioDirector(() -> engine);

        director.switchToWinMusic("/music/win.wav");

        assertTrue(engine.events.contains("loop:/music/win.wav:" + AudioChannelConstants.WIN_MUSIC));
        assertTrue(engine.events.contains("play:" + AudioResourceConstants.WinGameSoundComment));
    }

    private static final class RecordingAudioEngine implements IAudioEngine {
        private final List<String> events = new ArrayList<>();

        @Override
        public void play(String resourcePath) {
            events.add("play:" + resourcePath);
        }

        @Override
        public void playRateLimited(String resourcePath, String soundId, long cooldownMs) {
            events.add("playRateLimited:" + resourcePath + ":" + soundId + ":" + cooldownMs);
        }

        @Override
        public void playLoop(String resourcePath, String channelId) {
            events.add("loop:" + resourcePath + ":" + channelId);
        }

        @Override
        public void stopChannel(String channelId) {
            events.add("stop:" + channelId);
        }

        @Override
        public void dispose() {
            events.add("dispose");
        }
    }
}
