package main.game.maze.libgdx;

import com.badlogic.gdx.scenes.scene2d.Actor;
import main.game.maze.common.graphics.IBoundingBox;
import main.game.maze.common.graphics.ICharacterView;
import main.game.maze.common.graphics.UiScheduler;

/**
 * libGDX adapter that exposes a Scene2D {@link Actor} through the
 * {@link ICharacterView} facade. Mutating writes are routed through
 * {@link UiScheduler} so callers can update from any thread.
 *
 * <p>Note on coordinate model: libGDX's Scene2D origin is bottom-left while
 * JavaFX is top-left. The current implementation passes coordinates straight
 * through; coordinate-system reconciliation belongs in the future
 * backend-agnostic game-loop refactor and is intentionally not handled here.
 *
 * <p>"View order" is mapped to Scene2D z-order: higher order means rendered
 * later, i.e. on top, matching JavaFX's semantics close enough for HUD vs.
 * sprite layering.
 */
public final class GdxCharacterView implements ICharacterView {
    private volatile Actor actor;

    public GdxCharacterView(Actor actor) {
        this.actor = actor;
    }

    /** Exposes the underlying actor for backend-specific code (sprite swap). */
    public Actor getActor() {
        return actor;
    }

    @Override public double getX()      { Actor a = actor; return a == null ? 0 : a.getX(); }
    @Override public double getY()      { Actor a = actor; return a == null ? 0 : a.getY(); }
    @Override public double getWidth()  { Actor a = actor; return a == null ? 0 : a.getWidth(); }
    @Override public double getHeight() { Actor a = actor; return a == null ? 0 : a.getHeight(); }

    @Override
    public IBoundingBox getBoundingBox() {
        Actor a = actor;
        if (a == null) return IBoundingBox.of(0, 0, 0, 0);
        return IBoundingBox.of(a.getX(), a.getY(), a.getWidth(), a.getHeight());
    }

    @Override
    public void setPosition(double x, double y) {
        Actor a = actor;
        if (a == null) return;
        UiScheduler.get().runOnUiThread(() -> a.setPosition((float) x, (float) y));
    }

    @Override
    public void setScale(double sx, double sy) {
        Actor a = actor;
        if (a == null) return;
        UiScheduler.get().runOnUiThread(() -> a.setScale((float) sx, (float) sy));
    }

    @Override
    public void setOpacity(double opacity) {
        Actor a = actor;
        if (a == null) return;
        UiScheduler.get().runOnUiThread(() -> {
            var color = a.getColor();
            a.setColor(color.r, color.g, color.b, (float) opacity);
        });
    }

    @Override
    public void setVisible(boolean visible) {
        Actor a = actor;
        if (a == null) return;
        UiScheduler.get().runOnUiThread(() -> a.setVisible(visible));
    }

    @Override
    public void setViewOrder(double order) {
        Actor a = actor;
        if (a == null) return;
        UiScheduler.get().runOnUiThread(() -> a.setZIndex((int) order));
    }

    @Override
    public void clearEffect() {
        Actor a = actor;
        if (a == null) return;
        UiScheduler.get().runOnUiThread(a::clearActions);
    }

    @Override
    public void detachFromParent() {
        Actor a = actor;
        if (a == null) return;
        UiScheduler.get().runOnUiThread(() -> {
            try {
                a.clearActions();
                a.remove();
            } catch (Exception ignored) {
                // best-effort detach
            }
            actor = null;
        });
    }

    @Override
    public boolean isDisposed() {
        return actor == null;
    }
}
