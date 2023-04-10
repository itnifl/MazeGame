package main.game.maze;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import main.game.maze.actions.GameOverAction;
import main.game.maze.actions.WinGameAction;
import main.game.maze.areas.WinArea;
import main.game.maze.characters.GhostCharacter;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.characters.interfaces.IMovingComputerCharacter;
import main.game.maze.constants.StageConstants;

public class GameController implements Initializable {
    private Timeline timeline;

    @FXML
    private AnchorPane root;
    @FXML
    private Node ghost1;
    @FXML
    private Node ghost2;
    @FXML
    private Node ghost3;
    @FXML
    private Node ghost4;
    @FXML
    private Node ghost5;
    @FXML
    private Node ghost6;
    @FXML
    private Node player;
    @FXML
    private Pane gameBoard;
    @FXML
    private Label coordinatesLabel;
    @FXML
    private ProgressBar hpBar;
    @FXML
    private Node heart;

    private PlayerCharacter playerCharacter;
    private GhostCharacter ghostCharacter1;
    private GhostCharacter ghostCharacter2;
    private GhostCharacter ghostCharacter3;
    private GhostCharacter ghostCharacter4;
    private GhostCharacter ghostCharacter5;
    private GhostCharacter ghostCharacter6;

    private MazeWorld maze;
    private GameOverAction gameOverAction;
    private WinGameAction winGameAction;
    private WinArea winarea;

    private Thread runComputerCharactersThread;
    private static List<IMovingComputerCharacter> allComputerCharacters;

    private static Task runComputerCharacters = new Task() {

        @Override
        protected Boolean call() throws Exception {
            try {
                do {
                    for (var computerCharacter : allComputerCharacters) {
                        var successfulMove = computerCharacter.move();
                        if (!successfulMove) {
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
        hpBar.setProgress(1.0);
        allComputerCharacters = new ArrayList<IMovingComputerCharacter>();

        gameOverAction = new GameOverAction(root, () -> {
            runComputerCharacters.cancel();
        });

        winGameAction = new WinGameAction(root, () -> {
            runComputerCharacters.cancel();
        });

        // create a timeline with two key frames
        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(ghost1.opacityProperty(), 1.0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(ghost1.opacityProperty(), 0.0)),
                new KeyFrame(Duration.ZERO, new KeyValue(ghost2.opacityProperty(), 1.0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(ghost2.opacityProperty(), 0.0)));
        // set the cycle count to indefinite
        timeline.setCycleCount(Timeline.INDEFINITE);

        maze = MazeWorld.GetWorld();
        playerCharacter = new PlayerCharacter(
                player,
                player.getLayoutX(),
                player.getLayoutY(),
                hpBar);

        playerCharacter.addDeathNotificationSubscriber(gameOverAction);

        ghostCharacter1 = new GhostCharacter(ghost1, ghost1.getLayoutX(), ghost1.getLayoutY());
        allComputerCharacters.add(ghostCharacter1);

        ghostCharacter2 = new GhostCharacter(ghost2, ghost2.getLayoutX(), ghost2.getLayoutY());
        allComputerCharacters.add(ghostCharacter2);

        ghostCharacter3 = new GhostCharacter(ghost3, ghost3.getLayoutX(), ghost3.getLayoutY());
        allComputerCharacters.add(ghostCharacter3);

        ghostCharacter4 = new GhostCharacter(ghost4, ghost4.getLayoutX(), ghost4.getLayoutY());
        allComputerCharacters.add(ghostCharacter4);

        ghostCharacter5 = new GhostCharacter(ghost5, ghost5.getLayoutX(), ghost5.getLayoutY());
        allComputerCharacters.add(ghostCharacter5);

        ghostCharacter6 = new GhostCharacter(ghost6, ghost6.getLayoutX(), ghost6.getLayoutY());
        allComputerCharacters.add(ghostCharacter6);

        playerCharacter.addPositionSubscriber(ghostCharacter1);
        playerCharacter.addPositionSubscriber(ghostCharacter2);
        playerCharacter.addPositionSubscriber(ghostCharacter3);
        playerCharacter.addPositionSubscriber(ghostCharacter4);
        playerCharacter.addPositionSubscriber(ghostCharacter5);
        playerCharacter.addPositionSubscriber(ghostCharacter6);

        ghostCharacter1.addPositionSubscriber(playerCharacter);
        ghostCharacter2.addPositionSubscriber(playerCharacter);
        ghostCharacter3.addPositionSubscriber(playerCharacter);
        ghostCharacter4.addPositionSubscriber(playerCharacter);
        ghostCharacter5.addPositionSubscriber(playerCharacter);
        ghostCharacter6.addPositionSubscriber(playerCharacter);

        winarea = new WinArea(heart);
        winarea.addPositionSubscriber(playerCharacter);
        winarea.AddWinGameAction(winGameAction);

        playerCharacter.addPositionSubscriber(winarea);

        player.requestFocus();
        gameBoard.requestFocus();

        runComputerCharactersThread = new Thread(runComputerCharacters);
        runComputerCharactersThread.start();
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

        var coordinatesText = "X: " + playerCharacter.getCharacterPosition().getX() + ", Y: "
                + playerCharacter.getCharacterPosition().getY();
        var directionsText = "Direction: " + playerCharacter.getCharacterDirection();

        coordinatesLabel.setText(coordinatesText + " - " + directionsText);
    }

    private void movePlayerRight() {
        for (int x = 0; x < StageConstants.PlayerCharacterSpeed / StageConstants.SpeedReducer; x++) {
            if (playerCharacter.moveRight(StageConstants.PlayerCharacterSpeed - (x * StageConstants.SpeedReducer))) {
                return;
            }
            ;
        }
    }

    private void movePlayerLeft() {
        for (int x = 0; x < StageConstants.PlayerCharacterSpeed / StageConstants.SpeedReducer; x++) {
            if (playerCharacter.moveLeft(StageConstants.PlayerCharacterSpeed - (x * StageConstants.SpeedReducer))) {
                return;
            }
            ;
        }
    }

    private void movePlayerDown() {
        for (int x = 0; x < StageConstants.PlayerCharacterSpeed / StageConstants.SpeedReducer; x++) {
            if (playerCharacter.moveDown(StageConstants.PlayerCharacterSpeed - (x * StageConstants.SpeedReducer))) {
                return;
            }
            ;
        }
    }

    private void movePlayerUp() {
        for (int x = 0; x < StageConstants.PlayerCharacterSpeed / StageConstants.SpeedReducer; x++) {
            if (playerCharacter.moveUp(StageConstants.PlayerCharacterSpeed - (x * StageConstants.SpeedReducer))) {
                return;
            }
            ;
        }
    }
}