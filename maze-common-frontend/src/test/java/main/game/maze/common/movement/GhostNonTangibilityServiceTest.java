package main.game.maze.common.movement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GhostNonTangibilityServiceTest {

    @Test
    void isPhasing_returnsTrueWhenEnergyPositive() {
        assertTrue(GhostNonTangibilityService.isPhasing(50.0));
    }

    @Test
    void isPhasing_returnsTrueWhenEnergyAtMaximum() {
        assertTrue(GhostNonTangibilityService.isPhasing(GhostNonTangibilityService.MAX_ENERGY));
    }

    @Test
    void isPhasing_returnsFalseWhenEnergyZero() {
        assertFalse(GhostNonTangibilityService.isPhasing(0.0));
    }

    @Test
    void isPhasing_returnsFalseWhenEnergyNegative() {
        assertFalse(GhostNonTangibilityService.isPhasing(-1.0));
    }

    @Test
    void drainEnergy_reducesCorrectlyOverOneTick() {
        // JavaFX 60 ms tick: 0.060 s * ENERGY_DECREASE_PER_SEC should equal exactly 0.14
        double drained = GhostNonTangibilityService.drainEnergy(10.0, 0.060);
        assertEquals(10.0 - 0.14, drained, 1e-9,
                "one 60-ms JavaFX tick should drain exactly 0.14 energy");
    }

    @Test
    void drainEnergy_clampsToZero() {
        double result = GhostNonTangibilityService.drainEnergy(0.05, 1.0);
        assertEquals(0.0, result, "energy should never go below zero");
    }

    @Test
    void drainEnergy_returnsZeroWhenAlreadyZero() {
        assertEquals(0.0, GhostNonTangibilityService.drainEnergy(0.0, 0.060));
    }

    @Test
    void calculateOpacity_atFullEnergy_returnsMinimum() {
        // energy = MAX_ENERGY: opacity = 1 - (100/100) + 0.1 = 0.1
        double opacity = GhostNonTangibilityService.calculateOpacity(GhostNonTangibilityService.MAX_ENERGY);
        assertEquals(0.1, opacity, 1e-9, "fully-phasing ghost should have minimum opacity 0.1");
    }

    @Test
    void calculateOpacity_atZeroEnergy_returnsFull() {
        double opacity = GhostNonTangibilityService.calculateOpacity(0.0);
        assertEquals(1.0, opacity, 1e-9, "solid ghost (energy=0) should have full opacity 1.0");
    }

    @Test
    void calculateOpacity_atHalfEnergy() {
        double opacity = GhostNonTangibilityService.calculateOpacity(50.0);
        // 1 - (50/100) + 0.1 = 0.6
        assertEquals(0.6, opacity, 1e-9);
    }

    @Test
    void calculateOpacity_clampedToMinimum_whenEnergyExceedsMax() {
        double opacity = GhostNonTangibilityService.calculateOpacity(200.0);
        assertTrue(opacity >= 0.1 && opacity <= 1.0, "opacity must stay within [0.1, 1.0]");
    }
}
