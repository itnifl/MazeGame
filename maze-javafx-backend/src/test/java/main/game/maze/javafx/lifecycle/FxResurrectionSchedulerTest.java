package main.game.maze.javafx.lifecycle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("FxResurrectionScheduler")
class FxResurrectionSchedulerTest {

    @BeforeAll
    static void initFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    private static void onFx(Runnable action) throws Exception {
        CountDownLatch done = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } finally {
                done.countDown();
            }
        });
        assertTrue(done.await(5, TimeUnit.SECONDS));
    }

    @Test
    @DisplayName("a scheduled resurrection runs after its delay and is then no longer pending")
    void runsAfterDelay() throws Exception {
        FxResurrectionScheduler scheduler = new FxResurrectionScheduler();
        CountDownLatch ran = new CountDownLatch(1);

        onFx(() -> scheduler.schedule(0.05, ran::countDown));

        assertTrue(ran.await(3, TimeUnit.SECONDS));
        onFx(() -> assertEquals(0, scheduler.pendingCount()));
    }

    @Test
    @DisplayName("cancelAll (level teardown) stops pending resurrections from ever running")
    void cancelAllStopsPending() throws Exception {
        FxResurrectionScheduler scheduler = new FxResurrectionScheduler();
        AtomicInteger runs = new AtomicInteger();

        onFx(() -> {
            scheduler.schedule(0.1, runs::incrementAndGet);
            scheduler.schedule(0.1, runs::incrementAndGet);
            assertEquals(2, scheduler.pendingCount());
            scheduler.cancelAll();
            assertEquals(0, scheduler.pendingCount());
        });
        Thread.sleep(400);

        assertEquals(0, runs.get(), "a resurrection from the old level must not run");
        assertFalse(scheduler.pendingCount() > 0);
    }
}
