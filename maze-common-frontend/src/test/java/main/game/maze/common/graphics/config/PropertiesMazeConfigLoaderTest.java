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

class PropertiesMazeConfigLoaderTest {

    @Test
    void fallsBackToDefaultWhenResourceMissing() {
        MazeRuntimeConfig cfg = new PropertiesMazeConfigLoader("no-such-resource.properties").load();
        assertSame(MazeRuntimeConfig.DEFAULT, cfg);
    }

    @Test
    void loadsValuesFromFile(@TempDir Path dir) throws IOException {
        Path p = dir.resolve("maze.properties");
        Files.writeString(p,
            "windowWidth=320\n"
          + "windowHeight=240\n"
          + "mazeCols=4\n"
          + "mazeRows=3\n"
          + "cellSize=16.0\n"
          + "playerSpeed=42.5\n"
          + "useRealMaze=false\n");
        MazeRuntimeConfig cfg = PropertiesMazeConfigLoader.fromFile(p).load();
        assertEquals(320, cfg.windowWidth());
        assertEquals(240, cfg.windowHeight());
        assertEquals(4, cfg.mazeCols());
        assertEquals(3, cfg.mazeRows());
        assertEquals(16f, cfg.cellSize());
        assertEquals(42.5f, cfg.playerSpeed());
        assertFalse(cfg.useRealMaze());
    }

    @Test
    void missingKeysFallBackToDefaults(@TempDir Path dir) throws IOException {
        Path p = dir.resolve("partial.properties");
        Files.writeString(p, "windowWidth=512\n");
        MazeRuntimeConfig cfg = PropertiesMazeConfigLoader.fromFile(p).load();
        MazeRuntimeConfig d = MazeRuntimeConfig.DEFAULT;
        assertEquals(512, cfg.windowWidth());
        assertEquals(d.windowHeight(), cfg.windowHeight());
        assertEquals(d.mazeCols(),     cfg.mazeCols());
        assertEquals(d.cellSize(),     cfg.cellSize());
        assertEquals(d.useRealMaze(),  cfg.useRealMaze());
    }

    @Test
    void malformedNumberFailsLoudly(@TempDir Path dir) throws IOException {
        Path p = dir.resolve("bad.properties");
        Files.writeString(p, "windowWidth=not-a-number\n");
        IllegalStateException ex = assertThrows(IllegalStateException.class,
            () -> PropertiesMazeConfigLoader.fromFile(p).load());
        assertTrue(ex.getMessage().contains("windowWidth"));
    }

    @Test
    void missingFileFallsBackToDefault(@TempDir Path dir) {
        Path p = dir.resolve("does-not-exist.properties");
        MazeRuntimeConfig cfg = PropertiesMazeConfigLoader.fromFile(p).load();
        assertSame(MazeRuntimeConfig.DEFAULT, cfg);
    }

    @Test
    void rejectsNullFilePath() {
        assertThrows(IllegalArgumentException.class,
            () -> PropertiesMazeConfigLoader.fromFile(null));
    }

    @Test
    void recordValidatesInvariants() {
        assertThrows(IllegalArgumentException.class,
            () -> new MazeRuntimeConfig(0, 100, 1, 1, 1f, 1f, true));
        assertThrows(IllegalArgumentException.class,
            () -> new MazeRuntimeConfig(100, 100, 1, 1, 0f, 1f, true));
        assertThrows(IllegalArgumentException.class,
            () -> new MazeRuntimeConfig(100, 100, 1, 1, 1f, -1f, true));
    }

    @Test
    void rejectsNonFiniteFloatInvariants() {
        assertThrows(IllegalArgumentException.class,
            () -> new MazeRuntimeConfig(100, 100, 1, 1, Float.NaN, 1f, true));
        assertThrows(IllegalArgumentException.class,
            () -> new MazeRuntimeConfig(100, 100, 1, 1, Float.POSITIVE_INFINITY, 1f, true));
        assertThrows(IllegalArgumentException.class,
            () -> new MazeRuntimeConfig(100, 100, 1, 1, 1f, Float.NaN, true));
        assertThrows(IllegalArgumentException.class,
            () -> new MazeRuntimeConfig(100, 100, 1, 1, 1f, Float.NEGATIVE_INFINITY, true));
    }

    @Test
    void malformedBooleanFailsLoudly(@TempDir Path dir) throws IOException {
        Path p = dir.resolve("bad-bool.properties");
        Files.writeString(p, "useRealMaze=yes\n");
        IllegalStateException ex = assertThrows(IllegalStateException.class,
            () -> PropertiesMazeConfigLoader.fromFile(p).load());
        assertTrue(ex.getMessage().contains("useRealMaze"));
    }

    @Test
    void booleanAcceptsTrueFalseCaseInsensitively(@TempDir Path dir) throws IOException {
        Path p = dir.resolve("bool.properties");
        Files.writeString(p, "useRealMaze=FALSE\n");
        assertFalse(PropertiesMazeConfigLoader.fromFile(p).load().useRealMaze());
        Files.writeString(p, "useRealMaze=True\n");
        assertTrue(PropertiesMazeConfigLoader.fromFile(p).load().useRealMaze());
    }
}
