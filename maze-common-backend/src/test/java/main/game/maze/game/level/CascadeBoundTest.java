package main.game.maze.game.level;

import static org.junit.jupiter.api.Assertions.assertTrue;

import main.game.maze.mazeworld.BreakableWall;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.mazeworld.generators.DfsMazeGenerator;
import main.game.maze.mazeworld.generators.MazeGeneratorConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Breakable walls that touch fall together (the cascade rule), so a level must keep enough solid walls
 * to stop it: breaking one wall must never bring down a large part of the maze.
 */
@DisplayName("Level walls: one broken wall never brings down much of the maze")
class CascadeBoundTest {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 665;
    /** Largest share of all walls that one broken wall may take with it. */
    private static final double MAX_SHARE = 0.10;

    private static GameMazeWorld freshMaze() {
        return new GameMazeWorld(new DfsMazeGenerator(new MazeGeneratorConfig(WIDTH, HEIGHT,
                StageConstants.WallSegmentLengthPx, StageConstants.HallwayWidthPx, StageConstants.HallwayWidthPx, 40)));
    }

    private static double worstShare(GameMazeWorld world) {
        int worst = 0;
        for (BreakableWall wall : world.getBreakableWalls()) {
            worst = Math.max(worst, world.cascadeSize(wall));
        }
        return worst / (double) world.getMazeVectors().size();
    }

    @Test
    @DisplayName("every shipped level keeps the worst cascade small, in many generated mazes")
    void shippedLevelsBoundTheCascade() {
        LevelCatalog catalog = LevelCatalogLoader.loadDefault();
        for (int number = 1; number <= catalog.count(); number++) {
            LevelSpec level = catalog.level(number);
            double worst = 0;
            for (int maze = 0; maze < 12; maze++) {
                GameMazeWorld world = freshMaze();
                level.applyWalls(world, WIDTH, HEIGHT);
                worst = Math.max(worst, worstShare(world));
            }
            assertTrue(worst <= MAX_SHARE, "level " + number + ": one broken wall can bring down "
                    + Math.round(worst * 100) + " % of the maze (limit " + Math.round(MAX_SHARE * 100) + " %)");
        }
    }
}
