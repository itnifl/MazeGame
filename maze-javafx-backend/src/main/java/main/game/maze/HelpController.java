package main.game.maze;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import main.game.maze.game.theme.ControlsGuide;
import main.game.maze.game.theme.MenuButton;
import main.game.maze.game.theme.MenuTheme;
import main.game.maze.javafx.menu.FxMenuTheme;

/**
 * Controller of the help screen of the main menu ({@code helpScreen.fxml}): the shared {@link ControlsGuide} drawn as
 * keyboard keys (the arrow keys as a cross) with a picture of what each does, like the libGDX help screen. Back or Esc
 * closes it.
 */
public class HelpController implements Initializable {

    static final String ROW_CLASS = "menu-help-row";
    static final String KEY_CLASS = "menu-key";
    private static final double KEY_SIZE = 22;
    private static final double ICON_SIZE = 22;
    private static final double KEYS_WIDTH = 150;

    @FXML
    private AnchorPane screenRoot;
    @FXML
    private ImageView backdrop;
    @FXML
    private Label titleLabel;
    @FXML
    private Label subtitleLabel;
    @FXML
    private VBox entriesBox;
    @FXML
    private VBox tipsBox;
    @FXML
    private Button backButton;

    private Runnable onClose = () -> { };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FxMenuTheme.apply(screenRoot);
        backdrop.setImage(FxMenuTheme.image(MenuTheme.Art.BACKDROP));
        titleLabel.setText(ControlsGuide.TITLE);
        subtitleLabel.setText(ControlsGuide.SUBTITLE);
        FxMenuTheme.style(backButton, MenuButton.BACK);
        for (ControlsGuide.Entry entry : ControlsGuide.entries()) {
            entriesBox.getChildren().add(row(entry));
        }
        for (ControlsGuide.Tip tip : ControlsGuide.tips()) {
            Label text = new Label(tip.text());
            text.getStyleClass().add("menu-accent");
            HBox line = new HBox(8, icon(tip.icon(), 18), text);
            line.setAlignment(Pos.CENTER_LEFT);
            tipsBox.getChildren().add(line);
        }
        screenRoot.sceneProperty().addListener((property, before, scene) -> {
            if (scene != null) {
                screenRoot.requestFocus();
            }
        });
    }

    /** What happens when the help screen is closed (back to the main menu). */
    public void setOnClose(Runnable onClose) {
        this.onClose = onClose == null ? () -> { } : onClose;
    }

    @FXML
    protected void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE || event.getCode() == KeyCode.ENTER) {
            close();
            event.consume();
        }
    }

    @FXML
    protected void close() {
        onClose.run();
    }

    /** One key and what it does: the keys, the picture in its well, the title and the sentence. */
    HBox row(ControlsGuide.Entry entry) {
        Node keys = entry.arrows() ? arrowCross() : keycaps(entry);
        HBox keyArea = new HBox(keys);
        keyArea.setAlignment(Pos.CENTER_RIGHT);
        keyArea.setMinWidth(KEYS_WIDTH);
        keyArea.setPrefWidth(KEYS_WIDTH);

        // the picture of what the key does in its well; an empty space of the same size when there is none
        StackPane slot = new StackPane();
        slot.setMinSize(ICON_SIZE + 8, ICON_SIZE + 8);
        if (entry.icon() != null) {
            slot.getStyleClass().add("menu-slot");
            slot.getChildren().add(icon(entry.icon(), ICON_SIZE));
        }

        Label title = new Label(entry.title());
        title.getStyleClass().add("menu-card-title");
        Label text = new Label(entry.text());
        text.getStyleClass().add("menu-muted");
        text.setWrapText(true);
        VBox words = new VBox(0, title, text);
        words.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(words, Priority.ALWAYS);

        HBox row = new HBox(10, keyArea, slot, words);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().addAll("menu-card", ROW_CLASS);
        return row;
    }

    /** The arrow keys as on a keyboard: up above, left, down and right below. */
    private static GridPane arrowCross() {
        GridPane cross = new GridPane();
        cross.setHgap(2);
        cross.setVgap(2);
        cross.add(icon(FxMenuTheme.image(MenuTheme.Art.KEY_UP)), 1, 0);
        cross.add(icon(FxMenuTheme.image(MenuTheme.Art.KEY_LEFT)), 0, 1);
        cross.add(icon(FxMenuTheme.image(MenuTheme.Art.KEY_DOWN)), 1, 1);
        cross.add(icon(FxMenuTheme.image(MenuTheme.Art.KEY_RIGHT)), 2, 1);
        return cross;
    }

    /** Each key as a keycap with its name on it. */
    private static HBox keycaps(ControlsGuide.Entry entry) {
        HBox caps = new HBox(4);
        caps.setAlignment(Pos.CENTER_RIGHT);
        for (String key : entry.keys()) {
            Label name = new Label(key);
            name.getStyleClass().add("menu-key-text");
            StackPane cap = new StackPane(name);
            cap.getStyleClass().add(KEY_CLASS);
            cap.setMinHeight(KEY_SIZE + 4);
            cap.setMinWidth(KEY_SIZE + 4);
            caps.getChildren().add(cap);
        }
        return caps;
    }

    private static ImageView icon(javafx.scene.image.Image image) {
        ImageView view = new ImageView(image);
        view.setFitWidth(KEY_SIZE);
        view.setFitHeight(KEY_SIZE);
        view.setSmooth(true);
        return view;
    }

    private static ImageView icon(String path, double size) {
        ImageView view = new ImageView(FxMenuTheme.image(path));
        view.setFitWidth(size);
        view.setFitHeight(size);
        view.setPreserveRatio(true);
        view.setSmooth(true);
        return view;
    }
}
