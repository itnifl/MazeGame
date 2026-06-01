package main.game.maze;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import main.game.maze.actions.RestartGameAction;
import main.game.maze.constants.DataFileConstants;

public class ActionScreenController {
    private static final Logger LOGGER = Logger.getLogger(ActionScreenController.class.getName());

    @FXML
    protected AnchorPane screenRoot;

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

            writeScore(playerName, score, DataFileConstants.HighscoreFilePath);
        }
    }

    public void writeScore(String playerName, int score, String filename) {
        try {
            java.io.File file = new java.io.File(filename);
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            try (FileWriter writer = new FileWriter(file, true)) {
                writer.write(playerName + ": " + score + "\n");
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, String.format("Unable to write score file: %s", filename), e);
        }
    }

    public void setScoreLabel(int score) {
        scoreLabel.setText(String.valueOf(score));
    }

    public void showDamagePenaltyLabel() {
        damagePenaltyNoticeLabel.setVisible(true);
    }

    @FXML
    protected void restartGame() {
        RestartGameAction action = new RestartGameAction(screenRoot);
        action.Load();
    }
}
