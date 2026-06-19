package main.game.maze.mazeworld;

/**
 * A wall segment that can absorb damage and be destroyed.
 * Tracks remaining hit points independently of the WallMaterial model so the
 * same material definition can be reused across many wall instances.
 */
public final class BreakableWall {

    public final Vector2D geometry;
    private int remainingHp;

    public BreakableWall(Vector2D geometry, int hitPoints) {
        if (hitPoints <= 0) throw new IllegalArgumentException("hitPoints must be positive");
        this.geometry = geometry;
        this.remainingHp = hitPoints;
    }

    /** Subtracts {@code damage} from remaining HP (floor: 0) and returns the new value. */
    public int applyDamage(int damage) {
        remainingHp = Math.max(0, remainingHp - damage);
        return remainingHp;
    }

    public boolean isDestroyed() {
        return remainingHp <= 0;
    }

    public int getRemainingHp() {
        return remainingHp;
    }
}
