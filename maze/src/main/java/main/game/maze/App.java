// maze/src/main/java/main/game/maze/App.java
package main.game.maze;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.game.maze.constants.ResourceFileConstants;
import main.game.maze.constants.ScreenNameConstants;
import main.game.maze.mazeworld.constants.StageConstants;

// NEW imports:
import main.game.maze.service.DifficultyService;
import main.game.maze.difficulties.DifficultiesPackage;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.HardDifficulty;
import main.game.maze.difficulties.NormalDifficulty;
import main.game.maze.opponents.OpponentsPackage;
import main.game.maze.runtime.OclBootstrap;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class App extends Application {
    public static MediaPlayer inGameMediaPlayer;
    public static GameController gameController;
    public static Difficulty lastChosenDifficulty;


    @Override
    public void start(Stage primaryStage) {
        try {
            OclBootstrap.init();
            OpponentsPackage.eINSTANCE.eClass();
            DifficultiesPackage.eINSTANCE.eClass();


            FXMLLoader loader = new FXMLLoader(getClass().getResource(ScreenNameConstants.GameScreen));
            AnchorPane root = loader.load();
            gameController = loader.getController();

            primaryStage.setTitle("Maze Game");
            primaryStage.setScene(new Scene(root, App.getBoardMaxX(), App.getBoardMaxY()));
            setWindowIcon(primaryStage);
            // --- MDD difficulty selection (reads difficulties.xmi) ---
            this.setDifficulty(primaryStage);
            // --------------------------------------------------------
            applySizeForCurrentDifficulty(primaryStage);
            primaryStage.setResizable(false);
            primaryStage.show();

            gameController.setupGame();

            // Start playing the music
            MediaView view = addMusic();
            root.getChildren().add(view);
            inGameMediaPlayer.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setDifficulty(Stage primaryStage) {
        DifficultyService svc = new DifficultyService();
        Difficulty current = svc.getCurrent();

        Optional<Difficulty> chosen = pickDifficulty(primaryStage);
        if (chosen.isPresent()) {
            gameController.setStartDifficulty(chosen.get());
        } else if (current != null) {
            // Cancel → keep current from XMI
            gameController.setStartDifficulty(current);
            lastChosenDifficulty = current;
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

        ChoiceDialog<String> dlg = new ChoiceDialog<>(def, byName.keySet());
        dlg.setTitle("Select difficulty");
        dlg.setHeaderText("Choose game difficulty");
        dlg.setContentText("Difficulty:");
        dlg.initOwner(owner);

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

        stage.setWidth(width);
        stage.setHeight(height);

        stage.setWidth(width);
        stage.setHeight(height);

        Scene scene = stage.getScene();
        if (scene != null) {
            var root = (AnchorPane) scene.getRoot();
            root.setPrefWidth(width);
            root.setPrefHeight(height);
            root.setMaxWidth(width);
            root.setMaxHeight(height);        
            setProgressBarWidth(root);
            scene.setRoot(root); 

        }
    }

    public static void applyStandardSize(Stage stage) {
        int width  = StageConstants.BoardMaxX;
        int height = StageConstants.BoardMaxY;

        stage.setWidth(width);
        stage.setHeight(height);

        stage.setWidth(width);
        stage.setHeight(height);

        Scene scene = stage.getScene();
        if (scene != null) {
            var root = (AnchorPane) scene.getRoot();
            root.setPrefWidth(width);
            root.setPrefHeight(height);
            root.setMaxWidth(width);
            root.setMaxHeight(height);            
            setProgressBarWidth(root);
            scene.setRoot(root); 
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

    private MediaView addMusic() {
        var resource = getClass().getResource(ResourceFileConstants.BackgroundMusic);
        Media media = new Media(resource.toString());
        inGameMediaPlayer = new MediaPlayer(media);
        inGameMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        return new MediaView(inGameMediaPlayer);
    }

    private void setWindowIcon(Stage stage) {
        try {
            var icon = getClass().getResource("/main/game/maze/ghost1.png");
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
}
