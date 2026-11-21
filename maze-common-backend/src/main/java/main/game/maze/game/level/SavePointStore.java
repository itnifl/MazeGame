package main.game.maze.game.level;

import java.util.Optional;

/** Where the savepoint lives between games: kept until the player starts a new game. */
public interface SavePointStore {

    void save(SavePoint savePoint);

    Optional<SavePoint> load();

    /** A new game: no savepoint. */
    void clear();
}
