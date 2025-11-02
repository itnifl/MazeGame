package main.game.maze.actions;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.game.maze.App;
import main.game.maze.GameController;
import main.game.maze.GameOverController;
import main.game.maze.StartController;
import main.game.maze.WinGameController;
import main.game.maze.actions.base.ActionScreens;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.constants.ScreenNameConstants;
import main.game.maze.service.DifficultyService;

public class RestartGameAction extends ActionScreens {
    private AnchorPane root;

    public RestartGameAction(AnchorPane root) {
        this.root = root;
    }

    public void Load() {
        if(App.gameController != null   ) {
            App.gameController.dispose();
            App.gameController = null;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ScreenNameConstants.GameScreen));
                    
        if (WinGameController.winGameMediaPlayer != null) {
            WinGameController.winGameMediaPlayer.stop();
        }
         if (WinGameController.winGameMediaPlayerComment != null) {
            WinGameController.winGameMediaPlayerComment.stop();
        }
        if (GameOverController.gameOverMediaPlayer != null) {
            GameOverController.gameOverMediaPlayer.stop();
        }
        if (PlayerCharacter.infectedMediaPlayer != null) {
            PlayerCharacter.infectedMediaPlayer.stop();
        }
        if (PlayerCharacter.screamMediaPlayer != null) {
            PlayerCharacter.screamMediaPlayer.stop();
        }
        App.inGameMediaPlayer.play();

        try {
            AnchorPane screen = fxmlLoader.load();
            GameController controller = fxmlLoader.getController();
            App.gameController = controller;

            var newRoot = new AnchorPane();
            newRoot.getChildren().add(screen);

            this.replaceRoot(root, newRoot);

            if (App.lastChosenDifficulty != null) {
                controller.setStartDifficulty(App.lastChosenDifficulty);
            }

            controller.initialize(null, null);

            controller.setupGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
