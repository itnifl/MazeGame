package main.game.maze;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JavaFxInputCommandContextTest {

    @Test
    void contextDelegatesAllActionsToSink() {
        RecordingSink sink = new RecordingSink();
        JavaFxInputCommandContext context = new JavaFxInputCommandContext(sink);

        context.openHighScores();
        context.requestReturnToMenu();
        context.applyPathHintHeld(true);
        context.toggleSpanningTree();

        assertEquals(4, sink.calls.size(), "exactly 4 sink calls must have been dispatched");
        assertEquals("showHighScore", sink.calls.get(0));
        assertEquals("openDifficultyPickerAndMaybeRestart", sink.calls.get(1));
        assertEquals("showNavigationPath", sink.calls.get(2));
        assertEquals("showSpanningTree", sink.calls.get(3));
    }

    @Test
    void toggleSpanningTreeAlternatesShowAndClear() {
        RecordingSink sink = new RecordingSink();
        JavaFxInputCommandContext context = new JavaFxInputCommandContext(sink);

        context.toggleSpanningTree();  // first call → show
        context.toggleSpanningTree();  // second call → clear
        context.toggleSpanningTree();  // third call → show again

        assertEquals(3, sink.calls.size());
        assertEquals("showSpanningTree",  sink.calls.get(0));
        assertEquals("clearSpanningTree", sink.calls.get(1));
        assertEquals("showSpanningTree",  sink.calls.get(2));
    }

    @Test
    void openTerminalPromptDelegatesToSink() {
        RecordingSink sink = new RecordingSink();
        JavaFxInputCommandContext context = new JavaFxInputCommandContext(sink);

        context.openTerminalPrompt();

        assertEquals(1, sink.calls.size());
        assertEquals("openTerminalPrompt", sink.calls.get(0));
    }

    @Test
    void applyPathHintHeld_false_callsClearNavigationPath() {
        RecordingSink sink = new RecordingSink();
        JavaFxInputCommandContext context = new JavaFxInputCommandContext(sink);

        context.applyPathHintHeld(false);

        assertEquals(1, sink.calls.size());
        assertEquals("clearNavigationPath", sink.calls.get(0));
    }

    @Test
    void applyMovementFromFrame_doesNotThrow() {
        RecordingSink sink = new RecordingSink();
        JavaFxInputCommandContext context = new JavaFxInputCommandContext(sink);
        assertDoesNotThrow(context::applyMovementFromFrame);
        assertTrue(sink.calls.isEmpty(), "applyMovementFromFrame must be a no-op");
    }

    @Test
    void requestStop_doesNotThrowAndProducesNoSinkCall() {
        RecordingSink sink = new RecordingSink();
        JavaFxInputCommandContext context = new JavaFxInputCommandContext(sink);
        assertDoesNotThrow(context::requestStop);
        assertTrue(sink.calls.isEmpty());
    }

    @Test
    void stopRequested_alwaysReturnsFalse() {
        JavaFxInputCommandContext context = new JavaFxInputCommandContext(new RecordingSink());
        assertFalse(context.stopRequested());
    }

    @Test
    void terminalActive_alwaysReturnsFalse() {
        JavaFxInputCommandContext context = new JavaFxInputCommandContext(new RecordingSink());
        assertFalse(context.terminalActive());
    }

    @Test
    void updateDebugLabels_delegatesToSink() {
        RecordingSink sink = new RecordingSink();
        JavaFxInputCommandContext context = new JavaFxInputCommandContext(sink);
        context.updateDebugLabels();
        assertEquals(1, sink.calls.size());
        assertEquals("updateDebugLabels", sink.calls.get(0));
    }

    @Test
    void updateScoreHud_delegatesToSink() {
        RecordingSink sink = new RecordingSink();
        JavaFxInputCommandContext context = new JavaFxInputCommandContext(sink);
        context.updateScoreHud();
        assertEquals(1, sink.calls.size());
        assertEquals("updateScoreHud", sink.calls.get(0));
    }

    private static final class RecordingSink implements JavaFxInputCommandContext.ActionSink {
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

        @Override
        public void updateDebugLabels() {
            calls.add("updateDebugLabels");
        }

        @Override
        public void updateScoreHud() {
            calls.add("updateScoreHud");
        }

        @Override
        public void openTerminalPrompt() {
            calls.add("openTerminalPrompt");
        }
    }
}
