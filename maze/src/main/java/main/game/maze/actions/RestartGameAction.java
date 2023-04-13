package main.game.maze.actions;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import main.game.maze.GameController;
import main.game.maze.actions.base.ActionScreens;

public class RestartGameAction extends ActionScreens {
    private AnchorPane root;

    public RestartGameAction(AnchorPane root) {
        this.root = root;
    }

    public void Load() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/game/maze/game.fxml"));


        try {
            AnchorPane screen = fxmlLoader.load();
            GameController controller = fxmlLoader.getController();

            AnchorPane.setTopAnchor(screen, 0.0);
            AnchorPane.setRightAnchor(screen, 0.0);
            AnchorPane.setBottomAnchor(screen, 0.0);
            AnchorPane.setLeftAnchor(screen, 0.0);

            var newRoot = new AnchorPane();
            newRoot.getChildren().add(screen);

            AnchorPane.setTopAnchor(newRoot, 0.0);
            AnchorPane.setRightAnchor(newRoot, 0.0);
            AnchorPane.setBottomAnchor(newRoot, 0.0);


            this.replaceRoot(root, newRoot);

            controller.initialize(null, null);

            
            controller.setupGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
