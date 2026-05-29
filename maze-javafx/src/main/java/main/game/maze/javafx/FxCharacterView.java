package main.game.maze.javafx;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import main.game.maze.common.graphics.IBoundingBox;
import main.game.maze.common.graphics.ICharacterView;
import main.game.maze.common.graphics.UiScheduler;

/**
 * JavaFX adapter that exposes a {@link Node} through the {@link ICharacterView}
 * facade. Layout writes are routed through {@link UiScheduler} so callers may
 * mutate position from any thread without violating the FX threading model.
 *
 * <p>This adapter does not own image/sprite content; character classes still
 * load and assign images directly on their {@code ImageView}. The adapter only
 * mediates the small set of view properties that business logic touches.
 */
public final class FxCharacterView implements ICharacterView {
    private volatile Node node;

    public FxCharacterView(Node node) {
        this.node = node;
    }

    /** Exposes the underlying node for code that still needs it (e.g. image swap). */
    public Node getNode() {
        return node;
    }

    @Override public double getX()      { Node n = node; return n == null ? 0 : n.getLayoutX(); }
    @Override public double getY()      { Node n = node; return n == null ? 0 : n.getLayoutY(); }
    @Override public double getWidth()  { Node n = node; return n == null ? 0 : n.getBoundsInLocal().getWidth(); }
    @Override public double getHeight() { Node n = node; return n == null ? 0 : n.getBoundsInLocal().getHeight(); }

    @Override
    public IBoundingBox getBoundingBox() {
        Node n = node;
        if (n == null) return IBoundingBox.of(0, 0, 0, 0);
        double x = n.getLayoutX();
        double y = n.getLayoutY();
        double w = n.getBoundsInLocal().getWidth();
        double h = n.getBoundsInLocal().getHeight();
        return IBoundingBox.of(x, y, w, h);
    }

    @Override
    public void setPosition(double x, double y) {
        Node n = node;
        if (n == null) return;
        UiScheduler.get().runOnUiThread(() -> {
            n.setLayoutX(x);
            n.setLayoutY(y);
        });
    }

    @Override
    public void setScale(double sx, double sy) {
        Node n = node;
        if (n == null) return;
        UiScheduler.get().runOnUiThread(() -> {
            n.setScaleX(sx);
            n.setScaleY(sy);
        });
    }

    @Override
    public void setOpacity(double opacity) {
        Node n = node;
        if (n == null) return;
        UiScheduler.get().runOnUiThread(() -> n.setOpacity(opacity));
    }

    @Override
    public void setVisible(boolean visible) {
        Node n = node;
        if (n == null) return;
        UiScheduler.get().runOnUiThread(() -> n.setVisible(visible));
    }

    @Override
    public void setViewOrder(double order) {
        Node n = node;
        if (n == null) return;
        UiScheduler.get().runOnUiThread(() -> n.setViewOrder(order));
    }

    @Override
    public void clearEffect() {
        Node n = node;
        if (n == null) return;
        UiScheduler.get().runOnUiThread(() -> n.setEffect(null));
    }

    @Override
    public void detachFromParent() {
        Node n = node;
        if (n == null) return;
        UiScheduler.get().runOnUiThread(() -> {
            try {
                n.setEffect(null);
                if (n.getParent() instanceof Pane p) {
                    p.getChildren().remove(n);
                }
            } catch (Exception ignored) {
                // best-effort detach
            }
        });
        node = null;
    }

    @Override
    public boolean isDisposed() {
        return node == null;
    }
}
