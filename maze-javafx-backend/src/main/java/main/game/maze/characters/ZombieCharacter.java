package main.game.maze.characters;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.util.Duration;
import main.game.maze.App;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.characters.interfaces.ICanKill;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;
import main.game.maze.characters.interfaces.ICharacterAction;
import main.game.maze.characters.interfaces.PositionBounds;
import main.game.maze.characters.interfaces.ICharacterAnimations;
import main.game.maze.characters.interfaces.IHaveModel;
import main.game.maze.opponents.Zombie;                // generated EMF class
import main.game.maze.opponents.BehaviorType;         // generated enum
import main.game.maze.actions.MovementNotifierAction;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.common.graphics.AudioEngine;

public class ZombieCharacter extends ComputerCharacter
        implements ICanKill, ICharacterAnimations, ICanSubscribeAndNotifyPosition, ICanDie, IHaveModel<Zombie> {

    private static final Logger LOGGER = Logger.getLogger(ZombieCharacter.class.getName());
    private final Zombie zombieModel;
    private static final long ZOMBIE_SCREAM_COOLDOWN_MS = 600L;


    private List<ICanSubscribeAndNotifyPosition> touchTargets = new ArrayList<>();


    public ZombieCharacter(Node characterGraphics, double x, double y, Zombie model) {
        super(characterGraphics, model, x, y, mapSpeed(model.getSpeed()));
        this.zombieModel = model;
        this.characterXYSizeFromPoint = StageConstants.ZombieCharacterXYSize;
        calculateMaxPositions();

        // movement notifier (same pattern as Ghost)
        this.notifyMovement = new MovementNotifierAction(characterGraphics, this);
        
        // set initial direction or behaviour
        if (model.getBehavior() == BehaviorType.WANDER) {
            // leave default random motion, or set to a wandering AI
        }
    }

        private static double mapSpeed(double modelSpeed) {
        return Math.max(1.0, modelSpeed);
    }


    private class HappyAction implements ICharacterAction {
        public void doAction(Object characterGraphics) { /* animate */ }
    }

    private class DieAction implements ICharacterAction {
        private static final int HURT_FRAME_COUNT = 5;
        private static final double FRAME_DURATION_MS = 100.0;
        private static final String HURT_SPRITE_FORMAT = "/main/game/maze/zombie-hurt%d.png";

        public void doAction(Object characterGraphics) {
            Timeline timeline = new Timeline();
            for (int i = 1; i <= HURT_FRAME_COUNT; i++) {
                final int frameNum = i;
                KeyFrame frame = new KeyFrame(
                        Duration.millis((frameNum - 1) * FRAME_DURATION_MS),
                        e -> {
                            var stream = ZombieCharacter.class.getResourceAsStream(
                                    String.format(HURT_SPRITE_FORMAT, frameNum));
                            if (stream != null) {
                                setCharacterImage(new Image(stream));
                            }
                        });
                timeline.getKeyFrames().add(frame);
            }
            timeline.play();
        }
    }

    @Override
    public int getDamage() {
        return CollisionDamage.effectiveDamage(zombieModel.getThreatLevel(), zombieModel.getAttackDamage());
    }

    @Override
    public void PlayHappyAnimation() {
        super.doCharacterAnimation(new HappyAction());
    }

    @Override
    public void PlayDieAnimation() {
        super.doCharacterAnimation(new DieAction());
    }

    @Override
    public void doPositionEvaluation(PositionBounds nodeBounds, ICanSubscribeAndNotifyPosition entity) {
        var graphics = this.getCharacterGraphics();
        if (nodeBounds == null || graphics == null) {
            return;
        }
        var myBounds = new FxPositionBounds(graphics.getBoundsInParent());
        if (!nodeBounds.intersects(myBounds)) {
            return;
        }
        // A wall between the zombie and the target blocks damage.
        if (App.gameController != null
                && App.gameController.isWallBetween(
                        myBounds.getCenterX(), myBounds.getCenterY(),
                        nodeBounds.getCenterX(), nodeBounds.getCenterY())) {
            return;
        }
        if (entity instanceof ICanDie) {
            var canDieEntity = (ICanDie) entity;
            LOGGER.fine("Zombie is intersecting with " + canDieEntity);
            canDieEntity.subtractHitPoints(getDamage());                
        }
        AudioEngine.get().playRateLimited(
            zombieModel.getTouchSound(),
            "zombie.touch." + System.identityHashCode(this),
            ZOMBIE_SCREAM_COOLDOWN_MS);
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

    public Zombie getModel() {
        return this.zombieModel;
    }
}
