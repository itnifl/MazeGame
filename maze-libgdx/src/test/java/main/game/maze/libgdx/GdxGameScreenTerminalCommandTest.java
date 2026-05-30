package main.game.maze.libgdx;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GdxGameScreenTerminalCommandTest {

    @Test
    void parsesHelpCommand() {
        assertEquals(GdxGameScreen.TerminalCommand.HELP,
                GdxGameScreen.parseTerminalCommand("/h"));
    }

    @Test
    void parsesBehaviourCommandCaseInsensitive() {
        assertEquals(GdxGameScreen.TerminalCommand.SHOW_BEHAVIOUR_TYPE,
                GdxGameScreen.parseTerminalCommand("  /SHOWBEHAVIOURTYPE  "));
        assertEquals(GdxGameScreen.TerminalCommand.SHOW_BEHAVIOUR_TYPE,
            GdxGameScreen.parseTerminalCommand("/sbt"));
    }

    @Test
    void parsesMovementCommand() {
        assertEquals(GdxGameScreen.TerminalCommand.SHOW_MOVEMENT_TYPE,
                GdxGameScreen.parseTerminalCommand("/showmovementtype"));
        assertEquals(GdxGameScreen.TerminalCommand.SHOW_MOVEMENT_TYPE,
            GdxGameScreen.parseTerminalCommand("/smt"));
    }

    @Test
    void parsesUnknownAndEmptyCommands() {
        assertEquals(GdxGameScreen.TerminalCommand.UNKNOWN,
                GdxGameScreen.parseTerminalCommand("/noop"));
        assertEquals(GdxGameScreen.TerminalCommand.EMPTY,
                GdxGameScreen.parseTerminalCommand("   "));
    }

    @Test
    void parsesShowEnemyPathCommand() {
        assertEquals(GdxGameScreen.TerminalCommand.SHOW_ENEMY_PATH,
                GdxGameScreen.parseTerminalCommand("/showenemypath"));
        assertEquals(GdxGameScreen.TerminalCommand.SHOW_ENEMY_PATH,
                GdxGameScreen.parseTerminalCommand("/sep"));
        assertEquals(GdxGameScreen.TerminalCommand.SHOW_ENEMY_PATH,
                GdxGameScreen.parseTerminalCommand("  /SEP  "));
    }
}
