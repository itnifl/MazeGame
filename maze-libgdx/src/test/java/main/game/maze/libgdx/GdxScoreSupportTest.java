package main.game.maze.libgdx;

import main.game.maze.libgdx.adapter.*;
import main.game.maze.libgdx.controller.*;
import main.game.maze.libgdx.helper.*;
import main.game.maze.libgdx.service.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import main.game.maze.dto.Score;
import main.game.maze.game.score.HighScoreRepository;
import main.game.maze.game.score.ScoringEngine;
import main.game.maze.game.session.GameSession;
import org.junit.jupiter.api.Test;

class GdxScoreSupportTest {

    @Test
    void currentScoreDelegatesToSharedScoringEngine() {
        ScoringEngine scoringEngine = new ScoringEngine();
        GameSession session = new GameSession();
        session.setBaseScore(1_000);
        session.incrementMoveCount();
        session.incrementMoveCount();

        assertEquals(scoringEngine.gameplayScore(1_000, 2, 0f),
                GdxScoreSupport.currentScore(scoringEngine, session, 0f));
        assertEquals(scoringEngine.gameplayScore(1_000, 2, 55f),
                GdxScoreSupport.currentScore(scoringEngine, session, 55f));
    }

    @Test
    void loadHighScoresDelegatesToRepository() {
        HighScoreRepository repository = new HighScoreRepository() {
            @Override
            public List<Score> loadScores() {
                return List.of(new Score("Ada", 42));
            }

            @Override
            public boolean upsertScore(String playerName, int score) {
                return false;
            }
        };

        List<Score> scores = GdxScoreSupport.loadHighScores(repository);

        assertEquals(1, scores.size());
        assertEquals("Ada", scores.get(0).getName());
        assertEquals(42, scores.get(0).getTheScore());
    }

    @Test
    void saveHighScoreDelegatesToRepository() {
        final boolean[] called = {false};
        HighScoreRepository repository = new HighScoreRepository() {
            @Override
            public List<Score> loadScores() {
                return List.of();
            }

            @Override
            public boolean upsertScore(String playerName, int score) {
                called[0] = true;
                return "Ada".equals(playerName) && score == 42;
            }
        };

        assertTrue(GdxScoreSupport.saveHighScore(repository, "Ada", 42));
        assertTrue(called[0]);
        assertFalse(GdxScoreSupport.saveHighScore(repository, "Other", 7));
    }
}



