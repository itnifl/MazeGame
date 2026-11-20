package main.game.maze.actions;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.game.maze.App;
import main.game.maze.GameController;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.actions.base.ActionScreens;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import main.game.maze.common.graphics.config.PropertiesMazeVisualStyleLoader;
import main.game.maze.common.graphics.config.XmiMazeVisualStyleLoader;
import main.game.maze.constants.AudioChannelConstants;
import main.game.maze.common.constants.AudioResourceConstants;
import main.game.maze.constants.ScreenNameFXMLConstants;
import main.game.maze.common.graphics.AudioEngine;

public class RestartGameAction extends ActionScreens {
    private AnchorPane root;

    private final boolean nextLevel;

    /** A new game from level 1. */
    public RestartGameAction(AnchorPane root) {
        this(root, false);
    }

    /** @param nextLevel true after a win: moves on to the next level, carrying the weapons over */
    public RestartGameAction(AnchorPane root, boolean nextLevel) {
        this.root = root;
        this.nextLevel = nextLevel;
    }

    public void Load() {
        if(App.gameController != null   ) {
            App.gameController.dispose();
            App.gameController = null;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ScreenNameFXMLConstants.GameScreen));

        try {
            if (root == null || root.getScene() == null) {
                return;
            }

            AnchorPane screen = fxmlLoader.load();
            GameController controller = fxmlLoader.getController();
            App.gameController = controller;

            AnchorPane.setTopAnchor(screen, 0.0);
            AnchorPane.setRightAnchor(screen, 0.0);
            AnchorPane.setBottomAnchor(screen, 0.0);
            AnchorPane.setLeftAnchor(screen, 0.0);
            
            
            var newRoot = new AnchorPane();
            newRoot.getChildren().add(screen);
            

            if (App.lastChosenDifficulty != null) {
                controller.setStartDifficulty(App.lastChosenDifficulty);
            }

            Stage stage = (Stage) root.getScene().getWindow();
            this.replaceRoot(root, newRoot);
            App.applySizeForCurrentDifficulty(stage);
                        
            if (nextLevel && App.arsenalAtWin != null && App.levelProgress.hasNext()) {
                App.levelProgress.advance(main.game.maze.game.items.PlayerArsenal.restore(App.arsenalAtWin));
            } else {
                App.levelProgress.restart();
            }
            App.arsenalAtWin = null;
            GameMazeWorld.RegenerateWorld(App.getBoardMaxX(), App.getBoardMaxY());

            controller.setupGame();
            AudioEngine.get().stopChannel(AudioChannelConstants.WIN_MUSIC);
            AudioEngine.get().stopChannel(AudioChannelConstants.GAME_OVER_MUSIC);
            AudioEngine.get().stopChannel(AudioChannelConstants.IN_GAME_MUSIC);
            AudioEngine.get().playLoop(resolveInGameMusicPath(), AudioChannelConstants.IN_GAME_MUSIC);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String resolveInGameMusicPath() {
        try {
            return new XmiMazeVisualStyleLoader().load().inGameMusicPath();
        } catch (RuntimeException ex) {
            try {
                return new PropertiesMazeVisualStyleLoader().load().inGameMusicPath();
            } catch (RuntimeException ignored) {
                return MazeVisualStyleConfig.DEFAULT.inGameMusicPath() != null
                        ? MazeVisualStyleConfig.DEFAULT.inGameMusicPath()
                        : AudioResourceConstants.BackgroundMusic;
            }
        }
    }

}
