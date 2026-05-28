package main.game.maze.actions;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.application.Platform;
import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.game.maze.App;
import main.game.maze.GameOverController;
import main.game.maze.actions.base.CharacterActionScreens;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.constants.ScreenNameConstants;
import main.game.maze.interfaces.IDeathSubscriber;

public class GameOverAction extends CharacterActionScreens implements IDeathSubscriber {
    private AnchorPane root;
    private Runnable runnableOnGameOver;
    private volatile boolean gameOverScheduled = false;

    public GameOverAction(PlayerCharacter playerCharacter, AtomicInteger playerMoveCount, AnchorPane root,
            Runnable runnableOnGameOver) {
        this.root = root;
        this.runnableOnGameOver = runnableOnGameOver;
        this.playerMoveCount = playerMoveCount;
        this.playerCharacter = playerCharacter;
    }

    @Override
    public void AddDeathNotification(ICanDie mortalEntity) {
        // Ensure FX operations run on the FX application thread
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> handleGameOver(mortalEntity));
        } else {
            handleGameOver(mortalEntity);
        }
    }

    private void handleGameOver(ICanDie mortalEntity) {
        if (gameOverScheduled) {
            return;
        }
        gameOverScheduled = true;

        if (App.gameController != null) {
            App.gameController.stopPlayerMovement();
        }

        if (mortalEntity instanceof PlayerCharacter player) {
            player.PlayDieAnimation();
        }

        PauseTransition waitBeforeGameOver = new PauseTransition(Duration.seconds(3));
        waitBeforeGameOver.setOnFinished(event -> showGameOverScreen());
        waitBeforeGameOver.play();
    }

    private void showGameOverScreen() {
        // End movement loops only after the 3-second death display window.
        if (App.gameController != null) {
            App.gameController.stopComputerCharacters();
        }
        runnableOnGameOver.run();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ScreenNameConstants.GameOverScreen));

        try {
            AnchorPane gameOverScreen = fxmlLoader.load();
            GameOverController controller = fxmlLoader.getController();

            var newRoot = new AnchorPane();
            newRoot.getChildren().add(gameOverScreen);

            updateScore();
            controller.setScoreLabel(this.score);

            var hitPoints = playerCharacter.getHitPoints();
            if (hitPoints < 100) {
                controller.showDamagePenaltyLabel();
            }
            if (hitPoints <= 0) {
                controller.showDeathPenaltyLabel();
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
