package main.game.maze;

import java.util.List;
import java.util.Random;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.game.hazard.HazardSession;
import main.game.maze.game.hazard.MineField;
import main.game.maze.game.level.LevelSpec;
import main.game.maze.game.level.MineSpec;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.constants.StageConstants;

/**
 * JavaFX adapter for the mines, the holes they leave and the teleports: the counterpart of libGDX
 * {@code GdxHazardSupport}. All decisions come from the shared {@link HazardSession}; this class feeds it the
 * player's position and the clock and hands what it decides to the {@link Host}, so it can be tested without the
 * FXML game screen. Coordinates are world space (y-down), as the board is. Use on the JavaFX Application Thread.
 */
public final class FxHazardController {

    /** What the controller needs from the game screen. */
    public interface Host {
        PlayerCharacter player();

        /** A mine went off at (x, y): the blast (walls and enemies; the player's share is {@link #hurtPlayer}). */
        void blast(double x, double y, int damage, double range);

        /** The player set mines off and takes a little damage. */
        void hurtPlayer(int damage);

        /** Sends the player so that their centre is at (x, y). */
        void teleportPlayer(double x, double y, HazardSession.Cause cause);

        /** Redraws the mines and holes (null when the level has none: nothing to draw). */
        void draw(MineField field);
    }

    private static final double HALF_PLAYER = StageConstants.PlayerCharacterXYSize * 0.5d;

    private final Host host;
    private final HazardSession session;
    private long lastNanos = -1;

    public FxHazardController(Host host) {
        this(host, new Random());
    }

    FxHazardController(Host host, Random random) {
        this.host = host;
        this.session = new HazardSession(random);
    }

    public MineField field() {
        return session.field();
    }

    /**
     * New level: mines round every sergeant post and the free points a teleport can come out at.
     *
     * @param posts where the sergeants stand (world space, centres)
     */
    public void startGame(GameMazeWorld world, LevelSpec level, List<Point2D> posts) {
        session.start(world, level, posts);
        lastNanos = -1;
        host.draw(session.field());
    }

    /** Per frame: mines the player touches go off, a player over a hole falls to the opposite edge. */
    public void tick(long nowNanos) {
        double dt = lastNanos < 0 ? 0 : (nowNanos - lastNanos) / 1e9;
        lastNanos = nowNanos;
        PlayerCharacter player = host.player();
        if (session.field() == null || player == null) {
            return;
        }
        double cx = player.getCharacterPosition().getX() + HALF_PLAYER;
        double cy = player.getCharacterPosition().getY() + HALF_PLAYER;
        HazardSession.Result result = session.tick(dt, cx, cy, HALF_PLAYER, nowNanos / 1e9);
        MineSpec spec = session.mineSpec();
        for (MineField.Detonation detonation : result.detonations()) {
            host.blast(detonation.mine().x(), detonation.mine().y(), spec.blastDamage(), spec.blastRadius());
        }
        if (result.playerDamage() > 0) {
            host.hurtPlayer(result.playerDamage());
        }
        if (result.teleport() != null) {
            host.teleportPlayer(result.teleport().exit().getX(), result.teleport().exit().getY(), result.teleport().cause());
        }
        host.draw(session.field());
    }

    /** A portal ghost touched the player: a random spot at the edge of the map, if the cooldown allows. */
    public void onPortalGhostTouch(long nowNanos) {
        HazardSession.Teleport teleport = session.portalGhostTouched(nowNanos / 1e9);
        if (teleport != null) {
            host.teleportPlayer(teleport.exit().getX(), teleport.exit().getY(), teleport.cause());
        }
    }

    /** Level teardown. */
    public void dispose() {
        session.clear();
        host.draw(null);
    }
}
