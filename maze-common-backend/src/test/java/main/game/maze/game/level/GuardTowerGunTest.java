package main.game.maze.game.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GuardTowerGunTest {

    private static GuardTowerSpec spec() {
        return new GuardTowerSpec("Guard tower", 3000, 650, 3, 3.0, 300, 60, 25, 40, 6, 0.1, 3, 60, "t.png",
                null, null, null, null, null);
    }

    @Test
    void rpgEveryThreeSecondsAndGatlingBetween() {
        GuardTowerGun gun = new GuardTowerGun(spec());
        int rpgs = 0;
        int rounds = 0;
        double lastRpg = -1;
        double firstRoundAfterRpg = -1;
        for (int i = 0; i < 1000; i++) {   // 10 seconds in 10 ms steps
            GuardTowerGun.Shots shots = gun.tick(0.01, true);
            double t = i * 0.01;
            if (shots.rpgs() > 0) {
                rpgs += shots.rpgs();
                lastRpg = t;
            }
            if (shots.gatlingRounds() > 0 && firstRoundAfterRpg < 0) {
                firstRoundAfterRpg = t - lastRpg;
            }
            rounds += shots.gatlingRounds();
        }
        assertEquals(4, rpgs, "rpgs at 0, 3, 6 and 9 seconds");
        assertEquals(18, rounds, "three complete bursts of six rounds");
        assertEquals(1.5, firstRoundAfterRpg, 0.05, "gatling starts halfway between two rpgs");
    }

    @Test
    void gatlingNeverFiresInTheSameTickAsAnRpg() {
        GuardTowerGun gun = new GuardTowerGun(spec());
        for (int i = 0; i < 700; i++) {
            GuardTowerGun.Shots shots = gun.tick(0.01, true);
            assertFalse(shots.rpgs() > 0 && shots.gatlingRounds() > 0, "tick " + i);
        }
    }

    @Test
    void silentWhileThePlayerCannotBeSeenButTheCycleKeepsRunning() {
        GuardTowerGun gun = new GuardTowerGun(spec());
        assertEquals(1, gun.tick(0.0, true).rpgs(), "the first rpg is immediate");
        for (int i = 0; i < 400; i++) {
            assertEquals(GuardTowerGun.Shots.NONE, gun.tick(0.01, false));
        }
        // 4.0 s have passed: the next rpg is due at 6.0 s
        int rpgs = 0;
        for (int i = 0; i < 100; i++) {
            rpgs += gun.tick(0.01, true).rpgs();
        }
        assertEquals(0, rpgs);
        for (int i = 0; i < 105; i++) {
            rpgs += gun.tick(0.01, true).rpgs();
        }
        assertEquals(1, rpgs);
    }

    @Test
    void aLongFrameStillCountsEveryShot() {
        GuardTowerGun gun = new GuardTowerGun(spec());
        GuardTowerGun.Shots shots = gun.tick(6.5, true);
        assertEquals(3, shots.rpgs());
        assertEquals(12, shots.gatlingRounds());
    }

    @Test
    void shootsOverUpToThreeWallsAndOnlyInRange() {
        GuardTowerGun gun = new GuardTowerGun(spec());
        assertTrue(gun.canSee(0, 0, 300, 0, 0));
        assertTrue(gun.canSee(0, 0, 300, 0, 3));
        assertFalse(gun.canSee(0, 0, 300, 0, 4));
        assertFalse(gun.canSee(0, 0, 700, 0, 0));
    }

    @Test
    void resetRestartsTheCycle() {
        GuardTowerGun gun = new GuardTowerGun(spec());
        gun.tick(2.0, true);
        gun.reset();
        assertEquals(1, gun.tick(0.0, true).rpgs());
    }

    @Test
    void aGatlingBurstMustFitBetweenTwoRpgs() {
        assertThrows(IllegalArgumentException.class, () -> new GuardTowerSpec("x", 3000, 650, 3, 3.0, 300, 60, 25, 40,
                20, 0.1, 3, 60, "t.png", null, null, null, null, null));
    }
}
