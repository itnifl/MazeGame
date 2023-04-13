package main.game.maze.actions;

import java.util.concurrent.atomic.AtomicInteger;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.constants.StageConstants;

public class ActionScreens {
    protected int score = 0;
    protected AtomicInteger playerMoveCount = new AtomicInteger(0);
    protected PlayerCharacter playerCharacter;

    protected void replaceRoot(AnchorPane oldRoot, AnchorPane newRoot) {
        Stage primaryStage = (Stage) oldRoot.getScene().getWindow();
        Scene scene = primaryStage.getScene();
        scene.setRoot(newRoot);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
    }

    public int updateScore() {
        var hpReduction = 100 - playerCharacter.getHitPoints();
        var scoreDeathPenalty = hpReduction >= 100 ? 4000 : 0;
        this.score = StageConstants.MaxScore -  (playerMoveCount.get() * StageConstants.ScoreSubtractFactor) - (hpReduction * StageConstants.ScoreSubtractFactor) - scoreDeathPenalty;
        return score;
    }
}
