package main.game.maze;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.constants.AudioChannelConstants;
import main.game.maze.game.theme.MenuButton;
import main.game.maze.game.theme.MenuTheme;
import main.game.maze.javafx.menu.FxMenuTheme;
import main.game.maze.common.constants.AudioResourceConstants;

public class GameOverController extends ActionScreenController implements Initializable {

    @FXML
    private Label deathPenaltyNoticeLabel;

    @FXML
    private Button restartButton;

    @FXML
    private javafx.scene.image.ImageView tokenIcon;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        applyTheme();
        AudioEngine.get().playLoop(AudioResourceConstants.GameOverSound, AudioChannelConstants.GAME_OVER_MUSIC);
    }

    /** The look of the screens between levels (the menu theme model), the same as the libGDX game over screen. */
    private void applyTheme() {
        if (screenRoot == null) {
            return;
        }
        FxMenuTheme.apply(screenRoot);
        if (tokenIcon != null) {
            tokenIcon.setImage(FxMenuTheme.image(MenuTheme.Art.TOKEN));
        }
        if (restartButton != null) {
            FxMenuTheme.style(restartButton, MenuButton.RESTART_LEVEL);
        }
    }

    /** Plays the level that was lost again from its start (not from level 1), with the weapons it was entered with. */
    @javafx.fxml.FXML
    protected void restartLevel() {
        main.game.maze.actions.RestartGameAction.retryLevel(screenRoot).Load();
    }

    public void showDeathPenaltyLabel(int amount) {
        deathPenaltyNoticeLabel.setText("- Death penalty: -" + amount);
        deathPenaltyNoticeLabel.setVisible(true);
        deathPenaltyNoticeLabel.setManaged(true);
    }
}
