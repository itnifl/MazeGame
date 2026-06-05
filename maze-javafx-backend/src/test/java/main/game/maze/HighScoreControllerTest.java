package main.game.maze;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HighScoreControllerTest {

    @Test
    void CanReadHighScoresTest() {
        HighScoreController controller = new HighScoreController();
        controller.initialize(null, null);
        var scores = controller.getScores();

        assertTrue(3 == scores.size());
        assertTrue("KingKong: 5090".equals(scores.get(0).toString()));
        assertTrue("YesMan: 2990".equals(scores.get(1).toString()));
        assertTrue("Neddis: 1520".equals(scores.get(2).toString()));
    }

    @Test
    void ContinueHandlerIsInvokedWhenSet() {
        HighScoreController controller = new HighScoreController();
        AtomicInteger calls = new AtomicInteger(0);
        controller.setOnContinue(calls::incrementAndGet);

        controller.continueGame();

        assertEquals(1, calls.get(), "Continue handler should run once when triggered");
    }

    @Test
    void ContinueWithoutHandlerIsSafe() {
        HighScoreController controller = new HighScoreController();
        // Should not throw when no handler is attached.
        assertDoesNotThrow(controller::continueGame);
    }
}


