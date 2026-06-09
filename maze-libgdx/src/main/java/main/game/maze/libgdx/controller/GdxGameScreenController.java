package main.game.maze.libgdx.controller;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import main.game.maze.dto.Score;
import main.game.maze.constants.DataFileConstants;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.libgdx.backend.GdxBackend;
import main.game.maze.libgdx.game.PlayerCombatStateService;
import main.game.maze.libgdx.helper.GdxDebugOverlayState;
import main.game.maze.libgdx.helper.GdxGameFrameStateSupport;
import main.game.maze.libgdx.helper.GdxGameInputBindingsSupport;
import main.game.maze.libgdx.helper.GdxGameCombatAndEnemyFlowSupport;
import main.game.maze.libgdx.helper.GdxGameLayoutSupport;
import main.game.maze.libgdx.helper.GdxGameLifecycleSupport;
import main.game.maze.libgdx.helper.GdxGameMouseInteractionCoordinator;
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
import main.game.maze.common.input.InputFrame;
import main.game.maze.common.input.InputRouter;
import main.game.maze.common.input.KeyBindingRegistry;
import main.game.maze.libgdx.input.InputSnapshotReader;
import main.game.maze.libgdx.input.command.LibgdxInputCommandContext;
import main.game.maze.libgdx.lifecycle.GameSessionStartFlowCoordinator;
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
import main.game.maze.service.DifficultyService;

/**
 * Minimal-but-real libGDX gameplay subset: a procedurally generated maze the
 * player walks through with arrow keys until reaching the green goal cell.
 * Heavy lifting (maze generation, collision resolution) lives in pure-Java
 * helpers under {@code main.game.maze.mazeworld.generators} so it can be tested
 * headlessly.
 */
public final class GdxGameScreenController extends ApplicationAdapter {

    static final float GOAL_SIZE = 50f;
    static final int MAX_ENEMY_TICKS_PER_FRAME = 4;
    private static final long SEED = 1L;
    private static final float ROUTE_HINT_PENALTY_PER_SEC = 50f;
    private static final float ENEMY_LABEL_SECONDS = 20f;
    private static final float ENEMY_PATH_OVERLAY_SECONDS = 10f;
    private static final int TERMINAL_INPUT_MAX_CHARS = 64;
    private static final int WIN_NAME_MAX_CHARS = 24;

    final MazeArena providedMaze;
    private final float cellSize;
    final float playerSize;
    private final boolean useRealMaze;
    private final boolean autoStartOnCreate;
    private final boolean immediateStartOnCreate;
    private final boolean showHighScoresOnCreate;
    private final Runnable returnToMenuAction;
    /** If non-null, overrides {@link DifficultyService#getCurrent()} when starting a game. */
    private final Difficulty forcedDifficulty;
    private final DifficultyService difficultyService = new DifficultyService();
    final GameSession session = new GameSession();
    private final MazeVisualStyleConfig visualStyle = GdxVisualStyleSupport.loadOrDefault();
    boolean showHintInfo;
    boolean showSpanningTreeInfo;
    final GdxHudInteractionStateController hudInteractionState = new GdxHudInteractionStateController();
    private final GdxPlayerInputController playerInputController = new GdxPlayerInputController();
    final GdxDebugOverlayState debugOverlayState = new GdxDebugOverlayState();
    final GdxTerminalController terminalController = new GdxTerminalController(TERMINAL_INPUT_MAX_CHARS);
    final GdxWinOverlayController winOverlayController = new GdxWinOverlayController();
    final GdxHighScoresOverlayController highScoresOverlayController = new GdxHighScoresOverlayController();
    final GdxGameOverOverlayController gameOverOverlayController = new GdxGameOverOverlayController();
    final StatusMessageBus statusMessageBus = new StatusMessageBus();
    float enemyAnimationClock;
    final GdxModeInputController modeInputController = new GdxModeInputController();
    private final InputSnapshotReader inputSnapshotReader = new InputSnapshotReader();
    private final KeyBindingRegistry<Integer> keyBindingRegistry = new KeyBindingRegistry<>();
    private final InputRouter<Integer> inputRouter = new InputRouter<>(keyBindingRegistry);
    private final LibgdxInputCommandContext inputCommandContext;
    InputFrame<Integer> currentInputFrame = new InputFrame<>(Set.of(), Set.of(), 0, 0, false);
    private final GameModeRouter modeRouter = new GameModeRouter();
    private final GdxOverlayModeCoordinator overlayModeCoordinator;
    private final PlayingModeController playingModeController = new PlayingModeController();
    private final PlayingModeController.PlayingModeBridge playingBridge;
    private final GdxGameMouseInteractionCoordinator mouseInteractionCoordinator;
    private final GdxGameStartFlowRequestFactory startFlowRequestFactory;
    private boolean combatFrameDead;
    private float currentFrameDt;
    private final GameSessionStartFlowCoordinator gameSessionStartFlowCoordinator = new GameSessionStartFlowCoordinator();
    final GameWorldModel worldModel = new GameWorldModel();
    private final GdxGameRenderCoordinator renderCoordinator;

    MazeArena maze;
    PlayerState player;
    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private BitmapFont font;
    private GlyphLayout glyphLayout;
    OrthographicCamera camera;
    OrthographicCamera hudCamera;
    Viewport viewport;
    final RuntimeVisualModelLoader runtimeModelLoader = new RuntimeVisualModelLoader();
    private final ScoringEngine scoringEngine = new ScoringEngine();
    final EnemyDirectorService enemyDirectorService = new EnemyDirectorService();    private final GameAudioDirector gameAudioDirector = new GameAudioDirector(AudioEngine::get);
    private final GdxGameAudioCoordinator gameAudioCoordinator = new GdxGameAudioCoordinator(gameAudioDirector, visualStyle);
    final HighScoreRepository highScoreRepository =
            new FileHighScoreRepository(DataFileConstants.HighscoreFilePath);
        private final GdxAssetService assetService;
        private final boolean ownsAssetService;
    final List<GdxEnemyRuntime> animatedEnemies = worldModel.animatedEnemies();
    final PlayerCombatStateService combatState = new PlayerCombatStateService();
    private Texture playerTexture;
    private Texture playerDeathTexture;
    private Texture goalTexture;
    private Texture wallTexture;
    private Texture backgroundTexture;
    private Texture winBackgroundTexture;
    private Texture gameOverBackgroundTexture;
    
    final List<Point2D> activePathPoints = worldModel.activePathPoints();
    final List<Score> highScoreRows = worldModel.highScoreRows();
    final GdxGameWorldView gameWorldView = new GdxGameWorldView();
    final GdxHudView hudView = new GdxHudView();
    final GdxHighScoresOverlayView highScoresOverlayView = new GdxHighScoresOverlayView();
    final GdxWinOverlayView winOverlayView = new GdxWinOverlayView();
    final GdxGameOverOverlayView gameOverOverlayView = new GdxGameOverOverlayView();
    final GdxInfectionOverlayView infectionOverlayView = new GdxInfectionOverlayView();
    HudLayout hudLayout = HudLayout.zero();
    

    public GdxGameScreenController() {
        this(GdxGameScreenOptions.builder().build());
    }

    /**
     * Creates a controller that boots a game session and immediately opens the high-scores overlay.
     */
    static GdxGameScreenController forHighScores(MazeRuntimeConfig cfg, GdxAssetService assetService, Runnable returnToMenuAction) {
        return new GdxGameScreenController(GdxGameScreenOptions.builder()
                .runtimeConfig(cfg)
                .assetService(assetService)
                .ownsAssetService(false)
                .autoStartOnCreate(false)
                .showHighScoresOnCreate(true)
                .returnToMenuAction(returnToMenuAction)
                .build());
    }

    /**
     * Canonical constructor. All construction flags and collaborators are supplied
     * through an immutable {@link GdxGameScreenOptions} parameter object (SR-41),
     * replacing the previous telescoping-constructor chain.
     */
    public GdxGameScreenController(GdxGameScreenOptions options) {
        this.providedMaze = options.arena();
        this.cellSize = options.cellSize();
        this.playerSize = options.cellSize() * 0.5f;
        this.useRealMaze = options.useRealMaze();
        this.assetService = options.assetService();
        this.ownsAssetService = options.ownsAssetService();
        this.autoStartOnCreate = options.autoStartOnCreate();
        this.immediateStartOnCreate = options.immediateStartOnCreate();
        this.showHighScoresOnCreate = options.showHighScoresOnCreate();
        this.returnToMenuAction = options.returnToMenuAction();
        this.forcedDifficulty = options.forcedDifficulty();
        GdxGameCollaborators collaborators = GdxGameScreenAssembler.assemble(this);
        this.overlayModeCoordinator = collaborators.overlayModeCoordinator();
        this.inputCommandContext = collaborators.inputCommandContext();
        this.mouseInteractionCoordinator = collaborators.mouseInteractionCoordinator();
        this.playingBridge = collaborators.playingBridge();
        this.startFlowRequestFactory = collaborators.startFlowRequestFactory();
        this.renderCoordinator = collaborators.renderCoordinator();
        configureInputBindings();
        configureModeRouting();
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
        Gdx.input.setInputProcessor(GdxGameInputBindingsSupport.createTerminalKeyboardProcessor(
                () -> session.mode() == GameMode.WON && !session.winScoreSaved(),
                character -> winOverlayController.onKeyTyped((char) character, WIN_NAME_MAX_CHARS),
                character -> terminalController.onKeyTyped((char) character)));
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
        GdxGameScreenMetrics.GameStripBounds strip = GdxGameScreenMetrics.computeGameStripBounds(w, h);
        viewport.update(w, h, true);
        if (viewport instanceof ScreenViewport sv) {
            sv.setWorldSize(strip.width(), strip.height());
            sv.setScreenBounds(strip.x(), strip.y(), strip.width(), strip.height());
            sv.apply(true);
        }
    }

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

    boolean routeGameplayInput(float dt) {
        inputCommandContext.prepare(dt);
        inputCommandContext.applyPathHintHeld(currentInputFrame.isHeld(Input.Keys.P));
        inputRouter.route(currentInputFrame, inputCommandContext);
        return inputCommandContext.stopRequested();
    }

    void applyPathHintHeld(boolean held, float dt) {
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

    void applyMovementFromFrame() {
        GdxGameUpdateFlowSupport.applyMovementFromFrame(
                currentInputFrame,
                playerInputController,
                player,
                maze,
                worldModel.activePlayerSpeed(),
                currentFrameDt,
                session::incrementMoveCount);
    }

    void advanceEnemies(float dt) {
        GdxGameCombatAndEnemyFlowSupport.advanceEnemies(animatedEnemies, maze, player, enemyDirectorService, dt);
    }

    void updateCombat(float dt) {
        combatFrameDead = GdxGameCombatAndEnemyFlowSupport.updateCombat(dt, player, combatState, animatedEnemies, worldModel);
    }

    void applyDeathSequence(float dt) {
        GdxGameUpdateFlowSupport.applyDeathSequence(
                combatFrameDead,
                worldModel,
                dt,
                GdxGameScreenMetrics.DEATH_DISPLAY_DELAY_SECONDS,
                () -> session.setMode(GameMode.GAME_OVER),
                gameAudioCoordinator::switchToGameOverMusic);
    }

    boolean shouldTriggerWin() {
        return GdxGameCombatAndEnemyFlowSupport.shouldTriggerWin(session, combatFrameDead, player, worldModel);
    }

    void triggerWin() {
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
                GdxGameScreenMetrics.hpBarBottomY(hudCamera.viewportHeight),
                GdxGameScreenMetrics.bottomRowY(),
                GdxGameScreenMetrics.bottomRowHeight(),
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
    List<ActivePathPoint> enemyDisplayPath(GdxEnemyRuntime enemy) {
        // Always return the live snapshot from the movement service.
        // Points from the service are in game-world (bottom-left Y) space.
        // GdxGameWorldView expects the same space, so no Y-flip is needed here.
        return enemy.activePathPoints(enemyDirectorService.patrolService(), enemyDirectorService.adaptiveService());
    }

    void loadHighScores() {
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

    MazeArena buildArenaForSelectedDifficulty(Difficulty selectedDifficulty) {
        return GdxGameLayoutSupport.buildArenaForDifficulty(
                selectedDifficulty,
                useRealMaze,
                cellSize,
                SEED);
    }

    int baseScoreForDifficulty(Difficulty difficulty) {
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

    void requestReturnToMenu(boolean fromGame) {
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

    void flashStatus(String text) {
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

    Texture loadTexture(String classpathPath) {
        return assetService.getTexture(classpathPath);
    }
}
