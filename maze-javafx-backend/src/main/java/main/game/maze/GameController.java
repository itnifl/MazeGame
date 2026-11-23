package main.game.maze;

import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.common.graphics.UiScheduler;
import main.game.maze.common.graphics.fire.FireBlast;
import main.game.maze.common.graphics.fire.RubbleMaterial;
import main.game.maze.common.graphics.fire.WallRubble;
import main.game.maze.common.remains.DeathCause;
import main.game.maze.game.items.ArsenalHud;
import main.game.maze.game.score.PlayerIds;
import main.game.maze.game.session.PlayerLives;
import main.game.maze.interfaces.IDeathSubscriber;
import main.game.maze.javafx.render.FxWeaponHudView;
import main.game.maze.javafx.lifecycle.FxResurrectionScheduler;
import main.game.maze.javafx.render.FxExplosionView;
import main.game.maze.javafx.hud.FxBannerView;
import main.game.maze.javafx.render.FxHazardLayer;
import main.game.maze.javafx.render.FxRemainsLayer;
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
    private static final double PLAYER_FLAME_VISUAL_SECONDS = FireBlast.DEFAULT_LIFETIME_SECONDS;
    /** Full corridor width: 85 %³ of one maze hallway (≈ 37 px) for a snug fit. */
    private static final double PLAYER_FLAME_CORRIDOR_WIDTH = StageConstants.HallwayWidthPx * 0.85 * 0.85 * 0.85;

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
    /** Right click since the last frame: uses the weapon in hand. */
    private boolean rightMouseClicked;

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
        @Override public void switchWeapon()                        { GameController.this.switchWeapon(); }
    };

    private final GameControllerTerminalSupport.TerminalCommandSink terminalCommandSink = new GameControllerTerminalSupport.TerminalCommandSink() {
        @Override public void setHudMessage(String text)                          { GameController.this.setHudMessage(text); }
        @Override public void setHudMessage(String text, Duration visibleFor)     { GameController.this.setHudMessage(text, visibleFor); }
        @Override public void showEnemyDebugLabels(boolean behaviourType)         { enemyCoordinator.showEnemyDebugLabels(behaviourType); }
        @Override public void showEnemyPathsOverlay()                             { enemyCoordinator.showEnemyPathsOverlay(); }
        @Override public int killAllEnemies(DeathCause cause)                     { return enemyCoordinator.killAll(cause); }
    };

    private boolean cameraFollowListenersInstalled;

    /** Remains of killed enemies on the board; gameBoard is injected by FXML, so it is looked up lazily. */
    private final FxRemainsLayer remainsLayer = new FxRemainsLayer(() -> gameBoard);
    /** Pending enemy resurrections of the current level; cancelled on level teardown. */
    private final FxResurrectionScheduler resurrections = new FxResurrectionScheduler();

    // --- pickups, weapons and kill credit (shared rules, same as libGDX) --------------------------
    private final PlayerLives lives = new PlayerLives(1);
    private final FxWeaponHudView weaponHud = new FxWeaponHudView(() -> root);
    private final FxPlayerLifeController lifeController = new FxPlayerLifeController(lives, new FxPlayerLifeController.Host() {
        @Override public PlayerCharacter player()          { return playerCharacter; }
        @Override public void stopPlayerMovement()         { GameController.this.stopPlayerMovement(); }
        @Override public void resumePlayerMovement() {
            if (renderCoordinator != null && playerCharacter != null) {
                renderCoordinator.updateCameraFollow(playerCharacter);   // the player is back at the start
            }
            movementLoopCoordinator.startMovementTimer();
        }
        @Override public void protect(double seconds)      { weapons.protect(seconds); }
        @Override public void showMessage(String text)     { setHudMessage(text); }
        @Override public void livesChanged()               { runOnFx(GameController.this::updateBombHud); }
        @Override public IDeathSubscriber gameOver()       { return gameOverAction; }
        @Override public void lastLifeStarted()            { giveLastLifeGift(); }
        @Override public void after(double seconds, Runnable action) {
            runOnFx(() -> {
                PauseTransition wait = new PauseTransition(Duration.seconds(seconds));
                wait.setOnFinished(event -> action.run());
                wait.play();
            });
        }
    });
    private final FxWeaponController weapons = new FxWeaponController(new FxWeaponController.Host() {
        @Override public PlayerCharacter player()             { return playerCharacter; }
        @Override public Pane board()                         { return gameBoard; }
        @Override public FxEnemyCoordinator enemies()         { return enemyCoordinator; }
        @Override public int boardWidth()                     { return App.getBoardMaxX(); }
        @Override public int boardHeight()                    { return App.getBoardMaxY(); }
        @Override public void blast(double x, double y, int damage, double radius, boolean hurtsPlayer, String label) {
            blastAt(x, y, damage, radius, hurtsPlayer, label);
        }
        @Override public void nuclearBlast(double x, double y, int damage, double radius, boolean hurtsPlayer,
                                           String label) {
            nuclearBlastAt(x, y, damage, radius, hurtsPlayer, label);
        }
        @Override public void plantBomb(double x, double y)   { plantPlayerBomb(x, y); }
        @Override public void showMessage(String text)        { setHudMessage(text); }
        @Override public void arsenalChanged()                { updateBombHud(); }
        @Override public void impactShake() {
            if (gameBoard != null) main.game.maze.javafx.render.FxScreenShake.play(gameBoard.getParent());
        }
        @Override public void redrawMaze() {
            if (mazeCanvas != null && maze != null) {
                mazeCanvasRenderer.redrawInPlace(mazeCanvas, maze.getMazeVectors());
            }
        }
    });
    /** Collapsing breakable walls fall as rubble and crush enemies beside them. */
    private final GameMazeWorld.WallCollapseListener wallCollapseListener = this::onWallsCollapsed;
    private GameMazeWorld watchedWorld;
    private static final double RUBBLE_THICKNESS = WallRubble.visualThickness(StageConstants.WallThicknessPx);
    /** Rubble draws in front of the board like the other explosions. */
    private static final double RUBBLE_VIEW_ORDER = -9;

    private final List<ActivePlayerBomb> activePlayerBombs = new ArrayList<>();

    private record ActivePlayerBomb(double x, double y, Circle marker, PauseTransition fuse) {
    }

    // Extracted coordinators
    private final FxEnemyCoordinator  enemyCoordinator;
    private final FxHudCoordinator    hudCoordinator = new FxHudCoordinator(() -> mouseCoordsLabel);
    private FxPathHintCoordinator     pathHintCoordinator;
    private FxMazeCanvasRenderer      mazeCanvasRenderer;
    private FxGameSessionBootstrapper bootstrapper;
    private FxHazardLayer hazardLayer;
    private main.game.maze.javafx.render.FxLavaLayer lavaLayer;
    private main.game.maze.javafx.render.FxRocketLayer rocketLayer;
    /** Mines, the holes they leave and the teleports of the level. */
    private final FxHazardController hazards = new FxHazardController(new FxHazardController.Host() {
        @Override public PlayerCharacter player()                  { return playerCharacter; }
        @Override public void blast(double x, double y, int damage, double range) {
            // a small blast at the mine: a little less than the player takes for walls and enemies
            blastAt(x, y, damage, range, false, "Mine");
        }
        @Override public void hurtPlayer(int damage) {
            // the player, who set it off, takes a little damage (the shield still blocks it)
            if (playerCharacter != null) playerCharacter.subtractHitPoints(damage);
        }
        @Override public void teleportPlayer(double x, double y, main.game.maze.game.hazard.HazardSession.Cause cause) {
            teleportPlayerTo(x, y, cause);
        }
        @Override public void draw(main.game.maze.game.hazard.MineField field) {
            if (hazardLayer != null) hazardLayer.render(field);
        }
    });

    /** The guard towers of level 4: they shoot rpgs and a gatling gun, decided by the shared tower session. */
    private final FxGuardTowerController guardTowers = new FxGuardTowerController(new FxGuardTowerController.Host() {
        @Override public PlayerCharacter player() { return playerCharacter; }
        @Override public main.game.maze.game.targeting.SoldierTargets soldiers() {
            return weapons.soldierTargets();
        }
        @Override public int wallsBetween(double fromX, double fromY, double toX, double toY) {
            return maze == null ? 0 : main.game.maze.game.hazard.WallCrossing.countVectors(fromX, fromY, toX, toY, maze.getMazeVectors());
        }
        @Override public void blast(double x, double y, int damage, double range, String sound, boolean shake) {
            blastAt(x, y, damage, range, false, "Tower", sound, shake, null);   // nobody gets the kills
        }
        @Override public void hurtPlayer(int damage) {
            if (playerCharacter != null) playerCharacter.subtractHitPoints(damage);
        }
        @Override public void tracer(double fromX, double fromY, double toX, double toY) {
            weapons.showTracer(fromX, fromY, toX, toY);
        }
        @Override public void drawRockets(List<main.game.maze.common.graphics.fire.RocketEffect> rockets, double clockSeconds) {
            if (rocketLayer != null) rocketLayer.render(rockets, clockSeconds);
        }
        @Override public void play(String path, String key, long coolDownMillis) {
            if (coolDownMillis > 0) {
                main.game.maze.common.graphics.AudioEngine.get().playRateLimited(path, key, coolDownMillis);
            } else {
                main.game.maze.common.graphics.AudioEngine.get().play(path);
            }
        }
    });

    /** The wall builders of level 5: they walk to corridors into the block round the heart and wall them up with wood. */
    private final FxWallBuilderController wallBuilders = new FxWallBuilderController(new FxWallBuilderController.Host() {
        @Override public PlayerCharacter player() { return playerCharacter; }
        @Override public List<Vector2D> walls() { return maze != null ? maze.getMazeVectors() : List.of(); }
        @Override public void build(String builderId, Vector2D piece) {
            if (maze == null) return;
            var spec = App.levelProgress.current().wallBuilder();
            maze.addBuiltWall(piece, main.game.maze.game.level.LevelSpec.wallMaterial(spec.wallMaterial()));
            if (mazeCanvas != null) mazeCanvasRenderer.redrawInPlace(mazeCanvas, maze.getMazeVectors());
            if (spec.buildSound() != null) {
                main.game.maze.common.graphics.AudioEngine.get().playRateLimited(spec.buildSound(), "builder.build." + builderId, 250L);
            }
        }
    });

    /** A wall builder has been placed (called by the spawner). */
    public void registerWallBuilder(String id, main.game.maze.characters.WallBuilderCharacter builder) {
        wallBuilders.register(id, new FxWallBuilderController.Body() {
            @Override public double centerX() { return builder.centerX(); }
            @Override public double centerY() { return builder.centerY(); }
            @Override public boolean alive() { return builder.isAlive(); }
            @Override public void moveTo(double x, double y) { builder.placeCentreAt(x, y); }
        });
    }

    /** A guard tower has been placed (called by the spawner). */
    public void registerGuardTower(String id, main.game.maze.characters.GuardTowerCharacter tower) {
        guardTowers.register(id, new FxGuardTowerController.Body() {
            @Override public double centerX() { return tower.centerX(); }
            @Override public double centerY() { return tower.centerY(); }
            @Override public int health() { return tower.isAlive() ? Math.max(1, tower.getHitPoints()) : 0; }
        });
    }

    public GameController() {
        inputCommandContext = new JavaFxInputCommandContext(actionSink);
        JavaFxInputBindingsSupport.configureDefaultBindings(keyBindingRegistry);
        // gameBoard and root are @FXML fields — null here; suppliers are evaluated lazily after initialize().
        enemyCoordinator = new FxEnemyCoordinator(
                () -> gameBoard, () -> root, model,
                () -> maze,
                () -> playerCharacter,
                () -> pathHintCoordinator.refreshPathCanvas());
        enemyCoordinator.setTracerSink(weapons::showTracer);
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
        // the difficulty's starting score, plus the score the levels before this one ended with
        return main.game.maze.common.scoring.GameScoringConstants.baseScoreFor(startDifficulty) + App.levelProgress.carriedScore();
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
                    if (evt.getCode() == KeyCode.TAB) {
                        handleKeyPressed(evt);   // Tab switches weapons
                        evt.consume();           // ...and must not move focus off the board
                    }
                });
            }
        });

    }

    private void installBottomButtonPressEffects() {
        installButtonPressEffect(commandsMenuButton, COMMANDS_BUTTON_STYLE, COMMANDS_BUTTON_PRESSED_STYLE);
        installButtonPressEffect(terminalMenuButton, TERMINAL_BUTTON_STYLE, TERMINAL_BUTTON_PRESSED_STYLE);
    }

    /** Attack key: use the weapon in hand (bomb, laser rifle or sledgehammer). */
    void triggerPlayerFlameAttack() {
        weapons.useWeapon();
    }

    /** Tab: switch to the next weapon in stock. */
    void switchWeapon() {
        weapons.switchWeapon();
    }

    /** Credits a dead enemy to whoever killed it (called by the enemy as it dies). */
    public void recordKill(ICanDie victim, String victimTypeId, int points) {
        if (weapons.recordKill(victim, victimTypeId, points)) {
            // the live score otherwise only refreshes on a move: show the kill bonus at once
            runOnFx(this::refreshScoreHud);
        }
    }

    /** Runs now on the JavaFX Application Thread, or posts it there from another thread. */
    private static void runOnFx(Runnable action) {
        if (javafx.application.Platform.isFxApplicationThread()) {
            action.run();
        } else {
            javafx.application.Platform.runLater(action);
        }
    }

    private void refreshScoreHud() {
        if (gameOverAction == null || winGameAction == null) {
            return;
        }
        gameOverAction.updateScore();
        updateScoreHud(winGameAction.updateScore());
    }

    /** Points the local player earned for kills this game. */
    public int localKillPoints() {
        return weapons.localKillPoints();
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
        // the "extra long flames" bought in the shop make the bomb reach further and hit harder this level
        var boosts = weapons.boosts();
        blastAt(bomb.x(), bomb.y(),
                boosts.boostedBombDamage(playerCharacter != null ? playerCharacter.getFlameAttackDamage()
                        : (int) PLAYER_FLAME_DAMAGE_PER_DIRECTION),
                boosts.boostedBombRange(PLAYER_BOMB_RANGE), true, "Bomb");
    }

    /**
     * A blast at (x, y): flame corridors that break walls and hurt enemies, the visual, the deep boom and the
     * screen shake. Only the player's own bomb hurts the player.
     */
    private void blastAt(double x, double y, int damage, double range, boolean hurtsPlayer, String label) {
        blastAt(x, y, damage, range, hurtsPlayer, label, main.game.maze.common.graphics.fire.BombImpact.SOUND, true,
                PlayerIds.LOCAL_PLAYER);
    }

    /** As above, with the sound of this blast (null: silent, the caller plays its own) and whether it shakes the screen. */
    private void blastAt(double x, double y, int damage, double range, boolean hurtsPlayer, String label,
                         String sound, boolean shake, String attackerId) {
        List<Vector2D> walls = maze != null ? maze.getMazeVectors() : List.of();

        double playerCx = hurtsPlayer && playerCharacter != null
                ? playerCharacter.getCharacterPosition().getX() + StageConstants.PlayerCharacterXYSize * 0.5d
                : Double.NaN;
        double playerCy = hurtsPlayer && playerCharacter != null
                ? playerCharacter.getCharacterPosition().getY() + StageConstants.PlayerCharacterXYSize * 0.5d
                : Double.NaN;

        int enemyDamage = enemyCoordinator.applyPlayerFlameExplosion(
                x,
                y,
                damage,
                range,
                walls,
                playerCx,
                playerCy,
                dmg -> { if (hurtsPlayer && playerCharacter != null) playerCharacter.subtractHitPoints(dmg); },
                attackerId);
        if (mazeCanvas != null && maze != null) {
            mazeCanvasRenderer.redrawInPlace(mazeCanvas, maze.getMazeVectors());
        }
        // Pass surviving walls (destroyed ones already removed) so visual stops at solid walls.
        List<Vector2D> survivingWalls = maze != null ? maze.getMazeVectors() : List.of();
        showFlameExplosionVisual(x, y, range, survivingWalls);
        // a deep boom and a heavy shake of the whole screen
        if (sound != null) {
            main.game.maze.common.graphics.AudioEngine.get().play(sound);
        }
        if (shake && gameBoard != null) {
            main.game.maze.javafx.render.FxScreenShake.play(gameBoard.getParent());
        }

        if (enemyDamage > 0) {
            setHudMessage(label + " exploded, enemy damage " + enemyDamage, Duration.seconds(1.4));
        } else {
            setHudMessage(label + " exploded", Duration.seconds(1.0));
        }
    }

    /**
     * The small nuclear grenade: a round blast through walls (shared {@code NuclearBlastEngine}) that burns the enemies and
     * breaks the walls in reach, the player too when {@code hurtsPlayer}; drawn as a mushroom cloud with a deep roar and a
     * long heavy shake.
     */
    private void nuclearBlastAt(double x, double y, int damage, double radius, boolean hurtsPlayer, String label) {
        List<main.game.maze.mazeworld.flame.FlameTarget> targets = new ArrayList<>(
                enemyCoordinator.livingTargets(PlayerIds.LOCAL_PLAYER, main.game.maze.common.remains.DeathCause.FIRE));
        PlayerCharacter player = playerCharacter;
        if (hurtsPlayer && player != null) {
            double half = StageConstants.PlayerCharacterXYSize * 0.5d;
            double px = player.getCharacterPosition().getX() + half;
            double py = player.getCharacterPosition().getY() + half;
            targets.add(new main.game.maze.mazeworld.flame.FlameTarget() {
                @Override public double centerX() { return px; }
                @Override public double centerY() { return py; }
                @Override public int hitPoints() { return player.getHitPoints(); }
                @Override public void applyDamage(int amount) { player.subtractHitPoints(amount); }
                @Override public boolean isPassThrough() { return true; }
            });
        }
        int[] dealt = new int[1];
        GameMazeWorld.attributeDamageTo(PlayerIds.LOCAL_PLAYER, () -> dealt[0] =
                main.game.maze.mazeworld.flame.NuclearBlastEngine.apply(targets, maze, x, y, radius, damage));
        if (mazeCanvas != null && maze != null) {
            mazeCanvasRenderer.redrawInPlace(mazeCanvas, maze.getMazeVectors());
        }
        if (gameBoard != null) {
            var cloud = new main.game.maze.common.graphics.fire.MushroomCloudEffect(x, y, radius, -1);   // JavaFX is y-down
            FxExplosionView.of(cloud).play(gameBoard, cloud.lifetimeSeconds(), -9);
            main.game.maze.javafx.render.FxScreenShake.play(gameBoard.getParent(),
                    main.game.maze.common.graphics.fire.MushroomCloudEffect.SHAKE_SECONDS,
                    main.game.maze.common.graphics.fire.MushroomCloudEffect.SHAKE_STRENGTH);
        }
        main.game.maze.common.graphics.AudioEngine.get().play(main.game.maze.common.graphics.fire.MushroomCloudEffect.SOUND);
        setHudMessage(label + " - nuclear blast, enemy damage " + dealt[0], Duration.seconds(2.0));
    }

    private void showFlameExplosionVisual(double centerX, double centerY, double range,
                                          List<Vector2D> survivingWalls) {
        if (gameBoard == null) {
            return;
        }
        // JavaFX is y-down: +y arm reaches south, -y arm reaches north.
        FireBlast blast = new FireBlast(centerX, centerY,
                enemyCoordinator.flameVisualRange(centerX, centerY,  1,  0, survivingWalls, range),
                enemyCoordinator.flameVisualRange(centerX, centerY, -1,  0, survivingWalls, range),
                enemyCoordinator.flameVisualRange(centerX, centerY,  0,  1, survivingWalls, range),
                enemyCoordinator.flameVisualRange(centerX, centerY,  0, -1, survivingWalls, range),
                PLAYER_FLAME_CORRIDOR_WIDTH,
                FireBlast.seedFor(centerX, centerY));
        FxExplosionView.of(blast).play(gameBoard, PLAYER_FLAME_VISUAL_SECONDS, -9);
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

    /** Width of the score panel's text area (170 px panel minus the label padding). */
    private static final double ARSENAL_HUD_WIDTH = 150;
    private static final double ARSENAL_HUD_FONT_PX = 14;

    private void updateBombHud() {
        weaponHud.show(weapons.hudModel());
        if (bombsLabel == null) {
            return;
        }
        String text = weapons.statusText();   // weapons and counts are in the weapons HUD
        bombsLabel.setText(text);
        // shrink the arsenal line to the score panel so no count is cut off
        javafx.scene.text.Text probe = new javafx.scene.text.Text(text);
        probe.setFont(javafx.scene.text.Font.font("Consolas", javafx.scene.text.FontWeight.BOLD, ARSENAL_HUD_FONT_PX));
        double size = ARSENAL_HUD_FONT_PX * ArsenalHud.fitScale(probe.getLayoutBounds().getWidth(), ARSENAL_HUD_WIDTH);
        // the label's inline style sets the font size (and wins over setFont), so adjust it there
        bombsLabel.setStyle(bombsLabel.getStyle().replaceAll("-fx-font-size:\\s*[0-9.]+px",
                String.format(java.util.Locale.ROOT, "-fx-font-size: %.1fpx", size)));
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
     * Leaves remains for a killed enemy (called by the character as it dies). Coal for fire,
     * a bloody body when shot, a splat when crushed; nothing for other causes.
     */
    public void leaveRemains(Object owner, DeathCause cause, String bodySprite, double x, double y, double size) {
        UiScheduler.get().runOnUiThread(() -> remainsLayer.show(owner, cause, bodySprite, x, y, size));
    }

    /** Runs an enemy resurrection after {@code seconds}; cancelled if the level is torn down first. */
    public void scheduleResurrection(double seconds, Runnable resurrection) {
        UiScheduler.get().runOnUiThread(() -> resurrections.schedule(seconds, resurrection));
    }

    /** Removes the remains of {@code owner}, e.g. when it resurrects. */
    public void clearRemains(Object owner) {
        UiScheduler.get().runOnUiThread(() -> remainsLayer.clear(owner));
    }

    private void watchWallCollapses(GameMazeWorld world) {
        if (watchedWorld != null) {
            watchedWorld.removeWallCollapseListener(wallCollapseListener);
        }
        watchedWorld = world;
        if (world != null) {
            world.addWallCollapseListener(wallCollapseListener);
        }
    }

    /** Runs on the FX thread: wall damage is always applied there. */
    private void onWallsCollapsed(List<BreakableWall> collapsed, String attackerId) {
        for (BreakableWall wall : collapsed) {
            Vector2D g = wall.geometry;
            double ax = g.getStart().getX();
            double ay = g.getStart().getY();
            double bx = g.getEnd().getX();
            double by = g.getEnd().getY();
            if (gameBoard != null) {
                WallRubble rubble = new WallRubble(ax, ay, bx, by, RUBBLE_THICKNESS,
                        RubbleMaterial.fromMaterialId(wall.material.id()), Double.doubleToLongBits(ax * 31 + by));
                FxExplosionView.of(rubble).play(gameBoard, WallRubble.LIFETIME_SECONDS, RUBBLE_VIEW_ORDER);
            }
            enemyCoordinator.crushAlong(ax, ay, bx, by, attackerId);
        }
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
        if (event.getButton() == javafx.scene.input.MouseButton.SECONDARY) {
            rightMouseClicked = true;
        } else {
            leftMouseClicked = true;
        }
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

    /** On level 2 and later: the level's name in the HUD. */
    private void announceLevel() {
        if (App.levelProgress.number() > 1) {
            setHudMessage("Level " + App.levelProgress.number() + ": " + App.levelProgress.current().name());
        }
        // the last level, whichever it is: the words slam onto the screen and a dark voice says them
        if (main.game.maze.game.status.FinalLevelIntro.applies(App.levelProgress)) {
            banner.showTitleCard(main.game.maze.game.status.FinalLevelIntro.TEXT, main.game.maze.game.status.FinalLevelIntro.SECONDS);
            main.game.maze.common.graphics.AudioEngine.get().play(main.game.maze.game.status.FinalLevelIntro.VOICE);
        }
    }

    /** An item dropped on the board, e.g. the gun of a killed sergeant. */
    public void dropWeapon(main.game.maze.game.items.PickupKind kind, double x, double y) {
        weapons.dropPickup(kind, x, y);
    }

    /** What the player holds now; the win screen hands it on to the next level. */
    public main.game.maze.game.items.ArsenalSnapshot arsenalSnapshot() {
        return weapons.arsenalSnapshot();
    }

    public void setupGame() {
        clearActivePlayerBombs();
        resurrections.cancelAll();
        remainsLayer.clearAll();
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
        watchWallCollapses(maze);
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
        // deaths go through the lives: respawn at the start while lives are left, game over on the last
        playerCharacter.addDeathNotificationSubscriber(lifeController);
        lifeController.startGame(playerCharacter, PlayerLives.startingFor(startDifficulty));

        // new game: fresh arsenal, no items, shield or kills yet
        // the weapons of the previous level carry over (null on level 1: the difficulty's bombs)
        weapons.setBoosts(App.levelProgress.boosts());   // what was bought in the shop for this level
        // enemies near a soldier of the infantry strike go for the soldier first
        enemyCoordinator.setSoldiers(weapons.soldierTargets(), weapons.soldierLureRadius());
        weapons.startGame(playerCharacter, maze, lives, App.levelProgress.inheritedArsenal());
        if (lives.remaining() == 1) {
            giveLastLifeGift();   // a level that is started on the last life gives the gift at once
        }
        // mines round the sergeants (level 3 and on), and the free ground the teleports come out on
        // lava under the maze: flowing streams with bridges (levels that have them)
        if (lavaLayer != null) lavaLayer.detach();
        lavaLayer = new main.game.maze.javafx.render.FxLavaLayer(gameBoard);
        lavaLayer.attach(main.game.maze.game.lava.LavaEffects.of(maze.getLava(), App.levelProgress.current().lava(), App.getBoardMaxX(), App.getBoardMaxY(), 0),
                (gc, canvas) -> mazeCanvasRenderer.paintBackground(gc, canvas.getWidth(), canvas.getHeight()));
        if (hazardLayer != null) hazardLayer.detach();
        hazardLayer = new FxHazardLayer(gameBoard);
        hazardLayer.attach();
        hazards.startGame(maze, App.levelProgress.current(),
                main.game.maze.runtime.opponents.SergeantSpawner.postsFor(App.levelProgress.current(), startDifficulty, maze),
                App.getBoardMaxX() / 2.0, App.getBoardMaxY() / 2.0);   // the heart is at the centre of the board: no mine on it
        // guard towers (level 4): they are placed by the session bootstrapper; they register here as they appear
        if (rocketLayer != null) rocketLayer.detach();
        rocketLayer = new main.game.maze.javafx.render.FxRocketLayer(gameBoard);
        rocketLayer.attach();
        guardTowers.startGame(App.levelProgress.current().guardTower());
        wallBuilders.startGame(App.levelProgress.current().wallBuilder(), App.getBoardMaxX(), App.getBoardMaxY(), maze.getLava());
        announceLevel();

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
        currentInputFrame = inputSnapshotReader.read(pressedKeys, edgeKeys, mouseX, mouseY, leftMouseClicked,
                rightMouseClicked);
        leftMouseClicked = false;
        rightMouseClicked = false;
        playingModeController.update(currentInputFrame, now);
        weapons.tick(now);
        tickLava();   // first: lava kills before a hole or anything else can move the player
        hazards.tick(now);
        if (lavaLayer != null) lavaLayer.render(now / 1e9);
        guardTowers.tick(now);
        wallBuilders.tick(now);
    }

    /** Level 6 and every level after it: the pulse beam cannon is given when the player starts their last life. */
    private void giveLastLifeGift() {
        weapons.grantLastLifeGift(App.levelProgress.current().lastLifeGift());
    }

    private final FxBannerView banner = new FxBannerView(() -> root);

    /** The player who steps into lava (not on a bridge) dies at once, shield or not. */
    private void tickLava() {
        if (maze == null || maze.getLava() == null || playerCharacter == null || playerCharacter.getHitPoints() <= 0) return;
        double half = StageConstants.PlayerCharacterXYSize * 0.5d;
        if (maze.getLava().isDeadly(playerCharacter.getCharacterPosition().getX() + half,
                playerCharacter.getCharacterPosition().getY() + half)) {
            var spec = App.levelProgress.current().lava();
            if (spec != null && spec.deathSound() != null) {
                main.game.maze.common.graphics.AudioEngine.get().play(spec.deathSound());
            }
            setHudMessage("Burned in the lava!", Duration.seconds(1.6));
            playerCharacter.killOutright();
        }
    }

    /** The player is sent to a new place (centre at x, y): move, camera, sound, sparkle and a message. */
    private void teleportPlayerTo(double x, double y, main.game.maze.game.hazard.HazardSession.Cause cause) {
        if (playerCharacter == null) return;
        double half = StageConstants.PlayerCharacterXYSize * 0.5d;
        playerCharacter.teleportTo(x - half, y - half);
        renderCoordinator.updateCameraFollow(playerCharacter);
        // the player flashes in, the screen says Teleported and a deep voice says it too
        weapons.showEffect(main.game.maze.game.hazard.TeleportFeedback.flashAt(x, y, false));
        var rules = App.levelProgress.current().teleport();
        if (rules.zapSound() != null) main.game.maze.common.graphics.AudioEngine.get().play(rules.zapSound());
        if (rules.voiceSound() != null) main.game.maze.common.graphics.AudioEngine.get().play(rules.voiceSound());
        banner.show(rules.bannerText(), rules.bannerSeconds());
    }

    /** A portal ghost touched the player (called by the ghost). */
    public void onPortalGhostTouch() {
        hazards.onPortalGhostTouch(System.nanoTime());
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
        hazards.dispose();
        guardTowers.dispose();
        wallBuilders.dispose();
        if (rocketLayer != null) { rocketLayer.detach(); rocketLayer = null; }
        banner.hide();
        if (hazardLayer != null) { hazardLayer.detach(); hazardLayer = null; }
        if (lavaLayer != null) { lavaLayer.detach(); lavaLayer = null; }
        weapons.dispose();
        weaponHud.clear();
        lifeController.dispose();
        resurrections.cancelAll();
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
