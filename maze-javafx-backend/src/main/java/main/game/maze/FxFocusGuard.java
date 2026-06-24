package main.game.maze;

/**
 * Pure decision helper that keeps the gameplay key handler reliably focused.
 *
 * <p>JavaFX delivers key events only to the current focus owner (and its
 * ancestors). During play the {@code gameBoard} pane must retain focus so the
 * {@code onKeyPressed}/{@code onKeyReleased} handlers fire. Transient focus
 * changes (clicking a HUD button, a focused node being removed from the scene
 * which drops the focus owner to {@code null}) can otherwise leave the game
 * with no focus owner, after which "no keys have any effect".</p>
 *
 * <p>The logic is extracted here, free of any live JavaFX node, so the
 * re-assertion policy can be unit tested deterministically.</p>
 */
public final class FxFocusGuard {

    private FxFocusGuard() {
    }

    /**
     * Decides whether keyboard focus should be re-asserted onto the game board.
     *
     * @param focusOwner            the scene's current focus owner (may be {@code null})
     * @param gameBoard             the node that owns the gameplay key handlers
     * @param focusOwnerIsTextInput {@code true} when the focus owner is a text
     *                              input control (e.g. the in-game terminal),
     *                              in which case focus must be left alone so the
     *                              user can type
     * @return {@code true} when focus should be requested back onto the game board
     */
    public static boolean shouldReassertFocus(Object focusOwner, Object gameBoard, boolean focusOwnerIsTextInput) {
        if (gameBoard == null) {
            return false;
        }
        if (focusOwner == gameBoard) {
            return false;
        }
        if (focusOwnerIsTextInput) {
            return false;
        }
        return true;
    }
}
