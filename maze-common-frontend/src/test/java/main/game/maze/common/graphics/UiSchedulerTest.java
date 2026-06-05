package main.game.maze.common.graphics;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class UiSchedulerTest {

    @AfterEach
    void restoreDefault() {
        UiScheduler.reset();
    }

    @Test
    @DisplayName("Default scheduler runs actions inline on caller thread")
    void defaultSchedulerRunsInline() {
        AtomicInteger counter = new AtomicInteger();
        UiScheduler.get().runLater(counter::incrementAndGet);
        UiScheduler.get().runOnUiThread(counter::incrementAndGet);
        assertEquals(2, counter.get());
        assertTrue(UiScheduler.get().isUiThread());
    }

    @Test
    @DisplayName("Explicit SynchronousUiScheduler also runs inline")
    void explicitSyncSchedulerRunsInline() {
        UiScheduler.set(new SynchronousUiScheduler());
        AtomicInteger counter = new AtomicInteger();

        UiScheduler.get().runLater(counter::incrementAndGet);
        UiScheduler.get().runOnUiThread(counter::incrementAndGet);

        assertEquals(2, counter.get());
        assertTrue(UiScheduler.get().isUiThread());
    }

    @Test
    @DisplayName("Null actions are tolerated")
    void nullActionsAreTolerated() {
        UiScheduler.set(new SynchronousUiScheduler());
        assertDoesNotThrow(() -> {
            UiScheduler.get().runLater(null);
            UiScheduler.get().runOnUiThread(null);
        });
    }

    @Test
    @DisplayName("set(null) is rejected")
    void setNullRejected() {
        assertThrows(IllegalArgumentException.class, () -> UiScheduler.set(null));
    }
}


