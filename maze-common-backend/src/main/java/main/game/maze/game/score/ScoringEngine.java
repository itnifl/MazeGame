package main.game.maze.game.score;

import main.game.maze.mazeworld.constants.StageConstants;

/**
 * Shared scoring calculations for runtime and end-screen flows.
 */
public final class ScoringEngine {

    public int gameplayScore(int baseScore, int moveCount, float routeHintPenaltyPoints) {
        int movePenalty = Math.max(0, moveCount) * StageConstants.ScoreSubtractFactor;
        int hintPenalty = (int) Math.floor(Math.max(0f, routeHintPenaltyPoints));
        return Math.max(0, Math.max(0, baseScore) - movePenalty - hintPenalty);
    }

    public int characterScreenScore(
            int baseScore,
            int moveCount,
            int maxHitPoints,
            int currentHitPoints,
            int dynamicPenalty,
            boolean won) {
        int safeBase = Math.max(0, baseScore);
        int hpReduction = Math.max(0, Math.max(1, maxHitPoints) - Math.max(0, currentHitPoints));
        int scoreDeathPenalty = currentHitPoints <= 0 ? StageConstants.ScoreDeathPenalty : 0;
        int total = safeBase
                - (Math.max(0, moveCount) * StageConstants.ScoreSubtractFactor)
                - (hpReduction * StageConstants.ScoreSubtractFactor)
                - scoreDeathPenalty
                - Math.max(0, dynamicPenalty);
        if (won) {
            total += StageConstants.ScoreWinBonus;
        }
        return total;
    }
}
