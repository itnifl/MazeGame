package main.game.maze.game.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import main.game.maze.game.hazard.EdgeTeleport;
import main.game.maze.game.hazard.MazeHazards;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.constants.StageConstants;

/**
 * Where a level puts its enemies in a generated maze, chosen once for both frontends from the level's values:
 * the sergeants' posts near the heart and the spots of the portal ghosts. All points are free corridor points
 * (clear of every wall) in the shared world space (y-down); a frontend that draws in another space converts them.
 */
public final class LevelPlacement {

    /** Sergeant posts keep at least this far from each other (two sergeant sizes). */
    public static final double SERGEANT_POST_SPACING = StageConstants.ZombieCharacterXYSize * 2.0;
    /** Portal ghosts do not start closer to the player's start than this. */
    public static final double PORTAL_GHOST_MIN_START_DISTANCE = 200.0;
    /** Guard towers do not stand closer to the player's start than this. */
    public static final double TOWER_MIN_START_DISTANCE = 250.0;
    private static final long PORTAL_GHOST_SEED = 4242L;
    private static final long TOWER_SEED = 777L;

    private LevelPlacement() {
    }

    /**
     * The sergeants' posts: as many as the level says for this difficulty, at about the level's distance from the
     * heart, apart from each other. The same for a given maze every time, so the mines round them can find them again.
     * Fewer come back when the maze has no room for all of them.
     */
    public static List<Point2D> sergeantPosts(LevelSpec level, DifficultyTier tier, GameMazeWorld world,
                                              double heartX, double heartY) {
        if (level == null || !level.hasSergeants() || world == null) {
            return List.of();
        }
        return SergeantPosts.choose(MazeHazards.freePoints(world), heartX, heartY,
                level.sergeant().postDistanceFromHeart(), level.sergeantCount(tier), SERGEANT_POST_SPACING);
    }

    /**
     * The spots of the guard towers: as many as the level says for this difficulty, on free corridor points in the
     * outer band of the map, as far from each other as can be (so they cover different parts of the edge) and away
     * from the player's start. The same for a given maze every time.
     */
    public static List<Point2D> towerPosts(LevelSpec level, DifficultyTier tier, GameMazeWorld world,
                                           double startX, double startY) {
        if (level == null || !level.hasTowers() || world == null) {
            return List.of();
        }
        List<Point2D> outer = new ArrayList<>(EdgeTeleport.outerBand(MazeHazards.freePoints(world)));
        outer.removeIf(point -> Math.hypot(point.getX() - startX, point.getY() - startY) < TOWER_MIN_START_DISTANCE);
        return spreadOut(outer, level.towerCount(tier));
    }

    /** Picks {@code count} points as far from each other as possible, starting from a seeded pick. */
    static List<Point2D> spreadOut(List<Point2D> candidates, int count) {
        if (candidates.isEmpty() || count <= 0) {
            return List.of();
        }
        List<Point2D> pool = new ArrayList<>(candidates);
        Collections.shuffle(pool, new Random(TOWER_SEED));
        List<Point2D> chosen = new ArrayList<>();
        chosen.add(pool.remove(0));
        while (chosen.size() < count && !pool.isEmpty()) {
            Point2D best = null;
            double bestGap = -1;
            for (Point2D point : pool) {
                double gap = Double.MAX_VALUE;
                for (Point2D other : chosen) {
                    gap = Math.min(gap, Math.hypot(point.getX() - other.getX(), point.getY() - other.getY()));
                }
                if (gap > bestGap) {
                    bestGap = gap;
                    best = point;
                }
            }
            pool.remove(best);
            chosen.add(best);
        }
        return List.copyOf(chosen);
    }

    /**
     * The spots of the portal ghosts: as many as the level says for this difficulty, random free corridor points
     * (the same ones every time for a given maze) away from the player's start.
     */
    public static List<Point2D> portalGhostSpots(LevelSpec level, DifficultyTier tier, GameMazeWorld world,
                                                 double startX, double startY) {
        if (level == null || !level.hasTeleportGhosts() || world == null) {
            return List.of();
        }
        List<Point2D> corridor = new ArrayList<>(MazeHazards.freePoints(world));
        corridor.removeIf(point -> Math.hypot(point.getX() - startX, point.getY() - startY) < PORTAL_GHOST_MIN_START_DISTANCE);
        Collections.shuffle(corridor, new Random(PORTAL_GHOST_SEED));
        return List.copyOf(corridor.subList(0, Math.min(level.ghostCount(tier), corridor.size())));
    }
}
