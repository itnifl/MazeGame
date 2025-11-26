package main.game.maze;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import main.game.maze.actions.GameOverAction;
import main.game.maze.actions.HighscoreAction;
import main.game.maze.actions.WinGameAction;
import main.game.maze.areas.WinArea;
import main.game.maze.characters.ComputerCharacter;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;
import main.game.maze.characters.interfaces.IMovingComputerCharacter;
import main.game.maze.characters.interfaces.INonTangientMazeGameCharacter;
import main.game.maze.constants.StageConstants;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.runtime.opponents.OpponentRuntimeFactory;
import main.game.maze.service.CharacterIntersectionFixerService;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameController implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private Node player;
    @FXML
    private Pane gameBoard;
    @FXML
    private Label coordinatesLabel;
    @FXML
    private ProgressBar hpBar;
    @FXML
    private Label mouseCoordsLabel;
    @FXML
    private Node heart;
    @FXML
    private Label scoreLabel;

    private PlayerCharacter playerCharacter;
    private MazeWorld maze;
    private GameOverAction gameOverAction;
    private WinGameAction winGameAction;
    private WinArea winarea;
    private Thread runComputerCharactersThread;
    private final List<IMovingComputerCharacter> allComputerCharacters = new ArrayList<>();
    private final AtomicInteger playerMoveCount = new AtomicInteger(0);

    private static Task runComputerCharacters;
    private Difficulty startDifficulty; // <-- injected by StartController
    public void setStartDifficulty(Difficulty d) { this.startDifficulty = d; }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        javafx.application.Platform.runLater(() -> {
            if (gameBoard != null) {
                gameBoard.requestFocus();
            }
        });
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
            case H:
                showHighScore();
                break;
            case ESCAPE:
                openDifficultyPickerAndMaybeRestart();
            break;
            default:
                break;
        }

        var coordinatesText = "X: " + playerCharacter.getCharacterPosition().getX() + ", Y: "
                + playerCharacter.getCharacterPosition().getY();
        var directionsText = "Direction: " + playerCharacter.getCharacterDirection();

        coordinatesLabel.setText(coordinatesText + " - " + directionsText);

        playerMoveCount.getAndIncrement();

        gameOverAction.updateScore();
        var score = winGameAction.updateScore();

        scoreLabel.setText("Score: " + String.valueOf(score));
    }

    private void openDifficultyPickerAndMaybeRestart() {
        var window = (root != null && root.getScene() != null) ? root.getScene().getWindow() : null;

        App.pickDifficulty(window).ifPresent(chosen -> {
            // Offer to restart now with the chosen difficulty
            var confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Restart required");
            confirm.setHeaderText("Restart with " + App.displayName(chosen) + " difficulty now?");
            confirm.setContentText("Choose OK to restart, or Cancel to keep playing and apply on next restart.");
            if (window != null) confirm.initOwner(window);

            var res = confirm.showAndWait();
            if (res.isPresent() && res.get() == ButtonType.OK) {
                // Restart will inject App.lastChosenDifficulty in RestartGameAction
                new main.game.maze.actions.RestartGameAction(root).Load();
            } else {
                // Keep playing, but remember for the next restart in this session, too
                this.setStartDifficulty(chosen);
                App.lastChosenDifficulty = chosen;
            }
        });
    }

    @FXML
    private void handleMouseClicked(MouseEvent event) {
        System.out.println("Game has been clicked");
        mouseCoordsLabel.setText("X: " + event.getX() + ", Y: " + event.getY());
    }

    @FXML
    private void showHighScore() {
        runComputerCharacters.cancel();
        HighscoreAction action = new HighscoreAction(root);
        action.Load();
    }

    private void movePlayerRight() {
        for (int x = 0; x < StageConstants.PlayerCharacterSpeed / StageConstants.SpeedReducer; x++) {
            if (playerCharacter.moveRight(StageConstants.PlayerCharacterSpeed - (x * StageConstants.SpeedReducer), false)) {
                return;
            }
        }
    }

    private void movePlayerLeft() {
        for (int x = 0; x < StageConstants.PlayerCharacterSpeed / StageConstants.SpeedReducer; x++) {
            if (playerCharacter.moveLeft(StageConstants.PlayerCharacterSpeed - (x * StageConstants.SpeedReducer), false)) {
                return;
            }            
        }
    }

    private void movePlayerDown() {
        for (int x = 0; x < StageConstants.PlayerCharacterSpeed / StageConstants.SpeedReducer; x++) {
            if (playerCharacter.moveDown(StageConstants.PlayerCharacterSpeed - (x * StageConstants.SpeedReducer), false)) {
                return;
            }            
        }
    }

    private void movePlayerUp() {
        for (int x = 0; x < StageConstants.PlayerCharacterSpeed / StageConstants.SpeedReducer; x++) {
            if (playerCharacter.moveUp(StageConstants.PlayerCharacterSpeed - (x * StageConstants.SpeedReducer), false)) {
                return;
            }        
        }
    }

    public void setupGame() {
        hpBar.setProgress(1.0);

        maze = MazeWorld.GetWorld();
        playerCharacter = new PlayerCharacter(
                player,
                player.getLayoutX(),
                player.getLayoutY(),
                hpBar);

        var vectors = maze.getMazeVectors();

        // Create a canvas
        var canvas = this.drawCanvas(vectors);
        root.getChildren().add(canvas);

        gameOverAction = new GameOverAction(playerCharacter, playerMoveCount, root, () -> {
            runComputerCharacters.cancel();
        });

        winGameAction = new WinGameAction(playerCharacter, playerMoveCount, root, () -> {
            runComputerCharacters.cancel();
        });

        playerCharacter.addDeathNotificationSubscriber(gameOverAction);


        winarea = new WinArea(heart);
        winarea.addPositionSubscriber(playerCharacter);
        winarea.AddWinGameAction(winGameAction);

        playerCharacter.addPositionSubscriber(winarea);

        player.requestFocus();
        gameBoard.requestFocus();
        
        if (startDifficulty != null) {
            OpponentRuntimeFactory.instantiateFromModel(this, startDifficulty);
        } else {
            OpponentRuntimeFactory.instantiateFromModel(this); 
        }

        runComputerCharacters();
        javafx.application.Platform.runLater(() -> {
            var node = root.lookup("#heart");
            if (node instanceof javafx.scene.image.ImageView heart) {
                double heartW = heart.getBoundsInLocal().getWidth();
                double heartH = heart.getBoundsInLocal().getHeight();

                if (heartW <= 0) heartW = heart.getFitWidth();
                if (heartH <= 0) heartH = heart.getFitHeight();

                int width  = App.getBoardMaxX();
                int height = App.getBoardMaxY();
                heart.setLayoutX((width  - heartW) / 2.0);
                heart.setLayoutY((height - heartH) / 2.0);

                var characterIntersectionFixerService = new CharacterIntersectionFixerService(gameBoard, maze);
                characterIntersectionFixerService.fixInitialSpriteMazeIntersections();
            }
        });

        playerCharacter.setHitPoints(100);
        var score = winGameAction.resetScore();
        scoreLabel.setText("Score: " + String.valueOf(score));

        // Ensure the board is the main focus owner for key events
        gameBoard.setFocusTraversable(true);
        gameBoard.requestFocus();
    }

    public Canvas drawCanvas(List<Vector2D> vectors) {
        Canvas canvas = new Canvas(App.getBoardMaxX(), App.getBoardMaxY());
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Set the stroke color and width
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);

        // Draw the maze vectors
        for (Vector2D vector : vectors) {
            double startX = vector.getStart().getX();
            double startY = vector.getStart().getY();
            double endX = vector.getEnd().getX();
            double endY = vector.getEnd().getY();

            gc.strokeLine(startX, startY, endX, endY);
        }

        return canvas;
    }

    public void runComputerCharacters() {
        if (runComputerCharactersThread != null) {
            runComputerCharacters.cancel();
        }
        if (runComputerCharacters != null) {
            runComputerCharacters.cancel();
        }

        runComputerCharacters = new Task() {
            @Override
            protected Boolean call() throws Exception {
                try {
                    do {
                        for (var computerCharacter : allComputerCharacters) {
                            if(computerCharacter instanceof ComputerCharacter cc) {
                                BehaviorType characterBehavior = cc.getCharacterBehaviour();
                                //TODO: Implement other behaviours
                                switch (characterBehavior) {
                                    case WANDER:
                                            doCharacterWanderMove(computerCharacter);
                                        break;
                                
                                    default:
                                        doCharacterWanderMove(computerCharacter);
                                        break;
                                }                                                            
                            }                            
                        }
                        Thread.sleep(60);
                    } while (true);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    throw ex;
                }
            }
        };
        runComputerCharactersThread = new Thread(runComputerCharacters);
        runComputerCharactersThread.start();
    }

    private void doCharacterWanderMove(IMovingComputerCharacter computerCharacter) {
        var nonTangient = false;
        if(computerCharacter instanceof INonTangientMazeGameCharacter nontangientcc) {
            nonTangient = doNonTangientEnergyCalculation(nontangientcc);                                     
        }

        var successfulMove = computerCharacter.move(nonTangient);
        if (!successfulMove) {
            computerCharacter.changeDirection();
        }
    }

    /* Non-Tangient Energy Calculation - returns true if there still is non-tangient energy left */
    private boolean doNonTangientEnergyCalculation(INonTangientMazeGameCharacter nontangientcc) {
            var energy = nontangientcc.getNonTangientEnergy();
            boolean nonTangient = energy > 0; 
            
            final int maxEnergy = 100;  
            final double noneOpacityValue = 1; 
            final double minOpacityValue = 0.1;
            final double energyDecreaseValue = 0.14;
            final int maxRandomValue = 10;
            final double randomTangientMoveThreshold = 7;

            if(nonTangient) { 
                nontangientcc.setCharacterOpacity(noneOpacityValue-(energy/maxEnergy)+minOpacityValue);     
                nontangientcc.setNonTangientEnergy(energy-energyDecreaseValue);   
            } 

            if((int)(Math.random() * maxRandomValue) >= randomTangientMoveThreshold) {
                nonTangient = false;
            }  
            return nonTangient; 
    }

    public void registerComputerCharacter(IMovingComputerCharacter character, Node node) {
        // must be called on JavaFX thread
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> registerComputerCharacter(character, node));
            return;
        }
        gameBoard.getChildren().add(node);               // add sprite to board
        allComputerCharacters.add(character);     
        if(character instanceof ICanSubscribeAndNotifyPosition){
            playerCharacter.addPositionSubscriber((ICanSubscribeAndNotifyPosition)character);
            ((ICanSubscribeAndNotifyPosition)character).addPositionSubscriber(playerCharacter);
        }
    }

    public void unregisterComputerCharacter(ICanSubscribeAndNotifyPosition character, Node node) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> unregisterComputerCharacter(character,node));
            return;
        }
        allComputerCharacters.remove(character);
        playerCharacter.removePositionSubscriber(character);
        gameBoard.getChildren().remove(node);
    }

    public void dispose() {
        // stop background loop
        if (runComputerCharacters != null) runComputerCharacters.cancel();
        if (runComputerCharactersThread != null) runComputerCharactersThread.interrupt();

        // detach any cross-subscriptions
        if (winarea != null && playerCharacter != null) {
            playerCharacter.removePositionSubscriber(winarea);
        }
        if (playerCharacter != null) {
            playerCharacter.dispose();
        }
    }

    public void showInfectionWarning() {
        //TODO: Player is now infected, make sure this is properly communicated to the player
    }
}