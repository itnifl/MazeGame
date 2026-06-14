package main.game.maze;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.util.Duration;

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
                while (!isCancelled()) {
                    try {
                        Thread.sleep(100);
                        lastMovementLoopNanos.set(System.nanoTime());
                        callbacks.onComputerCharacterStep();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        if (isCancelled()) {
                            break;
                        }
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
            runComputerCharactersThread = null;
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
