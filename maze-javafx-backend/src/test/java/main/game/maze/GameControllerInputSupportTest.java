package main.game.maze;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EnumSet;
import java.util.Set;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

class GameControllerInputSupportTest {

    @Test
    void movementKeysAreTrackedForContinuousMovement() {
        RecordingSink sink = new RecordingSink();
        Set<KeyCode> pressed = EnumSet.noneOf(KeyCode.class);

        GameControllerInputSupport.handleKeyPressed(KeyCode.UP, pressed, sink);
        GameControllerInputSupport.handleKeyPressed(KeyCode.LEFT, pressed, sink);

        assertTrue(pressed.contains(KeyCode.UP));
        assertTrue(pressed.contains(KeyCode.LEFT));
        assertEquals(0, sink.calls.size(), "movement keys should not trigger instant actions");
    }

    @Test
    void instantActionKeysDispatchToExpectedCallbacks() {
        RecordingSink sink = new RecordingSink();
        Set<KeyCode> pressed = EnumSet.noneOf(KeyCode.class);

        GameControllerInputSupport.handleKeyPressed(KeyCode.H, pressed, sink);
        GameControllerInputSupport.handleKeyPressed(KeyCode.ESCAPE, pressed, sink);
        GameControllerInputSupport.handleKeyPressed(KeyCode.P, pressed, sink);
        GameControllerInputSupport.handleKeyPressed(KeyCode.O, pressed, sink);

        assertEquals("showHighScore", sink.calls.get(0));
        assertEquals("openDifficultyPickerAndMaybeRestart", sink.calls.get(1));
        assertEquals("showNavigationPath", sink.calls.get(2));
        assertEquals("showSpanningTree", sink.calls.get(3));
        assertTrue(pressed.isEmpty(), "instant action keys are not movement keys");
    }

    @Test
    void keyReleaseClearsPressedStateAndOverlayCallbacks() {
        RecordingSink sink = new RecordingSink();
        Set<KeyCode> pressed = EnumSet.of(KeyCode.P, KeyCode.O, KeyCode.RIGHT);

        GameControllerInputSupport.handleKeyReleased(KeyCode.P, pressed, sink);
        GameControllerInputSupport.handleKeyReleased(KeyCode.O, pressed, sink);
        GameControllerInputSupport.handleKeyReleased(KeyCode.RIGHT, pressed, sink);

        assertFalse(pressed.contains(KeyCode.P));
        assertFalse(pressed.contains(KeyCode.O));
        assertFalse(pressed.contains(KeyCode.RIGHT));
        assertEquals("clearNavigationPath", sink.calls.get(0));
        assertEquals("clearSpanningTree", sink.calls.get(1));
        assertEquals(2, sink.calls.size());
    }

    private static final class RecordingSink implements GameControllerInputSupport.GameKeyActionSink {
        private final java.util.List<String> calls = new java.util.ArrayList<>();

        @Override
        public void showHighScore() {
            calls.add("showHighScore");
        }

        @Override
        public void openDifficultyPickerAndMaybeRestart() {
            calls.add("openDifficultyPickerAndMaybeRestart");
        }

        @Override
        public void showNavigationPath() {
            calls.add("showNavigationPath");
        }

        @Override
        public void showSpanningTree() {
            calls.add("showSpanningTree");
        }

        @Override
        public void clearNavigationPath() {
            calls.add("clearNavigationPath");
        }

        @Override
        public void clearSpanningTree() {
            calls.add("clearSpanningTree");
        }
    }
}
