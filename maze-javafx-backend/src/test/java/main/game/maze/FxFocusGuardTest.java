package main.game.maze;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies the per-frame focus re-assertion policy that prevents JavaFX player
 * input from going permanently dead after focus is lost (BUG: input hangs after
 * a while of moving and then no keys have any effect).
 */
class FxFocusGuardTest {

    private static final Object GAME_BOARD = new Object();

    @Test
    void doesNotReassertWhenGameBoardAlreadyHasFocus() {
        assertFalse(FxFocusGuard.shouldReassertFocus(GAME_BOARD, GAME_BOARD, false));
    }

    @Test
    void reassertsWhenFocusOwnerIsNull() {
        // Focus owner dropping to null is the classic "no keys have any effect" state.
        assertTrue(FxFocusGuard.shouldReassertFocus(null, GAME_BOARD, false));
    }

    @Test
    void reassertsWhenFocusOwnerIsAnotherNode() {
        Object someButton = new Object();
        assertTrue(FxFocusGuard.shouldReassertFocus(someButton, GAME_BOARD, false));
    }

    @Test
    void doesNotStealFocusFromTextInput() {
        Object terminalField = new Object();
        assertFalse(FxFocusGuard.shouldReassertFocus(terminalField, GAME_BOARD, true));
    }

    @Test
    void doesNothingWhenGameBoardMissing() {
        assertFalse(FxFocusGuard.shouldReassertFocus(null, null, false));
    }
}
