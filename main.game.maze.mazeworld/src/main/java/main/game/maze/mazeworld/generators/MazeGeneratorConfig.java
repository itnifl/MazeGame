package main.game.maze.mazeworld.generators;

public final class MazeGeneratorConfig {

    private final int widthPx;
    private final int heightPx;
    private final int wallSegmentLengthPx;
    private final int doorwayWidthPx;
    private final int minHallwayWidthPx;
    private final int minGapBetweenWallsPx;

    public MazeGeneratorConfig(
            int widthPx,
            int heightPx,
            int wallSegmentLengthPx,
            int doorwayWidthPx,
            int minHallwayWidthPx,
            int minGapBetweenWallsPx) {

        this.widthPx = widthPx;
        this.heightPx = heightPx;
        this.wallSegmentLengthPx = wallSegmentLengthPx;
        this.doorwayWidthPx = doorwayWidthPx;
        this.minHallwayWidthPx = minHallwayWidthPx;
        this.minGapBetweenWallsPx = minGapBetweenWallsPx;

        // Basic sanity checks for your current use case
        if (wallSegmentLengthPx <= 0) {
            throw new IllegalArgumentException("wallSegmentLengthPx must be > 0");
        }
        if (doorwayWidthPx < minHallwayWidthPx) {
            throw new IllegalArgumentException("doorwayWidthPx must be ≥ minHallwayWidthPx");
        }
        if (minHallwayWidthPx < minGapBetweenWallsPx) {
            throw new IllegalArgumentException("minHallwayWidthPx must be ≥ minGapBetweenWallsPx");
        }
        if (doorwayWidthPx % wallSegmentLengthPx != 0) {
            throw new IllegalArgumentException("doorwayWidthPx must be a multiple of wallSegmentLengthPx");
        }
        if (minHallwayWidthPx % wallSegmentLengthPx != 0) {
            throw new IllegalArgumentException("minHallwayWidthPx must be a multiple of wallSegmentLengthPx");
        }
    }

    public int getWidthPx() {
        return widthPx;
    }

    public int getHeightPx() {
        return heightPx;
    }

    public int getWallSegmentLengthPx() {
        return wallSegmentLengthPx;
    }

    public int getDoorwayWidthPx() {
        return doorwayWidthPx;
    }

    public int getMinHallwayWidthPx() {
        return minHallwayWidthPx;
    }

    public int getMinGapBetweenWallsPx() {
        return minGapBetweenWallsPx;
    }
}
