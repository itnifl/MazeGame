package main.game.maze.actions;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.game.maze.App;
import main.game.maze.ShopController;
import main.game.maze.constants.ScreenNameFXMLConstants;

/**
 * Opens the shop between levels over the win screen and, when it is closed, puts the win screen back and runs
 * {@code onClosed} (which shows the score that is left).
 */
public class ShopAction {

    private static final Logger LOGGER = Logger.getLogger(ShopAction.class.getName());

    private final AnchorPane root;
    private final Runnable onClosed;

    public ShopAction(AnchorPane root, Runnable onClosed) {
        this.root = root;
        this.onClosed = onClosed;
    }

    public void Load() {
        if (root == null || root.getScene() == null) {
            return;
        }
        final Scene scene = root.getScene();
        final Stage stage = (Stage) scene.getWindow();
        final Parent winScreenRoot = scene.getRoot();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ScreenNameFXMLConstants.ShopScreen));
        try {
            AnchorPane screen = fxmlLoader.load();
            ShopController controller = fxmlLoader.getController();
            AnchorPane.setTopAnchor(screen, 0.0);
            AnchorPane.setRightAnchor(screen, 0.0);
            AnchorPane.setBottomAnchor(screen, 0.0);
            AnchorPane.setLeftAnchor(screen, 0.0);
            AnchorPane shopRoot = new AnchorPane(screen);
            controller.setOnClose(() -> {
                scene.setRoot(winScreenRoot);
                App.applyStandardSize(stage);
                if (onClosed != null) {
                    onClosed.run();
                }
            });
            scene.setRoot(shopRoot);
            App.applyStandardSize(stage);
            screen.requestFocus();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Could not open the shop", e);
        }
    }
}
