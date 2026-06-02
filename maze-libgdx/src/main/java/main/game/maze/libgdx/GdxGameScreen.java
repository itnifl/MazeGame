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
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.config.MazeRuntimeConfig;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import main.game.maze.common.movement.AntiLoopWanderMovementService;
import main.game.maze.common.movement.AdaptiveAggressiveMovementService;
import main.game.maze.common.movement.ActivePathPoint;
import main.game.maze.common.graphics.config.PropertiesMazeVisualStyleLoader;
import main.game.maze.common.graphics.config.XmiMazeVisualStyleLoader;
import main.game.maze.common.movement.GhostPhasingMovementService;
import main.game.maze.common.movement.PatrolMovementService;
import main.game.maze.common.movement.WorldView;
import main.game.maze.common.terminal.TerminalCommand;
import main.game.maze.common.terminal.TerminalCommandParser;
import main.game.maze.libgdx.movement.GdxWorldView;
import main.game.maze.constants.AudioChannelConstants;
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
    private static final float PATH_HINT_BUDGET_EASY_SEC = 45f;
    private static final float PATH_HINT_BUDGET_NORMAL_SEC = 25f;
    private static final float PATH_HINT_BUDGET_HARD_SEC = 15f;
    private static final float PLAYER_ALIVE_SCALE = 1f;
    private static final float PLAYER_DEAD_SCALE = 1.8f;
    private static final float HALF_RATIO = 0.5f;
    private static final String INFECTION_WARNING_TEXT = "Infected!";
    private static final float INFECTION_TRIANGLE_WIDTH = 120f;
    private static final float INFECTION_TRIANGLE_HEIGHT = 106f;
    private static final float INFECTION_PULSE_BASE = 0.5f;
    private static final float INFECTION_PULSE_AMPLITUDE = 0.5f;
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
    private float showBehaviourTypeSeconds;
    private float showMovementTypeSeconds;
    private float showEnemyPathSeconds;
    private final GdxTerminalController terminalController = new GdxTerminalController(TERMINAL_INPUT_MAX_CHARS);
    private String statusMessage = "";
    private float statusMessageTimer;
    private final StringBuilder winNameInput = new StringBuilder();
    private boolean winScoreSaved;
    private String winScoreStatus = "";
    private float enemyAnimationClock;
    private boolean escLatch;
    private boolean hLatch;
    private boolean oLatch;
    private boolean tLatch;
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
    private final Map<String, Texture> texturesByPath = new HashMap<>();
    private final List<GdxEnemyRuntime> animatedEnemies = new ArrayList<>();
    private final PlayerCombatStateService combatState = new PlayerCombatStateService();
    private final AntiLoopWanderMovementService antiLoopWanderMovementService =
            new AntiLoopWanderMovementService();
    private final AdaptiveAggressiveMovementService adaptiveAggressiveMovementService =
            new AdaptiveAggressiveMovementService();
    private final PatrolMovementService patrolMovementService =
            new PatrolMovementService();
    private final GhostPhasingMovementService ghostPhasingMovementService =
            new GhostPhasingMovementService();
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
    private final MenuLayout menuLayout = new MenuLayout();
    private final GdxStartMenuInputController startMenuInputController = new GdxStartMenuInputController();
    private final HudLayout hudLayout = new HudLayout();
    private float pathPenaltyPoints;
    /** Total seconds of P-key budget consumed this game. */
    private float pathHintTotalUsedSeconds;
    /** True once the P-key budget is exhausted for this game. */
    private boolean pathHintBudgetExhausted;
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
        AudioEngine.get().playLoop(visualStyle.menuMusicPath(), AudioChannelConstants.MENU_MUSIC);

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

        if (statusMessageTimer > 0f) {
            statusMessageTimer -= dt;
            if (statusMessageTimer <= 0f) {
                statusMessage = "";
            }
        }

        if (mode == Mode.START_MENU) {
            if (pausedFromGame && Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !escLatch) {
                escLatch = true;
                pausedFromGame = false;
                mode = Mode.PLAYING;
                switchToInGameMusic();
                flashStatus("Resumed game");
                return;
            }
            if (!Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                escLatch = false;
            }
            handleStartMenuInput();
            return;
        }

        if (mode == Mode.HIGH_SCORES) {
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !escLatch) {
                escLatch = true;
                if (highScoresReturnToStartMenu) {
                    highScoresReturnToStartMenu = false;
                    switchToStartMenu(false);
                } else {
                    mode = Mode.PLAYING;
                }
            }
            if (!Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                escLatch = false;
            }
            return;
        }

        if (mode == Mode.WON) {
            if (!winScoreSaved) {
                handleWinScoreEntryInput();
            }
            handleWonMouseInput();
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !escLatch) {
                escLatch = true;
                switchToStartMenu(false);
            }
            if (!Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                escLatch = false;
            }
            return;
        }

        if (mode == Mode.GAME_OVER) {
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !escLatch) {
                escLatch = true;
                switchToStartMenu(false);
            }
            if (!Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                escLatch = false;
            }
            return;
        }

        if (!terminalController.isActive() && Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !escLatch) {
            escLatch = true;
            switchToStartMenu(true);
            return;
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            escLatch = false;
        }

        if (!combatState.isDead()) {
            handleGameMouseInput();

            if (terminalController.isActive()) {
                handleTerminalTypingInput();
            } else {
                if (Gdx.input.isKeyPressed(Input.Keys.T) && !tLatch) {
                    tLatch = true;
                    openTerminalPrompt();
                    return;
                }
                if (!Gdx.input.isKeyPressed(Input.Keys.T)) {
                    tLatch = false;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.H) && !hLatch) {
                    hLatch = true;
                    loadHighScores();
                    mode = Mode.HIGH_SCORES;
                }
                if (!Gdx.input.isKeyPressed(Input.Keys.H)) {
                    hLatch = false;
                }

                showHintInfo = Gdx.input.isKeyPressed(Input.Keys.P);
                applyPathPenalty(dt);
                updatePathHint();

                if (Gdx.input.isKeyPressed(Input.Keys.O) && !oLatch) {
                    oLatch = true;
                    showSpanningTreeInfo = !showSpanningTreeInfo;
                }
                if (!Gdx.input.isKeyPressed(Input.Keys.O)) {
                    oLatch = false;
                }

                float dx = 0f, dy = 0f;
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) dx -= 1f;
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) dx += 1f;
                if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) dy -= 1f;
                if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) dy += 1f;
                if (dx != 0f && dy != 0f) {
                    float inv = (float) (1.0 / Math.sqrt(2.0));
                    dx *= inv; dy *= inv;
                }
                if (dx != 0f || dy != 0f) {
                    player.attemptMove(dx * activePlayerSpeed * dt, dy * activePlayerSpeed * dt, maze);
                    moveCount++;
                }
            }
        }

        enemyAnimationClock += dt;
        if (!animatedEnemies.isEmpty() && maze != null && player != null) {
            WorldView world = new GdxWorldView(maze, player);
            for (GdxEnemyRuntime enemy : animatedEnemies) {
                enemy.advance(
                    world,
                    antiLoopWanderMovementService,
                    patrolMovementService,
                    adaptiveAggressiveMovementService,
                    ghostPhasingMovementService,
                    dt);
            }
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
                    AudioEngine.get().stopChannel(AudioChannelConstants.IN_GAME_MUSIC);
                    AudioEngine.get().playLoop(AudioResourceConstants.GameOverSound, AudioChannelConstants.GAME_OVER_MUSIC);
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
                AudioEngine.get().stopChannel(AudioChannelConstants.IN_GAME_MUSIC);
                AudioEngine.get().playLoop(visualStyle.winSoundPath(), AudioChannelConstants.WIN_MUSIC);
                AudioEngine.get().play(AudioResourceConstants.WinGameSoundComment);
            }
        }
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

        viewport.apply();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        if (backgroundTexture != null) {
            float tileW = Math.max(1f, backgroundTexture.getWidth());
            float tileH = Math.max(1f, backgroundTexture.getHeight());
            float viewMinX = camera.position.x - viewport.getWorldWidth() * 0.5f;
            float viewMinY = camera.position.y - viewport.getWorldHeight() * 0.5f;
            float viewMaxX = camera.position.x + viewport.getWorldWidth() * 0.5f;
            float viewMaxY = camera.position.y + viewport.getWorldHeight() * 0.5f;

            float minX = Math.min(0f, viewMinX);
            float minY = Math.min(0f, viewMinY);
            float maxX = Math.max(maze.widthPx(), viewMaxX);
            float maxY = Math.max(maze.heightPx(), viewMaxY);

            float startX = (float) Math.floor(minX / tileW) * tileW;
            float startY = (float) Math.floor(minY / tileH) * tileH;

            for (float x = startX; x < maxX; x += tileW) {
                for (float y = startY; y < maxY; y += tileH) {
                    batch.draw(backgroundTexture, x, y, tileW, tileH);
                }
            }
        }

        // Goal / heart.
        if (goalTexture != null) {
            float hs = activeGoalSize * 0.5f;
            batch.draw(goalTexture, activeGoalX - hs, activeGoalY - hs, activeGoalSize, activeGoalSize);
        }

        // Walls mapped from generated WallRegistry + difficulty.
        float t = WALL_THICKNESS;
        if (wallTexture != null) {
            for (WallSegment w : maze.walls()) {
                if (w.isHorizontal()) {
                    float x = Math.min(w.x1, w.x2);
                    float len = Math.abs(w.x2 - w.x1);
                    batch.draw(wallTexture, x - t * 0.5f, w.y1 - t * 0.5f, len + t, t);
                } else {
                    float y = Math.min(w.y1, w.y2);
                    float len = Math.abs(w.y2 - w.y1);
                    batch.draw(wallTexture, w.x1 - t * 0.5f, y - t * 0.5f, t, len + t);
                }
            }
        }

        // Enemies loaded from opponents model and animated in-game.
        for (GdxEnemyRuntime enemy : animatedEnemies) {
            Texture enemyTexture = loadTexture(enemy.imagePath);
            if (enemyTexture == null) {
                continue;
            }
            if (enemy.infectious) {
                drawInfectiousEdgeMist(batch, enemy, enemyTexture);
            }
            float half = enemy.size * 0.5f;
            float opacity = enemy.renderOpacity();
            if (opacity < 1.0f) {
                batch.setColor(1f, 1f, 1f, opacity);
            }
            batch.draw(enemyTexture, enemy.x - half, enemy.y - half, enemy.size, enemy.size);
            if (opacity < 1.0f) {
                batch.setColor(Color.WHITE);
            }

            String label = enemy.debugLabel(showBehaviourTypeSeconds > 0f, showMovementTypeSeconds > 0f);
            if (label != null) {
                font.setColor(new Color(0.95f, 0.97f, 1f, 1f));
                font.draw(batch, label, enemy.x - enemy.size * 0.5f, enemy.y + enemy.size * 0.75f);
            }
        }

        // Player from player Ecore/XMI model.
        float playerDrawSize = player.halfSize() * 2f;
        Texture activePlayerTexture = combatState.isDead() ? playerDeathTexture : playerTexture;
        batch.setColor(playerTintRed, playerTintGreen, playerTintBlue, 1f);
        if (activePlayerTexture != null) {
            float scale = combatState.isDead() ? PLAYER_DEAD_SCALE : PLAYER_ALIVE_SCALE;
            float drawSize = playerDrawSize * scale;
            float halfDraw = drawSize * HALF_RATIO;
            batch.draw(activePlayerTexture, player.x() - halfDraw, player.y() - halfDraw, drawSize, drawSize);
        }
        batch.setColor(Color.WHITE);

        batch.end();

        shapes.setProjectionMatrix(camera.combined);
        shapes.begin(ShapeRenderer.ShapeType.Filled);

        // Goal fallback.
        if (goalTexture == null) {
            shapes.setColor(0.15f, 0.55f, 0.2f, 1f);
            float hs = activeGoalSize * 0.5f;
            shapes.rect(activeGoalX - hs, activeGoalY - hs, activeGoalSize, activeGoalSize);
        }

        // Wall fallback.
        if (wallTexture == null) {
            shapes.setColor(0.85f, 0.85f, 0.9f, 1f);
            for (WallSegment w : maze.walls()) {
                if (w.isHorizontal()) {
                    float x = Math.min(w.x1, w.x2);
                    float len = Math.abs(w.x2 - w.x1);
                    shapes.rect(x - t * 0.5f, w.y1 - t * 0.5f, len + t, t);
                } else {
                    float y = Math.min(w.y1, w.y2);
                    float len = Math.abs(w.y2 - w.y1);
                    shapes.rect(w.x1 - t * 0.5f, y - t * 0.5f, t, len + t);
                }
            }
        }

        // Enemy fallback if any texture was missing.
        for (GdxEnemyRuntime enemy : animatedEnemies) {
            if (loadTexture(enemy.imagePath) != null) {
                continue;
            }
            if (enemy.infectious) {
                drawInfectiousMist(shapes, enemy);
            }
            shapes.setColor(0.65f, 0.2f, 0.2f, 1f);
            float half = enemy.size * 0.5f;
            shapes.rect(enemy.x - half, enemy.y - half, enemy.size, enemy.size);
        }

        // Player fallback.
        if (activePlayerTexture == null) {
            shapes.setColor(playerTintRed, playerTintGreen, playerTintBlue, 1f);
            float scale = combatState.isDead() ? PLAYER_DEAD_SCALE : PLAYER_ALIVE_SCALE;
            float drawSize = playerDrawSize * scale;
            float halfDraw = drawSize * HALF_RATIO;
            shapes.rect(player.x() - halfDraw, player.y() - halfDraw, drawSize, drawSize);
        }

        if (!activePathPoints.isEmpty()) {
            shapes.setColor(0.12f, 0.58f, 0.95f, 0.72f);
            for (int i = 1; i < activePathPoints.size(); i++) {
                Point2D a = activePathPoints.get(i - 1);
                Point2D b = activePathPoints.get(i);
                float ax = (float) a.getX();
                float ay = maze.heightPx() - (float) a.getY();
                float bx = (float) b.getX();
                float by = maze.heightPx() - (float) b.getY();
                drawPathSegment(shapes, ax, ay, bx, by, 8f);
            }
        }

        if (showEnemyPathSeconds > 0f) {
            drawEnemyPathOverlay(shapes);
        }

        if (showSpanningTreeInfo && maze instanceof RealMaze realMaze && realMaze.navigationGraph() != null) {
            Point2D playerPos = new Point2D(player.x(), maze.heightPx() - player.y());
            MazeNavigationGraphService.rebuildSpanningTreeFrom(realMaze.navigationGraph(), playerPos);
            var grid = realMaze.navigationGraph().getGrid();
            int cols = realMaze.navigationGraph().getCols();
            int rows = realMaze.navigationGraph().getRows();
            shapes.setColor(1f, 0.23f, 0.20f, 0.58f);
            for (int c = 0; c < cols; c++) {
                for (int r = 0; r < rows; r++) {
                    var node = grid[c][r];
                    if (node == null || node.getTreeParent() == null) {
                        continue;
                    }
                    float x1 = (float) node.getX();
                    float y1 = maze.heightPx() - (float) node.getY();
                    float x2 = (float) node.getTreeParent().getX();
                    float y2 = maze.heightPx() - (float) node.getTreeParent().getY();
                    drawPathSegment(shapes, x1, y1, x2, y2, 4f);
                }
            }
        }

        shapes.end();

        applyFullWindowGlViewport();
        drawHud();
        if (mode == Mode.HIGH_SCORES) {
            drawHighScoresOverlay();
        }
        if (mode == Mode.WON) {
            drawCenteredStateOverlay("YOU WIN", "Type your name then click Save Score, or Back to Menu.", winBackgroundTexture, Color.GREEN);
        }
        if (mode == Mode.GAME_OVER) {
            drawCenteredStateOverlay("GAME OVER", "Press ESC to return to start menu", gameOverBackgroundTexture, Color.RED);
        }
        if (mode == Mode.PLAYING && infectionWarningVisible) {
            drawInfectionWarningSign();
        }
    }

    private void drawInfectionWarningSign() {
        float w = hudCamera.viewportWidth;
        float h = hudCamera.viewportHeight;
        float triangleW = INFECTION_TRIANGLE_WIDTH;
        float triangleH = INFECTION_TRIANGLE_HEIGHT;
        float cx = w * HALF_RATIO;
        float cy = h * HALF_RATIO;

        shapes.setProjectionMatrix(hudCamera.combined);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapes.begin(ShapeRenderer.ShapeType.Filled);

        // Pulsing green outer glow around the warning triangle edges.
        float pulse = INFECTION_PULSE_BASE + INFECTION_PULSE_AMPLITUDE * (float) Math.sin(enemyAnimationClock * INFECTION_PULSE_SPEED);
        int glowLayers = INFECTION_GLOW_LAYERS;
        for (int i = glowLayers; i >= 1; i--) {
            float spread = 6f + i * 4f + pulse * 4f;
            float alpha = (0.10f + 0.10f * pulse) * (i / (float) glowLayers) * 0.55f;
            shapes.setColor(0.20f, 1.0f, 0.45f, alpha);
            shapes.triangle(
                    cx, cy + triangleH * 0.5f + spread,
                    cx - triangleW * 0.5f - spread, cy - triangleH * 0.5f - spread * 0.5f,
                    cx + triangleW * 0.5f + spread, cy - triangleH * 0.5f - spread * 0.5f);
        }

        shapes.setColor(1f, 0.84f, 0.30f, 0.96f);
        shapes.triangle(
                cx, cy + triangleH * 0.5f,
                cx - triangleW * 0.5f, cy - triangleH * 0.5f,
                cx + triangleW * 0.5f, cy - triangleH * 0.5f);
        shapes.setColor(0.22f, 0.14f, 0.00f, 0.98f);
        shapes.rect(cx - 5f, cy - 22f, 10f, 44f);
        shapes.circle(cx, cy - 35f, 6f);
        shapes.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        font.setColor(new Color(0.20f, 1.0f, 0.45f, 1f));
        font.getData().setScale(1.3f);
        glyphLayout.setText(font, INFECTION_WARNING_TEXT);
        float tx = cx - glyphLayout.width * 0.5f;
        float ty = cy - triangleH * 0.5f - 18f;
        font.draw(batch, INFECTION_WARNING_TEXT, tx, ty);
        font.getData().setScale(1.0f);
        batch.end();
    }

    private void drawInfectiousMist(ShapeRenderer renderer, GdxEnemyRuntime enemy) {
        float pulse = 0.5f + 0.5f * (float) Math.sin(enemyAnimationClock * (1.8f + enemy.infectionStrength) + enemy.phase);
        float centerX = enemy.x;
        float centerY = enemy.y;
        float baseRadius = enemy.size * (0.65f + 0.14f * enemy.infectionStrength);
        float outerRadius = baseRadius + pulse * enemy.size * 0.42f;
        float midRadius = baseRadius * 0.78f + pulse * enemy.size * 0.22f;

        // Faint haze; alpha kept low so sprite drawn on top stays clearly visible.
        renderer.setColor(0.20f, 1.0f, 0.46f, 0.10f + 0.10f * pulse * enemy.infectionStrength);
        renderer.circle(centerX, centerY, outerRadius, 28);
        renderer.setColor(0.56f, 1.0f, 0.72f, 0.08f + 0.10f * pulse * enemy.infectionStrength);
        renderer.circle(centerX, centerY, midRadius, 24);
    }

    private void drawInfectiousEdgeMist(SpriteBatch spriteBatch, GdxEnemyRuntime enemy, Texture enemyTexture) {
        float pulse = 0.5f + 0.5f * (float) Math.sin(enemyAnimationClock * (1.8f + enemy.infectionStrength) + enemy.phase);
        float intensity = Math.max(0.35f, Math.min(1f, enemy.infectionStrength));

        int prevSrcBlend = spriteBatch.getBlendSrcFunc();
        int prevDstBlend = spriteBatch.getBlendDstFunc();
        spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);

        for (int i = 0; i < INFECTION_EDGE_LAYERS; i++) {
            float layerFraction = i / (float) (INFECTION_EDGE_LAYERS - 1);
            float scale = 1.04f + layerFraction * (0.22f + 0.08f * pulse) + 0.06f * pulse * intensity;
            float alpha = (0.34f - layerFraction * 0.07f) * (0.78f + 0.22f * pulse) * intensity;

            float drawSize = enemy.size * scale;
            float halfDraw = drawSize * HALF_RATIO;

            float edgeOffset = (1.5f + 2.3f * layerFraction) * (0.95f + 1.05f * pulse);
            spriteBatch.setColor(0.30f, 1.0f, 0.52f, alpha);
            spriteBatch.draw(enemyTexture, enemy.x - halfDraw - edgeOffset, enemy.y - halfDraw, drawSize, drawSize);
            spriteBatch.draw(enemyTexture, enemy.x - halfDraw + edgeOffset, enemy.y - halfDraw, drawSize, drawSize);
            spriteBatch.draw(enemyTexture, enemy.x - halfDraw, enemy.y - halfDraw - edgeOffset, drawSize, drawSize);
            spriteBatch.draw(enemyTexture, enemy.x - halfDraw, enemy.y - halfDraw + edgeOffset, drawSize, drawSize);

            float diagonalOffset = edgeOffset * 0.75f;
            float diagonalAlpha = alpha * 0.9f;
            spriteBatch.setColor(0.30f, 1.0f, 0.52f, diagonalAlpha);
            spriteBatch.draw(enemyTexture, enemy.x - halfDraw - diagonalOffset, enemy.y - halfDraw - diagonalOffset, drawSize, drawSize);
            spriteBatch.draw(enemyTexture, enemy.x - halfDraw + diagonalOffset, enemy.y - halfDraw - diagonalOffset, drawSize, drawSize);
            spriteBatch.draw(enemyTexture, enemy.x - halfDraw - diagonalOffset, enemy.y - halfDraw + diagonalOffset, drawSize, drawSize);
            spriteBatch.draw(enemyTexture, enemy.x - halfDraw + diagonalOffset, enemy.y - halfDraw + diagonalOffset, drawSize, drawSize);
        }

        spriteBatch.setBlendFunction(prevSrcBlend, prevDstBlend);

        spriteBatch.setColor(Color.WHITE);
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
        batch.setProjectionMatrix(hudCamera.combined);
        shapes.setProjectionMatrix(hudCamera.combined);

        float w = hudCamera.viewportWidth;
        float h = hudCamera.viewportHeight;

        float panelW = Math.min(760f, w - 80f);
        float panelH = 420f;
        float panelX = (w - panelW) * 0.5f;
        float panelY = (h - panelH) * 0.5f - 20f;

        float titleY = h - 60f;

        float comboW = Math.min(430f, panelW - 120f);
        float comboH = 52f;
        float comboX = panelX + (panelW - comboW) * 0.5f;
        float comboY = panelY + panelH - 132f;

        float buttonW = 250f;
        float buttonH = 52f;
        float buttonX = panelX + (panelW - buttonW) * 0.5f;
        float buttonY = comboY - 78f;

        float highScoresButtonW = buttonW;
        float highScoresButtonH = 40f;
        float highScoresButtonX = buttonX;
        float highScoresButtonY = buttonY - highScoresButtonH - 12f;

        menuLayout.comboX = comboX;
        menuLayout.comboY = comboY;
        menuLayout.comboW = comboW;
        menuLayout.comboH = comboH;
        menuLayout.buttonX = buttonX;
        menuLayout.buttonY = buttonY;
        menuLayout.buttonW = buttonW;
        menuLayout.buttonH = buttonH;
        menuLayout.highScoresButtonX = highScoresButtonX;
        menuLayout.highScoresButtonY = highScoresButtonY;
        menuLayout.highScoresButtonW = highScoresButtonW;
        menuLayout.highScoresButtonH = highScoresButtonH;

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0.11f, 0.05f, 0.18f, 1f);
        shapes.rect(0f, h * 0.45f, w, h * 0.55f);
        shapes.setColor(0.05f, 0.10f, 0.18f, 1f);
        shapes.rect(0f, h * 0.18f, w, h * 0.27f);
        shapes.setColor(0.07f, 0.16f, 0.12f, 1f);
        shapes.rect(0f, 0f, w, h * 0.18f);

        shapes.setColor(0f, 0f, 0f, 0.44f);
        shapes.rect(panelX, panelY, panelW, panelH);

        shapes.setColor(0.06f, 0.13f, 0.22f, 1f);
        shapes.rect(comboX, comboY, comboW, comboH);
        shapes.setColor(1f, 0.90f, 0.43f, 1f);
        shapes.rect(buttonX, buttonY, buttonW, buttonH);
        shapes.setColor(0.56f, 1.0f, 0.88f, 1f);
        shapes.rect(highScoresButtonX, highScoresButtonY, highScoresButtonW, highScoresButtonH);
        if (startMenuDropdownOpen && !difficulties.isEmpty()) {
            float optH = comboH;
            shapes.setColor(0.03f, 0.09f, 0.15f, 1f);
            shapes.rect(comboX, comboY - difficulties.size() * optH, comboW, difficulties.size() * optH);
            for (int i = 0; i < difficulties.size(); i++) {
                float oy = comboY - (i + 1) * optH;
                if (i == selectedDifficultyIndex) {
                    shapes.setColor(0.08f, 0.20f, 0.34f, 1f);
                } else {
                    shapes.setColor(0.04f, 0.11f, 0.19f, 1f);
                }
                shapes.rect(comboX, oy, comboW, optH);
            }
        }
        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(0.56f, 1.0f, 0.88f, 1f);
        shapes.rect(panelX, panelY, panelW, panelH);
        shapes.rect(comboX, comboY, comboW, comboH);
        if (startMenuDropdownOpen && !difficulties.isEmpty()) {
            float optH = comboH;
            for (int i = 0; i < difficulties.size(); i++) {
                float oy = comboY - (i + 1) * optH;
                shapes.rect(comboX, oy, comboW, optH);
            }
        }
        shapes.end();

        batch.begin();
        font.setColor(Color.GOLD);
        font.getData().setScale(2.8f);
        glyphLayout.setText(font, "Maze Game");
        float iconSize = menuIconTexture != null ? 42f : 0f;
        float iconGap = menuIconTexture != null ? 16f : 0f;
        float blockWidth = glyphLayout.width + iconSize + iconGap;
        float blockX = panelX + (panelW - blockWidth) * 0.5f;
        if (menuIconTexture != null) {
            batch.draw(menuIconTexture, blockX, titleY - 35f, iconSize, iconSize);
        }
        font.draw(batch, "Maze Game", blockX + iconSize + iconGap, titleY);
        font.getData().setScale(1.0f);

        font.setColor(Color.WHITE);
        font.getData().setScale(1.55f);
        glyphLayout.setText(font, "Select Difficulty");
        font.draw(batch, "Select Difficulty", panelX + (panelW - glyphLayout.width) * 0.5f, panelY + panelH - 52f);
        font.getData().setScale(1.0f);

        String selectedText = selectedDifficultyIndex >= 0 && selectedDifficultyIndex < difficulties.size()
                ? displayName(difficulties.get(selectedDifficultyIndex))
                : "Easy";
        font.setColor(new Color(0.95f, 0.98f, 1f, 1f));
        font.draw(batch, selectedText, comboX + 18f, comboY + 33f);
        font.setColor(new Color(0.95f, 0.98f, 1f, 1f));
        font.draw(batch, startMenuDropdownOpen ? "^" : "v", comboX + comboW - 20f, comboY + 33f);

        if (startMenuDropdownOpen && !difficulties.isEmpty()) {
            float optH = comboH;
            for (int i = 0; i < difficulties.size(); i++) {
            float oy = comboY - (i + 1) * optH;
            font.setColor(i == selectedDifficultyIndex
                ? new Color(0.95f, 1f, 0.98f, 1f)
                : new Color(0.84f, 0.94f, 0.98f, 1f));
            font.draw(batch, displayName(difficulties.get(i)), comboX + 18f, oy + 33f);
            }
        }

        font.setColor(new Color(0.18f, 0.11f, 0f, 1f));
        font.draw(batch, pausedFromGame ? "Restart Mission" : "Start Mission", buttonX + 56f, buttonY + 33f);

        font.setColor(new Color(0.06f, 0.21f, 0.18f, 1f));
        glyphLayout.setText(font, "High Scores");
        font.draw(batch, "High Scores", highScoresButtonX + (highScoresButtonW - glyphLayout.width) * 0.5f, highScoresButtonY + 26f);

        float hintY = highScoresButtonY - 22f;
        font.setColor(new Color(0.93f, 0.97f, 1f, 1f));
        font.draw(batch, "Arrow keys to move, P path hint, O spanning tree, H high score, ESC", panelX + 38f, hintY);
        font.draw(batch, pausedFromGame ? "return to game" : "restart menu", panelX + 38f, hintY - 24f);

        font.setColor(new Color(1f, 0.90f, 0.43f, 1f));
        font.draw(batch, "Collect the heart, avoid enemies, and maximize your score", panelX + 92f, panelY - 38f);

        if (selectedDifficultyIndex >= 0 && selectedDifficultyIndex < difficulties.size()) {
            font.setColor(new Color(0.80f, 1f, 0.94f, 0.95f));
            String dims = "Difficulty: " + displayName(difficulties.get(selectedDifficultyIndex))
                    + "  " + boardSizeLabel(difficulties.get(selectedDifficultyIndex));
                font.draw(batch, dims, panelX + 20f, panelY + 26f);
        }

        if (statusMessage != null && !statusMessage.isBlank()) {
            font.setColor(new Color(1f, 0.35f, 0.30f, 1f));
            font.draw(batch, statusMessage, panelX + 16f, panelY + 44f);
        }
        if (loadingPending) {
            font.setColor(Color.GOLD);
            font.getData().setScale(2.0f);
            glyphLayout.setText(font, "Loading ...");
            float lx = (w - glyphLayout.width) * 0.5f;
            float ly = titleY - 70f;
            font.draw(batch, "Loading ...", lx, ly);
            font.getData().setScale(1.0f);
        }
        batch.end();

        if (startMenuDropdownOpen && !difficulties.isEmpty()) {
            drawStartMenuDropdownOverlay(comboX, comboY, comboW, comboH);
        }
    }

    private void drawStartMenuDropdownOverlay(float comboX, float comboY, float comboW, float comboH) {
        float optH = comboH;
        float optionsHeight = difficulties.size() * optH;

        shapes.setProjectionMatrix(hudCamera.combined);
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0.03f, 0.09f, 0.15f, 1f);
        shapes.rect(comboX, comboY - optionsHeight, comboW, optionsHeight);
        for (int i = 0; i < difficulties.size(); i++) {
            float oy = comboY - (i + 1) * optH;
            if (i == selectedDifficultyIndex) {
                shapes.setColor(0.08f, 0.20f, 0.34f, 1f);
            } else {
                shapes.setColor(0.04f, 0.11f, 0.19f, 1f);
            }
            shapes.rect(comboX, oy, comboW, optH);
        }
        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(0.56f, 1.0f, 0.88f, 1f);
        for (int i = 0; i < difficulties.size(); i++) {
            float oy = comboY - (i + 1) * optH;
            shapes.rect(comboX, oy, comboW, optH);
        }
        shapes.end();

        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        for (int i = 0; i < difficulties.size(); i++) {
            float oy = comboY - (i + 1) * optH;
            font.setColor(i == selectedDifficultyIndex
                ? new Color(0.95f, 1f, 0.98f, 1f)
                : new Color(0.84f, 0.94f, 0.98f, 1f));
            font.draw(batch, displayName(difficulties.get(i)), comboX + 18f, oy + 33f);
        }
        batch.end();
    }

    private void drawHud() {
        batch.setProjectionMatrix(hudCamera.combined);
        shapes.setProjectionMatrix(hudCamera.combined);

        float w = hudCamera.viewportWidth;
        float h = hudCamera.viewportHeight;

        float scoreX = w - SCORE_PANEL_WIDTH - 6f;
        float scoreY = h - TOP_MARGIN - SCORE_PANEL_HEIGHT;

        float buttonX = 14f;
        float buttonY = 7f;
        float buttonW = 112f;
        float buttonH = 26f;
        float terminalButtonX = buttonX + buttonW + 12f;
        float terminalButtonY = buttonY;
        float terminalButtonW = 112f;
        float terminalButtonH = 26f;
        float commandsPressOffset = hudInteractionState.commandPressOffsetY();
        float terminalPressOffset = hudInteractionState.terminalPressOffsetY();
        float commandYDraw = buttonY + commandsPressOffset;
        float terminalYDraw = terminalButtonY + terminalPressOffset;
        float rowPanelX = 8f;
        float rowPanelY = bottomRowY();
        float rowPanelW = w - 16f;
        float rowPanelH = bottomRowHeight();

        hudLayout.commandButtonX = buttonX;
        hudLayout.commandButtonY = commandYDraw;
        hudLayout.commandButtonW = buttonW;
        hudLayout.commandButtonH = buttonH;
        hudLayout.terminalButtonX = terminalButtonX;
        hudLayout.terminalButtonY = terminalYDraw;
        hudLayout.terminalButtonW = terminalButtonW;
        hudLayout.terminalButtonH = terminalButtonH;

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        // HP bar like JavaFX.
        shapes.setColor(1f, 1f, 1f, 0.96f);
        shapes.rect(1f, hpBarBottomY(h), w - 2f, HP_BAR_HEIGHT);
        shapes.setColor(0.76f, 0.22f, 0.17f, 0.92f);
        shapes.rect(1f, hpBarBottomY(h), (w - 2f) * currentHpRatio, HP_BAR_HEIGHT);

        // Score panel and bottom row share the same visual language.
        shapes.setColor(0f, 0f, 0f, 0.45f);
        shapes.rect(scoreX, scoreY, SCORE_PANEL_WIDTH, SCORE_PANEL_HEIGHT);
        shapes.setColor(0f, 0f, 0f, 0.12f);
        shapes.rect(rowPanelX, rowPanelY, rowPanelW, rowPanelH);

        if (hudInteractionState.commandButtonPressedSeconds() > 0f) {
            shapes.setColor(0.42f, 0.86f, 0.74f, 0.92f);
        } else {
            shapes.setColor(0.56f, 1.0f, 0.88f, 0.85f);
        }
        shapes.rect(buttonX, commandYDraw, buttonW, buttonH);
        if (hudInteractionState.terminalButtonPressedSeconds() > 0f) {
            shapes.setColor(0.88f, 0.76f, 0.30f, 0.95f);
        } else {
            shapes.setColor(1f, 0.90f, 0.43f, 0.90f);
        }
        shapes.rect(terminalButtonX, terminalYDraw, terminalButtonW, terminalButtonH);
        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(0.56f, 1.0f, 0.88f, 0.85f);
        shapes.rect(1f, hpBarBottomY(h), w - 2f, HP_BAR_HEIGHT);
        shapes.rect(scoreX, scoreY, SCORE_PANEL_WIDTH, SCORE_PANEL_HEIGHT);
        shapes.rect(rowPanelX, rowPanelY, rowPanelW, rowPanelH);
        shapes.rect(0f, BOTTOM_BAR_HEIGHT, w, h - BOTTOM_BAR_HEIGHT - HP_BAR_HEIGHT);
        shapes.rect(buttonX, commandYDraw, buttonW, buttonH);
        shapes.rect(terminalButtonX, terminalYDraw, terminalButtonW, terminalButtonH);
        shapes.end();

        batch.begin();
        int score = currentScore();

        font.setColor(new Color(0.07f, 0.22f, 0.19f, 1f));
        font.draw(batch, "Commands", buttonX + 10f, commandYDraw + 18f);

        font.setColor(new Color(0.18f, 0.11f, 0f, 1f));
        font.draw(batch, "Terminal", terminalButtonX + 16f, terminalYDraw + 18f);

        font.setColor(new Color(0.84f, 1f, 0.96f, 1f));
        String commandText = "H Highscore  ESC Restart  P Path "
                + (showHintInfo ? "[ON]" : "[OFF]")
                + (pathHintBudgetExhausted ? " [SPENT]" : String.format(" [%.0fs left]", pathHintRemainingSeconds()))
                + "  O Tree " + (showSpanningTreeInfo ? "[ON]" : "[OFF]");
        font.draw(batch, commandText, terminalButtonX + terminalButtonW + 14f, buttonY + 18f);

        font.setColor(Color.GOLD);
        font.getData().setScale(1.25f);
        font.draw(batch, "Score: " + score, scoreX + 14f, scoreY + 21f);
        font.getData().setScale(1.0f);

        if (mode == Mode.WON) {
            font.setColor(new Color(0.56f, 1.0f, 0.88f, 1f));
            font.draw(batch, "You reached the heart. Press ESC to return to menu.", 12f, BOTTOM_BAR_HEIGHT + 18f);
        } else if (mode == Mode.GAME_OVER) {
            font.setColor(new Color(1f, 0.35f, 0.30f, 1f));
            font.draw(batch, "You were defeated. Press ESC to return to menu.", 12f, BOTTOM_BAR_HEIGHT + 18f);
        } else if (statusMessage != null && !statusMessage.isBlank()) {
            font.setColor(new Color(0.56f, 1.0f, 0.88f, 1f));
            font.draw(batch, statusMessage, 12f, BOTTOM_BAR_HEIGHT + 18f);
        }

        if (terminalController.isActive()) {
            float panelX = 22f;
            float panelY = BOTTOM_BAR_HEIGHT + 26f;
            float panelW = Math.max(360f, w * 0.58f);
            float panelH = 56f;
            batch.end();

            shapes.begin(ShapeRenderer.ShapeType.Filled);
            shapes.setColor(0.02f, 0.04f, 0.09f, 0.93f);
            shapes.rect(panelX, panelY, panelW, panelH);
            shapes.end();

            shapes.begin(ShapeRenderer.ShapeType.Line);
            shapes.setColor(1f, 0.90f, 0.43f, 0.94f);
            shapes.rect(panelX, panelY, panelW, panelH);
            shapes.end();

            batch.begin();
            font.setColor(new Color(1f, 0.95f, 0.72f, 1f));
            font.draw(batch, "Terminal: " + terminalController.bufferText(), panelX + 10f, panelY + 35f);
            font.setColor(new Color(0.78f, 0.90f, 1f, 1f));
            font.draw(batch, "Enter: run  Backspace: delete  Esc: close", panelX + 10f, panelY + 16f);
        }
        batch.end();

        if (hudInteractionState.commandsOverlayVisible()) {
            drawCommandsOverlay();
        }
    }

    private void drawCommandsOverlay() {
        float w = hudCamera.viewportWidth;
        float h = hudCamera.viewportHeight;

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0f, 0f, 0f, 0.38f);
        shapes.rect(0f, 0f, w, h);
        shapes.setColor(0.08f, 0.06f, 0.17f, 0.92f);
        shapes.rect(20f, 60f, w - 40f, h - 140f);
        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(0.56f, 1.0f, 0.88f, 0.95f);
        shapes.rect(20f, 60f, w - 40f, h - 140f);
        shapes.end();

        batch.begin();
        font.setColor(new Color(1f, 0.90f, 0.43f, 1f));
        font.getData().setScale(1.4f);
        font.draw(batch, "Commands", 40f, h - 95f);
        font.getData().setScale(1.0f);

        font.setColor(new Color(0.95f, 0.97f, 1f, 1f));
        font.draw(batch, "Arrow Keys: Move player", 40f, h - 130f);
        font.draw(batch, "H: Open high score screen", 40f, h - 156f);
        font.draw(batch, "ESC: Difficulty and restart prompt", 40f, h - 182f);
        font.draw(batch, "P: Show shortest path to heart (hold)", 40f, h - 208f);
        font.draw(batch, "O: Toggle tree info", 40f, h - 234f);
        font.draw(batch, "Warning: Showing shortest path continuously reduces score over time.", 40f, h - 260f);
        font.draw(batch, "Click anywhere to close", 40f, h - 292f);
        batch.end();
    }

    private void drawHighScoresOverlay() {
        batch.setProjectionMatrix(hudCamera.combined);
        shapes.setProjectionMatrix(hudCamera.combined);

        float w = hudCamera.viewportWidth;
        float h = hudCamera.viewportHeight;

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0.03f, 0.04f, 0.08f, 0.18f);
        shapes.rect(0f, 0f, w, h);
        float panelW = Math.min(560f, w - 70f);
        float panelH = Math.min(460f, h - 90f);
        float panelX = (w - panelW) * 0.5f;
        float panelY = (h - panelH) * 0.5f;
        shapes.setColor(0f, 0f, 0f, 0.35f);
        shapes.rect(panelX, panelY, panelW, panelH);
        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(0.56f, 1.0f, 0.88f, 0.95f);
        shapes.rect(panelX, panelY, panelW, panelH);
        shapes.end();

        batch.begin();
        font.setColor(Color.GOLD);
        font.getData().setScale(1.7f);
        font.draw(batch, "High Scores", panelX + 22f, panelY + panelH - 24f);
        font.getData().setScale(1.0f);

        if (highScoreRows.isEmpty()) {
            font.setColor(new Color(0.62f, 0.73f, 0.83f, 1f));
            font.draw(batch, "No saved scores yet", panelX + 22f, panelY + panelH - 68f);
        } else {
            float y = panelY + panelH - 66f;
            int max = Math.min(10, highScoreRows.size());
            for (int i = 0; i < max; i++) {
                Score row = highScoreRows.get(i);
                font.setColor(new Color(0.95f, 0.97f, 1f, 1f));
                font.draw(batch, String.format(Locale.ROOT, "%d. %s: %d", i + 1, row.getName(), row.getTheScore()), panelX + 22f, y);
                y -= 28f;
            }
        }

        font.setColor(new Color(0.56f, 1.0f, 0.88f, 1f));
        font.draw(batch, "Press ESC to continue", panelX + 22f, panelY + 24f);
        batch.end();
    }

    private void drawCenteredStateOverlay(String title, String subtitle, Texture backdrop, Color titleColor) {
        float w = hudCamera.viewportWidth;
        float h = hudCamera.viewportHeight;

        batch.setProjectionMatrix(hudCamera.combined);
        if (backdrop != null) {
            batch.begin();
            batch.setColor(Color.WHITE);
            batch.draw(backdrop, 0f, 0f, w, h);
            batch.end();
        }

        float panelW = Math.min(600f, w - 80f);
        float panelH = mode == Mode.WON ? 280f : 180f;
        float panelX = (w - panelW) * 0.5f;
        float panelY = (h - panelH) * 0.5f;

        float saveBtnW = 160f;
        float saveBtnH = 36f;
        float backBtnW = 160f;
        float backBtnH = 36f;
        float btnGap = 16f;
        float btnRowY = panelY + 18f;
        float saveBtnX = panelX + (panelW - saveBtnW - backBtnW - btnGap) * 0.5f;
        float backBtnX = saveBtnX + saveBtnW + btnGap;

        if (mode == Mode.WON) {
            winSaveButtonX = saveBtnX;
            winSaveButtonY = btnRowY;
            winSaveButtonW = saveBtnW;
            winSaveButtonH = saveBtnH;
            winBackButtonX = backBtnX;
            winBackButtonY = btnRowY;
            winBackButtonW = backBtnW;
            winBackButtonH = backBtnH;
        }

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0f, 0f, 0f, 0.88f);
        shapes.rect(panelX, panelY, panelW, panelH);
        if (mode == Mode.WON) {
            if (!winScoreSaved) {
                shapes.setColor(0.56f, 1.0f, 0.88f, 1f);
            } else {
                shapes.setColor(0.30f, 0.40f, 0.38f, 1f);
            }
            shapes.rect(winSaveButtonX, winSaveButtonY, winSaveButtonW, winSaveButtonH);
            shapes.setColor(1f, 0.90f, 0.43f, 1f);
            shapes.rect(winBackButtonX, winBackButtonY, winBackButtonW, winBackButtonH);
        }
        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(0.56f, 1.0f, 0.88f, 0.95f);
        shapes.rect(panelX, panelY, panelW, panelH);
        shapes.end();

        batch.begin();
        font.setColor(titleColor);
        font.getData().setScale(2.0f);
        font.draw(batch, title, panelX + 34f, panelY + panelH - 30f);
        font.getData().setScale(1.0f);
        font.setColor(new Color(0.9f, 0.96f, 1f, 1f));
        font.draw(batch, subtitle, panelX + 34f, panelY + panelH - 70f);
        if (mode == Mode.WON) {
            font.setColor(new Color(1f, 0.90f, 0.43f, 1f));
            font.draw(batch, "Score: " + currentScore(), panelX + 34f, panelY + panelH - 100f);
            if (winScoreSaved) {
                font.setColor(new Color(0.56f, 1.0f, 0.88f, 1f));
                font.draw(batch, winScoreStatus, panelX + 34f, panelY + panelH - 130f);
            } else {
                font.setColor(new Color(0.95f, 0.97f, 1f, 1f));
                font.draw(batch, "Name: " + winNameInput + "_", panelX + 34f, panelY + panelH - 130f);
                if (winScoreStatus != null && !winScoreStatus.isBlank()) {
                    font.setColor(new Color(1f, 0.55f, 0.45f, 1f));
                    font.draw(batch, winScoreStatus, panelX + 34f, panelY + panelH - 158f);
                }
            }
            font.setColor(new Color(0.06f, 0.21f, 0.18f, 1f));
            glyphLayout.setText(font, "Save Score");
            font.draw(batch, "Save Score", winSaveButtonX + (winSaveButtonW - glyphLayout.width) * 0.5f, winSaveButtonY + 24f);
            font.setColor(new Color(0.18f, 0.11f, 0f, 1f));
            glyphLayout.setText(font, "Back to Menu");
            font.draw(batch, "Back to Menu", winBackButtonX + (winBackButtonW - glyphLayout.width) * 0.5f, winBackButtonY + 24f);
        }
        batch.end();
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
                        menuLayout.comboX,
                        menuLayout.comboY,
                        menuLayout.comboW,
                        menuLayout.comboH,
                        menuLayout.buttonX,
                        menuLayout.buttonY,
                        menuLayout.buttonW,
                        menuLayout.buttonH,
                        menuLayout.highScoresButtonX,
                        menuLayout.highScoresButtonY,
                        menuLayout.highScoresButtonW,
                        menuLayout.highScoresButtonH),
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

        if (contains(mx, my, hudLayout.commandButtonX, hudLayout.commandButtonY, hudLayout.commandButtonW, hudLayout.commandButtonH)) {
            hudInteractionState.pressCommandsButton(BUTTON_PRESS_SECONDS);
            return;
        }

        if (contains(mx, my, hudLayout.terminalButtonX, hudLayout.terminalButtonY, hudLayout.terminalButtonW, hudLayout.terminalButtonH)) {
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
        return main.game.maze.dto.HighScoreFile.upsert(playerName, score, DataFileConstants.HighscoreFilePath);
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

    private void drawEnemyPathOverlay(ShapeRenderer renderer) {
        if (maze == null) {
            return;
        }
        renderer.setColor(1f, 0.72f, 0.2f, 0.7f);
        for (GdxEnemyRuntime enemy : animatedEnemies) {
            List<ActivePathPoint> path = enemyDisplayPath(enemy);
            if (path.isEmpty()) {
                continue;
            }
            for (int i = 1; i < path.size(); i++) {
                ActivePathPoint a = path.get(i - 1);
                ActivePathPoint b = path.get(i);
                float ax = (float) a.x();
                float ay = maze.heightPx() - (float) a.y();
                float bx = (float) b.x();
                float by = maze.heightPx() - (float) b.y();
                drawPathSegment(renderer, ax, ay, bx, by, 5f);
            }
        }
    }

    /**
     * Returns the live runtime path the enemy is currently following,
     * taken directly from the movement service snapshot.
     * For AGGRESSIVE enemies this is the path held by {@link AdaptiveAggressiveMovementService};
     * for PATROL enemies it is the path held by {@link PatrolMovementService}.
     * Returns an empty list when the enemy has no active path (e.g. during WANDER_RECOVERY).
     * Points are in game-world coordinate space; {@link #drawEnemyPathOverlay} applies
     * the Y-flip needed for libGDX screen rendering.
     */
    private List<ActivePathPoint> enemyDisplayPath(GdxEnemyRuntime enemy) {
        // Always return the live snapshot from the movement service.
        // Points from the service are in game-world (bottom-left Y) space.
        // drawEnemyPathOverlay() expects the same space, so no Y-flip is needed here.
        return enemy.activePathPoints(patrolMovementService, adaptiveAggressiveMovementService);
    }

    private void loadHighScores() {
        highScoreRows.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(DataFileConstants.HighscoreFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(":");
                if (data.length != 2) {
                    continue;
                }
                String name = data[0].trim();
                int score = Integer.parseInt(data[1].trim());
                highScoreRows.add(new Score(name, score));
            }
        } catch (Exception ignored) {
            // Missing score file or malformed lines should not break runtime.
        }
        Collections.sort(highScoreRows);
        Collections.reverse(highScoreRows);
    }

    private static boolean contains(float px, float py, float x, float y, float w, float h) {
        return px >= x && px <= x + w && py >= y && py <= y + h;
    }

    private static void drawPathSegment(ShapeRenderer renderer, float x1, float y1, float x2, float y2, float thickness) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        float len = (float) Math.sqrt(dx * dx + dy * dy);
        if (len <= 0.001f) {
            return;
        }
        float nx = -dy / len * (thickness * 0.5f);
        float ny = dx / len * (thickness * 0.5f);
        renderer.triangle(x1 - nx, y1 - ny, x1 + nx, y1 + ny, x2 + nx, y2 + ny);
        renderer.triangle(x1 - nx, y1 - ny, x2 + nx, y2 + ny, x2 - nx, y2 - ny);
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
        startMenuDropdownOpen = false;
        pausedFromGame = false;
        playedWinSound = false;
        playedGameOverSound = false;
        deathSequenceStarted = false;
        deathDisplayRemainingSeconds = 0f;
        animatedEnemies.clear();
        antiLoopWanderMovementService.reset();
        adaptiveAggressiveMovementService.reset();
        patrolMovementService.reset();
        ghostPhasingMovementService.reset();
        pathPenaltyPoints = 0f;
        pathHintTotalUsedSeconds = 0f;
        pathHintBudgetExhausted = false;
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
        AudioEngine.get().stopChannel(AudioChannelConstants.MENU_MUSIC);
        AudioEngine.get().stopChannel(AudioChannelConstants.WIN_MUSIC);
        AudioEngine.get().stopChannel(AudioChannelConstants.GAME_OVER_MUSIC);
        AudioEngine.get().stopChannel(AudioChannelConstants.IN_GAME_MUSIC);
        AudioEngine.get().playLoop(visualStyle.inGameMusicPath(), AudioChannelConstants.IN_GAME_MUSIC);
    }

    private void switchToMenuMusic() {
        AudioEngine.get().stopChannel(AudioChannelConstants.IN_GAME_MUSIC);
        AudioEngine.get().stopChannel(AudioChannelConstants.WIN_MUSIC);
        AudioEngine.get().stopChannel(AudioChannelConstants.GAME_OVER_MUSIC);
        AudioEngine.get().stopChannel(AudioChannelConstants.MENU_MUSIC);
        AudioEngine.get().playLoop(resolveMenuMusicPath(), AudioChannelConstants.MENU_MUSIC);
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
        if (pathHintBudgetExhausted) {
            if (showHintInfo) {
                showHintInfo = false;
                flashStatus("Path hint energy already spent!", 3f);
            }
            return;
        }
        if (!showHintInfo) {
            return;
        }
        // Consume budget.
        float budget = pathHintBudgetSeconds(difficulties.isEmpty() ? null : difficulties.get(selectedDifficultyIndex));
        float remaining = budget - pathHintTotalUsedSeconds;
        if (remaining <= 0f) {
            pathHintBudgetExhausted = true;
            pathHintTotalUsedSeconds = budget;
            showHintInfo = false;
            flashStatus("Path hint energy already spent!", 3f);
            return;
        }
        float consumed = Math.min(dt, remaining);
        pathHintTotalUsedSeconds += consumed;
        pathPenaltyPoints += consumed * ROUTE_HINT_PENALTY_PER_SEC;
        if (pathHintTotalUsedSeconds >= budget) {
            pathHintBudgetExhausted = true;
            pathHintTotalUsedSeconds = budget;
            showHintInfo = false;
            flashStatus("Path hint energy already spent!", 3f);
        }
    }

    private float pathHintBudgetSeconds(Difficulty selected) {
        if (selected instanceof HardDifficulty) return PATH_HINT_BUDGET_HARD_SEC;
        if (selected instanceof main.game.maze.difficulties.NormalDifficulty) return PATH_HINT_BUDGET_NORMAL_SEC;
        return PATH_HINT_BUDGET_EASY_SEC;
    }

    private float pathHintRemainingSeconds() {
        Difficulty selected = difficulties.isEmpty() ? null : difficulties.get(selectedDifficultyIndex);
        return Math.max(0f, pathHintBudgetSeconds(selected) - pathHintTotalUsedSeconds);
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
        int movePenalty = moveCount * 10;
        int hintPenalty = (int) Math.floor(pathPenaltyPoints);
        return Math.max(0, baseScore - movePenalty - hintPenalty);
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
        flashStatus(text, 2.4f);
    }

    private void flashStatus(String text, float durationSeconds) {
        statusMessage = text;
        statusMessageTimer = durationSeconds;
    }

    @Override
    public void dispose() {
        AudioEngine.get().stopChannel(AudioChannelConstants.IN_GAME_MUSIC);
        AudioEngine.get().stopChannel(AudioChannelConstants.MENU_MUSIC);
        AudioEngine.get().stopChannel(AudioChannelConstants.WIN_MUSIC);
        AudioEngine.get().stopChannel(AudioChannelConstants.GAME_OVER_MUSIC);
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

    private static final class MenuLayout {
        private float comboX;
        private float comboY;
        private float comboW;
        private float comboH;
        private float buttonX;
        private float buttonY;
        private float buttonW;
        private float buttonH;
        private float highScoresButtonX;
        private float highScoresButtonY;
        private float highScoresButtonW;
        private float highScoresButtonH;
    }

    private static final class HudLayout {
        private float commandButtonX;
        private float commandButtonY;
        private float commandButtonW;
        private float commandButtonH;
        private float terminalButtonX;
        private float terminalButtonY;
        private float terminalButtonW;
        private float terminalButtonH;
    }



}
