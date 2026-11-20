package main.game.maze.game.score;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.common.remains.DeathCause;

/**
 * Who killed what in the current game, kept per player for scores (and, later, multiplayer
 * standings). A kill is credited to the attacker of the killing blow ({@link ICanDie#killerId()});
 * deaths without an attacker (enemy projectiles, debug commands) credit nobody. Thread-safe.
 */
public final class KillLedger {

    /** One credited kill. */
    public record Kill(String killerId, String victimTypeId, DeathCause cause, int points) {
    }

    private final List<Kill> kills = new CopyOnWriteArrayList<>();

    /**
     * Credits a dead enemy's kill to its killer.
     *
     * @param victimTypeId the enemy's model id (e.g. {@code zombie_weak_1})
     * @param points       from {@link KillScoring}
     * @return the recorded kill, or empty when nobody killed it
     */
    public Optional<Kill> record(ICanDie victim, String victimTypeId, int points) {
        if (victim == null || victim.isAlive() || victim.killerId() == null) {
            return Optional.empty();
        }
        Kill kill = new Kill(victim.killerId(), victimTypeId, victim.deathCause(), Math.max(0, points));
        kills.add(kill);
        return Optional.of(kill);
    }

    public int pointsFor(String playerId) {
        int total = 0;
        for (Kill kill : kills) {
            if (kill.killerId().equals(playerId)) {
                total += kill.points();
            }
        }
        return total;
    }

    public List<Kill> killsBy(String playerId) {
        List<Kill> result = new ArrayList<>();
        for (Kill kill : kills) {
            if (kill.killerId().equals(playerId)) {
                result.add(kill);
            }
        }
        return result;
    }

    public List<Kill> kills() {
        return List.copyOf(kills);
    }

    /** New game. */
    public void clear() {
        kills.clear();
    }
}
