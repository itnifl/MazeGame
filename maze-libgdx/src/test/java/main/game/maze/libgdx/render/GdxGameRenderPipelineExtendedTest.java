package main.game.maze.libgdx.render;

import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import main.game.maze.game.session.GameMode;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.generators.WallSegment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Extended headless tests for {@link GdxGameRenderPipeline}'s pure static helpers.
 * GL-context operations ({@code render()}) are excluded — they require a live
 * libGDX application and cannot run headlessly in CI.
 */
class GdxGameRenderPipelineExtendedTest {

    private static MazeArena simpleMaze() {
        return new MazeArena() {
            @Override public List<WallSegment> walls() { return List.of(); }
            @Override public float widthPx()  { return 240f; }
            @Override public float heightPx() { return 240f; }
            @Override public float startX()   { return 8f; }
            @Override public float startY()   { return 8f; }
            @Override public float goalX()    { return 200f; }
            @Override public float goalY()    { return 200f; }
        };
    }

    // hasRenderableWorld — all 7 null combinations return false; all-present returns true.

    @Test
    void hasRenderableWorld_allPresent_returnsTrue() {
        MazeArena maze = simpleMaze();
        PlayerState player = PlayerState.spawnAwayFromWalls(8f, 8f, 16f, maze);
        Viewport viewport = new ScreenViewport();
        assertTrue(GdxGameRenderPipeline.hasRenderableWorld(maze, player, viewport));
    }

    @Test
    void hasRenderableWorld_nullMaze_returnsFalse() {
        MazeArena maze = simpleMaze();
        PlayerState player = PlayerState.spawnAwayFromWalls(8f, 8f, 16f, maze);
        assertFalse(GdxGameRenderPipeline.hasRenderableWorld(null, player, new ScreenViewport()));
    }

    @Test
    void hasRenderableWorld_nullPlayer_returnsFalse() {
        assertFalse(GdxGameRenderPipeline.hasRenderableWorld(simpleMaze(), null, new ScreenViewport()));
    }

    @Test
    void hasRenderableWorld_nullViewport_returnsFalse() {
        MazeArena maze = simpleMaze();
        PlayerState player = PlayerState.spawnAwayFromWalls(8f, 8f, 16f, maze);
        assertFalse(GdxGameRenderPipeline.hasRenderableWorld(maze, player, null));
    }

    @Test
    void hasRenderableWorld_allNull_returnsFalse() {
        assertFalse(GdxGameRenderPipeline.hasRenderableWorld(null, null, null));
    }

    // isHighScoresOnlyOverlay — only HIGH_SCORES + no world = true; every other combo = false.

    @Test
    void isHighScoresOnlyOverlay_highScoresModeWithoutWorld_returnsTrue() {
        assertTrue(GdxGameRenderPipeline.isHighScoresOnlyOverlay(GameMode.HIGH_SCORES, false));
    }

    @Test
    void isHighScoresOnlyOverlay_highScoresModeWithWorld_returnsFalse() {
        assertFalse(GdxGameRenderPipeline.isHighScoresOnlyOverlay(GameMode.HIGH_SCORES, true));
    }

    @ParameterizedTest
    @EnumSource(value = GameMode.class, names = {"HIGH_SCORES"}, mode = EnumSource.Mode.EXCLUDE)
    void isHighScoresOnlyOverlay_nonHighScoresMode_alwaysReturnsFalse(GameMode mode) {
        assertFalse(GdxGameRenderPipeline.isHighScoresOnlyOverlay(mode, false),
                "Expected false for mode " + mode + " without world");
        assertFalse(GdxGameRenderPipeline.isHighScoresOnlyOverlay(mode, true),
                "Expected false for mode " + mode + " with world");
    }
}
