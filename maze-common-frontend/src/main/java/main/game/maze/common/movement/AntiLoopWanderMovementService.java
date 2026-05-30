package main.game.maze.common.movement;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

/**
 * Stateful wander movement that avoids small repeating loops.
 *
 * <p>The service keeps a short history of visited cells per enemy and picks
 * the passable cardinal move that leads to the least recently visited cell.
 * This breaks common square and ping-pong loops while preserving a wander-like
 * non-targeted behaviour.
 */
public final class AntiLoopWanderMovementService {

    private static final int[][] CARDINAL = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };

    private static final int HISTORY_SIZE = 20;
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
        RuntimeState state = states.computeIfAbsent(id, ignored -> new RuntimeState());
        remember(state, cellKey(enemy.x(), enemy.y(), enemy.speed()));

        int reverseX = -enemy.directionX();
        int reverseY = -enemy.directionY();
        int seed = Math.floorMod(id.hashCode() + state.tick, CARDINAL.length);

        MovementResult best = null;
        int bestScore = Integer.MAX_VALUE;

        for (int i = 0; i < CARDINAL.length; i++) {
            int idx = Math.floorMod(seed + i, CARDINAL.length);
            int dx = CARDINAL[idx][0];
            int dy = CARDINAL[idx][1];

            MovementResult candidate = tryStep(enemy, world, dx, dy);
            if (!candidate.moved()) {
                continue;
            }

            long nextCell = cellKey(candidate.x(), candidate.y(), enemy.speed());
            int visits = state.visitCounts.getOrDefault(nextCell, 0);
            int reversePenalty = (dx == reverseX && dy == reverseY) ? 2 : 0;
            int keepHeadingPenalty = (dx == enemy.directionX() && dy == enemy.directionY()) ? 0 : 1;
            int score = visits * 10 + reversePenalty + keepHeadingPenalty;

            if (score < bestScore) {
                bestScore = score;
                best = candidate;
            }
        }

        state.tick++;
        if (best != null) {
            remember(state, cellKey(best.x(), best.y(), enemy.speed()));
            return best;
        }

        return new MovementResult(enemy.x(), enemy.y(), enemy.directionX(), enemy.directionY(), false);
    }

    private static MovementResult tryStep(EnemyState enemy, WorldView world, int dx, int dy) {
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
        state.visitCounts.put(key, state.visitCounts.getOrDefault(key, 0) + 1);

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
        private int tick;
    }
}
