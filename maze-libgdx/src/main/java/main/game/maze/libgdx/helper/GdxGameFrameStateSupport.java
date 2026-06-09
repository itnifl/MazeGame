package main.game.maze.libgdx.helper;

import java.util.function.Consumer;
import main.game.maze.game.status.StatusMessageBus;
import main.game.maze.libgdx.controller.GdxHudInteractionStateController;
import main.game.maze.libgdx.controller.GdxTerminalController;
import main.game.maze.libgdx.input.InputFrame;
import main.game.maze.libgdx.input.InputSnapshotReader;
import main.game.maze.libgdx.input.KeyBindingRegistry;

/**
 * Frame-state update support for polling input and ticking transient controllers.
 */
public final class GdxGameFrameStateSupport {

    private GdxGameFrameStateSupport() {
    }

    public static InputFrame updateFrameState(
            float dt,
            InputSnapshotReader inputSnapshotReader,
            KeyBindingRegistry keyBindingRegistry,
            GdxHudInteractionStateController hudInteractionState,
            GdxTerminalController terminalController,
            GdxDebugOverlayState debugOverlayState,
            Consumer<String> statusMessage,
            StatusMessageBus statusMessageBus,
            float enemyLabelSeconds,
            float enemyPathOverlaySeconds) {
        InputFrame currentInputFrame = inputSnapshotReader.read(keyBindingRegistry.trackedKeyCodes());
        hudInteractionState.tick(dt);

        String command = terminalController.consumePendingCommand();
        if (command != null) {
            GdxGameInteractionSupport.executeTerminalCommand(
                    command,
                    debugOverlayState,
                    statusMessage,
                    enemyLabelSeconds,
                    enemyPathOverlaySeconds);
        }

        debugOverlayState.tick(dt);
        statusMessageBus.tick(dt);
        return currentInputFrame;
    }
}
