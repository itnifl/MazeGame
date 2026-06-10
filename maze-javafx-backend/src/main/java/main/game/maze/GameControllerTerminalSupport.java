package main.game.maze;

import javafx.util.Duration;
import main.game.maze.common.terminal.TerminalCommand;
import main.game.maze.common.terminal.TerminalCommandParser;

final class GameControllerTerminalSupport {

    static final String PROMPT_CONTENT_TEXT = TerminalCommandParser.HELP_TEXT;
    static final String HELP_MESSAGE = "Commands: " + TerminalCommandParser.HELP_TEXT;
    static final String SHOW_BEHAVIOUR_MESSAGE = "Showing behaviour type above enemies";
    static final String SHOW_MOVEMENT_MESSAGE = "Showing movement type above enemies";
    static final String SHOW_ENEMY_PATH_MESSAGE = "Showing enemy paths for 10 seconds";
    static final String EMPTY_MESSAGE = "No command entered";
    static final String UNKNOWN_MESSAGE = "Unknown command. Use /h";

    private GameControllerTerminalSupport() {
    }

    static TerminalCommand parseTerminalCommand(String raw) {
        return TerminalCommandParser.parse(raw);
    }

    static void executeTerminalCommand(String raw, TerminalCommandSink sink) {
        switch (parseTerminalCommand(raw)) {
            case HELP -> sink.setHudMessage(HELP_MESSAGE, Duration.seconds(20));
            case SHOW_BEHAVIOUR_TYPE -> {
                sink.setHudMessage(SHOW_BEHAVIOUR_MESSAGE);
                sink.showEnemyDebugLabels(true);
            }
            case SHOW_MOVEMENT_TYPE -> {
                sink.setHudMessage(SHOW_MOVEMENT_MESSAGE);
                sink.showEnemyDebugLabels(false);
            }
            case SHOW_ENEMY_PATH -> {
                sink.setHudMessage(SHOW_ENEMY_PATH_MESSAGE);
                sink.showEnemyPathsOverlay();
            }
            case EMPTY -> sink.setHudMessage(EMPTY_MESSAGE);
            default -> sink.setHudMessage(UNKNOWN_MESSAGE);
        }
    }

    interface TerminalCommandSink {
        void setHudMessage(String text);

        void setHudMessage(String text, Duration visibleFor);

        void showEnemyDebugLabels(boolean behaviourType);

        void showEnemyPathsOverlay();
    }
}