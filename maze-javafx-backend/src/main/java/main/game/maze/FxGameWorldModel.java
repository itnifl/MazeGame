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
final class FxGameWorldModel {

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

    long pathHintTotalUsedNanos() {
        return pathHintTotalUsedNanos;
    }

    void setPathHintTotalUsedNanos(long value) {
        this.pathHintTotalUsedNanos = value;
    }

    long pathHintPressStartNanos() {
        return pathHintPressStartNanos;
    }

    void setPathHintPressStartNanos(long value) {
        this.pathHintPressStartNanos = value;
    }

    boolean pathHintKeyDown() {
        return pathHintKeyDown;
    }

    void setPathHintKeyDown(boolean value) {
        this.pathHintKeyDown = value;
    }

    boolean isRouteHintVisible() {
        return isRouteHintVisible;
    }

    void setRouteHintVisible(boolean value) {
        this.isRouteHintVisible = value;
    }

    long lastRouteHintPenaltyNanos() {
        return lastRouteHintPenaltyNanos;
    }

    void setLastRouteHintPenaltyNanos(long value) {
        this.lastRouteHintPenaltyNanos = value;
    }

    double routeHintPenaltyAccumulator() {
        return routeHintPenaltyAccumulator;
    }

    void setRouteHintPenaltyAccumulator(double value) {
        this.routeHintPenaltyAccumulator = value;
    }

    int routeHintPenaltyPoints() {
        return routeHintPenaltyPoints;
    }

    void setRouteHintPenaltyPoints(int value) {
        this.routeHintPenaltyPoints = value;
    }

    boolean enemyPathOverlayVisible() {
        return enemyPathOverlayVisible;
    }

    void setEnemyPathOverlayVisible(boolean value) {
        this.enemyPathOverlayVisible = value;
    }

    long enemyPathOverlayHideAtNanos() {
        return enemyPathOverlayHideAtNanos;
    }

    void setEnemyPathOverlayHideAtNanos(long value) {
        this.enemyPathOverlayHideAtNanos = value;
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
