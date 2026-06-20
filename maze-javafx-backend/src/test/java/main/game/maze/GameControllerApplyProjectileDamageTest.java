package main.game.maze;

import javafx.application.Platform;
import main.game.maze.mazeworld.BreakableWall;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("GameController — applyProjectileDamageToWall")
class GameControllerApplyProjectileDamageTest {

    @BeforeAll
    static void initFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        assertTrue(latch.await(2, TimeUnit.SECONDS), "JavaFX startup timed out");
    }

    @AfterEach
    void cleanup() {
        App.gameController = null;
    }

    private static void setField(Object target, String name, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(name);
        f.setAccessible(true);
        f.set(target, value);
    }

    @Test
    @DisplayName("returns without throwing when maze is null")
    void nullMazeDoesNotThrow() {
        GameController gc = new GameController();
        Vector2D wall = new Vector2D(0, 0, 100, 0);
        assertDoesNotThrow(() -> gc.applyProjectileDamageToWall(wall, 5));
    }

    @Test
    @DisplayName("returns without throwing when wall is not breakable")
    void indestructibleWallDoesNotThrow() throws Exception {
        GameController gc = new GameController();
        GameMazeWorld maze = new GameMazeWorld();
        setField(gc, "maze", maze);

        Vector2D indestructible = maze.getMazeVectors().stream()
                .filter(v -> maze.findBreakableWall(v) == null)
                .findFirst()
                .orElse(null);
        assumeTrue(indestructible != null, "All walls are breakable — skipping indestructible-wall path");

        assertDoesNotThrow(() -> gc.applyProjectileDamageToWall(indestructible, 5));
    }

    @Test
    @DisplayName("reduces breakable wall HP via Platform.runLater when wall is breakable")
    void breakableWallTakesDamage() throws Exception {
        GameController gc = new GameController();
        GameMazeWorld maze = new GameMazeWorld();
        setField(gc, "maze", maze);

        BreakableWall bw = maze.getBreakableWalls().get(0);
        int hpBefore = bw.getRemainingHp();

        gc.applyProjectileDamageToWall(bw.geometry, 1);

        // Drain the JavaFX event queue so the scheduled runLater executes.
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(latch::countDown);
        assertTrue(latch.await(2, TimeUnit.SECONDS), "Platform.runLater did not execute");

        assertEquals(hpBefore - 1, bw.getRemainingHp(),
                "Damage must be applied to the breakable wall via Platform.runLater");
    }
}
