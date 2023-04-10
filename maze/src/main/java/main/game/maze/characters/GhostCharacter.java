package main.game.maze.characters;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import main.game.maze.actions.MovementNotifierAction;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.characters.interfaces.ICanKill;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;
import main.game.maze.characters.interfaces.ICharacterAction;
import main.game.maze.characters.interfaces.ICharacterAnimations;
import main.game.maze.constants.StageConstants;

public class GhostCharacter extends ComputerCharacter
        implements ICanKill, ICharacterAnimations, ICanSubscribeAndNotifyPosition {
    private static int ghostSpeedFactor = 2;
    private static int damageByTouch = 1;

    private List<ICanSubscribeAndNotifyPosition> touchTargets = new ArrayList<ICanSubscribeAndNotifyPosition>();

    public GhostCharacter(Node characterGraphics, double positionX, double positionY) {
        super(characterGraphics, positionX, positionX, ghostSpeedFactor);
        this.characterXYSizeFromPoint = StageConstants.GhostCharacterXYSize;
        calculateMaxPositions();
        this.notifyMovement = new MovementNotifierAction(characterGraphics, this);
    }

    @Override
    public void PlayHappyAnimation() {
        super.doCharacterAnimation(new HappyAction());
    }

    @Override
    public void PlayDieAnimation() {
        super.doCharacterAnimation(new DieAction());
    }

    private class HappyAction implements ICharacterAction {
        public void doAction(Node characterGraphics) {
            // Animate the character and do stuff
        }
    }

    private class DieAction implements ICharacterAction {
        public void doAction(Node characterGraphics) {
            // Animate the character and do stuff
        }
    }

    @Override
    public void doPositionEvaluation(Bounds nodeBounds, ICanSubscribeAndNotifyPosition entity) {
        if (nodeBounds.intersects(this.getCharacterGraphics().getBoundsInParent())) {
            if (entity instanceof ICanDie) {
                var canDieEntity = (ICanDie) entity;
                System.out.println("Ghost is intersecting with " + canDieEntity);
                canDieEntity.subtractHitPoints(damageByTouch);
            }
        }
    }

    @Override
    public void addPositionSubscriber(ICanSubscribeAndNotifyPosition touchEntity) {
        touchTargets.add(touchEntity);
    }

    @Override
    public List<ICanSubscribeAndNotifyPosition> getPositionSubscribers() {
        return touchTargets;
    }

    @Override
    public int getDamage() {
        return damageByTouch;
    }
}
