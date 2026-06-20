package main.game.maze.common.terminal;

import java.util.Locale;

/**
 * Parses raw terminal input strings into {@link TerminalCommand} values.
 * Shared by all frontend modules so the command vocabulary stays consistent.
 */
public final class TerminalCommandParser {

    public static final String HELP_TEXT =
            "/h, /showbehaviourtype, /sbt, /showmovementtype, /smt, /showenemypath, /sep (shows enemy paths for 10 seconds), /kill (kills all enemies — triggers resurrection timer if configured)";

    private TerminalCommandParser() {}

    public static TerminalCommand parse(String raw) {
        String command = raw == null ? "" : raw.trim().toLowerCase(Locale.ROOT);
        if (command.isEmpty()) {
            return TerminalCommand.EMPTY;
        }
        if ("/h".equals(command)) {
            return TerminalCommand.HELP;
        }
        if ("/showbehaviourtype".equals(command) || "/sbt".equals(command)) {
            return TerminalCommand.SHOW_BEHAVIOUR_TYPE;
        }
        if ("/showmovementtype".equals(command) || "/smt".equals(command)) {
            return TerminalCommand.SHOW_MOVEMENT_TYPE;
        }
        if ("/showenemypath".equals(command) || "/sep".equals(command)) {
            return TerminalCommand.SHOW_ENEMY_PATH;
        }
        if ("/kill".equals(command)) {
            return TerminalCommand.KILL_ENEMIES;
        }
        return TerminalCommand.UNKNOWN;
    }
}
