package main.game.maze.game.items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.service.MazeNavigationGraph;

/**
 * Items lying in the current level. Every {@link PickupCatalog#spawnIntervalSeconds()} a random
 * item (weighted by the catalogue) spawns at a free corridor point, unless the board already
 * holds its cap; picking items up frees room, so spawning continues. Shared by both frontends;
 * coordinates are world space (y-down).
 */
public final class PickupField {

    private final PickupCatalog catalog;
    private final Random random;
    private final List<Pickup> items = new CopyOnWriteArrayList<>();
    private double sinceLastSpawn;
    private long nextId = 1;

    public PickupField(PickupCatalog catalog, Random random) {
        this.catalog = catalog;
        this.random = random;
    }

    public PickupCatalog catalog() {
        return catalog;
    }

    /**
     * Where items of this catalogue may spawn in a level: its navigation-graph points that lie inside the
     * maze and keep {@link PickupCatalog#spawnClearance()} from every wall, so no item is ever drawn in or
     * over a wall (grid nodes also sit on wall lines and in the padding around the maze). Walls only
     * disappear during a level, so the points stay valid.
     */
    public List<Point2D> spawnPointsOf(GameMazeWorld world) {
        List<Point2D> points = new ArrayList<>();
        MazeNavigationGraph graph = world != null ? world.getNavigationGraph() : null;
        List<Vector2D> walls = world != null ? world.getMazeVectors() : List.of();
        if (graph == null || walls.isEmpty()) {
            return points;
        }
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE, maxX = -Double.MAX_VALUE, maxY = -Double.MAX_VALUE;
        for (Vector2D wall : walls) {
            minX = Math.min(minX, Math.min(wall.getStart().getX(), wall.getEnd().getX()));
            minY = Math.min(minY, Math.min(wall.getStart().getY(), wall.getEnd().getY()));
            maxX = Math.max(maxX, Math.max(wall.getStart().getX(), wall.getEnd().getX()));
            maxY = Math.max(maxY, Math.max(wall.getStart().getY(), wall.getEnd().getY()));
        }
        double clearance = catalog.spawnClearance();
        for (MazeNavigationGraph.Node[] column : graph.getGrid()) {
            for (MazeNavigationGraph.Node node : column) {
                if (node != null && isInside(node.getX(), node.getY(), minX, minY, maxX, maxY)
                        && clearOfWalls(node.getX(), node.getY(), walls, clearance)) {
                    points.add(node.toPoint2D());
                }
            }
        }
        return points;
    }

    /** Shortest distance from (x, y) to a wall's centre line. */
    public static double distanceToSegment(double x, double y, Vector2D wall) {
        double ax = wall.getStart().getX(), ay = wall.getStart().getY();
        double dx = wall.getEnd().getX() - ax, dy = wall.getEnd().getY() - ay;
        double lengthSquared = dx * dx + dy * dy;
        double t = lengthSquared == 0 ? 0 : Math.max(0, Math.min(1, ((x - ax) * dx + (y - ay) * dy) / lengthSquared));
        return Math.hypot(x - (ax + t * dx), y - (ay + t * dy));
    }

    private static boolean isInside(double x, double y, double minX, double minY, double maxX, double maxY) {
        return x > minX && x < maxX && y > minY && y < maxY;
    }

    private static boolean clearOfWalls(double x, double y, List<Vector2D> walls, double clearance) {
        for (Vector2D wall : walls) {
            if (distanceToSegment(x, y, wall) < clearance) {
                return false;
            }
        }
        return true;
    }

    /**
     * Advances the spawn timer.
     *
     * @param boardWidth     decides the item cap (small / medium / large maps)
     * @param spawnPoints    walkable corridor points to spawn on (world space)
     * @param playerX,playerY player position, items keep their distance from it
     * @return the item that spawned, or null
     */
    public Pickup tick(double dt, int boardWidth, List<Point2D> spawnPoints, double playerX, double playerY) {
        sinceLastSpawn += Math.max(0, dt);
        if (sinceLastSpawn < catalog.spawnIntervalSeconds()) {
            return null;
        }
        sinceLastSpawn = 0;
        if (items.size() >= catalog.maxItemsForBoardWidth(boardWidth) || spawnPoints == null || spawnPoints.isEmpty()) {
            return null;
        }
        Point2D spot = freeSpot(spawnPoints, playerX, playerY);
        if (spot == null) {
            return null;
        }
        Pickup pickup = new Pickup(nextId++, catalog.randomKind(random), spot.getX(), spot.getY());
        items.add(pickup);
        return pickup;
    }

    /** Removes and returns the items the player at (x, y) with body radius {@code playerRadius} touches. */
    public List<Pickup> collect(double playerX, double playerY, double playerRadius) {
        List<Pickup> taken = new ArrayList<>();
        double reach = playerRadius + catalog.pickupRadius();
        for (Iterator<Pickup> it = items.iterator(); it.hasNext(); ) {
            Pickup pickup = it.next();
            if (Math.hypot(pickup.x() - playerX, pickup.y() - playerY) <= reach) {
                taken.add(pickup);
            }
        }
        items.removeAll(taken);
        return taken;
    }

    public List<Pickup> items() {
        return List.copyOf(items);
    }

    /** New level: no items, timer restarts. */
    public void clear() {
        items.clear();
        sinceLastSpawn = 0;
    }

    private Point2D freeSpot(List<Point2D> spawnPoints, double playerX, double playerY) {
        // a few random tries keep spawning cheap on big boards
        for (int attempt = 0; attempt < 32; attempt++) {
            Point2D candidate = spawnPoints.get(random.nextInt(spawnPoints.size()));
            if (isFree(candidate, playerX, playerY)) {
                return candidate;
            }
        }
        return null;
    }

    private boolean isFree(Point2D candidate, double playerX, double playerY) {
        if (Math.hypot(candidate.getX() - playerX, candidate.getY() - playerY) < catalog.minDistanceFromPlayer()) {
            return false;
        }
        for (Pickup other : items) {
            if (Math.hypot(candidate.getX() - other.x(), candidate.getY() - other.y()) < catalog.itemSize()) {
                return false;
            }
        }
        return true;
    }
}
