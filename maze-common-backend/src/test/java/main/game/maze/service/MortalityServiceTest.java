package main.game.maze.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.common.remains.DeathCause;
import main.game.maze.interfaces.IDeathSubscriber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("MortalityService")
class MortalityServiceTest {

    /** Minimal ICanDie that records the causes of the hits it takes. */
    private static final class Mortal implements ICanDie {
        private int hp;
        private final double x;
        private final double y;
        private final List<DeathCause> hits = new ArrayList<>();
        private boolean invulnerable;

        Mortal(int hp, double x, double y) {
            this.hp = hp;
            this.x = x;
            this.y = y;
        }

        @Override public int getHitPoints() { return hp; }
        @Override public void setHitPoints(int value) { hp = value; }
        @Override public void subtractHitPoints(int amount) { subtractHitPoints(amount, DeathCause.OTHER); }
        @Override public void subtractHitPoints(int amount, DeathCause cause) {
            hits.add(cause);
            if (!invulnerable) {
                hp -= amount;
            }
        }
        @Override public boolean isInvulnerable() { return invulnerable; }
        @Override public void addHitPoints(int amount) { hp += amount; }
        @Override public void addDeathNotificationSubscriber(IDeathSubscriber subscriber) { }
    }

    private static MortalityService.Body bodyOf(Mortal m) {
        return new MortalityService.Body(m.x, m.y, 10);
    }

    @Test
    @DisplayName("killAll kills every living ICanDie with the cause, skipping the dead and non-mortals")
    void killAll() {
        Mortal alive = new Mortal(40, 0, 0);
        Mortal dead = new Mortal(0, 0, 0);
        List<Object> characters = List.of(alive, dead, "not mortal");

        assertEquals(1, MortalityService.killAll(characters, DeathCause.FIRE));
        assertFalse(alive.isAlive());
        assertEquals(List.of(DeathCause.FIRE), alive.hits);
        assertTrue(dead.hits.isEmpty());
    }

    @Test
    @DisplayName("crushAlong crushes only living mortals whose body touches the wall")
    void crushAlong() {
        Mortal touching = new Mortal(50, 50, 100);   // on the wall line y = 100
        Mortal farAway = new Mortal(50, 50, 300);
        Mortal dead = new Mortal(0, 60, 100);

        int crushed = MortalityService.crushAlong(List.of(touching, farAway, dead),
                MortalityServiceTest::bodyOf, 0, 100, 200, 100);

        assertEquals(1, crushed);
        assertEquals(List.of(DeathCause.CRUSHED), touching.hits);
        assertFalse(touching.isAlive());
        assertTrue(farAway.isAlive());
        assertTrue(dead.hits.isEmpty());
    }

    @Test
    @DisplayName("protected enemies are neither killed nor counted, by killAll or crushAlong")
    void protectedEnemiesAreNotCounted() {
        Mortal protectedOne = new Mortal(40, 50, 100);
        protectedOne.invulnerable = true;
        Mortal normal = new Mortal(40, 60, 100);

        assertEquals(1, MortalityService.killAll(List.of(protectedOne, normal), DeathCause.FIRE));
        assertTrue(protectedOne.isAlive());
        assertTrue(protectedOne.hits.isEmpty(), "no hit is wasted on a protected enemy");

        Mortal protectedTwo = new Mortal(40, 50, 100);
        protectedTwo.invulnerable = true;
        assertEquals(0, MortalityService.crushAlong(List.of(protectedTwo), MortalityServiceTest::bodyOf, 0, 100, 200, 100));
        assertTrue(protectedTwo.isAlive());
    }

    @Test
    @DisplayName("a sledgehammer swing strikes every living, unprotected enemy within reach, not walls")
    void strikeAround() {
        Mortal close = new Mortal(200, 30, 0);       // 30 px away
        Mortal edge = new Mortal(200, 0, 55);        // body edge within reach (55 - 10 <= 50)
        Mortal far = new Mortal(200, 100, 0);
        Mortal shielded = new Mortal(200, -20, 0);
        shielded.invulnerable = true;

        List<ICanDie> struck = MortalityService.strikeAround(List.of(close, edge, far, shielded),
                MortalityServiceTest::bodyOf, 0, 0, 50, 120, DeathCause.CRUSHED, "player-1", (x, y) -> false);

        assertEquals(List.of(close, edge), struck);
        assertEquals(List.of(DeathCause.CRUSHED), close.hits);
        assertEquals(80, close.getHitPoints());
        assertTrue(far.hits.isEmpty());
        assertTrue(shielded.hits.isEmpty());
    }

    @Test
    @DisplayName("a wall between the player and an enemy blocks the swing")
    void strikeBlockedByWall() {
        Mortal behindWall = new Mortal(200, 30, 0);
        List<ICanDie> struck = MortalityService.strikeAround(List.of(behindWall), MortalityServiceTest::bodyOf,
                0, 0, 50, 120, DeathCause.CRUSHED, "player-1", (x, y) -> true);
        assertTrue(struck.isEmpty());
        assertTrue(behindWall.hits.isEmpty());
    }

    @Test
    @DisplayName("characters without a known body are not crushed")
    void noBodyNoCrush() {
        Mortal mortal = new Mortal(50, 50, 100);
        assertEquals(0, MortalityService.crushAlong(List.of(mortal), m -> null, 0, 100, 200, 100));
        assertTrue(mortal.isAlive());
    }

    @Test
    @DisplayName("ICanDie defaults: alive while HP > 0, death cause unknown unless tracked")
    void contractDefaults() {
        Mortal mortal = new Mortal(1, 0, 0);
        assertTrue(mortal.isAlive());
        assertEquals(DeathCause.OTHER, mortal.deathCause());
        mortal.setHitPoints(0);
        assertFalse(mortal.isAlive());
    }
}
