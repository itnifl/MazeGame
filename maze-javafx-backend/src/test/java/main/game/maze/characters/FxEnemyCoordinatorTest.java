package main.game.maze.characters;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import main.game.maze.FxEnemyCoordinator;
import main.game.maze.FxGameWorldModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Headless tests for {@link FxEnemyCoordinator} public lifecycle methods.
 * All tests use null-returning suppliers so board/maze operations no-op safely,
 * avoiding a real scene graph.
 */
class FxEnemyCoordinatorTest {

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

    private static void runOnFx(Runnable r) throws Exception {
        CountDownLatch done = new CountDownLatch(1);
        final Throwable[] err = {null};
        Platform.runLater(() -> {
            try { r.run(); } catch (Throwable t) { err[0] = t; } finally { done.countDown(); }
        });
        assertTrue(done.await(5, TimeUnit.SECONDS), "FX task timed out");
        if (err[0] != null) {
            if (err[0] instanceof RuntimeException re) throw re;
            throw new RuntimeException(err[0]);
        }
    }

    private FxEnemyCoordinator coordinator() {
        return new FxEnemyCoordinator(
                () -> null,      // gameBoardSupplier — null board means registration no-ops
                () -> null,      // rootSupplier
                new FxGameWorldModel(),
                () -> null,      // mazeSupplier — null maze means drawEnemyNavigationPaths early-returns
                () -> null,      // playerSupplier
                () -> {}         // pathCanvasRefreshCallback
        );
    }

    // stepAll() with no registered enemies must not throw.
    @Test
    void stepAll_withNoEnemies_doesNotThrow() {
        assertDoesNotThrow(() -> coordinator().stepAll());
    }

    // reset() delegates to movement services and must not throw.
    @Test
    void reset_doesNotThrow() {
        assertDoesNotThrow(() -> coordinator().reset());
    }

    // showEnemyDebugLabels() exits early when the board supplier returns null — no NPE.
    @Test
    void showEnemyDebugLabels_withNullBoard_doesNotThrow() {
        assertDoesNotThrow(() -> coordinator().showEnemyDebugLabels(true));
        assertDoesNotThrow(() -> coordinator().showEnemyDebugLabels(false));
    }

    // drawEnemyNavigationPaths() early-returns when the maze supplier returns null.
    @Test
    void drawEnemyNavigationPaths_withNullMaze_doesNotThrow() throws Exception {
        FxEnemyCoordinator c = coordinator();
        runOnFx(() -> {
            Canvas canvas = new Canvas(200, 200);
            assertDoesNotThrow(() -> c.drawEnemyNavigationPaths(canvas.getGraphicsContext2D()));
        });
    }

    // dispose() stops timers and resets model flags without exception.
    @Test
    void dispose_doesNotThrow() throws Exception {
        FxEnemyCoordinator c = coordinator();
        runOnFx(() -> assertDoesNotThrow(c::dispose));
    }

    // Calling dispose() twice must not throw (idempotent guard).
    @Test
    void dispose_calledTwice_doesNotThrow() throws Exception {
        FxEnemyCoordinator c = coordinator();
        runOnFx(() -> {
            assertDoesNotThrow(c::dispose);
            assertDoesNotThrow(c::dispose);
        });
    }
}
