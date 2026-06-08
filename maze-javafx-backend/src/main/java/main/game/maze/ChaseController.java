package main.game.maze;

import main.game.maze.characters.ComputerCharacter;
import main.game.maze.characters.interfaces.IMovingComputerCharacter;
import main.game.maze.common.movement.GridDirection;
import main.game.maze.mazeworld.Point2D;

/**
 * Computes a 4-way unit direction vector that points a computer character
 * toward a target position. Backs F19 (AGGRESSIVE behavior), where an
 * opponent actively chases the player every tick instead of wandering or
 * following a fixed patrol route.
 *
 * <p>The direction math itself lives in the backend-neutral
 * {@link GridDirection} helper in maze-common-frontend so the JavaFX and
 * libGDX runtimes provably share a single implementation (DIP).
 */
public final class ChaseController {

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
     * raw coordinates. Delegates to {@link GridDirection#directionBetween}
     * so the rule is defined exactly once across both frontends.
     */
    public static Point2D directionBetween(double currentX, double currentY,
                                           double targetX, double targetY) {
        int[] step = GridDirection.directionBetween(currentX, currentY, targetX, targetY);
        return new Point2D(step[0], step[1]);
    }
}
