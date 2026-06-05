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

    public static List<Score> loadHighScores(HighScoreRepository repository) {
        return repository.loadScores();
    }

    public static boolean saveHighScore(HighScoreRepository repository, String playerName, int score) {
        return repository.upsertScore(playerName, score);
    }
}


