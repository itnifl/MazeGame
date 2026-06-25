package main.game.maze;

import java.net.URL;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.util.Duration;
import main.game.maze.actions.GameOverAction;
import main.game.maze.actions.HighscoreAction;
import main.game.maze.actions.WinGameAction;
import main.game.maze.areas.WinArea;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.characters.interfaces.IMovingComputerCharacter;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import main.game.maze.common.graphics.config.PropertiesMazeVisualStyleLoader;
import main.game.maze.common.graphics.config.XmiMazeVisualStyleLoader;
import main.game.maze.common.input.InputFrame;
import main.game.maze.common.input.InputRouter;
import main.game.maze.common.input.KeyBindingRegistry;
import main.game.maze.common.controller.state.GameModeRouter;
import main.game.maze.javafx.controller.state.FxPlayingModeController;
import main.game.maze.javafx.render.FxGameRenderCoordinator;
import main.game.maze.javafx.render.FxMazeCanvasRenderer;
import main.game.maze.javafx.render.FxPathHintCoordinator;
import main.game.maze.mazeworld.BreakableWall;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.WallCollisionUtil;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.javafx.hud.FxHudCoordinator;
import main.game.maze.javafx.lifecycle.FxGameSessionBootstrapper;
import main.game.maze.javafx.menu.FxDifficultyPickerSupport;
import main.game.maze.runtime.opponents.EnemyRegistrar;
import main.game.maze.service.CharacterIntersectionFixerService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameController implements Initializable, EnemyRegistrar {
    private static final Logger LOGGER = Logger.getLogger(GameController.class.getName());
    private static final MazeVisualStyleConfig VISUAL_STYLE = loadVisualStyle();

    @FXML private AnchorPane root;
    @FXML private ImageView player;
    @FXML private Pane gameBoard;
    @FXML private Label coordinatesLabel;
    @FXML private ProgressBar hpBar;
    @FXML private Label mouseCoordsLabel;
    @FXML private Node heart;
    @FXML private Label scoreLabel;
    @FXML private AnchorPane scoreHudContainer;
    @FXML private Label bombsLabel;
    @FXML private AnchorPane bottomMenuContainer;
    @FXML private AnchorPane commandsOverlay;
    @FXML private Button commandsMenuButton;
    @FXML private Button terminalMenuButton;
    @FXML private Label pathHintTimerLabel;

    private static final String COMMANDS_BUTTON_STYLE         = "-fx-background-color: rgba(143,255,224,0.85); -fx-text-fill: #103630; -fx-font-family: 'Consolas'; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 4;";
    private static final String COMMANDS_BUTTON_PRESSED_STYLE = "-fx-background-color: rgba(115,215,189,0.95); -fx-text-fill: #0a2924; -fx-font-family: 'Consolas'; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 4;";
    private static final String TERMINAL_BUTTON_STYLE         = "-fx-background-color: rgba(255,229,110,0.90); -fx-text-fill: #2f1d00; -fx-font-family: 'Consolas'; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 4;";
    private static final String TERMINAL_BUTTON_PRESSED_STYLE = "-fx-background-color: rgba(232,197,79,0.95); -fx-text-fill: #251700; -fx-font-family: 'Consolas'; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 4;";
    private static final double PLAYER_BOMB_FUSE_SECONDS = 3.0;
    private static final double PLAYER_BOMB_RANGE = 220.0;
    private static final double PLAYER_FLAME_DAMAGE_PER_DIRECTION = 100.0;
    private static final double PLAYER_FLAME_VISUAL_SECONDS = 0.35;
    /** Full corridor width: 85 %² of one maze hallway (≈ 43 px) for a snug fit. */
    private static final double PLAYER_FLAME_CORRIDOR_WIDTH = StageConstants.HallwayWidthPx * 0.85 * 0.85;

    private PlayerCharacter playerCharacter;
    private GameMazeWorld maze;
    private GameOverAction gameOverAction;
    private WinGameAction winGameAction;
    private WinArea winarea;

    private final FxMovementLoopCoordinator movementLoopCoordinator = new FxMovementLoopCoordinator(new FxMovementLoopCoordinator.Callbacks() {
        @Override
        public void onComputerCharacterStep() {
            enemyCoordinator.stepAll();
        }
        @Override
        public void onPlayerStep(long now) {
            handlePlayerMovementTick(now);
        }
    });

    /** Gameplay scoring and path-hint state (MVC model). */
    private final FxGameWorldModel model = new FxGameWorldModel();

    private Canvas pathCanvas;
    private Canvas treeCanvas;
    private Canvas mazeCanvas;

    private Difficulty startDifficulty;

    // Key state tracking for smooth movement
    private final Set<KeyCode> pressedKeys = EnumSet.noneOf(KeyCode.class);
    private final Set<KeyCode> edgeKeys    = EnumSet.noneOf(KeyCode.class);
    private final JavaFxInputSnapshotReader inputSnapshotReader = new JavaFxInputSnapshotReader();
    private InputFrame<KeyCode> currentInputFrame = new InputFrame<>(Set.of(), Set.of(), 0d, 0d, false);
    private final KeyBindingRegistry<KeyCode> keyBindingRegistry = new KeyBindingRegistry<>();
    private final InputRouter<KeyCode> inputRouter = new InputRouter<>(keyBindingRegistry);
    private final JavaFxInputCommandContext inputCommandContext;
    private final GameModeRouter modeRouter = new GameModeRouter();
    private FxPlayingModeController playingModeController;
    private FxGameRenderCoordinator renderCoordinator;

    private double mouseX;
    private double mouseY;
    private boolean leftMouseClicked;

    private final JavaFxInputCommandContext.ActionSink actionSink = new JavaFxInputCommandContext.ActionSink() {
        @Override public void showHighScore()                       { GameController.this.showHighScore(); }
        @Override public void openDifficultyPickerAndMaybeRestart(){ GameController.this.openDifficultyPickerAndMaybeRestart(); }
        @Override public void showNavigationPath()  { pathHintCoordinator.showNavigationPath(); }
        @Override public void clearNavigationPath() { pathHintCoordinator.clearNavigationPath(); }
        @Override public void showSpanningTree()    { renderCoordinator.showSpanningTree(treeCanvas, playerCharacter, maze); }
        @Override public void clearSpanningTree()   { renderCoordinator.clearSpanningTree(treeCanvas); }
        @Override public void updateDebugLabels()                   { GameController.this.updateDebugLabels(); }
        @Override public void updateScoreHud() {
            var score = winGameAction.updateScore();
            GameController.this.updateScoreHud(score);
        }
        @Override public void openTerminalPrompt()                  { GameController.this.openTerminalPrompt(); }
        @Override public void triggerPlayerFlameAttack()            { GameController.this.triggerPlayerFlameAttack(); }
    };

    private final GameControllerTerminalSupport.TerminalCommandSink terminalCommandSink = new GameControllerTerminalSupport.TerminalCommandSink() {
        @Override public void setHudMessage(String text)                          { GameController.this.setHudMessage(text); }
        @Override public void setHudMessage(String text, Duration visibleFor)     { GameController.this.setHudMessage(text, visibleFor); }
        @Override public void showEnemyDebugLabels(boolean behaviourType)         { enemyCoordinator.showEnemyDebugLabels(behaviourType); }
        @Override public void showEnemyPathsOverlay()                             { enemyCoordinator.showEnemyPathsOverlay(); }
        @Override public int killAllEnemies()                                     { return enemyCoordinator.killAll(); }
    };

    private boolean cameraFollowListenersInstalled;

    private final List<ActivePlayerBomb> activePlayerBombs = new ArrayList<>();

    private record ActivePlayerBomb(double x, double y, Circle marker, PauseTransition fuse) {
    }

    // Extracted coordinators
    private final FxEnemyCoordinator  enemyCoordinator;
    private final FxHudCoordinator    hudCoordinator = new FxHudCoordinator(() -> mouseCoordsLabel);
    private FxPathHintCoordinator     pathHintCoordinator;
    private FxMazeCanvasRenderer      mazeCanvasRenderer;
    private FxGameSessionBootstrapper bootstrapper;

    public GameController() {
        inputCommandContext = new JavaFxInputCommandContext(actionSink);
        JavaFxInputBindingsSupport.configureDefaultBindings(keyBindingRegistry);
        // gameBoard and root are @FXML fields — null here; suppliers are evaluated lazily after initialize().
        enemyCoordinator = new FxEnemyCoordinator(
                () -> gameBoard, () -> root, model,
                () -> maze,
                () -> playerCharacter,
                () -> pathHintCoordinator.refreshPathCanvas());
        // Label resolved lazily — FXML field is null until initialize() runs.
        pathHintCoordinator = new FxPathHintCoordinator(
                () -> pathHintTimerLabel,
                model,
                () -> startDifficulty,
                () -> maze,
                () -> playerCharacter,
                () -> heart,
                () -> pathCanvas,
                gc -> enemyCoordinator.drawEnemyNavigationPaths(gc));
    }

    public void setStartDifficulty(Difficulty d) { this.startDifficulty = d; }

    int getBaseScoreForCurrentDifficulty() {
        return main.game.maze.common.scoring.GameScoringConstants.baseScoreFor(startDifficulty);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        renderCoordinator  = new FxGameRenderCoordinator(gameBoard);
        mazeCanvasRenderer = new FxMazeCanvasRenderer(VISUAL_STYLE, this::difficultyName);
        bootstrapper       = new FxGameSessionBootstrapper(VISUAL_STYLE, mazeCanvasRenderer, this);

                javafx.application.Platform.runLater(() -> {
            installBottomButtonPressEffects();
            if (gameBoard != null) {
                gameBoard.requestFocus();
                // Re-assert focus whenever the window state or scene graph changes
                gameBoard.sceneProperty().addListener((obs, oldS, newS) -> {
                    if (newS != null) {
                        newS.focusOwnerProperty().addListener((obsF, oldF, newF) -> {
                            boolean isTextInput = newF instanceof javafx.scene.control.TextInputControl;
                            if (FxFocusGuard.shouldReassertFocus(newF, gameBoard, isTextInput)) {
                                gameBoard.requestFocus();
                            }
                        });
                    }
                });
                // Prevent Tab key from moving focus away from the game board during play.
                gameBoard.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, evt -> {
                    if (evt.getCode() == KeyCode.TAB) evt.consume();
                });
            }
        });

    }

    private void installBottomButtonPressEffects() {
        installButtonPressEffect(commandsMenuButton, COMMANDS_BUTTON_STYLE, COMMANDS_BUTTON_PRESSED_STYLE);
        installButtonPressEffect(terminalMenuButton, TERMINAL_BUTTON_STYLE, TERMINAL_BUTTON_PRESSED_STYLE);
    }

    void triggerPlayerFlameAttack() {
        if (playerCharacter == null || !playerCharacter.consumeFlameBomb()) {
            return;
        }
        updateBombHud();
        double bombX = playerCharacter.getCharacterPosition().getX() + StageConstants.PlayerCharacterXYSize * 0.5d;
        double bombY = playerCharacter.getCharacterPosition().getY() + StageConstants.PlayerCharacterXYSize * 0.5d;
        plantPlayerBomb(bombX, bombY);
    }

    private void plantPlayerBomb(double centerX, double centerY) {
        if (gameBoard == null) {
            return;
        }
        Circle marker = new Circle(centerX, centerY, 8.0);
        marker.setFill(Color.rgb(40, 40, 40, 0.9));
        marker.setStroke(Color.rgb(255, 191, 71, 0.95));
        marker.setStrokeWidth(2.2);
        marker.setViewOrder(-8);
        gameBoard.getChildren().add(marker);

        PauseTransition fuse = new PauseTransition(Duration.seconds(PLAYER_BOMB_FUSE_SECONDS));
        ActivePlayerBomb bomb = new ActivePlayerBomb(centerX, centerY, marker, fuse);
        activePlayerBombs.add(bomb);
        setHudMessage("Bomb planted", Duration.seconds(1.2));
        fuse.setOnFinished(evt -> detonatePlayerBomb(bomb));
        fuse.playFromStart();
    }

    private void detonatePlayerBomb(ActivePlayerBomb bomb) {
        if (!activePlayerBombs.remove(bomb)) {
            return;
        }
        if (gameBoard != null) {
            gameBoard.getChildren().remove(bomb.marker());
        }

        List<Vector2D> walls = maze != null ? maze.getMazeVectors() : List.of();

        double playerCx = playerCharacter != null
                ? playerCharacter.getCharacterPosition().getX() + StageConstants.PlayerCharacterXYSize * 0.5d
                : Double.NaN;
        double playerCy = playerCharacter != null
                ? playerCharacter.getCharacterPosition().getY() + StageConstants.PlayerCharacterXYSize * 0.5d
                : Double.NaN;

        int enemyDamage = enemyCoordinator.applyPlayerFlameExplosion(
                bomb.x(),
                bomb.y(),
                playerCharacter != null ? playerCharacter.getFlameAttackDamage() : (int) PLAYER_FLAME_DAMAGE_PER_DIRECTION,
                PLAYER_BOMB_RANGE,
                walls,
                playerCx,
                playerCy,
                dmg -> { if (playerCharacter != null) playerCharacter.subtractHitPoints(dmg); });
        if (mazeCanvas != null && maze != null) {
            mazeCanvasRenderer.redrawInPlace(mazeCanvas, maze.getMazeVectors());
        }
        // Pass surviving walls (destroyed ones already removed) so visual stops at solid walls.
        List<Vector2D> survivingWalls = maze != null ? maze.getMazeVectors() : List.of();
        showFlameExplosionVisual(bomb.x(), bomb.y(), PLAYER_BOMB_RANGE, survivingWalls);

        if (enemyDamage > 0) {
            setHudMessage("Bomb exploded, enemy damage " + enemyDamage, Duration.seconds(1.4));
        } else {
            setHudMessage("Bomb exploded", Duration.seconds(1.0));
        }
    }

    private void showFlameExplosionVisual(double centerX, double centerY, double range,
                                          List<Vector2D> survivingWalls) {
        if (gameBoard == null) {
            return;
        }
        Circle core = new Circle(centerX, centerY, 12.0);
        core.setFill(Color.rgb(255, 170, 36, 0.55));
        core.setStroke(Color.rgb(255, 232, 160, 0.95));
        core.setStrokeWidth(2.6);
        core.setViewOrder(-9);

        double east  = enemyCoordinator.flameVisualRange(centerX, centerY,  1,  0, survivingWalls, range);
        double west  = enemyCoordinator.flameVisualRange(centerX, centerY, -1,  0, survivingWalls, range);
        double south = enemyCoordinator.flameVisualRange(centerX, centerY,  0,  1, survivingWalls, range);
        double north = enemyCoordinator.flameVisualRange(centerX, centerY,  0, -1, survivingWalls, range);

        Line eastLine  = flameSegment(centerX, centerY, centerX + east,  centerY);
        Line westLine  = flameSegment(centerX, centerY, centerX - west,  centerY);
        Line southLine = flameSegment(centerX, centerY, centerX,         centerY + south);
        Line northLine = flameSegment(centerX, centerY, centerX,         centerY - north);
        gameBoard.getChildren().addAll(core, eastLine, westLine, southLine, northLine);

        PauseTransition hide = new PauseTransition(Duration.seconds(PLAYER_FLAME_VISUAL_SECONDS));
        hide.setOnFinished(evt -> {
            if (gameBoard != null) {
                gameBoard.getChildren().removeAll(core, eastLine, westLine, southLine, northLine);
            }
        });
        hide.playFromStart();
    }

    private static Line flameSegment(double x1, double y1, double x2, double y2) {
        Line segment = new Line(x1, y1, x2, y2);
        segment.setStroke(Color.rgb(255, 122, 48, 0.85));
        segment.setStrokeWidth(PLAYER_FLAME_CORRIDOR_WIDTH);
        segment.setStrokeLineCap(StrokeLineCap.ROUND);
        segment.setViewOrder(-9);
        return segment;
    }

    private static void installButtonPressEffect(Button button, String normalStyle, String pressedStyle) {
        if (button == null) return;
        button.setStyle(normalStyle);
        button.setOnMousePressed(evt  -> { button.setTranslateY(1.8); button.setStyle(pressedStyle); });
        button.setOnMouseReleased(evt -> { button.setTranslateY(0);   button.setStyle(normalStyle); });
        button.setOnMouseExited(evt   -> { button.setTranslateY(0);   button.setStyle(normalStyle); });
    }

        @FXML
    private void handleKeyReleased(KeyEvent event) {
        pressedKeys.remove(event.getCode());
        inputSnapshotReader.clearKey(event.getCode());
    }


    @FXML
    private void handleKeyPressed(KeyEvent event) {
        pressedKeys.add(event.getCode());
        edgeKeys.add(event.getCode());
        InputFrame<KeyCode> inputFrame = inputSnapshotReader.read(pressedKeys, edgeKeys, mouseX, mouseY, leftMouseClicked);
        inputRouter.route(inputFrame, inputCommandContext);
    }

    private void updateDebugLabels() {
        if (playerCharacter == null || gameOverAction == null || winGameAction == null) return;
        var coordinatesText = "X: " + playerCharacter.getCharacterPosition().getX()
                + ", Y: " + playerCharacter.getCharacterPosition().getY();
        var directionsText = "Direction: " + playerCharacter.getCharacterDirection();
        coordinatesLabel.setText(coordinatesText + " - " + directionsText);
        model.playerMoveCount().getAndIncrement();
        gameOverAction.updateScore();
        var score = winGameAction.updateScore();
        updateScoreHud(score);
    }

    private void updateScoreHud(int score) {
        if (scoreLabel != null)        scoreLabel.setText("Score: " + score);
        updateBombHud();
        if (coordinatesLabel != null)  coordinatesLabel.setTextFill(Color.WHITE);
        if (mouseCoordsLabel != null)  mouseCoordsLabel.setTextFill(Color.WHITE);
    }

    private void updateBombHud() {
        if (bombsLabel != null && playerCharacter != null) bombsLabel.setText("Bombs: " + playerCharacter.getFlameBombsRemaining());
    }

    public int getDynamicScorePenalty() {
        return model.routeHintPenaltyPoints();
    }

    public boolean isWallBetween(double ex, double ey, double px, double py) {
        if (maze == null) return false;
        var walls = maze.getMazeVectors();
        if (walls == null || walls.isEmpty()) return false;
        return WallCollisionUtil.wallBetweenVectors(ex, ey, px, py, walls);
    }

    /**
     * Called from the projectile update loop (background thread) when a projectile
     * hits a wall. If the wall is breakable its HP is reduced; if it reaches zero
     * the wall is removed and the navigation graph is rewired. The mutation is
     * scheduled on the JavaFX Application Thread for thread safety.
     */
    public void applyProjectileDamageToWall(Vector2D wall, int damage) {
        if (maze == null) return;
        BreakableWall bw = maze.findBreakableWall(wall);
        if (bw == null) return;
        if (javafx.application.Platform.isFxApplicationThread()) {
            maze.applyWallDamage(bw, damage);
        } else {
            javafx.application.Platform.runLater(() -> maze.applyWallDamage(bw, damage));
        }
    }

    private void ensureHudLayersOnTop() {
        if (scoreHudContainer != null)  scoreHudContainer.setViewOrder(-20);
        if (bottomMenuContainer != null) bottomMenuContainer.setViewOrder(-20);
        if (commandsOverlay != null)    commandsOverlay.setViewOrder(-30);
    }

    @FXML
    private void toggleCommandsOverlay() {
        if (commandsOverlay == null) return;
        boolean show = !commandsOverlay.isVisible();
        commandsOverlay.setVisible(show);
        commandsOverlay.setManaged(show);
        if (!show && gameBoard != null) gameBoard.requestFocus();
    }

    @FXML
    private void hideCommandsOverlay() {
        if (commandsOverlay == null) return;
        commandsOverlay.setVisible(false);
        commandsOverlay.setManaged(false);
        if (gameBoard != null) gameBoard.requestFocus();
    }

    @FXML
    private void openTerminalPrompt() {
        var dialog = new TextInputDialog();
        dialog.setTitle("Maze Terminal");
        dialog.setHeaderText("Enter command");
        dialog.setContentText(GameControllerTerminalSupport.PROMPT_CONTENT_TEXT);
        var window = (root != null && root.getScene() != null) ? root.getScene().getWindow() : null;
        if (window != null) dialog.initOwner(window);
        dialog.showAndWait().ifPresent(raw -> GameControllerTerminalSupport.executeTerminalCommand(raw, terminalCommandSink));
        if (gameBoard != null) gameBoard.requestFocus();
    }

    private void setHudMessage(String text)                    { hudCoordinator.setMessage(text); }
    private void setHudMessage(String text, Duration visibleFor) { hudCoordinator.setMessage(text, visibleFor); }

    private void openDifficultyPickerAndMaybeRestart() {
        FxDifficultyPickerSupport.open(
                root,
                () -> { stopComputerCharacters(); hideCommandsOverlay(); },
                () -> { movementLoopCoordinator.startComputerCharacters(); movementLoopCoordinator.startMovementTimer(); if (gameBoard != null) gameBoard.requestFocus(); },
                chosen -> { setStartDifficulty(chosen); App.lastChosenDifficulty = chosen; },
                () -> new main.game.maze.actions.RestartGameAction(root).Load());
    }

    @FXML
    private void handleMouseClicked(MouseEvent event) {
        LOGGER.fine("Game has been clicked");
        mouseX = event.getX();
        mouseY = event.getY();
        leftMouseClicked = true;
        mouseCoordsLabel.setText("X: " + event.getX() + ", Y: " + event.getY());
        // Reclaim keyboard focus whenever the player clicks the game area so that
        // key events keep reaching handleKeyPressed after any UI element was clicked.
        if (gameBoard != null) gameBoard.requestFocus();
    }

    @FXML
    private void showHighScore() {
        movementLoopCoordinator.stopComputerCharacters();
        HighscoreAction action = new HighscoreAction(root);
        action.Load();
    }

    public void setupGame() {
        clearActivePlayerBombs();
        hpBar.setProgress(1.0);
        installCameraFollowListeners();

        // Remove stale canvases from the previous session
        if (mazeCanvas != null) { gameBoard.getChildren().remove(mazeCanvas); mazeCanvas = null; }
        if (pathCanvas != null) { gameBoard.getChildren().remove(pathCanvas); pathCanvas = null; }
        if (treeCanvas != null) { gameBoard.getChildren().remove(treeCanvas); treeCanvas = null; }

        // Bootstrap new session: world build, player creation, canvas setup, opponent spawn
        var result = bootstrapper.setup(gameBoard, player, hpBar, startDifficulty);
        playerCharacter = result.playerCharacter();
        maze            = result.maze();
        mazeCanvas      = result.mazeCanvas();
        pathCanvas      = result.pathCanvas();
        treeCanvas      = result.treeCanvas();
        ensureHudLayersOnTop();

        // Wire per-session actions
        int baseScore = getBaseScoreForCurrentDifficulty();
        gameOverAction = new GameOverAction(playerCharacter, model.playerMoveCount(), root, () -> {});
        winGameAction  = new WinGameAction(playerCharacter, model.playerMoveCount(), root, () -> {});
        gameOverAction.setBaseScore(baseScore);
        winGameAction.setBaseScore(baseScore);
        playerCharacter.addDeathNotificationSubscriber(gameOverAction);

        winarea = new WinArea(heart);
        winarea.addPositionSubscriber(playerCharacter);
        winarea.AddWinGameAction(winGameAction);
        playerCharacter.addPositionSubscriber(winarea);

        // Wire per-session playing controller
        playingModeController = new FxPlayingModeController(model, inputRouter, playerCharacter, renderCoordinator, inputCommandContext);
        modeRouter.register(playingModeController);

        player.requestFocus();
        gameBoard.requestFocus();
        enemyCoordinator.reset();

        movementLoopCoordinator.startComputerCharacters();
        javafx.application.Platform.runLater(() -> {
            var node = root.lookup("#heart");
            if (node instanceof javafx.scene.image.ImageView heartView) {
                double heartW = heartView.getBoundsInLocal().getWidth();
                double heartH = heartView.getBoundsInLocal().getHeight();
                if (heartW <= 0) heartW = heartView.getFitWidth();
                if (heartH <= 0) heartH = heartView.getFitHeight();
                heartView.setLayoutX((App.getBoardMaxX() - heartW) / 2.0);
                heartView.setLayoutY((App.getBoardMaxY() - heartH) / 2.0);
                new CharacterIntersectionFixerService(gameBoard, maze).fixInitialSpriteMazeIntersections();
                updateCameraFollow();
            }
        });

        var score = winGameAction.resetScore();
        model.resetScoringState();
        pathHintCoordinator.stopCountdown();
        pathHintCoordinator.clearTimerLabel();
        updateScoreHud(score);

        gameBoard.setFocusTraversable(true);
        gameBoard.requestFocus();
        ensureHudLayersOnTop();
        updateCameraFollow();
        movementLoopCoordinator.startMovementTimer();
    }

    private void installCameraFollowListeners() {
        if (root == null || gameBoard == null) return;
        if (!cameraFollowListenersInstalled) {
            root.widthProperty().addListener((obs, oldVal, newVal) -> updateCameraFollow());
            root.heightProperty().addListener((obs, oldVal, newVal) -> updateCameraFollow());
            if (bottomMenuContainer != null) {
                bottomMenuContainer.heightProperty().addListener((obs, oldVal, newVal) -> updateCameraFollow());
            }
            cameraFollowListenersInstalled = true;
        }
    }

    private void handlePlayerMovementTick(long now) {
        if (playerCharacter == null || playerCharacter.getCharacterGraphics() == null) return;
        ensureGameBoardFocus();
        currentInputFrame = inputSnapshotReader.read(pressedKeys, edgeKeys, mouseX, mouseY, leftMouseClicked);
        leftMouseClicked = false;
        playingModeController.update(currentInputFrame, now);
    }

    /**
     * Self-heals keyboard focus once per frame so player input cannot become
     * permanently dead after focus is lost to a transient node or dropped to
     * {@code null}. Focus is never stolen from a text input control so the
     * in-game terminal stays usable.
     */
    private void ensureGameBoardFocus() {
        if (gameBoard == null) return;
        var scene = gameBoard.getScene();
        if (scene == null) return;
        var focusOwner = scene.getFocusOwner();
        boolean focusOwnerIsTextInput = focusOwner instanceof javafx.scene.control.TextInputControl;
        if (FxFocusGuard.shouldReassertFocus(focusOwner, gameBoard, focusOwnerIsTextInput)) {
            gameBoard.requestFocus();
        }
    }

    private void updateCameraFollow() {
        renderCoordinator.updateCameraFollow(playerCharacter);
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
        movementLoopCoordinator.startComputerCharacters();
    }

    // -----------------------------------------------------------------------
    // Delegation API (called by OpponentRuntimeFactory and characters)
    // -----------------------------------------------------------------------

    @Override
    public void registerComputerCharacter(IMovingComputerCharacter character, javafx.scene.Node node) {
        enemyCoordinator.registerCharacter(character, node);
    }

    public void unregisterComputerCharacter(IMovingComputerCharacter character, javafx.scene.Node node) {
        enemyCoordinator.unregisterCharacter(character, node);
    }

    public void showInfectionWarning() {
        enemyCoordinator.showInfectionWarning();
    }

    @Override
    public Point2D resolveEnemySpawnPosition(double desiredX, double desiredY, double enemySize) {
        return enemyCoordinator.resolveSpawnPosition(desiredX, desiredY, enemySize);
    }

    // -----------------------------------------------------------------------
    // Lifecycle
    // -----------------------------------------------------------------------

    public void stopComputerCharacters() {
        movementLoopCoordinator.stopComputerCharacters();
        movementLoopCoordinator.stopMovementTimer();
        pressedKeys.clear();
        edgeKeys.clear();
    }

    public void stopPlayerMovement() {
        movementLoopCoordinator.stopMovementTimer();
        pressedKeys.clear();
        edgeKeys.clear();
    }

    public void stopOpponentMovement() {
        movementLoopCoordinator.stopComputerCharacters();
    }

    public void dispose() {
        clearActivePlayerBombs();
        stopComputerCharacters();
        if (pathHintCoordinator != null) pathHintCoordinator.dispose();
        enemyCoordinator.dispose();

        hudCoordinator.dispose();

        if (winarea != null && playerCharacter != null) {
            playerCharacter.removePositionSubscriber(winarea);
        }
        if (playerCharacter != null) {
            playerCharacter.dispose();
            playerCharacter = null;
        }
    }

    private void clearActivePlayerBombs() {
        for (ActivePlayerBomb bomb : activePlayerBombs) {
            bomb.fuse().stop();
            if (gameBoard != null) {
                gameBoard.getChildren().remove(bomb.marker());
            }
        }
        activePlayerBombs.clear();
    }
}
