package main.game.maze.common.movement;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Wander movement that walks in one cardinal direction until it collides,
 * then picks a random other (non-reverse) cardinal that is currently passable.
 *
 * <p>Two reasons for this contract:
 * <ul>
 *   <li>No shaking. The previous "least-recently-visited" scoring rotated
 *       direction every tick because every freshly visited cell scored higher
 *       than its neighbours, which players experienced as jitter.</li>
 *   <li>No circles. By forcing direction changes only on collision and never
 *       picking the immediate reverse, a wanderer cannot oscillate between two
 *       adjacent cells.</li>
 * </ul>
 */
public final class AntiLoopWanderMovementService {

    private static final int[][] CARDINAL = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };

    /**
     * Hard guarantee that a wanderer never visits the same cell more than this
     * many times in the recent history window. If exceeded we force a fresh
     * direction even if the current heading is still passable.
     */
    private static final int MAX_VISITS_PER_CELL = 3;
    private static final int HISTORY_SIZE = 24;
    private static final double GRID_MIN = 2.0d;

    private final Map<String, RuntimeState> states = new HashMap<>();

    public void reset() {
        states.clear();
    }

    public MovementResult tick(EnemyState enemy, WorldView world) {
        if (enemy == null || world == null) {
            return new MovementResult(0d, 0d, 0, 0, false);
        }

        String id = enemy.id() == null ? "<anonymous>" : enemy.id();
        RuntimeState state = states.computeIfAbsent(id, ignored -> new RuntimeState(id));
        remember(state, cellKey(enemy.x(), enemy.y(), enemy.speed()));

        int dx = enemy.directionX();
        int dy = enemy.directionY();

        if (dx == 0 && dy == 0) {
            int[] kick = pickRandomCardinal(state, enemy, world, 0, 0);
            if (kick == null) {
                return new MovementResult(enemy.x(), enemy.y(), 0, 0, false);
            }
            dx = kick[0];
            dy = kick[1];
        }

        boolean forceChange = anticipatedCellWouldExceedVisitCap(state, enemy, dx, dy);
        if (!forceChange) {
            MovementResult forward = tryStep(enemy, world, dx, dy);
            if (forward.moved()) {
                remember(state, cellKey(forward.x(), forward.y(), enemy.speed()));
                return forward;
            }
        }

        int[] alt = pickRandomCardinal(state, enemy, world, dx, dy);
        if (alt == null) {
            MovementResult reverse = tryStep(enemy, world, -dx, -dy);
            if (reverse.moved()) {
                remember(state, cellKey(reverse.x(), reverse.y(), enemy.speed()));
                return reverse;
            }
            return new MovementResult(enemy.x(), enemy.y(), dx, dy, false);
        }
        MovementResult moved = tryStep(enemy, world, alt[0], alt[1]);
        if (moved.moved()) {
            remember(state, cellKey(moved.x(), moved.y(), enemy.speed()));
            return moved;
        }
        return new MovementResult(enemy.x(), enemy.y(), alt[0], alt[1], false);
    }

    private boolean anticipatedCellWouldExceedVisitCap(RuntimeState state,
                                                       EnemyState enemy,
                                                       int dx,
                                                       int dy) {
        if (dx == 0 && dy == 0) {
            return false;
        }
        double nx = enemy.x() + dx * enemy.speed();
        double ny = enemy.y() + dy * enemy.speed();
        long key = cellKey(nx, ny, enemy.speed());
        return state.visitCounts.getOrDefault(key, 0) >= MAX_VISITS_PER_CELL;
    }

    private int[] pickRandomCardinal(RuntimeState state,
                                     EnemyState enemy,
                                     WorldView world,
                                     int currentDx,
                                     int currentDy) {
        int reverseX = -currentDx;
        int reverseY = -currentDy;

        List<int[]> candidates = new ArrayList<>(4);
        for (int[] c : CARDINAL) {
            if (c[0] == currentDx && c[1] == currentDy) {
                continue;
            }
            if (c[0] == reverseX && c[1] == reverseY && (currentDx != 0 || currentDy != 0)) {
                continue;
            }
            double nx = enemy.x() + c[0] * enemy.speed();
            double ny = enemy.y() + c[1] * enemy.speed();
            if (world.wouldCollide(nx, ny, enemy.size())) {
                continue;
            }
            long key = cellKey(nx, ny, enemy.speed());
            if (state.visitCounts.getOrDefault(key, 0) >= MAX_VISITS_PER_CELL) {
                continue;
            }
            candidates.add(c);
        }
        if (candidates.isEmpty()) {
            for (int[] c : CARDINAL) {
                if (c[0] == reverseX && c[1] == reverseY && (currentDx != 0 || currentDy != 0)) {
                    continue;
                }
                if (c[0] == currentDx && c[1] == currentDy) {
                    continue;
                }
                double nx = enemy.x() + c[0] * enemy.speed();
                double ny = enemy.y() + c[1] * enemy.speed();
                if (!world.wouldCollide(nx, ny, enemy.size())) {
                    candidates.add(c);
                }
            }
        }
        if (candidates.isEmpty()) {
            return null;
        }
        int idx = candidates.size() == 1 ? 0 : state.random.nextInt(candidates.size());
        return candidates.get(idx);
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

    private static long cellKey(double x, double y, double speed) {
        double cell = Math.max(GRID_MIN, Math.max(1d, speed));
        long qx = Math.round(x / cell);
        long qy = Math.round(y / cell);
        return (qx << 32) ^ (qy & 0xffffffffL);
    }

    private static void remember(RuntimeState state, long key) {
        state.recentCells.addLast(key);
        state.visitCounts.merge(key, 1, Integer::sum);
        while (state.recentCells.size() > HISTORY_SIZE) {
            long removed = state.recentCells.removeFirst();
            int next = state.visitCounts.getOrDefault(removed, 0) - 1;
            if (next <= 0) {
                state.visitCounts.remove(removed);
            } else {
                state.visitCounts.put(removed, next);
            }
        }
    }

    private static final class RuntimeState {
        private final ArrayDeque<Long> recentCells = new ArrayDeque<>();
        private final Map<Long, Integer> visitCounts = new HashMap<>();
        private final Random random;

        private RuntimeState(String id) {
            this.random = new Random(id.hashCode() == 0 ? 1L : id.hashCode());
        }
    }
}
