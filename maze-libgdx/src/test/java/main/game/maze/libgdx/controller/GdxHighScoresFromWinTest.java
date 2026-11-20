package main.game.maze.libgdx.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import main.game.maze.game.session.GameMode;
import main.game.maze.game.session.GameSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("High scores opened from the win screen: Esc goes back to the win screen when a next level follows")
class GdxHighScoresFromWinTest {

    private final GdxHighScoresOverlayController controller = new GdxHighScoresOverlayController();
    private final GdxModeInputController modeInput = new GdxModeInputController();

    @Test
    @DisplayName("Esc returns to the win screen, so Next Level can still be chosen after saving the score")
    void escReturnsToTheWinScreen() {
        GameSession session = new GameSession();
        session.setMode(GameMode.HIGH_SCORES);
        session.setHighScoresReturnToWin(true);

        boolean[] menuCalled = {false};
        boolean[] playCalled = {false};
        controller.update(session, modeInput, true, () -> menuCalled[0] = true, () -> playCalled[0] = true);

        assertEquals(GameMode.WON, session.mode());
        assertFalse(menuCalled[0], "not the main menu");
        assertFalse(playCalled[0]);
        assertFalse(session.highScoresReturnToWin(), "the flag is used up");
    }

    @Test
    @DisplayName("without the flag Esc still goes to the main menu (last level, or high scores from the menu)")
    void escWithoutTheFlagGoesToTheMenu() {
        GameSession session = new GameSession();
        session.setMode(GameMode.HIGH_SCORES);
        session.setHighScoresReturnToStartMenu(true);

        boolean[] menuCalled = {false};
        controller.update(session, modeInput, true, () -> menuCalled[0] = true, () -> { });

        assertEquals(true, menuCalled[0]);
    }
}
