package main.game.maze.libgdx.render;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.List;
import main.game.maze.game.session.GameMode;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.generators.WallSegment;
import org.junit.jupiter.api.Test;

/**
 * Verifies SR-37: the render pipeline's pure decision logic. The {@code render(...)}
 * method itself requires a GL context, so the world-availability and high-scores
 * overlay routing decisions are extracted into pure static helpers that are
 * exercised here headlessly.
 */
class GdxGameRenderPipelineTest {

    private static MazeArena simpleMaze() {
        return new MazeArena() {
            @Override public List<WallSegment> walls() { return List.of(); }
            @Override public float widthPx() { return 240f; }
            @Override public float heightPx() { return 240f; }
            @Override public float startX() { return 8f; }
            @Override public float startY() { return 8f; }
            @Override public float goalX() { return 200f; }
            @Override public float goalY() { return 200f; }
        };
    }

    @Test
    void worldIsRenderableOnlyWhenMazePlayerAndViewportAllPresent() {
        MazeArena maze = simpleMaze();
        PlayerState player = PlayerState.spawnAwayFromWalls(8f, 8f, 16f, maze);
        Viewport viewport = new ScreenViewport();

        assertTrue(GdxGameRenderPipeline.hasRenderableWorld(maze, player, viewport));
        assertFalse(GdxGameRenderPipeline.hasRenderableWorld(null, player, viewport));
        assertFalse(GdxGameRenderPipeline.hasRenderableWorld(maze, null, viewport));
        assertFalse(GdxGameRenderPipeline.hasRenderableWorld(maze, player, null));
    }

    @Test
    void highScoresOnlyOverlayWhenInHighScoresModeWithoutWorld() {
        assertTrue(GdxGameRenderPipeline.isHighScoresOnlyOverlay(GameMode.HIGH_SCORES, false));
    }

    @Test
    void notHighScoresOnlyOverlayWhenWorldIsAvailable() {
        assertFalse(GdxGameRenderPipeline.isHighScoresOnlyOverlay(GameMode.HIGH_SCORES, true));
    }

    @Test
    void notHighScoresOnlyOverlayInOtherModes() {
        assertFalse(GdxGameRenderPipeline.isHighScoresOnlyOverlay(GameMode.PLAYING, false));
        assertFalse(GdxGameRenderPipeline.isHighScoresOnlyOverlay(GameMode.WON, false));
        assertFalse(GdxGameRenderPipeline.isHighScoresOnlyOverlay(GameMode.GAME_OVER, false));
        assertFalse(GdxGameRenderPipeline.isHighScoresOnlyOverlay(GameMode.START_MENU, false));
    }
}
