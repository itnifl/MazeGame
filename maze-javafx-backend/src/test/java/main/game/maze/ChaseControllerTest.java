package main.game.maze;

import main.game.maze.characters.interfaces.IMovingComputerCharacter;
import main.game.maze.mazeworld.Point2D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Covers F19 AGGRESSIVE behavior: an enemy must produce a grid-aligned
 * direction vector pointing toward the player on every tick.
 */
public class ChaseControllerTest {

    private static class StubMovingCharacter implements IMovingComputerCharacter {
        @Override public boolean move(boolean force) { return false; }
        @Override public void changeDirection() {}
        @Override public void setDirection(Point2D direction) {}
    }

    @Test
    @DisplayName("getDirectionTowards returns null for non-ComputerCharacter")
    void nonComputerCharacterReturnsNull() {
        assertNull(ChaseController.getDirectionTowards(new StubMovingCharacter(), new Point2D(10, 10)));
    }

    @Test
    @DisplayName("getDirectionTowards returns null when target is null")
    void nullTargetReturnsNull() {
        assertNull(ChaseController.getDirectionTowards(new StubMovingCharacter(), null));
    }

    @Test
    @DisplayName("directionBetween moves right when target is east")
    void movesRight() {
        Point2D d = ChaseController.directionBetween(0, 0, 50, 0);
        assertEquals(1, d.getX());
        assertEquals(0, d.getY());
    }

    @Test
    @DisplayName("directionBetween moves left when target is west")
    void movesLeft() {
        Point2D d = ChaseController.directionBetween(50, 0, 0, 0);
        assertEquals(-1, d.getX());
        assertEquals(0, d.getY());
    }

    @Test
    @DisplayName("directionBetween moves down (positive Y) when target is south in screen coords")
    void movesDown() {
        Point2D d = ChaseController.directionBetween(0, 0, 0, 50);
        assertEquals(0, d.getX());
        assertEquals(1, d.getY());
    }

    @Test
    @DisplayName("directionBetween moves up when target is north")
    void movesUp() {
        Point2D d = ChaseController.directionBetween(0, 50, 0, 0);
        assertEquals(0, d.getX());
        assertEquals(-1, d.getY());
    }

    @Test
    @DisplayName("diagonal target collapses onto the dominant horizontal axis")
    void dominantHorizontalAxisWins() {
        Point2D d = ChaseController.directionBetween(0, 0, 100, 30);
        assertEquals(1, d.getX());
        assertEquals(0, d.getY());
    }

    @Test
    @DisplayName("diagonal target collapses onto the dominant vertical axis")
    void dominantVerticalAxisWins() {
        Point2D d = ChaseController.directionBetween(0, 0, 30, 100);
        assertEquals(0, d.getX());
        assertEquals(1, d.getY());
    }

    @Test
    @DisplayName("targets within alignment threshold yield zero motion on that axis")
    void zeroMotionWithinThreshold() {
        Point2D d = ChaseController.directionBetween(0, 0, 0.5, 0.5);
        assertEquals(0, d.getX());
        assertEquals(0, d.getY());
    }
}


