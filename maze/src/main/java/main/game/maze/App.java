package main.game.maze;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.game.maze.constants.StageConstants;
import java.io.IOException;



public class App extends Application {

    private static int BoardMaxX = StageConstants.BoardMaxX;
    private static int BoardMaxY = StageConstants.BoardMaxY;
    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
            AnchorPane root = loader.load();
            ProgressBar progressBar = (ProgressBar) root.lookup("#hpBar");
            progressBar.prefWidthProperty().bind(root.widthProperty());

            GameController controller = loader.getController();
            
            primaryStage.setTitle("Maze Game");
            primaryStage.setScene(new Scene(root, BoardMaxX, BoardMaxY));
            primaryStage.show();
            controller.setupGame();;

            // Start playing the music
            // this.mediaPlayer.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private MediaView addMusic() {

        var resource = getClass().getResource("backgroundMusic.mp3");
        Media media = new Media(resource.toString());
        this.mediaPlayer = new MediaPlayer(media);
        this.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        // Create a MediaView and add it to the root node
        MediaView mediaView = new MediaView(mediaPlayer);

        return mediaView;

    }
}
