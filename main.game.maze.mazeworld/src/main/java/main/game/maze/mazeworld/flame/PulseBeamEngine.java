package main.game.maze.mazeworld.flame;

import java.util.List;
import main.game.maze.mazeworld.BreakableWall;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;

/**
 * The pulse beam cannon shot, shared by both frontends. It works like the {@link LaserBeamEngine} shot with one
 * difference: the beam carries its {@code power} as a budget, and a breakable wall that is not strong enough to stop it
 * does not: the wall is destroyed, the wall's hit points are taken from the power and the beam goes on through the gap
 * to the next thing in its way, until it has no power left, hits an enemy, meets steel or a solid wall, or runs out of range.
 */
public final class PulseBeamEngine {

    /** Distance the beam moves on beyond a wall it has broken through, so the broken wall is not met again. */
    private static final double STEP_PAST_WALL = 1.0;

    /** Safety stop: no maze has more walls than this in one straight line. */
    private static final int MAX_WALLS = 64;

    private PulseBeamEngine() {
    }

    /** As {@link LaserBeamEngine#fire}, with {@code power} in place of the damage, and the beam piercing weak walls. */
    public static LaserBeamEngine.Shot fire(List<FlameTarget> candidates, GameMazeWorld world, List<Vector2D> walls,
                                            double originX, double originY, int dirX, int dirY,
                                            int power, double maxRange, double halfWidth) {
        int left = Math.max(0, power);
        double travelled = 0;
        for (int i = 0; i < MAX_WALLS; i++) {
            double x = originX + dirX * travelled;
            double y = originY + dirY * travelled;
            double range = maxRange - travelled;
            DirectionalFlameEngine.WallHit hit = walls == null || walls.isEmpty() || range <= 0 ? null
                    : DirectionalFlameEngine.findNextWallHit(x, y, dirX, dirY, walls, 0d, range, halfWidth);
            BreakableWall breakable = hit == null || world == null ? null : world.findBreakableWall(hit.wall());
            int wallHp = breakable == null ? 0 : breakable.getRemainingHp();
            LaserBeamEngine.Shot shot = LaserBeamEngine.fire(candidates, world, walls, x, y, dirX, dirY, left, range, halfWidth);
            boolean brokenThrough = shot.outcome() == LaserBeamEngine.Outcome.WALL_DAMAGED && left >= wallHp
                    && breakable.isDestroyed();
            if (!brokenThrough) {
                return new LaserBeamEngine.Shot(travelled + shot.reach(), shot.outcome());
            }
            left -= wallHp;
            travelled += shot.reach() + STEP_PAST_WALL;
            if (left <= 0 || travelled >= maxRange) {
                return new LaserBeamEngine.Shot(Math.min(travelled, maxRange), LaserBeamEngine.Outcome.WALL_DAMAGED);
            }
        }
        return new LaserBeamEngine.Shot(Math.min(travelled, maxRange), LaserBeamEngine.Outcome.WALL_DAMAGED);
    }
}
