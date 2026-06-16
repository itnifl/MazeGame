package main.game.maze.common.input;

import main.game.maze.common.terminal.TerminalCommand;
import main.game.maze.common.terminal.TerminalCommandParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class TerminalCommandParserTest {

    @Test
    void parse_helpAlias_returnsHelp() {
        assertEquals(TerminalCommand.HELP, TerminalCommandParser.parse("/h"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/showbehaviourtype", "/sbt"})
    void parse_showBehaviourTypeAliases_returnShowBehaviourType(String cmd) {
        assertEquals(TerminalCommand.SHOW_BEHAVIOUR_TYPE, TerminalCommandParser.parse(cmd));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/showmovementtype", "/smt"})
    void parse_showMovementTypeAliases_returnShowMovementType(String cmd) {
        assertEquals(TerminalCommand.SHOW_MOVEMENT_TYPE, TerminalCommandParser.parse(cmd));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/showenemypath", "/sep"})
    void parse_showEnemyPathAliases_returnShowEnemyPath(String cmd) {
        assertEquals(TerminalCommand.SHOW_ENEMY_PATH, TerminalCommandParser.parse(cmd));
    }

    @Test
    void parse_unknownInput_returnsUnknown() {
        assertEquals(TerminalCommand.UNKNOWN, TerminalCommandParser.parse("/xyz-not-a-command"));
    }

    @Test
    void parse_emptyString_returnsEmpty() {
        assertEquals(TerminalCommand.EMPTY, TerminalCommandParser.parse(""));
    }

    @Test
    void parse_null_returnsEmpty() {
        assertEquals(TerminalCommand.EMPTY, TerminalCommandParser.parse(null));
    }

    @Test
    void parse_whitespaceOnly_returnsEmpty() {
        assertEquals(TerminalCommand.EMPTY, TerminalCommandParser.parse("   "));
    }

    // Case-insensitive: '/H' should match the same as '/h'.
    @Test
    void parse_isCaseInsensitive() {
        assertEquals(TerminalCommand.HELP, TerminalCommandParser.parse("/H"));
        assertEquals(TerminalCommand.SHOW_ENEMY_PATH, TerminalCommandParser.parse("/SEP"));
        assertEquals(TerminalCommand.SHOW_BEHAVIOUR_TYPE, TerminalCommandParser.parse("/SBT"));
    }

    // Leading/trailing whitespace must be trimmed.
    @Test
    void parse_trimsWhitespace() {
        assertEquals(TerminalCommand.HELP, TerminalCommandParser.parse("  /h  "));
    }

    @Test
    void helpText_isNonEmpty() {
        assertNotNull(TerminalCommandParser.HELP_TEXT);
        assertFalse(TerminalCommandParser.HELP_TEXT.isBlank(), "HELP_TEXT must contain usage info");
    }
}
