package main.game.maze.game.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.game.targeting.SoldierTargets;
import main.game.maze.mazeworld.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Sergeant gatling gun: fires bursts on sight of the player")
class SergeantGunnerTest {

    private SergeantSpec spec;
    private SergeantGunner gunner;

    @BeforeEach
    void setUp() {
        spec = LevelCatalogLoader.loadDefault().level(2).sergeant();
        gunner = new SergeantGunner(spec);
    }

    @Test
    @DisplayName("sees the player only within range and with no wall in between")
    void sight() {
        assertTrue(gunner.canSee(100, 100, 200, 100, false));
        assertFalse(gunner.canSee(100, 100, 200, 100, true), "a wall blocks the view");
        assertFalse(gunner.canSee(100, 100, 100 + spec.sightRange() + 1, 100, false), "too far away");
        assertTrue(gunner.canSee(100, 100, 100 + spec.sightRange() - 1, 100, false));
    }

    @Test
    @DisplayName("never fires without seeing the player")
    void holdsFireWhenBlind() {
        for (int i = 0; i < 100; i++) {
            assertEquals(0, gunner.tick(0.1, false));
        }
    }

    @Test
    @DisplayName("fires the first round at once and the rest at the round interval")
    void burstTiming() {
        assertEquals(1, gunner.tick(0.0, true), "first round on sight");
        assertEquals(0, gunner.tick(spec.roundIntervalSeconds() * 0.5, true));
        assertEquals(1, gunner.tick(spec.roundIntervalSeconds() * 0.5, true));
    }

    @Test
    @DisplayName("a burst is exactly roundsPerBurst rounds, then the gun cools down before the next one")
    void burstThenCooldown() {
        int fired = 0;
        double elapsed = 0;
        double step = 0.01;
        while (elapsed < spec.burstCooldownSeconds() - 0.05) {
            fired += gunner.tick(step, true);
            elapsed += step;
        }
        assertEquals(spec.roundsPerBurst(), fired, "one burst until the cooldown is over");

        // well past the cooldown and the second burst
        for (int i = 0; i < 60; i++) {
            fired += gunner.tick(step, true);
        }
        assertEquals(spec.roundsPerBurst() * 2, fired, "the second burst starts after the cooldown");
    }

    @Test
    @DisplayName("a long frame mid-burst fires only the rest of that burst")
    void longFrame() {
        assertEquals(1, gunner.tick(0.0, true));
        assertEquals(spec.roundsPerBurst() - 1, gunner.tick(0.9, true));
    }

    @Test
    @DisplayName("losing sight of the player ends the burst")
    void losingSightStopsTheBurst() {
        assertEquals(1, gunner.tick(0.0, true));
        assertEquals(0, gunner.tick(1.0, false), "the player ducked behind a wall");
        assertEquals(0, gunner.tick(spec.roundIntervalSeconds(), false));
    }

    @Test
    @DisplayName("reset gives a fresh gun")
    void reset() {
        gunner.tick(0.0, true);
        gunner.reset();
        assertEquals(1, gunner.tick(0.0, true), "no cooldown left after a reset");
    }

    @Test
    @DisplayName("fires at a soldier it sees before the player, and at the player when no soldier is in sight")
    void firesAtSoldiersFirst() {
        Point2D soldier = new Point2D(160, 100);
        SoldierTargets soldiers = SoldierTargets.of(() -> List.of(soldier), (at, damage) -> { });
        SergeantGunner.Volley volley = gunner.fire(0.0, 100, 100, 200, 100, soldiers, (a, b, c, d) -> false);
        assertTrue(volley.rounds() > 0);
        assertTrue(volley.target().isSoldier());
        assertEquals(160, volley.target().x(), 1e-9);

        SergeantGunner other = new SergeantGunner(spec);
        SergeantGunner.Volley atPlayer = other.fire(0.0, 100, 100, 200, 100, soldiers,
                (fromX, fromY, toX, toY) -> toX == 160);
        assertFalse(atPlayer.target().isSoldier(), "the soldier stands behind a wall");
        assertTrue(atPlayer.rounds() > 0);
        assertEquals(0, new SergeantGunner(spec).fire(0.0, 100, 100, 200, 100, SoldierTargets.NONE,
                (a, b, c, d) -> true).rounds(), "nobody in sight");
    }
}
