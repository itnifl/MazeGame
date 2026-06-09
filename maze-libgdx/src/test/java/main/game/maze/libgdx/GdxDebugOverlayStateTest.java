package main.game.maze.libgdx;

import main.game.maze.libgdx.adapter.*;
import main.game.maze.libgdx.controller.*;
import main.game.maze.libgdx.helper.*;
import main.game.maze.libgdx.service.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GdxDebugOverlayStateTest {

    @Test
    void terminalActionsActivateExpectedOverlays() {
        GdxDebugOverlayState state = new GdxDebugOverlayState();

        state.applyTerminalAction(GdxTerminalCommandSupport.Action.SHOW_BEHAVIOUR_TYPE, 20f, 10f);
        assertTrue(state.showBehaviourType());
        assertFalse(state.showMovementType());
        assertEquals(0f, state.enemyPathSecondsRemaining());

        state.applyTerminalAction(GdxTerminalCommandSupport.Action.SHOW_MOVEMENT_TYPE, 20f, 10f);
        assertTrue(state.showMovementType());

        state.applyTerminalAction(GdxTerminalCommandSupport.Action.SHOW_ENEMY_PATH, 20f, 10f);
        assertEquals(10f, state.enemyPathSecondsRemaining());
    }

    @Test
    void tickExpiresOverlayFlagsOverTime() {
        GdxDebugOverlayState state = new GdxDebugOverlayState();
        state.applyTerminalAction(GdxTerminalCommandSupport.Action.SHOW_BEHAVIOUR_TYPE, 1f, 2f);
        state.applyTerminalAction(GdxTerminalCommandSupport.Action.SHOW_MOVEMENT_TYPE, 1f, 2f);
        state.applyTerminalAction(GdxTerminalCommandSupport.Action.SHOW_ENEMY_PATH, 1f, 2f);

        state.tick(0.4f);
        assertTrue(state.showBehaviourType());
        assertTrue(state.showMovementType());
        assertEquals(1.6f, state.enemyPathSecondsRemaining(), 0.0001f);

        state.tick(1f);
        assertFalse(state.showBehaviourType());
        assertFalse(state.showMovementType());
        assertEquals(0.6f, state.enemyPathSecondsRemaining(), 0.0001f);

        state.tick(1f);
        assertEquals(0f, state.enemyPathSecondsRemaining(), 0.0001f);
    }

    @Test
    void resetClearsAllOverlayState() {
        GdxDebugOverlayState state = new GdxDebugOverlayState();
        state.applyTerminalAction(GdxTerminalCommandSupport.Action.SHOW_BEHAVIOUR_TYPE, 20f, 10f);
        state.applyTerminalAction(GdxTerminalCommandSupport.Action.SHOW_MOVEMENT_TYPE, 20f, 10f);
        state.applyTerminalAction(GdxTerminalCommandSupport.Action.SHOW_ENEMY_PATH, 20f, 10f);

        state.reset();

        assertFalse(state.showBehaviourType());
        assertFalse(state.showMovementType());
        assertEquals(0f, state.enemyPathSecondsRemaining());
    }
}
