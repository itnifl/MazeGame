package main.game.maze.actions;

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
        for(var listener : entity.getPositionSubscribers() ) {
            listener.doPositionEvaluation(this.characterGraphics.getBoundsInParent(), this.entity);
        }
    }
    
}
