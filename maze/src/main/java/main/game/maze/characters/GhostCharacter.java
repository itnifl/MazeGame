package main.game.maze.characters;

import javafx.scene.Node;
import main.game.maze.characters.interfaces.ICharacterAction;
import main.game.maze.characters.interfaces.ICharacterAnimations;
import main.game.maze.constants.StageConstants;

public class GhostCharacter extends Character implements ICharacterAnimations {
    public GhostCharacter(Node characterGraphics, double x, double y) {
        super(characterGraphics, x, y);
        this.characterXYSizeFromPoint =  StageConstants.PlayerCharacterXYSize;
        CalculateMaxPositions();
    }

    @Override
    public void PlayHappyAnimation() {
        super.doCharacterAnimation(new HappyAction());
    }

    @Override
    public void PlayDieAnimation() {
        super.doCharacterAnimation(new DieAction());
    }

    private class HappyAction implements ICharacterAction {
        public void doAction(Node characterGraphics) {
            //Animate the character and do stuff
        }
    }

    private class DieAction implements ICharacterAction {
        public void doAction(Node characterGraphics) {
            //Animate the character and do stuff
        }
    }
}
