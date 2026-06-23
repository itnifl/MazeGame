package main.game.maze.libgdx.lifecycle;

import main.game.maze.difficulties.Difficulty;
import main.game.maze.game.score.PathHintBudget;
import main.game.maze.game.session.GameSession;
import main.game.maze.libgdx.helper.GdxDebugOverlayState;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.libgdx.model.RuntimeVisualModel;
import main.game.maze.libgdx.model.RuntimeVisualModelLoader;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;

/**
 * Encapsulates gameplay session bootstrap and common state resets.
 */
public final class GameSessionBootstrapper {

    public record BootstrapResult(MazeArena maze, RuntimeVisualModel runtimeModel, PlayerState player,
                                  float activePlayerSpeed, float activeGoalSize) {
    }

    public BootstrapResult bootstrap(BootstrapRequest request) {
        Difficulty selected = request.selectedDifficulty();
        MazeArena maze = request.providedMaze() != null
                ? request.providedMaze()
                : request.buildArena().apply(selected);

        RuntimeVisualModel runtimeModel = request.runtimeModelLoader().load(maze, selected);
        float baseSpeed = runtimeModel.playerSpeed() > 0f
                ? runtimeModel.playerSpeed()
                : request.defaultPlayerSpeed();
        float activePlayerSpeed = Math.max(1f, request.toJavaFxLikeSpeed().apply(baseSpeed));
        float activeGoalSize = runtimeModel.goalSize() > 0f ? runtimeModel.goalSize() : request.defaultGoalSize();

        PlayerState player = PlayerState.spawnAwayFromWalls(
                maze.startX(),
                maze.startY(),
                runtimeModel.playerSize() > 0f ? runtimeModel.playerSize() : request.fallbackPlayerSize(),
                maze);

        request.session().setBaseScore(request.baseScoreForDifficulty().apply(selected));
        request.session().resetMoveCount();
        request.session().setPausedFromGame(false);

        request.resetShowHint().run();
        request.resetShowSpanningTree().run();
        request.resetHudInteraction().run();
        request.terminalReset().run();
        request.modeInputReset().run();
        request.enemyDirectorReset().run();
        request.winOverlayReset().run();
        request.debugOverlayState().reset();

        request.worldModel().setPlayedWinSound(false);
        request.worldModel().setPlayedGameOverSound(false);
        request.worldModel().setDeathSequenceStarted(false);
        request.worldModel().setDeathDisplayRemainingSeconds(0f);
        request.worldModel().setPathPenaltyPoints(0f);
        request.worldModel().setPathHintBudget(PathHintBudget.forDifficulty(selected));
        request.worldModel().setCurrentHpRatio(1f);
        request.worldModel().setPlayerTintRed(1f);
        request.worldModel().setPlayerTintGreen(1f);
        request.worldModel().setPlayerTintBlue(1f);

        request.worldModel().activePathPoints().clear();
        request.worldModel().animatedEnemies().clear();
        request.worldModel().deadEnemies().clear();
        request.worldModel().dyingEnemies().clear();

        return new BootstrapResult(maze, runtimeModel, player, activePlayerSpeed, activeGoalSize);
    }

    public record BootstrapRequest(
            Difficulty selectedDifficulty,
            MazeArena providedMaze,
            RuntimeVisualModelLoader runtimeModelLoader,
            float defaultPlayerSpeed,
            java.util.function.Function<Float, Float> toJavaFxLikeSpeed,
            float defaultGoalSize,
            float fallbackPlayerSize,
            java.util.function.Function<Difficulty, MazeArena> buildArena,
            java.util.function.Function<Difficulty, Integer> baseScoreForDifficulty,
            GameSession session,
            GameWorldModel worldModel,
            GdxDebugOverlayState debugOverlayState,
            Runnable resetShowHint,
            Runnable resetShowSpanningTree,
            Runnable resetHudInteraction,
            Runnable terminalReset,
            Runnable modeInputReset,
            Runnable enemyDirectorReset,
            Runnable winOverlayReset) {
    }
}
