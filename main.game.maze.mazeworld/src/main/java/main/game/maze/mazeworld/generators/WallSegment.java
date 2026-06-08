package main.game.maze.mazeworld.generators;

/** Axis-aligned wall segment in continuous 2D space. */
public final class WallSegment {
    public final float x1;
    public final float y1;
    public final float x2;
    public final float y2;

    public WallSegment(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public boolean isHorizontal() {
        return Math.abs(y1 - y2) < 1e-4f;
    }
}
