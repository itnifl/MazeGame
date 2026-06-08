package main.game.maze.libgdx.audio;

import java.util.concurrent.ThreadLocalRandom;
import main.game.maze.common.constants.AudioResourceConstants;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import main.game.maze.game.audio.GameAudioDirector;

/**
 * Encapsulates libGDX screen music transition policy.
 */
public final class GdxGameAudioCoordinator {

    private final GameAudioDirector gameAudioDirector;
    private final MazeVisualStyleConfig visualStyle;

    public GdxGameAudioCoordinator(GameAudioDirector gameAudioDirector, MazeVisualStyleConfig visualStyle) {
        this.gameAudioDirector = gameAudioDirector;
        this.visualStyle = visualStyle;
    }

    public void switchToInGameMusic() {
        gameAudioDirector.switchToInGameMusic(visualStyle.inGameMusicPath());
    }

    public void switchToMenuMusic() {
        gameAudioDirector.switchToMenuMusic(resolveMenuMusicPath());
    }

    public void switchToWinMusic() {
        gameAudioDirector.switchToWinMusic(visualStyle.winSoundPath());
    }

    public void switchToGameOverMusic() {
        gameAudioDirector.switchToGameOverMusic(AudioResourceConstants.GameOverSound);
    }

    public void stopAll() {
        gameAudioDirector.stopAll();
    }

    private String resolveMenuMusicPath() {
        String primary = visualStyle.menuMusicPath();
        String alternate = AudioResourceConstants.MenuMusicAlternate;
        return ThreadLocalRandom.current().nextBoolean() ? primary : alternate;
    }
}
