package main.game.maze.javafx.render;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.game.maze.common.graphics.fire.ShieldBubble;
import main.game.maze.game.items.Pickup;
import main.game.maze.game.items.PickupCatalog;
import main.game.maze.game.items.Weapon;
import main.game.maze.mazeworld.Vector2D.VectorFacing;

/**
 * JavaFX view of the pickups (the model is the shared {@code PickupField}): item sprites lying in
 * the maze, the weapon in the player's hands and the power shield bubble. Board coordinates are
 * y-down, the same as the shared world. Use on the JavaFX Application Thread.
 */
public final class FxPickupLayer {

    static final double PICKUP_SIZE = 24;
    /** Just above the maze canvas, like the remains; characters are drawn over items. */
    private static final int FLOOR_INDEX = 1;
    /** In front of the player sprite. */
    private static final double HELD_VIEW_ORDER = -5;
    private static final double SHIELD_VIEW_ORDER = -6;

    private final Supplier<Pane> board;
    private final Map<Long, ImageView> itemViews = new HashMap<>();
    private final Map<String, Image> images = new HashMap<>();
    private ImageView heldView;
    private Canvas shieldCanvas;

    public FxPickupLayer(Supplier<Pane> board) {
        this.board = board;
    }

    /** Shows exactly the given items (adds new ones, removes picked-up ones), gently bobbing. */
    public void showItems(List<Pickup> items, PickupCatalog catalog, double clockSeconds) {
        Pane pane = board.get();
        if (pane == null) {
            return;
        }
        Set<Long> present = new HashSet<>();
        for (Pickup pickup : items) {
            present.add(pickup.id());
            ImageView view = itemViews.computeIfAbsent(pickup.id(), id -> {
                ImageView created = new ImageView(image(catalog.spec(pickup.kind()).sprite()));
                created.setFitWidth(PICKUP_SIZE);
                created.setFitHeight(PICKUP_SIZE);
                created.setPreserveRatio(true);
                created.setMouseTransparent(true);
                pane.getChildren().add(Math.min(FLOOR_INDEX, pane.getChildren().size()), created);
                return created;
            });
            double bob = Math.sin(clockSeconds * 4 + pickup.id()) * 2;
            view.setLayoutX(pickup.x() - PICKUP_SIZE / 2);
            view.setLayoutY(pickup.y() - PICKUP_SIZE / 2 + bob);
        }
        itemViews.entrySet().removeIf(entry -> {
            if (!present.contains(entry.getKey())) {
                pane.getChildren().remove(entry.getValue());
                return true;
            }
            return false;
        });
    }

    /**
     * The laser rifle or sledgehammer in the player's hands, held towards the facing (mirrored when
     * facing left); hidden for bombs or an empty weapon.
     */
    public void showHeldWeapon(Weapon weapon, int stock, PickupCatalog catalog, double playerCenterX,
                               double playerCenterY, double playerHalfSize, VectorFacing facing) {
        Pane pane = board.get();
        if (pane == null) {
            return;
        }
        if (weapon == Weapon.BOMB || stock <= 0) {
            hide(heldView);
            return;
        }
        if (heldView == null) {
            heldView = new ImageView();
            heldView.setMouseTransparent(true);
            heldView.setViewOrder(HELD_VIEW_ORDER);
            heldView.setPreserveRatio(true);
        }
        if (heldView.getParent() == null) {
            pane.getChildren().add(heldView);
        }
        heldView.setImage(image(catalog.spec(weapon.pickup()).sprite()));
        double size = playerHalfSize * 1.5;
        heldView.setFitWidth(size);
        heldView.setFitHeight(size);
        int fx = facing == VectorFacing.LEFT ? -1 : facing == VectorFacing.RIGHT || facing == VectorFacing.IDLE ? 1 : 0;
        int fy = facing == VectorFacing.UP ? -1 : facing == VectorFacing.DOWN ? 1 : 0;
        double reach = playerHalfSize * 0.8;
        heldView.setLayoutX(playerCenterX + fx * reach - size / 2);
        heldView.setLayoutY(playerCenterY + fy * reach + playerHalfSize * 0.2 - size / 2);
        heldView.setScaleX(fx < 0 ? -1 : 1);
        heldView.setRotate(fy > 0 ? 90 : fy < 0 ? -90 : 0);
    }

    /** The power shield bubble around the player while it is up. */
    public void showShield(double centerX, double centerY, double radius, double remainingSeconds, double clock) {
        Pane pane = board.get();
        if (pane == null) {
            return;
        }
        if (!(remainingSeconds > 0)) {
            hide(shieldCanvas);
            return;
        }
        double extent = radius * ShieldBubble.EXTENT_FACTOR;
        if (shieldCanvas == null || shieldCanvas.getWidth() < extent * 2) {
            hide(shieldCanvas);
            shieldCanvas = new Canvas(extent * 2, extent * 2);
            shieldCanvas.setMouseTransparent(true);
            shieldCanvas.setViewOrder(SHIELD_VIEW_ORDER);
        }
        if (shieldCanvas.getParent() == null) {
            pane.getChildren().add(shieldCanvas);
        }
        double half = shieldCanvas.getWidth() / 2;
        shieldCanvas.setLayoutX(centerX - half);
        shieldCanvas.setLayoutY(centerY - half);
        shieldCanvas.getGraphicsContext2D().clearRect(0, 0, shieldCanvas.getWidth(), shieldCanvas.getHeight());
        FxExplosionView.paint(shieldCanvas.getGraphicsContext2D(),
                ShieldBubble.puffs(centerX, centerY, radius, remainingSeconds, clock),
                shieldCanvas.getLayoutX(), shieldCanvas.getLayoutY());
    }

    /** New level: remove everything. */
    public void clear() {
        Pane pane = board.get();
        if (pane != null) {
            pane.getChildren().removeAll(itemViews.values());
        }
        itemViews.clear();
        hide(heldView);
        hide(shieldCanvas);
    }

    int itemCount() {
        return itemViews.size();
    }

    ImageView heldView() {
        return heldView;
    }

    private void hide(Node node) {
        Pane pane = board.get();
        if (node != null && pane != null) {
            pane.getChildren().remove(node);
        }
    }

    private Image image(String path) {
        return images.computeIfAbsent(path, p -> {
            URL url = FxPickupLayer.class.getResource(p);
            return url != null ? new Image(url.toExternalForm()) : null;
        });
    }
}
