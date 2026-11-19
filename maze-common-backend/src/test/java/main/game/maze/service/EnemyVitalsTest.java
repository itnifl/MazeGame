package main.game.maze.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.common.remains.DeathCause;
import main.game.maze.interfaces.IDeathSubscriber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("EnemyVitals")
class EnemyVitalsTest {

    /** Owner that forwards ICanDie to its vitals, like the frontend enemies do. */
    private static final class Owner implements ICanDie {
        final EnemyVitals vitals;
        final List<String> events = new ArrayList<>();

        Owner(int maxHp) {
            vitals = new EnemyVitals(this, maxHp, new EnemyVitals.Listener() {
                @Override public void onHitPointsChanged(int hp, int max) { events.add("hp " + hp + "/" + max); }
                @Override public void onDied(DeathCause cause) { events.add("died " + cause); }
            });
        }

        @Override public int getHitPoints() { return vitals.getHitPoints(); }
        @Override public void setHitPoints(int hp) { vitals.setHitPoints(hp); }
        @Override public void subtractHitPoints(int hp) { vitals.subtractHitPoints(hp, DeathCause.OTHER); }
        @Override public void subtractHitPoints(int hp, DeathCause cause) { vitals.subtractHitPoints(hp, cause); }
        @Override public void addHitPoints(int hp) { vitals.addHitPoints(hp); }
        @Override public void addDeathNotificationSubscriber(IDeathSubscriber s) { vitals.addDeathSubscriber(s); }
        @Override public DeathCause deathCause() { return vitals.deathCause(); }
    }

    @Test
    @DisplayName("damage lowers HP; the killing blow sets the cause and notifies once")
    void damageAndDeath() {
        Owner owner = new Owner(50);
        List<ICanDie> deaths = new ArrayList<>();
        owner.addDeathNotificationSubscriber(deaths::add);

        owner.subtractHitPoints(20, DeathCause.FIRE);
        assertEquals(30, owner.getHitPoints());
        assertTrue(owner.isAlive());
        assertEquals(DeathCause.OTHER, owner.deathCause());

        owner.subtractHitPoints(100, DeathCause.SHOT);
        owner.subtractHitPoints(100, DeathCause.CRUSHED);

        assertEquals(0, owner.getHitPoints());
        assertFalse(owner.isAlive());
        assertEquals(DeathCause.SHOT, owner.deathCause());
        assertEquals(1, deaths.size());
        assertSame(owner, deaths.get(0));
        assertEquals(List.of("hp 30/50", "hp 0/50", "died SHOT"), owner.events);
    }

    @Test
    @DisplayName("healing is capped at max and does not revive; setHitPoints(0) is a death of unknown cause")
    void healingAndSetting() {
        Owner owner = new Owner(50);
        owner.subtractHitPoints(10);
        owner.addHitPoints(1_000);
        assertEquals(50, owner.getHitPoints());

        owner.setHitPoints(0);
        assertFalse(owner.isAlive());
        assertEquals(DeathCause.OTHER, owner.deathCause());
        owner.addHitPoints(10);
        assertFalse(owner.isAlive(), "the dead are not healed back to life");
    }

    @Test
    @DisplayName("after respawn the enemy is whole again and briefly invulnerable")
    void respawnAndInvulnerability() {
        Owner owner = new Owner(40);
        owner.subtractHitPoints(40, DeathCause.CRUSHED);

        owner.vitals.respawn(1.0);
        assertEquals(40, owner.getHitPoints());
        assertEquals(DeathCause.OTHER, owner.deathCause());
        assertTrue(owner.vitals.isInvulnerable());

        owner.subtractHitPoints(40, DeathCause.FIRE);
        assertEquals(40, owner.getHitPoints(), "no damage while invulnerable");

        owner.vitals.tick(0.6);
        assertTrue(owner.vitals.isInvulnerable());
        owner.vitals.tick(0.5);
        assertFalse(owner.vitals.isInvulnerable());
        owner.subtractHitPoints(40, DeathCause.FIRE);
        assertFalse(owner.isAlive());

        owner.vitals.respawn(5.0);
        owner.vitals.clearInvulnerability();
        assertFalse(owner.vitals.isInvulnerable());
    }

    @Test
    void validation() {
        assertThrows(IllegalArgumentException.class, () -> new EnemyVitals(null, 10, null));
        assertEquals(1, new Owner(0).getHitPoints(), "max HP is at least 1");
    }

    @Test
    @DisplayName("concurrent hits: the last HP delivered to the listener is the current HP, never a stale one")
    void notificationsAreNotDeliveredOutOfOrder() throws Exception {
        AtomicReference<EnemyVitals> vitalsRef = new AtomicReference<>();
        AtomicReference<Thread> secondHit = new AtomicReference<>();
        List<Integer> delivered = Collections.synchronizedList(new ArrayList<>());
        EnemyVitals vitals = new EnemyVitals(new Owner(100), 100, new EnemyVitals.Listener() {
            @Override
            public void onHitPointsChanged(int hp, int max) {
                if (secondHit.get() == null) {
                    // thread A is notifying about its hit; thread B lands a second hit meanwhile
                    Thread b = new Thread(() -> vitalsRef.get().subtractHitPoints(60, DeathCause.FIRE));
                    secondHit.set(b);
                    b.start();
                    long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(2);
                    while (vitalsRef.get().getHitPoints() != 20 && System.nanoTime() < deadline) {
                        Thread.onSpinWait();
                    }
                }
                delivered.add(hp);
            }
        });
        vitalsRef.set(vitals);

        vitals.subtractHitPoints(20, DeathCause.FIRE);   // thread A
        secondHit.get().join(3_000);

        assertEquals(20, vitals.getHitPoints());
        assertEquals(20, delivered.get(delivered.size() - 1).intValue(), "stale HP delivered last: " + delivered);
    }
}
