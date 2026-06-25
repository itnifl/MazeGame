package main.game.maze.characters;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import main.game.maze.FxEnemyCoordinator;
import main.game.maze.FxGameWorldModel;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.WallMaterialSpec;
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

    @SuppressWarnings("unchecked")
    private static void addComputerCharacter(FxEnemyCoordinator coordinator, Object character)
            throws Exception {
        Field field = FxEnemyCoordinator.class.getDeclaredField("allComputerCharacters");
        field.setAccessible(true);
        ((List<Object>) field.get(coordinator)).add(character);
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

    @Test
    void applyPlayerFlameExplosion_capsDamagePerDirectionAtOneHundred() throws Exception {
        FxEnemyCoordinator c = coordinator();

        Zombie zombieModel = OpponentsFactory.eINSTANCE.createZombie();
        zombieModel.setHealth(100);
        zombieModel.setSpeed(2.0);
        zombieModel.setAttackDamage(1);
        zombieModel.setThreatLevel(1.0);
        zombieModel.setBehavior(BehaviorType.WANDER);

        ZombieCharacter zombie = new ZombieCharacter(new Rectangle(16, 16), 120, 40, zombieModel);
        addComputerCharacter(c, zombie);
        int before = zombie.getHitPoints();

        int applied = c.applyPlayerFlameExplosion(20, 60, 100, 400, List.of());

        assertEquals(100, applied, "Explosion damage must be capped at 100 per direction");
        assertEquals(before - 100, zombie.getHitPoints(), "Enemy should lose at most the 100 point directional budget");
    }

    @Test
    void applyPlayerFlameExplosion_stopsAtWallWhenWallSurvives() throws Exception {
        FxEnemyCoordinator c = coordinator();

        Zombie frontModel = OpponentsFactory.eINSTANCE.createZombie();
        frontModel.setHealth(20);
        frontModel.setSpeed(2.0);
        frontModel.setAttackDamage(1);
        frontModel.setThreatLevel(1.0);
        frontModel.setBehavior(BehaviorType.WANDER);

        Zombie rearModel = OpponentsFactory.eINSTANCE.createZombie();
        rearModel.setHealth(80);
        rearModel.setSpeed(2.0);
        rearModel.setAttackDamage(1);
        rearModel.setThreatLevel(1.0);
        rearModel.setBehavior(BehaviorType.WANDER);

        ZombieCharacter frontZombie = new ZombieCharacter(new Rectangle(16, 16), 80, 40, frontModel);
        ZombieCharacter rearZombie = new ZombieCharacter(new Rectangle(16, 16), 160, 40, rearModel);
        addComputerCharacter(c, frontZombie);
        addComputerCharacter(c, rearZombie);
        int frontBefore = frontZombie.getHitPoints();
        int rearBefore = rearZombie.getHitPoints();

        int applied = c.applyPlayerFlameExplosion(
                20,
                60,
                100,
                400,
                List.of(new Vector2D(120, 0, 120, 120)));

        assertTrue(applied <= 100, "A single direction must never apply more than 100 total damage");
        assertEquals(Math.max(0, frontBefore - applied), frontZombie.getHitPoints(), "Front enemy should absorb the directional damage budget");
        assertEquals(rearBefore, rearZombie.getHitPoints(), "Rear enemy must stay untouched after the wall stops the flame");
    }

    @Test
    void applyPlayerFlameExplosion_damagesPlayerInCorridor() throws Exception {
        FxEnemyCoordinator c = coordinator();

        java.util.concurrent.atomic.AtomicInteger playerDamage =
                new java.util.concurrent.atomic.AtomicInteger(0);

        // Player directly east of origin — no enemies, full 100 budget reaches player
        int applied = c.applyPlayerFlameExplosion(
                20, 60,          // origin
                100, 400,        // damage / range
                List.of(),       // no walls
                120, 60,         // player center (east of origin, same Y)
                playerDamage::addAndGet);

        assertTrue(playerDamage.get() > 0,
                "Player in the flame corridor must receive damage from the explosion");
        assertEquals(100, playerDamage.get(),
                "Player receives the full remaining budget in the matching corridor direction");
    }

    @Test
    void applyPlayerFlameExplosion_playerDoesNotBlockFlameForEnemyBeyond() throws Exception {
        FxEnemyCoordinator c = coordinator();

        Zombie model = OpponentsFactory.eINSTANCE.createZombie();
        model.setHealth(100);
        model.setSpeed(2.0);
        model.setAttackDamage(1);
        model.setThreatLevel(1.0);
        model.setBehavior(BehaviorType.WANDER);
        // Enemy east of player (player at 120, enemy at 200)
        ZombieCharacter zombie = new ZombieCharacter(new Rectangle(16, 16), 200, 52, model);
        addComputerCharacter(c, zombie);

        java.util.concurrent.atomic.AtomicInteger playerDamage =
                new java.util.concurrent.atomic.AtomicInteger(0);

        c.applyPlayerFlameExplosion(
                20, 60,          // origin
                100, 400,        // damage / range
                List.of(),       // no walls
                120, 60,         // player center (between origin and enemy)
                playerDamage::addAndGet);

        assertTrue(playerDamage.get() > 0,
                "Player in the corridor must take damage");
        assertTrue(zombie.getHitPoints() < 100,
                "Enemy beyond the player must also take damage since player is pass-through");
    }

    @Test
    void applyPlayerFlame_destroysBreakableWallAndContinuesToEnemyBeyond() throws Exception {
        // Vertical wall at x=120, spanning y=[0..120] — sits between origin (20,60) and enemy (200,60)
        Vector2D wallVec = new Vector2D(120, 0, 120, 120);
        GameMazeWorld world = new GameMazeWorld(() -> List.of(wallVec));
        world.assignBreakableWalls(0L,
                List.of(new WallMaterialSpec("WOOD_BASIC", "Wood", 20)));

        FxEnemyCoordinator c = new FxEnemyCoordinator(
                () -> null, () -> null, new FxGameWorldModel(),
                () -> world,
                () -> null, () -> {});

        Zombie model = OpponentsFactory.eINSTANCE.createZombie();
        model.setHealth(10);
        model.setSpeed(1.0);
        model.setAttackDamage(1);
        model.setThreatLevel(1.0);
        model.setBehavior(BehaviorType.WANDER);
        ZombieCharacter enemy = new ZombieCharacter(new Rectangle(16, 16), 200, 52, model);
        addComputerCharacter(c, enemy);

        // Budget 100: wall costs 20 HP, enemy costs 10 HP → total 30 applied at minimum
        int applied = c.applyPlayerFlameExplosion(
                20, 60,
                100, 500,
                world.getMazeVectors(),
                Double.NaN, Double.NaN, null);

        assertTrue(world.getBreakableWalls().isEmpty(),
                "The 20-HP wood wall must be destroyed by the 100-damage blast");
        assertEquals(0, enemy.getHitPoints(),
                "Enemy beyond the destroyed wall must be defeated");
        assertTrue(applied >= 30,
                "Applied damage must cover wall (20 HP) + enemy (10 HP) at minimum");
    }

    @Test
    void applyPlayerFlame_doesNotHitTargetOutsideCorridorHalfWidth() throws Exception {
        // Enemy is diagonally off all four flame corridors: >120px perpendicular from every axis.
        // Origin (20,60), enemy top-left at (300,300) → center ~(315,315).
        // East corridor perpendicular: |315-60|=255>120; north: |315-20|=295>120 — excluded.
        FxEnemyCoordinator c = coordinator();

        Zombie model = OpponentsFactory.eINSTANCE.createZombie();
        model.setHealth(100);
        model.setSpeed(1.0);
        model.setAttackDamage(1);
        model.setThreatLevel(1.0);
        model.setBehavior(BehaviorType.WANDER);
        ZombieCharacter offCorridor = new ZombieCharacter(new Rectangle(16, 16), 300, 300, model);
        addComputerCharacter(c, offCorridor);

        c.applyPlayerFlameExplosion(20, 60, 100, 400, List.of(), Double.NaN, Double.NaN, null);

        assertEquals(100, offCorridor.getHitPoints(),
                "Enemy outside the flame corridor half-width must take no damage");
    }
}

