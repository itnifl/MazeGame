package main.game.maze.libgdx.game;

/**
 * Mutable runtime state for a single in-flight projectile in the libGDX frontend.
 * Positions and velocities are in game-world (Y-down) coordinates so they can be
 * checked directly against {@code GameMazeWorld.getMazeVectors()}.
 */
public final class GdxProjectileRuntime {

    private static final float MAX_LIFETIME_SECONDS = 3f;
    public static final float PROJECTILE_SIZE = 8f;

    private double x;
    private double y;
    private final double vx;
    private final double vy;
    private final int damage;
    private float lifeElapsed;

    GdxProjectileRuntime(double x, double y, double vx, double vy, int damage) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.damage = damage;
    }

    public void advance(float dt) {
        x += vx * dt;
        y += vy * dt;
        lifeElapsed += dt;
    }

    public boolean isExpired() {
        return lifeElapsed >= MAX_LIFETIME_SECONDS;
    }

    public double x() { return x; }
    public double y() { return y; }
    public int damage() { return damage; }

    public static GdxProjectileRuntime fire(
            double fromWorldX, double fromWorldY,
            double toWorldX, double toWorldY,
            double speedPx, int damage) {
        double dx = toWorldX - fromWorldX;
        double dy = toWorldY - fromWorldY;
        double dist = Math.sqrt(dx * dx + dy * dy);
        if (dist < 1.0) {
            return new GdxProjectileRuntime(fromWorldX, fromWorldY, 0, 0, damage);
        }
        double vx = (dx / dist) * speedPx;
        double vy = (dy / dist) * speedPx;
        return new GdxProjectileRuntime(fromWorldX, fromWorldY, vx, vy, damage);
    }
}
