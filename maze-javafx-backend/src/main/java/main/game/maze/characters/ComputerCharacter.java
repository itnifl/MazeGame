package main.game.maze.characters;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumMap;
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
import main.game.maze.common.graphics.SpriteAnimationUtil;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D.VectorFacing;
import main.game.maze.characters.interfaces.IMovingComputerCharacter;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.CharacterType;

public class ComputerCharacter extends Character implements IMovingComputerCharacter {
    private static final double ANIMATION_FRAME_DURATION_MS = 250.0;

    private int speed;
    private final CharacterType characterModel;

    protected VectorFacing currentCharacterFacing = VectorFacing.IDLE;
    private static final Logger _logger = Logger.getLogger(ComputerCharacter.class.getName());

    /** Frame-0 (static) images per direction, used when animationFrameCount <= 1. */
    private final Map<VectorFacing, Image> images = new EnumMap<>(VectorFacing.class);
    /** All walk-cycle frames per direction; null when animationFrameCount <= 1. */
    private final Map<VectorFacing, List<Image>> animationFrames;
    private int currentWalkFrame = 0;
    private final Timeline walkAnimation;

    protected final Image imageLeft;
    protected final Image imageRight;
    protected final Image imageUp;
    protected final Image imageDown;

    public ComputerCharacter(Node characterGraphics,
        CharacterType characterType,
        double positionX,
        double positionY,
        int speed) {
        super(characterGraphics, positionX, positionY);
        this.speed = speed;
        calculateMaxPositions();
        this.characterModel = characterType;

        var basePath  = characterType.getImageBase();
        var leftPath  = firstNonBlank(characterType.getImageTurnLeft(),  basePath);
        var rightPath = firstNonBlank(characterType.getImageTurnRight(), basePath);
        var upPath    = firstNonBlank(characterType.getImageTurnUp(),    basePath);
        var downPath  = firstNonBlank(characterType.getImageTurnDown(),  basePath);

        _logger.info("Loading character images from paths: " + leftPath + ", " + rightPath + ", " + upPath + ", " + downPath);

        this.imageLeft  = loadOrStub(getClass(), leftPath);
        this.imageRight = loadOrStub(getClass(), rightPath);
        this.imageUp    = loadOrStub(getClass(), upPath);
        this.imageDown  = loadOrStub(getClass(), downPath);

        images.put(VectorFacing.LEFT,  imageLeft);
        images.put(VectorFacing.RIGHT, imageRight);
        images.put(VectorFacing.UP,    imageUp);
        images.put(VectorFacing.DOWN,  imageDown);

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
            Platform.runLater(() -> {
                if (direction != currentCharacterFacing) {
                    currentCharacterFacing = direction;
                    updateCharacterImage();
                }
            });
        };

        // Initialize with a random direction to avoid zero-length vector (NaN issues)
        changeDirection();
    }

    /** Advance the walk-cycle frame and redraw the current direction. */
    private void advanceWalkFrame(int frameCount) {
        currentWalkFrame = (currentWalkFrame + 1) % frameCount;
        updateCharacterImage();
    }

    private void updateCharacterImage() {
        if (getCharacterGraphics() == null) return;
        Image next = currentImage(currentCharacterFacing);
        if (next != null) {
            setCharacterImage(next);
        }
    }

    private Image currentImage(VectorFacing facing) {
        if (animationFrames != null) {
            List<Image> frames = animationFrames.get(facing);
            if (frames != null && !frames.isEmpty()) {
                return frames.get(currentWalkFrame % frames.size());
            }
        }
        return images.get(facing);
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

            for(int x = 0; x < speed / StageConstants.SpeedReducer; x++) {
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
