package main.game.maze;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.HardDifficulty;
import main.game.maze.difficulties.NormalDifficulty;
import main.game.maze.generated.WallRegistry;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.mazeworld.service.MazeNavigationGraphService;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.runtime.opponents.OpponentRuntimeFactory;
import main.game.maze.service.CharacterIntersectionFixerService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameController implements Initializable {
    private static final Logger LOGGER = Logger.getLogger(GameController.class.getName());

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
    private GameMazeWorld maze;
    private GameOverAction gameOverAction;
    private WinGameAction winGameAction;
    private WinArea winarea;
    private Thread runComputerCharactersThread;
    private final List<IMovingComputerCharacter> allComputerCharacters = new ArrayList<>();
    private final AtomicInteger playerMoveCount = new AtomicInteger(0);
    private Canvas pathCanvas;
    private Canvas treeCanvas;

    private static Task runComputerCharacters;
    private Difficulty startDifficulty; 

    // Mapping vectors to their visual definition as requested
    private final Map<Vector2D, WallRegistry.WallDefinition> vectorWallMap = new HashMap<>();
    // Cache for loaded images to avoid IO lag during draw
    private final Map<String, Image> wallImageCache = new HashMap<>();

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
    private void handleKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.P) {
            clearNavigationPath();
        } else if (event.getCode() == KeyCode.O) {
            clearSpanningTree();
        }
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
            case P:   
                showNavigationPath();
                break;
            case O: 
                showSpanningTree();
            break;
            default:
                break;
        }
        updateDebugLabels();
    }

    private void updateDebugLabels() {
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
            var confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Restart required");
            confirm.setHeaderText("Restart with " + App.displayName(chosen) + " difficulty now?");
            confirm.setContentText("Choose OK to restart, or Cancel to keep playing and apply on next restart.");
            if (window != null) confirm.initOwner(window);

            var res = confirm.showAndWait();
            if (res.isPresent() && res.get() == ButtonType.OK) {
                new main.game.maze.actions.RestartGameAction(root).Load();
            } else {
                this.setStartDifficulty(chosen);
                App.lastChosenDifficulty = chosen;
            }
        });
    }

    @FXML
    private void handleMouseClicked(MouseEvent event) {
        LOGGER.fine("Game has been clicked");
        mouseCoordsLabel.setText("X: " + event.getX() + ", Y: " + event.getY());
    }

    @FXML
    private void showHighScore() {
        if(runComputerCharacters != null) runComputerCharacters.cancel();
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

        updateBoardBackground();

        maze = GameMazeWorld.GetWorld(App.getBoardMaxX(), App.getBoardMaxY());
        playerCharacter = new PlayerCharacter(
                player,
                player.getLayoutX(),
                player.getLayoutY(),
                hpBar);

        var vectors = maze.getMazeVectors();

        // Create a canvas
        var canvas = this.drawCanvas(vectors);
        root.getChildren().add(canvas);

        pathCanvas = new Canvas(App.getBoardMaxX(), App.getBoardMaxY());
        root.getChildren().add(pathCanvas);

        treeCanvas = new Canvas(App.getBoardMaxX(), App.getBoardMaxY());
        root.getChildren().add(treeCanvas);

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
            if (node instanceof javafx.scene.image.ImageView heartView) {
                double heartW = heartView.getBoundsInLocal().getWidth();
                double heartH = heartView.getBoundsInLocal().getHeight();

                if (heartW <= 0) heartW = heartView.getFitWidth();
                if (heartH <= 0) heartH = heartView.getFitHeight();

                int width  = App.getBoardMaxX();
                int height = App.getBoardMaxY();
                heartView.setLayoutX((width  - heartW) / 2.0);
                heartView.setLayoutY((height - heartH) / 2.0);

                var characterIntersectionFixerService = new CharacterIntersectionFixerService(gameBoard, maze);
                characterIntersectionFixerService.fixInitialSpriteMazeIntersections();
            }
        });

        playerCharacter.setHitPoints(100);
        var score = winGameAction.resetScore();
        scoreLabel.setText("Score: " + String.valueOf(score));

        gameBoard.setFocusTraversable(true);
        gameBoard.requestFocus();
    }

    private void updateBoardBackground() {
        String bgImageName = "gameBackGround1.png"; // Default / Easy
        
        if (startDifficulty instanceof HardDifficulty) {
            bgImageName = "gameBackGround3.png";
        } else if (startDifficulty instanceof NormalDifficulty) {
            bgImageName = "gameBackGround2.png";
        }
        
        try {
            var url = getClass().getResource(bgImageName);
            if (url != null) {
                Image bgImage = new Image(url.toExternalForm());
                BackgroundImage bi = new BackgroundImage(bgImage,
                    BackgroundRepeat.REPEAT,
                    BackgroundRepeat.REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
                
                gameBoard.setBackground(new Background(bi));
            } else {
                LOGGER.warning("Could not find background image: " + bgImageName);
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error loading background", e);
        }
    }

    /**
     * Draws the maze using WallRegistry definitions.
     * Horizontal vectors are rotated because the base image is vertical.
     */
    public Canvas drawCanvas(List<Vector2D> vectors) {
        Canvas canvas = new Canvas(App.getBoardMaxX(), App.getBoardMaxY());
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear previous mappings
        vectorWallMap.clear();

        // Configuration for drawing
        double wallWidth = 5.0; // Defined in requirements
        double wallLength = StageConstants.WallSegmentLengthPx; // Defined in requirements
        
        // Use WOOD_BASIC as default for now, or fetch from logic if available

        var wallType = WallRegistry.get("DIRT_BASIC");
        if(startDifficulty instanceof HardDifficulty) {
            wallType = WallRegistry.get("STEEL_SOLID");
        } else if(startDifficulty instanceof NormalDifficulty) {
            wallType = WallRegistry.get("WOOD_BASIC");
        }
        
        // Ensure image is loaded
        Image wallImage = getOrLoadImage(wallType);

        for (Vector2D vector : vectors) {
            // Register mapping: Vector -> Graphic/Wall Definition
            vectorWallMap.put(vector, wallType);

            double startX = vector.getStart().getX();
            double startY = vector.getStart().getY();
            double endX = vector.getEnd().getX();
            double endY = vector.getEnd().getY();

            // Determine if horizontal or vertical
            boolean isHorizontal = Math.abs(endY - startY) < 0.001;

            if (wallImage == null) {
                // Fallback to black lines if image not found
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(wallWidth);
                gc.strokeLine(startX, startY, endX, endY);
            } else {
                if (!isHorizontal) {
                    // VERTICAL
                    // We draw the image centered on the vector's X.
                    // The height of the drawing is explicitly wallLength.
                    double drawX = startX - (wallWidth / 2.0);
                    double drawY = Math.min(startY, endY);
                    
                    gc.drawImage(wallImage, drawX, drawY, wallWidth, wallLength);
                } else {
                    // HORIZONTAL
                    // Rotate 90 degrees to draw horizontal using the vertical image.
                    double minX = Math.min(startX, endX);
                    
                    // Center of the wall segment
                    double centerX = minX + (Math.abs(endX - startX) / 2.0); 
                    double centerY = startY; 

                    gc.save();
                    gc.translate(centerX, centerY);
                    gc.rotate(90);
                    
                    // Draw centered at (0,0) after rotation.
                    // wallWidth is drawn along local X (screen width), wallLength along local Y (screen height).
                    // After 90 deg rotation, local Y becomes global X (horizontal length).
                    gc.drawImage(wallImage, -wallWidth / 2.0, -wallLength / 2.0, wallWidth, wallLength);
                    
                    gc.restore();
                }
            }
        }

        return canvas;
    }
    
    private Image getOrLoadImage(WallRegistry.WallDefinition def) {
        if (def == null || def.baseImage == null) return null;
        
        if (!wallImageCache.containsKey(def.id)) {
            try {
                // Load resource from classpath
                var url = getClass().getResource(def.baseImage);
                if (url != null) {
                    wallImageCache.put(def.id, new Image(url.toExternalForm()));
                } else {
                    LOGGER.warning("Could not find wall image: " + def.baseImage);
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return wallImageCache.get(def.id);
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
                                switch (characterBehavior) {
                                    case WANDER:
                                            doCharacterWanderMove(computerCharacter);
                                        break;
                                    case PATROL:
                                        	doCharacterPatrolMove(computerCharacter);
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
                    LOGGER.log(Level.SEVERE, "Error in computer character movement loop", ex);
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

    private void doCharacterPatrolMove(IMovingComputerCharacter computerCharacter) {
        var nonTangient = false;
        if(computerCharacter instanceof INonTangientMazeGameCharacter nontangientcc) {
            nonTangient = doNonTangientEnergyCalculation(nontangientcc);                                     
        }
        var direction = PatrolController.getDirectionToNextPatrolPoint(computerCharacter); //If there is a wall in the way to the point, we adjust to next best possible. If the point is reached, we go to next
        computerCharacter.setDirection(direction);
        var successfulMove = computerCharacter.move(nonTangient);
        
        if (!successfulMove) {
            computerCharacter.changeDirection();
        }
    }

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
                var opacityEnergy = Math.min(maxEnergy, Math.max(0d, energy));
                nontangientcc.setCharacterOpacity(noneOpacityValue-(opacityEnergy/maxEnergy)+minOpacityValue);
                nontangientcc.setNonTangientEnergy(Math.max(0d, energy-energyDecreaseValue));
            } 

            if((int)(Math.random() * maxRandomValue) >= randomTangientMoveThreshold) {
                nonTangient = false;
            }  
            return nonTangient; 
    }

    public void registerComputerCharacter(IMovingComputerCharacter character, Node node) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> registerComputerCharacter(character, node));
            return;
        }
        gameBoard.getChildren().add(node);
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

    public void showInfectionWarning() {
        // Implementation for infection warning
    }

    private void showNavigationPath() {
        if (maze == null || pathCanvas == null || heart == null || playerCharacter == null) {
            return;
        }

        var navGraph = maze.getNavigationGraph();
        if (navGraph == null) {
            return;
        }

        Point2D start = new Point2D(
                playerCharacter.getCharacterPosition().getX(),
                playerCharacter.getCharacterPosition().getY()
        );

        double heartW = heart.getBoundsInLocal().getWidth();
        double heartH = heart.getBoundsInLocal().getHeight();
        double hx = heart.getLayoutX() + heartW / 2.0;
        double hy = heart.getLayoutY() + heartH / 2.0;
        Point2D goal = new Point2D(hx, hy);

        var path = MazeNavigationGraphService.findPath(navGraph, start, goal);
        if (path == null || path.size() < 2) {
            clearNavigationPath();
            return;
        }

        GraphicsContext gc = pathCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, pathCanvas.getWidth(), pathCanvas.getHeight());

        gc.setLineWidth(8.0);
        gc.setStroke(Color.DODGERBLUE);
        gc.setGlobalAlpha(0.6);

        Point2D prev = path.get(0);
        for (int i = 1; i < path.size(); i++) {
            Point2D p = path.get(i);
            gc.strokeLine(prev.getX(), prev.getY(), p.getX(), p.getY());
            prev = p;
        }

        gc.setGlobalAlpha(1.0); 
    }

    private void clearNavigationPath() {
        if (pathCanvas == null) {
            return;
        }
        GraphicsContext gc = pathCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, pathCanvas.getWidth(), pathCanvas.getHeight());
    }

    private void showSpanningTree() {
        if (maze == null || treeCanvas == null || playerCharacter == null) {
            return;
        }

        var navGraph = maze.getNavigationGraph();
        if (navGraph == null) {
            return;
        }

        Point2D playerPos = new Point2D(
                playerCharacter.getCharacterPosition().getX(),
                playerCharacter.getCharacterPosition().getY()
        );
        MazeNavigationGraphService.rebuildSpanningTreeFrom(navGraph, playerPos);

        GraphicsContext gc = treeCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, treeCanvas.getWidth(), treeCanvas.getHeight());

        gc.setStroke(Color.RED);
        gc.setLineWidth(4.0);
        gc.setGlobalAlpha(0.6);

        var grid = navGraph.getGrid();
        int cols = navGraph.getCols();
        int rows = navGraph.getRows();

        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows; r++) {
                var node = grid[c][r];
                if (node == null) continue;
                var parent = node.getTreeParent();
                if (parent != null) {
                    gc.strokeLine(
                            node.getX(), node.getY(),
                            parent.getX(), parent.getY()
                    );
                }
            }
        }

        gc.setGlobalAlpha(1.0);
    }

    private void clearSpanningTree() {
        if (treeCanvas == null) {
            return;
        }
        GraphicsContext gc = treeCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, treeCanvas.getWidth(), treeCanvas.getHeight());
    }

    // Accessor for the map if needed by other components
    public Map<Vector2D, WallRegistry.WallDefinition> getVectorWallMap() {
        return vectorWallMap;
    }

    public void dispose() {
        if (runComputerCharacters != null) runComputerCharacters.cancel();
        if (runComputerCharactersThread != null) runComputerCharactersThread.interrupt();

        if (winarea != null && playerCharacter != null) {
            playerCharacter.removePositionSubscriber(winarea);
        }
        if (playerCharacter != null) {
            playerCharacter.dispose();
        }
    }
}