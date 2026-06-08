package main.game.maze.libgdx.controller;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.List;
import java.util.Set;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.config.MazeRuntimeConfig;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import main.game.maze.common.movement.ActivePathPoint;
import main.game.maze.libgdx.audio.GdxGameAudioCoordinator;
import main.game.maze.game.audio.GameAudioDirector;
import main.game.maze.game.runtime.EnemyDirectorService;
import main.game.maze.game.session.GameMode;
import main.game.maze.game.session.GameSession;
import main.game.maze.game.score.FileHighScoreRepository;
import main.game.maze.game.score.HighScoreRepository;
import main.game.maze.game.score.ScoringEngine;
import main.game.maze.game.status.StatusMessageBus;
import main.game.maze.common.terminal.TerminalCommand;
import main.game.maze.common.scoring.GameScoringConstants;
import main.game.maze.libgdx.movement.GdxWorldView;
import main.game.maze.dto.Score;
import main.game.maze.constants.DataFileConstants;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.libgdx.backend.GdxBackend;
import main.game.maze.libgdx.game.PlayerCombatStateService;
import main.game.maze.libgdx.helper.GdxDebugOverlayState;
import main.game.maze.libgdx.helper.GdxGameFrameStateSupport;
import main.game.maze.libgdx.helper.GdxGameInputBindingsSupport;
import main.game.maze.libgdx.helper.GdxGameInteractionSupport;
import main.game.maze.libgdx.helper.GdxGameCombatAndEnemyFlowSupport;
import main.game.maze.libgdx.helper.GdxGameLayoutSupport;
import main.game.maze.libgdx.helper.GdxGameLifecycleSupport;
import main.game.maze.libgdx.helper.GdxGameMouseInteractionCoordinator;
import main.game.maze.libgdx.helper.GdxGamePlayingBridgeFactory;
import main.game.maze.libgdx.helper.GdxGameRuntimeSupport;
import main.game.maze.libgdx.helper.GdxGameStartFlowRequestFactory;
import main.game.maze.libgdx.helper.GdxGameStartFlowApplySupport;
import main.game.maze.libgdx.helper.GdxGameUpdateFlowSupport;
import main.game.maze.libgdx.helper.GdxScoreSupport;
import main.game.maze.libgdx.helper.GdxTerminalCommandSupport;
import main.game.maze.libgdx.helper.GdxVisualStyleSupport;
import main.game.maze.libgdx.controller.state.GameModeRouter;
import main.game.maze.libgdx.controller.state.GdxOverlayModeCoordinator;
import main.game.maze.libgdx.controller.state.PlayingModeController;
import main.game.maze.libgdx.input.InputFrame;
import main.game.maze.libgdx.input.InputRouter;
import main.game.maze.libgdx.input.InputSnapshotReader;
import main.game.maze.libgdx.input.KeyBindingRegistry;
import main.game.maze.libgdx.input.command.LibgdxInputCommandContext;
import main.game.maze.libgdx.lifecycle.GameSessionStartFlowCoordinator;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.libgdx.model.RuntimeVisualModelLoader;
import main.game.maze.libgdx.render.GdxGameRenderCoordinator;
import main.game.maze.libgdx.service.GdxAssetService;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.view.GdxGameWorldView;
import main.game.maze.libgdx.view.GdxGameOverOverlayView;
import main.game.maze.libgdx.view.GdxHudView;
import main.game.maze.libgdx.view.GdxHighScoresOverlayView;
import main.game.maze.libgdx.view.GdxInfectionOverlayView;
import main.game.maze.libgdx.view.GdxWinOverlayView;
import main.game.maze.libgdx.view.layout.HudLayout;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.service.DifficultyService;

/**
 * Minimal-but-real libGDX gameplay subset: a procedurally generated maze the
 * player walks through with arrow keys until reaching the green goal cell.
 * Heavy lifting (maze generation, collision resolution) lives in pure-Java
 * helpers under {@code main.game.maze.mazeworld.generators} so it can be tested
 * headlessly.
 */
public final class GdxGameScreenController extends ApplicationAdapter {

    private static final float DEFAULT_CELL_SIZE = 48f;
    private static final int DEFAULT_COLS = 16;
    private static final int DEFAULT_ROWS = 12;
    private static final float DEFAULT_PLAYER_SPEED = DEFAULT_CELL_SIZE * 3.5f;
    private static final float WALL_THICKNESS = (float) StageConstants.WallThicknessPx;
    private static final float GOAL_SIZE = 50f;
    private static final float JAVA_FX_TICK_RATE = 30f;
    private static final int MAX_ENEMY_TICKS_PER_FRAME = 4;
    private static final float BOTTOM_BAR_HEIGHT = 40f;
    private static final float HP_BAR_HEIGHT = 20f;
    private static final float TOP_MARGIN = 22f;
    private static final float SCORE_PANEL_WIDTH = 170f;
    private static final float SCORE_PANEL_HEIGHT = 30f;
    private static final long SEED = 1L;
    private static final float ROUTE_HINT_PENALTY_PER_SEC = 50f;
    private static final float PLAYER_ALIVE_SCALE = 1f;
    private static final float PLAYER_DEAD_SCALE = 1.8f;
    private static final float HALF_RATIO = 0.5f;
    private static final String INFECTION_WARNING_TEXT = "Infected!";
    private static final float INFECTION_TRIANGLE_WIDTH = 120f;
    private static final float INFECTION_TRIANGLE_HEIGHT = 106f;
    private static final float INFECTION_PULSE_SPEED = 3.2f;
    private static final int INFECTION_GLOW_LAYERS = 6;
    private static final int INFECTION_EDGE_LAYERS = 4;
    private static final float DEATH_DISPLAY_DELAY_SECONDS = 3f;
    private static final float ENEMY_LABEL_SECONDS = 20f;
    private static final float ENEMY_PATH_OVERLAY_SECONDS = 10f;
    private static final int TERMINAL_INPUT_MAX_CHARS = 64;
    private static final int WIN_NAME_MAX_CHARS = 24;

    private final MazeArena providedMaze;
    private final float cellSize;
    private final float playerSize;
    private final boolean useRealMaze;
    private final boolean autoStartOnCreate;
    private final boolean immediateStartOnCreate;
    private final boolean showHighScoresOnCreate;
    private final Runnable returnToMenuAction;
    /** If non-null, overrides {@link DifficultyService#getCurrent()} when starting a game. */
    private final Difficulty forcedDifficulty;
    private final DifficultyService difficultyService = new DifficultyService();
    private final GameSession session = new GameSession();
    private final MazeVisualStyleConfig visualStyle = GdxVisualStyleSupport.loadOrDefault();
    private boolean showHintInfo;
    private boolean showSpanningTreeInfo;
    private final GdxHudInteractionStateController hudInteractionState = new GdxHudInteractionStateController();
    private final GdxPlayerInputController playerInputController = new GdxPlayerInputController();
    private final GdxDebugOverlayState debugOverlayState = new GdxDebugOverlayState();
    private final GdxTerminalController terminalController = new GdxTerminalController(TERMINAL_INPUT_MAX_CHARS);
    private final GdxWinOverlayController winOverlayController = new GdxWinOverlayController();
    private final GdxHighScoresOverlayController highScoresOverlayController = new GdxHighScoresOverlayController();
    private final GdxGameOverOverlayController gameOverOverlayController = new GdxGameOverOverlayController();
    private final StatusMessageBus statusMessageBus = new StatusMessageBus();
    private float enemyAnimationClock;
    private final GdxModeInputController modeInputController = new GdxModeInputController();
    private final InputSnapshotReader inputSnapshotReader = new InputSnapshotReader();
    private final KeyBindingRegistry keyBindingRegistry = new KeyBindingRegistry();
    private final InputRouter inputRouter = new InputRouter(keyBindingRegistry);
    private final LibgdxInputCommandContext inputCommandContext;
    private InputFrame currentInputFrame = new InputFrame(Set.of(), Set.of(), 0, 0, false);
    private final GameModeRouter modeRouter = new GameModeRouter();
    private final GdxOverlayModeCoordinator overlayModeCoordinator;
    private final PlayingModeController playingModeController = new PlayingModeController();
    private final PlayingModeController.PlayingModeBridge playingBridge;
    private final GdxGameMouseInteractionCoordinator mouseInteractionCoordinator;
    private final GdxGameStartFlowRequestFactory startFlowRequestFactory;
    private boolean combatFrameDead;
    private float currentFrameDt;
    private final GameSessionStartFlowCoordinator gameSessionStartFlowCoordinator = new GameSessionStartFlowCoordinator();
    private final GameWorldModel worldModel = new GameWorldModel();
    private final GdxGameRenderCoordinator renderCoordinator;

    private MazeArena maze;
    private PlayerState player;
    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private BitmapFont font;
    private GlyphLayout glyphLayout;
    private OrthographicCamera camera;
    private OrthographicCamera hudCamera;
    private Viewport viewport;
    private final RuntimeVisualModelLoader runtimeModelLoader = new RuntimeVisualModelLoader();
    private final ScoringEngine scoringEngine = new ScoringEngine();
    private final EnemyDirectorService enemyDirectorService = new EnemyDirectorService();
    private final GameAudioDirector gameAudioDirector = new GameAudioDirector(AudioEngine::get);
    private final GdxGameAudioCoordinator gameAudioCoordinator = new GdxGameAudioCoordinator(gameAudioDirector, visualStyle);
    private final HighScoreRepository highScoreRepository =
            new FileHighScoreRepository(DataFileConstants.HighscoreFilePath);
        private final GdxAssetService assetService;
        private final boolean ownsAssetService;
    private final List<GdxEnemyRuntime> animatedEnemies = worldModel.animatedEnemies();
    private final PlayerCombatStateService combatState = new PlayerCombatStateService();
    private Texture playerTexture;
    private Texture playerDeathTexture;
    private Texture goalTexture;
    private Texture wallTexture;
    private Texture backgroundTexture;
    private Texture winBackgroundTexture;
    private Texture gameOverBackgroundTexture;
    
    private final List<Point2D> activePathPoints = worldModel.activePathPoints();
    private final List<Score> highScoreRows = worldModel.highScoreRows();
    private final GdxGameWorldView gameWorldView = new GdxGameWorldView();
    private final GdxHudView hudView = new GdxHudView();
    private final GdxHighScoresOverlayView highScoresOverlayView = new GdxHighScoresOverlayView();
    private final GdxWinOverlayView winOverlayView = new GdxWinOverlayView();
    private final GdxGameOverOverlayView gameOverOverlayView = new GdxGameOverOverlayView();
    private final GdxInfectionOverlayView infectionOverlayView = new GdxInfectionOverlayView();
    private HudLayout hudLayout = HudLayout.zero();
    

    public GdxGameScreenController() {
        this(null, DEFAULT_CELL_SIZE, DEFAULT_COLS, DEFAULT_ROWS, DEFAULT_PLAYER_SPEED, true,
                new GdxAssetService(), true, false, false, null, null);
    }

    /**
     * Creates a controller that boots a game session and immediately opens the high-scores overlay.
     */
    static GdxGameScreenController forHighScores(MazeRuntimeConfig cfg, GdxAssetService assetService, Runnable returnToMenuAction) {
        return new GdxGameScreenController(
                null, cfg.cellSize(), cfg.mazeCols(), cfg.mazeRows(), cfg.playerSpeed(), cfg.useRealMaze(),
                assetService, false, false, false, true, returnToMenuAction, null);
    }

    public GdxGameScreenController(MazeArena arena) {
        this(arena, DEFAULT_CELL_SIZE, DEFAULT_COLS, DEFAULT_ROWS, DEFAULT_PLAYER_SPEED, true,
                new GdxAssetService(), true, false, false, null, null);
    }

    public GdxGameScreenController(MazeArena arena, MazeRuntimeConfig cfg) {
        this(arena, cfg.cellSize(), cfg.mazeCols(), cfg.mazeRows(), cfg.playerSpeed(), cfg.useRealMaze(),
                new GdxAssetService(), true, false, false, null, null);
    }

    public GdxGameScreenController(MazeArena arena, float cellSize, int cols, int rows, float playerSpeed, boolean useRealMaze) {
        this(arena, cellSize, cols, rows, playerSpeed, useRealMaze, new GdxAssetService(), true, false, false, null, null);
    }

    GdxGameScreenController(MazeArena arena, MazeRuntimeConfig cfg, GdxAssetService assetService, boolean ownsAssetService) {
        this(arena, cfg.cellSize(), cfg.mazeCols(), cfg.mazeRows(), cfg.playerSpeed(), cfg.useRealMaze(),
                assetService, ownsAssetService, false, false, null, null);
    }

    GdxGameScreenController(MazeArena arena, MazeRuntimeConfig cfg, GdxAssetService assetService, boolean ownsAssetService,
            boolean autoStartOnCreate) {
        this(arena, cfg.cellSize(), cfg.mazeCols(), cfg.mazeRows(), cfg.playerSpeed(), cfg.useRealMaze(),
            assetService, ownsAssetService, autoStartOnCreate, false, null, null);
    }

    GdxGameScreenController(MazeArena arena, MazeRuntimeConfig cfg, GdxAssetService assetService, boolean ownsAssetService,
            boolean autoStartOnCreate, boolean immediateStartOnCreate) {
        this(arena, cfg.cellSize(), cfg.mazeCols(), cfg.mazeRows(), cfg.playerSpeed(), cfg.useRealMaze(),
                assetService, ownsAssetService, autoStartOnCreate, immediateStartOnCreate, null, null);
    }

    GdxGameScreenController(MazeArena arena, MazeRuntimeConfig cfg, GdxAssetService assetService, boolean ownsAssetService,
            boolean autoStartOnCreate, boolean immediateStartOnCreate, Runnable returnToMenuAction) {
        this(arena, cfg.cellSize(), cfg.mazeCols(), cfg.mazeRows(), cfg.playerSpeed(), cfg.useRealMaze(),
                assetService, ownsAssetService, autoStartOnCreate, immediateStartOnCreate, returnToMenuAction, null);
    }

    GdxGameScreenController(MazeArena arena, MazeRuntimeConfig cfg, GdxAssetService assetService, boolean ownsAssetService,
            boolean autoStartOnCreate, boolean immediateStartOnCreate, Runnable returnToMenuAction,
            Difficulty forcedDifficulty) {
        this(arena, cfg.cellSize(), cfg.mazeCols(), cfg.mazeRows(), cfg.playerSpeed(), cfg.useRealMaze(),
                assetService, ownsAssetService, autoStartOnCreate, immediateStartOnCreate, returnToMenuAction,
                forcedDifficulty);
    }

    private GdxGameScreenController(MazeArena arena, float cellSize, int cols, int rows, float playerSpeed, boolean useRealMaze,
            GdxAssetService assetService, boolean ownsAssetService, boolean autoStartOnCreate,
            boolean immediateStartOnCreate, Runnable returnToMenuAction, Difficulty forcedDifficulty) {
        this(arena, cellSize, cols, rows, playerSpeed, useRealMaze, assetService, ownsAssetService,
                autoStartOnCreate, immediateStartOnCreate, false, returnToMenuAction, forcedDifficulty);
    }

    private GdxGameScreenController(MazeArena arena, float cellSize, int cols, int rows, float playerSpeed, boolean useRealMaze,
            GdxAssetService assetService, boolean ownsAssetService, boolean autoStartOnCreate,
            boolean immediateStartOnCreate, boolean showHighScoresOnCreate, Runnable returnToMenuAction, Difficulty forcedDifficulty) {
        this.providedMaze = arena;
        this.cellSize = cellSize;
        this.playerSize = cellSize * 0.5f;
        this.useRealMaze = useRealMaze;
        this.assetService = assetService;
        this.ownsAssetService = ownsAssetService;
        this.autoStartOnCreate = autoStartOnCreate;
        this.immediateStartOnCreate = immediateStartOnCreate;
        this.showHighScoresOnCreate = showHighScoresOnCreate;
        this.returnToMenuAction = returnToMenuAction;
        this.forcedDifficulty = forcedDifficulty;
        this.overlayModeCoordinator = new GdxOverlayModeCoordinator(
            session,
            modeInputController,
            highScoreRepository,
            highScoresOverlayController,
            winOverlayController,
            gameOverOverlayController);
        this.inputCommandContext = new LibgdxInputCommandContext(
            () -> terminalController.isActive(),
            () -> requestReturnToMenu(true),
            () -> GdxGameInteractionSupport.openTerminalPrompt(terminalController, this::flashStatus),
            () -> {
                loadHighScores();
                session.setMode(GameMode.HIGH_SCORES);
            },
            () -> showSpanningTreeInfo = !showSpanningTreeInfo,
            this::applyPathHintHeld,
            this::applyMovementFromFrame);
        configureInputBindings();
        configureModeRouting();
        this.mouseInteractionCoordinator = new GdxGameMouseInteractionCoordinator(
            () -> hudLayout,
            () -> hudCamera != null ? hudCamera.viewportHeight : 0f,
            hudInteractionState,
            terminalController,
            () -> viewport,
            () -> camera,
            () -> maze,
            () -> player,
            worldModel,
            this::flashStatus,
            (currentMaze, currentPlayer) -> GdxGameLayoutSupport.updateCameraFollow(viewport, currentMaze, currentPlayer, camera));
        this.playingBridge = GdxGamePlayingBridgeFactory.create(
            terminalController,
            () -> currentInputFrame,
            () -> requestReturnToMenu(true),
            combatState,
            mouseInteractionCoordinator::handle,
            this::flashStatus,
            this::routeGameplayInput,
            dt -> enemyAnimationClock += dt,
            this::advanceEnemies,
            () -> player != null,
            this::updateCombat,
            this::applyDeathSequence,
            () -> GdxGameLayoutSupport.updateCameraFollow(viewport, maze, player, camera),
            this::shouldTriggerWin,
            this::triggerWin);
        this.startFlowRequestFactory = new GdxGameStartFlowRequestFactory(
            providedMaze,
            runtimeModelLoader,
            session,
            worldModel,
            debugOverlayState,
            hudInteractionState,
            terminalController,
            modeInputController,
            enemyDirectorService,
            winOverlayController,
            combatState,
            animatedEnemies,
            this::buildArenaForSelectedDifficulty,
            this::baseScoreForDifficulty,
            this::loadTexture,
            bootstrapResult -> new GdxWorldView(bootstrapResult.maze(), bootstrapResult.player()),
            () -> showHintInfo = false,
            () -> showSpanningTreeInfo = false,
            GdxGameScreenController::toJavaFxLikeSpeed,
            playerSize,
            GOAL_SIZE,
            JAVA_FX_TICK_RATE,
            MAX_ENEMY_TICKS_PER_FRAME);
        this.renderCoordinator = new GdxGameRenderCoordinator(
            worldModel,
            animatedEnemies,
            activePathPoints,
            highScoreRows,
            session,
            debugOverlayState,
            statusMessageBus,
            hudInteractionState,
            terminalController,
            gameWorldView,
            hudView,
            highScoresOverlayView,
            winOverlayView,
            gameOverOverlayView,
            infectionOverlayView,
            winOverlayController,
            this::loadTexture,
            this::enemyDisplayPath,
            new GdxGameRenderCoordinator.RenderConstants(
                WALL_THICKNESS,
                PLAYER_ALIVE_SCALE,
                PLAYER_DEAD_SCALE,
                HALF_RATIO,
                INFECTION_EDGE_LAYERS,
                INFECTION_PULSE_SPEED,
                INFECTION_TRIANGLE_WIDTH,
                INFECTION_TRIANGLE_HEIGHT,
                INFECTION_GLOW_LAYERS,
                INFECTION_WARNING_TEXT,
                TOP_MARGIN,
                SCORE_PANEL_WIDTH,
                SCORE_PANEL_HEIGHT,
                BOTTOM_BAR_HEIGHT,
                HP_BAR_HEIGHT));
    }

    private void configureInputBindings() {
        GdxGameInputBindingsSupport.configureDefaultBindings(keyBindingRegistry);
    }

    private void configureModeRouting() {
        modeRouter
            .register(() -> overlayModeCoordinator.updateHighScores(() -> requestReturnToMenu(false)))
            .register(() -> overlayModeCoordinator.updateWon(
                hudCamera,
                this::currentScore,
                () -> requestReturnToMenu(false),
                this::openHighScoresFromWin))
            .register(() -> overlayModeCoordinator.updateGameOver(() -> requestReturnToMenu(false)));
    }

    private void openHighScoresFromWin() {
        loadHighScores();
        session.setHighScoresReturnToStartMenu(true);
        session.setMode(GameMode.HIGH_SCORES);
    }

    @Override
    public void create() {
        GdxBackend.install();
        var graphicsResources = GdxGameLifecycleSupport.createGraphicsResources();
        batch = graphicsResources.batch();
        shapes = graphicsResources.shapes();
        font = graphicsResources.font();
        glyphLayout = graphicsResources.glyphLayout();

        var overlayResources = GdxGameLifecycleSupport.createOverlayResources(this::loadTexture);
        camera = overlayResources.camera();
        hudCamera = overlayResources.hudCamera();
        winBackgroundTexture = overlayResources.winBackgroundTexture();
        gameOverBackgroundTexture = overlayResources.gameOverBackgroundTexture();

        resizeToCurrentWindow();
        gameAudioCoordinator.switchToMenuMusic();
        installTerminalKeyboardProcessor();
        startGameIfConfigured();
    }

    private void resizeToCurrentWindow() {
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void startGameIfConfigured() {
        if (immediateStartOnCreate || autoStartOnCreate) {
            startGameFromSelection();
        }
        if (showHighScoresOnCreate) {
            loadHighScores();
            session.setHighScoresReturnToStartMenu(true);
            session.setMode(GameMode.HIGH_SCORES);
        }
    }

    /**
     * Use the platform InputProcessor.keyTyped callback for terminal text
     * input so the OS-localized keyboard layout produces the right chars
     * (æ, ø, å, accented letters, etc.). Without this, libGDX's
     * Input.Keys.* constants map only US-layout ASCII.
     */
    private void installTerminalKeyboardProcessor() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyTyped(char character) {
                if (session.mode() == GameMode.WON && !session.winScoreSaved()) {
                    return winOverlayController.onKeyTyped(character, WIN_NAME_MAX_CHARS);
                }
                return terminalController.onKeyTyped(character);
            }
        });
    }

    @Override
    public void resize(int width, int height) {
        hudCamera.setToOrtho(false, Math.max(1, width), Math.max(1, height));
        hudCamera.update();
        if (viewport != null) {
            applyGameViewportBounds(width, height);
        }
    }

    private void applyGameViewportBounds(int width, int height) {
        int w = Math.max(1, width);
        int h = Math.max(1, height);
        GameStripBounds strip = computeGameStripBounds(w, h);
        viewport.update(w, h, true);
        if (viewport instanceof ScreenViewport sv) {
            sv.setWorldSize(strip.width(), strip.height());
            sv.setScreenBounds(strip.x(), strip.y(), strip.width(), strip.height());
            sv.apply(true);
        }
    }

    /**
     * Gameplay viewport screen-pixel bounds. Match JavaFX by rendering the
     * maze under HUD overlays across the full window.
     *
     * <p>Package-private for unit tests.
     */
    static GameStripBounds computeGameStripBounds(int windowWidth, int windowHeight) {
        int w = Math.max(1, windowWidth);
        int h = Math.max(1, windowHeight);
        return new GameStripBounds(0, 0, w, h);
    }

    /** HUD y of the HP bar lower edge so the bar is pinned to the window top. */
    static float hpBarBottomY(float windowHeight) {
        return Math.max(0f, windowHeight - HP_BAR_HEIGHT);
    }

    /** HUD y of the bottom command row lower edge so the bar is pinned to the window bottom. */
    static float bottomRowY() {
        return 0f;
    }

    /** HUD pixel height reserved for the bottom command row. */
    static float bottomRowHeight() {
        return BOTTOM_BAR_HEIGHT;
    }

    static float bottomBarHeight() {
        return BOTTOM_BAR_HEIGHT;
    }

    static float deathDisplayDelaySeconds() {
        return DEATH_DISPLAY_DELAY_SECONDS;
    }

    static float hpBarHeight() {
        return HP_BAR_HEIGHT;
    }

    static boolean isInfectious(EnemySpawn spawn) {
        return spawn != null && spawn.infectionLevel() > 0;
    }

    /** Package-private viewport-strip record exposed for unit tests. */
    public record GameStripBounds(int x, int y, int width, int height) {}

    @Override
    public void render() {
        float dt = clampedFrameDelta();
        update(dt);
        draw();
    }

    private float clampedFrameDelta() {
        return Math.min(Gdx.graphics.getDeltaTime(), 1f / 30f);
    }

    private void update(float dt) {
        currentFrameDt = dt;
        updateFrameState(dt);
        updateByMode(dt);
    }

    private void updateByMode(float dt) {
        if (modeRouter.update()) {
            return;
        }
        playingModeController.update(dt, playingBridge);
    }

    private void updateFrameState(float dt) {
        currentInputFrame = GdxGameFrameStateSupport.updateFrameState(
                dt,
                inputSnapshotReader,
                keyBindingRegistry,
                hudInteractionState,
                terminalController,
                debugOverlayState,
                this::flashStatus,
                statusMessageBus,
                ENEMY_LABEL_SECONDS,
                ENEMY_PATH_OVERLAY_SECONDS);
    }

    private boolean routeGameplayInput(float dt) {
        inputCommandContext.prepare(dt);
        inputCommandContext.applyPathHintHeld(currentInputFrame.isHeld(Input.Keys.P));
        inputRouter.route(currentInputFrame, inputCommandContext);
        return inputCommandContext.stopRequested();
    }

    private void applyPathHintHeld(boolean held, float dt) {
        showHintInfo = held;
        var outcome = GdxGameRuntimeSupport.applyPathPenalty(
                session.mode(),
                showHintInfo,
                worldModel.pathHintBudget(),
                worldModel.pathPenaltyPoints(),
                dt,
                ROUTE_HINT_PENALTY_PER_SEC);
        showHintInfo = outcome.showHintInfo();
        worldModel.setPathHintBudget(outcome.pathHintBudget());
        worldModel.setPathPenaltyPoints(outcome.pathPenaltyPoints());
        if (outcome.exhaustedNotified()) {
            flashStatus("Path hint energy already spent!", 3f);
        }
        GdxGameRuntimeSupport.updatePathHint(showHintInfo, maze, player, worldModel, activePathPoints);
    }

    private void applyMovementFromFrame() {
        GdxGameUpdateFlowSupport.applyMovementFromFrame(
                currentInputFrame,
                playerInputController,
                player,
                maze,
                worldModel.activePlayerSpeed(),
                currentFrameDt,
                session::incrementMoveCount);
    }

    private void advanceEnemies(float dt) {
        GdxGameCombatAndEnemyFlowSupport.advanceEnemies(animatedEnemies, maze, player, enemyDirectorService, dt);
    }

    private void updateCombat(float dt) {
        combatFrameDead = GdxGameCombatAndEnemyFlowSupport.updateCombat(dt, player, combatState, animatedEnemies, worldModel);
    }

    private void applyDeathSequence(float dt) {
        GdxGameUpdateFlowSupport.applyDeathSequence(
                combatFrameDead,
                worldModel,
                dt,
                DEATH_DISPLAY_DELAY_SECONDS,
                () -> session.setMode(GameMode.GAME_OVER),
                gameAudioCoordinator::switchToGameOverMusic);
    }

    private boolean shouldTriggerWin() {
        return GdxGameCombatAndEnemyFlowSupport.shouldTriggerWin(session, combatFrameDead, player, worldModel);
    }

    private void triggerWin() {
        GdxGameCombatAndEnemyFlowSupport.triggerWin(
                session,
                winOverlayController,
                worldModel,
                gameAudioCoordinator::switchToWinMusic);
    }

    private void draw() {
        hudLayout = renderCoordinator.render(new GdxGameRenderCoordinator.FrameInput(
                batch,
                shapes,
                font,
                glyphLayout,
                camera,
                hudCamera,
                viewport,
                maze,
                player,
                backgroundTexture,
                goalTexture,
                wallTexture,
                playerTexture,
                playerDeathTexture,
                winBackgroundTexture,
                gameOverBackgroundTexture,
                combatState.isDead(),
                enemyAnimationClock,
                hpBarBottomY(hudCamera.viewportHeight),
                bottomRowY(),
                bottomRowHeight(),
                showHintInfo,
                pathHintRemainingSeconds(),
                showSpanningTreeInfo,
                currentScore(),
                hudLayout));
    }

    static String terminalHelpText() {
        return GdxTerminalCommandSupport.helpText();
    }

    static TerminalCommand parseTerminalCommand(String raw) {
        return GdxTerminalCommandSupport.parse(raw);
    }

    /**
     * Returns the live runtime path the enemy is currently following,
     * taken directly from the movement service snapshot.
     * For AGGRESSIVE enemies this is the path held by
     * {@link main.game.maze.common.movement.AdaptiveAggressiveMovementService};
     * for PATROL enemies it is the path held by
     * {@link main.game.maze.common.movement.PatrolMovementService}.
     * Returns an empty list when the enemy has no active path (e.g. during WANDER_RECOVERY).
     * Points are in game-world coordinate space; {@link GdxGameWorldView} applies
     * the Y-flip needed for libGDX screen rendering.
     */
    private List<ActivePathPoint> enemyDisplayPath(GdxEnemyRuntime enemy) {
        // Always return the live snapshot from the movement service.
        // Points from the service are in game-world (bottom-left Y) space.
        // GdxGameWorldView expects the same space, so no Y-flip is needed here.
        return enemy.activePathPoints(enemyDirectorService.patrolService(), enemyDirectorService.adaptiveService());
    }

    private void loadHighScores() {
        highScoreRows.clear();
        highScoreRows.addAll(GdxScoreSupport.loadHighScores(highScoreRepository));
    }

    private void startGameFromSelection() {
        Difficulty selected = selectedDifficulty();
        var startFlow = gameSessionStartFlowCoordinator.start(buildStartFlowRequest(selected));
        applyStartFlow(startFlow, selected);
    }

    private Difficulty selectedDifficulty() {
        return forcedDifficulty != null ? forcedDifficulty : difficultyService.getCurrent();
    }

    private GameSessionStartFlowCoordinator.StartFlowRequest buildStartFlowRequest(Difficulty selected) {
        return startFlowRequestFactory.create(selected);
    }

    private MazeArena buildArenaForSelectedDifficulty(Difficulty selectedDifficulty) {
        return GdxGameLayoutSupport.buildArenaForDifficulty(
                selectedDifficulty,
                useRealMaze,
                cellSize,
                SEED);
    }

    private int baseScoreForDifficulty(Difficulty difficulty) {
        return GameScoringConstants.baseScoreFor(difficulty);
    }

    private void applyStartFlow(GameSessionStartFlowCoordinator.StartFlowResult startFlow, Difficulty selected) {
        var applied = GdxGameStartFlowApplySupport.apply(
                startFlow,
                selected,
                viewport,
                camera,
                worldModel,
                session,
                this::flashStatus,
                gameAudioCoordinator::switchToInGameMusic);
        maze = applied.maze();
        player = applied.player();
        viewport = applied.viewport();
        applyGameViewportBounds(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        var textures = applied.runtimeTextures();
        playerTexture = textures.playerTexture();
        playerDeathTexture = textures.playerDeathTexture();
        goalTexture = textures.goalTexture();
        wallTexture = textures.wallTexture();
        backgroundTexture = textures.backgroundTexture();
    }

    private int currentScore() {
        return GdxGameRuntimeSupport.currentScore(scoringEngine, session, worldModel);
    }

    static float toJavaFxLikeSpeed(float playerSpeed) {
        return playerSpeed * JAVA_FX_TICK_RATE;
    }

    private void requestReturnToMenu(boolean fromGame) {
        gameAudioCoordinator.switchToMenuMusic();
        if (returnToMenuAction != null) {
            returnToMenuAction.run();
            return;
        }
        returnToMenuFallback(fromGame);
    }

    private void returnToMenuFallback(boolean fromGame) {
        // Compatibility fallback for direct adapter usage without a Game router.
        session.setPausedFromGame(fromGame);
        session.setMode(GameMode.PLAYING);
        flashStatus("Return-to-menu requires screen routing from GdxGame.");
    }

    private float pathHintRemainingSeconds() {
        return worldModel.pathHintBudget().remainingSeconds();
    }

    private void flashStatus(String text) {
        statusMessageBus.publish(text);
    }

    private void flashStatus(String text, float durationSeconds) {
        statusMessageBus.publish(text, durationSeconds);
    }

    @Override
    public void dispose() {
        gameAudioCoordinator.stopAll();
        if (ownsAssetService) {
            assetService.dispose();
        }
        GdxGameLifecycleSupport.disposeGraphicsResources(batch, shapes, font);
    }

    private Texture loadTexture(String classpathPath) {
        return assetService.getTexture(classpathPath);
    }
}