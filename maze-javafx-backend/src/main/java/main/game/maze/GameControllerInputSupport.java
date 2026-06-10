package main.game.maze;

import java.util.Set;
import javafx.scene.input.KeyCode;

final class GameControllerInputSupport {

    private static final JavaFxGameCommand SHOW_HIGH_SCORE_COMMAND = new ShowHighScoreCommand();
    private static final JavaFxGameCommand OPEN_DIFFICULTY_PICKER_COMMAND = new OpenDifficultyPickerCommand();
    private static final JavaFxGameCommand SHOW_NAVIGATION_PATH_COMMAND = new ShowNavigationPathCommand();
    private static final JavaFxGameCommand SHOW_SPANNING_TREE_COMMAND = new ShowSpanningTreeCommand();

    private GameControllerInputSupport() {
    }

    static void handleKeyPressed(KeyCode code, Set<KeyCode> pressedKeys, GameKeyActionSink sink) {
        if (isMovementKey(code)) {
            pressedKeys.add(code);
            return;
        }

        JavaFxInputCommandContext context = new JavaFxInputCommandContext(sink);
        switch (code) {
            case H -> SHOW_HIGH_SCORE_COMMAND.execute(context);
            case ESCAPE -> OPEN_DIFFICULTY_PICKER_COMMAND.execute(context);
            case P -> SHOW_NAVIGATION_PATH_COMMAND.execute(context);
            case O -> SHOW_SPANNING_TREE_COMMAND.execute(context);
            default -> {
            }
        }
    }

    static void handleKeyReleased(KeyCode code, Set<KeyCode> pressedKeys, GameKeyActionSink sink) {
        pressedKeys.remove(code);
        switch (code) {
            case P -> sink.clearNavigationPath();
            case O -> sink.clearSpanningTree();
            default -> {
            }
        }
    }

    static boolean isMovementKey(KeyCode code) {
        return code == KeyCode.UP
                || code == KeyCode.DOWN
                || code == KeyCode.LEFT
                || code == KeyCode.RIGHT;
    }

    interface GameKeyActionSink {
        void showHighScore();

        void openDifficultyPickerAndMaybeRestart();

        void showNavigationPath();

        void showSpanningTree();

        void clearNavigationPath();

        void clearSpanningTree();
    }
}