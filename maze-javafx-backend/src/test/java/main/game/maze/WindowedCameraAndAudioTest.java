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
import main.game.maze.javafx.render.FxGameRenderCoordinator;

/**
 * Locks in the JavaFX window/scroll/audio behavior the player relies on (F27):
 *  - Window is set to the board size, capped at screen resolution (never fullscreen).
 *  - Camera always follows the player whenever the world is larger than the viewport.
 *  - Game-over screen starts the GAME_OVER_MUSIC loop.
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

    // --- Camera follow: world fits viewport ---

    @Test
    void cameraStaysAtOriginWhenWorldFitsViewport() {
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(
                1920, 1080, 800, 600, 400, 300);
        assertEquals(0d, t[0], 0.0001);
        assertEquals(0d, t[1], 0.0001);
    }

    @Test
    void cameraStaysAtOriginWhenWorldExactlyFitsViewport() {
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(
                1920, 1080, 1920, 1080, 960, 540);
        assertEquals(0d, t[0], 0.0001);
        assertEquals(0d, t[1], 0.0001);
    }

    // --- Camera follow: world wider than viewport ---

    @Test
    void cameraFollowsPlayerWhenWorldIsWider() {
        // viewport 800×600, world 1600×600, player at (400, 300)
        // translateX = viewportWidth/2 - playerX = 400 - 400 = 0, clamped to [-800, 0] → 0
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(
                800, 600, 1600, 600, 400, 300);
        assertEquals(0d, t[0], 0.0001, "player at centre-left: board origin stays flush with viewport left");
        assertEquals(0d, t[1], 0.0001);
    }

    @Test
    void cameraFollowsPlayerAndClampsWhenNearRightEdge() {
        // viewport 800×600, world 1600×600, player at (1500, 300)
        // translateX = 400 - 1500 = -1100, clamped to [-800, 0] → -800
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(
                800, 600, 1600, 600, 1500, 300);
        assertEquals(800 - 1600, t[0], 0.0001, "player near right edge: clamp to far-edge flush");
    }

    @Test
    void cameraFollowsCentrePlayerHorizontally() {
        // viewport 800×600, world 2000×600, player at centre (1000, 300)
        // translateX = 400 - 1000 = -600, clamped to [-1200, 0] → -600
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(
                800, 600, 2000, 600, 1000, 300);
        assertEquals(800.0 / 2 - 1000, t[0], 0.0001, "player at centre: translation centres player");
    }

    // --- Camera follow: world taller than viewport ---

    @Test
    void cameraFollowsPlayerWhenWorldIsTaller() {
        // viewport 800×600, world 800×1200, player at (400, 300)
        // translateY = 300 - 300 = 0, clamped to [-600, 0] → 0
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(
                800, 600, 800, 1200, 400, 300);
        assertEquals(0d, t[0], 0.0001);
        assertEquals(0d, t[1], 0.0001, "player near top: board origin stays flush with viewport top");
    }

    @Test
    void cameraFollowsPlayerAndClampsWhenNearBottomEdge() {
        // viewport 800×600, world 800×1200, player at (400, 1100)
        // translateY = 300 - 1100 = -800, clamped to [-600, 0] → -600
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(
                800, 600, 800, 1200, 400, 1100);
        assertEquals(600 - 1200, t[1], 0.0001, "player near bottom: clamp to far-edge flush");
    }

    // --- Camera follow: both axes overflow ---

    @Test
    void cameraFollowsPlayerWhenBothAxesOverflow() {
        // viewport 800×600, world 1600×1200, player at (800, 600) = exact centre of world
        // translateX = 400 - 800 = -400, clamped to [-800, 0] → -400
        // translateY = 300 - 600 = -300, clamped to [-600, 0] → -300
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(
                800, 600, 1600, 1200, 800, 600);
        assertEquals(800.0 / 2 - 800, t[0], 0.0001, "x: centres on player");
        assertEquals(600.0 / 2 - 600, t[1], 0.0001, "y: centres on player");
    }

    @Test
    void cameraClampsToBothEdgesWhenPlayerNearFarCorner() {
        // viewport 800×600, world 1600×1200, player at (1500, 1100)
        // translateX = 400 - 1500 = -1100, clamped to [-800, 0] → -800
        // translateY = 300 - 1100 = -800, clamped to [-600, 0] → -600
        double[] t = FxGameRenderCoordinator.computeCameraTranslation(
                800, 600, 1600, 1200, 1500, 1100);
        assertEquals(800 - 1600, t[0], 0.0001);
        assertEquals(600 - 1200, t[1], 0.0001);
    }

    // --- Window sizing (F27): board clamped to screen, no fullscreen ---

    @Test
    void windowClampedToScreenWhenBoardFits() {
        int[] size = App.clampBoardToScreen(800, 600, 1920, 1080);
        assertEquals(800, size[0]);
        assertEquals(600, size[1]);
    }

    @Test
    void windowClampedToScreenWhenBoardEqualsScreen() {
        int[] size = App.clampBoardToScreen(1920, 1080, 1920, 1080);
        assertEquals(1920, size[0]);
        assertEquals(1080, size[1]);
    }

    @Test
    void windowClampedToScreenWidthWhenBoardWider() {
        int[] size = App.clampBoardToScreen(2000, 600, 1920, 1080);
        assertEquals(1920, size[0]);
        assertEquals(600, size[1]);
    }

    @Test
    void windowClampedToScreenHeightWhenBoardTaller() {
        int[] size = App.clampBoardToScreen(800, 1200, 1920, 1080);
        assertEquals(800, size[0]);
        assertEquals(1080, size[1]);
    }

    @Test
    void windowClampedToBothDimensionsWhenBoardLarger() {
        int[] size = App.clampBoardToScreen(2000, 1400, 1920, 1080);
        assertEquals(1920, size[0]);
        assertEquals(1080, size[1]);
    }

    // --- Audio ---

    @Test
    void gameOverMusicIsStartedOnGameOver() {
        GameOverAction.startGameOverMusic();
        assertTrue(fakeAudio.playedResources().contains(AudioResourceConstants.GameOverSound),
                "GameOverAction must start the GAME_OVER_MUSIC loop so the player hears the game-over track");
    }
}
