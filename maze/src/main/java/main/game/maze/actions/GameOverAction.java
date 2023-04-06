package main.game.maze.actions;

import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.interfaces.IDeathSubscriber;

public class GameOverAction implements IDeathSubscriber {

    @Override
    public void AddDeathNotification(ICanDie mortalEntity) {
        // TODO create this, end game at death
        
    }
    
}
