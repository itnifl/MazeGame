package main.game.maze.libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import main.game.maze.common.graphics.config.MazeRuntimeConfig;
import main.game.maze.libgdx.game.MazeArena;
import main.game.maze.libgdx.game.PlayerState;
import main.game.maze.libgdx.game.RealMaze;
import main.game.maze.libgdx.game.SampleMaze;
import main.game.maze.libgdx.game.WallSegment;

/**
 * Minimal-but-real libGDX gameplay subset: a procedurally generated maze the
 * player walks through with arrow keys until reaching the green goal cell.
 * Heavy lifting (maze generation, collision resolution) lives in pure-Java
 * helpers under {@code main.game.maze.libgdx.game} so it can be tested
 * headlessly.
 */
public final class GdxGameScreen extends ApplicationAdapter {

    private static final float DEFAULT_CELL_SIZE = 48f;
    private static final int DEFAULT_COLS = 16;
    private static final int DEFAULT_ROWS = 12;
    private static final float DEFAULT_PLAYER_SPEED = DEFAULT_CELL_SIZE * 3.5f;
    private static final float WALL_THICKNESS = 3f;
    private static final float GOAL_SIZE = 32f;
    private static final long SEED = 1L;

    private final MazeArena providedMaze;
    private final float cellSize;
    private final int cols;
    private final int rows;
    private final float playerSize;
    private final float playerSpeed;
    private MazeArena maze;
    private PlayerState player;
    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private BitmapFont font;
    private OrthographicCamera camera;
    private Viewport viewport;
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
        this.playerSpeed = playerSpeed;
    }

    @Override
    public void create() {
        GdxBackend.install();
        maze = providedMaze != null
            ? providedMaze
            : new SampleMaze(cols, rows, cellSize, SEED);
        player = new PlayerState(maze.startX(), maze.startY(), playerSize);
        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        camera = new OrthographicCamera();
        viewport = new FitViewport(maze.widthPx(), maze.heightPx() + 40f, camera);
        viewport.apply(true);
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
            player.attemptMove(dx * playerSpeed * dt, dy * playerSpeed * dt, maze);
        }
        if (player.reached(maze.goalX(), maze.goalY(), GOAL_SIZE * 0.5f)) {
            won = true;
        }
    }

    private void draw() {
        ScreenUtils.clear(0.07f, 0.07f, 0.12f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        shapes.setProjectionMatrix(camera.combined);

        // Goal cell.
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0.15f, 0.55f, 0.2f, 1f);
        shapes.rect(maze.goalX() - GOAL_SIZE * 0.5f, maze.goalY() - GOAL_SIZE * 0.5f, GOAL_SIZE, GOAL_SIZE);

        // Walls.
        shapes.setColor(0.85f, 0.85f, 0.9f, 1f);
        float t = WALL_THICKNESS;
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

        // Player.
        shapes.setColor(won ? Color.GOLD : Color.SKY);
        float hs = player.halfSize();
        shapes.rect(player.x() - hs, player.y() - hs, hs * 2f, hs * 2f);
        shapes.end();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, won ? "You reached the goal! Close the window to exit."
                              : "Arrow keys or WASD to move. Reach the green cell.",
                  8f, maze.heightPx() + 30f);
        batch.end();
    }

    @Override
    public void dispose() {
        if (batch != null) batch.dispose();
        if (shapes != null) shapes.dispose();
        if (font != null) font.dispose();
    }
}
