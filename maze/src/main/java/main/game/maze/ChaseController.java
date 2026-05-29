package main.game.maze;

import main.game.maze.characters.ComputerCharacter;
import main.game.maze.characters.interfaces.IMovingComputerCharacter;
import main.game.maze.mazeworld.Point2D;

/**
 * Computes a 4-way unit direction vector that points a computer character
 * toward a target position. Backs F19 (AGGRESSIVE behavior), where an
 * opponent actively chases the player every tick instead of wandering or
 * following a fixed patrol route.
 */
public final class ChaseController {

    private static final double ALIGNMENT_THRESHOLD = 1.0;

    private ChaseController() {}

    /**
     * Returns a (-1|0|1, -1|0|1) unit vector pointing from the character
     * toward {@code target}, collapsed onto the dominant axis so movement
     * stays grid-aligned. Returns {@code null} if the character is not a
     * {@link ComputerCharacter} (and therefore has no readable position) or
     * the target is {@code null}.
     */
    public static Point2D getDirectionTowards(IMovingComputerCharacter character, Point2D target) {
        if (target == null || !(character instanceof ComputerCharacter cc)) {
            return null;
        }
        Point2D current = cc.getCharacterPosition();
        if (current == null) {
            return null;
        }
        return directionBetween(current.getX(), current.getY(), target.getX(), target.getY());
    }

    /**
     * Pure helper exposed for testability. Returns the same 4-way unit
     * vector that {@link #getDirectionTowards} would return for the given
     * raw coordinates.
     */
    public static Point2D directionBetween(double currentX, double currentY,
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
        return new Point2D(dirX, dirY);
    }
}
