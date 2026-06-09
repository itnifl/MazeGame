package main.game.maze.libgdx;

import main.game.maze.libgdx.adapter.*;
import main.game.maze.libgdx.controller.*;
import main.game.maze.libgdx.helper.*;
import main.game.maze.libgdx.service.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.game.maze.common.terminal.TerminalCommand;
import org.junit.jupiter.api.Test;

class GdxTerminalCommandSupportTest {

    @Test
    void parseDelegatesToTerminalParser() {
        assertEquals(TerminalCommand.HELP, GdxTerminalCommandSupport.parse("/h"));
        assertEquals(TerminalCommand.SHOW_ENEMY_PATH, GdxTerminalCommandSupport.parse("/sep"));
        assertEquals(TerminalCommand.UNKNOWN, GdxTerminalCommandSupport.parse("/missing"));
    }

    @Test
    void evaluateReturnsExpectedActionsAndMessages() {
        var empty = GdxTerminalCommandSupport.evaluate("   ");
        assertEquals(GdxTerminalCommandSupport.Action.NONE, empty.action());
        assertEquals("No command entered", empty.statusText());

        var behaviour = GdxTerminalCommandSupport.evaluate("/sbt");
        assertEquals(GdxTerminalCommandSupport.Action.SHOW_BEHAVIOUR_TYPE, behaviour.action());
        assertEquals("Showing behaviour type above enemies", behaviour.statusText());

        var movement = GdxTerminalCommandSupport.evaluate("/smt");
        assertEquals(GdxTerminalCommandSupport.Action.SHOW_MOVEMENT_TYPE, movement.action());
        assertEquals("Showing movement type above enemies", movement.statusText());

        var enemyPath = GdxTerminalCommandSupport.evaluate("/sep");
        assertEquals(GdxTerminalCommandSupport.Action.SHOW_ENEMY_PATH, enemyPath.action());
        assertEquals("Showing enemy paths for 10 seconds", enemyPath.statusText());

        var unknown = GdxTerminalCommandSupport.evaluate("/noop");
        assertEquals(GdxTerminalCommandSupport.Action.UNKNOWN, unknown.action());
        assertEquals("Unknown command. Use /h", unknown.statusText());
    }

    @Test
    void helpOutcomeUsesExtendedStatusDurationAndHelpText() {
        var help = GdxTerminalCommandSupport.evaluate("/h");

        assertEquals(GdxTerminalCommandSupport.Action.HELP, help.action());
        assertEquals(20f, help.statusSeconds());
        assertTrue(help.statusText().contains("Commands:"));
        assertTrue(GdxTerminalCommandSupport.helpText().contains("/sep"));
    }
}
