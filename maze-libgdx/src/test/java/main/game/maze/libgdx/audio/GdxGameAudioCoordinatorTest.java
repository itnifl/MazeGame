package main.game.maze.libgdx.audio;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.game.maze.common.constants.AudioResourceConstants;
import main.game.maze.common.graphics.AudioEngine;
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

    // --- BUG-3 regression: menu music was silent on initial load (MenuScreenController.show()
    //     was the first audio call but GdxBackend.install() hadn't been called yet, so
    //     AudioEngine.get() returned NoopAudioEngine). Fix: GdxGame.create() calls
    //     GdxBackend.install() before routing to the first screen, and MenuScreenController.show()
    //     calls audioCoordinator.switchToMenuMusic(). ---

    @Test
    void switchToMenuMusicStopsInGameMusicBeforeStarting() {
        RecordingAudioEngine engine = new RecordingAudioEngine();

        coordinator(engine, MazeVisualStyleConfig.DEFAULT).switchToMenuMusic();

        int stopIndex = engine.stoppedChannels.indexOf(AudioChannelConstants.IN_GAME_MUSIC);
        assertTrue(stopIndex >= 0, "in-game music channel must be stopped before menu music starts");
        // The loop must be started after the stop — its index in stoppedChannels is found above;
        // the loop entry appears in loopsByChannel only after all stops were issued.
        assertTrue(engine.loopsByChannel.containsKey(AudioChannelConstants.MENU_MUSIC),
                "menu music loop must be started on the MENU_MUSIC channel");
    }

    @Test
    void switchToMenuMusic_isStartedAsLoopNotOneShot() {
        RecordingAudioEngine engine = new RecordingAudioEngine();
        MazeVisualStyleConfig style = MazeVisualStyleConfig.DEFAULT;

        coordinator(engine, style).switchToMenuMusic();

        // Menu music must loop, not play once and stop.
        assertTrue(engine.loopsByChannel.containsKey(AudioChannelConstants.MENU_MUSIC),
                "menu music must be registered as a loop so it does not stop after one play-through");
        // The music path must NOT appear in one-shot plays.
        assertFalse(engine.oneShotPlays.contains(style.menuMusicPath()),
                "menu music path must not be played as a one-shot");
        assertFalse(engine.oneShotPlays.contains(
                main.game.maze.common.constants.AudioResourceConstants.MenuMusicAlternate),
                "alternate menu music path must not be played as a one-shot");
    }

    @Test
    void switchToMenuMusic_afterEngineInstalledOnAudioEngine_musicIsDelivered() {
        // Simulates GdxGame.create() having called GdxBackend.install() so that
        // AudioEngine.get() returns a real (recording) engine before MenuScreenController.show().
        RecordingAudioEngine engine = new RecordingAudioEngine();
        AudioEngine.set(engine);
        try {
            GameAudioDirector director = new GameAudioDirector(AudioEngine::get);
            GdxGameAudioCoordinator coord = new GdxGameAudioCoordinator(director, MazeVisualStyleConfig.DEFAULT);
            coord.switchToMenuMusic();
            assertTrue(engine.loopsByChannel.containsKey(AudioChannelConstants.MENU_MUSIC),
                    "menu music must reach the engine when it is installed before the menu is shown");
        } finally {
            AudioEngine.reset();
        }
    }
}
