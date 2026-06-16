package main.game.maze.javafx.hud;

import javafx.application.Platform;
import javafx.scene.control.Label;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class FxHudCoordinatorTest {

    @BeforeAll
    static void initFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        assertTrue(latch.await(2, TimeUnit.SECONDS), "JavaFX startup timed out");
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

    // setMessage(text) updates the label text.
    @Test
    void setMessage_updatesLabelText() throws Exception {
        AtomicReference<Label> labelRef = new AtomicReference<>();
        runOnFx(() -> {
            Label lbl = new Label();
            labelRef.set(lbl);
            FxHudCoordinator coordinator = new FxHudCoordinator(() -> lbl);
            coordinator.setMessage("hello world");
            assertEquals("hello world", lbl.getText());
        });
    }

    // setMessage("") clears the label.
    @Test
    void setMessage_emptyString_clearsLabel() throws Exception {
        runOnFx(() -> {
            Label lbl = new Label("old text");
            FxHudCoordinator coordinator = new FxHudCoordinator(() -> lbl);
            coordinator.setMessage("");
            assertEquals("", lbl.getText());
        });
    }

    // Null supplier is handled gracefully without NullPointerException.
    @Test
    void setMessage_nullSupplierResult_doesNotThrow() throws Exception {
        runOnFx(() -> {
            FxHudCoordinator coordinator = new FxHudCoordinator(() -> null);
            assertDoesNotThrow(() -> coordinator.setMessage("text"));
        });
    }

    // dispose() stops any pending clear timer without throwing.
    @Test
    void dispose_withNoPendingTimer_doesNotThrow() throws Exception {
        runOnFx(() -> {
            FxHudCoordinator coordinator = new FxHudCoordinator(() -> new Label());
            assertDoesNotThrow(coordinator::dispose);
        });
    }

    // setMessage(text, duration) sets the text immediately.
    @Test
    void setMessage_withDuration_setsTextImmediately() throws Exception {
        runOnFx(() -> {
            Label lbl = new Label();
            FxHudCoordinator coordinator = new FxHudCoordinator(() -> lbl);
            coordinator.setMessage("timed msg", javafx.util.Duration.seconds(10));
            assertEquals("timed msg", lbl.getText(), "Text must be set immediately, not after the timer fires");
            coordinator.dispose();
        });
    }

    // Replacing a timed message before the timer fires must update the label.
    @Test
    void setMessage_replacesTimedMessage_updatesLabel() throws Exception {
        runOnFx(() -> {
            Label lbl = new Label();
            FxHudCoordinator coordinator = new FxHudCoordinator(() -> lbl);
            coordinator.setMessage("first", javafx.util.Duration.seconds(60));
            coordinator.setMessage("second");
            assertEquals("second", lbl.getText(), "Second setMessage must replace the first");
            coordinator.dispose();
        });
    }
}
