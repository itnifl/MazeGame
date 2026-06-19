package main.game.maze.mazeworld;

import java.util.Objects;

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
        this.geometry = Objects.requireNonNull(geometry, "geometry must not be null");
        this.remainingHp = hitPoints;
    }

    /** Subtracts {@code damage} from remaining HP (floor: 0) and returns the new value. */
    public int applyDamage(int damage) {
        if (damage < 0) throw new IllegalArgumentException("damage must be >= 0");
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
