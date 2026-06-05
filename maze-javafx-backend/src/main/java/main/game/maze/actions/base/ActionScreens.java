package main.game.maze.actions.base;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class ActionScreens {

    protected void replaceRoot(AnchorPane oldRoot, AnchorPane newRoot) {
        if (oldRoot == null || newRoot == null) return;

        Runnable doSwap = () -> {
            Scene scene = oldRoot.getScene();
            if (scene == null) return; // node not attached → just skip

            scene.setRoot(newRoot);    // no need to call setScene again

            /*Window win = scene.getWindow();
            if (win instanceof Stage stage) {
                stage.sizeToScene();   
            }*/
        };

        if (javafx.application.Platform.isFxApplicationThread()) {
            doSwap.run();
        } else {
            javafx.application.Platform.runLater(doSwap);
        }
    }
}



