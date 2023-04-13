package main.game.maze;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class GameOverController implements Initializable {
    @FXML
    private Label scoreLabel;
    @FXML
    private Label deathPenaltyNoticeLabel;
    @FXML
    private Label damagePenaltyNoticeLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the controller
    }

    public void setScoreLabel(int score) {
        scoreLabel.setText(String.valueOf(score));
    }

    public void showDeathPenaltyLabel() {
        deathPenaltyNoticeLabel.setVisible(true);
    }

    public void showDamagePenaltyLabel() {
        damagePenaltyNoticeLabel.setVisible(true);
    }
}
