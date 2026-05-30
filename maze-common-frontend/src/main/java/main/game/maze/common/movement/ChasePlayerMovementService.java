package main.game.maze.common.movement;

/**
 * Default {@link EnemyMovementService}: picks the dominant-axis cardinal
 * step toward the player (mirrors the JavaFX AGGRESSIVE behaviour) and,
 * when that step would walk into a wall, falls back to the other axis or
 * the reverse direction so the enemy keeps making progress instead of
 * idling in a circle.
 *
 * <p>This is the single source of truth shared by the JavaFX and libGDX
 * runtimes through their respective {@link WorldView} adapters.
 */
public final class ChasePlayerMovementService implements EnemyMovementService {

    private static final int[][] FALLBACK_STEPS = {
            { 1, 0}, {-1, 0}, {0,  1}, {0, -1}
    };

    @Override
    public MovementResult tick(EnemyState enemy, WorldView world) {
        int[] desired = GridDirection.directionBetween(enemy.x(), enemy.y(),
                world.playerX(), world.playerY());

        // Try the dominant-axis step first.
        MovementResult attempt = tryStep(enemy, world, desired[0], desired[1]);
        if (attempt.moved()) {
            return attempt;
        }

        // Try the other axis next, biased toward closing distance to the player.
        double dx = world.playerX() - enemy.x();
        double dy = world.playerY() - enemy.y();
        int altX = desired[0] == 0 ? (int) Math.signum(dx) : 0;
        int altY = desired[1] == 0 ? (int) Math.signum(dy) : 0;
        if (altX != 0 || altY != 0) {
            attempt = tryStep(enemy, world, altX, altY);
            if (attempt.moved()) {
                return attempt;
            }
        }

        // Last resort: any cardinal direction that is not blocked, so a
        // walled-in enemy still picks a deliberate direction instead of
        // standing still or orbiting.
        for (int[] step : FALLBACK_STEPS) {
            if (step[0] == desired[0] && step[1] == desired[1]) {
                continue;
            }
            attempt = tryStep(enemy, world, step[0], step[1]);
            if (attempt.moved()) {
                return attempt;
            }
        }

        // Fully boxed in; stay put but remember the desired heading so the
        // caller still knows which way this enemy is facing.
        return new MovementResult(enemy.x(), enemy.y(), desired[0], desired[1], false);
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
}
