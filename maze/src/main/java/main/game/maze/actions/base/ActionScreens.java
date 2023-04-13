package main.game.maze.actions.base;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class ActionScreens {


    protected void replaceRoot(AnchorPane oldRoot, AnchorPane newRoot) {
        Stage primaryStage = (Stage) oldRoot.getScene().getWindow();
        Scene scene = primaryStage.getScene();
        scene.setRoot(newRoot);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
    }
}
