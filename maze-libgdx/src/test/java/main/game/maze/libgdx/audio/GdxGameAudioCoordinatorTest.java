package main.game.maze.libgdx.audio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.game.maze.common.constants.AudioResourceConstants;
import main.game.maze.common.graphics.IAudioEngine;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import main.game.maze.constants.AudioChannelConstants;
import main.game.maze.game.audio.GameAudioDirector;
import org.junit.jupiter.api.Test;

/**
 * Verifies SR-40: libGDX audio transitions are encapsulated behind a coordinator
 * that forwards the correct resource paths to {@link GameAudioDirector} per game
 * mode. Uses a recording {@link IAudioEngine} fake; no real audio backend.
 */
class GdxGameAudioCoordinatorTest {

    /** Records the most recent loop path per channel and any one-shot plays. */
    private static final class RecordingAudioEngine implements IAudioEngine {
        final Map<String, String> loopsByChannel = new HashMap<>();
        final List<String> stoppedChannels = new ArrayList<>();
        final List<String> oneShotPlays = new ArrayList<>();

        @Override
        public void play(String resourcePath) {
            oneShotPlays.add(resourcePath);
        }

        @Override
        public void playRateLimited(String resourcePath, String soundId, long cooldownMs) {
            // not exercised by the coordinator
        }

        @Override
        public void playLoop(String resourcePath, String channelId) {
            loopsByChannel.put(channelId, resourcePath);
        }

        @Override
        public void stopChannel(String channelId) {
            stoppedChannels.add(channelId);
        }

        @Override
        public void dispose() {
            // no-op
        }
    }

    private static GdxGameAudioCoordinator coordinator(RecordingAudioEngine engine, MazeVisualStyleConfig style) {
        GameAudioDirector director = new GameAudioDirector(() -> engine);
        return new GdxGameAudioCoordinator(director, style);
    }

    @Test
    void switchToInGameMusicLoopsConfiguredInGamePath() {
        RecordingAudioEngine engine = new RecordingAudioEngine();
        MazeVisualStyleConfig style = MazeVisualStyleConfig.DEFAULT;

        coordinator(engine, style).switchToInGameMusic();

        assertEquals(style.inGameMusicPath(), engine.loopsByChannel.get(AudioChannelConstants.IN_GAME_MUSIC));
    }

    @Test
    void switchToWinMusicLoopsWinPathAndPlaysWinComment() {
        RecordingAudioEngine engine = new RecordingAudioEngine();
        MazeVisualStyleConfig style = MazeVisualStyleConfig.DEFAULT;

        coordinator(engine, style).switchToWinMusic();

        assertEquals(style.winSoundPath(), engine.loopsByChannel.get(AudioChannelConstants.WIN_MUSIC));
        assertTrue(engine.oneShotPlays.contains(AudioResourceConstants.WinGameSoundComment),
                "win transition should play the win comment one-shot");
    }

    @Test
    void switchToGameOverMusicLoopsGameOverResource() {
        RecordingAudioEngine engine = new RecordingAudioEngine();

        coordinator(engine, MazeVisualStyleConfig.DEFAULT).switchToGameOverMusic();

        assertEquals(AudioResourceConstants.GameOverSound,
                engine.loopsByChannel.get(AudioChannelConstants.GAME_OVER_MUSIC));
    }

    @Test
    void switchToMenuMusicLoopsOneOfTheTwoMenuTracks() {
        RecordingAudioEngine engine = new RecordingAudioEngine();
        MazeVisualStyleConfig style = MazeVisualStyleConfig.DEFAULT;

        coordinator(engine, style).switchToMenuMusic();

        String played = engine.loopsByChannel.get(AudioChannelConstants.MENU_MUSIC);
        assertTrue(style.menuMusicPath().equals(played) || AudioResourceConstants.MenuMusicAlternate.equals(played),
                "menu music must resolve to the primary or alternate menu track, was: " + played);
    }

    @Test
    void stopAllStopsEveryMusicChannel() {
        RecordingAudioEngine engine = new RecordingAudioEngine();

        coordinator(engine, MazeVisualStyleConfig.DEFAULT).stopAll();

        assertTrue(engine.stoppedChannels.contains(AudioChannelConstants.IN_GAME_MUSIC));
        assertTrue(engine.stoppedChannels.contains(AudioChannelConstants.MENU_MUSIC));
        assertTrue(engine.stoppedChannels.contains(AudioChannelConstants.WIN_MUSIC));
        assertTrue(engine.stoppedChannels.contains(AudioChannelConstants.GAME_OVER_MUSIC));
    }
}
