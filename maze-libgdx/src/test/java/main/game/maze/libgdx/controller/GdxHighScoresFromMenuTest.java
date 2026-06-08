package main.game.maze.libgdx.controller;

import main.game.maze.game.session.GameMode;
import main.game.maze.game.session.GameSession;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies ESC navigation and session state for the high-scores overlay
 * when opened from the start menu (no active game).
 */
class GdxHighScoresFromMenuTest {

    private final GdxHighScoresOverlayController controller = new GdxHighScoresOverlayController();
    private final GdxModeInputController modeInput = new GdxModeInputController();

    @Test
    void escFromMenuHighScoresCallsReturnToMenuAction() {
        GameSession session = new GameSession();
        session.setMode(GameMode.HIGH_SCORES);
        session.setHighScoresReturnToStartMenu(true);

        boolean[] menuCalled = {false};
        boolean[] playCalled = {false};

        controller.update(session, modeInput, true, () -> menuCalled[0] = true, () -> playCalled[0] = true);

        assertTrue(menuCalled[0], "ESC when returnToStartMenu=true must invoke the return-to-menu action");
        assertFalse(playCalled[0], "ESC when returnToStartMenu=true must NOT invoke return-to-play");
    }

    @Test
    void escFromGameHighScoresCallsReturnToPlayAction() {
        GameSession session = new GameSession();
        session.setMode(GameMode.HIGH_SCORES);
        session.setHighScoresReturnToStartMenu(false);

        boolean[] menuCalled = {false};
        boolean[] playCalled = {false};

        controller.update(session, modeInput, true, () -> menuCalled[0] = true, () -> playCalled[0] = true);

        assertFalse(menuCalled[0], "ESC when returnToStartMenu=false must NOT invoke return-to-menu");
        assertTrue(playCalled[0], "ESC when returnToStartMenu=false must invoke return-to-play");
    }

    @Test
    void escFromMenuHighScoresClearsReturnToStartMenuFlag() {
        GameSession session = new GameSession();
        session.setMode(GameMode.HIGH_SCORES);
        session.setHighScoresReturnToStartMenu(true);

        controller.update(session, modeInput, true, () -> {}, () -> {});

        assertFalse(session.highScoresReturnToStartMenu(),
                "ESC must clear the returnToStartMenu flag after routing");
    }

    @Test
    void noEscPressDoesNotChangeSession() {
        GameSession session = new GameSession();
        session.setMode(GameMode.HIGH_SCORES);
        session.setHighScoresReturnToStartMenu(true);

        boolean[] menuCalled = {false};
        controller.update(session, modeInput, false, () -> menuCalled[0] = true, () -> {});

        assertFalse(menuCalled[0], "No ESC press must not trigger routing");
        assertEquals(GameMode.HIGH_SCORES, session.mode(), "Mode must stay HIGH_SCORES when ESC not pressed");
    }

    @Test
    void updateReturnsTrueOnlyInHighScoresMode() {
        GameSession session = new GameSession();
        session.setMode(GameMode.PLAYING);

        boolean result = controller.update(session, modeInput, false, () -> {}, () -> {});

        assertFalse(result, "update() must return false when not in HIGH_SCORES mode");
    }
}
