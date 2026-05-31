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
    private static final double SIGNIFICANT_PROGRESS_UNITS = 6.0d;
    private static final double AXIS_HYSTERESIS_UNITS = 6.0d;
    /** Hard anti-circle cap: never visit the same cell more than this many times
     * in the recent history window before forcing a fresh shortest-path retry. */
    private static final int MAX_CELL_VISITS = 2;
    private static final int CELL_HISTORY_SIZE = 24;
    private static final double CELL_GRID_MIN = 2.0d;
    private static final int FOUR_CYCLE_BLOCK = 4;
    private static final int MAX_FOUR_CYCLE_REPEATS = 3;
    private static final int DIRECTION_HISTORY_WINDOW = FOUR_CYCLE_BLOCK * MAX_FOUR_CYCLE_REPEATS;

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

    public List<ActivePathPoint> currentPathForEnemy(String enemyId, double currentX, double currentY) {
        String id = enemyId == null ? "<anonymous>" : enemyId;
        RuntimeState state = states.get(id);
        if (state == null || state.path == null || state.pathIndex >= state.path.size()) {
            return List.of();
        }
        List<ActivePathPoint> snapshot = new ArrayList<>();
        snapshot.add(new ActivePathPoint(currentX, currentY));
        for (int i = state.pathIndex; i < state.path.size(); i++) {
            Point point = state.path.get(i);
            snapshot.add(new ActivePathPoint(point.x, point.y));
        }
        return snapshot.size() > 1 ? snapshot : List.of();
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
        if (state.progressCheckpointDistance - dist >= SIGNIFICANT_PROGRESS_UNITS) {
            state.progressCheckpointDistance = dist;
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
        rememberCell(state, cellKey(enemy.x(), enemy.y(), enemy.speed()));

        int dx = enemy.directionX();
        int dy = enemy.directionY();

        // Anti-circle: if continuing straight would revisit a hot cell, force change.
        boolean blockedByVisitCap = dx != 0 || dy != 0
                ? visitCountAfterStep(state, enemy, dx, dy) >= MAX_CELL_VISITS
                : true;
        if (!blockedByVisitCap) {
            MovementResult forward = tryStep(enemy, world, dx, dy);
            if (forward.moved() && !wouldCreateThreeConsecutiveFourDirectionRepeats(state, dx, dy)) {
                state.wanderTick++;
                rememberCell(state, cellKey(forward.x(), forward.y(), enemy.speed()));
                rememberDirection(state, dx, dy);
                return forward;
            }
        }

        int startIndex = Math.floorMod(
                (enemyId == null ? 0 : enemyId.hashCode()) + state.wanderTick,
                CARDINAL.length);

        // Two passes: first respecting the visit cap, then ignoring it.
        // Reverse is allowed in both passes to break tight-area circles faster.
        for (int pass = 0; pass < 2; pass++) {
            for (int i = 0; i < CARDINAL.length; i++) {
                int idx = Math.floorMod(startIndex + i, CARDINAL.length);
                int cdx = CARDINAL[idx][0];
                int cdy = CARDINAL[idx][1];
                if (pass == 0 && visitCountAfterStep(state, enemy, cdx, cdy) >= MAX_CELL_VISITS) {
                    continue;
                }
                MovementResult candidate = tryStep(enemy, world, cdx, cdy);
                if (candidate.moved() && !wouldCreateThreeConsecutiveFourDirectionRepeats(state, cdx, cdy)) {
                    state.wanderTick++;
                    rememberCell(state, cellKey(candidate.x(), candidate.y(), enemy.speed()));
                    rememberDirection(state, cdx, cdy);
                    return candidate;
                }
            }
        }
        // If all cardinals are blocked, keep direction but report no movement.
        state.wanderTick++;
        return new MovementResult(enemy.x(), enemy.y(), dx, dy, false);
    }

    private static int visitCountAfterStep(RuntimeState state, EnemyState enemy, int dx, int dy) {
        double nx = enemy.x() + dx * enemy.speed();
        double ny = enemy.y() + dy * enemy.speed();
        return state.visitCounts.getOrDefault(cellKey(nx, ny, enemy.speed()), 0);
    }

    private static boolean wouldCreateThreeConsecutiveFourDirectionRepeats(RuntimeState state, int dx, int dy) {
        int dir = encodeDirection(dx, dy);
        if (dir < 0) {
            return false;
        }
        if (state.recentDirections.size() < DIRECTION_HISTORY_WINDOW - 1) {
            return false;
        }

        int[] seq = new int[DIRECTION_HISTORY_WINDOW];
        int i = 0;
        for (int existing : state.recentDirections) {
            seq[i++] = existing;
        }
        seq[i] = dir;

        for (int j = 0; j < FOUR_CYCLE_BLOCK; j++) {
            int expected = seq[j];
            if (seq[j + FOUR_CYCLE_BLOCK] != expected || seq[j + 2 * FOUR_CYCLE_BLOCK] != expected) {
                return false;
            }
        }
        return true;
    }

    private static void rememberDirection(RuntimeState state, int dx, int dy) {
        int dir = encodeDirection(dx, dy);
        if (dir < 0) {
            return;
        }
        state.recentDirections.addLast(dir);
        while (state.recentDirections.size() > DIRECTION_HISTORY_WINDOW - 1) {
            state.recentDirections.removeFirst();
        }
    }

    private static int encodeDirection(int dx, int dy) {
        if (dx > 0 && dy == 0) {
            return 0;
        }
        if (dx < 0 && dy == 0) {
            return 1;
        }
        if (dx == 0 && dy > 0) {
            return 2;
        }
        if (dx == 0 && dy < 0) {
            return 3;
        }
        return -1;
    }

    private static long cellKey(double x, double y, double speed) {
        double cell = Math.max(CELL_GRID_MIN, Math.max(1d, speed));
        long qx = Math.round(x / cell);
        long qy = Math.round(y / cell);
        return (qx << 32) ^ (qy & 0xffffffffL);
    }

    private static void rememberCell(RuntimeState state, long key) {
        state.recentCells.addLast(key);
        state.visitCounts.merge(key, 1, Integer::sum);
        while (state.recentCells.size() > CELL_HISTORY_SIZE) {
            long removed = state.recentCells.removeFirst();
            int next = state.visitCounts.getOrDefault(removed, 0) - 1;
            if (next <= 0) {
                state.visitCounts.remove(removed);
            } else {
                state.visitCounts.put(removed, next);
            }
        }
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
        private double progressCheckpointDistance = Double.NaN;
        private List<Point> path;
        private int pathIndex;
        private int wanderTick;
        private final ArrayDeque<Long> recentCells = new ArrayDeque<>();
        private final Map<Long, Integer> visitCounts = new HashMap<>();
        private final ArrayDeque<Integer> recentDirections = new ArrayDeque<>();

        private void initDistanceIfNeeded(double distance) {
            if (Double.isNaN(progressCheckpointDistance)) {
                progressCheckpointDistance = distance;
            }
        }

        private void resetProgress(double distance) {
            noProgressSeconds = 0d;
            progressCheckpointDistance = distance;
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
