package main.game.maze.libgdx.lifecycle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.game.session.GameSession;
import main.game.maze.libgdx.helper.GdxDebugOverlayState;
import main.game.maze.libgdx.lifecycle.GameSessionBootstrapper.BootstrapRequest;
import main.game.maze.libgdx.lifecycle.GameSessionBootstrapper.BootstrapResult;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.libgdx.model.RuntimeVisualModelLoader;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.WallSegment;
import main.game.maze.service.DifficultyService;
import org.junit.jupiter.api.Test;

/**
 * Verifies SR-39: session start/reset is encapsulated in a bootstrapper that
 * computes runtime state and resets all collaborators. Uses the production
 * {@link RuntimeVisualModelLoader} (which falls back to defaults when XMI is
 * unavailable) with a provided maze, so no GL or arena construction is needed.
 */
class GameSessionBootstrapperTest {

    private static MazeArena providedMaze() {
        return new MazeArena() {
            @Override public List<WallSegment> walls() { return List.of(); }
            @Override public float widthPx() { return 320f; }
            @Override public float heightPx() { return 320f; }
            @Override public float startX() { return 16f; }
            @Override public float startY() { return 16f; }
            @Override public float goalX() { return 280f; }
            @Override public float goalY() { return 280f; }
        };
    }

    @Test
    void bootstrapComputesRuntimeStateResetsSessionAndCollaborators() {
        Difficulty difficulty = new DifficultyService().getCurrent();
        MazeArena maze = providedMaze();
        GameSession session = new GameSession();
        GameWorldModel worldModel = new GameWorldModel();
        GdxDebugOverlayState debugOverlayState = new GdxDebugOverlayState();

        // Dirty the session and world model so resets are observable.
        session.incrementMoveCount();
        session.setPausedFromGame(true);
        worldModel.setPlayedWinSound(true);
        worldModel.setPlayedGameOverSound(true);
        worldModel.setDeathSequenceStarted(true);
        worldModel.setDeathDisplayRemainingSeconds(5f);
        worldModel.setPathPenaltyPoints(42f);
        worldModel.setCurrentHpRatio(0.25f);
        worldModel.setPlayerTintRed(0.1f);
        worldModel.setPlayerTintGreen(0.2f);
        worldModel.setPlayerTintBlue(0.3f);
        worldModel.activePathPoints().add(new main.game.maze.mazeworld.Point2D(1f, 1f));

        AtomicInteger resetShowHint = new AtomicInteger();
        AtomicInteger resetShowSpanningTree = new AtomicInteger();
        AtomicInteger resetHudInteraction = new AtomicInteger();
        AtomicInteger terminalReset = new AtomicInteger();
        AtomicInteger modeInputReset = new AtomicInteger();
        AtomicInteger enemyDirectorReset = new AtomicInteger();
        AtomicInteger winOverlayReset = new AtomicInteger();

        Function<Difficulty, MazeArena> buildArena = d -> {
            throw new AssertionError("buildArena must not be called when a maze is provided");
        };

        BootstrapRequest request = new BootstrapRequest(
                difficulty,
                maze,
                new RuntimeVisualModelLoader(),
                3f,
                speed -> speed,
                24f,
                16f,
                buildArena,
                d -> 1234,
                session,
                worldModel,
                debugOverlayState,
                resetShowHint::incrementAndGet,
                resetShowSpanningTree::incrementAndGet,
                resetHudInteraction::incrementAndGet,
                terminalReset::incrementAndGet,
                modeInputReset::incrementAndGet,
                enemyDirectorReset::incrementAndGet,
                winOverlayReset::incrementAndGet);

        BootstrapResult result = new GameSessionBootstrapper().bootstrap(request);

        // Result content
        assertNotNull(result);
        assertSame(maze, result.maze(), "provided maze should be reused, not rebuilt");
        assertNotNull(result.runtimeModel());
        assertNotNull(result.player());
        assertTrue(result.activePlayerSpeed() >= 1f, "active speed must be clamped to at least 1");
        assertTrue(result.activeGoalSize() > 0f, "active goal size must be positive");

        // Session reset + base score wiring
        assertEquals(1234, session.baseScore());
        assertEquals(0, session.moveCount());
        assertFalse(session.pausedFromGame());

        // World model reset
        assertFalse(worldModel.playedWinSound());
        assertFalse(worldModel.playedGameOverSound());
        assertFalse(worldModel.deathSequenceStarted());
        assertEquals(0f, worldModel.deathDisplayRemainingSeconds());
        assertEquals(0f, worldModel.pathPenaltyPoints());
        assertEquals(1f, worldModel.currentHpRatio());
        assertEquals(1f, worldModel.playerTintRed());
        assertEquals(1f, worldModel.playerTintGreen());
        assertEquals(1f, worldModel.playerTintBlue());
        assertNotNull(worldModel.pathHintBudget());
        assertTrue(worldModel.activePathPoints().isEmpty());
        assertTrue(worldModel.animatedEnemies().isEmpty());

        // Collaborator resets each invoked exactly once
        assertEquals(1, resetShowHint.get());
        assertEquals(1, resetShowSpanningTree.get());
        assertEquals(1, resetHudInteraction.get());
        assertEquals(1, terminalReset.get());
        assertEquals(1, modeInputReset.get());
        assertEquals(1, enemyDirectorReset.get());
        assertEquals(1, winOverlayReset.get());
    }
}
