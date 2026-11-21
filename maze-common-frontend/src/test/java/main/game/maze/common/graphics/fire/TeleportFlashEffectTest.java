package main.game.maze.common.graphics.fire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Teleport flash: the player flashing in at the new place")
class TeleportFlashEffectTest {

    @Test
    @DisplayName("it is short, over after its lifetime and empty outside it")
    void lifetime() {
        TeleportFlashEffect flash = new TeleportFlashEffect(100, 100);
        assertTrue(flash.lifetimeSeconds() > 0.3 && flash.lifetimeSeconds() < 1.2);
        assertTrue(flash.puffs(1.0, 0).isEmpty());
        assertTrue(flash.puffs(Double.NaN, 0).isEmpty());
    }

    @Test
    @DisplayName("it starts with a blinding white flash on the spot and a column of light, and dies away")
    void flashesThenFades() {
        TeleportFlashEffect flash = new TeleportFlashEffect(100, 100);
        List<FirePuff> start = flash.puffs(0.02, 0);
        FirePuff core = start.stream().filter(p -> p.layer() == FirePuff.Layer.CORE).findFirst().orElse(null);
        assertTrue(core != null, "a white core");
        assertEquals(1.0f, core.red(), 1e-6f);
        assertEquals(1.0f, core.green(), 1e-6f);
        assertEquals(1.0f, core.blue(), 1e-6f);
        assertTrue(core.alpha() > 0.9f, "blinding at the start: " + core.alpha());
        assertEquals(100, core.x(), 1e-9);
        assertEquals(100, core.y(), 1e-9);
        assertTrue(start.stream().anyMatch(p -> p.y() < 60), "a column of light rising above the spot");

        double late = flash.puffs(0.9, 0).stream().mapToDouble(FirePuff::alpha).max().orElse(0);
        double early = start.stream().mapToDouble(FirePuff::alpha).max().orElse(0);
        assertTrue(late < early * 0.4, "dies away: " + late + " vs " + early);
    }

    @Test
    @DisplayName("a ring of light spreads out over the ground")
    void ringSpreads() {
        TeleportFlashEffect flash = new TeleportFlashEffect(0, 0);
        double early = flash.puffs(0.1, 0).stream().filter(p -> p.layer() == FirePuff.Layer.SHOCKWAVE)
                .mapToDouble(p -> Math.hypot(p.x(), p.y())).max().orElse(0);
        double late = flash.puffs(0.6, 0).stream().filter(p -> p.layer() == FirePuff.Layer.SHOCKWAVE)
                .mapToDouble(p -> Math.hypot(p.x(), p.y())).max().orElse(0);
        assertTrue(late > early + 10, early + " -> " + late);
    }

    @Test
    @DisplayName("every puff stays inside the bounds, and the effect is the same every time")
    void bounds() {
        TeleportFlashEffect flash = new TeleportFlashEffect(40, 60);
        double[] b = flash.bounds();
        for (double progress = 0; progress < 1.0; progress += 0.05) {
            List<FirePuff> puffs = flash.puffs(progress, 0.3);
            assertEquals(puffs, flash.puffs(progress, 0.3));
            for (FirePuff p : puffs) {
                assertTrue(p.x() - p.radius() >= b[0] - 1e-6 && p.x() + p.radius() <= b[2] + 1e-6
                        && p.y() - p.radius() >= b[1] - 1e-6 && p.y() + p.radius() <= b[3] + 1e-6, "inside the bounds: " + p);
            }
        }
        assertFalse(flash.puffs(0.5, 0).isEmpty());
    }

    @Test
    @DisplayName("on a screen whose y axis points up (libGDX) the column of light rises towards larger y")
    void columnRisesWhicheverWayIsUp() {
        boolean downAbove = new TeleportFlashEffect(100, 100).puffs(0.02, 0).stream().anyMatch(p -> p.y() < 60);
        boolean upAbove = new TeleportFlashEffect(100, 100, true).puffs(0.02, 0).stream().anyMatch(p -> p.y() > 140);
        assertTrue(downAbove, "y-down screen: above means smaller y");
        assertTrue(upAbove, "y-up screen: above means larger y");
        double[] b = new TeleportFlashEffect(40, 60, true).bounds();
        for (FirePuff p : new TeleportFlashEffect(40, 60, true).puffs(0.1, 0)) {
            assertTrue(p.y() >= b[1] - 1e-6 && p.y() <= b[3] + 1e-6, "inside the bounds: " + p);
        }
    }
}
