package main.game.maze.actions;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.game.maze.App;
import main.game.maze.HighScoreController;
import main.game.maze.actions.base.ActionScreens;
import main.game.maze.constants.ScreenNameFXMLConstants;

public class HighscoreAction extends ActionScreens {
    private AnchorPane root;

    public HighscoreAction(AnchorPane root) {
        this.root = root;
    }

    public void Load() {
        if (root == null || root.getScene() == null) {
            return;
        }

        final Scene scene = root.getScene();
        final Stage stage = (Stage) scene.getWindow();
        // Capture the actual scene root, not the FXML root passed in. The scene root
        // is guaranteed to be parent-less, so it can safely be reinstated on Continue.
        final Parent originalSceneRoot = scene.getRoot();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ScreenNameFXMLConstants.HighscoreScreen));

        try {
            AnchorPane screen = fxmlLoader.load();
            HighScoreController controller = fxmlLoader.getController();

            // Make sure the highscore content fills the wrapper pane on resize.
            AnchorPane.setTopAnchor(screen, 0.0);
            AnchorPane.setRightAnchor(screen, 0.0);
            AnchorPane.setBottomAnchor(screen, 0.0);
            AnchorPane.setLeftAnchor(screen, 0.0);

            var newRoot = new AnchorPane();
            newRoot.getChildren().add(screen);

            controller.setOnContinue(() -> {
                if (originalSceneRoot == null) {
                    return;
                }
                // Defensive: if the original root still has a non-null parent (shouldn't
                // happen for a scene root) detach it before re-installing so JavaFX won't
                // reject the assignment.
                Parent parentOfOriginal = originalSceneRoot.getParent();
                if (parentOfOriginal instanceof Pane parentPane) {
                    parentPane.getChildren().remove(originalSceneRoot);
                }
                scene.setRoot(originalSceneRoot);
                App.applySizeForCurrentDifficulty(stage);
                if (App.gameController != null) {
                    App.gameController.runComputerCharacters();
                }
            });

            // Swap the scene root directly, bypassing the inherited helper, so the
            // restore path can target the captured originalSceneRoot rather than the
            // FXML-level root that may already have a parent after a restart.
            scene.setRoot(newRoot);
            App.applyStandardSize(stage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
