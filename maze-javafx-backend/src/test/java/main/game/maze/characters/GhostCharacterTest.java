package main.game.maze.characters;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import main.game.maze.characters.GhostCharacter;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.opponents.OpponentsFactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GhostCharacterTest {

    @Test
    void testDoPositionEvaluationWithAnyICanDie() {
        Rectangle gCharacterGraphics = new Rectangle();
        GhostCharacter ghost = new GhostCharacter(gCharacterGraphics, 0, 0, OpponentsFactory.eINSTANCE.createGhost());
        // Factory-default ghost has nonTangibilityEnergy=100 (phasing). Set to 0 so it
        // is solid and can physically harm the player.
        ghost.setNonTangientEnergy(0);

        ImageView pCharacterGraphics = new ImageView();
        PlayerCharacter entity = new PlayerCharacter(pCharacterGraphics, 0, 0, null);

        Bounds nodeBounds = new Rectangle(0, 0, 10, 10).getBoundsInParent();

        ghost.doPositionEvaluation(nodeBounds, entity);

        Assertions.assertTrue(entity.getHitPoints() == 99);
    }

    @Test
    void testDoPositionEvaluationWithAnyICanKill() {
        Rectangle characterGraphics = new Rectangle();
        GhostCharacter ghost = new GhostCharacter(characterGraphics, 0, 0, OpponentsFactory.eINSTANCE.createGhost());
        GhostCharacter entity = new GhostCharacter(characterGraphics, 0, 0, OpponentsFactory.eINSTANCE.createGhost());

        Bounds nodeBounds = new Rectangle(0, 0, 10, 10).getBoundsInParent();

        try {
            ghost.doPositionEvaluation(nodeBounds, entity);
        } catch (Exception e) {
            Assertions.fail("Exception caught: " + e.getMessage());
        }
    }
}


