package main.game.maze.libgdx.helper;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import main.game.maze.common.input.InputFrame;
import main.game.maze.libgdx.controller.GdxHudInteractionStateController;
import main.game.maze.libgdx.controller.GdxTerminalController;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.libgdx.view.layout.HudLayout;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;

/**
 * Coordinates gameplay mouse input wiring using live controller state suppliers.
 */
public final class GdxGameMouseInteractionCoordinator {

    private final Supplier<HudLayout> hudLayoutSupplier;
    private final Supplier<Float> hudHeightSupplier;
    private final GdxHudInteractionStateController hudInteractionState;
    private final GdxTerminalController terminalController;
    private final Supplier<Viewport> viewportSupplier;
    private final Supplier<OrthographicCamera> cameraSupplier;
    private final Supplier<MazeArena> mazeSupplier;
    private final Supplier<PlayerState> playerSupplier;
    private final GameWorldModel worldModel;
    private final Consumer<String> flashStatus;
    private final BiConsumer<MazeArena, PlayerState> updateCameraFollow;

    public GdxGameMouseInteractionCoordinator(
            Supplier<HudLayout> hudLayoutSupplier,
            Supplier<Float> hudHeightSupplier,
            GdxHudInteractionStateController hudInteractionState,
            GdxTerminalController terminalController,
            Supplier<Viewport> viewportSupplier,
            Supplier<OrthographicCamera> cameraSupplier,
            Supplier<MazeArena> mazeSupplier,
            Supplier<PlayerState> playerSupplier,
            GameWorldModel worldModel,
            Consumer<String> flashStatus,
            BiConsumer<MazeArena, PlayerState> updateCameraFollow) {
        this.hudLayoutSupplier = hudLayoutSupplier;
        this.hudHeightSupplier = hudHeightSupplier;
        this.hudInteractionState = hudInteractionState;
        this.terminalController = terminalController;
        this.viewportSupplier = viewportSupplier;
        this.cameraSupplier = cameraSupplier;
        this.mazeSupplier = mazeSupplier;
        this.playerSupplier = playerSupplier;
        this.worldModel = worldModel;
        this.flashStatus = flashStatus;
        this.updateCameraFollow = updateCameraFollow;
    }

    public void handle(InputFrame<Integer> inputFrame) {
        GdxGameInteractionSupport.handleGameMouseInput(
                inputFrame,
                hudLayoutSupplier.get(),
                hudHeightSupplier.get(),
                hudInteractionState,
                terminalController,
                viewportSupplier.get(),
                cameraSupplier.get(),
                mazeSupplier.get(),
                playerSupplier.get(),
                worldModel,
                flashStatus,
                updateCameraFollow,
                () -> GdxGameInteractionSupport.openTerminalPrompt(terminalController, flashStatus),
                () -> GdxGameInteractionSupport.closeTerminalPrompt(terminalController, flashStatus));
    }
}
