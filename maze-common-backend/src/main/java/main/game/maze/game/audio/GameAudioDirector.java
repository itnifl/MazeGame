package main.game.maze.game.audio;

import java.util.Objects;
import java.util.function.Supplier;
import main.game.maze.common.graphics.IAudioEngine;
import main.game.maze.constants.AudioChannelConstants;
import main.game.maze.common.constants.AudioResourceConstants;

/**
 * Shared audio transition orchestration for game modes.
 */
public final class GameAudioDirector {

    private final Supplier<IAudioEngine> engineSupplier;

    public GameAudioDirector(Supplier<IAudioEngine> engineSupplier) {
        this.engineSupplier = Objects.requireNonNull(engineSupplier, "engineSupplier must not be null");
    }

    public void switchToInGameMusic(String inGameMusicPath) {
        IAudioEngine engine = engineSupplier.get();
        engine.stopChannel(AudioChannelConstants.MENU_MUSIC);
        engine.stopChannel(AudioChannelConstants.WIN_MUSIC);
        engine.stopChannel(AudioChannelConstants.GAME_OVER_MUSIC);
        engine.stopChannel(AudioChannelConstants.IN_GAME_MUSIC);
        engine.playLoop(inGameMusicPath, AudioChannelConstants.IN_GAME_MUSIC);
    }

    public void switchToMenuMusic(String menuMusicPath) {
        IAudioEngine engine = engineSupplier.get();
        engine.stopChannel(AudioChannelConstants.IN_GAME_MUSIC);
        engine.stopChannel(AudioChannelConstants.WIN_MUSIC);
        engine.stopChannel(AudioChannelConstants.GAME_OVER_MUSIC);
        engine.stopChannel(AudioChannelConstants.MENU_MUSIC);
        engine.playLoop(menuMusicPath, AudioChannelConstants.MENU_MUSIC);
    }

    public void switchToWinMusic(String winMusicPath) {
        IAudioEngine engine = engineSupplier.get();
        engine.stopChannel(AudioChannelConstants.IN_GAME_MUSIC);
        engine.playLoop(winMusicPath, AudioChannelConstants.WIN_MUSIC);
        engine.play(AudioResourceConstants.WinGameSoundComment);
    }

    public void switchToGameOverMusic(String gameOverMusicPath) {
        IAudioEngine engine = engineSupplier.get();
        engine.stopChannel(AudioChannelConstants.IN_GAME_MUSIC);
        engine.playLoop(gameOverMusicPath, AudioChannelConstants.GAME_OVER_MUSIC);
    }

    public void stopAll() {
        IAudioEngine engine = engineSupplier.get();
        engine.stopChannel(AudioChannelConstants.IN_GAME_MUSIC);
        engine.stopChannel(AudioChannelConstants.MENU_MUSIC);
        engine.stopChannel(AudioChannelConstants.WIN_MUSIC);
        engine.stopChannel(AudioChannelConstants.GAME_OVER_MUSIC);
    }
}
