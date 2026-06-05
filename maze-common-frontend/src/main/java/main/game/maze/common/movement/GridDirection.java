package main.game.maze.common.movement;

/**
 * Pure helper exposing the grid-direction math used by both the JavaFX
 * {@code ChaseController} and the shared {@link EnemyMovementService}.
 *
 * <p>Lives in maze-common-frontend so {@code ChaseController.directionBetween}
 * and any libGDX-side adapter resolve to literally the same code path —
 * there is no second copy of this rule to drift out of parity.
 */
public final class GridDirection {

    /**
     * Snap-to-axis threshold: deltas with absolute value below this are
     * treated as already-aligned (direction 0 on that axis).
     */
    public static final double ALIGNMENT_THRESHOLD = 1.0;

    private GridDirection() {
    }

    /**
     * Returns the dominant-axis cardinal step ({@code -1}, {@code 0} or
     * {@code +1} on each axis) that points from ({@code currentX},
     * {@code currentY}) toward ({@code targetX}, {@code targetY}).
     */
    public static int[] directionBetween(double currentX, double currentY,
                                         double targetX, double targetY) {
        double deltaX = targetX - currentX;
        double deltaY = targetY - currentY;

        int dirX = Math.abs(deltaX) > ALIGNMENT_THRESHOLD ? (int) Math.signum(deltaX) : 0;
        int dirY = Math.abs(deltaY) > ALIGNMENT_THRESHOLD ? (int) Math.signum(deltaY) : 0;

        if (dirX != 0 && dirY != 0) {
            if (Math.abs(deltaX) >= Math.abs(deltaY)) {
                dirY = 0;
            } else {
                dirX = 0;
            }
        }
        return new int[] {dirX, dirY};
    }
}


