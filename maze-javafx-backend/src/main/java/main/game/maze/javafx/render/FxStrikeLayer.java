package main.game.maze.javafx.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import main.game.maze.game.items.PickupCatalog;
import main.game.maze.game.strike.InfantrySquad;
import main.game.maze.game.strike.ScheduledBlast;
import main.game.maze.game.strike.StrikeVisuals;

/**
 * JavaFX view of the strike weapons (the model is the shared {@code StrikeWeapons}): the animated soldiers of the
 * infantry strike (sprite chosen by {@link StrikeVisuals#soldierSprite}) and each grenade or fat bomb lying on the floor,
 * drawn as itself with a flickering fuse ring (radioactive green round the nuclear grenade). Board coordinates are y-down,
 * the same as the shared world. Views are pooled and reused frame by frame. Use on the JavaFX Application Thread.
 */
public final class FxStrikeLayer {

    /** Soldiers are drawn with the characters; what lies on the floor, under them. */
    private static final double SOLDIER_VIEW_ORDER = -4;
    private static final double LYING_VIEW_ORDER = -3;

    private final Supplier<Pane> board;
    private final List<ImageView> soldierViews = new ArrayList<>();
    private final List<Circle> fuseViews = new ArrayList<>();
    private final List<ImageView> lyingViews = new ArrayList<>();
    private final Map<String, Image> images = new HashMap<>();

    public FxStrikeLayer(Supplier<Pane> board) {
        this.board = board;
    }

    /** Shows exactly these soldiers, each the given size, in their current pose. */
    public void showSoldiers(List<InfantrySquad.SoldierView> soldiers, double size) {
        Pane pane = board.get();
        if (pane == null) {
            return;
        }
        grow(soldierViews, soldiers.size(), SOLDIER_VIEW_ORDER);
        for (int i = 0; i < soldierViews.size(); i++) {
            ImageView view = soldierViews.get(i);
            if (i >= soldiers.size()) {
                pane.getChildren().remove(view);
                continue;
            }
            InfantrySquad.SoldierView soldier = soldiers.get(i);
            attach(pane, view);
            view.setImage(image(StrikeVisuals.soldierSprite(soldier)));
            place(view, soldier.x(), soldier.y(), size);
            view.setScaleX(StrikeVisuals.soldierFlipX(soldier) ? -1 : 1);
        }
    }

    /** Each grenade or bomb lying on the floor: the weapon itself and a flickering fuse ring round it. */
    public void showFuses(List<ScheduledBlast> blasts, PickupCatalog items, double clockSeconds) {
        Pane pane = board.get();
        if (pane == null) {
            return;
        }
        List<ScheduledBlast> lying = blasts.stream().filter(ScheduledBlast::showsFuse).toList();
        while (fuseViews.size() < lying.size()) {
            Circle marker = new Circle();
            marker.setFill(Color.TRANSPARENT);
            marker.setStrokeWidth(2.2);
            marker.setMouseTransparent(true);
            marker.setViewOrder(LYING_VIEW_ORDER);
            fuseViews.add(marker);
        }
        grow(lyingViews, lying.size(), LYING_VIEW_ORDER + 0.5);
        for (int i = 0; i < fuseViews.size(); i++) {
            Circle marker = fuseViews.get(i);
            ImageView weapon = lyingViews.get(i);
            if (i >= lying.size()) {
                pane.getChildren().remove(marker);
                pane.getChildren().remove(weapon);
                continue;
            }
            ScheduledBlast blast = lying.get(i);
            attach(pane, marker);
            float[] colour = StrikeVisuals.fuseColour(blast);
            marker.setCenterX(blast.x());
            marker.setCenterY(blast.y());
            marker.setRadius(StrikeVisuals.fuseRadius(blast));
            marker.setStroke(Color.color(colour[0], colour[1], colour[2], StrikeVisuals.fuseAlpha(blast, clockSeconds)));
            String sprite = StrikeVisuals.lyingSprite(blast, items);
            if (sprite == null) {
                pane.getChildren().remove(weapon);
                continue;
            }
            attach(pane, weapon);
            weapon.setImage(image(sprite));
            place(weapon, blast.x(), blast.y(), StrikeVisuals.lyingSize(blast, items));
        }
    }

    /** Level teardown: removes every soldier, weapon and marker. */
    public void clear() {
        Pane pane = board.get();
        if (pane != null) {
            pane.getChildren().removeAll(soldierViews);
            pane.getChildren().removeAll(fuseViews);
            pane.getChildren().removeAll(lyingViews);
        }
        soldierViews.clear();
        fuseViews.clear();
        lyingViews.clear();
    }

    private static void grow(List<ImageView> views, int size, double viewOrder) {
        while (views.size() < size) {
            ImageView view = new ImageView();
            view.setMouseTransparent(true);
            view.setPreserveRatio(true);
            view.setViewOrder(viewOrder);
            views.add(view);
        }
    }

    private static void attach(Pane pane, javafx.scene.Node node) {
        if (node.getParent() == null) {
            pane.getChildren().add(node);
        }
    }

    private static void place(ImageView view, double centerX, double centerY, double size) {
        view.setFitWidth(size);
        view.setFitHeight(size);
        view.setLayoutX(centerX - size / 2);
        view.setLayoutY(centerY - size / 2);
    }

    private Image image(String path) {
        return images.computeIfAbsent(path, p -> new Image(FxStrikeLayer.class.getResource(p).toExternalForm()));
    }
}
