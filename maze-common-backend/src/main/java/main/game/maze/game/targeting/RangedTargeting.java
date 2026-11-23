package main.game.maze.game.targeting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import main.game.maze.mazeworld.Point2D;

/**
 * Whom a ranged enemy (a sergeant, a guard tower, a pumpkin bomber) shoots at, the same in both frontends: the nearest
 * soldier of the infantry strike it can hit from where it stands, and the player only when it can hit no soldier. What
 * "can hit" means is the enemy's own rule (its range, the walls it may shoot over), given as a {@link Sight}.
 */
public final class RangedTargeting {

    /** Whether the enemy can hit a target at (x, y) from where it stands. */
    @FunctionalInterface
    public interface Sight {
        boolean canHit(double x, double y);
    }

    /**
     * The target chosen: where it is, the soldier it is ({@code null} for the player) and whether the enemy can hit it (a
     * soldier is only chosen when it can; the player may be out of sight).
     */
    public record Target(double x, double y, Point2D soldier, boolean inSight) {

        public boolean isSoldier() {
            return soldier != null;
        }
    }

    private RangedTargeting() {
    }

    /** The target of an enemy at (fromX, fromY): the nearest soldier in its sight, else the player at (playerX, playerY). */
    public static Target choose(List<Point2D> soldiers, double fromX, double fromY, double playerX, double playerY,
                                Sight sight) {
        if (soldiers != null && !soldiers.isEmpty()) {
            List<Point2D> nearestFirst = new ArrayList<>(soldiers);
            nearestFirst.sort(Comparator.comparingDouble(soldier -> soldier.distance(fromX, fromY)));
            for (Point2D soldier : nearestFirst) {
                if (sight.canHit(soldier.getX(), soldier.getY())) {
                    return new Target(soldier.getX(), soldier.getY(), soldier, true);
                }
            }
        }
        return new Target(playerX, playerY, null, sight.canHit(playerX, playerY));
    }
}
