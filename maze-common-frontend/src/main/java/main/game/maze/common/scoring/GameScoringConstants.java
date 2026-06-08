package main.game.maze.common.scoring;

import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.HardDifficulty;
import main.game.maze.difficulties.NormalDifficulty;

/**
 * Shared scoring constants used by both the JavaFX and libGDX frontends so
 * the rule GR-17 (identical base score per difficulty) is enforced by a
 * single source of truth.
 */
public final class GameScoringConstants {

    public static final int EASY_BASE_SCORE = 5000;
    public static final int NORMAL_BASE_SCORE = 10000;
    public static final int HARD_BASE_SCORE = 30000;

    private GameScoringConstants() {
    }

    public static int baseScoreFor(Difficulty difficulty) {
        if (difficulty instanceof HardDifficulty) {
            return HARD_BASE_SCORE;
        }
        if (difficulty instanceof NormalDifficulty) {
            return NORMAL_BASE_SCORE;
        }
        return EASY_BASE_SCORE;
    }
}
