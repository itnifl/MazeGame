package main.game.maze.common.graphics.fire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Bomb impact: a deep boom and a heavy screen shake, the same in both frontends")
class BombImpactTest {

    @Test
    @DisplayName("the explosion sound exists on the classpath")
    void soundExists() {
        assertNotNull(BombImpact.class.getResource(BombImpact.SOUND), "missing " + BombImpact.SOUND);
    }

    @Test
    @DisplayName("the shake is clearly felt: strong and lasting about half a second")
    void shakeIsHeavy() {
        assertTrue(BombImpact.SHAKE_STRENGTH >= 10, "strong enough to see");
        assertTrue(BombImpact.SHAKE_SECONDS >= 0.4 && BombImpact.SHAKE_SECONDS <= 1.2);
    }

    @Test
    @DisplayName("the offset starts strong, dies away and is zero outside the shake")
    void offsetsDecay() {
        double[] before = BombImpact.shakeOffset(-0.1);
        assertEquals(0, before[0], 1e-9);
        assertEquals(0, before[1], 1e-9);
        double[] after = BombImpact.shakeOffset(BombImpact.SHAKE_SECONDS + 0.01);
        assertEquals(0, after[0], 1e-9);
        assertEquals(0, after[1], 1e-9);

        double early = 0;
        double late = 0;
        for (double t = 0; t < 0.2; t += 0.005) {
            early = Math.max(early, Math.hypot(BombImpact.shakeOffset(t)[0], BombImpact.shakeOffset(t)[1]));
        }
        for (double t = BombImpact.SHAKE_SECONDS - 0.2; t < BombImpact.SHAKE_SECONDS; t += 0.005) {
            late = Math.max(late, Math.hypot(BombImpact.shakeOffset(t)[0], BombImpact.shakeOffset(t)[1]));
        }
        assertTrue(early > BombImpact.SHAKE_STRENGTH * 0.5, "starts strong: " + early);
        assertTrue(late < early * 0.4, "dies away: " + late + " vs " + early);
    }

    @Test
    @DisplayName("the offset never exceeds the strength")
    void bounded() {
        for (double t = 0; t < BombImpact.SHAKE_SECONDS; t += 0.003) {
            double[] offset = BombImpact.shakeOffset(t);
            assertTrue(Math.abs(offset[0]) <= BombImpact.SHAKE_STRENGTH + 1e-9);
            assertTrue(Math.abs(offset[1]) <= BombImpact.SHAKE_STRENGTH + 1e-9);
        }
    }
}
