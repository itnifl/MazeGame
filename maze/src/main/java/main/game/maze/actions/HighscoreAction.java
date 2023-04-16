package main.game.maze.actions;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import main.game.maze.actions.base.ActionScreens;

public class HighscoreAction extends ActionScreens {
    private AnchorPane root;

    public HighscoreAction(AnchorPane root) {
        this.root = root;
    }

    public void Load() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/game/maze/highscoreScreen.fxml"));

        try {
            AnchorPane screen = fxmlLoader.load();

            var newRoot = new AnchorPane();
            newRoot.getChildren().add(screen);

            this.replaceRoot(root, newRoot);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
