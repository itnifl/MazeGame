package main.game.maze.libgdx.helper;

import main.game.maze.difficulties.Difficulty;
import main.game.maze.service.DifficultyPresentationSupport;

/**
 * Board dimension policy for libGDX based on selected difficulty.
 */
public final class DifficultyBoardConfig {

    private DifficultyBoardConfig() {
    }

    public static int boardWidth(Difficulty selected) {
        return DifficultyPresentationSupport.boardWidth(selected);
    }

    public static int boardHeight(Difficulty selected) {
        return DifficultyPresentationSupport.boardHeight(selected);
    }

    public static String boardSizeLabel(Difficulty selected) {
        return boardWidth(selected) + "x" + boardHeight(selected);
    }
}


