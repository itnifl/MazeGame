package main.game.maze;

import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;
import main.game.maze.characters.GhostCharacter;
import main.game.maze.characters.PlayerCharacter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GhostCharacterTest {

    @Test
    void testDoPositionEvaluationWithAnyICanDie() {
        Rectangle gCharacterGraphics = new Rectangle();
        GhostCharacter ghost = new GhostCharacter(gCharacterGraphics, 0, 0);

        Rectangle pCharacterGraphics = new Rectangle();
        PlayerCharacter entity = new PlayerCharacter(pCharacterGraphics, 0, 0, null);

        Bounds nodeBounds = new Rectangle(0, 0, 10, 10).getBoundsInParent();

        ghost.doPositionEvaluation(nodeBounds, entity);

        Assertions.assertTrue(entity.getHitPoints() == 99);
    }

    @Test
    void testDoPositionEvaluationWithAnyICanKill() {
        Rectangle characterGraphics = new Rectangle();
        GhostCharacter ghost = new GhostCharacter(characterGraphics, 0, 0);

        GhostCharacter entity = new GhostCharacter(characterGraphics, 0, 0);

        Bounds nodeBounds = new Rectangle(0, 0, 10, 10).getBoundsInParent();

        try {
            ghost.doPositionEvaluation(nodeBounds, entity);
        } catch (Exception e) {
            Assertions.fail("Exception caught: " + e.getMessage());
        }
    }
}
