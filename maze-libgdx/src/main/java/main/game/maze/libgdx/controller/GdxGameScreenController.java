package main.game.maze.libgdx.controller;

import main.game.maze.libgdx.helper.GdxWeaponSupport;
import main.game.maze.libgdx.helper.GdxStrikeSupport;
import main.game.maze.game.score.PlayerIds;
import main.game.maze.game.session.PlayerLives;
import main.game.maze.game.items.Weapon;
import main.game.maze.game.items.PickupKind;
import main.game.maze.game.items.PickupSpec;
import main.game.maze.game.items.PickupCatalog;
import main.game.maze.game.items.ArsenalHud;
import main.game.maze.common.remains.DeathCause;
import main.game.maze.common.graphics.fire.FireBlast;
import main.game.maze.libgdx.model.BombFuseGlow;
import main.game.maze.libgdx.model.FireBlastVisual;
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
import java.util.ArrayList;
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
import main.game.maze.game.score.ScoringEngine.ScoreBreakdown;
import main.game.maze.game.status.StatusMessageBus;
import main.game.maze.common.terminal.TerminalCommand;
import main.game.maze.common.scoring.GameScoringConstants;
import main.game.maze.dto.Score;
import main.game.maze.constants.DataFileConstants;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.libgdx.game.PlayerCombatStateService;
import main.game.maze.libgdx.helper.GdxDebugOverlayState;
import main.game.maze.libgdx.helper.GdxGameFrameStateSupport;
import main.game.maze.libgdx.helper.GdxGameInputBindingsSupport;
import main.game.maze.libgdx.helper.GdxGameCombatAndEnemyFlowSupport;
import main.game.maze.libgdx.helper.GdxGameLayoutSupport;
import main.game.maze.libgdx.helper.GdxGameLifecycleSupport;
import main.game.maze.libgdx.helper.GdxGameRuntimeSupport;
import main.game.maze.libgdx.helper.GdxGameStartFlowRequestFactory;
import main.game.maze.libgdx.helper.GdxGameStartFlowApplySupport;
import main.game.maze.libgdx.helper.GdxGameUpdateFlowSupport;
import main.game.maze.libgdx.helper.GdxScoreSupport;
import main.game.maze.libgdx.helper.GdxTerminalCommandSupport;
import main.game.maze.libgdx.helper.GdxWallCollapseSupport;
import main.game.maze.libgdx.helper.GdxWallDamageSupport;
import main.game.maze.libgdx.helper.GdxVisualStyleSupport;
import main.game.maze.common.graphics.fire.BombImpact;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.LevelProgress;
import main.game.maze.game.hazard.TeleportFeedback;
import main.game.maze.libgdx.helper.GdxHazardSupport;
import main.game.maze.libgdx.helper.GdxGuardTowerSupport;
import main.game.maze.libgdx.helper.GdxSergeantSupport;
import main.game.maze.libgdx.helper.GdxWallBuilderSupport;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.common.controller.state.GameModeRouter;
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
    private static final float PLAYER_BOMB_FUSE_SECONDS = 3.0f;
    private static final float PLAYER_FLAME_RANGE = 220.0f;
    private static final float PLAYER_FLAME_VISUAL_SECONDS = (float) FireBlast.DEFAULT_LIFETIME_SECONDS;
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
    private final boolean loadSavedGame;
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
    private final GdxGameStartFlowRequestFactory startFlowRequestFactory;
    private boolean combatFrameDead;
    private float currentFrameDt;
    /** Corridor points of the current level where items may spawn (world space). */
    private List<Point2D> pickupSpawnPoints = List.of();
    private int playerBombDamage;
    /** Where the player respawns after losing a life (the level start). */
    private float playerStartX;
    private float playerStartY;
    private final List<PendingPlayerBomb> pendingPlayerBombs = new ArrayList<>();
    private final List<ActivePlayerFireBlast> activePlayerFireBlasts = new ArrayList<>();
    private static final String WEAPON_SWITCH_SOUND = "/main/game/maze/sounds/weapon-switch.wav";
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
    /** Which level is being played; the win overlay moves on to the next one. */
    private final LevelProgress levelProgress = new LevelProgress(LevelCatalogLoader.loadDefault(),
            new main.game.maze.game.level.FileSavePointStore(java.nio.file.Path.of(
                    main.game.maze.constants.DataFileConstants.SavePointFilePath)));
    private final GdxSergeantSupport sergeantSupport = new GdxSergeantSupport();
    private final GdxHazardSupport hazardSupport = new GdxHazardSupport();
    private final GdxGuardTowerSupport guardTowerSupport = new GdxGuardTowerSupport();
    private final GdxWallBuilderSupport wallBuilderSupport = new GdxWallBuilderSupport();
    /** What the wall builders ask of this screen. */
    private final GdxWallBuilderSupport.Host wallBuilderHost = new GdxWallBuilderSupport.Host() {
        @Override public List<Vector2D> walls() {
            GameMazeWorld world = GdxWallDamageSupport.worldFrom(maze);
            return world == null ? List.of() : world.getMazeVectors();
        }
        @Override public void build(String builderId, Vector2D piece) {
            GameMazeWorld world = GdxWallDamageSupport.worldFrom(maze);
            var spec = levelProgress.current().wallBuilder();
            if (world == null || spec == null) {
                return;
            }
            world.addBuiltWall(piece, main.game.maze.game.level.LevelSpec.wallMaterial(spec.wallMaterial()));
            if (maze instanceof main.game.maze.mazeworld.generators.RealMaze realMaze) {
                realMaze.invalidateWalls();
            }
            if (spec.buildSound() != null) {
                AudioEngine.get().playRateLimited(spec.buildSound(), "builder.build." + builderId, 250L);
            }
        }
    };
    /** What the guard towers ask of this screen. */
    private final GdxGuardTowerSupport.Host guardTowerHost = new GdxGuardTowerSupport.Host() {
        @Override public int wallsBetween(float fromX, float fromY, float toX, float toY) {
            return maze == null ? 0 : main.game.maze.game.hazard.WallCrossing.countSegments(fromX, fromY, toX, toY, maze.walls());
        }
        @Override public void blast(float x, float y, int damage, float range, String sound, boolean shake) {
            detonateBlast(x, y, damage, range, false, "Tower", sound, shake, null);   // nobody gets the kills
        }
        @Override public void hurtPlayer(int damage) {
            combatState.applyDirectDamage(damage);
        }
        @Override public void showEffect(main.game.maze.common.graphics.fire.PuffEffect effect) {
            worldModel.effects().add(new GameWorldModel.ActiveEffect(effect));
        }
        @Override public void play(String path, String key, long coolDownMillis) {
            if (coolDownMillis > 0) {
                AudioEngine.get().playRateLimited(path, key, coolDownMillis);
            } else {
                AudioEngine.get().play(path);
            }
        }
    };
    private final ScoringEngine scoringEngine = new ScoringEngine();
    final EnemyDirectorService enemyDirectorService = new EnemyDirectorService();    private final GameAudioDirector gameAudioDirector = new GameAudioDirector(AudioEngine::get);
    private final GdxGameAudioCoordinator gameAudioCoordinator = new GdxGameAudioCoordinator(gameAudioDirector, visualStyle);
    final HighScoreRepository highScoreRepository =
            new FileHighScoreRepository(DataFileConstants.HighscoreFilePath);
        private final GdxAssetService assetService;
        private final boolean ownsAssetService;
    final List<GdxEnemyRuntime> animatedEnemies = worldModel.animatedEnemies();
    /** Collapse handling for the current level's world (see {@link #watchWallCollapses}). */
    private final GameMazeWorld.WallCollapseListener wallCollapseListener =
            GdxWallCollapseSupport.listener(worldModel, animatedEnemies, () -> maze != null ? maze.heightPx() : 0f);
    private GameMazeWorld watchedWorld;
    final PlayerCombatStateService combatState = new PlayerCombatStateService();
    private Texture playerTexture;
    private Texture playerDeathTexture;
    private Texture goalTexture;
    private Texture wallTexture;
    private Texture backgroundTexture;
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

    private record PendingPlayerBomb(float x, float y, float fuseSecondsRemaining) {
    }

    private record ActivePlayerFireBlast(FireBlast blast, float ttlSecondsRemaining) {
    }
    

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
        this.loadSavedGame = options.loadSavedGame();
        GdxGameCollaborators collaborators = GdxGameScreenAssembler.assemble(this);
        this.overlayModeCoordinator = collaborators.overlayModeCoordinator();
        this.inputCommandContext = collaborators.inputCommandContext();
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
        winOverlayController.setRestartLevelAction(this::restartCurrentLevel);
        // the shop between levels sells against the savepoint of the next level (paid with the score carried into it)
        winOverlayController.setShop(new GdxShopOverlayController(new main.game.maze.game.shop.ShopMenu(
                new main.game.maze.game.shop.LevelShop(main.game.maze.game.shop.ShopCatalogLoader.loadDefault(), levelProgress))));
        modeRouter
            .register(() -> overlayModeCoordinator.updateHighScores(() -> requestReturnToMenu(false)))
            .register(() -> overlayModeCoordinator.updateWon(
                hudCamera,
                this::displayedScore,
                () -> requestReturnToMenu(false),
                this::openHighScoresFromWin,
                this::startNextLevel))
            .register(() -> overlayModeCoordinator.updateGameOver(
                hudCamera,
                this::restartCurrentLevel,   // the level that was lost, from its savepoint (level 1: a new game)
                () -> requestReturnToMenu(false)));
    }

    private void openHighScoresFromWin() {
        loadHighScores();
        // with a next level to play, Esc goes back to the win screen instead of the main menu
        boolean backToWin = levelProgress.hasNext();
        session.setHighScoresReturnToWin(backToWin);
        session.setHighScoresReturnToStartMenu(!backToWin);
        session.setMode(GameMode.HIGH_SCORES);
    }

    @Override
    public void create() {
        var graphicsResources = GdxGameLifecycleSupport.createGraphicsResources();
        batch = graphicsResources.batch();
        shapes = graphicsResources.shapes();
        font = graphicsResources.font();
        glyphLayout = graphicsResources.glyphLayout();

        var overlayResources = GdxGameLifecycleSupport.createOverlayResources(this::loadTexture);
        camera = overlayResources.camera();
        hudCamera = overlayResources.hudCamera();
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
        if (loadSavedGame && levelProgress.resumeFromSavePoint()) {
            startCurrentLevel();   // the last level entered, with the state it was entered at
        } else if (immediateStartOnCreate || autoStartOnCreate) {
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
                ENEMY_PATH_OVERLAY_SECONDS,
                this::killEnemies);
    }

    private void killEnemies(DeathCause cause) {
        int killed = GdxGameCombatAndEnemyFlowSupport.killEnemies(
                animatedEnemies, worldModel.deadEnemies(), worldModel.dyingEnemies(), cause, worldModel.remains(),
                worldModel.retiredShooters());
        flashStatus("Killed " + killed + " enemi" + (killed == 1 ? "y" : "es"));
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

    /** Attack key: use the weapon in hand (bomb, laser rifle or sledgehammer). */
    void triggerPlayerFlameAttack() {
        if (player == null) {
            return;
        }
        if (worldModel.arsenal().selected() == Weapon.PULSE_CANNON && worldModel.arsenal().count(Weapon.PULSE_CANNON) > 0) {
            startPulseCharge();   // it takes two seconds to energy up before the beam leaves; nothing is spent yet
            return;
        }
        if (worldModel.arsenal().selected() == Weapon.LIFE_REFILL && worldModel.arsenal().count(Weapon.LIFE_REFILL) > 0
                && !GdxStrikeSupport.canRefillLife(combatState)) {
            flashStatus(main.game.maze.game.items.PickupEffects.HEALTH_ALREADY_FULL, 1.6f);   // kept for when it is needed
            return;
        }
        Weapon used = worldModel.arsenal().use();
        if (used == null) {
            AudioEngine.get().play(WEAPON_SWITCH_SOUND);
            flashStatus("Out of ammo - pick up weapons in the maze", 1.2f);
            return;
        }
        PickupCatalog catalog = worldModel.pickupField().catalog();
        if (main.game.maze.game.strike.StrikeWeapons.handles(used)) {
            // grenades, the fat bomb and the strikes: planned by the shared rules, set off in updateCombat
            flashStatus(GdxStrikeSupport.use(worldModel, maze, player, used, pickupSpawnPoints), 2.0f);
            return;
        }
        switch (used) {
            case BUNKER_BUSTER -> setOff(GdxStrikeSupport.fireBunkerBuster(worldModel, animatedEnemies, maze, player,
                    catalog.spec(PickupKind.BUNKER_BUSTER), PlayerIds.LOCAL_PLAYER));
            case LIFE_REFILL -> {
                GdxStrikeSupport.refillLife(worldModel, combatState);
                flashStatus("Health filled up", 1.2f);
            }
            default -> useClassicWeapon(used, catalog);
        }
    }

    /** A blast of a shop weapon (screen space), with the flames, boom and shake of the bomb, credited to the player. */
    private void setOff(GdxStrikeSupport.Blast blast) {
        if (blast.nuclear()) {
            nuclearBlast(blast);
            return;
        }
        detonateBlast(blast.x(), blast.y(), blast.damage(), blast.radius(), blast.hurtsPlayer(), blast.label());
    }

    /**
     * The small nuclear grenade: a round blast through walls (shared {@code NuclearBlastEngine}) that burns the enemies and
     * breaks the walls in reach, the player too when it hurts the player; a mushroom cloud, a deep roar, a long heavy shake.
     */
    private void nuclearBlast(GdxStrikeSupport.Blast blast) {
        if (maze == null) {
            return;
        }
        float height = maze.heightPx();
        List<main.game.maze.mazeworld.flame.FlameTarget> targets = new ArrayList<>(GdxStrikeSupport.livingTargets(
                animatedEnemies, height, PlayerIds.LOCAL_PLAYER, main.game.maze.common.remains.DeathCause.FIRE));
        if (blast.hurtsPlayer() && player != null) {
            float px = player.x();
            float py = height - player.y();
            targets.add(new main.game.maze.mazeworld.flame.FlameTarget() {
                @Override public double centerX() { return px; }
                @Override public double centerY() { return py; }
                @Override public int hitPoints() { return combatState.currentHitPointsAsInt(); }
                @Override public void applyDamage(int amount) { combatState.applyDirectDamage(amount); }
                @Override public boolean isPassThrough() { return true; }
            });
        }
        GameMazeWorld world = GdxWallDamageSupport.worldFrom(maze);
        int[] dealt = new int[1];
        GameMazeWorld.attributeDamageTo(PlayerIds.LOCAL_PLAYER, () -> dealt[0] =
                main.game.maze.mazeworld.flame.NuclearBlastEngine.apply(targets, world, blast.x(), height - blast.y(),
                        blast.radius(), blast.damage()));
        if (maze instanceof main.game.maze.mazeworld.generators.RealMaze realMaze) {
            realMaze.invalidateWalls();   // walls in reach are gone
        }
        // libGDX is y-up: the cloud rises in +y
        worldModel.effects().add(new GameWorldModel.ActiveEffect(
                new main.game.maze.common.graphics.fire.MushroomCloudEffect(blast.x(), blast.y(), blast.radius(), 1)));
        AudioEngine.get().play(main.game.maze.common.graphics.fire.MushroomCloudEffect.SOUND);
        worldModel.setExplosionShakeRemainingSeconds((float) main.game.maze.common.graphics.fire.MushroomCloudEffect.SHAKE_SECONDS);
        worldModel.setExplosionShakeIntensity(Math.max(worldModel.explosionShakeIntensity(),
                (float) main.game.maze.common.graphics.fire.MushroomCloudEffect.SHAKE_STRENGTH));
        int killed = GdxGameCombatAndEnemyFlowSupport.processKilledEnemies(
                animatedEnemies, worldModel.deadEnemies(), worldModel.dyingEnemies(), worldModel.remains(),
                worldModel.retiredShooters(), worldModel.killLedger());
        flashStatus(blast.label() + " - nuclear blast" + (killed > 0 ? ", " + killed + " killed" : ", damage " + dealt[0]), 2.0f);
    }

    /** The weapons from before the shop: bomb, laser, sledgehammer, gatling gun and pulse beam cannon. */
    private void useClassicWeapon(Weapon used, PickupCatalog catalog) {
        switch (used) {
            case BOMB -> {
                pendingPlayerBombs.add(new PendingPlayerBomb(player.x(), player.y(), PLAYER_BOMB_FUSE_SECONDS));
                flashStatus("Bomb planted", 1.2f);
            }
            case LASER_RIFLE -> GdxWeaponSupport.fireLaser(worldModel, animatedEnemies, maze, player,
                    catalog.spec(PickupKind.LASER_RIFLE), PlayerIds.LOCAL_PLAYER);
            case SLEDGEHAMMER -> GdxWeaponSupport.swingSledgehammer(worldModel, animatedEnemies, maze, player,
                    catalog.spec(PickupKind.SLEDGEHAMMER), PlayerIds.LOCAL_PLAYER);
            case GATLING_GUN -> GdxWeaponSupport.fireLaser(worldModel, animatedEnemies, maze, player,
                    catalog.spec(PickupKind.GATLING_GUN), PlayerIds.LOCAL_PLAYER);
            case PULSE_CANNON -> firePulseBeam();
            default -> throw new IllegalStateException("no use defined for " + used);
        }
    }

    private final main.game.maze.game.items.PulseCharge pulseCharge = new main.game.maze.game.items.PulseCharge();

    private void startPulseCharge() {
        if (pulseCharge.charging()) {
            return;
        }
        PickupSpec spec = worldModel.pickupField().catalog().spec(PickupKind.PULSE_CANNON);
        pulseCharge.start(spec.chargeSeconds());
        if (spec.chargeSound() != null) {
            AudioEngine.get().play(spec.chargeSound());
        }
        flashStatus("Pulse cannon charging...", 2.0f);
    }

    /** Per frame while the pulse beam cannon charges: the ball of light on the player, and the shot when the charge is full. */
    private void tickPulseCharge(float dt) {
        if (!pulseCharge.charging()) {
            return;
        }
        if (player == null || combatState.isDead() || worldModel.arsenal().selected() != Weapon.PULSE_CANNON
                || worldModel.arsenal().count(Weapon.PULSE_CANNON) <= 0) {
            pulseCharge.cancel();   // another weapon was chosen, or the player died: nothing is spent
            return;
        }
        worldModel.effects().add(new GameWorldModel.ActiveEffect(new main.game.maze.common.graphics.fire.PulseChargeEffect(
                player.x(), player.y(), pulseCharge.progress())));
        if (pulseCharge.tick(dt) && worldModel.arsenal().use() == Weapon.PULSE_CANNON) {
            firePulseBeam();
        }
    }

    private void firePulseBeam() {
        PickupSpec cannon = worldModel.pickupField().catalog().spec(PickupKind.PULSE_CANNON);
        // the beam: far stronger than the laser, it blows up a tower in one shot
        GdxWeaponSupport.fireLaser(worldModel, animatedEnemies, maze, player, cannon, PlayerIds.LOCAL_PLAYER);
        worldModel.setExplosionShakeRemainingSeconds((float) BombImpact.SHAKE_SECONDS * 0.6f);
        worldModel.setExplosionShakeIntensity(Math.max(worldModel.explosionShakeIntensity(), (float) BombImpact.SHAKE_STRENGTH * 0.7f));
    }

    /** Level 6 and every level after it: the pulse beam cannon is given when the player starts their last life. */
    private void giveLastLifeGift() {
        var gift = levelProgress.current().lastLifeGift();
        if (gift != null) {
            gift.give(worldModel.arsenal());
            flashStatus("Last life! " + ArsenalHud.text(worldModel.arsenal(), null), 2.5f);
        }
    }

    /** Tab: switch to the next weapon in stock. */
    void switchWeapon() {
        pulseCharge.cancel();
        Weapon before = worldModel.arsenal().selected();
        Weapon now = worldModel.arsenal().cycle();
        if (now != before) {
            AudioEngine.get().play(WEAPON_SWITCH_SOUND);
        }
        flashStatus("Weapon: " + ArsenalHud.text(worldModel.arsenal(), null), 1.0f);
    }

    void advanceEnemies(float dt) {
        // enemies near a soldier of the infantry strike go for the soldier first
        GdxGameCombatAndEnemyFlowSupport.advanceEnemies(animatedEnemies, maze, player, enemyDirectorService, dt,
                GdxStrikeSupport.soldierPositionsOnScreen(worldModel, maze), worldModel.strikes().lureRadius());
        GdxGameCombatAndEnemyFlowSupport.tickDeathAnimations(worldModel.dyingEnemies(), dt);
        if (maze != null && player != null) {
            GdxGameCombatAndEnemyFlowSupport.tickResurrections(
                    worldModel.deadEnemies(),
                    animatedEnemies,
                    new main.game.maze.libgdx.movement.GdxWorldView(maze, player),
                    GdxGameScreenMetrics.JAVA_FX_TICK_RATE,
                    MAX_ENEMY_TICKS_PER_FRAME,
                    dt,
                    worldModel.remains());
        }
    }

    void updateCombat(float dt) {
        combatFrameDead = GdxGameCombatAndEnemyFlowSupport.updateCombat(dt, player, combatState, animatedEnemies, worldModel);
        tickPlayerBombs(dt);
        // grenades, fat bombs and airstrike bombs whose fuse is out; the soldiers of the infantry strike fire
        GdxStrikeSupport.tick(worldModel, animatedEnemies, maze, dt, PlayerIds.LOCAL_PLAYER).forEach(this::setOff);
        worldModel.arsenal().tickBombRegen(dt);
        GdxWeaponSupport.updatePickups(worldModel, maze, player, combatState, pickupSpawnPoints, dt);
        // the ranged enemies shoot the soldiers of the infantry strike before the player
        var soldiers = GdxStrikeSupport.soldierTargets(worldModel, maze);
        sergeantSupport.tick(worldModel, animatedEnemies, maze, player, combatState, levelProgress.current().sergeant(), dt,
                soldiers);
        tickLava();   // first: lava kills before a hole or anything else can move the player
        tickPulseCharge(dt);
        tickHazards(dt);
        if (player != null) {
            guardTowerSupport.tick(guardTowerHost, animatedEnemies, player.x(), player.y(), dt, soldiers);
            wallBuilderSupport.tick(wallBuilderHost, animatedEnemies, player.x(), player.y(), dt);
        }
        // sergeant rounds and mines hurt the player after the combat frame was read: a lethal hit counts now,
        // so it suppresses a win on the goal in this same frame
        combatFrameDead = GdxGameCombatAndEnemyFlowSupport.deadAfterExtraDamage(combatFrameDead, combatState);
        GdxWeaponSupport.tickEffects(worldModel, animatedEnemies, dt);
        // enemies die from bombs, lasers, sledgehammers and collapsing walls; credit each kill to its killer
        GdxGameCombatAndEnemyFlowSupport.processKilledEnemies(
                animatedEnemies, worldModel.deadEnemies(), worldModel.dyingEnemies(), worldModel.remains(),
                worldModel.retiredShooters(), worldModel.killLedger());
        GdxWallCollapseSupport.tick(worldModel, dt);
        emitPlayerFlameVisuals();
    }

    private void tickPlayerBombs(float dt) {
        if (dt <= 0f) {
            return;
        }

        for (int i = pendingPlayerBombs.size() - 1; i >= 0; i--) {
            PendingPlayerBomb bomb = pendingPlayerBombs.get(i);
            float remainingFuse = bomb.fuseSecondsRemaining() - dt;
            if (remainingFuse > 0f) {
                pendingPlayerBombs.set(i, new PendingPlayerBomb(bomb.x(), bomb.y(), remainingFuse));
                continue;
            }
            pendingPlayerBombs.remove(i);
            detonatePlayerBomb(bomb.x(), bomb.y());
        }

        for (int i = activePlayerFireBlasts.size() - 1; i >= 0; i--) {
            ActivePlayerFireBlast active = activePlayerFireBlasts.get(i);
            float ttl = active.ttlSecondsRemaining() - dt;
            if (ttl > 0f) {
                activePlayerFireBlasts.set(i, new ActivePlayerFireBlast(active.blast(), ttl));
            } else {
                activePlayerFireBlasts.remove(i);
            }
        }
    }

    /** The player who steps into lava (not on a bridge) dies at once, shield or not. */
    private void tickLava() {
        if (maze == null || player == null || combatState.isDead()
                || !(maze instanceof main.game.maze.mazeworld.generators.RealMaze realMaze)) {
            return;
        }
        var lava = realMaze.sourceWorld().getLava();
        if (lava != null && lava.isDeadly(player.x(), maze.heightPx() - player.y())) {
            var spec = levelProgress.current().lava();
            if (spec != null && spec.deathSound() != null) {
                AudioEngine.get().play(spec.deathSound());
            }
            flashStatus("Burned in the lava!", 1.6f);
            combatState.killOutright();
        }
    }

    /** One frame of the mines, holes and teleports: mines that go off blast, a fall or a ghost touch teleports. */
    private void tickHazards(float dt) {
        var result = hazardSupport.tick(worldModel, player, animatedEnemies, dt, System.nanoTime() / 1e9);
        var mines = hazardSupport.mineSpec();
        for (var detonation : result.detonations()) {
            // a small blast at the mine: a little less than the player takes for walls and enemies
            detonateBlast((float) detonation.mine().x(), maze.heightPx() - (float) detonation.mine().y(),
                    mines.blastDamage(), (float) mines.blastRadius(), false, "Mine");
        }
        if (result.playerDamage() > 0) {
            // the player, who set the mines off, takes a little damage (the shield still blocks it)
            combatState.applyDirectDamage(result.playerDamage());
        }
        if (result.teleport() != null) {
            player.teleportTo(result.teleport().x(), result.teleport().y());
            // the player flashes in, the screen says Teleported and a deep voice says it too
            worldModel.effects().add(new GameWorldModel.ActiveEffect(
                    TeleportFeedback.flashAt(result.teleport().x(), result.teleport().y(), true)));
            var rules = levelProgress.current().teleport();
            if (rules.zapSound() != null) AudioEngine.get().play(rules.zapSound());
            if (rules.voiceSound() != null) AudioEngine.get().play(rules.voiceSound());
            statusMessageBus.showBanner(rules.bannerText(), (float) rules.bannerSeconds());
        }
    }

    private void detonatePlayerBomb(float x, float y) {
        // the "extra long flames" bought in the shop make the bomb reach further and hit harder this level
        var boosts = worldModel.boosts();
        detonateBlast(x, y, boosts.boostedBombDamage(playerBombDamage), (float) boosts.boostedBombRange(PLAYER_FLAME_RANGE),
                true, "Bomb");
    }

    /**
     * A blast at (x, y) (screen space): flame corridors that break walls and hurt enemies, the visual, the deep
     * boom and the screen shake. Only the player's own bomb hurts the player.
     */
    private void detonateBlast(float x, float y, int damage, float range, boolean hurtsPlayer, String label) {
        detonateBlast(x, y, damage, range, hurtsPlayer, label, BombImpact.SOUND, true, PlayerIds.LOCAL_PLAYER);
    }

    /** As above, with the sound of this blast (null: silent, the caller plays its own) and whether it shakes the screen. */
    private void detonateBlast(float x, float y, int damage, float range, boolean hurtsPlayer, String label,
                               String sound, boolean shake, String attackerId) {
        // player.x()/y() are already centre coordinates (see PlayerState.collides);
        // do NOT add halfSize() here.
        float playerCx = hurtsPlayer ? player.x() : Float.NaN;
        float playerCy = hurtsPlayer ? player.y() : Float.NaN;
        int appliedDamage = GdxGameCombatAndEnemyFlowSupport.applyDirectionalFlameExplosion(
                animatedEnemies,
                maze,
                x,
                y,
                damage,
                range,
                playerCx,
                playerCy,
                hurtsPlayer ? dmg -> combatState.applyDirectDamage(dmg) : null,
                attackerId);

        // Surviving walls (destroyed ones removed by applyDirectionalFlameExplosion above).
        GameMazeWorld survivingWorld = GdxWallDamageSupport.worldFrom(maze);
        List<Vector2D> surviving = survivingWorld != null ? survivingWorld.getMazeVectors() : List.of();

        // Wall vectors are in world space (Y-down); convert screen origin Y for correct visual ranges.
        float worldOriginY = maze.heightPx() - y;

        // Screen +y (up in libGDX) is world dirY=-1 (walls are Y-down); screen -y is world dirY=+1.
        FireBlast blast = new FireBlast(x, y,
                GdxGameCombatAndEnemyFlowSupport.flameVisualRange(x, worldOriginY,  1,  0, surviving, range),
                GdxGameCombatAndEnemyFlowSupport.flameVisualRange(x, worldOriginY, -1,  0, surviving, range),
                GdxGameCombatAndEnemyFlowSupport.flameVisualRange(x, worldOriginY,  0, -1, surviving, range),
                GdxGameCombatAndEnemyFlowSupport.flameVisualRange(x, worldOriginY,  0,  1, surviving, range),
                GdxGameCombatAndEnemyFlowSupport.FLAME_CORRIDOR_WIDTH,
                FireBlast.seedFor(x, y));
        activePlayerFireBlasts.add(new ActivePlayerFireBlast(blast, PLAYER_FLAME_VISUAL_SECONDS));

        // a deep boom and a heavy shake of the whole screen
        if (sound != null) {
            AudioEngine.get().play(sound);
        }
        if (shake) {
            worldModel.setExplosionShakeRemainingSeconds((float) BombImpact.SHAKE_SECONDS);
            worldModel.setExplosionShakeIntensity(Math.max(worldModel.explosionShakeIntensity(), (float) BombImpact.SHAKE_STRENGTH));
        }

        int flamedKilled = GdxGameCombatAndEnemyFlowSupport.processKilledEnemies(
                animatedEnemies, worldModel.deadEnemies(), worldModel.dyingEnemies(), worldModel.remains(),
                worldModel.retiredShooters(), worldModel.killLedger());

        if (appliedDamage > 0 || flamedKilled > 0) {
            String msg = flamedKilled > 0
                    ? label + " killed " + flamedKilled + " enem" + (flamedKilled == 1 ? "y" : "ies") + "!"
                    : label + " exploded, enemy damage " + appliedDamage;
            flashStatus(msg, 1.2f);
        } else {
            flashStatus(label + " exploded", 1.0f);
        }
    }

    private void emitPlayerFlameVisuals() {
        worldModel.bombFuseGlows().clear();
        for (PendingPlayerBomb bomb : pendingPlayerBombs) {
            float alpha = Math.max(0.35f, Math.min(1f, bomb.fuseSecondsRemaining() / PLAYER_BOMB_FUSE_SECONDS));
            worldModel.bombFuseGlows().add(new BombFuseGlow(bomb.x(), bomb.y(), 8f, alpha));
        }
        GdxStrikeSupport.addFuseGlows(worldModel, maze, enemyAnimationClock);

        worldModel.fireBlasts().clear();
        for (ActivePlayerFireBlast active : activePlayerFireBlasts) {
            float progress = 1f - Math.max(0f, Math.min(1f, active.ttlSecondsRemaining() / PLAYER_FLAME_VISUAL_SECONDS));
            worldModel.fireBlasts().add(new FireBlastVisual(active.blast(), progress));
        }
    }

    void applyDeathSequence(float dt) {
        GdxGameUpdateFlowSupport.applyDeathSequence(
                combatFrameDead,
                worldModel,
                dt,
                GdxGameScreenMetrics.DEATH_DISPLAY_DELAY_SECONDS,
                this::respawnPlayer,
                () -> session.setMode(GameMode.GAME_OVER),
                gameAudioCoordinator::switchToGameOverMusic);
    }

    /**
     * The player died: with lives left they come back at the start position with full health and a
     * short spawn protection; returns false when that was the last life (game over).
     */
    boolean respawnPlayer() {
        if (player == null || !worldModel.lives().loseLife()) {
            return false;
        }
        player.teleportTo(playerStartX, playerStartY);
        combatState.reset(combatState.maxHitPoints());
        combatFrameDead = false;
        worldModel.shield().activate(PlayerLives.RESPAWN_PROTECTION_SECONDS);
        int left = worldModel.lives().remaining();
        flashStatus(left == 1 ? "Last life!" : "Life lost - " + left + " lives left", 2.5f);
        pulseCharge.cancel();
        if (left == 1) {
            giveLastLifeGift();
        }
        return true;
    }

    boolean shouldTriggerWin() {
        return GdxGameCombatAndEnemyFlowSupport.shouldTriggerWin(session, combatFrameDead, player, worldModel);
    }

    /**
     * The score on the screen: on the win screen of a level with a next one, the score carried into it, which is what is
     * left after shopping (it is also what Save Score saves); otherwise the live score.
     */
    int displayedScore() {
        if (session.mode() == GameMode.WON) {
            var next = levelProgress.pendingSavePoint();
            if (next.isPresent()) {
                return next.get().score();
            }
        }
        return currentScore();
    }

    void triggerWin() {
        GdxGameCombatAndEnemyFlowSupport.triggerWin(
                session,
                winOverlayController,
                worldModel,
                gameAudioCoordinator::switchToWinMusic);
        winOverlayController.setNextLevelAvailable(levelProgress.hasNext());
        if (!levelProgress.hasNext()) {
            // the last level is won: the game is won ("GAME WON" on the screen) and a dark voice says it
            AudioEngine.get().play(main.game.maze.game.status.FinalLevelIntro.WIN_VOICE);
        }
        // the level is completed: the savepoint for the next one is made now
        levelProgress.completeLevel(worldModel.arsenal(), currentScore(), selectedDifficultyName());
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
                gameOverBackgroundTexture,
                combatState.isDead(),
                enemyAnimationClock,
                GdxGameScreenMetrics.hpBarBottomY(hudCamera.viewportHeight),
                GdxGameScreenMetrics.bottomRowY(),
                GdxGameScreenMetrics.bottomRowHeight(),
                showHintInfo,
                pathHintRemainingSeconds(),
                showSpanningTreeInfo,
                displayedScore(),
                currentBreakdown(),
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

    /** A new game: level 1 with the difficulty's own weapons. */
    private void startGameFromSelection() {
        levelProgress.restart();
        startCurrentLevel();
    }

    /** Restarts the level exactly as it was entered (its savepoint); on level 1 there is none, so that is a new game. */
    private void restartCurrentLevel() {
        levelProgress.restartLevel();
        startCurrentLevel();
    }

    /** After a win: the next level, with the weapons the player holds now. */
    private void startNextLevel() {
        if (!levelProgress.hasNext()) {
            return;
        }
        levelProgress.advance(worldModel.arsenal());
        startCurrentLevel();
    }

    private void startCurrentLevel() {
        Difficulty selected = selectedDifficulty();
        runtimeModelLoader.useLevel(levelProgress.current());
        var startFlow = gameSessionStartFlowCoordinator.start(buildStartFlowRequest(selected));
        applyStartFlow(startFlow, selected);
    }

    /** The name a savepoint keeps the difficulty under (Easy, Medium, Hard). */
    private String selectedDifficultyName() {
        Difficulty selected = selectedDifficulty();
        return selected == null ? "" : selected.eClass().getName();
    }

    private Difficulty selectedDifficulty() {
        return forcedDifficulty != null ? forcedDifficulty : difficultyService.getCurrent();
    }

    private GameSessionStartFlowCoordinator.StartFlowRequest buildStartFlowRequest(Difficulty selected) {
        return startFlowRequestFactory.create(selected);
    }

    MazeArena buildArenaForSelectedDifficulty(Difficulty selectedDifficulty) {
        MazeArena arena = GdxGameLayoutSupport.buildArenaForDifficulty(
                selectedDifficulty,
                useRealMaze,
                cellSize,
                SEED);
        // same generated maze; a level with zones only changes which walls are strong and which weak
        levelProgress.current().applyWalls(GdxWallDamageSupport.worldFrom(arena),
                (int) arena.widthPx(), (int) arena.heightPx());
        // lava under the maze, before anything is placed on it (spawns and free points keep out of the lava)
        levelProgress.current().applyLava(GdxWallDamageSupport.worldFrom(arena),
                (int) arena.widthPx(), (int) arena.heightPx());
        if (arena instanceof main.game.maze.mazeworld.generators.RealMaze realMaze) {
            realMaze.invalidateWalls();   // the walls have new materials
        }
        return arena;
    }

    int baseScoreForDifficulty(Difficulty difficulty) {
        // the difficulty's starting score, plus the score the levels before this one ended with
        return GameScoringConstants.baseScoreFor(difficulty) + levelProgress.carriedScore();
    }

    /** Collapsing walls fall as rubble and crush enemies next to them; follows the current level's world. */
    private void watchWallCollapses(GameMazeWorld world) {
        if (watchedWorld != null) {
            watchedWorld.removeWallCollapseListener(wallCollapseListener);
        }
        watchedWorld = world;
        if (world != null) {
            world.addWallCollapseListener(wallCollapseListener);
        }
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
        watchWallCollapses(GdxWallDamageSupport.worldFrom(maze));
        player = applied.player();
        playerStartX = player.x();
        playerStartY = player.y();
        viewport = applied.viewport();
        worldModel.lives().reset(PlayerLives.startingFor(selected));
        worldModel.resetArsenal(Math.max(0, startFlow.startState().runtimeModel().playerBombCount()));
        // the weapons of the previous level carry over (nothing on level 1)
        var inherited = levelProgress.inheritedArsenal();
        if (inherited != null) {
            worldModel.inheritArsenal(inherited);
        }
        sergeantSupport.reset();
        pulseCharge.cancel();
        worldModel.strikes().reset();
        worldModel.setBoosts(levelProgress.boosts());   // what was bought in the shop for this level
        if (worldModel.lives().remaining() == 1) {
            giveLastLifeGift();   // a level that is started on the last life gives the gift at once
        }
        guardTowerSupport.reset(levelProgress.current().guardTower());
        var builderWorld = GdxWallDamageSupport.worldFrom(maze);
        wallBuilderSupport.reset(levelProgress.current().wallBuilder(), maze.widthPx(), maze.heightPx(),
                builderWorld == null ? null : builderWorld.getLava());
        if (levelProgress.number() > 1) {
            flashStatus("Level " + levelProgress.number() + ": " + levelProgress.current().name(), 2.5f);
        }
        // the last level, whichever it is: the words slam onto the screen and a dark voice says them
        if (main.game.maze.game.status.FinalLevelIntro.applies(levelProgress)) {
            statusMessageBus.showTitleCard(main.game.maze.game.status.FinalLevelIntro.TEXT,
                    main.game.maze.game.status.FinalLevelIntro.SECONDS);
            AudioEngine.get().play(main.game.maze.game.status.FinalLevelIntro.VOICE);
        }
        worldModel.pickupField().clear();
        worldModel.shield().reset();
        worldModel.shield().activate(PlayerLives.RESPAWN_PROTECTION_SECONDS);   // spawn shield, like the pickup
        worldModel.killLedger().clear();
        worldModel.effects().clear();
        // mines, holes and portal ghosts of the level (after the old effects are gone, so their drawings stay)
        hazardSupport.start(worldModel, maze, levelProgress.current(), animatedEnemies);
        worldModel.healFeedback().reset();
        combatState.setShield(() -> worldModel.shield().isActive());
        pickupSpawnPoints = worldModel.pickupField().spawnPointsOf(GdxWallDamageSupport.worldFrom(maze));
        playerBombDamage = Math.max(1, startFlow.startState().runtimeModel().playerBombDamage());
        pendingPlayerBombs.clear();
        activePlayerFireBlasts.clear();
        worldModel.fireBlasts().clear();
        worldModel.enemyBursts().clear();
        worldModel.bombFuseGlows().clear();
        worldModel.wallRubble().clear();
        worldModel.remains().clear();
        worldModel.retiredShooters().clear();
        applyGameViewportBounds(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        var textures = applied.runtimeTextures();
        playerTexture = textures.playerTexture();
        playerDeathTexture = textures.playerDeathTexture();
        goalTexture = textures.goalTexture();
        wallTexture = textures.wallTexture();
        gameWorldView.setWallLookTextures(levelProgress.current().showWallMaterials() ? loadWallLookTextures() : null);
        // the lava of the level, drawn under the walls (the screen is y-up: the picture is flipped over the height)
        var lavaWorld = GdxWallDamageSupport.worldFrom(maze);
        gameWorldView.setLava(lavaWorld == null ? null : main.game.maze.game.lava.LavaEffects.of(lavaWorld.getLava(),
                levelProgress.current().lava(), maze.widthPx(), maze.heightPx(), maze.heightPx()));
        backgroundTexture = textures.backgroundTexture();
    }

    private int currentScore() {
        GameMode mode = session.mode();
        if (mode == GameMode.GAME_OVER || mode == GameMode.WON) {
            return GdxGameRuntimeSupport.endScreenScore(
                    scoringEngine, session, worldModel,
                    combatState.maxHitPoints(), combatState.currentHitPointsAsInt(),
                    mode == GameMode.WON);
        }
        return GdxGameRuntimeSupport.currentScore(scoringEngine, session, worldModel);
    }

    private ScoreBreakdown currentBreakdown() {
        GameMode mode = session.mode();
        if (mode == GameMode.GAME_OVER || mode == GameMode.WON) {
            return GdxGameRuntimeSupport.endScreenBreakdown(
                    scoringEngine, session, worldModel,
                    combatState.maxHitPoints(), combatState.currentHitPointsAsInt(),
                    mode == GameMode.WON);
        }
        return null;
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
        watchWallCollapses(null);
        worldModel.retiredShooters().clear();
        pendingPlayerBombs.clear();
        activePlayerFireBlasts.clear();
        worldModel.fireBlasts().clear();
        worldModel.enemyBursts().clear();
        worldModel.bombFuseGlows().clear();
        gameAudioCoordinator.stopAll();
        if (ownsAssetService) {
            assetService.dispose();
        }
        GdxGameLifecycleSupport.disposeGraphicsResources(batch, shapes, font);
    }

    /** The sprite of every wall material, for levels that draw each wall with its material. */
    private java.util.Map<main.game.maze.common.graphics.WallLook, Texture> loadWallLookTextures() {
        java.util.Map<main.game.maze.common.graphics.WallLook, Texture> textures =
                new java.util.EnumMap<>(main.game.maze.common.graphics.WallLook.class);
        for (var look : main.game.maze.common.graphics.WallLook.values()) {
            Texture texture = loadTexture(look.imagePath());
            if (texture != null) {
                textures.put(look, texture);
            }
        }
        return textures;
    }

    Texture loadTexture(String classpathPath) {
        return assetService.getTexture(classpathPath);
    }
}
