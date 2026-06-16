package main.game.maze.libgdx.terminal;

import main.game.maze.common.terminal.TerminalCommand;
import main.game.maze.libgdx.helper.GdxTerminalCommandSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Extended tests for {@link GdxTerminalCommandSupport} covering edge cases
 * not addressed in the existing {@code GdxTerminalCommandSupportTest}.
 */
class GdxTerminalCommandSupportExtendedTest {

    // null input must not throw and must produce NONE action.
    @Test
    void evaluate_nullInput_returnsNoneActionWithoutThrowing() {
        assertDoesNotThrow(() -> {
            var result = GdxTerminalCommandSupport.evaluate(null);
            assertEquals(GdxTerminalCommandSupport.Action.NONE, result.action());
        });
    }

    // parse delegates correctly for the full terminal-command alias set.
    @ParameterizedTest
    @ValueSource(strings = {"/showbehaviourtype", "/sbt"})
    void parse_showBehaviourTypeAliases_returnShowBehaviourType(String cmd) {
        assertEquals(TerminalCommand.SHOW_BEHAVIOUR_TYPE, GdxTerminalCommandSupport.parse(cmd));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/showmovementtype", "/smt"})
    void parse_showMovementTypeAliases_returnShowMovementType(String cmd) {
        assertEquals(TerminalCommand.SHOW_MOVEMENT_TYPE, GdxTerminalCommandSupport.parse(cmd));
    }

    // evaluate returns a non-null result for every possible TerminalCommand value.
    @Test
    void evaluate_allKnownCommands_returnNonNullResult() {
        String[] inputs = {"/h", "/sbt", "/smt", "/sep", "/sep", "/unknownxyz", ""};
        for (String input : inputs) {
            assertNotNull(GdxTerminalCommandSupport.evaluate(input),
                    "evaluate must never return null for input: '" + input + "'");
        }
    }

    // helpText must contain references to the enemy-path command.
    @Test
    void helpText_containsEnemyPathAlias() {
        assertTrue(GdxTerminalCommandSupport.helpText().contains("/sep"),
                "helpText must include the /sep alias for show-enemy-path");
    }

    // Outcome for unknown command must have a non-blank status text.
    @Test
    void evaluate_unknownCommand_hasNonBlankStatusText() {
        var result = GdxTerminalCommandSupport.evaluate("/thisdoesnotexist");
        assertFalse(result.statusText().isBlank(),
                "Unknown command outcome must provide a non-blank status message");
    }

    // HELP command statusSeconds must be positive (it shows the full help text for a while).
    @Test
    void evaluate_helpCommand_hasPositiveStatusDuration() {
        var result = GdxTerminalCommandSupport.evaluate("/h");
        assertTrue(result.statusSeconds() > 0f,
                "Status duration must be positive for the help command");
    }
}
