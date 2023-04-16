package main.game.maze;

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
}
