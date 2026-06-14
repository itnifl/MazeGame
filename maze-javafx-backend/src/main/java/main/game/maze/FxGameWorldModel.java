package main.game.maze;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Mutable container for the JavaFX gameplay scoring and path-hint state that was
 * previously embedded directly in {@link GameController}.
 *
 * <p>This is the first increment of the GameController MVC decomposition (see
 * {@code docs/plans/javafx-gamecontroller-mvc-command-refactor.md}, Phase 1). It
 * holds pure data only: no FXML nodes, services, timers, or threads. Keeping this
 * state in a dedicated model improves cohesion and testability while preserving
 * the exact behaviour of the controller.</p>
 *
 * <p>Field names intentionally mirror the original controller fields so that the
 * reflection-based unit tests continue to resolve them through the controller's
 * {@code model} reference.</p>
 */
public final class FxGameWorldModel {

    /** Number of player moves performed in the current game (shared with score actions). */
    private final AtomicInteger playerMoveCount = new AtomicInteger(0);

    /** Total nanoseconds of path-hint budget consumed this game. */
    private long pathHintTotalUsedNanos = 0L;
    /** Nanos when the P key was most recently pressed (0 if not held). */
    private long pathHintPressStartNanos = 0L;
    /** True while P is physically held down and a budget press is in progress. */
    private boolean pathHintKeyDown = false;

    /** True while the player navigation path overlay is visible. */
    private boolean isRouteHintVisible = false;
    /** Nanos of the most recent route-hint penalty tick (0 if not started). */
    private long lastRouteHintPenaltyNanos = 0L;
    /** Fractional penalty accumulated but not yet applied as whole points. */
    private double routeHintPenaltyAccumulator = 0.0;
    /** Whole penalty points applied for keeping the route hint visible. */
    private int routeHintPenaltyPoints = 0;

    /** True while the enemy navigation path overlay is visible. */
    private boolean enemyPathOverlayVisible = false;
    /** Nanos at which the enemy path overlay should auto-hide. */
    private long enemyPathOverlayHideAtNanos = 0L;

    AtomicInteger playerMoveCount() {
        return playerMoveCount;
    }

    public long pathHintTotalUsedNanos() {
        return pathHintTotalUsedNanos;
    }

    public void setPathHintTotalUsedNanos(long value) {
        this.pathHintTotalUsedNanos = value;
    }

    public long pathHintPressStartNanos() {
        return pathHintPressStartNanos;
    }

    public void setPathHintPressStartNanos(long value) {
        this.pathHintPressStartNanos = value;
    }

    public boolean pathHintKeyDown() {
        return pathHintKeyDown;
    }

    public void setPathHintKeyDown(boolean value) {
        this.pathHintKeyDown = value;
    }

    public boolean isRouteHintVisible() {
        return isRouteHintVisible;
    }

    public void setRouteHintVisible(boolean value) {
        this.isRouteHintVisible = value;
    }

    public long lastRouteHintPenaltyNanos() {
        return lastRouteHintPenaltyNanos;
    }

    public void setLastRouteHintPenaltyNanos(long value) {
        this.lastRouteHintPenaltyNanos = value;
    }

    public double routeHintPenaltyAccumulator() {
        return routeHintPenaltyAccumulator;
    }

    public void setRouteHintPenaltyAccumulator(double value) {
        this.routeHintPenaltyAccumulator = value;
    }

    public int routeHintPenaltyPoints() {
        return routeHintPenaltyPoints;
    }

    public void setRouteHintPenaltyPoints(int value) {
        this.routeHintPenaltyPoints = value;
    }

    public boolean enemyPathOverlayVisible() {
        return enemyPathOverlayVisible;
    }

    public void setEnemyPathOverlayVisible(boolean value) {
        this.enemyPathOverlayVisible = value;
    }

    public long enemyPathOverlayHideAtNanos() {
        return enemyPathOverlayHideAtNanos;
    }

    public void setEnemyPathOverlayHideAtNanos(long value) {
        this.enemyPathOverlayHideAtNanos = value;
    }

    /**
     * Atomically starts a new path-hint press session.
     * Sets {@code isRouteHintVisible = true} and, if the key was not already flagged
     * as held, initialises the press-start timestamp and penalty reference time.
     * Idempotent: calling again while the key is already down is a no-op on the timer.
     */
    public void beginPathHint() {
        isRouteHintVisible = true;
        if (!pathHintKeyDown) {
            long now = System.nanoTime();
            pathHintKeyDown           = true;
            pathHintPressStartNanos   = now;
            lastRouteHintPenaltyNanos = now;
        }
    }

    /**
     * Atomically ends the current path-hint press session.
     * Accumulates held nanoseconds into {@code pathHintTotalUsedNanos} (capped at
     * {@code budgetNanos}), clears {@code pathHintKeyDown}, and hides the overlay.
     *
     * @param budgetNanos the maximum total nanoseconds allowed for the current difficulty
     */
    public void endPathHint(long budgetNanos) {
        if (pathHintKeyDown) {
            long heldNanos = System.nanoTime() - pathHintPressStartNanos;
            pathHintTotalUsedNanos = Math.min(pathHintTotalUsedNanos + heldNanos, budgetNanos);
            pathHintKeyDown = false;
        }
        isRouteHintVisible = false;
    }

    /**
     * Resets all per-game scoring and path-hint state to its initial values.
     * Mirrors the inline reset previously performed in {@code setupGame()}.
     * The {@link #playerMoveCount} is intentionally left untouched here because
     * the controller manages its lifecycle alongside the score actions.
     */
    void resetScoringState() {
        routeHintPenaltyPoints = 0;
        routeHintPenaltyAccumulator = 0.0;
        isRouteHintVisible = false;
        lastRouteHintPenaltyNanos = 0L;
        pathHintTotalUsedNanos = 0L;
        pathHintKeyDown = false;
    }
}
