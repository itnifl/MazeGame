package main.game.maze.libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.config.MazeRuntimeConfig;
import main.game.maze.constants.AudioChannelConstants;
import main.game.maze.constants.ResourceFileConstants;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.HardDifficulty;
import main.game.maze.difficulties.NormalDifficulty;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.libgdx.model.RuntimeVisualModel;
import main.game.maze.libgdx.model.RuntimeVisualModelLoader;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.generators.RealMaze;
import main.game.maze.mazeworld.generators.SampleMaze;
import main.game.maze.mazeworld.generators.WallSegment;
import main.game.maze.mazeworld.constants.StageConstants;
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
    private static final float HUD_HEIGHT = 52f;
    private static final long SEED = 1L;
    private static final int EASY_BASE_SCORE = 10000;
    private static final int NORMAL_BASE_SCORE = 20000;
    private static final int HARD_BASE_SCORE = 30000;

    private enum Mode {
        START_MENU,
        PLAYING,
        WON
    }

    private final MazeArena providedMaze;
    private final float cellSize;
    private final int cols;
    private final int rows;
    private final float playerSize;
    private final float configuredPlayerSpeed;
    private final boolean useRealMaze;
    private final DifficultyService difficultyService = new DifficultyService();
    private final List<Difficulty> difficulties = new ArrayList<>();
    private int selectedDifficultyIndex;
    private int baseScore;
    private int moveCount;
    private boolean showHintInfo;
    private boolean showSpanningTreeInfo;
    private String statusMessage = "";
    private float statusMessageTimer;
    private float enemyAnimationClock;
    private boolean upLatch;
    private boolean downLatch;
    private boolean enterLatch;
    private boolean escLatch;
    private boolean hLatch;
    private boolean pLatch;
    private boolean oLatch;
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
    private float activePlayerSpeed;
    private float activeGoalSize;
    private boolean playedWinSound;

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
        this.configuredPlayerSpeed = playerSpeed;
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
            handleStartMenuInput();
            return;
        }

        if (mode == Mode.WON) {
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !escLatch) {
                escLatch = true;
                switchToStartMenu();
            }
            if (!Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                escLatch = false;
            }
            return;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !escLatch) {
            escLatch = true;
            switchToStartMenu();
            return;
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            escLatch = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.H) && !hLatch) {
            hLatch = true;
            flashStatus("High score screen is available in JavaFX backend");
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.H)) {
            hLatch = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.P) && !pLatch) {
            pLatch = true;
            showHintInfo = !showHintInfo;
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.P)) {
            pLatch = false;
        }

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

        updateCameraFollow();

        if (player.reached(runtimeModel.goalX(), runtimeModel.goalY(), activeGoalSize * 0.5f)) {
            mode = Mode.WON;
            if (!playedWinSound) {
                playedWinSound = true;
                AudioEngine.get().play(ResourceFileConstants.WinGameSound);
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

        // Goal / heart.
        if (goalTexture != null) {
            float hs = activeGoalSize * 0.5f;
            batch.draw(goalTexture, runtimeModel.goalX() - hs, runtimeModel.goalY() - hs, activeGoalSize, activeGoalSize);
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
            shapes.rect(runtimeModel.goalX() - hs, runtimeModel.goalY() - hs, activeGoalSize, activeGoalSize);
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

        shapes.end();

        drawHud();
    }

    private void drawStartMenu() {
        batch.setProjectionMatrix(hudCamera.combined);
        shapes.setProjectionMatrix(hudCamera.combined);

        float w = hudCamera.viewportWidth;
        float h = hudCamera.viewportHeight;

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0.06f, 0.09f, 0.16f, 1f);
        shapes.rect(0f, 0f, w, h);
        shapes.setColor(0.12f, 0.06f, 0.2f, 0.95f);
        shapes.rect(40f, h - 260f, w - 80f, 220f);
        shapes.setColor(0.04f, 0.12f, 0.09f, 0.95f);
        shapes.rect(40f, 80f, w - 80f, 180f);
        shapes.end();

        batch.begin();
        font.setColor(Color.GOLD);
        font.draw(batch, "MAZE QUEST", 70f, h - 80f);
        font.setColor(new Color(0.56f, 1.0f, 0.88f, 1f));
        font.draw(batch, "Retro libGDX Start Menu", 70f, h - 120f);
        font.setColor(Color.WHITE);
        font.draw(batch, "Use UP and DOWN to pick difficulty, press ENTER to start", 70f, h - 155f);

        float y = h - 220f;
        for (int i = 0; i < difficulties.size(); i++) {
            Difficulty difficulty = difficulties.get(i);
            String marker = i == selectedDifficultyIndex ? "> " : "  ";
            Color color = i == selectedDifficultyIndex ? Color.GOLD : Color.LIGHT_GRAY;
            font.setColor(color);
            font.draw(batch, marker + displayName(difficulty) + "  " + boardSizeLabel(difficulty), 90f, y);
            y -= 28f;
        }

        font.setColor(new Color(1f, 0.9f, 0.45f, 1f));
        font.draw(batch, "Controls: Arrow Keys / WASD move, P path info, O tree info, ESC start menu", 70f, 180f);
        font.setColor(Color.WHITE);
        if (statusMessage != null && !statusMessage.isBlank()) {
            font.draw(batch, statusMessage, 70f, 145f);
        }
        batch.end();
    }

    private void drawHud() {
        batch.setProjectionMatrix(hudCamera.combined);
        shapes.setProjectionMatrix(hudCamera.combined);

        float w = hudCamera.viewportWidth;

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0f, 0f, 0f, 0.72f);
        shapes.rect(0f, 0f, w, HUD_HEIGHT);
        shapes.setColor(0.56f, 1.0f, 0.88f, 0.9f);
        shapes.rect(0f, HUD_HEIGHT - 2f, w, 2f);
        shapes.end();

        batch.begin();
        int score = Math.max(0, baseScore - (moveCount * 10));
        font.setColor(Color.GOLD);
        font.draw(batch, "Score: " + score, 12f, 35f);

        font.setColor(Color.WHITE);
        String commandText = "Commands: H Highscore  ESC Start Menu  P Path Info "
                + (showHintInfo ? "[ON]" : "[OFF]")
                + "  O Tree Info " + (showSpanningTreeInfo ? "[ON]" : "[OFF]");
        font.draw(batch, commandText, 140f, 35f);

        if (mode == Mode.WON) {
            font.setColor(new Color(0.56f, 1.0f, 0.88f, 1f));
            font.draw(batch, "You reached the heart. Press ESC to return to menu.", 12f, 18f);
        } else if (statusMessage != null && !statusMessage.isBlank()) {
            font.setColor(new Color(0.56f, 1.0f, 0.88f, 1f));
            font.draw(batch, statusMessage, 12f, 18f);
        }
        batch.end();
    }

    private void handleStartMenuInput() {
        boolean upPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean downPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        boolean enterPressed = Gdx.input.isKeyPressed(Input.Keys.ENTER);

        if (upPressed && !upLatch && !difficulties.isEmpty()) {
            selectedDifficultyIndex = (selectedDifficultyIndex - 1 + difficulties.size()) % difficulties.size();
        }
        if (downPressed && !downLatch && !difficulties.isEmpty()) {
            selectedDifficultyIndex = (selectedDifficultyIndex + 1) % difficulties.size();
        }
        if (enterPressed && !enterLatch) {
            startGameFromSelection();
        }

        upLatch = upPressed;
        downLatch = downPressed;
        enterLatch = enterPressed;
    }

    private void startGameFromSelection() {
        Difficulty selected = difficulties.isEmpty() ? null : difficulties.get(selectedDifficultyIndex);
        if (selected != null) {
            difficultyService.setCurrent(selected);
        }

        maze = providedMaze != null ? providedMaze : buildArenaForDifficulty(selected);
        runtimeModel = runtimeModelLoader.load(maze.widthPx(), maze.heightPx());
        activePlayerSpeed = Math.max(1f, runtimeModel.playerSpeed() > 0f ? runtimeModel.playerSpeed() : configuredPlayerSpeed);
        activeGoalSize = runtimeModel.goalSize() > 0f ? runtimeModel.goalSize() : GOAL_SIZE;
        player = new PlayerState(maze.startX(), maze.startY(), runtimeModel.playerSize() > 0f ? runtimeModel.playerSize() : playerSize);
        baseScore = baseScoreForDifficulty(selected);
        moveCount = 0;
        showHintInfo = false;
        showSpanningTreeInfo = false;
        playedWinSound = false;
        animatedEnemies.clear();

        int idx = 0;
        for (EnemySpawn enemy : runtimeModel.enemies()) {
            animatedEnemies.add(EnemyRuntime.fromSpawn(enemy, idx++));
        }

        if (viewport == null) {
            viewport = new FitViewport(
                    Math.min(maze.widthPx(), Math.max(1, Gdx.graphics.getWidth())),
                    Math.min(maze.heightPx(), Math.max(1, Gdx.graphics.getHeight() - (int) HUD_HEIGHT)),
                    camera);
        } else {
            ((FitViewport) viewport).setWorldSize(
                    Math.min(maze.widthPx(), Math.max(1, Gdx.graphics.getWidth())),
                    Math.min(maze.heightPx(), Math.max(1, Gdx.graphics.getHeight() - (int) HUD_HEIGHT)));
        }
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        playerTexture = loadTexture(runtimeModel.playerImagePath());
        goalTexture = loadTexture(runtimeModel.goalImagePath());
        wallTexture = loadTexture(runtimeModel.wallImagePath());
        mode = Mode.PLAYING;

        AudioEngine.get().stopChannel(AudioChannelConstants.IN_GAME_MUSIC);
        AudioEngine.get().playLoop(ResourceFileConstants.BackgroundMusic, AudioChannelConstants.IN_GAME_MUSIC);
        flashStatus("Started " + (selected != null ? displayName(selected) : "Default") + " difficulty");
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
        float halfW = viewport.getWorldWidth() * 0.5f;
        float halfH = viewport.getWorldHeight() * 0.5f;
        float camX = clamp(player.x(), halfW, Math.max(halfW, maze.widthPx() - halfW));
        float camY = clamp(player.y(), halfH, Math.max(halfH, maze.heightPx() - halfH));
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

    private void switchToStartMenu() {
        mode = Mode.START_MENU;
        AudioEngine.get().stopChannel(AudioChannelConstants.IN_GAME_MUSIC);
        flashStatus("Returned to start menu");
    }

    private void flashStatus(String text) {
        statusMessage = text;
        statusMessageTimer = 2.4f;
    }

    @Override
    public void dispose() {
        AudioEngine.get().stopChannel(AudioChannelConstants.IN_GAME_MUSIC);
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
