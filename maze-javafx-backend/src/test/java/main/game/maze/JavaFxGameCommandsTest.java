package main.game.maze;

import static org.junit.jupiter.api.Assertions.*;

import main.game.maze.common.input.InputFrame;
import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * Tests for individual JavaFX input command implementations.
 * Verifies that each command correctly delegates to the context when executed.
 */
class JavaFxGameCommandsTest {

    @Test
    void allCommandsDelegateCorrectlyToContext() {
        RecordingSink sink = new RecordingSink();
        JavaFxInputCommandContext context = new JavaFxInputCommandContext(sink);

        new ShowHighScoreCommand().execute(context);
        new ReturnToMenuCommand().execute(context);
        new ApplyPathHintCommand().execute(context);
        new TriggerFlameAttackCommand().execute(context);
        new ToggleSpanningTreeCommand().execute(context);
        new OpenHighScoresCommand().execute(context);

        assertEquals(6, sink.calls.size(), "All commands should have delegated");
        assertEquals("showHighScore", sink.calls.get(0));
        assertEquals("openDifficultyPickerAndMaybeRestart", sink.calls.get(1));
        assertEquals("showNavigationPath", sink.calls.get(2));
        assertEquals("triggerPlayerFlameAttack", sink.calls.get(3));
        assertEquals("showSpanningTree", sink.calls.get(4));
        assertEquals("showHighScore", sink.calls.get(5));
    }

    @Test
    void movePlayerCommand_callsApplyMovementFromFrame() {
        RecordingSink sink = new RecordingSink();
        JavaFxInputCommandContext context = new JavaFxInputCommandContext(sink);
        assertDoesNotThrow(() -> new MovePlayerCommand().execute(context));
        // applyMovementFromFrame is a no-op in this implementation; just verify no crash
        assertTrue(sink.calls.isEmpty(), "MovePlayerCommand must not produce sink calls");
    }

    @Test
    void openDifficultyPickerCommand_callsRequestReturnToMenu() {
        RecordingSink sink = new RecordingSink();
        JavaFxInputCommandContext context = new JavaFxInputCommandContext(sink);
        new OpenDifficultyPickerCommand().execute(context);
        assertEquals(1, sink.calls.size());
        assertEquals("openDifficultyPickerAndMaybeRestart", sink.calls.get(0));
    }

    @Test
    void showNavigationPathCommand_callsApplyPathHintHeldTrue() {
        RecordingSink sink = new RecordingSink();
        JavaFxInputCommandContext context = new JavaFxInputCommandContext(sink);
        new ShowNavigationPathCommand().execute(context);
        assertEquals(1, sink.calls.size());
        assertEquals("showNavigationPath", sink.calls.get(0));
    }

    @Test
    void showSpanningTreeCommand_callsToggleSpanningTree() {
        RecordingSink sink = new RecordingSink();
        JavaFxInputCommandContext context = new JavaFxInputCommandContext(sink);
        new ShowSpanningTreeCommand().execute(context);
        assertEquals(1, sink.calls.size());
        assertEquals("showSpanningTree", sink.calls.get(0));
    }

    @Test
    void toggleTerminalCommand_callsOpenTerminalPrompt() {
        RecordingSink sink = new RecordingSink();
        JavaFxInputCommandContext context = new JavaFxInputCommandContext(sink);
        new ToggleTerminalCommand().execute(context);
        assertEquals(1, sink.calls.size());
        assertEquals("openTerminalPrompt", sink.calls.get(0));
    }

    @Test
    void defaultExecuteMethod_routesThroughJavaFxContext() {
        RecordingSink sink = new RecordingSink();
        JavaFxInputCommandContext context = new JavaFxInputCommandContext(sink);
        InputFrame<javafx.scene.input.KeyCode> frame =
                new InputFrame<>(Set.of(), Set.of(), 0, 0, false);

        JavaFxGameCommand cmd = new ShowHighScoreCommand();
        cmd.execute(context, frame);

        assertEquals(1, sink.calls.size());
        assertEquals("showHighScore", sink.calls.get(0));
    }

    @Test
    void defaultExecuteMethod_throwsOnWrongContextType() {
        InputFrame<javafx.scene.input.KeyCode> frame =
                new InputFrame<>(Set.of(), Set.of(), 0, 0, false);
        JavaFxGameCommand cmd = new ShowHighScoreCommand();

        assertThrows(IllegalArgumentException.class,
                () -> cmd.execute(new main.game.maze.common.input.command.GameCommandContext() {
                    @Override public boolean terminalActive() { return false; }
                    @Override public void requestReturnToMenu() {}
                    @Override public void openTerminalPrompt() {}
                    @Override public void openHighScores() {}
                    @Override public void toggleSpanningTree() {}
                    @Override public void applyPathHintHeld(boolean held) {}
                    @Override public void triggerPlayerFlameAttack() {}
                    @Override public void applyMovementFromFrame() {}
                    @Override public void requestStop() {}
                    @Override public boolean stopRequested() { return false; }
                }, frame));
    }

    @Test
    void defaultExecuteMethod_throwsOnNullContext() {
        InputFrame<javafx.scene.input.KeyCode> frame =
                new InputFrame<>(Set.of(), Set.of(), 0, 0, false);
        JavaFxGameCommand cmd = new ShowHighScoreCommand();
        assertThrows(IllegalArgumentException.class, () -> cmd.execute(null, frame));
    }

    // -----------------------------------------------------------------------

    private static final class RecordingSink implements JavaFxInputCommandContext.ActionSink {
        private final java.util.List<String> calls = new java.util.ArrayList<>();

        @Override public void showHighScore()                        { calls.add("showHighScore"); }
        @Override public void openDifficultyPickerAndMaybeRestart()  { calls.add("openDifficultyPickerAndMaybeRestart"); }
        @Override public void showNavigationPath()                   { calls.add("showNavigationPath"); }
        @Override public void showSpanningTree()                     { calls.add("showSpanningTree"); }
        @Override public void clearNavigationPath()                  { calls.add("clearNavigationPath"); }
        @Override public void clearSpanningTree()                    { calls.add("clearSpanningTree"); }
        @Override public void updateDebugLabels()                    { calls.add("updateDebugLabels"); }
        @Override public void updateScoreHud()                       { calls.add("updateScoreHud"); }
        @Override public void openTerminalPrompt()                   { calls.add("openTerminalPrompt"); }
        @Override public void triggerPlayerFlameAttack()             { calls.add("triggerPlayerFlameAttack"); }
    }
}
