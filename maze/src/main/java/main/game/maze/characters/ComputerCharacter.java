package main.game.maze.characters;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import main.game.maze.mazeworld.Vector2D.VectorFacing;
import main.game.maze.ai.PatrolMovementController;
import main.game.maze.characters.interfaces.IMovingComputerCharacter;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.CharacterType;
import main.game.maze.mazeworld.Vector2D.VectorFacing;

public class ComputerCharacter extends Character implements IMovingComputerCharacter {
    private int speed;
    private final CharacterType characterModel;

    protected VectorFacing currentCharacterFacing = VectorFacing.IDLE;
    private static final Logger _logger = Logger.getLogger(ComputerCharacter.class.getName());
    private final Map<VectorFacing, Image> images = new EnumMap<>(VectorFacing.class);

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

        var leftPath = characterType.getImageTurnLeft();
        var rightPath = characterType.getImageTurnRight();
        var upPath = characterType.getImageTurnUp();
        var downPath = characterType.getImageTurnDown();    

        _logger.info("Loading character images from paths: " + leftPath + ", " + rightPath + ", " + upPath + ", " + downPath);

        this.imageLeft  = loadOrStub(getClass(), leftPath);
        this.imageRight = loadOrStub(getClass(), rightPath);
        this.imageUp    = loadOrStub(getClass(), upPath);
        this.imageDown  = loadOrStub(getClass(), downPath);

        images.put(VectorFacing.LEFT,  imageLeft);
        images.put(VectorFacing.RIGHT, imageRight);
        images.put(VectorFacing.UP,    imageUp);
        images.put(VectorFacing.DOWN,  imageDown);

        this.directionSubscriber = (VectorFacing direction) -> {
            if (direction != null && direction != currentCharacterFacing) {
                Image next = images.get(direction);
                if (next != null) {
                    currentCharacterFacing = direction;
                    this.setCharacterImage(next);
                }
            }
        };
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

    public void changeDirection() {
        var rand = new Random();
        var direction = rand.nextInt(3);

        if(this.getDirectionX() > 0 && direction == 0) {
            direction = 3;
        }
        else if(this.getDirectionX() < 0 && direction == 3) {
            direction = 0;
        }
        else if(this.getDirectionY() > 0 && direction == 1) {
            direction = 2;
        }
        else if(this.getDirectionY() < 0 && direction == 2) {
            direction = 1;
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

    private static Image loadOrStub(Class<?> anchor, String path) {
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
