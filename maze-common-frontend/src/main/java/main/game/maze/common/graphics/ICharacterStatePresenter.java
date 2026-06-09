package main.game.maze.common.graphics;

/**
 * UI adapter for presenting character state (for now, health ratio).
 */
public interface ICharacterStatePresenter {
    void showHealthRatio(double ratio);
    void dispose();
}
