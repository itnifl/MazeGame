package main.game.maze.game.lava;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.game.maze.game.items.PickupField;
import main.game.maze.game.level.LavaSpec;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.LavaField;
import main.game.maze.mazeworld.LavaField.Bridge;
import main.game.maze.mazeworld.LavaField.Stream;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.service.MazeNavigationGraphService;

/**
 * Makes the lava of a level: random snaking streams across the board, and bridges over each. The streams are
 * different every game, but the maze can always be solved: the way from the start to the heart crosses every stream
 * only where there is a bridge. Bridges over the way are put where it crosses, the others at random places in the
 * corridors. Shared by both frontends; world space (y-down).
 */
public final class LavaGenerator {

    private static final Logger LOGGER = Logger.getLogger(LavaGenerator.class.getName());
    private static final int ATTEMPTS = 20000;
    private static final double STEP = 12.0;
    /** The start and the heart are kept this far clear of the lava (beyond its half width), a little more than the player. */
    private static final double START_CLEARANCE = 45.0;
    private static final double HEART_CLEARANCE = 40.0;
    /** A bridge needs room: no wall closer than this to its middle. */
    private static final double BRIDGE_WALL_CLEARANCE = 22.0;
    private static final double BRIDGE_SPACING = 120.0;
    private static final double SAMPLE = 2.0;

    private LavaGenerator() {
    }

    /**
     * @param start where the player starts; @param heart where the heart is (both world space)
     */
    public static LavaField generate(LavaSpec spec, GameMazeWorld world, int boardWidth, int boardHeight,
                                     Point2D start, Point2D heart, Random random) {
        List<Point2D> way = world.getNavigationGraph() == null ? List.of()
                : MazeNavigationGraphService.findPath(world.getNavigationGraph(), start, heart);
        // each stream is made on its own, tried again until the way to the heart crosses it only where one bridge can
        // cover the crossing (and not more often than there are bridges)
        List<Stream> streams = new ArrayList<>();
        for (int i = 0; i < spec.streamCount(); i++) {
            Stream stream = null;
            for (int attempt = 0; attempt < ATTEMPTS && stream == null; attempt++) {
                List<Point2D> path = snake(boardWidth, boardHeight, spec.width(), random, i);
                if (LavaField.distanceToPath(path, start.getX(), start.getY()) < spec.width() / 2.0 + START_CLEARANCE
                        || LavaField.distanceToPath(path, heart.getX(), heart.getY()) < spec.width() / 2.0 + HEART_CLEARANCE) {
                    continue;
                }
                List<Bridge> bridges = bridgesFor(path, way, spec, world, random);
                if (bridges != null) {
                    stream = new Stream(path, bridges);
                }
            }
            if (stream == null) {
                LOGGER.log(Level.WARNING, "No lava stream with a solvable maze found in {0} tries; leaving it out", ATTEMPTS);
            } else {
                streams.add(stream);
            }
        }
        return new LavaField(streams, spec.width());
    }

    /** Whether the way from the start to the heart runs through no deadly lava. */
    public static boolean solvable(LavaField field, List<Point2D> way) {
        for (int i = 0; i + 1 < way.size(); i++) {
            if (field.crossesDeadly(way.get(i).getX(), way.get(i).getY(), way.get(i + 1).getX(), way.get(i + 1).getY())) {
                return false;
            }
        }
        return true;
    }

    // ---------------------------------------------------------------- streams

    /** A stream that snakes from one side of the board to the other, flowing along its points. */
    static List<Point2D> snake(int width, int height, double streamWidth, Random random, int index) {
        boolean horizontal = random.nextBoolean();
        double length = horizontal ? width : height;
        double across = horizontal ? height : width;
        double margin = streamWidth + 20;
        double base = across * (0.2 + 0.6 * random.nextDouble());
        double amplitude = 60 + 70 * random.nextDouble();
        double wavelength = 260 + 170 * random.nextDouble();
        double phase = random.nextDouble() * 2 * Math.PI;
        double amplitude2 = 15 + 25 * random.nextDouble();
        double wavelength2 = wavelength / (1.8 + random.nextDouble());
        double phase2 = random.nextDouble() * 2 * Math.PI;
        boolean reverse = random.nextBoolean();
        List<Point2D> path = new ArrayList<>();
        for (double along = -STEP; along <= length + STEP; along += STEP) {
            double offset = base + amplitude * Math.sin(2 * Math.PI * along / wavelength + phase)
                    + amplitude2 * Math.sin(2 * Math.PI * along / wavelength2 + phase2);
            offset = Math.max(margin, Math.min(across - margin, offset));
            path.add(horizontal ? new Point2D(along, offset) : new Point2D(offset, along));
        }
        if (reverse) {
            java.util.Collections.reverse(path);
        }
        return path;
    }

    private static boolean clearOf(List<List<Point2D>> paths, double width, Point2D point, double clearance) {
        for (List<Point2D> path : paths) {
            if (LavaField.distanceToPath(path, point.getX(), point.getY()) < width / 2.0 + clearance) {
                return false;
            }
        }
        return true;
    }

    // ---------------------------------------------------------------- bridges

    /**
     * The bridges over one stream, or null when the way to the heart crosses it in more places than there are
     * bridges (or runs along it).
     */
    private static List<Bridge> bridgesFor(List<Point2D> path, List<Point2D> way, LavaSpec spec, GameMazeWorld world,
                                           Random random) {
        List<Bridge> bridges = new ArrayList<>();
        double half = spec.width() / 2.0;
        // walk the way in small steps; every stretch of it that lies in the stream needs one bridge over all of it
        List<Point2D> run = new ArrayList<>();
        Point2D previous = null;
        for (int i = 0; i + 1 < way.size(); i++) {
            Point2D a = way.get(i);
            Point2D b = way.get(i + 1);
            int steps = Math.max(1, (int) Math.ceil(Math.hypot(b.getX() - a.getX(), b.getY() - a.getY()) / SAMPLE));
            for (int k = 0; k <= steps; k++) {
                double t = (double) k / steps;
                Point2D p = new Point2D(a.getX() + (b.getX() - a.getX()) * t, a.getY() + (b.getY() - a.getY()) * t);
                boolean in = LavaField.distanceToPath(path, p.getX(), p.getY()) <= half;
                if (in) {
                    if (run.isEmpty() && previous != null) {
                        run.add(previous);   // the sample just before: the bridge must reach the very edge of the stream
                    }
                    run.add(p);
                } else if (!run.isEmpty()) {
                    run.add(p);   // and the sample just after
                    if (!addBridgeOver(bridges, path, run, spec) || bridges.size() > spec.bridgesPerStream()) {
                        return null;   // too many crossings for the bridges, or one bridge cannot cover this one
                    }
                    run = new ArrayList<>();
                }
                previous = p;
            }
        }
        if (!run.isEmpty() && !addBridgeOver(bridges, path, run, spec)) {
            return null;
        }
        if (bridges.size() > spec.bridgesPerStream()) {
            return null;
        }
        List<Vector2D> walls = world.getMazeVectors();
        for (int tries = 0; bridges.size() < spec.bridgesPerStream() && tries < 400; tries++) {
            Point2D candidate = path.get(random.nextInt(path.size()));
            if (clearOfWalls(candidate, walls) && apart(candidate, bridges)) {
                bridges.add(bridgeAt(path, candidate, spec));
            }
        }
        return bridges;
    }

    /** Adds a bridge over a stretch of the way that lies in the stream; false when one bridge cannot cover all of it. */
    private static boolean addBridgeOver(List<Bridge> bridges, List<Point2D> path, List<Point2D> run, LavaSpec spec) {
        Point2D first = run.get(0);
        Point2D last = run.get(run.size() - 1);
        Bridge bridge = bridgeAt(path, new Point2D((first.getX() + last.getX()) / 2.0, (first.getY() + last.getY()) / 2.0), spec);
        for (Point2D p : run) {
            if (!LavaField.bridgeCovers(bridge, spec.width(), p.getX(), p.getY())) {
                return false;
            }
        }
        bridges.add(bridge);
        return true;
    }

    /** A bridge over the stream at the point of its middle line nearest to {@code near}. */
    private static Bridge bridgeAt(List<Point2D> path, Point2D near, LavaSpec spec) {
        int best = 0;
        double bestDistance = Double.MAX_VALUE;
        for (int i = 0; i < path.size(); i++) {
            double d = Math.hypot(path.get(i).getX() - near.getX(), path.get(i).getY() - near.getY());
            if (d < bestDistance) {
                bestDistance = d;
                best = i;
            }
        }
        Point2D before = path.get(Math.max(0, best - 1));
        Point2D after = path.get(Math.min(path.size() - 1, best + 1));
        double angle = Math.atan2(after.getY() - before.getY(), after.getX() - before.getX());
        return new Bridge(path.get(best).getX(), path.get(best).getY(), angle, spec.bridgeLength());
    }

    private static boolean clearOfWalls(Point2D point, List<Vector2D> walls) {
        for (Vector2D wall : walls) {
            if (PickupField.distanceToSegment(point.getX(), point.getY(), wall) < BRIDGE_WALL_CLEARANCE) {
                return false;
            }
        }
        return true;
    }

    private static boolean apart(Point2D point, List<Bridge> bridges) {
        for (Bridge bridge : bridges) {
            if (Math.hypot(point.getX() - bridge.x(), point.getY() - bridge.y()) < BRIDGE_SPACING) {
                return false;
            }
        }
        return true;
    }
}
