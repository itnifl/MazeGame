package main.game.maze.libgdx.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.generators.RealMaze;
import main.game.maze.mazeworld.generators.WallSegment;
import main.game.maze.mazeworld.Vector2D;

class RealMazeTest {

    @Test
    void wrapsGameMazeWorldAndReportsBounds() {
        GameMazeWorld world = GameMazeWorld.RegenerateWorld(800, 600);
        RealMaze maze = new RealMaze(world, 800, 600);
        assertEquals(800f, maze.widthPx());
        assertEquals(600f, maze.heightPx());
        assertNotNull(maze.walls());
        assertFalse(maze.walls().isEmpty(), "generated maze should produce walls");
    }

    @Test
    void freshGeneratesNonEmptyArena() {
        RealMaze maze = RealMaze.fresh(640, 480);
        assertEquals(640f, maze.widthPx());
        assertEquals(480f, maze.heightPx());
        assertFalse(maze.walls().isEmpty());
        assertTrue(maze.startX() >= 0f && maze.startX() <= maze.widthPx());
        assertTrue(maze.goalX()  >= 0f && maze.goalX()  <= maze.widthPx());
    }

    @Test
    void freshStartPositionIsNotInsideAnyWall() {
        RealMaze maze = RealMaze.fresh(640, 480);
        PlayerState spawn = PlayerState.spawnAwayFromWalls(maze.startX(), maze.startY(), 16f, maze);

        assertFalse(spawn.collidesWith(maze), "the shared maze start should not place the player inside a wall");
        assertTrue(spawn.x() >= 0f && spawn.x() <= maze.widthPx(),
                "spawn x should stay inside the maze bounds");
        assertTrue(spawn.y() >= 0f && spawn.y() <= maze.heightPx(),
                "spawn y should stay inside the maze bounds");
    }

    @Test
    void rejectsInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> new RealMaze(null, 100, 100));
        GameMazeWorld w = new GameMazeWorld();
        assertThrows(IllegalArgumentException.class, () -> new RealMaze(w, 0, 100));
        assertThrows(IllegalArgumentException.class, () -> new RealMaze(w, 100, 0));
    }

    @Test
    void flipsYCoordinatesToLibgdxOrigin() {
        // Build a world with one known wall and verify the y is flipped.
        GameMazeWorld world = new GameMazeWorld();
        // GameMazeWorld() seeds a deterministic set of walls; pick any one
        // and check that its y has been reflected around heightPx.
        int heightPx = 600;
        RealMaze maze = new RealMaze(world, 800, heightPx);
        Vector2D first = world.getMazeVectors().get(0);
        WallSegment translated = maze.walls().get(0);
        assertEquals(heightPx - (float) first.getStart().getY(), translated.y1, 0.001f);
        assertEquals(heightPx - (float) first.getEnd().getY(),   translated.y2, 0.001f);
    }
}
