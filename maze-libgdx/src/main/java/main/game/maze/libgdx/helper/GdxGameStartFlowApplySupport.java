package main.game.maze.libgdx.helper;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.function.Consumer;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.game.session.GameMode;
import main.game.maze.game.session.GameSession;
import main.game.maze.libgdx.lifecycle.GameSessionStartFlowCoordinator;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.service.DifficultyPresentationSupport;

/**
 * Small helper for applying start-flow outputs without bloating the controller.
 */
public final class GdxGameStartFlowApplySupport {

    private GdxGameStartFlowApplySupport() {
    }

    public static StartState startState(GameSessionStartFlowCoordinator.StartFlowResult startFlow) {
        return new StartState(startFlow.startState().maze(), startFlow.startState().player());
    }

    public static AppliedStartFlow apply(
            GameSessionStartFlowCoordinator.StartFlowResult startFlow,
            Difficulty selected,
            Viewport currentViewport,
            OrthographicCamera camera,
            GameWorldModel worldModel,
            GameSession session,
            Consumer<String> flashStatus,
            Runnable switchToInGameMusic) {
        var startState = startState(startFlow);
        GdxGameLayoutSupport.resizeWindowForDifficulty(selected);
        Viewport viewport = ensureViewportInitialized(currentViewport, camera);
        var textures = runtimeTextures(startFlow.runtimeTextures());
        GdxGameLayoutSupport.recenterGoalLikeJavaFx(startState.maze(), worldModel);
        session.setMode(GameMode.PLAYING);
        switchToInGameMusic.run();
        flashStatus.accept(startedDifficultyText(selected));
        GdxGameLayoutSupport.updateCameraFollow(viewport, startState.maze(), startState.player(), camera);
        return new AppliedStartFlow(startState.maze(), startState.player(), viewport, textures);
    }

    public static Viewport ensureViewportInitialized(Viewport viewport, OrthographicCamera camera) {
        if (viewport == null) {
            return new ScreenViewport(camera);
        }
        return viewport;
    }

    public static RuntimeTexturesState runtimeTextures(main.game.maze.libgdx.helper.GdxRuntimeTextureSupport.RuntimeTextures textures) {
        return new RuntimeTexturesState(
                textures.playerTexture(),
                textures.playerDeathTexture(),
                textures.goalTexture(),
                textures.wallTexture(),
                textures.backgroundTexture());
    }

    public static String startedDifficultyText(Difficulty selected) {
        String label = selected != null ? DifficultyPresentationSupport.displayNameOr(selected, "Easy") : "Default";
        return "Started " + label + " difficulty";
    }

    public record StartState(MazeArena maze, PlayerState player) {
    }

    public record RuntimeTexturesState(
            Texture playerTexture,
            Texture playerDeathTexture,
            Texture goalTexture,
            Texture wallTexture,
            Texture backgroundTexture) {
    }

        public record AppliedStartFlow(
            MazeArena maze,
            PlayerState player,
            Viewport viewport,
            RuntimeTexturesState runtimeTextures) {
        }
}