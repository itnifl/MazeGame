package main.game.maze;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.util.Duration;
import main.game.maze.common.movement.GameplayTickRate;
import main.game.maze.common.movement.MovementTickAccumulator;

/**
 * Owns the movement Task/thread, the AnimationTimer, the watchdog Timeline,
 * and their start/stop/dispose lifecycle for JavaFX gameplay.
 * By extracting these from the GameController, the threading invariants are
 * isolated and easier to test without FXML dependencies.
 */
public final class FxMovementLoopCoordinator {

    private static final Logger LOGGER = Logger.getLogger(FxMovementLoopCoordinator.class.getName());
    private static final long MOVEMENT_STALL_THRESHOLD_NANOS = 6_000_000_000L; // 6 seconds
    private static final long OPPONENT_THREAD_JOIN_TIMEOUT_MS = 200L;
    /**
     * Interval between enemy AI ticks. Sourced from the shared
     * {@link GameplayTickRate} (≈ 33 ms / 30 Hz) so JavaFX enemies cover the same
     * distance per real-time second as libGDX. A previous value of 100 ms (10 Hz)
     * made enemies move at one third of the intended speed, most visible on hard
     * difficulty where the scaled speed is highest.
     */
    public static final long ENEMY_MOVEMENT_INTERVAL_MS = GameplayTickRate.intervalMillis();

    private Thread runComputerCharactersThread;
    private Task<Boolean> runComputerCharacters;
    private AnimationTimer movementTimer;
    private Timeline movementWatchdogTimer;
    private final AtomicLong lastMovementLoopNanos = new AtomicLong(0L);

    private final Callbacks callbacks;

    public interface Callbacks {
        /** Called on a background thread to step all computer characters. */
        void onComputerCharacterStep();
        /** Called on the JavaFX Application Thread once per frame to step the player. */
        void onPlayerStep(long now);
    }

    public FxMovementLoopCoordinator(Callbacks callbacks) {
        if (callbacks == null) throw new IllegalArgumentException("callbacks must not be null");
        this.callbacks = callbacks;
    }

    public void startMovementTimer() {
        if (movementTimer == null) {
            movementTimer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    callbacks.onPlayerStep(now);
                }
            };
        }
        movementTimer.start();
    }

    public void stopMovementTimer() {
        if (movementTimer != null) {
            movementTimer.stop();
        }
    }

    public void startComputerCharacters() {
        if (runComputerCharactersThread != null && runComputerCharactersThread.isAlive()) {
            LOGGER.info("Computer characters thread is already running.");
            return;
        }
        if (runComputerCharacters != null) {
            runComputerCharacters.cancel();
        }

        runComputerCharacters = new Task<>() {
            @Override
            protected Boolean call() {
                // Time-based catch-up: each iteration runs as many movement ticks
                // as the real elapsed wall-clock time represents, so the effective
                // rate stays at GameplayTickRate.ENEMY_MOVEMENT_TICKS_PER_SECOND even
                // when the per-iteration AI work (heavier on hard difficulty) plus
                // sleep jitter pushes a single iteration past the nominal interval.
                // This mirrors the libGDX per-enemy accumulator so both frontends
                // move difficulty-scaled enemies the same distance per real second.
                MovementTickAccumulator accumulator = new MovementTickAccumulator(
                        GameplayTickRate.ENEMY_MOVEMENT_TICKS_PER_SECOND,
                        GameplayTickRate.MAX_TICKS_PER_FRAME);
                long previousNanos = System.nanoTime();
                while (!isCancelled()) {
                    try {
                        Thread.sleep(ENEMY_MOVEMENT_INTERVAL_MS);
                        long now = System.nanoTime();
                        double elapsedSeconds = (now - previousNanos) / 1_000_000_000d;
                        previousNanos = now;
                        lastMovementLoopNanos.set(now);
                        int ticks = accumulator.accumulate(elapsedSeconds);
                        for (int i = 0; i < ticks && !isCancelled(); i++) {
                            callbacks.onComputerCharacterStep();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        if (isCancelled()) {
                            break;
                        }
                    } catch (Exception e) {
                        // Log and continue — one bad AI tick must not kill the thread.
                        LOGGER.log(Level.WARNING, "Unexpected error in computer-character step; continuing", e);
                    }
                }
                return true;
            }
        };

        runComputerCharactersThread = new Thread(runComputerCharacters, "RunComputerCharactersThread");
        runComputerCharactersThread.setDaemon(true);
        runComputerCharactersThread.start();
        ensureWatchdogTimer();
    }

    public void stopComputerCharacters() {
        if (movementWatchdogTimer != null) {
            movementWatchdogTimer.stop();
        }
        if (runComputerCharacters != null) {
            runComputerCharacters.cancel();
        }
        if (runComputerCharactersThread != null) {
            runComputerCharactersThread.interrupt();
            try {
                runComputerCharactersThread.join(OPPONENT_THREAD_JOIN_TIMEOUT_MS);
            } catch (InterruptedException e) {
                LOGGER.warning("Interrupted while joining computer characters thread");
                Thread.currentThread().interrupt();
            }
            if (runComputerCharactersThread.isAlive()) {
                LOGGER.warning("Computer characters thread did not stop within timeout — it may still be running");
            } else {
                runComputerCharactersThread = null;
            }
        }
    }

    private void ensureWatchdogTimer() {
        if (movementWatchdogTimer != null) {
            movementWatchdogTimer.playFromStart();
            return;
        }

        movementWatchdogTimer = new Timeline(new KeyFrame(Duration.seconds(1), evt -> {
            if (runComputerCharactersThread == null || !runComputerCharactersThread.isAlive()) {
                return;
            }

            long elapsed = System.nanoTime() - lastMovementLoopNanos.get();
            if (elapsed > MOVEMENT_STALL_THRESHOLD_NANOS) {
                LOGGER.severe("Movement loop stalled! Forcing restart of opponent thread.");
                stopComputerCharacters();
                startComputerCharacters();
            }
        }));
        movementWatchdogTimer.setCycleCount(Timeline.INDEFINITE);
        movementWatchdogTimer.playFromStart();
    }

    public void dispose() {
        stopMovementTimer();
        stopComputerCharacters();
    }
}
