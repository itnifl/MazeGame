package main.game.maze.common.remains;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Remains on the floor, one per dead enemy, kept until that enemy respawns ({@link #remove})
 * or the level ends ({@link #clear}). Owners are matched by identity, so two enemies with equal
 * spawn data still keep separate remains. Capped: the oldest remains go when the cap is passed.
 * Not thread-safe; use from the render / UI thread.
 *
 * @param <K> owner (the enemy or its spawn)
 * @param <R> remains (a view or a visual record)
 */
public final class RemainsLedger<K, R> {

    /** Default cap: plenty for a level, small enough to draw every frame. */
    public static final int DEFAULT_CAPACITY = 60;

    private record Entry<K, R>(K owner, R remains) {
    }

    private final int capacity;
    private final List<Entry<K, R>> entries = new ArrayList<>();

    public RemainsLedger(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive, was " + capacity);
        }
        this.capacity = capacity;
    }

    /**
     * Records remains for {@code owner}, replacing any it already had.
     *
     * @return remains dropped to stay under the cap (the caller may need to remove a view), or null
     */
    public R put(K owner, R remains) {
        removeEntry(owner);
        entries.add(new Entry<>(owner, remains));
        if (entries.size() > capacity) {
            return entries.remove(0).remains();
        }
        return null;
    }

    /** Removes and returns the remains of {@code owner} (e.g. when it respawns), or null. */
    public R remove(K owner) {
        Entry<K, R> removed = removeEntry(owner);
        return removed != null ? removed.remains() : null;
    }

    /** Remains in the order they were left, oldest first. */
    public List<R> values() {
        List<R> values = new ArrayList<>(entries.size());
        for (Entry<K, R> entry : entries) {
            values.add(entry.remains());
        }
        return values;
    }

    public void clear() {
        entries.clear();
    }

    private Entry<K, R> removeEntry(K owner) {
        for (Iterator<Entry<K, R>> it = entries.iterator(); it.hasNext(); ) {
            Entry<K, R> entry = it.next();
            if (entry.owner() == owner) {
                it.remove();
                return entry;
            }
        }
        return null;
    }
}
