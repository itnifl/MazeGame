package main.game.maze.libgdx.movement;

import main.game.maze.common.movement.WorldView;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.generators.WallSegment;

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
        double half = size * 0.5d;
        if (centerX - half < 0d || centerY - half < 0d) {
            return true;
        }
        if (centerX + half > maze.widthPx() || centerY + half > maze.heightPx()) {
            return true;
        }
        double left = centerX - half;
        double right = centerX + half;
        double bottom = centerY - half;
        double top = centerY + half;
        for (WallSegment w : maze.walls()) {
            if (w.isHorizontal()) {
                double wx1 = Math.min(w.x1, w.x2);
                double wx2 = Math.max(w.x1, w.x2);
                if (right < wx1 || left > wx2) {
                    continue;
                }
                if (bottom <= w.y1 && top >= w.y1) {
                    return true;
                }
            } else {
                double wy1 = Math.min(w.y1, w.y2);
                double wy2 = Math.max(w.y1, w.y2);
                if (top < wy1 || bottom > wy2) {
                    continue;
                }
                if (left <= w.x1 && right >= w.x1) {
                    return true;
                }
            }
        }
        return false;
    }
}
