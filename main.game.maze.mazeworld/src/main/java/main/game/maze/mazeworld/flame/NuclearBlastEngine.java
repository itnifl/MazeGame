package main.game.maze.mazeworld.flame;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.mazeworld.BreakableWall;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;

/**
 * The nuclear blast (small nuclear grenade), shared by both frontends. Unlike the bomb's flames, which run along the
 * corridors and stop at walls ({@link DirectionalFlameEngine}), it is round and goes through walls: everything within
 * {@code radius} of the centre takes damage, the full damage in the inner core and less towards the edge, and every
 * breakable wall within the radius takes the same. World space.
 */
public final class NuclearBlastEngine {

    /** Share of the radius that takes the full damage. */
    public static final double CORE_SHARE = 0.35;
    /** Share of the damage at the very edge of the blast. */
    public static final double EDGE_SHARE = 0.25;

    private NuclearBlastEngine() {
    }

    /** The damage at {@code distance} from the centre: full in the core, falling off linearly to the edge share, 0 outside. */
    public static int damageAt(double distance, double radius, int damage) {
        if (radius <= 0 || distance > radius || damage <= 0) {
            return 0;
        }
        double core = radius * CORE_SHARE;
        if (distance <= core) {
            return damage;
        }
        double t = (distance - core) / (radius - core);
        return (int) Math.round(damage * (1.0 - t * (1.0 - EDGE_SHARE)));
    }

    /**
     * Sets the blast off.
     *
     * @param targets everything that can be hurt (pass-through targets, the player, are hurt too; walls do not shield)
     * @param world   the maze, whose breakable walls within the radius are damaged; may be null
     * @return the damage dealt to the targets that are not pass-through (enemies)
     */
    public static int apply(List<FlameTarget> targets, GameMazeWorld world, double centerX, double centerY,
                            double radius, int damage) {
        int dealt = 0;
        for (FlameTarget target : targets) {
            int hp = target.hitPoints();
            if (hp <= 0) {
                continue;
            }
            int amount = damageAt(Math.hypot(target.centerX() - centerX, target.centerY() - centerY), radius, damage);
            if (amount <= 0) {
                continue;
            }
            target.applyDamage(amount);
            if (!target.isPassThrough()) {
                dealt += Math.min(hp, amount);
            }
        }
        if (world != null) {
            // collect first: a wall that falls changes the world's lists
            List<BreakableWall> hit = new ArrayList<>();
            List<Integer> amounts = new ArrayList<>();
            for (BreakableWall wall : world.getBreakableWalls()) {
                int amount = damageAt(distanceToSegment(centerX, centerY, wall.geometry), radius, damage);
                if (amount > 0 && !wall.isDestroyed()) {
                    hit.add(wall);
                    amounts.add(amount);
                }
            }
            for (int i = 0; i < hit.size(); i++) {
                if (!hit.get(i).isDestroyed()) {
                    world.applyWallDamage(hit.get(i), amounts.get(i));
                }
            }
        }
        return dealt;
    }

    static double distanceToSegment(double px, double py, Vector2D segment) {
        double x1 = segment.getStart().getX();
        double y1 = segment.getStart().getY();
        double x2 = segment.getEnd().getX();
        double y2 = segment.getEnd().getY();
        double dx = x2 - x1;
        double dy = y2 - y1;
        double lengthSquared = dx * dx + dy * dy;
        double t = lengthSquared == 0 ? 0 : Math.max(0, Math.min(1, ((px - x1) * dx + (py - y1) * dy) / lengthSquared));
        return Math.hypot(px - (x1 + t * dx), py - (y1 + t * dy));
    }
}
