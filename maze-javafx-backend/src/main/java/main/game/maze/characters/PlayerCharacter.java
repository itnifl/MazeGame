package main.game.maze.characters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

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
import main.game.maze.characters.interfaces.INonTangientMazeGameCharacter;
import main.game.maze.config.model.PlayerConfig;
import main.game.maze.constants.ColorHueConstants;
import main.game.maze.constants.PlayerConstants;
import main.game.maze.common.constants.AudioResourceConstants;
import main.game.maze.common.movement.GhostNonTangibilityService;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.interfaces.IDeathSubscriber;
import main.game.maze.mazeworld.Vector2D.VectorFacing;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.AnimationEngine;
import main.game.maze.common.graphics.IAnimationHandle;
import main.game.maze.common.graphics.ICharacterStatePresenter;
import main.game.maze.common.graphics.UiScheduler;
import java.util.EnumMap;
import java.util.Map;

public class PlayerCharacter extends Character
        implements ICharacterAnimations, ICanDie, ICanSubscribeAndNotifyPosition {

    private static final Logger LOGGER = Logger.getLogger(PlayerCharacter.class.getName());
    public static final int MAX_PLAYER_HP = 100;
    private AtomicInteger hitPoints = new AtomicInteger(MAX_PLAYER_HP);
    private List<IDeathSubscriber> deathSubscribers = new ArrayList<>();
    private List<ICanSubscribeAndNotifyPosition> touchKillers = new ArrayList<>();
    private ICharacterStatePresenter statePresenter;
    private static final long SOUND_COOLDOWN_MS = 250L;
    private static final double DEAD_PLAYER_SCALE = 1.8;
    private static final double DEAD_PLAYER_VIEW_ORDER = -1000.0;
    public boolean isWinning = false;
    private IAnimationHandle infectionAnimation = null;
    private final PlayerConfig playerConfig;
    private final int maxHitPoints;
    private final Map<VectorFacing, Image> directionalImages = new EnumMap<>(VectorFacing.class);
    private Image deathImage;
    private volatile boolean deadVisualActive = false;

    public PlayerCharacter(Node characterGraphics, double x, double y, ProgressBar hpBar) {
        this(characterGraphics, x, y, hpBar, null);
    }

    public PlayerCharacter(Node characterGraphics, double x, double y, ProgressBar hpBar, PlayerConfig playerConfig) {
        this(characterGraphics, x, y,
            hpBar == null ? null : new ProgressBarStatePresenter(hpBar),
            playerConfig);
    }

    public PlayerCharacter(Node characterGraphics, double x, double y,
                           ICharacterStatePresenter statePresenter,
                           PlayerConfig playerConfig) {
        super(characterGraphics, x, y);
        this.characterXYSizeFromPoint = StageConstants.PlayerCharacterXYSize;
        calculateMaxPositions();
        this.notifyMovement = new MovementNotifierAction(characterGraphics, this);
        this.statePresenter = statePresenter;
        this.playerConfig = playerConfig == null ? PlayerConfig.defaults() : playerConfig;
        this.maxHitPoints = Math.max(1, this.playerConfig.health());
        this.hitPoints.set(this.maxHitPoints);
        configureDirectionalImages();
    }

    private void updateHpBarProgress() {
        ICharacterStatePresenter presenter = statePresenter;
        if (presenter == null) return;
        double normalizedProgress = hitPoints.get() / (double) maxHitPoints;
        presenter.showHealthRatio(normalizedProgress);
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

        if (deathImage != null && isImageView) {
            setCharacterImage(deathImage);
        }

        // JavaFX setScaleX/Y pivots at the center of the node's untransformed
        // layoutBounds, so the death sprite already grows symmetrically in place.
        // No additional layoutX/Y translation is needed (and adding one shifts
        // the visual off the player's actual position).
        graphics.setScaleX(DEAD_PLAYER_SCALE);
        graphics.setScaleY(DEAD_PLAYER_SCALE);

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

    /**
     * Maximum hit points configured for this player (from PlayerConfig).
     * Used by score/UI code that needs to express damage as a fraction of full
     * HP without assuming a hard-coded 100-point pool.
     */
    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    @Override
    public void setHitPoints(int hp) {
        hitPoints.set(hp);
        UiScheduler.get().runLater(this::updateHpBarProgress);
    }

    @Override
    public void subtractHitPoints(int hp) {
        hitPoints.addAndGet(-hp);

        UiScheduler.get().runLater(this::updateHpBarProgress);

        if (hitPoints.get() <= 0) {
            UiScheduler.get().runLater(this::PlayDieAnimation);

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
        UiScheduler.get().runLater(this::updateHpBarProgress);
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

        // Phasing ghosts bypass walls but still deal contact damage and trigger the scream.
        // Use the shared service so the decision is never duplicated across frontends.
        boolean isPhasing = entity instanceof INonTangientMazeGameCharacter nonTangient
                && GhostNonTangibilityService.isPhasing(nonTangient.getNonTangientEnergy());

        var myBounds = graphics.getBoundsInParent();
        if (!nodeBounds.intersects(myBounds)) {
            return;
        }

        // A wall between the enemy centre and the player centre blocks damage for solid enemies.
        // Phasing ghosts bypass this check.
        if (!isPhasing
                && App.gameController != null
                && App.gameController.isWallBetween(
                        nodeBounds.getCenterX(), nodeBounds.getCenterY(),
                        myBounds.getCenterX(), myBounds.getCenterY())) {
            return;
        }

        if (entity instanceof ICanKill) {
            var canKillEntity = (ICanKill) entity;
            LOGGER.fine("Player is intersecting with " + canKillEntity);
            this.subtractHitPoints(canKillEntity.getDamage());
            if (this.getCharacterGraphics() instanceof ImageView iv) {
                this.flashCharacterColor(iv, ColorHueConstants.RED_HUE);
            }
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

    @Override
    public List<ICanSubscribeAndNotifyPosition> getPositionSubscribers() {
        return touchKillers;
    }

    private void doStandardScreamSound() {
        if (statePresenter == null) {
            return;
        }
        AudioEngine.get().playRateLimited(
            AudioResourceConstants.PlayerScreamSound,
            "player.scream",
            SOUND_COOLDOWN_MS);
    }

    private void doInfectedScreamSound() {
        if (statePresenter == null) {
            return;
        }
        AudioEngine.get().playRateLimited(
            AudioResourceConstants.PlayerInfectedSound,
            "player.infected",
            SOUND_COOLDOWN_MS);
    }

    private void flashCharacterColor(ImageView imageView, double colorHue) {
        if (imageView == null || statePresenter == null)
            return;

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.2);
        colorAdjust.setSaturation(1.0);
        colorAdjust.setContrast(1.0);
        colorAdjust.setHue(colorHue); 

        imageView.setEffect(colorAdjust);
        AnimationEngine.get().animateValues(
            new double[] {0.0, 0.5, 1.0},
            new double[] {-1.0, colorHue, -1.0},
            value -> UiScheduler.get().runOnUiThread(() -> colorAdjust.setHue(value)),
            () -> UiScheduler.get().runOnUiThread(() -> imageView.setEffect(null)));
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
            if (infectionAnimation == null || !infectionAnimation.isRunning()) {
                scheduleInfectionTick(dps, totalTicks);
            }

        }
    }

    private void scheduleInfectionTick(double dps, int remainingTicks) {
        if (remainingTicks <= 0) {
            infectionAnimation = null;
            return;
        }
        infectionAnimation = AnimationEngine.get().scheduleOnce(1.0, () -> {
            this.subtractHitPoints((int) Math.round(dps));
            if (this.getCharacterGraphics() instanceof ImageView iv) {
                this.flashCharacterColor(iv, ColorHueConstants.GREEN_HUE);
            }
            this.doInfectedScreamSound();
            scheduleInfectionTick(dps, remainingTicks - 1);
        });
    }

    public void dispose() {
        // stop infection tick
        if (infectionAnimation != null) {
            infectionAnimation.stop();
            infectionAnimation = null;
        }

        // clear any flash effect left on the sprite (defensive; base also clears)
        Node gfx = getCharacterGraphics();
        if (gfx != null) {
            UiScheduler.get().runOnUiThread(() -> gfx.setEffect(null));
        }

        // detach subscribers
        deathSubscribers.clear();
        touchKillers.clear();

        ICharacterStatePresenter presenter = statePresenter;
        statePresenter = null;
        if (presenter != null) {
            presenter.dispose();
        }

        // finally, let the base class clean up node + references
        super.dispose();
    }


}


