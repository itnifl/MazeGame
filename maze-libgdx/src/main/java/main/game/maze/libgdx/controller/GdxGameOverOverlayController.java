package main.game.maze.libgdx.controller;

import main.game.maze.game.session.GameMode;
import main.game.maze.game.session.GameSession;

/**
 * Controller for game over overlay mode transitions.
 */
public final class GdxGameOverOverlayController {

    public boolean update(
            GameSession session,
            GdxModeInputController modeInputController,
            boolean escPressed,
            Runnable returnToMenuAction) {
        if (session.mode() != GameMode.GAME_OVER) {
            return false;
        }
        if (modeInputController.consumeEsc(escPressed)) {
            returnToMenuAction.run();
        }
        return true;
    }
}
