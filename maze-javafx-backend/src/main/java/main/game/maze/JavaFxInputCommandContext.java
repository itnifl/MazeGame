package main.game.maze;

/**
 * Small facade used by JavaFX input command objects.
 */
final class JavaFxInputCommandContext {

    private final GameControllerInputSupport.GameKeyActionSink keyActionSink;

    JavaFxInputCommandContext(GameControllerInputSupport.GameKeyActionSink keyActionSink) {
        this.keyActionSink = keyActionSink;
    }

    void showHighScore() {
        keyActionSink.showHighScore();
    }

    void openDifficultyPickerAndMaybeRestart() {
        keyActionSink.openDifficultyPickerAndMaybeRestart();
    }

    void showNavigationPath() {
        keyActionSink.showNavigationPath();
    }

    void showSpanningTree() {
        keyActionSink.showSpanningTree();
    }
}
