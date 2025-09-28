package main.game.maze.characters.interfaces;

import main.game.maze.Vector2D.VectorFacing;

public interface ISubscribeOnDirection {
    void notifyCurrentDirection(VectorFacing direction);
}
