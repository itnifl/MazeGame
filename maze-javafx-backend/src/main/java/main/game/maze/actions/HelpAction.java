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
import main.game.maze.HelpController;
import main.game.maze.constants.ScreenNameFXMLConstants;

/**
 * Opens the help screen ("Help & Controls") over the main menu and, when it is closed, puts the main menu back and runs
 * {@code onClosed}.
 */
public class HelpAction {

    private static final Logger LOGGER = Logger.getLogger(HelpAction.class.getName());

    private final AnchorPane root;
    private final Runnable onClosed;

    public HelpAction(AnchorPane root, Runnable onClosed) {
        this.root = root;
        this.onClosed = onClosed;
    }

    public void Load() {
        if (root == null || root.getScene() == null) {
            return;
        }
        final Scene scene = root.getScene();
        final Stage stage = (Stage) scene.getWindow();
        final Parent menuRoot = scene.getRoot();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ScreenNameFXMLConstants.HelpScreen));
        try {
            AnchorPane screen = fxmlLoader.load();
            HelpController controller = fxmlLoader.getController();
            AnchorPane.setTopAnchor(screen, 0.0);
            AnchorPane.setRightAnchor(screen, 0.0);
            AnchorPane.setBottomAnchor(screen, 0.0);
            AnchorPane.setLeftAnchor(screen, 0.0);
            AnchorPane helpRoot = new AnchorPane(screen);
            controller.setOnClose(() -> {
                scene.setRoot(menuRoot);
                App.applyStandardSize(stage);
                if (onClosed != null) {
                    onClosed.run();
                }
            });
            scene.setRoot(helpRoot);
            App.applyStandardSize(stage);
            screen.requestFocus();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Could not open the help screen", e);
        }
    }
}
