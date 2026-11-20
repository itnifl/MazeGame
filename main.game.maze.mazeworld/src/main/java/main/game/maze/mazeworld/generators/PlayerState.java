package main.game.maze.mazeworld.generators;

import main.game.maze.mazeworld.WallCollisionUtil;

/**
 * Mutable player position + size, with axis-separated collision resolution.
 */
public final class PlayerState {

    private float x;
    private float y;
    private final float halfSize;
    /** Last movement direction as an axis unit vector, in the caller's coordinates (starts facing right). */
    private int facingX = 1;
    private int facingY = 0;

    public PlayerState(float x, float y, float size) {
        if (size <= 0f) throw new IllegalArgumentException("size must be positive");
        this.x = x;
        this.y = y;
        this.halfSize = size * 0.5f;
    }

    public float x() { return x; }
    public float y() { return y; }
    public float halfSize() { return halfSize; }
    /** Facing x: -1, 0 or 1 along the last movement (aimed weapons such as the laser use it). */
    public int facingX() { return facingX; }
    /** Facing y: -1, 0 or 1, in the same coordinates the moves are given in. */
    public int facingY() { return facingY; }

    public static PlayerState spawnAwayFromWalls(float x, float y, float size, MazeArena maze) {
        PlayerState spawn = new PlayerState(x, y, size);
        if (maze == null || !spawn.collidesWith(maze)) {
            return spawn;
        }

        float step = Math.max(1f, size * 0.25f);
        float maxRadius = Math.max(maze.widthPx(), maze.heightPx());
        for (float radius = step; radius <= maxRadius; radius += step) {
            float[][] candidates = {
                    {x + radius, y},
                    {x, y + radius},
                    {x - radius, y},
                    {x, y - radius},
                    {x + radius, y + radius},
                    {x - radius, y + radius},
                    {x + radius, y - radius},
                    {x - radius, y - radius}
            };
            for (float[] candidate : candidates) {
                PlayerState moved = new PlayerState(candidate[0], candidate[1], size);
                if (!moved.collidesWith(maze)) {
                    return moved;
                }
            }
        }

        return spawn;
    }

    /** Puts the player at (x, y) without collision checks, e.g. back at the start after losing a life. */
    public void teleportTo(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void attemptMove(float dx, float dy, MazeArena maze) {
        if (dx != 0f || dy != 0f) {
            // dominant axis wins, so diagonal input still aims along a corridor
            if (Math.abs(dx) >= Math.abs(dy)) {
                facingX = dx > 0 ? 1 : -1;
                facingY = 0;
            } else {
                facingX = 0;
                facingY = dy > 0 ? 1 : -1;
            }
        }
        if (dx != 0f) {
            float candidate = x + dx;
            if (!collides(candidate, y, maze)) x = candidate;
        }
        if (dy != 0f) {
            float candidate = y + dy;
            if (!collides(x, candidate, maze)) y = candidate;
        }
    }

    public boolean reached(float goalX, float goalY, float goalRadius) {
        float dx = x - goalX;
        float dy = y - goalY;
        return (dx * dx + dy * dy) <= (goalRadius * goalRadius);
    }

    public boolean collidesWith(MazeArena maze) {
        return collides(x, y, maze);
    }

    private boolean collides(float cx, float cy, MazeArena maze) {
        return WallCollisionUtil.wouldCollide(cx, cy, halfSize * 2f,
                maze.widthPx(), maze.heightPx(), maze.walls());
    }
}
