package main.game.maze.characters;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import main.game.maze.App;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.characters.interfaces.ICanKill;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;
import main.game.maze.characters.interfaces.ICharacterAction;
import main.game.maze.characters.interfaces.ICharacterAnimations;
import main.game.maze.characters.interfaces.IHaveModel;
import main.game.maze.opponents.Zombie;                // generated EMF class
import main.game.maze.opponents.BehaviorType;         // generated enum
import main.game.maze.actions.MovementNotifierAction;
import main.game.maze.constants.StageConstants;
import main.game.maze.interfaces.IDeathSubscriber;

public class ZombieCharacter extends ComputerCharacter
        implements ICanKill, ICharacterAnimations, ICanSubscribeAndNotifyPosition, ICanDie, IHaveModel<Zombie> {

    private final Zombie zombieModel;
    private AtomicInteger hitPoints;
    private MediaPlayer screamMediaPlayer;    

    private List<IDeathSubscriber> deathSubscribers = new ArrayList<>();
    private List<ICanSubscribeAndNotifyPosition> touchTargets = new ArrayList<>();
    

    public ZombieCharacter(Node characterGraphics, double x, double y, Zombie model) {
        super(characterGraphics, model, x, y, mapSpeed(model.getSpeed()));
        this.zombieModel = model;
        this.hitPoints = new AtomicInteger(Math.max(1, model.getHealth()));
        this.characterXYSizeFromPoint = StageConstants.ZombieCharacterXYSize;
        calculateMaxPositions();

        // movement notifier (same pattern as Ghost)
        this.notifyMovement = new MovementNotifierAction(characterGraphics, this);
        
        // set initial direction or behaviour
        if (model.getBehavior() == BehaviorType.WANDER) {
            // leave default random motion, or set to a wandering AI
        }
    }

    private static int mapSpeed(double modelSpeed) {
        return Math.max(1, (int)Math.round(modelSpeed));
    }

    private class HappyAction implements ICharacterAction {
        public void doAction(Node characterGraphics) { /* animate */ }
    }

    private class DieAction implements ICharacterAction {
        public void doAction(Node characterGraphics) { /* animate and remove node */ }
    }

    @Override
    public int getDamage() {
        return Math.max(0, zombieModel.getAttackDamage());
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
    public void subtractHitPoints(int hp) {
        hitPoints.addAndGet(-hp);

        var characterGraphics = this.getCharacterGraphics();
        if (characterGraphics != null) {
            //TODO: Do things with graphics when character is hurt;
        }

        if (hitPoints.get() <= 0) {
            PlayDieAnimation();
            for (var subscribers : deathSubscribers) {
                subscribers.AddDeathNotification(this);
            }
            App.gameController.unregisterComputerCharacter(this, characterGraphics);
        }
    }


    @Override
    public void doPositionEvaluation(Bounds nodeBounds, ICanSubscribeAndNotifyPosition entity) {
        if (nodeBounds.intersects(this.getCharacterGraphics().getBoundsInParent())) {
            if (entity instanceof ICanDie) {
                var canDieEntity = (ICanDie) entity;
                System.out.println("Zombie is intersecting with " + canDieEntity);
                canDieEntity.subtractHitPoints(getDamage());                
            }
            if(screamMediaPlayer == null || screamMediaPlayer.getStatus() != Status.PLAYING) {
                screamMediaPlayer = playScream();
                screamMediaPlayer.play();
            }
        }        
    }

    private MediaPlayer playScream() {
        var soundFile = zombieModel.getTouchSound();
        var resource = getClass().getResource(soundFile);
        Media media = new Media(resource.toString());
        return new MediaPlayer(media);
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
    public int getHitPoints() {
       return this.hitPoints.get();
    }

    @Override
    public void setHitPoints(int hp) {
        hitPoints = new AtomicInteger(hp);
    }

    @Override
    public void addHitPoints(int hp) {
        hitPoints.addAndGet(hp);
    }

    @Override
    public void addDeathNotificationSubscriber(IDeathSubscriber subscriber) {
        deathSubscribers.add(subscriber);
    }

    public Zombie getModel() {
        return this.zombieModel;
    }
}
