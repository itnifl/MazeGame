package main.game.maze.libgdx.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.OrthographicCamera;
import main.game.maze.game.score.HighScoreRepository;
import main.game.maze.game.session.GameMode;
import main.game.maze.game.session.GameSession;
import main.game.maze.libgdx.helper.GdxWinScoreSupport;
import main.game.maze.libgdx.view.GdxWinOverlayView;

/**
 * Controller for win overlay input and save flow.
 */
public final class GdxWinOverlayController {

    private String winScoreStatus = "";
    private final StringBuilder winNameInput = new StringBuilder();
    private float winSaveButtonX;
    private float winSaveButtonY;
    private float winSaveButtonW;
    private float winSaveButtonH;
    private float winBackButtonX;
    private float winBackButtonY;
    private float winBackButtonW;
    private float winBackButtonH;

    public void reset(GameSession session) {
        winNameInput.setLength(0);
        session.setWinScoreSaved(false);
        winScoreStatus = "";
    }

    public String winNameInputText() {
        return winNameInput.toString();
    }

    public String winScoreStatusText() {
        return winScoreStatus;
    }

    public boolean onKeyTyped(char character, int maxNameChars) {
        if (character < 32 || character == 127) {
            return false;
        }
        return GdxWinScoreSupport.appendCharacter(winNameInput, character, maxNameChars);
    }

    public void onOverlayRendered(GdxWinOverlayView.WinButtons buttons) {
        winSaveButtonX = buttons.saveX();
        winSaveButtonY = buttons.saveY();
        winSaveButtonW = buttons.saveW();
        winSaveButtonH = buttons.saveH();
        winBackButtonX = buttons.backX();
        winBackButtonY = buttons.backY();
        winBackButtonW = buttons.backW();
        winBackButtonH = buttons.backH();
    }

    public boolean update(
            GameSession session,
            GdxModeInputController modeInputController,
            OrthographicCamera hudCamera,
            HighScoreRepository highScoreRepository,
            int score,
            Runnable returnToMenuAction,
            Runnable openHighScoresAction) {
        if (session.mode() != GameMode.WON) {
            return false;
        }

        if (!session.winScoreSaved()) {
            handleWinScoreEntryInput(highScoreRepository, score, session, openHighScoresAction);
        }

        handleWonMouseInput(session, hudCamera, highScoreRepository, score, returnToMenuAction, openHighScoresAction);

        if (modeInputController.consumeEsc(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))) {
            returnToMenuAction.run();
        }
        return true;
    }

    private void handleWinScoreEntryInput(
            HighScoreRepository highScoreRepository,
            int score,
            GameSession session,
            Runnable openHighScoresAction) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) && winNameInput.length() > 0) {
            GdxWinScoreSupport.backspace(winNameInput);
            return;
        }
        if (!Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            return;
        }
        submitWinScore("Enter a name before saving.", highScoreRepository, score, session, openHighScoresAction);
    }

    private void handleWonMouseInput(
            GameSession session,
            OrthographicCamera hudCamera,
            HighScoreRepository highScoreRepository,
            int score,
            Runnable returnToMenuAction,
            Runnable openHighScoresAction) {
        if (!Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
            return;
        }
        float mx = Gdx.input.getX();
        float my = hudCamera.viewportHeight - Gdx.input.getY();
        if (!session.winScoreSaved() && contains(mx, my, winSaveButtonX, winSaveButtonY, winSaveButtonW, winSaveButtonH)) {
            submitWinScore("Type a name first.", highScoreRepository, score, session, openHighScoresAction);
            return;
        }
        if (contains(mx, my, winBackButtonX, winBackButtonY, winBackButtonW, winBackButtonH)) {
            returnToMenuAction.run();
        }
    }

    private void submitWinScore(
            String missingNameStatus,
            HighScoreRepository highScoreRepository,
            int score,
            GameSession session,
            Runnable openHighScoresAction) {
        if (!GdxWinScoreSupport.hasPlayerName(winNameInput)) {
            winScoreStatus = missingNameStatus;
            return;
        }
        String playerName = GdxWinScoreSupport.playerName(winNameInput);
        if (GdxWinScoreSupport.saveScore(highScoreRepository, winNameInput, score)) {
            session.setWinScoreSaved(true);
            winScoreStatus = "Saved score for " + playerName + ".";
            openHighScoresAction.run();
            return;
        }
        winScoreStatus = "Could not save score.";
    }

    private static boolean contains(float px, float py, float x, float y, float w, float h) {
        return px >= x && px <= x + w && py >= y && py <= y + h;
    }
}


