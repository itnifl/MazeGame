package main.game.maze.libgdx;

import main.game.maze.libgdx.adapter.*;
import main.game.maze.libgdx.controller.*;
import main.game.maze.libgdx.helper.*;
import main.game.maze.libgdx.service.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import org.junit.jupiter.api.Test;

class GdxVisualStyleSupportTest {

    @Test
    void returnsPrimaryLoaderValueWhenAvailable() {
        MazeVisualStyleConfig primary = new MazeVisualStyleConfig(
                "/easy.png",
                "/normal.png",
                "/hard.png",
                "/menu.png",
                "/goal.png",
                "WALL_E",
                "WALL_N",
                "WALL_H",
                "/menu.wav",
                "/select.wav",
                "/game.mp3",
                "/win.mp3");

        MazeVisualStyleConfig loaded = GdxVisualStyleSupport.loadOrDefault(
                () -> primary,
                () -> MazeVisualStyleConfig.DEFAULT,
                () -> {
                });

        assertSame(primary, loaded);
    }

    @Test
    void fallsBackToPropertiesLoaderWhenPrimaryFails() {
        MazeVisualStyleConfig secondary = new MazeVisualStyleConfig(
                "/easy2.png",
                "/normal2.png",
                "/hard2.png",
                "/menu2.png",
                "/goal2.png",
                "WALL2_E",
                "WALL2_N",
                "WALL2_H",
                "/menu2.wav",
                "/select2.wav",
                "/game2.mp3",
                "/win2.mp3");

        MazeVisualStyleConfig loaded = GdxVisualStyleSupport.loadOrDefault(
                () -> {
                    throw new IllegalStateException("primary failed");
                },
                () -> secondary,
                () -> {
                });

        assertSame(secondary, loaded);
    }

    @Test
    void returnsDefaultAndRunsHookWhenAllLoadersFail() {
        AtomicBoolean fallbackHookRan = new AtomicBoolean(false);

        MazeVisualStyleConfig loaded = GdxVisualStyleSupport.loadOrDefault(
                () -> {
                    throw new IllegalStateException("primary failed");
                },
                () -> {
                    throw new IllegalStateException("secondary failed");
                },
                () -> fallbackHookRan.set(true));

        assertSame(MazeVisualStyleConfig.DEFAULT, loaded);
        assertTrue(fallbackHookRan.get());
        assertEquals("/main/game/maze/ghost1.png", loaded.menuIconImagePath());
    }
}
