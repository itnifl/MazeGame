package main.game.maze.game.spawn;

import java.util.List;
import main.game.maze.game.hazard.MazeHazards;
import main.game.maze.game.spawn.HudZones.Rect;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;

/**
 * Where the player starts, chosen once for both frontends: the free corridor point (clear of every wall) nearest to the
 * original start that the HUD does not cover. World space, y-down; the point is the centre of the player.
 */
public final class PlayerSpawn {

    private PlayerSpawn() {
    }

    /**
     * @param originalX  the original start (centre of the player)
     * @param playerSize side of the player, so the whole player stays clear of the HUD
     * @return the spawn point, or the original start when the maze has no free points
     */
    public static Point2D find(GameMazeWorld world, double originalX, double originalY, double playerSize,
                               double boardWidth, double boardHeight) {
        return nearestClear(MazeHazards.freePoints(world), originalX, originalY, playerSize / 2.0,
                HudZones.covered(boardWidth, boardHeight));
    }

    /** The point of {@code free} nearest to the original start whose player square is outside every zone. */
    static Point2D nearestClear(List<Point2D> free, double originalX, double originalY, double halfPlayer, List<Rect> zones) {
        Point2D best = null;
        double bestDistance = Double.MAX_VALUE;
        for (Point2D point : free) {
            boolean covered = false;
            for (Rect zone : zones) {
                if (zone.containsWithMargin(point.getX(), point.getY(), halfPlayer)) {
                    covered = true;
                    break;
                }
            }
            double distance = Math.hypot(point.getX() - originalX, point.getY() - originalY);
            if (!covered && distance < bestDistance) {
                bestDistance = distance;
                best = point;
            }
        }
        return best != null ? best : new Point2D(originalX, originalY);
    }
}
