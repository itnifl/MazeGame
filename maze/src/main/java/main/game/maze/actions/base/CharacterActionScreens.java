package main.game.maze.actions.base;

import java.util.concurrent.atomic.AtomicInteger;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.constants.StageConstants;

public class CharacterActionScreens extends ActionScreens {
    protected int score = 0;
    protected AtomicInteger playerMoveCount = new AtomicInteger(0);
    protected PlayerCharacter playerCharacter;

    public int updateScore() {
        var hpReduction = 100 - playerCharacter.getHitPoints();
        var scoreDeathPenalty = hpReduction >= 100 ? StageConstants.ScoreDeathPenalty : 0;
        this.score = StageConstants.MaxScore -  (playerMoveCount.get() * StageConstants.ScoreSubtractFactor) - (hpReduction * StageConstants.ScoreSubtractFactor) - scoreDeathPenalty;
        return score;
    }
}
