package main.game.maze;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.game.maze.actions.GameOverAction;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.NoopAudioEngine;
import main.game.maze.common.constants.AudioResourceConstants;

/**
 * Locks in the JavaFX window/scroll/audio behavior the player relies on:
 *  - Camera follows the player only in fullscreen; in windowed mode the board
 *    stays at (0,0) so the map always fits inside the window.
 *  - When the chosen board is bigger than the player's screen, fullscreen is
 *    auto-enabled.
 *  - Game-over screen starts the GAME_OVER_MUSIC loop (regression: previously
 *    relied on FXML controller init which could be fragile).
 */
class WindowedCameraAndAudioTest {

    private NoopAudioEngine fakeAudio;

    @BeforeEach
    void installFakeAudio() {
        fakeAudio = new NoopAudioEngine();
        AudioEngine.set(fakeAudio);
    }

    @AfterEach
    void restoreAudio() {
        AudioEngine.reset();
    }

    @Test
    void cameraDoesNotScrollWhenNotFullscreen() {
        // World larger than viewport, but stage is windowed -> no translation.
        double[] t = GameController.computeCameraTranslation(
                800, 600,
                1600, 1200,
                1500, 1100,
                false);
        assertEquals(0d, t[0], 0.0001);
        assertEquals(0d, t[1], 0.0001);
    }

    @Test
    void cameraScrollsAndClampsWhenFullscreen() {
        // Player near the far corner: translation clamps so the world's far
        // edge sits flush with the viewport edge (map never scrolls past).
        double[] t = GameController.computeCameraTranslation(
                800, 600,
                1600, 1200,
                1500, 1100,
                true);
        assertEquals(800 - 1600, t[0], 0.0001, "x clamps to viewport-world");
        assertEquals(600 - 1200, t[1], 0.0001, "y clamps to viewport-world");

        // Player near origin: translation clamps to 0 so the world's origin
        // edge sits flush with the viewport origin.
        double[] t2 = GameController.computeCameraTranslation(
                800, 600,
                1600, 1200,
                10, 10,
                true);
        assertEquals(0d, t2[0], 0.0001);
        assertEquals(0d, t2[1], 0.0001);
    }

    @Test
    void cameraDoesNotScrollWhenWorldFitsViewport() {
        // Even in fullscreen, if the world fits the viewport, no translation.
        double[] t = GameController.computeCameraTranslation(
                1920, 1080,
                800, 600,
                400, 300,
                true);
        assertEquals(0d, t[0], 0.0001);
        assertEquals(0d, t[1], 0.0001);
    }

    @Test
    void needsFullscreenWhenBoardExceedsScreen() {
        assertTrue(App.needsFullscreenForBoard(2000, 1500, 1920, 1080));
        assertTrue(App.needsFullscreenForBoard(1000, 1500, 1920, 1080));
        assertTrue(App.needsFullscreenForBoard(2000, 800, 1920, 1080));
    }

    @Test
    void doesNotNeedFullscreenWhenBoardFitsScreen() {
        assertFalse(App.needsFullscreenForBoard(1000, 700, 1920, 1080));
        assertFalse(App.needsFullscreenForBoard(1920, 1080, 1920, 1080));
    }

    @Test
    void gameOverMusicIsStartedOnGameOver() {
        GameOverAction.startGameOverMusic();
        assertTrue(fakeAudio.playedResources().contains(AudioResourceConstants.GameOverSound),
                "GameOverAction must start the GAME_OVER_MUSIC loop so the player hears the game-over track");
    }
}
