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
import main.game.maze.common.graphics.fire.FirePuff;
import main.game.maze.common.graphics.fire.HealFeedback;
import main.game.maze.common.graphics.fire.HealFlashEffect;
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

    /** Just above the maze canvas, like the remains; characters are drawn over items. */
    private static final int FLOOR_INDEX = 1;
    /** In front of the player sprite. */
    private static final double HELD_VIEW_ORDER = -5;
    private static final double SHIELD_VIEW_ORDER = -6;
    /** The heal flash glows over the player sprite; the arrow is on top of everything near the player. */
    private static final double HEAL_FLASH_VIEW_ORDER = -7;
    private static final double HEAL_ARROW_VIEW_ORDER = -8;

    private final Supplier<Pane> board;
    private final Map<Long, ImageView> itemViews = new HashMap<>();
    private final Map<String, Image> images = new HashMap<>();
    private ImageView heldView;
    private Canvas shieldCanvas;
    private Canvas healCanvas;
    private ImageView healArrowView;

    public FxPickupLayer(Supplier<Pane> board) {
        this.board = board;
    }

    /** Shows exactly the given items (adds new ones, removes picked-up ones), gently bobbing. */
    public void showItems(List<Pickup> items, PickupCatalog catalog, double clockSeconds) {
        Pane pane = board.get();
        if (pane == null) {
            return;
        }
        double size = catalog.itemSize();
        Set<Long> present = new HashSet<>();
        for (Pickup pickup : items) {
            present.add(pickup.id());
            ImageView view = itemViews.computeIfAbsent(pickup.id(), id -> {
                ImageView created = new ImageView(image(catalog.spec(pickup.kind()).sprite()));
                created.setFitWidth(size);
                created.setFitHeight(size);
                created.setPreserveRatio(true);
                created.setMouseTransparent(true);
                pane.getChildren().add(Math.min(FLOOR_INDEX, pane.getChildren().size()), created);
                return created;
            });
            double bob = Math.sin(clockSeconds * 4 + pickup.id()) * 2;
            view.setLayoutX(pickup.x() - size / 2);
            view.setLayoutY(pickup.y() - size / 2 + bob);
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
        shieldCanvas = paintAround(pane, shieldCanvas, SHIELD_VIEW_ORDER, centerX, centerY,
                radius * ShieldBubble.EXTENT_FACTOR, ShieldBubble.puffs(centerX, centerY, radius, remainingSeconds, clock));
    }

    /**
     * After a heart: the player flashes ({@link HealFlashEffect}) and a green arrow rises over the head
     * ({@link HealFeedback}), both following the player; hidden when the feedback is over.
     */
    public void showHeal(HealFeedback feedback, double centerX, double centerY, double playerSize, double clock) {
        Pane pane = board.get();
        if (pane == null) {
            return;
        }
        if (!feedback.active()) {
            hide(healCanvas);
            hide(healArrowView);
            return;
        }
        HealFlashEffect flash = new HealFlashEffect(centerX, centerY, playerSize);
        double[] bounds = flash.bounds();
        healCanvas = paintAround(pane, healCanvas, HEAL_FLASH_VIEW_ORDER, centerX, centerY,
                (bounds[2] - bounds[0]) / 2, flash.puffs(feedback.progress(), clock));
        if (healArrowView == null) {
            healArrowView = new ImageView(image(HealFeedback.ARROW_SPRITE));
            healArrowView.setMouseTransparent(true);
            healArrowView.setPreserveRatio(true);
            healArrowView.setViewOrder(HEAL_ARROW_VIEW_ORDER);
        }
        if (healArrowView.getParent() == null) {
            pane.getChildren().add(healArrowView);
        }
        double size = playerSize * HealFeedback.ARROW_SIZE_FACTOR;
        healArrowView.setFitWidth(size);
        healArrowView.setFitHeight(size);
        healArrowView.setLayoutX(centerX - size / 2);
        healArrowView.setLayoutY(centerY - feedback.arrowLift(playerSize) - size / 2);   // y-down: up is minus
    }

    /** Paints puffs on a canvas centred on (x, y) reaching {@code extent} each way (re-created when too small). */
    private Canvas paintAround(Pane pane, Canvas canvas, double viewOrder, double x, double y, double extent,
                               List<FirePuff> puffs) {
        Canvas target = canvas;
        if (target == null || target.getWidth() < extent * 2) {
            hide(target);
            target = new Canvas(extent * 2, extent * 2);
            target.setMouseTransparent(true);
            target.setViewOrder(viewOrder);
        }
        if (target.getParent() == null) {
            pane.getChildren().add(target);
        }
        double half = target.getWidth() / 2;
        target.setLayoutX(x - half);
        target.setLayoutY(y - half);
        target.getGraphicsContext2D().clearRect(0, 0, target.getWidth(), target.getHeight());
        FxExplosionView.paint(target.getGraphicsContext2D(), puffs, target.getLayoutX(), target.getLayoutY());
        return target;
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
        hide(healCanvas);
        hide(healArrowView);
    }

    int itemCount() {
        return itemViews.size();
    }

    ImageView heldView() {
        return heldView;
    }

    ImageView healArrowView() {
        return healArrowView;
    }

    Canvas healCanvas() {
        return healCanvas;
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
