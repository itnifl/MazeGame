package main.game.maze;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import main.game.maze.constants.ResourceFileConstants;

public class GameOverController extends ActionScreenController implements Initializable {
    
    public static MediaPlayer gameOverMediaPlayer;

    @FXML
    private Label deathPenaltyNoticeLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the controller
        addGameOverSound();
        gameOverMediaPlayer.play();
    }

    private MediaView addGameOverSound() {

        var resource = getClass().getResource(ResourceFileConstants.GameOverSound);
        Media media = new Media(resource.toString());
        GameOverController.gameOverMediaPlayer = new MediaPlayer(media);

        // Create a MediaView and add it to the root node
        return new MediaView(gameOverMediaPlayer);
    }

    public void showDeathPenaltyLabel() {
        deathPenaltyNoticeLabel.setVisible(true);
    }
}
