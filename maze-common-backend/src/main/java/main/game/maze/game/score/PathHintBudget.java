package main.game.maze.game.score;

import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.HardDifficulty;
import main.game.maze.difficulties.NormalDifficulty;

/**
 * Tracks route hint usage budget and spent state.
 */
public final class PathHintBudget {

    public static final float EASY_SECONDS = 45f;
    public static final float NORMAL_SECONDS = 25f;
    public static final float HARD_SECONDS = 15f;

    private final float budgetSeconds;
    private float usedSeconds;
    private boolean exhausted;

    public PathHintBudget(float budgetSeconds) {
        this.budgetSeconds = Math.max(0f, budgetSeconds);
    }

    public static PathHintBudget forDifficulty(Difficulty difficulty) {
        return new PathHintBudget(secondsForDifficulty(difficulty));
    }

    public static float secondsForDifficulty(Difficulty difficulty) {
        if (difficulty instanceof HardDifficulty) {
            return HARD_SECONDS;
        }
        if (difficulty instanceof NormalDifficulty) {
            return NORMAL_SECONDS;
        }
        return EASY_SECONDS;
    }

    public float consume(float requestedSeconds) {
        if (!Float.isFinite(requestedSeconds) || requestedSeconds <= 0f || exhausted) {
            return 0f;
        }
        float remaining = remainingSeconds();
        if (remaining <= 0f) {
            usedSeconds = budgetSeconds;
            exhausted = true;
            return 0f;
        }
        float consumed = Math.min(requestedSeconds, remaining);
        usedSeconds += consumed;
        if (usedSeconds >= budgetSeconds) {
            usedSeconds = budgetSeconds;
            exhausted = true;
        }
        return consumed;
    }

    public float remainingSeconds() {
        return Math.max(0f, budgetSeconds - usedSeconds);
    }

    public float usedSeconds() {
        return usedSeconds;
    }

    public float budgetSeconds() {
        return budgetSeconds;
    }

    public boolean exhausted() {
        return exhausted;
    }
}
