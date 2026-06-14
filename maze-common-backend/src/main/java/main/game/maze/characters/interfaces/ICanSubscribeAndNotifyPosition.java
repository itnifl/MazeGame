package main.game.maze.characters.interfaces;

import java.util.List;

public interface ICanSubscribeAndNotifyPosition {
    void doPositionEvaluation(PositionBounds nodeBounds, ICanSubscribeAndNotifyPosition mortalEntity);
    void addPositionSubscriber(ICanSubscribeAndNotifyPosition entity);
    void removePositionSubscriber(ICanSubscribeAndNotifyPosition entity);
    List<ICanSubscribeAndNotifyPosition> getPositionSubscribers();
}
