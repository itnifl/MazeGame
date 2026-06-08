package main.game.maze;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WriteScoresTest {
    private static final String testScores = "testScores.txt";

    private static void createFile() {
        File file = new File(testScores);

        if (file.exists()) {
            file.delete();
        }

        try {
            boolean created = file.createNewFile();

            if (created) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    @Test
    public void testWriteScore() {
        createFile();

        ActionScreenController controller = new ActionScreenController();
        String playerName = "Tom";
        int score = 100;
        String expected = "Tom: 100\n";

        controller.writeScore(playerName, score, testScores);

        try {
            String content = Files.readString(Paths.get(testScores));
            Assertions.assertEquals(expected, content);
        } catch (IOException e) {
            Assertions.fail("Could not read file: " + e.getMessage());
        }
    }

    @Test
    public void testWriteMultipleScores() {
        createFile();

        ActionScreenController controller = new ActionScreenController();
        String playerName1 = "John";
        int score1 = 100;
        String playerName2 = "Jane";
        int score2 = 200;
        String expected = "John: 100\nJane: 200\n";

        controller.writeScore(playerName1, score1, testScores);
        controller.writeScore(playerName2, score2, testScores);

        try {
            String content = Files.readString(Paths.get(testScores));
            Assertions.assertEquals(expected, content);
        } catch (IOException e) {
            Assertions.fail("Could not read file: " + e.getMessage());
        }
    }

}
