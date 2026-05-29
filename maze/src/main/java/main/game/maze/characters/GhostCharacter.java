package main.game.maze.characters;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import main.game.maze.actions.MovementNotifierAction;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.characters.interfaces.ICanKill;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;
import main.game.maze.characters.interfaces.ICharacterAction;
import main.game.maze.characters.interfaces.ICharacterAnimations;
import main.game.maze.characters.interfaces.IHaveModel;
import main.game.maze.characters.interfaces.INonTangientMazeGameCharacter;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.opponents.Ghost;

public class GhostCharacter extends ComputerCharacter
        implements ICanKill, ICharacterAnimations, ICanSubscribeAndNotifyPosition, INonTangientMazeGameCharacter,  IHaveModel<Ghost> {
    private static final Logger LOGGER = Logger.getLogger(GhostCharacter.class.getName());
    private final Ghost ghostModel;

    private List<ICanSubscribeAndNotifyPosition> touchTargets = new ArrayList<>();



    public GhostCharacter(Node characterGraphics, double positionX, double positionY, Ghost model) {
        super(characterGraphics, model, positionX, positionY, mapSpeed(model.getSpeed()));        
        this.ghostModel = model;

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
        var graphics = this.getCharacterGraphics();
        if (nodeBounds == null || graphics == null) {
            return;
        }
        if (nodeBounds.intersects(graphics.getBoundsInParent())) {
            if (entity instanceof ICanDie) {
                var canDieEntity = (ICanDie) entity;
                LOGGER.fine("Ghost is intersecting with " + canDieEntity);
                canDieEntity.subtractHitPoints(getDamage());
            }
        }
    }

    @Override
    public void addPositionSubscriber(ICanSubscribeAndNotifyPosition touchEntity) {
        touchTargets.add(touchEntity);
    }

        @Override
    public void removePositionSubscriber(ICanSubscribeAndNotifyPosition touchEntity) {
        touchTargets.remove(touchEntity);
    }

    @Override
    public List<ICanSubscribeAndNotifyPosition> getPositionSubscribers() {
        return touchTargets;
    }

    @Override
    public int getDamage() {
        return CollisionDamage.effectiveDamage(ghostModel.getThreatLevel(), ghostModel.getAttackDamage());
    }

    private static int mapSpeed(double modelSpeed) {
        return Math.max(1, (int)Math.round(modelSpeed));
    }

    @Override
    public double getNonTangientEnergy() {
        return ghostModel.getNonTangibilityEnergy();
    }
    
    @Override
    public void setNonTangientEnergy(double value) {
        ghostModel.setNonTangibilityEnergy(value);
    }

    @Override
    public void setCharacterOpacity(double value) {
        var graphics = this.getCharacterGraphics();
        graphics.setOpacity(value);
        this.setCharacterGraphics(graphics);
    }

    @Override
    public Ghost getModel() {
        return this.ghostModel;
    }
}
