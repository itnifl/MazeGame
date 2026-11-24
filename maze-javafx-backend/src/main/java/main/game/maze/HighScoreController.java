package main.game.maze;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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
import main.game.maze.game.theme.MenuButton;
import main.game.maze.game.theme.MenuTheme;
import main.game.maze.javafx.menu.FxMenuTheme;

public class HighScoreController implements Initializable {

    @FXML
    private AnchorPane highScoreRoot;

    @FXML
    private VBox highScoresVBox;

    @FXML
    private Button continueButton;

    @FXML
    private Button restartButton;

    @FXML
    private javafx.scene.image.ImageView backdrop;

    private Runnable onContinue;

    private List<Score> scores = new ArrayList<>();
    private final HighScoreRepository highScoreRepository;

    public HighScoreController() {
        this.highScoreRepository = new FileHighScoreRepository(DataFileConstants.HighscoreFilePath);
    }

    HighScoreController(HighScoreRepository repository) {
        this.highScoreRepository = repository;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scores = loadScoresFromFile();
        applyTheme();

        // add score labels to the VBox
        if (highScoresVBox != null) {
            int maxRows = Math.min(scores.size(), 8);
            for (int i = 0; i < maxRows; i++) {
                Score score = scores.get(i);
                Label scoreLabel = new Label(score.toString());
                scoreLabel.getStyleClass().add(i == 0 ? "menu-score" : "menu-text");
                highScoresVBox.getChildren().add(scoreLabel);
            }

            if (scores.isEmpty()) {
                Label noScores = new Label("No saved scores yet");
                noScores.getStyleClass().add("menu-muted");
                highScoresVBox.getChildren().add(noScores);
            }
        }

        // Hide continue button until a handler is attached.
        if (continueButton != null) {
            continueButton.setVisible(false);
            continueButton.setManaged(false);
        }
    }

    /** The look of the screens between levels (the menu theme model), the same as the libGDX high scores. */
    private void applyTheme() {
        if (highScoreRoot == null) {
            return;
        }
        FxMenuTheme.apply(highScoreRoot);
        if (backdrop != null) {
            backdrop.setImage(FxMenuTheme.image(MenuTheme.Art.BACKDROP));
        }
        if (continueButton != null) {
            FxMenuTheme.style(continueButton, MenuButton.NEXT_LEVEL);
            continueButton.setText("Continue Game");
        }
        if (restartButton != null) {
            FxMenuTheme.style(restartButton, MenuButton.RESTART_MISSION);
        }
    }

    public List<Score> getScores() {
        return scores;
    }

    private List<Score> loadScoresFromFile() {
        List<Score> loadedScores = new ArrayList<>(highScoreRepository.loadScores());
        loadedScores.sort(Collections.reverseOrder());
        return loadedScores;
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
