package main.game.maze.game.strike;

import java.util.List;
import main.game.maze.common.movement.WorldView;
import main.game.maze.mazeworld.Point2D;

/**
 * Enemies go for the infantry strike's soldiers first, the same in both frontends: an enemy with a soldier within the lure
 * radius chases the nearest such soldier instead of the player. The enemy movement services chase "the player" of the
 * {@link WorldView} they are given, so the enemy is simply given a view whose player is that soldier ({@link #chasing}).
 * All points are in the frontend's own movement space (the frontend converts the soldiers into it).
 */
public final class DecoyTargeting {

    private DecoyTargeting() {
    }

    /** The nearest soldier within {@code lureRadius} of the enemy at (x, y), or null when none is that close. */
    public static Point2D nearestWithin(List<Point2D> soldiers, double x, double y, double lureRadius) {
        Point2D best = null;
        double bestDistance = lureRadius;
        if (soldiers == null) {
            return null;
        }
        for (Point2D soldier : soldiers) {
            double distance = soldier.distance(x, y);
            if (distance <= bestDistance) {
                bestDistance = distance;
                best = soldier;
            }
        }
        return best;
    }

    /** {@code base} with the soldier at {@code target} in place of the player; everything else is the base view's. */
    public static WorldView chasing(WorldView base, Point2D target) {
        if (target == null) {
            return base;
        }
        return new WorldView() {
            @Override public double playerX() { return target.getX(); }
            @Override public double playerY() { return target.getY(); }
            @Override public boolean wouldCollide(double centerX, double centerY, double size) {
                return base.wouldCollide(centerX, centerY, size);
            }
            @Override public boolean inDeadlyLava(double centerX, double centerY) {
                return base.inDeadlyLava(centerX, centerY);
            }
            @Override public double minX() { return base.minX(); }
            @Override public double minY() { return base.minY(); }
            @Override public double maxX() { return base.maxX(); }
            @Override public double maxY() { return base.maxY(); }
        };
    }

    /** The view an enemy at (x, y) moves by: chasing the nearest soldier within the lure radius, else the base view. */
    public static WorldView viewFor(WorldView base, List<Point2D> soldiers, double x, double y, double lureRadius) {
        return chasing(base, nearestWithin(soldiers, x, y, lureRadius));
    }
}
