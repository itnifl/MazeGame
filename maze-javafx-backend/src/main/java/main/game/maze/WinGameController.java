package main.game.maze;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import main.game.maze.actions.HighscoreAction;
import main.game.maze.actions.StartScreenAction;
import javafx.scene.layout.AnchorPane;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.constants.AudioChannelConstants;
import main.game.maze.common.constants.AudioResourceConstants;
import main.game.maze.constants.DataFileConstants;
import main.game.maze.game.theme.MenuButton;
import main.game.maze.game.theme.MenuTheme;
import main.game.maze.javafx.menu.FxMenuTheme;

public class WinGameController extends ActionScreenController implements Initializable {

    @FXML
    private Label winBonusNoticeLabel;

    @FXML
    private Button nextLevelButton;

    @FXML
    private Label winHeaderLabel;

    /** The shop between levels: offered while there is a next level to buy for. */
    @FXML
    private Button shopButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button restartButton;

    @FXML
    private javafx.scene.image.ImageView backdrop;

    @FXML
    private javafx.scene.image.ImageView tokenIcon;

    public void showWinBonusLabel(int amount) {
        winBonusNoticeLabel.setText("+ Win bonus: +" + amount);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        applyTheme();
        // the next level is offered only while there is one
        boolean moreLevels = main.game.maze.App.levelProgress.hasNext();
        if (nextLevelButton != null) {
            nextLevelButton.setVisible(moreLevels);
            nextLevelButton.setManaged(moreLevels);
        }
        if (shopButton != null) {
            shopButton.setVisible(moreLevels);
            shopButton.setManaged(moreLevels);
        }
        // winning the last level wins the game: "GAME WON" and a dark voice says "Ultimate victory"
        boolean gameWon = !moreLevels;
        if (winHeaderLabel != null) {
            winHeaderLabel.setText(main.game.maze.game.status.FinalLevelIntro.winText(gameWon));
        }
        AudioEngine.get().playLoop(AudioResourceConstants.WinGameSound, AudioChannelConstants.WIN_MUSIC);
        if (gameWon) {
            AudioEngine.get().play(main.game.maze.game.status.FinalLevelIntro.WIN_VOICE);
        }
        AudioEngine.get().play(AudioResourceConstants.WinGameSoundComment);
    }

    /** The look of the screens between levels (the menu theme model), the same as the libGDX win screen. */
    private void applyTheme() {
        if (screenRoot == null) {
            return;
        }
        FxMenuTheme.apply(screenRoot);
        if (backdrop != null) {
            backdrop.setImage(FxMenuTheme.image(MenuTheme.Art.BACKDROP));
        }
        if (tokenIcon != null) {
            tokenIcon.setImage(FxMenuTheme.image(MenuTheme.Art.TOKEN));
        }
        styleButton(saveButton, MenuButton.SAVE_SCORE);
        styleButton(shopButton, MenuButton.SHOP);
        styleButton(nextLevelButton, MenuButton.NEXT_LEVEL);
        styleButton(restartButton, MenuButton.RESTART_LEVEL);
    }

    private static void styleButton(Button button, MenuButton kind) {
        if (button != null) {
            FxMenuTheme.style(button, kind);
        }
    }

    /** Goes on to the next level with the weapons the player has now. */
    @FXML
    protected void nextLevel() {
        new main.game.maze.actions.RestartGameAction(screenRoot, true).Load();
    }

    /**
     * Opens the shop between levels. What is bought is paid with the score carried into the next level, so the score
     * shown (and saved) here afterwards is what is left.
     */
    @FXML
    protected void openShop() {
        new main.game.maze.actions.ShopAction(screenRoot, () -> main.game.maze.App.levelProgress.pendingSavePoint()
                .ifPresent(next -> setScoreLabel(next.score()))).Load();
    }

    /** Plays the level just won again from its start, with the weapons it started with. */
    @FXML
    protected void restartLevel() {
        main.game.maze.actions.RestartGameAction.retryLevel(screenRoot).Load();
    }

    @FXML
    protected void saveScore() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Save Score");
        dialog.setHeaderText("Enter your name to save your score");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty()) {
            return;
        }
        String playerName = result.get().trim();
        if (playerName.isEmpty()) {
            return;
        }
        int score = Integer.parseInt(scoreLabel.getText());
        upsertScore(playerName, score, DataFileConstants.HighscoreFilePath);
        showHighScoresThenReturnToMenu();
    }

    private void showHighScoresThenReturnToMenu() {
        if (screenRoot == null || screenRoot.getScene() == null) {
            return;
        }
        AnchorPane sceneRootPane = resolveAnchorRoot();
        if (sceneRootPane == null) {
            return;
        }
        final javafx.scene.Scene scene = sceneRootPane.getScene();
        final javafx.scene.Parent winScreenRoot = scene.getRoot();
        final boolean nextLevelFollows = main.game.maze.App.levelProgress.hasNext();
        HighscoreAction action = new HighscoreAction(sceneRootPane, () -> {
            if (nextLevelFollows) {
                // back to the win screen, where Next level is still offered
                scene.setRoot(winScreenRoot);
                main.game.maze.App.applyStandardSize((javafx.stage.Stage) scene.getWindow());
                return;
            }
            var current = scene.getRoot();
            if (current instanceof AnchorPane anchor) {
                new StartScreenAction(anchor).Load();
            }
        });
        action.Load();
    }

    private AnchorPane resolveAnchorRoot() {
        if (screenRoot != null && screenRoot.getScene() != null) {
            var root = screenRoot.getScene().getRoot();
            if (root instanceof AnchorPane anchor) {
                return anchor;
            }
        }
        return screenRoot;
    }
}
