package main.game.maze.characters;

import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import main.game.maze.actions.MovementNotifierAction;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.characters.interfaces.ICanKill;
import main.game.maze.characters.interfaces.ICanLetYouWin;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;
import main.game.maze.characters.interfaces.ICharacterAction;
import main.game.maze.characters.interfaces.ICharacterAnimations;
import main.game.maze.constants.StageConstants;
import main.game.maze.interfaces.IDeathSubscriber;

public class PlayerCharacter extends Character
        implements ICharacterAnimations, ICanDie, ICanSubscribeAndNotifyPosition {

    private AtomicInteger hitPoints = new AtomicInteger(100);
    private static final Object lockObjectForHpbar = new Object();
    private List<IDeathSubscriber> deathSubscribers = new ArrayList<IDeathSubscriber>();
    private List<ICanSubscribeAndNotifyPosition> touchKillers = new ArrayList<ICanSubscribeAndNotifyPosition>();
    private ProgressBar hpBar;
    private MediaPlayer screamMediaPlayer;
    public boolean isWinning = false;

    public PlayerCharacter(Node characterGraphics, double x, double y, ProgressBar hpBar) {
        super(characterGraphics, x, y);
        this.characterXYSizeFromPoint = StageConstants.PlayerCharacterXYSize;
        calculateMaxPositions();
        this.notifyMovement = new MovementNotifierAction(characterGraphics, this);
        this.hpBar = hpBar;
    }

    private MediaView addScreamSound() {

        var resource = getClass().getResource("/main/game/maze/playerScream.mp3");
        Media media = new Media(resource.toString());
        this.screamMediaPlayer = new MediaPlayer(media);

        // Create a MediaView and add it to the root node
        MediaView mediaView = new MediaView(screamMediaPlayer);

        return mediaView;

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
    public int getHitPoints() {
        return hitPoints.get();
    }

    @Override
    public void setHitPoints(int hp) {
        hitPoints = new AtomicInteger(hp);
        synchronized (lockObjectForHpbar) {
            if (hpBar != null)
                hpBar.setProgress(hitPoints.get() / 100.0);
        }
    }

    @Override
    public void subtractHitPoints(int hp) {
        hitPoints.addAndGet(-hp);

        var characterGraphics = this.getCharacterGraphics();
        if (characterGraphics != null && hpBar != null) {
            flashRed((ImageView) characterGraphics);
        }

        synchronized (lockObjectForHpbar) {
            if (hpBar != null)
                hpBar.setProgress(hitPoints.get() / 100.0);
        }

        if (hpBar != null) {
            addScreamSound();
            screamMediaPlayer.play();
        }

        if (hitPoints.get() <= 0) {
            PlayDieAnimation();
            for (var subscribers : deathSubscribers) {
                subscribers.AddDeathNotification(this);
            }
        }
    }

    @Override
    public void addHitPoints(int hp) {
        hitPoints.addAndGet(hp);
        synchronized (lockObjectForHpbar) {
            if (hpBar != null)
                hpBar.setProgress(hitPoints.get() / 100.0);
        }
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
        if (nodeBounds.intersects(this.getCharacterGraphics().getBoundsInParent())) {
            if (entity instanceof ICanKill) {
                var canKillEntity = (ICanKill) entity;
                System.out.println("Player is intersecting with " + canKillEntity);
                this.subtractHitPoints(canKillEntity.getDamage());

            }

            if (entity instanceof ICanLetYouWin) {
                try {
                    this.isWinning = true;
                    ((ICanLetYouWin) entity).WinGame();
                } catch (Exception ex) {

                }
            }
        }
    }

    @Override
    public List<ICanSubscribeAndNotifyPosition> getPositionSubscribers() {
        return touchKillers;
    }

    private void flashRed(ImageView imageView) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.2);
        colorAdjust.setSaturation(1.0);
        colorAdjust.setContrast(1.0);
        colorAdjust.setHue(-1.0);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(colorAdjust.hueProperty(), -1.0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(colorAdjust.hueProperty(), 0.5)),
                new KeyFrame(Duration.seconds(1.0), new KeyValue(colorAdjust.hueProperty(), -1.0)));

        timeline.setOnFinished(event -> {
            imageView.setEffect(null);
        });

        imageView.setEffect(colorAdjust);
        timeline.play();
    }
}
