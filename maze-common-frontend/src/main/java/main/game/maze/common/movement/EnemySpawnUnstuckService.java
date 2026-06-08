package main.game.maze.common.movement;

/**
 * Shared enemy spawn correction.
 *
 * <p>If a spawn position overlaps a wall, searches outward on a grid and
 * returns the nearest collision-free center coordinate.
 */
public final class EnemySpawnUnstuckService {

    private EnemySpawnUnstuckService() {
    }

    public static SpawnResolution nudgeIfColliding(WorldView world,
                                                   double centerX,
                                                   double centerY,
                                                   double size) {
        if (world == null) {
            return new SpawnResolution(centerX, centerY, false);
        }

        double safeSize = Math.max(1d, size);
        if (!world.wouldCollide(centerX, centerY, safeSize)) {
            return new SpawnResolution(centerX, centerY, false);
        }

        double half = safeSize * 0.5d;
        double minX = world.minX() + half;
        double minY = world.minY() + half;
        double maxX = Math.max(minX, world.maxX() - half);
        double maxY = Math.max(minY, world.maxY() - half);
        double startX = clamp(centerX, minX, maxX);
        double startY = clamp(centerY, minY, maxY);

        double step = Math.max(2d, Math.max(safeSize * 0.25d, 6d));
        int maxRadius = Math.max(8, (int) Math.ceil(
                Math.max(maxX - minX, maxY - minY) / step));

        for (int radius = 1; radius <= maxRadius; radius++) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dy = -radius; dy <= radius; dy++) {
                    if (Math.abs(dx) != radius && Math.abs(dy) != radius) {
                        continue;
                    }
                    double candidateX = clamp(startX + dx * step, minX, maxX);
                    double candidateY = clamp(startY + dy * step, minY, maxY);
                    if (!world.wouldCollide(candidateX, candidateY, safeSize)) {
                        return new SpawnResolution(candidateX, candidateY, true);
                    }
                }
            }
        }

        return new SpawnResolution(startX, startY, false);
    }

    private static double clamp(double value, double min, double max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    public record SpawnResolution(double x, double y, boolean moved) {
    }
}
