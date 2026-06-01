// maze/src/main/java/main/game/maze/App.java
package main.game.maze;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import main.game.maze.common.graphics.config.PropertiesMazeVisualStyleLoader;
import main.game.maze.common.graphics.config.XmiMazeVisualStyleLoader;
import main.game.maze.constants.AudioChannelConstants;
import main.game.maze.constants.ResourceFileConstants;
import main.game.maze.constants.ScreenNameFXMLConstants;
import main.game.maze.mazeworld.constants.StageConstants;

// NEW imports:
import main.game.maze.service.DifficultyService;
import main.game.maze.difficulties.DifficultiesPackage;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.HardDifficulty;
import main.game.maze.difficulties.NormalDifficulty;
import main.game.maze.opponents.OpponentsPackage;
import main.game.maze.runtime.OclBootstrap;
import main.game.maze.javafx.JavaFxBackend;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class App extends Application {
    public static GameController gameController;
    public static Difficulty lastChosenDifficulty;
    private static final MazeVisualStyleConfig VISUAL_STYLE = loadVisualStyle();
    private final AtomicBoolean shutdownInvoked = new AtomicBoolean(false);


    @Override
    public void start(Stage primaryStage) {
        try {
            JavaFxBackend.install();
            OclBootstrap.init();
            OpponentsPackage.eINSTANCE.eClass();
            DifficultiesPackage.eINSTANCE.eClass();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(ScreenNameFXMLConstants.StartScreen));
            AnchorPane root = loader.load();
            StartController startController = loader.getController();

            primaryStage.setTitle("Maze Game");
            primaryStage.setScene(new Scene(root, 980, 700));
            setWindowIcon(primaryStage);
            startController.setStage(primaryStage);
            primaryStage.setResizable(true);
            primaryStage.setOnCloseRequest(event -> forceShutdownAndExit());
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Optional<Difficulty> pickDifficulty(Window owner) {
        DifficultyService svc = new DifficultyService();
        List<Difficulty> diffs = svc.list();
        Difficulty current = svc.getCurrent();

        LinkedHashMap<String, Difficulty> byName = new LinkedHashMap<>();
        for (Difficulty d : diffs) byName.put(displayName(d), d);

        String def = current != null
                ? displayName(current)
                : (byName.isEmpty() ? null : byName.keySet().iterator().next());

        if (byName.isEmpty()) {
            return Optional.empty();
        }

        ChoiceDialog<String> dlg = new ChoiceDialog<>(def, byName.keySet());
        dlg.setTitle("Select difficulty");
        dlg.setHeaderText("Choose game difficulty");
        dlg.setContentText("Difficulty:");
        if (owner != null) {
            dlg.initOwner(owner);
        }
        dlg.getDialogPane().setMinWidth(380);
        dlg.getDialogPane().setMinHeight(180);

        Optional<String> result = dlg.showAndWait();
        if (result.isPresent()) {
            Difficulty chosen = byName.get(result.get());
            if (chosen != null) {
                svc.setCurrent(chosen);          // sync DifficultyGameData.currentDifficulty (in-memory)
                lastChosenDifficulty = chosen;   // remember for restarts
                return Optional.of(chosen);
            }
        }
        return Optional.empty();
    }

    public static int getBoardMaxX() {
        if (App.lastChosenDifficulty instanceof HardDifficulty) {
            return StageConstants.BoardMaxXLarge;
        } else if (App.lastChosenDifficulty instanceof NormalDifficulty) {
            return StageConstants.BoardMaxXMedium;
        } else {
            return StageConstants.BoardMaxX;
        }
    }

    public static int getBoardMaxY() {
        if (App.lastChosenDifficulty instanceof HardDifficulty) {
            return StageConstants.BoardMaxYLarge;
        } else if (App.lastChosenDifficulty instanceof NormalDifficulty) {
            return StageConstants.BoardMaxYMedium;
        } else {
            return StageConstants.BoardMaxY;
        }
    }


    public static void applySizeForCurrentDifficulty(Stage stage) {
        int width  = getBoardMaxX();
        int height = getBoardMaxY();
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

        if (needsFullscreenForBoard(width, height, bounds.getWidth(), bounds.getHeight())) {
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());
            stage.setFullScreenExitHint("");
            stage.setFullScreen(true);
            applyRootSize(stage.getScene(), (int) bounds.getWidth(), (int) bounds.getHeight());
            return;
        }

        stage.setFullScreen(false);
        stage.setWidth(width);
        stage.setHeight(height);

        applyRootSize(stage.getScene(), width, height);
    }

    /**
     * Fullscreen is auto-enabled whenever the chosen board exceeds the player's
     * screen size in either dimension; otherwise the window stays sized to the
     * board so the map always fits inside the window without camera scrolling.
     *
     * <p>Package-private for unit tests.
     */
    static boolean needsFullscreenForBoard(int boardWidth, int boardHeight, double screenWidth, double screenHeight) {
        return boardWidth > screenWidth || boardHeight > screenHeight;
    }

    public static void applyStandardSize(Stage stage) {
        int width  = StageConstants.BoardMaxX;
        int height = StageConstants.BoardMaxY;

        stage.setWidth(width);
        stage.setHeight(height);

        applyRootSize(stage.getScene(), width, height);
    }

    private static void applyRootSize(Scene scene, int width, int height) {
        if (scene == null) {
            return;
        }

        var root = scene.getRoot();
        if (root instanceof AnchorPane anchorRoot) {
            anchorRoot.setPrefWidth(width);
            anchorRoot.setPrefHeight(height);
            anchorRoot.setMaxWidth(width);
            anchorRoot.setMaxHeight(height);
            setProgressBarWidth(anchorRoot);
        }
    }

    private static void setProgressBarWidth(AnchorPane root) {
        ProgressBar progressBar = (ProgressBar) root.lookup("#hpBar");
        if (progressBar != null) {
            progressBar.prefWidthProperty().bind(root.widthProperty());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        // JavaFX calls stop() during normal shutdown paths; keep cleanup idempotent.
        shutdownResources();
    }

    private void setWindowIcon(Stage stage) {
        try {
            var icon = getClass().getResource(VISUAL_STYLE.menuIconImagePath());
            if (icon != null) {
                stage.getIcons().add(new Image(icon.toExternalForm()));
            }
        } catch (Exception ignored) {
            // Icon failure should not block startup.
        }
    }

    // Helper to show clean names (Easy/Normal/Hard) directly from the model type
    public static String displayName(Difficulty d) {
        if (d == null) return "";
        String n = d.eClass().getName(); // e.g., NormalDifficulty
        return n.endsWith("Difficulty") ? n.substring(0, n.length() - 10) : n;
    }

    private void shutdownResources() {
        if (!shutdownInvoked.compareAndSet(false, true)) {
            return;
        }
        try {
            if (App.gameController != null) {
                App.gameController.dispose();
                App.gameController = null;
            }
        } catch (Exception ignored) {
            // Best-effort cleanup during shutdown.
        }

        try {
            AudioEngine.get().dispose();
        } catch (Exception ignored) {
            // Best-effort cleanup during shutdown.
        }
    }

    private void forceShutdownAndExit() {
        shutdownResources();
        Platform.exit();
        System.exit(0);
    }

    private static MazeVisualStyleConfig loadVisualStyle() {
        try {
            return new XmiMazeVisualStyleLoader().load();
        } catch (RuntimeException ex) {
            try {
                return new PropertiesMazeVisualStyleLoader().load();
            } catch (RuntimeException ignored) {
                return MazeVisualStyleConfig.DEFAULT;
            }
        }
    }
}
