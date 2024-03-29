package main.game.maze.actions;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import main.game.maze.GameOverController;
import main.game.maze.actions.base.CharacterActionScreens;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.interfaces.IDeathSubscriber;

public class GameOverAction extends CharacterActionScreens implements IDeathSubscriber {
    private AnchorPane root;
    private Runnable runnableOnGameOver;

    public GameOverAction(PlayerCharacter playerCharacter, AtomicInteger playerMoveCount, AnchorPane root,
            Runnable runnableOnGameOver) {
        this.root = root;
        this.runnableOnGameOver = runnableOnGameOver;
        this.playerMoveCount = playerMoveCount;
        this.playerCharacter = playerCharacter;
    }

    @Override
    public void AddDeathNotification(ICanDie mortalEntity) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/game/maze/gameOverScreen.fxml"));

        runnableOnGameOver.run();

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

            this.replaceRoot(root, newRoot);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
