package main.game.maze.mazeworld;

/**
 * Concentric rectangular zones around the heart (the board centre). Zone 0 is the outer ring and the
 * last zone holds the heart; the rings are scaled to the board so each zone is the same share of the
 * distance from the heart to the nearest board edge in its direction.
 *
 * @param centerX   heart x
 * @param centerY   heart y
 * @param halfWidth half the board width (distance from the heart to the left / right edge)
 * @param halfHeight half the board height
 * @param zoneCount number of rings (at least 1)
 */
public record ZoneGeometry(double centerX, double centerY, double halfWidth, double halfHeight, int zoneCount) {

    public ZoneGeometry {
        if (halfWidth <= 0 || halfHeight <= 0) {
            throw new IllegalArgumentException("half width and height must be positive");
        }
        if (zoneCount < 1) {
            throw new IllegalArgumentException("zoneCount must be at least 1");
        }
    }

    /** A geometry for a board of this size, centred on it like the heart. */
    public static ZoneGeometry forBoard(double boardWidth, double boardHeight, int zoneCount) {
        return new ZoneGeometry(boardWidth / 2.0, boardHeight / 2.0, boardWidth / 2.0, boardHeight / 2.0, zoneCount);
    }

    /** The zone of a point: 0 = outer ring ... {@code zoneCount - 1} = around the heart. */
    public int zoneOf(double x, double y) {
        double distance = Math.max(Math.abs(x - centerX) / halfWidth, Math.abs(y - centerY) / halfHeight);
        int fromHeart = (int) Math.min(zoneCount - 1, Math.floor(distance * zoneCount));
        return zoneCount - 1 - fromHeart;
    }
}
