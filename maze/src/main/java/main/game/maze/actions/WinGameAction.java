package main.game.maze.actions;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import main.game.maze.WinGameController;
import main.game.maze.actions.base.CharacterActionScreens;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.characters.interfaces.ICanLetYouWin;

public class WinGameAction extends CharacterActionScreens implements ICanLetYouWin {
    private AnchorPane root;
    private Runnable runnableOnWin;

    public WinGameAction(PlayerCharacter playerCharacter, AtomicInteger playerMoveCount, AnchorPane root, Runnable runnableOnWin) {
        this.root = root;
        this.runnableOnWin = runnableOnWin;
        this.playerMoveCount = playerMoveCount;
        this.playerCharacter = playerCharacter;
    }

    @Override
    public void WinGame() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/game/maze/winScreen.fxml"));

        runnableOnWin.run();

        try {
            AnchorPane winScreen = fxmlLoader.load();
            WinGameController controller = fxmlLoader.getController();

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
            if(hitPoints < 100) {
                controller.showDamagePenaltyLabel();
            }

            this.replaceRoot(root, newRoot);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
