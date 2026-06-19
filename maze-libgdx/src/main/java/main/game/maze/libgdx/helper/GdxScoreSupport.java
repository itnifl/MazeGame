package main.game.maze.libgdx.helper;

import java.util.List;
import main.game.maze.dto.Score;
import main.game.maze.game.score.HighScoreRepository;
import main.game.maze.game.score.ScoringEngine;
import main.game.maze.game.session.GameSession;

/**
 * Shared score calculation and score persistence helpers for libGDX gameplay.
 */
public final class GdxScoreSupport {

    private GdxScoreSupport() {
    }

    public static int currentScore(ScoringEngine scoringEngine, GameSession session, float pathPenaltyPoints) {
        return scoringEngine.gameplayScore(session.baseScore(), session.moveCount(), pathPenaltyPoints);
    }

    /**
     * End-screen score shown on the GAME_OVER or WON overlay.
     * Applies the death penalty (when currentHp <= 0) and damage penalty
     * (damage * 10) in addition to the move and hint penalties.
     */
    public static int endScreenScore(
            ScoringEngine scoringEngine,
            GameSession session,
            float pathPenaltyPoints,
            int maxHp,
            int currentHp,
            boolean won) {
        int dynamicPenalty = (int) Math.floor(Math.max(0f, pathPenaltyPoints));
        return scoringEngine.characterScreenScore(
                session.baseScore(),
                session.moveCount(),
                maxHp,
                currentHp,
                dynamicPenalty,
                won);
    }

    public static List<Score> loadHighScores(HighScoreRepository repository) {
        return repository.loadScores();
    }

    public static boolean saveHighScore(HighScoreRepository repository, String playerName, int score) {
        return repository.upsertScore(playerName, score);
    }
}
