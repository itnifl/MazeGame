package main.game.maze.game.level;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import main.game.maze.constants.DataFileConstants;

/**
 * What the main menu's Load button needs, shared by both frontends: the savepoint the game keeps (the last level
 * the player entered, with the state it was entered at), the text of the button, and the difficulty to load it on.
 */
public final class LoadGameMenu {

    private LoadGameMenu() {
    }

    /** The savepoint file of the game (a relative path, like the high scores). */
    public static SavePointStore defaultStore() {
        return new FileSavePointStore(Path.of(DataFileConstants.SavePointFilePath));
    }

    /** The latest savepoint, if there is one. */
    public static Optional<SavePoint> latest(SavePointStore store) {
        return store.load();
    }

    /** The text of the Load button: the level that is loaded, or that there is nothing to load. */
    public static String label(Optional<SavePoint> savePoint) {
        return savePoint.map(point -> "Load Level " + point.level()).orElse("Load (no saved level)");
    }

    /**
     * The difficulty to load the savepoint on: the one it was saved on when the menu knows it, else {@code fallback}
     * (the one chosen in the menu).
     */
    public static <T> T difficultyFor(SavePoint savePoint, List<T> options, Function<T, String> nameOf, T fallback) {
        for (T option : options) {
            if (!savePoint.difficulty().isEmpty() && savePoint.difficulty().equalsIgnoreCase(nameOf.apply(option))) {
                return option;
            }
        }
        return fallback;
    }
}
