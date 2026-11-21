package main.game.maze.game.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import main.game.maze.game.level.GuardTowerSession.Event;
import main.game.maze.game.level.GuardTowerSession.Result;
import main.game.maze.game.level.GuardTowerSession.Tower;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Guard tower session: rpgs every 3 s, gatling in between, hits and destruction noticed once")
class GuardTowerSessionTest {

    private static final GuardTowerSpec SPEC = LevelCatalogLoader.loadDefault().level(4).guardTower();
    private final GuardTowerSession session = new GuardTowerSession(SPEC);

    private static Tower tower(int health) {
        return new Tower("tower_1", 0, 0, health);
    }

    /** Runs the session for {@code seconds}, 20 ms at a time, with the player 300 px to the right. */
    private int[] run(double seconds, int walls, List<Tower> towers) {
        int rpgs = 0;
        int rounds = 0;
        int landed = 0;
        int damage = 0;
        for (int i = 0; i < Math.round(seconds / 0.02); i++) {
            Result result = session.tick(0.02, towers, 300, 0, (a, b, c, d) -> walls);
            rpgs += result.launches().size();
            rounds += result.rounds().size();
            landed += result.impacts().size();
            damage += result.playerDamage();
        }
        return new int[] {rpgs, rounds, landed, damage};
    }

    @Test
    @DisplayName("a tower that sees the player fires an rpg every 3 seconds and a gatling burst in between")
    void firesToTheCycle() {
        int[] totals = run(9.5, 0, List.of(tower(SPEC.health())));
        assertEquals(4, totals[0], "rpgs at 0, 3, 6 and 9 seconds");
        assertEquals(18, totals[1], "gatling bursts at 1.5, 4.5 and 7.5 seconds");
    }

    /** Ticks for {@code seconds} in small steps (a tick never advances more than MAX_TICK_SECONDS) and collects the impacts. */
    private List<GuardTowerSession.Impact> fly(double seconds, double playerX, GuardTowerSession.WallCounter walls) {
        List<GuardTowerSession.Impact> impacts = new java.util.ArrayList<>();
        List<Tower> towers = List.of(new Tower("tower_1", 0, 0, 1));
        for (int i = 0; i < Math.round(seconds / 0.05); i++) {
            Result r = session.tick(0.05, towers, playerX, 0, walls);
            impacts.addAll(r.impacts());
            damageSeen += r.playerDamage();
        }
        return impacts;
    }

    private int damageSeen;

    @Test
    @DisplayName("rockets take 300 px / 300 px/s to fly, then explode where the player was")
    void rocketsFlyThenExplode() {
        Result first = session.tick(0.0, List.of(tower(SPEC.health())), 300, 0, (a, b, c, d) -> 0);
        assertEquals(1, first.launches().size());
        assertEquals(1, session.rockets().size());
        assertEquals(0, first.impacts().size());
        // the player stands in the blast on arrival; the tower's own cycle fires nothing more in 1.3 s but its gatling
        List<Tower> towers = List.of(tower(SPEC.health()));
        for (int i = 0; i < 10; i++) {
            session.tick(0.05, towers, 300, 0, (a, b, c, d) -> 0);
        }
        assertEquals(150, session.rockets().get(0).x(), 20, "about halfway after half a second");
        List<GuardTowerSession.Impact> landed = new java.util.ArrayList<>();
        int rocketDamage = 0;
        for (int i = 0; i < 12; i++) {
            Result r = session.tick(0.05, towers, 300, 0, (a, b, c, d) -> 0);
            landed.addAll(r.impacts());
            rocketDamage += r.impacts().isEmpty() ? 0 : r.playerDamage();
        }
        assertEquals(1, landed.size());
        assertEquals(300, landed.get(0).x(), 1e-6);
        assertTrue(session.rockets().isEmpty());
        assertTrue(rocketDamage >= SPEC.rpgPlayerDamage(), "the player stood in the blast");
    }

    @Test
    @DisplayName("a rocket that lands out of reach does no damage to the player")
    void dodgedRocket() {
        session.tick(0.0, List.of(tower(SPEC.health())), 300, 0, (a, b, c, d) -> 0);
        damageSeen = 0;
        // the tower itself is silent after the launch (4 walls from it); the impact-to-player line is open
        GuardTowerSession.WallCounter quietTower = (fx, fy, tx, ty) -> fx == 0 ? 4 : 0;
        List<GuardTowerSession.Impact> landed = fly(1.2, 300 + SPEC.rpgBlastRadius() + 20, quietTower);
        assertEquals(1, landed.size());
        assertEquals(0, damageSeen);
    }

    @Test
    @DisplayName("a wall between the impact and the player shields them from the splash")
    void wallsShieldFromSplash() {
        session.tick(0.0, List.of(tower(SPEC.health())), 300, 0, (a, b, c, d) -> 0);
        damageSeen = 0;
        // gun silent (4 walls from the tower), only the impact-to-player line is "open" or "walled"
        GuardTowerSession.WallCounter walled = (fx, fy, tx, ty) -> fx == 0 ? 4 : 1;
        List<GuardTowerSession.Impact> landed = fly(1.3, 300, walled);
        assertEquals(1, landed.size());
        assertEquals(0, damageSeen, "the wall took the blast");
    }

    @Test
    @DisplayName("after a long pause the towers carry on from where they were instead of firing it all at once")
    void aPauseDoesNotBurstFire() {
        Result paused = session.tick(30.0, List.of(tower(SPEC.health())), 150, 0, (a, b, c, d) -> 0);
        assertTrue(paused.launches().size() <= 1, "at most the shot of one capped tick: " + paused.launches().size());
        assertTrue(paused.impacts().isEmpty());
    }

    @Test
    @DisplayName("every gatling round hurts the player by the tower's round damage")
    void gatlingDamage() {
        int[] totals = run(1.9, 0, List.of(tower(SPEC.health())));
        assertTrue(totals[1] > 0);
        assertTrue(totals[3] >= totals[1] * SPEC.gatlingDamage());
    }

    @Test
    @DisplayName("four walls between: no shooting; three walls: shooting over them")
    void shootsOverThreeWallsNotFour() {
        assertEquals(0, run(7, 4, List.of(tower(SPEC.health())))[0]);
        session.reset();
        assertTrue(run(7, 3, List.of(tower(SPEC.health())))[0] >= 2);
    }

    @Test
    @DisplayName("a destroyed tower stops firing")
    void deadTowersAreSilent() {
        assertEquals(0, run(7, 0, List.of(tower(0)))[0]);
    }

    @Test
    @DisplayName("a hit and a destruction are each reported once, for the sounds")
    void hitAndDestroyed() {
        session.tick(0.0, List.of(tower(SPEC.health())), 5000, 0, (a, b, c, d) -> 0);
        Result hit = session.tick(0.02, List.of(tower(SPEC.health() - 50)), 5000, 0, (a, b, c, d) -> 0);
        assertEquals(1, hit.events().size());
        assertEquals(Event.HIT, hit.events().get(0).event());
        Result same = session.tick(0.02, List.of(tower(SPEC.health() - 50)), 5000, 0, (a, b, c, d) -> 0);
        assertTrue(same.events().isEmpty());
        Result destroyed = session.tick(0.02, List.of(tower(0)), 5000, 0, (a, b, c, d) -> 0);
        assertEquals(Event.DESTROYED, destroyed.events().get(0).event());
        assertTrue(session.tick(0.02, List.of(tower(0)), 5000, 0, (a, b, c, d) -> 0).events().isEmpty(), "reported once");
    }

    @Test
    @DisplayName("reset clears the rockets in the air")
    void resetClears() {
        session.tick(0.0, List.of(tower(SPEC.health())), 300, 0, (a, b, c, d) -> 0);
        session.reset();
        assertTrue(session.rockets().isEmpty());
    }
}
