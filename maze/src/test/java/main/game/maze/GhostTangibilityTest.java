package main.game.maze;

import javafx.scene.shape.Rectangle;
import main.game.maze.characters.GhostCharacter;
import main.game.maze.opponents.Ghost;
import main.game.maze.opponents.OpponentsFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GhostTangibilityTest {

    private GhostCharacter newGhost(Rectangle graphics) {
        Ghost ghostModel = OpponentsFactory.eINSTANCE.createGhost();
        // constructor: GhostCharacter(Node characterGraphics, double x, double y, Ghost model)
        return new GhostCharacter(graphics, 0, 0, ghostModel);
    }

    @Test
    void nonTangibilityEnergyPassesThroughModel() {
        Ghost ghostModel = OpponentsFactory.eINSTANCE.createGhost();
        GhostCharacter ghost = new GhostCharacter(new Rectangle(), 0, 0, ghostModel);

        // default model value (EMF default in model is int, often 0 unless set)
        var defaultEnergy = ghostModel.getNonTangibilityEnergy();
        assertEquals(defaultEnergy, (int) ghost.getNonTangientEnergy(), "Getter should reflect model value");

        // set via character and see the model update
        ghost.setNonTangientEnergy(7);
        assertEquals(7, ghostModel.getNonTangibilityEnergy(), "Model must track character-set energy");
        assertEquals(7, (int) ghost.getNonTangientEnergy(), "Character getter must reflect updated model value");
    }

    @Test
    void setCharacterOpacityUpdatesNodeOpacity() {
        Rectangle graphics = new Rectangle();
        GhostCharacter ghost = newGhost(graphics);

        // sanity: JavaFX Nodes default to opacity 1.0
        assertEquals(1.0, graphics.getOpacity(), 1e-9, "Precondition: default opacity should be 1.0");

        ghost.setCharacterOpacity(0.4);
        assertEquals(0.4, graphics.getOpacity(), 1e-9, "setCharacterOpacity should set Node opacity");

        ghost.setCharacterOpacity(0.85);
        assertEquals(0.85, graphics.getOpacity(), 1e-9, "Subsequent calls should update opacity deterministically");
    }
}
