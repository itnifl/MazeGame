package main.game.maze.mazeworld.flame;

import java.util.List;
import main.game.maze.mazeworld.BreakableWall;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.WallCollisionUtil;

/**
 * Shared laser rifle shot used by both the JavaFX and libGDX frontends (same adapters as the
 * bomb flame: {@link FlameTarget} for enemies, world-space y-down coordinates).
 *
 * <p>Rules: the beam travels along {@code (dirX, dirY)} and stops at the first thing in its way.
 * An enemy takes the shot damage. A breakable wall takes the damage, except steel walls, which
 * the laser cannot harm. Solid walls and steel just stop the beam. Dead and pass-through targets
 * are ignored.
 */
public final class LaserBeamEngine {

    /** Half the width of the player's laser beam: enemies this close to the line are hit (both frontends). */
    public static final double PLAYER_BEAM_HALF_WIDTH = 14.0;

    /** What the beam hit. */
    public enum Outcome { ENEMY, WALL_DAMAGED, BLOCKED, NOTHING }

    /**
     * @param reach   how far the beam travelled (for drawing it)
     * @param outcome what stopped it
     */
    public record Shot(double reach, Outcome outcome) {
    }

    private LaserBeamEngine() {
    }

    /**
     * Fires one shot.
     *
     * @param candidates enemies that may be hit (adapted by the frontend; damage is applied as a shot)
     * @param world      maze world for breakable-wall lookup; null treats every wall as solid
     * @param walls      live wall segments ({@code world.getMazeVectors()})
     * @param dirX       +1 east, -1 west, 0 for north / south
     * @param dirY       +1 south, -1 north, 0 for east / west
     * @param halfWidth  half the width of the beam (how close to the line an enemy must be)
     */
    public static Shot fire(List<FlameTarget> candidates, GameMazeWorld world, List<Vector2D> walls,
                            double originX, double originY, int dirX, int dirY,
                            int damage, double maxRange, double halfWidth) {
        DirectionalFlameEngine.WallHit wallHit = walls == null || walls.isEmpty() ? null
                : DirectionalFlameEngine.findNextWallHit(originX, originY, dirX, dirY, walls, 0d, maxRange, halfWidth);
        double wallDistance = wallHit == null ? Double.POSITIVE_INFINITY : wallHit.distance();

        FlameTarget nearest = null;
        double nearestDistance = Double.POSITIVE_INFINITY;
        for (FlameTarget candidate : candidates) {
            if (candidate.isPassThrough() || candidate.hitPoints() <= 0) {
                continue;
            }
            Double distance = DirectionalFlameEngine.projectedDistanceOnRay(originX, originY,
                    candidate.centerX(), candidate.centerY(), dirX, dirY, maxRange, halfWidth);
            boolean shielded = walls != null && !walls.isEmpty() && WallCollisionUtil.wallBetweenVectors(
                    originX, originY, candidate.centerX(), candidate.centerY(), walls);
            if (distance != null && distance < nearestDistance && distance < wallDistance && !shielded) {
                nearest = candidate;
                nearestDistance = distance;
            }
        }
        if (nearest != null) {
            nearest.applyDamage(Math.max(0, damage));
            return new Shot(nearestDistance, Outcome.ENEMY);
        }
        if (wallHit == null) {
            return new Shot(maxRange, Outcome.NOTHING);
        }
        BreakableWall breakable = world != null ? world.findBreakableWall(wallHit.wall()) : null;
        if (breakable == null || breakable.material.isSteel()) {
            return new Shot(wallHit.distance(), Outcome.BLOCKED);
        }
        world.applyWallDamage(breakable, Math.max(0, damage));
        return new Shot(wallHit.distance(), Outcome.WALL_DAMAGED);
    }
}
