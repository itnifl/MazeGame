package main.game.maze;

import java.util.ArrayList;
import java.util.List;

/**
 * Spy implementation of {@link JavaFxInputCommandContext.ActionSink} that records
 * every method invocation for assertion in unit tests.
 * Lives in the same package as ActionSink so it can access the package-private interface.
 */
public final class SpyActionSink implements JavaFxInputCommandContext.ActionSink {

    public final List<String> calls = new ArrayList<>();

    @Override
    public void showHighScore() {
        calls.add("showHighScore");
    }

    @Override
    public void openDifficultyPickerAndMaybeRestart() {
        calls.add("openDifficultyPickerAndMaybeRestart");
    }

    @Override
    public void openTerminalPrompt() {
        calls.add("openTerminalPrompt");
    }

    @Override
    public void showNavigationPath() {
        calls.add("showNavigationPath");
    }

    @Override
    public void clearNavigationPath() {
        calls.add("clearNavigationPath");
    }

    @Override
    public void showSpanningTree() {
        calls.add("showSpanningTree");
    }

    @Override
    public void clearSpanningTree() {
        calls.add("clearSpanningTree");
    }

    @Override
    public void updateDebugLabels() {
        calls.add("updateDebugLabels");
    }

    @Override
    public void updateScoreHud() {
        calls.add("updateScoreHud");
    }

    public int countOf(String methodName) {
        return (int) calls.stream().filter(methodName::equals).count();
    }
}
