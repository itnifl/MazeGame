package main.game.maze.characters;

import javafx.geometry.Bounds;
import main.game.maze.characters.interfaces.PositionBounds;

/**
 * JavaFX adapter that wraps {@link javafx.geometry.Bounds} to implement the
 * backend-neutral {@link PositionBounds} interface.
 */
public final class FxPositionBounds implements PositionBounds {

    private final Bounds bounds;

    public FxPositionBounds(Bounds bounds) {
        if (bounds == null) throw new IllegalArgumentException("bounds must not be null");
        this.bounds = bounds;
    }

    @Override public double getMinX() { return bounds.getMinX(); }
    @Override public double getMinY() { return bounds.getMinY(); }
    @Override public double getMaxX() { return bounds.getMaxX(); }
    @Override public double getMaxY() { return bounds.getMaxY(); }

    @Override
    public boolean intersects(PositionBounds other) {
        if (other == null) return false;
        return !(other.getMaxX() < getMinX() || other.getMinX() > getMaxX()
              || other.getMaxY() < getMinY() || other.getMinY() > getMaxY());
    }

    /** Returns the underlying JavaFX {@link Bounds} for code that still needs it directly. */
    public Bounds toJfxBounds() {
        return bounds;
    }
}
