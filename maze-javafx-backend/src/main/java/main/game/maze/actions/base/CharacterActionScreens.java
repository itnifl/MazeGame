package main.game.maze.actions.base;

import java.util.concurrent.atomic.AtomicInteger;
import main.game.maze.App;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.game.score.ScoringEngine;
import main.game.maze.mazeworld.constants.StageConstants;

public class CharacterActionScreens extends ActionScreens {
    private static final int ZERO_PENALTY = 0;
    protected int score = 0;
    protected int baseScore = StageConstants.MaxScore;
    protected AtomicInteger playerMoveCount = new AtomicInteger(0);
    protected PlayerCharacter playerCharacter;
    private final ScoringEngine scoringEngine = new ScoringEngine();

    public void setBaseScore(int baseScore) {
        this.baseScore = baseScore;
    }

    public int updateScore() {
        int maxHp = playerCharacter.getMaxHitPoints();
        int currentHp = playerCharacter.getHitPoints();
        var dynamicPenalty = (App.gameController != null) ? App.gameController.getDynamicScorePenalty() : ZERO_PENALTY;

        this.score = scoringEngine.characterScreenScore(
                baseScore,
                playerMoveCount.get(),
                maxHp,
                currentHp,
                dynamicPenalty,
                playerCharacter.isWinning);
        return score;
    }

    public int resetScore() {
        this.score = baseScore;
        return baseScore;
    }
}
