package main.game.maze.javafx;

import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.AnimationEngine;
import main.game.maze.common.graphics.UiScheduler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JavaFxUiSchedulerTest {

    @AfterEach
    void restoreDefault() {
        UiScheduler.reset();
        AudioEngine.reset();
        AnimationEngine.reset();
    }

    @Test
    @DisplayName("JavaFxUiScheduler does not throw regardless of toolkit state")
    void javaFxSchedulerDoesNotThrow() {
        UiScheduler.set(new JavaFxUiScheduler());
        assertDoesNotThrow(() -> UiScheduler.get().runLater(() -> {}));
        assertDoesNotThrow(() -> UiScheduler.get().runOnUiThread(() -> {}));
        assertDoesNotThrow(() -> UiScheduler.get().isUiThread());
    }

    @Test
    @DisplayName("JavaFxBackend.install swaps in JavaFX-backed singletons")
    void backendInstallSwapsInJavaFxImpls() {
        JavaFxBackend.install();
        assertTrue(UiScheduler.get() instanceof JavaFxUiScheduler);
        assertTrue(AnimationEngine.get() instanceof JavaFxAnimationEngine);
    }
}


