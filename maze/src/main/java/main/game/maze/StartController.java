package main.game.maze;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import main.game.maze.common.graphics.config.PropertiesMazeVisualStyleLoader;
import main.game.maze.common.graphics.config.XmiMazeVisualStyleLoader;
import main.game.maze.constants.AudioChannelConstants;
import main.game.maze.constants.ScreenNameConstants;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.service.DifficultyService;

public class StartController implements Initializable {
    @FXML private ComboBox<Difficulty> difficultyCombo;
    @FXML private Label error;

    private final DifficultyService svc = new DifficultyService();
    private final MazeVisualStyleConfig visualStyle = loadVisualStyle();
    private Stage stage;

    void setStage(Stage s) { this.stage = s; }

    @Override public void initialize(URL url, ResourceBundle rb) {
        var diffs = FXCollections.observableArrayList(svc.list());
        difficultyCombo.setItems(diffs);
        difficultyCombo.setConverter(new StringConverter<>() {
            @Override public String toString(Difficulty d) {
                if (d == null) return "";
                String n = d.eClass().getName(); // e.g., NormalDifficulty
                return n.endsWith("Difficulty") ? n.substring(0, n.length()-10) : n;
            }
            @Override public Difficulty fromString(String s) { return null; }
        });
        var cur = svc.getCurrent();
        if (cur != null) difficultyCombo.getSelectionModel().select(cur);
        else if (!diffs.isEmpty()) difficultyCombo.getSelectionModel().select(0);

        difficultyCombo.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && oldValue != newValue) {
                AudioEngine.get().playRateLimited(
                    visualStyle.menuSelectSoundPath(),
                        "menu.select",
                        90L);
            }
        });

            AudioEngine.get().playLoop(visualStyle.menuMusicPath(), AudioChannelConstants.MENU_MUSIC);
    }

    @FXML private void onStart() throws IOException {
        var selected = difficultyCombo.getValue();
        if (selected == null) { error.setText("Please choose a difficulty."); return; }

        // keep XMI's currentDifficulty in sync (in-memory)
        svc.setCurrent(selected);

        // Load game screen, inject chosen Difficulty, then start the game
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ScreenNameConstants.GameScreen));
        AnchorPane root = loader.load();
        GameController gc = loader.getController();
        gc.setStartDifficulty(selected); // <-- inject the EMF Difficulty from the model
        App.lastChosenDifficulty = selected;
        App.gameController = gc;

        stage.setScene(new Scene(root));
        App.applySizeForCurrentDifficulty(stage);
        stage.show();
        gc.setupGame();

        AudioEngine.get().stopChannel(AudioChannelConstants.MENU_MUSIC);
        AudioEngine.get().playLoop(visualStyle.inGameMusicPath(), AudioChannelConstants.IN_GAME_MUSIC);
    }

    private MazeVisualStyleConfig loadVisualStyle() {
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
