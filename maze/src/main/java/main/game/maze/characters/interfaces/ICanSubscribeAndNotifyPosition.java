package main.game.maze.characters.interfaces;

import java.util.List;

import javafx.geometry.Bounds;

public interface ICanSubscribeAndNotifyPosition {
    public void doPositionEvaluation(Bounds nodeBounds, ICanSubscribeAndNotifyPosition mortalEntity);
    public void addPositionSubscriber(ICanSubscribeAndNotifyPosition entity);
    public List<ICanSubscribeAndNotifyPosition> getPositionSubscribers();
}
