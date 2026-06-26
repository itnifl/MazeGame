package main.game.maze.characters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import main.game.maze.FxMovementLoopCoordinator;
import main.game.maze.common.movement.ChasePlayerMovementService;
import main.game.maze.common.movement.EnemyState;
import main.game.maze.common.movement.GameplayTickRate;
import main.game.maze.common.movement.MovementResult;
import main.game.maze.common.movement.WorldView;
import main.game.maze.opponents.util.EnemySpawnPlanner;

/**
 * Regression test for the "JavaFX enemies are ~1/3 the speed of libGDX on hard
 * difficulty" bug.
 *
 * <p>Root cause: enemy movement covers exactly {@code speed} world units per AI
 * tick (see {@link ChasePlayerMovementService}); the {@code deltaSeconds}
 * argument never affects the per-tick distance. The effective speed is therefore
 * {@code speed × ticksPerSecond}. libGDX advances enemies at
 * {@link GameplayTickRate#ENEMY_MOVEMENT_TICKS_PER_SECOND} (30&nbsp;Hz), but the
 * JavaFX background loop previously slept 100&nbsp;ms (10&nbsp;Hz), so JavaFX
 * enemies travelled only one third of the distance per real-time second. The gap
 * is widest — and most noticeable — on hard difficulty where the difficulty
 * multiplier makes {@code speed} largest.</p>
 *
 * <p>The test drives the shared {@link ChasePlayerMovementService} (the exact
 * code both frontends use) through one real-time second at each cadence and
 * asserts JavaFX now matches the libGDX reference, while the old 10&nbsp;Hz
 * cadence reproduced the one-third slowdown.</p>
 */
class EnemyMovementCadenceParityTest {

    /** Hard-difficulty monster speed multiplier (maze-common-backend difficulties.xmi). */
    private static final double HARD_SPEED_MULTIPLIER = 1.15d;
    /** Representative zombie base speed. */
    private static final double ZOMBIE_BASE_SPEED = 3.0d;
    /** Old buggy JavaFX cadence: Thread.sleep(100) → 10 ticks per second. */
    private static final int LEGACY_BUGGY_TICKS_PER_SECOND = 10;

    /** Open arena with the player far to the right and no walls, so a chase enemy steps +x by exactly {@code speed} each tick. */
    private static final class OpenArena implements WorldView {
        @Override public double playerX() { return 5000d; }
        @Override public double playerY() { return 10d; }
        @Override public boolean wouldCollide(double centerX, double centerY, double size) { return false; }
        @Override public double minX() { return -10_000d; }
        @Override public double minY() { return -10_000d; }
        @Override public double maxX() { return 10_000d; }
        @Override public double maxY() { return 10_000d; }
    }

    /** Distance an enemy of {@code speed} travels toward the player over one real-time second at the given cadence. */
    private static double distancePerSecond(int ticksPerSecond, double speed) {
        ChasePlayerMovementService service = new ChasePlayerMovementService();
        WorldView world = new OpenArena();
        double startX = 10d;
        double x = startX;
        double y = 10d;
        int dirX = 1;
        int dirY = 0;
        double deltaSeconds = 1.0d / ticksPerSecond;
        for (int i = 0; i < ticksPerSecond; i++) {
            EnemyState state = new EnemyState("e1", x, y, dirX, dirY, 4d, speed);
            MovementResult result = service.tick(state, world);
            x = result.x();
            y = result.y();
            dirX = result.directionX();
            dirY = result.directionY();
            // deltaSeconds is referenced to mirror the production call shape; it must not change distance.
            assertTrue(deltaSeconds > 0d);
        }
        return x - startX;
    }

    private static int javaFxTicksPerSecond() {
        return (int) Math.round(1000.0d / FxMovementLoopCoordinator.ENEMY_MOVEMENT_INTERVAL_MS);
    }

    @Test
    void javaFxMovementLoopRunsAtTheSharedParityRate_not10Hz() {
        double effectiveRate = 1000.0d / FxMovementLoopCoordinator.ENEMY_MOVEMENT_INTERVAL_MS;
        assertEquals(GameplayTickRate.ENEMY_MOVEMENT_TICKS_PER_SECOND, effectiveRate, 1.0d,
                "JavaFX enemy AI loop must tick at the shared ~30 Hz parity rate, not the legacy 10 Hz (Thread.sleep(100))");
    }

    @Test
    void hardDifficultyEnemy_javaFxMatchesLibGdxReference() {
        double hardSpeed = EnemySpawnPlanner.applySpeedMultiplier(ZOMBIE_BASE_SPEED, HARD_SPEED_MULTIPLIER);
        assertEquals(3.45d, hardSpeed, 1e-9, "Hard-difficulty zombie speed must be 3.0 × 1.15 = 3.45");

        int libGdxTicks = (int) Math.round(GameplayTickRate.ENEMY_MOVEMENT_TICKS_PER_SECOND);
        double libGdxReference = distancePerSecond(libGdxTicks, hardSpeed);
        double javaFxActual = distancePerSecond(javaFxTicksPerSecond(), hardSpeed);

        assertEquals(libGdxReference, javaFxActual, hardSpeed,
                "JavaFX hard-difficulty enemy must cover the same distance per second as libGDX (within one tick)");
    }

    @Test
    void legacy10HzCadence_reproducedTheOneThirdSlowdown() {
        double hardSpeed = EnemySpawnPlanner.applySpeedMultiplier(ZOMBIE_BASE_SPEED, HARD_SPEED_MULTIPLIER);

        int libGdxTicks = (int) Math.round(GameplayTickRate.ENEMY_MOVEMENT_TICKS_PER_SECOND);
        double libGdxReference = distancePerSecond(libGdxTicks, hardSpeed);
        double legacyJavaFx = distancePerSecond(LEGACY_BUGGY_TICKS_PER_SECOND, hardSpeed);

        // The bug: the old 100 ms (10 Hz) loop moved enemies at one third of the libGDX speed.
        assertEquals(libGdxReference / 3.0d, legacyJavaFx, hardSpeed,
                "The legacy 10 Hz JavaFX cadence must reproduce the one-third slowdown that this fix removes");
        assertTrue(legacyJavaFx < libGdxReference,
                "Sanity: the legacy cadence is strictly slower than the libGDX reference");
    }
}
