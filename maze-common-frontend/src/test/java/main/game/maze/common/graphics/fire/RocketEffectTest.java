package main.game.maze.common.graphics.fire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Rocket visual: a body, a flame and a smoke trail behind it, one short-lived frame at a time")
class RocketEffectTest {

    @Test
    @DisplayName("a frame lives only a moment, and is empty once over")
    void shortLived() {
        RocketEffect rocket = new RocketEffect(100, 100, 0);
        assertEquals(RocketEffect.FRAME_SECONDS, rocket.lifetimeSeconds(), 1e-9);
        assertFalse(rocket.puffs(0, 0.3).isEmpty());
        assertTrue(rocket.puffs(1.0, 0.3).isEmpty());
    }

    @Test
    @DisplayName("the flame and the smoke trail lie behind the rocket, whichever way it flies")
    void trailBehind() {
        for (double angle : new double[] {0, Math.PI / 2, Math.PI, -Math.PI / 4}) {
            RocketEffect rocket = new RocketEffect(200, 200, angle);
            List<FirePuff> puffs = rocket.puffs(0, 0.2);
            double dx = Math.cos(angle);
            double dy = Math.sin(angle);
            for (FirePuff puff : puffs) {
                double along = (puff.x() - 200) * dx + (puff.y() - 200) * dy;
                if (puff.layer() == FirePuff.Layer.SMOKE || puff.layer() == FirePuff.Layer.BODY) {
                    assertTrue(along < 0, puff.layer() + " behind the rocket at angle " + angle);
                }
            }
        }
    }

    @Test
    @DisplayName("every puff lies inside the effect's bounds")
    void withinBounds() {
        for (double angle : new double[] {0, Math.PI / 2, Math.PI, -Math.PI / 2, 0.7}) {
            RocketEffect rocket = new RocketEffect(50, 80, angle);
            double[] b = rocket.bounds();
            for (double clock : new double[] {0, 0.3, 1.3, 2.9}) {
                for (FirePuff puff : rocket.puffs(0, clock)) {
                    assertTrue(puff.x() - puff.radius() >= b[0] && puff.x() + puff.radius() <= b[2]
                            && puff.y() - puff.radius() >= b[1] && puff.y() + puff.radius() <= b[3],
                            "puff " + puff.layer() + " (radius included) inside the bounds at angle " + angle);
                }
            }
        }
    }

    @Test
    @DisplayName("the flame flickers with the clock")
    void flameFlickers() {
        RocketEffect rocket = new RocketEffect(0, 0, 0);
        double a = rocket.puffs(0, 0.0).stream().filter(p -> p.layer() == FirePuff.Layer.INNER)
                .mapToDouble(FirePuff::radius).max().orElse(0);
        double b = rocket.puffs(0, 0.011).stream().filter(p -> p.layer() == FirePuff.Layer.INNER)
                .mapToDouble(FirePuff::radius).max().orElse(0);
        assertTrue(a != b);
    }
}
