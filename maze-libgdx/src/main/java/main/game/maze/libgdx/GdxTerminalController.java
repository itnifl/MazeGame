package main.game.maze.libgdx;

import main.game.maze.common.terminal.TerminalCommandParser;

/**
 * Keeps terminal prompt state and user input buffering isolated from screen
 * rendering and gameplay logic.
 */
final class GdxTerminalController {
    private final int maxChars;
    private final StringBuilder buffer = new StringBuilder();
    private boolean active;
    private String pendingCommand;

    GdxTerminalController(int maxChars) {
        this.maxChars = maxChars;
    }

    boolean isActive() {
        return active;
    }

    String bufferText() {
        return buffer.toString();
    }

    boolean onKeyTyped(char character) {
        if (!active) {
            return false;
        }
        if (character == '\b' || character == '\r' || character == '\n' || character == 27) {
            // ENTER / BACKSPACE / ESC are handled by key polling.
            return false;
        }
        if (character < 32) {
            return false;
        }
        if (buffer.length() >= maxChars) {
            return true;
        }
        buffer.append(character);
        return true;
    }

    void open() {
        active = true;
        buffer.setLength(0);
    }

    void close() {
        active = false;
        buffer.setLength(0);
    }

    void toggle() {
        if (active) {
            close();
            return;
        }
        open();
    }

    void submit() {
        pendingCommand = buffer.toString();
        buffer.setLength(0);
        active = false;
    }

    void backspace() {
        if (buffer.length() > 0) {
            buffer.setLength(buffer.length() - 1);
        }
    }

    String consumePendingCommand() {
        String command = pendingCommand;
        pendingCommand = null;
        return command;
    }

    static String helpText() {
        return TerminalCommandParser.HELP_TEXT;
    }

    void reset() {
        active = false;
        pendingCommand = null;
        buffer.setLength(0);
    }
}