package main.game.maze.javafx.menu;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;
import main.game.maze.App;
import main.game.maze.difficulties.Difficulty;

import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Encapsulates the ESC-key difficulty-picker flow: stop movement, show a difficulty
 * dialog, prompt to confirm restart, and resume (or apply the new difficulty) without
 * restarting if the user cancels.
 */
public final class FxDifficultyPickerSupport {

    private FxDifficultyPickerSupport() {}

    /**
     * Production entry-point. Shows the real JavaFX dialogs.
     *
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

        Window window = (root != null && root.getScene() != null) ? root.getScene().getWindow() : null;

        Supplier<Optional<Difficulty>> picker = () -> App.pickDifficulty(window);
        BooleanSupplier confirmRestart = () -> {
            // chosen is captured via the lambda closure in resolvePickResult
            return false; // placeholder; actual logic is inlined below
        };

        onStop.run();
        Optional<Difficulty> chosenOpt = App.pickDifficulty(window);
        if (chosenOpt.isEmpty()) {
            onResume.run();
            return;
        }
        Difficulty chosen = chosenOpt.get();
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Restart required");
        confirm.setHeaderText("Restart with " + App.displayName(chosen) + " difficulty now?");
        confirm.setContentText("Choose OK to restart, or Cancel to keep playing and apply on next restart.");
        if (window != null) confirm.initOwner(window);

        Optional<ButtonType> res = confirm.showAndWait();
        resolvePickResult(chosen, () -> res.isPresent() && res.get() == ButtonType.OK,
                onResume, onDifficultySet, onHardRestart);
    }

    /**
     * Core routing logic — injectable for unit tests. No JavaFX dialogs involved.
     *
     * @param chosen           the difficulty the user selected (not empty)
     * @param confirmRestart   returns {@code true} when the user confirmed a hard restart
     * @param onResume         called when the user cancels or does not restart
     * @param onDifficultySet  called when difficulty is changed but restart is deferred
     * @param onHardRestart    called when the user confirms an immediate restart
     */
    static void resolvePickResult(
            Difficulty chosen,
            BooleanSupplier confirmRestart,
            Runnable onResume,
            Consumer<Difficulty> onDifficultySet,
            Runnable onHardRestart) {

        if (confirmRestart.getAsBoolean()) {
            onHardRestart.run();
            return;
        }
        onDifficultySet.accept(chosen);
        onResume.run();
    }
}
