package main.game.maze.common.graphics.fire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Mine and hole visuals: a pulsating mine and a bottomless hole, living as long as they are in the level")
class HazardEffectsTest {

    private static double glowAt(MinePulseEffect mine, double clock) {
        return mine.puffs(0, clock).stream()
                .filter(p -> p.layer() == FirePuff.Layer.BODY)
                .mapToDouble(FirePuff::alpha).max().orElse(0);
    }

    @Test
    @DisplayName("a mine and a hole last practically for ever: the level removes them, not the clock")
    void persistent() {
        assertTrue(new MinePulseEffect(0, 0, 0, 0.9).lifetimeSeconds() > 3600);
        assertTrue(new HoleEffect(0, 0, 30).lifetimeSeconds() > 3600);
    }

    @Test
    @DisplayName("when completed (progress >= 1), an empty puff list is returned")
    void completedReturnsEmptyList() {
        MinePulseEffect mine = new MinePulseEffect(100, 100, 0.0, 0.9);
        HoleEffect hole = new HoleEffect(100, 100, 30);
        assertTrue(mine.puffs(1.0, 0.5).isEmpty());
        assertTrue(mine.puffs(1.2, 0.5).isEmpty());
        assertTrue(hole.puffs(1.0, 0.5).isEmpty());
        assertTrue(hole.puffs(1.5, 0.5).isEmpty());
    }

    @Test
    @DisplayName("a mine pulses: its glow swells and fades once per beat, and repeats every beat")
    void minePulses() {
        MinePulseEffect mine = new MinePulseEffect(100, 100, 0.0, 0.9);
        double min = 1;
        double max = 0;
        for (double clock = 0; clock < 0.9; clock += 0.01) {
            double glow = glowAt(mine, clock);
            min = Math.min(min, glow);
            max = Math.max(max, glow);
        }
        assertTrue(max > min + 0.1, "the pulse changes: " + min + ".." + max);
        assertEquals(glowAt(mine, 0.31), glowAt(mine, 0.31 + 0.9), 1e-6, "periodic");
    }

    @Test
    @DisplayName("neighbouring mines pulse out of step")
    void phases() {
        double a = glowAt(new MinePulseEffect(0, 0, 0.0, 0.9), 0.2);
        double b = glowAt(new MinePulseEffect(0, 0, 0.5, 0.9), 0.2);
        assertTrue(Math.abs(a - b) > 0.05, a + " vs " + b);
    }

    @Test
    @DisplayName("a mine is an olive-drab anti-personnel mine with a faint light-blue pulse, drawn where it lies")
    void mineLooks() {
        List<FirePuff> puffs = new MinePulseEffect(100, 100, 0.2, 0.9).puffs(0, 0.4);
        assertTrue(puffs.stream().anyMatch(p -> p.layer() == FirePuff.Layer.SMOKE && p.alpha() >= 0.99f), "a solid body");
        assertTrue(puffs.stream().anyMatch(p -> p.layer() == FirePuff.Layer.SMOKE && p.alpha() >= 0.99f
                && p.green() > p.red() && p.red() > p.blue() + 0.1f), "olive drab, like an army mine");
        List<FirePuff> pulse = puffs.stream().filter(p -> p.layer() == FirePuff.Layer.BODY).toList();
        assertFalse(pulse.isEmpty(), "a pulse round the mine");
        for (FirePuff p : pulse) {
            assertTrue(p.blue() > p.red() + 0.3f && p.green() > p.red(), "light blue: " + p);
            assertTrue(p.alpha() <= 0.45f, "faint: " + p.alpha());
        }
        assertTrue(puffs.stream().noneMatch(p -> p.layer() == FirePuff.Layer.CORE), "no bright star in the middle");
        for (FirePuff p : puffs) {
            assertTrue(Math.hypot(p.x() - 100, p.y() - 100) < 30, "stays on the mine");
        }
    }

    @Test
    @DisplayName("a hole is a solid black pit with an earthen rim, and something swirls into it")
    void holeLooks() {
        List<FirePuff> puffs = new HoleEffect(200, 150, 30).puffs(0, 1.3);
        FirePuff pit = puffs.stream()
                .filter(p -> p.layer() == FirePuff.Layer.SMOKE && p.red() < 0.05f && p.green() < 0.05f)
                .findFirst().orElse(null);
        assertTrue(pit != null, "a black pit");
        assertTrue(pit.radius() >= 30 * 0.6, "covers most of the hole: " + pit.radius());
        assertEquals(1.0f, pit.alpha(), 1e-6f, "solid, not see-through");
        assertTrue(puffs.stream().anyMatch(p -> p.layer() == FirePuff.Layer.SMOKE && p.radius() > pit.radius()), "an earthen rim");
        assertTrue(puffs.stream().anyMatch(p -> p.layer() == FirePuff.Layer.EMBER), "sparks spiralling in");
    }

    @Test
    @DisplayName("what swirls into the hole moves with the clock")
    void holeSwirls() {
        HoleEffect hole = new HoleEffect(0, 0, 30);
        assertFalse(hole.puffs(0, 0.0).equals(hole.puffs(0, 0.25)));
    }

    @Test
    @DisplayName("every puff stays inside the bounds")
    void bounds() {
        MinePulseEffect mine = new MinePulseEffect(40, 60, 0.3, 0.9);
        HoleEffect hole = new HoleEffect(40, 60, 30);
        for (double clock = 0; clock < 3; clock += 0.17) {
            for (PuffEffect effect : List.of(mine, hole)) {
                double[] b = effect.bounds();
                for (FirePuff p : effect.puffs(0, clock)) {
                    assertTrue(p.x() - p.radius() >= b[0] - 1e-6 && p.x() + p.radius() <= b[2] + 1e-6
                            && p.y() - p.radius() >= b[1] - 1e-6 && p.y() + p.radius() <= b[3] + 1e-6,
                            effect.getClass().getSimpleName() + " " + p);
                }
            }
        }
    }
}
