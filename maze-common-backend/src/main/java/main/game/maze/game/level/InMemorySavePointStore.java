package main.game.maze.game.level;

import java.util.Optional;

/** A savepoint kept only while the game runs (tests, and as the store when none is given). */
public final class InMemorySavePointStore implements SavePointStore {

    private SavePoint savePoint;

    @Override
    public synchronized void save(SavePoint savePoint) {
        this.savePoint = savePoint;
    }

    @Override
    public synchronized Optional<SavePoint> load() {
        return Optional.ofNullable(savePoint);
    }

    @Override
    public synchronized void clear() {
        savePoint = null;
    }
}
