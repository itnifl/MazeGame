package main.game.maze.actions;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.constants.AudioChannelConstants;
import main.game.maze.constants.ResourceFileConstants;
import main.game.maze.constants.ScreenNameConstants;
import main.game.maze.interfaces.IDeathSubscriber;

public class GameOverAction extends CharacterActionScreens implements IDeathSubscriber {
    private static final Logger LOGGER = Logger.getLogger(GameOverAction.class.getName());
    private static final double DEFAULT_DEATH_DISPLAY_DELAY_SECONDS = 3.0;

    private final AnchorPane root;
    private final Runnable runnableOnGameOver;
    private final AtomicBoolean gameOverScheduled = new AtomicBoolean(false);
    private final Duration deathDisplayDelay;

    public GameOverAction(PlayerCharacter playerCharacter, AtomicInteger playerMoveCount, AnchorPane root,
            Runnable runnableOnGameOver) {
        this(playerCharacter, playerMoveCount, root, runnableOnGameOver,
                Duration.seconds(DEFAULT_DEATH_DISPLAY_DELAY_SECONDS));
    }

    public GameOverAction(PlayerCharacter playerCharacter, AtomicInteger playerMoveCount, AnchorPane root,
            Runnable runnableOnGameOver, Duration deathDisplayDelay) {
        this.root = root;
        this.runnableOnGameOver = runnableOnGameOver;
        this.playerMoveCount = playerMoveCount;
        this.playerCharacter = playerCharacter;
        this.deathDisplayDelay = deathDisplayDelay;
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
        if (!gameOverScheduled.compareAndSet(false, true)) {
            return;
        }

        if (App.gameController != null) {
            App.gameController.stopPlayerMovement();
        }

        if (mortalEntity instanceof PlayerCharacter player) {
            player.PlayDieAnimation();
        }

        PauseTransition waitBeforeGameOver = new PauseTransition(deathDisplayDelay);
        waitBeforeGameOver.setOnFinished(event -> showGameOverScreen());
        waitBeforeGameOver.play();
    }

    private void showGameOverScreen() {
        if (App.gameController != null) {
            App.gameController.stopComputerCharacters();
        }
        AudioEngine.get().stopChannel(AudioChannelConstants.IN_GAME_MUSIC);
        startGameOverMusic();
        runnableOnGameOver.run();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ScreenNameConstants.GameOverScreen));

        try {
            AnchorPane gameOverScreen = fxmlLoader.load();
            GameOverController controller = fxmlLoader.getController();

            var newRoot = new AnchorPane();
            newRoot.getChildren().add(gameOverScreen);

            updateScore();
            controller.setScoreLabel(this.score);
            configurePenaltyLabels(controller, playerCharacter.getHitPoints());

            Stage stage = (Stage) root.getScene().getWindow();
            this.replaceRoot(root, newRoot);
            App.applyStandardSize(stage);

            // Dispose AFTER switching screens to avoid scene graph corruption
            if (App.gameController != null) {
                App.gameController.dispose();
                App.gameController = null;
            }

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load game over screen", e);
        }
    }

    private void configurePenaltyLabels(GameOverController controller, int hitPoints) {
        if (hitPoints < PlayerCharacter.MAX_PLAYER_HP) {
            controller.showDamagePenaltyLabel();
        }
        if (hitPoints <= 0) {
            controller.showDeathPenaltyLabel();
        }
    }

    /**
     * Loops the game-over track on the GAME_OVER_MUSIC channel. Extracted so
     * the music start is testable in isolation (libgdx has the same behavior).
     */
    public static void startGameOverMusic() {
        AudioEngine.get().playLoop(ResourceFileConstants.GameOverSound, AudioChannelConstants.GAME_OVER_MUSIC);
    }

}
