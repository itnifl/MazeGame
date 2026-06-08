package main.game.maze.libgdx.controller;

import main.game.maze.common.terminal.TerminalCommandParser;

/**
 * Keeps terminal prompt state and user input buffering isolated from screen
 * rendering and gameplay logic.
 */
public final class GdxTerminalController {
    private final int maxChars;
    private final StringBuilder buffer = new StringBuilder();
    private boolean active;
    private String pendingCommand;

    public GdxTerminalController(int maxChars) {
        this.maxChars = maxChars;
    }

    public boolean isActive() {
        return active;
    }

    public String bufferText() {
        return buffer.toString();
    }

    public boolean onKeyTyped(char character) {
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

    public void open() {
        active = true;
        buffer.setLength(0);
    }

    public void close() {
        active = false;
        buffer.setLength(0);
    }

    public void toggle() {
        if (active) {
            close();
            return;
        }
        open();
    }

    public void submit() {
        pendingCommand = buffer.toString();
        buffer.setLength(0);
        active = false;
    }

    public void backspace() {
        if (buffer.length() > 0) {
            buffer.setLength(buffer.length() - 1);
        }
    }

    public String consumePendingCommand() {
        String command = pendingCommand;
        pendingCommand = null;
        return command;
    }

    public static String helpText() {
        return TerminalCommandParser.HELP_TEXT;
    }

    public void reset() {
        active = false;
        pendingCommand = null;
        buffer.setLength(0);
    }
}
