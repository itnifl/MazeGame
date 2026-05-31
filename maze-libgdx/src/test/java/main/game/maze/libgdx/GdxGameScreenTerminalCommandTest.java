package main.game.maze.libgdx;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.game.maze.common.terminal.TerminalCommand;
import org.junit.jupiter.api.Test;

class GdxGameScreenTerminalCommandTest {

    @Test
    void parsesHelpCommand() {
        assertEquals(TerminalCommand.HELP,
                GdxGameScreen.parseTerminalCommand("/h"));
    }

    @Test
    void parsesBehaviourCommandCaseInsensitive() {
        assertEquals(TerminalCommand.SHOW_BEHAVIOUR_TYPE,
                GdxGameScreen.parseTerminalCommand("  /SHOWBEHAVIOURTYPE  "));
        assertEquals(TerminalCommand.SHOW_BEHAVIOUR_TYPE,
            GdxGameScreen.parseTerminalCommand("/sbt"));
    }

    @Test
    void parsesMovementCommand() {
        assertEquals(TerminalCommand.SHOW_MOVEMENT_TYPE,
                GdxGameScreen.parseTerminalCommand("/showmovementtype"));
        assertEquals(TerminalCommand.SHOW_MOVEMENT_TYPE,
            GdxGameScreen.parseTerminalCommand("/smt"));
    }

    @Test
    void parsesUnknownAndEmptyCommands() {
        assertEquals(TerminalCommand.UNKNOWN,
                GdxGameScreen.parseTerminalCommand("/noop"));
        assertEquals(TerminalCommand.EMPTY,
                GdxGameScreen.parseTerminalCommand("   "));
    }

    @Test
    void parsesShowEnemyPathCommand() {
        assertEquals(TerminalCommand.SHOW_ENEMY_PATH,
                GdxGameScreen.parseTerminalCommand("/showenemypath"));
        assertEquals(TerminalCommand.SHOW_ENEMY_PATH,
                GdxGameScreen.parseTerminalCommand("/sep"));
        assertEquals(TerminalCommand.SHOW_ENEMY_PATH,
                GdxGameScreen.parseTerminalCommand("  /SEP  "));
    }

    @Test
    void helpTextMentionsEnemyPathDuration() {
        assertTrue(GdxGameScreen.terminalHelpText().contains("10 seconds"),
                "terminal help must say that /sep shows enemy paths for 10 seconds");
    }
}
