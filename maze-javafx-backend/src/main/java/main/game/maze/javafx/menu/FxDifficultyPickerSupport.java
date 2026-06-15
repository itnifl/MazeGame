package main.game.maze.javafx.menu;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import main.game.maze.App;
import main.game.maze.difficulties.Difficulty;

import java.util.function.Consumer;

/**
 * Encapsulates the ESC-key difficulty-picker flow: stop movement, show a difficulty
 * dialog, prompt to confirm restart, and resume (or apply the new difficulty) without
 * restarting if the user cancels.
 */
public final class FxDifficultyPickerSupport {

    private FxDifficultyPickerSupport() {}

    /**
     * @param root             the root pane (used to derive the owning window)
     * @param onStop           called before the dialog opens (stops movement + hides overlay)
     * @param onResume         called to restart movement after dialog is dismissed
     * @param onDifficultySet  called when the user selects a difficulty without restarting
     * @param onHardRestart    called when the user confirms an immediate restart
     */
    public static void open(
            AnchorPane root,
            Runnable onStop,
            Runnable onResume,
            Consumer<Difficulty> onDifficultySet,
            Runnable onHardRestart) {

        var window = (root != null && root.getScene() != null) ? root.getScene().getWindow() : null;
        onStop.run();

        var chosenOpt = App.pickDifficulty(window);
        if (chosenOpt.isEmpty()) {
            onResume.run();
            return;
        }
        var chosen = chosenOpt.get();
        var confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Restart required");
        confirm.setHeaderText("Restart with " + App.displayName(chosen) + " difficulty now?");
        confirm.setContentText("Choose OK to restart, or Cancel to keep playing and apply on next restart.");
        if (window != null) confirm.initOwner(window);

        var res = confirm.showAndWait();
        if (res.isPresent() && res.get() == ButtonType.OK) {
            onHardRestart.run();
            return;
        }
        onDifficultySet.accept(chosen);
        onResume.run();
    }
}
