package main.game.maze.actions;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import main.game.maze.App;
import main.game.maze.GameController;
import main.game.maze.WinGameController;
import main.game.maze.actions.base.ActionScreens;
import main.game.maze.constants.ScreenNameConstants;

public class RestartGameAction extends ActionScreens {
    private AnchorPane root;

    public RestartGameAction(AnchorPane root) {
        this.root = root;
    }

    public void Load() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ScreenNameConstants.GameScreen));

        if (WinGameController.winGameMediaPlayer != null) {
            WinGameController.winGameMediaPlayer.stop();
        }
        App.inGameMediaPlayer.play();

        try {
            AnchorPane screen = fxmlLoader.load();
            GameController controller = fxmlLoader.getController();

            var newRoot = new AnchorPane();
            newRoot.getChildren().add(screen);

            this.replaceRoot(root, newRoot);

            controller.initialize(null, null);

            controller.setupGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
