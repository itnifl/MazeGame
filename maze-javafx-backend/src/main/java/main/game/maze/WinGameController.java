package main.game.maze;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextInputDialog;
import main.game.maze.actions.HighscoreAction;
import main.game.maze.actions.StartScreenAction;
import javafx.scene.layout.AnchorPane;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.constants.AudioChannelConstants;
import main.game.maze.common.constants.AudioResourceConstants;
import main.game.maze.constants.DataFileConstants;

public class WinGameController extends ActionScreenController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AudioEngine.get().playLoop(AudioResourceConstants.WinGameSound, AudioChannelConstants.WIN_MUSIC);
        AudioEngine.get().play(AudioResourceConstants.WinGameSoundComment);
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
        HighscoreAction action = new HighscoreAction(sceneRootPane, () -> {
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

