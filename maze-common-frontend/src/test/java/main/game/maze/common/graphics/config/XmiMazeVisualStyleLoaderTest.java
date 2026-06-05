package main.game.maze.common.graphics.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class XmiMazeVisualStyleLoaderTest {

    @Test
    void fallsBackToDefaultWhenResourceMissing() {
        MazeVisualStyleConfig cfg = new XmiMazeVisualStyleLoader("xmi/no-such-style.xmi").load();
        assertSame(MazeVisualStyleConfig.DEFAULT, cfg);
    }

    @Test
    void loadsValuesFromFile(@TempDir Path dir) throws IOException {
        Path p = dir.resolve("maze-style.xmi");
        Files.writeString(p,
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<style:MazeVisualStyle xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:style=\"http://www.example.org/maze/style\" xmi:version=\"2.0\" "
                        + "easyBackgroundImagePath=\"/easy.png\" normalBackgroundImagePath=\"/normal.png\" hardBackgroundImagePath=\"/hard.png\" "
                        + "menuIconImagePath=\"/icon.png\" goalImagePath=\"/goal.png\" easyWallTypeId=\"A\" normalWallTypeId=\"B\" hardWallTypeId=\"C\" "
                        + "menuMusicPath=\"/menu.mp3\" menuSelectSoundPath=\"/select.mp3\" inGameMusicPath=\"/bg.mp3\" winSoundPath=\"/win.mp3\"/>\n");

        MazeVisualStyleConfig cfg = XmiMazeVisualStyleLoader.fromFile(p).load();
        assertEquals("/easy.png", cfg.easyBackgroundImagePath());
        assertEquals("/normal.png", cfg.normalBackgroundImagePath());
        assertEquals("/hard.png", cfg.hardBackgroundImagePath());
        assertEquals("/icon.png", cfg.menuIconImagePath());
        assertEquals("/goal.png", cfg.goalImagePath());
        assertEquals("A", cfg.easyWallTypeId());
        assertEquals("B", cfg.normalWallTypeId());
        assertEquals("C", cfg.hardWallTypeId());
        assertEquals("/menu.mp3", cfg.menuMusicPath());
        assertEquals("/select.mp3", cfg.menuSelectSoundPath());
        assertEquals("/bg.mp3", cfg.inGameMusicPath());
        assertEquals("/win.mp3", cfg.winSoundPath());
    }

    @Test
    void rejectsNullFilePath() {
        assertThrows(IllegalArgumentException.class, () -> XmiMazeVisualStyleLoader.fromFile(null));
    }
}


