package main.game.maze.libgdx.controller.state;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PlayingModeControllerTest {

    @Test
    void escEdgeRequestsReturnBeforeFurtherProcessing() {
        PlayingModeController controller = new PlayingModeController();
        FakeBridge bridge = new FakeBridge();
        bridge.escEdgePressed = true;

        controller.update(0.016f, bridge);

        assertEquals(1, bridge.returnToMenuCalls);
        assertEquals(0, bridge.updateCombatCalls);
    }

    @Test
    void nonTerminalFlowRoutesGameplayAndCombat() {
        PlayingModeController controller = new PlayingModeController();
        FakeBridge bridge = new FakeBridge();

        controller.update(0.016f, bridge);

        assertEquals(1, bridge.routeGameplayInputCalls);
        assertEquals(1, bridge.updateCombatCalls);
        assertEquals(1, bridge.updateCameraCalls);
    }

    private static final class FakeBridge implements PlayingModeController.PlayingModeBridge {
        boolean terminalActive;
        boolean escEdgePressed;
        boolean combatDead;
        boolean hasPlayer = true;
        boolean shouldTriggerWin;
        int returnToMenuCalls;
        int routeGameplayInputCalls;
        int updateCombatCalls;
        int updateCameraCalls;

        @Override
        public boolean isTerminalActive() {
            return terminalActive;
        }

        @Override
        public boolean isEscEdgePressed() {
            return escEdgePressed;
        }

        @Override
        public void requestReturnToMenu() {
            returnToMenuCalls++;
        }

        @Override
        public boolean isCombatDead() {
            return combatDead;
        }

        @Override
        public void handleGameMouseInput() {
        }

        @Override
        public void handleTerminalTypingInput() {
        }

        @Override
        public boolean routeGameplayInput(float dt) {
            routeGameplayInputCalls++;
            return false;
        }

        @Override
        public void tickEnemyAnimation(float dt) {
        }

        @Override
        public void advanceEnemies(float dt) {
        }

        @Override
        public boolean hasPlayer() {
            return hasPlayer;
        }

        @Override
        public void updateCombat(float dt) {
            updateCombatCalls++;
        }

        @Override
        public void applyDeathSequence(float dt) {
        }

        @Override
        public void updateCameraFollow() {
            updateCameraCalls++;
        }

        @Override
        public boolean shouldTriggerWin() {
            return shouldTriggerWin;
        }

        @Override
        public void triggerWin() {
        }
    }
}
