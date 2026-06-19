package main.game.maze.javafx.render;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import main.game.maze.generated.WallRegistry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Regression tests for the wall-canvas rendering path.
 *
 * Bug regression: main.game.maze.walls (eclipse-plugin packaging) was not
 * resolved transitively by VS Code's Java extension, causing WallRegistry
 * static initializer to throw NoClassDefFoundError: WallMaterialBaseType.
 * That error propagated through FxGameSessionBootstrapper.setup(), preventing
 * both wall rendering and enemy spawning. Adding main.game.maze.walls as an
 * explicit dependency in pom.xml fixes the classpath at both compile time and
 * runtime. These tests confirm WallRegistry initialises and that the renderer
 * produces a non-null Canvas under all input conditions.
 */
@DisplayName("FxMazeCanvasRenderer Tests")
class FxMazeCanvasRendererTest {

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

    private static Canvas runOnFxThread(FxMazeCanvasRenderer renderer) throws Exception {
        AtomicReference<Canvas> result = new AtomicReference<>();
        AtomicReference<Throwable> error = new AtomicReference<>();
        CountDownLatch done = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                result.set(renderer.drawCanvas(Collections.emptyList()));
            } catch (Throwable t) {
                error.set(t);
            } finally {
                done.countDown();
            }
        });
        assertTrue(done.await(5, TimeUnit.SECONDS), "drawCanvas must complete on FX thread");
        if (error.get() != null) {
            throw new RuntimeException("drawCanvas threw on FX thread", error.get());
        }
        return result.get();
    }

    // ------------------------------------------------------------------
    // Regression: WallMaterialBaseType must be on classpath (bug fix test)
    // ------------------------------------------------------------------

    @Test
    @DisplayName("WallRegistry static initializer must not throw — walls module is on classpath")
    void wallRegistryInitialisesWithoutError() {
        // If main.game.maze.walls is missing from the classpath, WallRegistry.<clinit>
        // throws ExceptionInInitializerError wrapping NoClassDefFoundError.
        // This test fails at that point, pinning the explicit-dependency requirement.
        assertDoesNotThrow(() -> WallRegistry.getMaterialCount(),
                "WallRegistry must initialise: ensure main.game.maze.walls is an explicit dependency");
    }

    @Test
    @DisplayName("WallRegistry must expose at least one wall material at runtime")
    void wallRegistryHasAtLeastOneMaterial() {
        assertTrue(WallRegistry.getMaterialCount() > 0,
                "WallRegistry must contain registered wall materials");
    }

    @Test
    @DisplayName("WallRegistry.get(DIRT_BASIC) must return a non-null definition")
    void wallRegistryGetReturnsKnownWall() {
        assertNotNull(WallRegistry.get("DIRT_BASIC"),
                "DIRT_BASIC must be resolvable — walls module classpath check");
    }

    // ------------------------------------------------------------------
    // Renderer behaviour
    // ------------------------------------------------------------------

    @Test
    @DisplayName("drawCanvas with empty wall list returns non-null Canvas")
    void drawCanvasEmptyListReturnsCanvas() throws Exception {
        FxMazeCanvasRenderer renderer = new FxMazeCanvasRenderer(
                MazeVisualStyleConfig.DEFAULT, () -> "");
        Canvas canvas = runOnFxThread(renderer);
        assertNotNull(canvas, "drawCanvas must never return null");
    }

    @Test
    @DisplayName("drawCanvas with unknown difficulty name falls back gracefully without throwing")
    void drawCanvasUnknownDifficultyFallsBack() throws Exception {
        FxMazeCanvasRenderer renderer = new FxMazeCanvasRenderer(
                MazeVisualStyleConfig.DEFAULT, () -> "UNKNOWN_DIFFICULTY");
        assertDoesNotThrow(() -> runOnFxThread(renderer),
                "drawCanvas must not throw for an unrecognised difficulty name");
    }

    @Test
    @DisplayName("drawCanvas with null difficulty supplier result falls back gracefully")
    void drawCanvasNullDifficultyNameFallsBack() throws Exception {
        FxMazeCanvasRenderer renderer = new FxMazeCanvasRenderer(
                MazeVisualStyleConfig.DEFAULT, () -> null);
        assertDoesNotThrow(() -> runOnFxThread(renderer),
                "drawCanvas must not throw when difficultyNameSupplier returns null");
    }
}
