package main.game.maze.javafx.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.game.maze.game.strike.InfantrySquad;
import main.game.maze.game.strike.StrikeVisuals;
import main.game.maze.game.ufo.UfoRaid;

/**
 * JavaFX view of the UFO retaliation (the model is the shared {@link UfoRaid}): the UFOs, the spot their beam marks and
 * burns on the floor, the column of light down to it, and the red soldiers (sprites chosen by
 * {@link StrikeVisuals#soldierSprite(InfantrySquad.SoldierView, String)}). Board coordinates are y-down, the same as the
 * shared world. Views are pooled and reused frame by frame. Use on the JavaFX Application Thread. The counterpart of the
 * libGDX {@code GdxUfoSupport}.
 */
public final class FxUfoLayer {

    /** The beamed spot lies on the floor, the soldiers with the characters, the UFOs and their light over everything. */
    private static final double SPOT_VIEW_ORDER = -3.5;
    private static final double SOLDIER_VIEW_ORDER = -4;
    private static final double AIR_VIEW_ORDER = -13;
    private static final int COLUMN_DISCS = 8;
    /** A UFO is drawn this many times the player's size. */
    private static final double UFO_SIZE_FACTOR = 2.6;

    private final Supplier<Pane> board;
    private final List<ImageView> soldierViews = new ArrayList<>();
    private final List<ImageView> spotViews = new ArrayList<>();
    private final List<ImageView> columnViews = new ArrayList<>();
    private final List<ImageView> ufoViews = new ArrayList<>();
    private final Map<String, Image> images = new HashMap<>();

    public FxUfoLayer(Supplier<Pane> board) {
        this.board = board;
    }

    /** Shows the raid as it is now: {@code playerSize} is the size of the player's sprite. */
    public void show(UfoRaid raid, double playerSize, double clockSeconds) {
        Pane pane = board.get();
        if (pane == null) {
            return;
        }
        if (raid.spec() == null || !raid.active()) {
            clear();
            return;
        }
        List<InfantrySquad.SoldierView> soldiers = raid.soldiers();
        List<UfoRaid.UfoView> ufos = raid.ufos();
        List<UfoRaid.BeamView> beams = ufos.stream().map(UfoRaid.UfoView::beam).filter(java.util.Objects::nonNull).toList();
        long burning = beams.stream().filter(beam -> !beam.charging()).count();

        grow(soldierViews, soldiers.size(), SOLDIER_VIEW_ORDER);
        grow(spotViews, beams.size(), SPOT_VIEW_ORDER);
        grow(columnViews, (int) burning * COLUMN_DISCS, AIR_VIEW_ORDER + 0.5);
        grow(ufoViews, ufos.size(), AIR_VIEW_ORDER);

        for (int i = 0; i < soldierViews.size(); i++) {
            ImageView view = soldierViews.get(i);
            if (i >= soldiers.size()) {
                pane.getChildren().remove(view);
                continue;
            }
            InfantrySquad.SoldierView soldier = soldiers.get(i);
            attach(pane, view);
            view.setImage(image(StrikeVisuals.soldierSprite(soldier, StrikeVisuals.RED_SOLDIER_DIR)));
            place(view, soldier.x(), soldier.y(), playerSize * StrikeVisuals.SOLDIER_SIZE_FACTOR);
            view.setScaleX(StrikeVisuals.soldierFlipX(soldier) ? -1 : 1);
        }
        for (int i = 0; i < spotViews.size(); i++) {
            ImageView view = spotViews.get(i);
            if (i >= beams.size()) {
                pane.getChildren().remove(view);
                continue;
            }
            UfoRaid.BeamView beam = beams.get(i);
            attach(pane, view);
            view.setImage(image(raid.spec().beamSprite()));
            double full = beam.radius() * 2;
            place(view, beam.x(), beam.y(), beam.charging() ? full * (0.25 + 0.75 * beam.progress())
                    : full * (0.95 + 0.07 * Math.sin(clockSeconds * 30)));
        }
        int disc = 0;
        for (UfoRaid.BeamView beam : beams) {
            if (beam.charging()) {
                continue;
            }
            for (int k = 1; k <= COLUMN_DISCS; k++, disc++) {
                double t = k / (double) (COLUMN_DISCS + 1);   // 0 at the UFO, 1 at the ground
                ImageView view = columnViews.get(disc);
                attach(pane, view);
                view.setImage(image(raid.spec().beamSprite()));
                place(view, beam.ufoX() + (beam.x() - beam.ufoX()) * t, beam.ufoY() + (beam.y() - beam.ufoY()) * t,
                        beam.radius() * (0.5 + 1.2 * t));
            }
        }
        for (int i = columnViews.size() - 1; i >= disc; i--) {
            pane.getChildren().remove(columnViews.get(i));
        }
        for (int i = 0; i < ufoViews.size(); i++) {
            ImageView view = ufoViews.get(i);
            if (i >= ufos.size()) {
                pane.getChildren().remove(view);
                continue;
            }
            UfoRaid.UfoView ufo = ufos.get(i);
            attach(pane, view);
            view.setImage(image(raid.spec().ufoSprite()));
            double bob = Math.sin(clockSeconds * 5 + ufo.x() * 0.02) * 3;
            place(view, ufo.x(), ufo.y() + bob, playerSize * UFO_SIZE_FACTOR);
            view.setScaleX(ufo.movingRight() ? -1 : 1);
        }
    }

    /** Level teardown or the raid is over: removes every view. */
    public void clear() {
        Pane pane = board.get();
        if (pane != null) {
            pane.getChildren().removeAll(soldierViews);
            pane.getChildren().removeAll(spotViews);
            pane.getChildren().removeAll(columnViews);
            pane.getChildren().removeAll(ufoViews);
        }
        soldierViews.clear();
        spotViews.clear();
        columnViews.clear();
        ufoViews.clear();
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
        return images.computeIfAbsent(path, p -> new Image(FxUfoLayer.class.getResource(p).toExternalForm()));
    }
}
