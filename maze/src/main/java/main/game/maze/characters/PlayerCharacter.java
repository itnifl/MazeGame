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
import main.game.maze.interfaces.IDeathSubscriber;

public class PlayerCharacter extends Character implements ICharacterAnimations, ICanDie, ICanSubscribeAndNotifyPosition {
    private int hitPoints = 30;
    private List<IDeathSubscriber> deathSubscribers = new ArrayList<IDeathSubscriber>();
    private List<ICanSubscribeAndNotifyPosition> touchKillers = new ArrayList<ICanSubscribeAndNotifyPosition>();

    public PlayerCharacter(Node characterGraphics, double x, double y) {
        super(characterGraphics, x, y);
        this.characterXYSizeFromPoint =  StageConstants.PlayerCharacterXYSize;
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
            //Animate the character and do stuff
        }
    }

    private class DieAction implements ICharacterAction {
        public void doAction(Node characterGraphics) {
            //Animate the character and do stuff
        }
    }

    @Override
    public int getHitPoints() {
        return hitPoints;
    }

    @Override
    public void setHitPoints(int hp) {
        hitPoints = hp;
    }

    @Override
    public void subtractHitPoints(int hp) {
        hitPoints -= hp;

        if(hitPoints <= 0) {
            PlayDieAnimation();
            for(var subscribers : deathSubscribers) {
                subscribers.AddDeathNotification(this);
            }
        }
    }

    @Override
    public void addHitPoints(int hp) {
        hitPoints += hp;
    }

    @Override
    public void addDeathNotificationSubscriber(IDeathSubscriber subscriber) {
        deathSubscribers.add(subscriber);
    }

    @Override
    public void addPositionSubscriber(ICanSubscribeAndNotifyPosition touchEntity) {
        touchKillers.add(touchEntity);
    }

    @Override
    public void doPositionEvaluation(Bounds nodeBounds, ICanSubscribeAndNotifyPosition entity) {
        if(nodeBounds.intersects(this.getCharacterGraphics().getBoundsInParent())) {
            if(entity instanceof ICanKill) {
                var canKillEntity = (ICanKill)entity;
                this.subtractHitPoints(canKillEntity.getDamage());
            }
        }
    }

    @Override
    public List<ICanSubscribeAndNotifyPosition> getPositionSubscribers() {
        return touchKillers;
    }
}
