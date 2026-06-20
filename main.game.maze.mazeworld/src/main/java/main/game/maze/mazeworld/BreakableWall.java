package main.game.maze.mazeworld;

import java.util.Objects;

/**
 * A wall segment that can absorb damage and be destroyed.
 *
 * <p>Hit points are derived from the wall's {@link WallMaterialSpec} so that
 * material type (Glass 5 HP, Dirt 10 HP, Wood 20 HP, Stone 40 HP) determines
 * durability rather than hard-coded values.
 */
public final class BreakableWall {

    public final Vector2D geometry;
    public final WallMaterialSpec material;
    private int remainingHp;

    public BreakableWall(Vector2D geometry, WallMaterialSpec material) {
        this.geometry = Objects.requireNonNull(geometry, "geometry must not be null");
        this.material = Objects.requireNonNull(material, "material must not be null");
        this.remainingHp = material.hitPoints();
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
