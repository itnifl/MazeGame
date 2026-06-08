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
import org.junit.jupiter.api.Test;

class GdxWinScoreSupportTest {

    @Test
    void appendCharacterAcceptsOnlyAllowedCharactersAndHonorsMaxLength() {
        StringBuilder input = new StringBuilder();

        assertTrue(GdxWinScoreSupport.appendCharacter(input, 'A', 3));
        assertTrue(GdxWinScoreSupport.appendCharacter(input, ' ', 3));
        assertTrue(GdxWinScoreSupport.appendCharacter(input, '-', 3));
        assertEquals("A -", input.toString());
        assertTrue(GdxWinScoreSupport.appendCharacter(input, 'Z', 3));
        assertEquals("A -", input.toString());

        StringBuilder invalid = new StringBuilder();
        assertFalse(GdxWinScoreSupport.appendCharacter(invalid, '@', 3));
        assertEquals("", invalid.toString());
    }

    @Test
    void backspaceAndPlayerNameTrimInput() {
        StringBuilder input = new StringBuilder(" Ada ");
        GdxWinScoreSupport.backspace(input);
        assertEquals(" Ada", input.toString());
        assertEquals("Ada", GdxWinScoreSupport.playerName(input));
        assertTrue(GdxWinScoreSupport.hasPlayerName(input));

        StringBuilder blank = new StringBuilder("   ");
        assertFalse(GdxWinScoreSupport.hasPlayerName(blank));
    }

    @Test
    void saveScoreDelegatesThroughScoreSupport() {
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

        assertTrue(GdxWinScoreSupport.saveScore(repository, new StringBuilder("Ada"), 42));
        assertTrue(called[0]);
        assertFalse(GdxWinScoreSupport.saveScore(repository, new StringBuilder("Other"), 7));
    }
}
