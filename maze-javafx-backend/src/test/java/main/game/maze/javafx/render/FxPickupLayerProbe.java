package main.game.maze.javafx.render;

import java.util.List;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.game.maze.game.items.Pickup;
import main.game.maze.game.items.PickupCatalog;
import main.game.maze.game.items.Weapon;
import main.game.maze.mazeworld.Vector2D.VectorFacing;

/** Test access to {@link FxPickupLayer} internals from other test packages. */
public final class FxPickupLayerProbe {

    private final FxPickupLayer layer;

    public FxPickupLayerProbe(Pane board) {
        this.layer = new FxPickupLayer(() -> board);
    }

    public void showItems(List<Pickup> items, PickupCatalog catalog, double clock) {
        layer.showItems(items, catalog, clock);
    }

    public void showHeldWeapon(Weapon weapon, int stock, PickupCatalog catalog, double x, double y, double half,
                               VectorFacing facing) {
        layer.showHeldWeapon(weapon, stock, catalog, x, y, half, facing);
    }

    public void showShield(double x, double y, double radius, double remaining, double clock) {
        layer.showShield(x, y, radius, remaining, clock);
    }

    public void clear() {
        layer.clear();
    }

    public int itemCount() {
        return layer.itemCount();
    }

    public ImageView heldView() {
        return layer.heldView();
    }
}
