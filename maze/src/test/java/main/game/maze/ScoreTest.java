package main.game.maze;

import main.game.maze.dto.Score;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Score DTO class covering comparisons, formatting, and accessors.
 */
public class ScoreTest {

    @Test
    @DisplayName("compareTo returns positive when this score is higher")
    void compareToReturnsPositiveWhenHigher() {
        Score higher = new Score("Alice", 500);
        Score lower = new Score("Bob", 300);
        
        assertTrue(higher.compareTo(lower) > 0, 
            "Higher score should return positive comparison");
    }

    @Test
    @DisplayName("compareTo returns negative when this score is lower")
    void compareToReturnsNegativeWhenLower() {
        Score lower = new Score("Alice", 200);
        Score higher = new Score("Bob", 400);
        
        assertTrue(lower.compareTo(higher) < 0, 
            "Lower score should return negative comparison");
    }

    @Test
    @DisplayName("compareTo returns zero when scores are equal")
    void compareToReturnsZeroWhenEqual() {
        Score score1 = new Score("Alice", 350);
        Score score2 = new Score("Bob", 350);
        
        assertEquals(0, score1.compareTo(score2), 
            "Equal scores should return zero comparison");
    }

    @Test
    @DisplayName("toString formats as 'name: score'")
    void toStringFormatsCorrectly() {
        Score score = new Score("Player1", 1000);
        
        assertEquals("Player1: 1000", score.toString(), 
            "toString should format as 'name: score'");
    }

    @Test
    @DisplayName("getName returns the player name")
    void getNameReturnsCorrectValue() {
        Score score = new Score("TestPlayer", 100);
        
        assertEquals("TestPlayer", score.getName());
    }

    @Test
    @DisplayName("getTheScore returns the score value")
    void getTheScoreReturnsCorrectValue() {
        Score score = new Score("Player", 999);
        
        assertEquals(999, score.getTheScore());
    }
}
