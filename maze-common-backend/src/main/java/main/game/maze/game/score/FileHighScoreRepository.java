package main.game.maze.game.score;

import java.util.Objects;
import java.util.List;
import main.game.maze.dto.HighScoreFile;
import main.game.maze.dto.Score;

public final class FileHighScoreRepository implements HighScoreRepository {

    private final String filePath;

    public FileHighScoreRepository(String filePath) {
        this.filePath = Objects.requireNonNull(filePath, "filePath must not be null");
    }

    @Override
    public List<Score> loadScores() {
        return HighScoreFile.readSortedDescending(filePath);
    }

    @Override
    public boolean upsertScore(String playerName, int score) {
        return HighScoreFile.upsert(playerName, score, filePath);
    }
}
