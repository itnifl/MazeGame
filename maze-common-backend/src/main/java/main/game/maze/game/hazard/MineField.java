package main.game.maze.game.hazard;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import main.game.maze.game.level.MineSpec;
import main.game.maze.mazeworld.Point2D;

/**
 * The pulsating mines round the sergeants and the holes they leave, shared by both frontends. Coordinates
 * are the shared world space (y-down); a frontend that draws in another space converts.
 *
 * <p>A player who touches a mine sets it off: the mine is used up and leaves a hole in the ground. A
 * player whose centre is over a hole falls in; the frontend then teleports them (see {@link EdgeTeleport}).
 * The blast itself (walls, enemies) is the frontend's job, with {@link MineSpec#blastDamage()} and
 * {@link MineSpec#blastRadius()}.</p>
 */
public final class MineField {

    /** Mines never sit closer together than this. */
    private static final double MIN_SPACING = 14.0;
    /** Radii tried for a mine, as shares of the ring radius, when the exact spot is not free. */
    private static final double[] RADIUS_TRIES = {1.0, 0.8, 1.2, 0.6, 1.4};

    /** One mine; {@code phase} (0..1) keeps neighbours from pulsing in step. */
    public record Mine(int id, double x, double y, double phase) {
    }

    public record Hole(double x, double y, double radius) {
    }

    /** A mine that went off, and the hole it left. */
    public record Detonation(Mine mine, Hole hole) {
    }

    /**
     * What one step did.
     *
     * @param fellInto the hole the player is over (a new one or an old one), or null
     */
    public record Update(List<Detonation> detonations, Hole fellInto) {
    }

    private final MineSpec spec;
    private final List<Mine> mines = new ArrayList<>();
    private final List<Hole> holes = new ArrayList<>();
    private double clock;
    private int nextId = 1;

    public MineField(MineSpec spec) {
        this.spec = spec;
    }

    public MineSpec spec() {
        return spec;
    }

    public List<Mine> mines() {
        return List.copyOf(mines);
    }

    public List<Hole> holes() {
        return List.copyOf(holes);
    }

    /** Seconds the field has been running; drives the pulse. */
    public double clock() {
        return clock;
    }

    /**
     * Puts a circle of mines round every post. A mine whose exact spot is not free slides along the same line
     * to another radius; one with no free spot, or hidden from its sergeant by a wall, is left out.
     *
     * @param free      whether a mine may sit at a point (clear of walls)
     * @param clearLine whether nothing blocks the way from the post to a mine (no wall between)
     */
    public void place(List<Point2D> posts, Predicate<Point2D> free, BiPredicate<Point2D, Point2D> clearLine) {
        for (int p = 0; p < posts.size(); p++) {
            Point2D post = posts.get(p);
            for (int k = 0; k < spec.count(); k++) {
                double angle = 2 * Math.PI * k / spec.count();
                for (double share : RADIUS_TRIES) {
                    double r = spec.ringRadius() * share;
                    Point2D spot = new Point2D(post.getX() + Math.cos(angle) * r, post.getY() + Math.sin(angle) * r);
                    if (free.test(spot) && clearLine.test(post, spot) && !crowded(spot)) {
                        double phase = (k * 0.37 + p * 0.13) % 1.0;
                        mines.add(new Mine(nextId++, spot.getX(), spot.getY(), phase));
                        break;
                    }
                }
            }
        }
    }

    private boolean crowded(Point2D spot) {
        for (Mine mine : mines) {
            if (Math.hypot(mine.x() - spot.getX(), mine.y() - spot.getY()) < MIN_SPACING) {
                return true;
            }
        }
        return false;
    }

    /**
     * Advances the field and checks the player: mines within {@link MineSpec#triggerRadius()} of the player
     * go off, each leaving a hole; the player falls if their centre is over a hole.
     *
     * @param dt seconds since the last step
     */
    public Update update(double dt, double playerX, double playerY, double playerRadius) {
        clock += Math.max(0, dt);
        List<Detonation> detonations = new ArrayList<>();
        for (Mine mine : List.copyOf(mines)) {
            if (Math.hypot(mine.x() - playerX, mine.y() - playerY) <= spec.triggerRadius()) {
                mines.remove(mine);
                Hole hole = new Hole(mine.x(), mine.y(), spec.holeRadius());
                holes.add(hole);
                detonations.add(new Detonation(mine, hole));
            }
        }
        Hole fellInto = null;
        for (Hole hole : holes) {
            if (Math.hypot(hole.x() - playerX, hole.y() - playerY) <= hole.radius()) {
                fellInto = hole;
                break;
            }
        }
        return new Update(detonations, fellInto);
    }

    /** How bright a mine glows at {@code clockSeconds}: 0 (dim) to 1 (bright), once per beat. */
    public static double glow(Mine mine, double clockSeconds, double pulseSeconds) {
        return 0.5 + 0.5 * Math.sin(2 * Math.PI * (clockSeconds / pulseSeconds + mine.phase()));
    }

    /** New level: no mines, no holes. */
    public void clear() {
        mines.clear();
        holes.clear();
        clock = 0;
        nextId = 1;
    }
}
