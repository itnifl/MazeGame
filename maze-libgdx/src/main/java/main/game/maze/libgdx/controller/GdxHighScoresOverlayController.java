package main.game.maze.libgdx.controller;

import main.game.maze.game.session.GameMode;
import main.game.maze.game.session.GameSession;

/**
 * Controller for high scores overlay mode transitions.
 */
public final class GdxHighScoresOverlayController {

    public boolean update(
            GameSession session,
            GdxModeInputController modeInputController,
            boolean escPressed,
            Runnable returnToMenuAction,
            Runnable returnToPlayAction) {
        if (session.mode() != GameMode.HIGH_SCORES) {
            return false;
        }
        if (modeInputController.consumeEsc(escPressed)) {
            if (session.highScoresReturnToStartMenu()) {
                session.setHighScoresReturnToStartMenu(false);
                returnToMenuAction.run();
            } else {
                returnToPlayAction.run();
            }
        }
        return true;
    }
}


