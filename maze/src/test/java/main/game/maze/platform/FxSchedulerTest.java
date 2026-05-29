package main.game.maze.platform;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class FxSchedulerTest {

    @AfterEach
    void restoreDefault() {
        FxScheduler.reset();
    }

    @Test
    @DisplayName("SynchronousFxScheduler runs actions on caller thread immediately")
    void synchronousSchedulerRunsImmediately() {
        FxScheduler.set(new SynchronousFxScheduler());
        AtomicInteger counter = new AtomicInteger();

        FxScheduler.get().runLater(counter::incrementAndGet);
        FxScheduler.get().runOnFxThread(counter::incrementAndGet);

        assertEquals(2, counter.get());
        assertTrue(FxScheduler.get().isFxApplicationThread());
    }

    @Test
    @DisplayName("Null actions are tolerated")
    void nullActionsAreTolerated() {
        FxScheduler.set(new SynchronousFxScheduler());
        assertDoesNotThrow(() -> {
            FxScheduler.get().runLater(null);
            FxScheduler.get().runOnFxThread(null);
        });
    }

    @Test
    @DisplayName("set(null) is rejected")
    void setNullRejected() {
        assertThrows(IllegalArgumentException.class, () -> FxScheduler.set(null));
    }

    @Test
    @DisplayName("JavaFxScheduler does not throw regardless of toolkit state")
    void javaFxSchedulerDoesNotThrow() {
        FxScheduler.set(new JavaFxScheduler());
        assertDoesNotThrow(() -> FxScheduler.get().runLater(() -> {}));
        assertDoesNotThrow(() -> FxScheduler.get().runOnFxThread(() -> {}));
        assertDoesNotThrow(() -> FxScheduler.get().isFxApplicationThread());
    }
}
