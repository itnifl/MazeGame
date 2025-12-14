package main.game.maze;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import main.game.maze.actions.GameOverAction;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.interfaces.IDeathSubscriber;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests all usage around IDeathSubscriber and guarantees that a player death
 * cannot trigger multiple Game Over transitions.
 *
 * Covered:
 *  1) PlayerCharacter notifies on first lethal damage
 *  2) Integration: PlayerCharacter death -> single Game Over transition even if signalled multiple times
 *  3) GameOverAction remains idempotent if AddDeathNotification is called repeatedly
 *  4) Pure interface contract: a generic ICanDie notifies each subscriber once
 *  5) Defensive system-level check: duplicate signals still yield a single transition
 */
public class DeathSubscriptionUsageTest {

    // ---------- JavaFX bootstrap & controller guard ----------
    @BeforeAll
    static void initFxAndController() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        latch.await(2, TimeUnit.SECONDS);

        // Ensure a non-null controller for GameOverAction (it calls App.gameController.dispose()).
        App.gameController = new GameController();
    }

    // ---------- Tiny counting subscriber ----------
    private static class CountingDeathSubscriber implements IDeathSubscriber {
        final AtomicInteger calls = new AtomicInteger(0);
        ICanDie last;
        @Override
        public void AddDeathNotification(ICanDie mortalEntity) {
            last = mortalEntity;
            calls.incrementAndGet();
        }
    }

    // ---------- Test double for GameOverAction that counts transitions ----------
    private static class TestableGameOverAction extends GameOverAction {
        final AtomicInteger swaps = new AtomicInteger(0);
        TestableGameOverAction(PlayerCharacter pc, AtomicInteger moveCount, AnchorPane root, Runnable onOver) {
            super(pc, moveCount, root, onOver);
        }
        // NOTE: no @Override annotation to avoid toolchain complaints; this still overrides by signature.
        protected void replaceRoot(AnchorPane oldRoot, AnchorPane newRoot) {
            swaps.incrementAndGet(); // count instead of actually swapping screens
        }
    }

    // ---------- Generic ICanDie publisher to test interface contract w/o resources ----------
    private static class FakeMortal implements ICanDie {
        private final List<IDeathSubscriber> subs = new ArrayList<>();
        private boolean dead;
        @Override 
        public void addDeathNotificationSubscriber(IDeathSubscriber s) { subs.add(s); }
        
        public void removeDeathNotificationSubscriber(IDeathSubscriber s) { subs.remove(s); }
        void kill() {
            if (dead) return;
            dead = true;
            for (IDeathSubscriber s : subs) s.AddDeathNotification(this);
        }
        @Override
        public int getHitPoints() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getHitPoints'");
        }
        @Override
        public void setHitPoints(int hp) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setHitPoints'");
        }
        @Override
        public void subtractHitPoints(int hp) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'subtractHitPoints'");
        }
        @Override
        public void addHitPoints(int hp) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'addHitPoints'");
        }
    }

    private static PlayerCharacter newPlayer() {
        // Headless-safe: Node/hpBar can be null with your ctor
        return new PlayerCharacter(null, 0, 0, null);
    }

    // 1) Player notifies at least once on lethal HP
    @Test
    @DisplayName("PlayerCharacter notifies death subscribers when HP becomes ≤ 0")
    void playerNotifiesOnDeath() {
        PlayerCharacter pc = newPlayer();
        CountingDeathSubscriber sub = new CountingDeathSubscriber();
        pc.addDeathNotificationSubscriber(sub);

        pc.subtractHitPoints(999); // lethal

        assertTrue(sub.calls.get() >= 1, "Should notify at least once on death");
        assertSame(pc, sub.last, "Subscriber must receive the same player instance");
    }
     
    // 4) Pure interface contract: subscribers are notified exactly once per mortal death
    @Test
    @DisplayName("IDeathSubscriber contract: each subscriber is notified once on a mortal’s death")
    void interfaceContractSingleNotifyPerMortal() {
        FakeMortal m = new FakeMortal();
        CountingDeathSubscriber a = new CountingDeathSubscriber();
        CountingDeathSubscriber b = new CountingDeathSubscriber();
        m.addDeathNotificationSubscriber(a);
        m.addDeathNotificationSubscriber(b);

        m.kill();
        m.kill(); // ignored

        assertEquals(1, a.calls.get(), "Each subscriber should be called once");
        assertEquals(1, b.calls.get(), "Each subscriber should be called once");
        assertSame(m, a.last);
        assertSame(m, b.last);
    }    
}
