package main.game.maze.service;

import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.HardDifficulty;
import main.game.maze.difficulties.NormalDifficulty;
import main.game.maze.mazeworld.constants.StageConstants;

/**
 * Shared difficulty presentation helpers used by multiple frontends.
 */
public final class DifficultyPresentationSupport {

    private DifficultyPresentationSupport() {
    }

    public static int boardWidth(Difficulty selected) {
        if (selected instanceof HardDifficulty) {
            return StageConstants.BoardMaxXLarge;
        }
        if (selected instanceof NormalDifficulty) {
            return StageConstants.BoardMaxXMedium;
        }
        return StageConstants.BoardMaxX;
    }

    public static int boardHeight(Difficulty selected) {
        if (selected instanceof HardDifficulty) {
            return StageConstants.BoardMaxYLarge;
        }
        if (selected instanceof NormalDifficulty) {
            return StageConstants.BoardMaxYMedium;
        }
        return StageConstants.BoardMaxY;
    }

    public static String boardSizeLabel(Difficulty selected) {
        return boardWidth(selected) + "x" + boardHeight(selected);
    }

    public static String displayName(Difficulty difficulty) {
        if (difficulty == null) {
            return "";
        }
        String modelName = difficulty.eClass().getName();
        if (modelName.endsWith("Difficulty")) {
            return modelName.substring(0, modelName.length() - "Difficulty".length());
        }
        return modelName;
    }

    public static String displayNameOr(Difficulty difficulty, String nullFallback) {
        String displayName = displayName(difficulty);
        if (!displayName.isEmpty()) {
            return displayName;
        }
        return nullFallback;
    }
}

