package main.game.maze;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;

public class ActionScreenController {
    @FXML
    protected Label scoreLabel;

    @FXML
    protected Label damagePenaltyNoticeLabel;


    @FXML
    protected void saveScore() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Save Score");
        dialog.setHeaderText("Enter your name to save your score");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String playerName = result.get();
            int score = Integer.parseInt(scoreLabel.getText());

            try {
                FileWriter writer = new FileWriter("scores.txt", true);
                writer.write(playerName + ": " + score + "\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setScoreLabel(int score) {
        scoreLabel.setText(String.valueOf(score));
    }


    public void showDamagePenaltyLabel() {
        damagePenaltyNoticeLabel.setVisible(true);
    }
}
