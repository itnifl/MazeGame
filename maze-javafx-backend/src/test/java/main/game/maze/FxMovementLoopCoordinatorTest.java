package main.game.maze;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Headless unit tests for {@link FxMovementLoopCoordinator} lifecycle.
 * Exercises start, stop, idempotency, and dispose semantics without
 * FXML or scene-graph involvement.
 */
class FxMovementLoopCoordinatorTest {

    @BeforeAll
    static void initFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        latch.await(2, TimeUnit.SECONDS);
    }

    private static FxMovementLoopCoordinator coordinatorWith(
            Runnable onStep, java.util.function.LongConsumer onPlayerStep) {
        return new FxMovementLoopCoordinator(new FxMovementLoopCoordinator.Callbacks() {
            @Override public void onComputerCharacterStep() { onStep.run(); }
            @Override public void onPlayerStep(long now)    { onPlayerStep.accept(now); }
        });
    }

    @Test
    void computerCharacterCallbackIsInvokedAfterStart() throws InterruptedException {
        CountDownLatch called = new CountDownLatch(1);
        FxMovementLoopCoordinator coordinator = coordinatorWith(called::countDown, now -> {});

        coordinator.startComputerCharacters();
        try {
            assertTrue(called.await(3, TimeUnit.SECONDS),
                    "onComputerCharacterStep must be invoked within 3 seconds of start");
        } finally {
            coordinator.dispose();
        }
    }

    @Test
    void stopComputerCharactersHaltsCallbacks() throws InterruptedException {
        AtomicInteger stepCount = new AtomicInteger(0);
        FxMovementLoopCoordinator coordinator = coordinatorWith(stepCount::incrementAndGet, now -> {});

        coordinator.startComputerCharacters();
        Thread.sleep(250);
        coordinator.stopComputerCharacters();

        int countAfterStop = stepCount.get();
        Thread.sleep(300);

        int countAfterWait = stepCount.get();
        // Allow at most one in-flight call that was already in progress at stop time.
        assertTrue(countAfterWait - countAfterStop <= 1,
                "No more than one additional step should occur after stopComputerCharacters()");
    }

    @Test
    void startComputerCharactersIsIdempotent() throws InterruptedException {
        CountDownLatch called = new CountDownLatch(1);
        FxMovementLoopCoordinator coordinator = coordinatorWith(called::countDown, now -> {});

        coordinator.startComputerCharacters();
        assertDoesNotThrow(coordinator::startComputerCharacters, "Second startComputerCharacters must not throw");

        try {
            assertTrue(called.await(3, TimeUnit.SECONDS), "Callback must still fire after idempotent second start");
        } finally {
            coordinator.dispose();
        }
    }

    @Test
    void stopBeforeStartDoesNotThrow() {
        FxMovementLoopCoordinator coordinator = coordinatorWith(() -> {}, now -> {});
        assertDoesNotThrow(coordinator::stopComputerCharacters,
                "stopComputerCharacters before start must not throw");
        assertDoesNotThrow(coordinator::stopMovementTimer,
                "stopMovementTimer before start must not throw");
    }

    @Test
    void disposeIsCallableWithoutPriorStart() {
        FxMovementLoopCoordinator coordinator = coordinatorWith(() -> {}, now -> {});
        assertDoesNotThrow(coordinator::dispose, "dispose before any start must not throw");
    }
}
