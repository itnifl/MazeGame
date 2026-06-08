package main.game.maze.libgdx.movement;

import main.game.maze.common.movement.WorldView;
import main.game.maze.mazeworld.WallCollisionUtil;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;

/**
 * libGDX-side {@link WorldView} adapter. Bridges the backend-neutral
 * movement service to the {@link MazeArena}/{@link PlayerState} pair the
 * libGDX runtime already owns. Holds no state of its own beyond the two
 * references; each call reads the current player position fresh.
 *
 * <p>The {@link #wouldCollide} predicate mirrors {@code PlayerState.collides}
 * (same AABB-vs-axis-aligned-wall test) so enemy and player wall semantics
 * stay identical inside libGDX.
 */
public final class GdxWorldView implements WorldView {

    private final MazeArena maze;
    private final PlayerState player;

    public GdxWorldView(MazeArena maze, PlayerState player) {
        if (maze == null) {
            throw new IllegalArgumentException("maze must not be null");
        }
        if (player == null) {
            throw new IllegalArgumentException("player must not be null");
        }
        this.maze = maze;
        this.player = player;
    }

    @Override
    public double playerX() {
        return player.x();
    }

    @Override
    public double playerY() {
        return player.y();
    }

    @Override
    public double minX() {
        return 0d;
    }

    @Override
    public double minY() {
        return 0d;
    }

    @Override
    public double maxX() {
        return maze.widthPx();
    }

    @Override
    public double maxY() {
        return maze.heightPx();
    }

    @Override
    public boolean wouldCollide(double centerX, double centerY, double size) {
        return WallCollisionUtil.wouldCollide(centerX, centerY, size,
                maze.widthPx(), maze.heightPx(), maze.walls());
    }
}
