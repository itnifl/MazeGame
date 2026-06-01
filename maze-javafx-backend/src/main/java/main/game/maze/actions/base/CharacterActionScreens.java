package main.game.maze.actions.base;

import java.util.concurrent.atomic.AtomicInteger;
import main.game.maze.App;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.mazeworld.constants.StageConstants;

public class CharacterActionScreens extends ActionScreens {
    private static final int ZERO_PENALTY = 0;
    protected int score = 0;
    protected int baseScore = StageConstants.MaxScore;
    protected AtomicInteger playerMoveCount = new AtomicInteger(0);
    protected PlayerCharacter playerCharacter;

    public void setBaseScore(int baseScore) {
        this.baseScore = baseScore;
    }

    public int updateScore() {
        int maxHp = playerCharacter.getMaxHitPoints();
        int currentHp = playerCharacter.getHitPoints();
        int hpReduction = Math.max(0, maxHp - currentHp);
        var scoreDeathPenalty = currentHp <= 0 ? StageConstants.ScoreDeathPenalty : ZERO_PENALTY;
        var dynamicPenalty = (App.gameController != null) ? App.gameController.getDynamicScorePenalty() : ZERO_PENALTY;

        this.score = baseScore - (playerMoveCount.get() * StageConstants.ScoreSubtractFactor)
            - (hpReduction * StageConstants.ScoreSubtractFactor) - scoreDeathPenalty - dynamicPenalty;

        if (playerCharacter.isWinning) {
            this.score += StageConstants.ScoreWinBonus;
        }
        return score;
    }

    public int resetScore() {
        this.score = baseScore;
        return baseScore;
    }
}
