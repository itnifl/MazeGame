package main.game.maze.game.hazard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import main.game.maze.game.items.PickupField;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.LevelSpec;
import main.game.maze.game.level.SergeantPosts;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.WallCollisionUtil;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.mazeworld.generators.DfsMazeGenerator;
import main.game.maze.mazeworld.generators.MazeGeneratorConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Mines and teleport exits in a generated maze: only on free ground")
class MazeHazardsTest {

    private static GameMazeWorld freshMaze() {
        return new GameMazeWorld(new DfsMazeGenerator(new MazeGeneratorConfig(800, 665,
                StageConstants.WallSegmentLengthPx, StageConstants.HallwayWidthPx, StageConstants.HallwayWidthPx, 40)));
    }

    @Test
    @DisplayName("every mine lies clear of the walls and can be seen from its sergeant, in many mazes")
    void minesAreOnFreeGround() {
        LevelSpec level = LevelCatalogLoader.loadDefault().level(3);
        int totalMines = 0;
        for (int maze = 0; maze < 8; maze++) {
            GameMazeWorld world = freshMaze();
            List<Point2D> corridor = MazeHazards.freePoints(world);
            List<Point2D> posts = SergeantPosts.choose(corridor, 400, 332, level.sergeant().postDistanceFromHeart(), 4, 80);
            MineField field = new MineField(level.mines());
            MazeHazards.placeMines(field, posts, world);
            List<Vector2D> walls = world.getMazeVectors();
            for (MineField.Mine mine : field.mines()) {
                totalMines++;
                for (Vector2D wall : walls) {
                    assertTrue(PickupField.distanceToSegment(mine.x(), mine.y(), wall) >= MazeHazards.WALL_CLEARANCE - 1e-6,
                            "clear of walls");
                }
                boolean seen = false;
                for (Point2D post : posts) {
                    if (Math.hypot(post.getX() - mine.x(), post.getY() - mine.y()) <= level.mines().ringRadius() * 1.4 + 1
                            && !WallCollisionUtil.wallBetweenVectors(post.getX(), post.getY(), mine.x(), mine.y(), walls)) {
                        seen = true;
                    }
                }
                assertTrue(seen, "no wall between a mine and its sergeant");
            }
        }
        assertTrue(totalMines > 8 * 4, "the mazes do get mines: " + totalMines);
    }

    @Test
    @DisplayName("the free points are never on a wall, so a teleport exit is never inside one")
    void freePointsAreClearOfWalls() {
        GameMazeWorld world = freshMaze();
        List<Vector2D> walls = world.getMazeVectors();
        List<Point2D> free = MazeHazards.freePoints(world);
        assertFalse(free.isEmpty());
        for (Point2D point : free) {
            for (Vector2D wall : walls) {
                assertTrue(PickupField.distanceToSegment(point.getX(), point.getY(), wall) > StageConstants.WallThicknessPx / 2.0);
            }
        }
    }
}
