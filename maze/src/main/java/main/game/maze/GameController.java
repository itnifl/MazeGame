package main.game.maze;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import main.game.maze.actions.GameOverAction;
import main.game.maze.characters.GhostCharacter;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.characters.interfaces.IMovingComputerCharacter;
import main.game.maze.constants.StageConstants;

public class GameController implements Initializable {
    @FXML private Button startButton;
    private Timeline timeline;

    @FXML private Node ghost1;
    @FXML private Node ghost2;
    @FXML private Node player;
    @FXML private Pane gameBoard;
    @FXML private Label coordinatesLabel;
    
    
    private PlayerCharacter playerCharacter;
    private GhostCharacter ghostCharacter1;
    private GhostCharacter ghostCharacter2;
    private MazeWorld maze;
    private GameOverAction gameOverAction;

    private Thread runComputerCharactersThread;
    private static List<IMovingComputerCharacter> allComputerCharacters;


    private static Task runComputerCharacters = new Task() {

        @Override
        protected Boolean call() throws Exception {
            try {
                do {
                    for(var computerCharacter : allComputerCharacters) {
                        var successfulMove = computerCharacter.move();
                        if(!successfulMove) {
                            computerCharacter.changeDirection();
                        }
                    }
                    Thread.sleep(60);
                } while (true);
            } catch (Exception ex) {
                throw ex;
            }
        }
        
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allComputerCharacters = new ArrayList<IMovingComputerCharacter>();
        gameOverAction = new GameOverAction();

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

        playerCharacter.addDeathNotificationSubscriber(gameOverAction);

        ghostCharacter1 = new GhostCharacter(ghost1, ghost1.getLayoutX(), ghost1.getLayoutY());
        allComputerCharacters.add(ghostCharacter1);

        ghostCharacter2 = new GhostCharacter(ghost2, ghost2.getLayoutX(), ghost2.getLayoutY());
        allComputerCharacters.add(ghostCharacter2);

        playerCharacter.addPositionSubscriber(ghostCharacter1);
        playerCharacter.addPositionSubscriber(ghostCharacter2);
        ghostCharacter1.addPositionSubscriber(playerCharacter);
        ghostCharacter2.addPositionSubscriber(playerCharacter);

        player.requestFocus();
        gameBoard.requestFocus();

        runComputerCharactersThread = new Thread(runComputerCharacters);
        runComputerCharactersThread.start();

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
                break;
            case LEFT:
                movePlayerLeft();
                break;
            case RIGHT:
                movePlayerRight();
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
    private void startNewMaze() {
        timeline.play();
        maze.GenerateMaze();
        //TODO: Wipe all vectors from board and draw again
    }
}