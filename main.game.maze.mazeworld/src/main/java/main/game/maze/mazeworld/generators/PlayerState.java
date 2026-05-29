package main.game.maze.mazeworld.generators;

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

    private boolean collides(float cx, float cy, MazeArena maze) {
        if (cx - halfSize < 0f || cy - halfSize < 0f) return true;
        if (cx + halfSize > maze.widthPx() || cy + halfSize > maze.heightPx()) return true;
        float left = cx - halfSize;
        float right = cx + halfSize;
        float bottom = cy - halfSize;
        float top = cy + halfSize;
        for (WallSegment w : maze.walls()) {
            if (w.isHorizontal()) {
                float wx1 = Math.min(w.x1, w.x2);
                float wx2 = Math.max(w.x1, w.x2);
                if (right < wx1 || left > wx2) continue;
                if (bottom <= w.y1 && top >= w.y1) return true;
            } else {
                float wy1 = Math.min(w.y1, w.y2);
                float wy2 = Math.max(w.y1, w.y2);
                if (top < wy1 || bottom > wy2) continue;
                if (left <= w.x1 && right >= w.x1) return true;
            }
        }
        return false;
    }
}
