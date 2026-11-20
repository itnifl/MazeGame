package main.game.maze.game.items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
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

    /** The walkable corridor points of a level (its navigation graph nodes), where items may spawn. */
    public static List<Point2D> spawnPointsOf(GameMazeWorld world) {
        List<Point2D> points = new ArrayList<>();
        MazeNavigationGraph graph = world != null ? world.getNavigationGraph() : null;
        if (graph == null) {
            return points;
        }
        for (MazeNavigationGraph.Node[] column : graph.getGrid()) {
            for (MazeNavigationGraph.Node node : column) {
                if (node != null) {
                    points.add(node.toPoint2D());
                }
            }
        }
        return points;
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
            if (Math.hypot(candidate.getX() - other.x(), candidate.getY() - other.y()) < catalog.pickupRadius() * 2) {
                return false;
            }
        }
        return true;
    }
}
