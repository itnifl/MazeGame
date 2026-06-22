package main.game.maze.libgdx.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.generators.RealMaze;
import main.game.maze.mazeworld.generators.SampleMaze;
import main.game.maze.mazeworld.generators.SpriteWallNudger;

/**
 * Shared arena, goal placement, and camera layout utilities for libGDX gameplay.
 */
public final class GdxGameLayoutSupport {

    private static final float CENTER_RATIO = 0.5f;
    private static final int GOAL_NUDGE_MAX_ITERATIONS = 400;

    private GdxGameLayoutSupport() {
    }

    public static void resizeWindowForDifficulty(Difficulty selected) {
        if (Gdx.graphics == null) {
            return;
        }
        int targetW = DifficultyBoardConfig.boardWidth(selected);
        int targetH = DifficultyBoardConfig.boardHeight(selected);
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

    public static MazeArena buildArenaForDifficulty(Difficulty selected, boolean useRealMaze, float cellSize, long seed) {
        int width = DifficultyBoardConfig.boardWidth(selected);
        int height = DifficultyBoardConfig.boardHeight(selected);
        if (useRealMaze) {
            return RealMaze.fresh(width, height);
        }
        int sampleCols = Math.max(8, Math.round(width / cellSize));
        int sampleRows = Math.max(6, Math.round(height / cellSize));
        return new SampleMaze(sampleCols, sampleRows, cellSize, seed);
    }

    public static void recenterGoalLikeJavaFx(MazeArena maze, GameWorldModel worldModel) {
        if (maze == null) {
            return;
        }
        worldModel.setActiveGoalX(maze.widthPx() * CENTER_RATIO);
        worldModel.setActiveGoalY(maze.heightPx() * CENTER_RATIO);
        nudgeGoalOffWalls(maze, worldModel, worldModel.activeGoalSize(), worldModel.activeGoalSize());
    }

    public static void updateCameraFollow(Viewport viewport, MazeArena maze, PlayerState player, OrthographicCamera camera) {
        if (viewport == null || maze == null || player == null || camera == null) {
            return;
        }
        float[] pos = computeCameraPosition(
                viewport.getWorldWidth(), viewport.getWorldHeight(),
                maze.widthPx(), maze.heightPx(),
                player.x(), player.y());
        camera.position.set(pos[0], pos[1], 0f);
    }

    /**
     * Pure-math camera position: centres the player in the viewport, clamped so
     * the world never scrolls past its own edges (F27). Package-private for tests.
     */
    static float[] computeCameraPosition(float viewW, float viewH,
            float mazeW, float mazeH, float playerX, float playerY) {
        float halfW = viewW * CENTER_RATIO;
        float halfH = viewH * CENTER_RATIO;
        float camX = mazeW <= viewW + 0.001f
                ? mazeW * CENTER_RATIO
                : clamp(playerX, halfW, mazeW - halfW);
        float camY = mazeH <= viewH + 0.001f
                ? halfH
                : clamp(playerY, halfH, mazeH - halfH);
        return new float[]{camX, camY};
    }

    private static void nudgeGoalOffWalls(MazeArena maze, GameWorldModel worldModel, float goalW, float goalH) {
        if (maze.walls() == null || maze.walls().isEmpty()) {
            return;
        }
        final float halfW = goalW * CENTER_RATIO;
        final float halfH = goalH * CENTER_RATIO;
        SpriteWallNudger.MovableAabb rect = new SpriteWallNudger.MovableAabb() {
            @Override
            public float minX() {
                return worldModel.activeGoalX() - halfW;
            }

            @Override
            public float minY() {
                return worldModel.activeGoalY() - halfH;
            }

            @Override
            public float maxX() {
                return worldModel.activeGoalX() + halfW;
            }

            @Override
            public float maxY() {
                return worldModel.activeGoalY() + halfH;
            }

            @Override
            public void offset(float dx, float dy) {
                worldModel.setActiveGoalX(worldModel.activeGoalX() + dx);
                worldModel.setActiveGoalY(worldModel.activeGoalY() + dy);
            }
        };
        SpriteWallNudger.nudgeOffWalls(rect, maze.walls(), GOAL_NUDGE_MAX_ITERATIONS);
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
}
