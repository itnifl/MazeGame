package main.game.maze.common.graphics.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class PropertiesMazeVisualStyleLoaderTest {

    @Test
    void fallsBackToDefaultWhenResourceMissing() {
        MazeVisualStyleConfig cfg = new PropertiesMazeVisualStyleLoader("no-such-style.properties").load();
        assertSame(MazeVisualStyleConfig.DEFAULT, cfg);
    }

    @Test
    void loadsValuesFromFile(@TempDir Path dir) throws IOException {
        Path p = dir.resolve("maze-style.properties");
        Files.writeString(p,
            "easyBackgroundImagePath=/easy.png\n"
          + "normalBackgroundImagePath=/normal.png\n"
          + "hardBackgroundImagePath=/hard.png\n"
          + "menuIconImagePath=/icon.png\n"
          + "goalImagePath=/goal.png\n"
          + "easyWallTypeId=EASY\n"
          + "normalWallTypeId=NORMAL\n"
          + "hardWallTypeId=HARD\n"
          + "menuMusicPath=/menu.mp3\n"
          + "menuSelectSoundPath=/select.mp3\n"
          + "inGameMusicPath=/bg.mp3\n"
          + "winSoundPath=/win.mp3\n");

        MazeVisualStyleConfig cfg = PropertiesMazeVisualStyleLoader.fromFile(p).load();
        assertEquals("/easy.png", cfg.easyBackgroundImagePath());
        assertEquals("/normal.png", cfg.normalBackgroundImagePath());
        assertEquals("/hard.png", cfg.hardBackgroundImagePath());
        assertEquals("/icon.png", cfg.menuIconImagePath());
        assertEquals("/goal.png", cfg.goalImagePath());
        assertEquals("EASY", cfg.easyWallTypeId());
        assertEquals("NORMAL", cfg.normalWallTypeId());
        assertEquals("HARD", cfg.hardWallTypeId());
        assertEquals("/menu.mp3", cfg.menuMusicPath());
        assertEquals("/select.mp3", cfg.menuSelectSoundPath());
        assertEquals("/bg.mp3", cfg.inGameMusicPath());
        assertEquals("/win.mp3", cfg.winSoundPath());
    }

    @Test
    void missingKeysFallBackToDefaults(@TempDir Path dir) throws IOException {
        Path p = dir.resolve("partial-style.properties");
        Files.writeString(p, "menuIconImagePath=/other-icon.png\n");

        MazeVisualStyleConfig cfg = PropertiesMazeVisualStyleLoader.fromFile(p).load();
        MazeVisualStyleConfig d = MazeVisualStyleConfig.DEFAULT;
        assertEquals("/other-icon.png", cfg.menuIconImagePath());
        assertEquals(d.easyBackgroundImagePath(), cfg.easyBackgroundImagePath());
        assertEquals(d.hardWallTypeId(), cfg.hardWallTypeId());
    }

    @Test
    void modelMapsDifficultyToAssets() {
        MazeVisualStyleConfig cfg = MazeVisualStyleConfig.DEFAULT;
        assertEquals(cfg.hardBackgroundImagePath(), cfg.backgroundImageForDifficultyName("HardDifficulty"));
        assertEquals(cfg.normalWallTypeId(), cfg.wallTypeIdForDifficultyName("normal"));
        assertEquals(cfg.easyWallTypeId(), cfg.wallTypeIdForDifficultyName(""));
    }

    @Test
    void modelRejectsBlankFields() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> new MazeVisualStyleConfig(
                " ", "/n", "/h", "/i", "/g", "A", "B", "C", "/m", "/s", "/bg", "/w"));
        assertTrue(ex.getMessage().contains("easyBackgroundImagePath"));
    }

    @Test
    void rejectsNullFilePath() {
        assertThrows(IllegalArgumentException.class,
            () -> PropertiesMazeVisualStyleLoader.fromFile(null));
    }

    @Test
    void missingFileFallsBackToDefault(@TempDir Path dir) {
        Path p = dir.resolve("missing-style.properties");
        MazeVisualStyleConfig cfg = PropertiesMazeVisualStyleLoader.fromFile(p).load();
        assertSame(MazeVisualStyleConfig.DEFAULT, cfg);
    }
}
