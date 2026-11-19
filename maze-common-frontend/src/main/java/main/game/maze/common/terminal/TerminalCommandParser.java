package main.game.maze.common.terminal;

import java.util.Locale;
import main.game.maze.common.remains.DeathCause;

/**
 * Parses raw terminal input strings into {@link TerminalCommand} values.
 * Shared by all frontend modules so the command vocabulary stays consistent.
 */
public final class TerminalCommandParser {

    public static final String HELP_TEXT =
            "/h, /showbehaviourtype, /sbt, /showmovementtype, /smt, /showenemypath, /sep (shows enemy paths for 10 seconds), /kill [fire|shot|crushed] (kills all enemies — triggers resurrection timer if configured; with a cause they leave remains)";

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
        if ("/kill".equals(command) || command.matches("/kill\\s.*")) {
            return killCause(raw) != null ? TerminalCommand.KILL_ENEMIES : TerminalCommand.UNKNOWN;
        }
        return TerminalCommand.UNKNOWN;
    }

    /**
     * Death cause for {@code /kill [cause]}: none means {@link DeathCause#OTHER} (no remains);
     * {@code fire}, {@code shot} or {@code crushed} (also {@code burn}, {@code shoot}, {@code crush},
     * {@code squash}) leave that kind of remains. Returns null for any other argument.
     */
    public static DeathCause killCause(String raw) {
        String[] words = raw == null ? new String[0] : raw.trim().toLowerCase(Locale.ROOT).split("\\s+");
        if (words.length < 2) {
            return DeathCause.OTHER;
        }
        if (words.length > 2) {
            return null;
        }
        return switch (words[1]) {
            case "fire", "burn" -> DeathCause.FIRE;
            case "shot", "shoot" -> DeathCause.SHOT;
            case "crushed", "crush", "squash" -> DeathCause.CRUSHED;
            default -> null;
        };
    }
}
