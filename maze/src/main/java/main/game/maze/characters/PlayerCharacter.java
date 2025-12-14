package main.game.maze.characters;

import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
import main.game.maze.App;
import main.game.maze.actions.MovementNotifierAction;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.characters.interfaces.ICanKill;
import main.game.maze.characters.interfaces.ICanLetYouWin;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;
import main.game.maze.characters.interfaces.ICharacterAction;
import main.game.maze.characters.interfaces.ICharacterAnimations;
import main.game.maze.constants.ColorHueConstants;
import main.game.maze.constants.ResourceFileConstants;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.interfaces.IDeathSubscriber;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class PlayerCharacter extends Character
        implements ICharacterAnimations, ICanDie, ICanSubscribeAndNotifyPosition {

    private AtomicInteger hitPoints = new AtomicInteger(100);
    private static final Object lockObjectForHpbar = new Object();
    private List<IDeathSubscriber> deathSubscribers = new ArrayList<>();
    private List<ICanSubscribeAndNotifyPosition> touchKillers = new ArrayList<>();
    private ProgressBar hpBar;
    public static MediaPlayer screamMediaPlayer;
    public static MediaPlayer infectedMediaPlayer;
    public boolean isWinning = false;
    private Timeline infectionTimeline = null;

    public PlayerCharacter(Node characterGraphics, double x, double y, ProgressBar hpBar) {
        super(characterGraphics, x, y);
        this.characterXYSizeFromPoint = StageConstants.PlayerCharacterXYSize;
        calculateMaxPositions();
        this.notifyMovement = new MovementNotifierAction(characterGraphics, this);
        this.hpBar = hpBar;
    }

    private MediaView addScreamSound() {

        var resource = getClass().getResource(ResourceFileConstants.PlayerScreamSound);
        Media media = new Media(resource.toString());
        this.screamMediaPlayer = new MediaPlayer(media);

        // Create a MediaView and add it to the root node
        return new MediaView(screamMediaPlayer);
    }

    private MediaView addInfectedSound() {
        var resource = getClass().getResource(ResourceFileConstants.PlayerInfectedSound);
        Media media = new Media(resource.toString());
        this.infectedMediaPlayer = new MediaPlayer(media);

        // Create a MediaView and add it to the root node
        return new MediaView(infectedMediaPlayer);
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

        synchronized (lockObjectForHpbar) {
            if (hpBar != null)
                hpBar.setProgress(hitPoints.get() / 100.0);
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
    public void removePositionSubscriber(ICanSubscribeAndNotifyPosition touchEntity) {
        touchKillers.remove(touchEntity);
    }

    @Override
    public void doPositionEvaluation(Bounds nodeBounds, ICanSubscribeAndNotifyPosition entity) {
        if (nodeBounds.intersects(this.getCharacterGraphics().getBoundsInParent())) {
            if (entity instanceof ICanKill) {
                var canKillEntity = (ICanKill) entity;
                System.out.println("Player is intersecting with " + canKillEntity);
                this.subtractHitPoints(canKillEntity.getDamage());
                this.flashCharacterColor((ImageView) this.getCharacterGraphics(), ColorHueConstants.RED_HUE);
                doStandardScreamSound();
            }
            if (entity instanceof ZombieCharacter zombieCharacter) {
                calculateInfection(zombieCharacter);
            }

            if (entity instanceof ICanLetYouWin) {
                try {
                    this.isWinning = true;
                    ((ICanLetYouWin) entity).WinGame();
                } catch (Exception ex) {
                    //Swallow exception
                }
            }
        }
    }

    @Override
    public List<ICanSubscribeAndNotifyPosition> getPositionSubscribers() {
        return touchKillers;
    }

    private void doStandardScreamSound() {
        if (hpBar != null) {
            addScreamSound();
            screamMediaPlayer.play();
        }
    }

    private void doInfectedScreamSound() {
        if (hpBar != null) {
            addInfectedSound();
            infectedMediaPlayer.play();
        }
    }

    private void flashCharacterColor(ImageView imageView, double colorHue) {
        if (imageView == null || hpBar == null) 
            return;

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.2);
        colorAdjust.setSaturation(1.0);
        colorAdjust.setContrast(1.0);
        colorAdjust.setHue(colorHue); 

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

    private void calculateInfection(ZombieCharacter z) {
        Random rng = new Random();
        var model = z.getModel();

        double calculatedInfectionLevel = model.getInfectionLevel() / 100.0;
        if (calculatedInfectionLevel <= 0) return;

        final double minimalInfectionChance = 0.5;
        final double maxInfectionChance = 0.9;
        final double infectionChance = Math.min(calculatedInfectionLevel + minimalInfectionChance, maxInfectionChance);

        if (rng.nextDouble() < infectionChance) {
            App.gameController.showInfectionWarning();
            
            double dps = 0.10 * z.getDamage() * calculatedInfectionLevel; // ticks each second for 6s
            final int totalTicks = 6;
            if(infectionTimeline == null || infectionTimeline.getStatus() != Timeline.Status.RUNNING) {
                    infectionTimeline= new Timeline(new KeyFrame(Duration.seconds(totalTicks), event -> {
                    this.subtractHitPoints((int)Math.round(dps));
                    this.flashCharacterColor((ImageView) this.getCharacterGraphics(), ColorHueConstants.GREEN_HUE);
                    this.doInfectedScreamSound();
                }));
                infectionTimeline.play();
            }

        }
    }

    public void dispose() {
        // stop infection tick
        if (infectionTimeline != null) {
            infectionTimeline.stop();
            infectionTimeline = null;
        }

        // stop & release sounds
        try {
            if (screamMediaPlayer != null) {
                screamMediaPlayer.stop();
                screamMediaPlayer.dispose();
                screamMediaPlayer = null;
            }
        } catch (Exception ignored) {}
        try {
            if (infectedMediaPlayer != null) {
                infectedMediaPlayer.stop();
                infectedMediaPlayer.dispose();
                infectedMediaPlayer = null;
            }
        } catch (Exception ignored) {}

        // clear any flash effect left on the sprite (defensive; base also clears)
        Node gfx = getCharacterGraphics();
        if (gfx != null) {
            if (javafx.application.Platform.isFxApplicationThread()) {
                gfx.setEffect(null);
            } else {
                CountDownLatch latch = new CountDownLatch(1);
                javafx.application.Platform.runLater(() -> {
                    try { gfx.setEffect(null); }
                    finally { latch.countDown(); }
                });
                try { latch.await(2, TimeUnit.SECONDS); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
            }
        }

        // detach subscribers
        deathSubscribers.clear();
        touchKillers.clear();

        synchronized (lockObjectForHpbar) {
            if (hpBar != null && hpBar.progressProperty().isBound()) {
                hpBar.progressProperty().unbind();
            }
            hpBar = null;
        }

        // finally, let the base class clean up node + references
        super.dispose();
    }


}
