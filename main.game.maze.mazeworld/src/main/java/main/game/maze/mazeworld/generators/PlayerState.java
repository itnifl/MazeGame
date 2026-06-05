package main.game.maze.mazeworld.generators;

import main.game.maze.mazeworld.WallCollisionUtil;

/**
 * Mutable player position + size, with axis-separated collision resolution.
 */
public final class PlayerState {

    private float x;
    private float y;
    private final float halfSize;

    public PlayerState(float x, float y, float size) {
        if (size <= 0f) throw new IllegalArgumentException("size must be positive");
        this.x = x;
        this.y = y;
        this.halfSize = size * 0.5f;
    }

    public float x() { return x; }
    public float y() { return y; }
    public float halfSize() { return halfSize; }

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

    public void attemptMove(float dx, float dy, MazeArena maze) {
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


