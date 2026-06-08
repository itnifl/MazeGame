package main.game.maze.libgdx.controller;

import main.game.maze.libgdx.adapter.*;
import main.game.maze.libgdx.controller.*;
import main.game.maze.libgdx.helper.*;
import main.game.maze.libgdx.service.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.game.maze.common.terminal.TerminalCommand;
import org.junit.jupiter.api.Test;

class GdxGameScreenTerminalCommandTest {

    @Test
    void parsesHelpCommand() {
        assertEquals(TerminalCommand.HELP,
                GdxGameScreenController.parseTerminalCommand("/h"));
    }

    @Test
    void parsesBehaviourCommandCaseInsensitive() {
        assertEquals(TerminalCommand.SHOW_BEHAVIOUR_TYPE,
                GdxGameScreenController.parseTerminalCommand("  /SHOWBEHAVIOURTYPE  "));
        assertEquals(TerminalCommand.SHOW_BEHAVIOUR_TYPE,
            GdxGameScreenController.parseTerminalCommand("/sbt"));
    }

    @Test
    void parsesMovementCommand() {
        assertEquals(TerminalCommand.SHOW_MOVEMENT_TYPE,
                GdxGameScreenController.parseTerminalCommand("/showmovementtype"));
        assertEquals(TerminalCommand.SHOW_MOVEMENT_TYPE,
            GdxGameScreenController.parseTerminalCommand("/smt"));
    }

    @Test
    void parsesUnknownAndEmptyCommands() {
        assertEquals(TerminalCommand.UNKNOWN,
                GdxGameScreenController.parseTerminalCommand("/noop"));
        assertEquals(TerminalCommand.EMPTY,
                GdxGameScreenController.parseTerminalCommand("   "));
    }

    @Test
    void parsesShowEnemyPathCommand() {
        assertEquals(TerminalCommand.SHOW_ENEMY_PATH,
                GdxGameScreenController.parseTerminalCommand("/showenemypath"));
        assertEquals(TerminalCommand.SHOW_ENEMY_PATH,
                GdxGameScreenController.parseTerminalCommand("/sep"));
        assertEquals(TerminalCommand.SHOW_ENEMY_PATH,
                GdxGameScreenController.parseTerminalCommand("  /SEP  "));
    }

    @Test
    void helpTextMentionsEnemyPathDuration() {
        assertTrue(GdxGameScreenController.terminalHelpText().contains("10 seconds"),
                "terminal help must say that /sep shows enemy paths for 10 seconds");
    }
}
