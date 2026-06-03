package main.game.maze.libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.config.MazeRuntimeConfig;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import main.game.maze.common.movement.ActivePathPoint;
import main.game.maze.game.audio.GameAudioDirector;
import main.game.maze.game.runtime.EnemyDirectorService;
import main.game.maze.game.score.FileHighScoreRepository;
import main.game.maze.game.score.HighScoreRepository;
import main.game.maze.game.score.PathHintBudget;
import main.game.maze.game.score.ScoringEngine;
import main.game.maze.game.status.StatusMessageBus;
import main.game.maze.common.graphics.config.PropertiesMazeVisualStyleLoader;
import main.game.maze.common.graphics.config.XmiMazeVisualStyleLoader;
import main.game.maze.common.movement.WorldView;
import main.game.maze.common.terminal.TerminalCommand;
import main.game.maze.common.terminal.TerminalCommandParser;
import main.game.maze.libgdx.movement.GdxWorldView;
import main.game.maze.common.constants.AudioResourceConstants;
import main.game.maze.dto.Score;
import main.game.maze.constants.DataFileConstants;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.HardDifficulty;
import main.game.maze.difficulties.NormalDifficulty;
import main.game.maze.libgdx.game.PlayerCombatStateService;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.libgdx.model.RuntimeVisualModel;
import main.game.maze.libgdx.model.RuntimeVisualModelLoader;
import main.game.maze.libgdx.view.GdxGameWorldView;
import main.game.maze.libgdx.view.GdxHudView;
import main.game.maze.libgdx.view.GdxOverlayView;
import main.game.maze.libgdx.view.GdxStartMenuView;
import main.game.maze.libgdx.view.layout.HudLayout;
import main.game.maze.libgdx.view.layout.MenuLayout;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.generators.RealMaze;
import main.game.maze.mazeworld.generators.SampleMaze;
import main.game.maze.mazeworld.generators.WallSegment;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.mazeworld.service.MazeNavigationGraphService;
import main.game.maze.service.DifficultyService;

/**
 * Minimal-but-real libGDX gameplay subset: a procedurally generated maze the
 * player walks through with arrow keys until reaching the green goal cell.
 * Heavy lifting (maze generation, collision resolution) lives in pure-Java
 * helpers under {@code main.game.maze.mazeworld.generators} so it can be tested
 * headlessly.
 */
public final class GdxGameScreen extends ApplicationAdapter {

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
    private static final float MOUSE_STEP_DISTANCE = 20f;
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
    private static final float BUTTON_PRESS_SECONDS = 0.14f;
    private static final int TERMINAL_INPUT_MAX_CHARS = 64;
    private static final int WIN_NAME_MAX_CHARS = 24;

    private enum Mode {
        START_MENU,
        PLAYING,
        HIGH_SCORES,
        WON,
        GAME_OVER
    }

    private final MazeArena providedMaze;
    private final float cellSize;
    private final float playerSize;
    private final boolean useRealMaze;
    private final DifficultyService difficultyService = new DifficultyService();
    private final MazeVisualStyleConfig visualStyle = loadVisualStyle();
    private final List<Difficulty> difficulties = new ArrayList<>();
    private int selectedDifficultyIndex;
    private int baseScore;
    private int moveCount;
    private boolean showHintInfo;
    private boolean showSpanningTreeInfo;
    private final GdxHudInteractionState hudInteractionState = new GdxHudInteractionState();
    private final GdxPlayerInputController playerInputController = new GdxPlayerInputController();
    private float showBehaviourTypeSeconds;
    private float showMovementTypeSeconds;
    private float showEnemyPathSeconds;
    private final GdxTerminalController terminalController = new GdxTerminalController(TERMINAL_INPUT_MAX_CHARS);
    private final StatusMessageBus statusMessageBus = new StatusMessageBus();
    private final StringBuilder winNameInput = new StringBuilder();
    private boolean winScoreSaved;
    private String winScoreStatus = "";
    private float enemyAnimationClock;
    private final GdxModeInputController modeInputController = new GdxModeInputController();
    private boolean startMenuDropdownOpen;
    private boolean pausedFromGame;
    private boolean highScoresReturnToStartMenu;
    private float winSaveButtonX;
    private float winSaveButtonY;
    private float winSaveButtonW;
    private float winSaveButtonH;
    private float winBackButtonX;
    private float winBackButtonY;
    private float winBackButtonW;
    private float winBackButtonH;
    private Mode mode = Mode.START_MENU;

    private MazeArena maze;
    private PlayerState player;
    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private BitmapFont font;
    private GlyphLayout glyphLayout;
    private OrthographicCamera camera;
    private OrthographicCamera hudCamera;
    private Viewport viewport;
    private RuntimeVisualModel runtimeModel;
    private final RuntimeVisualModelLoader runtimeModelLoader = new RuntimeVisualModelLoader();
    private final ScoringEngine scoringEngine = new ScoringEngine();
    private final EnemyDirectorService enemyDirectorService = new EnemyDirectorService();
    private final GameAudioDirector gameAudioDirector = new GameAudioDirector(AudioEngine::get);
    private final HighScoreRepository highScoreRepository =
            new FileHighScoreRepository(DataFileConstants.HighscoreFilePath);
    private final Map<String, Texture> texturesByPath = new HashMap<>();
    private final List<GdxEnemyRuntime> animatedEnemies = new ArrayList<>();
    private final PlayerCombatStateService combatState = new PlayerCombatStateService();
    private Texture playerTexture;
    private Texture playerDeathTexture;
    private Texture goalTexture;
    private Texture wallTexture;
    private Texture backgroundTexture;
    private Texture menuIconTexture;
    private Texture winBackgroundTexture;
    private Texture gameOverBackgroundTexture;
    private float activePlayerSpeed;
    private float activeGoalSize;
    private float activeGoalX;
    private float activeGoalY;
    private float currentHpRatio = 1f;
    private float playerTintRed = 1f;
    private float playerTintGreen = 1f;
    private float playerTintBlue = 1f;
    private boolean infectionWarningVisible;
    private boolean playedWinSound;
    private boolean playedGameOverSound;
    private boolean deathSequenceStarted;
    private float deathDisplayRemainingSeconds;
    private boolean loadingPending;
    private long loadingStartedAtNanos;
    private final List<Point2D> activePathPoints = new ArrayList<>();
    private final List<Score> highScoreRows = new ArrayList<>();
    private MenuLayout menuLayout = MenuLayout.zero();
    private final GdxGameWorldView gameWorldView = new GdxGameWorldView();
    private final GdxHudView hudView = new GdxHudView();
    private final GdxOverlayView overlayView = new GdxOverlayView();
    private final GdxStartMenuView startMenuView = new GdxStartMenuView();
    private final GdxStartMenuInputController startMenuInputController = new GdxStartMenuInputController();
    private HudLayout hudLayout = HudLayout.zero();
    private float pathPenaltyPoints;
    private PathHintBudget pathHintBudget = new PathHintBudget(PathHintBudget.EASY_SECONDS);
    private static final long START_MENU_LOADING_DELAY_NANOS = 1_000_000_000L;

    public GdxGameScreen() {
        this(null, DEFAULT_CELL_SIZE, DEFAULT_COLS, DEFAULT_ROWS, DEFAULT_PLAYER_SPEED, true);
    }

    public GdxGameScreen(MazeArena arena) {
        this(arena, DEFAULT_CELL_SIZE, DEFAULT_COLS, DEFAULT_ROWS, DEFAULT_PLAYER_SPEED, true);
    }

    public GdxGameScreen(MazeArena arena, MazeRuntimeConfig cfg) {
        this(arena, cfg.cellSize(), cfg.mazeCols(), cfg.mazeRows(), cfg.playerSpeed(), cfg.useRealMaze());
    }

    public GdxGameScreen(MazeArena arena, float cellSize, int cols, int rows, float playerSpeed, boolean useRealMaze) {
        this.providedMaze = arena;
        this.cellSize = cellSize;
        this.playerSize = cellSize * 0.5f;
        this.useRealMaze = useRealMaze;
    }

    @Override
    public void create() {
        GdxBackend.install();

        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        font = new BitmapFont();
        glyphLayout = new GlyphLayout();
        font.setColor(Color.WHITE);

        camera = new OrthographicCamera();
        hudCamera = new OrthographicCamera();
        menuIconTexture = loadTexture(visualStyle.menuIconImagePath());
        winBackgroundTexture = loadTexture("/main/game/maze/zombieGameOverBackground1.png");
        gameOverBackgroundTexture = loadTexture("/main/game/maze/zombieBackground.png");

        difficulties.clear();
        difficulties.addAll(difficultyService.list());
        Difficulty current = difficultyService.getCurrent();
        selectedDifficultyIndex = 0;
        if (current != null) {
            for (int i = 0; i < difficulties.size(); i++) {
                if (displayName(difficulties.get(i)).equals(displayName(current))) {
                    selectedDifficultyIndex = i;
                    break;
                }
            }
        }

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        switchToMenuMusic();

        installTerminalKeyboardProcessor();
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
                if (mode == Mode.WON && !winScoreSaved) {
                    return onWinScoreKeyTyped(character);
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
    record GameStripBounds(int x, int y, int width, int height) {}

    @Override
    public void render() {
        float dt = Math.min(Gdx.graphics.getDeltaTime(), 1f / 30f);
        update(dt);
        draw();
        if (loadingPending && TimeUtils.timeSinceNanos(loadingStartedAtNanos) >= START_MENU_LOADING_DELAY_NANOS) {
            loadingPending = false;
            startGameFromSelection();
        }
    }

    private void update(float dt) {
        hudInteractionState.tick(dt);

        String command = terminalController.consumePendingCommand();
        if (command != null) {
            executeTerminalCommand(command);
        }

        if (showBehaviourTypeSeconds > 0f) {
            showBehaviourTypeSeconds = Math.max(0f, showBehaviourTypeSeconds - dt);
        }
        if (showMovementTypeSeconds > 0f) {
            showMovementTypeSeconds = Math.max(0f, showMovementTypeSeconds - dt);
        }
        if (showEnemyPathSeconds > 0f) {
            showEnemyPathSeconds = Math.max(0f, showEnemyPathSeconds - dt);
        }

        statusMessageBus.tick(dt);

        if (handleStartMenuModeUpdate()) {
            return;
        }

        if (handleHighScoresModeUpdate()) {
            return;
        }

        if (handleWonModeUpdate()) {
            return;
        }

        if (handleGameOverModeUpdate()) {
            return;
        }

        if (!terminalController.isActive()
                && modeInputController.consumeEsc(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))) {
            switchToStartMenu(true);
            return;
        }

        if (!combatState.isDead()) {
            handleGameMouseInput();

            if (terminalController.isActive()) {
                handleTerminalTypingInput();
            } else {
                if (modeInputController.consumeT(Gdx.input.isKeyPressed(Input.Keys.T))) {
                    openTerminalPrompt();
                    return;
                }
                if (modeInputController.consumeH(Gdx.input.isKeyPressed(Input.Keys.H))) {
                    loadHighScores();
                    mode = Mode.HIGH_SCORES;
                }

                showHintInfo = Gdx.input.isKeyPressed(Input.Keys.P);
                applyPathPenalty(dt);
                updatePathHint();

                if (modeInputController.consumeO(Gdx.input.isKeyPressed(Input.Keys.O))) {
                    showSpanningTreeInfo = !showSpanningTreeInfo;
                }

                GdxPlayerInputController.MovementIntent movementIntent = playerInputController.resolveMovement(
                        Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A),
                        Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D),
                        Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S),
                        Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W));
                if (movementIntent.hasMovement()) {
                    player.attemptMove(
                            movementIntent.dx() * activePlayerSpeed * dt,
                            movementIntent.dy() * activePlayerSpeed * dt,
                            maze);
                    moveCount++;
                }
            }
        }

        enemyAnimationClock += dt;
        if (!animatedEnemies.isEmpty() && maze != null && player != null) {
            WorldView world = new GdxWorldView(maze, player);
            enemyDirectorService.advanceAll(animatedEnemies, world, dt);
        }

        var combatFrame = combatState.update(dt, player.x(), player.y(), player.halfSize(), currentEnemyContacts());
        currentHpRatio = combatFrame.hpRatio();
        playerTintRed = combatFrame.tintRed();
        playerTintGreen = combatFrame.tintGreen();
        playerTintBlue = combatFrame.tintBlue();
        infectionWarningVisible = combatFrame.infected();

        if (combatFrame.dead()) {
            if (!deathSequenceStarted) {
                deathSequenceStarted = true;
                deathDisplayRemainingSeconds = DEATH_DISPLAY_DELAY_SECONDS;
            }

            deathDisplayRemainingSeconds -= dt;
            if (deathDisplayRemainingSeconds <= 0f) {
                mode = Mode.GAME_OVER;
                if (!playedGameOverSound) {
                    playedGameOverSound = true;
                    gameAudioDirector.switchToGameOverMusic(AudioResourceConstants.GameOverSound);
                }
            }
        }

        updateCameraFollow();

        if (mode == Mode.PLAYING && !combatFrame.dead() && player.reached(activeGoalX, activeGoalY, activeGoalSize * 0.5f)) {
            mode = Mode.WON;
            winNameInput.setLength(0);
            winScoreSaved = false;
            winScoreStatus = "";
            if (!playedWinSound) {
                playedWinSound = true;
                gameAudioDirector.switchToWinMusic(visualStyle.winSoundPath());
            }
        }
    }

    private boolean handleStartMenuModeUpdate() {
        if (mode != Mode.START_MENU) {
            return false;
        }
        boolean escPressed = Gdx.input.isKeyPressed(Input.Keys.ESCAPE);
        if (pausedFromGame && modeInputController.consumeEsc(escPressed)) {
            pausedFromGame = false;
            mode = Mode.PLAYING;
            switchToInGameMusic();
            flashStatus("Resumed game");
            return true;
        }
        handleStartMenuInput();
        return true;
    }

    private boolean handleHighScoresModeUpdate() {
        if (mode != Mode.HIGH_SCORES) {
            return false;
        }
        if (modeInputController.consumeEsc(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))) {
            if (highScoresReturnToStartMenu) {
                highScoresReturnToStartMenu = false;
                switchToStartMenu(false);
            } else {
                mode = Mode.PLAYING;
            }
        }
        return true;
    }

    private boolean handleWonModeUpdate() {
        if (mode != Mode.WON) {
            return false;
        }
        if (!winScoreSaved) {
            handleWinScoreEntryInput();
        }
        handleWonMouseInput();
        if (modeInputController.consumeEsc(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))) {
            switchToStartMenu(false);
        }
        return true;
    }

    private boolean handleGameOverModeUpdate() {
        if (mode != Mode.GAME_OVER) {
            return false;
        }
        if (modeInputController.consumeEsc(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))) {
            switchToStartMenu(false);
        }
        return true;
    }

    private void draw() {
        ScreenUtils.clear(0.07f, 0.07f, 0.12f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (mode == Mode.START_MENU) {
            applyFullWindowGlViewport();
            drawStartMenu();
            return;
        }

        if (maze == null || player == null || runtimeModel == null || viewport == null) {
            return;
        }

        List<GdxGameWorldView.EnemyViewModel> enemyViewModels = new ArrayList<>(animatedEnemies.size());
        for (GdxEnemyRuntime enemy : animatedEnemies) {
            Texture enemyTexture = loadTexture(enemy.imagePath);
            String label = enemy.debugLabel(showBehaviourTypeSeconds > 0f, showMovementTypeSeconds > 0f);
            enemyViewModels.add(new GdxGameWorldView.EnemyViewModel(
                    enemyTexture,
                    enemy.x,
                    enemy.y,
                    enemy.size,
                    enemy.renderOpacity(),
                    enemy.infectious,
                    enemy.infectionStrength,
                    enemy.phase,
                    label,
                    enemyDisplayPath(enemy)));
        }

        gameWorldView.render(new GdxGameWorldView.RenderContext(
                batch,
                shapes,
                font,
                camera,
                viewport,
                maze,
                player,
                backgroundTexture,
                goalTexture,
                wallTexture,
                playerTexture,
                playerDeathTexture,
                activeGoalX,
                activeGoalY,
                activeGoalSize,
                WALL_THICKNESS,
                playerTintRed,
                playerTintGreen,
                playerTintBlue,
                combatState.isDead(),
                PLAYER_ALIVE_SCALE,
                PLAYER_DEAD_SCALE,
                HALF_RATIO,
                enemyAnimationClock,
                INFECTION_EDGE_LAYERS,
                enemyViewModels,
                activePathPoints,
                showEnemyPathSeconds,
                showSpanningTreeInfo));

        applyFullWindowGlViewport();
        drawHud();
        drawHighScoresOverlayIfNeeded();
        drawWinOverlayIfNeeded();
        drawGameOverOverlayIfNeeded();
        drawInfectionOverlayIfNeeded();
    }

    private void drawHighScoresOverlayIfNeeded() {
        if (mode != Mode.HIGH_SCORES) {
            return;
        }
        overlayView.renderHighScoresOverlay(
                new GdxOverlayView.RenderContext(batch, shapes, font, hudCamera),
                highScoreRows);
    }

    private void drawWinOverlayIfNeeded() {
        if (mode != Mode.WON) {
            return;
        }
        GdxOverlayView.WinButtons winButtons = overlayView.renderCenteredStateOverlay(
                new GdxOverlayView.CenteredOverlayContext(
                        batch,
                        shapes,
                        font,
                        glyphLayout,
                        hudCamera,
                        "YOU WIN",
                        "Type your name then click Save Score, or Back to Menu.",
                        winBackgroundTexture,
                        Color.GREEN,
                        true,
                        winScoreSaved,
                        winScoreStatus,
                        winNameInput.toString(),
                        currentScore()));
        winSaveButtonX = winButtons.saveX();
        winSaveButtonY = winButtons.saveY();
        winSaveButtonW = winButtons.saveW();
        winSaveButtonH = winButtons.saveH();
        winBackButtonX = winButtons.backX();
        winBackButtonY = winButtons.backY();
        winBackButtonW = winButtons.backW();
        winBackButtonH = winButtons.backH();
    }

    private void drawGameOverOverlayIfNeeded() {
        if (mode != Mode.GAME_OVER) {
            return;
        }
        overlayView.renderCenteredStateOverlay(
                new GdxOverlayView.CenteredOverlayContext(
                        batch,
                        shapes,
                        font,
                        glyphLayout,
                        hudCamera,
                        "GAME OVER",
                        "Press ESC to return to start menu",
                        gameOverBackgroundTexture,
                        Color.RED,
                        false,
                        false,
                        "",
                        "",
                        currentScore()));
    }

    private void drawInfectionOverlayIfNeeded() {
        if (mode != Mode.PLAYING || !infectionWarningVisible) {
            return;
        }
        overlayView.renderInfectionWarningSign(
                new GdxOverlayView.InfectionWarningContext(
                        batch,
                        shapes,
                        font,
                        glyphLayout,
                        hudCamera,
                        enemyAnimationClock,
                        INFECTION_PULSE_SPEED,
                        INFECTION_TRIANGLE_WIDTH,
                        INFECTION_TRIANGLE_HEIGHT,
                        INFECTION_GLOW_LAYERS,
                        INFECTION_WARNING_TEXT));
    }

    private void applyFullWindowGlViewport() {
        // viewport.apply() clamps the GL viewport to the gameplay strip; HUD must draw
        // over the full window so the bottom command bar sticks to the actual bottom
        // and the HP bar stays at the actual top.
        Gdx.gl.glViewport(0, 0,
                Math.max(1, Gdx.graphics.getBackBufferWidth()),
                Math.max(1, Gdx.graphics.getBackBufferHeight()));
    }

    private void drawStartMenu() {
        List<String> difficultyNames = new ArrayList<>(difficulties.size());
        for (Difficulty difficulty : difficulties) {
            difficultyNames.add(displayName(difficulty));
        }

        String selectedDifficultySummary = "";
        if (selectedDifficultyIndex >= 0 && selectedDifficultyIndex < difficulties.size()) {
            Difficulty selectedDifficulty = difficulties.get(selectedDifficultyIndex);
            selectedDifficultySummary = "Difficulty: " + displayName(selectedDifficulty)
                    + "  " + boardSizeLabel(selectedDifficulty);
        }

        String statusMessage = statusMessageBus.hasMessage() ? statusMessageBus.currentMessage() : "";

        menuLayout = startMenuView.render(new GdxStartMenuView.RenderContext(
                batch,
                shapes,
                font,
                glyphLayout,
                hudCamera,
                menuIconTexture,
                difficultyNames,
                selectedDifficultyIndex,
                startMenuDropdownOpen,
                pausedFromGame,
                loadingPending,
                selectedDifficultySummary,
                statusMessage));
    }

    private void drawHud() {
        GdxHudView.HudMessageMode messageMode = switch (mode) {
            case WON -> GdxHudView.HudMessageMode.WON;
            case GAME_OVER -> GdxHudView.HudMessageMode.GAME_OVER;
            default -> GdxHudView.HudMessageMode.STATUS;
        };
        String statusMessage = statusMessageBus.hasMessage() ? statusMessageBus.currentMessage() : "";

        hudLayout = hudView.render(new GdxHudView.RenderContext(
                batch,
                shapes,
                font,
                hudCamera,
                TOP_MARGIN,
                SCORE_PANEL_WIDTH,
                SCORE_PANEL_HEIGHT,
                BOTTOM_BAR_HEIGHT,
                HP_BAR_HEIGHT,
                hpBarBottomY(hudCamera.viewportHeight),
                bottomRowY(),
                bottomRowHeight(),
                currentHpRatio,
                hudInteractionState.commandPressOffsetY(),
                hudInteractionState.terminalPressOffsetY(),
                hudInteractionState.commandButtonPressedSeconds(),
                hudInteractionState.terminalButtonPressedSeconds(),
                showHintInfo,
                pathHintBudget.exhausted(),
                pathHintRemainingSeconds(),
                showSpanningTreeInfo,
                currentScore(),
                messageMode,
                statusMessage,
                terminalController.isActive(),
                terminalController.bufferText(),
                hudInteractionState.commandsOverlayVisible()));
    }

    private void handleStartMenuInput() {
        if (loadingPending) {
            return;
        }
        handleStartMenuMouseInput();
        var keyboardResult = startMenuInputController.handleKeyboard(
                Gdx.input.isKeyPressed(Input.Keys.UP),
                Gdx.input.isKeyPressed(Input.Keys.DOWN),
                Gdx.input.isKeyPressed(Input.Keys.ENTER),
                selectedDifficultyIndex,
                difficulties.size());
        selectedDifficultyIndex = keyboardResult.selectedDifficultyIndex();
        if (keyboardResult.playSelectSound()) {
            AudioEngine.get().play(visualStyle.menuSelectSoundPath());
        }
        if (keyboardResult.startRequested()) {
            beginStartLoading();
        }
    }

    private void handleStartMenuMouseInput() {
        if (!Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
            return;
        }
        float mx = Gdx.input.getX();
        float my = hudCamera.viewportHeight - Gdx.input.getY();

        var mouseResult = startMenuInputController.handleLeftClick(
                mx,
                my,
                new GdxStartMenuInputController.MenuLayoutValues(
                    menuLayout.comboX(),
                    menuLayout.comboY(),
                    menuLayout.comboW(),
                    menuLayout.comboH(),
                    menuLayout.buttonX(),
                    menuLayout.buttonY(),
                    menuLayout.buttonW(),
                    menuLayout.buttonH(),
                    menuLayout.highScoresButtonX(),
                    menuLayout.highScoresButtonY(),
                    menuLayout.highScoresButtonW(),
                    menuLayout.highScoresButtonH()),
                difficulties.size(),
                selectedDifficultyIndex,
                startMenuDropdownOpen);
        selectedDifficultyIndex = mouseResult.selectedDifficultyIndex();
        startMenuDropdownOpen = mouseResult.dropdownOpen();
        if (mouseResult.playSelectSound()) {
            AudioEngine.get().play(visualStyle.menuSelectSoundPath());
        }
        if (mouseResult.startRequested()) {
            beginStartLoading();
        }
        if (mouseResult.highScoresRequested()) {
            loadHighScores();
            highScoresReturnToStartMenu = true;
            mode = Mode.HIGH_SCORES;
        }
    }

    private void beginStartLoading() {
        loadingPending = true;
        loadingStartedAtNanos = TimeUtils.nanoTime();
    }

    private void handleGameMouseInput() {
        if (!Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
            return;
        }
        float mx = Gdx.input.getX();
        float my = hudCamera.viewportHeight - Gdx.input.getY();

        if (contains(mx, my, hudLayout.commandButtonX(), hudLayout.commandButtonY(), hudLayout.commandButtonW(), hudLayout.commandButtonH())) {
            hudInteractionState.pressCommandsButton(BUTTON_PRESS_SECONDS);
            return;
        }

        if (contains(mx, my, hudLayout.terminalButtonX(), hudLayout.terminalButtonY(), hudLayout.terminalButtonW(), hudLayout.terminalButtonH())) {
            hudInteractionState.pressTerminalButton(BUTTON_PRESS_SECONDS);
            toggleTerminalPrompt();
            return;
        }

        if (hudInteractionState.commandsOverlayVisible()) {
            hudInteractionState.hideCommandsOverlay();
            return;
        }

        if (player != null && viewport != null) {
            Vector3 worldClick = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0f);
            viewport.unproject(worldClick);
            float vx = worldClick.x - player.x();
            float vy = worldClick.y - player.y();
            float len = (float) Math.sqrt(vx * vx + vy * vy);
            if (len > 0.001f) {
                float nx = vx / len;
                float ny = vy / len;
                player.attemptMove(nx * MOUSE_STEP_DISTANCE, ny * MOUSE_STEP_DISTANCE, maze);
                moveCount++;
                updateCameraFollow();
            }
        }

        flashStatus(String.format(Locale.ROOT, "Mouse: %.0f, %.0f", mx, my));
    }

    private void openTerminalPrompt() {
        terminalController.open();
        flashStatus("Terminal opened. Type command and press Enter");
    }

    private void closeTerminalPrompt() {
        terminalController.close();
        flashStatus("Terminal closed");
    }

    private void toggleTerminalPrompt() {
        if (terminalController.isActive()) {
            closeTerminalPrompt();
            return;
        }
        openTerminalPrompt();
    }

    private void handleTerminalTypingInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            closeTerminalPrompt();
            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            terminalController.submit();
            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
            terminalController.backspace();
            return;
        }
        // All other character input is fed by InputAdapter#keyTyped which
        // respects the OS keyboard layout (æ, ø, å, etc.).
    }

    private void executeTerminalCommand(String raw) {
        TerminalCommand command = parseTerminalCommand(raw);
        if (command == TerminalCommand.EMPTY) {
            flashStatus("No command entered");
            return;
        }
        if (command == TerminalCommand.HELP) {
            flashStatus("Commands: " + TerminalCommandParser.HELP_TEXT, 20f);
            return;
        }
        if (command == TerminalCommand.SHOW_BEHAVIOUR_TYPE) {
            showBehaviourTypeSeconds = ENEMY_LABEL_SECONDS;
            flashStatus("Showing behaviour type above enemies");
            return;
        }
        if (command == TerminalCommand.SHOW_MOVEMENT_TYPE) {
            showMovementTypeSeconds = ENEMY_LABEL_SECONDS;
            flashStatus("Showing movement type above enemies");
            return;
        }
        if (command == TerminalCommand.SHOW_ENEMY_PATH) {
            showEnemyPathSeconds = ENEMY_PATH_OVERLAY_SECONDS;
            flashStatus("Showing enemy paths for 10 seconds");
            return;
        }
        flashStatus("Unknown command. Use /h");
    }

    private boolean onWinScoreKeyTyped(char character) {
        if (character < 32 || character == 127) {
            return false;
        }
        if (winNameInput.length() >= WIN_NAME_MAX_CHARS) {
            return true;
        }
        if (Character.isLetterOrDigit(character) || character == ' ' || character == '_' || character == '-') {
            winNameInput.append(character);
            return true;
        }
        return false;
    }

    private void handleWinScoreEntryInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) && winNameInput.length() > 0) {
            winNameInput.deleteCharAt(winNameInput.length() - 1);
            return;
        }
        if (!Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            return;
        }
        String playerName = winNameInput.toString().trim();
        if (playerName.isEmpty()) {
            winScoreStatus = "Enter a name before saving.";
            return;
        }
        if (writeHighScore(playerName, currentScore())) {
            winScoreSaved = true;
            winScoreStatus = "Saved score for " + playerName + ".";
            loadHighScores();
            highScoresReturnToStartMenu = true;
            mode = Mode.HIGH_SCORES;
        } else {
            winScoreStatus = "Could not save score.";
        }
    }

    private void handleWonMouseInput() {
        if (!Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
            return;
        }
        float mx = Gdx.input.getX();
        float my = hudCamera.viewportHeight - Gdx.input.getY();
        if (!winScoreSaved && contains(mx, my, winSaveButtonX, winSaveButtonY, winSaveButtonW, winSaveButtonH)) {
            String playerName = winNameInput.toString().trim();
            if (playerName.isEmpty()) {
                winScoreStatus = "Type a name first.";
                return;
            }
            if (writeHighScore(playerName, currentScore())) {
                winScoreSaved = true;
                winScoreStatus = "Saved score for " + playerName + ".";
                loadHighScores();
                highScoresReturnToStartMenu = true;
                mode = Mode.HIGH_SCORES;
            } else {
                winScoreStatus = "Could not save score.";
            }
            return;
        }
        if (contains(mx, my, winBackButtonX, winBackButtonY, winBackButtonW, winBackButtonH)) {
            switchToStartMenu(false);
        }
    }

    private boolean writeHighScore(String playerName, int score) {
        return highScoreRepository.upsertScore(playerName, score);
    }

    static String terminalHelpText() {
        return GdxTerminalController.helpText();
    }

    static TerminalCommand parseTerminalCommand(String raw) {
        return TerminalCommandParser.parse(raw);
    }

    private void updatePathHint() {
        activePathPoints.clear();
        if (!showHintInfo || !(maze instanceof RealMaze realMaze) || player == null) {
            return;
        }
        if (realMaze.navigationGraph() == null) {
            return;
        }

        Point2D start = new Point2D(player.x(), maze.heightPx() - player.y());
        Point2D goal = new Point2D(activeGoalX, maze.heightPx() - activeGoalY);
        var path = MazeNavigationGraphService.findPath(realMaze.navigationGraph(), start, goal);
        if (path != null && path.size() > 1) {
            activePathPoints.addAll(path);
        }
    }

    /**
     * Returns the live runtime path the enemy is currently following,
     * taken directly from the movement service snapshot.
     * For AGGRESSIVE enemies this is the path held by {@link AdaptiveAggressiveMovementService};
     * for PATROL enemies it is the path held by {@link PatrolMovementService}.
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
        highScoreRows.addAll(highScoreRepository.loadScores());
    }

    private static boolean contains(float px, float py, float x, float y, float w, float h) {
        return px >= x && px <= x + w && py >= y && py <= y + h;
    }

    private void startGameFromSelection() {
        Difficulty selected = difficulties.isEmpty() ? null : difficulties.get(selectedDifficultyIndex);
        if (selected != null) {
            difficultyService.setCurrent(selected);
        }
        resizeWindowForDifficulty(selected);

        maze = providedMaze != null ? providedMaze : buildArenaForDifficulty(selected);
        runtimeModel = runtimeModelLoader.load(maze, selected);
        float baseSpeed = runtimeModel.playerSpeed() > 0f
                ? runtimeModel.playerSpeed()
                : StageConstants.PlayerCharacterSpeed;
        activePlayerSpeed = Math.max(1f, toJavaFxLikeSpeed(baseSpeed));
        activeGoalSize = runtimeModel.goalSize() > 0f ? runtimeModel.goalSize() : GOAL_SIZE;
        player = PlayerState.spawnAwayFromWalls(
            maze.startX(),
            maze.startY(),
            runtimeModel.playerSize() > 0f ? runtimeModel.playerSize() : playerSize,
            maze);
        baseScore = baseScoreForDifficulty(selected);
        moveCount = 0;
        showHintInfo = false;
        showSpanningTreeInfo = false;
        hudInteractionState.reset();
        showBehaviourTypeSeconds = 0f;
        showMovementTypeSeconds = 0f;
        showEnemyPathSeconds = 0f;
        terminalController.reset();
        modeInputController.reset();
        startMenuDropdownOpen = false;
        pausedFromGame = false;
        playedWinSound = false;
        playedGameOverSound = false;
        deathSequenceStarted = false;
        deathDisplayRemainingSeconds = 0f;
        animatedEnemies.clear();
        enemyDirectorService.reset();
        pathPenaltyPoints = 0f;
        pathHintBudget = PathHintBudget.forDifficulty(selected);
        winNameInput.setLength(0);
        winScoreSaved = false;
        winScoreStatus = "";
        currentHpRatio = 1f;
        playerTintRed = 1f;
        playerTintGreen = 1f;
        playerTintBlue = 1f;
        combatState.reset(runtimeModel.playerMaxHitPoints());
        combatState.setMaze(maze);

        int idx = 0;
        WorldView spawnWorld = new GdxWorldView(maze, player);
        for (EnemySpawn enemy : runtimeModel.enemies()) {
                animatedEnemies.add(GdxEnemyRuntime.fromSpawn(
                    enemy,
                    idx++,
                    spawnWorld,
                    JAVA_FX_TICK_RATE,
                    MAX_ENEMY_TICKS_PER_FRAME));
        }

        if (viewport == null) {
            viewport = new ScreenViewport(camera);
        }
        applyGameViewportBounds(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        playerTexture = loadTexture(runtimeModel.playerImagePath());
        playerDeathTexture = loadTexture(runtimeModel.playerDeathImagePath());
        goalTexture = loadTexture(runtimeModel.goalImagePath());
        wallTexture = loadTexture(runtimeModel.wallImagePath());
        backgroundTexture = loadTexture(runtimeModel.backgroundImagePath());
        recenterGoalLikeJavaFx();
        mode = Mode.PLAYING;

        switchToInGameMusic();
        flashStatus("Started " + (selected != null ? displayName(selected) : "Default") + " difficulty");
    }

    static float toJavaFxLikeSpeed(float playerSpeed) {
        return playerSpeed * JAVA_FX_TICK_RATE;
    }

    private void recenterGoalLikeJavaFx() {
        activeGoalX = maze.widthPx() * 0.5f;
        activeGoalY = maze.heightPx() * 0.5f;
        nudgeGoalOffWalls(activeGoalSize, activeGoalSize);
    }

    private void nudgeGoalOffWalls(float goalW, float goalH) {
        if (maze == null || maze.walls() == null || maze.walls().isEmpty()) {
            return;
        }
        final float halfW = goalW * 0.5f;
        final float halfH = goalH * 0.5f;
        main.game.maze.mazeworld.generators.SpriteWallNudger.MovableAabb rect =
                new main.game.maze.mazeworld.generators.SpriteWallNudger.MovableAabb() {
            @Override public float minX() { return activeGoalX - halfW; }
            @Override public float minY() { return activeGoalY - halfH; }
            @Override public float maxX() { return activeGoalX + halfW; }
            @Override public float maxY() { return activeGoalY + halfH; }
            @Override public void offset(float dx, float dy) { activeGoalX += dx; activeGoalY += dy; }
        };
        main.game.maze.mazeworld.generators.SpriteWallNudger.nudgeOffWalls(rect, maze.walls(), 400);
    }

    private void resizeWindowForDifficulty(Difficulty selected) {
        if (Gdx.graphics == null) {
            return;
        }
        int targetW = boardWidth(selected);
        int targetH = boardHeight(selected);
        var display = Gdx.graphics.getDisplayMode();
        int maxW = display != null ? display.width : targetW;
        int maxH = display != null ? display.height : targetH;
        int finalWidth = Math.min(maxW, targetW);
        int finalHeight = Math.min(maxH, targetH);
        if (Gdx.graphics.getWidth() == finalWidth && Gdx.graphics.getHeight() == finalHeight) {
            return;
        }
        Gdx.graphics.setWindowedMode(finalWidth, finalHeight);
    }

    private MazeArena buildArenaForDifficulty(Difficulty selected) {
        int width = boardWidth(selected);
        int height = boardHeight(selected);
        if (cfgUsesRealMaze()) {
            return RealMaze.fresh(width, height);
        }
        int sampleCols = Math.max(8, Math.round(width / cellSize));
        int sampleRows = Math.max(6, Math.round(height / cellSize));
        return new SampleMaze(sampleCols, sampleRows, cellSize, SEED);
    }

    private boolean cfgUsesRealMaze() {
        return useRealMaze;
    }

    private int boardWidth(Difficulty selected) {
        if (selected instanceof HardDifficulty) {
            return StageConstants.BoardMaxXLarge;
        }
        if (selected instanceof NormalDifficulty) {
            return StageConstants.BoardMaxXMedium;
        }
        return StageConstants.BoardMaxX;
    }

    private int boardHeight(Difficulty selected) {
        if (selected instanceof HardDifficulty) {
            return StageConstants.BoardMaxYLarge;
        }
        if (selected instanceof NormalDifficulty) {
            return StageConstants.BoardMaxYMedium;
        }
        return StageConstants.BoardMaxY;
    }

    private String boardSizeLabel(Difficulty selected) {
        return boardWidth(selected) + "x" + boardHeight(selected);
    }

    private int baseScoreForDifficulty(Difficulty selected) {
        return main.game.maze.common.scoring.GameScoringConstants.baseScoreFor(selected);
    }

    private String displayName(Difficulty difficulty) {
        if (difficulty == null) {
            return "Easy";
        }
        String modelName = difficulty.eClass().getName();
        if (modelName.endsWith("Difficulty")) {
            return modelName.substring(0, modelName.length() - "Difficulty".length());
        }
        return modelName.toLowerCase(Locale.ROOT);
    }

    private void updateCameraFollow() {
        if (viewport == null || maze == null || player == null) {
            return;
        }
        float worldW = viewport.getWorldWidth();
        float worldH = viewport.getWorldHeight();
        float halfW = worldW * 0.5f;
        float halfH = worldH * 0.5f;

        float camX;
        float camY;
        if (maze.widthPx() <= worldW + 0.001f) {
            camX = maze.widthPx() * 0.5f;
        } else {
            camX = clamp(player.x(), halfW, maze.widthPx() - halfW);
        }
        if (maze.heightPx() <= worldH + 0.001f) {
            camY = halfH;
        } else {
            camY = clamp(player.y(), halfH, maze.heightPx() - halfH);
        }
        camera.position.set(camX, camY, 0f);
    }

    private static float clamp(float value, float min, float max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    private void switchToStartMenu(boolean fromGame) {
        pausedFromGame = fromGame;
        startMenuDropdownOpen = false;
        mode = Mode.START_MENU;
        switchToMenuMusic();
        flashStatus(fromGame ? "Paused in start menu. Press ESC to resume." : "Returned to start menu");
    }

    private void switchToInGameMusic() {
        gameAudioDirector.switchToInGameMusic(visualStyle.inGameMusicPath());
    }

    private void switchToMenuMusic() {
        gameAudioDirector.switchToMenuMusic(resolveMenuMusicPath());
    }

    private String resolveMenuMusicPath() {
        String primary = visualStyle.menuMusicPath();
        String alternate = "/main/game/maze/menumusic1.wav";
        return ThreadLocalRandom.current().nextBoolean() ? primary : alternate;
    }

    private void applyPathPenalty(float dt) {
        if (mode != Mode.PLAYING) {
            return;
        }
        // If budget is already exhausted, keep hint off and notify once.
        if (pathHintBudget.exhausted()) {
            if (showHintInfo) {
                showHintInfo = false;
                flashStatus("Path hint energy already spent!", 3f);
            }
            return;
        }
        if (!showHintInfo) {
            return;
        }
        float consumed = pathHintBudget.consume(dt);
        if (consumed <= 0f) {
            showHintInfo = false;
            flashStatus("Path hint energy already spent!", 3f);
            return;
        }
        pathPenaltyPoints += consumed * ROUTE_HINT_PENALTY_PER_SEC;
        if (pathHintBudget.exhausted()) {
            showHintInfo = false;
            flashStatus("Path hint energy already spent!", 3f);
        }
    }

    private float pathHintRemainingSeconds() {
        return pathHintBudget.remainingSeconds();
    }

    private List<EnemySpawn> currentEnemyContacts() {
        if (animatedEnemies.isEmpty()) {
            return List.of();
        }
        List<EnemySpawn> contacts = new ArrayList<>(animatedEnemies.size());
        for (GdxEnemyRuntime enemy : animatedEnemies) {
            contacts.add(enemy.contactSnapshot());
        }
        return contacts;
    }

    private int currentScore() {
        return scoringEngine.gameplayScore(baseScore, moveCount, pathPenaltyPoints);
    }

    private MazeVisualStyleConfig loadVisualStyle() {
        try {
            return new XmiMazeVisualStyleLoader().load();
        } catch (RuntimeException ex) {
            try {
                return new PropertiesMazeVisualStyleLoader().load();
            } catch (RuntimeException ignored) {
                return MazeVisualStyleConfig.DEFAULT;
            }
        }
    }

    private void flashStatus(String text) {
        statusMessageBus.publish(text);
    }

    private void flashStatus(String text, float durationSeconds) {
        statusMessageBus.publish(text, durationSeconds);
    }

    @Override
    public void dispose() {
        gameAudioDirector.stopAll();
        for (Texture texture : texturesByPath.values()) {
            texture.dispose();
        }
        texturesByPath.clear();
        if (batch != null) batch.dispose();
        if (shapes != null) shapes.dispose();
        if (font != null) font.dispose();
    }

    private Texture loadTexture(String classpathPath) {
        if (classpathPath == null || classpathPath.isBlank() || Gdx.files == null) {
            return null;
        }
        return texturesByPath.computeIfAbsent(classpathPath, path -> {
            String relative = path.startsWith("/") ? path.substring(1) : path;
            var file = Gdx.files.internal(relative);
            if (!file.exists()) {
                return null;
            }
            return new Texture(file);
        });
    }

}
