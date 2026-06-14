package main.game.maze;

/**
 * Handles TOGGLE_TERMINAL action (T key).
 * Opens the terminal command prompt.
 */
final class ToggleTerminalCommand implements JavaFxGameCommand {
    @Override
    public void execute(JavaFxInputCommandContext context) {
        // Terminal is currently invoked via ESC or explicit openTerminalPrompt() call.
        // This command is a placeholder for full terminal integration via the command system.
        context.openTerminalPrompt();
    }
}
