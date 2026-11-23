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
    private float winNextButtonX;
    private float winNextButtonY;
    private float winNextButtonW;
    private float winNextButtonH;
    private float winRestartButtonX;
    private float winRestartButtonY;
    private float winRestartButtonW;
    private float winRestartButtonH;
    private boolean nextLevelAvailable;
    private Runnable restartLevelAction;
    private float winShopButtonX;
    private float winShopButtonY;
    private float winShopButtonW;
    private float winShopButtonH;
    /** The shop between levels, opened with the Shop button while a next level follows (null: no shop). */
    private GdxShopOverlayController shop;

    public void setShop(GdxShopOverlayController shop) {
        this.shop = shop;
    }

    /** The shop, or null. */
    public GdxShopOverlayController shop() {
        return shop;
    }

    /** Whether the shop is open over the win screen now. */
    public boolean shopOpen() {
        return shop != null && shop.isOpen();
    }

    /** What the Restart Level button does: plays the level that was just won again, from its start. */
    public void setRestartLevelAction(Runnable action) {
        this.restartLevelAction = action;
    }

    /** Whether the win overlay offers the next level (set when the level is won). */
    public void setNextLevelAvailable(boolean available) {
        this.nextLevelAvailable = available;
    }

    public boolean nextLevelAvailable() {
        return nextLevelAvailable;
    }

    public void reset(GameSession session) {
        nextLevelAvailable = false;
        if (shop != null) {
            shop.close();
        }
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
        if (character < 32 || character == 127 || shopOpen()) {   // the shop's keys are not typed into the name
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
        winNextButtonX = buttons.nextX();
        winNextButtonY = buttons.nextY();
        winNextButtonW = buttons.nextW();
        winNextButtonH = buttons.nextH();
        winRestartButtonX = buttons.restartX();
        winRestartButtonY = buttons.restartY();
        winRestartButtonW = buttons.restartW();
        winRestartButtonH = buttons.restartH();
        winShopButtonX = buttons.shopX();
        winShopButtonY = buttons.shopY();
        winShopButtonW = buttons.shopW();
        winShopButtonH = buttons.shopH();
    }

    public boolean update(
            GameSession session,
            GdxModeInputController modeInputController,
            OrthographicCamera hudCamera,
            HighScoreRepository highScoreRepository,
            int score,
            Runnable returnToMenuAction,
            Runnable openHighScoresAction) {
        return update(session, modeInputController, hudCamera, highScoreRepository, score,
                returnToMenuAction, openHighScoresAction, null);
    }

    public boolean update(
            GameSession session,
            GdxModeInputController modeInputController,
            OrthographicCamera hudCamera,
            HighScoreRepository highScoreRepository,
            int score,
            Runnable returnToMenuAction,
            Runnable openHighScoresAction,
            Runnable nextLevelAction) {
        if (session.mode() != GameMode.WON) {
            return false;
        }
        if (shopOpen()) {
            shop.update(modeInputController, hudCamera);   // the shop has the keys and the mouse while it is open
            return true;
        }

        if (!session.winScoreSaved()) {
            handleWinScoreEntryInput(highScoreRepository, score, session, openHighScoresAction);
        }

        handleWonMouseInput(session, hudCamera, highScoreRepository, score, returnToMenuAction, openHighScoresAction,
                nextLevelAction);

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
            Runnable openHighScoresAction,
            Runnable nextLevelAction) {
        if (!Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
            return;
        }
        float mx = Gdx.input.getX();
        float my = hudCamera.viewportHeight - Gdx.input.getY();
        if (!session.winScoreSaved() && contains(mx, my, winSaveButtonX, winSaveButtonY, winSaveButtonW, winSaveButtonH)) {
            submitWinScore("Type a name first.", highScoreRepository, score, session, openHighScoresAction);
            return;
        }
        if (nextLevelAvailable && shop != null
                && contains(mx, my, winShopButtonX, winShopButtonY, winShopButtonW, winShopButtonH)) {
            shop.open();
            return;
        }
        if (nextLevelAvailable && nextLevelAction != null
                && contains(mx, my, winNextButtonX, winNextButtonY, winNextButtonW, winNextButtonH)) {
            nextLevelAction.run();
            return;
        }
        if (restartLevelAction != null
                && contains(mx, my, winRestartButtonX, winRestartButtonY, winRestartButtonW, winRestartButtonH)) {
            restartLevelAction.run();
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
