package main.game.maze.actions;

import java.util.ArrayList;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;
import main.game.maze.interfaces.INotifyMovement;

public class MovementNotifierAction implements INotifyMovement {
    private Node characterGraphics;
    private ICanSubscribeAndNotifyPosition entity;

    public MovementNotifierAction(Node characterGraphics, ICanSubscribeAndNotifyPosition entity) {
        this.characterGraphics = characterGraphics;
        this.entity = entity;
    }

    @Override
    public void doNotifyCharacterMovement() {
        if (entity == null || characterGraphics == null) {
            return;
        }

        Bounds bounds = characterGraphics.getBoundsInParent();
        if (bounds == null) {
            return;
        }

        var subscribers = entity.getPositionSubscribers();
        if (subscribers == null || subscribers.isEmpty()) {
            return;
        }

        // Iterate over a snapshot so listeners can subscribe/unsubscribe during callbacks.
        var snapshot = new ArrayList<>(subscribers);
        for (var listener : snapshot) {
            if (listener == null) {
                continue;
            }
            listener.doPositionEvaluation(bounds, this.entity);
        }
    }
    
}


