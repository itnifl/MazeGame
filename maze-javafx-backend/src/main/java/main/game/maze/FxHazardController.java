package main.game.maze;

import java.util.List;
import java.util.Random;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.game.hazard.EdgeTeleport;
import main.game.maze.game.hazard.MazeHazards;
import main.game.maze.game.hazard.MineField;
import main.game.maze.game.hazard.TeleportGate;
import main.game.maze.game.level.LevelSpec;
import main.game.maze.game.level.MineSpec;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.constants.StageConstants;

/**
 * JavaFX controller for the mines, the holes they leave and the teleports: the counterpart of libGDX
 * {@code GdxHazardSupport}, using the same shared rules ({@link MineField}, {@link EdgeTeleport},
 * {@link TeleportGate}). The {@link Host} gives it the player and the few game actions it needs, so it can be
 * tested without the FXML game screen. Coordinates are world space (y-down), as the board is.
 * Use on the JavaFX Application Thread.
 */
public final class FxHazardController {

    /** Why the player is being sent somewhere. */
    public enum Cause { HOLE, PORTAL_GHOST }

    /** What the controller needs from the game screen. */
    public interface Host {
        PlayerCharacter player();

        /** A mine went off at (x, y): the blast (walls and enemies; the player's share is {@link #hurtPlayer}). */
        void blast(double x, double y, int damage, double range);

        /** The player set a mine off and takes a little damage. */
        void hurtPlayer(int damage);

        /** Sends the player so that their centre is at (x, y). */
        void teleportPlayer(double x, double y, Cause cause);

        /** Redraws the mines and holes (null when the level has none: nothing to draw). */
        void draw(MineField field);
    }

    private static final double HALF_PLAYER = StageConstants.PlayerCharacterXYSize * 0.5d;
    private static final double DEFAULT_COOLDOWN_SECONDS = 1.5;

    private final Host host;
    private final Random random;
    private MineField field;
    private TeleportGate gate = new TeleportGate(DEFAULT_COOLDOWN_SECONDS);
    private List<Point2D> freePoints = List.of();
    private long lastNanos = -1;

    public FxHazardController(Host host) {
        this(host, new Random());
    }

    FxHazardController(Host host, Random random) {
        this.host = host;
        this.random = random;
    }

    public MineField field() {
        return field;
    }

    /**
     * New level: mines round every sergeant post and the free points a teleport can come out at.
     *
     * @param posts where the sergeants stand (world space, centres)
     */
    public void startGame(GameMazeWorld world, LevelSpec level, List<Point2D> posts) {
        field = null;
        freePoints = world == null ? List.of() : MazeHazards.freePoints(world);
        gate = new TeleportGate(level != null && level.hasTeleportGhosts()
                ? level.teleportGhost().cooldownSeconds() : DEFAULT_COOLDOWN_SECONDS);
        lastNanos = -1;
        if (world != null && level != null && level.hasMines()) {
            field = new MineField(level.mines());
            MazeHazards.placeMines(field, posts, world);
        }
        host.draw(field);
    }

    /** Per frame: mines the player touches go off, a player over a hole falls to the opposite edge. */
    public void tick(long nowNanos) {
        double dt = lastNanos < 0 ? 0 : (nowNanos - lastNanos) / 1e9;
        lastNanos = nowNanos;
        PlayerCharacter player = host.player();
        if (field == null || player == null) {
            return;
        }
        double cx = player.getCharacterPosition().getX() + HALF_PLAYER;
        double cy = player.getCharacterPosition().getY() + HALF_PLAYER;
        MineField.Update update = field.update(dt, cx, cy, HALF_PLAYER);
        MineSpec spec = field.spec();
        for (MineField.Detonation detonation : update.detonations()) {
            host.blast(detonation.mine().x(), detonation.mine().y(), spec.blastDamage(), spec.blastRadius());
            host.hurtPlayer(spec.playerDamage());
        }
        if (update.fellInto() != null && gate.tryUse(nowNanos / 1e9)) {
            Point2D exit = EdgeTeleport.oppositeEdge(cx, cy, freePoints);
            if (exit != null) {
                host.teleportPlayer(exit.getX(), exit.getY(), Cause.HOLE);
            }
        }
        host.draw(field);
    }

    /** A portal ghost touched the player: a random spot at the edge of the map, if the cooldown allows. */
    public void onPortalGhostTouch(long nowNanos) {
        if (gate.tryUse(nowNanos / 1e9)) {
            Point2D exit = EdgeTeleport.randomOuterEdge(freePoints, random);
            if (exit != null) {
                host.teleportPlayer(exit.getX(), exit.getY(), Cause.PORTAL_GHOST);
            }
        }
    }

    /** Level teardown. */
    public void dispose() {
        field = null;
        host.draw(null);
    }
}
