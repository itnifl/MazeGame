package main.game.maze.javafx.audio;

import main.game.maze.common.constants.AudioResourceConstants;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import main.game.maze.game.audio.GameAudioDirector;

/**
 * Handles audio transitions for JavaFX game modes, adapting the shared
 * {@link GameAudioDirector} to the active {@link MazeVisualStyleConfig}.
 */
public final class FxGameAudioCoordinator {

    private final GameAudioDirector director;
    private final MazeVisualStyleConfig styleConfig;

    public FxGameAudioCoordinator(MazeVisualStyleConfig styleConfig) {
        this.director = new GameAudioDirector(AudioEngine::get);
        this.styleConfig = styleConfig;
    }

    public void playMenuMusic() {
        director.switchToMenuMusic(styleConfig.menuMusicPath());
    }

    public void playInGameMusic() {
        director.switchToInGameMusic(styleConfig.inGameMusicPath());
    }

    public void playWinMusic() {
        director.switchToWinMusic(styleConfig.winSoundPath());
    }

    public void playGameOverMusic() {
        director.switchToGameOverMusic(AudioResourceConstants.GameOverSound);
    }
}
