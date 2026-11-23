package main.game.maze.game.strike;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import main.game.maze.game.items.PickupSpec;
import main.game.maze.game.level.LevelBoosts;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.flame.DirectionalFlameEngine;

/**
 * Where and when the blasts of the shop weapons go off, the same in both frontends. Everything is in world space (y down,
 * the space of {@code GameMazeWorld}); the libGDX frontend converts to and from its y-up screen space. The values come from
 * the items model ({@code xmi/items/items.xmi}).
 */
public final class StrikePlanner {

    /** A thrown grenade stops this far short of the wall it would hit, so it lands in the corridor, not in the wall. */
    public static final double LANDING_MARGIN = 10.0;
    /** Half the width of the line a grenade flies along when it is checked against walls. */
    static final double THROW_HALF_WIDTH = 2.0;
    /** The airstrike aims at the centre of the maze, give or take this share of the board size. */
    public static final double AIM_SCATTER = 0.08;
    /** The bunker buster's blast is set this far back from where the rocket stopped, so it is not inside a wall. */
    public static final double IMPACT_STEP_BACK = 8.0;

    private StrikePlanner() {
    }

    /**
     * How far a grenade thrown from (x, y) along (dirX, dirY) flies: its full range, or short of the first wall in the way.
     */
    public static double throwDistance(double x, double y, int dirX, int dirY, double maxRange, List<Vector2D> walls) {
        double range = Math.max(0.0, maxRange);
        if (walls == null || walls.isEmpty() || (dirX == 0 && dirY == 0)) {
            return range;
        }
        double toWall = DirectionalFlameEngine.flameVisualRange(x, y, dirX, dirY, walls, range, THROW_HALF_WIDTH);
        return toWall >= range ? range : Math.max(0.0, toWall - LANDING_MARGIN);
    }

    /** A thrown grenade (grenade, plasma grenade, small nuclear grenade): it lands along the facing and goes off after its fuse. */
    public static ScheduledBlast thrownGrenade(PickupSpec grenade, double x, double y, int dirX, int dirY, List<Vector2D> walls) {
        double distance = throwDistance(x, y, dirX, dirY, grenade.range(), walls);
        return new ScheduledBlast(grenade.fuseSeconds(), grenade.fuseSeconds(), x + dirX * distance, y + dirY * distance,
                grenade.damage(), grenade.blastRadius(), true, true, grenade.displayName());
    }

    /** A fat bomb planted at the player's feet; the "extra long flames" boost makes it stronger, like the bomb. */
    public static ScheduledBlast fatBomb(PickupSpec fatBomb, double x, double y, LevelBoosts boosts) {
        LevelBoosts active = boosts == null ? LevelBoosts.NONE : boosts;
        return new ScheduledBlast(fatBomb.fuseSeconds(), fatBomb.fuseSeconds(), x, y,
                active.boostedBombDamage(fatBomb.damage()), active.boostedBombRange(fatBomb.blastRadius()), true, true,
                fatBomb.displayName());
    }

    /**
     * The bunker buster's blast where its rocket stopped: {@code reach} along (dirX, dirY) from (x, y), set back a little
     * so it goes off in the open, not inside the wall that stopped it.
     */
    public static ScheduledBlast bunkerBusterImpact(PickupSpec buster, double x, double y, int dirX, int dirY, double reach) {
        double at = Math.max(0.0, reach - IMPACT_STEP_BACK);
        return new ScheduledBlast(0.0, 0.0, x + dirX * at, y + dirY * at, buster.damage(), buster.blastRadius(), true, false,
                buster.displayName());
    }

    /**
     * The bombs of an airstrike: {@code units} bombs at random within {@code range} of the approximate centre of the board
     * (the centre, give or take {@link #AIM_SCATTER} of the board), the first after {@code fuseSeconds}, the rest spread over
     * {@code durationSeconds}. They never hurt the player who called them in.
     */
    public static List<ScheduledBlast> airstrike(PickupSpec airstrike, double boardWidth, double boardHeight, Random random) {
        int bombs = Math.max(1, airstrike.units());
        double aimX = boardWidth / 2 + (random.nextDouble() * 2 - 1) * AIM_SCATTER * boardWidth;
        double aimY = boardHeight / 2 + (random.nextDouble() * 2 - 1) * AIM_SCATTER * boardHeight;
        double spread = Math.max(0.0, airstrike.range());
        double step = bombs > 1 ? Math.max(0.0, airstrike.durationSeconds()) / (bombs - 1) : 0.0;
        List<ScheduledBlast> plan = new ArrayList<>(bombs);
        for (int i = 0; i < bombs; i++) {
            // uniformly over the disc round the aim point
            double angle = random.nextDouble() * Math.PI * 2;
            double distance = Math.sqrt(random.nextDouble()) * spread;
            double x = clamp(aimX + Math.cos(angle) * distance, 0, boardWidth);
            double y = clamp(aimY + Math.sin(angle) * distance, 0, boardHeight);
            double delay = airstrike.fuseSeconds() + i * step;
            plan.add(new ScheduledBlast(delay, delay, x, y, airstrike.damage(), airstrike.blastRadius(), false, false,
                    airstrike.displayName()));
        }
        return plan;
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
