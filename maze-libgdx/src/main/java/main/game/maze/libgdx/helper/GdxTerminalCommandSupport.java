package main.game.maze.libgdx.helper;

import main.game.maze.common.terminal.TerminalCommand;
import main.game.maze.common.terminal.TerminalCommandParser;
import main.game.maze.libgdx.controller.GdxTerminalController;

/**
 * Shared terminal command interpretation for libGDX gameplay screens.
 */
public final class GdxTerminalCommandSupport {

    private static final float HELP_STATUS_SECONDS = 20f;

    private GdxTerminalCommandSupport() {
    }

    public enum Action {
        NONE,
        HELP,
        SHOW_BEHAVIOUR_TYPE,
        SHOW_MOVEMENT_TYPE,
        SHOW_ENEMY_PATH,
        KILL_ENEMIES,
        UNKNOWN
    }

    public record Outcome(Action action, String statusText, float statusSeconds) {
    }

    public static String helpText() {
        return GdxTerminalController.helpText();
    }

    public static TerminalCommand parse(String raw) {
        return TerminalCommandParser.parse(raw);
    }

    public static Outcome evaluate(String raw) {
        TerminalCommand command = parse(raw);
        if (command == TerminalCommand.EMPTY) {
            return new Outcome(Action.NONE, "No command entered", 0f);
        }
        if (command == TerminalCommand.HELP) {
            return new Outcome(Action.HELP, "Commands: " + TerminalCommandParser.HELP_TEXT, HELP_STATUS_SECONDS);
        }
        if (command == TerminalCommand.SHOW_BEHAVIOUR_TYPE) {
            return new Outcome(Action.SHOW_BEHAVIOUR_TYPE, "Showing behaviour type above enemies", 0f);
        }
        if (command == TerminalCommand.SHOW_MOVEMENT_TYPE) {
            return new Outcome(Action.SHOW_MOVEMENT_TYPE, "Showing movement type above enemies", 0f);
        }
        if (command == TerminalCommand.SHOW_ENEMY_PATH) {
            return new Outcome(Action.SHOW_ENEMY_PATH, "Showing enemy paths for 10 seconds", 0f);
        }
        if (command == TerminalCommand.KILL_ENEMIES) {
            return new Outcome(Action.KILL_ENEMIES, "Killing all enemies (resurrection timer active where configured)", 0f);
        }
        return new Outcome(Action.UNKNOWN, "Unknown command. Use /h", 0f);
    }
}
