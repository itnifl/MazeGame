package main.game.maze.characters.interfaces;

import main.game.maze.mazeworld.Vector2D.VectorFacing;

public interface ISubscribeOnDirection {
    void notifyCurrentDirection(VectorFacing direction);
}


