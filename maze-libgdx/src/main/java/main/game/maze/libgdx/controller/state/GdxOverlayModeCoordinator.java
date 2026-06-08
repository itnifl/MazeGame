package main.game.maze.libgdx.controller.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import java.util.function.IntSupplier;
import main.game.maze.game.score.HighScoreRepository;
import main.game.maze.game.session.GameMode;
import main.game.maze.game.session.GameSession;
import main.game.maze.libgdx.controller.GdxGameOverOverlayController;
import main.game.maze.libgdx.controller.GdxHighScoresOverlayController;
import main.game.maze.libgdx.controller.GdxModeInputController;
import main.game.maze.libgdx.controller.GdxWinOverlayController;

/**
 * Coordinates overlay-only mode updates for HIGH_SCORES, WON, and GAME_OVER.
 */
public final class GdxOverlayModeCoordinator {

    private final GameSession session;
    private final GdxModeInputController modeInputController;
    private final HighScoreRepository highScoreRepository;
    private final GdxHighScoresOverlayController highScoresOverlayController;
    private final GdxWinOverlayController winOverlayController;
    private final GdxGameOverOverlayController gameOverOverlayController;

    public GdxOverlayModeCoordinator(
            GameSession session,
            GdxModeInputController modeInputController,
            HighScoreRepository highScoreRepository,
            GdxHighScoresOverlayController highScoresOverlayController,
            GdxWinOverlayController winOverlayController,
            GdxGameOverOverlayController gameOverOverlayController) {
        this.session = session;
        this.modeInputController = modeInputController;
        this.highScoreRepository = highScoreRepository;
        this.highScoresOverlayController = highScoresOverlayController;
        this.winOverlayController = winOverlayController;
        this.gameOverOverlayController = gameOverOverlayController;
    }

    public boolean updateHighScores(Runnable returnToMenuAction) {
        return highScoresOverlayController.update(
                session,
                modeInputController,
                Gdx.input.isKeyPressed(Input.Keys.ESCAPE),
                returnToMenuAction,
                () -> session.setMode(GameMode.PLAYING));
    }

    public boolean updateWon(OrthographicCamera hudCamera, IntSupplier scoreSupplier, Runnable returnToMenuAction, Runnable openHighScoresAction) {
        return winOverlayController.update(
                session,
                modeInputController,
                hudCamera,
                highScoreRepository,
                scoreSupplier.getAsInt(),
                returnToMenuAction,
                openHighScoresAction);
    }

    public boolean updateGameOver(Runnable returnToMenuAction) {
        return gameOverOverlayController.update(
                session,
                modeInputController,
                Gdx.input.isKeyPressed(Input.Keys.ESCAPE),
                returnToMenuAction);
    }
}