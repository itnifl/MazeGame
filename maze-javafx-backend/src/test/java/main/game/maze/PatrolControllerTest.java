package main.game.maze;

import main.game.maze.characters.interfaces.IMovingComputerCharacter;
import main.game.maze.mazeworld.Point2D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for PatrolController wander fallback and direction calculation logic.
 */
public class PatrolControllerTest {

    /**
     * Stub implementation that is NOT a ComputerCharacter.
     */
    private static class StubMovingCharacter implements IMovingComputerCharacter {
        @Override public boolean move(boolean force) { return false; }
        @Override public void changeDirection() {}
        @Override public void setDirection(Point2D direction) {}
    }

    @Test
    @DisplayName("isInWanderFallback returns false for new character")
    void isInWanderFallbackReturnsFalseForNewCharacter() {
        IMovingComputerCharacter newChar = new StubMovingCharacter();
        
        assertFalse(PatrolController.isInWanderFallback(newChar),
            "New character should not be in wander fallback");
    }

    @Test
    @DisplayName("isInWanderFallback returns true after trigger and decrements")
    void isInWanderFallbackReturnsTrueAfterTrigger() {
        IMovingComputerCharacter character = new StubMovingCharacter();
        
        PatrolController.triggerWanderFallback(character);
        
        assertTrue(PatrolController.isInWanderFallback(character),
            "Character should be in wander fallback after trigger");
        
        // Wander fallback now runs for ~5 seconds. With a ~60ms tick this
        // works out to 83 ticks (see PatrolController#WANDER_FALLBACK_TICKS).
        for (int i = 0; i < 82; i++) {
            assertTrue(PatrolController.isInWanderFallback(character),
                "Should still be in wander fallback during countdown");
        }
        
        // After 83 calls total, should be false
        assertFalse(PatrolController.isInWanderFallback(character),
            "Should exit wander fallback after 83 ticks (~5 seconds)");
    }

    @Test
    @DisplayName("getDirectionToNextPatrolPoint returns null for non-ComputerCharacter")
    void getDirectionReturnsNullForNonComputerCharacter() {
        IMovingComputerCharacter nonCC = new StubMovingCharacter();
        
        Point2D direction = PatrolController.getDirectionToNextPatrolPoint(nonCC);
        
        assertNull(direction, 
            "Should return null for non-ComputerCharacter instances");
    }

    @Test
    @DisplayName("triggerWanderFallback can be called multiple times")
    void triggerWanderFallbackResetsCounter() {
        IMovingComputerCharacter character = new StubMovingCharacter();
        
        PatrolController.triggerWanderFallback(character);
        
        // Consume some ticks
        for (int i = 0; i < 30; i++) {
            PatrolController.isInWanderFallback(character);
        }
        
        // Re-trigger should reset
        PatrolController.triggerWanderFallback(character);
        
        // Should still have 83 more ticks (~5 seconds at ~60ms per tick)
        int count = 0;
        while (PatrolController.isInWanderFallback(character)) {
            count++;
            if (count > 150) break; // safety
        }
        
        assertEquals(83, count, "Re-trigger should reset to 83 ticks (~5 seconds)");
    }
}


