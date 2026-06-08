package main.game.maze.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class HighScoreFileTest {

    @Test
    void appendsNewName(@TempDir Path tmp) throws IOException {
        Path file = tmp.resolve("scores.txt");
        Files.writeString(file, "Alice: 100\n");
        assertTrue(HighScoreFile.upsert("Bob", 50, file.toString()));
        List<Score> scores = HighScoreFile.read(file.toString());
        assertEquals(2, scores.size());
        assertEquals("Alice", scores.get(0).getName());
        assertEquals("Bob", scores.get(1).getName());
        assertEquals(50, scores.get(1).getTheScore());
    }

    @Test
    void upsertsCaseInsensitivelyAndKeepsHigherScore(@TempDir Path tmp) throws IOException {
        Path file = tmp.resolve("scores.txt");
        Files.writeString(file, "Alice: 100\n");
        assertTrue(HighScoreFile.upsert("ALICE", 250, file.toString()));
        List<Score> scores = HighScoreFile.read(file.toString());
        assertEquals(1, scores.size());
        assertEquals("Alice", scores.get(0).getName());
        assertEquals(250, scores.get(0).getTheScore());
    }

    @Test
    void doesNotLowerScoreOnSecondAttempt(@TempDir Path tmp) throws IOException {
        Path file = tmp.resolve("scores.txt");
        Files.writeString(file, "Bob: 500\n");
        assertTrue(HighScoreFile.upsert("bob", 10, file.toString()));
        List<Score> scores = HighScoreFile.read(file.toString());
        assertEquals(1, scores.size());
        assertEquals(500, scores.get(0).getTheScore());
    }

    @Test
    void createsFileWhenMissing(@TempDir Path tmp) {
        Path file = tmp.resolve("nested/scores.txt");
        assertTrue(HighScoreFile.upsert("New", 42, file.toString()));
        List<Score> scores = HighScoreFile.read(file.toString());
        assertEquals(1, scores.size());
        assertEquals(42, scores.get(0).getTheScore());
    }

    @Test
    void dedupesExistingDuplicateNamesKeepingHighestScore(@TempDir Path tmp) throws IOException {
        Path file = tmp.resolve("scores.txt");
        Files.writeString(file, "Alice: 100\nBob: 200\nALICE: 350\nalice: 50\n");
        assertTrue(HighScoreFile.upsert("Carol", 10, file.toString()));
        List<Score> scores = HighScoreFile.read(file.toString());
        assertEquals(3, scores.size());
        assertEquals("ALICE", scores.get(0).getName());
        assertEquals(350, scores.get(0).getTheScore());
        assertEquals("Bob", scores.get(1).getName());
        assertEquals(200, scores.get(1).getTheScore());
        assertEquals("Carol", scores.get(2).getName());
    }

    @Test
    void dedupesAndUpdatesWhenNewScoreBeatsAllDuplicates(@TempDir Path tmp) throws IOException {
        Path file = tmp.resolve("scores.txt");
        Files.writeString(file, "Alice: 100\nALICE: 350\nalice: 50\n");
        assertTrue(HighScoreFile.upsert("alice", 900, file.toString()));
        List<Score> scores = HighScoreFile.read(file.toString());
        assertEquals(1, scores.size());
        assertEquals(900, scores.get(0).getTheScore());
    }

    @Test
    void dedupesButDoesNotLowerWhenNewScoreLosesToAnyDuplicate(@TempDir Path tmp) throws IOException {
        Path file = tmp.resolve("scores.txt");
        Files.writeString(file, "Alice: 100\nALICE: 350\nalice: 50\n");
        assertTrue(HighScoreFile.upsert("alice", 200, file.toString()));
        List<Score> scores = HighScoreFile.read(file.toString());
        assertEquals(1, scores.size());
        assertEquals("ALICE", scores.get(0).getName());
        assertEquals(350, scores.get(0).getTheScore());
    }
}
