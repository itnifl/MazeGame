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
        PATH_FOLLOW,
        WANDER_RECOVERY
    }

    public static final double STUCK_THRESHOLD_SECONDS = 4.0d;
    public static final double PATH_FOLLOW_SECONDS = 20.0d;
    public static final double WANDER_RECOVERY_SECONDS = 6.0d;
    public static final double NO_PROGRESS_STUCK_SECONDS = 10.0d;
    private static final double PROGRESS_EPSILON_UNITS = 1.0d;
    private static final double AXIS_HYSTERESIS_UNITS = 6.0d;

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
        if (state.pathSecondsRemaining > 0d) {
            return AggressiveMovementMode.PATH_FOLLOW;
        }
        if (state.wanderRecoverySecondsRemaining > 0d) {
            return AggressiveMovementMode.WANDER_RECOVERY;
        }
        return AggressiveMovementMode.DIRECTIONAL;
    }

    public MovementResult tick(EnemyState enemy, WorldView world, double deltaSeconds) {
        if (enemy == null || world == null) {
            return new MovementResult(0d, 0d, 0, 0, false);
        }
        String id = enemy.id() == null ? "<anonymous>" : enemy.id();
        RuntimeState state = states.computeIfAbsent(id, ignored -> new RuntimeState());
        double dt = Math.max(0d, deltaSeconds);
        state.initDistanceIfNeeded(distanceToPlayer(enemy.x(), enemy.y(), world));

        if (state.pathSecondsRemaining > 0d) {
            MovementResult whilePath = tickPathMode(enemy, world, state, dt);
            if (state.pathSecondsRemaining <= 0d) {
                // Full 20 second shortest-path phase completed successfully.
                state.resetProgress(distanceToPlayer(whilePath.x(), whilePath.y(), world));
                state.blockedSeconds = 0d;
                return whilePath;
            }
            if (whilePath.moved()) {
                state.blockedSeconds = 0d;
                return whilePath;
            }
            beginWanderRecovery(state);
            MovementResult wanderAfterPathFail = wanderStep(enemy, world, state, id);
            state.blockedSeconds = 0d;
            return wanderAfterPathFail;
        }

        if (state.wanderRecoverySecondsRemaining > 0d) {
            MovementResult wander = wanderStep(enemy, world, state, id);
            updateProgressState(state, wander.x(), wander.y(), world, dt);
            state.wanderRecoverySecondsRemaining = Math.max(0d, state.wanderRecoverySecondsRemaining - dt);
            if (state.noProgressSeconds > NO_PROGRESS_STUCK_SECONDS || state.wanderRecoverySecondsRemaining <= 0d) {
                MovementResult retryPath = startPathAttempt(enemy, world, state);
                if (state.pathSecondsRemaining > 0d) {
                    state.blockedSeconds = 0d;
                    return retryPath;
                }
                beginWanderRecovery(state);
            }
            state.blockedSeconds = 0d;
            return wander;
        }

        MovementResult directional = directionalStep(enemy, world);
        updateProgressState(state, directional.x(), directional.y(), world, dt);
        if (directional.moved()) {
            state.blockedSeconds = 0d;
            if (state.noProgressSeconds > NO_PROGRESS_STUCK_SECONDS) {
                MovementResult pathStart = startPathAttempt(enemy, world, state);
                if (state.pathSecondsRemaining > 0d) {
                    return pathStart;
                }
                beginWanderRecovery(state);
                return wanderStep(enemy, world, state, id);
            }
            return directional;
        }

        state.blockedSeconds += dt;
        if (state.blockedSeconds > STUCK_THRESHOLD_SECONDS) {
            MovementResult pathStart = startPathAttempt(enemy, world, state);
            if (state.pathSecondsRemaining > 0d) {
                state.blockedSeconds = 0d;
                return pathStart;
            }
            beginWanderRecovery(state);
            MovementResult wanderAfterNoPath = wanderStep(enemy, world, state, id);
            state.blockedSeconds = 0d;
            return wanderAfterNoPath;
        }
        return directional;
    }

    private static double distanceToPlayer(double x, double y, WorldView world) {
        return Math.hypot(world.playerX() - x, world.playerY() - y);
    }

    private static void updateProgressState(RuntimeState state,
                                            double x,
                                            double y,
                                            WorldView world,
                                            double deltaSeconds) {
        double dist = distanceToPlayer(x, y, world);
        if (dist + PROGRESS_EPSILON_UNITS < state.bestDistanceToPlayer) {
            state.bestDistanceToPlayer = dist;
            state.noProgressSeconds = 0d;
            return;
        }
        state.noProgressSeconds += Math.max(0d, deltaSeconds);
    }

    private static MovementResult tickPathMode(EnemyState enemy,
                                               WorldView world,
                                               RuntimeState state,
                                               double deltaSeconds) {
        MovementResult alongPath = followPath(enemy, world, state);
        state.pathSecondsRemaining = Math.max(0d, state.pathSecondsRemaining - deltaSeconds);
        if (state.pathSecondsRemaining <= 0d) {
            clearPath(state);
            return alongPath;
        }
        if (alongPath.moved()) {
            return alongPath;
        }

        // Path segment may have expired as the player moved; refresh path while
        // preserving remaining path-follow time.
        if (refreshPath(enemy, world, state)) {
            MovementResult refreshed = followPath(enemy, world, state);
            if (refreshed.moved()) {
                return refreshed;
            }
        }
        clearPath(state);
        return alongPath;
    }

    private static MovementResult startPathAttempt(EnemyState enemy,
                                                   WorldView world,
                                                   RuntimeState state) {
        clearPath(state);
        if (!refreshPath(enemy, world, state)) {
            return new MovementResult(enemy.x(), enemy.y(), enemy.directionX(), enemy.directionY(), false);
        }
        state.pathSecondsRemaining = PATH_FOLLOW_SECONDS;
        MovementResult firstStep = followPath(enemy, world, state);
        if (!firstStep.moved()) {
            clearPath(state);
        }
        return firstStep;
    }

    private static boolean refreshPath(EnemyState enemy, WorldView world, RuntimeState state) {
        List<Point> path = computeShortestPath(enemy, world);
        if (path.size() <= 1) {
            state.path = null;
            state.pathIndex = 0;
            return false;
        }
        state.path = path;
        state.pathIndex = 1;
        return true;
    }

    private static void beginWanderRecovery(RuntimeState state) {
        clearPath(state);
        state.wanderRecoverySecondsRemaining = WANDER_RECOVERY_SECONDS;
    }

    private static void clearPath(RuntimeState state) {
        state.path = null;
        state.pathIndex = 0;
        state.pathSecondsRemaining = 0d;
    }

    private static MovementResult directionalStep(EnemyState enemy, WorldView world) {
        int[] desired = directionalVectorWithHysteresis(enemy, world);
        return tryStep(enemy, world, desired[0], desired[1]);
    }

    private static int[] directionalVectorWithHysteresis(EnemyState enemy, WorldView world) {
        double deltaX = world.playerX() - enemy.x();
        double deltaY = world.playerY() - enemy.y();
        double absX = Math.abs(deltaX);
        double absY = Math.abs(deltaY);
        int signX = absX > GridDirection.ALIGNMENT_THRESHOLD ? (int) Math.signum(deltaX) : 0;
        int signY = absY > GridDirection.ALIGNMENT_THRESHOLD ? (int) Math.signum(deltaY) : 0;

        if (signX != 0 && signY != 0 && Math.abs(absX - absY) <= AXIS_HYSTERESIS_UNITS) {
            if (enemy.directionX() != 0) {
                return new int[] {signX, 0};
            }
            if (enemy.directionY() != 0) {
                return new int[] {0, signY};
            }
        }
        return GridDirection.directionBetween(enemy.x(), enemy.y(), world.playerX(), world.playerY());
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

    private static MovementResult wanderStep(EnemyState enemy,
                                             WorldView world,
                                             RuntimeState state,
                                             String enemyId) {
        MovementResult forward = tryStep(enemy, world, enemy.directionX(), enemy.directionY());
        if (forward.moved()) {
            return forward;
        }

        int reverseX = -enemy.directionX();
        int reverseY = -enemy.directionY();
        int startIndex = Math.floorMod((enemyId == null ? 0 : enemyId.hashCode()) + state.wanderTick, CARDINAL.length);

        for (int i = 0; i < CARDINAL.length; i++) {
            int idx = Math.floorMod(startIndex + i, CARDINAL.length);
            int dx = CARDINAL[idx][0];
            int dy = CARDINAL[idx][1];
            if (dx == reverseX && dy == reverseY) {
                continue;
            }
            MovementResult candidate = tryStep(enemy, world, dx, dy);
            if (candidate.moved()) {
                state.wanderTick++;
                return candidate;
            }
        }
        state.wanderTick++;
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
        // Keep the navigation lattice fine enough for narrow corridors.
        // Coupling this to movement speed can over-coarsen the grid and make
        // reachable shortest paths appear blocked.
        double bySize = Math.max(4d, enemy.size() * 0.35d);
        return Math.min(10d, bySize);
    }

    private static final class RuntimeState {
        private double blockedSeconds;
        private double pathSecondsRemaining;
        private double wanderRecoverySecondsRemaining;
        private double noProgressSeconds;
        private double bestDistanceToPlayer = Double.NaN;
        private List<Point> path;
        private int pathIndex;
        private int wanderTick;

        private void initDistanceIfNeeded(double distance) {
            if (Double.isNaN(bestDistanceToPlayer)) {
                bestDistanceToPlayer = distance;
            }
        }

        private void resetProgress(double distance) {
            noProgressSeconds = 0d;
            bestDistanceToPlayer = distance;
        }
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
