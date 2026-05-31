package main.game.maze;

import java.net.URL;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import main.game.maze.actions.GameOverAction;
import main.game.maze.actions.HighscoreAction;
import main.game.maze.actions.WinGameAction;
import main.game.maze.areas.WinArea;
import main.game.maze.characters.ComputerCharacter;
import main.game.maze.characters.GhostCharacter;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.characters.PumpkinBomberCharacter;
import main.game.maze.characters.ZombieCharacter;
import main.game.maze.characters.interfaces.ICanSubscribeAndNotifyPosition;
import main.game.maze.characters.interfaces.IMovingComputerCharacter;
import main.game.maze.characters.interfaces.INonTangientMazeGameCharacter;
import main.game.maze.config.model.PlayerConfig;
import main.game.maze.config.service.XmiRulesLoader;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import main.game.maze.common.graphics.config.PropertiesMazeVisualStyleLoader;
import main.game.maze.common.graphics.config.XmiMazeVisualStyleLoader;
import main.game.maze.common.movement.AntiLoopWanderMovementService;
import main.game.maze.common.movement.AdaptiveAggressiveMovementService;
import main.game.maze.common.movement.ActivePathPoint;
import main.game.maze.common.movement.EnemySpawnUnstuckService;
import main.game.maze.common.movement.EnemyState;
import main.game.maze.common.movement.GhostNonTangibilityService;
import main.game.maze.common.movement.GhostPhasingMovementService;
import main.game.maze.common.movement.MovementResult;
import main.game.maze.common.movement.PatrolMovementService;
import main.game.maze.common.movement.WorldView;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.EasyDifficulty;
import main.game.maze.difficulties.HardDifficulty;
import main.game.maze.generated.WallRegistry;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.common.terminal.TerminalCommand;
import main.game.maze.common.terminal.TerminalCommandParser;
import main.game.maze.mazeworld.WallCollisionUtil;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.mazeworld.service.MazeNavigationGraphService;
import main.game.maze.constants.PlayerConstants;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.runtime.opponents.OpponentRuntimeFactory;
import main.game.maze.service.CharacterIntersectionFixerService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameController implements Initializable {
    private static final Logger LOGGER = Logger.getLogger(GameController.class.getName());
    private static final MazeVisualStyleConfig VISUAL_STYLE = loadVisualStyle();

    @FXML
    private AnchorPane root;
    @FXML
    private ImageView player;
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
    @FXML
    private AnchorPane scoreHudContainer;
    @FXML
    private AnchorPane bottomMenuContainer;
    @FXML
    private AnchorPane commandsOverlay;
    @FXML
    private Button commandsMenuButton;
    @FXML
    private Button terminalMenuButton;
    @FXML
    private Label pathHintTimerLabel;

    private static final String COMMANDS_BUTTON_STYLE = "-fx-background-color: rgba(143,255,224,0.85); -fx-text-fill: #103630; -fx-font-family: 'Consolas'; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 4;";
    private static final String COMMANDS_BUTTON_PRESSED_STYLE = "-fx-background-color: rgba(115,215,189,0.95); -fx-text-fill: #0a2924; -fx-font-family: 'Consolas'; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 4;";
    private static final String TERMINAL_BUTTON_STYLE = "-fx-background-color: rgba(255,229,110,0.90); -fx-text-fill: #2f1d00; -fx-font-family: 'Consolas'; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 4;";
    private static final String TERMINAL_BUTTON_PRESSED_STYLE = "-fx-background-color: rgba(232,197,79,0.95); -fx-text-fill: #251700; -fx-font-family: 'Consolas'; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 4;";
    private static final Duration ENEMY_LABEL_DURATION = Duration.seconds(20);
    private static final double ENEMY_LABEL_Y_OFFSET = 14.0;

    private PlayerCharacter playerCharacter;
    private GameMazeWorld maze;
    private GameOverAction gameOverAction;
    private WinGameAction winGameAction;
    private WinArea winarea;
    private Thread runComputerCharactersThread;
    private final List<IMovingComputerCharacter> allComputerCharacters = new CopyOnWriteArrayList<>();
    private final AtomicInteger playerMoveCount = new AtomicInteger(0);
    private Canvas pathCanvas;
    private Canvas treeCanvas;
    private Canvas mazeCanvas;

    private static Task<Boolean> runComputerCharacters;
    private Difficulty startDifficulty; 

    // Mapping vectors to their visual definition as requested
    private final Map<Vector2D, WallRegistry.WallDefinition> vectorWallMap = new HashMap<>();
    // Cache for loaded images to avoid IO lag during draw
    private final Map<String, Image> wallImageCache = new HashMap<>();

    // Key state tracking for smooth movement
    private final Set<KeyCode> pressedKeys = EnumSet.noneOf(KeyCode.class);
    private AnimationTimer movementTimer;
    private long lastMoveTime = 0;
    private static final long MOVE_INTERVAL_NANOS = 33_000_000L; // ~30 moves per second
    private int playerMovementSpeed = StageConstants.PlayerCharacterSpeed;
    private static final double ROUTE_HINT_PENALTY_PER_MS = 0.05;
    private static final long OPPONENT_THREAD_JOIN_TIMEOUT_MS = 200L;

    // Path-hint energy budget (per difficulty, in seconds)
    private static final long PATH_HINT_BUDGET_EASY_NANOS  = 45L * 1_000_000_000L;
    private static final long PATH_HINT_BUDGET_NORMAL_NANOS = 25L * 1_000_000_000L;
    private static final long PATH_HINT_BUDGET_HARD_NANOS  = 15L * 1_000_000_000L;
    /** Total nanoseconds of path-hint budget consumed this game. */
    private long pathHintTotalUsedNanos = 0L;
    /** Nanos when the P key was most recently pressed (0 if not held). */
    private long pathHintPressStartNanos = 0L;
    /** True while P is physically held down and a budget press is in progress. */
    private boolean pathHintKeyDown = false;
    /** Timeline that ticks every 100ms while P is held to update the countdown label. */
    private Timeline pathHintCountdownTicker;
    /** Auto-clears the "energy already spent" message after a short delay. */
    private PauseTransition pathHintExhaustedClearTimer;
    /** Nanos of the most recent movement-loop heartbeat; used by the watchdog. */
    private final AtomicLong lastMovementLoopNanos = new AtomicLong(0L);
    /** Fires every second while the movement thread should be alive. */
    private Timeline movementWatchdogTimer;
    /** If no heartbeat is seen within this window the thread is considered stalled. */
    private static final long MOVEMENT_STALL_THRESHOLD_NANOS = 6_000_000_000L; // 6 s
    private boolean isRouteHintVisible = false;
    private long lastRouteHintPenaltyNanos = 0L;
    private double routeHintPenaltyAccumulator = 0.0;
    private int routeHintPenaltyPoints = 0;
    private Rectangle gameBoardClip;
    private boolean cameraFollowListenersInstalled;
    private VBox infectionWarningSign;
    private PauseTransition infectionWarningHideTimer;
    private final Map<Node, Timeline> infectiousMists = new HashMap<>();
    private final List<Node> activeEnemyDebugLabels = new CopyOnWriteArrayList<>();
    private PauseTransition enemyDebugLabelHideTimer;
    private PauseTransition hudMessageClearTimer;
    private final AntiLoopWanderMovementService antiLoopWanderMovementService = new AntiLoopWanderMovementService();
    private final AdaptiveAggressiveMovementService adaptiveAggressiveMovementService = new AdaptiveAggressiveMovementService();
    private final PatrolMovementService patrolMovementService = new PatrolMovementService();
    private final GhostPhasingMovementService ghostPhasingMovementService = new GhostPhasingMovementService();
    private AnimationTimer enemyPathOverlayTimer;
    private boolean enemyPathOverlayVisible;
    private long enemyPathOverlayHideAtNanos;

    public void setStartDifficulty(Difficulty d) { this.startDifficulty = d; }

    int getBaseScoreForCurrentDifficulty() {
        return main.game.maze.common.scoring.GameScoringConstants.baseScoreFor(startDifficulty);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        javafx.application.Platform.runLater(() -> {
            installBottomButtonPressEffects();
            if (gameBoard != null) {
                gameBoard.requestFocus();
            }
        });
    }

    private void installBottomButtonPressEffects() {
        installButtonPressEffect(commandsMenuButton, COMMANDS_BUTTON_STYLE, COMMANDS_BUTTON_PRESSED_STYLE);
        installButtonPressEffect(terminalMenuButton, TERMINAL_BUTTON_STYLE, TERMINAL_BUTTON_PRESSED_STYLE);
    }

    private static void installButtonPressEffect(Button button, String normalStyle, String pressedStyle) {
        if (button == null) {
            return;
        }
        button.setStyle(normalStyle);
        button.setOnMousePressed(evt -> {
            button.setTranslateY(1.8);
            button.setStyle(pressedStyle);
        });
        button.setOnMouseReleased(evt -> {
            button.setTranslateY(0);
            button.setStyle(normalStyle);
        });
        button.setOnMouseExited(evt -> {
            button.setTranslateY(0);
            button.setStyle(normalStyle);
        });
    }
    
    @FXML
    private void handleKeyReleased(KeyEvent event) {
        pressedKeys.remove(event.getCode());
        if (event.getCode() == KeyCode.P) {
            clearNavigationPath();
        } else if (event.getCode() == KeyCode.O) {
            clearSpanningTree();
        }
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        KeyCode code = event.getCode();
        
        // Track movement keys for continuous movement
        if (code == KeyCode.UP || code == KeyCode.DOWN || 
            code == KeyCode.LEFT || code == KeyCode.RIGHT) {
            pressedKeys.add(code);
            return;  // Movement handled by AnimationTimer
        }
        
        // Handle instant action keys
        switch (code) {
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
    }

    private void updateDebugLabels() {
        if (playerCharacter == null || gameOverAction == null || winGameAction == null) {
            return;
        }
        var coordinatesText = "X: " + playerCharacter.getCharacterPosition().getX() + ", Y: "
                + playerCharacter.getCharacterPosition().getY();
        var directionsText = "Direction: " + playerCharacter.getCharacterDirection();

        coordinatesLabel.setText(coordinatesText + " - " + directionsText);

        playerMoveCount.getAndIncrement();

        gameOverAction.updateScore();
        var score = winGameAction.updateScore();
        updateScoreHud(score);
    }

    private void updateScoreHud(int score) {
        if (scoreLabel != null) {
            scoreLabel.setText("Score: " + score);
        }
        if (coordinatesLabel != null) {
            coordinatesLabel.setTextFill(Color.WHITE);
        }
        if (mouseCoordsLabel != null) {
            mouseCoordsLabel.setTextFill(Color.WHITE);
        }
    }

    public int getDynamicScorePenalty() {
        return routeHintPenaltyPoints;
    }

    /**
     * Returns {@code true} if any maze wall separates the straight line between
     * {@code (ex, ey)} (enemy centre) and {@code (px, py)} (player centre).
     * Used by character collision handlers to prevent damage through walls.
     * Returns {@code false} when the maze is not yet initialised.
     */
    public boolean isWallBetween(double ex, double ey, double px, double py) {
        if (maze == null) {
            return false;
        }
        var walls = maze.getMazeVectors();
        if (walls == null || walls.isEmpty()) {
            return false;
        }
        return WallCollisionUtil.wallBetweenVectors(ex, ey, px, py, walls);
    }

    private void applyRouteHintPenalty(long now) {
        if (!isRouteHintVisible) {
            lastRouteHintPenaltyNanos = now;
            return;
        }

        if (lastRouteHintPenaltyNanos == 0L) {
            lastRouteHintPenaltyNanos = now;
            return;
        }

        long elapsedNanos = now - lastRouteHintPenaltyNanos;
        if (elapsedNanos <= 0) {
            return;
        }
        lastRouteHintPenaltyNanos = now;

        double elapsedMs = elapsedNanos / 1_000_000.0;
        routeHintPenaltyAccumulator += elapsedMs * ROUTE_HINT_PENALTY_PER_MS;

        if (routeHintPenaltyAccumulator >= 1.0) {
            int penaltyToApply = (int) routeHintPenaltyAccumulator;
            routeHintPenaltyPoints += penaltyToApply;
            routeHintPenaltyAccumulator -= penaltyToApply;

            // Keep HUD score in sync even when the player is not moving.
            if (winGameAction != null) {
                var score = winGameAction.updateScore();
                updateScoreHud(score);
            }
        }
    }

    private void ensureHudLayersOnTop() {
        if (scoreHudContainer != null) {
            scoreHudContainer.setViewOrder(-20);
        }
        if (bottomMenuContainer != null) {
            bottomMenuContainer.setViewOrder(-20);
        }
        if (commandsOverlay != null) {
            commandsOverlay.setViewOrder(-30);
        }
    }

    @FXML
    private void toggleCommandsOverlay() {
        if (commandsOverlay == null) {
            return;
        }

        boolean show = !commandsOverlay.isVisible();
        commandsOverlay.setVisible(show);
        commandsOverlay.setManaged(show);

        if (!show && gameBoard != null) {
            gameBoard.requestFocus();
        }
    }

    @FXML
    private void hideCommandsOverlay() {
        if (commandsOverlay == null) {
            return;
        }

        commandsOverlay.setVisible(false);
        commandsOverlay.setManaged(false);

        if (gameBoard != null) {
            gameBoard.requestFocus();
        }
    }

    @FXML
    private void openTerminalPrompt() {
        var dialog = new TextInputDialog();
        dialog.setTitle("Maze Terminal");
        dialog.setHeaderText("Enter command");
        dialog.setContentText("/h, /showbehaviourtype, /showmovementtype, /showenemypath, /sep (shows enemy paths for 10 seconds)");
        var window = (root != null && root.getScene() != null) ? root.getScene().getWindow() : null;
        if (window != null) {
            dialog.initOwner(window);
        }

        dialog.showAndWait().ifPresent(this::executeTerminalCommand);
        if (gameBoard != null) {
            gameBoard.requestFocus();
        }
    }

    private void executeTerminalCommand(String raw) {
        TerminalCommand command = parseTerminalCommand(raw);
        switch (command) {
            case HELP -> setHudMessage("Commands: /h, /showbehaviourtype, /sbt, /showmovementtype, /smt, /showenemypath, /sep (shows enemy paths for 10 seconds)", Duration.seconds(20));
            case SHOW_BEHAVIOUR_TYPE -> {
                setHudMessage("Showing behaviour type above enemies");
                showEnemyDebugLabels(true);
            }
            case SHOW_MOVEMENT_TYPE -> {
                setHudMessage("Showing movement type above enemies");
                showEnemyDebugLabels(false);
            }
            case SHOW_ENEMY_PATH -> {
                setHudMessage("Showing enemy paths for 10 seconds");
                showEnemyPathsOverlay();
            }
            case EMPTY -> setHudMessage("No command entered");
            default -> setHudMessage("Unknown command. Use /h");
        }
    }

    private static TerminalCommand parseTerminalCommand(String raw) {
        return TerminalCommandParser.parse(raw);
    }

    private void showEnemyPathsOverlay() {
        enemyPathOverlayVisible = true;
        enemyPathOverlayHideAtNanos = System.nanoTime() + 10_000_000_000L;
        ensureEnemyPathOverlayTimer();
        refreshPathCanvasOverlay();
    }

    private void ensureEnemyPathOverlayTimer() {
        if (enemyPathOverlayTimer != null) {
            enemyPathOverlayTimer.start();
            return;
        }
        enemyPathOverlayTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now >= enemyPathOverlayHideAtNanos) {
                    enemyPathOverlayVisible = false;
                    stop();
                }
                refreshPathCanvasOverlay();
            }
        };
        enemyPathOverlayTimer.start();
    }

    private void showEnemyDebugLabels(boolean behaviourType) {
        clearEnemyDebugLabels();
        if (gameBoard == null) {
            return;
        }
        for (var cc : allComputerCharacters) {
            if (!(cc instanceof ComputerCharacter computerCharacter)) {
                continue;
            }
            Node enemyNode = computerCharacter.getCharacterGraphics();
            if (enemyNode == null) {
                continue;
            }
            String labelText = behaviourType
                    ? computerCharacter.getCharacterBehaviour().name()
                    : movementTypeName(computerCharacter);
            Label label = new Label(labelText);
            label.setMouseTransparent(true);
            label.setStyle("-fx-text-fill: #f3f9ff; -fx-font-family: 'Consolas'; -fx-font-size: 12px; -fx-font-weight: bold; "
                    + "-fx-background-color: rgba(0,0,0,0.45); -fx-padding: 2 4 2 4; -fx-background-radius: 3;");
            label.layoutXProperty().bind(enemyNode.layoutXProperty());
            label.layoutYProperty().bind(enemyNode.layoutYProperty().subtract(ENEMY_LABEL_Y_OFFSET));
            gameBoard.getChildren().add(label);
            activeEnemyDebugLabels.add(label);
        }

        if (enemyDebugLabelHideTimer == null) {
            enemyDebugLabelHideTimer = new PauseTransition(ENEMY_LABEL_DURATION);
            enemyDebugLabelHideTimer.setOnFinished(evt -> clearEnemyDebugLabels());
        }
        enemyDebugLabelHideTimer.stop();
        enemyDebugLabelHideTimer.playFromStart();
    }

    private String movementTypeName(ComputerCharacter character) {
        BehaviorType behaviour = character.getCharacterBehaviour();
        if (behaviour == BehaviorType.AGGRESSIVE) {
            String id = enemyRuntimeId(character);
            var mode = adaptiveAggressiveMovementService.modeForEnemy(id);
            if (mode == AdaptiveAggressiveMovementService.AggressiveMovementMode.PATH_FOLLOW) {
                return "AGGRESSIVE_PATH";
            }
            if (mode == AdaptiveAggressiveMovementService.AggressiveMovementMode.WANDER_RECOVERY) {
                return "AGGRESSIVE_WANDER";
            }
            return "AGGRESSIVE_CHASE";
        }
        if (behaviour == BehaviorType.PATROL) {
            String id = enemyRuntimeId(character);
            return patrolMovementService.modeForEnemy(id) == PatrolMovementService.PatrolMovementMode.WANDER_RECOVERY
                    ? "PATROL_WANDER"
                    : "PATROL_PATH";
        }
        if (behaviour == BehaviorType.PASSIVE) {
            return "WANDER";
        }
        return behaviour.name();
    }

    private void clearEnemyDebugLabels() {
        if (activeEnemyDebugLabels.isEmpty() || gameBoard == null) {
            return;
        }
        for (Node label : activeEnemyDebugLabels) {
            if (label != null) {
                label.layoutXProperty().unbind();
                label.layoutYProperty().unbind();
                gameBoard.getChildren().remove(label);
            }
        }
        activeEnemyDebugLabels.clear();
    }

    private void setHudMessage(String text) {
        if (hudMessageClearTimer != null) {
            hudMessageClearTimer.stop();
            hudMessageClearTimer = null;
        }
        if (mouseCoordsLabel != null) {
            mouseCoordsLabel.setText(text);
        }
    }

    private void setHudMessage(String text, Duration visibleFor) {
        setHudMessage(text);
        hudMessageClearTimer = new PauseTransition(visibleFor);
        hudMessageClearTimer.setOnFinished(e -> setHudMessage(""));
        hudMessageClearTimer.play();
    }

    private void openDifficultyPickerAndMaybeRestart() {
        var window = (root != null && root.getScene() != null) ? root.getScene().getWindow() : null;

        stopComputerCharacters();
        hideCommandsOverlay();

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
                runComputerCharacters();
                startMovementTimer();
                if (gameBoard != null) {
                    gameBoard.requestFocus();
                }
            }
        });

        if (runComputerCharactersThread == null && movementTimer == null) {
            runComputerCharacters();
            startMovementTimer();
            if (gameBoard != null) {
                gameBoard.requestFocus();
            }
        }
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

    private void stepPlayer(java.util.function.IntPredicate step) {
        int iterations = Math.max(1, playerMovementSpeed / StageConstants.SpeedReducer);
        for (int x = 0; x < iterations; x++) {
            if (step.test(playerMovementSpeed - (x * StageConstants.SpeedReducer))) {
                return;
            }
        }
    }

    private void movePlayerRight() { stepPlayer(speed -> playerCharacter.moveRight(speed, false)); }
    private void movePlayerLeft()  { stepPlayer(speed -> playerCharacter.moveLeft(speed, false)); }
    private void movePlayerDown()  { stepPlayer(speed -> playerCharacter.moveDown(speed, false)); }
    private void movePlayerUp()    { stepPlayer(speed -> playerCharacter.moveUp(speed, false)); }

    public void setupGame() {
        hpBar.setProgress(1.0);

        gameBoard.setPrefSize(App.getBoardMaxX(), App.getBoardMaxY());
        gameBoard.setMinSize(App.getBoardMaxX(), App.getBoardMaxY());
        gameBoard.setMaxSize(App.getBoardMaxX(), App.getBoardMaxY());
        installGameBoardClip();

        updateBoardBackground();

        maze = GameMazeWorld.GetWorld(App.getBoardMaxX(), App.getBoardMaxY());
    PlayerConfig playerConfig = loadPlayerConfig();
    playerMovementSpeed = Math.max(1, (int) Math.round(playerConfig.speed()));

        setPlayerBaseImage(player, playerConfig.imageBase());

        playerCharacter = new PlayerCharacter(
                player,
                player.getLayoutX(),
                player.getLayoutY(),
        hpBar,
        playerConfig);

        var vectors = maze.getMazeVectors();

        // Replace dynamic canvases to avoid growth and pulse-time list inconsistencies.
        if (mazeCanvas != null) {
            gameBoard.getChildren().remove(mazeCanvas);
            mazeCanvas = null;
        }
        if (pathCanvas != null) {
            gameBoard.getChildren().remove(pathCanvas);
            pathCanvas = null;
        }
        if (treeCanvas != null) {
            gameBoard.getChildren().remove(treeCanvas);
            treeCanvas = null;
        }

        mazeCanvas = this.drawCanvas(vectors);
        gameBoard.getChildren().add(0, mazeCanvas);

        pathCanvas = new Canvas(App.getBoardMaxX(), App.getBoardMaxY());
        pathCanvas.setMouseTransparent(true);
        gameBoard.getChildren().add(pathCanvas);

        treeCanvas = new Canvas(App.getBoardMaxX(), App.getBoardMaxY());
        treeCanvas.setMouseTransparent(true);
        gameBoard.getChildren().add(treeCanvas);
        ensureHudLayersOnTop();

        gameOverAction = new GameOverAction(playerCharacter, playerMoveCount, root, () -> {});

        winGameAction = new WinGameAction(playerCharacter, playerMoveCount, root, () -> {});

        int baseScore = getBaseScoreForCurrentDifficulty();
        gameOverAction.setBaseScore(baseScore);
        winGameAction.setBaseScore(baseScore);

        playerCharacter.addDeathNotificationSubscriber(gameOverAction);

        winarea = new WinArea(heart);
        winarea.addPositionSubscriber(playerCharacter);
        winarea.AddWinGameAction(winGameAction);

        playerCharacter.addPositionSubscriber(winarea);

        player.requestFocus();
        gameBoard.requestFocus();
        antiLoopWanderMovementService.reset();
        adaptiveAggressiveMovementService.reset();
        patrolMovementService.reset();
        ghostPhasingMovementService.reset();

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
                updateCameraFollow();
            }
        });

        playerCharacter.setHitPoints(playerConfig.health());
        var score = winGameAction.resetScore();
        routeHintPenaltyPoints = 0;
        routeHintPenaltyAccumulator = 0.0;
        isRouteHintVisible = false;
        lastRouteHintPenaltyNanos = 0L;
        pathHintTotalUsedNanos = 0L;
        pathHintKeyDown = false;
        stopPathHintCountdown();
        clearPathHintTimerLabel();
        if (pathHintExhaustedClearTimer != null) {
            pathHintExhaustedClearTimer.stop();
            pathHintExhaustedClearTimer = null;
        }
        updateScoreHud(score);

        gameBoard.setFocusTraversable(true);
        gameBoard.requestFocus();
        ensureHudLayersOnTop();
        updateCameraFollow();
        
        startMovementTimer();
    }

    private void installGameBoardClip() {
        if (root == null || gameBoard == null) {
            return;
        }
        if (gameBoardClip == null) {
            gameBoardClip = new Rectangle();
            gameBoard.setClip(gameBoardClip);
        }

        if (!gameBoardClip.widthProperty().isBound()) {
            gameBoardClip.widthProperty().bind(root.widthProperty());
        }
        if (!gameBoardClip.heightProperty().isBound()) {
            gameBoardClip.heightProperty().bind(root.heightProperty());
        }
        if (!cameraFollowListenersInstalled) {
            root.widthProperty().addListener((obs, oldVal, newVal) -> updateCameraFollow());
            root.heightProperty().addListener((obs, oldVal, newVal) -> updateCameraFollow());
            if (bottomMenuContainer != null) {
                bottomMenuContainer.heightProperty().addListener((obs, oldVal, newVal) -> updateCameraFollow());
            }
            cameraFollowListenersInstalled = true;
        }
    }

    private PlayerConfig loadPlayerConfig() {
        var loader = new XmiRulesLoader();
        try {
            return loader.loadPlayerConfigFromClasspath(
                    PlayerConstants.PlayerModelPath,
                    PlayerConstants.PlayerModelEcorePath);
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Failed to load player model, falling back to defaults", ex);
            return PlayerConfig.defaults();
        }
    }

    private void setPlayerBaseImage(ImageView playerImageView, String imagePath) {
        if (imagePath == null || imagePath.isBlank()) {
            return;
        }
        try {
            var url = getClass().getResource(imagePath);
            if (url != null) {
                playerImageView.setImage(new Image(url.toExternalForm()));
            } else {
                LOGGER.warning("Player base image not found: " + imagePath);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Failed to load player base image from " + imagePath, ex);
        }
    }

    private void startMovementTimer() {
        if (movementTimer != null) {
            movementTimer.stop();
        }
        lastMoveTime = 0;
        movementTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (playerCharacter == null || playerCharacter.getCharacterGraphics() == null) {
                    return;
                }

                applyRouteHintPenalty(now);
                
                // Throttle movement to avoid being too fast
                if (now - lastMoveTime < MOVE_INTERVAL_NANOS) {
                    return;
                }
                lastMoveTime = now;
                
                boolean moved = false;
                if (pressedKeys.contains(KeyCode.UP)) {
                    movePlayerUp();
                    moved = true;
                }
                if (pressedKeys.contains(KeyCode.DOWN)) {
                    movePlayerDown();
                    moved = true;
                }
                if (pressedKeys.contains(KeyCode.LEFT)) {
                    movePlayerLeft();
                    moved = true;
                }
                if (pressedKeys.contains(KeyCode.RIGHT)) {
                    movePlayerRight();
                    moved = true;
                }
                if (moved) {
                    updateDebugLabels();
                    updateCameraFollow();
                }
            }
        };
        movementTimer.start();
    }

    private void updateCameraFollow() {
        if (root == null || gameBoard == null || playerCharacter == null) {
            return;
        }

        double viewportWidth = root.getWidth();
        double viewportHeight = root.getHeight();
        if (viewportWidth <= 0 || viewportHeight <= 0) {
            return;
        }

        double worldWidth = App.getBoardMaxX();
        double worldHeight = App.getBoardMaxY();

        double playerX = playerCharacter.getCharacterPosition().getX();
        double playerY = playerCharacter.getCharacterPosition().getY();

        boolean fullscreen = isStageFullscreen();
        double[] translation = computeCameraTranslation(
                viewportWidth, viewportHeight,
                worldWidth, worldHeight,
                playerX, playerY,
                fullscreen);

        gameBoard.setTranslateX(translation[0]);
        gameBoard.setTranslateY(translation[1]);
    }

    private boolean isStageFullscreen() {
        if (root == null || root.getScene() == null) {
            return false;
        }
        var window = root.getScene().getWindow();
        return window instanceof javafx.stage.Stage stage && stage.isFullScreen();
    }

    /**
     * Camera follow rule: scroll the board only when the stage is fullscreen AND
     * the world is larger than the viewport. In windowed mode the board stays at
     * (0,0) so it always fits inside the window without distortion.
     *
     * <p>Translation is clamped so the world edges never leave the viewport edges.
     *
     * <p>Package-private for unit tests.
     */
    static double[] computeCameraTranslation(double viewportWidth, double viewportHeight,
                                             double worldWidth, double worldHeight,
                                             double playerX, double playerY,
                                             boolean fullscreen) {
        if (!fullscreen) {
            return new double[] {0d, 0d};
        }
        double targetX = 0d;
        double targetY = 0d;
        if (worldWidth > viewportWidth) {
            targetX = clamp((viewportWidth / 2.0) - playerX, viewportWidth - worldWidth, 0);
        }
        if (worldHeight > viewportHeight) {
            targetY = clamp((viewportHeight / 2.0) - playerY, viewportHeight - worldHeight, 0);
        }
        return new double[] {targetX, targetY};
    }

    private static double clamp(double value, double min, double max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    private void updateBoardBackground() {
        String bgPath = VISUAL_STYLE.backgroundImageForDifficultyName(difficultyName());
        
        try {
            var url = getClass().getResource(bgPath);
            if (url != null) {
                Image bgImage = new Image(url.toExternalForm());
                BackgroundImage bi = new BackgroundImage(bgImage,
                    BackgroundRepeat.REPEAT,
                    BackgroundRepeat.REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
                
                gameBoard.setBackground(new Background(bi));
            } else {
                LOGGER.warning("Could not find background image: " + bgPath);
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
        double wallWidth = StageConstants.WallThicknessPx; // Defined in requirements
        double wallLength = StageConstants.WallSegmentLengthPx; // Defined in requirements
        
        // Use WOOD_BASIC as default for now, or fetch from logic if available

        String wallTypeId = VISUAL_STYLE.wallTypeIdForDifficultyName(difficultyName());
        var wallType = WallRegistry.get(wallTypeId);
        if (wallType == null) {
            wallType = WallRegistry.get(MazeVisualStyleConfig.DEFAULT.wallTypeIdForDifficultyName(difficultyName()));
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

    private String difficultyName() {
        return startDifficulty == null ? "" : startDifficulty.eClass().getName();
    }

    private static MazeVisualStyleConfig loadVisualStyle() {
        try {
            return new XmiMazeVisualStyleLoader().load();
        } catch (RuntimeException ex) {
            try {
                return new PropertiesMazeVisualStyleLoader().load();
            } catch (RuntimeException fallbackEx) {
                LOGGER.log(Level.WARNING, "Failed to load visual style config, using defaults", fallbackEx);
                return MazeVisualStyleConfig.DEFAULT;
            }
        }
    }

    public void runComputerCharacters() {
        // Stop any existing movement loop and watchdog before starting a fresh one.
        stopOpponentMovement();

        // Seed the watchdog timestamp so it does not fire before the new thread
        // has had a chance to begin its first iteration.
        lastMovementLoopNanos.set(System.nanoTime());

        runComputerCharacters = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                try {
                    do {
                        if (isCancelled()) {
                            return false;
                        }
                        for (var computerCharacter : allComputerCharacters) {
                            if (isCancelled()) {
                                return false;
                            }
                            try {
                                if(computerCharacter instanceof ComputerCharacter cc) {
                                    BehaviorType characterBehavior = cc.getCharacterBehaviour();
                                    switch (characterBehavior) {
                                        case WANDER:
                                            doCharacterWanderMove(computerCharacter);
                                            break;
                                        case PATROL:
                                            doCharacterPatrolMove(computerCharacter);
                                            break;
                                        case PASSIVE:
                                            doCharacterWanderMove(computerCharacter);
                                            break;
                                        case AGGRESSIVE:
                                            doCharacterAggressiveMove(computerCharacter);
                                            break;
                                        default:
                                            doCharacterWanderMove(computerCharacter);
                                            break;
                                    }
                                }
                            } catch (Exception charEx) {
                                LOGGER.log(Level.WARNING, "Error moving character: " + computerCharacter, charEx);
                            }
                        }
                        Thread.sleep(60);
                        // Heartbeat: let the watchdog know the loop is still alive.
                        lastMovementLoopNanos.set(System.nanoTime());
                    } while (true);
                } catch (InterruptedException ie) {
                    LOGGER.fine("Computer character movement loop interrupted");
                    return false;
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE,
                            "Fatal error in computer character movement loop; "
                            + "watchdog will attempt a restart.", ex);
                    throw ex;
                }
            }
        };
        runComputerCharactersThread = new Thread(runComputerCharacters);
        runComputerCharactersThread.setDaemon(true);
        runComputerCharactersThread.setName("enemy-movement-loop");
        runComputerCharactersThread.start();
        startMovementWatchdog();
    }

    /**
     * Starts (or restarts) the movement-loop watchdog.
     * The watchdog runs on the FX thread once per second. If the background
     * movement thread has not produced a heartbeat for
     * {@value #MOVEMENT_STALL_THRESHOLD_NANOS} ns and there is at least one
     * registered computer character, the thread is considered stalled: a
     * WARNING is logged and {@link #runComputerCharacters()} is invoked to
     * replace the defunct thread.
     */
    private void startMovementWatchdog() {
        stopMovementWatchdog();
        movementWatchdogTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (allComputerCharacters.isEmpty()) {
                // No characters registered yet; nothing to watch.
                return;
            }
            long stallNanos = System.nanoTime() - lastMovementLoopNanos.get();
            if (stallNanos > MOVEMENT_STALL_THRESHOLD_NANOS) {
                LOGGER.log(Level.WARNING,
                        "Enemy movement thread appears stalled — no heartbeat for {0} ms "
                        + "(threshold={1} ms). Restarting movement loop.",
                        new Object[]{stallNanos / 1_000_000L,
                                     MOVEMENT_STALL_THRESHOLD_NANOS / 1_000_000L});
                runComputerCharacters();
            }
        }));
        movementWatchdogTimer.setCycleCount(Animation.INDEFINITE);
        movementWatchdogTimer.play();
    }

    /** Stops and discards the movement-loop watchdog timer, if running. */
    private void stopMovementWatchdog() {
        if (movementWatchdogTimer != null) {
            movementWatchdogTimer.stop();
            movementWatchdogTimer = null;
        }
    }

    private EnemyState buildEnemyState(ComputerCharacter cc) {
        double speed = Math.max(1d, Math.max(Math.abs(cc.getDirectionX()), Math.abs(cc.getDirectionY())));
        double size = approximateEnemySize(cc);
        return new EnemyState(
                enemyRuntimeId(cc),
                cc.getCharacterPosition().getX(),
                cc.getCharacterPosition().getY(),
                directionSign(cc.getDirectionX()),
                directionSign(cc.getDirectionY()),
                size,
                speed);
    }

    private void doCharacterWanderMove(IMovingComputerCharacter computerCharacter) {
        if (!(computerCharacter instanceof ComputerCharacter cc) || maze == null) {
            var successfulMove = computerCharacter.move(false);
            if (!successfulMove) {
                computerCharacter.changeDirection();
            }
            return;
        }

        var nonTangient = false;
        if (computerCharacter instanceof INonTangientMazeGameCharacter nontangientcc) {
            nonTangient = doNonTangientEnergyCalculation(nontangientcc);
        }

        // Phasing ghost: use dedicated service that ignores walls but respects board boundaries.
        if (nonTangient) {
            MovementResult next = ghostPhasingMovementService.tick(buildEnemyState(cc), createJavaFxWorldView());
            cc.setDirection(new Point2D(next.directionX(), next.directionY()));
            computerCharacter.move(true);
            return;
        }

        // Silent-enemy guard: if both direction components are zero the model never
        // calls changeDirection() on spawn (some enemies were observed standing still
        // in JavaFX). Force a heading before asking the shared service for advice.
        if (cc.getDirectionX() == 0 && cc.getDirectionY() == 0) {
            computerCharacter.changeDirection();
        }

        MovementResult next = antiLoopWanderMovementService.tick(buildEnemyState(cc), createJavaFxWorldView());

        if (next.directionX() != 0 || next.directionY() != 0) {
            cc.setDirection(new Point2D(next.directionX(), next.directionY()));
        }

        var successfulMove = computerCharacter.move(false);
        if (!successfulMove) {
            computerCharacter.changeDirection();
            computerCharacter.move(false);
        }
    }

    private void doCharacterPatrolMove(IMovingComputerCharacter computerCharacter) {
        if (!(computerCharacter instanceof ComputerCharacter cc)) {
            doCharacterWanderMove(computerCharacter);
            return;
        }

        var nonTangient = false;
        if (computerCharacter instanceof INonTangientMazeGameCharacter nontangientcc) {
            nonTangient = doNonTangientEnergyCalculation(nontangientcc);
        }

        // Phasing ghost: use dedicated service that ignores walls but respects board boundaries.
        if (nonTangient) {
            MovementResult next = ghostPhasingMovementService.tick(buildEnemyState(cc), createJavaFxWorldView());
            cc.setDirection(new Point2D(next.directionX(), next.directionY()));
            computerCharacter.move(true);
            return;
        }

        MovementResult result = patrolMovementService.tick(buildEnemyState(cc), createJavaFxWorldView(), 0.06d);
        if (result.directionX() == 0 && result.directionY() == 0) {
            doCharacterWanderMove(computerCharacter);
            return;
        }

        computerCharacter.setDirection(new Point2D(result.directionX(), result.directionY()));
        var successfulMove = computerCharacter.move(false);
        if (!successfulMove) {
            doCharacterWanderMove(computerCharacter);
        }
    }

    private void doCharacterAggressiveMove(IMovingComputerCharacter computerCharacter) {
        if (playerCharacter == null || !(computerCharacter instanceof ComputerCharacter cc) || maze == null) {
            doCharacterWanderMove(computerCharacter);
            return;
        }

        var nonTangient = false;
        if (computerCharacter instanceof INonTangientMazeGameCharacter nontangientcc) {
            nonTangient = doNonTangientEnergyCalculation(nontangientcc);
        }

        // Phasing ghost: use dedicated service that ignores walls but respects board boundaries.
        if (nonTangient) {
            MovementResult next = ghostPhasingMovementService.tick(buildEnemyState(cc), createJavaFxWorldView());
            cc.setDirection(new Point2D(next.directionX(), next.directionY()));
            computerCharacter.move(true);
            return;
        }

        var result = adaptiveAggressiveMovementService.tick(
                buildEnemyState(cc),
                createJavaFxWorldView(),
                0.06d);

        if (result.directionX() == 0 && result.directionY() == 0) {
            doCharacterWanderMove(computerCharacter);
            return;
        }

        cc.setDirection(new Point2D(result.directionX(), result.directionY()));
        var successfulMove = computerCharacter.move(false);
        if (!successfulMove) {
            computerCharacter.changeDirection();
            computerCharacter.move(false);
        }
    }

    private static int directionSign(double value) {
        if (value > 0d) {
            return 1;
        }
        if (value < 0d) {
            return -1;
        }
        return 0;
    }

    private static double approximateEnemySize(ComputerCharacter character) {
        if (character instanceof ZombieCharacter) {
            return StageConstants.ZombieCharacterXYSize;
        }
        if (character instanceof GhostCharacter) {
            return StageConstants.GhostCharacterXYSize;
        }
        if (character instanceof PumpkinBomberCharacter) {
            return StageConstants.PumpkinBomberCharacterXYSize;
        }
        return StageConstants.TouchDistance;
    }

    private String enemyRuntimeId(ComputerCharacter character) {
        return Integer.toHexString(System.identityHashCode(character));
    }

    private WorldView createJavaFxWorldView() {
        return new WorldView() {
            @Override
            public double playerX() {
                return playerCharacter != null ? playerCharacter.getCharacterPosition().getX() : 0d;
            }

            @Override
            public double playerY() {
                return playerCharacter != null ? playerCharacter.getCharacterPosition().getY() : 0d;
            }

            @Override
            public double minX() {
                return 0d;
            }

            @Override
            public double minY() {
                return 0d;
            }

            @Override
            public double maxX() {
                return App.getBoardMaxX();
            }

            @Override
            public double maxY() {
                return App.getBoardMaxY();
            }

            @Override
            public boolean wouldCollide(double centerX, double centerY, double size) {
                if (maze == null || maze.getMazeVectors() == null) {
                    return false;
                }
                return WallCollisionUtil.wouldCollideVectors(centerX, centerY, size,
                        App.getBoardMaxX(), App.getBoardMaxY(),
                        maze.getMazeVectors());
            }
        };
    }

    public Point2D resolveEnemySpawnPosition(double desiredX, double desiredY, double enemySize) {
        var resolution = EnemySpawnUnstuckService.nudgeIfColliding(
                createJavaFxWorldView(),
                desiredX,
                desiredY,
                enemySize);
        return new Point2D(resolution.x(), resolution.y());
    }

    private boolean doNonTangientEnergyCalculation(INonTangientMazeGameCharacter nontangientcc) {
        var energy = nontangientcc.getNonTangientEnergy();
        boolean nonTangient = GhostNonTangibilityService.isPhasing(energy);
        if (nonTangient) {
            nontangientcc.setCharacterOpacity(GhostNonTangibilityService.calculateOpacity(energy));
            // 60 ms per tick in the JavaFX movement loop.
            nontangientcc.setNonTangientEnergy(GhostNonTangibilityService.drainEnergy(energy, 0.060));
        }
        return nonTangient;
    }

    public void registerComputerCharacter(IMovingComputerCharacter character, Node node) {
        Platform.runLater(() -> {
            if (gameBoard == null || node == null || character == null) {
                return;
            }
            if (!gameBoard.getChildren().contains(node)) {
                gameBoard.getChildren().add(node);
            }
            if (character instanceof main.game.maze.characters.ZombieCharacter zombieCharacter) {
                int infectionLevel = zombieCharacter.getModel() != null ? zombieCharacter.getModel().getInfectionLevel() : 0;
                applyInfectiousMist(node, infectionLevel);
            }
            if (!allComputerCharacters.contains(character)) {
                allComputerCharacters.add(character);
            }
            if (character instanceof ICanSubscribeAndNotifyPosition subscribable && playerCharacter != null) {
                playerCharacter.addPositionSubscriber(subscribable);
                subscribable.addPositionSubscriber(playerCharacter);
            }
        });
    }

    public void unregisterComputerCharacter(IMovingComputerCharacter character, Node node) {
        Platform.runLater(() -> {
            allComputerCharacters.remove(character);
            if (character instanceof ICanSubscribeAndNotifyPosition subscribable && playerCharacter != null) {
                playerCharacter.removePositionSubscriber(subscribable);
                subscribable.removePositionSubscriber(playerCharacter);
            }
            stopInfectiousMist(node);
            if (gameBoard != null && node != null && node.getParent() == gameBoard) {
                gameBoard.getChildren().remove(node);
            }
        });
    }

    public void showInfectionWarning() {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(this::showInfectionWarning);
            return;
        }
        if (root == null) {
            return;
        }
        ensureInfectionWarningSign();
        infectionWarningSign.setVisible(true);
        infectionWarningSign.setManaged(false);
        infectionWarningSign.toFront();

        if (infectionWarningHideTimer == null) {
            infectionWarningHideTimer = new PauseTransition(Duration.seconds(1.2));
            infectionWarningHideTimer.setOnFinished(evt -> {
                if (infectionWarningSign != null) {
                    infectionWarningSign.setVisible(false);
                }
            });
        }
        infectionWarningHideTimer.stop();
        infectionWarningHideTimer.playFromStart();
    }

    private void ensureInfectionWarningSign() {
        if (infectionWarningSign != null) {
            return;
        }

        Polygon triangle = new Polygon(
                0.0, 84.0,
                48.0, 0.0,
                96.0, 84.0);
        triangle.setFill(Color.rgb(255, 212, 77, 0.95));
        triangle.setStroke(Color.rgb(55, 40, 0, 0.95));
        triangle.setStrokeWidth(3.0);

        Label mark = new Label("!");
        mark.setTextFill(Color.rgb(35, 22, 0));
        mark.setStyle("-fx-font-size: 56px; -fx-font-family: 'Consolas'; -fx-font-weight: bold;");
        mark.setTranslateY(6);

        Text infectedLabel = new Text("Infected!");
        infectedLabel.setFill(Color.rgb(51, 255, 115));
        infectedLabel.setFont(Font.font("Consolas", FontWeight.BOLD, 22));
        infectedLabel.setMouseTransparent(true);

        StackPane signGraphic = new StackPane(triangle, mark);
        signGraphic.setAlignment(Pos.CENTER);

        infectionWarningSign = new VBox(6, signGraphic, infectedLabel);
        infectionWarningSign.setAlignment(Pos.CENTER);
        infectionWarningSign.setMouseTransparent(true);
        infectionWarningSign.setManaged(false);
        infectionWarningSign.setVisible(false);

        infectionWarningSign.layoutXProperty().bind(root.widthProperty().subtract(96).divide(2));
        infectionWarningSign.layoutYProperty().bind(root.heightProperty().subtract(130).divide(2));
        root.getChildren().add(infectionWarningSign);
    }

    private void applyInfectiousMist(Node node, int infectionLevel) {
        if (node == null || infectionLevel <= 0) {
            return;
        }
        stopInfectiousMist(node);

        DropShadow mist = new DropShadow();
        mist.setBlurType(BlurType.GAUSSIAN);
        mist.setColor(Color.rgb(60, 255, 130, 0.78));
        mist.setRadius(10.0);
        mist.setSpread(0.18);
        node.setEffect(mist);

        double intensity = Math.min(1.0, Math.max(0.35, infectionLevel / 100.0));
        Timeline pulse = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(mist.radiusProperty(), 10.0 + 8.0 * intensity),
                        new KeyValue(mist.spreadProperty(), 0.14 + 0.08 * intensity)),
                new KeyFrame(Duration.seconds(0.7),
                        new KeyValue(mist.radiusProperty(), 22.0 + 10.0 * intensity),
                        new KeyValue(mist.spreadProperty(), 0.34 + 0.10 * intensity)));
        pulse.setAutoReverse(true);
        pulse.setCycleCount(Animation.INDEFINITE);
        infectiousMists.put(node, pulse);
        pulse.play();
    }

    private void stopInfectiousMist(Node node) {
        if (node == null) {
            return;
        }
        Timeline pulse = infectiousMists.remove(node);
        if (pulse != null) {
            pulse.stop();
        }
        if (node.getEffect() instanceof DropShadow) {
            node.setEffect(null);
        }
    }

    private void showNavigationPath() {
        long remainingNanos = pathHintBudgetNanos() - pathHintTotalUsedNanos;
        if (remainingNanos <= 0) {
            showPathHintExhaustedMessage();
            return;
        }
        isRouteHintVisible = true;
        // Guard against OS key-repeat: only record the start time and launch the
        // countdown on the very first press event, not on every repeat event.
        if (!pathHintKeyDown) {
            pathHintKeyDown = true;
            pathHintPressStartNanos = System.nanoTime();
            lastRouteHintPenaltyNanos = pathHintPressStartNanos;
            startPathHintCountdown();
        }
        refreshPathCanvasOverlay();
    }

    private void clearNavigationPath() {
        if (pathHintKeyDown) {
            long heldNanos = System.nanoTime() - pathHintPressStartNanos;
            pathHintTotalUsedNanos = Math.min(pathHintTotalUsedNanos + heldNanos, pathHintBudgetNanos());
            pathHintKeyDown = false;
        }
        isRouteHintVisible = false;
        stopPathHintCountdown();
        clearPathHintTimerLabel();
        refreshPathCanvasOverlay();
    }

    /** Returns the path-hint energy budget in nanoseconds based on the active difficulty. */
    private long pathHintBudgetNanos() {
        if (startDifficulty instanceof HardDifficulty) return PATH_HINT_BUDGET_HARD_NANOS;
        if (startDifficulty instanceof EasyDifficulty) return PATH_HINT_BUDGET_EASY_NANOS;
        return PATH_HINT_BUDGET_NORMAL_NANOS;
    }

    /**
     * Starts a 100ms-tick Timeline that updates the path-hint countdown label
     * and auto-clears the path when the budget is exhausted mid-hold.
     */
    private void startPathHintCountdown() {
        stopPathHintCountdown();
        pathHintCountdownTicker = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            long usedSoFar = pathHintTotalUsedNanos + (System.nanoTime() - pathHintPressStartNanos);
            long budgetNanos = pathHintBudgetNanos();
            long remainingNanos = budgetNanos - usedSoFar;
            if (remainingNanos <= 0) {
                pathHintTotalUsedNanos = budgetNanos;
                pathHintKeyDown = false;
                isRouteHintVisible = false;
                stopPathHintCountdown();
                refreshPathCanvasOverlay();
                showPathHintExhaustedMessage();
                return;
            }
            updatePathHintTimerLabel(remainingNanos);
        }));
        pathHintCountdownTicker.setCycleCount(Animation.INDEFINITE);
        pathHintCountdownTicker.play();
        // Show initial value immediately without waiting for first tick.
        long initial = pathHintBudgetNanos() - pathHintTotalUsedNanos;
        updatePathHintTimerLabel(initial);
    }

    private void stopPathHintCountdown() {
        if (pathHintCountdownTicker != null) {
            pathHintCountdownTicker.stop();
            pathHintCountdownTicker = null;
        }
    }

    private void updatePathHintTimerLabel(long remainingNanos) {
        if (pathHintTimerLabel == null) return;
        double secs = remainingNanos / 1_000_000_000.0;
        pathHintTimerLabel.setText(String.format("Path hint: %.1fs left", secs));
        pathHintTimerLabel.setVisible(true);
    }

    private void clearPathHintTimerLabel() {
        if (pathHintTimerLabel == null) return;
        pathHintTimerLabel.setText("");
        pathHintTimerLabel.setVisible(false);
    }

    private void showPathHintExhaustedMessage() {
        if (pathHintTimerLabel == null) return;
        // Stop any previously scheduled auto-clear so we don't get double-clear.
        if (pathHintExhaustedClearTimer != null) {
            pathHintExhaustedClearTimer.stop();
        }
        pathHintTimerLabel.setText("Path hint energy already spent!");
        pathHintTimerLabel.setVisible(true);
        pathHintExhaustedClearTimer = new PauseTransition(Duration.seconds(3));
        pathHintExhaustedClearTimer.setOnFinished(ev -> clearPathHintTimerLabel());
        pathHintExhaustedClearTimer.play();
    }

    private void refreshPathCanvasOverlay() {
        if (pathCanvas == null) {
            return;
        }
        GraphicsContext gc = pathCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, pathCanvas.getWidth(), pathCanvas.getHeight());
        if (isRouteHintVisible) {
            drawPlayerNavigationPath(gc);
        }
        if (enemyPathOverlayVisible) {
            drawEnemyNavigationPaths(gc);
        }
        gc.setGlobalAlpha(1.0);
    }

    private void drawPlayerNavigationPath(GraphicsContext gc) {
        if (maze == null || heart == null || playerCharacter == null) {
            isRouteHintVisible = false;
            return;
        }

        var navGraph = maze.getNavigationGraph();
        if (navGraph == null) {
            isRouteHintVisible = false;
            return;
        }

        Point2D start = new Point2D(
                playerCharacter.getCharacterPosition().getX(),
                playerCharacter.getCharacterPosition().getY());
        double heartW = heart.getBoundsInLocal().getWidth();
        double heartH = heart.getBoundsInLocal().getHeight();
        Point2D goal = new Point2D(
                heart.getLayoutX() + heartW / 2.0,
                heart.getLayoutY() + heartH / 2.0);

        var path = MazeNavigationGraphService.findPath(navGraph, start, goal);
        if (path == null || path.size() < 2) {
            isRouteHintVisible = false;
            return;
        }

        gc.setLineWidth(8.0);
        gc.setStroke(Color.DODGERBLUE);
        gc.setGlobalAlpha(0.6);
        Point2D prev = path.get(0);
        for (int i = 1; i < path.size(); i++) {
            Point2D p = path.get(i);
            gc.strokeLine(prev.getX(), prev.getY(), p.getX(), p.getY());
            prev = p;
        }
    }

    private void drawEnemyNavigationPaths(GraphicsContext gc) {
        if (maze == null) {
            return;
        }
        gc.setLineWidth(5.0);
        gc.setStroke(Color.ORANGE);
        gc.setGlobalAlpha(0.65);

        for (var cc : allComputerCharacters) {
            if (!(cc instanceof ComputerCharacter computerCharacter)) {
                continue;
            }
            List<ActivePathPoint> path = activeEnemyPath(computerCharacter);
            if (path.isEmpty()) {
                continue;
            }
            ActivePathPoint prev = path.get(0);
            for (int i = 1; i < path.size(); i++) {
                ActivePathPoint p = path.get(i);
                gc.strokeLine(prev.x(), prev.y(), p.x(), p.y());
                prev = p;
            }
        }
    }

    private List<ActivePathPoint> activeEnemyPath(ComputerCharacter character) {
        String id = enemyRuntimeId(character);
        double x = character.getCharacterPosition().getX();
        double y = character.getCharacterPosition().getY();
        BehaviorType behaviour = character.getCharacterBehaviour();
        if (behaviour == BehaviorType.AGGRESSIVE) {
            return adaptiveAggressiveMovementService.currentPathForEnemy(id, x, y);
        }
        if (behaviour == BehaviorType.PATROL) {
            return patrolMovementService.currentPathForEnemy(id, x, y);
        }
        return List.of();
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

    /**
     * Stops the computer character movement loop without cleaning up nodes.
     * Call this before screen transitions to prevent race conditions.
     */
    public void stopComputerCharacters() {
        stopPlayerMovement();
        stopOpponentMovement();
    }

    /**
     * Stops only player movement and key state.
     * Opponent movement loop remains active.
     */
    public void stopPlayerMovement() {
        if (movementTimer != null) {
            movementTimer.stop();
            movementTimer = null;
        }
        pressedKeys.clear();
    }

    /**
     * Stops only opponent movement loop.
     */
    public void stopOpponentMovement() {
        stopMovementWatchdog();
        if (runComputerCharacters != null) runComputerCharacters.cancel();
        if (runComputerCharactersThread != null) {
            runComputerCharactersThread.interrupt();
            try {
                runComputerCharactersThread.join(OPPONENT_THREAD_JOIN_TIMEOUT_MS);
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
            }
            runComputerCharactersThread = null;
        }
    }

    public void dispose() {
        stopComputerCharacters();
        stopPathHintCountdown();
        if (pathHintExhaustedClearTimer != null) {
            pathHintExhaustedClearTimer.stop();
            pathHintExhaustedClearTimer = null;
        }

        if (enemyPathOverlayTimer != null) {
            enemyPathOverlayTimer.stop();
            enemyPathOverlayTimer = null;
        }
        enemyPathOverlayVisible = false;

        if (hudMessageClearTimer != null) {
            hudMessageClearTimer.stop();
            hudMessageClearTimer = null;
        }

        if (infectionWarningHideTimer != null) {
            infectionWarningHideTimer.stop();
            infectionWarningHideTimer = null;
        }
        if (infectionWarningSign != null) {
            VBox sign = infectionWarningSign;
            Platform.runLater(() -> {
                if (root != null) {
                    root.getChildren().remove(sign);
                }
            });
            infectionWarningSign = null;
        }
        infectiousMists.values().forEach(Timeline::stop);
        infectiousMists.keySet().forEach(n -> {
            if (n != null && n.getEffect() instanceof DropShadow) {
                n.setEffect(null);
            }
        });
        infectiousMists.clear();

        // Dispose all computer characters
        for (var cc : allComputerCharacters) {
            if (cc instanceof ComputerCharacter computerChar) {
                computerChar.dispose();
            }
        }
        allComputerCharacters.clear();

        if (winarea != null && playerCharacter != null) {
            playerCharacter.removePositionSubscriber(winarea);
        }
        if (playerCharacter != null) {
            playerCharacter.dispose();
            playerCharacter = null;
        }
    }
}