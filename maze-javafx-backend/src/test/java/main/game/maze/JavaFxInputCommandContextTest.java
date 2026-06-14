package main.game.maze;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        assertEquals("showHighScore", sink.calls.get(0));
        assertEquals("openDifficultyPickerAndMaybeRestart", sink.calls.get(1));
        assertEquals("showNavigationPath", sink.calls.get(2));
        assertEquals("showSpanningTree", sink.calls.get(3));
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
        }

        @Override
        public void updateScoreHud() {
        }

        @Override
        public void openTerminalPrompt() {
        }
    }
}
