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
import main.game.maze.game.hazard.EdgeTeleport;
import main.game.maze.game.hazard.MazeHazards;
import main.game.maze.game.hazard.MineField;
import main.game.maze.game.hazard.TeleportGate;
import main.game.maze.game.level.LevelSpec;
import main.game.maze.game.level.MineSpec;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;

/**
 * libGDX side of the mines, the holes they leave and the teleports. The rules come from the shared
 * {@link MineField}, {@link EdgeTeleport} and {@link TeleportGate}; this class converts between libGDX
 * screen space (y-up) and the shared world space (y-down), keeps the long-lived mine and hole visuals in the
 * world's effect list, and tells the controller what happened.
 */
public final class GdxHazardSupport {

    /** A portal ghost touches the player when their bodies overlap this much (share of the summed radii). */
    private static final double TOUCH_SHARE = 0.85;
    private static final double DEFAULT_COOLDOWN_SECONDS = 1.5;

    /** The player is sent here (screen space, y-up) and why. */
    public record Teleport(float x, float y, Cause cause) {
    }

    public enum Cause { HOLE, PORTAL_GHOST }

    /** What one frame did: the mines that went off and the teleport, if any. */
    public record Result(List<MineField.Detonation> detonations, Teleport teleport) {
        static Result none() {
            return new Result(List.of(), null);
        }
    }

    private final Random random;
    private final Map<Integer, GameWorldModel.ActiveEffect> mineEffects = new HashMap<>();
    private MineField field;
    private TeleportGate gate = new TeleportGate(DEFAULT_COOLDOWN_SECONDS);
    private List<Point2D> freePoints = List.of();
    private float heightPx;

    public GdxHazardSupport() {
        this(new Random());
    }

    public GdxHazardSupport(Random random) {
        this.random = random;
    }

    /** The mine values of the current level, or null when it has no mines. */
    public MineSpec mineSpec() {
        return field == null ? null : field.spec();
    }

    public MineField field() {
        return field;
    }

    /** New level: forget the old mines, holes and teleport cooldown. */
    public void reset(GameWorldModel model) {
        if (model != null) {
            model.effects().removeAll(mineEffects.values());
        }
        mineEffects.clear();
        field = null;
        freePoints = List.of();
        gate = new TeleportGate(DEFAULT_COOLDOWN_SECONDS);
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
        List<Vector2D> walls = world.getMazeVectors();
        List<Point2D> points = MazeHazards.freePoints(world);
        List<Point2D> posts = new ArrayList<>();
        for (GdxEnemyRuntime enemy : enemies) {
            if (enemy.isStationary()) {
                posts.add(new Point2D(enemy.x(), maze.heightPx() - enemy.y()));
            }
        }
        start(model, maze.heightPx(), level, posts, points, MazeHazards.freeSpot(walls), MazeHazards.clearLine(walls));
    }

    /** As {@link #start(GameWorldModel, MazeArena, LevelSpec, List)} with the maze's geometry given (world space, y-down). */
    public void start(GameWorldModel model, float heightPx, LevelSpec level, List<Point2D> sergeantPosts,
                      List<Point2D> freePoints, Predicate<Point2D> free, BiPredicate<Point2D, Point2D> clearLine) {
        reset(model);
        this.heightPx = heightPx;
        this.freePoints = List.copyOf(freePoints);
        if (level.hasTeleportGhosts()) {
            gate = new TeleportGate(level.teleportGhost().cooldownSeconds());
        }
        if (!level.hasMines()) {
            return;
        }
        field = new MineField(level.mines());
        field.place(sergeantPosts, free, clearLine);
        for (MineField.Mine mine : field.mines()) {
            GameWorldModel.ActiveEffect effect = new GameWorldModel.ActiveEffect(
                    new MinePulseEffect(mine.x(), heightPx - mine.y(), mine.phase(), level.mines().pulseSeconds()));
            mineEffects.put(mine.id(), effect);
            model.effects().add(effect);
        }
    }

    /**
     * One frame: mines the player touches go off (leaving holes), a player over a hole falls to the opposite edge,
     * a portal ghost that touches the player sends them to a random spot at the edge of the map. Only one teleport
     * per cooldown.
     *
     * @param nowSeconds a steady clock, for the cooldown
     */
    public Result tick(GameWorldModel model, PlayerState player, List<GdxEnemyRuntime> enemies, float dt, double nowSeconds) {
        if (player == null) {
            return Result.none();
        }
        List<MineField.Detonation> detonations = List.of();
        Teleport teleport = null;
        if (field != null) {
            MineField.Update update = field.update(dt, player.x(), heightPx - player.y(), player.halfSize());
            detonations = update.detonations();
            for (MineField.Detonation detonation : detonations) {
                GameWorldModel.ActiveEffect gone = mineEffects.remove(detonation.mine().id());
                if (gone != null) {
                    model.effects().remove(gone);
                }
                model.effects().add(new GameWorldModel.ActiveEffect(new HoleEffect(
                        detonation.hole().x(), heightPx - detonation.hole().y(), detonation.hole().radius())));
            }
            if (update.fellInto() != null && gate.tryUse(nowSeconds)) {
                Point2D exit = EdgeTeleport.oppositeEdge(player.x(), heightPx - player.y(), freePoints);
                if (exit != null) {
                    teleport = new Teleport((float) exit.getX(), heightPx - (float) exit.getY(), Cause.HOLE);
                }
            }
        }
        if (teleport == null && touchedByPortalGhost(player, enemies) && gate.tryUse(nowSeconds)) {
            Point2D exit = EdgeTeleport.randomOuterEdge(freePoints, random);
            if (exit != null) {
                teleport = new Teleport((float) exit.getX(), heightPx - (float) exit.getY(), Cause.PORTAL_GHOST);
            }
        }
        return new Result(detonations, teleport);
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
