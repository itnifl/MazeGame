package main.game.maze;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.constants.AudioChannelConstants;
import main.game.maze.common.constants.AudioResourceConstants;

public class GameOverController extends ActionScreenController implements Initializable {

    @FXML
    private Label deathPenaltyNoticeLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AudioEngine.get().playLoop(AudioResourceConstants.GameOverSound, AudioChannelConstants.GAME_OVER_MUSIC);
    }

    public void showDeathPenaltyLabel() {
        deathPenaltyNoticeLabel.setVisible(true);
        deathPenaltyNoticeLabel.setManaged(true);
    }
}


