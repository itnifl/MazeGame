package main.game.maze;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class WinGameController extends ActionScreenController implements Initializable {

    public static MediaPlayer winGameMediaPlayer;
    public static MediaPlayer winGameMediaPlayerComment;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the controller
        addWinGameSound();
        addWinGameSoundComment();
        winGameMediaPlayer.play();
        winGameMediaPlayerComment.play();
    }

    MediaView addWinGameSound() {

        var resource = getClass().getResource("/main/game/maze/winGame.mp3");
        Media media = new Media(resource.toString());
        winGameMediaPlayer = new MediaPlayer(media);

        // Create a MediaView and add it to the root node
        return new MediaView(winGameMediaPlayer);
    }
    MediaView addWinGameSoundComment() {

        var resource = getClass().getResource("/main/game/maze/ass-kickin.mp3");
        Media media = new Media(resource.toString());
        winGameMediaPlayerComment = new MediaPlayer(media);

        // Create a MediaView and add it to the root node
        return new MediaView(winGameMediaPlayerComment);
    }
}
