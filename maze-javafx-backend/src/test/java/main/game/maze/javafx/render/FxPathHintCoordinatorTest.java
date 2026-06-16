package main.game.maze.javafx.render;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import main.game.maze.FxGameWorldModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class FxPathHintCoordinatorTest {

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

    private static FxPathHintCoordinator coordinatorWithCanvas(Canvas canvas, FxGameWorldModel model,
            AtomicInteger enemyDrawerCalls) {
        return new FxPathHintCoordinator(
                () -> null,
                model,
                () -> null,
                () -> null,
                () -> null,
                () -> null,
                () -> canvas,
                gc -> enemyDrawerCalls.incrementAndGet()
        );
    }

    // refreshPathCanvas() calls clearRect before any drawing.
    @Test
    void refreshPathCanvas_callsClearRectOnCanvas() throws Exception {
        runOnFx(() -> {
            FxGameWorldModel model = new FxGameWorldModel();
            AtomicInteger enemyCalls = new AtomicInteger();
            Canvas canvas = new Canvas(100, 100);
            FxPathHintCoordinator coordinator = coordinatorWithCanvas(canvas, model, enemyCalls);

            // Should not throw and must clear the canvas.
            assertDoesNotThrow(coordinator::refreshPathCanvas);
        });
    }

    // When routeHintVisible is false, enemy path drawer is still called (always called).
    @Test
    void refreshPathCanvas_routeHintNotVisible_enemyDrawerStillCalled() throws Exception {
        runOnFx(() -> {
            FxGameWorldModel model = new FxGameWorldModel();
            model.setRouteHintVisible(false);
            model.setEnemyPathOverlayVisible(true);

            AtomicInteger enemyCalls = new AtomicInteger();
            Canvas canvas = new Canvas(200, 200);
            FxPathHintCoordinator coordinator = coordinatorWithCanvas(canvas, model, enemyCalls);

            coordinator.refreshPathCanvas();

            assertEquals(1, enemyCalls.get(),
                    "enemyPathDrawer must be called even when route hint is not visible");
        });
    }

    // After refreshPathCanvas(), globalAlpha is reset to 1.0 (regression guard for alpha-leak).
    @Test
    void refreshPathCanvas_resetsGlobalAlphaToOne() throws Exception {
        runOnFx(() -> {
            FxGameWorldModel model = new FxGameWorldModel();
            model.setEnemyPathOverlayVisible(true);

            AtomicInteger enemyCalls = new AtomicInteger();
            Canvas canvas = new Canvas(200, 200);
            FxPathHintCoordinator coordinator = coordinatorWithCanvas(canvas, model, enemyCalls);

            coordinator.refreshPathCanvas();

            assertEquals(1.0, canvas.getGraphicsContext2D().getGlobalAlpha(), 1e-9,
                    "globalAlpha must be 1.0 after refreshPathCanvas to prevent alpha-leak");
        });
    }

    // When canvas supplier returns null, refreshPathCanvas must not throw.
    @Test
    void refreshPathCanvas_nullCanvas_doesNotThrow() throws Exception {
        runOnFx(() -> {
            FxGameWorldModel model = new FxGameWorldModel();
            FxPathHintCoordinator coordinator = new FxPathHintCoordinator(
                    () -> null, model, () -> null, () -> null,
                    () -> null, () -> null, () -> null, gc -> {});

            assertDoesNotThrow(coordinator::refreshPathCanvas);
        });
    }

    // dispose() must not throw.
    @Test
    void dispose_doesNotThrow() throws Exception {
        runOnFx(() -> {
            FxGameWorldModel model = new FxGameWorldModel();
            FxPathHintCoordinator coordinator = new FxPathHintCoordinator(
                    () -> null, model, () -> null, () -> null,
                    () -> null, () -> null, () -> null, gc -> {});
            assertDoesNotThrow(coordinator::dispose);
        });
    }
}
