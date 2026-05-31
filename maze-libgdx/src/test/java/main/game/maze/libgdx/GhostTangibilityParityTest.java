package main.game.maze.libgdx;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import main.game.maze.common.movement.GhostNonTangibilityService;
import main.game.maze.libgdx.game.PlayerCombatStateService;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.opponents.BehaviorType;

/**
 * Parity tests verifying that the libgdx ghost tangibility rules match the
 * JavaFX rules documented in {@code GhostCharacter} and {@code PlayerCharacter}.
 *
 * <p>Ghost tangibility rules (shared between both frontends):
 * <ul>
 *   <li>A ghost with {@code nonTangibilityEnergy > 0} is phasing: it passes through
 *       walls but CAN still harm the player on contact.</li>
 *   <li>A ghost with {@code nonTangibilityEnergy == 0} is solid: it can harm the player
 *       on contact.</li>
 *   <li>Energy drains over time from its initial spawn value (default 100) to 0,
 *       at a rate defined in {@link GhostNonTangibilityService#ENERGY_DECREASE_PER_SEC}.</li>
 *   <li>While phasing, the ghost is rendered semi-transparent; the opacity formula is
 *       {@code clamp(1.0 - energy/100 + 0.1, 0.1, 1.0)}.</li>
 * </ul>
 */
class GhostTangibilityParityTest {

    /** Creates an EnemySpawn representing a ghost at (0,0) with the given energy level. */
    private static EnemySpawn ghostSpawn(double energy) {
        return new EnemySpawn(
                "ghost-1",
                "/main/game/maze/ghost1-right.png",
                0f, 0f,
                40f,
                1f, 5, 0, "",
                BehaviorType.WANDER,
                2f,
                energy);
    }

    // -----------------------------------------------------------------------
    // Combat rules: phasing ghost CAN harm player (bypasses wall check only)
    // -----------------------------------------------------------------------

    /**
     * A phasing ghost (energy > 0) MUST deal damage even though it passes through
     * walls. This mirrors the updated JavaFX {@code GhostCharacter.doPositionEvaluation}
     * behavior.
     */
    @Test
    void phasingGhostDealsDamage_libgdxMatchesJavaFx() {
        PlayerCombatStateService service = new PlayerCombatStateService();
        service.reset(100);

        EnemySpawn phasingGhost = ghostSpawn(100.0);

        // Place enemy directly on top of player (x=0,y=0) so distance check passes.
        var frame = service.update(1f / 30f, 0f, 0f, 20f, List.of(phasingGhost));

        assertTrue(frame.hpRatio() < 1.0f,
                "Phasing ghost must still deal damage on contact — matches updated JavaFX behavior");
    }

    /**
     * A solid ghost (energy == 0) in range MUST deal damage, mirroring the JavaFX
     * solid-ghost contact damage path.
     */
    @Test
    void solidGhostDealsDamage_libgdxMatchesJavaFx() {
        PlayerCombatStateService service = new PlayerCombatStateService();
        service.reset(100);

        EnemySpawn solidGhost = ghostSpawn(0.0);

        var frame = service.update(1f / 30f, 0f, 0f, 20f, List.of(solidGhost));

        assertTrue(frame.hpRatio() < 1.0f,
                "Solid ghost in contact must deal damage — matches JavaFX solid-ghost path");
    }

    /**
     * Ghost partially through energy depletion (0 < energy < 100) is still
     * phasing and must deal damage on contact.
     */
    @Test
    void partiallyDepletedPhasingGhostDealsDamage() {
        PlayerCombatStateService service = new PlayerCombatStateService();
        service.reset(100);

        EnemySpawn halfEnergyGhost = ghostSpawn(50.0);

        var frame = service.update(1f / 30f, 0f, 0f, 20f, List.of(halfEnergyGhost));

        assertTrue(frame.hpRatio() < 1.0f,
                "Ghost with energy=50 is still phasing but must deal damage on contact");
    }

    // -----------------------------------------------------------------------
    // EnemySpawn non-tangibility energy is carried through correctly
    // -----------------------------------------------------------------------

    @Test
    void enemySpawnCarriesNonTangibilityEnergy() {
        EnemySpawn phasing = ghostSpawn(100.0);
        EnemySpawn solid = ghostSpawn(0.0);

        assertEquals(100.0, phasing.nonTangibilityEnergy(), 0.001,
                "EnemySpawn must preserve the nonTangibilityEnergy passed at construction");
        assertEquals(0.0, solid.nonTangibilityEnergy(), 0.001,
                "EnemySpawn with zero energy must expose 0.0");
    }

    @Test
    void defaultEnemySpawnHasZeroNonTangibilityEnergy() {
        // The 10-arg convenience constructor (used for non-ghost enemies) defaults to 0.
        EnemySpawn zombie = new EnemySpawn("z1", "/zombie.png", 10f, 10f, 40f, 1f, 5, 0, "", 2f);

        assertEquals(0.0, zombie.nonTangibilityEnergy(), 0.001,
                "Default EnemySpawn (zombie) must have zero non-tangibility energy");
        assertFalse(zombie.nonTangibilityEnergy() > 0,
                "Default enemy must NOT be treated as phasing");
    }

    // -----------------------------------------------------------------------
    // Rendering opacity formula matches JavaFX
    // -----------------------------------------------------------------------

    /**
     * Verifies the libgdx opacity formula {@code clamp(1 - energy/100 + 0.1, 0.1, 1.0)}
     * matches the JavaFX implementation in {@code GameController.doNonTangientEnergyCalculation}.
     *
     * <p>This is a white-box test using a helper that exposes the formula without
     * requiring a full libgdx runtime.
     */
    @Test
    void renderOpacityFormulaMatchesJavaFx() {
        // energy=100 (fully phasing): opacity = 1 - 1.0 + 0.1 = 0.1
        assertEquals(0.1f, (float) GhostNonTangibilityService.calculateOpacity(100.0), 0.001f,
                "Fully-phasing ghost (energy=100) must have opacity 0.1 matching JavaFX");

        // energy=50: opacity = 1 - 0.5 + 0.1 = 0.6
        assertEquals(0.6f, (float) GhostNonTangibilityService.calculateOpacity(50.0), 0.001f,
                "Half-depleted ghost (energy=50) must have opacity 0.6 matching JavaFX");

        // energy=0: opacity clamped to 1.0 (solid)
        assertEquals(1.0f, (float) GhostNonTangibilityService.calculateOpacity(0.0), 0.001f,
                "Solid ghost (energy=0) must have opacity 1.0 matching JavaFX");

        // energy=90: opacity = 1 - 0.9 + 0.1 = 0.2
        assertEquals(0.2f, (float) GhostNonTangibilityService.calculateOpacity(90.0), 0.001f,
                "Ghost with energy=90 must have opacity 0.2");
    }

    /**
     * Helper that delegates to the shared service formula to ensure parity
     * between tests and the runtime.
     */
    private static float ghostRenderOpacity(double energy) {
        return (float) GhostNonTangibilityService.calculateOpacity(energy);
    }
}
