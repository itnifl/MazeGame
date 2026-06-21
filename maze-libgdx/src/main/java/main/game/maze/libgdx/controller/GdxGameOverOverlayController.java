package main.game.maze.libgdx.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.OrthographicCamera;
import main.game.maze.game.session.GameMode;
import main.game.maze.game.session.GameSession;
import main.game.maze.libgdx.view.GdxGameOverOverlayView;

/**
 * Controller for game over overlay mode transitions.
 */
public final class GdxGameOverOverlayController {

    private float restartButtonX;
    private float restartButtonY;
    private float restartButtonW;
    private float restartButtonH;
    private float backButtonX;
    private float backButtonY;
    private float backButtonW;
    private float backButtonH;

    public void onOverlayRendered(GdxGameOverOverlayView.GameOverButtons buttons) {
        restartButtonX = buttons.restartX();
        restartButtonY = buttons.restartY();
        restartButtonW = buttons.restartW();
        restartButtonH = buttons.restartH();
        backButtonX = buttons.backX();
        backButtonY = buttons.backY();
        backButtonW = buttons.backW();
        backButtonH = buttons.backH();
    }

    public boolean update(
            GameSession session,
            GdxModeInputController modeInputController,
            boolean escPressed,
            OrthographicCamera hudCamera,
            Runnable restartAction,
            Runnable returnToMenuAction) {
        if (session.mode() != GameMode.GAME_OVER) {
            return false;
        }
        handleMouseInput(hudCamera, restartAction, returnToMenuAction);
        if (modeInputController.consumeEsc(escPressed)) {
            returnToMenuAction.run();
        }
        return true;
    }

    private void handleMouseInput(
            OrthographicCamera hudCamera,
            Runnable restartAction,
            Runnable returnToMenuAction) {
        if (!Gdx.input.isButtonJustPressed(Buttons.LEFT) || hudCamera == null) {
            return;
        }
        float mx = Gdx.input.getX();
        float my = toHudY(hudCamera.viewportHeight, Gdx.input.getY());
        if (containsPoint(mx, my, restartButtonX, restartButtonY, restartButtonW, restartButtonH)) {
            restartAction.run();
            return;
        }
        if (containsPoint(mx, my, backButtonX, backButtonY, backButtonW, backButtonH)) {
            returnToMenuAction.run();
        }
    }

    static float toHudY(float viewportHeight, int screenY) {
        return viewportHeight - screenY;
    }

    static boolean containsPoint(float px, float py, float x, float y, float w, float h) {
        return px >= x && px <= x + w && py >= y && py <= y + h;
    }
}
