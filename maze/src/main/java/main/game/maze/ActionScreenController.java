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
import main.game.maze.constants.ResourceFileConstants;

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

            writeScore(playerName, score, ResourceFileConstants.HighscoreFilePath);
        }
    }

    public void writeScore(String playerName, int score, String filename) {
        try {
            FileWriter writer = new FileWriter(filename, true);
            writer.write(playerName + ": " + score + "\n");
            writer.close();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Unable to write score file: {0} ({1})", new Object[] { filename, e.getMessage() });
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
