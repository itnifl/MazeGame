package main.game.maze.libgdx.helper;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.game.builder.WallBuilderSession;
import main.game.maze.game.level.WallBuilderSpec;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.mazeworld.Vector2D;

/**
 * libGDX side of the wall builders: the counterpart of JavaFX {@code FxWallBuilderController}. All decisions (where a
 * builder goes, what it builds and when) come from the shared {@link WallBuilderSession}, which works in world space
 * (y-down); this class converts from and to the libGDX screen space (y-up), moves the builder enemies where the session
 * says and hands the pieces of wall that went up to the {@link Host}.
 */
public final class GdxWallBuilderSupport {

    /** What the support needs from the game screen. */
    public interface Host {
        /** The live walls of the maze (world space, y-down). */
        List<Vector2D> walls();

        /** A piece of wall went up: add it to the world (breakable, of the builder material), refresh, play the sound. */
        void build(String builderId, Vector2D piece);
    }

    private WallBuilderSession session;
    private float heightPx;

    /** New level: a crew for this level, or none. */
    public void reset(WallBuilderSpec spec, float widthPx, float heightPx, main.game.maze.mazeworld.LavaField lava) {
        this.heightPx = heightPx;
        session = spec == null ? null : new WallBuilderSession(spec, Math.round(widthPx), Math.round(heightPx), widthPx / 2.0, heightPx / 2.0);
        if (session != null) {
            session.setLava(lava);   // the builders keep out of the lava of the level (world space, y-down)
        }
    }

    /**
     * Advances the builders one frame.
     *
     * @param playerX the player centre (screen space)
     */
    public void tick(Host host, List<GdxEnemyRuntime> enemies, float playerX, float playerY, float dt) {
        if (session == null) {
            return;
        }
        List<WallBuilderSession.Builder> views = new ArrayList<>();
        List<GdxEnemyRuntime> crew = new ArrayList<>();
        for (GdxEnemyRuntime enemy : enemies) {
            if (enemy.isWallBuilder()) {
                crew.add(enemy);
                views.add(new WallBuilderSession.Builder(enemy.originalSpawn().id(), enemy.x(), heightPx - enemy.y(), enemy.isAlive()));
            }
        }
        if (views.isEmpty()) {
            return;
        }
        WallBuilderSession.Result result = session.tick(dt, views, host.walls(), playerX, heightPx - playerY);
        for (WallBuilderSession.Move move : result.moves()) {
            for (GdxEnemyRuntime enemy : crew) {
                if (enemy.isAlive() && enemy.originalSpawn().id().equals(move.id())) {
                    enemy.placeAt((float) move.x(), heightPx - (float) move.y());
                }
            }
        }
        for (WallBuilderSession.Piece piece : result.pieces()) {
            host.build(piece.builderId(), piece.wall());
        }
    }
}
