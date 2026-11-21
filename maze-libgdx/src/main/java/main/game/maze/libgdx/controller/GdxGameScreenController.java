package main.game.maze.libgdx.controller;

import main.game.maze.libgdx.helper.GdxWeaponSupport;
import main.game.maze.game.score.PlayerIds;
import main.game.maze.game.session.PlayerLives;
import main.game.maze.game.items.Weapon;
import main.game.maze.game.items.PickupKind;
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
    private final LevelProgress levelProgress = new LevelProgress(LevelCatalogLoader.loadDefault());
    private final GdxSergeantSupport sergeantSupport = new GdxSergeantSupport();
    private final GdxHazardSupport hazardSupport = new GdxHazardSupport();
    private final GdxGuardTowerSupport guardTowerSupport = new GdxGuardTowerSupport();
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
        winOverlayController.setRestartLevelAction(this::startCurrentLevel);
        modeRouter
            .register(() -> overlayModeCoordinator.updateHighScores(() -> requestReturnToMenu(false)))
            .register(() -> overlayModeCoordinator.updateWon(
                hudCamera,
                this::currentScore,
                () -> requestReturnToMenu(false),
                this::openHighScoresFromWin,
                this::startNextLevel))
            .register(() -> overlayModeCoordinator.updateGameOver(
                hudCamera,
                this::startCurrentLevel,   // the level that was lost, not level 1
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
        Weapon used = worldModel.arsenal().use();
        if (used == null) {
            AudioEngine.get().play(WEAPON_SWITCH_SOUND);
            flashStatus("Out of ammo - pick up weapons in the maze", 1.2f);
            return;
        }
        PickupCatalog catalog = worldModel.pickupField().catalog();
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
        }
    }

    /** Tab: switch to the next weapon in stock. */
    void switchWeapon() {
        Weapon before = worldModel.arsenal().selected();
        Weapon now = worldModel.arsenal().cycle();
        if (now != before) {
            AudioEngine.get().play(WEAPON_SWITCH_SOUND);
        }
        flashStatus("Weapon: " + ArsenalHud.text(worldModel.arsenal(), null), 1.0f);
    }

    void advanceEnemies(float dt) {
        GdxGameCombatAndEnemyFlowSupport.advanceEnemies(animatedEnemies, maze, player, enemyDirectorService, dt);
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
        worldModel.arsenal().tickBombRegen(dt);
        GdxWeaponSupport.updatePickups(worldModel, maze, player, combatState, pickupSpawnPoints, dt);
        sergeantSupport.tick(worldModel, animatedEnemies, maze, player, combatState, levelProgress.current().sergeant(), dt);
        tickHazards(dt);
        if (player != null) {
            guardTowerSupport.tick(guardTowerHost, animatedEnemies, player.x(), player.y(), dt);
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
        detonateBlast(x, y, playerBombDamage, PLAYER_FLAME_RANGE, true, "Bomb");
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
        return true;
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
        winOverlayController.setNextLevelAvailable(levelProgress.hasNext());
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
        if (arena instanceof main.game.maze.mazeworld.generators.RealMaze realMaze) {
            realMaze.invalidateWalls();   // the walls have new materials
        }
        return arena;
    }

    int baseScoreForDifficulty(Difficulty difficulty) {
        return GameScoringConstants.baseScoreFor(difficulty);
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
        guardTowerSupport.reset(levelProgress.current().guardTower());
        if (levelProgress.number() > 1) {
            flashStatus("Level " + levelProgress.number() + ": " + levelProgress.current().name(), 2.5f);
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
