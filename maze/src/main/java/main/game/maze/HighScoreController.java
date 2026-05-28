package main.game.maze;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.game.maze.actions.RestartGameAction;
import main.game.maze.constants.ResourceFileConstants;
import main.game.maze.dto.Score;

public class HighScoreController implements Initializable {

    @FXML
    private AnchorPane highScoreRoot;

    @FXML
    private VBox highScoresVBox;

    private List<Score> scores = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scores = loadScoresFromFile();

        // sort scores in descending order
        scores.sort(Collections.reverseOrder());

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
    }

    public List<Score> getScores() {
        return scores;
    }

    private List<Score> loadScoresFromFile() {

        try (BufferedReader reader = new BufferedReader(new FileReader(ResourceFileConstants.HighscoreFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(":");
                if (data.length == 2) {
                    String name = data[0].trim();
                    int score = Integer.parseInt(data[1].trim());
                    scores.add(new Score(name, score));
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        Collections.sort(scores);
        return scores;
    }

    @FXML
    protected void restartGame() {
        RestartGameAction action = new RestartGameAction(highScoreRoot);
        action.Load();
    }
}
