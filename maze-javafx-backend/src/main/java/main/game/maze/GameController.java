package main.game.maze;

import java.net.URL;
import java.util.EnumSet;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import main.game.maze.actions.GameOverAction;
import main.game.maze.actions.HighscoreAction;
import main.game.maze.actions.WinGameAction;
import main.game.maze.areas.WinArea;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.characters.interfaces.IMovingComputerCharacter;
import main.game.maze.config.model.PlayerConfig;
import main.game.maze.config.service.XmiRulesLoader;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import main.game.maze.common.graphics.config.PropertiesMazeVisualStyleLoader;
import main.game.maze.common.graphics.config.XmiMazeVisualStyleLoader;
import main.game.maze.common.input.InputFrame;
import main.game.maze.common.input.InputRouter;
import main.game.maze.common.input.KeyBindingRegistry;
import main.game.maze.common.controller.state.GameModeRouter;
import main.game.maze.javafx.audio.FxGameAudioCoordinator;
import main.game.maze.javafx.controller.state.FxPlayingModeController;
import main.game.maze.javafx.render.FxGameRenderCoordinator;
import main.game.maze.javafx.render.FxMazeCanvasRenderer;
import main.game.maze.javafx.render.FxPathHintCoordinator;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.WallCollisionUtil;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.constants.PlayerConstants;
import main.game.maze.runtime.opponents.OpponentRuntimeFactory;
import main.game.maze.service.CharacterIntersectionFixerService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameController implements Initializable {
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
    @FXML private AnchorPane bottomMenuContainer;
    @FXML private AnchorPane commandsOverlay;
    @FXML private Button commandsMenuButton;
    @FXML private Button terminalMenuButton;
    @FXML private Label pathHintTimerLabel;

    private static final String COMMANDS_BUTTON_STYLE         = "-fx-background-color: rgba(143,255,224,0.85); -fx-text-fill: #103630; -fx-font-family: 'Consolas'; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 4;";
    private static final String COMMANDS_BUTTON_PRESSED_STYLE = "-fx-background-color: rgba(115,215,189,0.95); -fx-text-fill: #0a2924; -fx-font-family: 'Consolas'; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 4;";
    private static final String TERMINAL_BUTTON_STYLE         = "-fx-background-color: rgba(255,229,110,0.90); -fx-text-fill: #2f1d00; -fx-font-family: 'Consolas'; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 4;";
    private static final String TERMINAL_BUTTON_PRESSED_STYLE = "-fx-background-color: rgba(232,197,79,0.95); -fx-text-fill: #251700; -fx-font-family: 'Consolas'; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 4;";

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
    private FxGameAudioCoordinator audioCoordinator;

    private double mouseX;
    private double mouseY;
    private boolean leftMouseClicked;

    private final JavaFxInputCommandContext.ActionSink actionSink = new JavaFxInputCommandContext.ActionSink() {
        @Override public void showHighScore()                       { GameController.this.showHighScore(); }
        @Override public void openDifficultyPickerAndMaybeRestart(){ GameController.this.openDifficultyPickerAndMaybeRestart(); }
        @Override public void showNavigationPath()                  { GameController.this.showNavigationPath(); }
        @Override public void showSpanningTree()                    { GameController.this.showSpanningTree(); }
        @Override public void clearNavigationPath()                 { GameController.this.clearNavigationPath(); }
        @Override public void clearSpanningTree()                   { GameController.this.clearSpanningTree(); }
        @Override public void updateDebugLabels()                   { GameController.this.updateDebugLabels(); }
        @Override public void updateScoreHud() {
            var score = winGameAction.updateScore();
            GameController.this.updateScoreHud(score);
        }
        @Override public void openTerminalPrompt()                  { GameController.this.openTerminalPrompt(); }
    };

    private final GameControllerTerminalSupport.TerminalCommandSink terminalCommandSink = new GameControllerTerminalSupport.TerminalCommandSink() {
        @Override public void setHudMessage(String text)                          { GameController.this.setHudMessage(text); }
        @Override public void setHudMessage(String text, Duration visibleFor)     { GameController.this.setHudMessage(text, visibleFor); }
        @Override public void showEnemyDebugLabels(boolean behaviourType)         { enemyCoordinator.showEnemyDebugLabels(behaviourType); }
        @Override public void showEnemyPathsOverlay()                             { enemyCoordinator.showEnemyPathsOverlay(); }
    };

    private int playerMovementSpeed = StageConstants.PlayerCharacterSpeed;
    private PauseTransition hudMessageClearTimer;
    private boolean cameraFollowListenersInstalled;
    private Rectangle gameBoardClip;

    // Extracted coordinators
    private final FxEnemyCoordinator enemyCoordinator;
    private FxPathHintCoordinator pathHintCoordinator;
    private FxMazeCanvasRenderer mazeCanvasRenderer;

    public GameController() {
        inputCommandContext = new JavaFxInputCommandContext(actionSink);
        JavaFxInputBindingsSupport.configureDefaultBindings(keyBindingRegistry);
        enemyCoordinator = new FxEnemyCoordinator(
                null, null, model,
                () -> maze,
                () -> playerCharacter,
                this::refreshPathCanvasOverlay);
        // Label resolved lazily — FXML field is null until initialize() runs.
        pathHintCoordinator = new FxPathHintCoordinator(
                () -> pathHintTimerLabel,
                model,
                () -> startDifficulty,
                () -> maze,
                () -> playerCharacter,
                () -> heart,
                this::refreshPathCanvasOverlay);
    }

    public void setStartDifficulty(Difficulty d) { this.startDifficulty = d; }

    int getBaseScoreForCurrentDifficulty() {
        return main.game.maze.common.scoring.GameScoringConstants.baseScoreFor(startDifficulty);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        audioCoordinator  = new FxGameAudioCoordinator(VISUAL_STYLE);
        renderCoordinator = new FxGameRenderCoordinator(gameBoard);
        mazeCanvasRenderer = new FxMazeCanvasRenderer(VISUAL_STYLE, this::difficultyName);

        javafx.application.Platform.runLater(() -> {
            installBottomButtonPressEffects();
            if (gameBoard != null) gameBoard.requestFocus();
        });
    }

    private void installBottomButtonPressEffects() {
        installButtonPressEffect(commandsMenuButton, COMMANDS_BUTTON_STYLE, COMMANDS_BUTTON_PRESSED_STYLE);
        installButtonPressEffect(terminalMenuButton, TERMINAL_BUTTON_STYLE, TERMINAL_BUTTON_PRESSED_STYLE);
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
        if (coordinatesLabel != null)  coordinatesLabel.setTextFill(Color.WHITE);
        if (mouseCoordsLabel != null)  mouseCoordsLabel.setTextFill(Color.WHITE);
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

    private void setHudMessage(String text) {
        if (hudMessageClearTimer != null) { hudMessageClearTimer.stop(); hudMessageClearTimer = null; }
        if (mouseCoordsLabel != null) mouseCoordsLabel.setText(text);
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
                movementLoopCoordinator.startComputerCharacters();
                movementLoopCoordinator.startMovementTimer();
                if (gameBoard != null) gameBoard.requestFocus();
            }
        });

        movementLoopCoordinator.startComputerCharacters();
        movementLoopCoordinator.startMovementTimer();
        if (gameBoard != null) gameBoard.requestFocus();
    }

    @FXML
    private void handleMouseClicked(MouseEvent event) {
        LOGGER.fine("Game has been clicked");
        mouseX = event.getX();
        mouseY = event.getY();
        leftMouseClicked = true;
        mouseCoordsLabel.setText("X: " + event.getX() + ", Y: " + event.getY());
    }

    @FXML
    private void showHighScore() {
        movementLoopCoordinator.stopComputerCharacters();
        HighscoreAction action = new HighscoreAction(root);
        action.Load();
    }

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

        playerCharacter = new PlayerCharacter(player, player.getLayoutX(), player.getLayoutY(), hpBar, playerConfig);

        playingModeController = new FxPlayingModeController(model, inputRouter, playerCharacter, renderCoordinator, inputCommandContext);
        modeRouter.register(playingModeController);

        var vectors = maze.getMazeVectors();

        if (mazeCanvas != null) { gameBoard.getChildren().remove(mazeCanvas); mazeCanvas = null; }
        if (pathCanvas != null) { gameBoard.getChildren().remove(pathCanvas); pathCanvas = null; }
        if (treeCanvas != null) { gameBoard.getChildren().remove(treeCanvas); treeCanvas = null; }

        mazeCanvas = mazeCanvasRenderer.drawCanvas(vectors);
        gameBoard.getChildren().add(0, mazeCanvas);

        pathCanvas = new Canvas(App.getBoardMaxX(), App.getBoardMaxY());
        pathCanvas.setMouseTransparent(true);
        gameBoard.getChildren().add(pathCanvas);

        treeCanvas = new Canvas(App.getBoardMaxX(), App.getBoardMaxY());
        treeCanvas.setMouseTransparent(true);
        gameBoard.getChildren().add(treeCanvas);
        ensureHudLayersOnTop();

        gameOverAction = new GameOverAction(playerCharacter, model.playerMoveCount(), root, () -> {});
        winGameAction  = new WinGameAction(playerCharacter, model.playerMoveCount(), root, () -> {});

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
        enemyCoordinator.reset();

        if (startDifficulty != null) {
            OpponentRuntimeFactory.instantiateFromModel(this, startDifficulty);
        } else {
            OpponentRuntimeFactory.instantiateFromModel(this);
        }

        movementLoopCoordinator.startComputerCharacters();
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

    private void installGameBoardClip() {
        if (root == null || gameBoard == null) return;
        if (gameBoardClip == null) {
            gameBoardClip = new Rectangle();
            gameBoard.setClip(gameBoardClip);
        }
        if (!gameBoardClip.widthProperty().isBound())  gameBoardClip.widthProperty().bind(root.widthProperty());
        if (!gameBoardClip.heightProperty().isBound()) gameBoardClip.heightProperty().bind(root.heightProperty());
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
            return loader.loadPlayerConfigFromClasspath(PlayerConstants.PlayerModelPath, PlayerConstants.PlayerModelEcorePath);
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Failed to load player model, falling back to defaults", ex);
            return PlayerConfig.defaults();
        }
    }

    private void setPlayerBaseImage(ImageView playerImageView, String imagePath) {
        if (imagePath == null || imagePath.isBlank()) return;
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

    private void handlePlayerMovementTick(long now) {
        if (playerCharacter == null || playerCharacter.getCharacterGraphics() == null) return;
        currentInputFrame = inputSnapshotReader.read(pressedKeys, edgeKeys, mouseX, mouseY, leftMouseClicked);
        leftMouseClicked = false;
        playingModeController.update(currentInputFrame, now);
    }

    private void updateCameraFollow() {
        renderCoordinator.updateCameraFollow(playerCharacter);
    }

    private void updateBoardBackground() {
        String bgPath = VISUAL_STYLE.backgroundImageForDifficultyName(difficultyName());
        try {
            var url = getClass().getResource(bgPath);
            if (url != null) {
                Image bgImage = new Image(url.toExternalForm());
                BackgroundImage bi = new BackgroundImage(bgImage,
                        BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                        BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
                gameBoard.setBackground(new Background(bi));
            } else {
                LOGGER.warning("Could not find background image: " + bgPath);
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error loading background", e);
        }
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

    public void registerComputerCharacter(IMovingComputerCharacter character, javafx.scene.Node node) {
        enemyCoordinator.registerCharacter(character, node);
    }

    public void unregisterComputerCharacter(IMovingComputerCharacter character, javafx.scene.Node node) {
        enemyCoordinator.unregisterCharacter(character, node);
    }

    public void showInfectionWarning() {
        enemyCoordinator.showInfectionWarning();
    }

    public Point2D resolveEnemySpawnPosition(double desiredX, double desiredY, double enemySize) {
        return enemyCoordinator.resolveSpawnPosition(desiredX, desiredY, enemySize);
    }

    // -----------------------------------------------------------------------
    // Path hint (delegating — tests access these methods on GameController)
    // -----------------------------------------------------------------------

    private void showNavigationPath() {
        long remainingNanos = pathHintBudgetNanos() - model.pathHintTotalUsedNanos();
        if (remainingNanos <= 0) {
            if (pathHintCoordinator != null) pathHintCoordinator.showExhaustedMessage();
            return;
        }
        model.setRouteHintVisible(true);
        if (!model.pathHintKeyDown()) {
            model.setPathHintKeyDown(true);
            model.setPathHintPressStartNanos(System.nanoTime());
            model.setLastRouteHintPenaltyNanos(model.pathHintPressStartNanos());
            if (pathHintCoordinator != null) pathHintCoordinator.startCountdown();
        }
        refreshPathCanvasOverlay();
    }

    private void clearNavigationPath() {
        if (model.pathHintKeyDown()) {
            long heldNanos = System.nanoTime() - model.pathHintPressStartNanos();
            model.setPathHintTotalUsedNanos(Math.min(model.pathHintTotalUsedNanos() + heldNanos, pathHintBudgetNanos()));
            model.setPathHintKeyDown(false);
        }
        model.setRouteHintVisible(false);
        if (pathHintCoordinator != null) pathHintCoordinator.stopCountdown();
        if (pathHintCoordinator != null) pathHintCoordinator.clearTimerLabel();
        refreshPathCanvasOverlay();
    }

    private long pathHintBudgetNanos() {
        return pathHintCoordinator.budgetNanos();
    }

    // -----------------------------------------------------------------------
    // Canvas overlay
    // -----------------------------------------------------------------------

    private void refreshPathCanvasOverlay() {
        if (pathCanvas == null) return;
        GraphicsContext gc = pathCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, pathCanvas.getWidth(), pathCanvas.getHeight());
        if (model.isRouteHintVisible()) {
            pathHintCoordinator.drawPlayerNavigationPath(gc);
        }
        if (model.enemyPathOverlayVisible()) {
            enemyCoordinator.drawEnemyNavigationPaths(gc);
        }
        gc.setGlobalAlpha(1.0);
    }

    private void showSpanningTree() {
        renderCoordinator.showSpanningTree(treeCanvas, playerCharacter, maze);
    }

    private void clearSpanningTree() {
        renderCoordinator.clearSpanningTree(treeCanvas);
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
        stopComputerCharacters();
        if (pathHintCoordinator != null) pathHintCoordinator.dispose();
        enemyCoordinator.dispose();

        if (hudMessageClearTimer != null) { hudMessageClearTimer.stop(); hudMessageClearTimer = null; }

        if (winarea != null && playerCharacter != null) {
            playerCharacter.removePositionSubscriber(winarea);
        }
        if (playerCharacter != null) {
            playerCharacter.dispose();
            playerCharacter = null;
        }
    }
}
