package main.game.maze.libgdx.lifecycle;

import java.util.List;
import main.game.maze.common.movement.WorldView;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.game.PlayerCombatStateService;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.libgdx.model.RuntimeVisualModel;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;

/**
 * Applies bootstrap output to gameplay state and materializes enemy runtimes.
 */
public final class GameSessionStartCoordinator {

    public record StartState(MazeArena maze, RuntimeVisualModel runtimeModel, PlayerState player) {
    }

    public record StartRequest(
            GameSessionBootstrapper.BootstrapResult bootstrapResult,
            GameWorldModel worldModel,
            PlayerCombatStateService combatState,
            List<GdxEnemyRuntime> animatedEnemies,
            WorldView spawnWorld,
            float javaFxTickRate,
            int maxEnemyTicksPerFrame) {
    }

    public StartState applyBootstrap(StartRequest request) {
        MazeArena maze = request.bootstrapResult().maze();
        RuntimeVisualModel runtimeModel = request.bootstrapResult().runtimeModel();
        PlayerState player = request.bootstrapResult().player();

        request.worldModel().setActivePlayerSpeed(request.bootstrapResult().activePlayerSpeed());
        request.worldModel().setActiveGoalSize(request.bootstrapResult().activeGoalSize());
        request.worldModel().setMaze(maze);
        request.worldModel().setRuntimeModel(runtimeModel);
        request.worldModel().setPlayer(player);

        request.combatState().reset(runtimeModel.playerMaxHitPoints());
        request.combatState().setMaze(maze);

        request.animatedEnemies().clear();
        int idx = 0;
        for (EnemySpawn enemy : runtimeModel.enemies()) {
            request.animatedEnemies().add(GdxEnemyRuntime.fromSpawn(
                    enemy,
                    idx++,
                    request.spawnWorld(),
                    request.javaFxTickRate(),
                    request.maxEnemyTicksPerFrame()));
        }

        return new StartState(maze, runtimeModel, player);
    }
}