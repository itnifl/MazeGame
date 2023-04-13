package main.game.maze;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class GameOverController extends ActionScreenController implements Initializable {
    
    private MediaPlayer gameOverMediaPlayer;

    @FXML
    private Label deathPenaltyNoticeLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the controller
        addGameOverSound();
        gameOverMediaPlayer.play();
    }

    private MediaView addGameOverSound() {

        var resource = getClass().getResource("/main/game/maze/gameOver.mp3");
        Media media = new Media(resource.toString());
        this.gameOverMediaPlayer = new MediaPlayer(media);

        // Create a MediaView and add it to the root node
        MediaView mediaView = new MediaView(gameOverMediaPlayer);

        return mediaView;

    }

    public void showDeathPenaltyLabel() {
        deathPenaltyNoticeLabel.setVisible(true);
    }
}
