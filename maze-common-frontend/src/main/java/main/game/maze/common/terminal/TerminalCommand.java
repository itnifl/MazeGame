package main.game.maze.common.terminal;

/**
 * Terminal commands shared by all frontend modules (JavaFX and libGDX).
 * Parsing is handled by {@link TerminalCommandParser}.
 */
public enum TerminalCommand {
    EMPTY,
    HELP,
    SHOW_BEHAVIOUR_TYPE,
    SHOW_MOVEMENT_TYPE,
    SHOW_ENEMY_PATH,
    KILL_ENEMIES,
    UNKNOWN
}
