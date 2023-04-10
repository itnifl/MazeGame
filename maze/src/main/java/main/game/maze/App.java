package main.game.maze;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.game.maze.constants.StageConstants;

import java.io.IOException;

public class App extends Application {

    private static int BoardMaxX = StageConstants.BoardMaxX;
    private static int BoardMaxY = StageConstants.BoardMaxY;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
            AnchorPane root = loader.load();
            ProgressBar progressBar = (ProgressBar) root.lookup("#hpBar");

            progressBar.prefWidthProperty().bind(root.widthProperty());

            GameController controller = loader.getController();
            MazeWorld maze = MazeWorld.GetWorld();
            var vectors = maze.getMazeVectors();

            // Create a canvas
            Canvas canvas = new Canvas(BoardMaxX, BoardMaxY);
            GraphicsContext gc = canvas.getGraphicsContext2D();

            // Set the stroke color and width
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(3);

            // Draw the maze vectors
            for (Vector2D vector : vectors) {
                double startX = vector.getStart().getX();
                double startY = vector.getStart().getY();
                double endX = vector.getEnd().getX();
                double endY = vector.getEnd().getY();

                gc.strokeLine(startX, startY, endX, endY);
            }

            // Add the canvas to the root pane
            root.getChildren().add(canvas);

            primaryStage.setTitle("Maze Game");
            primaryStage.setScene(new Scene(root, BoardMaxX, BoardMaxY));
            primaryStage.show();
            controller.initialize(null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
