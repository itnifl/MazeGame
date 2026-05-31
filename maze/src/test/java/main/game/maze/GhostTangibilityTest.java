package main.game.maze;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;
import main.game.maze.characters.GhostCharacter;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.opponents.Ghost;
import main.game.maze.opponents.OpponentsFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GhostTangibilityTest {

    @AfterEach
    void tearDown() {
        App.gameController = null;
    }

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
     * A phasing ghost (energy > 0) bypasses walls and CAN deal damage even when
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

        // Phasing ghost bypasses wall check and deals damage on contact.
        assertTrue(player.getHitPoints() < hpBefore,
                "Phasing ghost must deal damage on contact (bypasses wall check)");
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

        // Bounds overlap and App.gameController is null, so the wall check is skipped and damage is applied.
        Bounds playerBounds = new BoundingBox(5, 5, 30, 30);
        ghost.doPositionEvaluation(playerBounds, player);

        assertTrue(player.getHitPoints() < hpBefore,
                "Solid ghost in direct contact must subtract hit points");
    }

    /**
     * A solid ghost whose bounding box DOES overlap the player, while
     * {@code App.gameController} is {@code null} (no JavaFX context in unit tests),
     * MUST apply damage. The null-safe guard in {@code doPositionEvaluation}
     * skips the wall check entirely in this case.
     *
     * <p>Full integration of the wall-blocking branch (wall between ghost and
     * player prevents damage) is covered by
     * {@link main.game.maze.WallCollisionUtilWallBetweenTest} which tests
     * {@code WallCollisionUtil.wallBetweenVectors} directly, and by
     * {@link main.game.maze.libgdx.game.PlayerCombatStateServiceTest} for the
     * libGDX path.
     */
    @Test
    void solidGhostOverlappingBoundsWithNullControllerAppliesDamage() {
        // Ghost at (0,0) 40x40; player at (10,10) 30x30 — bounding boxes overlap.
        Rectangle ghostGfx = new Rectangle(0, 0, 40, 40);
        GhostCharacter ghost = newGhost(ghostGfx);
        ghost.setNonTangientEnergy(0);   // solid

        App.gameController = null;   // explicit: no wall check available in unit tests

        PlayerCharacter player = new PlayerCharacter(new Rectangle(10, 10, 30, 30), 0, 0, null);
        int hpBefore = player.getHitPoints();

        Bounds playerBounds = new BoundingBox(10, 10, 30, 30);
        ghost.doPositionEvaluation(playerBounds, player);

        assertTrue(player.getHitPoints() < hpBefore,
                "Solid ghost with overlapping bounds and no wall-check controller must apply damage");
    }

    /**
     * A solid ghost whose bounding box DOES overlap the player bounds, but a wall is
     * reported between them by the game controller, must NOT deal damage.
     * This exercises the wall-blocking branch in {@code doPositionEvaluation} when
     * {@code App.gameController} is non-null and reports {@code isWallBetween = true}.
     */
    @Test
    void solidGhostBlockedByWallDoesNoDamage() {
        Rectangle ghostGfx = new Rectangle(0, 0, 40, 40);
        GhostCharacter ghost = newGhost(ghostGfx);
        ghost.setNonTangientEnergy(0);   // solid

        // Stub controller that always reports a wall between ghost and player.
        App.gameController = new GameController() {
            @Override
            public boolean isWallBetween(double ex, double ey, double px, double py) {
                return true;
            }
        };

        // Overlapping bounds: ghost [0,0..40,40] and player [10,10..30,30].
        PlayerCharacter player = new PlayerCharacter(new Rectangle(10, 10, 30, 30), 0, 0, null);
        int hpBefore = player.getHitPoints();

        Bounds playerBounds = new BoundingBox(10, 10, 30, 30);
        ghost.doPositionEvaluation(playerBounds, player);

        assertEquals(hpBefore, player.getHitPoints(),
                "Solid ghost blocked by wall must not deal damage even when bounds intersect");
    }

    /**
     * A solid ghost whose bounding box does not overlap the player bounds must NOT deal damage.
     * The bounds-intersection check is the primary gate; no overlap means no contact.
     */
    @Test
    void solidGhostFarFromPlayerCausesNoBoundsOverlapNoDamage() {
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

    // -----------------------------------------------------------------------
    // Path-1 contact: ghost moves → player.doPositionEvaluation (task scream fix)
    // -----------------------------------------------------------------------

    /**
     * Creates a player whose graphics is a plain {@link Rectangle} (non-ImageView).
     * With the {@code instanceof ImageView} guard now in
     * {@code PlayerCharacter.doPositionEvaluation}, the flash is silently skipped
     * and no ClassCastException is thrown. This lets unit tests exercise the
     * damage and scream paths without a real sprite.
     */
    private PlayerCharacter newPlayerWithRectangle() {
        return new PlayerCharacter(new Rectangle(0, 0, 30, 30), 0, 0, null);
    }

    /**
     * A phasing ghost (energy > 0) must deal damage when evaluated by the player
     * (path 1: ghost notifier → {@code PlayerCharacter.doPositionEvaluation}).
     * Before the fix, this path returned early for phasing ghosts with no damage.
     */
    @Test
    @DisplayName("Phasing ghost — player evaluation path — deals damage")
    void phasingGhostContactViaPlayerEvaluation_dealsDamage() {
        PlayerCharacter player = newPlayerWithRectangle();
        int hpBefore = player.getHitPoints();

        Rectangle ghostGfx = new Rectangle(0, 0, 40, 40);
        GhostCharacter ghost = newGhost(ghostGfx);
        ghost.setNonTangientEnergy(5);   // phasing

        // Ghost bounds fully overlap the player (both start at origin).
        Bounds ghostBounds = new BoundingBox(0, 0, 40, 40);
        player.doPositionEvaluation(ghostBounds, ghost);

        assertTrue(player.getHitPoints() < hpBefore,
                "Phasing ghost must deal damage via PlayerCharacter.doPositionEvaluation (no early-return)");
    }

    /**
     * A phasing ghost with a wall controller that always reports a wall between ghost and
     * player must still deal damage — phasing ghosts bypass wall checks.
     */
    @Test
    @DisplayName("Phasing ghost — player evaluation path — bypasses wall check")
    void phasingGhostContactViaPlayerEvaluation_bypassesWallCheck() {
        App.gameController = new GameController() {
            @Override
            public boolean isWallBetween(double ex, double ey, double px, double py) {
                return true;   // always a wall
            }
        };

        PlayerCharacter player = newPlayerWithRectangle();
        int hpBefore = player.getHitPoints();

        Rectangle ghostGfx = new Rectangle(0, 0, 40, 40);
        GhostCharacter ghost = newGhost(ghostGfx);
        ghost.setNonTangientEnergy(5);   // phasing

        Bounds ghostBounds = new BoundingBox(0, 0, 40, 40);
        player.doPositionEvaluation(ghostBounds, ghost);

        assertTrue(player.getHitPoints() < hpBefore,
                "Phasing ghost must bypass wall check and deal damage in player's doPositionEvaluation");
    }

    /**
     * A solid ghost (energy == 0) with a wall between it and the player must NOT deal damage
     * via {@code PlayerCharacter.doPositionEvaluation}.
     */
    @Test
    @DisplayName("Solid ghost — player evaluation path — blocked by wall, no damage")
    void solidGhostContactViaPlayerEvaluation_blockedByWall_noDamage() {
        App.gameController = new GameController() {
            @Override
            public boolean isWallBetween(double ex, double ey, double px, double py) {
                return true;   // always a wall
            }
        };

        PlayerCharacter player = newPlayerWithRectangle();
        int hpBefore = player.getHitPoints();

        Rectangle ghostGfx = new Rectangle(0, 0, 40, 40);
        GhostCharacter ghost = newGhost(ghostGfx);
        ghost.setNonTangientEnergy(0);   // solid

        Bounds ghostBounds = new BoundingBox(0, 0, 40, 40);
        player.doPositionEvaluation(ghostBounds, ghost);

        assertEquals(hpBefore, player.getHitPoints(),
                "Solid ghost blocked by wall must not deal damage in player's doPositionEvaluation");
    }
}
