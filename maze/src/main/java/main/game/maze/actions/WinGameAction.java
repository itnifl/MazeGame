package main.game.maze.actions;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.game.maze.App;
import main.game.maze.WinGameController;
import main.game.maze.actions.base.CharacterActionScreens;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.characters.interfaces.ICanLetYouWin;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.constants.ScreenNameConstants;

public class WinGameAction extends CharacterActionScreens implements ICanLetYouWin {
    private AnchorPane root;
    private Runnable runnableOnWin;

    public WinGameAction(PlayerCharacter playerCharacter, AtomicInteger playerMoveCount, AnchorPane root,
            Runnable runnableOnWin) {
        this.root = root;
        this.runnableOnWin = runnableOnWin;
        this.playerMoveCount = playerMoveCount;
        this.playerCharacter = playerCharacter;
    }

    @Override
    public void WinGame() {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(this::WinGame);
            return;
        }

        // Stop movement loop immediately to prevent race conditions
        if (App.gameController != null) {
            App.gameController.stopComputerCharacters();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ScreenNameConstants.WinGameScreen));

        runnableOnWin.run();

        AudioEngine.get().stopChannel("music.inGame");

        try {
            AnchorPane winScreen = fxmlLoader.load();
            WinGameController controller = fxmlLoader.getController();
            playerCharacter.isWinning = true;
            updateScore();

            controller.setScoreLabel(this.score);

            AnchorPane.setTopAnchor(winScreen, 0.0);
            AnchorPane.setRightAnchor(winScreen, 0.0);
            AnchorPane.setBottomAnchor(winScreen, 0.0);
            AnchorPane.setLeftAnchor(winScreen, 0.0);

            var newRoot = new AnchorPane();
            newRoot.getChildren().add(winScreen);

            AnchorPane.setTopAnchor(newRoot, 0.0);
            AnchorPane.setRightAnchor(newRoot, 0.0);
            AnchorPane.setBottomAnchor(newRoot, 0.0);
            AnchorPane.setLeftAnchor(newRoot, 0.0);

            this.updateScore();

            controller.setScoreLabel(this.score);

            var hitPoints = playerCharacter.getHitPoints();
            if (hitPoints < 100) {
                controller.showDamagePenaltyLabel();
            }

            if (root == null || root.getScene() == null) {
                return;
            }
            Stage stage = (Stage) root.getScene().getWindow();
            this.replaceRoot(root, newRoot);
            App.applyStandardSize(stage);

            // Dispose AFTER switching screens to avoid scene graph corruption
            if (App.gameController != null) {
                App.gameController.dispose();
                App.gameController = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
