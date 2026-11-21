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

    @Test
    @DisplayName("rockets take 300 px / 300 px/s to fly, then explode where the player was")
    void rocketsFlyThenExplode() {
        Result first = session.tick(0.0, List.of(tower(SPEC.health())), 300, 0, (a, b, c, d) -> 0);
        assertEquals(1, first.launches().size());
        assertEquals(1, session.rockets().size());
        assertEquals(0, first.impacts().size());
        Result midway = session.tick(0.5, List.of(tower(SPEC.health())), 300, 0, (a, b, c, d) -> 0);
        assertEquals(0, midway.impacts().size());
        assertEquals(150, session.rockets().get(0).x(), 15, "about halfway");
        Result landed = session.tick(0.6, List.of(tower(SPEC.health())), 300, 0, (a, b, c, d) -> 0);
        assertEquals(1, landed.impacts().size());
        assertEquals(300, landed.impacts().get(0).x(), 1e-6);
        assertTrue(session.rockets().isEmpty());
        assertEquals(SPEC.rpgPlayerDamage(), landed.playerDamage(), "the player stood in the blast");
    }

    @Test
    @DisplayName("a rocket that lands out of reach does no damage to the player")
    void dodgedRocket() {
        session.tick(0.0, List.of(tower(SPEC.health())), 300, 0, (a, b, c, d) -> 0);
        Result landed = session.tick(1.2, List.of(tower(SPEC.health())), 300 + SPEC.rpgBlastRadius() + 20, 0, (a, b, c, d) -> 0);
        assertEquals(1, landed.impacts().size());
        assertEquals(0, landed.playerDamage());
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
