package main.game.maze.characters;
import java.util.Random;

import javafx.scene.Node;
import main.game.maze.characters.interfaces.IMovingComputerCharacter;
import main.game.maze.constants.StageConstants;

public class ComputerCharacter extends Character implements IMovingComputerCharacter {
    private int speed;

    public ComputerCharacter(Node characterGraphics, double positionX, double positionY, int speed) {
        super(characterGraphics, positionX, positionX);
        this.speed = speed;
        calculateMaxPositions();
    }

    public boolean move() {
        if(!isTouchingVector()) {
            boolean hasMoved = false;

            for(int x = 0; x < speed / StageConstants.SpeedReducer; x++) {
                var slowerSpeed = speed - (x *  StageConstants.SpeedReducer);
                var xDirection = this.getDirectionX();
                var yDirection = this.getDirectionY();

                if(xDirection < 0) {
                    hasMoved = moveLeft(slowerSpeed);
                } else if(xDirection > 0) {
                    hasMoved = moveRight(slowerSpeed);
                }
    
                if(yDirection > 0) {
                    hasMoved = moveDown(slowerSpeed) || hasMoved;
                } else if(yDirection < 0) {
                    hasMoved = moveUp(slowerSpeed) || hasMoved;
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
}
