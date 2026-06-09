package main.game.maze.libgdx.helper;

import com.badlogic.gdx.graphics.Texture;
import java.util.List;
import java.util.function.Function;
import main.game.maze.common.movement.WorldView;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.game.runtime.EnemyDirectorService;
import main.game.maze.game.session.GameSession;
import main.game.maze.libgdx.controller.GdxHudInteractionStateController;
import main.game.maze.libgdx.controller.GdxModeInputController;
import main.game.maze.libgdx.controller.GdxTerminalController;
import main.game.maze.libgdx.controller.GdxWinOverlayController;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.game.PlayerCombatStateService;
import main.game.maze.libgdx.lifecycle.GameSessionStartFlowCoordinator;
import main.game.maze.libgdx.lifecycle.GameSessionBootstrapper;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.libgdx.model.RuntimeVisualModelLoader;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.constants.StageConstants;

/**
 * Creates start-flow requests from controller collaborators to reduce controller orchestration code.
 */
public final class GdxGameStartFlowRequestFactory {

    private final MazeArena providedMaze;
    private final RuntimeVisualModelLoader runtimeModelLoader;
    private final GameSession session;
    private final GameWorldModel worldModel;
    private final GdxDebugOverlayState debugOverlayState;
    private final GdxHudInteractionStateController hudInteractionState;
    private final GdxTerminalController terminalController;
    private final GdxModeInputController modeInputController;
    private final EnemyDirectorService enemyDirectorService;
    private final GdxWinOverlayController winOverlayController;
    private final PlayerCombatStateService combatState;
    private final List<GdxEnemyRuntime> animatedEnemies;
    private final Function<Difficulty, MazeArena> arenaFactory;
    private final Function<Difficulty, Integer> baseScoreForDifficulty;
    private final Function<String, Texture> textureLoader;
    private final Function<GameSessionBootstrapper.BootstrapResult, WorldView> worldViewFactory;
    private final Runnable resetHintInfo;
    private final Runnable resetSpanningTreeInfo;
    private final Function<Float, Float> speedConverter;
    private final float playerSize;
    private final float goalSize;
    private final float javaFxTickRate;
    private final int maxEnemyTicksPerFrame;

    public GdxGameStartFlowRequestFactory(
            MazeArena providedMaze,
            RuntimeVisualModelLoader runtimeModelLoader,
            GameSession session,
            GameWorldModel worldModel,
            GdxDebugOverlayState debugOverlayState,
            GdxHudInteractionStateController hudInteractionState,
            GdxTerminalController terminalController,
            GdxModeInputController modeInputController,
            EnemyDirectorService enemyDirectorService,
            GdxWinOverlayController winOverlayController,
            PlayerCombatStateService combatState,
            List<GdxEnemyRuntime> animatedEnemies,
            Function<Difficulty, MazeArena> arenaFactory,
            Function<Difficulty, Integer> baseScoreForDifficulty,
            Function<String, Texture> textureLoader,
            Function<GameSessionBootstrapper.BootstrapResult, WorldView> worldViewFactory,
            Runnable resetHintInfo,
            Runnable resetSpanningTreeInfo,
            Function<Float, Float> speedConverter,
            float playerSize,
            float goalSize,
            float javaFxTickRate,
            int maxEnemyTicksPerFrame) {
        this.providedMaze = providedMaze;
        this.runtimeModelLoader = runtimeModelLoader;
        this.session = session;
        this.worldModel = worldModel;
        this.debugOverlayState = debugOverlayState;
        this.hudInteractionState = hudInteractionState;
        this.terminalController = terminalController;
        this.modeInputController = modeInputController;
        this.enemyDirectorService = enemyDirectorService;
        this.winOverlayController = winOverlayController;
        this.combatState = combatState;
        this.animatedEnemies = animatedEnemies;
        this.arenaFactory = arenaFactory;
        this.baseScoreForDifficulty = baseScoreForDifficulty;
        this.textureLoader = textureLoader;
        this.worldViewFactory = worldViewFactory;
        this.resetHintInfo = resetHintInfo;
        this.resetSpanningTreeInfo = resetSpanningTreeInfo;
        this.speedConverter = speedConverter;
        this.playerSize = playerSize;
        this.goalSize = goalSize;
        this.javaFxTickRate = javaFxTickRate;
        this.maxEnemyTicksPerFrame = maxEnemyTicksPerFrame;
    }

    public GameSessionStartFlowCoordinator.StartFlowRequest create(Difficulty selected) {
        return new GameSessionStartFlowCoordinator.StartFlowRequest(
                selected,
                providedMaze,
                runtimeModelLoader,
                StageConstants.PlayerCharacterSpeed,
                speedConverter,
                goalSize,
                playerSize,
                arenaFactory,
                baseScoreForDifficulty,
                session,
                worldModel,
                debugOverlayState,
                resetHintInfo,
                resetSpanningTreeInfo,
                hudInteractionState::reset,
                terminalController::reset,
                modeInputController::reset,
                enemyDirectorService::reset,
                () -> winOverlayController.reset(session),
                combatState,
                animatedEnemies,
                worldViewFactory,
                javaFxTickRate,
                maxEnemyTicksPerFrame,
                textureLoader);
    }
}
