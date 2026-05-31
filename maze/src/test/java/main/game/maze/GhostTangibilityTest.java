package main.game.maze;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;
import main.game.maze.characters.GhostCharacter;
import main.game.maze.characters.PlayerCharacter;
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

    // -----------------------------------------------------------------------
    // Wall-through contact guard (task #2)
    // -----------------------------------------------------------------------

    /**
     * A phasing ghost (energy > 0) must NOT deal damage to the player even when
     * its bounding box intersects the player's bounds.
     */
    @Test
    void phasingGhostDealsNoDamageThroughWall() {
        // Place ghost graphics directly on top of a fake player bounds.
        Rectangle ghostGfx = new Rectangle(0, 0, 40, 40);
        GhostCharacter ghost = newGhost(ghostGfx);
        ghost.setNonTangientEnergy(5);   // energy > 0  =>  phasing

        // A PlayerCharacter whose health we can observe.
        PlayerCharacter player = new PlayerCharacter(new Rectangle(0, 0, 30, 30), 0, 0, null);
        int hpBefore = player.getHitPoints();

        // Simulate the ghost evaluating its position against the player's bounds.
        Bounds playerBounds = new BoundingBox(0, 0, 30, 30);
        ghost.doPositionEvaluation(playerBounds, player);

        assertEquals(hpBefore, player.getHitPoints(),
                "Phasing ghost must not deal damage even when bounding boxes overlap");
    }

    /**
     * A solid ghost (energy == 0) in direct contact MUST deal damage.
     */
    @Test
    void solidGhostDealtContactDamage() {
        // Ghost at (0,0) with size 40x40; player at (5,5) with size 30x30 — well within touch range.
        Rectangle ghostGfx = new Rectangle(0, 0, 40, 40);
        GhostCharacter ghost = newGhost(ghostGfx);
        ghost.setNonTangientEnergy(0);   // solid

        PlayerCharacter player = new PlayerCharacter(new Rectangle(5, 5, 30, 30), 0, 0, null);
        int hpBefore = player.getHitPoints();

        // Player's bounds in the parent pane context: x=5, y=5, w=30, h=30 → center (20, 20).
        // Ghost center: (20, 20). Distance = 0 < touchThreshold (35). Damage expected.
        Bounds playerBounds = new BoundingBox(5, 5, 30, 30);
        ghost.doPositionEvaluation(playerBounds, player);

        assertTrue(player.getHitPoints() < hpBefore,
                "Solid ghost in direct contact must subtract hit points");
    }

    /**
     * A solid ghost whose bounding box does not overlap the player bounds
     * (i.e. the ghost is far away on the other side of a wall) must NOT deal damage.
     * The bounds-intersection check is the primary gate; any two characters
     * far apart simply never intersect.
     */
    @Test
    void solidGhostThroughWallDoesNoDamage() {
        // Ghost at (0,0) 40x40; player bounds at (200,0) 30x30 — no overlap.
        Rectangle ghostGfx = new Rectangle(0, 0, 40, 40);
        GhostCharacter ghost = newGhost(ghostGfx);
        ghost.setNonTangientEnergy(0);   // solid

        PlayerCharacter player = new PlayerCharacter(new Rectangle(200, 0, 30, 30), 0, 0, null);
        int hpBefore = player.getHitPoints();

        Bounds playerBounds = new BoundingBox(200, 0, 30, 30);
        ghost.doPositionEvaluation(playerBounds, player);

        assertEquals(hpBefore, player.getHitPoints(),
                "Solid ghost far away (wall simulation) must not deal damage");
    }
}
