package main.game.maze.game.runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

/**
 * Dead ranged enemies whose shots are still in the air. A killed shooter leaves the living
 * enemies (no movement, contact damage or new attacks) but its projectiles keep flying, hitting
 * and exploding until they land. Shared rule for both frontends; thread-safe.
 *
 * @param <T> the frontend's enemy type
 */
public final class RetiredShooters<T> {

    private final Predicate<? super T> hasShotsInFlight;
    private final List<T> retired = new CopyOnWriteArrayList<>();

    /** @param hasShotsInFlight whether a shooter still has projectiles (or their effects) active */
    public RetiredShooters(Predicate<? super T> hasShotsInFlight) {
        this.hasShotsInFlight = hasShotsInFlight;
    }

    /** Keeps a just-killed shooter around if it still has shots in flight. */
    public void retire(T shooter) {
        if (shooter != null && !retired.contains(shooter) && hasShotsInFlight.test(shooter)) {
            retired.add(shooter);
        }
    }

    /** Retired shooters whose shots are still in flight (finished ones are dropped). */
    public List<T> active() {
        retired.removeIf(shooter -> !hasShotsInFlight.test(shooter));
        return new ArrayList<>(retired);
    }

    /** Drops every retired shooter (new level). */
    public void clear() {
        retired.clear();
    }
}
