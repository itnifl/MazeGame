package main.game.maze.game.score;

import main.game.maze.mazeworld.constants.StageConstants;

/**
 * Shared scoring calculations for runtime and end-screen flows.
 */
public final class ScoringEngine {

    /**
     * Itemised breakdown of an end-screen score.
     * Each penalty field is a non-negative amount to subtract; {@code winBonus} is non-negative.
     */
    public record ScoreBreakdown(
            int baseScore,
            int movePenalty,
            int damagePenalty,
            int deathPenalty,
            int dynamicPenalty,
            int winBonus,
            int total) {
    }

    /** Returns the itemised breakdown matching {@link #characterScreenScore}. */
    public ScoreBreakdown breakdown(
            int baseScore,
            int moveCount,
            int maxHitPoints,
            int currentHitPoints,
            int dynamicPenalty,
            boolean won) {
        int safeBase = Math.max(0, baseScore);
        int hpReduction = Math.max(0, Math.max(1, maxHitPoints) - Math.max(0, currentHitPoints));
        int movePen   = Math.max(0, moveCount) * StageConstants.ScoreSubtractFactor;
        int damagePen = hpReduction * StageConstants.ScoreSubtractFactor;
        int deathPen  = currentHitPoints <= 0 ? StageConstants.ScoreDeathPenalty : 0;
        int dynPen    = Math.max(0, dynamicPenalty);
        int bonus     = won ? StageConstants.ScoreWinBonus : 0;
        int total     = safeBase - movePen - damagePen - deathPen - dynPen + bonus;
        return new ScoreBreakdown(safeBase, movePen, damagePen, deathPen, dynPen, bonus, total);
    }

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
        return breakdown(baseScore, moveCount, maxHitPoints, currentHitPoints, dynamicPenalty, won).total();
    }
}
