package main.game.maze;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import main.game.maze.actions.HighscoreAction;
import main.game.maze.actions.StartScreenAction;
import javafx.scene.layout.AnchorPane;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.constants.AudioChannelConstants;
import main.game.maze.common.constants.AudioResourceConstants;
import main.game.maze.constants.DataFileConstants;

public class WinGameController extends ActionScreenController implements Initializable {

    @FXML
    private Label winBonusNoticeLabel;

    @FXML
    private Button nextLevelButton;

    @FXML
    private Label winHeaderLabel;

    public void showWinBonusLabel(int amount) {
        winBonusNoticeLabel.setText("+ Win bonus: +" + amount);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // the next level is offered only while there is one
        boolean moreLevels = main.game.maze.App.levelProgress.hasNext();
        if (nextLevelButton != null) {
            nextLevelButton.setVisible(moreLevels);
            nextLevelButton.setManaged(moreLevels);
        }
        // winning the last level wins the game: "GAME WON" and a dark voice says "Ultimate victory"
        boolean gameWon = !moreLevels;
        if (winHeaderLabel != null) {
            winHeaderLabel.setText(main.game.maze.game.status.FinalLevelIntro.winText(gameWon));
        }
        AudioEngine.get().playLoop(AudioResourceConstants.WinGameSound, AudioChannelConstants.WIN_MUSIC);
        if (gameWon) {
            AudioEngine.get().play(main.game.maze.game.status.FinalLevelIntro.WIN_VOICE);
        }
        AudioEngine.get().play(AudioResourceConstants.WinGameSoundComment);
    }

    /** Goes on to the next level with the weapons the player has now. */
    @FXML
    protected void nextLevel() {
        new main.game.maze.actions.RestartGameAction(screenRoot, true).Load();
    }

    /** Plays the level just won again from its start, with the weapons it started with. */
    @FXML
    protected void restartLevel() {
        main.game.maze.actions.RestartGameAction.retryLevel(screenRoot).Load();
    }

    @FXML
    protected void saveScore() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Save Score");
        dialog.setHeaderText("Enter your name to save your score");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty()) {
            return;
        }
        String playerName = result.get().trim();
        if (playerName.isEmpty()) {
            return;
        }
        int score = Integer.parseInt(scoreLabel.getText());
        upsertScore(playerName, score, DataFileConstants.HighscoreFilePath);
        showHighScoresThenReturnToMenu();
    }

    private void showHighScoresThenReturnToMenu() {
        if (screenRoot == null || screenRoot.getScene() == null) {
            return;
        }
        AnchorPane sceneRootPane = resolveAnchorRoot();
        if (sceneRootPane == null) {
            return;
        }
        final javafx.scene.Scene scene = sceneRootPane.getScene();
        final javafx.scene.Parent winScreenRoot = scene.getRoot();
        final boolean nextLevelFollows = main.game.maze.App.levelProgress.hasNext();
        HighscoreAction action = new HighscoreAction(sceneRootPane, () -> {
            if (nextLevelFollows) {
                // back to the win screen, where Next level is still offered
                scene.setRoot(winScreenRoot);
                main.game.maze.App.applyStandardSize((javafx.stage.Stage) scene.getWindow());
                return;
            }
            var current = scene.getRoot();
            if (current instanceof AnchorPane anchor) {
                new StartScreenAction(anchor).Load();
            }
        });
        action.Load();
    }

    private AnchorPane resolveAnchorRoot() {
        if (screenRoot != null && screenRoot.getScene() != null) {
            var root = screenRoot.getScene().getRoot();
            if (root instanceof AnchorPane anchor) {
                return anchor;
            }
        }
        return screenRoot;
    }
}
