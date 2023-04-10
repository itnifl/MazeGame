package main.game.maze.actions;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.game.maze.GameController;
import main.game.maze.characters.interfaces.ICanLetYouWin;

public class WinGameAction extends ActionScreens implements ICanLetYouWin {
    private AnchorPane root;
    private Runnable runnableOnWin;

    public WinGameAction(AnchorPane root, Runnable runnableOnWin) {
        this.root = root;
        this.runnableOnWin = runnableOnWin;
    }

    @Override
    public void WinGame() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("winScreen.fxml"));
        /*
         * GameController controller = new GameController();
         * fxmlLoader.setController(controller);
         */

        try {
            VBox winScreen = fxmlLoader.load();

            winScreen.setAlignment(Pos.CENTER);

            AnchorPane.setTopAnchor(winScreen, 0.0);
            AnchorPane.setRightAnchor(winScreen, 0.0);
            AnchorPane.setBottomAnchor(winScreen, 0.0);
            AnchorPane.setLeftAnchor(winScreen, 0.0);

            var newRoot = new AnchorPane();
            newRoot.getChildren().add(winScreen);

            AnchorPane.setTopAnchor(newRoot, 0.0);
            AnchorPane.setRightAnchor(newRoot, 0.0);
            AnchorPane.setBottomAnchor(newRoot, 0.0);
            AnchorPane.setLeftAnchor(newRoot, 0.0);

            this.replaceRoot(root, newRoot);

        } catch (IOException e) {
            e.printStackTrace();
        }
        runnableOnWin.run();
    }

}
