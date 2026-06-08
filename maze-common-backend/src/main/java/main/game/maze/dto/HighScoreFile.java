package main.game.maze.dto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Pure-Java shared high score file IO with case-insensitive upsert semantics.
 * Reads and writes the "Name: score" line format used by both JavaFX and libGDX.
 */
public final class HighScoreFile {

    private HighScoreFile() { }

    public static List<Score> read(String path) {
        List<Score> scores = new ArrayList<>();
        File file = new File(path);
        if (!file.isFile()) {
            return scores;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(":");
                if (data.length != 2) {
                    continue;
                }
                try {
                    String name = data[0].trim();
                    int value = Integer.parseInt(data[1].trim());
                    if (!name.isEmpty()) {
                        scores.add(new Score(name, value));
                    }
                } catch (NumberFormatException ignored) {
                    // skip malformed line
                }
            }
        } catch (IOException ignored) {
            // missing or unreadable file is treated as empty list
        }
        return scores;
    }

    /**
     * Upserts a score for the given player name (case insensitive match).
     * Always deduplicates the file so each case-insensitive name appears
     * at most once, keeping the highest score recorded for that name
     * (preserving the originally stored name casing of that highest entry).
     * If no entry exists for the name, appends it. If an entry exists and
     * the new score is higher than the kept maximum, replaces it.
     */
    public static boolean upsert(String playerName, int score, String path) {
        if (playerName == null) {
            return false;
        }
        String trimmedName = playerName.trim();
        if (trimmedName.isEmpty()) {
            return false;
        }
        List<Score> scores = dedupe(read(path));
        String key = trimmedName.toLowerCase(Locale.ROOT);

        int existingIndex = -1;
        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i).getName().trim().toLowerCase(Locale.ROOT).equals(key)) {
                existingIndex = i;
                break;
            }
        }

        if (existingIndex < 0) {
            scores.add(new Score(trimmedName, score));
            return write(scores, path);
        }
        Score existing = scores.get(existingIndex);
        if (score <= existing.getTheScore()) {
            return write(scores, path);
        }
        scores.set(existingIndex, new Score(existing.getName(), score));
        return write(scores, path);
    }

    /**
     * Collapses duplicate case-insensitive name entries into a single entry
     * containing the highest score for that name. Preserves the casing of
     * the entry that held the kept (highest) score, and preserves the
     * order of first appearance for kept entries.
     */
    static List<Score> dedupe(List<Score> scores) {
        List<Score> result = new ArrayList<>();
        for (Score current : scores) {
            String key = current.getName().trim().toLowerCase(Locale.ROOT);
            int found = -1;
            for (int i = 0; i < result.size(); i++) {
                if (result.get(i).getName().trim().toLowerCase(Locale.ROOT).equals(key)) {
                    found = i;
                    break;
                }
            }
            if (found < 0) {
                result.add(current);
            } else if (current.getTheScore() > result.get(found).getTheScore()) {
                result.set(found, current);
            }
        }
        return result;
    }

    public static List<Score> readSortedDescending(String path) {
        List<Score> scores = read(path);
        Collections.sort(scores);
        Collections.reverse(scores);
        return scores;
    }

    private static boolean write(List<Score> scores, String path) {
        try {
            File file = new File(path);
            File parent = file.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
            try (FileWriter writer = new FileWriter(file, false)) {
                for (Score s : scores) {
                    writer.write(s.getName() + ": " + s.getTheScore() + "\n");
                }
            }
            return true;
        } catch (IOException ignored) {
            return false;
        }
    }
}
