package main.game.maze.libgdx.controller.state;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/**
 * Functional adapter from controller collaborators into {@link PlayingModeController.PlayingModeBridge}.
 */
public final class LibgdxPlayingBridge implements PlayingModeController.PlayingModeBridge {

    private final BooleanSupplier terminalActive;
    private final BooleanSupplier escEdgePressed;
    private final Runnable requestReturnToMenu;
    private final BooleanSupplier combatDead;
    private final Runnable handleGameMouseInput;
    private final Runnable handleTerminalTypingInput;
    private final java.util.function.Function<Float, Boolean> routeGameplayInput;
    private final Consumer<Float> tickEnemyAnimation;
    private final Consumer<Float> advanceEnemies;
    private final BooleanSupplier hasPlayer;
    private final Consumer<Float> updateCombat;
    private final Consumer<Float> applyDeathSequence;
    private final Runnable updateCameraFollow;
    private final BooleanSupplier shouldTriggerWin;
    private final Runnable triggerWin;

    public LibgdxPlayingBridge(
            BooleanSupplier terminalActive,
            BooleanSupplier escEdgePressed,
            Runnable requestReturnToMenu,
            BooleanSupplier combatDead,
            Runnable handleGameMouseInput,
            Runnable handleTerminalTypingInput,
            java.util.function.Function<Float, Boolean> routeGameplayInput,
            Consumer<Float> tickEnemyAnimation,
            Consumer<Float> advanceEnemies,
            BooleanSupplier hasPlayer,
            Consumer<Float> updateCombat,
            Consumer<Float> applyDeathSequence,
            Runnable updateCameraFollow,
            BooleanSupplier shouldTriggerWin,
            Runnable triggerWin) {
        this.terminalActive = terminalActive;
        this.escEdgePressed = escEdgePressed;
        this.requestReturnToMenu = requestReturnToMenu;
        this.combatDead = combatDead;
        this.handleGameMouseInput = handleGameMouseInput;
        this.handleTerminalTypingInput = handleTerminalTypingInput;
        this.routeGameplayInput = routeGameplayInput;
        this.tickEnemyAnimation = tickEnemyAnimation;
        this.advanceEnemies = advanceEnemies;
        this.hasPlayer = hasPlayer;
        this.updateCombat = updateCombat;
        this.applyDeathSequence = applyDeathSequence;
        this.updateCameraFollow = updateCameraFollow;
        this.shouldTriggerWin = shouldTriggerWin;
        this.triggerWin = triggerWin;
    }

    @Override
    public boolean isTerminalActive() {
        return terminalActive.getAsBoolean();
    }

    @Override
    public boolean isEscEdgePressed() {
        return escEdgePressed.getAsBoolean();
    }

    @Override
    public void requestReturnToMenu() {
        requestReturnToMenu.run();
    }

    @Override
    public boolean isCombatDead() {
        return combatDead.getAsBoolean();
    }

    @Override
    public void handleGameMouseInput() {
        handleGameMouseInput.run();
    }

    @Override
    public void handleTerminalTypingInput() {
        handleTerminalTypingInput.run();
    }

    @Override
    public boolean routeGameplayInput(float dt) {
        return routeGameplayInput.apply(dt);
    }

    @Override
    public void tickEnemyAnimation(float dt) {
        tickEnemyAnimation.accept(dt);
    }

    @Override
    public void advanceEnemies(float dt) {
        advanceEnemies.accept(dt);
    }

    @Override
    public boolean hasPlayer() {
        return hasPlayer.getAsBoolean();
    }

    @Override
    public void updateCombat(float dt) {
        updateCombat.accept(dt);
    }

    @Override
    public void applyDeathSequence(float dt) {
        applyDeathSequence.accept(dt);
    }

    @Override
    public void updateCameraFollow() {
        updateCameraFollow.run();
    }

    @Override
    public boolean shouldTriggerWin() {
        return shouldTriggerWin.getAsBoolean();
    }

    @Override
    public void triggerWin() {
        triggerWin.run();
    }
}
