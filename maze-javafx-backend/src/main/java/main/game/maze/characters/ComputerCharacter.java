package main.game.maze.characters;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.util.Duration;
import main.game.maze.App;
import main.game.maze.common.graphics.DamageState;
import main.game.maze.common.graphics.DamagePathResolver;
import main.game.maze.common.graphics.SpriteAnimationUtil;
import main.game.maze.common.remains.DeathCause;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D.VectorFacing;
import main.game.maze.characters.interfaces.IMovingComputerCharacter;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.CharacterType;

public class ComputerCharacter extends Character implements IMovingComputerCharacter {
    private static final double ANIMATION_FRAME_DURATION_MS = 250.0;

    /** Shared by all characters so each damage-sprite lookup happens once per game. */
    private static final DamagePathResolver CLASSPATH_DAMAGE_PATHS =
            DamagePathResolver.classpath(ComputerCharacter.class);

    private double speed;
    private final CharacterType characterModel;

    protected VectorFacing currentCharacterFacing = VectorFacing.IDLE;
    private static final Logger _logger = Logger.getLogger(ComputerCharacter.class.getName());

    /** Frame-0 (static) images per direction, used when animationFrameCount <= 1. */
    private final Map<VectorFacing, Image> images = new EnumMap<>(VectorFacing.class);
    /** All walk-cycle frames per direction; null when animationFrameCount <= 1. */
    private final Map<VectorFacing, List<Image>> animationFrames;
    private int currentWalkFrame = 0;
    private final Timeline walkAnimation;

    private final Map<VectorFacing, String> directionalPaths = new EnumMap<>(VectorFacing.class);
    private final Map<String, Image> imageCache = new HashMap<>();
    private final String basePath;

    protected int currentHp;
    protected int maxHp;
    protected DamageState currentDamageState = DamageState.HEALTHY;
    private DamagePathResolver damagePathResolver = CLASSPATH_DAMAGE_PATHS;
    /** Cause of the damage being applied right now; read by subclasses when that damage kills. */
    private DeathCause damageCause = DeathCause.OTHER;

    protected final Image imageLeft;
    protected final Image imageRight;
    protected final Image imageUp;
    protected final Image imageDown;

    public ComputerCharacter(Node characterGraphics, 
        CharacterType characterType, 
        double positionX, 
        double positionY, 
        double speed) {
        super(characterGraphics, positionX, positionY);

        this.speed = speed;
        calculateMaxPositions();
        this.characterModel = characterType;

        this.maxHp = characterType != null ? Math.max(1, characterType.getHealth()) : 100;
        this.currentHp = this.maxHp;
        this.currentDamageState = DamageState.of(this.currentHp, this.maxHp);

        this.basePath = characterType != null ? characterType.getImageBase() : null;
        var leftPath  = firstNonBlank(characterType != null ? characterType.getImageTurnLeft() : null,  basePath);
        var rightPath = firstNonBlank(characterType != null ? characterType.getImageTurnRight() : null, basePath);
        var upPath    = firstNonBlank(characterType != null ? characterType.getImageTurnUp() : null,    basePath);
        var downPath  = firstNonBlank(characterType != null ? characterType.getImageTurnDown() : null,  basePath);

        directionalPaths.put(VectorFacing.LEFT,  leftPath);
        directionalPaths.put(VectorFacing.RIGHT, rightPath);
        directionalPaths.put(VectorFacing.UP,    upPath);
        directionalPaths.put(VectorFacing.DOWN,  downPath);
        directionalPaths.put(VectorFacing.IDLE,  rightPath);

        _logger.info("Loading character images from paths: " + leftPath + ", " + rightPath + ", " + upPath + ", " + downPath);

        this.imageLeft  = loadOrStub(getClass(), leftPath);
        this.imageRight = loadOrStub(getClass(), rightPath);
        this.imageUp    = loadOrStub(getClass(), upPath);
        this.imageDown  = loadOrStub(getClass(), downPath);

        images.put(VectorFacing.LEFT,  imageLeft);
        images.put(VectorFacing.RIGHT, imageRight);
        images.put(VectorFacing.UP,    imageUp);
        images.put(VectorFacing.DOWN,  imageDown);

        if (leftPath != null)  imageCache.put(leftPath,  imageLeft);
        if (rightPath != null) imageCache.put(rightPath, imageRight);
        if (upPath != null)    imageCache.put(upPath,    imageUp);
        if (downPath != null)  imageCache.put(downPath,  imageDown);

        int frameCount = Math.max(1, characterType.getAnimationFrameCount());
        if (frameCount > 1) {
            animationFrames = new EnumMap<>(VectorFacing.class);
            animationFrames.put(VectorFacing.LEFT,  loadAnimationFrames(leftPath,  frameCount));
            animationFrames.put(VectorFacing.RIGHT, loadAnimationFrames(rightPath, frameCount));
            animationFrames.put(VectorFacing.UP,    loadAnimationFrames(upPath,    frameCount));
            animationFrames.put(VectorFacing.DOWN,  loadAnimationFrames(downPath,  frameCount));
            walkAnimation = buildWalkTimeline(frameCount);
            walkAnimation.play();
        } else {
            animationFrames = null;
            walkAnimation = null;
        }

        this.directionSubscriber = (VectorFacing direction) -> {
            if (direction == null) return;
            Runnable r = () -> {
                if (direction != currentCharacterFacing) {
                    currentCharacterFacing = direction;
                    updateCharacterImage();
                }
            };
            if (Platform.isFxApplicationThread()) {
                r.run();
            } else {
                try {
                    Platform.runLater(r);
                } catch (IllegalStateException e) {
                    r.run();
                }
            }
        };

        // Initialize with a random direction to avoid zero-length vector (NaN issues)
        changeDirection();
    }

    /** Advance the walk-cycle frame and redraw the current direction. */
    private void advanceWalkFrame(int frameCount) {
        currentWalkFrame = (currentWalkFrame + 1) % frameCount;
        updateCharacterImage();
    }

    protected void updateCharacterImage() {
        if (getCharacterGraphics() == null) return;
        Image next = currentImage(currentCharacterFacing);
        if (next != null) {
            setCharacterImage(next);
        }
    }

    private Image currentImage(VectorFacing facing) {
        if (isDamageStatesEnabled() && currentDamageState != DamageState.HEALTHY) {
            String dirPath = getDirectionalPath(facing);
            if (dirPath != null && !dirPath.isBlank()) {
                String framePath = dirPath;
                if (animationFrames != null) {
                    framePath = SpriteAnimationUtil.deriveAnimationFramePath(dirPath, currentWalkFrame);
                }
                String resolvedPath = damagePathResolver.resolve(framePath, currentDamageState);
                if (resolvedPath != null && !resolvedPath.equals(framePath)) {
                    return getOrLoadImage(resolvedPath);
                }
            }
        }
        if (animationFrames != null) {
            List<Image> frames = animationFrames.get(facing);
            if (frames != null && !frames.isEmpty()) {
                return frames.get(currentWalkFrame % frames.size());
            }
        }
        return images.get(facing);
    }

    private String getDirectionalPath(VectorFacing facing) {
        if (facing != null && directionalPaths.containsKey(facing)) {
            return directionalPaths.get(facing);
        }
        return directionalPaths.getOrDefault(VectorFacing.RIGHT, basePath);
    }

    Image getOrLoadImage(String path) {
        if (path == null || path.isBlank()) return null;
        return imageCache.computeIfAbsent(path, p -> loadOrStub(getClass(), p));
    }

    /** Test seam: replaces the damage-sprite lookup; {@code null} restores the classpath lookup. */
    void setDamagePathResolver(DamagePathResolver resolver) {
        this.damagePathResolver = resolver != null ? resolver : CLASSPATH_DAMAGE_PATHS;
    }

    /** Test seam: pre-registers the image returned for {@code path}. */
    void registerImageForPath(String path, Image image) {
        if (path != null && image != null) {
            imageCache.put(path, image);
        }
    }

    public int getHitPoints() {
        return currentHp;
    }

    public int getMaxHitPoints() {
        return maxHp;
    }

    public DamageState getDamageState() {
        return currentDamageState;
    }

    public void setHitPoints(int hp) {
        onHpChanged(hp, this.maxHp);
    }

    public void subtractHitPoints(int hp) {
        onHpChanged(this.currentHp - hp, this.maxHp);
    }

    /**
     * Applies damage dealt by {@code cause} (bomb fire, a shot, a crushing wall); if it kills,
     * the character leaves the matching remains. Overrides {@code ICanDie}'s default for subclasses.
     */
    public void subtractHitPoints(int hp, DeathCause cause) {
        damageCause = cause != null ? cause : DeathCause.OTHER;
        try {
            subtractHitPoints(hp);
        } finally {
            damageCause = DeathCause.OTHER;
        }
    }

    /** Cause of the damage currently being applied ({@link DeathCause#OTHER} outside a caused hit). */
    protected DeathCause damageCause() {
        return damageCause;
    }

    /**
     * Leaves remains where this character's sprite is, for the cause of the killing blow.
     * Call while the character is still on the board (before unregistering it).
     */
    protected void leaveRemains() {
        Node graphics = getCharacterGraphics();
        if (graphics == null || App.gameController == null) {
            return;
        }
        var bounds = graphics.getBoundsInParent();
        App.gameController.leaveRemains(this, damageCause, basePath,
                bounds.getMinX(), bounds.getMinY(), Math.max(bounds.getWidth(), bounds.getHeight()));
    }

    public void addHitPoints(int hp) {
        onHpChanged(this.currentHp + hp, this.maxHp);
    }

    private void scheduleImageUpdate() {
        if (Platform.isFxApplicationThread()) {
            updateCharacterImage();
        } else {
            try {
                Platform.runLater(this::updateCharacterImage);
            } catch (IllegalStateException e) {
                updateCharacterImage();
            }
        }
    }

    public void onHpChanged(int currentHp, int maxHp) {
        this.maxHp = Math.max(1, maxHp);
        this.currentHp = currentHp;
        this.currentDamageState = DamageState.of(this.currentHp, this.maxHp);
        if (isDamageStatesEnabled()) {
            scheduleImageUpdate();
        }
    }

    /** True when the opponent model opts in to damage-state sprites. */
    protected boolean isDamageStatesEnabled() {
        return characterModel != null && characterModel.isDamageStatesEnabled();
    }

    /**
     * Replaces the healthy (non-damaged) image shown for {@code facing}, e.g. a sprite variant
     * generated at runtime. It is also the fallback when a damage-state asset is missing.
     * Only affects single-frame characters; walk-cycle frames are loaded from assets.
     */
    protected void overrideHealthyImage(VectorFacing facing, Image image) {
        if (facing != null && image != null) {
            images.put(facing, image);
        }
    }

    public void onHpChanged(int currentHp) {
        onHpChanged(currentHp, this.maxHp);
    }

    int getCurrentWalkFrame() {
        return currentWalkFrame;
    }

    /** Test seam: stops the automatic walk-cycle timer so frames can be stepped manually. */
    void pauseWalkAnimation() {
        if (walkAnimation != null) {
            walkAnimation.stop();
        }
    }

    void setCurrentWalkFrame(int frame) {
        this.currentWalkFrame = frame;
        updateCharacterImage();
    }

    private Timeline buildWalkTimeline(int frameCount) {
        Timeline tl = new Timeline(new KeyFrame(
                Duration.millis(ANIMATION_FRAME_DURATION_MS),
                e -> advanceWalkFrame(frameCount)));
        tl.setCycleCount(Animation.INDEFINITE);
        return tl;
    }

    @Override
    public void dispose() {
        if (walkAnimation != null) {
            walkAnimation.stop();
        }
        super.dispose();
    }

    private List<Image> loadAnimationFrames(String frame1Path, int frameCount) {
        List<Image> frames = new ArrayList<>(frameCount);
        for (int i = 0; i < frameCount; i++) {
            String path = SpriteAnimationUtil.deriveAnimationFramePath(frame1Path, i);
            frames.add(loadOrStub(getClass(), path));
        }
        return frames;
    }

    @Override
    public boolean move(boolean force) {
        if(force || !isTouchingVector()) {
            boolean hasMoved = false;

            double steps = speed / StageConstants.SpeedReducer;
            for(int x = 0; x < steps; x++) {
                var slowerSpeed = speed - (x *  StageConstants.SpeedReducer);

                var xDirection = this.getDirectionX();
                var yDirection = this.getDirectionY();

                if(xDirection < 0) {
                    hasMoved = moveLeft(slowerSpeed, force);
                } else if(xDirection > 0) {
                    hasMoved = moveRight(slowerSpeed, force);
                }
    
                if(yDirection > 0) {
                    hasMoved = moveDown(slowerSpeed, force) || hasMoved;
                } else if(yDirection < 0) {
                    hasMoved = moveUp(slowerSpeed, force) || hasMoved;
                }

                if(hasMoved || (yDirection == 0 && xDirection == 0)) {
                    break;
                }
            } 
            
            return hasMoved;
        }
        return false;
    }

     @Override
    public void setDirection(Point2D direction) {
        if (direction != null) {
            this.setCharacterDirection(direction.getX(), direction.getY(), this.speed);
        }
    }

    public void changeDirection() {
        var rand = new Random();
        var direction = rand.nextInt(4);  // 0=right, 1=down, 2=up, 3=left

        // Avoid reversing direction (bouncing back and forth)
        if(this.getDirectionX() > 0 && direction == 3) {  // moving right, don't go left
            direction = rand.nextInt(3);  // pick from 0,1,2 instead
        }
        else if(this.getDirectionX() < 0 && direction == 0) {  // moving left, don't go right
            direction = 1 + rand.nextInt(3);  // pick from 1,2,3 instead
        }
        else if(this.getDirectionY() > 0 && direction == 2) {  // moving down, don't go up
            direction = rand.nextBoolean() ? 0 : (rand.nextBoolean() ? 1 : 3);  // pick 0,1,3
        }
        else if(this.getDirectionY() < 0 && direction == 1) {  // moving up, don't go down
            direction = rand.nextBoolean() ? 0 : (rand.nextBoolean() ? 2 : 3);  // pick 0,2,3
        }


        switch(direction) {
            case 0:
                setCharacterDirection(1, 0, speed);
                break;

            case 1:
                setCharacterDirection(0, 1, speed);
                break;

            case 2:
                setCharacterDirection(0, -1, speed);
                break;

            case 3:
                setCharacterDirection(-1, 0, speed);
                break;
        }
    }

    private static String firstNonBlank(String preferred, String fallback) {
        return preferred != null && !preferred.isBlank() ? preferred : fallback;
    }

    private static Image loadOrStub(Class<?> anchor, String path) {
        if (path == null || path.isBlank()) {
            Logger.getLogger(ComputerCharacter.class.getName())
                .warning("Missing or blank image path — using 1x1 placeholder");
            return new WritableImage(1, 1);
        }
        // Allow both "/a/b.png" and "a/b.png"
        String normalized = path.startsWith("/") ? path : "/" + path;
        URL url = anchor.getResource(normalized);
        if (url != null) {
            return new Image(url.toExternalForm());
        }
        // Fail soft in tests: 1x1 transparent image, no NPE
        Logger.getLogger(ComputerCharacter.class.getName())
            .warning("Missing image resource: " + normalized + " (using 1x1 placeholder)");
        return new WritableImage(1, 1);
    }

    public BehaviorType getCharacterBehaviour() {
        return characterModel.getBehavior();
    }
}
