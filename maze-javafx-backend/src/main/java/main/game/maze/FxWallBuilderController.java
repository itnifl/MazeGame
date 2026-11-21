package main.game.maze;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.game.builder.WallBuilderSession;
import main.game.maze.game.level.WallBuilderSpec;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.constants.StageConstants;

/**
 * JavaFX adapter for the wall builders: the counterpart of libGDX {@code GdxWallBuilderSupport}. All decisions (where
 * a builder goes, what it builds and when) come from the shared {@link WallBuilderSession}; this class feeds it the
 * builders, the live walls and the player, moves the builders where it says and hands the pieces of wall that went up
 * to the {@link Host}. World space (y-down), as the board is. Use on the JavaFX Application Thread.
 */
public final class FxWallBuilderController {

    /** What the controller needs from the game screen. */
    public interface Host {
        PlayerCharacter player();

        /** The live walls of the maze. */
        List<Vector2D> walls();

        /** A piece of wall went up: add it to the world (breakable, of the builder material), redraw, play the sound. */
        void build(String builderId, Vector2D piece);
    }

    /** A builder as the controller needs it. */
    public interface Body {
        double centerX();

        double centerY();

        boolean alive();

        void moveTo(double x, double y);
    }

    private static final double HALF_PLAYER = StageConstants.PlayerCharacterXYSize * 0.5d;

    private final Host host;
    private final Map<String, Body> builders = new LinkedHashMap<>();
    private WallBuilderSession session;
    private long lastNanos = -1;

    public FxWallBuilderController(Host host) {
        this.host = host;
    }

    /** New level: no builders yet (they register as they are placed); a level without builders has none. */
    public void startGame(WallBuilderSpec spec, int boardWidth, int boardHeight, main.game.maze.mazeworld.LavaField lava) {
        builders.clear();
        session = spec == null ? null : new WallBuilderSession(spec, boardWidth, boardHeight, boardWidth / 2.0, boardHeight / 2.0);
        if (session != null) {
            session.setLava(lava);   // the builders keep out of the lava of the level
        }
        lastNanos = -1;
    }

    public void register(String id, Body builder) {
        builders.put(id, builder);
    }

    public int builderCount() {
        return builders.size();
    }

    /** Per frame: the builders plan, walk and build. */
    public void tick(long nowNanos) {
        double dt = lastNanos < 0 ? 0 : (nowNanos - lastNanos) / 1e9;
        lastNanos = nowNanos;
        PlayerCharacter player = host.player();
        if (session == null || player == null || builders.isEmpty()) {
            return;
        }
        double px = player.getCharacterPosition().getX() + HALF_PLAYER;
        double py = player.getCharacterPosition().getY() + HALF_PLAYER;
        List<WallBuilderSession.Builder> views = new ArrayList<>();
        for (Map.Entry<String, Body> entry : builders.entrySet()) {
            Body body = entry.getValue();
            views.add(new WallBuilderSession.Builder(entry.getKey(), body.centerX(), body.centerY(), body.alive()));
        }
        WallBuilderSession.Result result = session.tick(dt, views, host.walls(), px, py);
        for (WallBuilderSession.Move move : result.moves()) {
            Body body = builders.get(move.id());
            if (body != null && body.alive()) {
                body.moveTo(move.x(), move.y());
            }
        }
        for (WallBuilderSession.Piece piece : result.pieces()) {
            host.build(piece.builderId(), piece.wall());
        }
    }

    /** Level teardown. */
    public void dispose() {
        builders.clear();
        session = null;
    }
}
