package main.game.maze.libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.HashMap;
import java.util.Map;

import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.config.MazeRuntimeConfig;
import main.game.maze.constants.AudioChannelConstants;
import main.game.maze.constants.ResourceFileConstants;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.libgdx.model.RuntimeVisualModel;
import main.game.maze.libgdx.model.RuntimeVisualModelLoader;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.generators.SampleMaze;
import main.game.maze.mazeworld.generators.WallSegment;

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
    private static final long SEED = 1L;

    private final MazeArena providedMaze;
    private final float cellSize;
    private final int cols;
    private final int rows;
    private final float playerSize;
    private final float configuredPlayerSpeed;
    private MazeArena maze;
    private PlayerState player;
    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private BitmapFont font;
    private OrthographicCamera camera;
    private Viewport viewport;
    private RuntimeVisualModel runtimeModel;
    private final RuntimeVisualModelLoader runtimeModelLoader = new RuntimeVisualModelLoader();
    private final Map<String, Texture> texturesByPath = new HashMap<>();
    private Texture playerTexture;
    private Texture goalTexture;
    private Texture wallTexture;
    private float activePlayerSpeed;
    private float activeGoalSize;
    private boolean playedWinSound;
    private boolean won;

    public GdxGameScreen() {
        this(null, DEFAULT_CELL_SIZE, DEFAULT_COLS, DEFAULT_ROWS, DEFAULT_PLAYER_SPEED);
    }

    public GdxGameScreen(MazeArena arena) {
        this(arena, DEFAULT_CELL_SIZE, DEFAULT_COLS, DEFAULT_ROWS, DEFAULT_PLAYER_SPEED);
    }

    public GdxGameScreen(MazeArena arena, MazeRuntimeConfig cfg) {
        this(arena, cfg.cellSize(), cfg.mazeCols(), cfg.mazeRows(), cfg.playerSpeed());
    }

    public GdxGameScreen(MazeArena arena, float cellSize, int cols, int rows, float playerSpeed) {
        this.providedMaze = arena;
        this.cellSize = cellSize;
        this.cols = cols;
        this.rows = rows;
        this.playerSize = cellSize * 0.5f;
        this.configuredPlayerSpeed = playerSpeed;
    }

    @Override
    public void create() {
        GdxBackend.install();
        maze = providedMaze != null
            ? providedMaze
            : new SampleMaze(cols, rows, cellSize, SEED);
        runtimeModel = runtimeModelLoader.load(maze.widthPx(), maze.heightPx());
        activePlayerSpeed = Math.max(1f, runtimeModel.playerSpeed() > 0f ? runtimeModel.playerSpeed() : configuredPlayerSpeed);
        activeGoalSize = runtimeModel.goalSize() > 0f ? runtimeModel.goalSize() : GOAL_SIZE;
        player = new PlayerState(maze.startX(), maze.startY(), runtimeModel.playerSize() > 0f ? runtimeModel.playerSize() : playerSize);
        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        camera = new OrthographicCamera();
        viewport = new FitViewport(maze.widthPx(), maze.heightPx() + 40f, camera);
        viewport.apply(true);

        playerTexture = loadTexture(runtimeModel.playerImagePath());
        goalTexture = loadTexture(runtimeModel.goalImagePath());
        wallTexture = loadTexture(runtimeModel.wallImagePath());

        AudioEngine.get().playLoop(ResourceFileConstants.BackgroundMusic, AudioChannelConstants.IN_GAME_MUSIC);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        update(Math.min(Gdx.graphics.getDeltaTime(), 1f / 30f));
        draw();
    }

    private void update(float dt) {
        if (won) return;
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
        }
        if (player.reached(runtimeModel.goalX(), runtimeModel.goalY(), activeGoalSize * 0.5f)) {
            won = true;
            if (!playedWinSound) {
                playedWinSound = true;
                AudioEngine.get().play(ResourceFileConstants.WinGameSound);
            }
        }
    }

    private void draw() {
        ScreenUtils.clear(0.07f, 0.07f, 0.12f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

        // Enemies loaded from opponents model.
        for (EnemySpawn enemy : runtimeModel.enemies()) {
            Texture enemyTexture = loadTexture(enemy.imagePath());
            if (enemyTexture == null) {
                continue;
            }
            float half = enemy.size() * 0.5f;
            batch.draw(enemyTexture, enemy.x() - half, enemy.y() - half, enemy.size(), enemy.size());
        }

        // Player from player Ecore/XMI model.
        float playerDrawSize = player.halfSize() * 2f;
        float playerHalf = player.halfSize();
        if (playerTexture != null) {
            batch.draw(playerTexture, player.x() - playerHalf, player.y() - playerHalf, playerDrawSize, playerDrawSize);
        }

        font.draw(batch, won ? "You reached the goal! Close the window to exit."
                              : "Arrow keys or WASD to move. Reach the heart.",
                  8f, maze.heightPx() + 30f);
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
        for (EnemySpawn enemy : runtimeModel.enemies()) {
            if (loadTexture(enemy.imagePath()) != null) {
                continue;
            }
            shapes.setColor(0.65f, 0.2f, 0.2f, 1f);
            float half = enemy.size() * 0.5f;
            shapes.rect(enemy.x() - half, enemy.y() - half, enemy.size(), enemy.size());
        }

        // Player fallback.
        if (playerTexture == null) {
            shapes.setColor(won ? Color.GOLD : Color.SKY);
            shapes.rect(player.x() - playerHalf, player.y() - playerHalf, playerDrawSize, playerDrawSize);
        }

        shapes.end();
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
}
