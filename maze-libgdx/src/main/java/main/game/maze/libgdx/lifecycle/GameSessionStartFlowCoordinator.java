package main.game.maze.libgdx.lifecycle;

import com.badlogic.gdx.graphics.Texture;
import java.util.List;
import java.util.function.Function;
import main.game.maze.common.movement.WorldView;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.game.session.GameSession;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.game.PlayerCombatStateService;
import main.game.maze.libgdx.helper.GdxDebugOverlayState;
import main.game.maze.libgdx.helper.GdxRuntimeTextureSupport;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.libgdx.model.RuntimeVisualModelLoader;
import main.game.maze.mazeworld.generators.MazeArena;

/**
 * Coordinates full gameplay start flow from bootstrap through runtime textures.
 */
public final class GameSessionStartFlowCoordinator {

    private final GameSessionBootstrapper bootstrapper;
    private final GameSessionStartCoordinator startCoordinator;

    public GameSessionStartFlowCoordinator() {
        this(new GameSessionBootstrapper(), new GameSessionStartCoordinator());
    }

    GameSessionStartFlowCoordinator(GameSessionBootstrapper bootstrapper, GameSessionStartCoordinator startCoordinator) {
        this.bootstrapper = bootstrapper;
        this.startCoordinator = startCoordinator;
    }

    public record StartFlowRequest(
            Difficulty selectedDifficulty,
            MazeArena providedMaze,
            RuntimeVisualModelLoader runtimeModelLoader,
            float defaultPlayerSpeed,
            Function<Float, Float> toJavaFxLikeSpeed,
            float defaultGoalSize,
            float fallbackPlayerSize,
            Function<Difficulty, MazeArena> buildArena,
            Function<Difficulty, Integer> baseScoreForDifficulty,
            GameSession session,
            GameWorldModel worldModel,
            GdxDebugOverlayState debugOverlayState,
            Runnable resetShowHint,
            Runnable resetShowSpanningTree,
            Runnable resetHudInteraction,
            Runnable terminalReset,
            Runnable modeInputReset,
            Runnable enemyDirectorReset,
            Runnable winOverlayReset,
            PlayerCombatStateService combatState,
            List<GdxEnemyRuntime> animatedEnemies,
            Function<GameSessionBootstrapper.BootstrapResult, WorldView> spawnWorldFactory,
            float javaFxTickRate,
            int maxEnemyTicksPerFrame,
            Function<String, Texture> textureLoader) {
    }

    public record StartFlowResult(
            GameSessionStartCoordinator.StartState startState,
            GdxRuntimeTextureSupport.RuntimeTextures runtimeTextures) {
    }

    public StartFlowResult start(StartFlowRequest request) {
        GameSessionBootstrapper.BootstrapResult bootstrapResult = bootstrapper.bootstrap(
                new GameSessionBootstrapper.BootstrapRequest(
                        request.selectedDifficulty(),
                        request.providedMaze(),
                        request.runtimeModelLoader(),
                        request.defaultPlayerSpeed(),
                        request.toJavaFxLikeSpeed(),
                        request.defaultGoalSize(),
                        request.fallbackPlayerSize(),
                        request.buildArena(),
                        request.baseScoreForDifficulty(),
                        request.session(),
                        request.worldModel(),
                        request.debugOverlayState(),
                        request.resetShowHint(),
                        request.resetShowSpanningTree(),
                        request.resetHudInteraction(),
                        request.terminalReset(),
                        request.modeInputReset(),
                        request.enemyDirectorReset(),
                        request.winOverlayReset()));

        var startState = startCoordinator.applyBootstrap(
                new GameSessionStartCoordinator.StartRequest(
                        bootstrapResult,
                        request.worldModel(),
                        request.combatState(),
                        request.animatedEnemies(),
                        request.spawnWorldFactory().apply(bootstrapResult),
                        request.javaFxTickRate(),
                        request.maxEnemyTicksPerFrame()));

        var runtimeTextures = GdxRuntimeTextureSupport.load(startState.runtimeModel(), request.textureLoader());
        return new StartFlowResult(startState, runtimeTextures);
    }
}