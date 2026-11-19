package main.game.maze.common.graphics.fire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PumpkinBurstModel")
class PumpkinBurstModelTest {

    private static final PumpkinBurst BURST = new PumpkinBurst(200, 150, 52, 42L);

    private static List<FirePuff> layer(List<FirePuff> puffs, FirePuff.Layer layer) {
        return puffs.stream().filter(p -> p.layer() == layer).toList();
    }

    private static double meanDistance(List<FirePuff> puffs) {
        return puffs.stream()
                .mapToDouble(p -> Math.hypot(p.x() - BURST.centerX(), p.y() - BURST.centerY()))
                .average().orElse(0);
    }

    @Test
    @DisplayName("every puff stays inside the burst's maximum extent over the whole life")
    void staysInsideExtent() {
        double limit = BURST.maxExtent();
        for (double p = 0; p < 1.0; p += 0.01) {
            for (FirePuff puff : PumpkinBurstModel.puffs(BURST, p, p * 3)) {
                double reach = Math.hypot(puff.x() - BURST.centerX(), puff.y() - BURST.centerY()) + puff.radius();
                assertTrue(reach <= limit + 1e-9, "puff beyond extent at progress " + p + ": " + puff);
            }
        }
    }

    @Test
    @DisplayName("detonation starts with a bright white-hot flash at the centre")
    void flashAtStart() {
        List<FirePuff> core = layer(PumpkinBurstModel.puffs(BURST, 0.02, 0), FirePuff.Layer.CORE);
        assertTrue(core.stream().anyMatch(p -> p.alpha() > 0.6
                        && p.radius() > BURST.radius() * 0.3
                        && Math.hypot(p.x() - BURST.centerX(), p.y() - BURST.centerY()) < 1e-9),
                "expected a large, bright core flash at the centre");
    }

    @Test
    @DisplayName("pumpkin shell chunks (orange rind and green stem) fly outward")
    void pumpkinDebrisFliesOutward() {
        List<FirePuff> early = layer(PumpkinBurstModel.puffs(BURST, 0.05, 0), FirePuff.Layer.DEBRIS);
        List<FirePuff> later = layer(PumpkinBurstModel.puffs(BURST, 0.4, 0), FirePuff.Layer.DEBRIS);

        assertFalse(later.isEmpty());
        assertTrue(later.stream().anyMatch(p -> p.red() > 0.6f && p.green() > 0.25f && p.green() < 0.6f && p.blue() < 0.2f),
                "expected orange rind chunks");
        assertTrue(later.stream().anyMatch(p -> p.green() > p.red()), "expected green stem bits");
        assertTrue(meanDistance(later) > meanDistance(early) * 2, "debris should travel outward");
    }

    @Test
    @DisplayName("debris is painted over normally, not glowing")
    void debrisIsNotAdditive() {
        assertFalse(FirePuff.Layer.DEBRIS.additive());
    }

    @Test
    @DisplayName("the fire dies into smoke: no white-hot core late in life")
    void endsInSmoke() {
        List<FirePuff> late = PumpkinBurstModel.puffs(BURST, 0.85, 0);
        assertTrue(layer(late, FirePuff.Layer.CORE).isEmpty());
        assertFalse(layer(late, FirePuff.Layer.SMOKE).isEmpty());
    }

    @Test
    @DisplayName("puffs are ordered back to front by layer, with valid colours")
    void orderedAndValid() {
        for (double p = 0; p < 1.0; p += 0.1) {
            List<FirePuff> puffs = PumpkinBurstModel.puffs(BURST, p, 1.3);
            for (int i = 1; i < puffs.size(); i++) {
                assertTrue(puffs.get(i - 1).layer().ordinal() <= puffs.get(i).layer().ordinal());
            }
            for (FirePuff puff : puffs) {
                assertTrue(puff.radius() > 0);
                assertTrue(puff.alpha() > 0 && puff.alpha() <= 1);
                assertTrue(puff.red() >= 0 && puff.red() <= 1 && puff.green() >= 0 && puff.green() <= 1
                        && puff.blue() >= 0 && puff.blue() <= 1);
            }
        }
    }

    @Test
    @DisplayName("deterministic per seed; different seeds scatter debris differently")
    void deterministic() {
        assertEquals(PumpkinBurstModel.puffs(BURST, 0.3, 0.7), PumpkinBurstModel.puffs(BURST, 0.3, 0.7));
        PumpkinBurst other = new PumpkinBurst(200, 150, 52, 43L);
        assertNotEquals(layer(PumpkinBurstModel.puffs(BURST, 0.3, 0.7), FirePuff.Layer.DEBRIS),
                layer(PumpkinBurstModel.puffs(other, 0.3, 0.7), FirePuff.Layer.DEBRIS));
    }

    @Test
    @DisplayName("finished or missing bursts draw nothing")
    void finished() {
        assertTrue(PumpkinBurstModel.puffs(BURST, 1.0, 0).isEmpty());
        assertTrue(PumpkinBurstModel.puffs(null, 0.5, 0).isEmpty());
        assertTrue(PumpkinBurstModel.puffs(BURST, Double.NaN, 0).isEmpty());
    }

    @Test
    @DisplayName("bigger bursts last longer, within bounds; radius must be positive")
    void lifetimeAndValidation() {
        double small = PumpkinBurst.lifetimeFor(16);
        double big = PumpkinBurst.lifetimeFor(52);
        assertTrue(small < big);
        assertTrue(small >= PumpkinBurst.MIN_LIFETIME_SECONDS && big <= PumpkinBurst.MAX_LIFETIME_SECONDS);
        assertEquals(PumpkinBurst.MAX_LIFETIME_SECONDS, PumpkinBurst.lifetimeFor(10_000));
        assertThrows(IllegalArgumentException.class, () -> new PumpkinBurst(0, 0, 0, 1L));
        assertEquals(52.0, PumpkinBurst.lobRadius(52.0));
        assertEquals(PumpkinBurst.MIN_LOB_RADIUS, PumpkinBurst.lobRadius(10.0));
    }
}
