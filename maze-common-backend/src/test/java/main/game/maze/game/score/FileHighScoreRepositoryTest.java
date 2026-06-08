package main.game.maze.game.score;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import main.game.maze.dto.Score;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileHighScoreRepositoryTest {

    @Test
    void upsertAndLoadScoresAreSortedDescending() throws Exception {
        Path temp = Files.createTempFile("maze-scores", ".txt");
        try {
            HighScoreRepository repository = new FileHighScoreRepository(temp.toString());

            assertTrue(repository.upsertScore("alice", 25));
            assertTrue(repository.upsertScore("Bob", 90));
            assertTrue(repository.upsertScore("ALICE", 60));

            List<Score> scores = repository.loadScores();

            assertEquals(2, scores.size());
            assertEquals("Bob", scores.get(0).getName());
            assertEquals(90, scores.get(0).getTheScore());
            assertEquals("alice", scores.get(1).getName());
            assertEquals(60, scores.get(1).getTheScore());
        } finally {
            Files.deleteIfExists(temp);
        }
    }
}
