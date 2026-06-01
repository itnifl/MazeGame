package main.game.maze;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import main.game.maze.common.graphics.AudioEngine;
import main.game.maze.constants.AudioChannelConstants;
import main.game.maze.common.constants.AudioResourceConstants;

public class WinGameController extends ActionScreenController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AudioEngine.get().playLoop(AudioResourceConstants.WinGameSound, AudioChannelConstants.WIN_MUSIC);
        AudioEngine.get().play(AudioResourceConstants.WinGameSoundComment);
    }
}
