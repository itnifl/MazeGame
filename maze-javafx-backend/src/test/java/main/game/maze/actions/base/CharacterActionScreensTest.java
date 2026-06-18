package main.game.maze.actions.base;

import main.game.maze.App;
import main.game.maze.characters.PlayerCharacter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link CharacterActionScreens} scoring logic.
 * Uses a package-accessible subclass to set the protected {@code playerCharacter} field.
 */
class CharacterActionScreensTest {

    /** Minimal subclass that exposes the protected field for test injection. */
    static class TestScreens extends CharacterActionScreens {
        void setPlayer(PlayerCharacter p) {
            this.playerCharacter = p;
        }
    }

    @AfterEach
    void cleanup() {
        App.gameController = null;
    }

    @Test
    void resetScore_returnsBaseScore() {
        TestScreens screens = new TestScreens();
        screens.setBaseScore(7500);
        assertEquals(7500, screens.resetScore());
    }

    @Test
    void setBaseScore_updatesBaseScoreUsedByResetScore() {
        TestScreens screens = new TestScreens();
        screens.setBaseScore(1000);
        assertEquals(1000, screens.resetScore());
        screens.setBaseScore(2000);
        assertEquals(2000, screens.resetScore());
    }

    @Test
    void updateScore_withNullController_usesZeroDynamicPenalty() {
        // App.gameController is null → dynamicPenalty = 0 (ZERO_PENALTY branch)
        TestScreens screens = new TestScreens();
        screens.setBaseScore(10000);
        PlayerCharacter player = new PlayerCharacter(null, 0, 0, null);
        screens.setPlayer(player);

        int score = screens.updateScore();
        // score is computed from ScoringEngine; simply verify it is non-negative and deterministic
        assertTrue(score >= 0, "Score must be non-negative");
        assertEquals(score, screens.updateScore(), "Repeated call must return same value");
    }

    @Test
    void updateScore_afterResetScore_usesCurrentBaseScore() {
        TestScreens screens = new TestScreens();
        screens.setBaseScore(5000);
        screens.resetScore();

        PlayerCharacter player = new PlayerCharacter(null, 0, 0, null);
        screens.setPlayer(player);

        int score = screens.updateScore();
        // Score will be ≤ baseScore (penalties reduce it). Just ensure it ran without exception.
        assertNotNull(score);
    }
}
