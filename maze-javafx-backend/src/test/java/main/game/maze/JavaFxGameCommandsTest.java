package main.game.maze;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for individual JavaFX input command implementations.
 * Verifies that each command correctly delegates to the context when executed.
 */
class JavaFxGameCommandsTest {

    @Test
    void allCommandsDelegateCorrectlyToContext() {
        RecordingSink sink = new RecordingSink();
        JavaFxInputCommandContext context = new JavaFxInputCommandContext(sink);

        // Test all available commands
        new ShowHighScoreCommand().execute(context);
        new ReturnToMenuCommand().execute(context);
        new ApplyPathHintCommand().execute(context);
        new ToggleSpanningTreeCommand().execute(context);
        new OpenHighScoresCommand().execute(context);

        assertEquals(5, sink.calls.size(), "All commands should have delegated");
        assertEquals("showHighScore", sink.calls.get(0));
        assertEquals("openDifficultyPickerAndMaybeRestart", sink.calls.get(1));
        assertEquals("showNavigationPath", sink.calls.get(2));
        assertEquals("showSpanningTree", sink.calls.get(3));
        assertEquals("showHighScore", sink.calls.get(4));
    }

    private static final class RecordingSink implements JavaFxInputCommandContext.GameKeyActionSink {
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
        }

        @Override
        public void clearSpanningTree() {
        }
    }
}
