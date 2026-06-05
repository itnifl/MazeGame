package main.game.maze.common.graphics;

/**
 * Read/write facade over a renderable character sprite. Business logic talks
 * to this interface so it does not need to import a specific rendering
 * backend's node type for the few view properties it actually manipulates
 * (position, scale, visibility, view order, effect clearing).
 *
 * <p>Each backend provides its own adapter:
 * <ul>
 *   <li>JavaFX: {@code main.game.maze.javafx.FxCharacterView} wraps a
 *       {@code javafx.scene.Node}.</li>
 *   <li>libGDX: {@code main.game.maze.libgdx.GdxCharacterView} wraps a
 *       {@code com.badlogic.gdx.scenes.scene2d.Actor}.</li>
 * </ul>
 */
public interface ICharacterView {
    double getX();
    double getY();
    double getWidth();
    double getHeight();

    /** Current axis-aligned bounds for collision or overlap checks. */
    IBoundingBox getBoundingBox();

    void setPosition(double x, double y);
    void setScale(double sx, double sy);
    void setOpacity(double opacity);
    void setVisible(boolean visible);
    void setViewOrder(double order);

    /** Clears any visual effect applied to the sprite (color flash, etc.). */
    void clearEffect();

    /**
     * Removes the sprite from its parent container, if any. Safe to call
     * multiple times or when never attached.
     */
    void detachFromParent();

    /** True when the sprite is no longer usable (disposed/detached). */
    boolean isDisposed();
}


