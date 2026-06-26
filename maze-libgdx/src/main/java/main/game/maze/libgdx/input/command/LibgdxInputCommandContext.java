package main.game.maze.libgdx.input.command;

import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import main.game.maze.common.input.command.GameCommandContext;

/**
 * Functional adapter for libGDX command handling with per-frame lifecycle.
 */
public final class LibgdxInputCommandContext implements GameCommandContext {

    private final BooleanSupplier terminalActive;
    private final Runnable requestReturnToMenu;
    private final Runnable openTerminalPrompt;
    private final Runnable openHighScores;
    private final Runnable toggleSpanningTree;
    private final BiConsumer<Boolean, Float> applyPathHintHeld;
    private final Runnable triggerPlayerFlameAttack;
    private final Runnable applyMovementFromFrame;

    private float dt;
    private boolean stopRequested;

    public LibgdxInputCommandContext(
            BooleanSupplier terminalActive,
            Runnable requestReturnToMenu,
            Runnable openTerminalPrompt,
            Runnable openHighScores,
            Runnable toggleSpanningTree,
            BiConsumer<Boolean, Float> applyPathHintHeld,
            Runnable triggerPlayerFlameAttack,
            Runnable applyMovementFromFrame) {
        this.terminalActive = terminalActive;
        this.requestReturnToMenu = requestReturnToMenu;
        this.openTerminalPrompt = openTerminalPrompt;
        this.openHighScores = openHighScores;
        this.toggleSpanningTree = toggleSpanningTree;
        this.applyPathHintHeld = applyPathHintHeld;
        this.triggerPlayerFlameAttack = triggerPlayerFlameAttack;
        this.applyMovementFromFrame = applyMovementFromFrame;
    }

    public void prepare(float frameDt) {
        dt = frameDt;
        stopRequested = false;
    }

    @Override
    public boolean terminalActive() {
        return terminalActive.getAsBoolean();
    }

    @Override
    public void requestReturnToMenu() {
        requestReturnToMenu.run();
    }

    @Override
    public void openTerminalPrompt() {
        openTerminalPrompt.run();
    }

    @Override
    public void openHighScores() {
        openHighScores.run();
    }

    @Override
    public void toggleSpanningTree() {
        toggleSpanningTree.run();
    }

    @Override
    public void applyPathHintHeld(boolean held) {
        applyPathHintHeld.accept(held, dt);
    }

    @Override
    public void triggerPlayerFlameAttack() {
        triggerPlayerFlameAttack.run();
    }

    @Override
    public void applyMovementFromFrame() {
        applyMovementFromFrame.run();
    }

    @Override
    public void requestStop() {
        stopRequested = true;
    }

    @Override
    public boolean stopRequested() {
        return stopRequested;
    }
}
