package main.game.maze.libgdx.lifecycle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.common.movement.WorldView;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.game.PlayerCombatStateService;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.libgdx.model.RuntimeVisualModel;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.generators.SampleMaze;
import org.junit.jupiter.api.Test;

class GameSessionStartCoordinatorTest {

    @Test
    void applyBootstrapAssignsWorldModelAndCombatState() {
        var coordinator = new GameSessionStartCoordinator();
        var worldModel = new GameWorldModel();
        var combatState = new PlayerCombatStateService();
        var animatedEnemies = new ArrayList<GdxEnemyRuntime>();

        var maze = new SampleMaze(8, 6, 48f, 1L);
        var player = new PlayerState(maze.startX(), maze.startY(), 24f);
        var runtimeModel = new RuntimeVisualModel(
                "/player.png",
                "/player-dead.png",
                220f,
                24f,
                140,
                "/bg.png",
                "/wall.png",
                "/goal.png",
                120f,
                80f,
                48f,
                List.of());

        var bootstrapResult = new GameSessionBootstrapper.BootstrapResult(maze, runtimeModel, player, 300f, 48f);
        var startState = coordinator.applyBootstrap(new GameSessionStartCoordinator.StartRequest(
                bootstrapResult,
                worldModel,
                combatState,
                animatedEnemies,
                new NoCollisionWorldView(),
                30f,
                4));

        assertSame(maze, startState.maze());
        assertSame(runtimeModel, startState.runtimeModel());
        assertSame(player, startState.player());

        assertSame(maze, worldModel.maze());
        assertSame(runtimeModel, worldModel.runtimeModel());
        assertSame(player, worldModel.player());
        assertEquals(300f, worldModel.activePlayerSpeed(), 0.001f);
        assertEquals(48f, worldModel.activeGoalSize(), 0.001f);
        assertTrue(animatedEnemies.isEmpty());

        var frame = combatState.update(0f, player.x(), player.y(), player.halfSize(), List.of());
        assertEquals(1f, frame.hpRatio(), 0.001f);
    }

    private static final class NoCollisionWorldView implements WorldView {
        @Override
        public double playerX() {
            return 0;
        }

        @Override
        public double playerY() {
            return 0;
        }

        @Override
        public boolean wouldCollide(double centerX, double centerY, double size) {
            return false;
        }
    }
}
