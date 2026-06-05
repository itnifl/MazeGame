package main.game.maze;

import javafx.application.Platform;
import javafx.concurrent.Task;
import main.game.maze.areas.WinArea;
import main.game.maze.characters.PlayerCharacter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerDisposeTest {

    @BeforeAll
    static void initFx() throws Exception {
        // Start JavaFX toolkit exactly once for headless CI
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            // Toolkit already initialized — fine
            latch.countDown();
        }
        // Small wait to be safe on slower CI
        latch.await(2, TimeUnit.SECONDS);
    }

    // Reflective helpers to access private fields on GameController
    private static void set(Object target, String field, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(field);
        f.setAccessible(true);
        f.set(target, value);
    }

    @Test
    void disposeCancelsTaskInterruptsThreadAndUnsubscribes() throws Exception {
        GameController gc = new GameController();

        // Cancellable Task
        Task<Void> bgTask = new Task<>() {
            @Override protected Void call() throws Exception { Thread.sleep(1000); return null; }
        };

        // Interruptible worker thread (not started; interrupt() should still set the flag)
        Thread worker = new Thread(() -> {
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        });

        // Player + WinArea set up a subscription to verify unsubscribe on dispose()
        PlayerCharacter player = new PlayerCharacter(null, 0, 0, null);
        WinArea winArea = new WinArea(null);
        player.addPositionSubscriber(winArea);
        assertEquals(1, player.getPositionSubscribers().size(), "Precondition: win area subscribed");

        // Inject into controller
        set(gc, "runComputerCharacters", bgTask);
        set(gc, "runComputerCharactersThread", worker);
        set(gc, "playerCharacter", player);
        set(gc, "winarea", winArea);

        // Act
        gc.dispose();

        // Assert
        assertTrue(bgTask.isCancelled(), "Background Task should be cancelled");
        assertTrue(worker.isInterrupted(), "Worker thread should be interrupted");
        assertTrue(player.getPositionSubscribers().isEmpty(), "WinArea should be unsubscribed on dispose()");
    }
}


