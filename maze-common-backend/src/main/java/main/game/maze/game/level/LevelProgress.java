package main.game.maze.game.level;

import main.game.maze.game.items.ArsenalSnapshot;
import main.game.maze.game.items.PlayerArsenal;

/**
 * Which level the player is on and what they carry into it, shared by both frontends. Winning a level
 * calls {@link #advance}, which hands the weapons on to the next one; a new game calls {@link #restart}.
 * Every call to {@link #inheritedArsenal()} gives a fresh copy, so playing a level (or retrying it after
 * losing) never changes what it was entered with.
 */
public final class LevelProgress {

    private final LevelCatalog catalog;
    private int number = 1;
    private ArsenalSnapshot inherited;

    public LevelProgress(LevelCatalog catalog) {
        this.catalog = catalog;
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

    /** Level won: moves to the next level, carrying the arsenal as it is now. */
    public synchronized void advance(PlayerArsenal arsenalAtTheEnd) {
        if (!catalog.hasNext(number)) {
            throw new IllegalStateException("level " + number + " is the last level");
        }
        inherited = arsenalAtTheEnd.snapshot();
        number++;
    }

    /** The weapons the current level starts with, or null on level 1 (the difficulty's starting bombs apply). */
    public synchronized PlayerArsenal inheritedArsenal() {
        return inherited == null ? null : PlayerArsenal.restore(inherited);
    }

    /** New game: level 1, nothing carried. */
    public synchronized void restart() {
        number = 1;
        inherited = null;
    }
}
