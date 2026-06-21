package main.game.maze.libgdx.helper;

import com.badlogic.gdx.Input;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import main.game.maze.common.input.InputFrame;
import main.game.maze.libgdx.controller.GdxTerminalController;
import main.game.maze.libgdx.controller.state.LibgdxPlayingBridge;
import main.game.maze.libgdx.game.PlayerCombatStateService;

/**
 * Creates the playing-mode bridge wiring so the controller constructor stays compact.
 */
public final class GdxGamePlayingBridgeFactory {

    private GdxGamePlayingBridgeFactory() {
    }

    public static LibgdxPlayingBridge create(
            GdxTerminalController terminalController,
            Supplier<InputFrame<Integer>> inputFrameSupplier,
            Runnable returnToMenu,
            PlayerCombatStateService combatState,
            Consumer<InputFrame<Integer>> handleMouseInput,
            Consumer<String> flashStatus,
            Function<Float, Boolean> routeGameplayInput,
            Consumer<Float> advanceAnimationClock,
            Consumer<Float> advanceEnemies,
            BooleanSupplier hasPlayer,
            Consumer<Float> updateCombat,
            Consumer<Float> applyDeathSequence,
            Runnable updateCameraFollow,
            BooleanSupplier shouldTriggerWin,
            Runnable triggerWin) {
        return new LibgdxPlayingBridge(
                terminalController::isActive,
                () -> inputFrameSupplier.get().isEdge(Input.Keys.ESCAPE),
                returnToMenu,
                combatState::isDead,
                () -> handleMouseInput.accept(inputFrameSupplier.get()),
                () -> GdxGameInteractionSupport.handleTerminalTypingInput(terminalController, flashStatus),
                routeGameplayInput,
                advanceAnimationClock,
                advanceEnemies,
                hasPlayer,
                updateCombat,
                applyDeathSequence,
                updateCameraFollow,
                shouldTriggerWin,
                triggerWin);
    }
}
