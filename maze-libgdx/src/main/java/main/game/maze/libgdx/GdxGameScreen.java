package main.game.maze.libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
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

import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.config.MazeRuntimeConfig;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import main.game.maze.common.graphics.config.PropertiesMazeVisualStyleLoader;
import main.game.maze.common.graphics.config.XmiMazeVisualStyleLoader;
import main.game.maze.constants.AudioChannelConstants;
import main.game.maze.constants.ResourceFileConstants;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.HardDifficulty;
import main.game.maze.difficulties.NormalDifficulty;
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
    private static final float WALL_THICKNESS = 3f;
    private static final float GOAL_SIZE = 50f;
    private static final float JAVA_FX_TICK_RATE = 30f;
    private static final float BOTTOM_BAR_HEIGHT = 40f;
    private static final float HP_BAR_HEIGHT = 20f;
    private static final float TOP_MARGIN = 22f;
    private static final float SCORE_PANEL_WIDTH = 170f;
    private static final float SCORE_PANEL_HEIGHT = 30f;
    private static final long SEED = 1L;
    private static final int EASY_BASE_SCORE = 10000;
    private static final int NORMAL_BASE_SCORE = 20000;
    private static final int HARD_BASE_SCORE = 30000;
    private static final float MOUSE_STEP_DISTANCE = 20f;
    private static final float ROUTE_HINT_PENALTY_PER_SEC = 5f;
    private static final float PLAYER_MAX_HP = 100f;
    private static final float CONTACT_DAMAGE = 10f;
    private static final float CONTACT_DAMAGE_COOLDOWN_SEC = 0.45f;
    private static final float CONTACT_HIT_DISTANCE_PADDING = 2f;

    private enum Mode {
        START_MENU,
        PLAYING,
        HIGH_SCORES,
        WON,
        GAME_OVER
    }

    private final MazeArena providedMaze;
    private final float cellSize;
    private final int cols;
    private final int rows;
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
    private boolean showCommandsOverlay;
    private String statusMessage = "";
    private float statusMessageTimer;
    private float enemyAnimationClock;
    private boolean upLatch;
    private boolean downLatch;
    private boolean enterLatch;
    private boolean escLatch;
    private boolean hLatch;
    private boolean oLatch;
    private boolean startMenuDropdownOpen;
    private boolean pausedFromGame;
    private Mode mode = Mode.START_MENU;

    private MazeArena maze;
    private PlayerState player;
    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private BitmapFont font;
    private OrthographicCamera camera;
    private OrthographicCamera hudCamera;
    private Viewport viewport;
    private RuntimeVisualModel runtimeModel;
    private final RuntimeVisualModelLoader runtimeModelLoader = new RuntimeVisualModelLoader();
    private final Map<String, Texture> texturesByPath = new HashMap<>();
    private final List<EnemyRuntime> animatedEnemies = new ArrayList<>();
    private Texture playerTexture;
    private Texture goalTexture;
    private Texture wallTexture;
    private Texture backgroundTexture;
    private Texture menuIconTexture;
    private float activePlayerSpeed;
    private float activeGoalSize;
    private float activeGoalX;
    private float activeGoalY;
    private float currentHpRatio = 1f;
    private boolean playedWinSound;
    private boolean playedGameOverSound;
    private final List<Point2D> activePathPoints = new ArrayList<>();
    private final List<ScoreRow> highScoreRows = new ArrayList<>();
    private final MenuLayout menuLayout = new MenuLayout();
    private final HudLayout hudLayout = new HudLayout();
    private float pathPenaltyPoints;
    private float currentHp = PLAYER_MAX_HP;
    private float contactDamageCooldown;

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
        this.cols = cols;
        this.rows = rows;
        this.playerSize = cellSize * 0.5f;
        this.useRealMaze = useRealMaze;
    }

    @Override
    public void create() {
        GdxBackend.install();

        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        camera = new OrthographicCamera();
        hudCamera = new OrthographicCamera();
        menuIconTexture = loadTexture(visualStyle.menuIconImagePath());

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
    }

    @Override
    public void resize(int width, int height) {
        hudCamera.setToOrtho(false, Math.max(1, width), Math.max(1, height));
        hudCamera.update();
        if (viewport != null) {
            viewport.update(width, height, true);
        }
    }

    @Override
    public void render() {
        float dt = Math.min(Gdx.graphics.getDeltaTime(), 1f / 30f);
        update(dt);
        draw();
    }

    private void update(float dt) {
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
                mode = Mode.PLAYING;
            }
            if (!Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                escLatch = false;
            }
            return;
        }

        if (mode == Mode.WON) {
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

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !escLatch) {
            escLatch = true;
            switchToStartMenu(true);
            return;
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            escLatch = false;
        }

        handleGameMouseInput();

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

        enemyAnimationClock += dt;
        for (EnemyRuntime enemy : animatedEnemies) {
            enemy.update(enemyAnimationClock, maze.widthPx(), maze.heightPx());
        }

        handleEnemyCollisions(dt);

        updateCameraFollow();

        if (player.reached(activeGoalX, activeGoalY, activeGoalSize * 0.5f)) {
            mode = Mode.WON;
            if (!playedWinSound) {
                playedWinSound = true;
                AudioEngine.get().stopChannel(AudioChannelConstants.IN_GAME_MUSIC);
                AudioEngine.get().play(visualStyle.winSoundPath());
            }
        }
    }

    private void draw() {
        ScreenUtils.clear(0.07f, 0.07f, 0.12f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (mode == Mode.START_MENU) {
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
        for (EnemyRuntime enemy : animatedEnemies) {
            Texture enemyTexture = loadTexture(enemy.imagePath);
            if (enemyTexture == null) {
                continue;
            }
            float half = enemy.size * 0.5f;
            batch.draw(enemyTexture, enemy.x - half, enemy.y - half, enemy.size, enemy.size);
        }

        // Player from player Ecore/XMI model.
        float playerDrawSize = player.halfSize() * 2f;
        float playerHalf = player.halfSize();
        if (playerTexture != null) {
            batch.draw(playerTexture, player.x() - playerHalf, player.y() - playerHalf, playerDrawSize, playerDrawSize);
        }

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
        for (EnemyRuntime enemy : animatedEnemies) {
            if (loadTexture(enemy.imagePath) != null) {
                continue;
            }
            shapes.setColor(0.65f, 0.2f, 0.2f, 1f);
            float half = enemy.size * 0.5f;
            shapes.rect(enemy.x - half, enemy.y - half, enemy.size, enemy.size);
        }

        // Player fallback.
        if (playerTexture == null) {
            shapes.setColor(mode == Mode.WON ? Color.GOLD : Color.SKY);
            shapes.rect(player.x() - playerHalf, player.y() - playerHalf, playerDrawSize, playerDrawSize);
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

        drawHud();
        if (mode == Mode.HIGH_SCORES) {
            drawHighScoresOverlay();
        }
        if (mode == Mode.WON) {
            drawCenteredStateOverlay("YOU WIN", "Press ESC to return to start menu");
        }
        if (mode == Mode.GAME_OVER) {
            drawCenteredStateOverlay("GAME OVER", "Press ESC to return to start menu");
        }
    }

    private void drawStartMenu() {
        batch.setProjectionMatrix(hudCamera.combined);
        shapes.setProjectionMatrix(hudCamera.combined);

        float w = hudCamera.viewportWidth;
        float h = hudCamera.viewportHeight;

        float panelW = Math.min(760f, w - 80f);
        float panelH = 340f;
        float panelX = (w - panelW) * 0.5f;
        float panelY = (h - panelH) * 0.5f - 20f;

        float titleY = panelY + panelH + 156f;
        float subtitleY = panelY + panelH + 106f;

        float comboW = Math.min(430f, panelW - 120f);
        float comboH = 52f;
        float comboX = panelX + (panelW - comboW) * 0.5f;
        float comboY = panelY + panelH - 132f;

        float buttonW = 250f;
        float buttonH = 52f;
        float buttonX = panelX + (panelW - buttonW) * 0.5f;
        float buttonY = comboY - 78f;

        menuLayout.comboX = comboX;
        menuLayout.comboY = comboY;
        menuLayout.comboW = comboW;
        menuLayout.comboH = comboH;
        menuLayout.buttonX = buttonX;
        menuLayout.buttonY = buttonY;
        menuLayout.buttonW = buttonW;
        menuLayout.buttonH = buttonH;

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
        if (startMenuDropdownOpen && !difficulties.isEmpty()) {
            float optH = comboH;
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
        font.draw(batch, "MAZEGAME", panelX + panelW * 0.30f, titleY);
        font.getData().setScale(1.0f);

        if (menuIconTexture != null) {
            float iconSize = 42f;
            batch.draw(menuIconTexture, panelX + panelW * 0.30f - 58f, titleY - 35f, iconSize, iconSize);
        }

        font.setColor(new Color(0.56f, 1.0f, 0.88f, 1f));
        font.draw(batch, "Retro Challenge Mode", panelX + panelW * 0.40f, subtitleY);

        font.setColor(Color.WHITE);
        font.getData().setScale(1.55f);
        font.draw(batch, "Select Difficulty", panelX + panelW * 0.40f, panelY + panelH - 52f);
        font.getData().setScale(1.0f);

        String selectedText = selectedDifficultyIndex >= 0 && selectedDifficultyIndex < difficulties.size()
                ? displayName(difficulties.get(selectedDifficultyIndex))
                : "Easy";
        font.setColor(new Color(0.95f, 0.98f, 1f, 0.88f));
        font.draw(batch, selectedText, comboX + 18f, comboY + 33f);
        font.setColor(new Color(0.95f, 0.98f, 1f, 0.45f));
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

        float hintY = buttonY - 34f;
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
        float rowPanelX = 8f;
        float rowPanelY = 3f;
        float rowPanelW = w - 16f;
        float rowPanelH = BOTTOM_BAR_HEIGHT - 6f;

        hudLayout.commandButtonX = buttonX;
        hudLayout.commandButtonY = buttonY;
        hudLayout.commandButtonW = buttonW;
        hudLayout.commandButtonH = buttonH;

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        // HP bar like JavaFX.
        shapes.setColor(0.76f, 0.22f, 0.17f, 0.92f);
        shapes.rect(1f, h - HP_BAR_HEIGHT - 1f, (w - 2f) * currentHpRatio, HP_BAR_HEIGHT);

        // Score panel and bottom row share the same visual language.
        shapes.setColor(0f, 0f, 0f, 0.45f);
        shapes.rect(scoreX, scoreY, SCORE_PANEL_WIDTH, SCORE_PANEL_HEIGHT);
        shapes.setColor(0f, 0f, 0f, 0.12f);
        shapes.rect(rowPanelX, rowPanelY, rowPanelW, rowPanelH);

        shapes.setColor(0.56f, 1.0f, 0.88f, 0.85f);
        shapes.rect(buttonX, buttonY, buttonW, buttonH);
        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(0.56f, 1.0f, 0.88f, 0.85f);
        shapes.rect(1f, h - HP_BAR_HEIGHT - 1f, w - 2f, HP_BAR_HEIGHT);
        shapes.rect(scoreX, scoreY, SCORE_PANEL_WIDTH, SCORE_PANEL_HEIGHT);
        shapes.rect(rowPanelX, rowPanelY, rowPanelW, rowPanelH);
        shapes.rect(0f, BOTTOM_BAR_HEIGHT, w, h - BOTTOM_BAR_HEIGHT - HP_BAR_HEIGHT);
        shapes.end();

        batch.begin();
        int score = currentScore();

        font.setColor(new Color(0.07f, 0.22f, 0.19f, 1f));
        font.draw(batch, "Commands", buttonX + 10f, buttonY + 18f);

        font.setColor(new Color(0.84f, 1f, 0.96f, 1f));
        font.draw(batch, "Open keyboard command help", buttonX + buttonW + 14f, buttonY + 18f);

        font.setColor(Color.GOLD);
        font.getData().setScale(1.25f);
        font.draw(batch, "Score: " + score, scoreX + 14f, scoreY + 21f);
        font.getData().setScale(1.0f);

        font.setColor(new Color(0.84f, 1f, 0.96f, 1f));
        String commandText = "H Highscore  ESC Restart  P Path "
                + (showHintInfo ? "[ON]" : "[OFF]")
                + "  O Tree " + (showSpanningTreeInfo ? "[ON]" : "[OFF]");
        font.draw(batch, commandText, buttonX + buttonW + 260f, buttonY + 18f);

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
        batch.end();

        if (showCommandsOverlay) {
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
                ScoreRow row = highScoreRows.get(i);
                font.setColor(new Color(0.95f, 0.97f, 1f, 1f));
                font.draw(batch, String.format(Locale.ROOT, "%d. %s: %d", i + 1, row.name, row.score), panelX + 22f, y);
                y -= 28f;
            }
        }

        font.setColor(new Color(0.56f, 1.0f, 0.88f, 1f));
        font.draw(batch, "Press ESC to continue", panelX + 22f, panelY + 24f);
        batch.end();
    }

    private void drawCenteredStateOverlay(String title, String subtitle) {
        float w = hudCamera.viewportWidth;
        float h = hudCamera.viewportHeight;

        float panelW = Math.min(600f, w - 80f);
        float panelH = 180f;
        float panelX = (w - panelW) * 0.5f;
        float panelY = (h - panelH) * 0.5f;

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0f, 0f, 0f, 0.30f);
        shapes.rect(panelX, panelY, panelW, panelH);
        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(0.56f, 1.0f, 0.88f, 0.95f);
        shapes.rect(panelX, panelY, panelW, panelH);
        shapes.end();

        batch.begin();
        font.setColor(Color.GOLD);
        font.getData().setScale(2.0f);
        font.draw(batch, title, panelX + 34f, panelY + panelH - 50f);
        font.getData().setScale(1.0f);
        font.setColor(new Color(0.9f, 0.96f, 1f, 1f));
        font.draw(batch, subtitle, panelX + 34f, panelY + 58f);
        batch.end();
    }

    private void handleStartMenuInput() {
        handleStartMenuMouseInput();
        boolean upPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean downPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        boolean enterPressed = Gdx.input.isKeyPressed(Input.Keys.ENTER);

        if (upPressed && !upLatch && !difficulties.isEmpty()) {
            selectedDifficultyIndex = (selectedDifficultyIndex - 1 + difficulties.size()) % difficulties.size();
            AudioEngine.get().play(visualStyle.menuSelectSoundPath());
        }
        if (downPressed && !downLatch && !difficulties.isEmpty()) {
            selectedDifficultyIndex = (selectedDifficultyIndex + 1) % difficulties.size();
            AudioEngine.get().play(visualStyle.menuSelectSoundPath());
        }
        if (enterPressed && !enterLatch) {
            AudioEngine.get().play(visualStyle.menuSelectSoundPath());
            startGameFromSelection();
        }

        upLatch = upPressed;
        downLatch = downPressed;
        enterLatch = enterPressed;
    }

    private void handleStartMenuMouseInput() {
        if (!Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
            return;
        }
        float mx = Gdx.input.getX();
        float my = hudCamera.viewportHeight - Gdx.input.getY();

        if (startMenuDropdownOpen && !difficulties.isEmpty()) {
            float optH = menuLayout.comboH;
            for (int i = 0; i < difficulties.size(); i++) {
                float oy = menuLayout.comboY - (i + 1) * optH;
                if (contains(mx, my, menuLayout.comboX, oy, menuLayout.comboW, optH)) {
                    selectedDifficultyIndex = i;
                    startMenuDropdownOpen = false;
                    AudioEngine.get().play(visualStyle.menuSelectSoundPath());
                    return;
                }
            }
            if (!contains(mx, my, menuLayout.comboX, menuLayout.comboY, menuLayout.comboW, menuLayout.comboH)) {
                startMenuDropdownOpen = false;
            }
        }

        if (contains(mx, my, menuLayout.comboX, menuLayout.comboY, menuLayout.comboW, menuLayout.comboH) && !difficulties.isEmpty()) {
            startMenuDropdownOpen = !startMenuDropdownOpen;
            AudioEngine.get().play(visualStyle.menuSelectSoundPath());
            return;
        }
        if (contains(mx, my, menuLayout.buttonX, menuLayout.buttonY, menuLayout.buttonW, menuLayout.buttonH)) {
            startMenuDropdownOpen = false;
            AudioEngine.get().play(visualStyle.menuSelectSoundPath());
            startGameFromSelection();
        }
    }

    private void handleGameMouseInput() {
        if (!Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
            return;
        }
        float mx = Gdx.input.getX();
        float my = hudCamera.viewportHeight - Gdx.input.getY();

        if (showCommandsOverlay) {
            showCommandsOverlay = false;
            return;
        }

        if (contains(mx, my, hudLayout.commandButtonX, hudLayout.commandButtonY, hudLayout.commandButtonW, hudLayout.commandButtonH)) {
            showCommandsOverlay = !showCommandsOverlay;
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

    private void loadHighScores() {
        highScoreRows.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(ResourceFileConstants.HighscoreFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(":");
                if (data.length != 2) {
                    continue;
                }
                String name = data[0].trim();
                int score = Integer.parseInt(data[1].trim());
                highScoreRows.add(new ScoreRow(name, score));
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
        player = new PlayerState(maze.startX(), maze.startY(), runtimeModel.playerSize() > 0f ? runtimeModel.playerSize() : playerSize);
        baseScore = baseScoreForDifficulty(selected);
        moveCount = 0;
        showHintInfo = false;
        showSpanningTreeInfo = false;
        startMenuDropdownOpen = false;
        pausedFromGame = false;
        playedWinSound = false;
        playedGameOverSound = false;
        animatedEnemies.clear();
        pathPenaltyPoints = 0f;
        currentHp = PLAYER_MAX_HP;
        currentHpRatio = 1f;
        contactDamageCooldown = 0f;

        int idx = 0;
        for (EnemySpawn enemy : runtimeModel.enemies()) {
            animatedEnemies.add(EnemyRuntime.fromSpawn(enemy, idx++));
        }

        if (viewport == null) {
            viewport = new ScreenViewport(camera);
        }
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        playerTexture = loadTexture(runtimeModel.playerImagePath());
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
        float goalW = activeGoalSize;
        float goalH = activeGoalSize;
        if (goalTexture != null) {
            goalW = goalTexture.getWidth();
            goalH = goalTexture.getHeight();
        }
        activeGoalX = (maze.widthPx() - goalW) * 0.5f + goalW * 0.5f;
        activeGoalY = (maze.heightPx() - goalH) * 0.5f + goalH * 0.5f;
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
        int windowW = Math.min(targetW, maxW);
        int windowH = Math.min(targetH + (int) BOTTOM_BAR_HEIGHT, maxH);
        int finalWidth = Math.min(maxW, Math.max(800, windowW));
        int finalHeight = Math.min(maxH, Math.max(640, windowH));
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
        if (selected instanceof HardDifficulty) {
            return HARD_BASE_SCORE;
        }
        if (selected instanceof NormalDifficulty) {
            return NORMAL_BASE_SCORE;
        }
        return EASY_BASE_SCORE;
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
            camY = maze.heightPx() * 0.5f;
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

    private static float distanceSquared(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        return dx * dx + dy * dy;
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
        AudioEngine.get().stopChannel(AudioChannelConstants.IN_GAME_MUSIC);
        AudioEngine.get().playLoop(visualStyle.inGameMusicPath(), AudioChannelConstants.IN_GAME_MUSIC);
    }

    private void switchToMenuMusic() {
        AudioEngine.get().stopChannel(AudioChannelConstants.IN_GAME_MUSIC);
        AudioEngine.get().stopChannel(AudioChannelConstants.MENU_MUSIC);
        AudioEngine.get().playLoop(visualStyle.menuMusicPath(), AudioChannelConstants.MENU_MUSIC);
    }

    private void applyPathPenalty(float dt) {
        if (!showHintInfo || mode != Mode.PLAYING) {
            return;
        }
        pathPenaltyPoints += dt * ROUTE_HINT_PENALTY_PER_SEC;
    }

    private void handleEnemyCollisions(float dt) {
        if (mode != Mode.PLAYING || player == null) {
            return;
        }
        contactDamageCooldown = Math.max(0f, contactDamageCooldown - dt);
        if (contactDamageCooldown > 0f) {
            return;
        }

        float playerRadius = player.halfSize();
        for (EnemyRuntime enemy : animatedEnemies) {
            float enemyRadius = enemy.size * 0.5f;
            float allowed = playerRadius + enemyRadius + CONTACT_HIT_DISTANCE_PADDING;
            if (distanceSquared(player.x(), player.y(), enemy.x, enemy.y) <= allowed * allowed) {
                currentHp = Math.max(0f, currentHp - CONTACT_DAMAGE);
                currentHpRatio = Math.max(0f, currentHp / PLAYER_MAX_HP);
                contactDamageCooldown = CONTACT_DAMAGE_COOLDOWN_SEC;
                if (currentHp <= 0f) {
                    mode = Mode.GAME_OVER;
                    if (!playedGameOverSound) {
                        playedGameOverSound = true;
                        AudioEngine.get().stopChannel(AudioChannelConstants.IN_GAME_MUSIC);
                        AudioEngine.get().play(ResourceFileConstants.GameOverSound);
                    }
                }
                break;
            }
        }
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
        statusMessage = text;
        statusMessageTimer = 2.4f;
    }

    @Override
    public void dispose() {
        AudioEngine.get().stopChannel(AudioChannelConstants.IN_GAME_MUSIC);
        AudioEngine.get().stopChannel(AudioChannelConstants.MENU_MUSIC);
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
    }

    private static final class HudLayout {
        private float commandButtonX;
        private float commandButtonY;
        private float commandButtonW;
        private float commandButtonH;
    }

    private static final class ScoreRow implements Comparable<ScoreRow> {
        private final String name;
        private final int score;

        private ScoreRow(String name, int score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public int compareTo(ScoreRow other) {
            return Integer.compare(this.score, other.score);
        }
    }

    private static final class EnemyRuntime {
        private final String imagePath;
        private final float size;
        private final float baseX;
        private final float baseY;
        private final float radius;
        private final float speed;
        private final float phase;
        private float x;
        private float y;

        private EnemyRuntime(String imagePath, float size, float baseX, float baseY, float radius, float speed, float phase) {
            this.imagePath = imagePath;
            this.size = size;
            this.baseX = baseX;
            this.baseY = baseY;
            this.radius = radius;
            this.speed = speed;
            this.phase = phase;
            this.x = baseX;
            this.y = baseY;
        }

        private static EnemyRuntime fromSpawn(EnemySpawn spawn, int index) {
            float radius = Math.max(8f, spawn.size() * 0.35f);
            float speed = 0.9f + (index % 5) * 0.25f;
            float phase = index * 0.8f;
            return new EnemyRuntime(spawn.imagePath(), spawn.size(), spawn.x(), spawn.y(), radius, speed, phase);
        }

        private void update(float t, float maxX, float maxY) {
            float nx = baseX + (float) Math.cos(t * speed + phase) * radius;
            float ny = baseY + (float) Math.sin(t * speed + phase) * radius;
            x = clamp(nx, size * 0.5f, Math.max(size * 0.5f, maxX - size * 0.5f));
            y = clamp(ny, size * 0.5f, Math.max(size * 0.5f, maxY - size * 0.5f));
        }
    }
}
