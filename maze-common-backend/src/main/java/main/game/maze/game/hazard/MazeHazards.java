package main.game.maze.game.hazard;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import main.game.maze.game.items.PickupCatalogLoader;
import main.game.maze.game.items.PickupField;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.WallCollisionUtil;

/**
 * Puts the mines and the teleport exits into a generated maze (shared world space, y-down): they only ever
 * lie on free corridor points that keep clear of every wall.
 */
public final class MazeHazards {

    /** Clearance a mine keeps from a wall's centre line: half a wall, the mine's body and a little air. */
    public static final double WALL_CLEARANCE = 14.0;

    private MazeHazards() {
    }

    /** The corridor points of the maze that keep clear of every wall: where a teleported player may come out. */
    public static List<Point2D> freePoints(GameMazeWorld world) {
        return new PickupField(PickupCatalogLoader.loadDefault(), new java.util.Random()).spawnPointsOf(world);
    }

    /** Whether a mine may lie at {@code spot}: no wall's centre line closer than {@link #WALL_CLEARANCE}. */
    public static boolean clearOfWalls(Point2D spot, List<Vector2D> walls) {
        for (Vector2D wall : walls) {
            if (PickupField.distanceToSegment(spot.getX(), spot.getY(), wall) < WALL_CLEARANCE) {
                return false;
            }
        }
        return true;
    }

    /** Where a mine may lie: clear of the walls (the live wall list, so destroyed walls no longer count). */
    public static Predicate<Point2D> freeSpot(List<Vector2D> walls) {
        return spot -> clearOfWalls(spot, walls);
    }

    /** Where a mine may lie in this world: clear of the walls and out of the lava. */
    public static Predicate<Point2D> freeSpot(GameMazeWorld world) {
        Predicate<Point2D> clear = freeSpot(world.getMazeVectors());
        return spot -> clear.test(spot) && (world.getLava() == null || !world.getLava().isLava(spot.getX(), spot.getY()));
    }

    /** Whether a mine can be seen from its sergeant: no wall between them. */
    public static BiPredicate<Point2D, Point2D> clearLine(List<Vector2D> walls) {
        return (from, to) -> !WallCollisionUtil.wallBetweenVectors(from.getX(), from.getY(), to.getX(), to.getY(), walls);
    }

    /** A circle of mines round every post, only where a mine is clear of walls and can be seen from its post. */
    public static void placeMines(MineField field, List<Point2D> posts, GameMazeWorld world) {
        List<Vector2D> walls = world.getMazeVectors();
        Predicate<Point2D> free = freeSpot(walls);
        field.place(posts, spot -> free.test(spot) && (world.getLava() == null || !world.getLava().isLava(spot.getX(), spot.getY())),
                clearLine(walls));
    }
}
