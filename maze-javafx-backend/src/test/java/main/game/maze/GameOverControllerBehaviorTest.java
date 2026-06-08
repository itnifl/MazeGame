package main.game.maze;

import javafx.application.Platform;
import javafx.scene.control.Label;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class GameOverControllerBehaviorTest {

    @BeforeAll
    static void initFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        latch.await(2, TimeUnit.SECONDS);
    }

    private GameOverController newControllerWithLabels() {
        GameOverController c = new GameOverController();
        try {
            var deathField = GameOverController.class.getDeclaredField("deathPenaltyNoticeLabel");
            deathField.setAccessible(true);
            Label death = new Label();
            death.setVisible(true); // default can be true; we’ll set false in tests explicitly
            deathField.set(c, death);

            var scoreField = ActionScreenController.class.getDeclaredField("scoreLabel");
            scoreField.setAccessible(true);
            scoreField.set(c, new Label());

            var dmgField = ActionScreenController.class.getDeclaredField("damagePenaltyNoticeLabel");
            dmgField.setAccessible(true);
            Label dmg = new Label();
            dmg.setVisible(true); // default can be true; we’ll set false in tests explicitly
            dmgField.set(c, dmg);
        } catch (ReflectiveOperationException e) {
            fail("Failed to wire labels via reflection: " + e.getMessage());
        }
        return c;
    }

    // 1) showDeathPenaltyLabel() makes death label visible from a hidden state
    @Test
    @DisplayName("GameOverController.showDeathPenaltyLabel reveals the death label")
    void deathPenaltyLabelBecomesVisible() throws Exception {
        GameOverController c = newControllerWithLabels();

        var deathField = GameOverController.class.getDeclaredField("deathPenaltyNoticeLabel");
        deathField.setAccessible(true);
        Label death = (Label) deathField.get(c);

        // Ensure known precondition
        death.setVisible(false);
        assertFalse(death.isVisible(), "Precondition: we set the label invisible");

        c.showDeathPenaltyLabel();

        assertTrue(death.isVisible(), "Death penalty label should become visible");
    }

    // 2) inherited showDamagePenaltyLabel() reveals damage label from a hidden state
    @Test
    @DisplayName("ActionScreenController.showDamagePenaltyLabel reveals the damage label")
    void damagePenaltyLabelBecomesVisible() throws Exception {
        GameOverController c = newControllerWithLabels();

        var dmgField = ActionScreenController.class.getDeclaredField("damagePenaltyNoticeLabel");
        dmgField.setAccessible(true);
        Label damage = (Label) dmgField.get(c);

        // Ensure known precondition
        damage.setVisible(false);
        assertFalse(damage.isVisible(), "Precondition: we set the label invisible");

        c.showDamagePenaltyLabel();

        assertTrue(damage.isVisible(), "Damage penalty label should become visible");
    }

    // 3) setScoreLabel(int) updates label text
    @Test
    @DisplayName("ActionScreenController.setScoreLabel updates score text")
    void setScoreLabelUpdatesText() throws Exception {
        GameOverController c = newControllerWithLabels();

        var scoreField = ActionScreenController.class.getDeclaredField("scoreLabel");
        scoreField.setAccessible(true);
        Label score = (Label) scoreField.get(c);

        c.setScoreLabel(1234);
        assertEquals("1234", score.getText(), "Score label should reflect the given integer");
    }

    // 4) writeScore(...) creates/appends correctly
    @Test
    @DisplayName("ActionScreenController.writeScore appends lines to file")
    void writeScoreCreatesAndAppends() throws Exception {
        GameOverController c = newControllerWithLabels();

        File temp = File.createTempFile("scores-", ".txt");
        temp.deleteOnExit();

        c.writeScore("Alice", 100, temp.getAbsolutePath());
        c.writeScore("Bob", 250, temp.getAbsolutePath());

        try (BufferedReader br = new BufferedReader(new FileReader(temp))) {
            List<String> lines = br.lines().toList();
            assertEquals(2, lines.size(), "Expected exactly two lines");
            assertEquals("Alice: 100", lines.get(0).trim());
            assertEquals("Bob: 250", lines.get(1).trim());
        }
    }

    // 5) writeScore(...) handles I/O errors gracefully (no throw)
    @Test
    @DisplayName("ActionScreenController.writeScore gracefully handles IO exceptions")
    void writeScoreHandlesIoErrorsGracefully() throws Exception {
        GameOverController c = newControllerWithLabels();

        File tempDir = Files.createTempDirectory("scores-dir").toFile();
        tempDir.deleteOnExit();

        assertDoesNotThrow(() ->
                c.writeScore("Charlie", 9001, tempDir.getAbsolutePath()),
                "writeScore should catch IOException and not propagate");
    }
}
