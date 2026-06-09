package main.game.maze.libgdx.helper;

import java.util.function.Consumer;
import main.game.maze.common.input.InputFrame;
import main.game.maze.common.input.KeyBindingRegistry;
import main.game.maze.game.status.StatusMessageBus;
import main.game.maze.libgdx.controller.GdxHudInteractionStateController;
import main.game.maze.libgdx.controller.GdxTerminalController;
import main.game.maze.libgdx.input.InputSnapshotReader;

/**
 * Frame-state update support for polling input and ticking transient controllers.
 */
public final class GdxGameFrameStateSupport {

    private GdxGameFrameStateSupport() {
    }

    public static InputFrame<Integer> updateFrameState(
            float dt,
            InputSnapshotReader inputSnapshotReader,
            KeyBindingRegistry<Integer> keyBindingRegistry,
            GdxHudInteractionStateController hudInteractionState,
            GdxTerminalController terminalController,
            GdxDebugOverlayState debugOverlayState,
            Consumer<String> statusMessage,
            StatusMessageBus statusMessageBus,
            float enemyLabelSeconds,
            float enemyPathOverlaySeconds) {
        InputFrame<Integer> currentInputFrame = inputSnapshotReader.read(keyBindingRegistry.trackedKeys());
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
