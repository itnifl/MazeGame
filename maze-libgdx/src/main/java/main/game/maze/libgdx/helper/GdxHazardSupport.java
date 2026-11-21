package main.game.maze.libgdx.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import main.game.maze.common.graphics.fire.HoleEffect;
import main.game.maze.common.graphics.fire.MinePulseEffect;
import main.game.maze.game.hazard.HazardSession;
import main.game.maze.game.hazard.MineField;
import main.game.maze.game.level.LevelSpec;
import main.game.maze.game.level.MineSpec;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;

/**
 * libGDX side of the mines, the holes they leave and the teleports. All decisions come from the shared
 * {@link HazardSession}; this class only converts between libGDX screen space (y-up) and the shared world space
 * (y-down), keeps the long-lived mine and hole visuals in the world's effect list, and notices when a portal ghost
 * touches the player.
 */
public final class GdxHazardSupport {

    /** A portal ghost touches the player when their bodies overlap this much (share of the summed radii). */
    private static final double TOUCH_SHARE = 0.85;

    /** The player is sent here (screen space, y-up) and why. */
    public record Teleport(float x, float y, HazardSession.Cause cause) {
    }

    /** What one frame did: the mines that went off, the damage to the player and the teleport, if any. */
    public record Result(List<MineField.Detonation> detonations, int playerDamage, Teleport teleport) {
        static Result none() {
            return new Result(List.of(), 0, null);
        }
    }

    private final HazardSession session;
    private final Map<Integer, GameWorldModel.ActiveEffect> mineEffects = new HashMap<>();
    private float heightPx;

    public GdxHazardSupport() {
        this(new Random());
    }

    public GdxHazardSupport(Random random) {
        this.session = new HazardSession(random);
    }

    /** The mine values of the current level, or null when it has no mines. */
    public MineSpec mineSpec() {
        return session.mineSpec();
    }

    public MineField field() {
        return session.field();
    }

    /** New level: forget the old mines, holes and teleport cooldown. */
    public void reset(GameWorldModel model) {
        if (model != null) {
            model.effects().removeAll(mineEffects.values());
        }
        mineEffects.clear();
        session.clear();
    }

    /**
     * Sets the level up from its generated maze: the free corridor points (for the teleports), the mines round
     * every sergeant. Only a generated maze has a world to find them in.
     */
    public void start(GameWorldModel model, MazeArena maze, LevelSpec level, List<GdxEnemyRuntime> enemies) {
        reset(model);
        GameMazeWorld world = GdxWallDamageSupport.worldFrom(maze);
        if (world == null || level == null || maze == null) {
            return;
        }
        heightPx = maze.heightPx();
        List<Point2D> posts = new ArrayList<>();
        for (GdxEnemyRuntime enemy : enemies) {
            if (enemy.isStationary()) {
                posts.add(new Point2D(enemy.x(), maze.heightPx() - enemy.y()));
            }
        }
        session.start(world, level, posts);
        drawMines(model);
    }

    /** As {@link #start(GameWorldModel, MazeArena, LevelSpec, List)} with the maze's geometry given (world space, y-down). */
    public void start(GameWorldModel model, float heightPx, LevelSpec level, List<Point2D> sergeantPosts,
                      List<Point2D> freePoints, Predicate<Point2D> free, BiPredicate<Point2D, Point2D> clearLine) {
        reset(model);
        this.heightPx = heightPx;
        session.start(level, sergeantPosts, freePoints, free, clearLine);
        drawMines(model);
    }

    private void drawMines(GameWorldModel model) {
        if (session.field() == null) {
            return;
        }
        double pulse = session.mineSpec().pulseSeconds();
        for (MineField.Mine mine : session.field().mines()) {
            GameWorldModel.ActiveEffect effect = new GameWorldModel.ActiveEffect(
                    new MinePulseEffect(mine.x(), heightPx - mine.y(), mine.phase(), pulse));
            mineEffects.put(mine.id(), effect);
            model.effects().add(effect);
        }
    }

    /**
     * One frame: mines the player touches go off (leaving holes, hurting the player a little), a player over a hole
     * falls to the opposite edge, a portal ghost that touches the player sends them to a random spot at the edge of
     * the map. Only one teleport per cooldown.
     *
     * @param nowSeconds a steady clock, for the cooldown
     */
    public Result tick(GameWorldModel model, PlayerState player, List<GdxEnemyRuntime> enemies, float dt, double nowSeconds) {
        if (player == null) {
            return Result.none();
        }
        HazardSession.Result result = session.tick(dt, player.x(), heightPx - player.y(), player.halfSize(), nowSeconds);
        for (MineField.Detonation detonation : result.detonations()) {
            GameWorldModel.ActiveEffect gone = mineEffects.remove(detonation.mine().id());
            if (gone != null) {
                model.effects().remove(gone);
            }
            model.effects().add(new GameWorldModel.ActiveEffect(new HoleEffect(
                    detonation.hole().x(), heightPx - detonation.hole().y(), detonation.hole().radius())));
        }
        HazardSession.Teleport teleport = result.teleport();
        if (teleport == null && touchedByPortalGhost(player, enemies)) {
            teleport = session.portalGhostTouched(nowSeconds);
        }
        return new Result(result.detonations(), result.playerDamage(), teleport == null ? null
                : new Teleport((float) teleport.exit().getX(), heightPx - (float) teleport.exit().getY(), teleport.cause()));
    }

    private static boolean touchedByPortalGhost(PlayerState player, List<GdxEnemyRuntime> enemies) {
        for (GdxEnemyRuntime enemy : enemies) {
            if (enemy.isPortalGhost() && enemy.isAlive()
                    && Math.hypot(enemy.x() - player.x(), enemy.y() - player.y())
                            < (player.halfSize() + enemy.size() * 0.5) * TOUCH_SHARE) {
                return true;
            }
        }
        return false;
    }
}
