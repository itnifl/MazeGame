package main.game.maze.common.movement;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Shared PATROL behavior for JavaFX and libGDX.
 *
 * <p>Patrol enemies follow the shortest 4-way path to the current waypoint.
 * If no path exists, they fall back to anti-loop wander for 5 seconds before
 * retrying the shortest path. Waypoints loop forever.
 */
public final class PatrolMovementService {

    public enum PatrolMovementMode {
        PATH_FOLLOW,
        WANDER_RECOVERY
    }

    public static final double WANDER_RECOVERY_SECONDS = 5.0d;
    private static final int[][] CARDINAL = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };

    private final Map<String, RuntimeState> states = new HashMap<>();
    private final AntiLoopWanderMovementService wanderService = new AntiLoopWanderMovementService();
    private final List<Waypoint> fixedWaypoints;

    public PatrolMovementService() {
        this.fixedWaypoints = List.of();
    }

    public PatrolMovementService(List<Waypoint> fixedWaypoints) {
        this.fixedWaypoints = fixedWaypoints == null ? List.of() : List.copyOf(fixedWaypoints);
    }

    public void reset() {
        states.clear();
    }

    public PatrolMovementMode modeForEnemy(String enemyId) {
        RuntimeState state = states.get(enemyId == null ? "<anonymous>" : enemyId);
        if (state == null || state.wanderRecoverySecondsRemaining <= 0d) {
            return PatrolMovementMode.PATH_FOLLOW;
        }
        return PatrolMovementMode.WANDER_RECOVERY;
    }

    public MovementResult tick(EnemyState enemy, WorldView world, double deltaSeconds) {
        if (enemy == null || world == null) {
            return new MovementResult(0d, 0d, 0, 0, false);
        }
        String id = enemy.id() == null ? "<anonymous>" : enemy.id();
        RuntimeState state = states.computeIfAbsent(id, ignored -> new RuntimeState());
        List<Waypoint> waypoints = patrolWaypoints(world);
        if (waypoints.isEmpty()) {
            return wanderService.tick(enemy, world);
        }

        double dt = Math.max(0d, deltaSeconds);
        Waypoint target = activeWaypoint(state, waypoints);
        if (reached(enemy, target)) {
            state.advanceWaypoint(waypoints.size());
            clearPath(state);
            target = activeWaypoint(state, waypoints);
        }

        if (state.wanderRecoverySecondsRemaining > 0d) {
            MovementResult wander = wanderService.tick(enemy, world);
            state.wanderRecoverySecondsRemaining = Math.max(0d, state.wanderRecoverySecondsRemaining - dt);
            if (state.wanderRecoverySecondsRemaining <= 0d) {
                clearPath(state);
            }
            return wander;
        }

        if (!pathStillTargets(state, target)) {
            clearPath(state);
        }
        if (state.path == null && !refreshPath(enemy, world, state, target)) {
            state.wanderRecoverySecondsRemaining = WANDER_RECOVERY_SECONDS;
            return wanderService.tick(enemy, world);
        }

        MovementResult next = followPath(enemy, world, state);
        if (next.moved()) {
            return next;
        }

        clearPath(state);
        state.wanderRecoverySecondsRemaining = WANDER_RECOVERY_SECONDS;
        return wanderService.tick(enemy, world);
    }

    private List<Waypoint> patrolWaypoints(WorldView world) {
        if (!fixedWaypoints.isEmpty()) {
            return fixedWaypoints;
        }
        double minX = world.minX();
        double minY = world.minY();
        double maxX = world.maxX();
        double maxY = world.maxY();
        double width = Math.max(1d, maxX - minX);
        double height = Math.max(1d, maxY - minY);
        double qx = width * 0.25d;
        double qy = height * 0.25d;
        return List.of(
                new Waypoint(minX + qx, minY + qy),
                new Waypoint(maxX - qx, minY + qy),
                new Waypoint(maxX - qx, maxY - qy),
                new Waypoint(minX + qx, maxY - qy));
    }

    private static Waypoint activeWaypoint(RuntimeState state, List<Waypoint> waypoints) {
        return waypoints.get(Math.floorMod(state.currentWaypointIndex, waypoints.size()));
    }

    private static boolean reached(EnemyState enemy, Waypoint target) {
        double threshold = Math.max(enemy.speed() * 1.5d, enemy.size() * 0.6d);
        return Math.hypot(target.x() - enemy.x(), target.y() - enemy.y()) <= threshold;
    }

    private static boolean pathStillTargets(RuntimeState state, Waypoint target) {
        return state.path != null
                && Math.abs(state.targetX - target.x()) < 0.001d
                && Math.abs(state.targetY - target.y()) < 0.001d;
    }

    private static boolean refreshPath(EnemyState enemy,
                                       WorldView world,
                                       RuntimeState state,
                                       Waypoint target) {
        List<GridPoint> path = computeShortestPath(enemy, world, target.x(), target.y());
        if (path.size() <= 1) {
            return false;
        }
        state.path = path;
        state.pathIndex = 1;
        state.targetX = target.x();
        state.targetY = target.y();
        return true;
    }

    private static void clearPath(RuntimeState state) {
        state.path = null;
        state.pathIndex = 0;
        state.targetX = Double.NaN;
        state.targetY = Double.NaN;
    }

    private static MovementResult followPath(EnemyState enemy, WorldView world, RuntimeState state) {
        while (state.path != null && state.pathIndex < state.path.size()) {
            GridPoint target = state.path.get(state.pathIndex);
            double dx = target.x - enemy.x();
            double dy = target.y - enemy.y();
            if (Math.hypot(dx, dy) <= Math.max(1d, enemy.speed())) {
                state.pathIndex++;
                continue;
            }
            int[] dir = GridDirection.directionBetween(enemy.x(), enemy.y(), target.x, target.y);
            MovementResult primary = tryStep(enemy, world, dir[0], dir[1]);
            if (primary.moved()) {
                return primary;
            }

            int altX = dir[0] == 0 ? (int) Math.signum(dx) : 0;
            int altY = dir[1] == 0 ? (int) Math.signum(dy) : 0;
            if (altX != 0 || altY != 0) {
                MovementResult alternate = tryStep(enemy, world, altX, altY);
                if (alternate.moved()) {
                    return alternate;
                }
            }
            return primary;
        }
        return new MovementResult(enemy.x(), enemy.y(), enemy.directionX(), enemy.directionY(), false);
    }

    private static MovementResult tryStep(EnemyState enemy, WorldView world, int dx, int dy) {
        if (dx == 0 && dy == 0) {
            return new MovementResult(enemy.x(), enemy.y(), 0, 0, false);
        }
        double nx = enemy.x() + dx * enemy.speed();
        double ny = enemy.y() + dy * enemy.speed();
        if (world.wouldCollide(nx, ny, enemy.size())) {
            return new MovementResult(enemy.x(), enemy.y(), dx, dy, false);
        }
        return new MovementResult(nx, ny, dx, dy, true);
    }

    private static List<GridPoint> computeShortestPath(EnemyState enemy,
                                                       WorldView world,
                                                       double targetX,
                                                       double targetY) {
        double step = gridStep(enemy);
        double minX = world.minX();
        double minY = world.minY();
        double maxX = world.maxX();
        double maxY = world.maxY();
        int cols = Math.max(2, (int) Math.floor((maxX - minX) / step) + 1);
        int rows = Math.max(2, (int) Math.floor((maxY - minY) / step) + 1);

        GridPoint start = quantize(enemy.x(), enemy.y(), minX, minY, step, cols, rows);
        GridPoint goal = quantize(targetX, targetY, minX, minY, step, cols, rows);
        GridPoint resolvedStart = nearestWalkable(start, world, enemy.size(), minX, minY, step, cols, rows);
        GridPoint resolvedGoal = nearestWalkable(goal, world, enemy.size(), minX, minY, step, cols, rows);
        if (resolvedStart == null || resolvedGoal == null) {
            return List.of();
        }

        int startIndex = index(resolvedStart.col, resolvedStart.row, cols);
        int goalIndex = index(resolvedGoal.col, resolvedGoal.row, cols);
        boolean[] visited = new boolean[cols * rows];
        int[] parent = new int[cols * rows];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = -1;
        }
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        visited[startIndex] = true;
        queue.add(startIndex);

        while (!queue.isEmpty()) {
            int current = queue.removeFirst();
            if (current == goalIndex) {
                break;
            }
            int col = current % cols;
            int row = current / cols;
            for (int[] d : CARDINAL) {
                int nc = col + d[0];
                int nr = row + d[1];
                int ni = index(nc, nr, cols);
                if (nc < 0 || nr < 0 || nc >= cols || nr >= rows) {
                    continue;
                }
                if (visited[ni]) {
                    continue;
                }
                if (!isWalkable(nc, nr, world, enemy.size(), minX, minY, step, cols, rows)) {
                    continue;
                }
                visited[ni] = true;
                parent[ni] = current;
                queue.addLast(ni);
            }
        }

        if (!visited[goalIndex]) {
            return List.of();
        }

        List<GridPoint> reversed = new ArrayList<>();
        for (int cur = goalIndex; cur >= 0; cur = parent[cur]) {
            int col = cur % cols;
            int row = cur / cols;
            reversed.add(pointAt(col, row, minX, minY, step));
            if (cur == startIndex) {
                break;
            }
        }

        List<GridPoint> path = new ArrayList<>(reversed.size());
        for (int i = reversed.size() - 1; i >= 0; i--) {
            path.add(reversed.get(i));
        }
        return path;
    }

    private static GridPoint nearestWalkable(GridPoint around,
                                             WorldView world,
                                             double size,
                                             double minX,
                                             double minY,
                                             double step,
                                             int cols,
                                             int rows) {
        if (isWalkable(around.col, around.row, world, size, minX, minY, step, cols, rows)) {
            return around;
        }
        int maxRadius = Math.max(cols, rows);
        for (int radius = 1; radius <= maxRadius; radius++) {
            for (int dc = -radius; dc <= radius; dc++) {
                for (int dr = -radius; dr <= radius; dr++) {
                    if (Math.abs(dc) != radius && Math.abs(dr) != radius) {
                        continue;
                    }
                    int nc = around.col + dc;
                    int nr = around.row + dr;
                    if (isWalkable(nc, nr, world, size, minX, minY, step, cols, rows)) {
                        return pointAt(nc, nr, minX, minY, step);
                    }
                }
            }
        }
        return null;
    }

    private static boolean isWalkable(int col,
                                      int row,
                                      WorldView world,
                                      double size,
                                      double minX,
                                      double minY,
                                      double step,
                                      int cols,
                                      int rows) {
        if (col < 0 || row < 0 || col >= cols || row >= rows) {
            return false;
        }
        double x = minX + col * step;
        double y = minY + row * step;
        return !world.wouldCollide(x, y, size);
    }

    private static GridPoint quantize(double x,
                                      double y,
                                      double minX,
                                      double minY,
                                      double step,
                                      int cols,
                                      int rows) {
        int col = (int) Math.round((x - minX) / step);
        int row = (int) Math.round((y - minY) / step);
        col = Math.max(0, Math.min(cols - 1, col));
        row = Math.max(0, Math.min(rows - 1, row));
        return pointAt(col, row, minX, minY, step);
    }

    private static GridPoint pointAt(int col, int row, double minX, double minY, double step) {
        return new GridPoint(col, row, minX + col * step, minY + row * step);
    }

    private static int index(int col, int row, int cols) {
        return row * cols + col;
    }

    private static double gridStep(EnemyState enemy) {
        double bySize = Math.max(4d, enemy.size() * 0.35d);
        return Math.min(10d, bySize);
    }

    public record Waypoint(double x, double y) {
    }

    private static final class RuntimeState {
        private double wanderRecoverySecondsRemaining;
        private int currentWaypointIndex;
        private List<GridPoint> path;
        private int pathIndex;
        private double targetX = Double.NaN;
        private double targetY = Double.NaN;

        private void advanceWaypoint(int pathSize) {
            currentWaypointIndex = Math.floorMod(currentWaypointIndex + 1, pathSize);
        }
    }

    private static final class GridPoint {
        private final int col;
        private final int row;
        private final double x;
        private final double y;

        private GridPoint(int col, int row, double x, double y) {
            this.col = col;
            this.row = row;
            this.x = x;
            this.y = y;
        }
    }
}