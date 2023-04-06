package main.game.maze.interfaces;

import main.game.maze.characters.interfaces.ICanDie;

public interface IDeathSubscriber {
    public void AddDeathNotification(ICanDie mortalEntity);
}
