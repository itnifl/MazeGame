package main.game.maze.common.movement;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Movement service for ghosts that are currently phasing through walls
 * ({@code nonTangibilityEnergy > 0}).
 *
 * <p>Phasing ghosts move in a single random cardinal direction, ignoring all
 * wall collisions. When the next position would leave the playfield boundary,
 * a new random cardinal direction is chosen and the position is clamped to keep
 * the ghost within the board.
 *
 * <p>Direction state is stored per-enemy keyed by {@link EnemyState#id()}.
 * Call {@link #reset()} when a game session ends or restarts to discard stale
 * state and ensure a fresh random direction on the next spawn.
 *
 * <p>Thread-safe: uses {@link ConcurrentHashMap} for per-enemy direction storage.
 */
public final class GhostPhasingMovementService {

    private static final int[][] CARDINALS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    private final ConcurrentHashMap<String, int[]> directions = new ConcurrentHashMap<>();
    private final Random random;

    /** Creates a service with a default (non-deterministic) random source. */
    public GhostPhasingMovementService() {
        this.random = new Random();
    }

    /** Package-private constructor for tests that need a seeded, deterministic RNG. */
    GhostPhasingMovementService(Random seededRandom) {
        this.random = seededRandom;
    }

    /**
     * Advances a phasing ghost one tick in its current direction.
     *
     * <p>Only board boundaries (from {@code world.minX/maxX/minY/maxY}) are
     * consulted. Wall collisions are deliberately ignored.
     *
     * <p>If the computed next position would leave the playfield, a new random
     * direction is chosen and the position is clamped to remain inside.
     *
     * @param state current enemy position, size, and speed
     * @param world board boundary provider
     * @return movement result containing the new position and the chosen direction
     */
    public MovementResult tick(EnemyState state, WorldView world) {
        int[] dir = directions.computeIfAbsent(state.id(), k -> randomDirection());

        double halfSize = state.size() * 0.5;
        double newX = state.x() + dir[0] * state.speed();
        double newY = state.y() + dir[1] * state.speed();

        if (outOfBounds(newX, newY, halfSize, world)) {
            dir = randomDirection();
            directions.put(state.id(), dir);
            newX = state.x() + dir[0] * state.speed();
            newY = state.y() + dir[1] * state.speed();
            // Clamp to ensure the ghost stays within the playfield even after re-pick.
            newX = Math.max(world.minX() + halfSize, Math.min(world.maxX() - halfSize, newX));
            newY = Math.max(world.minY() + halfSize, Math.min(world.maxY() - halfSize, newY));
        }

        return new MovementResult(newX, newY, dir[0], dir[1], true);
    }

    /**
     * Clears all per-enemy direction state.
     * Call at game-session start/restart so each new ghost gets a fresh direction.
     */
    public void reset() {
        directions.clear();
    }

    private boolean outOfBounds(double x, double y, double halfSize, WorldView world) {
        return x - halfSize < world.minX()
                || x + halfSize > world.maxX()
                || y - halfSize < world.minY()
                || y + halfSize > world.maxY();
    }

    private int[] randomDirection() {
        return CARDINALS[random.nextInt(CARDINALS.length)];
    }
}


