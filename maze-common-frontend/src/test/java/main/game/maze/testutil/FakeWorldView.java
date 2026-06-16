package main.game.maze.testutil;

import main.game.maze.common.movement.WorldView;

import java.util.function.BiFunction;

/**
 * Configurable {@link WorldView} for movement tests.
 * By default represents an open board with no walls and the player at origin.
 */
public final class FakeWorldView implements WorldView {

    private double playerX = 0;
    private double playerY = 0;
    private double minX = 0;
    private double minY = 0;
    private double maxX = 1000;
    private double maxY = 1000;
    /** Returns true if a move to (cx, cy) with given size would collide. Default: always free. */
    private BiFunction<double[], Double, Boolean> collisionRule = (pos, size) -> false;

    public FakeWorldView playerAt(double x, double y) {
        this.playerX = x;
        this.playerY = y;
        return this;
    }

    public FakeWorldView bounds(double minX, double minY, double maxX, double maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        return this;
    }

    /** All positions collide — simulates a fully walled arena. */
    public FakeWorldView allBlocked() {
        this.collisionRule = (pos, size) -> true;
        return this;
    }

    /** Only this specific cell is blocked. */
    public FakeWorldView blockAt(double bx, double by) {
        BiFunction<double[], Double, Boolean> prev = this.collisionRule;
        this.collisionRule = (pos, size) ->
                (Math.abs(pos[0] - bx) < size && Math.abs(pos[1] - by) < size) || prev.apply(pos, size);
        return this;
    }

    @Override public double playerX() { return playerX; }
    @Override public double playerY() { return playerY; }
    @Override public double minX()    { return minX; }
    @Override public double minY()    { return minY; }
    @Override public double maxX()    { return maxX; }
    @Override public double maxY()    { return maxY; }

    @Override
    public boolean wouldCollide(double centerX, double centerY, double size) {
        return collisionRule.apply(new double[]{centerX, centerY}, size);
    }
}
