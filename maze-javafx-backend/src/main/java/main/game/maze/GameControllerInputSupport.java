package main.game.maze;

import java.util.Set;
import javafx.scene.input.KeyCode;

final class GameControllerInputSupport {

    private GameControllerInputSupport() {
    }

    static void handleKeyPressed(KeyCode code, Set<KeyCode> pressedKeys, GameKeyActionSink sink) {
        if (isMovementKey(code)) {
            pressedKeys.add(code);
            return;
        }

        switch (code) {
            case H -> sink.showHighScore();
            case ESCAPE -> sink.openDifficultyPickerAndMaybeRestart();
            case P -> sink.showNavigationPath();
            case O -> sink.showSpanningTree();
            default -> {
            }
        }
    }

    static void handleKeyReleased(KeyCode code, Set<KeyCode> pressedKeys, GameKeyActionSink sink) {
        pressedKeys.remove(code);
        if (code == KeyCode.P) {
            sink.clearNavigationPath();
        } else if (code == KeyCode.O) {
            sink.clearSpanningTree();
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