package main.game.maze;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.game.items.PickupCatalog;
import main.game.maze.game.items.PickupCatalogLoader;
import main.game.maze.game.shop.LevelShop;
import main.game.maze.game.shop.ShopCatalogLoader;
import main.game.maze.game.shop.ShopMenu;

/**
 * Controller of the shop screen between levels ({@code shopScreen.fxml}). The model is the shared {@link ShopMenu}; this
 * controller only turns its rows into JavaFX nodes and passes keys and clicks to it, like the libGDX shop overlay.
 * Up/Down choose, Enter or Space buy, Esc closes; a click selects a row and a double click buys it.
 */
public class ShopController implements Initializable {

    private static final String ROW_STYLE = "-fx-padding: 3 8 3 8; -fx-background-radius: 3; -fx-cursor: hand;";
    private static final String SELECTED_STYLE = ROW_STYLE + " -fx-background-color: rgba(255,229,110,0.22);"
            + " -fx-border-color: #ffe56e; -fx-border-radius: 3;";
    private static final String TEXT = "-fx-font-family: 'Consolas'; -fx-font-size: 14px;";
    private static final double ICON_SIZE = 28;

    @FXML
    private AnchorPane screenRoot;
    @FXML
    private Label titleLabel;
    @FXML
    private Label creditsLabel;
    @FXML
    private VBox itemsBox;
    @FXML
    private Label statusLabel;
    @FXML
    private Label helpLabel;

    private final PickupCatalog items = PickupCatalogLoader.loadDefault();
    private final Map<String, Image> icons = new HashMap<>();
    private ShopMenu menu;
    private Runnable onClose = () -> { };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setMenu(new ShopMenu(new LevelShop(ShopCatalogLoader.loadDefault(), App.levelProgress)));
        titleLabel.setText(ShopMenu.TITLE);
        helpLabel.setText(ShopMenu.HELP);
        screenRoot.sceneProperty().addListener((property, before, scene) -> {
            if (scene != null) {
                screenRoot.requestFocus();   // the keys go to the shop
            }
        });
    }

    /** The menu this screen shows (a fresh one when the shop opens). */
    void setMenu(ShopMenu menu) {
        this.menu = menu;
        menu.reset();
        refresh();
    }

    /** What happens when the shop is closed (back to the win screen). */
    public void setOnClose(Runnable onClose) {
        this.onClose = onClose == null ? () -> { } : onClose;
    }

    @FXML
    protected void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case UP, W -> menu.moveUp();
            case DOWN, S -> menu.moveDown();
            case ENTER, SPACE -> buySelected();
            case ESCAPE -> {
                close();
                event.consume();
                return;
            }
            default -> {
                return;
            }
        }
        event.consume();
        refresh();
    }

    @FXML
    protected void buySelected() {
        LevelShop.Result result = menu.buySelected();
        AudioEngine.get().play(ShopMenu.soundFor(result));
        refresh();
    }

    @FXML
    protected void close() {
        onClose.run();
    }

    /** Draws the menu as it is now. */
    void refresh() {
        if (creditsLabel == null) {
            return;   // not loaded from FXML
        }
        creditsLabel.setText(menu.creditsText());
        statusLabel.setText(menu.status());
        itemsBox.getChildren().clear();
        var rows = menu.rows();
        for (int i = 0; i < rows.size(); i++) {
            itemsBox.getChildren().add(rowNode(rows.get(i), i));
        }
    }

    private HBox rowNode(ShopMenu.Row row, int index) {
        ImageView icon = new ImageView(icon(ShopMenu.iconOf(row.item().kind(), items)));
        icon.setFitWidth(ICON_SIZE);
        icon.setFitHeight(ICON_SIZE);
        icon.setPreserveRatio(true);
        String colour = row.affordable() ? "#ffffff" : "#7d8590";
        Label title = label(row.title(), colour, 230);
        title.setTooltip(new javafx.scene.control.Tooltip(row.item().description()));
        Label tier = label(row.item().tier().label(), "#9aa4b0", 80);
        Label price = label(Integer.toString(row.item().price()), row.affordable() ? "#ffe56e" : "#ff6b5e", 60);
        Label owned = label(row.ownedText(), "#8fffe0", 70);
        Region gap = new Region();
        HBox.setHgrow(gap, Priority.ALWAYS);
        HBox box = new HBox(8, icon, title, tier, gap, price, owned);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setStyle(row.selected() ? SELECTED_STYLE : ROW_STYLE);
        box.setOnMouseClicked(event -> {
            if (event.getButton() != MouseButton.PRIMARY) {
                return;
            }
            menu.select(index);
            if (event.getClickCount() >= 2) {
                buySelected();
            } else {
                refresh();
            }
            screenRoot.requestFocus();
        });
        return box;
    }

    private static Label label(String text, String colour, double width) {
        Label label = new Label(text);
        label.setStyle(TEXT + " -fx-text-fill: " + colour + ";");
        label.setMinWidth(width);
        return label;
    }

    private Image icon(String path) {
        return icons.computeIfAbsent(path, p -> new Image(ShopController.class.getResource(p).toExternalForm()));
    }
}
