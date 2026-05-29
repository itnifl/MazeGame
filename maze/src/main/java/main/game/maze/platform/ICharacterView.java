package main.game.maze.platform;

/**
 * Read/write facade over a renderable character sprite. Business logic talks
 * to this interface so it does not need to import {@code javafx.scene.Node}
 * for the few view properties it actually manipulates (position, scale,
 * visibility, view order, effect clearing). Tests can provide a recording
 * fake; production code uses {@link FxCharacterView}.
 */
public interface ICharacterView {
    double getX();
    double getY();
    double getWidth();
    double getHeight();

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
