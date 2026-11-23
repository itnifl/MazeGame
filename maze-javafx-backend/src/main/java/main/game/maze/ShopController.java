package main.game.maze;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.game.items.PickupCatalog;
import main.game.maze.game.items.PickupCatalogLoader;
import main.game.maze.game.shop.LevelShop;
import main.game.maze.game.shop.ShopCatalogLoader;
import main.game.maze.game.shop.ShopMenu;
import main.game.maze.game.theme.MenuButton;
import main.game.maze.game.theme.MenuTheme;
import main.game.maze.game.theme.MenuThemeLoader;
import main.game.maze.javafx.menu.FxMenuTheme;

/**
 * Controller of the shop screen between levels ({@code shopScreen.fxml}). The model is the shared {@link ShopMenu}; this
 * controller only turns its rows into item cards and passes keys and clicks to it, like the libGDX shop overlay. The look
 * is the menu theme ({@link FxMenuTheme}). Up/Down choose, Enter or Space buy, Esc closes; a click selects a card and a
 * double click buys it.
 */
public class ShopController implements Initializable {

    static final String CARD_CLASS = "menu-card";
    static final String SELECTED_CLASS = "selected";
    static final String LOCKED_CLASS = "locked";
    static final String SHELF_CLASS = "menu-shelf";
    private static final double ICON_SIZE = 26;
    private static final double BADGE_SIZE = 18;
    private static final double TOKEN_SIZE = 14;

    @FXML
    private AnchorPane screenRoot;
    @FXML
    private ImageView backdrop;
    @FXML
    private ImageView tokenIcon;
    @FXML
    private Label titleLabel;
    @FXML
    private Label subtitleLabel;
    @FXML
    private Label creditsLabel;
    @FXML
    private HBox shelvesBox;
    @FXML
    private Label briefingLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label helpLabel;
    @FXML
    private Button buyButton;
    @FXML
    private Button doneButton;

    private final PickupCatalog items = PickupCatalogLoader.loadDefault();
    private final MenuTheme theme = MenuThemeLoader.loadDefault();
    private ShopMenu menu;
    private Runnable onClose = () -> { };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FxMenuTheme.apply(screenRoot);
        backdrop.setImage(FxMenuTheme.image(MenuTheme.Art.BACKDROP));
        tokenIcon.setImage(FxMenuTheme.image(MenuTheme.Art.TOKEN));
        FxMenuTheme.style(buyButton, MenuButton.BUY);
        FxMenuTheme.style(doneButton, MenuButton.DONE);
        setMenu(new ShopMenu(new LevelShop(ShopCatalogLoader.loadDefault(), App.levelProgress)));
        titleLabel.setText(ShopMenu.TITLE);
        subtitleLabel.setText(ShopMenu.SUBTITLE);
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
            case LEFT, A -> menu.moveLeft();
            case RIGHT, D -> menu.moveRight();
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
        briefingLabel.setText(menu.briefing());
        statusLabel.setText(menu.status());
        statusLabel.getStyleClass().removeAll("menu-text", "menu-good", "menu-bad");
        statusLabel.getStyleClass().add(menu.status().isEmpty() ? "menu-text" : menu.statusIsGood() ? "menu-good" : "menu-bad");
        shelvesBox.getChildren().clear();
        for (ShopMenu.Shelf shelf : menu.shelves()) {
            shelvesBox.getChildren().add(shelf(shelf));
        }
    }

    /** One tier: its rank badge, name and when it is sold as the header, its items as cards under it. */
    VBox shelf(ShopMenu.Shelf shelf) {
        Label name = new Label(shelf.tier().label().toUpperCase(java.util.Locale.ROOT));
        name.getStyleClass().add(FxMenuTheme.tierStyleClass(shelf.tier()));
        HBox header = new HBox(6, icon(theme.badge(shelf.tier()).image(), BADGE_SIZE), name);
        if (shelf.locked()) {
            Label when = new Label(shelf.lockedText());
            when.getStyleClass().add("menu-muted");
            header.getChildren().addAll(icon(theme.image(MenuTheme.Art.LOCK).path(), TOKEN_SIZE), when);
        }
        header.setAlignment(Pos.CENTER_LEFT);
        header.getStyleClass().add("menu-shelf-header");
        VBox column = new VBox(4, header);
        for (ShopMenu.ShelfItem item : shelf.items()) {
            column.getChildren().add(card(item.row(), item.index()));
        }
        column.getStyleClass().add(SHELF_CLASS);
        column.setPrefWidth(240);
        HBox.setHgrow(column, Priority.ALWAYS);
        return column;
    }

    /** One item as a crate card: icon in its well; its name; price in skull coins and what the player has of it. */
    HBox card(ShopMenu.Row row, int index) {
        StackPane slot = new StackPane(icon(ShopMenu.iconOf(row.item().kind(), items), ICON_SIZE));
        slot.getStyleClass().add("menu-slot");

        Label title = new Label(row.title());
        title.getStyleClass().add("menu-card-title");
        Label price = new Label(Integer.toString(row.item().price()));
        price.getStyleClass().addAll("menu-card-line",
                row.locked() ? "menu-muted" : row.affordable() ? "menu-price" : "menu-too-dear");
        Region gap = new Region();
        HBox.setHgrow(gap, Priority.ALWAYS);
        Label owned = new Label(row.locked() ? "" : row.ownedText());
        owned.getStyleClass().addAll("menu-card-line", "menu-accent");
        HBox line = new HBox(4, icon(theme.image(MenuTheme.Art.TOKEN).path(), TOKEN_SIZE), price, gap, owned);
        line.setAlignment(Pos.CENTER_LEFT);
        VBox text = new VBox(1, title, line);
        text.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(text, Priority.ALWAYS);

        HBox card = new HBox(6, slot, text);
        card.setAlignment(Pos.CENTER_LEFT);
        card.getStyleClass().add(CARD_CLASS);
        if (row.locked()) {
            card.getStyleClass().add(LOCKED_CLASS);
        }
        if (row.selected()) {
            card.getStyleClass().add(SELECTED_CLASS);   // a locked card can be chosen too, to read what it does
        }
        if (row.item().description() != null) {
            Tooltip.install(card, new Tooltip(row.item().description()));
        }
        card.setOnMouseClicked(event -> {
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
        return card;
    }

    private static ImageView icon(String path, double size) {
        Image image = FxMenuTheme.image(path);
        ImageView view = new ImageView(image);
        view.setFitWidth(size);
        view.setFitHeight(size);
        view.setPreserveRatio(true);
        view.setSmooth(true);
        return view;
    }
}
