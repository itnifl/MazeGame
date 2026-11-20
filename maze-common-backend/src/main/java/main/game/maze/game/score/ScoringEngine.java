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
            int killBonus,
            int total) {
    }

    /** Returns the itemised breakdown matching {@link #characterScreenScore} (no kill points). */
    public ScoreBreakdown breakdown(
            int baseScore,
            int moveCount,
            int maxHitPoints,
            int currentHitPoints,
            int dynamicPenalty,
            boolean won) {
        return breakdown(baseScore, moveCount, maxHitPoints, currentHitPoints, dynamicPenalty, won, 0);
    }

    /**
     * Itemised end-screen score, including {@code killPoints}: the points the player earned for
     * the enemies they killed (see {@code KillLedger}).
     */
    public ScoreBreakdown breakdown(
            int baseScore,
            int moveCount,
            int maxHitPoints,
            int currentHitPoints,
            int dynamicPenalty,
            boolean won,
            int killPoints) {
        int safeBase = Math.max(0, baseScore);
        int hpReduction = Math.max(0, Math.max(1, maxHitPoints) - Math.max(0, currentHitPoints));
        int movePen   = Math.max(0, moveCount) * StageConstants.ScoreSubtractFactor;
        int damagePen = hpReduction * StageConstants.ScoreSubtractFactor;
        int deathPen  = currentHitPoints <= 0 ? StageConstants.ScoreDeathPenalty : 0;
        int dynPen    = Math.max(0, dynamicPenalty);
        int bonus     = won ? StageConstants.ScoreWinBonus : 0;
        int kills     = Math.max(0, killPoints);
        int total     = safeBase - movePen - damagePen - deathPen - dynPen + bonus + kills;
        return new ScoreBreakdown(safeBase, movePen, damagePen, deathPen, dynPen, bonus, kills, total);
    }

    public int gameplayScore(int baseScore, int moveCount, float routeHintPenaltyPoints) {
        return gameplayScore(baseScore, moveCount, routeHintPenaltyPoints, 0);
    }

    /** Live score shown while playing, plus the kill points of the player. */
    public int gameplayScore(int baseScore, int moveCount, float routeHintPenaltyPoints, int killPoints) {
        int movePenalty = Math.max(0, moveCount) * StageConstants.ScoreSubtractFactor;
        int hintPenalty = (int) Math.floor(Math.max(0f, routeHintPenaltyPoints));
        return Math.max(0, Math.max(0, baseScore) - movePenalty - hintPenalty) + Math.max(0, killPoints);
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
