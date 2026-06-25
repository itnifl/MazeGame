package main.game.maze.mazeworld.flame;

/**
 * Abstraction over anything the directional flame explosion can hit.
 *
 * <p>Implementations are created by each frontend as thin adapters over their
 * own enemy/player types.  The engine never references frontend-specific classes,
 * keeping the algorithm in a single shared location.</p>
 *
 * <p>Pass-through targets (e.g. the player) receive the remaining damage budget
 * at their position but neither consume the budget nor stop the flame from
 * continuing past them.</p>
 */
public interface FlameTarget {

    /** World-pixel X of the target's centre. */
    double centerX();

    /** World-pixel Y of the target's centre. */
    double centerY();

    /**
     * Current hit-points.  Queried once per damage application; must return the
     * live value (not a snapshot from target-list construction time).
     */
    int hitPoints();

    /**
     * Applies {@code amount} damage to this target.
     * For pass-through targets the call should deliver the damage to the
     * underlying entity (e.g. invoke an {@code IntConsumer} callback) without
     * deducting from the flame's remaining budget.
     */
    void applyDamage(int amount);

    /**
     * {@code true} if this target does not block or consume the flame budget.
     * The player is the canonical pass-through target: they receive the full
     * remaining budget as damage but the flame continues past them.
     */
    boolean isPassThrough();
}
