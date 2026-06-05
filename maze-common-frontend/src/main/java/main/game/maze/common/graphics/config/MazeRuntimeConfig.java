package main.game.maze.common.graphics.config;

/**
 * Immutable, backend-neutral runtime configuration for a maze game session.
 *
 * <p>Phase 4 (F16) entrypoint: this record is the canonical handle the
 * launcher reads from. A loader populates it from a configuration source
 * (.properties today, eventually the DSL emitted by
 * {@code main.game.maze.dsl}). The record stays JavaFX/libGDX-free so
 * either backend can consume it.
 *
 * @param windowWidth   pixel width of the game window
 * @param windowHeight  pixel height of the game window
 * @param mazeCols      maze grid columns (used by the procedural generator)
 * @param mazeRows      maze grid rows
 * @param cellSize      pixel size of one maze cell
 * @param playerSpeed   player movement speed in pixels/second
 * @param useRealMaze   when true, render the shared {@code GameMazeWorld}
 *                      via the libGDX RealMaze adapter; when false, use the
 *                      standalone procedural SampleMaze placeholder
 */
public record MazeRuntimeConfig(
        int windowWidth,
        int windowHeight,
        int mazeCols,
        int mazeRows,
        float cellSize,
        float playerSpeed,
        boolean useRealMaze) {

    public static final MazeRuntimeConfig DEFAULT = new MazeRuntimeConfig(
        1024, 768, 16, 12, 48f, 168f, true);

    public MazeRuntimeConfig {
        if (windowWidth  <= 0) throw new IllegalArgumentException("windowWidth must be positive");
        if (windowHeight <= 0) throw new IllegalArgumentException("windowHeight must be positive");
        if (mazeCols     <= 0) throw new IllegalArgumentException("mazeCols must be positive");
        if (mazeRows     <= 0) throw new IllegalArgumentException("mazeRows must be positive");
        if (!Float.isFinite(cellSize)    || cellSize    <= 0f) throw new IllegalArgumentException("cellSize must be a positive finite value");
        if (!Float.isFinite(playerSpeed) || playerSpeed <= 0f) throw new IllegalArgumentException("playerSpeed must be a positive finite value");
    }
}


