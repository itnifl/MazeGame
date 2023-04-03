package main.game.maze.characters;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import main.game.maze.MazeWorld;
import main.game.maze.Vector2D;
import main.game.maze.constants.StageConstants;

public class Character {
    protected int characterXYSizeFromPoint = StageConstants.TouchDistance;

    private Point2D characterPosition;
    private Vector2D characterDirection;


    private double directionX;
    private double directionY;
    private Node characterGraphics;

    private int maxX;
    private int maxY;

    private MazeWorld maze;

    public Character(Node characterGraphics, double x, double y) {
        this.directionX = 0;
        this.directionY = 0;
        this.characterGraphics = characterGraphics;

        characterPosition = new Point2D(x, y);
        characterDirection = new Vector2D(
            this.characterPosition.getX(), this.characterPosition.getY(), 
            this.characterPosition.getX() + this.directionX, this.characterPosition.getY() + this.directionY).normalize(StageConstants.PlayerCharacterSpeed);

        maze = MazeWorld.GetWorld();
        CalculateMaxPositions();
    }

    protected void CalculateMaxPositions() {
        maxX = StageConstants.BoardMaxX-characterXYSizeFromPoint;
        maxY = StageConstants.BoardMaxY-characterXYSizeFromPoint;
    }

    public Node getCharacterGraphics() {
        return this.characterGraphics;
    }

    public void setCharacterGraphics(Node newGraphics) {
        this.characterGraphics = newGraphics;
    }

    public Point2D getCharacterPosition() {
        return this.characterPosition;
    }

    public Vector2D getCharacterDirection() {
        return this.characterDirection;
    }

    /*
    * Is character touching any vector?
    */
    private boolean isTouchingVector() {
        // Loop through all the vectors in the maze and check the distance 
        for (Vector2D vector : maze.getVectors()) {
            if (characterDirection.doIntersect(vector, characterXYSizeFromPoint)) {
                return true;
            }
        }
        return false;
    }

    public boolean moveUp(double speed) {
        directionX = 0;
        directionY = -speed;
        updateDirection((int)speed);
        if(moveCharacterUp(speed)) {
            updatePosition();
            return true;
        }
        return false;
    }

    public boolean moveDown(double speed) {
        directionX = 0;
        directionY = speed;
        updateDirection((int)speed);
        if(moveCharacterDown(speed)) {
            updatePosition();
            return true;
        }
        return false;
    }

    public boolean moveLeft(double speed) {
        directionX = -speed;
        directionY = 0;
        updateDirection((int)speed);
        if(moveCharacterLeft(speed)) {
            updatePosition();
            return true;
        }
        return false;
    }

    public boolean moveRight(double speed) {
        directionX = speed;
        directionY = 0;
        updateDirection((int)speed);
        if(moveCharacterRight(speed)) {
            updatePosition();
            return true;
        }
        return false;
    }

    private void updateDirection(int factor) {
        characterDirection = new Vector2D(this.characterPosition.getX(), this.characterPosition.getY(), 
        this.characterPosition.getX() + this.directionX, this.characterPosition.getY() + this.directionY).normalize(factor);
    }

    private void updatePosition() {
        characterPosition = characterPosition.add(directionX, directionY);
    }

    private boolean moveCharacterRight(double speed) {
        double newX = characterGraphics.getLayoutX() + speed;
        if (newX < maxX && !isTouchingVector()) {
            characterGraphics.setLayoutX(newX);
            return true;
        }
        return false;
    }

    private boolean moveCharacterLeft(double speed) {
        double newX = characterGraphics.getLayoutX() - speed;
        if (newX >= 0 && !isTouchingVector()) {
            characterGraphics.setLayoutX(newX);
            return true;
        }
        return false;
    }

    private boolean moveCharacterDown(double speed) {
        double newY = characterGraphics.getLayoutY() + speed;
        if (newY < maxY && !isTouchingVector()) {
            characterGraphics.setLayoutY(newY);
            return true;
        }
        return false;
    }

    private boolean moveCharacterUp(double speed) {
        double newY = characterGraphics.getLayoutY() - speed;
        if (newY >= 0 && !isTouchingVector()) {
            characterGraphics.setLayoutY(newY);
            return true;
        }
        return false;
    }
}

