package main.game.maze.common.graphics.fire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Pulse beam cannon visuals: the charge and the beam")
class PulseEffectsTest {

    private static double widest(List<FirePuff> puffs) {
        return puffs.stream().mapToDouble(FirePuff::radius).max().orElse(0);
    }

    @Test
    @DisplayName("the beam is far thicker than the laser, swells at once, then fades and ends")
    void beam() {
        PulseBeamEffect beam = new PulseBeamEffect(0, 0, 400, 0);
        List<FirePuff> early = beam.puffs(0.1, 0.1);
        List<FirePuff> late = beam.puffs(0.8, 0.8);
        assertFalse(early.isEmpty());
        assertTrue(early.stream().anyMatch(p -> p.layer() == FirePuff.Layer.CORE), "a white-hot core");
        assertTrue(early.stream().anyMatch(p -> p.layer() == FirePuff.Layer.BODY && p.radius() > 20), "a wide halo");
        assertTrue(early.stream().anyMatch(p -> p.layer() == FirePuff.Layer.SHOCKWAVE), "a shockwave");
        double laserHalfWidth = new LaserBeamEffect(0, 0, 400, 0).puffs(0.1, 0.1).stream().mapToDouble(FirePuff::radius).max().orElse(0);
        assertTrue(widest(early) > 2 * laserHalfWidth, "much thicker than the laser: " + widest(early) + " vs " + laserHalfWidth);
        assertTrue(late.stream().filter(p -> p.layer() == FirePuff.Layer.CORE).mapToDouble(FirePuff::alpha).max().orElse(0)
                < early.stream().filter(p -> p.layer() == FirePuff.Layer.CORE).mapToDouble(FirePuff::alpha).max().orElse(0), "fading");
        assertTrue(beam.puffs(1.0, 1.0).isEmpty());
        double[] b = beam.bounds();
        for (FirePuff p : early) {
            assertTrue(p.x() >= b[0] && p.x() <= b[2] && p.y() >= b[1] && p.y() <= b[3], "inside the bounds");
        }
    }

    @Test
    @DisplayName("the beam runs along its line whichever way it points")
    void beamFollowsItsLine() {
        for (double[] end : new double[][] {{300, 0}, {0, 300}, {-300, 0}, {0, -300}}) {
            PulseBeamEffect beam = new PulseBeamEffect(50, 50, 50 + end[0], 50 + end[1]);
            long onLine = beam.puffs(0.2, 0.2).stream().filter(p -> p.layer() == FirePuff.Layer.CORE)
                    .filter(p -> Math.abs((p.x() - 50) * end[1] - (p.y() - 50) * end[0]) / 300.0 < 1.0).count();
            assertTrue(onLine > 20, "the core lies along the line");
        }
    }

    @Test
    @DisplayName("the charge grows as it comes up: a bigger, brighter ball with more sparks, and a frame is short-lived")
    void charge() {
        PulseChargeEffect start = new PulseChargeEffect(100, 100, 0.0);
        PulseChargeEffect full = new PulseChargeEffect(100, 100, 1.0);
        assertEquals(PulseChargeEffect.FRAME_SECONDS, start.lifetimeSeconds(), 1e-9);
        List<FirePuff> a = start.puffs(0, 1.0);
        List<FirePuff> b = full.puffs(0, 1.0);
        assertTrue(widest(b.stream().filter(p -> p.layer() == FirePuff.Layer.BODY).toList())
                > widest(a.stream().filter(p -> p.layer() == FirePuff.Layer.BODY).toList()), "the ball swells");
        assertTrue(b.stream().filter(p -> p.layer() == FirePuff.Layer.EMBER).count()
                > a.stream().filter(p -> p.layer() == FirePuff.Layer.EMBER).count(), "more sparks");
        assertTrue(start.puffs(1.0, 1.0).isEmpty());
        double[] bounds = full.bounds();
        for (FirePuff p : b) {
            assertTrue(p.x() >= bounds[0] && p.x() <= bounds[2] && p.y() >= bounds[1] && p.y() <= bounds[3]);
        }
    }
}
