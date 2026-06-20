package main.game.maze.libgdx.controller.state;

/**
 * Coordinates update flow for PLAYING mode.
 */
public final class PlayingModeController {

    public interface PlayingModeBridge {
        boolean isTerminalActive();

        boolean isEscEdgePressed();

        void requestReturnToMenu();

        boolean isCombatDead();

        void handleGameMouseInput();

        void handleTerminalTypingInput();

        boolean routeGameplayInput(float dt);

        void tickEnemyAnimation(float dt);

        void advanceEnemies(float dt);

        void updateProjectiles(float dt);

        boolean hasPlayer();

        void updateCombat(float dt);

        void applyDeathSequence(float dt);

        void updateCameraFollow();

        boolean shouldTriggerWin();

        void triggerWin();
    }

    public void update(float dt, PlayingModeBridge bridge) {
        if (!bridge.isTerminalActive() && bridge.isEscEdgePressed()) {
            bridge.requestReturnToMenu();
            return;
        }

        if (!bridge.isCombatDead()) {
            bridge.handleGameMouseInput();

            if (bridge.isTerminalActive()) {
                bridge.handleTerminalTypingInput();
            } else if (bridge.routeGameplayInput(dt)) {
                return;
            }
        }

        bridge.tickEnemyAnimation(dt);
        bridge.advanceEnemies(dt);
        bridge.updateProjectiles(dt);

        if (!bridge.hasPlayer()) {
            return;
        }

        bridge.updateCombat(dt);
        bridge.applyDeathSequence(dt);
        bridge.updateCameraFollow();

        if (bridge.shouldTriggerWin()) {
            bridge.triggerWin();
        }
    }
}
