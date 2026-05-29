package main.game.maze.libgdx.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.generators.SampleMaze;

class PlayerStateTest {

    private static SampleMaze maze() {
        return new SampleMaze(8, 6, 48f, 1L);
    }

    @Test
    void recordsInitialPosition() {
        PlayerState p = new PlayerState(24f, 24f, 16f);
        assertEquals(24f, p.x());
        assertEquals(24f, p.y());
        assertEquals(8f, p.halfSize());
    }

    @Test
    void rejectsNonPositiveSize() {
        assertThrows(IllegalArgumentException.class, () -> new PlayerState(0f, 0f, 0f));
        assertThrows(IllegalArgumentException.class, () -> new PlayerState(0f, 0f, -1f));
    }

    @Test
    void moveStoppedByOuterBorder() {
        SampleMaze m = maze();
        PlayerState p = new PlayerState(m.startX(), m.startY(), 16f);
        float before = p.x();
        p.attemptMove(-1000f, 0f, m);
        // Cannot pass the left wall.
        assertTrue(p.x() >= p.halfSize(), "player should remain inside the playfield");
        assertTrue(p.x() <= before, "leftward move should not increase x");
    }

    @Test
    void axesResolveIndependentlyAllowingWallSlide() {
        // Build a maze with a single horizontal wall and verify that a
        // diagonal move blocked vertically still applies horizontally.
        SampleMaze m = new SampleMaze(4, 4, 64f, 3L);
        PlayerState p = new PlayerState(m.startX(), m.startY(), 8f);
        float startX = p.x();
        float startY = p.y();
        // Try a move strictly into the upper wall of the start cell. Even if
        // y is blocked, the player should still have a chance to slide in x.
        p.attemptMove(5f, 1000f, m);
        assertNotEquals(startX, p.x(), "horizontal slide should have taken effect");
        // y is clamped by either the cell wall or the outer border.
        assertTrue(p.y() >= startY, "y should not go below the start");
    }

    @Test
    void reachedReportsGoalProximity() {
        PlayerState p = new PlayerState(100f, 100f, 16f);
        assertTrue(p.reached(105f, 100f, 10f));
        assertFalse(p.reached(200f, 100f, 10f));
    }

    @Test
    void zeroMoveDoesNothing() {
        SampleMaze m = maze();
        PlayerState p = new PlayerState(m.startX(), m.startY(), 16f);
        p.attemptMove(0f, 0f, m);
        assertEquals(m.startX(), p.x());
        assertEquals(m.startY(), p.y());
    }
}
