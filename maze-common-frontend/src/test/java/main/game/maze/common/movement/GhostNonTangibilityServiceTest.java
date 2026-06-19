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

    // -----------------------------------------------------------------------
    // Two-arg overload: calculateOpacity(energy, visibilityLevel)
    // -----------------------------------------------------------------------

    @Test
    void calculateOpacity_withVisibility_solidGhost_returnsBaseOpacity() {
        assertEquals(0.5, GhostNonTangibilityService.calculateOpacity(0, 50), 1e-9,
                "solid ghost with visibilityLevel=50 must return 0.5");
    }

    @Test
    void calculateOpacity_withVisibility_solidGhost_fullVisibility_returnsOne() {
        assertEquals(1.0, GhostNonTangibilityService.calculateOpacity(0, 100), 1e-9,
                "solid ghost with visibilityLevel=100 must return 1.0");
    }

    @Test
    void calculateOpacity_withVisibility_solidGhost_zeroVisibility_returnsZero() {
        assertEquals(0.0, GhostNonTangibilityService.calculateOpacity(0, 0), 1e-9,
                "solid ghost with visibilityLevel=0 must return 0.0 (fully invisible)");
    }

    @Test
    void calculateOpacity_withVisibility_solidGhost_negativeClamped() {
        assertEquals(0.0, GhostNonTangibilityService.calculateOpacity(0, -10), 1e-9,
                "negative visibilityLevel must clamp to 0.0");
    }

    @Test
    void calculateOpacity_withVisibility_solidGhost_overHundredClamped() {
        assertEquals(1.0, GhostNonTangibilityService.calculateOpacity(0, 150), 1e-9,
                "visibilityLevel > 100 must clamp to 1.0");
    }

    @Test
    void calculateOpacity_withVisibility_phasingAtMax_returnsFloor() {
        double opacity = GhostNonTangibilityService.calculateOpacity(GhostNonTangibilityService.MAX_ENERGY, 50);
        assertEquals(0.1, opacity, 1e-9,
                "fully-phasing ghost must return floor 0.1 regardless of visibilityLevel");
    }

    @Test
    void calculateOpacity_withVisibility_phasingAtHalf_cappedAtBase() {
        // energy=50, visibilityLevel=30: formula=0.6, but capped at baseOpacity=0.3
        double opacity = GhostNonTangibilityService.calculateOpacity(50, 30);
        assertEquals(0.3, opacity, 1e-9,
                "phasing opacity must be capped at baseOpacity when formula exceeds it");
    }

    @Test
    void calculateOpacity_withVisibility_phasingLowEnergy_cappedAtBase() {
        // energy=10, visibilityLevel=80: formula = 1 - 0.1 + 0.1 = 1.0, capped at 0.8
        double opacity = GhostNonTangibilityService.calculateOpacity(10, 80);
        assertEquals(0.8, opacity, 1e-9,
                "phasing ghost near solid must be capped at visibilityLevel/100 when formula exceeds it");
    }

    @Test
    void calculateOpacity_withVisibility_zeroVisibility_phasingRetainsFloor() {
        // intentional gameplay floor: even a fully-invisible ghost shows at 0.1 while phasing
        double opacity = GhostNonTangibilityService.calculateOpacity(50, 0);
        assertEquals(0.1, opacity, 1e-9,
                "phasing ghost with visibilityLevel=0 must still render at 0.1 (gameplay floor)");
    }

    @Test
    void calculateOpacity_withVisibility_singleArgOverloadPreserved_solidReturnsOne() {
        assertEquals(1.0, GhostNonTangibilityService.calculateOpacity(0.0), 1e-9,
                "single-arg overload must be preserved: solid ghost (energy=0) returns 1.0");
    }
}
