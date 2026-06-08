package main.game.maze.game.score;

import java.util.List;
import main.game.maze.dto.Score;

public interface HighScoreRepository {
    /**
     * Loads scores sorted in descending score order (highest first).
     */
    List<Score> loadScores();

    boolean upsertScore(String playerName, int score);
}
