package main.game.maze;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.constants.StageConstants;

public class GameController implements Initializable {
    @FXML private Button startButton;
    private Timeline timeline;

    @FXML private Node player;
    @FXML private Pane gameBoard;
    @FXML private Label coordinatesLabel;
    
    private int maxX = StageConstants.BoardMaxX-StageConstants.TouchDistance;
    private int maxY = StageConstants.BoardMaxY-StageConstants.TouchDistance;

    
    private PlayerCharacter playerCharacter;
    private MazeWorld maze;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // create a timeline with two key frames
        timeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(startButton.opacityProperty(), 1.0)),
            new KeyFrame(Duration.seconds(1), new KeyValue(startButton.opacityProperty(), 0.0))
        );
        // set the cycle count to indefinite
        timeline.setCycleCount(Timeline.INDEFINITE);
        // pause the timeline initially
        timeline.pause();

        maze = MazeWorld.GetWorld();
        playerCharacter = new PlayerCharacter(
            player, 
            player.getLayoutX(), 
            player.getLayoutY());

        player.requestFocus();
        gameBoard.requestFocus();
    }

    @FXML
    private void handlePlayerClicked(KeyEvent event) {
    
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                movePlayerUp();
                break;
            case DOWN:
                movePlayerDown();
                // implement move player down
                break;
            case LEFT:
                movePlayerLeft();
                // implement move player left
                break;
            case RIGHT:
                movePlayerRight();
                // implement move player right
                break;
            default:
                break;
        }

        var coordinatesText = "X: " + playerCharacter.getCharacterPosition().getX() + ", Y: " + playerCharacter.getCharacterPosition().getY();
        var directionsText = "Direction: " + playerCharacter.getCharacterDirection();
        
        coordinatesLabel.setText(coordinatesText + " - " + directionsText);
    }

    private void movePlayerRight() {
        for(int x = 0; x < StageConstants.PlayerCharacterSpeed / StageConstants.SpeedReducer; x++) {
            if(playerCharacter.moveRight(StageConstants.PlayerCharacterSpeed - (x *  StageConstants.SpeedReducer))) {
                return;
            };
        } 
    }

    private void movePlayerLeft() {
        for(int x = 0; x < StageConstants.PlayerCharacterSpeed / StageConstants.SpeedReducer; x++) {
            if(playerCharacter.moveLeft(StageConstants.PlayerCharacterSpeed - (x *  StageConstants.SpeedReducer))) {
                return;
            };
        } 
    }

    private void movePlayerDown() {
        for(int x = 0; x < StageConstants.PlayerCharacterSpeed / StageConstants.SpeedReducer; x++) {
            if(playerCharacter.moveDown(StageConstants.PlayerCharacterSpeed - (x *  StageConstants.SpeedReducer))) {
                return;
            };
        } 
    }

    private void movePlayerUp() {
        for(int x = 0; x < StageConstants.PlayerCharacterSpeed / StageConstants.SpeedReducer; x++) {
            if(playerCharacter.moveUp(StageConstants.PlayerCharacterSpeed - (x *  StageConstants.SpeedReducer))) {
                return;
            };
        } 
    }
    

    @FXML
    private void startAnimation() {
        timeline.play();
    }
}