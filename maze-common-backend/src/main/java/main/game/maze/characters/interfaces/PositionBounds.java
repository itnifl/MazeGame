package main.game.maze.characters.interfaces;

/**
 * Backend-neutral bounding-box abstraction used for position/collision evaluation.
 * Replaces the JavaFX-specific {@code javafx.geometry.Bounds} in shared interfaces
 * so that {@link ICanSubscribeAndNotifyPosition} can live in the common backend
 * without a JavaFX compile dependency.
 *
 * <p>JavaFX implementations wrap {@code javafx.geometry.Bounds} via a thin adapter.</p>
 */
public interface PositionBounds {

    double getMinX();
    double getMinY();
    double getMaxX();
    double getMaxY();

    default double getWidth()   { return getMaxX() - getMinX(); }
    default double getHeight()  { return getMaxY() - getMinY(); }
    default double getCenterX() { return getMinX() + getWidth()  / 2.0; }
    default double getCenterY() { return getMinY() + getHeight() / 2.0; }

    /**
     * Returns true if this bounding box overlaps {@code other} (AABB overlap test).
     * Implementors may override for specialised geometry, but the default covers
     * the standard axis-aligned case and guards against {@code null}.
     */
    default boolean intersects(PositionBounds other) {
        if (other == null) return false;
        return !(other.getMaxX() < getMinX() || other.getMinX() > getMaxX()
              || other.getMaxY() < getMinY() || other.getMinY() > getMaxY());
    }
}
