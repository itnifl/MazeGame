package main.game.maze.characters.interfaces;

import main.game.maze.interfaces.IDeathSubscriber;

public interface ICanDie {
    public int getHitPoints();
    public void setHitPoints(int hp);
    public void subtractHitPoints(int hp);
    public void addHitPoints(int hp);
    public void addDeathNotificationSubscriber(IDeathSubscriber subscriber);
}
