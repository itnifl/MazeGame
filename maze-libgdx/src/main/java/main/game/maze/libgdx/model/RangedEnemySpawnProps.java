package main.game.maze.libgdx.model;

/**
 * Immutable ranged-enemy parameters derived from {@link main.game.maze.opponents.RangedEnemy}.
 * Carried as a nullable component on {@link EnemySpawn}; null for non-ranged enemies.
 */
public record RangedEnemySpawnProps(
        double attackRange,
        int attackCooldownMs,
        double projectileSpeedPx,
        double splashRadius) {
}
