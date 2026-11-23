package main.game.maze.game.targeting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import main.game.maze.mazeworld.Point2D;

/**
 * The soldiers of the infantry strike as a ranged enemy sees them: where they stand and a way to hurt one. Sergeants,
 * guard towers and pumpkin bombers shoot at them ({@link RangedTargeting}) the same way in both frontends. The points are
 * in the space of whoever holds this view ({@link #mapped} converts from the world space of {@code StrikeWeapons}).
 */
public interface SoldierTargets {

    /** A soldier is hit by a shot or splash reaching this close to its centre. */
    double BODY_RADIUS = 16.0;

    /** No soldiers in the maze. */
    SoldierTargets NONE = of(List::of, (soldier, damage) -> { });

    /** Where the living soldiers stand now. */
    List<Point2D> positions();

    /** The soldier standing at {@code soldier} (one of {@link #positions()}) takes {@code damage}. */
    void hit(Point2D soldier, int damage);

    /** The living soldier whose body is within {@code reach} of (x, y), the nearest one, or null. */
    default Point2D touching(double x, double y, double reach) {
        Point2D nearest = null;
        double best = reach + BODY_RADIUS;
        for (Point2D soldier : positions()) {
            double distance = soldier.distance(x, y);
            if (distance <= best) {
                best = distance;
                nearest = soldier;
            }
        }
        return nearest;
    }

    /**
     * A splash at (x, y): every soldier within {@code radius} takes {@code damage}.
     *
     * @return how many soldiers were hit
     */
    default int splash(double x, double y, double radius, int damage) {
        int hits = 0;
        for (Point2D soldier : positions()) {
            if (soldier.distance(x, y) <= radius + BODY_RADIUS) {
                hit(soldier, damage);
                hits++;
            }
        }
        return hits;
    }

    /**
     * This view in another space: {@code toView} turns a point of this view into the other space, {@code toThis} turns it
     * back.
     */
    default SoldierTargets mapped(UnaryOperator<Point2D> toView, UnaryOperator<Point2D> toThis) {
        SoldierTargets base = this;
        return of(() -> {
            List<Point2D> mappedPositions = new ArrayList<>();
            for (Point2D soldier : base.positions()) {
                mappedPositions.add(toView.apply(soldier));
            }
            return mappedPositions;
        }, (soldier, damage) -> base.hit(toThis.apply(soldier), damage));
    }

    /** This view moved by (dx, dy), e.g. from centres to top-left corners. */
    default SoldierTargets shifted(double dx, double dy) {
        return mapped(p -> new Point2D(p.getX() + dx, p.getY() + dy), p -> new Point2D(p.getX() - dx, p.getY() - dy));
    }

    /** A view over the given positions and hit action. */
    static SoldierTargets of(Supplier<List<Point2D>> positions, BiConsumer<Point2D, Integer> hit) {
        return new SoldierTargets() {
            @Override
            public List<Point2D> positions() {
                List<Point2D> now = positions.get();
                return now == null ? List.of() : now;
            }

            @Override
            public void hit(Point2D soldier, int damage) {
                hit.accept(soldier, damage);
            }
        };
    }
}
