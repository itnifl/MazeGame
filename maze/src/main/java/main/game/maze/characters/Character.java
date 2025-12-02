package main.game.maze.characters;
import main.game.maze.mazeworld.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.game.maze.App;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.characters.interfaces.ICharacterAction;
import main.game.maze.characters.interfaces.ISubscribeOnDirection;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.interfaces.INotifyMovement;

public class Character  {
    public INotifyMovement notifyMovement = null;
    public ISubscribeOnDirection directionSubscriber = null;
    protected int characterXYSizeFromPoint = StageConstants.TouchDistance;

    private Point2D characterPosition;
    private Vector2D characterDirection;


    private double directionX;
    private double directionY;
    private Node characterGraphics;

    private int maxX;
    private int maxY;

    private GameMazeWorld maze;

    public Character(Node characterGraphics, double x, double y) {
        this.directionX = 0;
        this.directionY = 0;
        this.characterGraphics = characterGraphics;

        characterPosition = new Point2D(x, y);
        characterDirection = new Vector2D(
            this.characterPosition.getX(), this.characterPosition.getY(), 
            this.characterPosition.getX() + this.directionX, this.characterPosition.getY() + this.directionY).normalize(StageConstants.PlayerCharacterSpeed);

        maze = GameMazeWorld.GetWorld();
        calculateMaxPositions();
    }

    protected void calculateMaxPositions() {
        maxX = App.getBoardMaxX()-characterXYSizeFromPoint;
        maxY = App.getBoardMaxY()-characterXYSizeFromPoint;
    }

    public Node getCharacterGraphics() {
        return this.characterGraphics;
    }

    public void setCharacterGraphics(Node newGraphics) {
        this.characterGraphics = newGraphics;
    }

    protected void setCharacterImage(Image image) {
        Node node = this.getCharacterGraphics();
        var i = (ImageView)node;
        i.setImage(image);
    }


    public Point2D getCharacterPosition() {
        return this.characterPosition;
    }

    public Vector2D getCharacterDirection() {
        return this.characterDirection;
    }

    public void setCharacterDirection(double x, double y, int speed) {
        directionX = x;
        directionY = y;
        updateDirection(speed);
    }

    public double getDirectionX() {
        return directionX;
    }

    public double getDirectionY() {
        return directionY;
    }

    /*
    * Is character touching any vector?
    */
    protected boolean isTouchingVector() {
        // Loop through all the vectors in the maze and check the distance 
        for (Vector2D vector : maze.getMazeVectors()) {
            if (characterDirection.doIntersect(vector, characterXYSizeFromPoint)) {
                return true;
            }
        }
        return false;
    }

    public boolean moveUp(double speed, boolean force) {
        directionX = 0;
        directionY = -speed;
        updateDirection((int)speed);
        if(moveCharacterUp(speed, force)) {
            updatePosition();
            return true;
        }
        return false;
    }

    public boolean moveDown(double speed, boolean force) {
        directionX = 0;
        directionY = speed;
        updateDirection((int)speed);
        if(moveCharacterDown(speed, force)) {
            updatePosition();
            return true;
        }
        return false;
    }

    public boolean moveLeft(double speed, boolean force) {
        directionX = -speed;
        directionY = 0;
        updateDirection((int)speed);
        if(moveCharacterLeft(speed, force)) {
            updatePosition();
            return true;
        }
        return false;
    }

    public boolean moveRight(double speed, boolean force) {
        directionX = speed;
        directionY = 0;
        updateDirection((int)speed);
        if(moveCharacterRight(speed, force)) {
            updatePosition();
            return true;
        }
        return false;
    }

    protected void updateDirection(int factor) {
        characterDirection = new Vector2D(this.characterPosition.getX(), this.characterPosition.getY(), 
        this.characterPosition.getX() + this.directionX, this.characterPosition.getY() + this.directionY).normalize(factor);
    }

    protected void updatePosition() {
        characterPosition = characterPosition.add(directionX, directionY);
    }

    private boolean moveCharacterRight(double speed, boolean force) {
        double newX = characterGraphics.getLayoutX() + speed;
        if (newX < maxX && (force || !isTouchingVector())) {
            characterGraphics.setLayoutX(newX);
            doNotifyMovement();
            return true;
        }
        return false;
    }

    private boolean moveCharacterLeft(double speed, boolean force) {
        double newX = characterGraphics.getLayoutX() - speed;
        if (newX >= 0 && (force || !isTouchingVector())) {
            characterGraphics.setLayoutX(newX);
            doNotifyMovement();
            return true;
        }
        return false;
    }

    private boolean moveCharacterDown(double speed, boolean force) {
        double newY = characterGraphics.getLayoutY() + speed;
        if (newY < maxY && (force || !isTouchingVector())) {
            characterGraphics.setLayoutY(newY);
            doNotifyMovement();
            return true;
        }
        return false;
    }

    private boolean moveCharacterUp(double speed, boolean force) {
        double newY = characterGraphics.getLayoutY() - speed;
        if (newY >= 0 && (force || !isTouchingVector())) {
            characterGraphics.setLayoutY(newY);
            doNotifyMovement();
            return true;
        }
        return false;
    }

    public void doCharacterAnimation(ICharacterAction animation) {
        animation.doAction(characterGraphics);
    }

    private void doNotifyMovement() {
        if(notifyMovement != null) {
            notifyMovement.doNotifyCharacterMovement();
        }
        if(directionSubscriber != null) {
            directionSubscriber.notifyCurrentDirection(characterDirection.getFacingFromVector());
        }
    }

    public void dispose() {
        // stop emitting notifications
        notifyMovement = null;
        directionSubscriber = null;

        // remove graphics from scene graph and clear effects
        Node gfx = characterGraphics;
        if (gfx != null) {
            Runnable remove = () -> {
                try {
                    gfx.setEffect(null);
                    var parent = gfx.getParent();
                    if (parent instanceof javafx.scene.layout.Pane p) {
                        p.getChildren().remove(gfx);
                    }
                } catch (Exception ignored) {}
            };
            if (javafx.application.Platform.isFxApplicationThread()) {
                remove.run();
            } else {
                javafx.application.Platform.runLater(remove);
            }
        }

        // help GC
        characterGraphics = null;
        maze = null;
    }
}

