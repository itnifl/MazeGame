package main.game.maze.libgdx.commands;

import com.badlogic.gdx.Input;
import main.game.maze.common.input.InputFrame;
import main.game.maze.libgdx.input.command.ApplyPathHintCommand;
import main.game.maze.libgdx.input.command.MovePlayerCommand;
import main.game.maze.libgdx.input.command.OpenHighScoresCommand;
import main.game.maze.libgdx.input.command.ReturnToMenuCommand;
import main.game.maze.libgdx.input.command.ToggleSpanningTreeCommand;
import main.game.maze.libgdx.input.command.ToggleTerminalCommand;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GdxGameCommandsTest {

    private static InputFrame<Integer> emptyFrame() {
        return new InputFrame<>(Set.of(), Set.of(), 0, 0, false);
    }

    private static InputFrame<Integer> heldFrame(int key) {
        return new InputFrame<>(Set.of(key), Set.of(), 0, 0, false);
    }

    // Each command delegates to the expected context method when terminal is NOT active.
    @Test
    void applyPathHintCommand_pHeld_callsApplyPathHintHeld() {
        CapturingContext ctx = new CapturingContext(false);
        new ApplyPathHintCommand().execute(ctx, heldFrame(Input.Keys.P));
        // P is held → applyPathHintHeld(true)
        assertTrue(ctx.calls.contains("applyPathHintHeld:true"),
                "ApplyPathHintCommand with P held must call applyPathHintHeld(true)");
    }

    @Test
    void applyPathHintCommand_pNotHeld_callsApplyPathHintNotHeld() {
        CapturingContext ctx = new CapturingContext(false);
        new ApplyPathHintCommand().execute(ctx, emptyFrame());
        assertTrue(ctx.calls.contains("applyPathHintHeld:false"),
                "ApplyPathHintCommand without P held must call applyPathHintHeld(false)");
    }

    @Test
    void returnToMenuCommand_callsReturnToMenuAndStop() {
        CapturingContext ctx = new CapturingContext(false);
        new ReturnToMenuCommand().execute(ctx, emptyFrame());
        assertTrue(ctx.calls.contains("requestReturnToMenu"), "Must call requestReturnToMenu");
        assertTrue(ctx.calls.contains("requestStop"), "Must call requestStop");
    }

    @Test
    void openHighScoresCommand_callsOpenHighScores() {
        CapturingContext ctx = new CapturingContext(false);
        new OpenHighScoresCommand().execute(ctx, emptyFrame());
        assertTrue(ctx.calls.contains("openHighScores"));
    }

    @Test
    void toggleSpanningTreeCommand_callsToggleSpanningTree() {
        CapturingContext ctx = new CapturingContext(false);
        new ToggleSpanningTreeCommand().execute(ctx, emptyFrame());
        assertTrue(ctx.calls.contains("toggleSpanningTree"));
    }

    @Test
    void toggleTerminalCommand_callsOpenTerminalAndStop() {
        CapturingContext ctx = new CapturingContext(false);
        new ToggleTerminalCommand().execute(ctx, emptyFrame());
        assertTrue(ctx.calls.contains("openTerminalPrompt"));
        assertTrue(ctx.calls.contains("requestStop"));
    }

    @Test
    void movePlayerCommand_callsApplyMovementFromFrame() {
        CapturingContext ctx = new CapturingContext(false);
        new MovePlayerCommand().execute(ctx, emptyFrame());
        assertTrue(ctx.calls.contains("applyMovementFromFrame"));
    }

    // All commands are no-ops when terminal is active.
    @Test
    void allCommands_terminalActive_areNoOps() {
        CapturingContext ctx = new CapturingContext(true);
        new ApplyPathHintCommand().execute(ctx, heldFrame(Input.Keys.P));
        new ReturnToMenuCommand().execute(ctx, emptyFrame());
        new OpenHighScoresCommand().execute(ctx, emptyFrame());
        new ToggleSpanningTreeCommand().execute(ctx, emptyFrame());
        new ToggleTerminalCommand().execute(ctx, emptyFrame());
        new MovePlayerCommand().execute(ctx, emptyFrame());
        assertTrue(ctx.calls.isEmpty(),
                "All commands must be no-ops when terminal is active");
    }

    // Two execute() calls produce two context invocations (no dedup).
    @Test
    void openHighScoresCommand_executedTwice_producesExactlyTwoCalls() {
        CapturingContext ctx = new CapturingContext(false);
        new OpenHighScoresCommand().execute(ctx, emptyFrame());
        new OpenHighScoresCommand().execute(ctx, emptyFrame());
        long count = ctx.calls.stream().filter("openHighScores"::equals).count();
        assertEquals(2, count, "Two execute() calls must produce exactly two sink invocations");
    }

    // -----------------------------------------------------------------------
    private static final class CapturingContext implements main.game.maze.common.input.command.GameCommandContext {
        final List<String> calls = new ArrayList<>();
        private final boolean terminal;

        CapturingContext(boolean terminal) { this.terminal = terminal; }

        @Override public boolean terminalActive() { return terminal; }
        @Override public void requestReturnToMenu() { calls.add("requestReturnToMenu"); }
        @Override public void openTerminalPrompt() { calls.add("openTerminalPrompt"); }
        @Override public void openHighScores() { calls.add("openHighScores"); }
        @Override public void toggleSpanningTree() { calls.add("toggleSpanningTree"); }
        @Override public void applyPathHintHeld(boolean held) { calls.add("applyPathHintHeld:" + held); }
        @Override public void applyMovementFromFrame() { calls.add("applyMovementFromFrame"); }
        @Override public void requestStop() { calls.add("requestStop"); }
        @Override public boolean stopRequested() { return false; }
    }
}
