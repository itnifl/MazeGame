package main.game.maze;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.game.maze.actions.RestartGameAction;
import main.game.maze.constants.DataFileConstants;
import main.game.maze.dto.Score;
import main.game.maze.game.score.FileHighScoreRepository;
import main.game.maze.game.score.HighScoreRepository;

public class HighScoreController implements Initializable {

    @FXML
    private AnchorPane highScoreRoot;

    @FXML
    private VBox highScoresVBox;

    @FXML
    private Button continueButton;

    private Runnable onContinue;

    private List<Score> scores = new ArrayList<>();
        private final HighScoreRepository highScoreRepository =
            new FileHighScoreRepository(DataFileConstants.HighscoreFilePath);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scores = loadScoresFromFile();

        // add score labels to the VBox
        if (highScoresVBox != null) {
            int maxRows = Math.min(scores.size(), 8);
            for (int i = 0; i < maxRows; i++) {
                Score score = scores.get(i);
                Label scoreLabel = new Label(score.toString());
                scoreLabel.setStyle("-fx-font-size: 22px; -fx-font-family: 'Consolas'; -fx-text-fill: #f2f8ff; -fx-font-weight: bold;");
                highScoresVBox.getChildren().add(scoreLabel);
            }

            if (scores.isEmpty()) {
                Label noScores = new Label("No saved scores yet");
                noScores.setStyle("-fx-font-size: 18px; -fx-font-family: 'Consolas'; -fx-text-fill: #9fbad2;");
                highScoresVBox.getChildren().add(noScores);
            }
        }

        // Hide continue button until a handler is attached.
        if (continueButton != null) {
            continueButton.setVisible(false);
            continueButton.setManaged(false);
        }
    }

    public List<Score> getScores() {
        return scores;
    }

    private List<Score> loadScoresFromFile() {
        return new ArrayList<>(highScoreRepository.loadScores());
    }

    @FXML
    protected void restartGame() {
        RestartGameAction action = new RestartGameAction(highScoreRoot);
        action.Load();
    }

    @FXML
    protected void continueGame() {
        if (onContinue != null) {
            onContinue.run();
        }
    }

    public void setOnContinue(Runnable handler) {
        this.onContinue = handler;
        if (continueButton != null) {
            // Hide the resume button if there is no live game to return to.
            continueButton.setVisible(handler != null);
            continueButton.setManaged(handler != null);
        }
    }
}
