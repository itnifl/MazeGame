package main.game.maze.characters;

import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.game.maze.App;
import main.game.maze.actions.MovementNotifierAction;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.characters.interfaces.ICanKill;
import main.game.maze.characters.interfaces.ICanLetYouWin;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;
import main.game.maze.characters.interfaces.ICharacterAction;
import main.game.maze.characters.interfaces.ICharacterAnimations;
import main.game.maze.config.model.PlayerConfig;
import main.game.maze.constants.ColorHueConstants;
import main.game.maze.constants.PlayerConstants;
import main.game.maze.constants.ResourceFileConstants;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.interfaces.IDeathSubscriber;
import main.game.maze.mazeworld.Vector2D.VectorFacing;
import main.game.maze.platform.AudioEngine;
import main.game.maze.platform.FxScheduler;
import java.util.EnumMap;
import java.util.Map;

public class PlayerCharacter extends Character
        implements ICharacterAnimations, ICanDie, ICanSubscribeAndNotifyPosition {

    private static final Logger LOGGER = Logger.getLogger(PlayerCharacter.class.getName());
    public static final int MAX_PLAYER_HP = 100;
    private AtomicInteger hitPoints = new AtomicInteger(MAX_PLAYER_HP);
    private static final Object lockObjectForHpbar = new Object();
    private List<IDeathSubscriber> deathSubscribers = new ArrayList<>();
    private List<ICanSubscribeAndNotifyPosition> touchKillers = new ArrayList<>();
    private ProgressBar hpBar;
    private static final long SOUND_COOLDOWN_MS = 250L;
    private static final double DEAD_PLAYER_SCALE = 1.2;
    private static final double DEAD_PLAYER_VIEW_ORDER = -1000.0;
    public boolean isWinning = false;
    private Timeline infectionTimeline = null;
    private final PlayerConfig playerConfig;
    private final int maxHitPoints;
    private final Map<VectorFacing, Image> directionalImages = new EnumMap<>(VectorFacing.class);
    private Image deathImage;
    private volatile boolean deadVisualActive = false;

    public PlayerCharacter(Node characterGraphics, double x, double y, ProgressBar hpBar) {
        this(characterGraphics, x, y, hpBar, null);
    }

    public PlayerCharacter(Node characterGraphics, double x, double y, ProgressBar hpBar, PlayerConfig playerConfig) {
        super(characterGraphics, x, y);
        this.characterXYSizeFromPoint = StageConstants.PlayerCharacterXYSize;
        calculateMaxPositions();
        this.notifyMovement = new MovementNotifierAction(characterGraphics, this);
        this.hpBar = hpBar;
        this.playerConfig = playerConfig == null ? PlayerConfig.defaults() : playerConfig;
        this.maxHitPoints = Math.max(1, this.playerConfig.health());
        this.hitPoints.set(this.maxHitPoints);
        configureDirectionalImages();
    }

    private void updateHpBarProgress() {
        synchronized (lockObjectForHpbar) {
            if (hpBar == null) {
                return;
            }
            double normalizedProgress = hitPoints.get() / (double) maxHitPoints;
            hpBar.setProgress(Math.max(0.0, Math.min(1.0, normalizedProgress)));
        }
    }

    private void configureDirectionalImages() {
        if (!(getCharacterGraphics() instanceof ImageView)) {
            return;
        }

        directionalImages.put(VectorFacing.LEFT, loadOrFallback(playerConfig.imageTurnLeft(), playerConfig.imageBase()));
        directionalImages.put(VectorFacing.RIGHT, loadOrFallback(playerConfig.imageTurnRight(), playerConfig.imageBase()));
        directionalImages.put(VectorFacing.UP, loadOrFallback(playerConfig.imageTurnUp(), playerConfig.imageBase()));
        directionalImages.put(VectorFacing.DOWN, loadOrFallback(playerConfig.imageTurnDown(), playerConfig.imageBase()));
        deathImage = loadOrFallback(playerConfig.imageDeath(), PlayerConstants.DefaultDeathImage);

        Image baseImage = loadOrFallback(playerConfig.imageBase(), PlayerConstants.DefaultPlayerImage);
        if (baseImage != null) {
            ((ImageView) getCharacterGraphics()).setImage(baseImage);
        }

        this.directionSubscriber = direction -> {
            if (direction == null) {
                return;
            }
            if (deadVisualActive) {
                return;
            }
            Image next = directionalImages.get(direction);
            if (next != null) {
                setCharacterImage(next);
            }
        };
    }

    private Image loadOrFallback(String resourcePath, String fallbackPath) {
        Image image = loadImage(resourcePath);
        if (image != null) {
            return image;
        }
        return loadImage(fallbackPath);
    }

    private Image loadImage(String resourcePath) {
        if (resourcePath == null || resourcePath.isBlank()) {
            return null;
        }
        var resource = getClass().getResource(resourcePath);
        if (resource == null) {
            LOGGER.warning("Missing player image resource: " + resourcePath);
            return null;
        }
        return new Image(resource.toExternalForm());
    }

    @Override
    public void PlayHappyAnimation() {
        super.doCharacterAnimation(new HappyAction());
    }

    @Override
    public void PlayDieAnimation() {
        deadVisualActive = true;
        applyDeathVisualState(getCharacterGraphics());
        super.doCharacterAnimation(new DieAction());
    }

    private void applyDeathVisualState(Node graphics) {
        if (graphics == null) {
            return;
        }

        boolean isImageView = graphics instanceof ImageView;
        double previousLayoutX = graphics.getLayoutX();
        double previousLayoutY = graphics.getLayoutY();
        double previousWidth = graphics.getBoundsInLocal().getWidth();
        double previousHeight = graphics.getBoundsInLocal().getHeight();

        if (deathImage != null && isImageView) {
            setCharacterImage(deathImage);
        }

        graphics.setScaleX(DEAD_PLAYER_SCALE);
        graphics.setScaleY(DEAD_PLAYER_SCALE);

        if (previousWidth > 0 && previousHeight > 0) {
            double xOffset = (previousWidth * (DEAD_PLAYER_SCALE - 1.0)) / 2.0;
            double yOffset = (previousHeight * (DEAD_PLAYER_SCALE - 1.0)) / 2.0;
            graphics.setLayoutX(previousLayoutX + xOffset);
            graphics.setLayoutY(previousLayoutY + yOffset);
        }

        graphics.setViewOrder(DEAD_PLAYER_VIEW_ORDER);
    }

    private class HappyAction implements ICharacterAction {
        public void doAction(Node characterGraphics) {
        }
    }

    private class DieAction implements ICharacterAction {
        public void doAction(Node characterGraphics) {
        }
    }

    @Override
    public int getHitPoints() {
        return hitPoints.get();
    }

    @Override
    public void setHitPoints(int hp) {
        hitPoints.set(hp);
        FxScheduler.get().runLater(this::updateHpBarProgress);
    }

    @Override
    public void subtractHitPoints(int hp) {
        hitPoints.addAndGet(-hp);

        FxScheduler.get().runLater(this::updateHpBarProgress);

        if (hitPoints.get() <= 0) {
            FxScheduler.get().runLater(this::PlayDieAnimation);

            // Notify subscribers synchronously (they can schedule UI work themselves if needed)
            var subscribersCopy = new ArrayList<>(deathSubscribers);
            for (var subscriber : subscribersCopy) {
                subscriber.AddDeathNotification(this);
            }
        }
    }

    @Override
    public void addHitPoints(int hp) {
        hitPoints.addAndGet(hp);
        FxScheduler.get().runLater(this::updateHpBarProgress);
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
        var graphics = this.getCharacterGraphics();
        if (graphics == null) {
            return;  // Player removed (game over), skip evaluation
        }
        if (nodeBounds.intersects(graphics.getBoundsInParent())) {
            if (entity instanceof ICanKill) {
                var canKillEntity = (ICanKill) entity;
                LOGGER.fine("Player is intersecting with " + canKillEntity);
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
        if (hpBar == null) {
            return;
        }
        AudioEngine.get().playRateLimited(
            ResourceFileConstants.PlayerScreamSound,
            "player.scream",
            SOUND_COOLDOWN_MS);
    }

    private void doInfectedScreamSound() {
        if (hpBar == null) {
            return;
        }
        AudioEngine.get().playRateLimited(
            ResourceFileConstants.PlayerInfectedSound,
            "player.infected",
            SOUND_COOLDOWN_MS);
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

        // clear any flash effect left on the sprite (defensive; base also clears)
        Node gfx = getCharacterGraphics();
        if (gfx != null) {
            FxScheduler.get().runOnFxThread(() -> gfx.setEffect(null));
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
