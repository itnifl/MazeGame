package main.game.maze;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class GameOverController extends ActionScreenController implements Initializable {
    
    @FXML
    private Label deathPenaltyNoticeLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the controller
    }

    public void showDeathPenaltyLabel() {
        deathPenaltyNoticeLabel.setVisible(true);
    }
}
