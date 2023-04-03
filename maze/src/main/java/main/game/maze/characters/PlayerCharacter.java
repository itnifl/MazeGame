package main.game.maze.characters;

import javafx.scene.Node;
import main.game.maze.constants.StageConstants;

public class PlayerCharacter extends Character {
    public PlayerCharacter(Node characterGraphics, double x, double y) {
        super(characterGraphics, x, y);
        this.characterXYSizeFromPoint =  StageConstants.PlayerCharacterXYSize;
        CalculateMaxPositions();
    }
}
