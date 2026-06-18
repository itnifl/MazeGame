package main.game.maze.javafx.lifecycle;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the WallRegistry class-loading error guard in
 * {@link FxGameSessionBootstrapper#drawCanvasOrFallback}.
 *
 * <p>When {@code main.game.maze.walls} is absent from the runtime classpath,
 * {@code WallRegistry} throws {@code ExceptionInInitializerError} (first access)
 * or {@code NoClassDefFoundError} (subsequent accesses).  The guard must catch
 * both, log a warning, and return a blank canvas so that enemy spawning and
 * the rest of session setup continue normally.</p>
 */
@DisplayName("FxGameSessionBootstrapper — canvas guard")
class FxGameSessionBootstrapperCanvasGuardTest {

    @BeforeAll
    static void initFx() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        assertTrue(latch.await(5, TimeUnit.SECONDS), "JavaFX toolkit must start within 5 s");
    }

    @Test
    @DisplayName("returns blank fallback canvas when supplier throws ExceptionInInitializerError")
    void fallbackOnExceptionInInitializerError() throws Exception {
        AtomicReference<Canvas> result = new AtomicReference<>();
        CountDownLatch done = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                result.set(FxGameSessionBootstrapper.drawCanvasOrFallback(
                        () -> { throw new ExceptionInInitializerError(); },
                        800, 600));
            } finally {
                done.countDown();
            }
        });
        assertTrue(done.await(5, TimeUnit.SECONDS), "guard must complete within 5 s");
        assertNotNull(result.get(), "fallback canvas must not be null");
        assertEquals(800.0, result.get().getWidth(),  "fallback canvas width must match requested");
        assertEquals(600.0, result.get().getHeight(), "fallback canvas height must match requested");
    }

    @Test
    @DisplayName("returns blank fallback canvas when supplier throws NoClassDefFoundError")
    void fallbackOnNoClassDefFoundError() throws Exception {
        AtomicReference<Canvas> result = new AtomicReference<>();
        CountDownLatch done = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                result.set(FxGameSessionBootstrapper.drawCanvasOrFallback(
                        () -> { throw new NoClassDefFoundError("simulated missing class"); },
                        640, 480));
            } finally {
                done.countDown();
            }
        });
        assertTrue(done.await(5, TimeUnit.SECONDS), "guard must complete within 5 s");
        assertNotNull(result.get(), "fallback canvas must not be null");
        assertEquals(640.0, result.get().getWidth(),  "fallback canvas width must match requested");
        assertEquals(480.0, result.get().getHeight(), "fallback canvas height must match requested");
    }

    @Test
    @DisplayName("returns supplier canvas unchanged when no error occurs")
    void passesSupplierCanvasThrough() throws Exception {
        AtomicReference<Canvas> result = new AtomicReference<>();
        CountDownLatch done = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                Canvas expected = new Canvas(1024, 768);
                result.set(FxGameSessionBootstrapper.drawCanvasOrFallback(
                        () -> expected,
                        100, 100));
            } finally {
                done.countDown();
            }
        });
        assertTrue(done.await(5, TimeUnit.SECONDS), "guard must complete within 5 s");
        assertNotNull(result.get());
        assertEquals(1024.0, result.get().getWidth(),  "supplier canvas dimensions must be preserved");
        assertEquals(768.0,  result.get().getHeight(), "supplier canvas dimensions must be preserved");
    }
}
