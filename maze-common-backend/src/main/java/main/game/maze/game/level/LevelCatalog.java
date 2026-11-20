package main.game.maze.game.level;

import java.util.List;

/** The levels of the game in play order, loaded from the levels model ({@link LevelCatalogLoader}). */
public record LevelCatalog(List<LevelSpec> levels) {

    public LevelCatalog {
        levels = List.copyOf(levels);
    }

    public int count() {
        return levels.size();
    }

    /** The level with this 1-based number. */
    public LevelSpec level(int number) {
        if (number < 1 || number > levels.size()) {
            throw new IllegalArgumentException("no level " + number + " (1.." + levels.size() + ")");
        }
        return levels.get(number - 1);
    }

    /** Whether a level follows the level with this number. */
    public boolean hasNext(int number) {
        return number >= 1 && number < levels.size();
    }
}
