package main.game.maze;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import main.game.maze.actions.RestartGameAction;
import main.game.maze.game.score.FileHighScoreRepository;

public class ActionScreenController {
    private static final Logger LOGGER = Logger.getLogger(ActionScreenController.class.getName());

    @FXML
    protected AnchorPane screenRoot;

    @FXML
    protected Label scoreLabel;

    @FXML
    protected Label damagePenaltyNoticeLabel;

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

    /**
     * Saves a score using case-insensitive upsert semantics: if a score for the same
     * name (case insensitive) already exists, it is replaced only when the new score
     * is higher. Otherwise the entry is appended.
     */
    public boolean upsertScore(String playerName, int score, String filename) {
        return new FileHighScoreRepository(filename).upsertScore(playerName, score);
    }

    public void setScoreLabel(int score) {
        scoreLabel.setText(String.valueOf(score));
    }

    public void showDamagePenaltyLabel() {
        damagePenaltyNoticeLabel.setVisible(true);
        damagePenaltyNoticeLabel.setManaged(true);
    }

    @FXML
    protected void restartGame() {
        RestartGameAction action = new RestartGameAction(screenRoot);
        action.Load();
    }
}


