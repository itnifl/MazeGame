package main.game.maze.actions;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.game.maze.App;
import main.game.maze.StartController;
import main.game.maze.actions.base.ActionScreens;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.constants.AudioChannelConstants;
import main.game.maze.constants.ScreenNameFXMLConstants;

/** Action that swaps the current scene root for the start menu screen. */
public class StartScreenAction extends ActionScreens {
    private final AnchorPane root;

    public StartScreenAction(AnchorPane root) {
        this.root = root;
    }

    public void Load() {
        if (root == null || root.getScene() == null) {
            return;
        }
        final Scene scene = root.getScene();
        final Stage stage = (Stage) scene.getWindow();

        if (App.gameController != null) {
            try {
                App.gameController.dispose();
            } catch (RuntimeException ignored) {
                // best effort cleanup
            }
            App.gameController = null;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ScreenNameFXMLConstants.StartScreen));
            AnchorPane startRoot = loader.load();
            StartController startController = loader.getController();
            startController.setStage(stage);

            Parent currentRoot = scene.getRoot();
            if (currentRoot != null) {
                Parent parent = currentRoot.getParent();
                if (parent instanceof Pane parentPane) {
                    parentPane.getChildren().remove(currentRoot);
                }
            }
            scene.setRoot(startRoot);

            AudioEngine.get().stopChannel(AudioChannelConstants.WIN_MUSIC);
            AudioEngine.get().stopChannel(AudioChannelConstants.GAME_OVER_MUSIC);
            AudioEngine.get().stopChannel(AudioChannelConstants.IN_GAME_MUSIC);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
