package main.game.maze.javafx.render;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import main.game.maze.game.strike.InfantrySquad;
import main.game.maze.game.strike.ScheduledBlast;
import main.game.maze.game.strike.StrikeVisuals;

/**
 * JavaFX view of the strike weapons (the model is the shared {@code StrikeWeapons}): the soldiers of the infantry strike
 * and a glowing marker on each grenade or fat bomb lying on the floor. Board coordinates are y-down, the same as the
 * shared world. Views are pooled and reused frame by frame. Use on the JavaFX Application Thread.
 */
public final class FxStrikeLayer {

    /** Soldiers are drawn with the characters; the fuse markers on the floor, under them. */
    private static final double SOLDIER_VIEW_ORDER = -4;
    private static final double FUSE_VIEW_ORDER = -3;

    private final Supplier<Pane> board;
    private final List<ImageView> soldierViews = new ArrayList<>();
    private final List<Circle> fuseViews = new ArrayList<>();
    private Image soldierImage;

    public FxStrikeLayer(Supplier<Pane> board) {
        this.board = board;
    }

    /** Shows exactly these soldiers, each the given size, mirrored when it faces left. */
    public void showSoldiers(List<InfantrySquad.SoldierView> soldiers, double size) {
        Pane pane = board.get();
        if (pane == null) {
            return;
        }
        while (soldierViews.size() < soldiers.size()) {
            ImageView view = new ImageView(soldierImage());
            view.setMouseTransparent(true);
            view.setPreserveRatio(true);
            view.setViewOrder(SOLDIER_VIEW_ORDER);
            soldierViews.add(view);
        }
        for (int i = 0; i < soldierViews.size(); i++) {
            ImageView view = soldierViews.get(i);
            if (i >= soldiers.size()) {
                pane.getChildren().remove(view);
                continue;
            }
            InfantrySquad.SoldierView soldier = soldiers.get(i);
            if (view.getParent() == null) {
                pane.getChildren().add(view);
            }
            view.setFitWidth(size);
            view.setFitHeight(size);
            view.setLayoutX(soldier.x() - size / 2);
            view.setLayoutY(soldier.y() - size / 2);
            view.setScaleX(soldier.facingX() < 0 ? -1 : 1);
            view.setOpacity(soldier.firing() ? 1.0 : 0.95);
        }
    }

    /** A flickering marker on each blast that lies on the floor with a burning fuse. */
    public void showFuses(List<ScheduledBlast> blasts, double clockSeconds) {
        Pane pane = board.get();
        if (pane == null) {
            return;
        }
        List<ScheduledBlast> lying = blasts.stream().filter(ScheduledBlast::showsFuse).toList();
        while (fuseViews.size() < lying.size()) {
            Circle marker = new Circle();
            marker.setFill(Color.rgb(40, 40, 40, 0.9));
            marker.setStrokeWidth(2.2);
            marker.setMouseTransparent(true);
            marker.setViewOrder(FUSE_VIEW_ORDER);
            fuseViews.add(marker);
        }
        for (int i = 0; i < fuseViews.size(); i++) {
            Circle marker = fuseViews.get(i);
            if (i >= lying.size()) {
                pane.getChildren().remove(marker);
                continue;
            }
            ScheduledBlast blast = lying.get(i);
            if (marker.getParent() == null) {
                pane.getChildren().add(marker);
            }
            marker.setCenterX(blast.x());
            marker.setCenterY(blast.y());
            marker.setRadius(StrikeVisuals.fuseRadius(blast));
            marker.setStroke(Color.rgb(255, 191, 71, StrikeVisuals.fuseAlpha(blast, clockSeconds)));
        }
    }

    /** Level teardown: removes every soldier and marker. */
    public void clear() {
        Pane pane = board.get();
        if (pane != null) {
            pane.getChildren().removeAll(soldierViews);
            pane.getChildren().removeAll(fuseViews);
        }
        soldierViews.clear();
        fuseViews.clear();
    }

    private Image soldierImage() {
        if (soldierImage == null) {
            soldierImage = new Image(FxStrikeLayer.class.getResource(StrikeVisuals.SOLDIER_SPRITE).toExternalForm());
        }
        return soldierImage;
    }
}
