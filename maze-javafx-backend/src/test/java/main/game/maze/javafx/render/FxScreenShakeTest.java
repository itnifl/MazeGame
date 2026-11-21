package main.game.maze.javafx.render;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import main.game.maze.common.graphics.fire.BombImpact;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("FxScreenShake lifecycle and baseline preservation")
class FxScreenShakeTest {

    @BeforeAll
    static void initJavaFx() throws Exception {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(latch::countDown);
            latch.await(2, TimeUnit.SECONDS);
        } catch (IllegalStateException ignored) {
        }
    }

    /** Runs the task on the JavaFX thread (where FxScreenShake lives and its timers pulse) and rethrows its failure here. */
    private static void onFxThread(Runnable task) throws Exception {
        FutureTask<Void> run = new FutureTask<>(task, null);
        Platform.runLater(run);
        try {
            run.get(10, TimeUnit.SECONDS);
        } catch (ExecutionException failure) {
            Throwable cause = failure.getCause();
            if (cause instanceof Error error) {
                throw error;
            }
            if (cause instanceof Exception exception) {
                throw exception;
            }
            throw failure;
        }
    }

    @Test
    @DisplayName("overlapping shakes preserve original baseline translation")
    void overlappingShakesPreserveOriginalBaseline() throws Exception {
        // play, advance and assert in one task: a real pulse cannot interleave with it on the same thread
        onFxThread(this::overlappingShakesSequence);
    }

    private void overlappingShakesSequence() {
        Pane pane = new Pane();
        pane.setTranslateX(15.0);
        pane.setTranslateY(25.0);

        FxScreenShake.play(pane);
        FxScreenShake.ActiveShake shake = FxScreenShake.activeShake(pane);
        assertNotNull(shake, "shake should be active");
        assertEquals(15.0, shake.baseX(), 1e-6);
        assertEquals(25.0, shake.baseY(), 1e-6);

        // Advance shake into the middle of the duration (displaced offset)
        shake.handle(1_000_000_000L); // 1.0s nanos start
        shake.handle(1_100_000_000L); // 0.1s elapsed
        double displacedX = pane.getTranslateX();
        double displacedY = pane.getTranslateY();

        // Trigger another shake while still shifted
        FxScreenShake.play(pane);
        FxScreenShake.ActiveShake sameShake = FxScreenShake.activeShake(pane);
        assertEquals(shake, sameShake, "should reuse existing active shake instance");
        assertEquals(15.0, sameShake.baseX(), 1e-6, "baseX must remain the original 15.0, not displaced " + displacedX);
        assertEquals(25.0, sameShake.baseY(), 1e-6, "baseY must remain the original 25.0, not displaced " + displacedY);

        // Advance to completion
        sameShake.handle(2_000_000_000L); // restart sets startNanos on next handle
        sameShake.handle((long) (2_000_000_000L + (BombImpact.SHAKE_SECONDS + 0.1) * 1e9));

        assertEquals(15.0, pane.getTranslateX(), 1e-6, "final translateX must match original baseline");
        assertEquals(25.0, pane.getTranslateY(), 1e-6, "final translateY must match original baseline");
        assertNull(FxScreenShake.activeShake(pane), "active shake should be removed on completion");
    }
}
