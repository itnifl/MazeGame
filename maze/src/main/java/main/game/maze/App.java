package main.game.maze;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.game.maze.constants.ResourceFileConstants;
import main.game.maze.constants.ScreenNameConstants;
import main.game.maze.constants.StageConstants;

// NEW imports:
import main.game.maze.service.DifficultyService;
import main.game.maze.difficulties.Difficulty;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class App extends Application {
    private static int boardMaxX = StageConstants.BoardMaxX;
    private static int boardMaxY = StageConstants.BoardMaxY;

    public static MediaPlayer inGameMediaPlayer;
    public static GameController gameController;
    public static Difficulty lastChosenDifficulty;


    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ScreenNameConstants.GameScreen));
            AnchorPane root = loader.load();

            // Bind HP bar width after FXML is loaded
            ProgressBar progressBar = (ProgressBar) root.lookup("#hpBar");
            if (progressBar != null) progressBar.prefWidthProperty().bind(root.widthProperty());

            gameController = loader.getController();

            primaryStage.setTitle("Maze Game");
            primaryStage.setScene(new Scene(root, boardMaxX, boardMaxY));
            primaryStage.show();

            // --- MDD difficulty selection (reads difficulties.xmi) ---
            this.setDifficulty(primaryStage);
            // --------------------------------------------------------

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

    // Helper to show clean names (Easy/Normal/Hard) directly from the model type
    public static String displayName(Difficulty d) {
        if (d == null) return "";
        String n = d.eClass().getName(); // e.g., NormalDifficulty
        return n.endsWith("Difficulty") ? n.substring(0, n.length() - 10) : n;
    }
}
