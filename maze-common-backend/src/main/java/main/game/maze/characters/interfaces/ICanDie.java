package main.game.maze.characters.interfaces;

import main.game.maze.common.remains.DeathCause;
import main.game.maze.interfaces.IDeathSubscriber;

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
}
