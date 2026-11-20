package main.game.maze.mazeworld;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.LevelSpec;
import main.game.maze.mazeworld.flame.DirectionalFlameEngine;
import main.game.maze.mazeworld.generators.DfsMazeGenerator;
import main.game.maze.mazeworld.generators.MazeGeneratorConfig;
import main.game.maze.mazeworld.constants.StageConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Cascade depth: breakable walls that touch fall together, up to a limit")
class CascadeDepthTest {

    /** Six walls in a row, each sharing an endpoint with the next; all breakable. */
    private static GameMazeWorld chainOfSix() {
        GameMazeWorld world = new GameMazeWorld(() -> {
            List<Vector2D> walls = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                walls.add(new Vector2D(100 + i * 20, 100, 120 + i * 20, 100));
            }
            return walls;
        });
        world.assignBreakableWalls(1L, List.of(new WallMaterialSpec("GLASS_BASIC", "Glass", 5)));
        // assignBreakableWalls makes about half of them breakable: make all of them so
        world.getBreakableWalls().clear();
        for (Vector2D wall : world.getMazeVectors()) {
            world.getBreakableWalls().add(new BreakableWall(wall, new WallMaterialSpec("GLASS_BASIC", "Glass", 5)));
        }
        return world;
    }

    private static int fallsWhenFirstBreaks(GameMazeWorld world) {
        BreakableWall first = world.getBreakableWalls().get(0);
        return world.cascadeSize(first);
    }

    @Test
    @DisplayName("without a limit the whole connected chain falls (the classic rule)")
    void unlimited() {
        assertEquals(6, fallsWhenFirstBreaks(chainOfSix()));
    }

    @Test
    @DisplayName("a limit of two steps takes the wall and two neighbours along the chain")
    void depthTwo() {
        GameMazeWorld world = chainOfSix();
        world.setMaxCascadeDepth(2);
        assertEquals(3, fallsWhenFirstBreaks(world));
    }

    @Test
    @DisplayName("a limit of zero takes only the wall that was hit; a negative limit means no limit")
    void zeroAndNegative() {
        GameMazeWorld world = chainOfSix();
        world.setMaxCascadeDepth(0);
        assertEquals(1, fallsWhenFirstBreaks(world));
        world.setMaxCascadeDepth(-1);
        assertEquals(6, fallsWhenFirstBreaks(world));
    }

    @Test
    @DisplayName("destroying a wall removes exactly the limited cascade from the maze")
    void destroyingRemovesTheLimitedCascade() {
        GameMazeWorld world = chainOfSix();
        world.setMaxCascadeDepth(1);
        world.applyWallDamage(world.getBreakableWalls().get(2), 100);
        assertEquals(3, world.getMazeVectors().size(), "the wall hit and its two neighbours fell");
    }

    private static GameMazeWorld freshMaze(LevelSpec level) {
        GameMazeWorld world = new GameMazeWorld(new DfsMazeGenerator(new MazeGeneratorConfig(800, 665,
                StageConstants.WallSegmentLengthPx, StageConstants.HallwayWidthPx, StageConstants.HallwayWidthPx, 40)));
        level.applyWalls(world, 800, 665);
        return world;
    }

    @Test
    @DisplayName("in level 2 a bomb brings down some walls, never a large part of the maze")
    void bombOnLevelTwo() {
        LevelSpec level = LevelCatalogLoader.loadDefault().level(2);
        int bombs = 0;
        int bombsThatBrokeAWall = 0;
        double worstShare = 0;
        for (int maze = 0; maze < 6; maze++) {
            GameMazeWorld world = freshMaze(level);
            int total = world.getMazeVectors().size();
            var points = new main.game.maze.game.items.PickupField(
                    main.game.maze.game.items.PickupCatalogLoader.loadDefault(), new java.util.Random(maze))
                    .spawnPointsOf(world);
            for (int p = 0; p < points.size(); p += Math.max(1, points.size() / 12)) {
                Point2D at = points.get(p);
                int before = world.getMazeVectors().size();
                for (int[] dir : new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}) {
                    DirectionalFlameEngine.applyDirectionalFlame(List.of(), world, world.getMazeVectors(),
                            at.getX(), at.getY(), dir[0], dir[1], 100, 120, 120);
                }
                int fell = before - world.getMazeVectors().size();
                bombs++;
                if (fell > 0) {
                    bombsThatBrokeAWall++;
                }
                worstShare = Math.max(worstShare, fell / (double) total);
            }
        }
        assertTrue(bombsThatBrokeAWall >= bombs * 0.8,
                "most bombs break a wall: " + bombsThatBrokeAWall + " of " + bombs);
        assertTrue(worstShare <= 0.10, "one bomb never takes down more than 10 % of the maze: "
                + Math.round(worstShare * 100) + " %");
    }
}
