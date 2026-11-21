package main.game.maze.game.level;

import java.util.Optional;
import main.game.maze.game.items.ArsenalSnapshot;
import main.game.maze.game.items.PlayerArsenal;

/**
 * Which level the player is on and what they carry into it, shared by both frontends.
 *
 * <p>Completing a level ({@link #completeLevel}) saves a <b>savepoint</b>: the next level and the weapons the
 * player takes into it. {@link #advance} then enters that level exactly as saved. The savepoint is kept (in the
 * {@link SavePointStore}) until the player starts a new game ({@link #restart}). {@link #restartLevel} replays the
 * current level exactly as it was entered the first time; level 1 has no savepoint to restore from, so restarting it
 * starts a new game. Every call to {@link #inheritedArsenal()} gives a fresh copy, so playing a level (or retrying
 * it after losing) never changes what it was entered with.
 */
public final class LevelProgress {

    private final LevelCatalog catalog;
    private final SavePointStore store;
    private int number = 1;
    private ArsenalSnapshot inherited;
    /** Saved when the current level was completed; becomes the entry state of the next level. */
    private SavePoint pending;

    public LevelProgress(LevelCatalog catalog) {
        this(catalog, new InMemorySavePointStore());
    }

    public LevelProgress(LevelCatalog catalog, SavePointStore store) {
        this.catalog = catalog;
        this.store = store;
    }

    public synchronized int number() {
        return number;
    }

    public synchronized LevelSpec current() {
        return catalog.level(number);
    }

    public synchronized boolean hasNext() {
        return catalog.hasNext(number);
    }

    /**
     * The current level has been completed: the savepoint for the next one is made, with the arsenal as it is now.
     * Does nothing on the last level.
     */
    public synchronized void completeLevel(PlayerArsenal arsenalAtTheEnd) {
        if (!catalog.hasNext(number)) {
            return;
        }
        int next = number + 1;
        ArsenalSnapshot carried = catalog.level(next).inheritWeapons() ? arsenalAtTheEnd.snapshot() : null;
        pending = new SavePoint(next, carried);
        store.save(pending);
    }

    /**
     * Moves to the next level. It is entered from the savepoint made when this level was completed; without one
     * (a level advanced without being completed) from the arsenal given.
     */
    public synchronized void advance(PlayerArsenal arsenalAtTheEnd) {
        if (!catalog.hasNext(number)) {
            throw new IllegalStateException("level " + number + " is the last level");
        }
        if (pending == null || pending.level() != number + 1) {
            completeLevel(arsenalAtTheEnd);
        }
        number = pending.level();
        // a level can choose to start from scratch instead of keeping the weapons
        inherited = pending.arsenal();
        pending = null;
    }

    /** The weapons the current level starts with, or null on level 1 (the difficulty's starting bombs apply). */
    public synchronized PlayerArsenal inheritedArsenal() {
        return inherited == null ? null : PlayerArsenal.restore(inherited);
    }

    /** New game: level 1, nothing carried, and the savepoint is gone. */
    public synchronized void restart() {
        number = 1;
        inherited = null;
        pending = null;
        store.clear();
    }

    /**
     * Restarts the current level exactly as it was entered the first time: from its savepoint. Level 1 has no
     * savepoint, so restarting it is a new game.
     */
    public synchronized void restartLevel() {
        if (number == 1) {
            restart();
        }
        // a later level keeps its entry state (the savepoint); nothing about the progress changes
    }

    /** The savepoint that is kept now, if any (the latest level completed). */
    public synchronized Optional<SavePoint> savePoint() {
        return store.load();
    }

    /**
     * Picks the game up from the kept savepoint: the level it is for, entered with the weapons saved.
     *
     * @return false when there is no savepoint
     */
    public synchronized boolean resumeFromSavePoint() {
        Optional<SavePoint> saved = store.load();
        if (saved.isEmpty() || saved.get().level() > catalog.count()) {
            return false;
        }
        number = saved.get().level();
        inherited = saved.get().arsenal();
        pending = null;
        return true;
    }
}
