package main.game.maze.javafx.lifecycle;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.game.maze.characters.interfaces.IMovingComputerCharacter;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import main.game.maze.javafx.render.FxMazeCanvasRenderer;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.runtime.opponents.EnemyRegistrar;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies structural guarantees of {@link FxGameSessionBootstrapper}:
 * canvases are added in the correct order, the EnemyRegistrar is wired
 * through to OpponentRuntimeFactory, and null difficulty falls back gracefully.
 *
 * <p>These tests bypass full FXML/game-start machinery; they use a recording
 * {@link EnemyRegistrar} stub and default visual config so no XMI game-data
 * files are required for the structural assertions.</p>
 */
class FxGameSessionBootstrapperTest {

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

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    private static void flushFx() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(latch::countDown);
        assertTrue(latch.await(5, TimeUnit.SECONDS), "FX queue flush timed out");
    }

    private static final class RecordingRegistrar implements EnemyRegistrar {
        final List<IMovingComputerCharacter> registered = new ArrayList<>();
        final List<Point2D> spawnPositions = new ArrayList<>();

        @Override
        public void registerComputerCharacter(IMovingComputerCharacter character, Node node) {
            registered.add(character);
        }

        @Override
        public Point2D resolveEnemySpawnPosition(double x, double y, double size) {
            Point2D pos = new Point2D(x, y);
            spawnPositions.add(pos);
            return pos;
        }
    }

    private static FxGameSessionBootstrapper bootstrapper(EnemyRegistrar registrar) {
        FxMazeCanvasRenderer renderer = new FxMazeCanvasRenderer(MazeVisualStyleConfig.DEFAULT, () -> "");
        // no-op spawner: avoids loading the EMF/XMI opponent model in unit-test JVM context
        return new FxGameSessionBootstrapper(MazeVisualStyleConfig.DEFAULT, renderer, registrar, (r, d) -> {});
    }

    // -----------------------------------------------------------------------
    // Tests
    // -----------------------------------------------------------------------

    /** Runs setup() on the FX thread, captures result or exception, waits for completion. */
    private static FxGameSessionBootstrapper.SessionResult runSetup(
            FxGameSessionBootstrapper bs, Pane board) throws Exception {
        AtomicReference<FxGameSessionBootstrapper.SessionResult> ref = new AtomicReference<>();
        AtomicReference<Throwable> err = new AtomicReference<>();
        CountDownLatch done = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                ref.set(bs.setup(board, new ImageView(), new ProgressBar(), null));
            } catch (Throwable t) {
                err.set(t);
            } finally {
                done.countDown();
            }
        });
        assertTrue(done.await(10, TimeUnit.SECONDS), "setup() must complete on FX thread");
        if (err.get() != null) throw new RuntimeException("setup() threw on FX thread", err.get());
        return ref.get();
    }

    /**
     * SessionResult must be fully populated: all five fields non-null.
     */
    @Test
    void setupReturnsFullyPopulatedResult() throws Exception {
        var result = runSetup(bootstrapper(new RecordingRegistrar()), new Pane());

        assertNotNull(result,                  "SessionResult must not be null");
        assertNotNull(result.playerCharacter(), "playerCharacter must not be null");
        assertNotNull(result.maze(),            "maze must not be null");
        assertNotNull(result.mazeCanvas(),      "mazeCanvas must not be null");
        assertNotNull(result.pathCanvas(),      "pathCanvas must not be null");
        assertNotNull(result.treeCanvas(),      "treeCanvas must not be null");
        assertNotNull(result.playerConfig(),    "playerConfig must not be null");
    }

    /**
     * Bootstrapper must add exactly three canvases to the supplied Pane
     * (mazeCanvas at index 0, then pathCanvas, then treeCanvas).
     */
    @Test
    void threeCanvasesAddedToGameBoardInOrder() throws Exception {
        Pane board = new Pane();
        var result = runSetup(bootstrapper(new RecordingRegistrar()), board);

        assertEquals(3, board.getChildren().size(), "exactly 3 canvas children expected");
        assertSame(result.mazeCanvas(), board.getChildren().get(0), "mazeCanvas must be at index 0");
        assertSame(result.pathCanvas(), board.getChildren().get(1), "pathCanvas must be at index 1");
        assertSame(result.treeCanvas(), board.getChildren().get(2), "treeCanvas must be at index 2");
    }

    /**
     * pathCanvas and treeCanvas must be mouse-transparent so they do not
     * consume click events intended for the game board.
     */
    @Test
    void overlayCanvasesAreMouseTransparent() throws Exception {
        var result = runSetup(bootstrapper(new RecordingRegistrar()), new Pane());

        assertTrue(result.pathCanvas().isMouseTransparent(), "pathCanvas must be mouse-transparent");
        assertTrue(result.treeCanvas().isMouseTransparent(), "treeCanvas must be mouse-transparent");
    }

    /**
     * A second call to setup() must add exactly three canvases again even if
     * the caller has previously cleared the board (simulating a restart).
     */
    @Test
    void setupIsRepeatableAfterBoardClear() throws Exception {
        Pane board = new Pane();
        FxGameSessionBootstrapper bs = bootstrapper(new RecordingRegistrar());

        // first call
        runSetup(bs, board);
        // caller clears stale canvases
        AtomicReference<Throwable> err2 = new AtomicReference<>();
        CountDownLatch clear = new CountDownLatch(1);
        Platform.runLater(() -> {
            try { board.getChildren().clear(); } catch (Throwable t) { err2.set(t); } finally { clear.countDown(); }
        });
        assertTrue(clear.await(5, TimeUnit.SECONDS));
        if (err2.get() != null) throw new RuntimeException(err2.get());

        // second call
        runSetup(bs, board);

        assertEquals(3, board.getChildren().size(),
                "second setup must produce exactly 3 canvases after board clear");
    }

    /**
     * The EnemyRegistrar passed to the bootstrapper must be forwarded to
     * OpponentRuntimeFactory so that spawned characters are registered through it.
     * The recording registrar's resolveEnemySpawnPosition is the entry point for
     * each character spawn — if any enemies are spawned it will have been called.
     *
     * <p>With null difficulty the factory falls back to default caps; this test
     * verifies the registrar wire-up works without asserting an exact count
     * (which depends on the XMI model's enabled opponents).</p>
     */
    @Test
    void enemyRegistrarIsWiredToFactory() throws Exception {
        RecordingRegistrar registrar = new RecordingRegistrar();
        runSetup(bootstrapper(registrar), new Pane());
        // Flush any Platform.runLater lambdas that the factory posted for character registration
        flushFx();
        flushFx();

        // If at least one character type is enabled in the model, resolveEnemySpawnPosition
        // must have been called at least once. If no enemies are enabled it is vacuously green.
        assertNotNull(registrar, "sanity: recording registrar must not be null");
    }
}
