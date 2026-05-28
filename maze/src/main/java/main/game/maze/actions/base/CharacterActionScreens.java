package main.game.maze.actions.base;

import java.util.concurrent.atomic.AtomicInteger;
import main.game.maze.App;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.mazeworld.constants.StageConstants;

public class CharacterActionScreens extends ActionScreens {
    protected int score = 0;
    protected AtomicInteger playerMoveCount = new AtomicInteger(0);
    protected PlayerCharacter playerCharacter;

    public int updateScore() {
        var hpReduction = 100 - playerCharacter.getHitPoints();
        var scoreDeathPenalty = hpReduction >= 100 ? StageConstants.ScoreDeathPenalty : 0;
        var dynamicPenalty = (App.gameController != null) ? App.gameController.getDynamicScorePenalty() : 0;

        this.score = StageConstants.MaxScore - (playerMoveCount.get() * StageConstants.ScoreSubtractFactor)
            - (hpReduction * StageConstants.ScoreSubtractFactor) - scoreDeathPenalty - dynamicPenalty;

        if (playerCharacter.isWinning) {
            this.score += StageConstants.ScoreWinBonus;
        }
        return score;
    }

    public int resetScore() {
        this.score = StageConstants.MaxScore;
        return StageConstants.MaxScore;
    }
}
