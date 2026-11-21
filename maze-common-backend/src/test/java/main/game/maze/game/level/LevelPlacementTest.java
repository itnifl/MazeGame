package main.game.maze.game.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import main.game.maze.game.hazard.EdgeTeleport;
import main.game.maze.game.hazard.MazeHazards;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.mazeworld.generators.DfsMazeGenerator;
import main.game.maze.mazeworld.generators.MazeGeneratorConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Level placement: where sergeants and portal ghosts go, chosen once for both frontends")
class LevelPlacementTest {

    private static final LevelCatalog LEVELS = LevelCatalogLoader.loadDefault();

    private static GameMazeWorld freshMaze() {
        return new GameMazeWorld(new DfsMazeGenerator(new MazeGeneratorConfig(800, 665,
                StageConstants.WallSegmentLengthPx, StageConstants.HallwayWidthPx, StageConstants.HallwayWidthPx, 40)));
    }

    @Test
    @DisplayName("sergeant posts: near the heart, apart from each other, free ground, never more than the level says")
    void sergeantPosts() {
        GameMazeWorld world = freshMaze();
        List<Point2D> free = MazeHazards.freePoints(world);
        LevelSpec level = LEVELS.level(2);
        for (DifficultyTier tier : DifficultyTier.values()) {
            List<Point2D> posts = LevelPlacement.sergeantPosts(level, tier, world, 400, 332);
            assertTrue(posts.size() <= level.sergeantCount(tier), tier + ": " + posts.size());
            assertTrue(posts.size() >= 1, "the maze has room for at least one post");
            for (Point2D post : posts) {
                assertTrue(free.contains(post), "free ground");
                assertTrue(Math.abs(Math.hypot(post.getX() - 400, post.getY() - 332) - level.sergeant().postDistanceFromHeart()) <= 70,
                        "close to the post ring");
            }
            for (int a = 0; a < posts.size(); a++) {
                for (int b = a + 1; b < posts.size(); b++) {
                    assertTrue(Math.hypot(posts.get(a).getX() - posts.get(b).getX(), posts.get(a).getY() - posts.get(b).getY())
                            >= LevelPlacement.SERGEANT_POST_SPACING);
                }
            }
        }
    }

    @Test
    @DisplayName("the same maze gives the same posts and ghost spots every time")
    void deterministic() {
        GameMazeWorld world = freshMaze();
        assertEquals(LevelPlacement.sergeantPosts(LEVELS.level(3), DifficultyTier.HARD, world, 400, 332),
                LevelPlacement.sergeantPosts(LEVELS.level(3), DifficultyTier.HARD, world, 400, 332));
        assertEquals(LevelPlacement.portalGhostSpots(LEVELS.level(3), DifficultyTier.HARD, world, 40, 600),
                LevelPlacement.portalGhostSpots(LEVELS.level(3), DifficultyTier.HARD, world, 40, 600));
    }

    @Test
    @DisplayName("portal ghosts: 10 / 6 / 3 on hard / normal / easy, on free ground, away from the player's start")
    void portalGhostSpots() {
        GameMazeWorld world = freshMaze();
        List<Point2D> free = MazeHazards.freePoints(world);
        LevelSpec level = LEVELS.level(3);
        assertEquals(10, LevelPlacement.portalGhostSpots(level, DifficultyTier.HARD, world, 40, 600).size());
        assertEquals(6, LevelPlacement.portalGhostSpots(level, DifficultyTier.NORMAL, world, 40, 600).size());
        assertEquals(3, LevelPlacement.portalGhostSpots(level, DifficultyTier.EASY, world, 40, 600).size());
        for (Point2D spot : LevelPlacement.portalGhostSpots(level, DifficultyTier.HARD, world, 40, 600)) {
            assertTrue(free.contains(spot));
            assertTrue(Math.hypot(spot.getX() - 40, spot.getY() - 600) >= LevelPlacement.PORTAL_GHOST_MIN_START_DISTANCE);
        }
    }

    @Test
    @DisplayName("guard towers: 2 / 3 / 4 on easy / normal / hard, on free ground in the outer band, spread apart")
    void towerPosts() {
        GameMazeWorld world = freshMaze();
        List<Point2D> free = MazeHazards.freePoints(world);
        List<Point2D> outer = EdgeTeleport.outerBand(free);
        LevelSpec level = LEVELS.level(4);
        for (DifficultyTier tier : DifficultyTier.values()) {
            List<Point2D> posts = LevelPlacement.towerPosts(level, tier, world, 400, 332);
            assertEquals(level.towerCount(tier), posts.size(), tier.name());
            for (Point2D post : posts) {
                assertTrue(free.contains(post), "free ground");
                assertTrue(outer.contains(post), "outer part of the map");
                assertTrue(Math.hypot(post.getX() - 400, post.getY() - 332) >= LevelPlacement.TOWER_MIN_START_DISTANCE);
            }
            assertEquals(posts, LevelPlacement.towerPosts(level, tier, world, 400, 332), "the same every time");
        }
        assertTrue(LevelPlacement.towerPosts(LEVELS.level(3), DifficultyTier.HARD, world, 400, 332).isEmpty());
        assertTrue(LevelPlacement.towerPosts(level, DifficultyTier.HARD, null, 400, 332).isEmpty());
    }

    @Test
    @DisplayName("wall builders: 10 / 5 / 2 on hard / normal / easy, on free ground in the area round the heart")
    void builderSpots() {
        GameMazeWorld world = freshMaze();
        List<Point2D> free = MazeHazards.freePoints(world);
        LevelSpec level = LEVELS.level(5);
        assertEquals(10, LevelPlacement.builderSpots(level, DifficultyTier.HARD, world, 400, 332).size());
        assertEquals(5, LevelPlacement.builderSpots(level, DifficultyTier.NORMAL, world, 400, 332).size());
        assertEquals(2, LevelPlacement.builderSpots(level, DifficultyTier.EASY, world, 400, 332).size());
        for (Point2D spot : LevelPlacement.builderSpots(level, DifficultyTier.HARD, world, 400, 332)) {
            assertTrue(free.contains(spot));
            assertTrue(Math.hypot(spot.getX() - 400, spot.getY() - 332) <= level.wallBuilder().spawnRadius());
        }
        assertEquals(LevelPlacement.builderSpots(level, DifficultyTier.HARD, world, 400, 332),
                LevelPlacement.builderSpots(level, DifficultyTier.HARD, world, 400, 332), "the same every time");
        assertTrue(LevelPlacement.builderSpots(LEVELS.level(4), DifficultyTier.HARD, world, 400, 332).isEmpty());
    }

    @Test
    @DisplayName("spreading picks the points as far apart as it can")
    void spreadOut() {
        List<Point2D> corners = List.of(new Point2D(0, 0), new Point2D(10, 0), new Point2D(0, 10), new Point2D(10, 10),
                new Point2D(5, 5));
        List<Point2D> chosen = LevelPlacement.spreadOut(corners, 2);
        assertEquals(2, chosen.size());
        assertTrue(Math.hypot(chosen.get(0).getX() - chosen.get(1).getX(), chosen.get(0).getY() - chosen.get(1).getY()) >= 10,
                "never two next to each other when there is room");
        assertTrue(LevelPlacement.spreadOut(List.of(), 3).isEmpty());
        assertEquals(1, LevelPlacement.spreadOut(List.of(new Point2D(1, 1)), 3).size());
    }

    @Test
    @DisplayName("levels without sergeants or portal ghosts, or without a maze, place nothing")
    void nothingToPlace() {
        GameMazeWorld world = freshMaze();
        assertTrue(LevelPlacement.sergeantPosts(LEVELS.level(1), DifficultyTier.HARD, world, 400, 332).isEmpty());
        assertTrue(LevelPlacement.portalGhostSpots(LEVELS.level(2), DifficultyTier.HARD, world, 40, 600).isEmpty());
        assertTrue(LevelPlacement.sergeantPosts(LEVELS.level(2), DifficultyTier.HARD, null, 400, 332).isEmpty());
        assertTrue(LevelPlacement.portalGhostSpots(LEVELS.level(3), DifficultyTier.HARD, null, 40, 600).isEmpty());
    }
}
