package main.game.maze.actions;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.game.maze.GameController;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.interfaces.IDeathSubscriber;

public class GameOverAction extends ActionScreens implements IDeathSubscriber {
    private AnchorPane root;
    private Runnable runnableOnGameOver;

    public GameOverAction(AnchorPane root, Runnable runnableOnGameOver) {
        this.root = root;
        this.runnableOnGameOver = runnableOnGameOver;
    }

    @Override
    public void AddDeathNotification(ICanDie mortalEntity) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gameOverScreen.fxml"));
        /*
         * GameController controller = new GameController();
         * fxmlLoader.setController(controller);
         */

        try {
            VBox gameOverScreen = fxmlLoader.load();

            gameOverScreen.setAlignment(Pos.CENTER);

            AnchorPane.setTopAnchor(gameOverScreen, 0.0);
            AnchorPane.setRightAnchor(gameOverScreen, 0.0);
            AnchorPane.setBottomAnchor(gameOverScreen, 0.0);
            AnchorPane.setLeftAnchor(gameOverScreen, 0.0);

            var newRoot = new AnchorPane();
            newRoot.getChildren().add(gameOverScreen);

            AnchorPane.setTopAnchor(newRoot, 0.0);
            AnchorPane.setRightAnchor(newRoot, 0.0);
            AnchorPane.setBottomAnchor(newRoot, 0.0);
            AnchorPane.setLeftAnchor(newRoot, 0.0);

            this.replaceRoot(root, newRoot);

        } catch (IOException e) {
            e.printStackTrace();
        }
        runnableOnGameOver.run();
    }

}
