package main.game.maze.game.strike;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The blasts that are on their way (grenades, fat bombs, the bombs of an airstrike, ...), shared by both frontends. Each
 * frame the frontend calls {@link #tick} and sets off the blasts it returns. Not thread-safe: use it from the game loop.
 */
public final class BlastSchedule {

    private final List<ScheduledBlast> pending = new ArrayList<>();

    public void add(ScheduledBlast blast) {
        if (blast != null) {
            pending.add(blast);
        }
    }

    public void addAll(Collection<ScheduledBlast> blasts) {
        for (ScheduledBlast blast : blasts) {
            add(blast);
        }
    }

    /**
     * Advances every fuse by {@code dt} seconds.
     *
     * @return the blasts that go off now, in the order they were added
     */
    public List<ScheduledBlast> tick(double dt) {
        List<ScheduledBlast> due = new ArrayList<>();
        double step = Math.max(0.0, dt);
        for (int i = 0; i < pending.size(); i++) {
            ScheduledBlast later = pending.get(i).after(step);
            if (later.secondsLeft() <= 0) {
                due.add(later);
                pending.remove(i--);
            } else {
                pending.set(i, later);
            }
        }
        return due;
    }

    /** The blasts still on their way (for drawing the fuses). */
    public List<ScheduledBlast> pending() {
        return List.copyOf(pending);
    }

    public boolean isEmpty() {
        return pending.isEmpty();
    }

    /** New level, death or teardown: nothing goes off any more. */
    public void clear() {
        pending.clear();
    }
}
