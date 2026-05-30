package main.game.maze.common.movement;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Aggressive movement with anti-stuck fallback.
 *
 * <p>Normal mode uses a directional approach toward the player. If the enemy
 * fails to move for more than {@link #STUCK_THRESHOLD_SECONDS}, it computes a
 * shortest 4-way path and follows it for up to {@link #PATH_FOLLOW_SECONDS}
 * before returning to directional approach.
 */
public final class AdaptiveAggressiveMovementService {

    public enum AggressiveMovementMode {
        DIRECTIONAL,
        PATH_FOLLOW
    }

    public static final double STUCK_THRESHOLD_SECONDS = 3.0d;
    public static final double PATH_FOLLOW_SECONDS = 20.0d;

    private static final int[][] CARDINAL = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };

    private final Map<String, RuntimeState> states = new HashMap<>();

    public void reset() {
        states.clear();
    }

    public AggressiveMovementMode modeForEnemy(String enemyId) {
        if (enemyId == null) {
            return AggressiveMovementMode.DIRECTIONAL;
        }
        RuntimeState state = states.get(enemyId);
        if (state == null) {
            return AggressiveMovementMode.DIRECTIONAL;
        }
        return state.pathSecondsRemaining > 0d
                ? AggressiveMovementMode.PATH_FOLLOW
                : AggressiveMovementMode.DIRECTIONAL;
    }

    public MovementResult tick(EnemyState enemy, WorldView world, double deltaSeconds) {
        if (enemy == null || world == null) {
            return new MovementResult(0d, 0d, 0, 0, false);
        }
        String id = enemy.id() == null ? "<anonymous>" : enemy.id();
        RuntimeState state = states.computeIfAbsent(id, ignored -> new RuntimeState());

        if (state.pathSecondsRemaining > 0d && state.path != null && !state.path.isEmpty()) {
            MovementResult alongPath = followPath(enemy, world, state);
            state.pathSecondsRemaining = Math.max(0d, state.pathSecondsRemaining - Math.max(0d, deltaSeconds));
            if (state.pathSecondsRemaining <= 0d || state.path == null || state.pathIndex >= state.path.size()) {
                state.path = null;
                state.pathIndex = 0;
            }
            if (alongPath.moved()) {
                state.blockedSeconds = 0d;
                return alongPath;
            }
            // Path became invalid, reset and re-enter directional mode.
            state.path = null;
            state.pathIndex = 0;
            state.pathSecondsRemaining = 0d;
        }

        MovementResult directional = directionalStep(enemy, world);
        if (directional.moved()) {
            state.blockedSeconds = 0d;
            return directional;
        }

        state.blockedSeconds += Math.max(0d, deltaSeconds);
        if (state.blockedSeconds > STUCK_THRESHOLD_SECONDS) {
            List<Point> path = computeShortestPath(enemy, world);
            if (path.size() > 1) {
                state.path = path;
                state.pathIndex = 1;
                state.pathSecondsRemaining = PATH_FOLLOW_SECONDS;
                state.blockedSeconds = 0d;
                MovementResult firstPathMove = followPath(enemy, world, state);
                if (firstPathMove.moved()) {
                    return firstPathMove;
                }
                state.path = null;
                state.pathIndex = 0;
                state.pathSecondsRemaining = 0d;
            }
        }
        return directional;
    }

    private static MovementResult directionalStep(EnemyState enemy, WorldView world) {
        int[] desired = GridDirection.directionBetween(enemy.x(), enemy.y(), world.playerX(), world.playerY());
        return tryStep(enemy, world, desired[0], desired[1]);
    }

    private static MovementResult followPath(EnemyState enemy, WorldView world, RuntimeState state) {
        while (state.path != null && state.pathIndex < state.path.size()) {
            Point target = state.path.get(state.pathIndex);
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

    private static List<Point> computeShortestPath(EnemyState enemy, WorldView world) {
        double step = gridStep(enemy);
        double minX = world.minX();
        double minY = world.minY();
        double maxX = world.maxX();
        double maxY = world.maxY();
        int cols = Math.max(2, (int) Math.floor((maxX - minX) / step) + 1);
        int rows = Math.max(2, (int) Math.floor((maxY - minY) / step) + 1);

        Point start = quantize(enemy.x(), enemy.y(), minX, minY, step, cols, rows);
        Point goal = quantize(world.playerX(), world.playerY(), minX, minY, step, cols, rows);
        Point resolvedStart = nearestWalkable(start, world, enemy.size(), minX, minY, step, cols, rows);
        Point resolvedGoal = nearestWalkable(goal, world, enemy.size(), minX, minY, step, cols, rows);
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

        List<Point> reversed = new ArrayList<>();
        for (int cur = goalIndex; cur >= 0; cur = parent[cur]) {
            int col = cur % cols;
            int row = cur / cols;
            reversed.add(pointAt(col, row, minX, minY, step));
            if (cur == startIndex) {
                break;
            }
        }

        List<Point> path = new ArrayList<>(reversed.size());
        for (int i = reversed.size() - 1; i >= 0; i--) {
            path.add(reversed.get(i));
        }
        return path;
    }

    private static Point nearestWalkable(Point around,
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

    private static Point quantize(double x,
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

    private static Point pointAt(int col, int row, double minX, double minY, double step) {
        return new Point(col, row, minX + col * step, minY + row * step);
    }

    private static int index(int col, int row, int cols) {
        return row * cols + col;
    }

    private static double gridStep(EnemyState enemy) {
        double bySize = Math.max(6d, enemy.size() * 0.5d);
        double bySpeed = Math.max(1d, enemy.speed());
        return Math.max(bySpeed, bySize);
    }

    private static final class RuntimeState {
        private double blockedSeconds;
        private double pathSecondsRemaining;
        private List<Point> path;
        private int pathIndex;
    }

    private static final class Point {
        private final int col;
        private final int row;
        private final double x;
        private final double y;

        private Point(int col, int row, double x, double y) {
            this.col = col;
            this.row = row;
            this.x = x;
            this.y = y;
        }
    }
}
