package main.game.maze.characters.interfaces;

import main.game.maze.mazeworld.Point2D;

public interface IMovingComputerCharacter {
    public boolean move(boolean force);
    public void changeDirection();
    void setDirection(Point2D direction);
}
