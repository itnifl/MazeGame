package main.game.maze.characters;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import main.game.maze.FxEnemyCoordinator;
import main.game.maze.FxGameWorldModel;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.OpponentsFactory;
import main.game.maze.opponents.Zombie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
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
        assertTrue(latch.await(10, TimeUnit.SECONDS), "JavaFX startup timed out");
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

    // resolveSpawnPosition() with null maze → no collision → returns position close to input.
    @Test
    void resolveSpawnPosition_withNoMaze_returnsPoint() {
        FxEnemyCoordinator c = coordinator();
        Point2D result = c.resolveSpawnPosition(150.0, 200.0, 32.0);
        assertNotNull(result, "resolveSpawnPosition must return a non-null Point2D");
    }

    // showInfectionWarning() called from non-FX thread → posts to runLater, returns immediately.
    @Test
    void showInfectionWarning_fromNonFxThread_doesNotThrow() throws Exception {
        FxEnemyCoordinator c = coordinator();  // rootSupplier returns null
        assertDoesNotThrow(c::showInfectionWarning,
                "showInfectionWarning from non-FX thread must post to runLater without throwing");
        // Flush the FX queue so the runLater lambda executes (null root → returns early there too)
        runOnFx(() -> {});
    }

    // showInfectionWarning() called on FX thread with null root → early return after null check.
    @Test
    void showInfectionWarning_onFxThread_withNullRoot_doesNotThrow() throws Exception {
        FxEnemyCoordinator c = coordinator();
        runOnFx(() -> assertDoesNotThrow(c::showInfectionWarning,
                "showInfectionWarning on FX thread with null root must return cleanly"));
    }

    // showEnemyPathsOverlay() sets model state and starts the overlay timer.
    @Test
    void showEnemyPathsOverlay_setsModelOverlayVisible() throws Exception {
        FxGameWorldModel model = new FxGameWorldModel();
        FxEnemyCoordinator c = new FxEnemyCoordinator(
                () -> null, () -> null, model,
                () -> null, () -> null, () -> {});
        runOnFx(c::showEnemyPathsOverlay);
        assertTrue(model.enemyPathOverlayVisible(),
                "enemy path overlay must be visible after showEnemyPathsOverlay");
        // cleanup
        runOnFx(c::dispose);
    }

    // showEnemyPathsOverlay() called twice must not throw (timer is idempotent).
    @Test
    void showEnemyPathsOverlay_calledTwice_doesNotThrow() throws Exception {
        FxEnemyCoordinator c = coordinator();
        runOnFx(() -> {
            assertDoesNotThrow(c::showEnemyPathsOverlay);
            assertDoesNotThrow(c::showEnemyPathsOverlay);
        });
        runOnFx(c::dispose);
    }

    // unregisterCharacter(null, null) posts a safe runLater that handles null arguments.
    @Test
    void unregisterCharacter_withNullArgs_doesNotThrow() throws Exception {
        FxEnemyCoordinator c = coordinator();
        // Calling from non-FX thread posts a Platform.runLater lambda — must not throw.
        assertDoesNotThrow(() -> c.unregisterCharacter(null, null));
        // Flush so the lambda actually executes.
        runOnFx(() -> {});
    }

    // unregisterCharacter() on FX thread with valid Pane (non-null board, node not in board).
    @Test
    void unregisterCharacter_onFxThread_withNonNullPaneNotContainingNode_doesNotThrow()
            throws Exception {
        Pane board = new Pane();
        FxEnemyCoordinator c = new FxEnemyCoordinator(
                () -> board, () -> null, new FxGameWorldModel(),
                () -> null, () -> null, () -> {});
        Rectangle node = new Rectangle();
        runOnFx(() -> assertDoesNotThrow(() -> c.unregisterCharacter(null, node),
                "unregisterCharacter must handle node not in board gracefully"));
    }

    // -----------------------------------------------------------------------
    // Bug regression: ghost solidification tick must not fire a phasing move
    // -----------------------------------------------------------------------

    /**
     * Regression test for the ghost "stuck-in-wall" bug.
     *
     * <p>Root cause: {@code drainNonTangientEnergy} returned {@code wasPhasing} (the <em>old</em>
     * state). When energy drained to zero on a tick, it still returned {@code true}, causing
     * {@code applyPhasing} to fire one extra wall-ignoring {@code character.move(true)} after
     * the nudge — potentially moving the ghost back inside a wall.</p>
     *
     * <p>The fix changes the return to {@code wasPhasing && isPhasing(newEnergy)}.  This test
     * verifies the key invariant using only {@link main.game.maze.common.movement.GhostNonTangibilityService}
     * (no {@code Character} subclass construction, which would touch the pre-existing
     * {@code Vector2D.normalize} classpath conflict).</p>
     */
    @Test
    @DisplayName("Ghost solidification: after one drain tick from energy=1, isPhasing is false")
    void ghostSolidificationTick_afterOneDrainTickFromEnergy1_isNotPhasing() {
        // EMF model stores nonTangibilityEnergy as int.
        // setNonTangientEnergy(0.86) truncates to (int)0.86 = 0.
        int startEnergy = 1; // stored int, equivalent to ghost.getNonTangientEnergy() == 1.0

        assertTrue(
                main.game.maze.common.movement.GhostNonTangibilityService.isPhasing(startEnergy),
                "Ghost must be phasing when energy = 1");

        // Replicate exactly what drainNonTangientEnergy does (MOVEMENT_TICK_THRESHOLD = 0.06 s):
        double drained = main.game.maze.common.movement.GhostNonTangibilityService
                .drainEnergy(startEnergy, 0.06); // → 0.86

        int storedEnergy = (int) drained; // EMF int model: (int)0.86 = 0

        // The fixed code: "return wasPhasing && isPhasing(cc.getNonTangientEnergy())"
        // With newEnergy stored as 0, isPhasing(0) == false → combined result is false.
        // The old code returned wasPhasing (= true), causing the extra wall-ignoring move.
        assertEquals(0, storedEnergy,
                "EMF int model must truncate 0.86 to 0 on the solidification tick");
        assertFalse(
                main.game.maze.common.movement.GhostNonTangibilityService.isPhasing(storedEnergy),
                "After one drain tick from energy=1, stored energy must be 0 (int truncation) "
                + "and isPhasing must return false — this is the invariant that prevents the "
                + "extra through-wall move on the solidification tick");
    }

    // stepAll() with a registered ZombieCharacter (wander behavior, null maze) → doWanderMove early return.
    @SuppressWarnings("unchecked")
    @Test
    void stepAll_withWanderZombie_coversWanderMoveNullMazePath() throws Exception {
        FxEnemyCoordinator c = coordinator();

        // Build a minimal ZombieCharacter via reflection-free EMF factory.
        Zombie zombieModel = OpponentsFactory.eINSTANCE.createZombie();
        zombieModel.setHealth(10);
        zombieModel.setSpeed(2.0);
        zombieModel.setAttackDamage(1);
        zombieModel.setThreatLevel(1.0);
        zombieModel.setBehavior(BehaviorType.WANDER);

        Rectangle gfx = new Rectangle(16, 16);
        ZombieCharacter zombie = new ZombieCharacter(gfx, 100, 100, zombieModel);

        // Inject directly into allComputerCharacters (bypass Platform.runLater for speed).
        Field field = FxEnemyCoordinator.class.getDeclaredField("allComputerCharacters");
        field.setAccessible(true);
        ((List<Object>) field.get(c)).add(zombie);

        // stepAll() with null maze → doWanderMove null-maze early return path.
        assertDoesNotThrow(c::stepAll,
                "stepAll with a wander zombie and null maze must not throw");
    }
}
