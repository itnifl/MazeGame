package main.game.maze.javafx.render;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import main.game.maze.game.items.WeaponHud;

/**
 * Weapons HUD in the top-left corner (the model is the shared {@link WeaponHud}): one icon per weapon
 * the player has with its stock, the weapon in hand framed in gold so Tab visibly moves the frame,
 * then the lives counter. Rebuilt only when the model changes. Use on the JavaFX Application Thread.
 */
public final class FxWeaponHudView {

    static final String SELECTED_STYLE = "-fx-border-color: #ffd23f; -fx-border-width: 2; -fx-background-color: rgba(255, 210, 63, 0.35);";
    static final String SLOT_STYLE = "-fx-border-color: transparent; -fx-border-width: 2;";
    private static final String PANEL_STYLE = "-fx-background-color: rgba(0, 0, 0, 0.45); -fx-border-color: #8fffe0; "
            + "-fx-border-width: 1; -fx-background-radius: 4; -fx-border-radius: 4;";
    private static final String COUNT_STYLE = "-fx-font-family: 'Consolas'; -fx-font-weight: bold; -fx-font-size: 12px; "
            + "-fx-text-fill: white; -fx-effect: dropshadow(gaussian, black, 2, 1, 0, 0);";

    private final Supplier<Pane> parent;
    private final Map<String, Image> images = new HashMap<>();
    private HBox panel;
    private WeaponHud.Model shown;

    public FxWeaponHudView(Supplier<Pane> parent) {
        this.parent = parent;
    }

    public void show(WeaponHud.Model model) {
        Pane pane = parent.get();
        if (pane == null || model == null) {
            return;
        }
        if (panel == null) {
            panel = new HBox(WeaponHud.PADDING);
            panel.setPadding(new Insets(WeaponHud.PADDING));
            panel.setAlignment(Pos.CENTER_LEFT);
            panel.setStyle(PANEL_STYLE);
            panel.setMouseTransparent(true);
            panel.setViewOrder(-20);
            AnchorPane.setLeftAnchor(panel, 6.0);
            AnchorPane.setTopAnchor(panel, 22.0);
        }
        if (panel.getParent() == null) {
            pane.getChildren().add(panel);
        }
        if (model.equals(shown)) {
            return;
        }
        shown = model;
        panel.getChildren().clear();
        for (WeaponHud.Slot slot : model.slots()) {
            ImageView icon = icon(slot.sprite());
            icon.setOpacity(slot.count() > 0 ? 1 : 0.4);
            Label count = new Label(String.valueOf(slot.count()));
            count.setStyle(COUNT_STYLE);
            StackPane cell = new StackPane(icon, count);
            StackPane.setAlignment(count, Pos.BOTTOM_RIGHT);
            cell.setStyle(slot.selected() ? SELECTED_STYLE : SLOT_STYLE);
            panel.getChildren().add(cell);
        }
        Label lives = new Label("x" + model.lives());
        lives.setStyle(COUNT_STYLE.replace("12px", "15px").replace("white", "#ffec80"));
        HBox livesCell = new HBox(4, icon(model.lifeIcon()), lives);
        livesCell.setAlignment(Pos.CENTER_LEFT);
        livesCell.setPadding(new Insets(0, 0, 0, WeaponHud.PADDING));
        panel.getChildren().add(livesCell);
    }

    /** Level teardown. */
    public void clear() {
        Pane pane = parent.get();
        if (pane != null && panel != null) {
            pane.getChildren().remove(panel);
        }
        shown = null;
    }

    HBox panel() {
        return panel;
    }

    private ImageView icon(String path) {
        ImageView view = new ImageView(images.computeIfAbsent(path, p -> {
            URL url = FxWeaponHudView.class.getResource(p);
            return url != null ? new Image(url.toExternalForm()) : null;
        }));
        view.setFitWidth(WeaponHud.ICON_SIZE);
        view.setFitHeight(WeaponHud.ICON_SIZE);
        view.setPreserveRatio(true);
        return view;
    }
}
