package main.game.maze.game.hazard;

import java.util.List;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import main.game.maze.game.level.LevelSpec;
import main.game.maze.game.level.MineSpec;
import main.game.maze.game.level.TeleportRulesSpec;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;

/**
 * Everything the level's hazards decide, in one place for both frontends: the mines round the sergeants, what a
 * touched mine does (a small blast, a little damage to the player, a hole), whether the player falls into a hole,
 * where a teleport comes out, and the cooldown between teleports. The frontends only feed it the player's position
 * and the clock (world space, y-down) and carry out what it returns: draw the mines and holes, apply the blast and
 * the damage, move the player and show the flash, the word and the voice.
 */
public final class HazardSession {

    /** Why the player is being sent somewhere. */
    public enum Cause { HOLE, PORTAL_GHOST }

    /** Where the player comes out (world space, a free point) and why. */
    public record Teleport(Point2D exit, Cause cause) {
    }

    /**
     * What one step did.
     *
     * @param detonations  the mines that went off (each left a hole)
     * @param playerDamage the damage the player takes from them in total
     * @param teleport     the teleport that happens now, or null
     */
    public record Result(List<MineField.Detonation> detonations, int playerDamage, Teleport teleport) {
        static final Result NONE = new Result(List.of(), 0, null);
    }

    private final Random random;
    private MineField field;
    private TeleportGate gate = new TeleportGate(TeleportRulesSpec.DEFAULT.cooldownSeconds());
    private TeleportRulesSpec rules = TeleportRulesSpec.DEFAULT;
    private List<Point2D> freePoints = List.of();

    public HazardSession() {
        this(new Random());
    }

    public HazardSession(Random random) {
        this.random = random;
    }

    /** The mines and holes of the level, or null when it has no mines. */
    public MineField field() {
        return field;
    }

    /** The values of the mines of the level, or null when it has none. */
    public MineSpec mineSpec() {
        return field == null ? null : field.spec();
    }

    public TeleportRulesSpec rules() {
        return rules;
    }

    /** New level on a generated maze: the free corridor points, and mines round every sergeant post. */
    public void start(GameMazeWorld world, LevelSpec level, List<Point2D> sergeantPosts) {
        if (world == null || level == null) {
            clear();
            return;
        }
        List<main.game.maze.mazeworld.Vector2D> walls = world.getMazeVectors();
        start(level, sergeantPosts, MazeHazards.freePoints(world), MazeHazards.freeSpot(walls), MazeHazards.clearLine(walls));
    }

    /** As {@link #start(GameMazeWorld, LevelSpec, List)} with the maze's geometry given (world space, y-down). */
    public void start(LevelSpec level, List<Point2D> sergeantPosts, List<Point2D> freePoints,
                      Predicate<Point2D> free, BiPredicate<Point2D, Point2D> clearLine) {
        clear();
        this.freePoints = List.copyOf(freePoints);
        this.rules = level.teleport();
        this.gate = new TeleportGate(rules.cooldownSeconds());
        if (level.hasMines()) {
            field = new MineField(level.mines());
            field.place(sergeantPosts, free, clearLine);
        }
    }

    /** No mines, no holes, ready to teleport. */
    public void clear() {
        field = null;
        freePoints = List.of();
        rules = TeleportRulesSpec.DEFAULT;
        gate = new TeleportGate(rules.cooldownSeconds());
    }

    /**
     * One step: mines the player touches go off (leaving holes, hurting the player a little), and a player over
     * a hole falls to the opposite edge of the map if the cooldown allows.
     *
     * @param nowSeconds a steady clock, for the cooldown
     */
    public Result tick(double dt, double playerX, double playerY, double playerRadius, double nowSeconds) {
        if (field == null) {
            return Result.NONE;
        }
        MineField.Update update = field.update(dt, playerX, playerY, playerRadius);
        int damage = update.detonations().size() * field.spec().playerDamage();
        Teleport teleport = null;
        if (update.fellInto() != null && gate.tryUse(nowSeconds)) {
            Point2D exit = EdgeTeleport.oppositeEdge(playerX, playerY, freePoints);
            if (exit != null) {
                teleport = new Teleport(exit, Cause.HOLE);
            }
        }
        return new Result(update.detonations(), damage, teleport);
    }

    /**
     * A portal ghost touched the player: a random free spot at the outer edge of the map, unless the cooldown
     * has not run out (or there is nowhere to go), then null.
     */
    public Teleport portalGhostTouched(double nowSeconds) {
        if (!gate.tryUse(nowSeconds)) {
            return null;
        }
        Point2D exit = EdgeTeleport.randomOuterEdge(freePoints, random);
        return exit == null ? null : new Teleport(exit, Cause.PORTAL_GHOST);
    }
}
