package main.game.maze.common.movement;

/**
 * Backend-agnostic enemy AI. Both JavaFX and libGDX runtime adapters
 * delegate per-tick movement to an implementation of this interface so
 * the two frontends behave identically for any (enemy, world) pair.
 *
 * <p>Implementations MUST be pure: same inputs in, same outputs out, no
 * hidden state. Stateful concerns (animation clocks, RNG, etc.) belong
 * on the calling adapter, not here.
 */
public interface EnemyMovementService {

    /**
     * Compute the next position and direction for a single enemy.
     */
    MovementResult tick(EnemyState enemy, WorldView world);
}


