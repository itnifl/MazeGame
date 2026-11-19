package main.game.maze.characters.interfaces;

import main.game.maze.common.remains.DeathCause;
import main.game.maze.interfaces.IDeathSubscriber;

/**
 * Contract for anything that has hit points and can die. Shared by both frontends: JavaFX
 * characters ({@code ZombieCharacter}, {@code PumpkinBomberCharacter}, the player) and libGDX
 * enemies ({@code GdxEnemyRuntime}) implement it, so damage, deaths and death causes go through
 * the same calls everywhere (see {@code MortalityService}).
 */
public interface ICanDie {
    public int getHitPoints();
    public void setHitPoints(int hp);
    public void subtractHitPoints(int hp);

    /**
     * Applies damage and records how it was dealt, so a killing blow leaves the right remains
     * (coal for fire, a bloody body for a shot, a splat when crushed). Implementations that do not
     * track causes just apply the damage.
     */
    default void subtractHitPoints(int hp, DeathCause cause) {
        subtractHitPoints(hp);
    }

    public void addHitPoints(int hp);
    public void addDeathNotificationSubscriber(IDeathSubscriber subscriber);

    /**
     * Whether damage is currently ignored (e.g. the brief protection right after a respawn).
     * Callers that share out a damage budget skip such targets so the damage is not wasted.
     */
    default boolean isInvulnerable() {
        return false;
    }

    /** Whether this still has hit points left. */
    default boolean isAlive() {
        return getHitPoints() > 0;
    }

    /**
     * How the last death happened ({@link DeathCause#OTHER} while alive, or when the
     * implementation does not track causes).
     */
    default DeathCause deathCause() {
        return DeathCause.OTHER;
    }
}
