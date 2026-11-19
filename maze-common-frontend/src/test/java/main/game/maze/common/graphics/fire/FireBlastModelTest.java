package main.game.maze.common.graphics.fire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("FireBlastModel")
class FireBlastModelTest {

    private static final double W = 40.0;
    private static final FireBlast CROSS = new FireBlast(100, 200, 120, 80, 60, 0, W, 42L);

    @Test
    @DisplayName("blast is gone at the end of its life")
    void emptyWhenFinished() {
        assertTrue(FireBlastModel.puffs(CROSS, 1.0, 0.0).isEmpty());
        assertTrue(FireBlastModel.puffs(CROSS, 1.5, 0.0).isEmpty());
        assertTrue(FireBlastModel.puffs(null, 0.3, 0.0).isEmpty());
    }

    @Test
    @DisplayName("at detonation only the centre fireball, shockwave and embers exist")
    void detonationFrameHasNoArmPuffs() {
        List<FirePuff> puffs = FireBlastModel.puffs(CROSS, 0.0, 0.0);

        assertFalse(puffs.isEmpty());
        for (FirePuff puff : puffs) {
            if (puff.layer() == FirePuff.Layer.CORE) {
                assertEquals(CROSS.centerX(), puff.x(), 1e-9);
                assertEquals(CROSS.centerY(), puff.y(), 1e-9);
            }
        }
    }

    @ParameterizedTest(name = "progress {0}")
    @ValueSource(doubles = {0.05, 0.15, 0.3, 0.6, 0.9})
    @DisplayName("flame puffs stay inside the corridor and within each arm's reach")
    void puffsStayInsideCorridors(double progress) {
        for (FirePuff puff : FireBlastModel.puffs(CROSS, progress, 1.7)) {
            if (puff.layer() == FirePuff.Layer.SHOCKWAVE || puff.layer() == FirePuff.Layer.EMBER) {
                continue;
            }
            double dx = puff.x() - CROSS.centerX();
            double dy = puff.y() - CROSS.centerY();
            double lateralLimit = W * FireBlastModel.MAX_LATERAL + 1e-9;
            boolean onXArm = Math.abs(dy) <= lateralLimit
                    && dx <= CROSS.armPlusX() + 1e-9 && -dx <= CROSS.armMinusX() + lateralLimit;
            boolean onYArm = Math.abs(dx) <= lateralLimit
                    && dy <= CROSS.armPlusY() + 1e-9 && -dy <= CROSS.armMinusY() + lateralLimit;
            assertTrue(onXArm || onYArm, "puff outside corridor: " + puff);
        }
    }

    @Test
    @DisplayName("an arm blocked by an adjacent wall gets no flame puffs")
    void zeroLengthArmHasNoPuffs() {
        for (FirePuff puff : FireBlastModel.puffs(CROSS, 0.5, 0.0)) {
            if (puff.layer() == FirePuff.Layer.SHOCKWAVE || puff.layer() == FirePuff.Layer.EMBER) {
                continue;
            }
            assertTrue(puff.y() >= CROSS.centerY() - W * FireBlastModel.MAX_LATERAL - 1e-9,
                    "puff on blocked -y arm: " + puff);
        }
    }

    @Test
    @DisplayName("flame fronts travel outwards over the first part of the life")
    void flameFrontsTravelOutwards() {
        double early = farthestPlusX(FireBlastModel.puffs(CROSS, 0.05, 0.0));
        double later = farthestPlusX(FireBlastModel.puffs(CROSS, 0.3, 0.0));

        assertTrue(later > early, "front should advance: early=" + early + " later=" + later);
        assertEquals(CROSS.centerX() + CROSS.armPlusX(), later, W * FireBlastModel.MAX_LATERAL + 1e-9);
    }

    @Test
    @DisplayName("white-hot core fades out and smoke builds up as the fire dies")
    void coolsFromWhiteHotToSmoke() {
        List<FirePuff> early = FireBlastModel.puffs(CROSS, 0.05, 0.0);
        List<FirePuff> late = FireBlastModel.puffs(CROSS, 0.7, 0.0);

        assertTrue(count(early, FirePuff.Layer.CORE) > 0);
        assertEquals(0, count(late, FirePuff.Layer.CORE));
        assertEquals(0, count(early, FirePuff.Layer.SMOKE));
        assertTrue(count(late, FirePuff.Layer.SMOKE) > 0);
    }

    @Test
    @DisplayName("puffs are ordered back to front by layer")
    void orderedByLayer() {
        List<FirePuff> puffs = FireBlastModel.puffs(CROSS, 0.35, 0.4);
        for (int i = 1; i < puffs.size(); i++) {
            assertTrue(puffs.get(i - 1).layer().ordinal() <= puffs.get(i).layer().ordinal(),
                    "layer order broken at " + i);
        }
    }

    @Test
    @DisplayName("colours and alpha are valid and radii positive")
    void valuesInRange() {
        for (double p = 0; p < 1.0; p += 0.05) {
            for (FirePuff puff : FireBlastModel.puffs(CROSS, p, p * 3)) {
                assertTrue(puff.radius() > 0);
                assertTrue(puff.alpha() > 0 && puff.alpha() <= 1);
                assertTrue(puff.red() >= 0 && puff.red() <= 1);
                assertTrue(puff.green() >= 0 && puff.green() <= 1);
                assertTrue(puff.blue() >= 0 && puff.blue() <= 1);
            }
        }
    }

    @Test
    @DisplayName("same blast, progress and clock give the same frame; the clock adds flicker")
    void deterministicWithFlicker() {
        assertEquals(FireBlastModel.puffs(CROSS, 0.4, 2.0), FireBlastModel.puffs(CROSS, 0.4, 2.0));
        assertNotEquals(FireBlastModel.puffs(CROSS, 0.4, 2.0), FireBlastModel.puffs(CROSS, 0.4, 2.05));
    }

    @Test
    @DisplayName("only smoke uses normal blending")
    void onlySmokeIsNotAdditive() {
        for (FirePuff.Layer layer : FirePuff.Layer.values()) {
            assertEquals(layer != FirePuff.Layer.SMOKE, layer.additive(), layer.name());
        }
    }

    @Test
    @DisplayName("FireBlast clamps negative arms and rejects a non-positive width")
    void fireBlastValidation() {
        FireBlast blast = new FireBlast(0, 0, -5, 10, 0, 3, 1, 0);
        assertEquals(0, blast.armPlusX());
        assertEquals(10, blast.maxArm());
        assertThrows(IllegalArgumentException.class, () -> new FireBlast(0, 0, 1, 1, 1, 1, 0, 0));
        assertEquals(FireBlast.seedFor(1, 2), FireBlast.seedFor(1, 2));
        assertNotEquals(FireBlast.seedFor(1, 2), FireBlast.seedFor(2, 1));
    }

    private static double farthestPlusX(List<FirePuff> puffs) {
        return puffs.stream()
                .filter(p -> p.layer() == FirePuff.Layer.BODY)
                .mapToDouble(FirePuff::x)
                .max()
                .orElse(Double.NaN);
    }

    private static long count(List<FirePuff> puffs, FirePuff.Layer layer) {
        return puffs.stream().filter(p -> p.layer() == layer).count();
    }
}
